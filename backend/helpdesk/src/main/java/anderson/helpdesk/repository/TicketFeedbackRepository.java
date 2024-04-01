package anderson.helpdesk.repository;

import anderson.helpdesk.domain.entities.TicketFeedback;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketFeedbackRepository extends JpaRepository<TicketFeedback, Long> {
    Optional<TicketFeedback> findByTicketId(Long ticketId);
}
