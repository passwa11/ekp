/*定义运算符规则*/
var operators = {
	"String": [
	    {"name": "等于", "value": "eq", "formulaVal":'.equals("{value}")', "isFun": true},
	    {"name": "包含", "value": "contain", "formulaVal":'$字符串.包含$({field}, "{value}")', "isFun": true}
	],
	"String[]":[
		{"name": "包含", "value": "contain", "formulaVal":'$列表.包含$({field}, "{value}")', "isFun": true}
	],
	"com.landray.kmss.sys.organization.model.SysOrgPerson|com.landray.kmss.sys.organization.model.SysOrgElement": [
	    {"name": "等于", "value": "eq", "formulaVal":'.equals("{value}")', "isFun": true}
	],
	"com.landray.kmss.sys.organization.model.SysOrgPerson[]|com.landray.kmss.sys.organization.model.SysOrgElement[]": [
	    {"name": "包含", "value": "contain", "formulaVal":'List list = {field};for(int i=0; i<list.size(); i++){if(list.get(i).fdId .equals("{value}") ){return true;}}return false;', "isFun": true}
	],
	"Date|DateTime|Time": [
	    {"name": "等于", "value": "eq", "formulaVal":"=="},
	    {"name": "早于", "value": "lt", "formulaVal":"<"},
	    {"name": "晚于", "value": "gt", "formulaVal":">"},
	    {"name": "不早于", "value": "ge", "formulaVal":">="},
		{"name": "不晚于", "value": "le", "formulaVal":"<="}
	    //介于 bt
	],
	"Date[]|DateTime[]|Time[]":[
		{"name": "包含", "value": "contain", "formulaVal":'$列表.包含$({field}, new Date(Long.valueOf({value})))', "isFun": true}
	],
	"Number|Double|BigDecimal": [
	    {"name": "等于", "value": "eq", "formulaVal":"=="},
	    {"name": "小于", "value": "lt", "formulaVal":"<"},
	    {"name": "小于等于", "value": "le", "formulaVal":"<="},
	    {"name": "大于", "value": "gt", "formulaVal":">"},
	    {"name": "大于等于", "value": "ge", "formulaVal":">="}
	],
	"Number[]|Double[]|BigDecimal[]": [
		{"name": "包含", "value": "contain", "formulaVal":'$列表.包含$({field}, {value})', "isFun": true}
	],
	"default":[
		{"name": "等于", "value": "eq", "formulaVal":"=="}
	]
}
/*支持的字段类型*/
var supportTypes = ["String","String[]","com.landray.kmss.sys.organization.model.SysOrgPerson",
	"com.landray.kmss.sys.organization.model.SysOrgElement","com.landray.kmss.sys.organization.model.SysOrgPerson[]",
	"com.landray.kmss.sys.organization.model.SysOrgElement[]","Date","DateTime","Time","Date[]","DateTime[]","Time[]",
	"Number","Double","BigDecimal","Number[]","Double[]","BigDecimal[]"];

/*获取对应类型的运算符*/
function getOperatorsByType(type){
	for(var types in operators){
		var arr = types.split("|");
		for(var i=0; i<arr.length; i++){
			if(arr[i] == type){
				return operators[types];
			}
		}
	}
	//取不到时获取默认的
	return operators['default'];
}

/*表单数据字典*/
function GetFieldByTempId(tempId){
	var fdScopeFormModelNames = $("[name='fdScopeFormModelNames']").val();
	var modelName = fdScopeFormModelNames.split(";")[0];
	
	var extFields = new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
	var fields = new KMSSData().AddBeanData("sysFormulaDictVarTree&modelName="+modelName).GetHashMapArray();
	
	return filterFieldByType(extFields).concat(filterFieldByType(fields));
}

/*过滤不必要类型的字段*/
function filterFieldByType(fields){
	var newFields = [];
	for(var i=0; i<fields.length; i++){
		var field = fields[i];
		if(supportTypes.indexOf(field.type) != -1){
			newFields.push(field);
		}
	}
	return newFields;
}

/*获取模板id*/
window.modelId2TempId = {};
function getTempId(){
	var modelId = $("[name='fdScopeFormTemplateIds']").val();
	if(!modelId){
		//简单分类
		modelId = $("[name='fdScopeFormAuthorizeCateIds']").val();
	}
	modelId = modelId.substring(0,modelId.length-1);
	var tempId = modelId2TempId[modelId];
	if(!tempId){
		var elem = new KMSSData().AddBeanData("lbpmAuthorizeMainService&modelId="+modelId).GetHashMapArray()[0];
		if(elem){
			tempId = elem.tempId;
		}
	}
	modelId2TempId[modelId] = tempId;
	return tempId;
}
