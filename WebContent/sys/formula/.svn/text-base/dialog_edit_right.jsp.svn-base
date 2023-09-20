<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>   
<div class="txttitle txttitleTop"><bean:message bundle="sys-formula" key="formula.title"/></div><br>
<table class="tb_normal" width="98%">
	<tr>
		<td colspan="2">
			<textarea id="expression" name="expression" style="width:100%;height:160px;"></textarea>
		</td>
	</tr>
	<tr id="operator">
	  <td><table width="100%" class="tb_normal" border="0">
        <tr align="center">
          <td width="12%" class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('1');" value="1"></td>
          <td width="12%" class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('2');" value="2"></td>
          <td width="12%" class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('3');" value="3"></td>
          <td width="12%" class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('/', ' ');" value="/"></td>
          <td width="12%" class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('(');" value="("></td>
          <td width="12%" class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula(')', ' ');" value=")"></td>
          <td width="14%" class="tdBl"><input type="button" class="calcBtn" onclick="opFormula('&&', ' ');" value="&&" title="<bean:message bundle="sys-formula" key="formula.op.and"/>"></td>
          <td width="14%" class="tdBl"><input type="button" class="calcBtn" onclick="opFormula('||', ' ');" value="||" title="<bean:message bundle="sys-formula" key="formula.op.or"/>"></td>
        </tr>
        <tr align="center">
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('4');" value="4"></td>
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('5');" value="5"></td>
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('6');" value="6"></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('*', ' ');" value="*"></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('<', ' ');" value="&lt;"></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('>', ' ');" value="&gt;"></td>
          <td class="tdBl"><input type="button" class="calcBtn" onclick="opFormula('!', ' ');" value="!" title="<bean:message bundle="sys-formula" key="formula.op.not"/>"></td>
          <td class="tdBl"><input type="button" class="calcBtn" onclick="opFormula('!=', ' ');" value="!=" title="<bean:message bundle="sys-formula" key="formula.op.notEq"/>"></td>
        </tr>
        <tr align="center">
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('7');" value="7"></td>
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('8');" value="8"></td>
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('9');" value="9"></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('-', ' ');" value="-"></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('<=', ' ');" value="&lt;="></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('>=', ' ');" value="&gt;="></td>
          <td class="tdBl"><input type="button" class="calcBtn" onclick="opFormula(';', ' ');" value=";"></td>
          <td class="tdBl"><input type="button" class="calcBtn" onclick="opFormula(',', ' ');" value=","></td>
        </tr>
        <tr align="center">
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('0');" value="0"></td>
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('.');" value="."></td>
          <td class="tdNumber"><input type="button" class="calcBtn" onclick="opFormula('%', ' ');" value="%" title="<bean:message bundle="sys-formula" key="formula.op.percen"/>"></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('+', ' ');" value="+"></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('=', ' ');" value="=" title="<bean:message bundle="sys-formula" key="formula.op.set"/>"></td>
          <td class="tdOpr"><input type="button" class="calcBtn" onclick="opFormula('==', ' ');" value="==" title="<bean:message bundle="sys-formula" key="formula.op.eq"/>"></td>
          <td class="tdBl" colspan="2"><input type="button"  class="calcBtn" style="width:100px" onclick="opFormula('.equals( )', ' ');" value="Equals" title="<bean:message bundle="sys-formula" key="formula.op.objEq"/>"></td>
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

	    	<div class="resultBtn-group">
      		<input class="calcBtn resultBtn" type=button value="<bean:message key="button.ok"/>" onclick="validateFormula(writeBack);">
					<input class="calcBtn resultBtn" type=button value="<bean:message bundle="sys-formula" key="button.clear"/>" 
						onclick="clearExp();">
					<input class="calcBtn resultBtn" type=button value="<bean:message bundle="sys-formula" key="button.check"/>" 
						onclick="validateFormula(validateMessage);">
					<!-- 公式模拟按钮 -->
					<input class="calcBtn resultBtn" type=button value="<bean:message bundle="sys-formula" key="button.simulate"/>"
						onclick="simulateFormula(this);">
					<!-- 开始模拟按钮 -->
					<input class="calcBtn resultBtn" id="startSimulaFormula" style="display:none;" type="button" value="<bean:message bundle="sys-formula" key = "button.startSimulate"/>"
						onclick="startSimulateFormula(this);">
					<input class="calcBtn resultBtn" type="button" value="<bean:message key="button.cancel"/>" onClick="window.close();">
					<input class="calcBtn resultBtn" type="button" value="<bean:message bundle="sys-formula" key="button.help"/>" 
				onClick="Com_OpenWindow('<c:url value="/sys/formula/formula_help.jsp"/>', '_blank');">
	    	</div>

    	</td>
    </tr>
</table>	