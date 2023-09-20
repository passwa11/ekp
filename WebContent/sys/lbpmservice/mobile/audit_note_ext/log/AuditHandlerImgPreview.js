define( [ "dojo/_base/declare", "dojo/_base/lang", "dojo/dom-construct", "dojox/mobile/_ItemBase" ], function(declare, lang, domConstruct, _ItemBase) {

	return declare("sys.lbpmservice.mobile.audit_note_ext.log.AuditHandlerImgPreview",
			[ _ItemBase ], {

				buildRendering:function(){
					this.inherited(arguments);
				},
				
				postCreate : function() {
					this.inherited(arguments);
					this.previewImgBgColor="#fff";
					this.formatContent(this.domNode);
				}
			
		});
});