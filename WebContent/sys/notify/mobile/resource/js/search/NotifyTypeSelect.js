define([ "dojo/_base/declare", "dojo/topic", "dojo/dom-class", "dojo/dom-style", "dojo/query", "dojo/dom-construct","dijit/_WidgetBase" ], 
	function(declare, topic, domClass, domStyle, query, domConstruct, WidgetBase) {
	
	return declare("notify.search.NotifyTypeSelect", [WidgetBase], {
		
		optionDatas: [],     // 可选项数据 [{value:'1',text:'选项1'}、{value:'2',text:'选项2'}...]
		
		name: '',     // 下拉框对应隐藏hidden的name  
		
		value: null,  // 默认值（对应optionDatas中可选项的value，设置value将默认选中对应的项）
		
		status: "collapse",  // 默认状态（expand：展开、  collapse：收缩）
		
		isInitSetValue: true, // 是否为初始化时设置下拉框的选中值  
		
		buildRendering: function(){
			this.inherited(arguments);
			
			// 下拉框外层容器DOM
		    this.selectContainerNode = domConstruct.create('div', { className:'muiNotifyTypeSelectContainer' }, this.domNode);
		    
		    //  选中项文本展示DOM
		    this.selectTextNode = domConstruct.create('div', { className:'muiNotifyTypeSelectText muiFontSizeM muiFontColorInfo' }, this.selectContainerNode);

		    // 下拉框展开、收缩状态图标DOM
		    var selectStatusIconContainer = domConstruct.create('div', { className:'muiNotifyTypeSelectStatusContainer' }, this.selectContainerNode);
		    this.iconNode = domConstruct.create('i', { className:'fontmuis' }, selectStatusIconContainer); 
		    
		    // option列表展示top基线
		    this.selectOptionsBaseLine = domConstruct.create('div', { className:'muiNotifyTypeSelectOptionsBaseLine' }, this.selectContainerNode);
		    
		    // 隐藏hidden
		    this.selectHiddenNode = domConstruct.create("input" ,{type:'hidden',name:this.name},this.selectContainerNode);
		    
		    // 下拉框选项弹出层
		    this.selectOptionsDialog = domConstruct.create('div', { className:'muiNotifyTypeSelectOptionsDialog'}, document.body);
		      
		    // 可选项option展示列表的容器DOM
		    this.optionContainer = domConstruct.create('div', { className:'muiNotifyTypeSelectOptionsContainer' }, this.selectOptionsDialog);
		    this._buildOptionElement(this.optionContainer,this.optionDatas);
		    
		    // 设置下拉框(展开或收缩)状态
		    this._setStatus(this.status);
		    
		    // 设置下拉框默认选中值
		    this.setValue(this._getDefaultValue());
		    this.isInitSetValue = false;
		    
		    // 绑定下拉框点击(展开或收缩)事件
		    this.connect(this.selectContainerNode, "click", '_selectClick');
            
		    //监听窗口大小改变
		    var _self = this;
		    window.addEventListener("resize",function(){
		    	if(_self.status=="expand"){
					var selectOptionsBaseLineTop = _self.selectOptionsBaseLine.getBoundingClientRect().top;
					domStyle.set(_self.selectOptionsDialog,"top",selectOptionsBaseLineTop+"px");
				    domStyle.set(_self.selectOptionsDialog,"height",(document.documentElement.clientHeight-selectOptionsBaseLineTop)+"px");
		    	}
		    });
		},
	
		
		/**
		* 设置下拉框组件的选中值
		* @return
		*/	
		setValue:function(value){
			var isTriggerChangeEvent = false;
			if(value){
				// 获取设置的值在下拉框列表中的索引位置
				var selectOptionIndex = -1;
				if(this.optionDatas.length>0){
					for(var i=0; i<this.optionDatas.length; i++){
						var optionData = this.optionDatas[i];
						if(optionData.value==value){
							selectOptionIndex = i;
							break;
						}
					}
				}
				if(selectOptionIndex != -1){
			    	if(!this.isInitSetValue && this.value && this.value!= value ){
			    		isTriggerChangeEvent = true;
			    	}
					this.value = value;
					this.selectHiddenNode.value = this.value;
					// 设置选中项的文本展示
					this.selectTextNode.innerText = this.optionDatas[selectOptionIndex].text;
					// 添加选中项在下拉框可选列表中的高亮样式
					var optionDomList = query(".muiNotifyTypeSelectOption",this.optionContainer);
			    	for(var k=0; k<optionDomList.length; k++){
			    		var itemDom = optionDomList[k];
			    		if(k==selectOptionIndex){
			    			domClass.remove(itemDom,"muiFontColorInfo");
			    			domClass.add(itemDom,"muiFontColor");
			    		}else{
			    			domClass.remove(itemDom,"muiFontColor");
			    			domClass.add(itemDom,"muiFontColorInfo");	    			
			    		}
			    	}
				}
			}
			if(isTriggerChangeEvent){
				topic.publish("/mui/notify/select/change",value);
			}
		},
	     
		
		/**
		* 获取默认选中项的值
		* @return
		*/		
	    _getDefaultValue: function(){
	    	var defaultValue = null;
	    	if(this.optionDatas.length>0){
	    		if(this.value){
	    			for(var i=0;i<this.optionDatas.length;i++){
	    				if(this.optionDatas[i].value==this.value){
	    					defaultValue = this.optionDatas[i].value;
	    				}
	    			}
	    		}else{
	    			defaultValue = this.optionDatas[0].value;
	    		}
	    	}
	    	return defaultValue;
	    },
	    
	    
		/**
		* 设置下拉框(展开或收缩)状态
		* @param status 状态
		* @return
		*/
	    _setStatus: function(status){
	    	this.status = status;
			if(this.status=="collapse"){ // 收起
				domClass.remove(this.iconNode,"muis-put-away");
				domClass.add(this.iconNode,"muis-spread");
				domStyle.set(this.selectOptionsDialog,"display","none");
				if(this.handle){
					this.disconnect(this.handle);
				}
			}else if(this.status=="expand"){ // 展开
				domClass.remove(this.iconNode,"muis-spread");
				domClass.add(this.iconNode,"muis-put-away");
				domStyle.set(this.selectOptionsDialog,"display","block");
				var selectOptionsBaseLineTop = this.selectOptionsBaseLine.getBoundingClientRect().top;
				domStyle.set(this.selectOptionsDialog,"top",selectOptionsBaseLineTop+"px");
			    domStyle.set(this.selectOptionsDialog,"height",(document.documentElement.clientHeight-selectOptionsBaseLineTop)+"px");
				this.handle = this.connect(document.body, 'touchend', 'unClick');
			}
	    },
	    
	    
		/**
		* 点击页面其他地方隐藏弹出层
		* @return
		*/
		unClick : function(evt) {
			var target = evt.target, isHide = true;
			while (target) {
				if (target == this.optionContainer || target == this.selectContainerNode ) {
					isHide = false;
					break;
				}
				target = target.parentNode;
			}
			if (isHide) 
				this.defer(function() {
					if(this.status=="expand"){
						this._setStatus("collapse");
					}
				}, 1);
		},
	
		
		/**
		* 点击选择框，展开或收起下拉列表
		* @return
		*/		    
		_selectClick: function(){
			var status = "";
			if(this.status=="collapse"){
				status="expand";
			}else if(this.status=="expand"){
				status="collapse";
			}
			this._setStatus(status);
		}, 
	    
		
		/**
		* 构建option可选项元素DOM
		* @param optionContainer 父级容器DOM
		* @param optionDatas 可选项列表数据
		* @return
		*/	    
	    _buildOptionElement: function(optionContainer, optionDatas){
			var _self = this;
	    	for(var i=0;i<optionDatas.length;i++){
	    		var optionData = optionDatas[i];
	    		var optionDom = domConstruct.create('div', { className:'muiNotifyTypeSelectOption muiFontSizeM'}, optionContainer);
	    		optionDom.innerText = optionData.text;
	    		// 绑定可选项的点击事件
	    		this.connect(optionDom, "click", (function(optionData){
  			          return function(evt){
							if(evt) {
								if (evt.stopPropagation) evt.stopPropagation();
								if (evt.cancelBubble) evt.cancelBubble = true;
								if (evt.preventDefault) evt.preventDefault();
								if (evt.returnValue) evt.returnValue = false;
							}
  			        	    _self.setValue(optionData.value); 
  			        	    _self._setStatus("collapse");
  			          }
	    		})(optionData));
	    	}
	    },
	    
	    

	
		
	});
});