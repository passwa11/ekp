<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.edit" compatibleMode="true">
	
	<template:replace name="title">
		上传上会资料
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/edit.css" />
		<script type="text/javascript">
		   	require(["dojo/ready","dojo/query","dojo/on","dojo/dom-style","dojo/dom-class","mui/dialog/Tip"],
		   			function(ready, query,on,domStyle,domClass,tip ){
		   		
				window.submit = function(){
					Com_Submit(document.kmImeetingMainForm, 'saveUpdateAtt');
				};
				
				ready(function(e){
					if(query('.muiDetailTableUp')){
						
						var muiDetailTableDown = query('.muiDetailTableUp');
						on(muiDetailTableDown,"click", function(evt){
							var agendaInfoIcon = evt.path[0];
							var table = evt.path[5];
							var queryTable = query(table);
							var nextTable = queryTable.next()[0];
							var display = domStyle.get(nextTable,'display');
							if(display != 'none'){
								domStyle.set(nextTable,'display','none');
								domClass.add(agendaInfoIcon,'mui-down-n');
								domClass.remove(agendaInfoIcon,'mui-up-n');
							}else{
								domStyle.set(nextTable,'display','');
								domClass.add(agendaInfoIcon,'mui-up-n');
								domClass.remove(agendaInfoIcon,'mui-down-n');
							}
						})
					}
				});
		   	})
		</script>
	</template:replace>
	<template:replace name="content"> 
		<xform:config  orient="vertical">
			<html:form action="/km/imeeting/km_imeeting_main/kmImeetingMain.do">
				<html:hidden property="fdId" />
				<script type="text/javascript">
					Com_IncludeFile("doclist.js");
				</script>
				<%--将明细表推送到动态表格的列表中 --%>
				<script type="text/javascript">
					DocList_Info.push("TABLE_DocList");
				</script>
				<div id="scrollView"  class="gray" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
					<div data-dojo-type="mui/panel/AccordionPanel">
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.upload.title"/>',icon:'mui-ul'">
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<tr>
									<%--会议名称--%>
									<td>
										<bean:message  bundle="km-imeeting" key="kmImeetingMain.fdName"/>
									</td>
									<td> 
										<c:out value="${kmImeetingMainForm.fdName}"/>
									</td>
									<%--已召开会议无权限再提交上会材料--%>
									<c:if test="${canUpload==false}">
										<script>
									 		require(["dojo/ready","mui/dialog/Tip"],function(ready,Tip){
									 			ready(function(){
									 				Tip.tip({icon:'mui mui-warn', text:'<bean:message  bundle="km-imeeting" key="kmImeetingMain.cannot.upload.tip"/>',width:'260'});
									 			});
									 		})
										</script>
									</c:if>
								</tr>
								<%-- 所属场所 --%>
								<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
				                     <c:param name="id" value="${kmImeetingMainForm.authAreaId}"/>
				                </c:import>
								<tr>
									<%--召开时间--%>
									<td>
										<bean:message  bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
									</td>
									<td> 
										<xform:datetime property="fdHoldDate" dateTimeType="datetime"></xform:datetime>~
										<xform:datetime property="fdFinishDate" dateTimeType="datetime"></xform:datetime>
									</td>
								</tr>
								<tr>
									<%--地点--%>
									<td>
										<bean:message  bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
									</td>
									<td> 
										<c:out value="${kmImeetingMainForm.fdPlaceName}"/>
										<c:if test="${not empty kmImeetingMainForm.fdOtherPlace }">
											<c:out value="${kmImeetingMainForm.fdOtherPlace}"/>
										</c:if>
									</td>
								</tr>
								<tr>
									<%--主持人--%>
									<td>
										<bean:message  bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
									</td>
									<td> 
										<c:out value="${kmImeetingMainForm.fdHostName}"/>
										<c:if test="${not empty kmImeetingMainForm.fdOtherHostPerson }">
											<c:out value="${kmImeetingMainForm.fdOtherHostPerson}"/>
										</c:if>
									</td>
								</tr>
							</table>
						</div>
						<%@ include file="/km/imeeting/mobile/uploadAtt_detailTab.jsp"%>	
						<%--参加人员 --%>
						<%-- <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('km-imeeting:kmImeetingMain.fdAttendPersons') }',icon:''">
							
						</div> --%>
					</div>
					<c:if test="${canUpload==true}">
						<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
							<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props='colSize:2,href:"javascript:window.submit();"'>
							  	<bean:message bundle="km-imeeting" key="mobile.oper.submit"/>
						  	</li>
						</ul>
					</c:if>
				</div>
			</html:form>
		</xform:config>
	</template:replace>
</template:include>
<%-- <%@ include file="/km/imeeting/mobile/edit_js.jsp"%> --%>