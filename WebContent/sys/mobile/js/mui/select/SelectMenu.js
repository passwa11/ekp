define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase","dojo/dom-attr","dojo/dom-class",
        "dojo/query","dojo/touch","dojo/topic","dojox/mobile/viewRegistry","dijit/registry",
        "dojo/dom-construct","mui/util","dojo/on","dojo/dom","dojo/_base/array","dojo/dom-style","dojo/ready"],function(declare,lang,WidgetBase,domAttr,domClass,query,touch,
        		topic,viewRegistry,registry,domConstruct,util,on,dom,array,domStyle,ready){
	
		return declare("mui.select.SelectMenu",[WidgetBase],{
			
			nextId:'', // 关联父组件
			
			data : [], //数据集合
			
			dataIndex : 0, //选中序号
			
			SELECT_CHANGE:'mui/select/changed',
			
			buildRendering:function(){
				this.inherited(arguments);
				this.buildDefaultRender();	
				this.buildSelectingRender();				
			},
			
			buildDefaultRender:function(){
				var self = this;
				//获取当前选中
				var selectedName = (function(){
					for(var i = 0; i < self.data.length;i++){
						var _data = data[i];
						if(_data.selected){
							self.dataIndex = i;
							return _data.text;
						}
					}
					var _data = data[0];
					return _data.text;
				})();
				var headNode = this.headNode = domConstruct.toDom('<div id="h" class="muiFontSizeM muiFontColorInfo">' + selectedName + '<span class="arrow"></span></div>');
				domConstruct.place(headNode,this.domNode);
				if (this.domNode.parentNode) {
					var h = this.domNode.parentNode.style.height;
					var styleVar =  {
						'height' : h,
						'line-height' : h,
						'text-align':'center',
						'width':'100%'
					};
					domStyle.set(this.domNode, styleVar);
				}
				
				this.connect(headNode, touch.press, "headClick");
			},
			
			headClick : function(){
				var bn = query(".muiSignInList")[0];
				if (!domStyle.get(bn,"display")||domStyle.get(bn,"display") == 'none') {
					domStyle.set(bn,'display','block');
				}else{
					domStyle.set(bn,'display','none');
				}
			},
			
			buildSelectingRender:function(){
				this.parentNode = domConstruct.create('div', {className : 'muiSignInList'},dom.byId(this.nextId));
				this.parentLiNode = domConstruct.create("li",{className:"emphasize muiSignInDropDown muiSignInOpen"},this.parentNode);
				this.selectingNode = domConstruct.create('ul', {className : 'muiSignInDropDownMenu'},this.parentLiNode);
				domStyle.set(this.parentNode,'display','none');
				for(var i = 0 ;i < this.data.length;i++){
					var selectedClass = '';
					var fdName = this.data[i].text;
					if(i == this.dataIndex){
						selectedClass = 'selected';
						this.headNode.innerHTML = util.formatText(fdName) + '<span class="arrow"></span>';
					}
					this.liNode = domConstruct.create('li', {id:this.data[i].url,className : selectedClass,innerHTML:fdName}, this.selectingNode);
					domAttr.set(this.liNode,'data-index',i);
					this.connect(this.liNode,touch.press,"itemClick");
				}
				this.hbgDiv = domConstruct.create('div', {id:"hbg",className : 'ekp-ledger-customer-option-mask'}, this.parentNode);
				this.connect(this.hbgDiv,touch.press,"headBackClick");
				dom.byId("content").insertBefore(this.parentNode, dom.byId(this.nextId).nextSibling);
			},
			
			headBackClick:function(event){
				this.defer(function(){
					var bn = query(".muiSignInList")[0];
					domStyle.set(bn,'display','none');
				},350);
			},
			
			itemClick : function(event){
				this.defer(function(){
					var bn = query(".muiSignInList")[0];
					if (!domStyle.get(bn,"display")||domStyle.get(bn,"display") == 'none') {
						domStyle.set(bn,'display','block');
					}else{
						domStyle.set(bn,'display','none');
					}
					
					query(".muiSignInDropDownMenu li").forEach(function(item){
						domClass.remove(item,"selected");
					});
					
					for(var i = 0 ;i < this.data.length;i++){
						var selectedIndex = domAttr.get(event.target,'data-index');
						if(i == selectedIndex){
							domClass.add(event.target,"selected");
							this.headNode.innerHTML = util.formatText(this.data[i].text) + '<span class="arrow"></span>';
							this.dataIndex = i;
						}
					}
					
					var dataIndex = this.dataIndex,
						params = this.data[dataIndex].params || {};
					topic.publish(this.SELECT_CHANGE,{
						args : params
					});
					
				},350);
			},
			
			startup:function(){
				if(this._started){ return; }
				this.inherited(arguments);
				var dataIndex = this.dataIndex,
					params = this.data[dataIndex].params || {};
				topic.publish(this.SELECT_CHANGE,{
					args : params
				});
			}
		});
});