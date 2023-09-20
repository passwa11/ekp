<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

 <api:data-object name="evaluation" >
 	<api:prop-auto exclude=""/>
     <api:prop name="myEva">
     	<api:prop-attr name="childEva" value="String"/>
     	<api:prop-attr name="value" value="sky"/>
     </api:prop>
 </api:data-object>
