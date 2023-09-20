<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit">
	<template:replace name="content" >
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<div style="margin-top:25px">
				<p class="configtitle"><bean:message bundle="sys-organization" key="sysOrgConfig"/></p>
				<center>
					<table class="tb_normal" width=95%>
						<tr>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="km-imeeting" key="kmImeetingRes.conflict.configMethod"/>
							</td>
							<td colspan="3">
								<html:hidden property="value(unShow)" />
								<input name="unShow" type="checkbox" onclick="changeValue(this);"/>
								<bean:message bundle="km-imeeting" key="kmImeetingRes.config.unShow"/>
							</td>		
						</tr>
						
						<tr>
							<td class="td_normal_title" width=20%>
								${lfn:message('km-imeeting:kmImeeting.SendEmails.notify')}
							</td>
							<td colspan="3">
								<html:hidden property="value(setICS)" />
								<input name="setICS" type="checkbox" onclick="changeICS(this);"/>
								${lfn:message('km-imeeting:kmImeeting.SendEmails.notify')}
								</br>
								<span style="color: red;"><bean:message bundle="km-imeeting" key="kmImeeting.SendEmails.notify.tip"/></span>
							</td>		
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="kmImeetingSummary.notifyPerson.config" bundle="km-imeeting" />
							</td>
							<td colspan="3">
								<xform:checkbox property="value(summaryNotifyPerson)">
									<xform:simpleDataSource value="1"><bean:message key="kmImeetingSummary.fdNotifyPerson.fdEmcee" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="2"><bean:message key="kmImeetingSummary.fdNotifyPerson.fdHost" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="3"><bean:message key="kmImeetingSummary.fdNotifyPerson.fdActualAttendPerson" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="4"><bean:message key="kmImeetingSummary.fdNotifyPerson.fdCopyToPerson" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="5"><bean:message key="kmImeetingSummary.fdNotifyPerson.fdPlanPerson" bundle="km-imeeting" /></xform:simpleDataSource>
								</xform:checkbox>
							</td>		
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="kmImeetingFeedback.viewPerson.config" bundle="km-imeeting" />
							</td>
							<td colspan="3">
								<xform:checkbox property="value(feedbackViewPerson)">
									<xform:simpleDataSource value="1"><bean:message key="kmImeetingFeedback.viewPerson.docCreator" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="2"><bean:message key="kmImeetingFeedback.viewPerson.fdEmcee" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="3"><bean:message key="kmImeetingFeedback.viewPerson.fdHost" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="4"><bean:message key="kmImeetingFeedback.viewPerson.fdAttendPerson" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="5"><bean:message key="kmImeetingFeedback.viewPerson.fdCopyToPerson" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="6"><bean:message key="kmImeetingFeedback.viewPerson.fdSummaryInputPerson" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="7"><bean:message key="kmImeetingFeedback.viewPerson.agendaDocRespons" bundle="km-imeeting" /></xform:simpleDataSource>
								</xform:checkbox>
							</td>		
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="kmImeeting.useCyclicity.config" bundle="km-imeeting" />
							</td>
							<td colspan="3">
								<xform:radio property="value(useCyclicity)" onValueChange="changeUseCyclicity">
									<xform:simpleDataSource value="1"><bean:message key="kmImeeting.useCyclicity.no" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="2"><bean:message key="kmImeeting.useCyclicity.all" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="3"><bean:message key="kmImeeting.useCyclicity.other" bundle="km-imeeting" /></xform:simpleDataSource>
								</xform:radio>
								<br/>
								<div id="usePersonDiv" style="display:none;">
									<xform:address propertyName="value(useCyclicityPersonName)" propertyId="value(useCyclicityPersonId)" orgType="ORG_TYPE_PERSON" style="width:90%;height:80px" textarea="true"  mulSelect="true" showStatus="edit"></xform:address>
								</div>
							</td>		
						</tr>
						
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="kmImeetingTopic.config" />
							</td>
							<td colspan="3">
								<ui:switch property="value(topicMng)" checkVal="2" unCheckVal="1" enabledText="${ lfn:message('km-imeeting:kmImeeting.topicMng.on') }" disabledText="${ lfn:message('km-imeeting:kmImeeting.topicMng.off') }" ></ui:switch>
							</td>
						</tr>
						<!-- #77497 --><!-- #81614 隐藏“云会议”后台配置项 -->
						<%-- <tr>
							<td><bean:message bundle="km-imeeting" key="appConfig.KmImeetingConfig.cloud"/></td>
							<td colspan="3">
								<ui:switch property="value(useCloudMng)" checkVal="2" unCheckVal="1" enabledText="${ lfn:message('km-imeeting:kmImeeting.useCloudMng.on') }" disabledText="${ lfn:message('km-imeeting:kmImeeting.useCloudMng.off') }" ></ui:switch>
							</td>
						</tr> --%>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="km-imeeting" key="appConfig.tongyiyunxushanghui" />
							</td>
							<td colspan="3">
								<ui:switch property="value(topicAcceptRepeat)" checkVal="2" unCheckVal="1" enabledText="${ lfn:message('km-imeeting:kmImeeting.topicAcceptRepeat.on') }" disabledText="${ lfn:message('km-imeeting:kmImeeting.topicAcceptRepeat.off') }" ></ui:switch>
							</td>
						</tr>
						<!-- 暂时隐藏视频会议 开始 -->
						<%-- <kmss:ifModuleExist path="/third/aliMeeting">
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-imeeting" key="table.kmImeetingVideo" />
								</td>
								<td colspan="3">
									<ui:switch property="value(videoMeeting)" checkVal="2" unCheckVal="1" enabledText="${ lfn:message('km-imeeting:kmImeeting.videoMeeting.on') }" disabledText="${ lfn:message('km-imeeting:kmImeeting.videoMeeting.off') }" ></ui:switch>
									(<bean:message bundle="km-imeeting" key="tips.videoMeetingNote" />)
								</td>
							</tr>
						</kmss:ifModuleExist> --%>
						<!-- 暂时隐藏视频会议 结束 -->
						<%--
						<tr>
							<td>
								对接铂恩会议系统
							</td>
							<td colspan="3">
								<ui:switch property="value(integrateBoen)" checkVal="2" unCheckVal="1" enabledText="${ lfn:message('km-imeeting:kmImeeting.integrateBoen.on') }" disabledText="${ lfn:message('km-imeeting:kmImeeting.integrateBoen.off') }" ></ui:switch>
							</td>
						</tr>
						<tr>
							<td>
								铂恩会中资料同步完成通知对象
							</td>
							<td colspan="3">
								<xform:checkbox property="value(boenNotifyPerson)">
									<xform:simpleDataSource value="1"><bean:message key="kmImeetingSummary.fdNotifyPerson.fdEmcee" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="2"><bean:message key="kmImeetingSummary.fdNotifyPerson.fdHost" bundle="km-imeeting" /></xform:simpleDataSource>
									<xform:simpleDataSource value="3"><bean:message key="kmImeetingSummary.fdNotifyPerson.fdLiaison" bundle="km-imeeting" /></xform:simpleDataSource>
								</xform:checkbox>
							</td>
						</tr>
						 --%>
					</table>
					<div style="margin-bottom: 10px;margin-top:25px">
						   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
					</div>
					</center>
			</div>
		</html:form>
		<script type="text/javascript">
			window.onload = function(){
				var unShow=document.getElementsByName("unShow")[0];
				var unShowValue=document.getElementsByName("value(unShow)")[0];
				if(unShowValue.value=="false"){
					unShow.checked=false;
				}else{
					unShow.checked=true;
				}
				
				var setICS=document.getElementsByName("setICS")[0];
				var setICSValue=document.getElementsByName("value(setICS)")[0];
				if(setICSValue.value=="false"){
					setICS.checked=false;
				}else{
					setICS.checked=true;
				}
				
				var useCyclicityArr=document.getElementsByName("value(useCyclicity)");
				var flag = false;
				for(var i = 0 ; i<useCyclicityArr.length;i++){
					var useCyclicity = useCyclicityArr[i];
					if(useCyclicity.checked == true){
						flag = true;
						changeUseCyclicity(useCyclicity.value);
					}
				}
				if(!flag){
					useCyclicityArr[0].checked = true;
				}
			};
			
			function changeValue(thisObj){
				var unShow=document.getElementsByName("unShow")[0];
				var unShowValue=document.getElementsByName("value(unShow)")[0];
				if(unShow.checked){
					unShowValue.value="true";
				}else{
					unShowValue.value="false";
				}
			}
			function changeICS(thisObj){
				var setICS=document.getElementsByName("setICS")[0];
				var setICSValue=document.getElementsByName("value(setICS)")[0];
				if(setICS.checked){
					setICSValue.value="true";
				}else{
					setICSValue.value="false";
				}
			}
			function changeUseCyclicity(value){
				var usePersonDiv = document.getElementById("usePersonDiv");
				if("3" == value){
					usePersonDiv.style.display = "inline";
				}else {
				    usePersonDiv.style.display = "none";
				}
			}
		</script>
	</template:replace>
</template:include>