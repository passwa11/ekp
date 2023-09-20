//配置页对话框
function dialogSelect(mul, key, idField, nameField, action){
	var rtfield=function(idField, nameField) {
		var idObj, nameObj;
		if (idField != null) {
			if (typeof (idField) == "string")
				idObj = document.getElementsByName(idField)[0];
			else
				idObj = idField;
		}
		if (nameField != null) {
			if (typeof (nameField) == "string")
				nameObj = document.getElementsByName(nameField)[0];
			else
				nameObj = nameField;
		}
		return {
			idObj : idObj,
			nameObj : nameObj
		}
	};
	var dialogCfg = editOption.dialogs[key];
	if(dialogCfg){
		var params={};
		if(dialogCfg.params){
			for(var i=0;i<dialogCfg.params.length;i++){
    			var argu = dialogCfg.params[i];
    			for(var field in argu){
    				var tmpFieldObj = document.getElementsByName(field);
    				if(tmpFieldObj.length>0){
    					params['c.' + argu[field] + '.'+field] = tmpFieldObj[0].value;
    				}
    			}
    		}
		}
		var tempUrl = 'sys/ui/js/category/common-template.jsp?dialogType=opener&modelName=' + dialogCfg.modelName + '&_key=dialog_' + idField;
		if(mul==true){
			tempUrl += '&mulSelect=true';
		}else{
			tempUrl += '&mulSelect=false';
		}
		var dialog = new KMSSDialog(mul,true);
		dialog.URL = Com_Parameter.ContextPath + tempUrl;
		var source = dialogCfg.sourceUrl;
		var propKey = '__dialog_' + idField + '_dataSource';
		dialog[propKey] = function(){
			var url = source;
			for(var key in params){
				if(params[key]!=null && params[key]!=''){
					url = Com_SetUrlParameter(url,key,params[key]);
				}
			}
			return url;
		};
		window[propKey] = dialog[propKey];
		propKey =  'dialog_' + idField;
		dialog[propKey] = function(rtnInfo){
			if(rtnInfo==null) return;
			var datas = rtnInfo.data;
			var rtnDatas=[],ids=[],names=[];
			for(var i=0;i<datas.length;i++){
				var rowData = domain.toJSON(datas[i]);
				rtnDatas.push(rowData);
				ids.push($.trim(rowData[rtnInfo.idField]));
				names.push($.trim(rowData[rtnInfo.nameField]));
			}
			var fields = rtfield(idField, nameField);
			if(fields.idObj) fields.idObj.value = ids.join(";");
			if(fields.nameObj) fields.nameObj.value = names.join(";");
			if(action){
				action(rtnDatas);
			}
		};
		domain.register(propKey,dialog[propKey]);
		dialog.Show(800,500);
	}
}
