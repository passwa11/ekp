<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.net.URLEncoder" %>
<template:include file="/sys/profile/resource/template/list.jsp">
<template:replace name="toolbar">
	<div style="width:100%;height:38px;position: fixed; top: 0px; left: 0px; z-index: 8;">
	   <ui:toolbar id="toolbar" style="float:right;margin-right:20px" >
	       <kmss:auth requestURL="/sys/portal/sys_portal_main/sysPortalMain.do?method=add">
		      <ui:button text="${lfn:message('sys-portal:sysPortalMain.add')}"  onclick="addPortal();" order="1" ></ui:button>
		      <ui:button text="${lfn:message('sys-portal:sysPortalMain.anonymous.add')}" onclick="addPortalAnonymous();" order="2" ></ui:button>
			  <ui:button text="${lfn:message('sys-portal:sysPortalMain.import')}" onclick="importPortal();" order="4" ></ui:button>
			  <kmss:ifModuleExist path="/third/mall/">
				   <ui:button text="${lfn:message('third-mall:kmReuseCommon.center')}"  onclick="redirectMall();" order="3" ></ui:button>
			  </kmss:ifModuleExist>
		   </kmss:auth>
		   <ui:button text="${lfn:message('button.refresh')}"  onclick="loadPortalData();" order="5" ></ui:button>
		   <kmss:auth requestURL="/sys/portal/sys_portal_main/sysPortalMain.do?method=deleteall">
			   <ui:button text="${lfn:message('button.deleteall')}"  onclick="deleteAllPortal();" order="6" ></ui:button>
		   </kmss:auth>
 	   </ui:toolbar>
	</div>
</template:replace>
<template:replace name="content">
<%
	String path = request.getParameter("s_path");
	request.setAttribute("path", URLEncoder.encode(path, "UTF-8"));
%>
	<!-- 筛选器 start-->
	<list:criteria id="criteria">
		<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-portal:sysPortalMain.fdName') }">
		</list:cri-ref>
		<%--状态--%>
		<list:cri-criterion title="${lfn:message('sys-portal:sysPortalMain.docstatus')}" key="fdEnabled" multi="false">
			<list:box-select>
				<list:item-select>
					<ui:source type="Static">
						[{text:'${ lfn:message('sys-portal:sysPortalMain.logOper.setEnable')}', value:'1'},
						{text:'${ lfn:message('sys-portal:sysPortalMain.logOper.setDisable')}',value:'0'}]
					</ui:source>
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
		<%--匿名--%>
		<list:cri-criterion title="${ lfn:message('sys-portal:sysportal.anonymous')}" key="fdAnonymous" multi="false">
			<list:box-select>
				<list:item-select>
					<ui:source type="Static">
						[{text:'${ lfn:message('sys-portal:sys_portal_anonymous')}', value:'1'},
						{text:'${ lfn:message('sys-portal:sys_portal_general')}',value:'0'}]
					</ui:source>
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
		<%--创建时间--%>
		<list:cri-auto modelName="com.landray.kmss.sys.portal.model.SysPortalMain" property="docCreateTime" />
	</list:criteria>
	<!-- 筛选器 end-->
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	Com_IncludeFile("sysPortalMain_list.css","${LUI_ContextPath}/sys/portal/sys_portal_main/resource/css/",'css',true);
	Com_IncludeFile("sysPortalMain_list.js", "${LUI_ContextPath}/sys/portal/sys_portal_main/resource/js/", null, true);
	seajs.use(['lui/jquery','lui/topic'], function($,topic) {
		//监听
		topic.subscribe('criteria.criterion.changed', function(evt) {
			loadPortalData(evt);
		});
	});

function redirectMall(){
	window.top.location.href='${LUI_ContextPath}/sys/profile/index.jsp#portal/templateCenter';
	window.top.location.reload();
}
//筛选器数据
	var portal_main_list_criteria_data = {
		"fdName":"",
		"fdEnabled":"",
		"fdAnonymous":"",
		"createStartTime":"",
		"createEndTime":""
	}
// 门户配置表格标题
var portal_main_list_title_data = {
	"fdName":"${lfn:message('sys-portal:sysPortalMain.fdName')}", // 门户名称
	"fdType":"${lfn:message('sys-portal:sysPortalMain.fdType')}", // 门户类型
	"fdAnonymous":"${lfn:message('sys-portal:sysportal.anonymous')}", // 匿名
	"fdEnabled":"${lfn:message('sys-portal:sysPortalMain.fdEnabled')}", // 是否启用
	"fdOpts":"${lfn:message('sys-portal:sysPortalMain.fdOpts')}" // 操作
};
// 门户类型
var portal_type_data = {
	"portal":"${lfn:message('sys-portal:sys_portal_page_type_portal')}", // 门户	
	"page":"${lfn:message('sys-portal:sys_portal_page_type_page')}", // 页面
	"url":"${lfn:message('sys-portal:sys_portal_page_type_url')}" // URL
};
// 是否启用
var portal_enabled_status_data = {
	"true":"${lfn:message('sys-portal:sysPortalMain.fdEnabled.true')}", // 已启用
	"false":"${lfn:message('sys-portal:sysPortalMain.fdEnabled.false')}" // 已禁用		
}; 
// 操作按钮
var portal_operation_button_data = {
    "exportInitData":"${lfn:message('sys-datainit:sysDatainitMain.data.export')}", // 导出为初始数据
	"edit":"${lfn:message('button.edit')}", // 编辑
    "delete":"${lfn:message('button.delete')}", // 删除
    "enable":"${lfn:message('sys-portal:sysPortalMain.operation.enable')}", // 启用
    "disable":"${lfn:message('sys-portal:sysPortalMain.operation.disable')}", // 禁用
	"export":"${lfn:message('sys-portal:sysPortalMain.export')}" // 禁用
};


// 提示信息
var portal_msg_info_data = {
    "sysPortalMain.msg.delete":"${lfn:message('sys-portal:sysPortalMain.msg.delete')}", // 确定删除该门户？
    "sysPortalPage.msg.delete":"${lfn:message('sys-portal:sysPortalMain.msg.delete')}", // 确定删除该页面？
    "sysPortalMain.msg.enable":"${lfn:message('sys-portal:sysPortalMain.msg.enable')}", // 确定启用该门户？
    "sysPortalMain.msg.disable":"${lfn:message('sys-portal:sysPortalMain.msg.disable')}", // 确定禁用该门户？
    "page.noSelect":"${lfn:message('page.noSelect')}", // 您没有选择需要操作的数据
    "return.optSuccess":"${lfn:message('return.optSuccess')}", // 您的操作已成功！
    "return.optFailure":"${lfn:message('return.optFailure')}",// 操作失败！
	"importTitle":"${lfn:message('sys-portal:sysPortalMain.import.title')}",//导入门户
	"deleteAllTitle":"${lfn:message('page.comfirmDelete')}"// 一旦选择了删除，所选记录的相关数据都会被删除，无法恢复！您确认要执行此删除操作吗？
};

// 请求参数
var portal_request_param = {
    "s_path":"${path}"
};

// 匿名 @author 吴进 by 20191113
var portal_anonymous_data = {
	"false":"${lfn:message('sys-portal:sys_portal_general')}", 	// 普通	
	"true":"${lfn:message('sys-portal:sys_portal_anonymous')}"	// 匿名
};
</script>


<html:form action="/sys/portal/sys_portal_main/sysPortalMain.do" styleClass="lui_profile_table_form">
    <!-- 门户信息列表渲染容器 -->
    <div id="portal_info_container" ></div>
</html:form>

</template:replace>
</template:include>