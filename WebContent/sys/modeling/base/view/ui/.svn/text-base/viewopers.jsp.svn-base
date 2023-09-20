<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/modeling/modeling.tld" prefix="modeling"%>
<style>
	/*#136642 操作按钮太多时，期望前台有个滚动条*/
	.lui_toolbar_right .lui_toolbar_content{
		height: auto;
		max-height: 600px;
	}
</style>
<c:set var="isShowCirculation" value="false"></c:set>
<c:set var="hasShowCirculation" value="false"></c:set>
<c:forEach items="${operList }" var="oper">
	<modeling:mauthurl fdOprId="${oper['fdOperId']}">
		<c:if test="${oper['fdDefType'] == 13}">
			<c:set var="isShowCirculation" value="true"></c:set>
		</c:if>
		<kmss:auth requestURL="${modelingAuthUrl}" requestMethod="GET">
			<c:choose>
				<c:when test="${oper['fdDefType'] == 3}">
					<c:if test="${enableFlow == true}">
						<c:if test="${modelingAppModelMainForm.docStatus !='30'}">
							<!-- 有流程文档信息编辑 -->
							<ui:button parentId="toolbar" text="${oper['text']}" onclick="clickOperation('${oper['url']}${oper['fdOperId']}', '${oper['fdDefType']}', '${param.listviewId}', '${param.fdAppModelId}','${oper['listValid']}')" order="2"></ui:button>

						</c:if>
					</c:if>
					<c:if test="${empty enableFlow or enableFlow == false }">
						<!-- 无流程文档信息编辑 -->
						<ui:button parentId="toolbar" text="${oper['text']}" onclick="clickOperation('${oper['url']}${oper['fdOperId']}', '${oper['fdDefType']}', '${param.listviewId}', '${param.fdAppModelId}','${oper['listValid']}')" order="2"></ui:button>
					</c:if>
				</c:when>
				<c:when test="${oper['fdDefType'] == 11}">
					<c:if test="${enableFlow == true}">
						<kmss:ifModuleExist path="/km/archives/">
							<c:if test="${(modelingAppModelMainForm.docStatus=='30' or modelingAppModelMainForm.docStatus=='31') and (empty modelingAppModelMainForm.fdIsFiling or !modelingAppModelMainForm.fdIsFiling)}">
								<!-- 有流程文档信息归档 -->
								<c:import url="/sys/modeling/main/mechanism/filing/kmArchivesFileButton.jsp" charEncoding="UTF-8">
									<c:param name="fdId" value="${modelingAppModelMainForm.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain" />
									<c:param name="serviceName" value="modelingAppModelMainService" />
									<c:param name="userSetting" value="true" />
									<c:param name="cateName" value="fdModel" />
									<c:param name="moduleUrl" value="sys/modeling" />
									<c:param name="xformId" value="${xformId }" />
									<c:param name="modelId" value="${modelingAppModelMainForm.fdModelId }" />
									<c:param name="isFlow" value="true" />
									<c:param name="text" value="${oper['text']}" />
								</c:import>
							</c:if>
						</kmss:ifModuleExist>
					</c:if>
					<c:if test="${enableFlow == false}">
						<kmss:ifModuleExist path="/km/archives/">
							<!-- 无流程文档信息归档 -->
							<c:if test="${empty modelingAppSimpleMainForm.fdIsFiling or !modelingAppSimpleMainForm.fdIsFiling}">
								<c:import url="/sys/modeling/main/mechanism/filing/kmArchivesFileButton.jsp" charEncoding="UTF-8">
									<c:param name="fdId" value="${modelingAppSimpleMainForm.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain" />
									<c:param name="serviceName" value="modelingAppSimpleMainService" />
									<c:param name="userSetting" value="true" />
									<c:param name="cateName" value="fdModel" />
									<c:param name="moduleUrl" value="sys/modeling" />
									<c:param name="xformId" value="${xformId }" />
									<c:param name="modelId" value="${modelingAppSimpleMainForm.fdModelId }" />
									<c:param name="isFlow" value="false" />
									<c:param name="text" value="${oper['text']}" />
								</c:import>
							</c:if>
						</kmss:ifModuleExist>
					</c:if>
				</c:when>
				<c:when test="${oper['fdDefType'] == 12}">
					<c:if test="${enableFlow == true}">
						<c:if test="${modelingAppModelMainForm.docStatus =='30' || modelingAppModelMainForm.docStatus == '31'}">
							<!-- 有流程文档沉淀-->
							<kmss:ifModuleExist path="/kms/multidoc/">
								<kmss:auth requestURL="/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc&modelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel&fdId=${param.fdId}" requestMethod="GET">
									<c:import url="/sys/modeling/main/mechanism/sediment/subsideButton.jsp" charEncoding="UTF-8">
										<c:param name="isFlow" value="true" />
										<c:param name="modelId" value="${modelingAppModelMainForm.fdModelId }" />
										<c:param name="text" value="${oper['text']}" />
									</c:import>
								</kmss:auth>
							</kmss:ifModuleExist>
						</c:if>
					</c:if>
					<c:if test="${enableFlow == false}">
						<!-- 无流程文档沉淀-->
						<kmss:ifModuleExist path="/kms/multidoc/">
							<kmss:auth requestURL="/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc&modelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel&fdId=${param.fdId}" requestMethod="GET">
								<c:import url="/sys/modeling/main/mechanism/sediment/subsideButton.jsp" charEncoding="UTF-8">
									<c:param name="isFlow" value="false" />
									<c:param name="modelId" value="${modelingAppSimpleMainForm.fdModelId }" />
									<c:param name="text" value="${oper['text']}" />
								</c:import>
							</kmss:auth>
						</kmss:ifModuleExist>
					</c:if>
				</c:when>
				<c:when test="${oper['fdDefType'] == 13}">
					<c:if test="${enableFlow == true}">
						<!-- 传阅机制(传阅记录) -->
						<c:if test="${modelingAppModelMainForm.fdCanCircularize eq 'true' }">
							<c:set var="hasShowCirculation" value="true"></c:set>
							<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"	charEncoding="UTF-8">
								<c:param name="formName" value="modelingAppModelMainForm" />
								<c:param name="toolbarOrder" value="2" />
								<c:param name="isOper" value="true" />
								<c:param name="isNew" value="true" />
								<c:param name="isModeling" value="true" />
								<c:param name="text" value="${oper['text']}" />
							</c:import>
						</c:if>
					</c:if>
					<c:if test="${enableFlow == false}">
						<!-- 传阅机制(传阅记录) -->
						<c:if test="${modelingAppSimpleMainForm.fdCanCircularize eq 'true' }">
							<c:set var="hasShowCirculation" value="true"></c:set>
							<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"	charEncoding="UTF-8">
								<c:param name="formName" value="modelingAppSimpleMainForm" />
								<c:param name="toolbarOrder" value="2" />
								<c:param name="isOper" value="true" />
								<c:param name="isNew" value="true" />
								<c:param name="text" value="${oper['text']}" />
							</c:import>
						</c:if>
					</c:if>
				</c:when>
				<c:when test="${oper['fdDefType'] == 14}">
					<!--复制流程-->
					<kmss:auth requestURL="/sys/modeling/main/modelingAppModelMain.do?method=copyFlowDoc&fdId=${modelingAppModelMainForm.fdId}&fdAppModelId=${modelingAppModelMainForm.fdModelId}&fdAppFlowId=${modelingAppModelMainForm.fdFlowId}" requestMethod="GET">
						<ui:button parentId="toolbar" text="${oper['text']}" order="2" onclick="copyFlowDoc('${LUI_ContextPath}/sys/modeling/main/modelingAppModelMain.do?method=add&fdReviewId=${modelingAppModelMainForm.fdId}&fdAppModelId=${modelingAppModelMainForm.fdModelId}&fdAppFlowId=${modelingAppModelMainForm.fdFlowId}');">
						</ui:button>
					</kmss:auth>
				</c:when>
				<c:otherwise>
					<ui:button parentId="toolbar" text="${oper['text']}" onclick="clickOperation('${oper['url']}${oper['fdOperId']}', '${oper['fdDefType']}', '${param.listviewId}', '${param.fdAppModelId}','${oper['listValid']}','${listViewType}')" order="2"></ui:button>
				</c:otherwise>
			</c:choose>
			
			<%--导出按钮使用的提交表单--%>
			<c:if test="${oper['fdDefType'] == 7}">
				<div style="display:none">
					<form name="exportForm" action="" method="POST">
						<input type="hidden" name="fdColumns" />
						<input type="hidden" name="fdNum" class="inputsgl" />
						<input type="hidden" name="fdNumStart" class="inputsgl" />
						<input type="hidden" name="fdNumEnd" class="inputsgl" />
						<input type="hidden" name="fdKeepRtfStyle" value="true" checked="checked"/>
						<input type="hidden" name="checkIdValues" class="inputsgl" />
						<input type="hidden" name="fdExportType" class="inputsgl" />
					</form>
				</div>
			</c:if>
		</kmss:auth>
	</modeling:mauthurl>
</c:forEach>
<c:if test="${enableFlow == true}">
	<!-- 继续传阅：有流程 -->
	<c:if test="${modelingAppModelMainForm.fdCanCircularize eq 'true'  && hasShowCirculation eq 'false' && isShowCirculation eq 'true'}">
		<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"	charEncoding="UTF-8">
			<c:param name="formName" value="modelingAppModelMainForm" />
			<c:param name="toolbarOrder" value="2" />
			<c:param name="isOper" value="true" />
			<c:param name="isNew" value="true" />
		</c:import>
	</c:if>
</c:if>
<c:if test="${enableFlow == false}">
	<!-- 继续传阅:无流程 -->
	<c:if test="${modelingAppSimpleMainForm.fdCanCircularize eq 'true'  && hasShowCirculation eq 'false' && isShowCirculation eq 'true'}">
		<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"	charEncoding="UTF-8">
			<c:param name="formName" value="modelingAppSimpleMainForm" />
			<c:param name="toolbarOrder" value="2" />
			<c:param name="isOper" value="true" />
			<c:param name="isNew" value="true" />
		</c:import>
	</c:if>
</c:if>
<script>
	Com_IncludeFile("viewoper.js",Com_Parameter.ContextPath+'sys/modeling/base/resources/js/','js',true);
</script>