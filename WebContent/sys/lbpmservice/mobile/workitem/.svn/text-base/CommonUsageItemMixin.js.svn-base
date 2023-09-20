define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/topic",
  "mui/openProxyMixin",
  "dojox/mobile/_ItemBase",
  "dojo/dom-class",
  "dojo/_base/lang",
  "dojox/mobile/_ScrollableMixin",
  "dojo/dom-style",
  "dijit/registry",
  "mui/i18n/i18n!sys-lbpmservice:mui.commonUsage"
], function(declare, domConstruct, topic, openProxyMixin, ItemBase, domClass, lang, ScrollableMixin,domStyle,registry,Msg) {
	return declare("sys.lbpmservice.mobile.workitem.CommonUsageItemMixin", [ItemBase,openProxyMixin,ScrollableMixin], {
	  tag:"li",
	  
	  baseClass:"commonUsageItem",
	  
	  scrollDir : "h",
	  
	  width : '100%',
	  
	  height: 'auto',

	  // 不显示滚动条
	  scrollBar : false,
		
	  appBars: false,
	  
	  buildRendering:function(){
		  this.inherited(arguments);
		  this.domNode = domConstruct.create(this.tag);
		  this.containerNode = domConstruct.create("div",{className:"commonUsageItemContainer"},this.domNode);
		  domClass.add(this.domNode, this.baseClass);
		  this.buildContent();
	  },
	  
	  buildContent : function(){
		  //构建内容部分
		  this.contentNode = domConstruct.create("div",{className:"commonUsageItemContent"},this.containerNode);
		  domStyle.set(this.contentNode,{"width":document.body.offsetWidth+"px"});
		  var infoNode = domConstruct.create("div",{className:"commonUsageItemInfo"},this.contentNode);
		  var textNode = domConstruct.create("div",{className:"commonUsageItemText",innerHTML:this.fdContent.replaceAll("<", "&lt;").replaceAll(">", "&gt;")},infoNode);
		  var editBtnNode = domConstruct.create("div",{className:"commonUsageItemEditBtn",innerHTML:Msg["mui.commonUsage.edit"]},infoNode);
		  this.connect(editBtnNode, "click", "_updateItem");
		  //构建删除按钮
		  this.delBtnNode = domConstruct.create("div",{className:"commonUsageItemDelBtn",innerHTML:Msg["mui.commonUsage.del"]},this.containerNode);
	      this.connect(this.delBtnNode, "click", "_deleteItem");
	  },
	  
	  startup : function() {
		  this.inherited(arguments);
		  
		  //计算宽度，设置到滚动的containernode上
		  var contentNodeWidth = this.contentNode.offsetWidth;
		  var delBtnNodeWidth = this.delBtnNode.offsetWidth;
		  var containerNodeWidth = contentNodeWidth+delBtnNodeWidth;
		  domStyle.set(this.containerNode,{
			  "width":containerNodeWidth+"px"
		  });
	  },
	  
	  _setLabelAttr: function(text){
		if(text)
			this._set("label", text);
	  },
	  
	  _updateItem:function(){
		  var listWgt = registry.getEnclosingWidget(this.domNode.parentNode);
		  if(!listWgt){
			  listWgt = registry.byId("commonUsageList");
		  }
		  topic.publish("/lbpm/commonUsageItem/edit",this,{listDatas:listWgt.listDatas});
	  },
	  
	  _deleteItem:function(){
		  topic.publish("/lbpm/commonUsageItem/delete",this);
	  }
  })
})
