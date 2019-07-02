package portal.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Answer;
import portal.repository.AnswerRepository;
import portal.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService{
	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public Answer SaveAnswer(Answer answer) {

		return answerRepository.save(answer);
	}

	@Override
	public Answer UpdateAnswer(Answer answer) {

		return answerRepository.saveAndFlush(answer);
	}

	@Override
	public void DeleteAnswer(Answer answer) {
		answerRepository.delete(answer);
		
	}

	@Override
	public List<Answer> getAllAnswer() {

		return answerRepository.findAll();
	}
	
}
