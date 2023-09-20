define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"dojo/on",
   	"mui/dialog/Tip", 
	"mui/openProxyMixin",
	"dojo/dom-geometry",
	"dojo/_base/window",
	"mui/i18n/i18n!sys-modeling-main"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,on,Tip, openProxyMixin,domGeometry,win,modelingLang) {
	var item = declare("sys.modeling.main.resources.js.mobile.listView.DocItemMixin", [ItemBase, openProxyMixin], {
		tag:"li",
		baseClass:"muiDocItem", 

		//创建时间
		created:"",
		//创建者
		creator:"",
		//创建人图像
		icon:"",
		//链接
		href:"",
		//摘要
		summary : [],
		//发布时间
		docPublishTime:"",
		//所属部门
		docDeptName:"",
		//阅读数
		docReadCount:"",
		//标签
		tagNames:"",
		
		buildRendering:function(){
			var className = "";
			if(this.columnNum == "2"){
				className = "S";
			}
			if(this.fdType == "2"){
				className += " board-list";
			}
			this.domNode = domConstruct.create('li', {className : className}, this.containerNode);
			this.inherited(arguments);
			if(this.fdType == "1"){
				this.buildCardRender();
			}else if(this.fdType == "2"){
				this.buildBoardRender();
			}else{
			    this.buildInternalRender();
			}
		},
		buildInternalRender : function() {
			var itemClass = this.href ? {}:{className:'lock'};
			this.contentNode = domConstruct.create('a', itemClass);

			var infoNode = domConstruct.create("div",{className:".mui_modeling_list_container"},this.contentNode);

			// 标题字段
			var subjectNode = domConstruct.create("div",{
				className : "subjectBlock"
			},infoNode);
			domConstruct.create("span",{
				className : "lines-cut",
				innerHTML : this.label || modelingLang['mui.modeling.undefind']
			},subjectNode);

			// 摘要字段
			if(this.summary.length > 0){
				var summaryNode = domConstruct.create("div",{
					className : "summaryBlock"
				},infoNode);
				for(var i = 0;i < this.summary.length;i++){
					domConstruct.create("div",{
						className : "summaryBlockDiv",
						innerHTML : this.summary[i]
					},summaryNode);
				}
			}

			if(this.href){
				this.proxyClick(this.contentNode, this.href, '_blank');
			}
			
			domConstruct.place(this.contentNode,this.domNode);
		},
		
		makeLockLinkTip:function(linkNode){
			this.href='javascript:void(0);';
			on(linkNode,'click',function(evt){
				Tip.tip({icon:'mui mui-warn', text:modelingLang['mui.modeling.Mobile.access.not.supported']});
			});
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},

		buildCardRender:function(){
			var itemClass = this.href ? {}:{className:'lock'};
			this.contentNode = domConstruct.create('a', itemClass);

			var contentClassName = "viewMobileConfigCardContent";
			if(this.summary.length <= 0){
				contentClassName = "viewMobileConfigCardContent abstract";
			}
			var infoNode = domConstruct.create("div",{className:contentClassName},this.contentNode);

			if(this.imgSrc){
				var coverNode = domConstruct.create("div",{className:"viewMobileConfigCardCoverBox"},infoNode);
				domConstruct.create("div",{
					className : "viewMobileConfigCardCover default",
					style:"background:url('" + util.formatUrl(this.imgSrc) + "')  no-repeat center;background-size: contain;background-repeat: no-repeat;"
				},coverNode);
			}
			var textBoxNode = domConstruct.create("div",{className:"viewMobileConfigCardTextBox"},infoNode);
			// 标题字段
			domConstruct.create("div",{
				className : "viewMobileConfigCardTextTitle",
				innerHTML : this.label || modelingLang['mui.modeling.undefind']
			},textBoxNode);

			// 摘要字段
			if(this.summary.length > 0){
				var summaryNode = domConstruct.create("ul",{
					className : "viewMobileConfigCardTextList"
				},textBoxNode);
				for(var i = 0;i < this.summary.length;i++){
					domConstruct.create("li",{
						className : "",
						innerHTML : this.summary[i]
					},summaryNode);
				}
			}

			if(this.href){
				this.proxyClick(this.contentNode, this.href, '_blank');
			}

			domConstruct.place(this.contentNode,this.domNode);
		},

		buildBoardRender:function () {
			var itemClass = this.href ? {}:{className:'lock'};
			this.contentNode = domConstruct.create('a', itemClass);
			//看板视图
			var infoNode = domConstruct.create("div",{className:"cardViewMobileConfigCardContent"},this.contentNode);

			var boardTextBox = domConstruct.create("div",{className:"cardViewMobileConfigCardTextBox"},infoNode);
			domConstruct.create("div",{
				className:"cardViewMobileConfigCardTextTitle",
				innerHTML : this.label || modelingLang['mui.modeling.undefind']
			},boardTextBox);
			var boardTextBoxList = domConstruct.create("div",{className:"cardViewMobileConfigCardTextList"},boardTextBox);
			if(this.imgSrc){
				var coverNode = domConstruct.create("div",{className:"cardViewMobileConfigCardCoverBox"},infoNode);
				var style = "";
				if(this.imgSrc.indexOf("default")<0){
					style = "background:url('" + util.formatUrl(this.imgSrc) + "')  no-repeat center;background-size:contain;";
				}
				domConstruct.create("div",{
					className:"cardViewMobileConfigCardCover default",
					style:style
				},coverNode);
				var boardTextBoxWidth = this.getScreenWidth() - //屏幕宽度
									88 -  //图片宽度
									15*2 - //docItem的padding宽度
									24*2 - //摘要项的padding宽度
									6;//其他宽度
				domStyle.set(boardTextBox,"width",boardTextBoxWidth + 'px');
			}else{
				var boardTextBoxWidth = this.getScreenWidth() - //屏幕宽度
									15*2 - //docItem的padding宽度
									24*2 - //摘要项的padding宽度
									6;//其他宽度
			 	domStyle.set(boardTextBox,"width",boardTextBoxWidth + 'px');
			}
			// 摘要字段
			if(this.summary.length > 0){
				//摘要显示个数
				var count = 6;
				var splitLength = this.summaryFlag === "1"?2:3;
				var boardTextSummary = null;
				let mod = this.summary.length % splitLength;
				if (this.summary.length > count){
					for(var i = 0;i < count;i++){
						this.createBoardItem(this.summary[i],boardTextBoxList,false);
					}
					var boardTextBoxMoreList = domConstruct.create("div",{className:"cardViewMobileConfigCardTextList boardContentMore"},boardTextBox);
					for(var i = count;i < this.summary.length;i++){
						this.createBoardItem(this.summary[i],boardTextBoxMoreList,false);
					}
					var boardTextMore = domConstruct.create("div",{className:"viewMobileConfigCardContentMore"},infoNode);
					on(boardTextMore,'click',function(evt){
						evt.stopPropagation();
						domStyle.set(boardTextMore,"display","none");
						domClass.add(boardTextBoxMoreList,"active");
					});
				}else{
					for(var i = 0;i < this.summary.length;i++){
						if(i % splitLength === 0){
							boardTextSummary = domConstruct.create("div",{className:"cardViewMobileConfigCardSummary"},boardTextBoxList);
						}
						let maxWidth = "";
						if(i + mod < this.summary.length){
							maxWidth = splitLength === 2 ? "max-width:50%":"max-width:33%";
						}
						let isLast = i == this.summary.length-1 && (i + 1) % splitLength === 1;
						this.createBoardItem(this.summary[i],boardTextSummary || boardTextBoxList,isLast,maxWidth);
					}
				}
			}

			if(this.href){
				this.proxyClick(this.contentNode, this.href, '_blank');
			}

			domConstruct.place(this.contentNode,this.domNode);
		},

		createBoardItem : function (data,dom,isLast,maxWidth){
			if(isLast){
				maxWidth = "max-width:100%";
			}
			let summaryLi = domConstruct.create("div",{
				className:"cardViewMobileConfigCardSummaryItem",
				style:maxWidth
			},dom);
			let width = "max-width:100%";
			if(this.summaryFlag === "1"){
				domConstruct.create("div",{
					innerHTML : data.text,
					className: "cardViewMobileConfigCardSummaryItemTitle"
				},summaryLi);
				domConstruct.create("div",{
					innerHTML : ":",
					className: ""
				},summaryLi);
				width = "";
			}
			domConstruct.create("div",{
				innerHTML : data.value,
				className: "cardViewMobileConfigCardSummaryItemContent",
				style:width
			},summaryLi);
		},
		getScreenWidth : function() {
			return win.global.innerWidth
				|| win.doc.documentElement.clientWidth
				|| win.doc.documentElement.offsetWidth;
		},
	});
	return item;
});