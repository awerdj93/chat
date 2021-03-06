package backend.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dto.MessageDTO;
import backend.exception.NotFoundException;
import backend.model.Chat;
import backend.model.Message;
import backend.repository.ChatRepository;
import backend.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ChatRepository chatRepository;

	@Override
	public Long create(MessageDTO messageDTO) {
		Message message = new Message();
		BeanUtils.copyProperties(messageDTO, message);
		System.out.println(messageDTO.getCreatedAt());

		System.out.println(message.getCreatedAt());
		Optional<Chat> opt = chatRepository.findById(messageDTO.getChatId());
		if (!opt.isPresent()) {
			throw new NotFoundException();
		} 
		message.setChat(opt.get());
		message = messageRepository.save(message);
		
		return message.getId();
	}

	@Override
	public void update(MessageDTO messageDTO) {
		Message message = new Message();
		BeanUtils.copyProperties(messageDTO, message);
		messageRepository.save(message);
	}

	@Override
	public List<MessageDTO> findAllByUserId(Long userId) {
		Iterable<Message> iterable = messageRepository.findAllByCreatedBy(userId);

		List<MessageDTO> result = StreamSupport.stream(iterable.spliterator(), false).map(new Function<Message, MessageDTO>() {
			@Override
			public MessageDTO apply(Message message) {
				MessageDTO messageDTO = new MessageDTO();
				BeanUtils.copyProperties(message, messageDTO);
				return messageDTO;
			}
		}).collect(Collectors.toList());

		return result;
	}

	@Override
	public void deleteById(Long id) {
		Optional<Message> messageOpt = messageRepository.findById(id);
		if(messageOpt.isPresent()) {
			Message message = messageOpt.get();
			message.setDeleted(true);
			messageRepository.save(message);
		}		
	}	
}
