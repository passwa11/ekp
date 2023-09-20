define([ "dojo/_base/declare", "dijit/_WidgetBase",
         "mui/i18n/i18n!sys-introduce:sysIntroduceMain.mobile",
         "dojo/dom-construct", "mui/device/adapter"], 
		function(declare, widgetBase, Msg, domConstruct, adapter) {
	return declare("sys.introduce.IntroduceViewContent", [widgetBase], {
		
		url : null,

		docSubject : null,
		
		fdIntroduceReason : null,
		
		buildRendering : function() {
			this.inherited(arguments);
			
			// 标题
			this.buildTitle();
			
			// 推荐原因
			this.buildContent();
			
		},
		
		buildTitle : function(){
			var muiIntroduceTitle = domConstruct.create('div', {
				className : 'muiIntroduceTitle',
			}, this.domNode);
			
			domConstruct.create('div', {
				className : 'muiIntroduceTitleLeft',
				innerHTML : Msg['sysIntroduceMain.mobile.title'] + ':'
			}, muiIntroduceTitle);
			
			var target = domConstruct.create('div', {
				className : 'muiIntroduceTitleRight',
				innerHTML : this.docSubject,
			}, muiIntroduceTitle);
			
			this.connect(target, 'click', 'open');
			
		},
		
		open : function() {

			if(this.url){
				adapter.open(this.url, "_self");
			}

		},
		
		buildContent : function(){
			var muiIntroduceContent = domConstruct.create('div', {
				className : 'muiIntroduceContent'
			}, this.domNode);
			
			domConstruct.create('div', {
				className : 'muiIntroduceContentTitle',
				innerHTML : Msg['sysIntroduceMain.mobile.reason'] + ':'
			}, muiIntroduceContent);
			
			domConstruct.create('div', {
				className : 'muiIntroduceContentMsg',
				innerHTML : this.fdIntroduceReason
			}, muiIntroduceContent);
		}
		
	});
});