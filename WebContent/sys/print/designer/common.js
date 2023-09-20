(function(window, undefined){
	/**
	 * 模块通用工具类
	 */
	var sysPrintCommon={};
	
	//获取文档数据字典 xformTemplateId(表单模板id,流程拷贝时必须传该属性参数)
	sysPrintCommon.getDocDict = function(fdKey,modelName,_xformCloneTemplateId){
		//文档数据字典
		var _cmd = "window._XForm_GetSysDictObj ? _XForm_GetSysDictObj('"+ modelName +"'):Formula_GetVarInfoByModelName('" + modelName + "')";

		var sysObj = eval(_cmd);
		_xformCloneTemplateId = _xformCloneTemplateId ? _xformCloneTemplateId:"";
		var xFormMethod = "window.XForm_getXFormDesignerObj_" + fdKey;
		var cmd = xFormMethod + "? " + xFormMethod + "('" + _xformCloneTemplateId + "'):Formula_GetVarInfoByModelName('" + modelName + "')";
		var baseObjs = eval(cmd);
		if(PRINT_OPER_TYPE=='templateHistory'){//历史模板编辑场景
			//获取表单某个模板对应的数据字典
			var fileNameValue = $('input[name="fdFormFileNames"]').val();
			var fileNames = fileNameValue.split(';');
			for(var i=0;i<fileNames.length;i++){
				if(!fileNames[i]){
					continue;
				}
				var tmpbaseObjs = new KMSSData().AddBeanData("sysFormDictVarTree&tempType=file&fileName="+fileNames[i]).GetHashMapArray();
				baseObjs = baseObjs.concat(tmpbaseObjs);
			}
			
		}
		//自定义属性
		var data = new Array();
		data = new KMSSData().AddBeanData("sysPrintPropertyDictService&categoryId=&modelName="+modelName).GetHashMapArray();
		baseObjs = baseObjs.concat(data);
		if(!baseObjs||baseObjs.length==0){
			return baseObjs;
		}
		//文档属性、自定义表单数据字典区分标识
		sysPrintCommon._setXFormDictFlag(sysObj,baseObjs);
		return baseObjs;
	}
		
	 /**
	  * sysDict 文档数据字典
	  * baseObjs 所有数据字典
	  * 是否自定义表单数据 添加标识
	  */
	sysPrintCommon._setXFormDictFlag = function(sysDict,baseObjs){
		for(var i=0;i<baseObjs.length;i++){
			var p = baseObjs[i];
			var isXFormDict = false;
			if(!sysPrintCommon._isContain(sysDict,p.name)){
				isXFormDict = true;
			}
			p.isXFormDict = isXFormDict;
		}
	}
	sysPrintCommon._isContain = function(arrValue,value){
		for(var j = 0;j < arrValue.length;j++){
			var isExits = false;
			if(arrValue[j].name==value){
				return true;
			}
		}
		return false;
	}
	
	window.sysPrintCommon=sysPrintCommon;//打印机制通用工具
	
})(window);