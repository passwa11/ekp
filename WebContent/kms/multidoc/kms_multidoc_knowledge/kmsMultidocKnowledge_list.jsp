<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<%
	KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
	String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabled)) {
%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>	
<%
	}
%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器  -->
        <list:criteria id="criteria">
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
			<list:cri-property modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" categoryId="${categoryId }"/>
			<list:cri-auto modelName="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" 
				property="docDept"/>
			
			<list:cri-ref key="fdDocAuthorList"  ref="criterion.sys.person"  multi="false" expand="false"
			      title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }">
			</list:cri-ref>
			
			<list:cri-criterion title="${lfn:message('kms-multidoc:kmsMultidoc.fileType')}" key="fileType" expand="false" multi="true"> 
				<list:box-select >
					<list:item-select cfg-enable="false" id="fileType">
						<ui:source type="Static">
							[{text:'DOC', value:'doc'}, {text:'PPT', value: 'ppt'}, {text:'PDF',value:'pdf'},{text:'XLS', value: 'excel'},
							{text:'${lfn:message('kms-multidoc:kmsMultidoc.pic')}', value: 'pic'},{text:'${lfn:message('kms-multidoc:kmsMultidoc.sound')}', value: 'sound'}, 
							{text:'${lfn:message('kms-multidoc:kmsMultidoc.video')}', value: 'video'},
							{text:'${lfn:message('kms-multidoc:kmsMultidoc.others')}', value: 'others'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docStatus') }" key="docStatus" expand="false">
				<list:box-select >
					<list:item-select >
						<ui:source type="Static">
							[{text:'${ lfn:message('status.discard') }', value:'00'},
							{text:'${ lfn:message('status.draft') }',value:'10'},
							{text:'${ lfn:message('status.refuse') }',value:'11'},
							{text:'${ lfn:message('status.examine') }',value:'20'},
							{text:'${ lfn:message('status.publish') }',value:'30'}
							<c:if test="${kms_professional}">
							,{text:'${ lfn:message('status.expire') }',value:'40'},
							{text:'${ lfn:message('kms-common:kmsDocStatus.waitpublish') }',value:'25'}
							</c:if>
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" 
				property="docPublishTime;docIsIntroduced"/>
		</list:criteria>
        <!-- 操作栏 -->
		<div class="lui_list_operation">
		
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" >
						<list:sort property="docPublishTime" text="${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docPublishTime') }"></list:sort>
                        <list:sort property="docSubject" text="${lfn:message('kms-knowledge:kmsKnowledge.docSubject.small') }"></list:sort>
                    </ui:toolbar>
				</div>				
				
			</div>
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div  class="lui_table_toolbar_inner">
                    <ui:toolbar count="4">
                      <%-- 修改权限 --%>
					  <c:import url="/sys/right/import/cchange_doc_right_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" />
								<c:param name="categoryId" value="${param.fdTemplateId }" />
					  </c:import>							
					 <%-- 分类转移 --%>
					 <c:import url="/sys/simplecategory/import/doc_cate_change_button.jsp" charEncoding="UTF-8">
								<c:param name="categoryId" value="${param.fdTemplateId }"/>
								<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
								<c:param name="docFkName" value="docCategory" />
								<c:param name="cateModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
								<c:param name="extProps" value="fdTemplateType:1;fdTemplateType:3" />
					 </c:import>
                     <!-- 新建 -->				
                     <kmss:auth requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add&fdTemplateId=${param.fdTemplateId}" requestMethod="GET">
					   		<ui:button text="${lfn:message('button.add')}" onclick="add()"></ui:button>
                     </kmss:auth>
                      <!-- 删除 -->				 
                      <kmss:auth requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=deleteall&status=${param.status}&categoryId=${param.fdTemplateId}&nodeType=${param.nodeType}" requestMethod="GET">
					   		<ui:button text="${lfn:message('button.delete')}" onclick="del()"></ui:button>
					  </kmss:auth>
                    </ui:toolbar> 
                </div>
			</div>
		</div>
        <ui:fixed elem=".lui_list_operation"></ui:fixed>
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                    {url:'/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=${JsParam.methodName}&q.fdDelFlag=0&fdTemplateId=${JsParam.fdTemplateId}&orderby=${JsParam.orderby}&ordertype=${JsParam.ordertype}&status=${JsParam.status}&forwordPage=data'}
            </ui:source>
            
            <%-- 列表视图--%>
			<list:colTable layout="sys.ui.listview.columntable"
				name="columntable"
				rowHref="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId=!{fdId}">
				<%@ include
					file="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_col_tmpl.jsp"%>
			</list:colTable>
            
        </list:listview> 
        <list:paging></list:paging>
        
        <c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
		<c:if test="${frameShowTop=='yes' }">
		<ui:top id="top"></ui:top>
			<kmss:ifModuleExist path="/sys/help">
				<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
			</kmss:ifModuleExist>
		</c:if>
        
	</template:replace>
</template:include>
<c:import
	url="/resource/jsp/search_bar.jsp"
	charEncoding="UTF-8">
	<c:param
		name="fdModelName"
		value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
</c:import>
<script type="text/javascript">
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	// 监听新建更新等成功后刷新
	topic.subscribe('successReloadPage', function() {
		topic.publish("list.refresh");
	});
 	// 增加
	window.add = function() {
		Com_OpenWindow('<c:url value="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do" />?method=add&fdTemplateId=${JsParam.fdTemplateId}');
	};
	// 删除
	window.del = function(id) {
		
		var comfirmMsg = Com_Parameter.ComfirmDelete;
		// 判断是否开启软删除
		if (Com_Parameter.SoftDeleteEnableModules.length > 0) {
			if(Com_Parameter.SoftDeleteEnableModules.indexOf("com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge") > -1) {
				comfirmMsg = Com_Parameter.ComfirmSoftDelete;
			}
		}
		
		var values = [];
		if(id) {
			values.push(id);
		} else {
			$("input[name='List_Selected']:checked").each(function() {
				values.push($(this).val());
			});
		}
		if(values.length==0){
			dialog.alert('<bean:message key="page.noSelect"/>');
			return;
		}
		var url  = '<c:url value="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=deleteall&categoryId=${JsParam.fdTemplateId}&fdModelName=${JsParam.fdModelName}&successForward=lui-source&failureForward=lui-failure"/>';
		dialog.confirm(comfirmMsg, function(value) {
			if(value == true) {
				window.loading = dialog.loading();
				$.ajax({
					url : url,
					type : 'POST',
					data : $.param({"List_Selected" : values}, true),
					dataType : 'json',
					error : function(error) {//删除失败
						loading.hide();	
						dialog.failure(
								"${lfn:message('kms-knowledge:kmsKnowledge.delete.fail')}",
									'#listview');
					},
					success: function(data) {
						
						if (data.flag) {
							loading.hide();
							if(data.errorMessage) {//新版本锁定
								dialog.failure(
										data.errorMessage ,'#listview');
							}
							else {//删除成功
								dialog.success("${lfn:message('kms-knowledge:kmsKnowledge.delete.success')}",
										'#listview');
								topic.publish('list.refresh');
							}
						} else {
							loading.hide();	
						}
					}
			   });
			}
		});
	};
});
</script>
