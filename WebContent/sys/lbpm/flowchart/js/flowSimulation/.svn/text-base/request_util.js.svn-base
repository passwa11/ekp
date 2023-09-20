/**
 * ajax请求工具类
 */

var RequestUtil=new Object();
/**
 * 异步请求Service
 * arg：请求参数
*/
RequestUtil.postRequestServers = function (arg) {
	var result = null;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_bean=flowSimulationService",
		async: false,
		data: arg,
		type: "POST",
		dataType: 'json',
		success: function (data) {
			result = data;
		},
		error: function (er) {

		}
	});
	return result;
}
RequestUtil.postRequest=function(arg,url){
	var result = null;
	$.ajax({
		url: Com_Parameter.ContextPath + url,
		async: false,
		data: arg,
		type: "POST",
		dataType: 'json',
		success: function (data) {
			result = data;
		},
		error: function (er) {

		}
	});
	return result;
}