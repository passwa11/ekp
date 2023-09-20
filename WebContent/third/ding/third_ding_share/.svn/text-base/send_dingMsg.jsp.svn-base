<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig" %>
<%@ page import="com.landray.kmss.third.ding.oms.DingOmsConfig" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.third.ding.util.DingUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//模块名称
   String modelName= request.getParameter("fdModelName");
   //模块ID
   String modelId=request.getParameter("fdId");
   //推送到钉钉工作通知的地址，默认根据ModelName和id自动构建
   String url = request.getParameter("url");
   //主题标识
   String docSubject = request.getParameter("docSubject");
   //附件key
   String fdKey = request.getParameter("fdKey");
   //内容 contentMap
   String contentMap = request.getParameter("contentMap");

    String hadSendNum = null;
	String dingTopDeptIds = DingUtil.getDingTopDeptIds();
	String names =null;
	boolean isSendDingAll = false; // 是否发送钉钉全员
	if(StringUtil.isNotNull(dingTopDeptIds)){
		names = DingUtil.getDeptNameByDingIds(dingTopDeptIds);
	}else{
		names = "钉钉全员";
		hadSendNum = new DingOmsConfig().getHadSendAllNotifyNums();
		isSendDingAll = true;
	}
%>

<template:include ref="config.profile.edit" sidebar="no">
	
	<template:replace name="head">
		<style>
			.send_content{height: 175px;width: 95%;padding:5px 0px 3px 5px;}
			.Msgtip{padding:0px 0px 3px 20px;}
			.tipColor{
				color:orangered;
			}
		</style>
	</template:replace>
	<template:replace name="content">
	  	   
	   <div class="send_content" >
           <table width="95%" class="tb_normal">
			   <tr>
				   <td  width="20%" class="td_normal_title">${lfn:message('third-ding:third.ding.share2ding.target')}</td>
				   <td  width="80%">
					   <input type="radio" name="sendTargetType" checked="checked" value="specified" onclick="checkTarget()"/>${lfn:message('third-ding:third.ding.share2ding.target.type.specified')}
					   <input type="radio" name="sendTargetType" value="all" onclick="checkTarget()"/>${lfn:message('third-ding:third.ding.share2ding.target.type.all')}
				       <div id="target_content">
						   <xform:address propertyId='sendTargets'  propertyName='value(sendTargets)' mulSelect='true' orgType='ORG_TYPE_ALL' showStatus='edit' style='width:100%;' subject='发送对象' textarea='true'></xform:address>
					   </div>
				   </td>
			   </tr>
			   <tr id="dept_content">
				   <td class="td_normal_title">部门(范围)</td>
				   <td width="80%">
					   <font color="red"><%=names%></font>
					   <c:if test='<%=isSendDingAll%>'>
						   <span>
						   ${lfn:message('third-ding:third.ding.share2ding.target.all.tip.2')}<%=hadSendNum%>${lfn:message('third-ding:third.ding.share2ding.target.all.tip.3')}
						   </span>
					   </c:if>
				   </td>
			   </tr>
			   <tr>
				   <td class="td_normal_title">${lfn:message('third-ding:third.ding.share2ding.application')}</td>
				   <td width="80%">
					   <xform:select property="agentId" showStatus="edit" showPleaseSelect="false" className="selectsgl">
						   <xform:simpleDataSource value="">${lfn:message('third-ding:third.ding.share2ding.def.application')}</xform:simpleDataSource>
						   <xform:beanDataSource serviceBean="thirdDingWorkService" selectBlock="fdAgentid,fdName" />
					   </xform:select>
				   </td>
			   </tr>
		   </table>
	   </div>
		<div class="Msgtip">
				${lfn:message('third-ding:third.ding.share2ding.target.tip')} <br/>
			<span id="specifiedTip" class="tipColor">${lfn:message('third-ding:third.ding.share2ding.target.specified.tip')} <br/></span>
			<span id="allTip">${lfn:message('third-ding:third.ding.share2ding.target.all.tip.1')}

				   </span>
		</div>
		<hr style="height:1px;border:none;border-top:1px solid #d1d1d1;width: 100%;" />
	   <div>
	      <center>
	         <ui:button text="${lfn:message('third-ding:third.ding.share2ding.send')}" onclick="send();"  width="120" height="35"></ui:button>
	      </center>
	          
	   </div>
	   <script>
		   $("#dept_content").hide();
	     function checkTarget(){
			 var sendTargetType = $("input[name='sendTargetType']:checked").val();
	    	if(sendTargetType == 'all'){
	    		$("#target_content").hide();
	    		$("#dept_content").show();
				$("#allTip").addClass('tipColor');
				$("#specifiedTip").removeClass('tipColor');
	    	}else{
				$("#target_content").show();
				$("#dept_content").hide();
				$("#specifiedTip").addClass('tipColor');
				$("#allTip").removeClass('tipColor');
	    	}
	     } 
	     function send(){
			 var sendTargetType = $("input[name='sendTargetType']:checked").val();
	    	 var targets = $("input[name='sendTargets']").val();
	    	 var agentId = $("select[name='agentId']").val();
	    	 var modelName ='<%=modelName%>';
	    	 var fdId ='<%=modelId%>';
	    	 var url ='<%=url%>';
	    	 if(modelName == "" || modelName == null){
	    		 alert("${lfn:message('third-ding:third.ding.share2ding.notNull.modelName')}");
	    		 return;
	    	 }
	    	 if(fdId == "" || fdId == null){
	    		 alert("${lfn:message('third-ding:third.ding.share2ding.notNull.modelId')}");
	    		 return;
	    	 }
    		 if(sendTargetType=='specified' && targets==""){
				 alert("${lfn:message('third-ding:third.ding.share2ding.notNull.target')}")
				 return;
			 }
	    	 console.log("targets:"+targets+"  agentId:"+agentId+" modelName:"+modelName+"  fdId:"+fdId+"  url:"+url);
	    	 send_toding(targets,agentId,sendTargetType);
	     }
	     
	     function send_toding(targets,agentId,sendTargetType){
	    	 $.post('${LUI_ContextPath}/third/ding/ThirdDingShare.do?method=sendMsg',{
		        	'reqUrl':'<%=url%>',
		        	'docSubject':'<%=docSubject%>',
		        	'fdModelName':'<%=modelName%>',
		        	'fdModelId':'<%=modelId%>',
		        	'fdKey':'<%=fdKey%>',
		        	'contentMap':'<%=contentMap%>',
		        	'sendAll':sendTargetType,
		        	'targets':targets,
		        	'agentId':agentId		        	
		        },function(result){
		        	console.log(result);
		        	var data = $.parseJSON(result);
					if(data.error == 0){
						successTip();
					}else{
						failTip(data.error);
					}
				    $("#allNum").html($("#allNum").val()+1)
		        	window.$dialog.hide("sendMsg");
		        });
	     }
		 function  successTip() {
	 		seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
				dialog.success("${lfn:message('third-ding:third.ding.share2ding.send.success')}", '.lui_accordionpanel_float_contents');
			});
		}
		function failTip(err) {
	 		seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
	 			dialog.failure(err);
	 		});
	 	}
	   </script>
	</template:replace>
	
</template:include>


