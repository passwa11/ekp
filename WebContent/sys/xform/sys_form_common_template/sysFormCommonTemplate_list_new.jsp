<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysFormCommonTemplate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column property="fdIsInside" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-xform:sysFormCommonTemplate.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdDefaultFlag" title="${ lfn:message('sys-number:sysNumberMain.fdDefaultFlag') }" escape="false">
			<c:if test="${sysFormCommonTemplate.fdIsDefault=='1' }">
				<img src='<c:url value="/sys/profile/resource/images/profile_list_status_y.png"/>'>
			</c:if>
			<c:if test="${sysFormCommonTemplate.fdIsDefault=='0' }">

			</c:if>
		</list:data-column>
		<list:data-column headerClass="width100" property="fdCreator.fdName" title="${ lfn:message('sys-xform:sysFormCommonTemplate.fdCreatorId') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdCreateTime" title="${ lfn:message('sys-xform:sysFormCommonTemplate.fdCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<c:if test="${sysFormCommonTemplate.fdIsInside!='1' }">
				<div class="conf_show_more_w">
					<div class="conf_btn_edit">
						<kmss:auth requestURL="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=edit&fdId=${sysFormCommonTemplate.fdId}" requestMethod="GET">
							<!-- 编辑 -->
							<a class="btn_txt" href="javascript:edit('${sysFormCommonTemplate.fdId}')">${lfn:message('button.edit')}</a>
						</kmss:auth>
						<kmss:auth requestURL="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=delete&fdId=${sysFormCommonTemplate.fdId}" requestMethod="POST">
							<!-- 删除 -->
							<a class="btn_txt" href="javascript:deleteAll('${sysFormCommonTemplate.fdId}')">${lfn:message('button.delete')}</a>
						</kmss:auth>
					</div>
				</div>
			</c:if>
			<c:if test="${sysFormCommonTemplate.fdIsInside=='1' }">
				<div class="conf_show_more_w">
					<div class="conf_btn_edit">
						<kmss:auth requestURL="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=edit&fdId=${sysFormCommonTemplate.fdId}" requestMethod="GET">
							<!-- 编辑 -->
							<span style="color:#d1d1d1;">${lfn:message('button.edit')}</span>
						</kmss:auth>
						<kmss:auth requestURL="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=delete&fdId=${sysFormCommonTemplate.fdId}" requestMethod="POST">
							<!-- 删除 -->
							<span style="color:#d1d1d1;">${lfn:message('button.delete')}</span>
						</kmss:auth>
					</div>
				</div>
			</c:if>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>