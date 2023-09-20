define(["dojo/_base/declare",
	"dojo/_base/lang",
	"mui/panel/extend/_PromptMixin",
	"dojo/dom-style",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/dom-attr",
	"dojox/mobile/_StoreMixin",
	"mui/util",
	"mui/openProxyMixin",
	"mui/store/JsonRest",
	"mui/device/adapter",
	"dojo/on"],function(declare,lang,PromptMixin,domStyle,domConstruct,domClass,domAttr,StoreMixin,util,OpenProxyMixin,JsonRest,adapter,on){
	return declare("mui.panel._NavPanelPrompt",[StoreMixin,OpenProxyMixin],{
		
		url : "/sys/mobile/sys_mobile_module_navitem_config/sysMobileModuleNavItemConfig.do?method=dataNavItem&fdModelName=!{modelName}&fdModelId=!{modelId}",
		
		modelName:null,
		
		modelId:null,
		
		startup : function() {
			this.url = util.urlResolver(this.url,this);
			this.url = util.formatUrl(this.url);
			//请求json配置
			var store = new JsonRest({
				idProperty : 'fdId',
				target : this.url,
				defaultType : 'get'
			});
			setTimeout(lang.hitch(this, function() {
				this.setStore(store, this.query,
						this.queryOptions);
			}), 1)
			
			this.inherited(arguments);
			
			//绑定事件
			this.bindEvent();
		},
		
		buildRendering:function(){
			this.promptWgt = new PromptMixin({content:"测试"});
			this.moreNavItemBtnNode = domConstruct.create("div",{className: "muiMoreNavitemBtn"});
			//domClass.add(this.promptWgt.domNode,"muiMoreNavitemBtn");
			domConstruct.place(this.promptWgt.domNode,this.moreNavItemBtnNode,"last");
			//替换原图标，改动样式
			domClass.add(this.promptWgt.iconNode,"muiMoreNavitemIcon")
			//替换内容
			this.promptWgt.contentDom.innerHTML = "";
			this.promptWgt.tabItems = domConstruct.create(
				"ul",
				{
					className: "muiNavItems",
				},
				this.promptWgt.contentDom
	        );
			this.inherited(arguments);
		},
		
		buildOtherItems:function(modelingCssFlag){
			if(modelingCssFlag){
				domStyle.set(this.moreNavItemBtnNode,{"margin-top":".4rem"});
			}else{
				domStyle.set(this.fixedItem.domNode,{
				"padding-right":"5rem",
				"position":"relative"
				});
			}

			// 实例化一个更多页签按钮对象(提示框类型）
			domConstruct.place(this.moreNavItemBtnNode,this.fixedItem.domNode,'last');
		},
		
		bindEvent:function(){
			this.connect(this.promptWgt.contentDom,"click",function(event){
				var target = event.target;
				var url;
				while(target){
					if(domClass.contains(target,"muiNavItem") && domAttr.get(target,"data-url")){
						url = domAttr.get(target,"data-url");
						url = util.formatUrl(url,true)
						break;
					}
					target = target.parentElement;
				}
				if(url){
					adapter.open(url, "_blank");
				}
			})
		},
	
		onComplete : function(items) {
			this.generateList(items);
			this.promptWgt.content = this.promptWgt.contentDom.innerHTML;
		},
		
		generateList : function(items){
			var _self = this;
			if(!items)
				return;
			for(var i=0; i<items.length; i++){
				var url = items[i].url;
				var text = items[i].text;
				var iconClassName = items[i].iconClassName;
				
				var tabItemNode = domConstruct.create(
					"li",
					{
						className: "muiNavItem",
					},
					_self.promptWgt.tabItems
		        );
				var itemIconNode = domConstruct.create(
					"i",
					{
						className: "muiTabItemIcon",
					},
					tabItemNode
		        );
				domConstruct.create(
					"span",
					{
						className: "muiTabItemText",
						innerHTML:text
					},
					tabItemNode
		        );
				domAttr.set(tabItemNode,"data-url",url);
				domClass.add(itemIconNode,iconClassName);
			}
		}
		
	});
});