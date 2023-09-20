<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.DateUtil,java.util.Date,com.landray.kmss.util.ResourceUtil" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
		${ lfn:message('sys-attend:sysAttendMain.fdExcStatus') }
	</template:replace>
	
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>
	<script type="text/javascript">
		   	require(["dojo/store/Memory","dojo/topic","dijit/registry"],function(Memory,topic,registry){
		   		var navData = [{'text':'01  /  <bean:message bundle="sys-mobile" key="mui.mobile.info" />',
		   			'moveTo':'scrollView','selected':true},{'text':'02  /  <bean:message bundle="sys-mobile" key="mui.mobile.review" />',
			   		'moveTo':'lbpmView'}]
		   		window._narStore = new Memory({data:navData});
		   		var changeNav = function(view){
		   			var wgt = registry.byId("_flowNav");
		   			for(var i=0;i<wgt.getChildren().length;i++){
		   				var tmpChild = wgt.getChildren()[i];
		   				if(view.id == tmpChild.moveTo){
		   					tmpChild.beingSelected(tmpChild.domNode);
		   					return;
		   				}
		   			}
		   		}
		   		topic.subscribe("mui/form/validateFail",function(view){
		   			changeNav(view);
		   		});
				topic.subscribe("mui/view/currentView",function(view){
					changeNav(view);
		   		});
		   	});
	   </script>
	</template:replace>
	<template:replace name="content"> 
		<html:form action="/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=save">
			<div>
				<div data-dojo-type="mui/fixed/Fixed" class="muiFlowEditFixed">
					<div data-dojo-type="mui/fixed/FixedItem"
						class="muiFlowEditFixedItem">
						<div data-dojo-type="mui/nav/NavBarStore" id="_flowNav"
							data-dojo-props="store:_narStore"></div>
					</div>
				</div>
				<html:hidden property="fdId" />
				<html:hidden property="fdAttendMainId" />
				<html:hidden property="fdCateTemplId" />
				<html:hidden property="docStatus" />
				<html:hidden property="docSubject" />
				<html:hidden property="fdManagerId" />
				<html:hidden property="method_GET" />

				<div data-dojo-type="mui/view/DocScrollableView"
					data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView"
					class="muiSignExc gray">
					<div class="muiFormContent"
						style="background: #fff; padding: 0rem 0 0 1rem;">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="muiTitle">${ lfn:message('sys-attend:mui.people.name') }</td>
								<td><c:out value="${sysAttendMainForm.docCreatorName}" />
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-attend"
										key="sysAttendMain.docCreateTime" /></td>
								<td>
									<xform:datetime property="fdAttendTime" dateTimeType="datetime" mobile="true" showStatus="edit"
											validators="checkAttendTime required" subject="${ lfn:message('sys-attend:mui.exc.attend.time') }"></xform:datetime>
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-attend"
										key="sysAttendMain.fdStatus1" /></td>
								<td>
									<span style="color: red;">
									<c:choose>
										<c:when test="${sysAttendMainForm.fdStatus=='1' && sysAttendMainForm.fdOutside=='true'}">
											${ lfn:message('sys-attend:sysAttendMain.fdOutside') }
										</c:when>
										<c:otherwise>
											<sunbor:enumsShow
												value="${sysAttendMainForm.fdStatus}"
												enumsType="sysAttendMain_fdStatus" />
										</c:otherwise>
									</c:choose>	
									</span>
								</td>
							</tr>
							<c:if test="${not empty sysAttendMainForm.fdLocation }">
							<tr>
								<td class="muiTitle">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdLocation1"/>
								</td>
								<td>
									<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
									<c:set var="fdLocationCoordinate" value="${sysAttendMainForm.fdLat}${','}${sysAttendMainForm.fdLng}"/>
									<map:location propertyName="fdLocation" nameValue="${sysAttendMainForm.fdLocation }"
										propertyCoordinate="fdLocationCoordinate" coordinateValue="${fdLocationCoordinate }" 
										showStatus="view" mobile="true"></map:location>
								</td>
							</tr>
							</c:if>
							<tr>
								<td colspan="2"><xform:textarea placeholder="${ lfn:message('sys-attend:mui.exc.reason.desc') }"
										validators="maxLength(150)" property="fdDesc" mobile="true" showStatus="edit"/>
									<div class="muiDesc">(${ lfn:message('sys-attend:mui.exc.reason.tips') })</div></td>
							</tr>
							<tr>
								<td colspan="2" id="td_attachments"><c:import
										url="/sys/attachment/mobile/import/edit.jsp"
										charEncoding="UTF-8">
										<c:param name="formName" value="sysAttendMainExcForm"></c:param>
										<c:param name="fdKey" value="attachment" />
										<c:param name="fdAttType" value="pic"></c:param>
									</c:import></td>
							</tr>

						</table>
					</div>
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
						<li data-dojo-type="mui/back/BackButton" edit="true" data-dojo-props="icon1:''"></li>
						<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext "
							data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>${ lfn:message('sys-attend:mui.next.step') }</li>
						<li data-dojo-type="mui/tabbar/TabBarButtonGroup"
							data-dojo-props="icon1:'',label:'${ lfn:message('operation.more') }'">
							<div data-dojo-type="mui/back/HomeButton"></div>
						</li>
					</ul>
				</div>
				<c:import url="/sys/lbpmservice/mobile/import/edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendMainExcForm" />
					<c:param name="fdKey" value="attendMainExc" />
					<c:param name="viewName" value="lbpmView" />
					<c:param name="backTo" value="scrollView" />
					<c:param name="onClickSubmitButton" value="commitMethod();" />
				</c:import>
		</html:form>
	</template:replace>
</template:include>
<script type="text/javascript">
	require(["mui/form/ajax-form!sysAttendMainExcForm"]);
	require(['dojo/topic','mui/dialog/Tip',"dojo/dom-class","dojo/query","dojo/dom-style","dijit/registry","dojo/ready"],
		function(topic,Tip,domClass,query,domStyle,registry,ready){
		window.commitMethod=function(){
			var validorObj = registry.byId('scrollView');
			if(!validorObj.validate()){
				return false;
			}
			var docStatus = document.getElementsByName("docStatus")[0];
			var method = Com_GetUrlParameter(location.href,'method');
			docStatus.value = "20";
			if(method=='addExc' || method=='add'){
				Com_Submit(document.sysAttendMainExcForm,'save','fdAttendMainId');
			}else{
				Com_Submit(document.sysAttendMainExcForm,'update','fdAttendMainId');
			}
		}
		window.setAttShowChange = function(){
			setTimeout(function(){
				var count = query('#td_attachments .muiAttachmentEditItem').length-1;
				if(count>=4){
					domStyle.set(query('#td_attachments .muiAttachmentEditOptItem')[0],'display','none');
				}else{
					domStyle.set(query('#td_attachments .muiAttachmentEditOptItem')[0],'display','');
				}
				var descDoms = query('#td_attachments .muiAttachmentEditItem .muiAttachmentItemB');
				for(var i = 0 ; i < descDoms.length;i++){
					if (domClass.contains(descDoms[i], "muiAttachmentMsg")){
					    continue;
					}
					domStyle.set(descDoms[i],'display','none');
				}
			},1);
			
		};
		ready(function(){
			var validorObj = registry.byId('scrollView');
			validorObj._validation.addValidator('checkAttendTime',"${ lfn:message('sys-attend:mui.exc.attend.time.tips') }", function(value){
				var fdStartTime = '${fdStartTime}';
				var fdEndTime = '${fdEndTime}';
				if(value && fdStartTime && fdEndTime) {
					fdStartTime = Com_GetDate('${fdStartTime}', 'datetime','<%= ResourceUtil.getString("date.format.datetime") %>');
					fdEndTime = Com_GetDate('${fdEndTime}', 'datetime','<%= ResourceUtil.getString("date.format.datetime") %>');
					var date = Com_GetDate(value, 'datetime','<%= ResourceUtil.getString("date.format.datetime") %>');
					<%--var isAcorss = "${sysAttendMainForm.fdIsAcross}";--%>
					<%--if(isAcorss == 'true'){--%>
					<%--	date.setDate(date.getDate() + 1);--%>
					<%--}--%>
					if(date && fdStartTime && fdEndTime) {
						return date.getTime() >= fdStartTime.getTime() && date.getTime() <= fdEndTime.getTime();
					}
				}
				return true;
			});
			
			topic.subscribe('attachmentObject_attachment_success',function(srcObj,evt){
				setAttShowChange();
			});
			topic.subscribe('attachmentObject_attachment_del',function(srcObj,evt){
				setAttShowChange();
			});
		});
		
});	


</script>


