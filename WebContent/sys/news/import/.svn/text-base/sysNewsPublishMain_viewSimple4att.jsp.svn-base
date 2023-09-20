<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%> 
<c:if test="${sysNewsPublishMainForm.fdIsShow||docForm.docStatusFirstDigit>=3}">
				<kmss:auth requestURL="${sysNewsPublishUrl}" >
					<script language="JavaScript">
						function SNP_att_publish() {
							seajs.use([ 'lui/dialog','lang!sys-news:news.tree.publishNews'], function(dialog,lang) {
							 var url=Com_GetCurDnsHost()+"${KMSS_Parameter_ContextPath}sys/news/sys_news_publish_Att/sysNewsPublishAtt.jsp?&contentAttKey=${JsParam.contentAttKey}&attKey=${JsParam.attKey}&fdKey=${sysNewsPublishMainForm.fdKey}&fdModelId=${sysNewsPublishMainForm.fdModelId}&fdModelName=${sysNewsPublishMainForm.fdModelName}";
							 dialog.iframe(url,"${lfn:message('sys-news:sysNewsPublishMain.button.push')}",null,{width:700,height :350}); 
						  });
					   }
					</script> 
					<ui:button parentId="toolbar" text="${lfn:message('sys-news:sysNewsPublishMain.button.push')}"
						onclick="SNP_att_publish();" order="3">
			        </ui:button>
				</kmss:auth>
			</c:if>
