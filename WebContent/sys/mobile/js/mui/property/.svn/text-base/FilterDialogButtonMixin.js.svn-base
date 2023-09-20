define([ "dojo/_base/declare", "dijit/_WidgetBase", 
         "dojo/dom-construct", "dojo/query", "dojo/dom-class", "dojo/topic","mui/i18n/i18n!sys-mobile:mui.button"], 
		function(declare, WidgetBase, domConstruct, query, domClass, topic, msg) {
	return declare( "mui.property.FilterDialogButtonMixin", null,{
		
		buildDialogButton : function(){
			
			var buttonDialog = domConstruct.create('div', {
				className : 'filterlayer_bottom_buttons'
			});

			var buttonReset = domConstruct.create('div', {
				className : 'filterlayer_bottom_button bg_1',
				innerHTML : msg['mui.button.reset']
			}, buttonDialog);
			
			this.connect(buttonReset, 'onclick', 'reset');
			
			var buttonSubmit = domConstruct.create('div', {
				className : 'filterlayer_bottom_button bg_2',
				innerHTML : msg['mui.button.ok']
			}, buttonDialog);

			this.connect(buttonSubmit, 'onclick', 'submit');
			
			return buttonDialog
		}
		
	});
});
