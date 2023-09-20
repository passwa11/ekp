define([ "dojo/_base/declare", "sys/xform/mobile/controls/xformUtil", "dojo/json", "dojo/dom-construct", "mui/form/_FormBase","dojo/query","dojo/_base/array","dojo/dom-attr","mui/form/DateTime"], 
         function(declare,  xUtil, json, domConstruct, FormBase, query, array, domAttr, dateTime) {
	var claz = declare("sys.xform.mobile.controls.relationRule.RelationRuleAttrChange", null, {
		
		baseField : null,
		
		constructor:function(baseField){
			this.inherited(arguments);
			this.baseField = baseField;
			if(baseField.target && baseField.target.widget){
				this.widgetDomNode = baseField.target.widget.domNode;
			}
		},
		
		//是否被权限控件嵌套
		isContainedByRightControl : function(){
			var node = this.widgetDomNode;
			if(node){
				while(node != document.body){
					if(node.nodeName == "XFORMFLAG"){
						var flagtype = domAttr.get(node,"flagtype");
						if(flagtype && flagtype == "xform_right"){
							return true;
						}
					}
					node = node.parentNode;
				}
			}
			return false;
		},
		
		execRequired : function(flag){
			var baseField = this.baseField;
			//#162725移动端属性变更级联还没数据时就显示红星、PC端不显示。 此处relationRadio的选项个数如果为空，也就是用户看不到选项的时候，就不显示必填*。
			if (baseField.target.type === "relationRadio" && this.widgetDomNode && $(this.widgetDomNode).find("input[type='radio']").length ===0){
				flag ='0';
			}
			//flag ‘0’：非必填,'1':必填
			if(flag){
				if(flag == '0' || flag == false){
					baseField.required(false);
					if (baseField.target && baseField.target.widget) {
						baseField.target.widget.isRelationRule = true;
					}
				}else if(flag == '1' || flag == true){
					baseField.required(true);
				}
			}
		},
		
		execDisplay : function(flag){
			var baseField = this.baseField;
			//flag ‘0’：隐藏，‘1’：显示，‘21’：显示行，‘20’：隐藏行
			if(flag){
				if(flag == '0' || flag == false){
					baseField.display(false);
				}else if(flag == '1' || flag == true){
					baseField.display(true);
				}else if(flag == '21'){
					var node = this.widgetDomNode;
					if(node){
						// 查找父tr
						while(node.nodeName != "TR" && node != document.body){
							node = node.parentNode;
						}
						node.style.display = "table-row";
					}
					
				}else if(flag == '20'){
					var node = this.widgetDomNode;
					if(node){
						while(node.nodeName != "TR" && node != document.body){
							node = node.parentNode;
						}
						node.style.display = "none";
					}
				}
			}
		},
		
		execReadonly : function(flag){
			var baseField = this.baseField;
			//flag ‘0’：可编辑，‘1’：只读
			if(flag){
				if(flag == '0' || flag == false){
					baseField.readOnly(false);
				}else if(flag == '1' || flag == true){
					baseField.readOnly(true);
				}
			}
		}
		

	});
	return claz;
});
