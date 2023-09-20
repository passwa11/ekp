<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.IDGenerator" %>
<%@ page import="com.landray.kmss.sys.mportal.model.SysMportalCpage" %>
<%@ page import="com.landray.kmss.sys.mportal.util.SysMportalMportletUtil" %>
<list:data>
	<list:data-columns var="sysMportalCpage" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column headerClass="width30" property="fdOrder" title="${lfn:message('sys-mportal:sysMportalCpage.fdOrder') }" />
		<!-- 名称 -->
		<list:data-column  property="fdName" title="${ lfn:message('sys-mportal:sysMportalCpage.fdName') }" style="text-align:left;min-width:180px" />
		<!-- 名称 -->
		<list:data-column  col="fdNameWithComposite" title="${ lfn:message('sys-mportal:sysMportalCpage.fdName') }" style="text-align:left;min-width:180px">
			<%
					Object obj = pageContext.getAttribute("sysMportalCpage");
					if(obj != null) {
						SysMportalCpage main = (SysMportalCpage)obj;
						out.print(main.getFdName() + " ：" + SysMportalMportletUtil.getStringCompositeByPageId(main.getFdId()));
					}
				%>
		</list:data-column>
		<!-- 名称 -->
		<list:data-column  property="fdIcon" title="${ lfn:message('sys-mportal:sysMportalCpage.fdIcon') }" />
		<list:data-column  property="fdImg" title="${ lfn:message('sys-mportal:sysMportalCpage.fdImg') }" />
		<list:data-column  col="temporaryFdId" title="temporaryFdId" >
			<%=IDGenerator.generateID() %>
		</list:data-column>
		<!-- 是否有效 -->
		<list:data-column  col="fdEnabled" title="${ lfn:message('sys-mportal:sysMportalCpage.fdEnabled') }" >
			<sunbor:enumsShow enumsType="common_yesno"  value="${sysMportalCpage.fdEnabled}"></sunbor:enumsShow>
		</list:data-column>
		<!-- 类型 -->
		<list:data-column  property="fdType" title="${ lfn:message('sys-mportal:sysMportalCpage.fdType') }" >
		</list:data-column>
		<list:data-column  col="fdTypeLang" title="${ lfn:message('sys-mportal:sysMportalCpage.fdType') }" >
			<c:if test="${sysMportalCpage.fdType == '1'}">
				${ lfn:message('sys-mportal:sysMportalCpage.fdType.page') }
			</c:if>
			<c:if test="${sysMportalCpage.fdType == '2'}">
				${ lfn:message('sys-mportal:sysMportalCpage.fdType.url') }
			</c:if>
		</list:data-column>
		<!-- 创建者 -->
		<list:data-column headerClass="width120" property="docCreator.fdName" title="${ lfn:message('sys-mportal:sysMportalCpage.docCreator') }" />
		<!-- 创建时间 -->
		<list:data-column headerClass="width160" property="docCreateTime" title="${ lfn:message('sys-mportal:sysMportalCpage.docCreateTime') }" />
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=edit&fdId=${sysMportalCpage.fdId }" requestMethod="POST">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysMportalCpage.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=delete&fdId=${sysMportalCpage.fdId }" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysMportalCpage.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=edit&fdId=${sysMportalCpage.fdId }" requestMethod="POST">
					<c:if test="${sysMportalCpage.fdEnabled == false}">
						<a class="btn_txt" href="javascript:enableAll('${sysMportalCpage.fdId}')">${lfn:message('sys-mportal:btn.fdIsAvailable.on')}</a>
					</c:if>
					<c:if test="${sysMportalCpage.fdEnabled == true}">
						<a class="btn_txt" href="javascript:disableAll('${sysMportalCpage.fdId}')">${lfn:message('sys-mportal:btn.fdIsAvailable.off')}</a>
					</c:if>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>