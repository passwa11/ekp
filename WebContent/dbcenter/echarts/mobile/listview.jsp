<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
    <div data-dojo-type="mui/nav/MobileCfgNavBar" 
        data-dojo-props="modelName:'com.landray.kmss.dbcenter.echarts.model.DbEchartsTotal'">
    </div>
    
    <div data-dojo-type="mui/search/SearchButtonBar"
        data-dojo-props="modelName:'com.landray.kmss.dbcenter.echarts.model.DbEchartsTotal'" >
    </div>
</div>
   
<div data-dojo-type="mui/header/NavHeader">
    <div class="muiHeaderItemRight" 
        data-dojo-type="mui/catefilter/simplecategory/FilterItem"
        data-dojo-props="modelName:'com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate',parentId:'q.docCategory'">
    </div>
</div>

<div data-dojo-type="mui/list/NavView">
    <ul class="dbcenterEcharts"
    	data-dojo-type="mui/list/HashJsonStoreList" 
    	data-dojo-mixins="dbcenter/echarts/mobile/resource/js/EchartsListMixin">
	</ul>
</div>
