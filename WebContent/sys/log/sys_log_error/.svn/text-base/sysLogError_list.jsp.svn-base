<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogError" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 操作时间 -->
		<list:data-column headerClass="width120" col="fdCreateTime" title="${ lfn:message('sys-log:sysLogError.fdCreateTime') }">
			<kmss:showDate type="datetime" value="${sysLogError.fdCreateTime}"/>
		</list:data-column>
		<!-- IP地址 -->
		<list:data-column headerClass="width100" property="fdIp" title="${ lfn:message('sys-log:sysLogError.fdIp') }">
		</list:data-column>
		<!-- 浏览器 -->
		<list:data-column headerClass="width100" property="fdBrowser" title="${ lfn:message('sys-log:sysLogError.fdBrowser') }">
		</list:data-column>
		<!-- 设备 -->
		<list:data-column headerClass="width100" property="fdEquipment" title="${ lfn:message('sys-log:sysLogError.fdEquipment') }">
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column headerClass="width100" property="fdOperator" title="${ lfn:message('sys-log:sysLogError.fdOperator') }">
		</list:data-column>
		<!-- URL -->
		<list:data-column headerClass="width200" col="fdUrl" title="${ lfn:message('sys-log:sysLogError.fdUrl') }">
			<%
				String url = ((com.landray.kmss.sys.log.model.BaseSysLogError)pageContext.getAttribute("sysLogError")).getFdUrl();
				int i = url.indexOf('?');
				if(i>-1)
					url = url.substring(0, i);
				out.write(url);
			%>
		</list:data-column>
		<!-- 方法 -->
		<list:data-column headerClass="width80" col="fdMethod" title="${ lfn:message('sys-log:sysLogError.fdMethod') }">
			<bean:write name="sysLogError" property="fdMethod"/>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/log/sys_log_error/sysLogError.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${HtmlParam.isBak}','${sysLogError.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>