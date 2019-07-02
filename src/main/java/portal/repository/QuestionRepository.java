package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import portal.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

}
