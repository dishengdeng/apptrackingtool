package portal.service.Impl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.entity.Parameter;
import portal.models.ParamConditionModel;
import portal.repository.ParameterRepository;
import portal.service.ParameterService;
import portal.utility.ParameterCondition;
import portal.utility.ParameterType;

@Service
public class ParameterServiceImpl implements ParameterService{
	
	@Autowired
	private ParameterRepository parameterRepository;

	@Override
	public Parameter save(Parameter parameter) {

		return parameterRepository.save(parameter);
	}

	@Override
	public Parameter update(Parameter parameter) {

		return parameterRepository.saveAndFlush(parameter);
	}

	@Override
	public void delete(Parameter parameter) {
		parameterRepository.delete(parameter);
		
	}

	@Override
	public List<Parameter> getParameters() {
	
		return parameterRepository.findAll();
	}

	@Override
	public List<ParamConditionModel> getConditionsByType(ParameterType parameterType) {

		
		
		return ParameterCondition.getCategoryByType(parameterType);
	}

}
