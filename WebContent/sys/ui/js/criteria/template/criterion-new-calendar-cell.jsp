<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
		<li name="chekbox">
			<a href="javascript:;" data-lui-val="6" title="${lfn:message('sys-ui:ui.criteria.all')}" class="selected"><span class="text">${lfn:message('sys-ui:ui.criteria.all')}</span></a>
		</li>
		<li name="chekbox"><a href="javascript:;" title="${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_week')}" 
			data-lui-val="1"><i class="checkbox"></i><span class="text">${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_week')}</span>
			</a>
		</li>		
		<li name="chekbox"><a href="javascript:;" title="${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_month')}" 
			data-lui-val="2"><i class="checkbox"></i><span class="text">${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_month')}</span>
			</a>
		</li>
		<li name="chekbox"><a href="javascript:;" title="${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_three_month')}" 
			data-lui-val="3"><i class="checkbox"></i><span class="text">${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_three_month')}</span>
			</a>
		</li>
		<li name="chekbox"><a href="javascript:;" title="${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_harf_a_year')}" 
			data-lui-val="4"><i class="checkbox"></i><span class="text">${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_harf_a_year')}</span>
			</a>
		</li>		
		<li name="chekbox"><a href="javascript:;" title="${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_year')}" 
			data-lui-val="5"><i class="checkbox"></i><span class="text">${lfn:message('sys-profile:enumeration_sys_show_config_load_data_volume_year')}</span>
			</a>
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
