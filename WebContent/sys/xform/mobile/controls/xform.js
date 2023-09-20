(function(win){
	// 缓存对象
	var XFormOnValueChangeFuns = [];
	var __xform_changeEventByIdFuns = {};
	var __xform_changeEventByLabelFuns = {};
	var __xform_labeObjCache = {};
	var __xform_idObjCache = {};

	win.__xformDispatch = function(value, widget) {
		var i = 0, funs;
		if (typeof XFormOnValueChange == 'function') {
			XFormOnValueChange(value, widget);
		}
		for (i = 0; i < XFormOnValueChangeFuns.length; i ++) {
			XFormOnValueChangeFuns[i](value, widget);
		}	
	
		var id = widget.get("name");
		var e = null;
		for(e in __xform_changeEventByIdFuns) {
			if (id.indexOf(e) > -1) {
				funs = __xform_changeEventByIdFuns[e];
				for (i = 0; i < funs.length; i ++) {
					funs[i](value, widget);
				}
			}
		}
		for(e in __xform_changeEventByLabelFuns) {
			if (widget.subject == e) {
				funs = __xform_changeEventByLabelFuns[e];
				for (i = 0; i < funs.length; i ++) {
					funs[i](value, widget);
				}
			}
		}
	};
	
	win.AttachXFormValueChangeEventById=function(id, fun) {
		var funs = __xform_changeEventByIdFuns[id];
		if (funs == null) {
			funs = [];
			__xform_changeEventByIdFuns[id] = funs;
		}
		funs.push(fun);
	};

	win.AttachXFormValueChangeEventByLabel=function(label, fun) {
		var funs = __xform_changeEventByLabelFuns[label];
		if (funs == null) {
			funs = [];
			__xform_changeEventByLabelFuns[label] = funs;
		}
		funs.push(fun);
	};
	win.GetXFormFieldById = function(id, nocache) {};
	
	win.GetXFormFieldByLabel = function(label, nocache) {};
	
	require(["sys/xform/mobile/controls/xformUtil"],function(xformUtil){
		win.GetXFormFieldById = function(id, nocache) {
			var obj = nocache == true ? null : __xform_idObjCache[id];
			if (obj != null && obj.length>0) return obj;
			obj = xformUtil.getXformWidgetsNoFilIndex(null,id);
			__xform_idObjCache[id] = obj;
			return obj;
		};
		
		win.GetXFormFieldByLabel = function(label, nocache) {
			var obj = nocache == true ? null : __xform_labeObjCache[label];
			if (obj != null && obj.length>0) return obj;
			obj = [];
			//TODO 待实现
			__xform_labeObjCache[label] = obj;
			return obj;
		};
	});
	
	win.GetXFormFieldValueById = function(id, nocache) {
		var rtn = [];
		var objs = win.GetXFormFieldById(id, nocache);
		for (var i = 0; i < objs.length; i ++) {
			var obj = objs[i];
			//兼容地址本和高级地址本
			if(obj.declaredClass == "mui.form.Address" || obj.declaredClass == "sys.xform.mobile.controls.NewAddress"){
				var value = obj.curNames;
				if(obj.getText)
					value = obj.getText();
				rtn.push(value);
			}else{
				rtn.push(objs[i].value);
			}
		}
		return rtn;
	};

	_SetXFormFieldValue=function(objs, value) {
		for(var i = 0 ; i < objs.length;i++){
			var obj = objs[i];
			if(obj.type=='radio' && obj.value==value){
				obj.set('value',true);
			}
			if(obj.type=='checkbox' && (value == obj.value && value.indexOf(obj.value) > -1)){
				obj.set('value',true);
			}
			if(obj.type=='input' || obj.type=='textarea' || obj.type=='select'){
				if (value instanceof Array) {
					value = value.length > 1 ? value[1] : value[0];
				}
				obj.set('value',value);
			}
			if(obj.type == 'date' || obj.type == "datetime" || obj.type == "time"){
				//这里用了trycatch是为了避免一些非日期数据强行填充到日期控件，遇到这种情况相当于不设置
				//这里依赖于mobile里边的form.js
				try{
					if (value instanceof Array) {
						value = value[0];
					}
					//时间格式转换
					if(value.match(RegExp(/-/ig))){
						//替换-（如：2021-10-31）
						value = value.replace(/-/ig,"/");
					}else if(value.match(RegExp(/[年月日]/g))){
						//替换年月日（如：2021年10月31日）
						value = value.replace(/[年月日]/g,"/");
					}else {
						//替换字符串（如：20211031 ）
						value = value.substring(0,4) + '/' +  value.substring(4,6) +'/' + value.substring(6,8);
					}

					//判断数据类型
					if(matchDate(value)){//日期格式
						//若是日期类型，需要拼接时间格式
						value = value+" 00:00:00";//这里是直接构建时间的格式，然后再通过Date对象
					}else if(matchTime(value)){
						//若是时间类型，需要拼接日期格式
						var curDate = new Date();
						var year = curDate.getFullYear();
						var month = curDate.getMonth() + 1;
						var day = curDate.getDate();
						value = year+"-"+month+"-"+day+" "+value;//这里是直接构建时间的格式，然后再通过Date对象
					}
					//根据维度转换格式
					var dimension = obj.dimension;
					//如果是年月日的形式,逻辑和之前一样不做处理
					if(dimension == "yearMonthDay"){
						dimension = "";
					}
					value = new Date(value);
					value = window.$form.str(value,obj.type,dimension);
					window.$form(obj.valueField).val(value);
				}catch(e){
				}
			}
			var className = obj.declaredClass;
			if (className=='mui.form.RadioGroup'){
				var childrenArray = obj.getChildren();
				for(var k = 0;k < childrenArray.length;k++){
					if(childrenArray[k].value == value && childrenArray[k]._onClick){
						childrenArray[k]._onClick();	
					}
				}
			}
			if(className =='mui.form.CheckBoxGroup'){
				var childrenArray = obj.getChildren();
				var valArray = (value +'').split(";");
				for(var k = 0;k < childrenArray.length;k++){
					if(childrenArray[k]._onClick){
						if(childrenArray[k].value && valArray.indexOf(childrenArray[k].value) > -1){
							// 此处设置相反，在_onClick的方法里面会取反
							childrenArray[k].checked = false;
						}else{
							childrenArray[k].checked = true;
						}
						childrenArray[k]._onClick();
					}
				}
			}
		}
	};
	
	matchDate=function(value){
		var reg = /^[1-9]\d{3}\/(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[0-1])$/;//年月日
		var reg1 = /^[1-9]\d{3}\/(0[1-9]|1[0-2])$/;//年月
		var reg2 = /^(0[1-9]|1[0-2])\/(0[1-9]|[1-2][0-9]|3[0-1])$/;//月日
		var regExp = new RegExp(reg);
		var regExp1 = new RegExp(reg1);
		var regExp2 = new RegExp(reg2);
		if(!regExp.test(value) && !regExp1.test(value) && !regExp2.test(value)){
		　　return false;
		}
		return true;
	};
	
	matchTime=function(value){
		var reg = /^(20|21|22|23|[0-1]\d):[0-5]\d$/;
		var reg1 = /^(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;
		var regExp = new RegExp(reg);
		var regExp1 = new RegExp(reg1);
		if(!regExp.test(value) && !regExp1.test(value)){
		　　return false;
		}
		return true;
	};
	
	win.SetXFormFieldValueById=function(id, value, nocache) {
		var objs = win.GetXFormFieldById(id, nocache);
		_SetXFormFieldValue(objs, value);
	};

	win.GetXFormFieldValueByLabel=function(label, nocache) {
		var rtn = [];
		var objs = win.GetXFormFieldByLabel(label, nocache);
		for (var i = 0; i < objs.length; i ++) {
			rtn.push(objs[i].value);
		}
		return rtn;
	};


	win.SetXFormFieldValueByLabel=function(label, value, nocache) {
		var objs = win.GetXFormFieldByLabel(label, nocache);
		_SetXFormFieldValue(objs, value);
	};
	
	win.GetXFormSameRowFieldById=function(dom, id) {
		var rtn = [], i, nm, name;
		var fs = win.GetXFormFieldById(id, true);
		if (dom.name != null && dom.name != '' && dom.name.indexOf('.') > -1) {
			name = dom.name;
			name = name.substring(0, name.lastIndexOf('.'));
			for (i = 0; i < fs.length; i ++) {
				nm = fs[i].name;
				nm = nm.substring(0, nm.lastIndexOf('.'));
				if (nm == name) {
					rtn.push(fs[i]);
				}
			}
		}
		return rtn;
	};
	
	win.GetDomOwnerDomTag = function(owner,obj,event){
	   if(typeof(owner)=="object" && owner.tagName){
		   owner= owner.tagName;
	   }
	   //调用系统公共获取事件对象的方法，兼容多浏览器
	   if(event==null)
		   event =Com_GetEventObject();
	   if(!obj){
		   obj = event.target ? event.target : event.srcElement;
	   }
	   if(typeof(obj)=="string")
	   {
		   obj = document.getElementById(obj);
	   }
	   if(obj.tagName.toLowerCase() == owner.toLowerCase()){
		   return obj;
	   } else{
	       return GetDomOwnerDomTag(owner,obj.parentNode,event);
	   }
	 };
	 
	//添加扩展的校验
	require(['sys/xform/mobile/validate/ValidationExtend',"dojo/topic"],function(ValidationExtend,topic){
		topic.subscribe("mui/form/validationInitFinish",function(data){//监听主校验器
			if(data._validation){
				var validation = data._validation;
				var XFormExtendValidates = ValidationExtend.XFormExtendValidates;
				for (var type in XFormExtendValidates) {//添加扩展的校验到主要校验器中
					validation.addValidator(type, XFormExtendValidates[type].error, XFormExtendValidates[type].test);
				}
			}
		});
	})

})(window);