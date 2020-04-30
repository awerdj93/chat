package backend.service;

import java.util.List;

import backend.dto.MessageDTO;

public interface MessageService {

	public Long create(MessageDTO messageDTO);

	public void update(MessageDTO messageDTO);
	
	public MessageDTO findMessageById(Long messageId);

	public List<MessageDTO> findAllByUserId(Long userId);
	
	public void deleteById(Long id);
	
}
