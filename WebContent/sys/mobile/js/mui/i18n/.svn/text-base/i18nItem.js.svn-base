define(['dojo/_base/declare','dojo/_base/lang','dojo/dom-style','dijit/_WidgetBase','dojo/dom-construct','mui/i18n/i18nUtils','dojo/topic'], 
		function(declare,lang,domStyle,WidgetBase,domConstruct,i18nUtils,topic) {

	return declare('mui.i18n.i18nItem', [WidgetBase], {

		bundle : null,
		
		key : null,
		
		buildRendering: function(){
			this.inherited(arguments);
			domStyle.set(this.domNode,'display','inline');
		},
		
		startup : function(){
			i18nUtils.queue(this.bundle + ':' + this.key);
			topic.subscribe('mui.i18n.complete',lang.hitch(this,this.i18nComplete));
		},
		
		i18nComplete : function(msgObj){
			msgObj = msgObj || {};
			var msg = msgObj[this.bundle + ':' + this.key];
			this.domNode.innerHTML = msg;
		}
		
	});

});