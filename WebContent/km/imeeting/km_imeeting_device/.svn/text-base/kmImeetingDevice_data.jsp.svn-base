<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingDevice" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width30"  property="fdOrder" title="${ lfn:message('km-imeeting:kmImeetingDevice.fdOrder') }" />
		<!-- 设备名称 -->	
		<list:data-column  property="fdName" title="${ lfn:message('km-imeeting:kmImeetingDevice.fdName') }" />
		<!-- 是否有效 -->
		<list:data-column col="fdIsAvailable" title="${ lfn:message('km-imeeting:kmImeetingDevice.fdIsAvailable') }" >
			<sunbor:enumsShow value="${kmImeetingDevice.fdIsAvailable}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/imeeting/km_imeeting_device/kmImeetingDevice.do?method=edit&fdId=${kmImeetingDevice.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmImeetingDevice.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_device/kmImeetingDevice.do?method=delete&fdId=${kmImeetingDevice.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteDevice('${kmImeetingDevice.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>