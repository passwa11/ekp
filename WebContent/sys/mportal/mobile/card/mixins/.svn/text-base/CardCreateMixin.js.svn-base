define([ "dojo/_base/declare", "dojo/dom-construct", "mui/util", 
		"sys/mportal/mobile/card/mixins/CardCreateSimplateCategoryMixin",
		"mui/i18n/i18n!:button.add"], 
		function(declare, domConstruct, util,
		CardCreateSimplateCategoryMixin,msg) {

	return declare("sys.mportal.CardCreateMixin", [CardCreateSimplateCategoryMixin], {
		
		buildFooter : function() {
			this.inherited(arguments);

			var create = this.config.operations.create;
			
			if (!create)
				return;

			if (!this.footerNode)
				this.buildFooterNode();

			// 不清楚有没有包含业务逻辑，暂且叫新建
			create.name = msg['button.add'];
			
			var btn = domConstruct.create('a', {
				className : 'mui_ekp_portal_btn_new muiFontColorMuted',
				innerHTML : '<i class="fontmuis muis-new muiFontSizeXS"></i>' + create.name
			}, this.footerNode);
			
			if(create.cfg && create.cfg.type && this.bindClick) {
				var vars = this.config.vars;
				this.bindClick(btn, create, vars, this.uuid);
			} else {
				var url = util.setUrlParameter(create.href, 'ownerId', dojoConfig.CurrentUserId);
				this.proxyClick(btn, url, '_blank');
			}
		}

		
	});
});