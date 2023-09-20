<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysPersonMlinkCategory" list="${queryPage.list }">
		<list:data-column property="fdId" ></list:data-column>
		<!-- 名称 -->
		<list:data-column  property="fdName" title="${ lfn:message('sys-person:sysPersonMlinkCategory.fdName') }" />
		<!-- 是否启用 -->
		<list:data-column  col="fdEnabled" title="${ lfn:message('sys-person:sysPersonMlinkCategory.fdEnabled') }" >
			<sunbor:enumsShow enumsType="common_yesno" value="${sysPersonMlinkCategory.fdEnabled}"></sunbor:enumsShow>
		</list:data-column>
		<!-- 排序号 -->
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-person:sysPersonMlinkCategory.fdOrder') }" />
		<!-- 其它操作 -->
		<list:data-column headerClass="width250" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do?method=edit&fdId=${sysPersonMlinkCategory.fdId}"
						requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysPersonMlinkCategory.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do?method=delete&fdId=${sysPersonMlinkCategory.fdId}"
						requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysPersonMlinkCategory.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>	
					<kmss:auth requestURL="/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do?method=edit&fdId=${sysPersonMlinkCategory.fdId}"
						requestMethod="GET">
						<c:if test="${ sysPersonMlinkCategory.fdEnabled == true }">
							<a class="btn_txt" href="javascript:inverseEnable('${sysPersonMlinkCategory.fdId}')"><bean:message key="sysPersonMlinkCategory.enabled.jinyong" bundle="sys-person"/></a>
						</c:if>
						<c:if test="${ sysPersonMlinkCategory.fdEnabled == false }">
							<a class="btn_txt" href="javascript:inverseEnable('${sysPersonMlinkCategory.fdId}')"><bean:message key="sysPersonMlinkCategory.enabled.qiyong" bundle="sys-person"/></a>
						</c:if>
					</kmss:auth>	
				</div>
			</div>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>