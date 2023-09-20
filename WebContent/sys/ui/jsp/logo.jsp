<%@page import="com.landray.kmss.sys.ui.plugin.SysUiTools"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">选择Logo</template:replace>
	<template:replace name="head">
		<template:super/>
		<style type="text/css">
			html,body {
				height: 100%;
			}
			.logo {
				float: left;
				padding: 5px 1%;
				cursor: pointer;
				width:31%;
				height: 90px;
				overflow: hidden;
			}
		</style>
		<script>
			function onLogoClick(title){
				window.$dialog.hide(title);
			}
			LUI.ready(function(){
				//下面这段代码是给上传logo的Form的action地址加上model如果存在的model参数的话，以便让调用logo.jsp的模块控制权限
				var modelName="${JsParam.modelName }";
				if (modelName && modelName!=null){
				  var $uiLogoForm = $("form[name=sysUiLogoForm]");
				  if ($uiLogoForm){
					  var url=$uiLogoForm.attr("action");
					  url=encodeURI((url.indexOf("?")>-1)?url+"&modelName="+modelName : url+"?modelName="+modelName);
					  $uiLogoForm.attr("action",url);
				  }
				}
			});
			seajs.use(['lui/jquery', 'lui/dialog'], function($,dialog) {
				window.onCloseOver = function(obj){
					$(obj).addClass('lui_icon_on');
				}
				window.onCloseOut = function(obj){
					$(obj).removeClass('lui_icon_on');
				}
				window.deleteLogo = function(obj,fileName){
					if(!fileName)
						return;
					dialog.confirm('<bean:message bundle="sys-ui" key="ui.portlet.delLogo.tip"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: '${ LUI_ContextPath }/sys/ui/sys_ui_logo/sysUiLogo.do?method=deleteLogo',
								type: 'POST',
								data:{fileName:fileName},
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.failure('<bean:message key="return.optFailure" />');
								},
								success: function(data){
									if(window.del_load!=null){
										window.del_load.hide();
									}
									if(data!=null && data.code==1){
										dialog.success('<bean:message key="return.optSuccess" />');
										$(obj).parent().parent().remove();
									}else if(data!=null && data.code==2){
										dialog.failure('<bean:message bundle="sys-ui" key="ui.portlet.delLogo.error" />');
									}else{
										dialog.failure('<bean:message key="return.optFailure" />');
									}
								}
						   });
						}
					});
				};
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<%
		List<String> logos = SysUiTools.scanLogoPath();
		request.setAttribute("logos", logos);
		%>
		<div style="height: 100%;width:100%; overflow: hidden;">		
			<div style="height: 300px;overflow: auto;">
				<div class="logo">
					 <div style="background: #C78700;"><img src="${ LUI_ContextPath }/resource/images/logo.png" title="/resource/images/logo.png"  onclick="onLogoClick(this.title);" width="149" style="vertical-align:top;" /></div>
				</div> 
				<c:forEach items="${logos}" var="logo">
					 <div class="logo">
					 	<div style="background: #C78700;position:relative;">
						 	<img src="${ LUI_ContextPath }${ logo }" title="${ logo }"  onclick="onLogoClick(this.title);" width="149" style="vertical-align:top;" />
						 	<div onmouseout="onCloseOut(this)" onmouseover="onCloseOver(this)" onclick="deleteLogo(this,'${logo}')" class="lui_icon_s" style="position: absolute;right: -6px;top: -6px;padding: 2px;"><div class="lui_icon_s lui_icon_s_icon_close_red"></div></div>
					 	</div>
					 </div> 
				</c:forEach>
			</div>	
			<div style="color: red;height:20px;">
		 		${ errorMessage }
		 		</div>			
			<html:form enctype="multipart/form-data" style="padding:5px;" action="/sys/ui/sys_ui_logo/sysUiLogo.do" method="post">  
				<input type="file" style="width: 350px;" name="file" />
		 		<input type="submit" value="上传" style="width: 115px;" />
		 		<input type="hidden" name="method" value="upload" />
		 		
			</html:form>
		</div>
	</template:replace>
</template:include>