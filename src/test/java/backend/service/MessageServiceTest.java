package backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import backend.dto.ChatDTO;
import backend.dto.MessageDTO;
import backend.model.Chat;
import backend.model.Message;
import backend.repository.ChatRepository;
import backend.repository.MessageRepository;

@SpringBootTest(classes = MessageServiceImpl.class)
class MessageServiceTest {

	@Autowired
	private MessageService messageService;

	@MockBean
	private ChatRepository chatRepository;
	
	@MockBean
	private MessageRepository messageRepository;
	
	@Test
	void createMessageTest() {
		MessageDTO msgDTO = new MessageDTO();
		msgDTO.setId(1L);
		msgDTO.setRecipient(2l);
		msgDTO.setMessage("Send by 1");
		msgDTO.setSequence(1L);
		msgDTO.setCreatedBy(3L);
		msgDTO.setUpdatedBy(3L);
		
		Chat chat = buildChat();
	
		when(chatRepository.findById(any(Long.class))).thenReturn(Optional.of(chat));
		
		messageService.create(msgDTO);
		
		verify(messageRepository, times(1)).findById(1l);
		verify(messageRepository, times(1)).save(any(Message.class));
	}
	
	@Test
	void updateMessageTest() {
		MessageDTO msgDTO = new MessageDTO();
		msgDTO.setId(1L);
		msgDTO.setRecipient(2l);
		msgDTO.setMessage("Send by 1 again");
		msgDTO.setSequence(1L);
		msgDTO.setCreatedBy(3L);
		msgDTO.setUpdatedBy(3L);
		
		Chat chat = buildChat();
		
		when(chatRepository.findById(any(Long.class))).thenReturn(Optional.of(chat));
		messageService.update(msgDTO);
		
		verify(messageRepository, times(1)).findById(1l);
		verify(messageRepository, times(1)).save(any(Message.class));
	}
	
	@Test
	void deleteMessageTest() {
		Message msg = new Message();
		msg.setId(1L);
		msg.setMessage("Send by 1");
		msg.setSequence(1L);
		msg.setCreatedBy(1L);
		msg.setUpdatedBy(2L);
		
		when(messageRepository.findById(any(Long.class))).thenReturn(Optional.of(msg));
		
		messageService.deleteById(1L);
		
		verify(messageRepository, times(1)).findById(1L);
		verify(messageRepository, times(1)).save(any(Message.class));
	}	
	
	@Test
	void findMessageByIdTest() {
		Message msg = new Message();
		msg.setId(1L);
		msg.setMessage("Send by 1");
		msg.setSequence(1L);
		msg.setCreatedBy(1L);
		msg.setUpdatedBy(2L);
		Chat chat = buildChat();
		msg.setChat(chat);
		when(messageRepository.findById(any(Long.class))).thenReturn(Optional.of(msg));
		
		MessageDTO dto = messageService.findMessageById(1L);
		
		verify(messageRepository, times(1)).findById(any(Long.class));
		assertEquals(dto.getSender(), msg.getCreatedBy());
		assertEquals(dto.getMsgDate(), msg.getCreatedAt());
		assertEquals(dto.getChatId(), chat.getId());
		assertEquals(dto.getRecipient(), msg.getChat().getRecipientId());
	}

	@Test
	void findAll() {
		Message msg1 = new Message();
		msg1.setId(1L);
		msg1.setMessage("Send by 1");
		msg1.setSequence(1L);
		msg1.setCreatedBy(1L);
		msg1.setUpdatedBy(2L);
		
		Message msg2 = new Message();
		msg2.setId(1L);
		msg2.setMessage("Send by 2");
		msg2.setSequence(2L);
		msg2.setCreatedBy(2L);
		msg2.setUpdatedBy(1L);
		
		List<Message> messages = new ArrayList<>();
		messages.add(msg1);
		messages.add(msg2);

		Iterable<Message> iter = messages;
		
		when(messageRepository.findAllByCreatedBy(1L)).thenReturn(iter);
		
		List<MessageDTO> dto = messageService.findAllByUserId(1L);
		
		verify(messageRepository, times(1)).findAllByCreatedBy(any(Long.class));
		assertEquals(dto.size(), 2);
		assertEquals(dto.get(0).getMessage(), msg1.getMessage());
		assertEquals(dto.get(0).getSequence(), msg1.getSequence());
		assertEquals(dto.get(0).getCreatedBy(), msg1.getCreatedBy());
		assertEquals(dto.get(0).getUpdatedBy(), msg1.getUpdatedBy());
		
		assertEquals(dto.get(1).getMessage(), msg2.getMessage());
		assertEquals(dto.get(1).getSequence(), msg2.getSequence());
		assertEquals(dto.get(1).getCreatedBy(), msg2.getCreatedBy());
		assertEquals(dto.get(1).getUpdatedBy(), msg2.getUpdatedBy());
	}
	
	private Chat buildChat() {
		Message msg1 = new Message();
		msg1.setId(1L);
		msg1.setMessage("Send by 1");
		msg1.setSequence(1L);
		msg1.setCreatedBy(1L);
		msg1.setUpdatedBy(2L);
		
		Message msg2 = new Message();
		msg2.setId(1L);
		msg2.setMessage("Send by 2");
		msg2.setSequence(2L);
		msg2.setCreatedBy(2L);
		msg2.setUpdatedBy(1L);
		
		Set<Message> messages = new HashSet<>();
		messages.add(msg1);
		messages.add(msg2);
				
		Chat chat = new Chat();
		chat.setId(101l);
		chat.setSender(1l);
		chat.setRecipientId(2l);
		chat.setRecipientName("TESTING");
		chat.setMessages(messages);
		return chat;
	}
}
