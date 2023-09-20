<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple">
	<template:replace name="body">
	 <style>
		  body{
		  	overflow:hidden;
		  }
	  </style>
	  <ui:tabpanel layout="sys.ui.tabpanel.list">
		 <ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.dataCount') }" style="margin:10px 1px 0px 5px;" >
		 				<jsp:include page="/sys/lbpmperson/person_efficiency/data_create/data_create_summary.jsp"></jsp:include>
		</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>