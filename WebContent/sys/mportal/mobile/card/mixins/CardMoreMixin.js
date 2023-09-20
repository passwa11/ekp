define([ "dojo/_base/declare", "dojo/dom-construct", "mui/util" ,"mui/i18n/i18n!sys-mportal:sysMportalPage.more"], function(
		declare, domConstruct, util, msg) {

	return declare("sys.mportal.CardMoreMixin", null, {

		buildHeader : function() {

			this.inherited(arguments);
			if (this.config.operations.more != false && this.config.more) {

				var mores = this.config.more.split('|');
				var url = "";
				var title = msg['sysMportalPage.more'] + " " + this.title;

				if (mores.length > 1) {
					url = mores[1];
					title = msg['sysMportalPage.more'];
				} else
					url = mores[0];

				url = util.urlResolver(url, this.config.vars);

				var moreNode = domConstruct.create('a', {
					href : 'javascript:;',
					className : 'muiFontSizeS muiFontColorMuted',
					innerHTML : title + '<i class="fontmuis muis-to-right muiFontSizeXS mui_ekp_portal_more_arrow"></i>'
				}, this.headerNode);

				this.proxyClick(moreNode, url, '_blank');

			}

		}

	});
});