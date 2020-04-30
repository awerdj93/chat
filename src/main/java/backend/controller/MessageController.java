package backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.MessageDTO;
import backend.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	private MessageService messageService;

	@GetMapping("/users/{userId}/messages")
	@ResponseStatus(HttpStatus.OK)
	public List<MessageDTO> findAllByUserId(@PathVariable Long userId) {
		return messageService.findAllByUserId(userId);
	}
	
	@GetMapping("/users/{userId}/messages/{messageId}")
	@ResponseStatus(HttpStatus.OK)
	public MessageDTO findMessageByUserIdAndId(@PathVariable Long userId, @PathVariable Long messageId) {
		return messageService.findMessageById(messageId);
	}
	
	@PostMapping("/users/{userId}/messages")
	@ResponseStatus(HttpStatus.CREATED)
	public Long create(@PathVariable Long userId, @RequestBody MessageDTO messageDTO) {
		messageDTO.setCreatedBy(userId);
		messageDTO.setUpdatedBy(userId);
		return messageService.create(messageDTO);
	}

	@PutMapping("/users/{userId}/messages/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable Long userId, @RequestBody MessageDTO messageDTO) {
		messageDTO.setUpdatedBy(userId);
		messageService.update(messageDTO);
	}

	@DeleteMapping(value = "/users/{userId}/messages/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Long id) {
		messageService.deleteById(id);
	}
}
