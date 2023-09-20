define([ "dojo/_base/declare", "dojo/dom-construct", "mui/util",
	"sys/mportal/mobile/card/mixins/CardCreateSimplateCategoryMixin"], function(
		declare, domConstruct, util, CardCreateSimplateCategoryMixin) {

	return declare("sys.mportal.TabCardCreateMixin", [CardCreateSimplateCategoryMixin], {

		buildFooter : function(index) {

			this.inherited(arguments);

			var create = this.configs[index].operations.create;
			var vars = this.configs[index].vars;
			if (!create)
				return;

			// 不清楚有没有业务逻辑包含，暂且叫新建
			create.name = '新建';
			
			var btn = domConstruct.create('a', {
				className : 'mui_ekp_portal_btn_new muiFontColorMuted',
				innerHTML : '<i class="fontmuis muis-new muiFontSizeXS"></i>' + create.name
			}, this['footer_' + index]);
			
			if(create.cfg && create.cfg.type && this.bindClick) {
				this.bindClick(btn, create, vars, this.uuid + "_" + index);
			} else {
				this.proxyClick(btn, create.href, '_blank');
			}
		}

	});
});