package anderson.helpdesk.domain.usecase;

import anderson.helpdesk.domain.entities.TicketStatus;
import anderson.helpdesk.mapper.TicketMapper;
import anderson.helpdesk.repository.TicketRepository;
import anderson.helpdesk.domain.entities.Ticket;
import anderson.helpdesk.dto.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CreateTicket {

    private final TicketRepository ticketRepository;

    @Autowired
    public CreateTicket(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketDTO execute(Long userId, String subject, String description) {
        Ticket newTicket = new Ticket();
        newTicket.setUserId(userId);
        newTicket.setSubject(subject);
        newTicket.setDescription(description);
        newTicket.setStatus(TicketStatus.OPEN);
        newTicket.setCreatedAt(LocalDateTime.now());
        Ticket savedTicket = ticketRepository.save(newTicket);
        return TicketMapper.INSTANCE.ticketToTicketDTO(savedTicket);
    }
}
