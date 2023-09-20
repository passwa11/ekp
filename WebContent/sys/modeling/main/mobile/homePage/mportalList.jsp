<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.simple" compatibleMode="true">
	<template:replace name="title">
		${appName }
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-portal.css" cacheType="md5" />
	   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/mportalList.css?s_cache=${MUI_Cache}">
	   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/list.css?s_cache=${MUI_Cache}">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/cardList.css?s_cache=${MUI_Cache}">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/base/resources/css/boardMobile.css?s_cache=${MUI_Cache}">
	</template:replace>
	<template:replace name="content">
		<div class="mportalList" id="scrollView" data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/pageView"
			data-dojo-props="fdAppId:'${param.fdId}',fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'">
			<div class="mportalList-box">
				<div class="mportalList-content">
					<!-- 统计区 -->
					<div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/mportalList/statistics"
						 data-dojo-props="fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'"></div>
					<!-- 数据展示区 -->
					<div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/mportalList/dataList"
						 data-dojo-props="fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'"></div>
					<!-- 查看更多 -->
					<div data-dojo-type='sys/modeling/main/resources/js/mobile/homePage/mportalList/showManyDataList'
						 data-dojo-props="text:'${lfn:message('sys-modeling-main:modeling.see.more') } >'">
					</div>
					<!-- 图表区 -->
					<div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/mportalList/chartDataList"></div>
				</div>
			</div>
		</div>
	</template:replace>
</template:include>