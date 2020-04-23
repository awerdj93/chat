package backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import backend.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>, QueryByExampleExecutor<Message> {
	Iterable<Message> findAllByCreatedBy(Long userId);
}
