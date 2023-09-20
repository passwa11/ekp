/**
 * 参考ProcessItemMixin
 */
define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin",
   	"mui/i18n/i18n!sys-lbpmservice-support"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,openProxyMixin,msg) {
	var item = declare("sys.lbpmservice.mobile.lbpm_audit_note.lbpm_process_status.js.ProcessStatusItemMixin", [ItemBase,openProxyMixin], {
		tag:"div",		
		baseClass:"",
		//创建人头像
		icon:"",
		//节点名称
		nodeName:"",
		//处理人
		fdHandler:"",
		//接收时间
		fdStartDate:"",
		//操作时间
		operationDate:"",
		//操作状态
		operationType:"",
		//操作
		operations:"",	
		buildRendering:function(){
			this.inherited(arguments);
			this.contentNode = domConstruct.create(
					this.tag, {
						className : 'mui_flowstate_data',
						style:{"margin-top":".6rem"}
					});
			this.buildInternalRender();
			
			domConstruct.place(this.contentNode,this.containerNode);
			
		},
		buildInternalRender : function() {
//			<div class="mui_flowstate_data_heading">
//	        <div class="heading_avatar"><img src="images/avatar.png"></div>
//	        <div class="heading_content">
//	            <h4 class="heading_title">用户体验中心审批节点</h4>
//	            <p class="heading_subhead">EKP设计部-负责人</p>
//	            <a class="mui_flowstate_data_btn" href="javascript:void(0);">催办</a>
//	        </div>
//        </div>
			var topArea = domConstruct.create("div",{className:"mui_flowstate_data_heading"},this.contentNode);
			var topt=domConstruct.create("div", { className: "heading_avatar",innerHTML:'<img src="'+util.formatUrl(this.icon)+'">'}, topArea);
			var topb=domConstruct.create("div",{className:"heading_content"},topArea);
			domConstruct.create("h4", { className: "heading_title",innerHTML:this.nodeName}, topb);
			domConstruct.create("p", { className: "heading_subhead",innerHTML:this.fdHandler}, topb);
			topb.appendChild(domConstruct.toDom(this.operations));
			//和上面的添加元素的方式都可以都是直接添加字符串形式的dom例“<span>test</span>”
			//topb.innerHTML+=this.operations;
//			<div class="mui_flowstate_data_content">
//		        <ul class="mui_flowstate_data_timeaxis">
//		            <li>
//		                <span class="title">接收时间：</span>
//		                <span class="content">2018-06-26 14：55</span>
//		            </li>
//		            <li>
//		                <span class="title">操作时间：</span>
//		                <span class="content"></span>
//		            </li>
//		            <li>
//		                <span class="title">操作状态：</span>
//		                <span class="content"><span class="color_warning">未查看</span></span>
//		            </li>
//		        </ul>
//	        </div>
			var bottomArea = domConstruct.create("div",{className:"mui_flowstate_data_content"},this.contentNode);
			var bottomUl = domConstruct.create("ul",{className:"mui_flowstate_data_timeaxis"},bottomArea);
			domConstruct.create("li",{innerHTML:'<span class="title">'+msg['mui.lbpm.process.status.fdStartDate']+'：</span><span class="content">'+this.fdStartDate+'</span>'},bottomUl);
			this.operationDate=this.operationDate.replace(/^\s+|\s+$/gm,"");//去除前后空格
			domConstruct.create("li",{innerHTML:'<span class="title">'+msg['mui.lbpm.process.status.fdFinishDate']+'：</span><span class="content">'+this.operationDate+'</span>'},bottomUl);
			domConstruct.create("li",{innerHTML:'<span class="title">'+msg['mui.lbpm.process.status.operationType']+'：</span><span class="content">'+this.operationType+'</span>'},bottomUl);
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});