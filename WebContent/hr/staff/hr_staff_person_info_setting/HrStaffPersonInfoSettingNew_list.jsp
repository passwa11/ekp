<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
<list:data-columns var="hrStaffPersonInfoSetting" list="${queryPage.list}">
		<list:data-column property="fdId"/>
		
		<list:data-column headerClass="width20" col="fdCheckBox" title="" escape="false">
			<c:if test="${empty hrStaffPersonInfoSetting.fdDefault || !hrStaffPersonInfoSetting.fdDefault }">
				<input type="checkbox" name="List_Selected" value="${hrStaffPersonInfoSetting.fdId}" data-lui-mark="table.content.checkbox">
			</c:if>
		</list:data-column>
		
		<list:data-column property="fdOrder" title="${ lfn:message('hr-staff:hrStaff.fdOrder') }">
		</list:data-column>
		<list:data-column property="fdName" title="${ lfn:message('hr-staff:hrStaff.fdName') }">
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${empty hrStaffPersonInfoSetting.fdDefault || !hrStaffPersonInfoSetting.fdDefault }">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${hrStaffPersonInfoSetting.fdId}')">${lfn:message('button.edit')}</a>
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${hrStaffPersonInfoSetting.fdId}')">
							<bean:message key="button.delete"/>						
						</a>
					</c:if>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>					
	</list:data-columns>	
	
	<list:data-paging page="${ queryPage }"></list:data-paging>
</list:data>