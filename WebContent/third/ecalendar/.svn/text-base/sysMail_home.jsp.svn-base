<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.third.ecalendar.model.EcalendarBindData"%>
<%@page import="com.landray.kmss.third.ecalendar.utils.ExchangeUtil"%>

<%
	EcalendarBindData data = ExchangeUtil.getDataByUser();
	String showMailFlag = "0";
	if (null != data && null != data.getFdShowExchangeMailNum() && data.getFdShowExchangeMailNum()) {
		showMailFlag = "1";
	}
	if (null != data && null != data.getFdShowExchangeMailNum() && !data.getFdShowExchangeMailNum()) {
		showMailFlag = "2";
	}
	request.setAttribute("showMailFlag", showMailFlag);
	String enabledExMail = ExchangeUtil.getEXCHANGE_MAIL();	//是否启动exchange邮箱
	String enabledExMailFlag = "0";
	if (null != enabledExMail && enabledExMail.equals("true")) {
		enabledExMailFlag = "1";
	}
	request.setAttribute("enabledExMailFlag", enabledExMailFlag);
%>

<script type="text/javascript">
Com_IncludeFile("jquery.js");

function getMailNum(url){
	$.ajax(
	{
		url : url,
		dataType : 'json',
		data : '',
		jsonp : 'callback',
		success : function(result) {
			setMailSummaryInfo(result);
		},
		timeout : 11000
	});
}

function setMailSummaryInfo(data){
	//alert(data);
	//debugger;
	var div_num_zero = $("#div_num_zero");
	var div_num_nozero = $("#div_num_nozero");
	var div_num_err = $("#div_num_err");
	if(data.errMsg){
		div_num_zero.hide();
		div_num_nozero.hide();
		div_num_err.show();
		div_num_err.text("获取未读邮件数出错："+data.errMsg);
	}else{
		var num = data.num;
		//debugger;
		if(num == "0"){
			div_num_zero.show();
			div_num_nozero.hide();
			div_num_err.hide();
		}else{
			$("#num_block").text(" "+num);
			div_num_zero.hide();
			div_num_nozero.show();
			div_num_err.hide();
		}
	}

	//$("#username").val(data.f_user);
	//$("#password").val(data.f_pass);
	//$("#destination").val(data.f_owa);
	//$("#login_form").attr("action", data.f_page);
}

window.onload = function(){
	var showMailFlagVar = '${showMailFlag}';
	var enabledExMailFlag = '${enabledExMailFlag}';
	if (null != showMailFlagVar && showMailFlagVar == "1" && enabledExMailFlag == "1") {
		var url = '<c:url value="/third/ecalendar/ecalendar.do?method=getNotReadMail"/>';
		getMailNum(url);
	}
}

function login() {
	document.logonForm.submit();
}
</script>
<%-- 
<form id="login_form" name="logonForm" action="${f_page}" method="post" target="_blank" autocomplete="off">
	<input type="hidden" name="destination" id="destination" value="${f_owa }"/>
	<input type="hidden" name="flags" value="0"/>
	<input type="hidden" name="forcedownlevel" value="0"/> 
	<input type="hidden" id="rdoPblc" name="trusted" value="0"/>
	<input type="hidden" id="username" name="username" value="${f_user}" />
	<input type="hidden" id="password" name="password" value="${f_pass}" />
</form>
--%>
<div id="div_num_zero" style="display:none;">
	<bean:message bundle="sys-notify" key="sysMail.home.you" /><font style="color:#FF6600;"><b>
	<bean:message bundle="sys-notify" key="sysMail.home.notHave" /></b></font>
	<bean:message bundle="sys-notify" key="sysMail.home.rec" />
	<a href="<%=request.getContextPath() %>/third/ecalendar/ecalendar.do?method=sso" target="_blank" 
	style="text-decoration: underline;">
	<bean:message bundle="sys-notify" key="sysNotify.type.email" />
	</a>
</div>
<div id="div_num_nozero" style="display:none;font-size: 14px;">
	<bean:message bundle="sys-notify" key="sysMail.home.youHave"  />
	<font style="color:#FF6600;"><b id="num_block"></b></font>
	<bean:message bundle="sys-notify" key="sysMail.home.new"  />
	<a href="<%=request.getContextPath() %>/third/ecalendar/ecalendar.do?method=sso" target="_blank" 
	style="text-decoration: underline;">
	<bean:message bundle="sys-notify" key="sysNotify.type.email" />
	</a>	
</div>
<div id="div_num_err" style="display:none;">
	
</div>

<%
	if (enabledExMailFlag.equals("1") && showMailFlag.equals("0")) {
		//启动exchange邮箱，但是没有配置显示帐号信息
		%>
			<div id="div_num_nosetting" style="font-size: 14px;">
				<a target="_blank" href="<%=request.getContextPath() %>/third/ecalendar/ecalendar_bind_data/ecalendarBindData.do?method=edit" style="text-decoration: underline;">
					<bean:message bundle="third-ecalendar" key="ecalendarBindData.error.remind" />
				</a>	
			</div>
		<%
	}
%>