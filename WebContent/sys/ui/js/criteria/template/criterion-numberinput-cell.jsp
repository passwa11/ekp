<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
<li class="criterion-all">
	<a href="javascript:;" title="${lfn:message('sys-ui:ui.criteria.all')}" class="selected"><span class="text">${lfn:message('sys-ui:ui.criteria.all')}</span></a>
</li>
$}
for (var i = 0 ; i < data.length; i ++) {
	{$<li data-critertion-number-min="{%data[i].min%}" data-critertion-number-max="{%data[i].max%}">$}
		if (data[i].min == 'MIN' && data[i].max == 'MAX') {
			{$
			<span class="criteria-input-number">
				<input type="text" name="lui-critertion-min">
			</span>
			<span class="grid">-</span>
			<span class="criteria-input-number">
				<input type="text" name="lui-critertion-max">
			</span>
			<input type="button" class="commit-action" value="${lfn:message('button.ok')}" title="${lfn:message('button.ok')}" />
			$}
		}
		else if (data[i].min == 'MIN') {
			{$
			<a href="javascript:;" title="{%data[i].min%}${lfn:message('sys-ui:ui.criteria.above')}">
				<i class="checkbox"></i><span class="text">{%data[i].max%}${lfn:message('sys-ui:ui.criteria.above')}</span></a>
			$}
		}
		else if (data[i].max == 'MAX') {
			{$
			<a href="javascript:;" title="{%data[i].min%}${lfn:message('sys-ui:ui.criteria.above')}">
				<i class="checkbox"></i><span class="text">{%data[i].min%}${lfn:message('sys-ui:ui.criteria.above')}</span></a>
			$}
		}
		else {
			{$
			<a href="javascript:;" title="{%data[i].min%} - {%data[i].max%}">
				<i class="checkbox"></i><span class="text">{%data[i].min%} - {%data[i].max%}</span></a>
			$}
		}
		{$<div class="lui_criteria_number_validate_container">
			<span class="lui_criteria_number_validate">
				<div class="lui_icon_s lui_icon_s_icon_validator">
				</div>
				<div class="text">请输入正确数字
				</div>
			</span>
		</div>
		$}
	{$</li>$}
	
}