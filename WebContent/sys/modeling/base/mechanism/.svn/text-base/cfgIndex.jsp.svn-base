<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/mechanism.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<div id="mainContent" class="lui_list_mainContent fullscreen" style="margin: 0;padding: 0;">
			<div class="lui_modeling">
				<div class="lui_modeling_aside" >
					<div data-lui-type="lui/base!DataView" style="display:none;">
						<ui:source type="Static">
							[{
							"text" : "${lfn:message('sys-modeling-base:table.mechanismNumber')}",
							"iframeId":"cfg_iframe",
							"selected":"true",
							"src" :  "sys/modeling/base/modelingAppModel.do?method=editNumber&fdId=${param.fdAppModelId}&enableFlow=${param.enableFlow}"
							},
							{
							"text" : "${lfn:message('sys-modeling-base:table.mechanismPrint')}",
							"iframeId":"cfg_iframe",
							"src" :  "sys/modeling/base/modelingAppModel.do?method=editPrint&fdId=${param.fdAppModelId }&enableFlow=${param.enableFlow}"
							},
							{
							"text" : "${lfn:message('sys-modeling-base:table.mechanismImport')}",
							"iframeId":"cfg_iframe",
							"src" :  "sys/modeling/base/modelingAppModel.do?method=editImport&fdId=${param.fdAppModelId}&enableFlow=${param.enableFlow}"
							}
							<kmss:ifModuleExist path="/km/archives/">
								,{
								"text" : "${lfn:message('sys-modeling-base:table.mechanismArchives')}",
								"iframeId":"cfg_iframe",
								"src" :  "sys/modeling/base/modelingAppModel.do?method=editArchives&fdId=${param.fdAppModelId}&enableFlow=${param.enableFlow}"
								}
							</kmss:ifModuleExist>
							<kmss:ifModuleExist path="/third/payment/">
								,{
								"text" : "${lfn:message('sys-modeling-base:table.mechanismPayment')}",
								"iframeId":"cfg_iframe",
								"src" :  "sys/modeling/base/modelingAppModel.do?method=editPayment&fdId=${param.fdAppModelId}&enableFlow=${param.enableFlow}"
								}
							</kmss:ifModuleExist>]
						</ui:source>
						<ui:render type="Javascript">
							<c:import url="/sys/modeling/base/resources/js/menu_side.js" charEncoding="UTF-8"/>
						</ui:render>
					</div>
				</div>

				<div class="lui_modeling_main aside_main" >
					<iframe id="cfg_iframe" class="lui_modeling_iframe_body" scrolling="yes" style="height: 800px" frameborder="no" border="0" src="${LUI_ContextPath}/sys/modeling/base/modelingAppModel.do?method=editNumber&fdId=${param.fdAppModelId}&enableFlow=${param.enableFlow}"></iframe>
				</div>

			</div>
		</div>
	</template:replace>
</template:include>