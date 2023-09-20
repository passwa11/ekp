<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include
		pathFixed='yes' 
		ref="slide.view"
		leftWidth="250"
		leftShow ="yes"
		leftBar="yes"
		showPraise="yes">
    <template:replace name="head">
        <link rel="stylesheet" href="${LUI_ContextPath}/sys/help/sys_help_main/css/sysHelpMain_view.css" type="text/css" />
        <%@ include file="/sys/help/sys_help_main/sysHelpMain_view_js.jsp"%>
    </template:replace>
    <template:replace name="title">
        <c:out value="${sysHelpMainForm.docSubject} - " />
        <c:out value="${ lfn:message('sys-help:table.sysHelpMain') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function(dialog) {
                    dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                        if(isOk) {
                            Com_OpenWindow(delUrl, '_self');
                        }
                    });
                });
            }
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <!--发布-->
            <c:if test="${sysHelpMainForm.docStatus != '30' }">
	            <kmss:auth requestURL="/sys/help/sys_help_main/sysHelpMain.do?method=edit&fdId=${param.fdId}">
	                <ui:button text="${lfn:message('sys-help:sysHelpMain.publish')}" onclick="publishMain('${sysHelpMainForm.fdId}')" order="2" />
	            </kmss:auth>
            </c:if>
            <!--edit-->
            <kmss:auth requestURL="/sys/help/sys_help_main/sysHelpMain.do?method=edit&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('sysHelpMain.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
            </kmss:auth>
            <!--delete-->
            <kmss:auth requestURL="/sys/help/sys_help_main/sysHelpMain.do?method=delete&fdId=${param.fdId}">
                <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('sysHelpMain.do?method=delete&fdId=${param.fdId}');" order="4" />
            </kmss:auth>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />

        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
            <ui:menu-item text="${ lfn:message('sys-help:table.sysHelpMain') }" href="/sys/help/sys_help_main/" target="_self" />
        </ui:menu>
    </template:replace>
    
    <template:replace name="barLeft">
    	<div class="sysHelpView_left" id="r_cateinfo2">
			<div class="" style="padding-left: 10px;">
				<ul class="lui_sys_help_catelogUl" id="catelogUl" style="margin-bottom: 20px;">
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
						<a onclick="catelogScroll('${sysHelpCatelogForm.fdId}')" href="javascript:;" 
							<c:if test="${sysHelpCatelogForm.fdLevel == 2}"> class="lui_catelog_dot" </c:if>
							<c:if test="${sysHelpCatelogForm.fdLevel > 5}"> style="font-size: 12px" </c:if>
							<c:if test="${sysHelpCatelogForm.fdLevel < 6}"> style="font-size: ${17 - sysHelpCatelogForm.fdLevel}px;
							<c:if test="${sysHelpCatelogForm.fdLevel == 1}"> color: #15a4fa;</c:if>" </c:if>>
							<c:out value="${sysHelpCatelogForm.fdCateIndex}"/>&nbsp;
							<c:out value="${sysHelpCatelogForm.docSubject}"/> 
						</a>
						<div id="viewable_${sysHelpCatelogForm.fdId}"></div>
					</c:forEach>
				</ul>
			</div>
		</div>
    </template:replace>
    
    <template:replace name="content">
    
		<div class="sysHelpContent">
	    	<div class='lui_form_title_frame'>
				<div class='lui_form_subject'>
					<div class="sysHelp_title">
							${fn:escapeXml(sysHelpMainForm.docSubject)}
					</div>
					<div class="sysHelp_message">
					    <div class="sysHelp_docCreator com_author">
							<ui:person personId="${sysHelpMainForm.docCreatorId}" personName="${sysHelpMainForm.docCreatorName}"></ui:person>
						</div>
						<div class="sysHelp_docCreateTime">
							${sysHelpMainForm.docCreateTime}
						</div>
						<div class="sysHelp_docStatus">
							<sunbor:enumsShow value="${sysHelpMainForm.docStatus}" enumsType="common_status" />
						</div>
					</div>
				</div>
			</div>
		    
	    	<div class="lui_form_content">
				
				<!-- 文档目录 -->
				<%-- <div id="lui-sysHelp-catalog" class="lui_sys_help_catalog_top clearfloat">
					<div class="lui_sys_help_catalog_border_top"></div>
					<div class="lui_sys_help_catalog_title" style="float: left;">
						<i class="lui_sys_help_catalog_title_icon"></i>
						<span class="lui_sys_help_catalog_message"> 
							<bean:message key="sysHelpMain.catelogView" bundle="sys-help" />
						</span>
						<span style="display: inline-block;width:100%"></span>
					</div>
					<div id="sys_help_catalog_content" class="clearfloat lui_sys_help_catalog_content"></div>
					<div class="lui_sys_help_catalog_border_bottom"></div>
				</div> --%>
				
				<!-- 文档内容 -->
				<div id="_____rtf__temp_____${sysHelpMainForm.fdId }"></div>
				<div id="_____rtf_____${sysHelpMainForm.fdId }" style="display: none;width: 100%">
					<table width="100%" style="table-layout:fixed;hidden;word-break: break-all;word-wrap: break-word;">
						<%--内容 --%>
						<c:forEach  varStatus="status" items="${sysHelpMainForm.sysHelpCatelogList}"  var="sysHelpCatelogForm">
							<tr class="lui_sys_help_tr_title">
								<%-- 这里的顺序和生成目录时候获取数据的顺序是一样的 --%>
							    <div class="sysHelpMain_cate_hidden" style="display: none">
							    	<input type="hidden" name="fdId" value="${sysHelpCatelogForm.fdId}" />
							    	<input type="hidden" name="docSubject" value="${sysHelpCatelogForm.docSubject}" />
							    	<input type="hidden" name="fdOrder" value="${sysHelpCatelogForm.fdOrder}" />
							    	<input type="hidden" name="fdLevel" value="${sysHelpCatelogForm.fdLevel}" />
							    	<input type="hidden" name="fdCateIndex" value="${sysHelpCatelogForm.fdCateIndex}" />
							    </div>
								<%-- 目录标题 --%>
								<td class="lui_sys_help_td_l" id="sysHelpMain_view_fdName_${sysHelpCatelogForm.fdId}">
									<c:if test="${sysHelpCatelogForm.fdLevel == 1}">
									   <span class="com_bgcolor_d" style="width: 8px; display: inline-block; float: left; height: 15px; line-height: 30px; margin-top: 7px;margin-right: 3px;"></span>
									</c:if>
									<c:if test="${sysHelpCatelogForm.fdLevel != 1}">
									     <span class="com_bgcolor_d" style="width: 4px; display: inline-block; float: left; height: 15px; line-height: 30px; margin-top: 7px;margin-right: 3px;"></span>
									</c:if>
								    <span class="lui_sys_help_index">${sysHelpCatelogForm.fdCateIndex}</span>
								    <span class="lui_sys_help_catelog2" id="sysHelpMain_view_fdName_td_${sysHelpCatelogForm.fdId}">
								    	<c:out value="${sysHelpCatelogForm.docSubject}" />
								    </span>
							   </td>
							</tr>
							<tr class="lui_sys_help_tr_title1">
								<td class="lui_sys_help_td_r" valign="bottom" id="lui_sys_help_td_r_${sysHelpCatelogForm.fdId}">
									<c:if test="${not empty sysHelpCatelogForm.docSubject}">
							        	<!--设置关联页面 -->
										<span id="editParagraph_${sysHelpCatelogForm.fdId}"
									          class="lui_sys_help_editParagraph lui_sys_help_edit_cate com_subject"
									          onclick="Com_OpenWindow('${LUI_ContextPath}/sys/help/sys_help_catelog/sysHelpCatelog.do?method=editCatelogContent&fdParentId=1693209579a174971b5cb634bc59a35a&fdId=${sysHelpCatelogForm.fdId}','_blank');">
								        	<bean:message bundle="sys-help" key="sysHelpMain.selec.config" />
										</span>
							        	<!--编辑此段 -->
										<span id="editParagraph_${sysHelpCatelogForm.fdId}"
									          class="lui_sys_help_editParagraph lui_sys_help_edit_cate com_subject"
									          onclick="Com_OpenWindow('${LUI_ContextPath}/sys/help/sys_help_catelog/sysHelpCatelog.do?method=editCatelogContent&fdParentId=1693209579a174971b5cb634bc59a35a&fdId=${sysHelpCatelogForm.fdId}','_blank');">
								        	<bean:message bundle="sys-help" key="sysHelpMain.editParagraph" />
										</span>
						        	</c:if>
								</td>
							</tr>
							
							<tr class="lui_sys_help_catelog_content2">
								<td class="lui_sys_help_catelog_content2_td">
									<div>
										<div name="rtf_docContent" id="sysHelpMain_view_docContent_${sysHelpCatelogForm.fdId}">
											${sysHelpCatelogForm.docContent}
										</div>
									</div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
    </template:replace>

</template:include>