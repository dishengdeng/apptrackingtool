package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import portal.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long>{

}
