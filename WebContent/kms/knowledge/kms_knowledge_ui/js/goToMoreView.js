define(function(require, exports, module) {

	var env = require('lui/util/env');

	/**
	 * 统一的门户更多跳转
	 * tangyw
	 * 2021-12-10
	 * @param categoryId
	 * @param moduleUrl
	 * @param type
	 * @param toggleView
	 * @param scope
	 * @returns {string}
	 */
	function goToView(categoryId, moduleUrl, type, toggleView, scope, showIntro) {
		var fdUrl = "";

		// 模块前缀
		if (!moduleUrl) {
			return fdUrl;
		}
		fdUrl = moduleUrl;

		// 展示视图
		if (toggleView) {
			fdUrl = Com_SetUrlParameter(fdUrl, 'toggleView', toggleView);
		}

		// 分类处理
		if (categoryId) {
			if (categoryId.indexOf(";") > -1) {
				categoryId = "";
			} else {
				fdUrl = buildHashQueryParameter(fdUrl, "docCategory", categoryId)
			}
		}

		// 最热
		if ("docReadCount" == type || "fdTotalCount" == type) {
			fdUrl = Com_SetUrlParameter(fdUrl, 'orderBy', "fdTotalCount");
			fdUrl = buildHashQueryParameter(fdUrl, "j_path", "%2Fhot")
		}

		// 是否展示推荐
		if ("true" == showIntro) {
			// console.log("intro url before", fdUrl);
			fdUrl = buildHashQueryParameter(fdUrl, "showIntro", "true")
			fdUrl = Com_SetUrlParameter(fdUrl, 'orderBy', "docIntrCount");
			fdUrl = Com_SetUrlParameter(fdUrl, 'ordertype', "down");
			// console.log("intro url after", fdUrl);
		}

		// 精华
		if("docIsIntroduced" == type){
			fdUrl = buildHashQueryParameter(fdUrl, "docIsIntroduced", "1")
		}

		// 置顶
		if("docIsIndexTop" == type){
			fdUrl = buildHashQueryParameter(fdUrl, "docIsIndexTop", "1")
		}

		// 发布时间筛选
		if (scope && scope != "no") {
			fdUrl = buildCriQueryParameter(fdUrl, 'docPublishTime', getStartTime(scope))

			fdUrl = buildCriQueryParameter(fdUrl, 'docPublishTime', getEndTime(scope))
		}

		// 门户部件的更多只展示发布状态文档
		fdUrl = buildCriQueryParameter(fdUrl, "docStatus", "30");

		// 统一避开首页
		// 如果没有指定j_path，默认跳转到全部知识，用于条件筛选
		if (fdUrl.indexOf("j_path") < 0) {
			fdUrl = buildHashQueryParameter(fdUrl, "j_path", "%2Fall");
		}
		console.log("fdUrl=>", fdUrl)

		window.open(Com_Parameter.ContextPath + fdUrl, '_blank');
	}
	
	/**
	 * 文档知识专区门户部件更多跳转
	 */
	function goToZonePortletView(categoryId, type, scope) {
		var moduleUrl = 'kms/knowledge/';
		var toggleView = 'rowtable';

		goToView(categoryId, moduleUrl, type, toggleView, scope);
	}

	/**
	 * 构建hash参数
	 * @param fdUrl
	 * @param pkey
	 * @param pvalue
	 */
	function buildHashQueryParameter(fdUrl, pkey, pvalue) {
		if (fdUrl.indexOf("#") > -1) {
			fdUrl = fdUrl + "&";
		} else {
			fdUrl = fdUrl + "#";
		}
		fdUrl = fdUrl + pkey + "=" + pvalue;
		return fdUrl;
	}

	/**
	 * 统一的构造筛选项跳转参数的方法
	 *
	 * tangyw
	 * 2021-11-30
	 *
	 * @param fdUrl
	 * @param pkey
	 * @param pvalue
	 */
	function buildCriQueryParameter(fdUrl, pkey, pvalue) {
		if (fdUrl.indexOf("cri.q") > -1) {
			fdUrl = fdUrl + "%3B" + pkey + "%3A" + pvalue;
		} else {
			var criValue = pkey + "%3A" + pvalue;
			fdUrl = buildHashQueryParameter(fdUrl, "cri.q", criValue)
		}
		return fdUrl;
	}

	/**
	 * 根据类型获取时间范围
	 * @param type
	 * @returns {string}
	 */
	function getStartTime(type) {
		if (type == "month") {
			return getDate("day", -30);
		} else if (type == "season") {
			return getDate("day", -90);
		} else if (type == "halfYear") {
			return getDate("day", -180);
		} else if (type == "year") {
			return getDate("day", -365);
		} else if (type == "twoYear") {
			return getDate("day", -365 * 2);
		}

		return "";
	}

	/**
	 * 取当前时间
	 * @param type
	 * @returns {string}
	 */
	function getEndTime(type) {
		return getDate();
	}

	/**
	 * 根据天数查找时间
	 * @param type
	 * @param number
	 * @returns {*}
	 */
	function getDate(type,number) {
		if(!type){
			type = null;
		}
		if(!number){
			number=0;
		}
		var nowdate = new Date();
		switch (type) {
			case "day":   //取number天前、后的时间
				nowdate.setTime(nowdate.getTime() + (24 * 3600 * 1000) * number);
				var y = nowdate.getFullYear();
				var m = nowdate.getMonth() + 1;
				var d = nowdate.getDate();
				var retrundate = y + '-' + m + '-' + d;
				break;
			case "week":  //取number周前、后的时间
				var weekdate = new Date(nowdate + (7 * 24 * 3600 * 1000) * number);
				var y = weekdate.getFullYear();
				var m = weekdate.getMonth() + 1;
				var d = weekdate.getDate();
				var retrundate = y + '-' + m + '-' + d;
				break;
			case "month":  //取number月前、后的时间
				nowdate.setMonth(nowdate.getMonth() + number);
				var y = nowdate.getFullYear();
				var m = nowdate.getMonth() + 1;
				var d = nowdate.getDate();
				var retrundate = y + '-' + m + '-' + d;
				break;
			case "year":  //取number年前、后的时间
				nowdate.setFullYear(nowdate.getFullYear() + number);
				var y = nowdate.getFullYear();
				var m = nowdate.getMonth() + 1;
				var d = nowdate.getDate();
				var retrundate = y + '-' + m + '-' + d;
				break;
			default:     //取当前时间
				var y = nowdate.getFullYear();
				var m = nowdate.getMonth() + 1;
				var d = nowdate.getDate();
				var retrundate = y + '-' + m + '-' + d;
		}
		return retrundate;
	}

	exports.goToView = goToView;
	exports.goToZonePortletView = goToZonePortletView;
});