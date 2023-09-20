<%@ page language="java" contentType="text/javascript; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%  request.setAttribute("loginName", UserUtil.getUser().getFdLoginName()); %>
define(function(require, exports, module) { 
	
	var $ = require("lui/jquery");
	var strutil = require('lui/util/str');
	function ImKK(config) {
		this.kkPrefix = "landray://im?u=";
		this.imType = "t=0"
		this.awareport = "<%=ResourceUtil.getKmssConfigString("kmss.ims.kk.awareport") %>";
		this.awareIp = "<%=ResourceUtil.getKmssConfigString("third.im.kk.serverIp") %>";
		this.params = $.extend({}, config);
		this.imgPrefix = "http://" + this.awareIp + ":" + this.awareport + "/kkonline//?p=0:";
	}
	//show事件,___params为communicate的参数
	ImKK.prototype.onShow =  function ($content, ___params) {
		var self = this;
		if(self.awareport != "null" && self.awareIp != "null") {
		    var $img = $content.find("img");
			var loginName = ___params.loginName;
			var prefix = self.imgPrefix + loginName;
			var time = new Date().getTime();
			$img.attr("src", prefix + "&" + time );
			
			setTimeout(function() {
				var time = new Date().getTime();
				$img.attr("src", prefix + "&" + time );
				
				setTimeout(arguments.callee, 50000);
			}, 50000);
		}
	};
	
	ImKK.prototype.onClick = function($content, ___params) {
		if(___params.personId) {
			var fdId = ___params.personId;
			var url = "${KMSS_Parameter_ContextPath}third/im/kk/user.do?method=getLoginNameByUserId";
			$.ajax({
		        type: "post",
		        url:  url,
		        data: {"fdId":fdId},
		        async : false,
		        dataType: "json",
		        success: function (results ,textStatus, jqXHR)
		        {
		        	//console.log(results);
		        	if(results != null){
						var url = "landray.kk:${loginName}@p2p/open?user=" + results.loginName;
						//console.log(url);
						window.open(url, "_parent");
					}
		        },
		        error:function (XMLHttpRequest, textStatus, errorThrown) {      
		        	console.log("获取用户登录名失败，请检查网络！");
		        }
		     });
		
		}
	};
	module.exports = ImKK;
	
});