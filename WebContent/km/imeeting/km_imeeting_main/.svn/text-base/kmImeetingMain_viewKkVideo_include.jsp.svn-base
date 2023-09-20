<%@page import="com.landray.kmss.km.imeeting.util.AliMeetingUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.util.KKUtil"%>
<%
	String serviceType = AliMeetingUtil.getServiceType();
	request.setAttribute("serviceType", serviceType);
%>

<c:choose>
	<c:when test="${serviceType eq '1'}">
		<c:if test="${isBegin==true and isEnd==false and kmImeetingMainForm.fdIsVideo eq 'true' and kmImeetingMainForm.docStatus ne '41' and canEnterAliMeeting eq 'true' and not empty fdMeetingCode}">	
			<div id="ImeetingEnterBtn"  onclick="joinAliyunMeeting();">
				<p>视频会议</p>
			</div>
		</c:if>	
	</c:when>
	<c:otherwise>
		<c:if test="${isBegin==true and isEnd==false and kmImeetingMainForm.fdIsVideo eq 'true' and kmImeetingMainForm.docStatus ne '41' and canEnterKkVedio eq 'true' and not empty roomId}">	
			<div id="ImeetingEnterBtn"  onclick="enterVedioMeeting('${roomId}');">
				<p>视频会议</p>
			</div>
		</c:if>
	</c:otherwise>
</c:choose>
<%
	request.setAttribute("fdKkVideoUrl", KKUtil.getKKUrlHttps());

%>

<script type="text/javascript">
seajs.use(['lui/dialog'], function (dialog) {
	window.dialog = dialog;
});

function enterVedioMeeting(roomId){
	if(roomId){
		var videoUrl = "${fdKkVideoUrl}/serverj/alimeeting/enter.ajax?roomId=" + roomId;
		window.open(videoUrl,"_blank");
	}else{
		dialog.alert("无法进入会议，请确认会议是否已经同步到KK中");
	}
};

//加入阿里云视频会议
window.joinAliyunMeeting = function() {
	if (navigator.userAgent.indexOf('Chrome') > -1) {
		window.open('<c:url value="/third/alimeeting/third_alimeeting/joinMeeting.jsp?fdMeetingId=${kmImeetingMainForm.fdId}"></c:url>', "_blank");
	} else {
		dialog.alert("仅谷歌浏览器可发起/参与会议！");
	}
}

function syncVedioMeeting(){
	var url = '<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=addSyncToKk"/>';
	$.ajax({
		url: url,
		type: 'post',
		dataType: 'json',
		data:{fdId:'${kmImeetingMainForm.fdId}'},
		error: function(data){
		},
		success: function(data){
	    	 if(data['success'] =='false'){
	    		 dialog.failure('同步失败!');
	    	 }else{
	    		 dialog.success('同步成功!');
	    	 }
		}
   });
};
</script>	
		
