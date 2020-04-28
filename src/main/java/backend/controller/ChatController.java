package backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.ChatDTO;
import backend.service.ChatService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@PostMapping("/users/{userId}/chats")
	@ResponseStatus(HttpStatus.CREATED)
	public long create(@RequestBody ChatDTO chatDTO) {
		return chatService.createChat(chatDTO);
	}
	
	@GetMapping("/users/{userId}/chats")
	@ResponseStatus(HttpStatus.OK)
	public List<ChatDTO> findAll(@PathVariable long userId) {
		System.out.println(userId);
		return chatService.findAll(userId);
	}
	
	@GetMapping("/users/{userId}/chats/recipient/{recipientId}")
	@ResponseStatus(HttpStatus.OK)
	public ChatDTO findChatByRecipient(@PathVariable long userId, @PathVariable long recipientId) {
		return chatService.findChatByRecipient(userId, recipientId);
	}
}
