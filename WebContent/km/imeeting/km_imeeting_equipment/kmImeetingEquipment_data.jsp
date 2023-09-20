<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingEquipment" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('km-imeeting:kmImeetingEquipment.fdOrder') }" />
		<!-- 设备名称 -->	
		<list:data-column  property="fdName" title="${ lfn:message('km-imeeting:kmImeetingEquipment.fdName') }" />
		<!-- 是否有效 -->
		<list:data-column col="fdIsAvailable" title="${ lfn:message('km-imeeting:kmImeetingEquipment.fdIsAvailable') }" >
			<sunbor:enumsShow value="${kmImeetingEquipment.fdIsAvailable}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/imeeting/km_imeeting_equipment/kmImeetingEquipment.do?method=edit&fdId=${kmImeetingEquipment.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmImeetingEquipment.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_equipment/kmImeetingEquipment.do?method=delete&fdId=${kmImeetingEquipment.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteEquipment('${kmImeetingEquipment.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>