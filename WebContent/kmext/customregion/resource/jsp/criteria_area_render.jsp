<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
	<li>
		<span class="criteria-input-text">
			<SELECT class="selected customregion-select-country" style="width: 130px;height: 26px;cursor:pointer;">
             </SELECT>
 			 <SELECT class="selected customregion-select-province" style="width: 120px;height: 26px;cursor:pointer;">
             </SELECT>
             <!-- <SELECT class="selected customregion-select-city" style="width: 120px;height: 26px;cursor:pointer;">
             </SELECT> -->
		</span>
		<input type="button" class="commit-action" value="${lfn:message('button.ok')}" />
		
		<div class="lui_criteria_number_validate_container" >
			<span class="lui_criteria_number_validate" style="width: 500px">
				<div class="lui_icon_s lui_icon_s_icon_validator" ></div>
				<div class="text" style="width: 450px"></div>
			</span>
		</div>
	
	</li>
$}