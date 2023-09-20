<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<mui:cache-file name="mui-circulate.css" cacheType="md5"/>
<div data-dojo-type="mui/view/DocView" id="circulationView" style="display: none"> 
	<div id="circulationContent" class="muiCirculation">
		<html:form action="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=saveCirculate">
			<div data-dojo-type="sys/circulation/mobile/js/CirculationView" data-dojo-mixins="mui/form/_ValidateMixin" id="circulationScrollView">
				<div class="muiFormContent">
					<html:hidden property="fdId" />
					<html:hidden property="fdFromParentId" value="${JsParam.fdFromParentId}"/>
					<html:hidden property="fdFromOpinionId" value="${JsParam.fdFromOpinionId}"/>
					<html:hidden property="fdModelId" value="${JsParam.modelId}"/>
					<html:hidden property="fdModelName" value="${JsParam.modelName}"/>
				</div>
				<table class="muiSimple headTb" cellpadding="0" cellspacing="0" >
					<tr>
						<td class="muiTitle">
							<bean:message bundle="sys-circulation" key="table.sysCirculationCirculors" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<span style="color: #AAACB7"><bean:message bundle="sys-circulation" key="sysCirculationMain.receivedCirCulatorTip" /></span>
							<c:choose>
								<c:when test="${JsParam.fdSpreadScope eq '1'}">
									<div style="margin-top: 0.5rem">
										<xform:address mobile="true" showStatus="edit" otherProperties="scope:'22'" propertyId="receivedCirCulatorIds"  propertyName="receivedCirCulatorNames"  mulSelect="true" textarea="true"
											orgType="ORG_TYPE_PERSON" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
											style="width:95%" >
										</xform:address>
									</div>
								</c:when>
								<c:when test="${JsParam.fdSpreadScope eq '0'}">
									<div style="margin-top: 0.5rem">
										<xform:address  mobile="true" showStatus="edit" otherProperties="scope:'33'"  propertyId="receivedCirCulatorIds"  propertyName="receivedCirCulatorNames"  mulSelect="true" textarea="true"
											orgType="ORG_TYPE_PERSON" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
											style="width:95%" >
										</xform:address>
									</div>
								</c:when>
								<c:otherwise>
									<div style="margin-top: 0.5rem">
										<div data-dojo-type="sys/circulation/mobile/js/CirculationOtherScope" 
											data-dojo-props="idField:'receivedCirCulatorIds',nameField:'receivedCirCulatorNames',fdModelName:'${JsParam.modelName}',orgIds:'${JsParam.fdOtherSpreadScopeIds}',required:true,subject:'${ lfn:message('sys-circulation:table.sysCirculationCirculors') }'">
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdOpinionRequired" />
						</td>
						<td class="muiRightTd">
							<div data-dojo-type="mui/form/switch/NewSwitch" data-dojo-props="property:'fdOpinionRequired',showStatus:'edit',leftLabel:'',rightLabel:'', value:'off'"></div>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdNotifyType" />
						</td>
						<td class="muiRightTd">
							<kmss:editNotifyType property="fdNotifyType" mobile="true"/>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdExpireTime" />
						</td>
						<td class="muiRightTd">
							<xform:datetime property="fdExpireTime" showStatus="edit" mobile="true" dateTimeType="datetime" style="width:50%" validators="after" subject="${ lfn:message('sys-circulation:sysCirculationMain.fdExpireTime') }"></xform:datetime>
						</td>
					</tr>
					<tr>
					   <td class="muiTitle">
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRemark" />
						</td>
						<td>
							<xform:textarea property="fdRemark" showStatus="edit"  validators="maxLength(500)" mobile="true" style="width:94%"/>
						</td>
					</tr>
				</table>	
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="muiViewBottom">
					<li data-dojo-type="sys/circulation/mobile/js/CirculationOperationButton" data-dojo-props="fdType:'cancel'">
						<bean:message key="button.cancel" />
					</li>
					<li data-dojo-type="sys/circulation/mobile/js/CirculationOperationButton" data-dojo-props="fdType:'ok'" class="muiBtnDefault mainTabBarButton">
						<bean:message key="button.ok" />
					</li>
				</ul>
			</div>
		</html:form>
	</div>
</div> 