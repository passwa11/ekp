<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="isHasWiki" value="false"></c:set>
<kmss:ifModuleExist path="/kms/wiki/">
	<c:set var="isHasWiki" value="true"></c:set>
</kmss:ifModuleExist>
<c:set var="isHasKem" value="false"></c:set>
<c:if test="${empty fdSourceType}"></c:if>
<c:set var="fdSourceType" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:set>
<kmss:ifModuleExist path="/kms/kem/">
	<c:set var="isHasKem" value="true"></c:set>
</kmss:ifModuleExist>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<list:criteria  expand="false" id="criteria1">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
			
			<list:cri-criterion
				title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType') }"
				key="fdSourceType" expand="false" multi="false" >
				<list:box-select>
					<list:item-select cfg-required="true" cfg-defaultValue ="${fdSourceType}">
						<ui:source type="Static" >
							[
								{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsMultidocKnowledge') }', value:'com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge'}
								<c:if test="${isHasWiki}">
									,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsWikiMain') }',value:'com.landray.kmss.kms.wiki.model.KmsWikiMain'}
								</c:if>
								<c:if test="${isHasKem}">
									,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsKemMain') }',value:'com.landray.kmss.kms.kem.model.KmsKemMain'}
								</c:if>
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			
			<list:cri-criterion title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.docStatus') }"
				key="docStatus" expand="false">
				<list:box-select>
					<list:item-select cfg-if="!param.docStatus" type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="true">
						<ui:source type="Static">
							[{text:'${ lfn:message('status.discard') }', value:'00'},
							{text:'${ lfn:message('status.draft') }',value:'10'},
							{text:'${ lfn:message('status.refuse') }',value:'11'},
							{text:'${ lfn:message('status.examine') }',value:'20'},
							{text:'${ lfn:message('status.publish') }',value:'30'},
							{text:'${ lfn:message('status.expire') }',value:'40'}
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			
			<list:cri-auto
				modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
				property="docCreator" />
			
			<list:cri-auto
				modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
				property="docCreateTime" />
		</list:criteria>
		
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
        <div class="lui_list_operation">
			<div class="lui_list_operation_order_text">
				${lfn:message('kms-category:kmsCategoryKnowledgeRel.list.orderType')}：</div>
			<%--排序按钮  --%>
			<div class="lui_list_operation_order_btn">
				<ui:toolbar layout="sys.ui.toolbar.sort">
					<list:sortgroup>
						<list:sort property="docCreateTime"
							text="${lfn:message('kms-category:kmsCategoryKnowledgeRel.docCreateTime') }"
							group="sort.list" />
					</list:sortgroup>
				</ui:toolbar>
			</div>

			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>
		</div>
        
        
        <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/kms/category/kms_category_knowledge_rel/kmsCategoryKnowledgeRel.do?method=data&categoryId=${JsParam.categoryId}')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false"  name="columntable">
                    <list:col-serial/>
                    
                    <list:col-html  title="${ lfn:message('kms-category:kmsCategoryKnowledgeRel.docSubject') }">
						{$	 
							<a target="_blank" href="${LUI_ContextPath}{%row['linkStr']%}">
								{%row['docSubject']%}
							</a>
	 					$}
					</list:col-html>
                    <list:col-auto props="fdCategoryNames;docTemplate;fdSourceType;docCreateName;docCreateTime;docStatus" />
                </list:colTable>
          </list:listview>
          <!-- 翻页 -->
        <list:paging></list:paging>
	</template:replace>
</template:include>
<script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.kms.category.model.KmsCategoryKnowledgeRel',
                templateName: '',
                basePath: '/kms/category/kms_category_knowledge_rel/kmsCategoryKnowledgeRel.do',
                canDelete: '${canDelete}',
                mode: '',
                customOpts: {
                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/kms/category/resource/js/", 'js', true);
           </script>

