<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">

	<template:replace name="content">
		<div style="margin: 5px 10px;">
			<!-- 筛选 -->
			<list:criteria id="archivesCriteria">
				<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"></list:cri-ref>
				<list:cri-criterion expand="false"
					title="${lfn:message('km-archives:lbpm.my')}" key="mydoc"
					multi="false">
					<list:box-select>
						<list:item-select>
							<ui:source type="Static">
                                [{text:'${ lfn:message('km-archives:lbpm.create.my') }', value:'create'},{text:'${ lfn:message('km-archives:lbpm.approval.my') }',value:'approval'}, {text:'${ lfn:message('km-archives:lbpm.approved.my') }', value: 'approved'}]
                            </ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
				<%--  <list:cri-ref ref="criterion.sys.simpleCategory" key="docTemplate" multi="false" title="${lfn:message('km-archives:kmArchivesMain.docTemplate')}" expand="false">
                    <list:varParams modelName="com.landray.kmss.km.archives.model.KmArchivesCategory" />
                </list:cri-ref> --%>
				<list:cri-auto
					modelName="com.landray.kmss.km.archives.model.KmArchivesMain"
					property="docStatus" />
				<list:cri-auto
					modelName="com.landray.kmss.km.archives.model.KmArchivesMain"
					property="docNumber" />
				<list:cri-auto
					modelName="com.landray.kmss.km.archives.model.KmArchivesMain"
					property="docCreator" />
				<list:cri-auto
					modelName="com.landray.kmss.km.archives.model.KmArchivesMain"
					property="fdFileDate" />

			</list:criteria>
			<!-- 操作 -->
			<div class="lui_list_operation">

				<!-- 全选 -->
				<div class="lui_list_operation_order_btn">
					<list:selectall></list:selectall>
				</div>
				<!-- 分割线 -->
				<div class="lui_list_operation_line"></div>
				<!-- 排序 -->
				<div class="lui_list_operation_sort_btn">
					<div class="lui_list_operation_order_text">
						${ lfn:message('list.orderType') }：
					</div>
					<div class="lui_list_operation_sort_toolbar">
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							<list:sortgroup>
								<list:sort property="fdFileDate"
									text="${lfn:message('km-archives:kmArchivesMain.fdFileDate')}"
									group="sort.list" value="down" />
								<list:sort property="fdLibrary"
									text="${lfn:message('km-archives:kmArchivesMain.fdLibrary')}"
									group="sort.list"/>
							</list:sortgroup>
						</ui:toolbar>
					</div>
				</div>
				<!-- 分页 -->
				<div class="lui_list_operation_page_top">
					<list:paging layout="sys.ui.paging.top" />
				</div>
				<div style="float: right">
					<div style="display: inline-block; vertical-align: middle;">
						<ui:toolbar count="4">
							<kmss:authShow roles="ROLE_KMARCHIVES_CREATE">
								<c:if test="${empty param.categoryId}">
									<ui:button text="${lfn:message('button.add')}"
										onclick="addDoc3()">
									</ui:button>
								</c:if>
								<c:if test="${not empty param.categoryId}">
									<c:set var="flg" value="no" />
									<kmss:auth
										requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=add&fdTemplateId=${param.categoryId}"
										requestMethod="GET">
										<ui:button text="${lfn:message('button.add')}"
											onclick="addDoc1()">
										</ui:button>
										<c:set var="flg" value="yes" />
									</kmss:auth>
									<c:if test="${flg eq 'no'}">
										<ui:button text="${lfn:message('button.add')}"
											onclick="addDoc2()">
										</ui:button>
									</c:if>
								</c:if>
							</kmss:authShow>
							<kmss:auth
								requestURL="/km/archives/km_archives_main/kmArchivesMain.do?method=deleteall">
								<c:set var="canDelete" value="true" />
							</kmss:auth>
							<!---->
							<ui:button text="${lfn:message('button.deleteall')}"
								onclick="deleteAll()" order="4" id="btnDelete" />
							<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
							</c:import>
							<c:import url="/km/archives/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
							</c:import>
							<c:import url="/km/archives/import/doc_cate_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.archives.model.KmArchivesMain" />
								<c:param name="docFkName" value="docTemplate" />
								<c:param name="cateModelName" value="com.landray.kmss.km.archives.model.KmArchivesCategory" />
							</c:import>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation" />
			<!-- 列表 -->
			<list:listview id="listview">
				<ui:source type="AjaxJson">
                    {url:appendQueryParameter('/km/archives/km_archives_main/kmArchivesMain.do?method=manageList&categoryId=${JsParam.categoryId}')}
                </ui:source>
				<!-- 列表视图 -->
				<list:colTable isDefault="false"
					rowHref="/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=!{fdId}"
					name="columntable">
					<list:col-checkbox />
					<list:col-serial />
					<list:col-auto
						props="docSubject;docNumber;docCreator.fdName;fdFileDate;fdLibrary;lbpm_main_listcolumn_node;lbpm_main_listcolumn_handler" />
				</list:colTable>
			</list:listview>
			<!-- 翻页 -->
			<list:paging />
		</div>
		<script>
			var listOption = {
				contextPath : '${LUI_ContextPath}',
				modelName : 'com.landray.kmss.km.archives.model.KmArchivesMain',
				templateName : 'com.landray.kmss.km.archives.model.KmArchivesCategory',
				basePath : '/km/archives/km_archives_main/kmArchivesMain.do',
				canDelete : '${canDelete}',
				mode : 'main_scategory',
				customOpts : {

					____fork__ : 0
				},
				lang : {
					noSelect : '${lfn:message("page.noSelect")}',
					comfirmDelete : '${lfn:message("page.comfirmDelete")}'
				}

			};
			Com_IncludeFile("list.js",
					"${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
			seajs
					.use(
							[ 'lui/jquery', 'lui/dialog', 'lui/topic' ],
							function($, dialog, topic) {
								var cateId;
								window.importArchives = function() {
									/* dialog.simpleCategoryForNewFile(listOption.templateName,'/km/archives/km_archives_main/kmArchivesMain_upload.jsp?docTemplate=!{id}',
											false,null,null,getValueByHash("docTemplate")); */
									if (cateId == null) {
										/* dialog.alert("${lfn:message('km-archives:please.choose.category')}"); */
									} else {
										Com_OpenWindow("${LUI_ContextPath}/km/archives/km_archives_main/kmArchivesMain_upload.jsp?docTemplate="
												+ cateId);
									}
								};
								window.batchUpdate = function() {
									var selected = [];
									$("input[name='List_Selected']:checked")
											.each(function() {
												selected.push($(this).val());
											});
									if (selected.length == 0) {
										dialog
												.alert('${lfn:message("page.noSelect")}');
										return;
									}
									var url = "/km/archives/km_archives_main/kmArchivesMain_batchUpdate.jsp?selectedIds="
											+ selected.join(";");
									dialog
											.iframe(
													url,
													"${lfn:message('km-archives:kmArchivesMain.batchUpdate')}",
													function(value) {
														topic
																.publish('list.refresh');
													}, {
														"width" : 500,
														"height" : 300
													});
								};
								topic
										.subscribe(
												'criteria.changed',
												function(evt) {
													cateId = null;
													for (var i = 0; i < evt['criterions'].length; i++) {
														//获取分类id和类型
														if (evt['criterions'][i].key == "docTemplate") {
															cateId = evt['criterions'][i].value[0];
														}
													}
												});
							});
		</script>
		<script>
			window.addDoc1 = function() {
				Com_OpenWindow('<c:url value="/km/archives/km_archives_main/kmArchivesMain.do" />?method=add&i.docTemplate=${JsParam.categoryId}');
			};
			window.addDoc2 = function() {
				Dialog_SimpleCategoryForNewFile(
						'com.landray.kmss.km.archives.model.KmArchivesCategory',
						'<c:url value="/km/archives/km_archives_main/kmArchivesMain.do" />?method=add&i.docTemplate=!{id}&fdTemplateName=!{name}');
			};
			window.addDoc3 = function() {
				dDialog_SimpleCategoryForNewFile(
						'com.landray.kmss.km.archives.model.KmArchivesCategory',
						'<c:url value="/km/archives/km_archives_main/kmArchivesMain.do" />?method=add&i.docTemplate=!{id}&fdTemplateName=!{name}');
			};
		</script>

	</template:replace>
</template:include>