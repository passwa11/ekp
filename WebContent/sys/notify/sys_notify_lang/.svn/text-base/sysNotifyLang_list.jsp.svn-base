<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysNotifyLang" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 标题 -->
		<list:data-column headerClass="width300" col="fdBundle" title="${ lfn:message('sys-notify:sysNotifySelfTitleSetting.fdBundle') }">
			${sysNotifyLang.fdBundle}
		</list:data-column>
		<list:data-column headerClass="width300" col="fdMessage" title="${ lfn:message('sys-notify:sysNotifySelfTitleSetting.module') }">
			${sysNotifyLang.fdMessage}
		</list:data-column>	
		<list:data-column headerClass="width120" col="docCreateTime" title="${ lfn:message('sys-notify:sysNotifySelfTitleSetting.docCreateTime') }">
			<kmss:showDate value="${sysNotifyLang.docCreateTime}" type="datetime" />
		</list:data-column>					
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:setNotifySelfTitle('1','${sysNotifyLang.fdId}')">${lfn:message('button.edit')}</a>
				</div>
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:del('${sysNotifyLang.fdId}')">${lfn:message('button.delete')}</a>
				</div>				
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>