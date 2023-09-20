<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<div style="position: fixed;z-index:3;" id="add_nav_right_fixed">
	<ui:drawerpanel width="350">
		<ui:content title="${lfn:message('sys-help:sysHelpCatelog.catelogInfo')}" titleicon="lui_iconfont_navleft_catalog">
			<div class="" id="r_cateinfo2">
				<div class="">
					<c:if test="${empty param.catelogId}">
						<%--编辑段落时不能编辑目录 --%>
						<div style="display: inline-block;width: 100%;margin-bottom: 20px;">
							<div class="sys_help_btnopt" style="float:right; margin-right: 10px;" onclick="editCatelog()">
								<bean:message bundle="sys-help" key="sysHelpCatelog.editCatelog"/>
							</div>
						</div>
					</c:if>
					
					<ul class="lui_sys_help_catelogUl" id="catelogUl" style="margin-bottom: 20px; padding-left: 10px; padding-right: 10px;">
						<c:forEach items="${sysHelpMainForm.sysHelpCatelogList}" var="sysHelpCatelogForm" varStatus="varStatuses">
							<c:if test="${sysHelpCatelogForm.fdLevel == 1}">
								<li class="right_selectLi lui_sys_help_catelog_li" style="">
							</c:if>
							<c:if test="${sysHelpCatelogForm.fdLevel == 2}">
								<li class="right_selectLi lui_sys_help_catelog_li" style="padding-left: 10px;">
							</c:if>
							<c:if test="${sysHelpCatelogForm.fdLevel > 2}">
								<li class="right_selectLi lui_sys_help_catelog_li" style="padding-left: ${(sysHelpCatelogForm.fdLevel-1)*10 + 15}px;">
							</c:if>
								<a href="#catelogChild_${sysHelpCatelogForm.fdId}" 
									<c:if test="${sysHelpCatelogForm.fdLevel == 2}"> class="lui_catelog_dot" </c:if>
									<c:if test="${sysHelpCatelogForm.fdLevel > 5}"> style="font-size: 12px" </c:if>
									<c:if test="${sysHelpCatelogForm.fdLevel < 6}"> style="font-size: ${17 - sysHelpCatelogForm.fdLevel}px;
									<c:if test="${sysHelpCatelogForm.fdLevel == 1}"> color: #15a4fa;</c:if>" </c:if>>
									<c:out value="${sysHelpCatelogForm.fdCateIndex}"/>
									&nbsp;
									<c:out value="${sysHelpCatelogForm.docSubject}"/>
								</a>
								<div id="viewable_${sysHelpCatelogForm.fdId}"></div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</ui:content>
		
	</ui:drawerpanel>
</div>