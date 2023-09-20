<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script type="text/javascript">
	seajs.use(['theme!list']);	
</script>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
<script type="text/javascript" src="${LUI_ContextPath}/hr/staff/resource/js/hrImageUtil.js?s_cache=${LUI_Cache}"></script>
<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/hr/staff/resource/css/hr_box.css?s_cache=${LUI_Cache}">
<!-- 筛选器 -->
<list:criteria id="criteria">
	<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdKey') }">
	</list:cri-ref>
	<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus">
		<list:box-select>
			<list:item-select>
					<ui:source type="Static">
					[{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trial') }', value:'trial'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.practice') }', value:'practice'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.official') }',value:'official'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.temporary') }',value:'temporary'},
					{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trialDelay') }',value:'trialDelay'}]
				</ui:source>
			</list:item-select>
		</list:box-select> 
	</list:cri-criterion>
	<list:cri-ref key="fdTimeOfEnterprise" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }"></list:cri-ref>
	<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdLabel') }" key="_fdLabel">
		<list:box-select>
			<list:item-select>
				<ui:source type="AjaxJson">
					{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getTags'}
				</ui:source>
			</list:item-select>
		</list:box-select> 
	</list:cri-criterion>
</list:criteria>

<!-- 排序 -->
<div class="lui_list_operation">
	<div style='color: #979797;float: left;padding-top:1px;'>
		${ lfn:message('list.orderType') }：
	</div>
	<div style="float:left">
		<div style="display: inline-block;vertical-align: middle;">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
			<list:sortgroup>
			    <list:sort property="fdTimeOfEnterprise" text="${lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise') }" group="sort.list" value="down"></list:sort>
			</list:sortgroup>
			</ui:toolbar>
		</div>
	</div>
	<div style="float:left;">
		<list:paging layout="sys.ui.paging.top" >
		</list:paging>
	</div>
	<div style="float:right">
		<div style="display: inline-block;vertical-align: middle;">
			<ui:toolbar count="4">
				<%-- 视图切换 --%>
				<ui:togglegroup order="0">
					 <ui:toggle icon="lui_icon_s_zaiyao" title="${ lfn:message('list.rowTable') }" 
						  group="tg_1" text="${ lfn:message('list.rowTable') }" value="gridtable" 
						onclick="LUI('listview').switchType(this.value);">
					</ui:toggle>
					<ui:toggle icon="lui_icon_s_liebiao" title="${ lfn:message('list.columnTable') }" 
						value="columntable"	group="tg_1" text="${ lfn:message('list.columnTable') }" 
						onclick="LUI('listview').switchType(this.value);">
					</ui:toggle>
				 </ui:togglegroup>
			</ui:toolbar>
		</div>
	</div>
</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>

<!-- 列表 -->
<list:listview id="listview">
	<ui:source type="AjaxJson">
		{url:'/sys/subordinate/sysSubordinate.do?method=list&modelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo&orgId=${JsParam.orgId}'}
	</ui:source>
	<!-- 列表视图 -->	
	<list:colTable  layout="sys.ui.listview.columntable" 
		rowHref="/sys/subordinate/sysSubordinate.do?method=view&readOnly=true&modelId=!{fdId}&modelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo&orgId=${JsParam.orgId}" name="columntable" >
		<list:col-serial></list:col-serial> 
		<list:col-auto props="fdName;fdLoginName;fdStaffNo;fdTimeOfEnterprise;fdDeptName;fdStatus"></list:col-auto>
		<kmss:authShow roles="ROLE_HRSTAFF_DELETE;ROLE_HRSTAFF_EDIT">
		</kmss:authShow>
	</list:colTable>
	<!-- 摘要 -->	
	<list:gridTable name="gridtable" columnNum="2">
		<list:row-template >
			{$
				<div class="lui_hr_box_2">
					<a class="lui_hr_img" 
					   target="_blank" onclick="Com_OpenNewWindow(this)"
					   data-href="${LUI_ContextPath }/sys/subordinate/sysSubordinate.do?method=view&readOnly=true&modelId={%grid['fdId']%}&modelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo">
						<img  src="{% grid['imgUrl']%}" onload="javascript:drawImage(this,this.parentNode)">
					</a>
					<ul>
						<li>
						<input type="hidden" value="{% grid['fdIsAvailable']%}" name="fdIsAvailable"/>
							<a target="_blank" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath }/sys/subordinate/sysSubordinate.do?method=view&readOnly=true&modelId={%grid['fdId']%}&modelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo">
								<span class="com_author">{%grid['fdName']%}</span>
							</a>
						</li>
						<li title="{% grid['fdDeptName']%}" >${lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent')}：{% grid['fdDeptName'] %}</li>
			$}				
				if("" != grid['fdStaffNo'] && null !=grid['fdStaffNo'] && "" != grid['fdLoginName'] && null !=grid['fdLoginName']){
					{$
						<li title="{% grid['fdLoginName']%}|{% grid['fdStaffNo']%}">${lfn:message('hr-staff:hrStaffPersonInfo.CountOrNo')}：{% grid['fdLoginName'] %}|{% grid['fdStaffNo']%}</li>
					$}
				}else{
					{$
						<li title="{% grid['fdLoginName']%}{% grid['fdStaffNo']%}">${lfn:message('hr-staff:hrStaffPersonInfo.CountOrNo')}：{% grid['fdLoginName'] %}{% grid['fdStaffNo']%}</li>
					$}
				}
						{$  
							<li title="{% grid['fdTimeOfEnterprise']%}">${lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise')}：{% grid['fdTimeOfEnterprise']%}</li>
					 	$}
			{$
				    </ul>					
				</div>
			$}
		</list:row-template>
	</list:gridTable>
</list:listview> 
<list:paging></list:paging>
