<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 草稿状态的文档默认选中基本信息页签 -->
<c:if test="${sysNewsMainForm.docStatus=='10'}">
	<ui:event event="layoutDone">
		$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
    </ui:event>
</c:if>
<ui:content title="${lfn:message('sys-news:sysNewsMain.baseInfo') }" titleicon="lui-fm-icon-2">
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!-- 录入者 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-news" key="sysNewsMain.docEntering" />
			</td>
			<td>
				<c:out value="${ sysNewsMainForm.fdCreatorName }"></c:out>
			</td>
		</tr>
		<c:if test="${sysNewsMainForm.fdIsWriter==false}">
		<!-- 所属部门 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-news" key="sysNewsMain.publishUnit" />
			</td>
			<td>
				<c:out value="${ sysNewsMainForm.fdDepartmentName }"></c:out>
			</td>
		</tr>
		</c:if>

		<!-- 创建时间 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-news" key="sysNewsMain.docEnteringTime" />
			</td>
			<td>
				<c:out value="${ sysNewsMainForm.docCreateTime }"></c:out>
			</td>
		</tr>
		<!-- 文档状态 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-news" key="sysNews.sysNewsMain.status" />
			</td>
			<td>
				<sunbor:enumsShow value="${sysNewsMainForm.docStatus}" enumsType="news_status" />
			</td>
		</tr>
		<!-- 所属分类 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="sys-news" key="sysNewsMain.fdTemplate" />
			</td>
			<td>
				<c:out value="${ sysNewsMainForm.fdTemplateName }"></c:out>
			</td>
		</tr>
		<table>
			<tr>
				<c:if test="${sysNewsMainForm.docStatus=='20'}">
					<c:if test="${hasImage eq true}">  
						<ul class='lui_form_info'>
							<li>${lfn:message("sys-news:sysNewsMain.fdMainPicture") }</li>
					    	<li>
							    <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="Attachment" />
									<c:param name="fdMulti" value="false" />
									<c:param name="fdAttType" value="pic" />
									<c:param name="fdImgHtmlProperty" value="width=120" />
									<c:param name="fdModelId" value="${sysNewsMainForm.fdId }" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
									<%-- 图片设定大小 --%>
									<c:param name="picWidth" value="258" />
									<c:param name="picHeight" value="192" />
									<c:param name="proportion" value="false" />
									<c:param name="fdLayoutType" value="pic"/>
									<c:param name="fdPicContentWidth" value="258"/>
									<c:param name="fdPicContentHeight" value="192"/>
									<c:param name="fdViewType" value="pic_single"/>
									<c:param name="fdShowMsg">true</c:param>
								</c:import>
					    	</li>
					    </ul>
					</c:if>
				</c:if>
			</tr>
		</table>
	</table>
</ui:content>