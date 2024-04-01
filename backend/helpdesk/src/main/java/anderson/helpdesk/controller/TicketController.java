package anderson.helpdesk.controller;

import anderson.helpdesk.service.SseService;
import anderson.helpdesk.service.UserService;
import anderson.helpdesk.domain.entities.Role;
import anderson.helpdesk.domain.entities.Ticket;
import anderson.helpdesk.domain.entities.User;
import anderson.helpdesk.domain.usecase.CloseTicket;
import anderson.helpdesk.domain.usecase.CreateTicket;
import anderson.helpdesk.domain.usecase.FilterTickets;
import anderson.helpdesk.dto.TicketDTO;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final CreateTicket createTicket;

    @Autowired
    private final CloseTicket closeTicket;
    @Autowired
    private UserService userService;
    @Autowired
    private FilterTickets filterTickets;
    private final SseService sseService;

    @Autowired
    public TicketController(CreateTicket createTicket,
            CloseTicket closeTicket, SseService sseService, 
            UserService userService, FilterTickets filterTickets) {
        this.createTicket = createTicket;
        this.closeTicket = closeTicket;
        this.sseService = sseService;
        this.userService = userService;
        this.filterTickets = filterTickets;
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        TicketDTO createdTicket = createTicket.execute(ticketDTO.getUserId(), ticketDTO.getSubject(),
                ticketDTO.getDescription());
        sseService.sendTicketToAll(createdTicket);
        return ResponseEntity.ok(createdTicket);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> listUserTicketsByFilter(
            @PathVariable Long userId,
            @RequestParam(required = false) String filter,
            @PageableDefault Pageable pageable) {

        Page<Ticket> tickets = filterTickets.execute(userId, filter, pageable);
        return ResponseEntity.ok(tickets);
    }

    @PatchMapping("/{ticketId}/close")
    public ResponseEntity<?> closeTicket(@PathVariable Long ticketId) {
        if (ticketId == null) {
            return ResponseEntity.badRequest().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserFromAuthentication(authentication);

        if (user.getRoles().contains(Role.MANAGER)) {
            @SuppressWarnings("null")
            Optional<Ticket> closedTicket = closeTicket.execute(user.getId(), ticketId);
            return closedTicket.map(ticket -> ResponseEntity.ok().build())
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
