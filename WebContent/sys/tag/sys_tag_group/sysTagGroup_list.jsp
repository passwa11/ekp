<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.ui.xml.model.SysUiTheme"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  
				property="fdName"
				title="${lfn:message('sys-tag:sysTagGroup.fdName') }">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${lfn:message('sys-tag:sysTagGroup.fdOrder') }" ></list:data-column>
		<list:data-column property="fdModelName" title="${lfn:message('sys-tag:sysTagGroup.fdModelName') }" ></list:data-column>
		<!-- 是否有效 -->
		<list:data-column  col="fdEnabled" title="${ lfn:message('sys-tag:sysTagGroup.fdEnabled') }" >
			<sunbor:enumsShow enumsType="common_yesno"  value="${item.fdEnabled}"/>
		</list:data-column>
		<!-- 创建者 -->
		<list:data-column property="docCreator.fdName" title="${ lfn:message('sys-tag:sysTagGroup.docCreator') }" />
		<!-- 创建时间 -->
		<list:data-column property="fdCreateTime" title="${ lfn:message('sys-tag:sysTagGroup.fdCreateTime') }" />
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/tag/sys_tag_group/sysTagGroup.do?method=edit&fdId=${item.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${item.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/tag/sys_tag_group/sysTagGroup.do?method=delete&fdId=${item.fdId}" requestMethod="GET">
					    <!-- 删除-->
						<a class="btn_txt" href="javascript:deleteAll('${item.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth> 
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>