//新建分类选择框，给门户或二级页面调用
define(function(require, exports, module) {
	var dialog = require('lui/dialog');
	var modelingLang = require("lang!sys-modeling-base");
	//参数为默认选中的分类
	function addDoc(fdAppModelId, isFlow){
		if(isFlow === 'true'){
			var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=" + fdAppModelId;
			$.ajax({
				url: url,
				method: 'GET',
				async: false
			}).success(function (ret) {
				if (ret && ret.data) {
					if (ret.data.length === 1) {
						var id = ret.data[0].id;
						Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingAppModelMain.do?method=add&fdAppModelId=" + fdAppModelId + "&fdAppFlowId=" + id, "_blank");
					} else {
						var url = "/sys/modeling/main/flow_dialog.jsp?fdAppModelId=" + fdAppModelId;
						dialog.iframe(url, modelingLang['modelingAppView.selectFlow'], function (param) {
							if(param) {
								var data = $.parseJSON(param);
								if (data.url){
									Com_OpenWindow(Com_Parameter.ContextPath +data.url, "_blank");
								}
							}
						}, {width: 750, height: 500})
					}
				} else {
					alert(ret);
				}
			});
		}else{
			Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingAppSimpleMain.do?method=add&fdAppModelId=" + fdAppModelId, "_blank");
		}
	}
	exports.addDoc = addDoc;
});