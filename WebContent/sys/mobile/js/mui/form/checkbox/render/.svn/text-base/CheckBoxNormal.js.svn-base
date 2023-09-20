/**
* @ignore  =====================================================================================
* @desc    构建CheckBox多选框的【标准】样式单个选项
* @ignore  =====================================================================================
*/
define([ "dojo/_base/declare",
         "dojo/dom-construct", 
         "dijit/_WidgetBase",
         "dojo/dom-class",
         "dojo/dom-style",
         "mui/util"
	   ], function(declare, domConstruct, WidgetBase, domClass, domStyle, util) {
	
	   return declare("mui.form.checkbox.render.CheckBoxNormal",[WidgetBase], {
		   
			// 呈现样式类型
			renderType: "normal",
			
			// 单个【标准】项复选框DOM
			checkboxNode: null,
		   
			
			/**
			* 复选框DOM构建完成之后，监听复选框选中状态渲染事件
			* （当复选框选中或取消选中时会触发该事件）
			*/
			postCreate : function(){
				this.inherited(arguments);
				this.subscribe("mui/form/checkbox/renderCheckedStatus" , "_renderCheckedStatus");
			},
		   
			
			/**
			* 构建【标准】复选框DOM
			* @param obj 复选框对象(mui/form/CheckBox)
			* @return
			*/
		    _buildRender: function(obj){
				// 163465双反斜线转义 此单转义放置在checkboxgroup.js中处理
				//obj.value = util.formatTextChar(obj.value);
		    	if(this.renderType == obj.renderType){
					this.checkboxNode = domConstruct.create('label', {
						className : 'muiCheckItem'
					}, obj.domNode, 'after');
                    
					if(obj.text){
						domClass.add(this.checkboxNode,"hasText");
						var textNode = domConstruct.create('div', {
							className : 'mui-checkbox-base',
							    style : obj.style,
							innerHTML : obj.text&&obj.text.indexOf("&#039;")>0 ? obj.text.replace(/</g, "&lt;").replace(/>/g, "&gt;") : util.formatText(obj.text)
						}, this.checkboxNode);
					}
					
					this.checkboxIcon = domConstruct.create('span', {
						className : 'fontmuis muis-form-selected-cor muiCheckBlock '+ obj.unCheckedIcon
					}, this.checkboxNode, 'first');
		    	}
				return this.checkboxNode;
		     },
		    
		    
			/**
			* 渲染复选框的选中状态
			* @param obj 复选框对象(mui/form/CheckBox)
			* @param data 复选框状态相关参数对象
			* @return
			*/
		    _renderCheckedStatus: function(obj,data){
		    	if(this.checkboxNode == data.domNode){
					if (data.checked) {
						domClass.replace(this.checkboxNode, data.checkedIcon, data.unCheckedIcon);
						domClass.replace(this.checkboxIcon, data.checkedIcon, data.unCheckedIcon);
					} else{
						domClass.replace(this.checkboxNode, data.unCheckedIcon, data.checkedIcon);
						domClass.replace(this.checkboxIcon, data.unCheckedIcon, data.checkedIcon);
					}
		    	}
		    }
		    
			
			
		   
	   });
});