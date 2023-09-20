/**
 * Switch开关Base
 */
define(['dojo/_base/declare','dijit/_WidgetBase','dojo/topic','dojo/_base/lang','dojo/dom-construct','dojo/dom-style','dojo/dom-class',"mui/form/_AlignMixin"],
		function(declare, WidgetBase, topic, lang, domConstruct, domStyle, domClass, _AlignMixin){
	return declare('mui.form.switch._SwitchBase',[ WidgetBase, _AlignMixin ],{
		
		isForm: true,
		
		/* 开关基础标识样式class类名    */
		baseClass: '',
		

	    // 显示状态 (可选项： edit、view、readOnly、noshow )
	    showStatus: "edit",		
	    
	    
	    /* 对齐方式：    left:居左、right:居右、center:居中  */
	    align: 'left',
	    
	    
		/* 属性名称 
		 * 会作为表单hidden提交的名称，例：<input type="hidden" name="属性名称" />
		 */
		property:'',
		
		
		/* 开关的初始状态值：   on:开启     或     off:关闭     */
		value: "on",
		
		
		/* switch开关表单hidden的值：   true:开启     或     false:关闭     */
		realValue : true,
		
		
		/* 是否禁用  */
		disabled : false,
		
		
		/* 开关左侧文本(即开启状态时显示的文本) */
		leftLabel : 'ON',
		
		
		/* 开关右侧文本(即关闭状态时显示的文本) */
		rightLabel : 'OFF',
		
		
		buildRendering: function(){
			this.inherited(arguments);
			
            if(this.baseClass){
            	// 表单元素类名
            	domClass.add(this.domNode,'muiFormEleWrap');
            	
            	// 状态类名：编辑状态（muiFormStatusEdit）、查看状态（muiFormStatusView）、只读状态（muiFormStatusReadOnly）
            	domClass.add(this.domNode,'muiFormStatus'+this.showStatus.substring(0,1).toUpperCase()+this.showStatus.substring(1));
            	
            	// 对齐方式类名: 左对齐（muiFormLeft）、右对齐（muiFormRight）、居中对齐（muiFormCenter）
            	domClass.add(this.domNode,'muiForm'+this.align.substring(0,1).toUpperCase()+this.align.substring(1));
            	
            	domClass.add(this.domNode,this.baseClass);
            }
			
			// 创建switch开关DOM
			this.switchNode = this.containerNode = domConstruct.create('div', { className:'muiSwitch ' }, this.domNode);
			if(this.showStatus=='readOnly'){
				this.disabled=true;
			}
			if(this.disabled){
				domClass.add(this.switchNode,'muiSwitchDisabled');
			}
			
			// 初始化开关状态
			if(!this.realValue || this.realValue == 'false'){
				this.value = 'off';
			}			
			this._onSetSwitchState(this.value);
			
			// 创建圆形旋钮DOM
			var knobDom = domConstruct.create('div', {className:'muiSwitchKnob'}, this.switchNode);
			
			// 创建switch开关图形中的文本DOM
			if(this.leftLabel!=''){
				domConstruct.create('span', {className:'muiSwitchText muiSwitchLeftText',innerHTML:this.leftLabel}, this.switchNode);
			}
			if(this.rightLabel!=''){
				domConstruct.create('span', {className:'muiSwitchText muiSwitchRightText',innerHTML:this.rightLabel}, this.switchNode);
			}
			
			// 创建属性hidden
			this.propDom = domConstruct.create('input',{
				type : 'hidden',
				name : this.property,
				value :  this.value  ?  (this.value == 'on' ? true : false) : this.realValue
			},this.domNode);
		
		},
		
		
		postCreate: function(){
			this.inherited(arguments);
			
			// 绑定开关点击事件
			if( this.showStatus=='edit' && this.disabled==false ){
				this.connect(this.switchNode, 'click', lang.hitch(this, function() {
	                this._onSwitchClick();
				}));
			}
			
		},
		

		/**
		* 添加“开”或“关”状态样式class类名，并设置状态变量
		* @param value 开关状态值：on 或 off
		*/		
		_onSetSwitchState: function(value){
			this.state = value;
			if(value=='on'){
				domClass.remove(this.switchNode,"muiSwitchOff");
				domClass.add(this.switchNode,'muiSwitchOn');
			}else{
				domClass.remove(this.switchNode,"muiSwitchOn");
				domClass.add(this.switchNode,'muiSwitchOff');
			}			
		},
		
		
		/**
		* (此函数是为兼容历史代码，有部分模块通过扩展Mixin重写了此方法)
		* 开关状态已改变，发布开关状态变更事件，供外部监听处理其它业务
		* @param newState 新的状态值（on 或 off） 
		*/
		onStateChanged: function(newState){
			var hiddenValue = newState == 'on' ? true : false;
			this.propDom.value = hiddenValue;
			topic.publish('mui/switch/statChanged', this, hiddenValue);
		},
		
		
		/**
		* 开关点击处理函数，切换开关状态
		*/
		_onSwitchClick: function(){
			/* 连续点击不能超过500毫秒，防止快速双击
			（苹果IOS下，click事件未知原因会连续触发两次，下面的的 nowTime、clickTime的条件判断只是为了防止一次点击多次处理，是一种变通的方式来防止双击）*/
			var nowTime = new Date().getTime();
		    var clickTime = this.ctime;
		    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
		        return false;
		    } else {
		    	
		    	// 切换开关状态
		    	this.set("value",(this.state=='on') ? 'off' : 'on');
                
                return true;
		    }			
		},
		
		
		/**
		* 调用DOJO原生set方法来重置Switch开关的选中状态
		* 调用示例：this.set("value","on");
		*/
		_setValueAttr : function(value) {
			this.inherited(arguments);
			if(this.value != value){
				this.state = value;
		    	this.value = value;
		    	this._onSetSwitchState(this.value);
	            this.onStateChanged(this.state);
            }
		},
		
	});
	
});