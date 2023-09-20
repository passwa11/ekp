<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String fdFileName = (String)request.getAttribute("fdFileName");
	if(StringUtil.isNotNull(fdFileName))
		fdFileName =  URLDecoder.decode(fdFileName,"UTF-8");
%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<center>
		<html:form action="/sys/attachment/sys_att_main/sysAttMain.do" >
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<td width="18%" class="td_normal_title">
						${ lfn:message('sys-attachment:sysAttMain.fdFileName') }
					</td>
					<td colspan="3">
						<!--解决form表单在只有一个input输入框时回车会自动提交表单--> 
						<input type="text" name="notautosubmit" style="display:none" /> 
						<xform:text property="fdFileName" value="<%=fdFileName%>" style="width:95%;" showStatus="edit" required="true"  validators="validateFileName maxLength(200)"/>
					</td>
				</tr>
			</table>
		</html:form>

<ui:button text="${lfn:message('button.save')}" onclick="_submit();" height="35" width="120" ></ui:button>

<script type="text/javascript">
	// 表单校验
	var _validation = $KMSSValidation();
	
	//自定义校验器:校验会议历时不能大于会议实际时差
	_validation.addValidator('validateFileName','${lfn:message("sys-attachment:sysAttMain.illegal.fileName")}',function(v,e,o){
		var fdFileName = document.getElementsByName('fdFileName')[0].value,
			result = true;
		
		var reg = new RegExp('[\\/:*?"<>|\r\n]+');
		if(reg.test(fdFileName)){
		    //文件名含有非法字符()
			result = false; 
		}
		
		return result;
	});

	seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog, dateUtil) {

			// 确认提交
			window._submit = function() {
				if ($KMSSValidation().validate()) {
					var o = {};
					o["fdFileName"] = document.getElementsByName("fdFileName")[0].value;
					window.$dialog.hide(o);
				}
			};

			// 取消
			window._cancel = function() {
				window.$dialog.hide();
			};
		});
</script>
		</center>
	</template:replace>
</template:include>
