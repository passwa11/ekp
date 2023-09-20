define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/query", "mui/form/_FormBase", "dojo/dom-class",
         "dojo/touch","dojo/dom-style"], 
		function(declare, domConstruct, query, _FormBase, domClass, touch, domStyle) {
	var claz = declare("sys.xform.mobile.controls.AuditShow", [ _FormBase], {

		buildRendering :function(){
			this.auditShow = query(".muiFormEleAuditShowWrap",this.domNode);
			this.inherited(arguments);
			if (this.newMui) {
				domConstruct.place(this.tipNode,this.domNode,'first');
				
			}
			domClass.add(this.domNode,"muiDingAuditShow");
			this.iconNode = domConstruct.create(
	            "div",
	            { className: ".muiFormEleAuditIcon" },
	            this.domNode
		     );
			domConstruct.destroy(this.rightIcon);
			var self = this;
			
			query('.lui-ding-audit-postscript', this.domNode).forEach(function(obj){
				var folder = query(".lui-ding-audit-folder",obj)[0];
				var expand = query(".lui-ding-audit-expand",obj)[0];
				self.connect(folder, "click", function(evt) {
					self._stopPropagation(evt);
					domStyle.set(expand,"display","block");
					domStyle.set(folder,"display","none");
					if (!domClass.contains(obj,"collapse")) {
						domClass.add(obj,"collapse");
					}
		        });
				self.connect(expand, "click", function(evt) {
					self._stopPropagation(evt);
					domStyle.set(folder,"display","block");
					domStyle.set(expand,"display","none");
					if (domClass.contains(obj,"collapse")) {
						domClass.remove(obj,"collapse");
					}
		        });
				
			});
			
		},
		
		_stopPropagation: function(evt){
			if (evt.stopPropagation) {
                evt.stopPropagation()
              }
              if (evt.cancelBubble) {
                evt.cancelBubble = true
              }
              if (evt.preventDefault) {
                evt.preventDefault()
              }
              if (evt.returnValue) {
                evt.returnValue = false
              }
		},
		
		startup: function () {
			this.inherited(arguments);
			domClass.remove(this.domNode, "muiFormRight");
		}
		
	});
	return claz;
});