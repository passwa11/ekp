<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendCategory"%>
<%@ page import="com.landray.kmss.sys.attend.util.CategoryUtil" %>
<%
	if(request.getParameter("fdCategoryId") != null) {
		SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(request.getParameter("fdCategoryId"));
		pageContext.setAttribute("sysAttendCategory", sysAttendCategory);
	}
%>
<template:include ref="default.dialog">
	<template:replace name="title">
		${ lfn:message('sys-attend:sysAttendMainExc.select.exception') }
	</template:replace>
	<template:replace name="content">
	<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<div class="lui-attend-bus-btn-container">
			<c:forEach items="${sysAttendCategory.busSetting}" var="busSetting">
				<div class="lui-attend-bus-btn" onclick="addReivew('${busSetting.fdTemplateId }');">
					${busSetting.fdBusName }
				</div>
			</c:forEach>
			<div class="lui-attend-bus-btn" onclick="addExc();">
				${ lfn:message('sys-attend:sysAttendMainExc.exception') }
			</div>
		</div>
	<script type="text/javascript">
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
		window.addReivew = function addReivew(fdTemplateId) {
			window.open('${LUI_ContextPath }/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=' + fdTemplateId, '_blank');
			$dialog.hide(null);
		};
		window.addExc = function addExc() {

			window.addExc_loading = dialog.loading();
			var url =  "${LUI_ContextPath }/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=isAllowPatch&fdAttendMainId=${param.fdAttendMainId}";
			$.ajax({
			   type: "GET",
			   url: url,
			   dataType: "json",
			   async:false,
			   success: function(data){
				   addExc_loading && addExc_loading.hide();
				   if(data) {
					  if(data.status == 0 && data.msg){
						  var text = '';
						  switch(data.msg) {
						  case 'notAllow' : text = "${ lfn:message('sys-attend:sysAttendMainExc.error.notAllow') }";break;
						  case 'overdue' : text = "${ lfn:message('sys-attend:sysAttendMainExc.error.overdue') }";break;
						  case 'overTimes': text = "${ lfn:message('sys-attend:sysAttendMainExc.error.overdue') }";break;
						  default : 'error';
						  }
						  dialog.alert(text);
						  return;
					  } else{
						  //补卡超过次数按事假半天计算
						  var text = '';
						  var submit=true;
						  var num=data.count;
						  switch(data.msg) {
							  case 'overTimes':  text = "${ lfn:message('sys-attend:sysAttendMainExc.error.overdue') }";break;
							  default : 'error';
						  }
						  //您已累计提交签卡'+num+'次，
		                        dialog.confirm(' 当月可提交三次签卡，超次数一次记事假半天，请注意提交', function(isOk) {
		                            if(isOk) {
		                            	openExc();
		                            }else{
		                            	submit=false;
		                            }
		                        });
		                        if(!submit){
		                        	return;
		                        }
					  }
				   }
				  
			   },
			   error : function(e){
				   addExc_loading && addExc_loading.hide();
				   console.error(e);
				   openExc();
			   }
			});
		};
		
		var openExc = function() {
			window.open('${LUI_ContextPath }/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=addExc&fdAttendMainId=${param.fdAttendMainId}','_blank');
			$dialog.hide(null);
		};
	});
	</script>
	</template:replace>
</template:include>