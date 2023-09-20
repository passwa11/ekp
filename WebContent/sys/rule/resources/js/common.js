//获取真正的类型值，将规则类型转换成实际的类型，传给公式定义器
function getRealTypeVal(type){
	var rtnVal = type;
	//如果是组织机构类型，就需要进行匹配
	if(type && type.indexOf("ORG_TYPE_") != -1){
		if(type.indexOf("[]")!=-1){
			rtnVal = "com.landray.kmss.sys.organization.model.SysOrgElement[]";
//			if(type.indexOf("ORG_TYPE_PERSON") != -1 && type.indexOf("|") == -1){
//				rtnVal = "com.landray.kmss.sys.organization.model.SysOrgPerson[]";
//			}
		}else if(type=='ORG_TYPE_PERSON'){
			rtnVal = "com.landray.kmss.sys.organization.model.SysOrgPerson";
		}else{
			rtnVal = "com.landray.kmss.sys.organization.model.SysOrgElement";
		}
	}
	//如果是系统内数据和系统自定义数据，转换成字符串
	if(type == "sys" || type == "cust"){
		rtnVal = "String";
	}
	return rtnVal;
}
//model字段兼容数字类型
function getCompatibleType(fieldType){
	var type = fieldType.toLowerCase();
	if(type == "integer" || type == "int" || type == "float" || type == "number" || type == "long"){
		return "Double";
	}
	return fieldType;
}
//比较字段类型和参数类型
function compareTypes(fieldType, paramType){
	if(fieldType == paramType){
		return true;
	}
	if(paramType == 'com.landray.kmss.sys.organization.model.SysOrgPerson' && fieldType == 'com.landray.kmss.sys.organization.model.SysOrgElement'){
		return true;
	}
	return false;
}
//将实际的类型转换成规则的类型
function getRuleTypeVal(type){
	var rtnVal = type;
	//如果是组织机构类型，就需要进行匹配
	if(type && type.indexOf("SysOrgPerson") != -1){
		if(type.indexOf("[]") != -1){
			rtnVal = "ORG_TYPE_PERSON[]";
		}else{
			rtnVal = "ORG_TYPE_PERSON";
		}
	}else if(type && type.indexOf("SysOrgElement") != -1){
		//除了人员，其他组织架构类型都转换成组织架构类型
		if(type.indexOf("[]") != -1){
			rtnVal = "ORG_TYPE_ALL[]";
		}else{
			rtnVal = "ORG_TYPE_ALL";
		}
	}
	return rtnVal;
}
//矩阵组织的类型转换成规则集参数类型
function getRuleTypeFromMatrixType(type){
	var rtnVal = type;
	if(type){
		if(type.indexOf("person") != -1){
			rtnVal = "ORG_TYPE_PERSON";
		}else if(type.indexOf("org") != -1){
			rtnVal = "ORG_TYPE_ORG";
		}else if(type.indexOf("dept") != -1){
			rtnVal = "ORG_TYPE_DEPT";
		}else if(type.indexOf("post") != -1){
			rtnVal = "ORG_TYPE_POST";
		}else if(type.indexOf("group") != -1){
			rtnVal = "ORG_TYPE_GROUP";
		}else{
			//常量或者非组织架构类型暂时都已字符串表示，比如矩阵组织有constant和sys等
			rtnVal = "String";
		}
	}
	return rtnVal;
}
//获取多值格式的类型值
function getMutilTypeVal(type, isMulti){
	if(isMulti && isMulti == "1"){
		type += "[]";
	}
	return type;
}
//格式化orgType
function formateOrgType(orgType){
	if(orgType && orgType.indexOf("[]")!=-1){
		var ch = "[]";
		orgType = orgType.replace(new RegExp(ch,'g'),"");
	}
	return orgType;
}

function GetParentByTagName(tagName,obj){
	if(obj==null){
		if(Com_Parameter.IE)
			obj = event.srcElement;
		else
			obj = Com_GetEventObject().target;
	}
	for(; obj!=null; obj = obj.parentNode)
		if(obj.tagName == tagName)
			return obj;
}

//获取事件源对于的index
function getIndex(){
	try{
		var row = GetParentByTagName("TR");
		var table = GetParentByTagName("TABLE", row);
		var id = $(table).attr("id");
		//判断table是否为docList的表格，不是就返回默认的
		var isDocList = false;
		if(id){
			if(DocList_Info && DocList_Info.length > 0){
				for(var i=0; i<DocList_Info.length; i++){
					var tableId = DocList_Info[i];
					if(tableId === id){
						isDocList = true;
						break;
					}
				}
			}
		}
		if(isDocList){
			for(var i=1; i<table.rows.length; i++){
				if(table.rows[i]==row){
					return i;
				}
			}
		}else{
			//默认返回1
			return 1;
		}
	}catch(e){//非表格，默认返回1
		return 1;
	}
}