<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTagNew"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextareaGroupTagNew.isLangSuportEnabled());
%>
<style type="text/css">
body{margin:0px}
.calcBtn {
	display: block;
	margin: 0;
	padding: 5px 0;
	width: 100%;
	color: #333;
	font-size: 18px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	text-transform: capitalize;
	-ms-touch-action: manipulation;
			touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
		 -moz-user-select: none;
			-ms-user-select: none;
					user-select: none;
  background-image: none;
  background-color: #fff;
	border: 1px solid transparent;
	border-radius: 0;
	outline: 0;
	transition-duration: .3s;
	-wekbit-box-sizing: content-box;
	        box-sizing: content-box;
}
.calcBtn:hover,
.calcBtn:focus{
  text-decoration: none;
	color: #fff;
  background-color: #4285f4;
  border-color: #4285f4;
}
.calcBtn:active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .15);
          box-shadow: inset 0 3px 5px rgba(0, 0, 0, .15);
}
.tb_normal td.tdNumber,
.tb_normal td.tdOpr,
.tb_normal td.tdBl {
	padding: 0;
	text-align: center;
}
.mediumButton{}
.smallButton {
	font-size: 14px;
	font-weight: normal;
	width: 50px;
	cursor: pointer;
}
.resultBtn{
	font-size: 14px;
	padding: 2px 10px;
	border-radius: 4px;
	border-color: #d2d2d2;
}
.resultBtn-group{
	padding: 10px 0;
	text-align: center;
	font-size: 0;
	*white-space: -1px;
}
.resultBtn-group > .resultBtn{
	margin: 0 5px;
	width: auto;
	display: inline-block;
}
.tdNumber{
	bgcolor:#FFE6E6;
}
.tdOpr{
	bgcolor:#CCFFCC;
}
.tdBl{
	bgcolor:#FFFFCC;
}
.txttitleTop{
	margin-top:50px;
}
</style>
<script type="text/javascript">
Com_IncludeFile("data.js");
Com_IncludeFile("jquery.js");
Com_IncludeFile("dialog.js");
Com_IncludeFile("calendar.js");
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("validation.js|validation.jsp|validator.jsp|plugin.js");
Com_AddEventListener(window,"load",function(){
	$KMSSValidation = $KMSSValidation();
	$("textarea[name^='expression']").focus(function(){
		focusTextArea = this;
	});
});
var focusTextArea = null;
var message_unknowfunc = '<bean:message bundle="sys-formula" key="validate.unknowfunc"/>';
var message_unknowvar = '<bean:message bundle="sys-formula" key="validate.unknowvar"/>';
var message_wait = '<bean:message bundle="sys-formula" key="validate.wait"/>';
var message_eval_error = '<bean:message bundle="sys-formula" key="validate.failure.evalError"/>';
var message_insert_formula = '<bean:message bundle="sys-formula" key="formula.link.insertFormula"/>';
</script>
<script src="<c:url value="/sys/lbpmservice/node/common/node_custom_notify_formula_edit.js"/>"></script>
</head>
<body>
<script type="text/javascript">
//某些浏览器，高度设为100%,不继承父类高度？导致样式错乱，这里手动设置一下
Com_AddEventListener(window, "load", function(){
	if(window.innerHeight){
		document.getElementById("treeiframe").setAttribute("height",window.innerHeight-3);
	}else{
		var winHeight = Math.max(document.documentElement.clientHeight, document.body.clientHeight);
		document.getElementById("treeiframe").setAttribute("height",Math.max(winHeight, document.body.scrollHeight)-3);
	}
});
</script>
<table cellpadding=0 cellspacing=0 width="100%" style="height:99%; border-collapse:collapse;border: 0px #303030 solid;">
	<tr>
		<td valign="top" style="width:220px; border:#303030 solid; border-width:0px 1px 0px 0px; border-collapse:collapse;">
			<iframe id="treeiframe" height=100% style="width:220px;" frameborder=0 scrolling="auto" src='node_custom_notify_dialog_tree.jsp'></iframe>
		</td>
		<td width="5px">&nbsp;</td>
		<td valign="top">
		<div class="txttitle txttitleTop"><bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.content"/></div><br>
		<table class="tb_normal" width="98%">
			<tr>
				<td colspan="2">
					<%-- <textarea id="expression" name="expression" style="width:100%;"></textarea>
					<xlang:lbpmlangArea property="expression" style="width:100%;" langs=""/> --%>
					<c:if test="${!isLangSuportEnabled }">
						<textarea id="expression" name="expression" style="width:100%;"></textarea>
					</c:if>
					<c:if test="${isLangSuportEnabled }">
						<xlang:lbpmlangAreaNew property="expression" id="expression" style="width:100%;" langs=""/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<sapn style="color:red;"><bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.customNotify.instructions"/></sapn>
				</td>
			</tr>
		    <tr>
		    	<td align=center colspan="2">
		    		<div id="funcDetail" style="display:none">
			    		<table width="100%" class="tb_normal" border="0">
			    			<tr>
			    				<td width="15%"><bean:message bundle="sys-formula" key="formula.label.funcDesc"/></td>
			    				<td><div id="desc" style="text-align: left;"></div></td>
			    			</tr>
			    			<tr>
			    				<td width="15%"><bean:message bundle="sys-formula" key="formula.label.commonFormula"/></td>
			    				<td><div id="example" style="text-align: left;"></div>
			    				<a href="javascript:void(0)" class="com_btn_link" onClick="Com_OpenWindow('<c:url value="/sys/formula/formula_examples.jsp"/>', '_blank');">
			    				<bean:message bundle="sys-formula" key="formula.link.moreExample"/></td>
			    			</tr>
			    		</table>
			    	</div>
		    		<div id="expSummary" style="text-align: left;display:none"></div>
			    	<div class="resultBtn-group">
	      				<input class="calcBtn resultBtn" type=button value="<bean:message key="button.ok"/>" onclick="validateFormula(writeBack);">
						<input class="calcBtn resultBtn" type=button value="<bean:message bundle="sys-formula" key="button.clear"/>" 
							onclick="clearExp();">
						<input class="calcBtn resultBtn" type="button" value="<bean:message key="button.cancel"/>" onClick="window.close();">
					</div>
		    	</td>
		    </tr>
		</table>		
		</td>
	</tr>
</table>
</body>
</html>