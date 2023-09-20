<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrStaffPersonInfoLog" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 操作时间-->
		<list:data-column headerClass="width100" col="fdCreateTime" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdCreateTime') }">
		   <kmss:showDate value="${hrStaffPersonInfoLog.fdCreateTime}" type="datetime" /> 
		</list:data-column>
		<!--IP地址-->
		<list:data-column headerClass="width80" property="fdIp" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdIp') }"> 
		</list:data-column>
		<!--浏览器-->
		<list:data-column headerClass="width100" property="fdBrowser" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdBrowser') }">
		</list:data-column>
		<!--设备-->
		<list:data-column headerClass="width100" property="fdEquipment" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdEquipment') }">
		</list:data-column> 
		<!--操作者-->
		<list:data-column headerClass="width100" col="fdCreator" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdCreator') }">
			<c:choose>
				<c:when test="${hrStaffPersonInfoLog.isAnonymous}">
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.sync.creator') }
				</c:when>
				<c:otherwise>
					${hrStaffPersonInfoLog.fdCreator.fdName}
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!--操作方法-->
		<list:data-column headerClass="width100" col="fdParaMethod" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdParaMethod') }">
			<sunbor:enumsShow value="${ hrStaffPersonInfoLog.fdParaMethod }" enumsType="hrStaffPersonInfoLog_fdParaMethod" />
		</list:data-column>
		<!--操作记录-->
		<list:data-column headerClass="width160" col="fdDetails" title="${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdDetails') }" escape="false">
			<span style="width:200px" class="textEllipsis" title="${hrStaffPersonInfoLog.fdDetails}">${hrStaffPersonInfoLog.fdDetails}</span>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:authShow roles="ROLE_HRSTAFF_LOG_DELETE">
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:del('${hrStaffPersonInfoLog.fdId}')">${ lfn:message('button.delete') }</a>
					</kmss:authShow>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>