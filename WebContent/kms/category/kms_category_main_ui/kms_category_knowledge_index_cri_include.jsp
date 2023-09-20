<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.common.model.KmsMultidocLifeCycleConfig"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>


<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
</list:cri-ref>

<!-- 我的知识 -->
<list:cri-criterion
	title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType') }"
	key="template" expand="false" multi="false">
	<list:box-select >
		<list:item-select cfg-required="true" cfg-if="param.mydoc&&(!param.docCategory)" >
			<ui:source type="Static" >
				[
					{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsMultidocKnowledge') }', value:'1'}
					<c:if test="${isHasWiki}">
						,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsWikiMain') }',value:'2'}
					</c:if>
					<c:if test="${isHasKem}">
						,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsKemMain') }',value:'3'}
					</c:if>
				]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>
<list:cri-criterion
	title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType') }"
	key="template" expand="false" multi="false">
	<list:box-select >
		<list:item-select cfg-required="true" 
					cfg-if="(param.docCategory||criteria('template')[0]=='1'||criteria('template')[0]=='2'||criteria('template')[0]=='3')&&(!param.mydoc)&&(!param.myBookmark)&&(!param.myDraft)&&(!param.type)" 
					cfg-defaultValue ="1">
			<ui:source type="Static" >
				[
					{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsMultidocKnowledge') }', value:'1'}
					<c:if test="${isHasWiki}">
						,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsWikiMain') }',value:'2'}
					</c:if>
					<c:if test="${isHasKem}">
						,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsKemMain') }',value:'3'}
					</c:if>
				]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>


<!-- 我的草稿 -->
<list:cri-criterion
	title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType') }"
	key="template" expand="false" multi="false">
	<list:box-select >
		<list:item-select cfg-required="true" cfg-if="param.myDraft&&(!param.docCategory)" >
			<ui:source type="Static" >
				[
					{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsMultidocKnowledge') }', value:'1'}
					<c:if test="${isHasWiki}">
						,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsWikiMain') }',value:'2'}
					</c:if>
					<c:if test="${isHasKem}">
						,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsKemMain') }',value:'3'}
					</c:if>
				]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>

<!-- 收藏知识 -->
<list:cri-criterion
	title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType') }"
	key="template" expand="false" multi="false">
	<list:box-select>
		<list:item-select cfg-if="param.myBookmark&&(!param.docCategory)"   cfg-required="true">
			<ui:source type="Static" >
				[
					{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsMultidocKnowledge') }', value:'1'}
					<c:if test="${isHasWiki}">
						,{text:'${ lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType.KmsWikiMain') }',value:'2'}
					</c:if>
				]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>

<list:cri-ref cfg-if="criteria('template')[0]=='3'||(param.type&&((param.type).indexOf('kem'))>-1)" ref="criterion.sys.simpleCategory" 
	key="kemMainCategory" multi="false" expand="false" 
	title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.docTemplate') } ">
	<list:varParams
		modelName="com.landray.kmss.kms.kem.model.KmsKemCategory"/>
</list:cri-ref>

<list:cri-ref ref="criterion.sys.simpleCategory" cfg-if="criteria('template')[0]=='1'||criteria('template')[0]=='2'||(param.type&&((param.type).indexOf('wiki'))>-1)||(param.type&&((param.type).indexOf('multidoc'))>-1)"
	key="mainCategory" multi="false" expand="false"
	title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.docTemplate') }">
	<list:varParams
		modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
</list:cri-ref>

<list:cri-ref cfg-if="criteria('template')[0]=='1'||criteria('template')[0]=='2'||(param.type&&((param.type).indexOf('multidoc'))>-1)||(param.type&&((param.type).indexOf('wiki'))>-1)" key="fdDocAuthorList"  ref="criterion.sys.person"  multi="false" expand="false" 
       title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }">
</list:cri-ref>


<list:cri-criterion
	title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docStatus') }"
	key="docStatus" expand="false">
	<list:box-select>
		<list:item-select cfg-if="!param.docStatus">
			<ui:source type="Static">
				[{text:'${ lfn:message('status.discard') }', value:'00'},
				{text:'${ lfn:message('status.draft') }',value:'10'},
				{text:'${ lfn:message('status.refuse') }',value:'11'},
				{text:'${ lfn:message('status.examine') }',value:'20'},
				{text:'${ lfn:message('status.publish') }',value:'30'},
				{text:'${ lfn:message('status.expire') }',value:'40'}
				<%
				// 获取文档生命周期是否启动
								KmsMultidocLifeCycleConfig cycleConfig = new KmsMultidocLifeCycleConfig();
								String docLifeCycleShowFlag = (String) cycleConfig.getDataMap()
										.get("showMultidocLifeCycleFlag");
								if ("true".equals(docLifeCycleShowFlag)) {
			%>
				,{text:'${ lfn:message('kms-common:kmsDocStatus.waitpublish') }',value:'25'}
				<%
				}
			%>
				]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>

<%-- <list:cri-auto
		modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
		property="docCreator" /> --%>

<list:cri-auto
		modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
		property="docPublishTime" />

<list:cri-criterion
	title="${lfn:message('kms-knowledge:kmsKnowledge.myknowledge') }"
	key="mydoc" expand="false" multi="false">
	<list:box-select>
		<list:item-select >
			<ui:source type="Static">
				[{text:'${ lfn:message('list.create') }', value:'create'},
				{text:'${ lfn:message('list.approval') }',value:'approval'}, 
				{text:'${ lfn:message('list.approved') }', value: 'approved'}]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>




