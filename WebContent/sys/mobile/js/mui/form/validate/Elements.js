define([ "dojo/_base/declare", "dojo/_base/lang", "dojo/dom-construct", 
         "dojo/dom-style", "dojo/dom-attr", "dojo/dom-geometry", "dojo/query", "mui/form/validate/Reminder"], 
		function(declare, lang, domConstruct, domStyle,  domAttr, domGeometry, query, Reminder) {
	var Elements = declare("mui.form.validate.Elements", null, {
		
		// 给元素添加检验方法
		addElementValiate : function(element,validateName){
			var validate = element.getAttribute('validate');
			if(validate != null && $.trim(validate) != ''){
				var validates = validate.split(" ");
				if(validateName){
					var idx = $.inArray(validateName.toLowerCase(),validates);
					if(idx == -1){
						validates.push(validateName);
						element.setAttribute('validate',validates.join(' '));
					}
				}
			}else{
				 if(validateName)
					 element.setAttribute('validate',validateName);
			}
		},
		
		// 移除元素的校验方法
		removeElementValiate : function(element ,validateName){
			var validate = element.getAttribute('validate');
			if(validate != null && $.trim(validate) != ''){
				if(validateName == null){
					element.removeAttribute('validate');
				}else{
					var validates = validate.split(" ");
					var idx = $.inArray(validateName.toLowerCase(),validates);
					if(idx > -1){
						validates.splice(idx, 1);
						var tmpVal = validates.join(' ');
						if($.trim(tmpVal) == ''){
							element.removeAttribute('validate');
						}else{
							element.setAttribute('validate',tmpVal);
						}
					}
				}
				/*if(!this.hasAttribute(element,'_validate')&&!notReset)
					element.setAttribute('_validate',validate);*/
				//没有校验器时移除相关已经存在的提示信息；
				validate = element.getAttribute('validate');
				if(validate == null || $.trim(validate) == ''){
					var _reminder = new Reminder(element);
					_reminder.hide();
				}
			}
		}
		
	});
	return Elements;
});