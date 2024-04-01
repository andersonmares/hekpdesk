package anderson.helpdesk.domain.usecase;

import anderson.helpdesk.domain.entities.TicketFeedback;
import anderson.helpdesk.mapper.TicketFeedbackMapper;
import anderson.helpdesk.repository.TicketFeedbackRepository;
import anderson.helpdesk.dto.TicketFeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ProvideFeedback {

    private final TicketFeedbackRepository ticketFeedbackRepository;

    @Autowired
    public ProvideFeedback(TicketFeedbackRepository ticketFeedbackRepository) {
        this.ticketFeedbackRepository = ticketFeedbackRepository;
    }

    public TicketFeedbackDTO execute(Long userId, Long ticketId, int rating, String comment) {
        TicketFeedback feedback = new TicketFeedback();
        feedback.setUserId(userId);
        feedback.setTicketId(ticketId);
        feedback.setRating(rating);
        feedback.setComment(comment);
        feedback.setCreatedAt(LocalDateTime.now());
        TicketFeedback savedFeedback = ticketFeedbackRepository.save(feedback);
        return TicketFeedbackMapper.INSTANCE.ticketFeedbackToTicketFeedbackDTO(savedFeedback);
    }
}
