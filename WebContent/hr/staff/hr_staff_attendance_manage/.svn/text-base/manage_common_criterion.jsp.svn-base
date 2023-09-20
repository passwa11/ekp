<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 筛选器 -->
<list:criteria id="criteria">
	<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdKey') }" style="width:300px;">
	</list:cri-ref>
	<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus" multi="true">
		<list:box-select>
			<list:item-select cfg-defaultValue="official">
				<ui:source type="Static">
					[{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trial') }', value:'trial'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.practice') }', value:'practice'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.official') }',value:'official'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.temporary') }',value:'temporary'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trialDelay') }',value:'trialDelay'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.dismissal') }',value:'dismissal'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'leave'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.retire') }',value:'retire'}]
				</ui:source>
			</list:item-select>
		</list:box-select> 
	</list:cri-criterion>
	<list:cri-criterion title="${ lfn:message('hr-staff:table.hrStaffAttendanceManage') }" key="_type" multi="false">
		<list:box-select>
			<list:item-select cfg-required="true" cfg-defaultValue="${param.type}">
				<ui:source type="Static">
					[{text:'${ lfn:message('hr-staff:hrStaffAttendanceManage.paidHoliday') }', value:'manage'},
					{text:'${ lfn:message('hr-staff:table.hrStaffAttendanceManageDetailed') }',value:'detailed'},
					{text:'${ lfn:message('hr-staff:table.hrStaffAttendanceManageDetailed.overtime') }',value:'overtime'}]
				</ui:source>
			</list:item-select>
		</list:box-select> 
	</list:cri-criterion>
	<list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
	<c:if test="${param.type eq 'manage'}">
		<list:cri-ref key="_fdPerson" ref="criterion.sys.person" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdPerson') }" multi="false">
		</list:cri-ref>
		<list:cri-criterion  key="_fdYear" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdYear') }" multi="false">
			<list:box-select>
				<list:item-select>
					<ui:source type="AjaxJson">
						{url:'/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=getYearCriterion'}
					</ui:source>
				</list:item-select>
			</list:box-select> 
		</list:cri-criterion>
	</c:if>
	<c:if test="${param.type eq 'detailed'}">
		<list:cri-ref key="_fdPerson" ref="criterion.sys.person" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdPerson') }" multi="false">
		</list:cri-ref>
		<list:cri-criterion key="_fdLeaveName" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdLeaveName') }" multi="false">
			<list:box-select>
				<list:item-select>
					<ui:source type="AjaxJson">
						{url:'/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=getLeaveCriterion'}
					</ui:source>
				</list:item-select>
			</list:box-select> 
		</list:cri-criterion>
		<list:cri-ref key="_fdStartTime" ref="criterion.sys.calendar" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdStartTime') }" multi="false">
		</list:cri-ref>
		<list:cri-ref key="_fdEndTime" ref="criterion.sys.calendar" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdEndTime') }" multi="false">
		</list:cri-ref>
		<list:cri-criterion key="_fdOprType" title="${ lfn:message('sys-time:sysTimeLeaveDetail.source') }" multi="false">
			<list:box-select>
				<list:item-select>
					<ui:source type="Static">
						[{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.review') }', value:'1'},
						{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.manual') }',value:'2'},
						{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprType.batch') }',value:'3'}]
					</ui:source>
				</list:item-select>
			</list:box-select> 
		</list:cri-criterion>
		<list:cri-criterion key="_fdOprStatus" title="${ lfn:message('sys-time:sysTimeLeaveDetail.fdOprDesc') }" multi="false">
			<list:box-select>
				<list:item-select>
					<ui:source type="Static">
						[{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.no') }', value:'0'},
						{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.success') }', value:'1'},
						{text:'${ lfn:message('sys-time:sysTimeLeaveDetail.deduct.fail') }', value:'2'}]
					</ui:source>
				</list:item-select>
			</list:box-select> 
		</list:cri-criterion>
	</c:if>
</list:criteria>

<!-- 排序 -->
<div class="lui_list_operation">
	<!-- 全选 -->
	<div class="lui_list_operation_order_btn">
		<list:selectall></list:selectall>
	</div>
	<!-- 分割线 -->
	<div class="lui_list_operation_line"></div>
	<!-- 排序 -->
	<div class="lui_list_operation_sort_btn">
		<div class="lui_list_operation_order_text">
			${ lfn:message('list.orderType') }：
		</div>
		<div class="lui_list_operation_sort_toolbar">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
			<c:if test="${param.type eq 'manage'}">
			<list:sortgroup>
			    <list:sort property="sysTimeLeaveAmount.docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
			</list:sortgroup>
			</c:if>
			<c:if test="${param.type eq 'detailed'}">
			<list:sortgroup>
			    <list:sort property="sysTimeLeaveDetail.docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
			</list:sortgroup>
			</c:if>
			<c:if test="${param.type eq 'overtime'}">
			<list:sortgroup>
			    <list:sort property="sysTimeLeaveDetail.docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
			</list:sortgroup>
			</c:if>
			</ui:toolbar>
		</div>
	</div>
	<!-- 分页 -->
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top" >
		</list:paging>
	</div>
	<div style="float:right">
		<div style="display: inline-block;vertical-align: middle;">
			<ui:toolbar count="5">
			<c:if test="${param.type eq 'manage'}">
				<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=add">
				<ui:button text="${lfn:message('button.add')}" onclick="addDoc();" order="1" ></ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=fileUpload">
				<ui:button text="${lfn:message('sys-time:sysTimeLeaveAmount.import.batch')}" onclick="importFile();" order="1" ></ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=deleteall">
				<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
				</kmss:auth>
			</c:if>
			<c:if test="${param.type eq 'detailed'}">
				<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=add">
				<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.add')}" onclick="addDoc();" order="1" ></ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=fileUpload">
				<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.addAll')}" onclick="importDoc();" order="2" ></ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=deductAll">
				<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.deduct.batch')}" onclick="deductAll();" order="3" ></ui:button>
				</kmss:auth>
				
				<kmss:ifModuleExist path="/sys/attend">
				<kmss:auth requestURL="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=updateAttendAll">
				<ui:button text="${lfn:message('sys-time:sysTimeLeaveDetail.updateAttendAll')}" onclick="updateAttendAll();" order="4" ></ui:button>
				</kmss:auth>
				</kmss:ifModuleExist>
			</c:if>
			<c:if test="${param.type eq 'overtime'}">
				
			</c:if>
			</ui:toolbar>
		</div>
	</div>
</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>