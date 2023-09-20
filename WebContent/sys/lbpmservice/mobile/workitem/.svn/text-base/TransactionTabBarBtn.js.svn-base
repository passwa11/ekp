/**
 * 事务组件
 */
define(["dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-construct", "dojo/dom-class", "dojo/dom-style",
		"dojo/dom-prop", "dojo/topic", 'dojo/_base/lang', "dojo/query", "dojo/_base/array"],
		function(declare, WidgetBase, domConstruct, domClass, domStyle, domProp, topic, lang, query, array){
	
	var clz = declare("sys.lbpmservice.mobile.workitem.TransactionTabBarBtn",[WidgetBase],{
		
		tabBar : null,
		
		options : [], 	// 事务项
		
		isExpand : false,
		
		curTaskId : '',
		
		buildRendering : function() {
			this.inherited(arguments);
			domConstruct.place(this.domNode,this.tabBar.domNode,"before");
			domClass.add(this.domNode,"transactionBar");
			// 头部
			this.headNode = domConstruct.create("div",{
				className : "transactionHead"
			},this.domNode);
			this.renderHead(this.headNode);
			
			// 内容区
			this.contentNode = domConstruct.create("div",{
				"className" : "transactionContent"
			},this.domNode);
			this.ulContentNode = domConstruct.create("ul",{},this.contentNode);
			this.renderContent();
			
			this.expand(this.isExpand);
			// 默认选中第一项
			var liArr = query("li",this.ulContentNode);
			if(liArr.length > 0){
				for(var i = 0;i < liArr.length;i++){
					var transactionid = domProp.get(liArr[i],"data-transactionid");
					if(this.curTaskId == transactionid){
						this.chooseItem(liArr[i]);
					}
				}
			}
		},
		
		postCreate: function() {
			this.inherited(arguments);
			// 当导航栏的页签切换和底部按钮滑动时，收起弹层
			this.subscribe('/mui/navitem/_selected',"packUp");
			this.subscribe("/mui/tabbar/switch","packUp");
		},
		
		renderHead : function(headNode){
			var titleNode = domConstruct.create("span",{
				
			},headNode);
			titleNode.innerHTML = "事务(<span class='striking'>" + this.options.length + "</span>):";
			
			this.textNode = domConstruct.create("span",{
				className : "text"
			},headNode);
			
			// 伸展按钮
			this.expandNode = domConstruct.create("span",{
				className : "striking icon mui"
			},headNode);
			this.connect(this.expandNode,"click",lang.hitch(this, function(evt){
				this.isExpand = !this.isExpand;
				this.expand(this.isExpand);
			}));
		},
		
		renderContent : function(ulContentNode){
			for(var i = 0;i < this.options.length;i++){
				var option = this.options[i];
				var li = domConstruct.create("li",{},this.ulContentNode);
				var isHideAllNodeIdentifier = false;
				var optionText = option.text;
				var defalutRep = new RegExp("^"+"N.");
				// 开启了隐藏节点编号中的流程中的节点编号则隐藏节点编号
				if(defalutRep.test(optionText)){
					var opt = optionText.substring(
						optionText.indexOf("N"),
						optionText.indexOf(".") + 1);
					optionText = optionText.replace(opt, "");
					if (lbpm && lbpm.settingInfo){
						if (lbpm.settingInfo.isHideNodeIdentifier === "true" && lbpm.settingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier"){
							isHideAllNodeIdentifier = true;
						}
					}
				}
				li.innerHTML = isHideAllNodeIdentifier ? optionText : option.text;
				this.connect(li, "click", lang.hitch(this, function(evt){
					var triggeredDom = evt.target;
					this.chooseItem(triggeredDom);
				}));
				domProp.set(li,"data-transactionid",option.value);
			}
		},
		
		expand : function(isExpand){
			if(isExpand){
				this.expandNode.innerHTML = "收起";
				domClass.remove(this.expandNode,"mui-up-n");
				domClass.add(this.expandNode,"mui-down-n");
				domStyle.set(this.textNode,"display","none");
				domClass.add(this.contentNode,"expand");
			}else{
				this.expandNode.innerHTML = "伸展";
				domClass.remove(this.expandNode,"mui-down-n");
				domClass.add(this.expandNode,"mui-up-n");
				domStyle.set(this.textNode,"display","inline-block");
				domClass.remove(this.contentNode,"expand");
			}
		},
		
		chooseItem : function(dom){
			// 收起内容区
			this.packUp();
			var text = dom.innerHTML;
			var liArr = query("li",this.ulContentNode);
			array.forEach(liArr,function(item){
				domClass.remove(item,"active");
			});
			domClass.add(dom,"active");
			// 填充标题内容
			this.textNode.innerHTML = text;
			topic.publish("/lbpm/operation/switch",this,{
				methodSwitch: false,
				value: domProp.get(dom,"data-transactionid") ,
				label: text
			});
		},
		
		// 收起弹层
		packUp : function(){
			this.isExpand = false;
			this.expand(this.isExpand);
		}
	});
	
	return clz;
});