define([ "dojo/_base/declare", "mui/form/Category","dojo/_base/lang","dojo/query","dojo/topic","dojox/mobile/viewRegistry", "dojox/mobile/TransitionEvent"],
	function(declare, Category, lang, query, topic,viewRegistry,TransitionEvent) {
		var PlaceComponent = declare("km.imeeting.EquipmentComponent",[ Category ], {
			
			isMul : true,

			templURL : "km/imeeting/mobile/resource/tmpl/equipment.jsp" ,
			
			postCreate : function(){
				this.inherited(arguments);
				topic.subscribe("/mui/category/tmploaded",lang.hitch(this,"handleTmpLoaded"));
			},
			
			startup : function(){
				this.inherited(arguments);
				this.backview = viewRegistry.getEnclosingView(this.domNode);
			},
			
			handleTmpLoaded : function(evt){
				//this.connect(query('.muiCateHeaderReturn',evt.dom)[0],'click',lang.hitch(this,"backOpt"));
			},
			
			backOpt : function(){
				this.closeDialog(this);
			}
			
		});
		return PlaceComponent;
});