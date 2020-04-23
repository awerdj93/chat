package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import backend.model.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long>, QueryByExampleExecutor<Chat> {
	List<Chat> findByRecipientId(long recipientId);
	
	Optional<Chat> findBySenderAndRecipientId(long sender, long recipientId);
}
