define(function(require, exports, module) {

	var $ = require('lui/jquery');
	var env = require('lui/util/env');

	function Mind() {

		this.init = function(config) {

			var url = env.fn.formatUrl(config.url);
			var container = config.container;

			if (!url || !container)
				return;

			// 是否支持canvas
			var elem = document.createElement('canvas');

			if (!!(elem.getContext && elem.getContext('2d'))) {
				this.h5Mind(container, url);
			} else {
				this.flashMind(container, url);
			}

		};

		// canvas实现的思维导图
		this.h5Mind = function(container, url) {

			$.getJSON(url + "&fdType=h5", function(data) {
				if(data) {
					var  rootNode = null, rbadge = 0;
					for(var i = 0; i < data.length; i ++) {
						if(rootNode == null && data[i].id == "root") {
							rootNode = data[i];
						} 
						if(data[i].parentid == "root") {
							rbadge += (data[i].badge ? parseInt(data[i].badge) : 0);
						}
					}
					if(rootNode) {
						rootNode.badge = rbadge;
					}
				}
				
				var map = kmsjsmap.init({
					container : container,
					data : data,
					editable : false,
					onRelation : function(data) {

						if (!data.id)
							return;

						var seajs = parent.seajs;
						seajs.use('lui/framework/router/router-utils',
								function(routerUtils) {

									var router = routerUtils.getRouter();
									if (router) {

										if ('root' == data.id) {
											router.push('/all', {});
											return;
										}

										router.push('/docCategory', {
											'docCategory' : data.id
										});
									}
								});

					}
				});

			});

		};

		// flash实现的思维导图
		this.flashMind = function(container, url) {

			var kvs = new KPlayer(
					{
						id : container,
						width : '100%',
						height : '650px'
					},
					env.fn
							.formatUrl('/sys/simplecategory/category_preview/flash/KPlayer.swf'));
			kvs.onReady(function() {

				kvs.loadDataFromServer(url);
			});
		};

	}

	module.exports = Mind;

})
