/**
 * Switch开关组件 (New)
 */
define([ 'dojo/_base/declare', './_SwitchBase' ,'dojo/dom-construct' ,'mui/util' ],function( declare, _SwitchBase, domConstruct, util ){
	
	return declare('mui.form.switch.NewSwitch',[ _SwitchBase ],{
		
		
		/* 覆盖SwitchBase开关基础标识样式class类名    */
		baseClass: 'muiNewSwitch muiFormItem',
		
		
	    /* 组件布局朝向：   horizontal:横向, vertical:纵向(默认),  none: 不做布局处理仅显示控件 */
	    orient: 'none',	
	    
	    
		/* 支持switch开关显示标题  */
		subject: '',
		
		
		/**
		 * 重写SwitchBase的postCreate，添加标题相关的DOM处理
		 */
		postCreate: function(){
			this.inherited(arguments);
			// 构建标题DOM
			this._buildSubjectRendering();
		},
		
		
		/**
		* 构建标题DOM
		*/			
		_buildSubjectRendering: function(){
			if(this.orient=='vertical' && this.subject){
				//非标准做法，为了兼容老版本，在switch外层再包个container
				this.containerNode = domConstruct.create('div',{
					'className' : 'muiSwitchSubject'
				},this.switchNode,'before');
				
				//标题节点
				this.tipNode = domConstruct.create("div",{'className':'muiFormEleTip'},this.containerNode);
				this.titleNode = domConstruct.create("span", {
					'className' : 'muiFormEleTitle',
					'innerHTML' : util.formatText(this.subject)
				},this.tipNode);				
			}
		}
		
		
	});
	
});
