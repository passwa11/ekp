define([ "dojo/_base/declare", "dijit/_WidgetBase",
         "mui/i18n/i18n!kms-common:kmsCommonDocErrorCorrection.mobile",
         "dojo/dom-construct", "mui/device/adapter", "mui/util"], 
		function(declare, widgetBase, Msg, domConstruct, adapter, util) {
	return declare("sys.kms.common.ErrorCorrectionViewContent", [widgetBase], {
		
		fdModelId : null,
		
		fdModelName : null,
		
		docSubject : null,
		
		docDescription : null,
		
		buildRendering : function() {
			this.inherited(arguments);
			
			// 标题
			this.buildTitle();
			
			// 纠错原因
			this.buildContent();
			
		},
		
		buildTitle : function(){
			var muiErrorCorrectionTitle = domConstruct.create('div', {
				className : 'muiErrorCorrectionTitle',
			}, this.domNode);
			
			domConstruct.create('div', {
				className : 'muiErrorCorrectionTitleLeft',
				innerHTML : Msg['kmsCommonDocErrorCorrection.mobile.title'] + ':'
			}, muiErrorCorrectionTitle);
			
			var target = domConstruct.create('div', {
				className : 'muiErrorCorrectionTitleRight',
				innerHTML : this.docSubject,
			}, muiErrorCorrectionTitle);
			
			this.connect(target, 'click', 'open');
			
		},
		
		open : function() {

			if(this.fdModelId && this.fdModelName){
				
				if(this.fdModelName.indexOf('KmsMultidocKnowledge') > 0)
					adapter.open(util.formatUrl("/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId=" + this.fdModelId), '_self');
				else if(this.fdModelName.indexOf('KmsWikiMain') > 0)
					adapter.open(util.formatUrl("/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=view&fdId=" + this.fdModelId), '_self');
					
			}

		},
		
		buildContent : function(){
			var muiErrorCorrectionContent = domConstruct.create('div', {
				className : 'muiErrorCorrectionContent'
			}, this.domNode);
			
			domConstruct.create('div', {
				className : 'muiErrorCorrectionContentTitle',
				innerHTML : Msg['kmsCommonDocErrorCorrection.mobile.reason'] + ':'
			}, muiErrorCorrectionContent);
			
			domConstruct.create('div', {
				className : 'muiErrorCorrectionContentMsg',
				innerHTML : this.docDescription
			}, muiErrorCorrectionContent);
		}
		
	});
});