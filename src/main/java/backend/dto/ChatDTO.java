package backend.dto;

import java.util.ArrayList;
import java.util.List;

import backend.model.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDTO {
	private Long id;
	private Long recipientId;
	private String recipientName;
	private Long sender;
	private MessageDTO lastMessage;

	List<MessageDTO> messages = new ArrayList<>();
}
