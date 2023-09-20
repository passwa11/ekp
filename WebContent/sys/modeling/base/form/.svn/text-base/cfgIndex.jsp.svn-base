<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">

    	<link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<div id="mainContent" class="lui_list_mainContent fullscreen" style="margin: 0;padding: 0;">
			<div class="lui_modeling">
			    <div class="lui_modeling_aside" >
			        <div data-lui-type="lui/base!DataView" style="display:none;">
			            <ui:source type="Static">
			                [{
			                    "text" : "${lfn:message('sys-modeling-base:table.sysFormTemplate')}",
			                    "iframeId":"cfg_iframe",
			                    "selected":"true",
			                    "src" :  "sys/modeling/base/formLang.do?method=edit&fdModelId=${param.fdAppModelId}"
			                },
			                {
			                    "text" : "${lfn:message('sys-modeling-base:table.modelingAppDataValidate')}",
			                    "iframeId":"cfg_iframe",
			                    "src" :  "sys/modeling/base/dataValidate/index.jsp?fdModelId=${param.fdAppModelId}"
			                }
							, {
								"text" : "${lfn:message('sys-modeling-base:table.modelingExternalQuery')}",
								"iframeId":"cfg_iframe",
								"src" :  "sys/modeling/base/externalQuery.do?method=edit&fdModelId=${param.fdAppModelId}"
							}
							<c:if test="${param.enableFlow == false }">
								, {
								"text" : "${lfn:message('sys-modeling-base:table.modelingExternalShare')}",
								"iframeId":"cfg_iframe",
								"src" :  "sys/modeling/base/externalShare.do?method=edit&fdModelId=${param.fdAppModelId}"
								}
							</c:if>
							<c:if test="${param.enableFlow == true }">
								, {
								"text" : "${lfn:message('sys-modeling-base:table.modelingAutomaticFill')}",
								"iframeId":"cfg_iframe",
								"src" :  "sys/modeling/base/automaticFill/index.jsp?fdModelId=${param.fdAppModelId}"
								}
							</c:if>
							]
			            </ui:source>
			            <ui:render type="Javascript">
			                <c:import url="/sys/modeling/base/resources/js/menu_side.js" charEncoding="UTF-8"></c:import>
			            </ui:render>
			        </div>
			    </div>
			
			    <div class="lui_modeling_main aside_main" >
			        <iframe id="cfg_iframe" class="lui_modeling_iframe_body"  frameborder="no" border="0" src="${LUI_ContextPath}/sys/modeling/base/formLang.do?method=edit&fdModelId=${param.fdAppModelId}"></iframe>
			    </div>
			
			</div>
		</div>
	</template:replace>
</template:include>