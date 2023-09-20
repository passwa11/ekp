/**
 * 
 */
define([ "dojo/_base/declare", "dojo/_base/lang", "mui/form/RadioGroup", "dojo/request", "mui/util", "sys/xform/mobile/controls/xformUtil", "dojo/dom-construct",  "dojo/topic",  "dojo/query"],
		function(declare, lang, RadioGroup, request, util, xformUtil,domConstruct,topic,query) {
		var claz =  declare("sys.modeling.main.xform.controls.placeholder.mobile.Util", null, {
			
			CONST : {"RECORDID" : "fdId"},
			
			defalutNull : "",
			
			// 根据控件ID返回控件值
			findFormValueByCfg : function(cfg){
				var inputCfg = cfg.inputs;
				var formDatas = {};
				if(inputCfg.fields.length > 0){
					for(var i = 0;i < inputCfg.fields.length;i++){
						var field = inputCfg.fields[i];
						formDatas[field] = this.getFormValueById(field).join(";");
					}
				}
				return formDatas;
			},
			
			getFormValueById : function(controlId){
				//创建者特殊处理
				if(controlId === "docCreator"){
					var areaDom = document.forms[0];
					var domArr = query('[name=docCreatorId]', areaDom);
					var wgt = [];
					if(domArr && domArr.length > 0){
						wgt.push(domArr[0].value);
					}
					return wgt;
				}
				return xformUtil.getXformWidgetValues(null,controlId) || [];
			},
			
			setFormValueById : function(controlId, value){
				var wgt = xformUtil.getXformWidget(null, controlId);
				if(wgt){
				//动态下拉框控件的值是需要实时渲染的，有些需要上一个控件的值当下一个控件的入参，比如地区（省，市，区），直接赋值会赋值失败。
				if (wgt.id.indexOf("RelationSelect")!=-1){
					wgt.queryData(true,true);
				}else if (wgt.id.indexOf("RelationChoose")!=-1){//选择框是系统数据的直接赋值也会失败，
					domConstruct.empty(wgt.textNode);
					wgt.set("value", value);
					wgt.set("text", value);
					wgt.initDisplay();
					return;
				}
					wgt.set("value", value);
				}else{
					console.log("【业务关联控件】填充传出数据时，找不到控件ID为\""+ controlId +"\"的控件!");
				}
			},

			setXformWidgetValuesById:function(controlId, value) {
				var wgt = xformUtil.getXformWidget(null, controlId);
				if(wgt){
					this.setXformWidgetValues(wgt,value,controlId);
				}else{
					console.log("【业务关联控件】填充传出数据时，找不到控件ID为\""+ controlId +"\"的控件!");
				}
			},
			
			transValByExp : function(info, expression){
				var rs = "";
				// 把两个$里面括起来的内容替换为对应的值
				var controls = expression.match(/\$[^\$][\w.]*\$/g);
				if(controls) {
					for (var i = 0; i < controls.length; i++) {
						var control = controls[i].replace(/\$/g, '');
						//#134256 (control:fd_38fd8c477e4be4.fd_3949b1eb07ea7e)
						var controlsArr = control.split(".");
						controlObj = controlsArr[0];
						if (info.hasOwnProperty(controlObj)) {
							var val = info[controlObj]["value"];
							//#134256 关系设置返回值选择后返回的是ID，现明细表里面不能嵌套明细表，因此最多只有两层
							if (Array.prototype.isPrototypeOf(val)) {
								for(var j = 0;j < val.length;j++){
									if(val[j].hasOwnProperty(controlsArr[controlsArr.length - 1])){
										val = val[j][controlsArr[controlsArr.length - 1]];
										break;
									}
								}
							}
							// 地址本，默认取名字
							if (typeof val === "object" && val["name"]) {
								val = val["name"];
							}
							//枚举值，取text
							if (typeof val === "object" && val["text"]) {
								val = val["text"];
							}
							// RegExp 里面的表达式需要两个反斜杠
							expression = expression.replace(new RegExp("\\$" + control + "\\$", "g"), val);
						}
					}
					rs = expression;
				}
				return rs;
			},

			setXformWidgetValues : function(wgt,val,prop){
				if(wgt){
					var fieldId = prop;
					// 处理地址本下的id和name的情况
					if(/-fd(\w+)/g.test(fieldId)){
						var param ='';
						param = fieldId.match(/-fd(\w+)/g)[0].replace("-fd","").toLowerCase();
						fieldId = fieldId.match(/(\S+)-/g)[0].replace("-",".") + param;
					}
					if(wgt.declaredClass == "mui.form.Address"
						|| wgt.declaredClass == "sys.xform.mobile.controls.NewAddress"){
						if(val instanceof Array){
							wgt.set("curIds",val[0]);
							wgt.set("curNames",(val[1]!=null?val[1]:val[0]));
						}else if(fieldId){
							if(fieldId.indexOf('.id') > -1){
								wgt.set("curIds",val);
								// setcurIds的时候，没有构建视图，不想改category的代码，手动在这里调用
								wgt.buildValue(wgt.cateFieldShow);
							}else if(fieldId.indexOf('.name') > -1){
								wgt.set("curNames",val);
							}
						}
					}else if(wgt.declaredClass == "mui.form.RadioGroup"){
						// 单选
						var childrenArray = wgt.getChildren();
						val = val.substring(val.lastIndexOf(";")+1,val.length);
						for(var i = 0;i < childrenArray.length;i++){
							if (!val && childrenArray[i]._onClick){
								childrenArray[i].checked = false;
								childrenArray[i]._extendCheckAction(false);
								continue;
							}
							if(childrenArray[i].value == val && childrenArray[i]._onClick){
								childrenArray[i]._onClick();
							}
						}
					}else if(wgt.declaredClass == "mui.form.CheckBoxGroup"){
						var childrenArray = wgt.getChildren();
						// 多选
						var valArray = val.split(";");
						// 遍历子组件
						for(var i = 0;i < childrenArray.length;i++){
							if(childrenArray[i]._onClick){
								if(childrenArray[i].value && valArray.indexOf(childrenArray[i].value) > -1){
									// 此处设置相反，在_onClick的方法里面会取反
									childrenArray[i].checked = false;
								}else{
									childrenArray[i].checked = true;
								}
								childrenArray[i]._onClick();
							}
						}
					}else if(wgt.declaredClass == "mui.form.Select"){
						//下拉控件单选
						var _val = val;
						if (!wgt.mul){
							// 多个传入值
							var valArray = val.split(";");
							if (valArray.length > 1){
								_val = "";
							}
						}
						if(wgt.textName && prop.indexOf('_text') > -1 && wgt.textName.indexOf(prop) > -1){
							wgt.set('text',_val);
						}else{
							wgt.set("value",_val);
						}
					}else if(wgt.declaredClass == "sys.xform.mobile.controls.RelationRadio"){
						//单值类型控件，如果是多值拼接时，取最后一个
						val = val.substring(val.lastIndexOf(";")+1,val.length);
						if(wgt.textName && prop.indexOf('_text') > -1 && wgt.textName.indexOf(prop) > -1){
							wgt.set('text',val);
						}else{
							wgt._setValueAttr(val);
						}
					}else if (wgt.declaredClass == "sys.xform.mobile.controls.RelationSelect"){
						//单值类型控件，如果是多值拼接时，取最后一个
						var _val = val.substring(val.lastIndexOf(";")+1,val.length);
						if(wgt.textName && prop.indexOf('_text') > -1 && wgt.textName.indexOf(prop) > -1){
							wgt.set('text',_val);
						}else{
							//#167455 下拉框的值还没加载出来，导致赋值失败了
							wgt.queryData();
							wgt.set("value",_val);
							topic.publish("mui/form/select/callback", wgt);
						}
					}else if (wgt.declaredClass && wgt.declaredClass.indexOf("mui.datetime.") == 0){
						//单值类型控件，如果是多值拼接时，取最后一个
						var _val = val.substring(val.lastIndexOf(";")+1,val.length);
						_val = xformUtil.formatDateVal(wgt, _val);
						wgt.set("value",_val);
					}else if (wgt.declaredClass && wgt.declaredClass.indexOf("placeholder") >= 0){
						//#162298 兼容关联控件的数据回填
						if(wgt.textName && prop.indexOf('_text') > -1 && wgt.textName.indexOf(prop) > -1){
							wgt.set("text",val);
							wgt.buildTextItem(val);
						}else{
							wgt.hiddenValueNode.value = val;
							wgt.set("value",val);
						}
					}else if(wgt.textName && prop.indexOf('_text') > -1 && wgt.textName.indexOf(prop) > -1){
						wgt.set('text',val);
						if (wgt.declaredClass == "sys.xform.mobile.controls.RelationChoose") {
							wgt.buildTextItem({});
						}
					}else{
						//val = xformUtil.formatDateVal(wgt, val);
						wgt.set("value",val);
					}
				}
			},
		});
		
		return new claz();
})