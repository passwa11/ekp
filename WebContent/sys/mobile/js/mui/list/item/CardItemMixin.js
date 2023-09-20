define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojox/mobile/_ItemBase",
  "mui/util",
  "dojo/on",
  "mui/dialog/Tip",
  "mui/openProxyMixin"
], function(declare, domConstruct, ItemBase, util, on, Tip, openProxyMixin) {
  var item = declare(
    "mui.list.item.CardItemMixin",
    [ItemBase, openProxyMixin],
    {
      tag: "li",

      // 消息来源
      modelNameText: "",
      // 创建时间
      created: "",
      // 创建者
      creator: "",
      // 创建人图像
      icon: "",
      // 链接
      href: "",
      // 当前节点
      lbpmCurrNodeValue: "",
      // 摘要
      summary: "",
      // 发布时间
      docPublishTime: "",
      // 所属部门
      docDeptName: "",
      // 摘要
      summary: "",
      // 阅读数
      docReadCount: "",
      // 标签
      tagNames: "",

      buildRendering: function() {
        this.domNode = domConstruct.create("li",{className: "muiCardItem"}, this.containerNode);
        this.inherited(arguments)
        this.buildInternalRender()
      },
      
      buildInternalRender: function() {
    	  
			// 对齐方式Class类名（只显示标题时，标题需与左侧图标垂直居中对齐，反之则垂直顶端对齐）
		    var verticalAlignClass = this.isJustShowSubject()?'muiCardItemVerticalAlignMiddle':'muiCardItemVerticalAlignTop';
		    
			// 外层容器DOM
			var containerNode = domConstruct.create('div', { className:'muiCardItemContainer' }, this.domNode);
			
			// 左侧容器DOM
			var leftNode = domConstruct.create('div', { className:'muiCardItemLeft '+verticalAlignClass }, containerNode);
			
			if(this.icon){
				// 用户头像
				var personIconNode = domConstruct.create('div', { className:'muiCardItemPersonIcon' }, leftNode);
				domConstruct.create("div", {className:"muiCardItemPersonIconImg", style:{background:'url(' + util.formatUrl(this.icon) +') center center no-repeat',backgroundSize:'cover'}}, personIconNode);
			}else{
				//自定义列表图标（字体图标）
				var listIconClass = this.listIcon? this.listIcon : "muis-official-doc";
				var cardIconNode = domConstruct.create('div', { className:'muiCardItemIcon' }, leftNode);
				domConstruct.create("i", {className:'fontmuis '+listIconClass}, cardIconNode);
			}			
			
			// 右侧容器DOM
			var rightNode = domConstruct.create('div', { className:'muiCardItemRight '+verticalAlignClass }, containerNode);		
			
			// 标题
			var subject = domConstruct.create("div",{className:"muiCardItemSubject muiFontSizeM muiFontColorInfo",innerHTML:this.label},rightNode);
			
			// 摘要文本
			if(this.summary){
				domConstruct.create("div",{className:'muiCardItemSummary muiFontSizeS',innerHTML:this.summary},rightNode);
			}			
			
			if( this.creator || this.docDeptName || this.created || this.docPublishTime 
				|| this.modelNameText || this.lbpmCurrNodeValue || this.docReadCount || this.tagNames
			    || this.arg0 || this.arg1 || this.arg2){
				
				// 右侧底部footer DOM
				var rightFooterNode = domConstruct.create('div', { className:'muiCardItemRightFooter muiFontSizeS muiFontColorMuted '+(this.docReadCount?'muiCardItemHasReadCount':'') }, rightNode);
				
				// 创建人
				if(this.creator){
					domConstruct.create('div', { className:'muiCardItemCreator', innerHTML:this.creator }, rightFooterNode);
				}
				
				// 部门名称（发文部门）
				if(this.docDeptName){
					domConstruct.create('div', { className:'muiCardItemDocDeptName', innerHTML:this.docDeptName }, rightFooterNode);
				}
				
				// 创建时间
				if(this.created){
					domConstruct.create('div', { className:'muiCardItemCreatedTime', innerHTML:this.created }, rightFooterNode);
				}
				
				// 发布时间
				if(this.docPublishTime){
					domConstruct.create('div', { className:'muiCardItemPublishTime', innerHTML:this.docPublishTime }, rightFooterNode);
				}
				
				// 消息来源（模块名称）
				if(this.modelNameText){
					domConstruct.create('div', { className:'muiCardItemModelNameText', innerHTML:this.modelNameText }, rightFooterNode);
				}
				
				// 当前节点
				if(this.lbpmCurrNodeValue){
					domConstruct.create('div', { className:'muiCardItemCurrNodeText', innerHTML:this.lbpmCurrNodeValue }, rightFooterNode);
				}
				
				// 标签
				if(this.tagNames){
					domConstruct.create('div', { className:'muiCardItemTagNames', innerHTML:this.tagNames }, rightFooterNode);
				}
				
				// 自定义参数
				if(this.arg0){
					domConstruct.create('div', { className:'muiCardItemExtArg extArg0', innerHTML:this.arg0 }, rightFooterNode);
				}
				if(this.arg1){
					domConstruct.create('div', { className:'muiCardItemExtArg extArg1', innerHTML:this.arg1 }, rightFooterNode);
				}
				if(this.arg2){
					domConstruct.create('div', { className:'muiCardItemExtArg extArg2', innerHTML:this.arg2 }, rightFooterNode);
				}				
				
				// 阅读数量
				if(this.docReadCount){
					domConstruct.create('div', { className:'muiCardItemReadInfo', innerHTML:'<span class="muiCardItemReadCount">'+this.docReadCount+'</span><span class="muiCardItemViewText">浏览</span>' }, rightFooterNode);
				}				
			}
			
			if(this.href){
				// 绑定点击事件（跳转至详情查看页）
				this.proxyClick(this.domNode, this.href, '_blank');
			}else{
				// 背景锁（不支持移动端查看详情的数据添加一张半透明锁状背景图）
				var lockNode = domConstruct.create('div', { className:'muiCardItemLock' }, this.domNode);
				// tip提醒（暂不支持移动访问）
				this.makeLockLinkTip(this.domNode);
			}
			
      },
      
      
		/**
		* 是否仅显示标题
		*  此方法的意义是提供给列表数据在不同场景下设置不同的CSS样式
		* （“摘要文本”、“创建人”、“部门名称”、“创建时间”、“发布时间”、“消息来源”、“当前节点”、“标签”、“阅读数量” 这些全部都不显示时，标题与左侧图标垂直居中对齐，反之则垂直顶端对齐）
		* @return boolean
		*/
		isJustShowSubject:function(){
			return !(this.summary || this.creator || this.docDeptName || this.created || this.docPublishTime 
					|| this.modelNameText || this.lbpmCurrNodeValue || this.tagNames  || this.docReadCount 
					|| this.arg0 || this.arg1 || this.arg2);
		},	

        makeLockLinkTip: function(linkNode) {
	        this.href = "javascript:void(0);";
	        on(linkNode, "click", function(evt) {
	          Tip.tip({icon: "mui mui-warn", text: "暂不支持移动访问"});
	        });
        },

      startup: function() {
        if (this._started) {
          return
        }
        this.inherited(arguments)
      },

      _setLabelAttr: function(text) {
        if (text) this._set("label", text)
      }
    }
  )
  return item
})
