<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%> 
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
Com_AddEventListener(window,"load",function(){$KMSSValidation = $KMSSValidation();});
var message_unknowfunc = '<bean:message bundle="sys-formula" key="validate.unknowfunc"/>';
var message_unknowvar = '<bean:message bundle="sys-formula" key="validate.unknowvar"/>';
var message_wait = '<bean:message bundle="sys-formula" key="validate.wait"/>';
var message_eval_error = '<bean:message bundle="sys-formula" key="validate.failure.evalError"/>';
var message_insert_formula = '<bean:message bundle="sys-formula" key="formula.link.insertFormula"/>';
</script>
<script src="<c:url value="/sys/formula/formula_edit.js"/>"></script>
</head>