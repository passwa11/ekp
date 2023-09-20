<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple">

	<template:replace name="body">
	<style>
		  /* body{
		  	overflow-y:hidden;
		  } */
.conf_btn_edit .btn_txt {
	margin: 0px 2px;
	color:#2574ad;
	border-bottom:1px solid transparent;
}
.conf_btn_edit .btn_txt:hover{
	color:#123a6b;
	border-bottom-color:#123a6b; 
}

	  </style>
	  <ui:tabpanel layout="sys.ui.tabpanel.list">
		 <ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.myctracked') }" style="" >
					<jsp:include page="/sys/lbpmperson/person_flow_track/track_index_common.jsp"></jsp:include>
		</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>