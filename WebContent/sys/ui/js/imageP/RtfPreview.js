define(function(require, exports, module) {
	var preview = require('./preview');
	var $ = require('lui/jquery');

	var data = [];
	var _container = null;
	var prefix = '/resource/fckeditor/editor/filemanager/download?fdId=';

	var RtfPreview = function(cfg) {
		var container = $(cfg.selector);
		container.on('click', function(evt) {
			var target = evt.target;
			if (target.tagName.toLowerCase() == 'img') {
				var src = $(target).attr('src'), fdId = src.substring(src.indexOf(prefix)+prefix.length);
				if(src.indexOf(prefix)<0){
					return false;
				}
				var data = buildData(container);
				var datas = {
					data : data,
					value : fdId
				};
				preview({
					data : datas,
					path : cfg.path,
					panel : cfg.panel,
					url : cfg.url,
					thumbUrl : cfg.thumbUrl
				});
			}
		});

	};

	function buildData(root) {
		
		if (data.length > 0 && _container == root[0])
			return data;
		_container = root[0];
		data = [];
		root.find('img').each(function() {
			var src = $(this).attr('src');
			if (src.indexOf(prefix) >= 0) {
				var fdId = src.substring(src.indexOf(prefix)+prefix.length);
				data.push({
					value : fdId
				});
			}
		});
		return data;

	}

	module.exports = RtfPreview;

});