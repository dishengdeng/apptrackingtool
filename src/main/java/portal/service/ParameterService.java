package portal.service;

import java.util.List;

import portal.entity.Parameter;
import portal.models.ParamConditionModel;
import portal.utility.ParameterType;

public interface ParameterService {
	public Parameter save(Parameter parameter);
	public Parameter update(Parameter parameter);
	public void delete(Parameter parameter);
	public List<Parameter> getParameters();
	public List<ParamConditionModel> getConditionsByType(ParameterType parameterType);
	
}
