<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.organization.util.SysOrgEcoUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" showQrcode="false">
	<template:replace name="head">
		<script>
			Com_IncludeFile("doclist.js");
			seajs.use(["sys/mportal/sys_mportal_page/css/edit.css"]);
		</script>
		<style>
			#TABLE_DocList > tbody >tr > td, #TABLE_DocList_type2 > tbody >tr > td {
				border: none;
			}
			.page_btn {
				cursor:pointer !important;
				border:none;
				background:none;
				font-size:12px;
			}
			.td_icon {
				cursor:pointer;
			}
			.td_icon_none {
				border: 1px solid;
				border-radius: 4px;
				width: 80px;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>

	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%" count="4">
			<c:choose>
				<c:when test="${ sysMportalCompositeForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }"
						onclick="Com_Submit(document.sysMportalCompositeForm, 'update');">
					</ui:button>
					<ui:button text="${ lfn:message('sys-mportal:sysMportal.saveAndPreview') }" onclick="saveDraftAndPerview()"></ui:button>
				</c:when>
				<c:when test="${ sysMportalCompositeForm.method_GET == 'add' }">
					<ui:button text="${ lfn:message('button.save') }"
						onclick="Com_Submit(document.sysMportalCompositeForm, 'save');">
					</ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }"
						onclick="Com_Submit(document.sysMportalCompositeForm, 'saveadd');"></ui:button>
					<ui:button text="${ lfn:message('sys-mportal:sysMportal.saveDraftAndPreview') }" onclick="saveDraftAndPerview()"></ui:button>
				</c:when>
			</c:choose>

			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
			<ui:menu-item
				text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home"
				href="/index.jsp">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item1') }">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item2') }">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportal.profile.maintain') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-mportal:sysMportal.profile.public') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-mportal:table.sysMportalComposite') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
	<div class="lui_form_content" style="padding:10px 0">
	<html:form action="/sys/mportal/sys_mportal_composite/sysMportalComposite.do">
		<p class="txttitle">
			<bean:message bundle="sys-mportal" key="table.sysMportalComposite"/>
		</p>
		<center>
		<table class="tb_normal" width=95%>
			<tr>
				<!-- 名称 -->
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalComposite.fdName"/>
				</td>
				<td width="35%">
				    <xform:text subject="${lfn:message('sys-mportal:sysMportalComposite.fdName')}" property="fdName" style="width:85%" required="true"  validators="required maxLength(100)"
				     isLoadDataDict="false" />
				</td>
				<!-- 排序号 -->
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalComposite.fdOrder"/>
				</td>
				<td width="35%">
					<xform:text property="fdOrder" style="width:85%" validators="number"/>
				</td>
			</tr>


	   	 <tr>
	   	 	<!-- 是否启用 -->
	   	 	<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalComposite.fdEnabled"/>
			</td>
			<td width="35%">
				<xform:radio property="fdEnabled">
					<xform:enumsDataSource enumsType="common_yesno" />
				</xform:radio>
			</td>
			 <!-- LOGO -->
		   	 <td class="td_normal_title" width=15%>
					LOGO
			</td>
			<td width="35%" >
				<html:hidden property="fdLogo"/>
				<div class="fdLogoImg">
					<c:if test="${not empty sysMportalCompositeForm.fdLogo}">
						<div class="fdLogoImgBg">
							<img
								class="lui_mportal_logo"
								alt="${lfn:message('sys-mportal:sysMportal.profile.logo.alt') }"
								src="${LUI_ContextPath }${sysMportalCompositeForm.fdLogo}"/>
						</div>
					</c:if>
				</div>
				<ui:button text="${lfn:message('sys-mportal:sysMportal.profile.logo.select') }" onclick="selectLogo();"></ui:button>
			</td>
		</tr>
		<tr>
			<!-- 头部设置 -->
	   	 	<%-- <td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalComposite.fdHeadType"/>
			</td>
			<td width="35%">
				<xform:radio property="fdHeadType">
					<xform:enumsDataSource enumsType="sysMportalPage_headerType" />
				</xform:radio>
			</td> --%>
			<!-- 导航布局 -->
	   	 	<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalComposite.fdNavLayout"/>
			</td>
			<td width="35%" colspan='3'>
				<xform:radio property="fdNavLayout" onValueChange="__changeNavLayout">
					<xform:enumsDataSource enumsType="sysMportalComposite_fdNavLayout" />
				</xform:radio>
			</td>

		</tr>

	   	 <tr>
	   	 	<% if (StringUtil.isNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) {
						pageContext.setAttribute("colspan", "colspan='3'");
			} %>
			<!-- 头部门户切换 -->
	   	 	<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalComposite.fdHeadChangeEnabled"/>
			</td>
			<td width="35%" ${colspan}>
					<xform:radio property="fdHeadChangeEnabled"
								 validators="required" required="true">
						<xform:enumsDataSource enumsType="sysMportalComposite_fdHeadChangeEnabled" />
					</xform:radio>
				<xform:text property="fdUrl" style="width:75%;${sysMportalPageForm.fdType == '2' ? '' :'display:none;' }"></xform:text>
			</td>
			<!-- 语言 -->
			<% if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) { %>
			<td class="td_normal_title" width=15%>
				${ lfn:message('sys-portal:sysPortalMain.fdLang') }
			</td>
			<td width="35%">
				<xform:select property="fdLang" showPleaseSelect="false">
					<xform:customizeDataSource className="com.landray.kmss.sys.mportal.service.spring.SysMportalLangDataSource"></xform:customizeDataSource>
				</xform:select>
			</td>
			<% } %>

	   	 </tr>

	   	<!-- 顶部导航布局 Starts  -->
		<tr id="__mportlet_pages_fdNavLayout_type1" style="${sysMportalCompositeForm.fdNavLayout == '2' ? 'display:none;' :'' }">
			<td class="td_normal_title" width=15%>
				${lfn:message("sys-mportal:sysMportal.msg.pageSet")}
			</td>
			<td colspan="3" width="85%">
				<table id="TABLE_DocList" class="tb_normal" width=100%>
					<tr>
						<td align="center" class="td_normal_title">
							${ lfn:message('sys-mportal:sysMportalPageCard.fdName') }
						</td>
						<td align="center" class="td_normal_title">
							${lfn:message("sys-mportal:sysMportalCpageRelation.fdIcon")}
						</td>
						<td align="center" class="td_normal_title">
							${lfn:message("sys-mportal:sysMportalCpageRelation.fdType")}
						</td>
						<td width="15%" align="center" class="td_normal_title">
							<ui:button text="${lfn:message('sys-mportal:sysMportal.btn.relationPage')}" onclick="selectPage();"></ui:button>
						</td>
					</tr>
					<%-- 模版行 --%>
					<tr style="display:none;" KMSS_IsReferRow="1">
						<td>
							<input  type="hidden"
									class="td_fdId_hidden_input"
									name="type1_pages[!{index}].fdId" />
							<input  type="hidden"
									class="td_fdOrder_hidden_input"
									name="type1_pages[!{index}].fdOrder" />
							<input  type="hidden"
									name="type1_pages[!{index}].sysMportalCpageId"
									value="" />
							<input type="hidden"
									name="type1_pages[!{index}].sysMportalCompositeId"
									value="" />
							<input type="hidden"
									name="type1_pages[!{index}].fdParentId"
									value="" />
							<xform:text
							    property="type1_pages[!{index}].fdName"
								style="width:65%"
								subject="${lfn:message('sys-mportal:sysMportalPageCard.fdName') }"
								required="true"
								validators="required maxLength(100)"/>
								<span class="lui_oname"></span>
						</td>

						<td align="center">
							<div class="td_icon mui" onclick="__changeIcon(this)">
							</div>
							<input class="td_img_validate td_img_hidden_input" type="hidden" name="type1_pages[!{index}].fdImg" value="" />
							<input class="td_icon_validate td_icon_hidden_input" type="hidden" name="type1_pages[!{index}].fdIcon" value="" />
						</td>

						<td align="center">
							<div class="td_type">
							</div>
							<input class="td_type_hidden_input" type="hidden" name="type1_pages[!{index}].fdType" value="" />
							<input class="td_cpageType_hidden_input" type="hidden" name="type1_pages[!{index}].fdCpageType" value="" />

						</td>

						<td align="center">
							<input type="button" class="page_btn" onclick="DocList_DeleteRow();" value="${lfn:message('sys-mportal:sysMportal.btn.delete')}"/>
							<input type="button" class="page_btn" onclick="DocList_MoveRow(-1);" value="${lfn:message('sys-mportal:sysMportal.btn.moveUp')}"/>
							<input type="button" class="page_btn" onclick="DocList_MoveRow(1);" value="${lfn:message('sys-mportal:sysMportal.btn.moveDown')}"/>
						</td>
					</tr>
					<%--内容行  --%>
					<c:if test="${empty sysMportalCompositeForm.fdNavLayout || sysMportalCompositeForm.fdNavLayout == '1'}">
					<c:forEach items="${sysMportalCompositeForm.pages}" var="page" varStatus="vstatus">
						<tr KMSS_IsContentRow="1" mportal_page_level="1">
							<td width="60%">
								<input  type="hidden"
									class="td_fdId_hidden_input"
									name="type1_pages[${vstatus.index}].fdId" value="${page.fdId }" />
								<input  type="hidden"
									class="td_fdOrder_hidden_input"
									name="type1_pages[${vstatus.index}].fdOrder" value="${page.fdOrder }" />
								<input  type="hidden"
												name="type1_pages[${vstatus.index}].sysMportalCpageId"
												value="${ page.sysMportalCpageId }" />
								<input type="hidden"
												name="type1_pages[${vstatus.index}].sysMportalCompositeId"
												value="${ page.sysMportalCompositeId }" />
								<xform:text
								    property="type1_pages[${vstatus.index}].fdName"
									style="width:65%"
									value="${page.fdName}"
									subject="${lfn:message('sys-mportal:sysMportalPageCard.fdName')}"
									required="true"
									validators="required maxLength(100)"/>
								<span class="lui_oname">[<c:out value="${page.sysMportalCpageName}" />]</span>
							</td>

							<td align="center">
								<c:choose>
									<c:when test="${not empty page.fdIcon && fn:indexOf(page.fdIcon, 'mui')<0 }">
										<div class="mui" claz="${page.fdIcon}" onclick="__changeIcon(this)" >
											${page.fdIcon}
										</div>
									</c:when>
									<c:otherwise>
										<c:choose>
										<c:when test="${not empty page.fdImg}">
											<div class="mui imgBox" claz="" onclick="__changeIcon(this)" style="background: url('${LUI_ContextPath}${page.fdImg}') no-repeat center;background-size: contain">
											</div>
										</c:when>
											<c:otherwise>
												<div class="mui ${page.fdIcon}" claz="${page.fdIcon}" onclick="__changeIcon(this)">
												</div>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<input class="td_img_hidden_input" type="hidden" name="type1_pages[${vstatus.index}].fdImg" value="${page.fdImg}" />
								<input class="td_icon_hidden_input" type="hidden" name="type1_pages[${vstatus.index}].fdIcon" value="${page.fdIcon}" />

							</td>

							<td align="center">
								<div class="td_type">
									<c:if test="${page.fdType == '1'}">
										${lfn:message('sys-mportal:sysMportal.msg.page')}：
										<c:if test="${page.sysMportalCpageType == '1'}">
												${ lfn:message('sys-mportal:sysMportalCpage.fdType.page') }
										</c:if>
										<c:if test="${page.sysMportalCpageType == '2'}">
												${ lfn:message('sys-mportal:sysMportalCpage.fdType.url') }
										</c:if>
									</c:if>
								</div>
								<input class="td_type_hidden_input" type="hidden" name="type1_pages[${vstatus.index}].fdType" value="${page.fdType}" />
								<input class="td_cpageType_hidden_input" type="hidden" name="type1_pages[${vstatus.index}].fdCpageType" value="${page.sysMportalCpageType}" />

							</td>
							<td width="15%" align="center">
								<input type="button" class="page_btn" onclick="DocList_DeleteRow();" value="${lfn:message('sys-mportal:sysMportal.btn.delete')}"/>
								<input type="button" class="page_btn" onclick="DocList_MoveRow(-1);" value="${lfn:message('sys-mportal:sysMportal.btn.moveUp')}"/>
								<input type="button" class="page_btn" onclick="DocList_MoveRow(1);" value="${lfn:message('sys-mportal:sysMportal.btn.moveDown')}"/>
							</td>
						</tr>
					</c:forEach>
					</c:if>
				</table>
			</td>
		</tr>
		<!-- 顶部导航布局 Starts  -->

		<!-- 底部导航布局 Starts  -->
		<tr id="__mportlet_pages_fdNavLayout_type2" style="${(empty sysMportalCompositeForm.fdNavLayout || sysMportalCompositeForm.fdNavLayout == '1') ? 'display:none;' :'' }">
			<td class="td_normal_title" width=15%>
				${lfn:message('sys-mportal:sysMportal.msg.pageSet')}
			</td>
			<td colspan="3" width="85%">
				<table id="TABLE_DocList_type2" class="tb_normal" width=100%>
					<tr>
						<td align="center" class="td_normal_title">
							${ lfn:message('sys-mportal:sysMportalPageCard.fdName') }
						</td>
						<td width="15%" align="center" class="td_normal_title">
							${lfn:message('sys-mportal:sysMportalCpageRelation.fdIcon')}
						</td>
						<td width="20%" align="center" class="td_normal_title">
							${lfn:message('sys-mportal:sysMportalCpageRelation.fdType')}
						</td>
						<td width="25%" align="center" class="td_normal_title">
							<ui:button text="${lfn:message('sys-mportal:sysMportal.btn.addTab')}" onclick="__addTab(this);"></ui:button>
						</td>
					</tr>
					<%-- 模版行 --%>
					<tr style="display:none;" KMSS_IsReferRow="1">
						<td>
							<input  type="hidden"
									class="td_fdId_hidden_input"
									name="type2_pages[!{index}].fdId" />
							<input type="hidden"
									class="td_fdOrder_hidden_input"
									name="type2_pages[!{index}].fdOrder"
									value="" />
							<input  type="hidden"
									name="type2_pages[!{index}].sysMportalCpageId"
									value="" />
							<input type="hidden"
									name="type2_pages[!{index}].sysMportalCompositeId"
									value="" />
							<input type="hidden"
									name="type2_pages[!{index}].fdParentId"
									value="" />
							<xform:text
							    property="type2_pages[!{index}].fdName"
								style="width:65%"
								subject="${lfn:message('sys-mportal:sysMportalPageCard.fdName') }"
								required="true"
								validators="required maxLength(100)"/>
								<span class="lui_oname"></span>
						</td>

						<td align="center">
							<div class="td_icon mui" onclick="__changeIcon(this)">
							</div>
							<input style="display:none;" subject="${lfn:message('sys-mportal:sysMportalCpageRelation.fdImg')}" class="td_img_validate td_img_hidden_input"  type="text" name="type2_pages[!{index}].fdImg" value="" />
							<input style="display:none;" subject="${lfn:message('sys-mportal:sysMportalCpageRelation.fdIcon')}" class="td_icon_validate td_icon_hidden_input"  type="text" name="type2_pages[!{index}].fdIcon" value="" />
						</td>

						<td align="center">
							<div class="td_type">
							</div>
							<input class="td_type_hidden_input" type="hidden" name="type2_pages[!{index}].fdType" value="" />
							<input class="td_cpageType_hidden_input" type="hidden" name="type2_pages[!{index}].fdCpageType" value="" />
						</td>

						<td align="center">
							<input type="button" class="page_btn" onclick="__delete_row(this);" value="${lfn:message('sys-mportal:sysMportal.btn.delete')}"/>
							<input type="button" class="page_btn" onclick="__move_up(this);" value="${lfn:message('sys-mportal:sysMportal.btn.moveUp')}"/>
							<input type="button" class="page_btn" onclick="__move_down(this);" value="${lfn:message('sys-mportal:sysMportal.btn.moveDown')}"/>
							<input class="page_btn td_addSubPage" type="button" onclick="selectPage(this,'addSub');" value="${lfn:message('sys-mportal:sysMportal.btn.relationPage')}"/>
						</td>
					</tr>
					<%--内容行  --%>
					<c:if test="${not empty sysMportalCompositeForm.fdNavLayout && sysMportalCompositeForm.fdNavLayout == '2'}">
						<c:set var="tempCount" value="0"></c:set>
						<c:forEach items="${sysMportalCompositeForm.pages}" var="page" varStatus="vstatus">
						<tr KMSS_IsContentRow="1" mportal_page_level="1" style="${page.fdType == '2' ? 'border-bottom-style:dotted;':''}">
							<td>
								<input  type="hidden" class="td_fdId_hidden_input" name="type2_pages[${tempCount}].fdId" value="${page.fdId }" />
								<input  type="hidden" class="td_fdOrder_hidden_input" name="type2_pages[${tempCount}].fdOrder" value="${page.fdOrder }" />
								<input  type="hidden" name="type2_pages[${tempCount}].sysMportalCpageId" value="${ page.sysMportalCpageId }" />
								<input type="hidden" name="type2_pages[${tempCount}].sysMportalCompositeId" value="${ page.sysMportalCompositeId }" />
								<xform:text
								    property="type2_pages[${tempCount}].fdName"
								    value="${page.fdName}"
									style="width:65%"
									subject="${lfn:message('sys-mportal:sysMportalPageCard.fdName')}"
									required="true"
									validators="required maxLength(100)"/>
								<span class="lui_oname">
									<c:if test="${page.fdType != '2'}">
										[<c:out value="${page.fdName}" />]
									</c:if>
								</span>
							</td>
							<td align="center">
                                <c:choose>
                                    <c:when test="${not empty page.fdIcon && fn:indexOf(page.fdIcon, 'mui')<0 }">
                                        <div class="mui" claz="${page.fdIcon}" onclick="__changeIcon(this)" >
                                                ${page.fdIcon}
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                    <c:choose>
                                        <c:when test="${not empty page.fdImg}">
                                            <div class="mui imgBox" claz="" onclick="__changeIcon(this)" style="background: url('${LUI_ContextPath}${page.fdImg}') no-repeat center;background-size: contain">
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="mui ${page.fdIcon}" claz="${page.fdIcon}" onclick="__changeIcon(this)">
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    </c:otherwise>
                                </c:choose>
								<input class="td_icon_hidden_input" type="hidden" name="type2_pages[${tempCount}].fdIcon" value="${page.fdIcon}" />
								<input class="td_img_hidden_input" type="hidden" name="type2_pages[${tempCount}].fdImg" value="${page.fdImg}" />
							</td>
							<td align="center">
								<div class="td_type">
									<c:if test="${page.fdType == '1'}">
										${lfn:message('sys-mportal:sysMportal.msg.page')}：
										<c:if test="${page.sysMportalCpageType == '1'}">
												${ lfn:message('sys-mportal:sysMportalCpage.fdType.page') }
										</c:if>
										<c:if test="${page.sysMportalCpageType == '2'}">
												${ lfn:message('sys-mportal:sysMportalCpage.fdType.url') }
										</c:if>
									</c:if>
									<c:if test="${page.fdType == '2'}">
										${lfn:message('sys-mportal:sysMportal.msg.tab')}
									</c:if>
								</div>
								<input class="td_type_hidden_input" type="hidden" name="type2_pages[${tempCount}].fdType" value="${page.fdType}" />
								<input class="td_cpageType_hidden_input" type="hidden" name="type2_pages[${tempCount}].fdCpageType" value="${page.sysMportalCpageType}" />				
							</td>
							<td width="15%" align="center">														
								<input type="button" class="page_btn" onclick="__delete_row(this);" value="${lfn:message('sys-mportal:sysMportal.btn.delete')}"/>
								<input type="button" class="page_btn" onclick="__move_up(this);" value="${lfn:message('sys-mportal:sysMportal.btn.moveUp')}"/>
								<input type="button" class="page_btn" onclick="__move_down(this);" value="${lfn:message('sys-mportal:sysMportal.btn.moveDown')}"/>
								<c:if test="${page.fdType == '2'}">
									<input class="page_btn td_addSubPage" type="button" onclick="selectPage(this,'addSub');" value="${lfn:message('sys-mportal:sysMportal.btn.relationPage')}"/>
								</c:if>								
							</td>
						</tr>
						<c:if test="${page.fdType == '2'}">
							<c:forEach items="${page.childPageRelations}" var="childPage" varStatus="child_vstatus">
								<c:set var="tempCount" value="${tempCount+1}"></c:set>
								<tr KMSS_IsContentRow="1" style="border:none;" mportal_page_level="2" mportal_page_parent_id="${childPage.fdParentId }">
									<td style="border:none;padding-left:20px;">
										<input  type="hidden" 
											class="td_fdId_hidden_input"
											name="type2_pages[${tempCount}].fdId" value="${childPage.fdId }" />
										<input  type="hidden" 
											class="td_fdOrder_hidden_input"
											name="type2_pages[${tempCount}].fdOrder" value="${childPage.fdOrder }" />
										<input type="hidden"
											name="type2_pages[${tempCount}].fdParentId" 
											value="${childPage.fdParentId }" />
										<input  type="hidden" 
														name="type2_pages[${tempCount}].sysMportalCpageId" 
														value="${ childPage.sysMportalCpageId }" />
										<input type="hidden" 
														name="type2_pages[${tempCount}].sysMportalCompositeId" 
														value="${ childPage.sysMportalCompositeId }" />
										<xform:text 
										    property="type2_pages[${tempCount}].fdName" 
										    value="${childPage.fdName}"
											style="width:65%" 
											subject="${lfn:message('sys-mportal:sysMportalPageCard.fdName')}"
											required="true" 
											validators="required maxLength(100)"/>
										<span class="lui_oname">[<c:out value="${childPage.sysMportalCpageName}" />]</span>	
									</td>
									
									<td align="center" style="border:none;">
                                        <c:choose>
                                            <c:when test="${not empty childPage.fdIcon && fn:indexOf(childPage.fdIcon, 'mui')<0 }">
                                                <div class="mui" claz="${childPage.fdIcon}" onclick="__changeIcon(this)" >
                                                        ${childPage.fdIcon}
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${not empty childPage.fdImg}">
                                                        <div class="mui imgBox" claz="" onclick="__changeIcon(this)" style="background: url('${LUI_ContextPath}${childPage.fdImg}') no-repeat center;background-size: contain">
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="mui ${childPage.fdIcon}" claz="${childPage.fdIcon}" onclick="__changeIcon(this)">
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>

										<input class="td_icon_hidden_input" type="hidden" name="type2_pages[${tempCount}].fdIcon" value="${childPage.fdIcon}" />
										<input class="td_img_hidden_input" type="hidden" name="type2_pages[${tempCount}].fdImg" value="${childPage.fdImg}" />
									</td>
								
									<td align="center" style="border:none;">
										<div class="td_type">
											<c:if test="${childPage.sysMportalCpageType == '1'}">
												${ lfn:message('sys-mportal:sysMportalCpage.fdType.page') }
											</c:if>
											<c:if test="${childPage.sysMportalCpageType == '2'}">
													${ lfn:message('sys-mportal:sysMportalCpage.fdType.url') }
											</c:if>
										</div>	
										<input class="td_type_hidden_input" type="hidden" name="type2_pages[${tempCount}].fdType" value="${childPage.fdType}" />	
										<input class="td_cpageType_hidden_input" type="hidden" name="type2_pages[${tempCount}].fdCpageType" value="${childPage.sysMportalCpageType}" />						
									</td>
									<td width="15%" align="center" style="border:none;">														
										<input type="button" class="page_btn" onclick="__delete_row(this);" value="${lfn:message('sys-mportal:sysMportal.btn.delete')}"/>
										<input type="button" class="page_btn" onclick="__move_up(this);" value="${lfn:message('sys-mportal:sysMportal.btn.moveUp')}"/>
										<input type="button" class="page_btn" onclick="__move_down(this);" value="${lfn:message('sys-mportal:sysMportal.btn.moveDown')}"/>
									</td>
								</tr>
							</c:forEach>
						</c:if>	
						<c:set var="tempCount" value="${tempCount+1}"></c:set>					
					</c:forEach>
					</c:if>					
				</table>
			</td>
		</tr>
		<!--  底部导航布局 Ends  -->
		
		<tr style="display:none;">
			<td id="__mportlet_pages_info"></td>
		</tr>
		
		<!-- 门户权限 Starts -->
		<tr id="__mportlet_readers" style="${sysMportalPageForm.fdType == '3' ? 'display:none;' :'' }">
			<td class="td_normal_title"  width="15%">
				${ lfn:message('sys-mportal:sysMportalPage.fdReaders')}
			</td>
			<td colspan="3">
				<xform:address textarea="true" 
					mulSelect="true" 
					propertyId="authReaderIds" 
					propertyName="authReaderNames" style="width:96%;height:90px;" >
				</xform:address>
				<br><span class="com_help">
					<% if (SysOrgEcoUtil.IS_ENABLED_ECO) { %>
					    <% if(SysOrgEcoUtil.isExternal()) { %>
					        <!-- （为空则本组织人员可使用） -->
					        (${ lfn:message('sys-mportal:sysMportalPage.external.fdReaders.tips')})
					    <% } else { %>
					        <!-- （为空则所有内部人员可使用） -->
					        (${ lfn:message('sys-mportal:sysMportalPage.internal.fdReaders.tips')})
					    <% } %>
					<% } else { %>
					    <!-- （为空则所有人可使用） -->
					    (${ lfn:message('sys-mportal:sysMportalPage.fdReaders.tips')})
					<% } %>				
					
				</span>
			</td>
		</tr>
		<tr id="__mportlet_editors" style="${sysMportalPageForm.fdType == '3' ? 'display:none;' :'' }">
			<td class="td_normal_title"  width="15%">
				${ lfn:message('sys-mportal:sysMportalPage.authEditors')}
			</td>
			<td colspan="3">
				<xform:address textarea="true" 
					mulSelect="true" 
					propertyId="authEditorIds" 
					propertyName="authEditorNames" style="width:96%;height:90px;" >
				</xform:address>
				<br>
				<span class="com_help">
					(${ lfn:message('sys-mportal:sysMportalPage.authEditors.tips')})
				</span>
			</td>
		</tr>
		<!-- 门户权限 Ends -->
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalPage.docCreator"/>
			</td><td width="35%">
				<xform:address propertyId="docCreatorId" propertyName="docCreatorName" 
				orgType="ORG_TYPE_PERSON" showStatus="view" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalPage.docCreateTime"/>
			</td>
			<td width="35%">
				<xform:datetime property="docCreateTime" showStatus="view" />
			</td>
		 </tr>
		 
		</table>
	</center>
	<html:hidden property="docCreatorId"/>
	<html:hidden property="docCreateTime"/>
	<html:hidden property="fdId" />
	<html:hidden property="docStatus" />
	<html:hidden property="method_GET" />
	<%@ include file="/sys/mportal/sys_mportal_composite/sysMportalComposite_edit_js.jsp"%>
	</html:form>
	</div>
	</template:replace>
</template:include>