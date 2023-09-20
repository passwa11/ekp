define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin",
   	"mui/i18n/i18n!km-comminfo:mui.kmComminfoMainPieces",
	], function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase, util, openProxyMixin, kmComminfoMsg) {
	var item = declare("mui.list.item.ComminfoItemMixin", [ItemBase,openProxyMixin], {
		tag:"li",
		
		baseClass:"muiCommInfoDataItem",
		
		linkHref:'/km/comminfo/km_comminfo_main/kmComminfoMain.do?method=view&&forward=viewDoc&fdId=',
		
		// 分类名称
		label:"",
		
		// 分类下的常用资料总计数
		count:"",
		
		// 常用分类列表
		childrens:[],
		
		
		buildRendering:function(){
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create(this.tag);
			}
			this.inherited(arguments);
			if (!this._templated)
				this.buildInternalRender();
		},
		
		buildInternalRender : function() {
			
			// 构建头部信息（分类名称、分类下的常用资料总计数）
			this._buildHeaderContent();
			
			// 构建分类下的常用资料项列表
			this._buildCommItemsContent();

		},
		
		
		/**
		* 构建头部信息（分类名称、分类下的常用资料总计数）
		* @return
		*/	
		_buildHeaderContent: function(){
			// 头部信息DOM
			var headerDom = domConstruct.create("div", { className: "muiCommInfoHeaderContainer" }, this.domNode);
			
			// 分类名称
			var cateTitleDom = domConstruct.create("div", { className: "muiCommInfoCateTitle",innerHTML:this.label }, headerDom);
			
			// 分类下的常用资料总计数
			var commCountDom = domConstruct.create("div", { className: "muiCommInfoCount",innerHTML:this.count+kmComminfoMsg['mui.kmComminfoMainPieces'] }, headerDom);
		},
		
		
		/**
		* 构建分类下的常用资料项列表内容
		* @return
		*/			
		_buildCommItemsContent: function(){
			// 一个分类下最多显示的常用资料项个数
			var maxShowLength = 3; 
			
			// 分类下的常用资料列表容器DOM
			var listContainerDom = domConstruct.create("div", { className: "muiCommInfoListContainer" }, this.domNode);	
			
			if(this.childrens){
				
				// 如果常用资料项超过三个，则只截取前面三个显示（如用户需要查看分类下的全部常用资料，需点击“展开”按钮查看更多）
				if(this.childrens.length>maxShowLength){
					
					var showChildrens = this.childrens.slice(0,maxShowLength);
					
					this._buildCommItems(showChildrens,listContainerDom);
					
					// 构建“展开”更多按钮DOM
					this.expandButton = domConstruct.create("div", { className: "muiCommInfoListExpandButton" }, this.domNode);
					domConstruct.create("i", {className:"fontmuis muis-drop-down"}, this.expandButton);
					
					// 构建“收起”更多按钮DOM（默认隐藏）
					this.collapseButton = domConstruct.create("div", { className: "muiCommInfoListCollapseButton", style:{"display":"none"} }, this.domNode);
					domConstruct.create("i", {className:"fontmuis muis-drop-up"}, this.collapseButton);
					
				    // 绑定“展开”更多按钮点击事件
					var _self = this;
				    this.connect(_self.expandButton, "click", (function(childrens,listContainerDom){
				    	return function(){
				    		 domStyle.set(_self.expandButton, {"display":"none"} );
				    		 domStyle.set(_self.collapseButton, {"display":"block"} );
				    		_self._buildCommItems(childrens,listContainerDom);
				    	}
				    })(this.childrens.slice(maxShowLength),listContainerDom));
				    
				    // 绑定“收起”更多按钮点击事件
				    this.connect(_self.collapseButton, "click", (function(listContainerDom){
				    	return function(){
				    		 domStyle.set(_self.collapseButton, {"display":"none"} );
				    		 domStyle.set(_self.expandButton, {"display":"block"} );
				    		 var listItemNodes = listContainerDom.children;
				    		 while(listItemNodes.length>maxShowLength){
				    			 domConstruct.destroy(listItemNodes[maxShowLength]);
				    		 }
				    	}
				    })(listContainerDom));				    
				    
				}else{
					this._buildCommItems(this.childrens,listContainerDom);
				}
				
			}
		},
		
		
		/**
		* 构建常用资料项列表
		* @param childrens 常用资料列表JSON数据数组
		* @param listContainerDom 分类下的常用资料列表容器DOM
		* @return
		*/
		_buildCommItems: function(childrens,listContainerDom){
			for(var i=0;i<childrens.length;i++){
				var itemData = childrens[i];
				var itemDom = domConstruct.create("div", { className: "muiCommInfoListItem" }, listContainerDom);
				
		        // 绑定常用资料点击事件（跳转至明细查看页面）
		        this.proxyClick(itemDom, this.linkHref+itemData.fdId, '_blank');
				
				// 左侧图标载体DOM
				var leftDom = domConstruct.create("div", { className: "muiCommInfoListItemLeft" }, itemDom);
				var iconDom = domConstruct.create("div", { className: "muiCommInfoListItemIcon" }, leftDom);
				domConstruct.create("i", {className:"fontmuis muis-official-doc"}, iconDom);
				
				// 右侧基本信息载体DOM（常用资料标题、创建人、创建时间）
				var rightDom = domConstruct.create("div", { className: "muiCommInfoListItemRight" }, itemDom);
				
				// 常用资料标题
				var commSubjectDom = domConstruct.create("div", { className: "muiCommInfoListItemSubject", innerHTML:itemData.label }, rightDom);
				
				// 右侧基本信息底部载体DOM（创建人、创建时间）
				var rightFooterDom = domConstruct.create("div", { className: "muiCommInfoListItemRightFooter"}, rightDom);
				
				// 创建人
				var creatorDom = domConstruct.create("div", { className: "muiCommInfoListItemCreator", innerHTML:itemData.creator }, rightFooterDom);
				
				// 创建时间
				var createdDom = domConstruct.create("div", { className: "muiCommInfoListItemCreated", innerHTML:itemData.created }, rightFooterDom);
			}		
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
