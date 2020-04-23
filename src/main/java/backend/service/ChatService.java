package backend.service;

import java.util.List;

import backend.dto.ChatDTO;

public interface ChatService {
	
	public long createChat(ChatDTO chatDTO);

	public ChatDTO findChatByRecipient(long userId, long recipientId);
	
	public List<ChatDTO> findAll(long userId);
}
