package portal.utility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import portal.models.ParamConditionModel;

public enum ParameterCondition {
	
	Contains("Contains"),
	DNC("Does not contain"),
	EQ("Equals"),
	DNEQ("Does not equal"),
	IE("Is Empty"),
	INE("Is Not Empty"),
	GE("Greater Than"),
	LE("Less Than"),
	GEEQ("Greater than Or Equals"),
	LEEQ("Less than Or Equals"),
	Between("Between");	

	private final String conditiontext;
	
	private ParameterCondition(String _conditiontext)
	{
		this.conditiontext=_conditiontext;
	}

	public String getConditiontext() {
		return conditiontext;
	}
	
	public static List<ParamConditionModel> getCategoryByType(ParameterType parameterType)
	{
		HashMap<ParameterType,List<ParamConditionModel>> conditions=new HashMap<ParameterType,List<ParamConditionModel>>();
		conditions.put(ParameterType.String, Arrays.asList(new ParamConditionModel(ParameterCondition.Contains.name(),ParameterCondition.Contains.getConditiontext()),
										new ParamConditionModel(ParameterCondition.DNC.name(),ParameterCondition.DNC.getConditiontext()),
										new ParamConditionModel(ParameterCondition.EQ.name(),ParameterCondition.EQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.DNEQ.name(),ParameterCondition.DNEQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.IE.name(),ParameterCondition.IE.getConditiontext()),
										new ParamConditionModel(ParameterCondition.INE.name(),ParameterCondition.INE.getConditiontext())));
		
		conditions.put(ParameterType.Number, Arrays.asList(new ParamConditionModel(ParameterCondition.Contains.name(),ParameterCondition.GE.getConditiontext()),
										new ParamConditionModel(ParameterCondition.LE.name(),ParameterCondition.LE.getConditiontext()),
										new ParamConditionModel(ParameterCondition.EQ.name(),ParameterCondition.EQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.DNEQ.name(),ParameterCondition.DNEQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.GEEQ.name(),ParameterCondition.GEEQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.LEEQ.name(),ParameterCondition.LEEQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.Between.name(),ParameterCondition.Between.getConditiontext())));		
		
		conditions.put(ParameterType.Date, Arrays.asList(new ParamConditionModel(ParameterCondition.GE.name(),ParameterCondition.GE.getConditiontext()),
										new ParamConditionModel(ParameterCondition.LE.name(),ParameterCondition.LE.getConditiontext()),
										new ParamConditionModel(ParameterCondition.EQ.name(),ParameterCondition.EQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.DNEQ.name(),ParameterCondition.DNEQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.GEEQ.name(),ParameterCondition.GEEQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.LEEQ.name(),ParameterCondition.LEEQ.getConditiontext()),
										new ParamConditionModel(ParameterCondition.Between.name(),ParameterCondition.Between.getConditiontext()),
										new ParamConditionModel(ParameterCondition.IE.name(),ParameterCondition.IE.getConditiontext()),
										new ParamConditionModel(ParameterCondition.INE.name(),ParameterCondition.INE.getConditiontext())));
		
		conditions.put(ParameterType.Boolean, Arrays.asList(new ParamConditionModel(ParameterCondition.EQ.name(),ParameterCondition.EQ.getConditiontext()),
												new ParamConditionModel(ParameterCondition.DNEQ.name(),ParameterCondition.DNEQ.getConditiontext())));
		return conditions.get(parameterType);
	}
	
	
}
