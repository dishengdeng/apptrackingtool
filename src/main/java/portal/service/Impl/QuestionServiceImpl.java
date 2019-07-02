package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Question;
import portal.repository.QuestionRepository;
import portal.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Question SaveQuestion(Question question) {
	
		return questionRepository.save(question);
	}

	@Override
	public void DeleteQuestion(Question question) {
		questionRepository.delete(question);
		
	}

	@Override
	public Question UpdateQuestion(Question question) {

		return questionRepository.saveAndFlush(question);
	}

	@Override
	public List<Question> getAllQuestion() {

		return questionRepository.findAll();
	}
	

}
