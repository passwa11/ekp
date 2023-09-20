<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/main/xform/controls/filling/css/filling.css" />
<!-- 导航栏 -->
<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4rem'">
	<div data-dojo-type="sys/modeling/main/xform/controls/filling/mobile/ModelingCfgNavBar"
		 data-dojo-props="fieldName:'{categroy.fieldName}',fdAppModelId:'{categroy.fdAppModelId}',ins:'{categroy.ins}',widgetId:'{categroy.widgetId}'">
	</div>
</div>
<div data-dojo-type="mui/header/NavHeader"  style="background: #ffffff">

</div>
<%--  页签内容展示区域，可纵向上下滑动   --%>
<div data-dojo-type="mui/list/NavView" style="background: #ffffff" class="modelListView">
	<%--  默认列表模板   --%>
	<ul data-dojo-type="sys/modeling/main/xform/controls/filling/mobile/HashJsonStoreList"
		data-dojo-mixins="sys/modeling/main/xform/controls/filling/mobile/DocItemListMixin"
		data-dojo-props="selType:'filling',ins:'{categroy.ins}',fsKey:'{categroy.key}'">
	</ul>
</div>
<!-- 已选 -->
<div data-dojo-type="sys/modeling/main/xform/controls/filling/mobile/FillingSelection"
	 data-dojo-props="key:'{categroy.key}'" fixed="bottom">
</div>


