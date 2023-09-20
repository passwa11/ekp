<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<style type="text/css">
body{margin:0px}
.bigButton {
	font-size: 18px;
	font-weight: bold;
	text-transform: capitalize;
	width: 60px;
	font-family: "Courier New", Courier, mono;
	cursor: hand;
}
.smallButton {
	font-size: 14px;
	font-weight: normal;
	width: 50px;
	cursor: hand;
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
Com_IncludeFile("data.js|jquery.js");
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
var message_unknowfunc = "<bean:message bundle="sys-formula" key="validate.unknowfunc"/>";
var message_unknowvar = "<bean:message bundle="sys-formula" key="validate.unknowvar"/>";
var message_wait = "<bean:message bundle="sys-formula" key="validate.wait"/>";
var message_eval_error = "<bean:message bundle="sys-formula" key="validate.failure.evalError"/>";
</script>
<script src="<c:url value="/sys/formula/formula_edit.js"/>"></script>
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
<table cellpadding=0 cellspacing=0 style="height:100%; border-collapse:collapse;border: 0px #303030 solid;">
	<tr>
		<td valign="top" style="border:#303030 solid; border-width:0px 1px 0px 0px; border-collapse:collapse;">
			<iframe id="treeiframe" width=200 height=100% frameborder=0 scrolling=auto src='./relationFormulaDialog_tree.jsp'></iframe>
		</td>
		<td width="10px">&nbsp;</td>
		<td width="100%" valign="top">
			<div class="txttitle"><bean:message bundle="sys-formula" key="formula.title"/></div><br>
			<table class="tb_normal" width="100%">
				<tr width="100%">
					<td width="100%">
						<input type="hidden" name="idField" id="idField" value="" />
						<textarea id="expression" name="expression"
								style="width:100%;height:160px;"></textarea>
					</td>
				</tr>
					<tr>
	  <td><table width="100%" class="tb_normal" border="0">
        <tr align="center">
                   <td width="12%" class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('1');" value="1"></td>
          <td width="12%" class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('2');" value="2"></td>
          <td width="12%" class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('3');" value="3"></td>
          <td width="12%" class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('/', ' ');" value="/"></td>
        
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
        </tr>
        <tr align="center">
          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('7');" value="7"></td>
          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('8');" value="8"></td>
          <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('9');" value="9"></td>
          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('-', ' ');" value="-"></td>
          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('<=', ' ');" value="&lt;="></td>
          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('>=', ' ');" value="&gt;="></td>
        </tr>
        <tr align="center">
             <td class="tdNumber"><input type="button" class="bigButton" onclick="opFormula('0');" value="0"></td>
                 <td></td>
           <td></td>
          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('+', ' ');" value="+"></td>
          <td class="tdOpr"><input type="button" class="bigButton" onclick="opFormula('==', ' ');" value="==" title="<bean:message bundle="sys-formula" key="formula.op.eq"/>"></td>
   
             <td></td>
        </tr>
      </table>
      </td>
    </tr>
			    <tr>
					<td align=center>
						<div id="expSummary" style="text-align: left;"></div>
						<br>
			        		<input type=button value="<bean:message key="button.ok"/>" onclick="validateFormula_self(writeBack_self);">&nbsp;&nbsp;&nbsp;&nbsp;
							<input type=button value="<bean:message bundle="sys-formula" key="button.clear"/>" 
								onclick="clearExpress();">&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="<bean:message key="button.cancel"/>" onClick="window.close();">&nbsp;&nbsp;&nbsp;&nbsp;
							
					</td>
				</tr>
			</table>		
		</td>
	</tr>
</table>
<script type="text/javascript">
   function writeBack_self(rtnVal){
	   var success = rtnVal.GetHashMapArray()[0].success;
		if(success=="1"){
			dialogObject.rtnData = [validateResult];
			setTimeout(function(){dialogObject.AfterShow();close();},100);
		}else if (success=="0"){
			if(<%-- confirm(rtnVal.GetHashMapArray()[0].confirm)--%>
					true){
				dialogObject.rtnData = [validateResult];
				setTimeout(function(){dialogObject.AfterShow();close();},100);
			}
			else{
				validateResult = null;
			}
		}else{
			validateResult = null;
			<%-- 
			alert(rtnVal.GetHashMapArray()[0].message);
			--%>
		}
	   }
   function validateFormula_self(action){
	   var expressionValue = document.getElementById('expression').value;
	   if(validateResult!=null){
			alert(message_wait);
			return;
		}
		if (Com_Trim(expressionValue) == '') {
			dialogObject.rtnData = [{name:'', id:''}];
			setTimeout(function(){dialogObject.AfterShow();close();},100);
			return true;
		}
		// 是否必填选中
		var isRequiredObjs = document.getElementsByName("isRequired");
		//var idField = document.getElementById("idField").value;
		//var fieldJson = "{\"idField\" : \""+idField.replace(/\\$/g, "")+"\", "
		//	+"\"nameField\" : \""+expressionValue.replace(/\\$/g, "")+"\", "
		//	+"\"isRequired\" : \""+isRequired+"\"}";
		//转换表达式
		var scriptIn =expressionValue;// replaceSymbol(idField.replace(/\\$/g, "") + "|" + expressionValue + "|" + isRequired);
		var preInfo = {rightIndex:-1};
		var scriptOut = "";
		
		for (var nxtInfo = getNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = getNextInfo(scriptIn, nxtInfo)) {
			var varId = getVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
			if(varId==null){
				alert((nxtInfo.isFunc ? message_unknowfunc : message_unknowvar) + nxtInfo.varName);
				return;
			}
			//alert(scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex));
			scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "$" + varId + "$";
			preInfo = nxtInfo;
		}
		scriptOut += scriptIn.substring(preInfo.rightIndex+1);
		
		
		//提交到后台进行校验
		var info = {};
		info["script"] = scriptOut;
		info["funcs"] = dialogObject.formulaParameter.funcs;
		info["model"] = dialogObject.formulaParameter.model;
		info["returnType"] = dialogObject.formulaParameter.returnType;
		var varInfo = dialogObject.formulaParameter.varInfo;
		for(var i=0; i<varInfo.length; i++){
			info[varInfo[i].name+".type"] = varInfo[i].type;
			info[varInfo[i].name+".label"] = varInfo[i].label;
		}
		var data = new KMSSData();
		data.AddHashMap(info);
		
		data.SendToBean("sysRelationFormulaValidate", action);
		validateResult = {name:scriptIn, id:scriptOut};
    }



  //获取下个变量位置的信息
    function getNextInfo(script, preInfo){
    	var rtnVal = {};
    	rtnVal.leftIndex = script.indexOf("$", preInfo==null?0:preInfo.rightIndex+1);
    	if(rtnVal.leftIndex==-1)
    		return null;
    	rtnVal.rightIndex = script.indexOf("$", rtnVal.leftIndex+1);
    	if(rtnVal.rightIndex==-1)
    		return null;
    	rtnVal.varName = script.substring(rtnVal.leftIndex + 1, rtnVal.rightIndex);
    	rtnVal.isFunc = script.charAt(rtnVal.rightIndex+1)=="(";
    	return rtnVal;
    }

  //根据变量名取ID
    function getVarIdByName(varName, isFunc){
    	if(isFunc){
    		var funcInfo = dialogObject.formulaParameter.funcInfo;
    		for(var i=0; i<funcInfo.length; i++){
    			if(funcInfo[i].text==varName)
    				return varName;
    		}
    	}else{
    		var varInfo = dialogObject.formulaParameter.varInfo;
    		for(var i=0; i<varInfo.length; i++){
    			if(varInfo[i].label==varName)
    				return varInfo[i].name;
    		}
    	}
    }

    //根据ID取变量名
    function getVarNameById(varName, isFunc){
    	if(isFunc){
    		var funcInfo = dialogObject.formulaParameter.funcInfo;
    		for(var i=0; i<funcInfo.length; i++){
    			if(funcInfo[i].text==varName)
    				return varName;
    		}
    	}else{
    		var varInfo = dialogObject.formulaParameter.varInfo;
    		for(var i=0; i<varInfo.length; i++){
    			if(varInfo[i].name==varName)
    				return varInfo[i].label;
    		}
    	}
    }
	function clearExpress(){
		 document.getElementById('expression').value = '';
	}
   
        
</script>
</body>
</html>