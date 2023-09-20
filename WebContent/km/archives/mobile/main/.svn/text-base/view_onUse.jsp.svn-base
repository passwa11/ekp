<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesDetails"%>
<%@page import="com.landray.kmss.km.archives.forms.KmArchivesMainForm"%>
<%@page import="com.landray.kmss.km.archives.util.KmArchivesUtil"%>
<%@page import="java.util.Calendar"%>
<%
	KmArchivesMainForm mainForm = (KmArchivesMainForm)request.getAttribute("kmArchivesMainForm");
	String fdId = mainForm.getFdId();
	KmArchivesDetails detail = KmArchivesUtil.getBorrowDetail(fdId);
	boolean isValidity = KmArchivesUtil.isValidity(fdId);
	boolean canBorrow = KmArchivesUtil.isCanBorrowByTemplate(fdId);
	pageContext.setAttribute("isValidity", isValidity);
	pageContext.setAttribute("canBorrow", canBorrow);
	pageContext.setAttribute("isBorrowed", detail != null);
	pageContext.setAttribute("currentId",UserUtil.getUser().getFdId());
	pageContext.setAttribute("isWork",KmArchivesUtil.isWorkUser(mainForm.getFdId()));
	pageContext.setAttribute("viewAll", UserUtil.checkRole("ROLE_KMARCHIVES_VIEW_ALL"));
%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="loading">
		<c:import url="/km/archives/mobile/borrow/view_banner.jsp" charEncoding="UTF-8">
			<c:param name="formBeanName" value="kmArchivesMainForm"></c:param>
			<c:param name="loading" value="true"></c:param>
		</c:import>
	</template:replace>
	<template:replace name="title">
		 <c:out value="${kmArchivesMainForm.docSubject} - " />
         <c:out value="${ lfn:message('km-archives:table.kmArchivesMain') }" />
	</template:replace>
	<template:replace name="head">
	<link rel="Stylesheet" href="${LUI_ContextPath}/km/archives/mobile/resource/css/archives_main_view.css?s_cache=${MUI_Cache}" />
	  <script type="text/javascript">
	   	require(["dojo/store/Memory","dojo/topic"],function(Memory, topic){
	   		window._narStore = new Memory({data:[{'text':'<bean:message bundle="km-archives" key="py.JiBenXinXi" />',
	   			'moveTo':'_contentView','selected':true},{'text':'<bean:message bundle="km-archives" key="kmArchivesMain.fileLevel" />',
		   			'moveTo':'_fileView'},{'text':'<bean:message bundle="sys-mobile" key="mui.mobile.review.record" />',
	   			'moveTo':'_noteView'}]});
	   		topic.subscribe("/mui/navitem/_selected",function(evtObj){
	   			setTimeout(function(){topic.publish("/mui/list/resize");},150);
	   		});
	   	});
	   </script>
	</template:replace>
	<template:replace name="content">

			<div id="scrollView" data-dojo-type="mui/view/DocScrollableView">
			<c:import url="/km/archives/mobile/main/view_banner.jsp" charEncoding="UTF-8">
				<c:param name="formBeanName" value="kmArchivesMainForm"></c:param>
			</c:import>

			<div data-dojo-type="mui/fixed/Fixed" id="fixed">
				<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowFixedItem">
					<div data-dojo-type="mui/nav/NavBarStore"
						data-dojo-props="store:_narStore"></div>
				</div>
			</div>
			<div data-dojo-type="dojox/mobile/View" id="_contentView">
				<div data-dojo-type="mui/panel/AccordionPanel">
						<div class="muiFormContent muiFlowInfoW">
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<tr>
									<td class="muiTitle">
										<bean:message bundle="km-archives" key="kmArchivesMain.fdDenseLevel" /></td>
									<td ><xform:text property="fdDenseName" showStatus="view"  mobile="true" /></td>
								</tr>
								<tr>
									<td class="muiTitle"><bean:message bundle="km-archives" key="kmArchivesMain.fdUnit" />
									</td>
									<td>
										<xform:text property="fdUnit" showStatus="view" mobile="true" />
									</td>
								</tr>
								<tr>
									<td class="muiTitle">
											<bean:message bundle="km-archives"
												key="kmArchivesMain.fdStorekeeper" /></td>
									<td>
									<xform:address propertyId="fdStorekeeperId" propertyName="fdStorekeeperName" orgType="ORG_TYPE_PERSON" showStatus="view" mobile="true" />
									</td>
								</tr>
								<tr>
									<td class="muiTitle"><bean:message bundle="km-archives"
											key="kmArchivesMain.fdLibrary" /></td>
									<td > <xform:text property="fdLibrary" showStatus="view" mobile="true"  /></td>
								</tr>
								<tr>
									<td class="muiTitle"><bean:message bundle="km-archives"
											key="kmArchivesMain.fdVolumeYear" /></td>
									<td ><c:out value="${kmArchivesMainForm.fdVolumeYear}"/></td>
								</tr>
							</table>
					</div>
			</div>
		</div>
		<div data-dojo-type="dojox/mobile/View" id="_fileView">
			<div class="muiFormContent muiFlowInfoW">
				  <c:if test="${not empty kmArchivesMainForm.extendFilePath }">
					  <div>
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<c:import url="/sys/property/include/sysProperty_pda.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmArchivesMainForm" />
		                        <c:param name="fdDocTemplateId" value="${kmArchivesMainForm.docTemplateId}" />
		                        <c:param name="isPda" value="true" />
							</c:import>
		               	</table>
		              </div>
                  </c:if>
               	<!-- 附件机制 -->
             	  <table class="muiSimple">
              	<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesMainForm" />
					<c:param name="fdKey" value="attArchivesMain" />
				</c:import>
				</table>
			</div>
		</div>
		<div data-dojo-type="dojox/mobile/View" id="_noteView">
			<div class="muiFormContent muiFlowInfoW">
				<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
					<c:param name="fdModelId" value="${kmArchivesMainForm.fdId }"/>
					<c:param name="fdModelName" value="com.landray.kmss.km.archives.model.KmArchivesMain"/>
					<c:param name="formBeanName" value="kmArchivesMainForm"/>
				</c:import>
		   </div>
		 </div>
		 <template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
			      docStatus="${kmArchivesMainForm.docStatus}"
				  editUrl="/km/archives/km_archives_main/kmArchivesMain.do?method=edit&fdId=${param.fdId}"
				  formName="kmArchivesMainForm"
				  viewName="lbpmView"
				  allowReview="true">
				<template:replace name="flowArea">
				</template:replace>
				<template:replace name="publishArea">
					<kmss:authShow roles="ROLE_KMARCHIVES_CREATE_BORROW">
					<c:if test="${isValidity and canBorrow}">
						<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'',href:'javascript:addBorrow();'">
							${ lfn:message('km-archives:button.borrow') }
						</li>
					</c:if>
					</kmss:authShow>
					<c:if test="${isBorrowed }">
						<li data-dojo-type="mui/tabbar/TabBarButton"
							data-dojo-props="icon1:'',href:'javascript:returnBack();'">
							${ lfn:message('km-archives:button.return') }
						</li>
					</c:if>
				</template:replace>
		</template:include>
	</div>

		<!-- 钉钉图标 -->
		<kmss:ifModuleExist path="/third/ding">
			<c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmArchivesMainForm" />
			</c:import>
		</kmss:ifModuleExist>
		<kmss:ifModuleExist path="/third/lding">
			<c:import url="/third/lding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmArchivesMainForm" />
			</c:import>
		</kmss:ifModuleExist>
		<!-- 钉钉图标 end -->
	<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmArchivesMainForm" />
			<c:param name="fdKey" value="kmArchivesMain" />
			<c:param name="viewName" value="lbpmView" />
			<c:param name="backTo" value="scrollView" />
	</c:import> 
	</template:replace>
</template:include>
<script type="text/javascript">
require(["dojo/ready","dojo/dom-class","dojo/request","dojo/dom","dojo/query","dojo/on","dojo/dom-attr","dojo/dom-style","dojox/mobile/TransitionEvent",'mui/dialog/Tip','mui/dialog/Confirm','dojo/dom-geometry','dojo/topic','dijit/registry'], 
		function(ready,domClass,request,dom,query,on,domAttr,domStyle,TransitionEvent,Tip,Confirm,domGeometry,topic,registry) {
	window.addBorrow = function(){
		var url=Com_Parameter.ContextPath+'km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add4m&fdMainId=${kmArchivesMainForm.fdId}';
		window.open(url,"_self");
	};
	
	window.returnBack = function(){
		var url = Com_Parameter.ContextPath+'km/archives/km_archives_details/kmArchivesDetails.do?method=returnBack&fdArchId=${kmArchivesMainForm.fdId}';
		request.get(url, {handleAs : 'json',headers: {"accept": "application/json"}})
		.response.then(function(datas) {
			if(datas.status == '200'){
				var _data = datas.data;
				if(_data.length == 0){
					Tip.fail({
						text:'您未借阅该档案!' 
					});
				}else{
					Confirm('确认归还？','',function(value){
						if(value){
							var _url = Com_Parameter.ContextPath+'km/archives/km_archives_details/kmArchivesDetails.do?method=comfirmReturnBack&fdId='+_data[0].fdId;
							request.get(_url,{handleAs:'json',headers: {"accept": "application/json"}})
							.response.then(function(data){
								Tip.success({
									text:'归还成功'
								});
								setTimeout('location.reload();',1000);
							});
						}
					});
				}
			}
		});
	}
});
</script>