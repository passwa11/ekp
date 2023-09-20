<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogApp" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 操作时间 -->
		<list:data-column headerClass="width100" col="fdCreateTime" title="${ lfn:message('sys-log:sysLogApp.fdCreateTime') }">
			<kmss:showDate type="datetime" value="${sysLogApp.fdCreateTime}"/>
		</list:data-column>
		<!-- IP地址 -->
		<list:data-column headerClass="width100" property="fdIp" title="${ lfn:message('sys-log:sysLogApp.fdIp') }">
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column headerClass="width100" property="fdOperator" title="${ lfn:message('sys-log:sysLogApp.fdOperator') }">
		</list:data-column>
		<!-- URL -->
		<list:data-column headerClass="width300" col="fdUrl" title="${ lfn:message('sys-log:sysLogApp.fdUrl') }">
			<%
				String url = ((com.landray.kmss.sys.log.model.BaseSysLogApp)pageContext.getAttribute("sysLogApp")).getFdUrl();
				int i = url.indexOf('?');
				if(i>-1)
					url = url.substring(0, i);
				out.write(url);
			%>
		</list:data-column>
		<!-- HTTP请求 -->
		<list:data-column headerClass="width100" col="fdParaMethod" title="${ lfn:message('sys-log:sysLogApp.fdParaMethod') }">
			<% try{ %>
				<bean:message key="button.${sysLogApp.fdParaMethod}"/>
			<% }catch(Exception e){ %>
				<bean:write name="sysLogApp" property="fdParaMethod"/>
			<% } %>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/log/sys_log_app/sysLogApp.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${HtmlParam.isBak}','${sysLogApp.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>