<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<template:include ref="mobile.list" tiny="true" canHash="true">
	<template:replace name="title">
		我的订阅
	</template:replace>
	<template:replace name="head">
	 	<mui:min-file name="mui-follow-index.js" />
		<mui:min-file name="mui-follow-index.css"/>
	</template:replace>
	<template:replace name="content">
       <div data-dojo-type="mui/header/Header">
            <div data-dojo-type="mui/nav/MobileCfgNavBar" 
                data-dojo-props="modelName:'com.landray.kmss.sys.follow.model.SysFollowDoc'">
            </div>
        </div>
        
        <div data-dojo-type="mui/header/NavHeader">
            <div data-dojo-type="mui/sort/SortItem" 
                data-dojo-props="name:'sysFollowPersonDocRelated.followDoc.docCreateTime',subject:'创建时间',value:'down'">
            </div> 
            <!-- <div class="muiHeaderItemRight" 
                 data-dojo-type="mui/property/FilterItem"
                 data-dojo-mixins="sys/follow/mobile/js/propertyMixin">
             </div> -->
        </div>
        
        <div data-dojo-type="mui/list/NavView">
        	<ul class="sysFollow"
        		data-dojo-type="mui/list/HashJsonStoreList" 
                data-dojo-mixins="sys/follow/mobile/js/SysFollowListMixin">
            </ul>
        </div> 
	</template:replace>
</template:include>