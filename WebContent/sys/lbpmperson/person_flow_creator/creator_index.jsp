<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple">

	<template:replace name="body">
	  	<style>
		  /* body{
		  	overflow-y:hidden;
		  } */
	  </style>
	  <ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.submitprocess') }" style="" >
						<jsp:include page="/sys/lbpmperson/person_flow_creator/creator_index_common.jsp"></jsp:include>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>