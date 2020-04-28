package backend.dto;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

	private long id;
	private Long recipient;
	private String message;
	private Long sequence;
	private Long sender;
	private ZonedDateTime msgDate;
	private Long chatId;
	private Long createdBy;
	private Long updatedBy;
}
