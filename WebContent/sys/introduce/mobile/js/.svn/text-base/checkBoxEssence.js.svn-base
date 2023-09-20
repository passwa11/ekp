define(
		[ "dojo/_base/declare", "dijit/_WidgetBase", 
		  "mui/form/CheckBox", "dojo/dom-construct",
		  "dojo/topic"],
		function(declare, widgetBase, checkBox, domConstruct, topic) {

		return declare('sys.introduce.checkBoxEssence', [ checkBox ], {

			buildRendering : function() {
				this.inherited(arguments);
				this.buildHidden();
			},
			
			buildHidden : function(){
				this.hiddenDom = domConstruct.create('input', {
					'type' : 'hidden',
					'value' : '0',
					'name' : 'fdIntroduceToEssence',
				}, this.domNode);
				
			},
			
			_onClick : function(evt){
				this.inherited(arguments);

				if(this.checked)
					this.hiddenDom.value = '1';
				else
					this.hiddenDom.value = '0';
				
				topic.publish('/mui/introduce/validate');
				
			}

		})
});