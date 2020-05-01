package backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import backend.model.Chat;
import backend.model.Message;
import backend.repository.ChatRepository;

@SpringBootTest(classes = ChatServiceImpl.class)
class ChatServiceTest {

	@Autowired
	private ChatService chatService;

	@MockBean
	private ChatRepository chatRepository;
	
	@Test
	void addChatTest() {
		ChatDTO chatDTO = new ChatDTO();
		chatDTO.setSender(1l);
		chatDTO.setRecipientId(2l);
		chatDTO.setRecipientName("TESTING");
		chatDTO.setMessages(new ArrayList<>());
		
		Chat chat = new Chat();
		chat.setId(102l);
		chat.setSender(1l);
		chat.setRecipientId(2l);
		chat.setRecipientName("TESTING");
		
		when(chatRepository.findBySenderAndRecipientId(any(Long.class), any(Long.class))).thenReturn(Optional.empty());
		when(chatRepository.save(any())).thenReturn(chat);
		long id = chatService.createChat(chatDTO);
		verify(chatRepository, times(1)).save(any(Chat.class));
		assertNotNull(id);
	}
	
	@Test
	void findChatByRecipientTest() {
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
		
		when(chatRepository.findBySenderAndRecipientId(any(Long.class), any(Long.class))).thenReturn(Optional.of(chat));
		
		ChatDTO dto = chatService.findChatByRecipient(1, 2);
		
		verify(chatRepository, times(1)).findBySenderAndRecipientId(any(Long.class), any(Long.class));
		assertEquals(dto.getSender(), chat.getSender());
		assertEquals(dto.getRecipientId(), chat.getRecipientId());
		assertEquals(dto.getRecipientName(), chat.getRecipientName());
		assertEquals(dto.getMessages().size(), 2);
		assertEquals(dto.getLastMessage().getMessage(), msg2.getMessage());
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
		
		Set<Message> messages = new HashSet<>();
		messages.add(msg1);
		messages.add(msg2);
				
		Chat chat1 = new Chat();
		chat1.setId(101l);
		chat1.setSender(1l);
		chat1.setRecipientId(2l);
		chat1.setRecipientName("TESTING");
		chat1.setMessages(messages);
		
		Chat chat2 = new Chat();
		chat2.setId(102l);
		chat2.setSender(1l);
		chat2.setRecipientId(2l);
		chat2.setRecipientName("TESTING");
		
		List<Chat> chats = new ArrayList<>();
		chats.add(chat1);
		chats.add(chat2);
		Iterable<Chat> chatIter = chats;
		
		when(chatRepository.findAll()).thenReturn(chatIter);
		
		List<ChatDTO> dto = chatService.findAll(1);
		
		verify(chatRepository, times(1)).findAll();
		assertEquals(dto.get(0).getSender(), chat1.getSender());
		assertEquals(dto.get(0).getRecipientId(), chat1.getRecipientId());
		assertEquals(dto.get(0).getRecipientName(), chat1.getRecipientName());
		assertEquals(dto.get(0).getMessages().size(), 2);
		assertEquals(dto.get(0).getLastMessage().getMessage(), msg2.getMessage());
		
		assertEquals(dto.get(1).getSender(), chat2.getSender());
		assertEquals(dto.get(1).getRecipientId(), chat2.getRecipientId());
		assertEquals(dto.get(1).getRecipientName(), chat2.getRecipientName());
		assertNull(dto.get(1).getLastMessage());
	}
	
	
}
