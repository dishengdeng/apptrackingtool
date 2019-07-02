package portal.service;

import java.util.List;

import portal.entity.Answer;

public interface AnswerService {
	public Answer SaveAnswer(Answer answer);
	public Answer UpdateAnswer(Answer answer);
	public void DeleteAnswer(Answer answer);
	public List<Answer> getAllAnswer();
}
