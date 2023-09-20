<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
		<li name="chekbox">
			<a href="javascript:;" data-lui-val="all" title="${lfn:message('sys-ui:ui.criteria.all')}" class="selected"><span class="text">${lfn:message('sys-ui:ui.criteria.all')}</span></a>
		</li>
		<li class="lui_criteria_date_li">
			<span class="lui_criteria_date_span">
				<input type="text" name="{%data[0].name%}" data-lui-date-input-name="{%data[0].name%}" 
					 placeholder="HH:mm">
				<div class="lui_criteria_date_select"></div>
			</span>
			
			<span class="grid">-</span>
			
			<span class="lui_criteria_date_span" >
				<input type="text" name="{%data[1].name%}" data-lui-date-input-name="{%data[1].name%}" 
					 placeholder="HH:mm" >
				<div class="lui_criteria_date_select"></div>
			</span>
			
			<input type="button" class="commit-action" value="${lfn:message('button.ok')}" title="${lfn:message('button.ok')}" />
			
			<div class="lui_criteria_date_validate_container">
				<span class="lui_criteria_date_validate" data-lui-date-input-validate="{%data[0].name%}">
					<div class="lui_icon_s lui_icon_s_icon_validator">
					</div>
					<div class="text">
					</div>
				</span>
				<span class="lui_criteria_date_validate sec" data-lui-date-input-validate="{%data[1].name%}">
					<div class="lui_icon_s lui_icon_s_icon_validator">
					</div>
					<div class="text">
					</div>
				</span>
			</div>
			<div class="lui_criteria_date_validate_container area">
				<span class="lui_criteria_date_validate">
					<div class="lui_icon_s lui_icon_s_icon_validator">
					</div>
					<div class="text">
					</div>
				</span>
			</div>
		</li>
$}
