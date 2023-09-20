<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.km.calendar.model.KmCalendarAgendaLabel" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmCalendarAgendaLabel" list="${queryPage.list }">
		<list:data-column property="fdId" />
			<list:data-column  col="fdCheckBox" title="" escape="false">
		   <c:if test="${!kmCalendarAgendaLabel.isAgendaLabel}">
		     <input type="checkbox" name="List_Selected" data-lui-mark="table.content.checkbox" value="${kmCalendarAgendaLabel.fdId}">
		   </c:if>
		   <c:if test="${kmCalendarAgendaLabel.isAgendaLabel}">
		     <input type="checkbox" name="List_Selected" disabled="disabled">
		   </c:if>
		</list:data-column>
		<list:data-column  col="fdSerial" title="${ lfn:message('page.serial')}" escape="false">
		   ${status+1}
		</list:data-column>
		<list:data-column  headerClass="width140" col="fdName" title="${ lfn:message('km-calendar:kmCalendarAgendaLabel.fdName') }" style="text-align:center;min-width:120px">
			<%
				KmCalendarAgendaLabel kmCalendarAgendaLabel = (KmCalendarAgendaLabel)pageContext.getAttribute("kmCalendarAgendaLabel");
				String fdAgendaModelName = kmCalendarAgendaLabel.getFdAgendaModelName();
				SysDictModel sysDictModel = SysDataDict.getInstance().getModel(fdAgendaModelName);
				if(kmCalendarAgendaLabel.getIsAgendaLabel() && sysDictModel != null){
					String messageKey = sysDictModel.getMessageKey();
					String fdName_lang = ResourceUtil.getString(messageKey);
					out.print(fdName_lang);
				}else{
					out.print(kmCalendarAgendaLabel.getFdName());
				}
			%>
		</list:data-column>
		<list:data-column headerClass="width160" property="fdDescription" title="${ lfn:message('km-calendar:kmCalendarAgendaLabel.remark') }">
		</list:data-column>
		<list:data-column  property="fdAgendaModelName" title="${ lfn:message('km-calendar:kmCalendarAgendaLabel.fdModelName') }">
		</list:data-column>
		<list:data-column headerClass="width60" col="fdIsAvailable" title="${ lfn:message('km-calendar:kmCalendarAgendaLabel.enable') }" escape="false">
		   <sunbor:enumsShow value="${kmCalendarAgendaLabel.fdIsAvailable}" enumsType="common_yesno" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=edit&fdId=${kmCalendarAgendaLabel.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmCalendarAgendaLabel.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<c:if test="${!kmCalendarAgendaLabel.isAgendaLabel}">
					<kmss:auth requestURL="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=delete&fdId=${kmCalendarAgendaLabel.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${kmCalendarAgendaLabel.fdId}')">
							<bean:message key="button.delete"/>
						</a>
					</kmss:auth>
					</c:if>
					<c:if test="${ empty kmCalendarAgendaLabel.fdIsAvailable ||  kmCalendarAgendaLabel.fdIsAvailable==false}">
						<a class="btn_txt" href="javascript:enable('${kmCalendarAgendaLabel.fdId}')">
							<bean:message bundle="km-calendar" key="kmCalendarMain.btn.open"/>
						</a>
					</c:if>
					<c:if test="${kmCalendarAgendaLabel.fdIsAvailable==true}">
						<a class="btn_txt" href="javascript:disable('${kmCalendarAgendaLabel.fdId}')">
							<bean:message bundle="km-calendar" key="kmCalendarMain.btn.close"/>
						</a>
					</c:if>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>