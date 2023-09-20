<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.simple" compatibleMode="true">
	<template:replace name="title">
		${appName }
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-portal.css" cacheType="md5" />
	   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/mportal.css?s_cache=${MUI_Cache}">
	</template:replace>
	<template:replace name="content">
		<div class="mportal" id="scrollView" data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/pageView"
			data-dojo-props="fdAppId:'${param.fdId}',fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'">
			<div class="mportal-box">
				<div class="mportal-content">
					<!-- 统计区 -->
					<div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/mportal/statistics"
						 data-dojo-props="fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'"></div>
					<!-- 图标区 -->
					<div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/mportal/iconArea"></div>
					<!-- 列表区 -->
					<div data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/mportal/listView"
						 data-dojo-props="fdMobileId:'${empty fdMobileId ? param.fdMobileId : fdMobileId }'"></div>

				</div>
			</div>
		</div>
	</template:replace>
</template:include>