/**
 * 判断浏览器是否支持某一个CSS3属性
 * 
 * @param {String}
 *            属性名称
 * @return {Boolean} true/false
 */

function supportCss3(style) {
	style = style || 'animation-play-state';
	var prefix = [ 'webkit', 'Moz', 'ms', 'o' ], i, humpString = [], htmlStyle = document.documentElement.style, _toHumb = function(string) {
		return string.replace(/-(\w)/g, function($0, $1) {
			return $1.toUpperCase();
		});
	};
	for (i in prefix)
		humpString.push(_toHumb(prefix[i] + '-' + style));
	humpString.push(_toHumb(style));
	for (i in humpString)
		if (humpString[i] in htmlStyle)
			return true;
	return false;
}

/**
 * 动态加载CSS文件
 * 
 * @param path
 * @return
 */
function loadingCSS(path) {
	if (!path || path.length === 0) {
		throw new Error('没有文件可加载！');
	}
	var head = document.getElementsByTagName('head')[0];
	var link = document.createElement('link');
	link.href = path;
	link.rel = 'stylesheet';
	link.type = 'text/css';
	head.appendChild(link);
}

// 判断浏览器是否支持CSS3样式
if (supportCss3()) {
	loadingCSS(Com_Parameter.ContextPath + "sys/ui/extend/theme/default/style/switch.css");
}
