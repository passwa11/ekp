<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.common.model.KmsMultidocLifeCycleConfig"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>
<%
	Map<String, String> configMap = KmsKnowledgeUtil.getFilterConfig();
	pageContext.setAttribute("configMap", configMap);
%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%> 
<c:set var="kmsCategoryEnabled" value="false"></c:set>
<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig3 = new KmsCategoryConfig();
		String kmsCategoryEnabled3 = (String) kmsCategoryConfig3.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled3)) {
	%>
		<c:set var="kmsCategoryEnabled" value="true"></c:set>
	<%
		}
	%>
</c:if>
<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
</list:cri-ref>

<c:if test="${kmsCategoryEnabled}">
	<list:cri-ref ref="criterion.sys.simpleCategory"
		key="kmsCategoryKnowledgeRels" multi="false" expand="false"
		title="${lfn:message('kms-category:title.kms.category')}">
		<list:varParams
			modelName="com.landray.kmss.kms.category.model.KmsCategoryMain" />
	</list:cri-ref>
</c:if>

<list:cri-property
	modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory"
	cfg-spa="true" />
<c:if test="${configMap.department != '0' }">
<list:cri-auto
		modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
		property="docDept" />
</c:if>
<c:if test="${configMap.disableDepartment == '1' }">
	<list:cri-ref key="docDept" ref="criterion.sys.dep.disable"
		title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docDeptId.disable') }">
	</list:cri-ref>
</c:if>

<c:if test="${configMap.author != '0' }">
	<list:cri-ref key="fdDocAuthorList"  ref="criterion.sys.person"  multi="false" expand="false"
	        title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }">
	</list:cri-ref>
	
	<list:cri-auto modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" property="outerAuthor"/>
</c:if>

<list:cri-criterion
	title="${lfn:message('kms-knowledge:kmsKnowledge.myknowledge') }"
	key="mydoc" expand="false" multi="false">
	<list:box-select>
		<list:item-select cfg-if="!param.mydoc">
			<ui:source type="Static">
				[{text:'${ lfn:message('list.create') }', value:'create'},
				{text:'${ lfn:message('list.approval') }',value:'approval'},
				{text:'${ lfn:message('list.approved') }', value: 'approved'},
				{text:'${ lfn:message('kms-knowledge:list.myBookmark') }', value: 'myBookmark'},
				{text:'${ lfn:message('kms-knowledge:kmsKnowledge.eva.my') }', value: 'myEval'}]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>

<list:cri-criterion
	title="${lfn:message('kms-knowledge:kmsKnowledge.fileType')}"
	key="fileType" expand="false" multi="true">
	<list:box-select>
		<list:item-select cfg-enable="false" id="fileType">
			<ui:source type="Static">
				[{text:'DOC', value:'doc'}, {text:'PPT', value: 'ppt'}, {text:'PDF',value:'pdf'},
				{text:'XLS', value: 'excel'},
				{text:'${lfn:message('kms-knowledge:kmsKnowledge.pic')}', value: 'pic'},
				{text:'${lfn:message('kms-knowledge:kmsKnowledge.sound')}', value: 'sound'}, 
				{text:'${lfn:message('kms-knowledge:kmsKnowledge.video')}', value: 'video'},
				{text:'${lfn:message('kms-knowledge:kmsKnowledge.others')}', value: 'others'}]
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>
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
				{text:'${ lfn:message('status.publish') }',value:'30'}
				<c:if test="${kms_professional}">
					,{text:'${ lfn:message('status.expire') }',value:'40'}

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
				</c:if>
				]

			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>

<c:if test="${configMap.docCreator == '1' }">
	<list:cri-auto
		modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
		property="docCreator" />
</c:if>

<c:if test="${configMap.docPublishTime != '0' }">
	<list:cri-auto
		modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
		property="docPublishTime" />
</c:if>

<c:if test="${configMap.docCreateTime == '1' }">
	<list:cri-auto
		modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
		property="docCreateTime" />
</c:if>





