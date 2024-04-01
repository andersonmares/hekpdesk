package anderson.helpdesk.mapper;

import anderson.helpdesk.domain.entities.TicketFeedback;
import anderson.helpdesk.dto.TicketFeedbackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketFeedbackMapper {
    TicketFeedbackMapper INSTANCE = Mappers.getMapper(TicketFeedbackMapper.class);

    TicketFeedbackDTO ticketFeedbackToTicketFeedbackDTO(TicketFeedback ticketFeedback);
    TicketFeedback ticketFeedbackDTOToTicketFeedback(TicketFeedbackDTO ticketFeedbackDTO);
}
