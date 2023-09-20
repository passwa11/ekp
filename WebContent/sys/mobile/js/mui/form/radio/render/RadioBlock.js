/**
* @ignore  =====================================================================================
* @desc    构建Radio单选按钮的【块状】样式单个选项
* @ignore  =====================================================================================
*/
define([ "dojo/_base/declare",
         "dojo/dom-construct",
         "dijit/_WidgetBase",
         "dojo/dom-class",
         "dojo/dom-style",
         "mui/util"
	   ], function(declare, domConstruct, WidgetBase, domClass, domStyle, util) {
	
	   return declare("mui.form.radio.render.RadioBlock",[WidgetBase], {
		   
			// 呈现样式类型
			renderType: "block",
			
			// 单个【块状】项单选按钮DOM
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
			* 构建【块状】单选按钮DOM
			* @param obj 单选按钮对象(mui/form/Radio)
			* @return
			*/
			_buildRender: function(obj){
			   if(this.renderType == obj.renderType){
					this.radioNode = domConstruct.create('label', {
						className : 'muiRadioBlockItem',
					}, obj.domNode, 'after');
					
					this.radioText = domConstruct.create('span', {
						className : 'muiRadioBlockText',
						    style : obj.style,
						innerHTML : obj.text&&obj.text.indexOf("&#039;")>0 ? obj.text.replace(/</g, "&lt;").replace(/>/g, "&gt;") : util.formatText(obj.text)
					}, this.radioNode, 'last');
					
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
					} else{
						domClass.replace(this.radioNode, data.unCheckedIcon, data.checkedIcon);
					}
		    	}
		    }
		   
	   });
});