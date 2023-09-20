<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="head">
        <script type="text/javascript">
            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <script type="text/javascript">
            Com_IncludeFile("data.js|dialog.js|jquery.js");
        </script>

    </template:replace>
	<template:replace name="content">
	
		<script type="text/javascript">
			seajs.use(['theme!list']);
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript"
			src="${LUI_ContextPath}/hr/staff/resource/js/hrImageUtil.js?s_cache=${LUI_Cache}"></script>
		<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/hr/staff/resource/css/hr_box.css?s_cache=${LUI_Cache}">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffPersonInfo" property="fdLeaveTime" />
		</list:criteria>
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list&personStatus=quit'}
			</ui:source>
			<!-- 列表视图 -->
			<list:colTable layout="sys.ui.listview.columntable"
				rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{fdId}"
				name="columntable">
		
				<list:col-auto props="fdName;fdDeptName;fdOrgPostNames;fdEntryTime;fdTimeOfEnterprise;fdStatus">
				</list:col-auto>
			</list:colTable>
		</list:listview>
		<list:paging></list:paging>

<Style>
.dlg_btn_bar_bottom{
  	position: fixed;
    bottom: 0px;
    width:100%;
 }
</Style>
   <div class="dlg_btn_bar_bottom">
  			<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();" suspend="bottom" style="float:right;">
			</ui:button>
        </div>

	</template:replace>
</template:include>