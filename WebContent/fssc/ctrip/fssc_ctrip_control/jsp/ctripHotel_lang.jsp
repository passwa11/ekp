<%@ page language="java" pageEncoding="UTF-8" contentType="text/javascript; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
Designer_Lang.controlCtripHotel_attr_title='<kmss:message bundle="fssc-ctrip" key="message.sso.hotel" />';
Designer_Lang.controlCtripHotel_attr_docNumber="<kmss:message bundle="fssc-ctrip" key="message.docNumber" />";
Designer_Lang.controlCtripHotel_attr_docNumber_null="<kmss:message bundle="fssc-ctrip" key="message.hotel.docNumber.isNull.tips" />";
<kmss:ifModuleExist path="/fssc/ctrip">
Designer_Lang.CtripExists = true;
</kmss:ifModuleExist>
