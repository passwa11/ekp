<%@ page language="java" contentType="javascript/x-javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
	/************************************************************
		custom.validations.sign = ×
		custom.validations.required = 不能为空
		custom.validations.plusInt = 只能为正整数
		custom.validations.plusFloatZero = 只能为非负数
		custom.validations.picExt = 文件格式不正确
		custom.validations.email = 请输入有效电子邮件地址，例如：
	*************************************************************/
	var Cus_Valid_Properties = {
		sign : "<bean:message bundle="tic-core" key="custom.validations.sign"/>",
		required : "<bean:message bundle="tic-core" key="custom.validations.required"/>",
		plusInt : "<bean:message bundle="tic-core" key="custom.validations.plusInt"/>",
		plusFloatZero : "<bean:message bundle="tic-core" key="custom.validations.plusFloatZero"/>",
		picExt : "<bean:message bundle="tic-core" key="custom.validations.picExt"/>",
		email : "<bean:message bundle="tic-core" key="custom.validations.email"/>"
		
	};
