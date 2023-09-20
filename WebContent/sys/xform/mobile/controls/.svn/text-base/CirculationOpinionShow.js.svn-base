define([ "dojo/_base/declare", "dojo/dom-construct", "sys/xform/mobile/controls/xformUtil", "dojo/query", "mui/form/_FormBase"], 
		function(declare, domConstruct, xUtil, query, _FormBase) {
	var claz = declare("sys.xform.mobile.controls.CirculationOpinionShow", [ _FormBase], {

		buildRendering :function(){
			this.auditShow = query(".muiFormEleAuditShowWrap",this.domNode);
			if(this.auditShow.length >0){
				for(var i = 0 ; i < this.auditShow.length ; i++){
					this.auditShow[i].style.textAlign="left" // 使其内容向左靠齐
				}
			}
			this.inherited(arguments);
			if (this.newMui) {
				domConstruct.place(this.tipNode,this.domNode,'first');
			}
			domConstruct.destroy(this.rightIcon);
		}
		
	});
	return claz;
});
