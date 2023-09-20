<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="pdaModuleConfigMain" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 模块名 -->
		<list:data-column headerClass="width300"  property="fdName" title="${ lfn:message('third-pda:pdaModuleConfigMain.fdName') }" />
		<!-- 序号 -->
		<list:data-column headerClass="width30"  property="fdOrder" title="${ lfn:message('third-pda:pdaModuleConfigMain.fdOrder') }" />
		<!-- 所属分类 -->
		<list:data-column property="fdModuleCate.fdName" title="${ lfn:message('third-pda:pdaModuleConfigMain.fdModuleCate') }" />
		<!-- 模块前缀 -->
		<list:data-column property="fdUrlPrefix" title="${ lfn:message('third-pda:pdaModuleConfigMain.fdUrlPrefix') }" />
		<!-- 图标 -->
		<list:data-column col="fdIconUrl" title="${ lfn:message('third-pda:pdaModuleConfigMain.fdIconUrl') }" escape="false">
			<img src="<c:url value="${pdaModuleConfigMain.fdIconUrl}" />" width="30px" height="30px">
		</list:data-column>
		<!-- 状态 -->
		<list:data-column col="fdStatus" title="${ lfn:message('third-pda:pdaModuleConfigMain.fdStatus') }" >
			<xform:select property="fdStatus" showStatus="view" value="${pdaModuleConfigMain.fdStatus}">
				<xform:enumsDataSource enumsType="pda_module_config_status" />
			</xform:select>
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column property="docCreator.fdName" title="${ lfn:message('third-pda:pdaModuleConfigMain.docCreator') }" />
		<!-- 创建时间 -->
		<list:data-column property="fdCreateTime" title="${ lfn:message('third-pda:pdaModuleConfigMain.fdCreateTime') }" />
		
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=edit&fdId=${pdaModuleConfigMain.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${pdaModuleConfigMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=delete&fdId=${pdaModuleConfigMain.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${pdaModuleConfigMain.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=update">
						<c:choose>
							<c:when test="${pdaModuleConfigMain.fdStatus == '0'}">						
								<!-- 启用 -->
								<a class="btn_txt" href="javascript:updateEnable(true,'${pdaModuleConfigMain.fdId}')">${lfn:message('third-pda:pdaModuleConfigMain.status.enable')}</a>
							</c:when>
							<c:otherwise>
								<!-- 禁用 -->
								<a class="btn_txt" href="javascript:updateEnable(false,'${pdaModuleConfigMain.fdId}')">${lfn:message('third-pda:pdaModuleConfigMain.status.disable')}</a>
							</c:otherwise>
						</c:choose>							
					</kmss:auth>
					<!-- 推送应用到钉钉 -->
					<kmss:ifModuleExist  path = "/third/ding/">
						<c:import url="/third/ding/common/syn_app_button.jsp" charEncoding="UTF-8">
							<c:param name="buttonType" value="2"></c:param>
							<c:param name="fdId" value="${pdaModuleConfigMain.fdId}"></c:param>
						</c:import>
					</kmss:ifModuleExist>			
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>