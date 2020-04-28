package backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import backend.dto.ChatDTO;
import backend.model.Chat;
import backend.repository.ChatRepository;
import backend.repository.MessageRepository;

@SpringBootTest(classes = ChatServiceImpl.class)
class ChatServiceTest {

	@Autowired
	private ChatService chatService;
	
	@MockBean
	private MessageRepository messageRepository;

	@MockBean
	private ChatRepository chatRepository;
	
	@Test
	void addChatTest() {
		ChatDTO chatDTO = new ChatDTO();
		chatDTO.setId(1l);
		chatDTO.setSender(1l);
		chatDTO.setRecipientId(1l);
		chatDTO.setRecipientName("laal");
		chatDTO.setMessages(new ArrayList<>());
		
		chatService.createChat(chatDTO);
		
		verify(chatRepository, times(1)).findByRecipientId(1l);
		verify(chatRepository, times(1)).save(any(Chat.class));
		
	}
	
//	@Test
//	void removeProductTest() {
//		Product product = new Product();
//		product.setId(1l);
//		product.setUserId(1l);
//		product.setCarts(new HashSet<>());
//		
//		Cart cart = new Cart();
//		cart.setId(1l);
//		cart.setProducts(new HashSet<>());
//		
//		when(cartRepository.findOne(any())).thenReturn(Optional.of(cart));
//		when(productRepository.findById(1l)).thenReturn(Optional.of(product));
//		
//		cartService.removeProduct(1, 1);
//		
//		verify(productRepository, times(1)).findById(1l);
//		verify(cartRepository, times(1)).findOne(any());
//		verify(cartRepository, times(1)).save(any(Cart.class));
//	}
//	
//	@Test
//	void getUserCartTest() {
//		Set<Product> productList = new HashSet<>();
//		
//		Product product = new Product();
//		product.setId(1l);
//		product.setUserId(1l);
//		product.setPrice(new BigDecimal(100));
//		
//		Product product2 = new Product();
//		product2.setId(2l);
//		product2.setUserId(1l);
//		product2.setPrice(new BigDecimal(105));
//		
//		productList.add(product);
//		productList.add(product2);
//		
//		product.setCarts(new HashSet<>());
//		product2.setCarts(new HashSet<>());
//		
//		Cart cart = new Cart();
//		cart.setId(1l);
//		cart.setProducts(productList);
//		
//		when(cartRepository.findOne(any())).thenReturn(Optional.of(cart));
//		
//		CartResponseDTO dto = cartService.getUserCart(1);
//		
//		verify(cartRepository, times(1)).findOne(any());
//		
//		assertEquals(dto.getTotalPrice(), product.getPrice().add(product2.getPrice()));
//	}
//	
//	@Test
//	void cartCloseForOrderTest() {
//		Set<Product> productList = new HashSet<>();
//		
//		Product product = new Product();
//		product.setId(1l);
//		product.setUserId(1l);
//		product.setIsDeleted(false);
//		
//		Product product2 = new Product();
//		product2.setId(2l);
//		product2.setUserId(1l);
//		product.setIsDeleted(false);
//		
//		productList.add(product);
//		productList.add(product2);
//		
//		product.setCarts(new HashSet<>());
//		product2.setCarts(new HashSet<>());
//		
//		Cart cart = new Cart();
//		cart.setId(1l);
//		cart.setUserId(1);
//		cart.setProducts(productList);
//		
//		when(cartRepository.findOne(any())).thenReturn(Optional.of(cart));
//		
//		cartService.cartClosedForOrder(1);
//		
//		verify(cartRepository, times(1)).findOne(any());
//		verify(cartRepository, times(1)).save(any(Cart.class));
//		
//		assertTrue(cart.getIsDeleted());
//	}

}
