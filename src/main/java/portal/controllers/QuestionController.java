package portal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import portal.entity.Answer;

import portal.entity.Question;

import portal.service.AnswerService;
import portal.service.DepartmentService;
import portal.service.QuestionService;

@Controller
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private DepartmentService departmentService;
	
    @GetMapping("/questions")
    public String departmenttable(ModelMap model) {
    	model.addAttribute("questions", questionService.getAllQuestion());

        return "questions";
    }
    
    @PostMapping("/addQuestion")
    public String addZac(@ModelAttribute("question") Question question) {

    	questionService.SaveQuestion(question);
        return "redirect:/questions";
    }
    
    @PostMapping("/updateQuestion")
    public String updateQuestion(@ModelAttribute("questionModel") Question question) {

    	questionService.UpdateQuestion(question);
    	return "redirect:/questiondetail?question="+question.getId();
    }
    
    @GetMapping("/addQuestion")
    public String createZac(ModelMap model) {
    	model.addAttribute("question", new Question());
        return "addQuestion";
    }
    
    @GetMapping("/deleteQuestion")
    public String deleteZac(@ModelAttribute("question") Question question) {
    	


    	question.removeAllDepandence();


    	questionService.DeleteQuestion(question);
    	return "redirect:/questions";
    }
    
    @GetMapping("/questiondetail")
    public String zacdetail(@ModelAttribute("question") Question question,ModelMap model) {
    	model.addAttribute("question",question);
    	model.addAttribute("questionModel",new Question());
    	model.addAttribute("departments",departmentService.getAll());
 	
        return "questiondetail";
    }

    //------department---------------    
    @GetMapping("/deleteQuestionDepartment")
    public String deleteQuestionDepartment(@ModelAttribute("answer") Answer answer,@ModelAttribute("question") Question question) {


    	
    	answer.removeAllDependence();
    	answerService.DeleteAnswer(answer);
    	
    	
    	return "redirect:/questiondetail?question="+question.getId();
    }
    
    @PostMapping("/addQeustionDepartment")
    public String addQeustionDepartment(@ModelAttribute("answer") Answer answer) {


    	
    	answerService.UpdateAnswer(answer);
    	
    	
    	return "redirect:/questiondetail?question="+answer.getQuestion().getId();

    }  
    

}
