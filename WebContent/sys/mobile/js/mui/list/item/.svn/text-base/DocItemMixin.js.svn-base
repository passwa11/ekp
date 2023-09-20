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
	"mui/i18n/i18n!sys-mobile:mui.mobile"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,on,Tip, openProxyMixin, msg) {
	var item = declare("mui.list.item.DocItemMixin", [ItemBase, openProxyMixin], {
		
		tag:"li",

		//创建时间
		created:"",
		//创建者
		creator:"",
		//创建人图像
		icon:"",
		//链接
		href:"",
		//摘要
		summary:"",
		//发布时间
		docPublishTime:"",
		//所属部门
		docDeptName:"",
		//摘要
		summary:"",
		//阅读数
		docReadCount:"",
		//标签
		tagNames:"",
		
		buildRendering:function(){
			this.domNode = domConstruct.create(this.tag, {className : 'muiDocItem'}, this.containerNode);
			this.inherited(arguments);
			this.buildInternalRender();
		},
		
		buildInternalRender : function() {
			
			// 对齐方式Class类名（只显示标题时，标题需与左侧图标垂直居中对齐，反之则垂直顶端对齐）
		    var verticalAlignClass = this.isJustShowSubject()?'muiDocItemVerticalAlignMiddle':'muiDocItemVerticalAlignTop';
		    
			// 外层容器DOM
			var containerNode = domConstruct.create('div', { className:'muiDocItemContainer' }, this.domNode);
			
			// 左侧容器DOM
			var leftNode = domConstruct.create('div', { className:'muiDocItemLeft '+verticalAlignClass }, containerNode);
			
			if(this.icon){
				// 用户头像
				var personIconNode = domConstruct.create('div', { className:'muiDocItemPersonIcon' }, leftNode);
				domConstruct.create("div", {className:"muiDocItemPersonIconImg", style:{background:'url(' + util.formatUrl(this.icon) +') center center no-repeat',backgroundSize:'cover'}}, personIconNode);
			}else{
				//自定义列表图标（字体图标）
				var listIconClass = this.listIcon? this.listIcon : "muis-official-doc";
				var cardIconNode = domConstruct.create('div', { className:'muiDocItemIcon' }, leftNode);
				domConstruct.create("i", {className:'fontmuis '+listIconClass}, cardIconNode);
			}			
			
			// 右侧容器DOM
			var rightNode = domConstruct.create('div', { className:'muiDocItemRight '+verticalAlignClass }, containerNode);		
			
			// 标题
			var subject = domConstruct.create("div",{className:"muiDocItemSubject muiFontSizeM muiFontColorInfo",innerHTML:this.label},rightNode);
			
			// 摘要文本
			if(this.summary){
				domConstruct.create("div",{className:'muiDocItemSummary muiFontSizeS',innerHTML:this.summary},rightNode);
			}			
			
			if( this.creator || this.docDeptName || this.created || this.docPublishTime || this.docReadCount || this.tagNames ){
				// 右侧底部footer DOM
				var rightFooterNode = domConstruct.create('div', { className:'muiDocItemRightFooter muiFontSizeS muiFontColorMuted '+(this.docReadCount?'muiDocItemHasReadCount':'') }, rightNode);
				
				// 创建人
				if(this.creator){
					domConstruct.create('div', { className:'muiDocItemCreator', innerHTML:this.creator }, rightFooterNode);
				}
				
				// 部门名称（发文部门）
				if(this.docDeptName){
					domConstruct.create('div', { className:'muiDocItemDocDeptName', innerHTML:this.docDeptName }, rightFooterNode);
				}
				
				// 创建时间
				if(this.created){
					domConstruct.create('div', { className:'muiDocItemCreatedTime', innerHTML:this.created }, rightFooterNode);
				}
				
				// 发布时间
				if(this.docPublishTime){
					domConstruct.create('div', { className:'muiDocItemPublishTime', innerHTML:this.docPublishTime }, rightFooterNode);
				}
				
				// 标签
				if(this.tagNames){
					domConstruct.create('div', { className:'muiDocTagNames mui_DocTagNames', innerHTML:this.tagNames }, rightFooterNode);
				}
				
				// 阅读数量
				if(this.docReadCount){
					domConstruct.create('div', { className:'muiDocItemReadInfo', innerHTML:'<span class="muiDocItemReadCount">'+this.docReadCount+'</span><span class="muiDocItemViewText">'+ msg['mui.mobile.sysMobile.view'] + '</span>' }, rightFooterNode);
				}				
			}
			
			if(this.href){
				// 绑定点击事件（跳转至详情查看页）
				this.proxyClick(this.domNode, this.href, '_blank');
			}else{
				// 背景锁（不支持移动端查看详情的数据添加一张半透明锁状背景图）
				var lockNode = domConstruct.create('div', { className:'muiDocItemLock' }, this.domNode);
				// tip提醒（暂不支持移动访问）
				this.makeLockLinkTip(this.domNode);
			}
			
		},
		
		/**
		* 是否仅显示标题
		*  此方法的意义是提供给列表数据在不同场景下设置不同的CSS样式
		* （“摘要文本”、“创建人”、“部门名称”、“创建时间”、“发布时间”、“标签”、“阅读数量” 这些全部都不显示时，标题与左侧图标垂直居中对齐，反之则垂直顶端对齐）
		* @return boolean
		*/
		isJustShowSubject:function(){
			return !(this.summary || this.creator || this.docDeptName || this.created || this.docPublishTime || this.tagNames  || this.docReadCount);
		},		
		
		makeLockLinkTip:function(linkNode){
			this.href='javascript:void(0);';
			on(linkNode,'click',function(evt){
				Tip.tip({icon:'mui mui-warn', text:'暂不支持移动访问'});
			});
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