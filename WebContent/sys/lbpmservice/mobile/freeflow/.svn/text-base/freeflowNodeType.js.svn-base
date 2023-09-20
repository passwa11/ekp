define(
		['dojo/_base/declare',"dijit/_WidgetBase","dojo/query","dojo/dom-attr","dojo/dom-construct","dojo/topic","mui/i18n/i18n!sys-lbpmservice:mui.freeFlow"],
		function(declare,WidgetBase,query,domAttr,domConstruct,topic,Msg) {
			var freeflowNodeType = declare("sys.lbpmservice.mobile.freeflow.freeflowNodeType",
					[WidgetBase], {
						nodeType : 'reviewNode',
				
						buildRendering: function() {
					        this.inherited(arguments);
					        this.contentNode = domConstruct.create('div', {
								className : 'muiFreeflowNodeTypeWarp'
							},this.domNode);
					        domConstruct.create('div', {
								className : 'muiFreeflowNodeTypeTitle',
								innerHTML : Msg["mui.freeFlow.lbpm.nodeType."+this.nodeType]
							},this.contentNode);
							var listNode = domConstruct.create('ul', {
								className : 'muiFreeflowNodeTypeList'
							},this.contentNode);
							var height = (listNode.offsetWidth-20)/3/200*132;
							var liNode = domConstruct.create('li', {
								className : 'muiFreeflowNodeTypeli muiFreeflowNodeTypeDefault',
								style : {height:height+"px"}
							},listNode);
							domConstruct.create('div', {'data-value' : 3}, liNode);
							liNode = domConstruct.create('li', {
								className : 'muiFreeflowNodeTypeli muiFreeflowNodeTypeSerial',
								style : {height:height+"px"}
							},listNode);
							domConstruct.create('div', {'data-value' : 0}, liNode);
							liNode = domConstruct.create('li', {
								className : 'muiFreeflowNodeTypeli muiFreeflowNodeTypeSplit',
								style : {height:height+"px"}
							},listNode);
							domConstruct.create('div', {'data-value' : 1}, liNode);
							domConstruct.create('div', {
								className : 'muiFreeflowNodeTypeSplitDiv'
							},this.contentNode);
							this.cancelNode = domConstruct.create('div', {
								className : 'muiFreeflowNodeTypeCancel',
								innerHTML : Msg["mui.freeFlow.cancel"]
							},this.contentNode);
							this.bindEvent();
						},
						
						bindEvent : function(){
							this.connect(this.cancelNode,"click",function(evt){
								topic.publish("/sys/lbpmservice/freeflow/nodeTypeCancel");
							});
							var self = this;
							query(".muiFreeflowNodeTypeli div",this.domNode).on("click",function(evt){
								var nowTime = new Date().getTime();
							    var clickTime = this.ctime;
							    if(clickTime != 'undefined' && (nowTime - clickTime < 500)){
							       return false;
							    }
							    this.ctime = nowTime;
							    var dom = evt.target;
							    var val = domAttr.get(dom,"data-value");
							    topic.publish("/sys/lbpmservice/freeflow/nodeTypeSelect",val);
							})
						},
						
						postCreate : function() {
							this.inherited(arguments);
						}
					});
			return freeflowNodeType;
		});