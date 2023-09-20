/**
 * 列表增加海报<br>
 * 暂时课件列表使用
 */
define([ "dojo/_base/declare", 'dojo/dom-construct', 'mui/util' ], function(
		declare,
		domConstruct,
		util) {

	return declare("mui.list.PosterMixin", null, {
		

		poster : null,

		startup : function() {

			if (this._started) {
				return;
			}

			if (this.poster)
				domConstruct.create('img', {
					style : 'width:100%;margin-top:1rem',
					src : util.formatUrl(this.poster)
				}, this.domNode);

			this.inherited(arguments);

		}

	});
});