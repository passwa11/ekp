define(function(require, exports, module) {



	var $ = require('lui/jquery');
	var dialog = require('lui/dialog');
	var topic = require('lui/topic');
	var Template = require('lui/view/Template');
	var env = require('lui/util/env');
	

	window.goToView = function(url){
		url = env.fn.formatUrl(url)
       // window.open(url,"blank");
		Com_OpenWindow(url,"blank");
	}

})