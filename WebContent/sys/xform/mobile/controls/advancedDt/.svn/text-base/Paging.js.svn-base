/**
 *  @description: 高级明细表查看更多
 *
 */
define([ "dojo/_base/declare", "dijit/_WidgetBase","dojo/dom-construct" , "dojo/dom-class",
        'dojo/_base/lang', 'dojo/dom-style', "dojo/topic", "mui/i18n/i18n!sys-xform-base:mui"],
		function(declare, WidgetBase,domConstruct , domClass, lang, domStyle, topic, Msg) {
	var claz = declare("sys.xform.mobile.controls.advancedDt.Paging", [WidgetBase], {

		render : function(evt){
			if(!this.buttonDomNode){
				this.buttonDomNode = domConstruct.create("div",{
					className : "muiDetailTableMore"
				},this.domNode);
			}
			this.buttonDomNode.innerHTML = "";
			var totalPage = Math.ceil(evt.totalSize / evt.pageSize);
			this.pageno = evt.pageno;
			this.controlId = evt.controlId;
			this.hasNext = totalPage > evt.pageno ;
			this.hasPre = evt.pageno > 1 ;
			if(this.hasNext){
				this.createPageElement();
                domClass.remove(this.buttonDomNode, "hide");
			} else {
                domClass.add(this.buttonDomNode, "hide");
            }
		},
		
		createPageElement : function(){
			var preDomNode = domConstruct.create("span", {
				className: "muiDetailTableMoreText",
                innerHTML: Msg["mui.detailTable.more"]
			},this.buttonDomNode);
			var nextDomNode = domConstruct.create("i", {
				className : "mui-detail-table-forward-icon icon-drop-down"
			},this.buttonDomNode);
			this.on("click", lang.hitch(this, function(evt){
				if(this.hasNext){
					topic.publish("/sys/xform/detailsTable/page", {pageno: ++this.pageno, controlId: this.controlId});
				}
			}));
		}
	});
	return claz;
});