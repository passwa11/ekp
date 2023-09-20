<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" rwd="true">
	<template:replace name="body">
	<div style="width:100%;">
	  <ui:tabpanel layout="sys.ui.tabpanel.list">
		 <ui:content title="${lfn:message('km-archives:py.RecycleBorrow')}">
		 	 <ui:iframe id="recysle_borrow" src="${LUI_ContextPath }/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesBorrow&roles=ROLE_KMARCHIVES_TRANSPORT_EXPORT"></ui:iframe>
		  </ui:content>
		  <ui:content title="${lfn:message('km-archives:py.RecycleArchives')}">
		  	<ui:iframe id="recysle_archives" src="${LUI_ContextPath }/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.km.archives.model.KmArchivesMain&roles=ROLE_KMARCHIVES_TRANSPORT_EXPORT"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
