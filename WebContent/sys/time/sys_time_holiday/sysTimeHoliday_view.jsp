<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
			<kmss:auth requestURL="/sys/time/sys_time_holiday/sysTimeHoliday.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('sysTimeHoliday.do?method=edit&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/time/sys_time_holiday/sysTimeHoliday.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('sysTimeHoliday.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="../sys_time_area/resource/css/maincss.css"/>
		<link type="text/css" rel="stylesheet" href="../sys_time_area/resource/css/css.css"/>
	</template:replace>
	<template:replace name="title">
		<bean:message bundle="sys-time" key="table.sysTimeHoliday"/>
	</template:replace>
	<template:replace name="content">
	
<p class="txttitle"><bean:message bundle="sys-time" key="table.sysTimeHoliday"/></p>

<center>
<table class="tb_normal" width=95%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHoliday.fdName"/>
		</td><td width="85%">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<%-- 所属场所 --%>
    <%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
		<tr>
			<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field_single.jsp" charEncoding="UTF-8">
                <c:param name="id" value="${sysTimeHolidayForm.authAreaId}"/>
            </c:import>
		</tr>
	<%} %>
	<tr>
		<td width="100%" colspan="4">
		<div class="lui_workforce_management_container" style="width: 100%">
			<div class="lui_workforce_management_tab">
		      <div class="lui_workforce_management_tab-content" style="padding: 5px 5px">
		        <ul class="lui_workforce_management_tab-content-list">
		          <li class="active">
		            <div class="lui_workforce_management-inner-tab">
		              <div class="lui_workforce_management-inner-tab-wrap">
		                <ul class="lui_workforce_management-inner-tab-list">
		                	<li class="lui_icon_arrow arrowL" style="padding: 0;margin-top: 4px;margin-left: 10px;"></li>
		                	<c:forEach items="${years}" var="tyear" varStatus="vstatus">
								<li class="year" data-year="${tyear }">
			                  	${tyear }
			                  	</li>
		                	</c:forEach>
		                	<li class="lui_icon_arrow arrowR" style="padding: 0;margin-top: 4px;"></li>
		                </ul>
		              </div>
		              <div class="lui_workforce_management-inner-tab-content-wrap">
		                <ul class="lui_workforce_management-inner-tab-content-list">
		                  <li class="active">
		                  	<c:import url="/sys/time/sys_time_holiday_detail/sysTimeHolidayDetail_view.jsp"
								charEncoding="UTF-8">
							</c:import>
		                  </li>
		                </ul>
		              </div>
		            </div>
		          </li>
		        </ul>
	      	</div>
	      </div>
	    </div>	      
	</td>			
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHoliday.docCreator"/>
		</td><td width="85%">
			<c:out value="${sysTimeHolidayForm.docCreatorName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHoliday.docCreateTime"/>
		</td><td width="85%">
			<xform:datetime property="docCreateTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHoliday.remark"/>
		</td><td width="85%">
			<bean:message bundle="sys-time" key="sysTimeHoliday.remark.desc"/>
		</td>
	</tr>
</table>
</center>
	<script>
		Com_IncludeFile("jquery.js");
	</script>
	<script>
		$(document).ready(function() {
			
			//点击年份逻辑
			$('.year').click(function() {
				$('.year').removeClass('active');
				$(this).addClass('active');
				
				var year = $(this).attr('data-year');
				$('[nm="dls"]').hide();
				$('[name="' + year + '"]').show();
			});
			
			//点击前后箭头逻辑
			$('.lui_icon_arrow ').click(function() {
				var currentYear = $('.year.active');
				if(!currentYear.get(0)) {
					return;
				}
				
				var resYear = null;
				
				if($(this).hasClass('arrowR')) {
					resYear = currentYear.next();
				} else if($(this).hasClass('arrowL')){
					resYear = currentYear.prev();
				}
				
				if(!resYear.get(0) || !resYear.hasClass('year')) {
					return;
				}
				
				resYear.click();
				
			});
			
			
			var firstYear = $('.year').get(0);
			if(firstYear) {
				$(firstYear).click();
			}
			
		});
	</script>
	</template:replace>
</template:include>