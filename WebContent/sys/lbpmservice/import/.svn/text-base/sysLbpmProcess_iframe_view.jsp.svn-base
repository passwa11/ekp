<%@ page pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="content">
		<style>
			.lui_form_path_frame{
				display: none !important;
			}
			.com_qrcode {
				display:none!important;
			}
			.tempTB {
				width: 100% !important;
				max-width:100% !important;
			}
		</style>

		<%-- 流程 --%>
		<ui:tabpanel expand="false" var-navwidth="90%" collapsed="true">
			<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="${param.formName}" />
				<c:param name="fdKey" value="${param.fdKey}" />
			</c:import>
		</ui:tabpanel>
		<script>
			//流程审批提交
			window.Com_Submit = function(formObj, method, clearParameter) {
				if (!bCancel){
					if(formObj.onsubmit!=null && !formObj.onsubmit())
						return false;
					for(var i=0; i<Com_Parameter.event["submit"].length; i++)
						if(!Com_Parameter.event["submit"][i]())
							return false;
				}
				var i;
				var url = Com_CopyParameter(formObj.action);
				if(clearParameter!=null){
					clearParameter = clearParameter.split(":");
					for(i=0; i<clearParameter.length; i++)
						url = Com_SetUrlParameter(url, clearParameter[i], null);
				}
				if(method!=null)
					url = Com_SetUrlParameter(url, "method", method);
				var seq = parseInt(Com_GetUrlParameter(url, "s_seq"));
				seq = isNaN(seq)?1:seq+1;
				url = Com_SetUrlParameter(url, "s_seq", seq);
				formObj.action = url;
				var btns = document.getElementsByTagName("INPUT");
				for(i=0; i<btns.length; i++)
					if(btns[i].type=="button" || btns[i].type=="image")
						btns[i].disabled = true;
				btns = document.getElementsByTagName("A");
				for(i=0; i<btns.length; i++){
					btns[i].disabled = true;
					btns[i].removeAttribute("href");
					btns[i].onclick = null;
				}
				Ajax_Submit(formObj, method);
				return true;
			}

			/**
			 * 异步提交form
			 */
			function Ajax_Submit(formObj, method, clearParameter){
				var url = formObj.action;
				var data = $(formObj).serialize();
				$.ajax({
					url : url,
					type : "POST",
					data : data,
					//dataType : "form",
					success : function() {
						//通知上层窗口
						lbpmSubmitNotify();
					}
				});
			}

			//特权人操作页面回调
			window.lbpm.globals.popupWindow = function(url, width, height, param) {
				var left = (screen.width - width) / 2;
				var top = (screen.height - height) / 2;
				var isWebKit = navigator.userAgent.indexOf('AppleWebKit') != -1;
				var isSafari = navigator.userAgent.indexOf('Safari') > -1 && navigator.userAgent.indexOf('Chrome') == -1;
				if ((window.showModalDialog && !isWebKit) || isSafari) {
					var winStyle = "resizable:1;scroll:1;dialogwidth:" + width
							+ "px;dialogheight:" + height + "px;dialogleft:" + left
							+ ";dialogtop:" + top;
					var rtnVal = window.showModalDialog(url, param, winStyle);
					if (param.AfterShow)
						param.AfterShow(rtnVal);
				} else {
					var winStyle = "resizable=1,scrollbars=1,width=" + width + ",height="
							+ height + ",left=" + left + ",top=" + top
							+ ",dependent=yes,alwaysRaised=1";
					Com_Parameter.Dialog = param;
					var tmpwin = window.open(url, "_blank", winStyle);
					if (tmpwin) {
						tmpwin.onbeforeunload = function() {
							//通知上层窗口
							lbpmSubmitNotify();
						}
					}
				}
			}

			/**
			 * 通知上层窗口
			 */
			window.lbpmSubmitNotify = function () {
				if (parent) {
					parent.postMessage({"event": "lbpmSubmitSuccess"}, "*");
				}
			}
		</script>
	</template:replace>
</template:include>