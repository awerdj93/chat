package backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dto.ChatDTO;
import backend.dto.MessageDTO;
import backend.exception.NotFoundException;
import backend.model.Chat;
import backend.model.Message;
import backend.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Override
	public long createChat(ChatDTO chatDTO) {
		List<Chat> list = chatRepository.findByRecipientId(chatDTO.getRecipientId());
		if (list != null && list.size() == 0) {
			return list.get(0).getId();
		}
		else {
			Chat chat = new Chat();
			BeanUtils.copyProperties(chatDTO, chat);
			chat = chatRepository.save(chat);
			return chat.getId();
		}
	}

	@Override
	public ChatDTO findChatByRecipient(long userId, long recipientId) {
		Optional<Chat> optional = chatRepository.findBySenderAndRecipientId(userId, recipientId);
		
		if (optional.isEmpty()) {
			throw new NotFoundException("Chat not found.");
		}
		
		Chat chat = optional.get();
		ChatDTO chatDTO = new ChatDTO();
		BeanUtils.copyProperties(chat, chatDTO);
		
		List<MessageDTO> messageList = new ArrayList<>();
		if (chat.getMessages()!=null) {
			for (Message message: chat.getMessages()) {
				MessageDTO messageDTO = new MessageDTO();
				BeanUtils.copyProperties(message, messageDTO);
				messageList.add(messageDTO);
			}
		}
		sortMessagesBySeq(messageList);
		populateMessages(chatDTO, messageList);
		
		
		return chatDTO;
	}
	
	@Override
	public List<ChatDTO> findAll(long userId) {
		Iterable<Chat> iterable = chatRepository.findAll();

		List<ChatDTO> result = StreamSupport.stream(iterable.spliterator(), false).map(new Function<Chat, ChatDTO>() {
			@Override
			public ChatDTO apply(Chat chat) {
				ChatDTO chatDTO = new ChatDTO();
				BeanUtils.copyProperties(chat, chatDTO);
				
				List<MessageDTO> messageList = new ArrayList<>();
				if (chat.getMessages()!=null) {
					for (Message message: chat.getMessages()) {
						MessageDTO messageDTO = new MessageDTO();
						BeanUtils.copyProperties(message, messageDTO);
						messageList.add(messageDTO);
					}
				}
				sortMessagesBySeq(messageList);
				populateMessages(chatDTO, messageList);
				
				return chatDTO;
			}
		}).collect(Collectors.toList());

		return result;
	}
	
	
	private List<MessageDTO> sortMessagesBySeq(List<MessageDTO> messageList) {
		 Collections.sort(messageList, new Comparator<MessageDTO>() {
			  public int compare(MessageDTO o1, MessageDTO o2) {
			      return o1.getSequence().compareTo(o2.getSequence());
			  }
			});
		 return messageList;
	}
	
	private void populateMessages(ChatDTO chatDTO, List<MessageDTO> messageList) {
		if (messageList.size() > 0) {
			chatDTO.setLastMessage(messageList.get(messageList.size() - 1));
		}
		chatDTO.setMessages(messageList);
	}
}
