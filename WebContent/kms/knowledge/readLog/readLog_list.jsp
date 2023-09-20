<%@ page import="com.landray.kmss.kms.category.model.KmsCategoryConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>
<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
		String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled)) {
	%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>
	<%
		}
	%>
</c:if>

<c:set var="isHasWiki" value="false"></c:set>
<kmss:ifModuleExist path="/kms/wiki/">
	<c:set var="isHasWiki" value="true"></c:set>
</kmss:ifModuleExist>

<c:set var="pathTitle" value="${lfn:message('kms-knowledge:module.kms.knowledge') }"></c:set>

<template:include file="/sys/profile/resource/template/list.jsp" spa="true" >
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_ui/style/index_new.css" />
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/knowledge/resource/style/common.css" />
		<style>
		.lui_listview_columntable_table tbody tr td:first-child {
		    padding-left: 10px;
		}		
		
		.lui_toolbar_sort_group .lui_widget_btn {
			margin-top: -2px !important;
		}

		#listviewHistory {
			position: relative;
			top: -16px;
		}

		.lui_listview_columntable_table tbody tr td:first-child {
			padding-left: 10px !important;
		}

		.criterion-expand-top {
			float: left !important;
		}

		.lui_tabpanel_navs_item_title {
			color: #333333 !important;
		}
		</style>
	</template:replace>
	<template:replace name="content">
		<ui:panel id="kms_index_panel" >
		
		<ui:content title="${ lfn:message('kms-knowledge:list.readlog')}" id="kms_readlog_panel_content">
		<list:criteria id="criteria1">
			
			<list:cri-criterion
				title="${lfn:message('sys-readlog:sysReadLog.fdReadType') }" key="fdReadType" multi="false" >
				<list:box-select>
					<list:item-select cfg-required="true" cfg-defaultValue ="publish">
						<ui:source type="Static" >
							[
								{text:'${ lfn:message('sys-readlog:sysReadLog.fdReadType.publish') }', value:'publish'},
								{text:'${ lfn:message('sys-readlog:sysReadLog.fdReadType.process') }', value:'process'}
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion
				title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType') }"
				key="fdModelName" multi="false" >
				<list:box-select>
					<list:item-select>
						<ui:source type="Static" >
							[
								{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsMultidocKnowledge') }', value:'com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge'}
								<c:if test="${isHasWiki}">
									,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsWikiMain') }',value:'com.landray.kmss.kms.wiki.model.KmsWikiMain'}
								</c:if>
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			
			
			<list:cri-auto
				modelName="com.landray.kmss.sys.readlog.model.SysReadLog"
				property="fdReadTime" />
		</list:criteria>
		
       	<div class="lui_list_operation">
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort">
						<list:sortgroup>
							<list:sort
								property="fdReadTime" value="down"
								text="${ lfn:message('kms-knowledge:kmsKnowledge.index.text.read.time')}"
								group="sort.list" />
						</list:sortgroup>
					</ui:toolbar>
				</div>

			</div>
       		<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>
			<div class="lui_list_operation_toolbar">
				<div class="toolbar_label_btn">
					<span>
						<input type="checkbox" name="introduce">
						${lfn:message('kms-knowledge:kmsKnowledge.portlet.essential') }
					</span>
				</div>
				<ui:toolbar count="3" id="Btntoolbar">
					<ui:togglegroup order="0">
					<ui:toggle icon="lui_icon_s_zaiyao"
							title="${ lfn:message('list.rowTable') }" selected="true"
							group="tg_1" value="rowtable"
							text="${ lfn:message('list.rowTable') }"
							onclick="LUI('listviewHistory').switchType(this.value);">
						</ui:toggle>
						<ui:toggle icon="lui_icon_s_liebiao"
							title="${ lfn:message('list.columnTable') }" group="tg_1"
							value="columntable" text="${ lfn:message('list.columnTable') }"
							onclick="LUI('listviewHistory').switchType(this.value);">
						</ui:toggle>
					</ui:togglegroup>
				</ui:toolbar>
			</div>
		</div>
        
        <div class="knowledge_common">
        <list:listview id="listviewHistory" >
                <ui:source type="AjaxJson">
                	{url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=readLogdata'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable
					isDefault="false"
				   	name="columntable"
				   	rowHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
                    <list:col-serial headerStyle="padding-left: 10px;"/>
                    
                    <list:col-html  title="${ lfn:message('kms-category:kmsCategoryKnowledgeRel.docSubject') }" style="width:20%">
						{$	 
							<a target="_blank" title="{%row['docSubject']%}" href="${LUI_ContextPath}{%row['linkStr']%}">
								{%	strutil.textEllipsis(row['docSubject'],25) %}
							</a>
	 					$}
					</list:col-html>
					
					<list:col-html title="${ lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docAuthor')}" >
						{$
							<div class="lui_multi_author_wrap"><span>{%row['docAuthor.fdName']%}</span> </div>
						$}
					</list:col-html>
					<c:if test="${kmsCategoryEnabled == true}">
					<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory') }" 
				   		style="width:15%" >
				   		{$
							<span title="{%row['docTemplate']%}">
								{% strutil.textEllipsis(row['docTemplate'], 20) %}
							</span>
						$}	
				 </list:col-html>
					</c:if>
					<c:if test="${kmsCategoryEnabled == false}">
						<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory.categoryTrue') }"
									   style="width:15%" >
							{$
							<span title="{%row['docTemplate']%}">
								{% strutil.textEllipsis(row['docTemplate'], 20) %}
							</span>
							$}
						</list:col-html>
					</c:if>
                    <list:col-auto props="fdSourceType;docReadTime;fdTotalCount;docIntrCount;docEvalCount;docScore;docPublishTime;" />
                </list:colTable>
                
				<%-- 摘要视图--%>
				<list:rowTable
					layout="sys.ui.listview.rowtable"
					name="rowtable"
					rowHref="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=!{fdId}&fdKnowledgeType=!{fdKnowledgeType}">
					<list:row-template ref="kms.knowledge.listview.rowtable">
							{
								showCheckbox: false,
								showOtherProps:"fdTotalCount;docIntrCount;docEvalCount;docScore;"
							}
						</list:row-template>
					</list:rowTable>
          </list:listview>
          <!-- 翻页 -->
        <list:paging></list:paging>
        </div>
        </ui:content>
		</ui:panel>
	</template:replace>
</template:include>
<script>
	 seajs.use(['lui/spa/Spa','lui/topic'], function(Spa, topic) {
		var isIntroInit = false;
		// 是否精华
		$('input[name="introduce"]').on('click', function(evt) {
		
			var value = '';
		
			if (this.checked) {
				value = '1';
				isIntroInit = true;
			}else{
				isIntroInit = false;
			}
			
			Spa.spa.setValue('introduce', value);
		});
		
		topic.subscribe('spa.change.values', function(evt) {
			if (isIntroInit){
				var isDocIsIntroduced= Spa.spa.getValue('introduce');
				if(isDocIsIntroduced=='1')
					return;
				else
					Spa.spa.setValue('introduce', '1');
			}
		
			if (evt.value && evt.value.docIsIntroduced == '1') {
				$('input[name="introduce"]').attr('checked', true);
				isIntroInit = true;
			}
		});
	})

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

