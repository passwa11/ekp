/**
 * 
 */
define([ "dojo/_base/declare", "dijit/_WidgetBase","dojo/dom-construct" , "dojo/dom-class", 'dojo/_base/lang', "dojo/topic"], 
		function(declare, WidgetBase,domConstruct , domClass, lang, topic) {
	var claz = declare("sys.xform.mobile.controls.massdata.template.Paging", [WidgetBase], {

		// evt : {pageno:xx,pageSize,totalSize:xx,columns:xxx,datas:xxx}
		render : function(evt){
			if(!this.buttonDomNode){
				this.buttonDomNode = domConstruct.create("div",{
					className : "massdata-paging"
				},this.domNode); 
			}
			this.buttonDomNode.innerHTML = "";
			var totalPage = Math.ceil(evt.totalSize / evt.pageSize);
			this.pageno = evt.pageno;
			this.controlId = evt.controlId;
			this.hasNext = totalPage > evt.pageno ;
			this.hasPre = evt.pageno > 1 ;
			if(this.hasPre || this.hasNext){
				this.createPageElement();	
			}
			
		},
		
		createPageElement : function(){
			// 上一页
			var preDomNode = domConstruct.create("span",{
				className : "mui mui mui-back"
			},this.buttonDomNode);
			this.on("click",lang.hitch(this, function(evt){
				if(this.hasPre && preDomNode === evt.target){
					topic.publish("/sys/xform/massdata/page",{pageno : --this.pageno,controlId : this.controlId});	
				}
			}));
			if(this.hasPre){
				domClass.add(preDomNode,"active");
			}else{
				domClass.remove(preDomNode,"active");
			}
			
			// 下一页
			var nextDomNode = domConstruct.create("span",{
				className : "mui mui mui-forward"
			},this.buttonDomNode);
			this.on("click",lang.hitch(this, function(evt){
				if(this.hasNext && nextDomNode === evt.target){
					topic.publish("/sys/xform/massdata/page",{pageno : ++this.pageno,controlId : this.controlId});
				}
			}));
			if(this.hasNext){
				domClass.add(nextDomNode,"active");
			}else{
				domClass.remove(nextDomNode,"active");
			}
		}
		
	});
	return claz;
});