<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.km.archives.service.IKmArchivesMainService"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
  		<div style="background-color: #fff">
  			<%@ include file="/sys/category/import/category_search.jsp"%>
  			<%@ include file="/sys/lbpmperson/import/usualCate.jsp"%>
  			<%@ include file="/sys/person/sys_person_favorite_category/favorite_category_flat.jsp"%>
  		</div>
	</template:replace> 
</template:include>