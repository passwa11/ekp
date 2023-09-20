//重写resource里面的dialog.js对应的方法，用新的选择框
Dialog_SimpleCategory = function (modelName,idField,nameField,mulSelect,
		splitStr,authType,action,notNull,exceptValue,winTitle,extProps){
	if(modelName==null || modelName=='')
		return; 
	var cfg = {
			modelName:modelName ,
			authType:authType,
			idField:idField,
			nameField:nameField,
			canClose:true,
			mulSelect: mulSelect,
			notNull : notNull ,
			action :action
	}
	seajs.use(['sys/ui/js/dialog'], 
			function(dialog){
				dialog.simpleCategory(cfg);
			}
	);
}
Dialog_Category = function (modelName, idField, nameField,mulSelect,authType,showType,
		areaId,action,notNull,exceptValue,winTitle) {
	if(modelName==null || modelName=="") {
		return false;
	}
	var cfg = {
			modelName:modelName ,
			idField:idField,
			nameField:nameField,
			canClose:true,
			mulSelect: mulSelect,
			notNull : notNull ,
			isShowTemp : showType,
			action : action
	}
	seajs.use(['sys/ui/js/dialog'], 
			function(dialog){
				dialog.category(cfg);
			}
	);
}
 Dialog_Template=function(modelName, urlParam,mulSelect,isReturn,authType,action,notNull,winTitle){

		if(modelName==null || modelName=='')
			return;
		if(isReturn) {
			var i = urlParam.indexOf("::");
			if(i>0) {
				var idField = urlParam.substring(0,i);			
				var nameField = urlParam.substring(i+2);
			}
		}
		if(authType==null) authType = "02";
		if(notNull==null) notNull = true;
		if(isReturn==null) isReturn = false;
		if(mulSelect==null) mulSelect = false;
		var cfg = {
				modelName:modelName ,
				authType:authType,
				idField:idField,
				nameField:nameField,
			//	winTitle:winTitle,
				canClose:true,
				mulSelect: mulSelect,
				notNull : notNull ,
				action :action,
				noFavorite:null
		}
		seajs.use(['sys/ui/js/dialog'], 
				function(dialog){
					dialog.category(cfg);
				}
		);
	}	