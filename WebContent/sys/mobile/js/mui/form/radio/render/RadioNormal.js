/**
* @ignore  =====================================================================================
* @desc    构建Radio单选按钮的【标准】样式单个选项
* @ignore  =====================================================================================
*/
define([ "dojo/_base/declare",
         "dojo/dom-construct", 
         "dijit/_WidgetBase",
         "dojo/dom-class",
         "dojo/dom-style",
         "mui/util"
	   ], function(declare, domConstruct, WidgetBase, domClass, domStyle, util) {
	
	   return declare("mui.form.radio.render.RadioNormal",[WidgetBase], {
		   
			// 呈现样式类型
			renderType: "normal",
			
			// 单个【标准】项单选按钮DOM
			radioNode: null,
		   
			
			/**
			* 单选按钮DOM构建完成之后，监听单选按钮选中状态渲染事件
			* （当单选按钮选中或取消选中时会触发该事件）
			*/
			postCreate : function(){
				this.inherited(arguments);
				this.subscribe("mui/form/radio/renderCheckedStatus" , "_renderCheckedStatus");
			},
		   
			
			/**
			* 构建【标准】单选按钮DOM
			* @param obj 单选按钮对象(mui/form/Radio)
			* @return
			*/
		    _buildRender: function(obj){
				// 163465双反斜线转义 此单转义放置在radiogroup.js中处理
				//obj.value = util.formatTextChar(obj.value);
		    	if(this.renderType == obj.renderType){
					this.radioNode = domConstruct.create('label', {
						className : 'muiRadioItem',
						    style : obj.style,
						innerHTML :"<span class='mui-radio-base'>"+ obj.text&&obj.text.indexOf("&#039;")>0 ? obj.text.replace(/</g, "&lt;").replace(/>/g, "&gt;"): util.formatText(obj.text)+"</span>"
					}, obj.domNode, 'after');
					
					this.radioIcon = domConstruct.create('span', {
						className : 'fontmuis muis-form-radio muiRadioCircle ' + obj.unCheckedIcon,
					}, this.radioNode, 'first');					
		    	}
				return this.radioNode;
		     },


			/**
			* 渲染单选按钮的选中状态
			* @param obj 单选按钮对象(mui/form/Radio)
			* @param data 单选按钮状态相关参数对象
			* @return
			*/
		    _renderCheckedStatus: function(obj,data){
		    	if(this.radioNode == data.domNode){
					if (data.checked) {
						domClass.replace(this.radioNode, data.checkedIcon, data.unCheckedIcon);
						domClass.replace(this.radioIcon, data.checkedIcon, data.unCheckedIcon);
					}else{
						domClass.replace(this.radioNode, data.unCheckedIcon, data.checkedIcon);
						domClass.replace(this.radioIcon, data.unCheckedIcon, data.checkedIcon);
					}
		    	}
		    }
		    
			
			
		   
	   });
});