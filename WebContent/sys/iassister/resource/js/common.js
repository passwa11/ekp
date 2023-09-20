define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");
	require("resource/js/jquery-plugin/src/jquery.form.js");

	var findIdx = function(arr, finded) {
		var rtnIdx = -1;
		for (var i = 0, len = arr.length; i < len; i++) {
			var item = arr[i];
			if (finded(item)) {
				rtnIdx = i;
				break;
			}
		}
		return rtnIdx;
	}

	var getFileName = function(filePath) {
		var fileName = filePath.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,
				"$1");
		var pos = fileName.lastIndexOf("\.");
		return fileName.substring(0, pos);
	}

	var getFileExt = function(filePath) {
		var fileName = filePath.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,
				"$1");
		var pos = fileName.lastIndexOf("\.");
		return fileName.substring(pos + 1);
	}

	var vueInstance = Vue && new Vue();

	var msg = function(params) {
		vueInstance && vueInstance.$message(params);
	}

	var notify = function(params) {
		vueInstance && vueInstance.$notify(params);
	}

	var loading = function(params) {
		return vueInstance && vueInstance.$loading(params);
	}

	var splitArray = function(arr, split) {
		var rtn = "";
		for (var i = 0, len = arr.length; i < len; i++) {
			rtn += arr[i];
			if (i < len - 1) {
				rtn += split;
			}
		}
		return rtn;
	}

	var _notifyOffset = {
		time : 0,
		offset : 0
	}

	var notifyOffset = function() {
		var time = new Date().getTime()
		if (time - _notifyOffset.time > 500) {
			_notifyOffset.offset = 0
			_notifyOffset.time = time
		} else {
			_notifyOffset.offset += 90
		}
		return _notifyOffset.offset
	}

	/**
	 * 验证url格式
	 */
	var checkUrl = function(str_url) {
		var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
				+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp
				// 的user@
				+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
				+ "|" // 允许IP和DOMAIN（域名）
				+ "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
				+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
				+ "[a-z]{2,6})" // first level domain- .com or .museum
				+ "(:[0-9]{1,4})?" // 端口- :80
				+ "((/?)|" // a slash isn't required if there is no file name
				+ "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
		var re = new RegExp(strRegex);
		return re.test(str_url);
	}

	var arrayToMap = function(arr, key) {
		var rtnMap = {}
		for (var i = 0, len = arr.length; i < len; i++) {
			rtnMap[arr[i][key]] = arr[i];
		}
		return rtnMap;
	}

	var cloneObject = function(obj) {
		return JSON.parse(JSON.stringify(obj));
	}

	var genUUID = function() {
		return Number(Math.random().toString().substr(3, 6) + Date.now())
				.toString(36);
	}
	
	var genID = function() {
		var rtnID = "";
		var getIdUrl = require.resolve("../jsp/genID.jsp#");
		$.ajax({
			async : false,
			cache : false,// IE必须这样
			url : getIdUrl,
			success : function(rtnData) {
				rtnID = $.trim(rtnData);
			}
		})
		return rtnID;
	}

	var arrForEach = function(arr, callback) {
		for (var i = 0, len = arr.length; i < len; i++) {
			if ("break" == callback(arr[i], i)) {
				break;
			}
		}
	}

	var arrDel = function(arr, findCond, value) {
		arrForEach(arr, function(item, idx) {
			if (findCond(item, value)) {
				arr.splice(idx, 1);
				return "break";
			}
		})
	}

	var postJsonData = function(url, data) {
		var rtn = {
			success : false
		}
		var formEle = window.$("<form></form>");
		formEle.ajaxSubmit({
			async : false,
			url : url,
			type : "POST",
			data : data,
			success : function(result) {
				rtn = result;
			}
		})
		return rtn;
	}

	module.exports.findIdx = findIdx;
	module.exports.getFileName = getFileName;
	module.exports.getFileExt = getFileExt;
	module.exports.msg = msg;
	module.exports.notify = notify;
	module.exports.loading = loading;
	module.exports.splitArray = splitArray;
	module.exports.notifyOffset = notifyOffset;
	module.exports.checkUrl = checkUrl;
	module.exports.arrayToMap = arrayToMap;
	module.exports.arrForEach = arrForEach;
	module.exports.arrDel = arrDel;
	module.exports.cloneObject = cloneObject;
	module.exports.genUUID = genUUID;
	module.exports.genID = genID;
	module.exports.postJsonData = postJsonData;
})