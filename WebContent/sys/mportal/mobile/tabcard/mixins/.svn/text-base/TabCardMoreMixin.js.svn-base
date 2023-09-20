define([ "dojo/_base/declare", "dojo/dom-construct", "mui/util", "mui/i18n/i18n!sys-mportal:sysMportalPage.more" ], function(
		declare, domConstruct, util,msg) {

	return declare("sys.mportal.TabCardMoreMixin", null, {

		buildFooter : function(index) {

			this.inherited(arguments);

			var config = this.configs[index];

			if (!config.operations.more)
				return;

			var more = config.more;

			if (!more)
				return;

			var mores = more.split('|'), url, title = msg['sysMportalPage.more'];

			if (mores.length > 1) {
				url = mores[1];
			} else
				url = mores[0];

			url = util.urlResolver(url, config.vars);

			var btn = domConstruct.create('a', {
				className : 'muiFontColorMuted mui_ekp_portal_btn_more',
				innerHTML : '<i class="fontmuis muis-more"></i>' + title
			}, this['footer_' + index]);

			this.proxyClick(btn, url, '_blank');

		}

	});
});