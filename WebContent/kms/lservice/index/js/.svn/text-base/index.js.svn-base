define(function(require, exports, module) {

	var env = require('lui/util/env');

	window.lservice = {

		// 左侧导航点击跳转
		navOpenPage : function(url) {

			var view = LUI.getPageView();

			if (view) {

				view.open(url, '_iframe', {
					transition : 'fadeIn',
					history : true
				});

			} else {
				window.open(url, '_self');
			}
		},

		// 右侧角色切换
		switchRole : function(fdUrl, on) {

			if ('on' == on)
				return;

			LUI.pageOpen(env.fn.formatUrl(fdUrl), '_iframe');

		}
	};
});