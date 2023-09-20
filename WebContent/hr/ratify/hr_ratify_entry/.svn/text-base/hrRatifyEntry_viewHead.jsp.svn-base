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
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}"/>
 	<script type="text/javascript">
   		var formInitData = {

        };
        var messageInfo = {

        };
        Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    </script>
</template:replace>
<template:replace name="title">
	<c:out value="${hrRatifyEntryForm.docSubject} - " />
	<c:out value="${ lfn:message('hr-ratify:table.hrRatifyEntry') }" />
</template:replace>
<template:replace name="toolbar">
	<script>
	    function deleteDoc(delUrl) {
	    	Com_Delete_Get(delUrl, 'com.landray.kmss.hr.ratify.model.HrRatifyEntry');
	    }
		seajs.use(['lui/dialog'],function(dialog){
			window.addOrgPerson = function(){
				var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+'hr/ratify/hr_ratify_entry_dr/hrRatifyEntryDR.do?method=addOrgPerson&fdEntryId=${param.fdId}';
			    dialog.iframe(url,'<bean:message bundle="hr-ratify" key="hrRatifyEntry.addOrgPerson"/>',function(rtn){
			    	 if(typeof rtn == 'undefined' || (rtn != null && rtn != "cancel")){
			    	    location.reload();
			    	 }
				},{width:850,height:500});
			}
		});
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
            basePath: '/hr/ratify/hr_ratify_entry/hrRatifyEntry.do',
            customOpts: {

                ____fork__: 0
            }
        };

        function printDoc() {
            var url = '${LUI_ContextPath}/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=print&fdId=${param.fdId}';
            Com_OpenWindow(url);
        }
        Com_IncludeFile("security.js");
        Com_IncludeFile("domain.js");
	</script>
	<c:if test="${hrRatifyEntryForm.docDeleteFlag ==1}">
		<div id="toolbar" style="display:none"></div>
	</c:if>
	<c:if test="${hrRatifyEntryForm.docDeleteFlag !=1}">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="90%" style="display:none;">
		<c:set var="addOrgPerson" value="false" scope="request"></c:set>
		<c:if test="${hrRatifyEntryForm.sysWfBusinessForm.fdNodeAdditionalInfo.addOrgPerson =='true'}">
			<c:set var="addOrgPerson" value="true" scope="request"></c:set>
		</c:if>
		<c:if test="${addOrgPerson eq 'true' and empty hrRatifyEntryForm.fdHasWrite}">
			<ui:button text="${lfn:message('hr-ratify:hrRatifyEntry.addOrgPerson')}" onclick="addOrgPerson();" order="1" />
		</c:if>
		<c:if test="${ hrRatifyEntryForm.docStatus=='10' || hrRatifyEntryForm.docStatus=='11' || hrRatifyEntryForm.docStatus=='20' }">
	    	<!--edit-->
	      	<kmss:auth requestURL="/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=edit&fdId=${param.fdId}">
	    		<ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('hrRatifyEntry.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
	    	</kmss:auth>
	  	</c:if>
   		<!-- ============================================================================= -->          
        <c:import url="/hr/ratify/import/feedback.jsp" charEncoding="UTF-8"></c:import>
        <c:if test="${hrRatifyEntryForm.docStatus=='30' || hrRatifyEntryForm.docStatus=='31'}">
			<!-- 实施反馈 -->
			<kmss:auth requestURL="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do?method=add&fdDocId=${param.fdId}&fdCreatorId=${hrRatifyEntryForm.docCreatorId}" requestMethod="GET">
				<ui:button text="实施反馈" onclick="feedback('${hrRatifyEntryForm.docCreatorId}')" order="4" />
     		</kmss:auth>
     		<c:if test="${hrRatifyEntryForm.fdFeedbackExecuted!='1'}">
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
        <kmss:auth requestURL="/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=delete&fdId=${param.fdId}">
            <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('hrRatifyEntry.do?method=delete&fdId=${param.fdId}');" order="4" />
        </kmss:auth>
        <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />

        <kmss:auth requestURL="/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=print&fdId=${param.fdId}">
            <ui:button text="${lfn:message('button.print')}" onclick="printDoc()">
            </ui:button>
        </kmss:auth>
        
        <!-- 归档 -->
        <c:if test="${(hrRatifyEntryForm.docStatus == '30' or hrRatifyEntryForm.docStatus == '31') and (empty hrRatifyEntryForm.fdIsFiling or !hrRatifyEntryForm.fdIsFiling)}">
			<c:import url="/sys/archives/include/sysArchivesFileButton.jsp" charEncoding="UTF-8">
				<c:param name="fdId" value="${param.fdId}" />
				<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifyEntry" />
				<c:param name="serviceName" value="hrRatifyEntryService" />
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
	<ui:varParams moduleTitle="${ lfn:message('hr-ratify:table.hrRatifyEntry') }"
   		modulePath="/hr/ratify/" 
		modelName="com.landray.kmss.hr.ratify.model.HrRatifyTemplate"
   		autoFetch="false" 
   		extHash="j_path=/entry"
   		href="/hr/ratify/"
		categoryId="${hrRatifyEntryForm.docTemplateId}" />
	</ui:combin>
</template:replace>
