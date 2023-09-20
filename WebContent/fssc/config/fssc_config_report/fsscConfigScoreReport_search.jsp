<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String orgType = "ORG_TYPE_POSTORPERSON";
	if ("config".equals(request.getParameter("type"))) {
		orgType += "|ORG_TYPE_DEPT";
	}
	request.setAttribute("orgType", orgType);
%>
<template:include ref="default.edit">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("security.js|common.js");
			Com_IncludeFile("domain.js");
			Com_IncludeFile("form.js");
			Com_IncludeFile("form.js");
			Com_IncludeFile("config_edit.js",
					"${LUI_ContextPath}/fssc/config/resource/js/", 'js', true);
			Com_IncludeFile("form_option.js",
					"${LUI_ContextPath}/fssc/config/fssc_config_score/", 'js',
					true);
			Com_IncludeFile("fsscConfigReport.js",
					"${LUI_ContextPath}/fssc/config/fssc_config_report/", 'js',
					true);
		</script>
		<script>
			var validation = $KMSSValidation();
			if (document.addEventListener) {//如果是Firefox      
				document.addEventListener("keypress", otherHandler, true);
			} else {
				document.attachEvent("onkeypress", ieHandler);
			}
			function otherHandler(evt) {
				if (evt.keyCode == 13) {
					FS_Search();
				}
			}
			function ieHandler(evt) {
				if (evt.keyCode == 13) {
					FS_Search();
				}
			}
		</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/fssc/config/fssc_config_score/fsscConfigScore.do"
			method="get" target="searchIframe">
			<center>
				<p class="txttitle">
				<p class="txttitle">
					<bean:message bundle="fssc-config" key="score.report" />
				</p>
				</p>
				<table class="tb_normal" style="width: 100%">
					<tr>
						<td style="width: 20%;" class="td_normal_title">
							${lfn:message('fssc-config:fsscConfigScoreDetail.fdAddScorePerson')}
						</td>
						<td style="width: 70%;"><xform:address showStatus="edit"
								propertyName="fdAddScorePersonName"
								propertyId="fdAddScorePersonId" orgType="ORG_TYPE_PERSON"
								style="width:75%;" ></xform:address>
						</td>
					</tr>

					<tr>
						<td style="width: 20%;" class="td_normal_title">
							${lfn:message('fssc-config:fsscConfigScoreDetail.docCreateTime')}
						</td>
						<td style="width: 70%;">
						 <xform:datetime property="docCreateTimeStart" showStatus="edit" dateTimeType="date" style="width:35%;" />
						 <xform:datetime property="docCreateTimeEnd" showStatus="edit" dateTimeType="date" style="width:35%;" />
						 
						</td>
					</tr>
					
				</table>
				<br>
				<ui:button text="${ lfn:message('button.search') }"
					onclick="FSSC_Search_Score('report');" />
				<ui:button text="${ lfn:message('button.reset') }"
					onclick="FSSC_Reset();" />
				<ui:button text="${ lfn:message('button.export') }"
					onclick="FSSC_Search_Score('exportReport');" />
			</center>
		</html:form>
		<div>
			<iframe src="" name="searchIframe" id="searchIframe" align="top"
				onload="this.height=searchIframe.document.body.scrollHeight;"
				width="97%" Frameborder=No Border=0 Marginwidth=0 Marginheight=0
				Scrolling=No> </iframe>
		</div>
	</template:replace>
</template:include>
