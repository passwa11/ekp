<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/listview.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css" rel="stylesheet">
<script>
	Com_IncludeFile("jquery.js");
</script>
<script>
	$(function(){
		init();
	});

	function init() {
		var jsonStr = "${checkSrategy}";
		var appAuthMap = "${appAuthMap}";
		if (!jsonStr)
			return;
		var json = JSON.parse(jsonStr);
		var html = "<div class=\"model-radios\">";
		//相同版本的应用
		if(json.sameVersion){
			html += buildAppHtml(json.sameVersion, 2,appAuthMap);
		}
		//不同版本的应用
		if(json.diffVersion){
			html += buildAppHtml(json.diffVersion, 1,appAuthMap);
		}
		//不存在的应用
		if(json.nonExistApp){
			html += buildAppHtml(json.nonExistApp, 0);
		}
		html += "</div>";
		$("#content").html(html);

		//绑定点击事件
		var items = $(".model-radios-item");
        var size = items.size()
		if (size > 1){
			$(".top-tip-content").show();
		}
		items.each(function () {
			var lis = $(this).find(".model-radios-opt-item");
			lis.each(function () {
				$(this).on("click", function () {
					liOnClick($(this), lis);
				});
			});
		});
	}

	/**
	 * 选项点击事件
	 */
	function liOnClick(li, lis){
		var value = li.find("input[name^='List_Selected_']:first").val();
		lis.each(function () {
			var radio = $(this).find("input[name^='List_Selected_']:first");
			if(radio.val() === value){
				radio[0].checked=true;
				$(this).addClass("active");
			} else {
				radio[0].checked=false;
				$(this).removeClass("active");
			}
		});
	}

	function buildAppHtml(json, type,appAuthMap){
		if(appAuthMap){
			var appAuthMapObj = JSON.parse(appAuthMap);
		}
		var html = "";
		//选项
		for (i in json) {
			var fdId = json[i].fdId;
			var fdAppName = json[i].fdAppName;
			var fdVersionText = json[i].fdVersionText;
			var fdNewVersionText = json[i].fdNewVersionText;
			var sourceAppLicenseType = json[i].sourceAppLicenseType;
			var appStatus = json[i].appStatus;

			var auth = true;
			if(appAuthMap){
				for(var item in appAuthMapObj){ 
					if(item == fdId){
						auth = appAuthMapObj[item];
						break;
					}
				}
			}
			//试用期，已发布，是可维护者，相同版本导入
			if (sourceAppLicenseType && "0" == sourceAppLicenseType && appStatus == "00" && auth && type ==2){
				html += "<div class=\"model-radios-item\"><p class=\"model-radios-title\">【" + fdAppName + "】${lfn:message("sys-modeling-base:modeling.same.publish.app.canont.overwrite")}</p></div>";
				if (json.length ==1){
				window.parent.document.getElementsByClassName("lui_dialog_content")[0].setAttribute('style', 'height: 200px;');
				}
			}else if(sourceAppLicenseType && "0" == sourceAppLicenseType && !auth && type ==2){
				html += "<div class=\"model-radios-item\"><p class=\"model-radios-title\">【" + fdAppName + "】${lfn:message("sys-modeling-base:modeling.cannot.edit.app.canont.overwrite")}</p></div>";
				if (json.length ==1) {
					window.parent.document.getElementsByClassName("lui_dialog_content")[0].setAttribute('style', 'height: 200px;');
				}
			}else if(sourceAppLicenseType && "0" == sourceAppLicenseType && type ==2){//相同版本未激活
				html += "<div class=\"model-radios-item\"><p class=\"model-radios-title\">" + buildTitleHtml(fdAppName, fdVersionText, type) + "</p><ul class=\"model-radios-opt\">";
				html += buildRadioHtml(fdId, fdNewVersionText, "21",auth,appStatus);

			}else if(sourceAppLicenseType && "0" == sourceAppLicenseType && type ==1){//不同版本未激活
				html += "<div class=\"model-radios-item\"><p class=\"model-radios-title\">" + buildTitleHtml(fdAppName, fdVersionText, type) + "</p><ul class=\"model-radios-opt\">";
				html += buildRadioHtml(fdId, fdNewVersionText, "11",auth,appStatus);

			}else{
				html += "<div class=\"model-radios-item\"><p class=\"model-radios-title\">" + buildTitleHtml(fdAppName, fdVersionText, type) + "</p><ul class=\"model-radios-opt\">";
				html += buildRadioHtml(fdId, fdNewVersionText, type,auth,appStatus);
			}


		}
		return html;
	}


	function buildTitleHtml(fdAppName, fdVersionText, type){
		var title = "";
		if(type == 2){
			title = "【" + fdAppName + "】${lfn:message("sys-modeling-base:modeling.current.list.has.version")}" + fdVersionText + "${lfn:message("sys-modeling-base:modeling.Consistent.select.import.method")}";
		} else if (type == 1) {
			title = "【" + fdAppName + "】${lfn:message("sys-modeling-base:modeling.exists.version.app")}" + fdVersionText + "${lfn:message("sys-modeling-base:modeling.Inconsistent.select.import.method")}";
		} else if (type == 0) {
			title = "【" + fdAppName + "】${lfn:message("sys-modeling-base:modeling.select.import.method")}";
		}
		return title;
	}

	function buildRadioHtml(fdId, fdNewVersionText, type,auth,appStatus){
		var html = "";
		if(type == 0) {
			html += "<li class=\"model-radios-opt-item active\"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"0\" checked><i></i></div><p>${lfn:message("sys-modeling-base:modeling.generate.new.application")}</p></label></li>";
		}else if(!auth){
			//应用已存在，并且用户没有权限维护，只能生成新应用
			html += "<li class=\"model-radios-opt-item active\"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"1\" checked><i></i></div><p>${lfn:message("sys-modeling-base:modeling.generate.new.application")}</p></label></li>";
		}else if(type == 1){
			//应用已存在，则建新应用的type值为1
			html += "<li class=\"model-radios-opt-item active\"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"2\" checked><i></i></div><p>${lfn:message("sys-modeling-base:modeling.generate.new.version")}" + fdNewVersionText + "</p></label></li>";
			html += "<li class=\"model-radios-opt-item\"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"1\"><i></i></div><p>${lfn:message("sys-modeling-base:modeling.generate.new.application")}</p></label></li>";
		}else if(type == 11){
			//应用已存在不同版本，但这已存在的不同版本的应用是未激活
			html += "<li class=\"model-radios-opt-item active\"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"0\" checked><i></i></div><p>${lfn:message("sys-modeling-base:modeling.generate.new.application")}</p></label></li>";
		}else if(type == 2){
			html += "<li class=\"model-radios-opt-item active\"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"2\" checked><i></i></div><p>${lfn:message("sys-modeling-base:modeling.generate.new.version")}" + fdNewVersionText + "</p></label></li>";
			html += "<li class=\"model-radios-opt-item\"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"1\"><i></i></div><p>${lfn:message("sys-modeling-base:modeling.generate.new.application")}</p></label></li>";
			//相同版本应用下，发布的应用不能覆盖
			if (appStatus != "00"){
				html += "<li class=\"model-radios-opt-item \"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"3\" ><i></i></div><p>${lfn:message("sys-modeling-base:modeling.overwrite.current.version")}</p></label></li>";
			}
		}else if(type == 21){
			//应用已存在，但是原应用为试用版
			html += "<li class=\"model-radios-opt-item active\"><label><div><input type=\"radio\" name=\"List_Selected_" + fdId + "\" value=\"3\" checked><i></i></div><p>${lfn:message("sys-modeling-base:modeling.overwrite.current.version")}</p></label></li>";
		}
		html += "</ul></div>";
		return html;
	}

	function startImportWithStrategy(){
		var values = [];
		$("input[name^='List_Selected_']:checked").each(function(){
			var key = $(this).attr("name");
			key = key.replace('List_Selected_', '');
			var value = $(this).val();
			values.push(key + "_" + value);
		});
		if (values.length ==0){
			$dialog.hide();
		}
		var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingDatainitMain.do?method=startImportWithStrategy&" + $.param({"strategys":values}, true);
		Com_OpenWindow(url, "_self");
	}
	function del_tips(){
		$(".top-tip-content").hide();
	}


</script>
<div class="model-mask-panel" id="contents"style="height: 290px;overflow: auto">
	<div class="top-tip-content" style="display: none">
		<span class="top-tips"></span>
		<p style="margin-left: 40px;">${lfn:message('sys-modeling-base:modeling.datainit.top.tips')}</p>
		<span class="del-tips" onclick="del_tips()"></span>
	</div>
	<div id="content"></div>
</div>
<div class="toolbar-bottom">
	<div class="precaution" style="line-height: 60px;">
		<span class="bottom-tips"></span>
		<span>${lfn:message('sys-modeling-base:modeling.datainit.bottom.tips')}</span>
	</div>
	<ui:button target="_self" onclick="$dialog.hide()" styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-base:modeling.Cancel')}"/>
	<ui:button onclick="startImportWithStrategy();"  text="${lfn:message('sys-modeling-base:modeling.button.ok')}"/>
</div>
<%@ include file="/resource/jsp/view_down.jsp"%>
