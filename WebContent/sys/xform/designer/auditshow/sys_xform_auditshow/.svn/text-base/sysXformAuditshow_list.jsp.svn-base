<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysXformAuditshowForm" list="${queryPage.list }">
		<list:data-column property="fdId" />
		
		<list:data-column property="fdName" style="width:60%;text-align: left" title="${ lfn:message('sys-xform-base:sysXformAuditshow.fdName')}" >
		</list:data-column>
		
		<list:data-column property="fdOrder" title="${ lfn:message('sys-xform-base:sysXformAuditshow.fdOrder')}" >
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/xform/auditshow/sys_xform_auditshow/sysXformAuditshow.do?method=edit" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysXformAuditshowForm.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/xform/auditshow/sys_xform_auditshow/sysXformAuditshow.do?method=copy" requestMethod="GET">
						<!-- 复制 -->
						<a class="btn_txt" href="javascript:copy('${sysXformAuditshowForm.fdId}')">${lfn:message('sys-xform-base:sysXformAuditshow.copy')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/xform/auditshow/sys_xform_auditshow/sysXformAuditshow.do?method=disableall" requestMethod="POST">
						<!-- 禁用 -->
						<c:choose>
							<c:when test="${ sysXformAuditshowForm.fdIsenabled == '1' }">
								<a class="btn_txt" href="javascript:disable('disable','${sysXformAuditshowForm.fdId}')">${lfn:message('sys-xform-base:sysXformAuditshow.disable')}</a>
							</c:when>
							<c:when test="${ sysXformAuditshowForm.fdIsenabled == '0' }">	
								<a class="btn_txt" href="javascript:disable('enabled','${sysXformAuditshowForm.fdId}')">${lfn:message('sys-xform-base:sysXformAuditshow.enabled')}</a>
							</c:when>
						</c:choose>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>