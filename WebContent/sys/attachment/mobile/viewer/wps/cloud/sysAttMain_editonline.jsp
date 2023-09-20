<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.view">
	<template:replace name="head">
		<style>
			.wpsCloudView: {
				width:100%;
				height:100%;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<div id="WPSCLOUD_${sysAttMainForm.fdKey}">
		</div>
		<script src="${LUI_ContextPath}/sys/attachment/mobile/viewer/wps/js/web-office-sdk-1.1.1.umd.js"></script>
		<script>
			var fdAttMainId ='${sysAttMainForm.fdId}';
			var fdKey = '${sysAttMainForm.fdKey}';
			var fdModelId = '${sysAttMainForm.fdModelId}';
			var fdModelName = '${sysAttMainForm.fdModelName}';
			var fdFileName = '${sysAttMainForm.fdFileName}';
			require(["sys/attachment/mobile/viewer/wps/js/WpsCloudAattachment","mui/dialog/Tip", "dojo/domReady!" ], function(WpsCloudAattachment,tip) {
				console.log(WpsCloudAattachment);
				var wpsCloudAattachment = new WpsCloudAattachment({
					fdId: fdAttMainId,
					fdKey: fdKey,
					fdModelId: fdModelId,
					fdModelName: fdModelName,
					fdMode: "write",
					fdFileName:fdFileName
				});
				wpsCloudAattachment.load();

				window.wps_submit=function(){
					wpsCloudAattachment.submit(wps_submit_callBack);
				}

				window.wps_submit_callBack=function(data){
					if(data=="ok"){
						tip.success({text:"提交成功"});
					}
					if(data=="error"){
						tip.fail({text:"提交失败"});
					}
					if(data=="nochange"){
						tip.warn({text:"文件没有修改"});
					}
				}
			});
		</script>

		<div>
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
				<li data-dojo-type="mui/tabbar/TabBarButton"
					class="muiBtnSubmit"
					onclick="wps_submit()">${lfn:message('button.submit')}</li>
			</ul>
		</div>
	</template:replace>
</template:include>	