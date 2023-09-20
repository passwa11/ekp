<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysSearchMain" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-search:sysSearchMain.fdOrder') }">
		</list:data-column>
		<!-- 名称 -->
		<list:data-column property="fdName" title="${ lfn:message('sys-search:sysSearchMain.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<!-- 创建人 -->
		<c:choose>
			<c:when test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
				<list:data-column headerClass="width100" property="fdCreator.fdName" col="fdCreator" title="${ lfn:message('model.fdCreator') }" escape="false">
				</list:data-column>
			</c:when>
			<c:otherwise>
				<list:data-column headerClass="width100" col="fdCreator" title="${ lfn:message('model.fdCreator') }" escape="false">
				 	<ui:person personId="${sysSearchMain.fdCreator.fdId}" personName="${sysSearchMain.fdCreator.fdName}"></ui:person>
				</list:data-column>
			</c:otherwise>
		</c:choose>
		<!-- 创建时间 -->
		<list:data-column headerClass="width100" col="fdCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		    <kmss:showDate value="${sysSearchMain.fdCreateTime}" type="date"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/search/sys_search_main/sysSearchMain.do?method=edit&fdId=${sysSearchMain.fdId}&fdModelName=${JsParam.fdModelName}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysSearchMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/search/sys_search_main/sysSearchMain.do?method=deleteall&fdModelName=${JsParam.fdModelName}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysSearchMain.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>