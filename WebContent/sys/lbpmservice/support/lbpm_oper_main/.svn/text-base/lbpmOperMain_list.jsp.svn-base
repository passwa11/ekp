<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="lbpmOperMain" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 方式名称 -->
		<list:data-column col="fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmOperMain.fdName') }" style="text-align:left;min-width:180px" escape="false">
			<xlang:lbpmlang property="fdName" langs="${lbpmOperMain.fdLangJson}"  value="${lbpmOperMain.fdName}" showStatus="list"/>
		</list:data-column>

		<!-- 节点类型 -->
		<list:data-column headerClass="width100" col="fdNodeType" title="${ lfn:message('sys-lbpmservice-support:lbpmOperMain.fdNodeType') }" escape="false">
			<xform:select property="fdNodeType" value="${lbpmOperMain.fdNodeType}" showStatus="view" >
	            <xform:customizeDataSource className="com.landray.kmss.sys.lbpmservice.support.service.spring.NodeTypeDataSource" />
	            <xform:simpleDataSource value="freeFlowSignNode"><bean:message bundle="sys-lbpmservice-support" key="lbpmOperMain.fdNodeType.freeFlowSignNode" /></xform:simpleDataSource>
			    <xform:simpleDataSource value="freeFlowReviewNode"><bean:message bundle="sys-lbpmservice-support" key="lbpmOperMain.fdNodeType.freeFlowReviewNode" /></xform:simpleDataSource>
	        </xform:select>
		</list:data-column>
		<!-- 是否默认 -->
		<list:data-column headerClass="width100"  col="fdIsDefault" title="${ lfn:message('sys-lbpmservice-support:lbpmOperMain.fdIsDefault') }" escape="false">
			<c:if test="${lbpmOperMain.fdIsDefault}">
				<img src='<c:url value="/sys/profile/resource/images/profile_list_status_y.png"/>'>
			</c:if>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_oper_main/lbpmOperMain.do?method=edit" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmOperMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_oper_main/lbpmOperMain.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${lbpmOperMain.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>