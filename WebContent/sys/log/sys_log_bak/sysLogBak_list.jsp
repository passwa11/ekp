<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysLogBak" list="${queryPage.list}">
        <list:data-column property="fdId" />
        <list:data-column col="fdDate" title="${lfn:message('date.label')}" headerClass="width80">
        	<c:out value="${sysLogBak.fdYear}" />${lfn:message('calendar.year')}
            <c:out value="${sysLogBak.fdMonth}" />${lfn:message('calendar.month')}
        </list:data-column>
        <list:data-column col="fdBackupDate" headerClass="width80" title="${lfn:message('sys-log:sysLogBak.fdBackupDate')}" escape="true">
            <kmss:showDate value="${sysLogBak.fdBackupDate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdCleanDate" headerClass="width80" title="${lfn:message('sys-log:sysLogBak.fdCleanDate')}" escape="true">
            <kmss:showDate value="${sysLogBak.fdCleanDate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdBackupStatus.name" title="${lfn:message('sys-log:sysLogBak.fdBackupStatus')}" headerClass="width60">
            <sunbor:enumsShow value="${sysLogBak.fdBackupStatus}" enumsType="sys_log_backup_backupStatus" />
        </list:data-column>
        <list:data-column col="fdBackupStatus">
            <c:out value="${sysLogBak.fdBackupStatus}" />
        </list:data-column>
        <list:data-column col="fdBackupSource.name" title="${lfn:message('sys-log:sysLogBak.fdBackupSource')}" headerClass="width40">
            <sunbor:enumsShow value="${sysLogBak.fdBackupSource}" enumsType="sys_log_backup_detail_source" />
        </list:data-column>
        <list:data-column col="fdBackupSource">
            <c:out value="${sysLogBak.fdBackupSource}" />
        </list:data-column>
        <list:data-column col="fdCleanStatus.name" title="${lfn:message('sys-log:sysLogBak.fdCleanStatus')}" headerClass="width60">
            <sunbor:enumsShow value="${sysLogBak.fdCleanStatus}" enumsType="sys_log_backup_cleanStatus" />
        </list:data-column>
        <list:data-column col="fdCleanStatus">
            <c:out value="${sysLogBak.fdCleanStatus}" />
        </list:data-column>
        <list:data-column col="fdRecoveryStatus.name" title="${lfn:message('sys-log:sysLogBak.fdRecoveryStatus')}" headerClass="width60">
            <sunbor:enumsShow value="${sysLogBak.fdRecoveryStatus}" enumsType="sys_log_backup_recoveryStatus" />
        </list:data-column>
        <list:data-column col="fdRecoveryStatus">
            <c:out value="${sysLogBak.fdRecoveryStatus}" />
        </list:data-column>
        <list:data-column property="fdDesc" title="${lfn:message('sys-log:sysLogBak.fdDesc')}" headerClass="width200" />
        <!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/log/sys_log_bak/sysLogBak.do?method=backup" requestMethod="POST">
						<!-- 所有操作都为非进行中的才可执行 -->
						<c:if test="${sysLogBak.fdBackupStatus != 1 && sysLogBak.fdCleanStatus != 1 && sysLogBak.fdRecoveryStatus != 1}">
							<!-- 不可操作当月数据 -->
							<c:if test="${currentMonth != sysLogBak.fdMonth}">
								<!-- 备份(未清理且不在备份中的可备份) -->
								<c:if test="${sysLogBak.fdBackupStatus != 1 && sysLogBak.fdCleanStatus == 0}">
									<a class="btn_txt" href="javascript:backup('${sysLogBak.fdId}')">${lfn:message('sys-log:enums.backup_detail_type.backup')}</a>
								</c:if>
								<!-- 清理 (已备份且未清理的可清理)-->
								<c:if test="${sysLogBak.fdBackupStatus == 2 && sysLogBak.fdCleanStatus == 0}">
									<a class="btn_txt" href="javascript:clean('${sysLogBak.fdId}')">${lfn:message('sys-log:enums.backup_detail_type.clean')}</a>
								</c:if>
								<!-- 恢复(已备份且已清理的可恢复) -->
								<c:if test="${sysLogBak.fdBackupStatus == 2 && sysLogBak.fdCleanStatus == 2}">
									<a class="btn_txt" href="javascript:recovery('${sysLogBak.fdId}')">${lfn:message('sys-log:enums.backup_detail_type.recovery')}</a>
								</c:if>
							</c:if>
						</c:if>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    
    <!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
