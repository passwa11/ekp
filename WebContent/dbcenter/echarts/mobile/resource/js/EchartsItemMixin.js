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
	"mui/openProxyMixin"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,on,Tip, openProxyMixin) {
	var item = declare("mui.list.item.DocItemMixin", [ItemBase, openProxyMixin], {
		
		tag:"li",
		
		buildRendering:function(){
			this.domNode = domConstruct.create(this.tag, {className : 'clearfloat'}, this.containerNode);
			this.inherited(arguments);
			this.buildInternalRender();
		},
		
		buildInternalRender : function() {
			
			var dbcenterEchartsLeft = domConstruct.create('div', { 
				className : 'dbcenterEchartsLeft' 
			}, this.domNode);
			
			var p = domConstruct.create('p', null, dbcenterEchartsLeft);

			if(this.icon){
				domConstruct.create('img', { 
					src : this.icon,
					className : 'dbcenterEchartsImg'
				}, p);
			}
			
			var dbcenterEchartsInfo = domConstruct.create('div', { 
				className : 'dbcenterEchartsInfo'
			}, this.domNode);
			
			if(this.label){
				domConstruct.create('p', { 
					innerHTML : this.label
				}, dbcenterEchartsInfo);
			}
			
			var div = domConstruct.create('div', null, dbcenterEchartsInfo);
			
			if(this.creator) {
				domConstruct.create('span', { 
					className : 'line-cut',
					innerHTML : this.creator
				}, div);
			}
			
			if(this.created) {
				domConstruct.create('span', { 
					className : 'line-cut',
					innerHTML : this.created
				}, div);
			}
			
			if(this.href){
				// 绑定点击事件（跳转至详情查看页）
				this.proxyClick(this.domNode, this.href, '_blank');
			}
			
		},
		
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