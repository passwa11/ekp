<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 
			***** 通用手机号校验 *****
	校验规则：
	1、手机号码 国际手机号需加区号"+"，"+"后面纯数字
	2、中国大陆的 只需要校验11位数字即可
	3、如果有手动输入+86，保存时校验+86后面有11位数字，且保存后的号码强制去除+86
	4、国际手机号必须以+区号手机号，区号可以是1~5位数数字，手机号可以是6~11位的数字
	5、若不符合上述规范，提示"手机号码格式错误" 
	
	例如：
	13888888888			正确（国内）
	+8613888888888		正确（国内）
	+852123456			正确（香港）
	+85-2123456			正确（香港）
	123456				错误
	+86123456			错误
	
	引用说明，在需要使用的页面通过以下代码引用：
	<c:import url="/sys/organization/sys_org_person/common_mobileNo_check.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="sysOrgPersonForm" /> <!-- 表单formName -->
	</c:import>

--%>

<script language="JavaScript">
	var _validation = $KMSSValidation(document.forms['${JsParam.formName}']);

	// 增加一个字符串的startsWith方法
	function startsWith(value, prefix) {
		return value.slice(0, prefix.length) === prefix;
	}

	// 校验手机号是否正确
	_validation.addValidator(
		'phone',
		"<bean:message key='sysOrgPerson.error.newMoblieNoError' bundle='sys-organization' />",
		function(v, e, o) {
			if (v == "") {
				return true;
			}
			// 国内手机号可以有+86，但是后面必须是11位数字
			// 国际手机号必须要以+区号开头，后面可以是6~11位数据
			// （国际手机号需加区号，格式为“+区号手机号”或者“+区号-手机号”）
			if(startsWith(v, "+")) {
				if(startsWith(v, "+86")) {
					return /^(\+86)(-)?(\d{11})$/.test(v);
				} else {
					return /^(\+\d{1,5})(-)?(\d{6,11})$/.test(v);
				}
			} else {
				// 没有带+号开头，默认是国内手机号
				return /^(\d{11})$/.test(v);
			}
	});

	// 校验手机号是否唯一
	var MobileNoValidators = {
		'uniqueMobileNo' : {
			error : "<bean:message key='sysOrgPerson.error.newMoblieNoSameOldName' bundle='sys-organization' />",
			test : function(value) {
				if(startsWith(value, "+86")) {
					// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
					value = value.slice(3, value.length)
				}
				if(startsWith(value, "+")) {
					value = value.replace("+", "x")
				}
				var fdId = document.getElementsByName("fdId")[0].value;
				var result = mobileNoCheckUnique("sysOrgPersonService", value,
						fdId, "unique");
				if (!result)
					return false;
				return true;
			}
		}
	};
	_validation.addValidators(MobileNoValidators);

	function mobileNoCheckUnique(bean, mobileNo, fdId, checkType) {
		var url = encodeURI(Com_Parameter.ResPath
				+ "jsp/ajax.jsp?&serviceName=" + bean + "&mobileNo=" + mobileNo
				+ "&fdId=" + fdId + "&checkType=" + checkType + "&date="
				+ new Date());
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "")
					.replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}
</script>
