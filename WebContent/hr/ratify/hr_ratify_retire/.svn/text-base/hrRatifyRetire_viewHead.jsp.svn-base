<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="head">
	<style type="text/css">
                
		.lui_paragraph_title{
	   		font-size: 15px;
	  		color: #15a4fa;
			padding: 15px 0px 5px 0px;
	  	}
		.lui_paragraph_title span{
	   		display: inline-block;
	   		margin: -2px 5px 0px 0px;
		}
		.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
	   		border: 0px;
	   		color: #868686
	  	}
	   		
	</style>
 	<script type="text/javascript">
   		var formInitData = {

        };
        var messageInfo = {

        };
        Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    </script>
</template:replace>
<template:replace name="title">
	<c:out value="${hrRatifyRetireForm.docSubject} - " />
	<c:out value="${ lfn:message('hr-ratify:table.hrRatifyRetire') }" />
</template:replace>
<template:replace name="toolbar">
	<script>
                function deleteDoc(delUrl) {
                	Com_Delete_Get(delUrl, 'com.landray.kmss.hr.ratify.model.HrRatifyRetire');
                }

                function openWindowViaDynamicForm(popurl, params, target) {
                    var form = document.createElement('form');
                    if(form) {
                        try {
                            target = !target ? '_blank' : target;
                            form.style = "display:none;";
                            form.method = 'post';
                            form.action = popurl;
                            form.target = target;
                            if(params) {
                                for(var key in params) {
                                    var
                                    v = params[key];
                                    var vt = typeof
                                    v;
                                    var hdn = document.createElement('input');
                                    hdn.type = 'hidden';
                                    hdn.name = key;
                                    if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                                        hdn.value =
                                        v +'';
                                    } else {
                                        if($.isArray(
                                            v)) {
                                            hdn.value =
                                            v.join(';');
                                        } else {
                                            hdn.value = toString(
                                                v);
                                        }
                                    }
                                    form.appendChild(hdn);
                                }
                            }
                            document.body.appendChild(form);
                            form.submit();
                        } finally {
                            document.body.removeChild(form);
                        }
                    }
                }

                function doCustomOpt(fdId, optCode) {
                    if(!fdId || !optCode) {
                        return;
                    }

                    if(viewOption.customOpts && viewOption.customOpts[optCode]) {
                        var param = {
                            "List_Selected_Count": 1
                        };
                        var argsObject = viewOption.customOpts[optCode];
                        if(argsObject.popup == 'true') {
                            var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                            for(var arg in argsObject) {
                                param[arg] = argsObject[arg];
                            }
                            openWindowViaDynamicForm(popurl, param, '_self');
                            return;
                        }
                        var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
                        Com_OpenWindow(optAction, '_self');
                    }
                }
                window.doCustomOpt = doCustomOpt;
                var viewOption = {
                    contextPath: '${LUI_ContextPath}',
                    basePath: '/hr/ratify/hr_ratify_retire/hrRatifyRetire.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                function printDoc() {
                    var url = '${LUI_ContextPath}/hr/ratify/hr_ratify_retire/hrRatifyRetire.do?method=print&fdId=${param.fdId}';
                    Com_OpenWindow(url);
                }
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
	<c:if test="${hrRatifyRetireForm.docDeleteFlag ==1}">
		<div id="toolbar" style="display:none"></div>
	</c:if>
	<c:if test="${hrRatifyRetireForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="90%" style="display:none;">
		<c:if test="${ hrRatifyRetireForm.docStatus=='10' || hrRatifyRetireForm.docStatus=='11' || hrRatifyRetireForm.docStatus=='20' }">
	        <!--edit-->
	        <kmss:auth requestURL="/hr/ratify/hr_ratify_retire/hrRatifyRetire.do?method=edit&fdId=${param.fdId}">
	            <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('hrRatifyRetire.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
	        </kmss:auth>
	    </c:if>
   		<!-- ============================================================================= -->          
        <c:import url="/hr/ratify/import/feedback.jsp" charEncoding="UTF-8"></c:import>
        <c:if test="${hrRatifyRetireForm.docStatus=='30' || hrRatifyRetireForm.docStatus=='31'}">
			<!-- 实施反馈 -->
			<kmss:auth requestURL="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do?method=add&fdDocId=${param.fdId}&fdCreatorId=${hrRatifyRetireForm.docCreatorId}" requestMethod="GET">
				<ui:button text="实施反馈" onclick="feedback('${hrRatifyRetireForm.docCreatorId}')" order="4" />
     		</kmss:auth>
     		<c:if test="${hrRatifyRetireForm.fdFeedbackExecuted!='1'}">
				<kmss:auth requestURL="/hr/ratify/hr_ratify_main/hrRatifyChangeFeedback.jsp?fdId=${param.fdId}" requestMethod="GET">
					<!-- 指定反馈人 -->
					<ui:button order="4" text="指定反馈人" 
						onclick="appointFeedback();">
					</ui:button>
				</kmss:auth>
			</c:if>
		</c:if>
				
		<!-- ============================================================================= -->
                
		<!--delete-->
       	<kmss:auth requestURL="/hr/ratify/hr_ratify_retire/hrRatifyRetire.do?method=delete&fdId=${param.fdId}">
     		<ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('hrRatifyRetire.do?method=delete&fdId=${param.fdId}');" order="4" />
        </kmss:auth>
      	<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />

        <kmss:auth requestURL="/hr/ratify/hr_ratify_retire/hrRatifyRetire.do?method=print&fdId=${param.fdId}">
            <ui:button text="${lfn:message('button.print')}" onclick="printDoc()">
            </ui:button>
        </kmss:auth>
        
        <!-- 归档 -->
        <c:if test="${(hrRatifyRetireForm.docStatus == '30' or hrRatifyRetireForm.docStatus == '31') and (empty hrRatifyRetireForm.fdIsFiling or !hrRatifyRetireForm.fdIsFiling)}">
			<c:import url="/sys/archives/include/sysArchivesFileButton.jsp" charEncoding="UTF-8">
				<c:param name="fdId" value="${param.fdId}" />
				<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyRetire" />
				<c:param name="serviceName" value="hrRatifyRetireService" />
				<c:param name="userSetting" value="true" />
				<c:param name="cateName" value="docTemplate" />
				<c:param name="moduleUrl" value="hr/ratify" />
			</c:import>
		</c:if>

	</ui:toolbar>
	</c:if>
</template:replace>
<template:replace name="path">
	 <ui:combin ref="menu.path.category">
				<ui:varParams moduleTitle="${ lfn:message('hr-ratify:table.hrRatifyRetire') }"
				    modulePath="/hr/ratify/" 
					modelName="com.landray.kmss.hr.ratify.model.HrRatifyTemplate"
				    autoFetch="false" 
				    extHash="j_path=/retire"
				    href="/hr/ratify/"
					categoryId="${hrRatifyRetireForm.docTemplateId}" />
			</ui:combin>
</template:replace>
