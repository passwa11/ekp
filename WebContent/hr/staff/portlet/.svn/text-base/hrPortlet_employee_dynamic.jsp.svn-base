<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}" />
<div>
	<ui:dataview>
			<ui:source type="AjaxJson">{url:'/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=overview'}</ui:source>
			<ui:render type="Template">
			{$
				<div class="lui_hr_staff_panel_content">
					<!-- 动态树 -->
					<div class="hr_staff_trends_tree">
			$} 
			if(data.length < 1) { 
				{$
				<div style="text-align: center;">
					<span class="trend_info">${ lfn:message('message.noRecord') }</span>
				</div>
				$} 
			} for(var i=0; i <data.length ; i++) {
				var obj=data[i];// 这里的数据是对象，对象里有2个属性，一个是年月(title)，另一个是数组数据(list)
				{$
					<dl>
						<dt><i class="lui_icon_s icon_clock icon_date"></i><span>{% obj.title %}</span></dt>
				$}
					for(var j=0; j<obj.list.length; j++) {
						{$
							<dd>
								<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=view&fdId={% obj.list[j].fdId %}" target="_blank">
									<span class="trend_date">{% obj.list[j].fdCreateTime %}</span>
									<span class="trend_info"><em>{% obj.list[j].fdCreator %}</em> {% obj.list[j].fdDetails %}</span>
								</a>
							</dd>
						$}
					}
				{$</dl>$}
			}
			{$
				</div>
			</div>
			$}
			</ui:render>
		</ui:dataview>
</div>
