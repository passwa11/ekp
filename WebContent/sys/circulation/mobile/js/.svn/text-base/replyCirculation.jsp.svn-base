<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<mui:cache-file name="mui-circulate.css" cacheType="md5"/>
<div data-dojo-type="mui/view/DocView" id="replyCirculationView" style="display: none"> 
	<div id="replyCirculationContent" class="muiCirculationReply">
		<html:form action="/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=updateOpinion&mobile=true&fdId=${JsParam.fdId}">
			<div data-dojo-type="mui/view/DocView" data-dojo-mixins="mui/form/_ValidateMixin" id="replyCirculationScrollView">
				<div class="muiFormContent">
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="muiReplyTitle"><bean:message bundle="sys-circulation" key="sysCirculationMain.fdOpinion" /></div>
								<html:hidden property="fdId" value="${JsParam.fdId}"/>
								<html:hidden property="sysCirculationMainId" value="${JsParam.sysCirculationMainId}"/>
								<html:hidden property="fdBelongPersonId" value="${JsParam.fdBelongPersonId}"/>
								<xform:textarea property="docContent" mobile="true" style="height:100px;width:96%" showStatus="edit" required="${JsParam.required}" subject="${ lfn:message('sys-circulation:sysCirculationMain.fdOpinion') }"></xform:textarea>
							</td>
						</tr>
						<tr>
							<td>
								<div class="muiReplyTitle" style="float:left;line-height:5rem"><bean:message bundle="sys-circulation" key="sysCirculationMain.attachment" /></div>
								<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="attachment" />
									<c:param name="fdMulti" value="true" />
									<c:param name="fdModelId" value="${JsParam.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.circulation.model.SysCirculationOpinion" />
									<c:param name="widgitId" value="replyCirculationAtt" />
								</c:import>
							</td>
						</tr>
					</table>
				</div>
				<br>
				<c:if test="${'1' eq JsParam.fdRegular}">
					<div class="nextInfo"><bean:message bundle="sys-circulation" key="sysCirculationMain.next" />:<span>${not empty JsParam.nextPerson?param.nextPerson:(lfn:message('sys-circulation:sysCirculationMain.end'))}</span></div>
					<div class="nextInfoTips"><bean:message bundle="sys-circulation" key="sysCirculationMain.tip" /></div>
				</c:if>
				<div data-dojo-type="dojox/mobile/View">
					<ul class="muiCirculationList"
				    	data-dojo-type="mui/list/JsonStoreList"
				    	data-dojo-mixins="${LUI_ContextPath }/sys/circulation/mobile/js/CirculationOpinionItemListNewVersionOptMixin.js"
				    	data-dojo-props="url:'/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=list&fdMainId=${JsParam.sysCirculationMainId}&isOpinion=true',lazy:false">
					</ul>
				</div>
			</div>
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
			  	<li data-dojo-type="sys/circulation/mobile/js/CirculationOperationButton" data-dojo-props="fdType:'replyCancel'">
					<bean:message key="button.cancel" />
				</li>
				<li data-dojo-type="sys/circulation/mobile/js/CirculationOperationButton" data-dojo-props="fdType:'replySubmit',fdOpinionId:'${JsParam.fdId}'" class="muiBtnDefault mainTabBarButton">
					<bean:message key="button.submit" />
				</li>
			</ul>
		</html:form>
	</div>
</div> 