<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="JavaScript">
	Com_IncludeFile("sysForm_script.js",Com_Parameter.ContextPath+'sys/xform/include/','js',true);
	Com_IncludeFile("formula.js");
	function XForm_getXFormDesignerObj_${JsParam.fdKey}() {
		var obj = [];
		<%-- // 1 加载系统字典 --%>
		var sysObj = _XForm_GetSysDictObj("${_xformMainForm.modelClass.name}");
		var extObj = _XForm_GetExitFileDictObj("${_formFilePath}");
		return sysObj.concat(extObj);
	}
	
	function _XForm_GetExitFileDictObj(fileName) {
		return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=file&fileName="+fileName).GetHashMapArray();
	}
	
	function _XForm_GetSysDictObj(modelName){
		return Formula_GetVarInfoByModelName(modelName);
	}
	
	_xformMainModelClass="${_xformMainForm.modelClass.name}";
	_xformMainModelId="${_xformMainForm.fdId}";
</script>