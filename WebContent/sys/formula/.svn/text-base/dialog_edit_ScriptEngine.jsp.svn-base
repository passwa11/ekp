<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<style type="text/css">
body{margin:0px}
.bigButton {
	font-size: 18px;
	font-weight: bold;
	text-transform: capitalize;
	width: 60px;
	font-family: "Courier New", Courier, mono;
	cursor: pointer;
}
.smallButton {
	font-size: 14px;
	font-weight: normal;
	width: 50px;
	cursor: pointer;
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
<script src="<c:url value="/sys/formula/formula_edit_ScriptEngine.js"/>"></script>
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
			<iframe id="treeiframe" height=100% style="width:220px;" frameborder=0 scrolling="auto" src='dialog_tree_ScriptEngine.jsp'></iframe>
		</td>
		<td width="5px">&nbsp;</td>
		<td valign="center">
		<div class="txttitle">新公式定义器(基于ScriptEngine)</div><br>
		<table class="tb_normal" width="98%">
			<tr>
				<td colspan="2">
					<textarea id="expression" name="expression" style="width:100%;height:160px;"></textarea>
				</td>
			</tr>
			<tr id="operator">
			  <td><table width="100%" class="tb_normal" border="0">
		        <tr align="center">
		          <td width="12%" class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('1');" value="1"></td>
		          <td width="12%" class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('2');" value="2"></td>
		          <td width="12%" class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('3');" value="3"></td>
		          <td width="12%" class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('/', ' ');" value="/"></td>
		          <td width="12%" class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('(');" value="("></td>
		          <td width="12%" class="tdOpr"><input type="button" class="bigButton" onclick="opFormula(')', ' ');" value=")"></td>
		          <td width="14%" class="tdBl"><input type="button" class="bigButton" onclick="opFormula('&&', ' ');" value="&&" title="<bean:message bundle="sys-formula" key="formula.op.and"/>"></td>
		          <td width="14%" class="tdBl"><input type="button" class="bigButton" onclick="opFormula('||', ' ');" value="||" title="<bean:message bundle="sys-formula" key="formula.op.or"/>"></td>
		        </tr>
		        <tr align="center">
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('4');" value="4"></td>
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('5');" value="5"></td>
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('6');" value="6"></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('*', ' ');" value="*"></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('<', ' ');" value="&lt;"></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('>', ' ');" value="&gt;"></td>
		          <td class="tdBl"><input type="button" class="bigButton" onclick="opFormula('!', ' ');" value="!" title="<bean:message bundle="sys-formula" key="formula.op.not"/>"></td>
		          <td class="tdBl"><input type="button" class="bigButton" onclick="opFormula('!=', ' ');" value="!=" title="<bean:message bundle="sys-formula" key="formula.op.notEq"/>"></td>
		        </tr>
		        <tr align="center">
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('7');" value="7"></td>
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('8');" value="8"></td>
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('9');" value="9"></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('-', ' ');" value="-"></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('<=', ' ');" value="&lt;="></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('>=', ' ');" value="&gt;="></td>
		          <td class="tdBl"><input type="button" class="bigButton" onclick="opFormula(';', ' ');" value=";"></td>
		          <td class="tdBl"><input type="button" class="bigButton" onclick="opFormula(',', ' ');" value=","></td>
		        </tr>
		        <tr align="center">
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('0');" value="0"></td>
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('.');" value="."></td>
		          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('%', ' ');" value="%" title="<bean:message bundle="sys-formula" key="formula.op.percen"/>"></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('+', ' ');" value="+"></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('=', ' ');" value="=" title="<bean:message bundle="sys-formula" key="formula.op.set"/>"></td>
		          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('==', ' ');" value="==" title="<bean:message bundle="sys-formula" key="formula.op.eq"/>"></td>
		        </tr>
		      </table>
		      </td>
		    </tr>
		    <!-- 变量区域 -->
		    <tr id="variable" style="display:none;">
		    	<td width="10%"><bean:message bundle="sys-formula" key="formula.simulaVars"/>:</td>
		    	<td width="90%">
		    		<table width="100%" class="tb_normal" border="0">
		    			
		    		</table>
		    	</td>
		    </tr>
		    <!-- 结果区域 -->
		    <tr id="result" style="display:none;">
		    	<td width="10%"><bean:message bundle="sys-formula" key="formula.simulaResult"/>:</td>
		    	<td width="90%">
		    		<table width="100%" class="tb_normal" border="0">
		    			<textarea id="resultArea" name="resultArea" style="width:100%;height:80px;"></textarea>
		    		</table>
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
					<br/>
	        		<input type=button value="<bean:message key="button.ok"/>" onclick="validateFormulaByJS(writeBack);">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type=button value="<bean:message bundle="sys-formula" key="button.clear"/>" 
						onclick="clearExp();">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type=button value="<bean:message bundle="sys-formula" key="button.check"/>" 
						onclick="validateFormulaByJS(validateMessage);">&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- 公式模拟按钮 -->
					<input type=button value="<bean:message bundle="sys-formula" key="button.simulate"/>"
						onclick="simulateFormula(this);">&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- 开始模拟按钮 -->
					<input id="startSimulaFormula" style="display:none;" type="button" value="<bean:message bundle="sys-formula" key = "button.startSimulate"/>"
						onclick="startSimulateFormula(this);">
					<input type="button" value="<bean:message key="button.cancel"/>" onClick="window.close();">&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="<bean:message bundle="sys-formula" key="button.help"/>" 
						onClick="Com_OpenWindow('<c:url value="/sys/formula/help/formula_scriptEngine.html"/>', '_blank');">
		    	</td>
		    </tr>
		</table>		
		</td>
	</tr>
</table>
</body>
</html>