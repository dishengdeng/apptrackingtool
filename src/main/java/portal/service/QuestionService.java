package portal.service;

import java.util.List;

import portal.entity.Question;

public interface QuestionService {
	
	public Question SaveQuestion(Question question);
	public void DeleteQuestion(Question question);
	public Question UpdateQuestion(Question question);
	public List<Question> getAllQuestion(); 

}
