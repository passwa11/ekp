<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysMportalCard" list="${queryPage.list }">
		<list:data-column property="fdId"></list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${lfn:message('sys-mportal:sysMportalCard.fdOrder') }" />
		<list:data-column property="fdName" title="${lfn:message('sys-mportal:sysMportalCard.fdName') }" style="text-align:left;min-width:180px;padding-left:20px" />
		<list:data-column headerClass="width100" col="fdEnabled" title="${lfn:message('sys-mportal:sysMportalCard.fdEnabled') }" >
			<sunbor:enumsShow  enumsType="common_yesno"  value="${sysMportalCard.fdEnabled}" ></sunbor:enumsShow>
		</list:data-column>
		<list:data-column headerClass="width120" property="docCreator.fdName" title="${lfn:message('sys-mportal:sysMportalCard.docCreator') }" />
		<list:data-column headerClass="width160"  property="docCreateTime" title="${lfn:message('sys-mportal:sysMportalCard.docCreateTime') }" />
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/mportal/sys_mportal_card/sysMportalCard.do?method=edit&fdId=${sysMportalCard.fdId}" requestMethod="POST">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysMportalCard.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_card/sysMportalCard.do?method=delete&fdId=${sysMportalCard.fdId}" requestMethod="POST">
						<!-- 删除 --> 
						<a class="btn_txt" href="javascript:del('${sysMportalCard.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_card/sysMportalCard.do?method=edit&fdId=${sysMportalCard.fdId}" requestMethod="POST">
					<c:if test="${sysMportalCard.fdEnabled == false}">
						<a class="btn_txt" href="javascript:enableAll('${sysMportalCard.fdId}')">${lfn:message('sys-mportal:btn.fdIsAvailable.on')}</a>
					</c:if>
					<c:if test="${sysMportalCard.fdEnabled == true}">
						<a class="btn_txt" href="javascript:disableAll('${sysMportalCard.fdId}')">${lfn:message('sys-mportal:btn.fdIsAvailable.off')}</a>
					</c:if>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
	    <!-- 所属分类 -->
		<list:data-column property="fdModuleCate.fdName" title="${ lfn:message('sys-mportal:sysMportalCard.fdModuleCate') }" />
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
