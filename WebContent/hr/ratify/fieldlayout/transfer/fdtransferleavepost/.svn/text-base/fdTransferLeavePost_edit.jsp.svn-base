<%-- 入职部门 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "45%");%>
<div id="_fdTransferLeavePost" valField="fdTransferLeavePostIds" xform_type="address">
  <%-- <xform:address 
            isLoadDataDict="false"
            showStatus="edit"
            mobile="${param.mobile eq 'true'? 'true':'false'}"
			required="true" style="<%=parse.getStyle()%>"
			subject="${lfn:message('hr-ratify:hrRatifyTransfer.fdTransferLeavePosts')}"
			propertyId="fdTransferLeavePostIds" propertyName="fdTransferLeavePostNames"
			orgType='ORG_TYPE_POST' className="input" mulSelect="false" htmlElementProperties="id='fdTransferLeavePost'">
   </xform:address> --%>
   <xform:select property="fdTransferLeavePostIds" isLoadDataDict="false" showStatus="edit" mobile="${param.mobile eq 'true'? 'true':'false'}" required="true" 
   			style="<%=parse.getStyle()%>" subject="${lfn:message('hr-ratify:hrRatifyTransfer.fdTransferLeavePosts')}" className="input" mul="false"
   			 htmlElementProperties="id='fdTransferLeavePost'" onValueChange="switchTransferLeavePost">
   </xform:select>
   <xform:text
            isLoadDataDict="false"
            showStatus="noShow"
            mobile="${param.mobile eq 'true'? 'true':'false'}" style="display:none"
			property="fdTransferLeavePostNames"
			className="input" htmlElementProperties="id='fdTransferLeavePostName'">
   </xform:text>
   <c:if test="${param.mobile eq 'false'}">
   <script type="text/javascript">
	   window.switchTransferLeavePost = function(value,obj){
			if(value){
				var text = $("#fdTransferLeavePost option:selected").text();
				$("[name='fdTransferLeavePostNames']").val(text);
			}
		}
   </script>
   </c:if>
     <c:if test="${param.mobile eq 'true'}">
   <script type="text/javascript">
  	require(["dijit/registry"],function(registry){
	   window.switchTransferLeavePost = function(value,obj){
			if(value){
				var text = obj.text;
				var fdTransferLeavePostName = registry.byId('fdTransferLeavePostName');
				fdTransferLeavePostName._setValueAttr(text);
			}
		}
  	});
   </script>
   </c:if>
   </div>
   