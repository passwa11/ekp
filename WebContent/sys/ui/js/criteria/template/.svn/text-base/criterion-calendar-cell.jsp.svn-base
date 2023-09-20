<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
		<li class="criterion-all">
			<a href="javascript:;" title="${lfn:message('sys-ui:ui.criteria.all')}" class="selected"><span class="text">${lfn:message('sys-ui:ui.criteria.all')}</span></a>
			<select class="selected" style="height: 25px;">
				<option value="all">${lfn:message('sys-ui:ui.criteria.calendar.all')}</option>
				<option value="today">${lfn:message('sys-ui:ui.criteria.calendar.today')}</option>
				<option value="week">${lfn:message('sys-ui:ui.criteria.calendar.week')}</option>
				<option value="month">${lfn:message('sys-ui:ui.criteria.calendar.month')}</option>
				<option value="quarter">${lfn:message('sys-ui:ui.criteria.calendar.quarter')}</option>
				<option value="year">${lfn:message('sys-ui:ui.criteria.calendar.year')}</option>
				<option value="last_month">${lfn:message('sys-ui:ui.criteria.calendar.lastMonth')}</option>
				<option value="last_year">${lfn:message('sys-ui:ui.criteria.calendar.lastYear')}</option>
				<option value="custom">${lfn:message('sys-ui:ui.criteria.calendar.custom')}</option>
			</select>
		</li>
		<li class="lui_criteria_date_li">
		
			<span class="lui_criteria_date_span">
				<input type="text" name="{%data[0].name%}" data-lui-date-input-name="{%data[0].name%}" 
					 placeholder="{%data[0].placeholder%}">
				<div class="lui_criteria_date_select"></div>
			</span>
			
			<span class="grid">-</span>
			
			<span class="lui_criteria_date_span" >
				<input type="text" name="{%data[1].name%}" data-lui-date-input-name="{%data[1].name%}" 
					 placeholder="{%data[1].placeholder%}" >
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
