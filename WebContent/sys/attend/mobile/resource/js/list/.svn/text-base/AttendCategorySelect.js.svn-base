define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase","dojo/dom-attr","dojo/dom-class",
        "dojo/query","dojo/touch","dojo/topic","dojox/mobile/viewRegistry","dijit/registry",
        "dojo/dom-construct","mui/util","dojo/on","mui/i18n/i18n!sys-attend","mui/dialog/Dialog"],function(declare,lang,WidgetBase,domAttr,domClass,query,touch,
        		topic,viewRegistry,registry,domConstruct,util,on,Msg,Dialog){
	
		return declare("sys.attend.AttendCategorySelect",[WidgetBase],{
			currentCategoryId:'',
			currentCategoryName : '',
			store:[],
			
			buildRendering:function(){
				this.inherited(arguments);
				//debugger;
				this.currentNode = domConstruct.create('div', {className:"muiEkpSubClockInTitleText"}, this.domNode);
				this.valueNode = domConstruct.toDom('<input type="hidden" id="_curCategoryId" name="curCategoryId"/>');
				domConstruct.place(this.valueNode, this.currentNode);
				this.valueShowNode = domConstruct.create("em",{className:"muiFontSizeMS muiFontColor",innerHTML:''},this.currentNode);
				this.iconNode = domConstruct.toDom('<i class="fontmuis muis-spread muiFontColor"></i>');
				domConstruct.place(this.iconNode, this.currentNode);
				this.buildSelectingRender();
			},
			
			buildSelectingRender:function(){
				if(this.store.length==0){
					this.valueShowNode.innerHTML=Msg['mui.no.category'];
					domConstruct.empty(this.iconNode);
					return;
				}
				if(this.store.length >1){
					this.connect(this.currentNode,"touchend",this.onCategorySelect);
				}else{
					domConstruct.empty(this.iconNode);
				}
				this.selectingNodeP = domConstruct.create('div', {style : {'display':'none'}}, this.domNode);
				this.selectingNode = domConstruct.create('ul', {className : 'muiSignInDropDownMenu'}, this.selectingNodeP);
				for(var i = 0 ;i < this.store.length;i++){
					var selectedClass="muiFontSizeMS";
					var record = this.store[i];
					var fdId = record.fdId;
					var fdName = record.fdName+record.workTimes;
					if(!this.currentCategoryId){
						if(i==0){
							this.currentCategoryId = fdId;
						}
					}
					if(this.currentCategoryId==fdId){
						selectedClass = "selected muiFontColor muiFontSizeMS";
						this.currentCategoryName=fdName;
						this.setSelected();
					}
					this.liNode = domConstruct.create('li', {id:fdId,className : selectedClass,innerHTML:fdName}, this.selectingNode);
					on(this.liNode,"click",lang.hitch(this,this.onItemClick));
				}
			},
			onCategorySelect :function(){
				var self = this;
				this.SelectingDialog = new Dialog.claz({
					element: self.selectingNode,
					scrollable: false,
					parseable: false,
					position: "bottom",
					canClose: false
				});
				this.SelectingDialog.show();
			},
			hideSelectingShade:function(){
				this.SelectingDialog.hide();
			},
			onItemClick : function(event){
				var self = this;
				this.defer(function(){
					if(self.openUrl){
						location.href=self.openUrl+"?categoryId="+event.target.id;
						return;
					}
					
					location.href=util.formatUrl("/sys/attend/mobile/index.jsp?categoryId="+event.target.id,true);
					return;
					var targetDom = event.target;
					self.currentCategoryId=targetDom.id;
					self.currentCategoryName=targetDom.innerText;
					self.setSelected();
					self.hideSelectingShade();
					
					domClass.remove(query('.muiSignInDropDownMenu .selected')[0],'selected');
					domClass.add(targetDom,'selected');
					
					var listDom = query('.muiSignInflowList')[0];
					var scrollView = viewRegistry.getEnclosingScrollable(listDom);
					var store = registry.byNode(listDom);
					store.url = util.setUrlParameter(store.url,"categoryId",self.currentCategoryId);
					topic.publish('/mui/list/onReload',scrollView);
				},350);
			},
			setSelected :function(){
				this.valueNode.value=this.currentCategoryId;
				this.valueShowNode.innerHTML = this.currentCategoryName;
			},
			startup:function(){
				if(this._started){ return; }
				this.inherited(arguments);
			}
		});
});