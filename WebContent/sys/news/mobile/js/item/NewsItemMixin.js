define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
	"dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin",
   	"mui/i18n/i18n!sys-news:mobile"
	], function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase, util, openProxyMixin, msg) {
	var item = declare("sys.news.mobile.js.item.NewsItemMixin", [ItemBase,openProxyMixin], {
		
		tag:"li",
		// 标题
		label:"",
		// 图片URL
		icon:"",
		// 摘要文本信息
		summary:"",
		// 创建时间
		created:"",
		// 创建者
		creator:"",
		// 阅读量
		count:"",
		// 状态
		status:"",
		// 详情页链接
		href:"",
		
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = this.containerNode = domConstruct.create(this.tag, { className:'muiNewsListItem' });
			this.buildInternalRender();
		},
		
		buildInternalRender : function() {

		    var contentNode = domConstruct.create('div', {className:'muiNewsListItemContent'}, this.containerNode);
			
			// 标题DOM
		    if(this.label){
			   var subjectNode = domConstruct.create("div",{className:"muiNewsListItemTitle muiFontSizeM muiFontColorInfo"},contentNode);
			   if(this.status){
				   // 有【置顶】标签的情况
				   subjectNode.innerHTML = this.status + this.label;
			   }else{
				   subjectNode.innerHTML = this.label;
			   }
		    }
		    
			// 摘要文本信息DOM
		    if(this.summary){
		    	var summaryNode = domConstruct.create("div",{className:"muiNewsListItemSummary muiFontSizeS",innerHTML:this.summary},contentNode);
		    }
			
			// 封面图片
		    if(this.icon){
		    	var imgDivNode = domConstruct.create("div",{className:"muiNewsListItemIcon"},contentNode);
		    	var imgNode = domConstruct.create("span", { className: "muiNewsListItemImg",style:{'background-image':'url(' + util.formatUrl(this.icon) +')'}}, imgDivNode);
		    }
			
	        // 组件底部基本信息容器（创建人、创建时间、阅读量）
	        var footerContainer = domConstruct.create("div", { className: "muiNewsListItemFooter muiFontSizeS muiFontColorMuted" }, contentNode);
		    
		    // 创建人
			if(this.creator){
				this.createdNode = domConstruct.create("div",{className:"muiNewsListItemCreator",innerHTML:this.creator},footerContainer);
			}
			
			// 创建时间
			if(this.created){
				this.createdNode = domConstruct.create("div",{className:"muiNewsListItemCreated",innerHTML: this.created},footerContainer);
			}
			
			// 阅读量
			if(this.count){
				this.countNode = domConstruct.create("div",{className:"muiNewsListItemRead",innerHTML: "<span class='muiNewsListItemReadNum muiFontSizeM'>"+this.count+"</span><span class='muiNewsListItemReadViewText'>" + msg['mobile.sysNews.read'] + "</span>"},footerContainer);
			}		
			
			
			// 绑定点击事件
			if(this.href){
				this.proxyClick(this.containerNode, this.href, '_blank');
			}
			
		},
		
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});