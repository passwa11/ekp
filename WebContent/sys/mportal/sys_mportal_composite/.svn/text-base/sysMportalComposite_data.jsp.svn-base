<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mportal.model.SysMportalComposite" %>
<%@ page import="com.landray.kmss.sys.mportal.util.SysMportalMportletUtil" %>
<list:data>
	<list:data-columns var="sysMportalComposite" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width30" property="fdOrder" title="${lfn:message('sys-mportal:sysMportalPage.fdOrder') }" />
		<!-- 名称 -->
		<list:data-column  property="fdName" title="${ lfn:message('sys-mportal:sysMportalPage.fdName') }" style="text-align:left;min-width:180px" />
		<list:data-column col="docStatus" title="${lfn:message('sys-mportal:sysMportalComposite.docStatus')}">
			<sunbor:enumsShow
				value="${sysMportalComposite.docStatus}"
				enumsType="common_status" />
		</list:data-column>
		<!-- 是否有效 -->
		<list:data-column  col="fdEnabled" title="${ lfn:message('sys-mportal:sysMportalPage.fdEnabled') }" >
			<sunbor:enumsShow enumsType="common_yesno"  value="${sysMportalComposite.fdEnabled}"></sunbor:enumsShow>
		</list:data-column>
		<list:data-column  escape="false" col="pages" title="页面" >
			<%
					Object obj = pageContext.getAttribute("sysMportalComposite");
					if(obj != null) {
						SysMportalComposite main = (SysMportalComposite)obj;
						out.print(SysMportalMportletUtil.getCompositePagesJsonById(main.getFdId()));
					}
				%>
		</list:data-column>
		<!-- 创建者 -->
		<list:data-column headerClass="width120" property="docCreator.fdName" title="${ lfn:message('sys-mportal:sysMportalPage.docCreator') }" />
		<!-- 创建时间 -->
		<list:data-column headerClass="width160" property="docCreateTime" title="${ lfn:message('sys-mportal:sysMportalPage.docCreateTime') }" />
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalComposite.do?method=edit&fdId=${sysMportalComposite.fdId }" requestMethod="POST">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysMportalComposite.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalComposite.do?method=delete&fdId=${sysMportalComposite.fdId }" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysMportalComposite.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalComposite.do?method=edit&fdId=${sysMportalComposite.fdId }" requestMethod="POST">
					<c:if test="${sysMportalComposite.fdEnabled == false && sysMportalComposite.docStatus == '30'}">
						<a class="btn_txt" href="javascript:enableAll('${sysMportalComposite.fdId}')">${lfn:message('sys-mportal:btn.fdIsAvailable.on')}</a>
					</c:if>
					<c:if test="${sysMportalComposite.fdEnabled == true && sysMportalComposite.docStatus == '30'}">
						<a class="btn_txt" href="javascript:disableAll('${sysMportalComposite.fdId}')">${lfn:message('sys-mportal:btn.fdIsAvailable.off')}</a>
					</c:if>
					</kmss:auth>
						<a class="btn_txt"  style="position:relative; " href="javascript:preview('${sysMportalComposite.fdId}')">
							<span><bean:message bundle="sys-mportal" key="sysMportal.preview"/></span>
							<div class="preview_content" style="height: 180px;width: 180px;text-align: center;" id="pre_${sysMportalComposite.fdId}"><div style="text-align: center;">${ lfn:message('sys-mportal:sysMportal.scanToPreview') }</div></div>
						</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>