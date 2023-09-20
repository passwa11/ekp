<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<mui:cache-file name="mui-circulate.css" cacheType="md5"/>
<div data-dojo-type="mui/view/DocView" id="circulationView" style="display: none"> 
	<div id="circulationContent" class="muiCirculation">
		<html:form action="/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=save&fdModelName=${JsParam.modelName}&fdModelId=${JsParam.modelId}">
			<div data-dojo-type="sys/circulation/mobile/js/CirculationView" data-dojo-mixins="mui/form/_ValidateMixin" id="circulationScrollView">
				<div class="muiFormContent">
					<html:hidden property="fdId" />
					<html:hidden property="fdFromParentId" />
					<html:hidden property="fdFromOpinionId" />
					<html:hidden property="fdModelId" value="${JsParam.modelId}"/>
					<html:hidden property="fdModelName" value="${JsParam.modelName}"/>
				</div>
				<table class="muiSimple headTb" cellpadding="0" cellspacing="0" >
					<tr>
						<td class="muiTitle">
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdRegular" />
						</td>
						<td>
							<xform:radio property="fdRegular" mobile="true" showStatus="edit" >
								<xform:enumsDataSource enumsType="sysCirculationMain_fdRegular"></xform:enumsDataSource>
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="sys-circulation" key="table.sysCirculationCirculors" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<span style="color: #AAACB7"><bean:message bundle="sys-circulation" key="sysCirculationMain.receivedCirCulatorTip" /></span>
							<input type="hidden" name="receivedCirCulatorIds" value="">
							<div id="main_m" style="display:block;margin-top: 0.5rem">
								<xform:address htmlElementProperties="id='receivedCirCulator_m'" showStatus="edit" mobile="true" propertyId="receivedCirCulatorIds_m"  propertyName="receivedCirCulatorNames_m"  mulSelect="true" textarea="true"
									orgType="ORG_TYPE_ALLORG" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
									style="width:95%" >
								</xform:address>
							</div>
							<div id="main_s"  style="display:none;margin-top: 0.5rem">
								<xform:address htmlElementProperties="id='receivedCirCulator_s'" showStatus="edit" mobile="true" propertyId="receivedCirCulatorIds_s"  propertyName="receivedCirCulatorNames_s"  mulSelect="true" textarea="true"
									orgType="ORG_TYPE_PERSON" required="true" subject="${ lfn:message('sys-circulation:table.sysCirculationCirculors') }" 
									style="width:95%" >
								</xform:address>
							</div>
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
					
					<tr id="spreadScopeTr">
						<td class="muiTitle">
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdAllowSpread" />
						</td>
						<td class="muiRightTd">
							<div data-dojo-type="mui/form/switch/NewSwitch" data-dojo-props="property:'fdAllowSpread',showStatus:'edit',leftLabel:'',rightLabel:'', value:'off'"></div>
						</td>
					</tr>
					<tr id="spreadScopeTr2" style="display:none;">
						<td class="muiTitle">
							<bean:message bundle="sys-circulation" key="sysCirculationMain.fdSpreadScope" />
						</td>
						<td class="muiRightTd">
							<div id="allowSpreadScope" style="margin-top:6px">
								<xform:radio property="fdSpreadScope" mobile="true" showStatus="edit" htmlElementProperties="id='fdSpreadScope'">
									<xform:enumsDataSource enumsType="sysCirculationMain_fdSpreadScope"></xform:enumsDataSource>
								</xform:radio><br/>
								<div id="OtherSpreadScope" style="display:none;margin-top:6px">
									<xform:address htmlElementProperties="id=spreadScope" mobile="true" required="true" textarea="true" propertyId="fdOtherSpreadScopeIds" style="width:95%" propertyName="fdOtherSpreadScopeNames"  mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="edit">
									</xform:address>
								</div>
							</div>
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