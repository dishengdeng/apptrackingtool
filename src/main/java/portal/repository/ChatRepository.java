package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import portal.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long>{

}
