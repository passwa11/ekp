<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/smissive/mobile/resource/css/smissive.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="title">
		<c:out value="${kmSmissiveMainForm.docSubject}"></c:out>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView" class="gray" data-dojo-type="mui/view/DocScrollableView">
			<div class="muiViewBanner">
			<c:choose> 
			   <c:when test="${kmSmissiveMainForm.fdNeedContent=='1'}">
			      <c:set var="attForms" value="${kmSmissiveMainForm.attachmentForms['mainOnline']}" />
			   </c:when>
			   <c:otherwise>
				  <c:set var="attForms" value="${kmSmissiveMainForm.attachmentForms['editOnline']}" />
				</c:otherwise>
			</c:choose>	
			 	<c:if test="${attForms!=null && fn:length(attForms.attachments)>0}">
			 		<c:forEach var="sysAttMain" items="${attForms.attachments}" varStatus="vsStatus">
			 			<c:set var="attMainId" value="${sysAttMain.fdId }"></c:set>
			 			<%
							SysAttMain sysAttMain = (SysAttMain) pageContext
											.getAttribute("sysAttMain");
							String path = SysAttViewerUtil.getViewerPath(
									sysAttMain, request);
							if (StringUtil.isNotNull(path)){
								pageContext.setAttribute("hasThumbnail", "true");
								pageContext.setAttribute("hasViewer", "true");
							}
							pageContext.setAttribute("_sysAttMain", sysAttMain);
						%>
			 		</c:forEach>
			 	</c:if>
			 	
				<div class="leftInfo">
					<c:if test="${hasThumbnail =='true'}">
						<img class="muiSummaryImg" src="${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&fdId=${attMainId }" onerror="this.src='${LUI_ContextPath}/km/smissive/mobile/resource/images/thumbnail.png'" alt="" />
					</c:if>
					<c:if test="${hasThumbnail !='true'}">
						<img class="muiSummaryImg" src="${LUI_ContextPath}/km/smissive/mobile/resource/images/thumbnail.png" alt="" />
					</c:if>
					<c:choose>
							<c:when test="${kmSmissiveMainForm.docStatus=='00'}">
								<c:set var="statusBg" value="muiSmissiveDiscard"/>
								<c:set var="statusText" value="${ lfn:message('status.discard')}"/>
							</c:when>
							<c:when test="${kmSmissiveMainForm.docStatus=='10'}">
								<c:set var="statusBg" value="muiSmissiveDraft"/>
								<c:set var="statusText" value="${ lfn:message('status.draft')}"/>
							</c:when>
							<c:when test="${kmSmissiveMainForm.docStatus=='11'}">
								<c:set var="statusBg" value="muiSmissiveRefuse"/>
								<c:set var="statusText" value="${ lfn:message('status.refuse')}"/>
							</c:when>
							<c:when test="${kmSmissiveMainForm.docStatus=='20'}">
								<c:set var="statusBg" value="muiSmissiveExamine"/>
								<c:set var="statusText" value="${ lfn:message('status.examine')}"/>
							</c:when>
							<c:when test="${kmSmissiveMainForm.docStatus=='30'}">
								<c:set var="statusBg" value="muiSmissivePublish"/>
								<c:set var="statusText" value="${ lfn:message('status.publish')}"/>
							</c:when>
						</c:choose>
					<div class="muiSmissiveStatus ${statusBg}">
						${statusText }
					</div>
				</div>
				<div class="rightInfo">
					<span class="title">
						<xform:text property="docSubject"></xform:text>
					</span>
					<ul>
						<li>
							<span><bean:message  bundle="km-smissive" key="kmSmissiveMain.fdMainDeptId"/>：<c:out value="${kmSmissiveMainForm.fdMainDeptName}" /></span>
						</li>
						<li>
							<span><bean:message  bundle="km-smissive" key="kmSmissiveMain.fdFileNo"/>：<c:out value="${kmSmissiveMainForm.fdFileNo}" /></span>
						</li>
					</ul>
				</div>
				
			</div>
			<div data-dojo-type="mui/panel/AccordionPanel">
				<c:if test="${not empty _sysAttMain.fdId }">
					 <c:set var="editContent" value="false"/>
					 <c:if  test="${kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.editDocContent =='true'}">
						<c:set var="editContent" value="true"/>
					 </c:if>
					<c:choose> 
					   <c:when test="${kmSmissiveMainForm.fdNeedContent=='1'}">
					      <c:import url="/sys/attachment/mobile/import/viewContent.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmSmissiveMainForm"></c:param>
							<c:param  name="contentFlag"  value="true"/>
							<c:param name="fdKey" value="mainOnline"></c:param>
							<c:param name="editContent" value="${editContent}"></c:param>
						</c:import> 
					   </c:when>
					   <c:otherwise>
						  <c:import url="/sys/attachment/mobile/import/viewContent.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmSmissiveMainForm"></c:param>
							<c:param  name="contentFlag"  value="true"/>
							<c:param name="fdKey" value="editOnline"></c:param>
							<c:param name="editContent" value="${editContent}"></c:param>
						</c:import>
						</c:otherwise>
					</c:choose>	
				</c:if>
				<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmSmissiveMainForm"></c:param>
					<c:param name="fdKey" value="mainAtt"></c:param>
				</c:import> 
				<%--草稿，驳回和审批的文档稿纸和流程默认展开 --%>
				<c:if test="${kmSmissiveMainForm.docStatus eq '10' or kmSmissiveMainForm.docStatus eq '11' or kmSmissiveMainForm.docStatus eq '20'}">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message  bundle="km-smissive" key="mui.kmSmissiveMain.label.baseInfo.details"/>',icon:'mui-ul'">
						<c:import url="/km/smissive/mobile/basicInfoView.jsp" charEncoding="UTF-8">
						</c:import>
					</div>
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message  bundle="km-smissive" key="mui.kmSmissiveMain.label.processRecords"/>',icon:'mui-ul'">
						<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
							<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId }"/>
							<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain"/>
							<c:param name="formBeanName" value="kmSmissiveMainForm"/>
					   </c:import>
				   </div>
				</c:if>
			</div>
			<%--发布和废弃的文档稿纸和流程默认折叠 --%>
			<c:if test="${kmSmissiveMainForm.docStatus eq '30' or kmSmissiveMainForm.docStatus eq '00' }">
				<div class="detailInfo_wrapper">
					<div class="detailInfo">
						<div data-dojo-type="dojox/mobile/ListItem" class="baseInfoNav"
		 					data-dojo-props='icon:"mui mui-bookViewDetail",rightIcon:"mui mui-forward",clickable:true,noArrow:true,onClick:viewDetailInfo'><bean:message  bundle="km-smissive" key="mui.kmSmissiveMain.label.baseinfo"/></div>
					</div>
					<c:choose>
					 <c:when test="${kmSmissiveMainForm.fdFlowFlag == 'false'}">
						<div class="detailInfo">
							<div data-dojo-type="dojox/mobile/ListItem" class="baseInfoNav"
			 					data-dojo-props='icon:"mui mui-flowRecord",rightIcon:"mui mui-forward",clickable:true,noArrow:true,moveTo:"workFlowView",transitionDir:1,transition:"slide"'><bean:message  bundle="km-smissive" key="mui.kmSmissiveMain.label.processRecords"/></div>
						</div>
					 </c:when>
					 <c:otherwise>
					    <kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=viewWfLog&fdId=${param.fdId}" requestMethod="GET">
						<div class="detailInfo">
							<div data-dojo-type="dojox/mobile/ListItem" class="baseInfoNav"
			 					data-dojo-props='icon:"mui mui-flowRecord",rightIcon:"mui mui-forward",clickable:true,noArrow:true,moveTo:"workFlowView",transitionDir:1,transition:"slide"'><bean:message  bundle="km-smissive" key="mui.kmSmissiveMain.label.processRecords"/></div>
						</div>
						</kmss:auth>
					  </c:otherwise>
					</c:choose>
				</div>
			</c:if>
			
			<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
			      docStatus="${kmSmissiveMainForm.docStatus}" 
				  formName="kmSmissiveMainForm"
				  viewName="lbpmView"
				  allowReview="true">
				<template:replace name="flowArea">
				<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
						   <c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain"></c:param>
						   <c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}"></c:param>
						   <c:param name="fdSubject" value="${kmSmissiveMainForm.docSubject}"></c:param>
						   <c:param name="label" value="${lfn:message('sys-bookmark:button.bookmark')}"></c:param>
						   <c:param name="showOption" value="label"></c:param>
				</c:import>
				<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
				    		<c:param name="formName" value="kmSmissiveMainForm"></c:param>
				    		<c:param name="showOption" value="label"></c:param>
				 </c:import>
				</template:replace>
				<template:replace name="publishArea">
				 <c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
					  <c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain"></c:param>
					  <c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}"></c:param>
					  <c:param name="fdSubject" value="${kmSmissiveMainForm.docSubject}"></c:param>
					  <c:param name="showOption" value="label"></c:param>
				  </c:import>
				  <%--传阅 --%>
				  <c:import url="/sys/circulation/mobile/import/view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmSmissiveMainForm"></c:param>
					<c:param name="showOption" value="label"></c:param>
				  </c:import>
				  <c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
				    		<c:param name="formName" value="kmSmissiveMainForm"></c:param>
				    		<c:param name="showOption" value="label"></c:param>
				    </c:import>
				</template:replace>
			</template:include>
		</div>
		<!-- 钉钉图标 -->
		<kmss:ifModuleExist path="/third/ding">
			<c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmSmissiveMainForm" />
			</c:import>
		</kmss:ifModuleExist>
		<!-- 钉钉图标 end -->
		
		<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmSmissiveMainForm" />
			<c:param name="fdKey" value="smissiveDoc" />
			<c:param name="viewName" value="lbpmView" />
			<c:param name="backTo" value="scrollView" />
		</c:import>
		<%--发布和废弃的文档稿纸和流程默认折叠 --%>
		<c:if test="${kmSmissiveMainForm.docStatus eq '30' or kmSmissiveMainForm.docStatus eq '00' }">
			<%--查看详细稿纸 --%>
			<div id='basicInfoTemp' data-dojo-type="mui/form/Template"  style="display:none">
				<div id='basicInfoView' data-dojo-type="dojox/mobile/View"  class="muiFormContent" >
					<c:import url="/km/smissive/mobile/basicInfoView.jsp" charEncoding="UTF-8">
					</c:import>
				</div>
			</div>
			<div id='workFlowView' data-dojo-type="dojox/mobile/View">
				<div class="muiHeaderBasicInfo" data-dojo-type="mui/header/Header" fixed="top" data-dojo-props="height:'4rem'">
					<div class="muiHeaderBasicInfoTitle"><bean:message  bundle="km-smissive" key="mui.kmSmissiveMain.label.processRecords"/></div>
					<div class="muiHeaderBasicInfoBack" onclick="backToDocView(this)">
						<i class="mui mui-close"></i>
					</div>
				</div>
				<div data-dojo-type="mui/view/DocScrollableView">
					<div data-dojo-type="mui/table/ScrollableHContainer">
					  <div data-dojo-type="mui/table/ScrollableHView" class="muiFormContent">
					  <div data-dojo-type="mui/panel/Content" data-dojo-props="title:'',icon:'mui-ul'">
						   <c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId }"/>
								<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain"/>
								<c:param name="formBeanName" value="kmSmissiveMainForm"/>
							</c:import>
					   </div>
					  </div>
					</div>
				</div>
			</div>
		</c:if>	
<c:if  test="${kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.modifyDocNum =='true'}">
<input type="hidden"  name="fdFileNo" value=""/>
</c:if>
	</template:replace>
</template:include>
<script>
$(document).ready(function(){
	<c:if  test="${kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.modifyDocNum =='true'}">
	generateFileNum();
	</c:if>
});
function generateFileNum(){
	   var docNum = document.getElementsByName("fdFileNo")[0];
	    var url="${KMSS_Parameter_ContextPath}km/smissive/km_smissive_main/kmSmissiveMain.do?method=generateNum"; 
		 $.ajax({     
	     type:"post",     
	     url:url,     
	     data:{fdDocNum:docNum.value,fdId:"${kmSmissiveMainForm.fdId}"},    
	     async:false,    //用同步方式 
		 dataType:"json",
		 success:function(results){
	 	   if(results['docNum']!=null){
   		   	   docNum.value = results['docNum'];
   			}
		}    
    });
}
<c:if  test="${kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.modifyDocNum =='true'}">
Com_Parameter.event["submit"].push(function(){
		//操作类型为通过类型 ，才写回编号 
		if(lbpm.globals.getCurrentOperation().operation['isPassType'] == true){			
		    var docNum = document.getElementsByName("fdFileNo")[0];
		    var flag = false;
		    var results;
		    if(""==docNum.value){
	    	generateFileNum();
	    	 var url="${KMSS_Parameter_ContextPath}km/smissive/km_smissive_main/kmSmissiveMain.do?method=saveDocNum"; 
	    	 $.ajax({     
	    	     type:"post",     
	    	     url:url,     
	    	     data:{fdDocNum:docNum.value,fdId:"${kmSmissiveMainForm.fdId}"},    
	    	     async:false,    //用同步方式 
				 dataType:"json",
				 success:function(results){
	    	    	 if(results['flag']=="true"){
	    	    		 flag = true;
	    	    	 }
			    }     
	          });
	    	 if(results['flag']=="false"){
			        alert("生成文档编号不成功");
			        return false;
			    }else{
		    	    return flag;
			    }   	  
		     }else{
				return true;
		    }
		}else{
			return true;
		}
});
</c:if>
</script>
<script type="text/javascript" >
require(['dojo/topic',
         'dojo/ready',
         'dijit/registry',
         'dojox/mobile/TransitionEvent',
         'mui/device/adapter'
         ],function(topic,ready,registry,TransitionEvent,adapter){
	
		//返回主视图
		window.backToDocView=function(obj){
			var opts = {
				transition : 'slide',
				transitionDir:-1,
				moveTo:'scrollView'
			};
			new TransitionEvent(obj, opts).dispatch();
			
		};
		//正文阅读
		window.smissiveViewer = function(){
			var type = "${_sysAttMain.fdContentType}";
			var href = '${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${_sysAttMain.fdId}&viewer=mobilehtmlviewer';
			var downLoadUrl = "${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${_sysAttMain.fdId}";
			var hasViewer = "${hasViewer }";
			if(hasViewer !='true' && type !='img'){
				href = downLoadUrl;
			}
			window.location.href=href;
		}
	});
</script>
<script type="text/javascript">
require(["dojo/parser", "dojo/dom", "dojo/dom-construct", "dijit/registry","dojo/topic","mui/rtf/RtfResize","dojo/_base/lang","mui/dialog/Dialog",'dojo/query'], function(parser, dom, domConstruct, registry, topic,Rtf,lang,Dialog,query){
	window.viewDetailInfo=function(){
		query('.missiveBaseInfo').remove();
		var contentNode = registry.byId("basicInfoTemp");
		var appendTo = document.getElementById("scrollView");
		dialog = new Dialog.claz({ 
			'title':'<bean:message  bundle="km-smissive" key="mui.kmSmissiveMain.label.baseInfo.details"/>',
			'element' : contentNode.domNode.innerHTML,
			'appendTo':appendTo,
			'destroyAfterClose':true,
			'closeOnClickDomNode':true,
			'parseable': true,
			'canClose':true,
			'showClass':'muiDialogElementShow missiveBaseInfo',
			'transform':'bottom',
			'position':'bottom',
			'buttons' : []
		});
		dialog.show();
	};
});
</script>
