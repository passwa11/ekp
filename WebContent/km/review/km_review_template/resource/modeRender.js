/**
 * 表单模式item
 */
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	var lang = seajs.require('lang!km-review');
	window.buildNode = function(data){
		var node = $('<div/>').attr("class","card-box");
		node.attr("data-formid",data.value);
		var className = 'top icon-' + data.value;
		if (data.value.indexOf("thirdMall") > -1) {
			className += ' thirdMall';
		}
		$top = $("<span class='" + className + "' />").appendTo(node);
		//更新样式
		var html = [];
		if (data.name) {
			html.push('<p>' + data.name + '</p>');
		}
		if (data.desc) {
			html.push('<span>' + data.desc + '</span>');
		}
		if (data.value.indexOf("thirdMall") > -1) {
			if (isSuccess) { //云商城
				html.push('<a href="#">' + modelLang["enterTemplateCenter"] + '</a>');
			}
			if (isNoAuth) {
				html.push('<a href="#">' + modelLang["authorize"] + '</a>');
			}
		}
		//html.push('</div>');
		node.append(html.join(""));
		node.on('click',function(){
			var url = "";
			var thirdMallCreateParam = "&sourceFrom=Reuse&sourceKey=Reuse&type=2";
			
			if (data.value.indexOf("thirdMall") > -1) {
				//跳转到云商城页面
				var createUrl = __absPath + $("[name='add_url']").val();
				if (isSuccess) {
					url = data.url + "&sysVerId=" + version;
					url += "&product=EKP&createUrl=" + encodeURIComponent(createUrl + thirdMallCreateParam);
				}
				if (isNoAuth) {
					url = Com_Parameter.ContextPath + "sys/profile/index.jsp#integrate/saas/mall";
				}
			} else {
				var createUrl = Com_Parameter.ContextPath + $("[name='add_url']").val();
				//新建模板
				url = createUrl + "&mode=" + data.value;
			}
			if(Com_Parameter.dingXForm === "true") {
				//因为钉钉审批高级版后台页面最外层是moduleindex，与\sys\profile\index.jsp不同，frameWin[funcName]能找到对应方法，会导致新建模板页面在viewFrame中打开
				Com_OpenWindow(url, "_blank");
			} else {
				var win = Com_OpenWindow(url);
				if(win){
					Com_AddEventListener(win,"load",function(){
						Com_AddEventListener(win,"beforeunload",function(){
							setTimeout(function(){
								if($dialog){
									$dialog.hide(true);
								}
							},500);
						});
					})
				}
			}
		});
		return node;
	}
});

function hide() {
	var context = window.top.document;
	seajs.use(['lui/jquery'],function($){
		$("[data-lui-mark='dialog.nav.close']",context).trigger("click");
	});
}

var element = render.parent.element;

var isSuccess = (netWork_reachable == "true" && isAuth == "true");
var isNoAuth = (netWork_reachable == "true"  && isAuth == "false");
var isNoNetWork = (netWork_reachable == "false");

$(element).html("");
if (data == null || data.length == 0) {
	done();
} else {
	var wrap = $(element);
	var order = ["3","5","1","4"];
	for (var i = 0;i < order.length; i++) {
		var key = order[i];
		var name = data[key];
		if (name == null) {
			continue;
		}
		var modeInfo = {value:key};
		var desc = "";
		if (key == "1") {
			name = modelLang["rtfTitle"];
		} else if (key == "3") {
			name = modelLang["xformTitle"];
			// desc = modelLang["xformDesc"];
		} else if (key == "4") {
			name = modelLang["multiXformTitle"];
		} else if (key == "5") {
			name = modelLang["wordTitle"];
		} 
		modeInfo.name = name;
		modeInfo.desc = desc;
		var node = buildNode(modeInfo);
		node.appendTo(wrap);
	}
	//根据扩展点创建更多
	if (extensions != null) {
		extensions = JSON.parse(extensions);
		var reuseItem = {};
		for (var i = 0; i < extensions.length; i++) {
			var ext = extensions[i];
			if (ext["sourceUUID"] == "Reuse") {
				if (isSuccess) {
					reuseItem = {"name": modelLang["createByTemplateCenter"],"value":"thirdMall-success",
							"desc":modelLang["createByTemplateCenterDesc"],"url":ext["moreURL"]};
				} else if (isNoNetWork) {
					reuseItem = {"name":modelLang["noNetWork"],"value":"thirdMall-noNetWork","desc":modelLang["noNetWorkDesc"]};
				} else if (isNoAuth){
					reuseItem = {"name":modelLang["noAuth"],"value":"thirdMall-noAuth","desc":""};
				}
				var node = buildNode(reuseItem);
				node.appendTo(wrap);
			}
		}
	}
}
