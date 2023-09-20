<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></lbpm:lbpmApproveModel>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<%@ page import="com.landray.kmss.sys.appconfig.model.BaseAppconfigCache" %>
<%@ page import="java.util.Map" %>
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
<%

	Map<String, String> data =  BaseAppconfigCache.getCacheData("com.landray.kmss.third.hiez.config.ThirdHiezConfig");
	boolean hiezEnable = false;
	boolean hiezAutoCate = false;
	boolean hiezAutoTag = false;
	if(null != data){
		hiezEnable =  null != data.get("enable") && "true".equals(data.get("enable"))? true:false;
		hiezAutoCate =  null != data.get("cateEnabel") && "true".equals(data.get("cateEnabel"))? true:false;
		hiezAutoTag =  null != data.get("tagEnabel") && "true".equals(data.get("tagEnabel"))? true:false;

	}
	request.setAttribute("hiezEnable", hiezEnable);
	request.setAttribute("hiezAutoCate", hiezAutoCate);
	request.setAttribute("hiezAutoTag", hiezAutoTag);
%>
<c:set var="isHasIstorage" value="false"></c:set>
<kmss:ifModuleExist path="/kms/istorage/">
	<c:set var="isHasIstorage" value="true"></c:set>
</kmss:ifModuleExist>

<c:set var="isHasHiez" value="false"></c:set>
<kmss:ifModuleExist path="/third/hiez/">
	<c:set var="isHasHiez" value="true"></c:set>
</kmss:ifModuleExist>
<c:choose>
	<%-- 流程引擎默认设置是否开启右侧审批模式 --%>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<%-- 流程右侧审批三级页面模板 --%>
<%-- 		<c:set var="ref" value="kms.knowledge.content.left"></c:set>--%>
		<c:set var="ref" value="lbpm.right"></c:set>
		<c:set var="showQrcode" value="true"></c:set>
		<c:set var="showContentType" value="right"></c:set>
		<c:set var="isEdit" value="true"></c:set>
		<c:set var="formName" value="kmsMultidocKnowledgeForm"></c:set>
		<c:set var="formUrl" value="${KMSS_Parameter_ContextPath}kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="pathFixed" value="yes"></c:set>
		<!-- 左侧审批模板-->
		<c:set var="ref" value="kms.knowledge.content.left"></c:set>
		<!-- 知识仓库优化后默认使用原右侧审批的内容 -->
		<!-- 条件 lbpmApproveModel ne 'right' 的内容已废弃 -->
		<c:set var="showContentType" value="right"></c:set>
		<c:set var="leftWidth" value="270"></c:set>
		<c:set var="leftBar" value="yes"></c:set>
		<c:set var="showQrcode" value="true"></c:set>
		<c:set var="isEdit" value="true"></c:set>
		<c:set var="formName" value="kmsMultidocKnowledgeForm"></c:set>
		<c:set var="formUrl" value="${KMSS_Parameter_ContextPath}kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do"></c:set>
	</c:otherwise>
</c:choose>
<template:include
	pathFixed='${pathFixed }' 
	ref="${ref }"
	leftWidth="${leftWidth }"
	leftBar="${leftBar }"
	showQrcode="${showQrcode }"
	isEdit="${isEdit }"
	formName="${formName }"
	formUrl="${formUrl }" >
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/multidoc/kms_multidoc_ui/style/edit.css" />
	</template:replace>
	<template:replace name="title">
		<c:choose> 
			<c:when test="${ kmsMultidocKnowledgeForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('kms-multidoc:kmsMultidoc.create.title') } - ${ lfn:message('kms-multidoc:table.kmdoc') }"></c:out>
			</c:when>
			<c:otherwise>
				<c:out value="${kmsMultidocKnowledgeForm.docSubject} - ${ lfn:message('kms-multidoc:table.kmdoc') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<c:choose>
				<c:when test="${ kmsMultidocKnowledgeForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('kms-multidoc:kmsMultidoc.button.savedraft') }" order="2" onclick="commitMethod('save','true');">
					</ui:button>
					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('save','false');">
					</ui:button>
				</c:when>
				<c:when test="${ kmsMultidocKnowledgeForm.method_GET == 'edit' }">
					<c:if test="${kmsMultidocKnowledgeForm.docStatusFirstDigit=='1'}">
						<ui:button text="${lfn:message('kms-multidoc:kmsMultidoc.button.savedraft') }" order="2" onclick="commitMethod('update','true');">
						</ui:button>
					</c:if>
					<c:if test="${kmsMultidocKnowledgeForm.docStatusFirstDigit<'3'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update','false');">
						</ui:button>
					</c:if>
					<c:if test="${kmsMultidocKnowledgeForm.docStatusFirstDigit>='3'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="Com_Submit(document.kmsMultidocKnowledgeForm, 'update');">
						</ui:button>
					</c:if>			
				</c:when>
			</c:choose>
		
			<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();" order="5">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
			<ui:combin ref="menu.path.simplecategory">
				<ui:varParams 
					moduleTitle="${ lfn:message('kms-multidoc:table.kmdoc') }" 
					modulePath="/kms/multidoc/" 
					modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" 
					autoFetch="false"
					target="_blank"
					categoryId="${kmsMultidocKnowledgeForm.docCategoryId}" />
			</ui:combin>
	</template:replace>	
	<c:if test="${showContentType eq 'right'}">
	<%-- 流程右侧审批三级页面模板 --%>
	<template:replace name="barRight">
		<div class="bar_tabpanel_box tabpanel_box">
		<ui:tabpanel layout="sys.ui.tabpanel.sucktop" var-average='false' var-useMaxWidth='true'>
				<ui:content title="${ lfn:message('kms-multidoc:kmsMultidoc.docInfo') }" 
					titleicon="lui-fm-icon-2" id="barRight_kmsMultidoc_baseInfo">
					<div style="width: 338px;margin: 0 auto;border-bottom: 1px solid #eee;padding-bottom: 8px;">
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="spic"/>
							<c:param name="fdAttType" value="pic"/>
							<c:param name="fdShowMsg" value="true"/>
							<c:param name="fdMulti" value="false"/>
							<c:param name="fdLayoutType" value="pic"/>
							<c:param name="fdPicContentWidth" value="338"/>
							<c:param name="fdPicContentHeight" value="200"/>
							<c:param name="fdViewType" value="pic_single"/>
						</c:import>
					</div>
					<div class="barRight_kmsMultidoc_baseInfo_box">
						<table class="tb_n_simple tb_n_simple_add" width="96%" id="validate_ele0">
							<tr>
								<!-- 作者 -->
								<td width="22%" class="td_normal_title">
									<span class="txtstrongLight">*</span>
									<bean:message bundle="kms-multidoc" key="kmsMultidoc.Author" />
								</td>
								<td  colspan="3" width="78%" class="authorType_td">
									<xform:radio 
										htmlElementProperties="placeholder=${lfn:message('kms-knowledge:kms.knowledge.placeholder.secondCategort')}"
										property="authorType" 
										onValueChange="changeAuthorType" 
										value="${not empty kmsMultidocKnowledgeForm.fdDocAuthorList?1:2}">
										<xform:enumsDataSource enumsType="kmsKnowledgeAuthorType">
										</xform:enumsDataSource>
									</xform:radio>
								</td>
							</tr>
							<tr>
								<td width="22%" class="td_normal_title" style="position: relative;left: -10px;">
									<%--<span class="txtstrongLight">*</span>
									<bean:message bundle="kms-multidoc" key="kmsMultidoc.Author" />--%>
								</td>
								<td  colspan="3" width="78%" class="innerAuthor_td" id="innerAuthor" <c:if test="${empty kmsMultidocKnowledgeForm.fdDocAuthorList }">style="display: none;"</c:if> >
									<xform:address 
								    	mulSelect="true"
										required="true" 
										style="width:95%" 
										propertyId="docAuthorId" 
										propertyName="docAuthorName" 
										orgType="ORG_TYPE_PERSON"
										onValueChange="changeAuthodInfo" />
								</td>
								<td  colspan="3" width="78%" class="outerAuthor_td" id="outerAuthor" <c:if test="${not empty kmsMultidocKnowledgeForm.fdDocAuthorList }">style="display: none;"</c:if>>
									<xform:text property="outerAuthor" subject="${ lfn:message('kms-multidoc:kmsMultidoc.Author')}" 
												className="inputselectsgl"
												style="width:94%"></xform:text>
									<span class="txtstrong">*</span>
								</td>
							</tr>
										
							<tr>
								<!-- 所属部门 -->
								<td width="22%" class="td_normal_title">
									<bean:message bundle="sys-doc" key="sysDocBaseInfo.docDept" />
								</td>
								<td width="78%" class="docDept_td">
									<xform:address 
										required="false" 
										validators="" 
										subject="${ lfn:message('kms-multidoc:kmsMultidoc.form.main.docDeptId') }" 
										style="width:95%" 
										propertyId="docDeptId" 
										propertyName="docDeptName" 
										orgType='ORG_TYPE_ORGORDEPT'>
									</xform:address>
								</td>
							</tr>
							<tr>
								<!-- 所属岗位 -->
								<td width="22%" class="td_normal_title">
									<bean:message bundle="kms-multidoc" key="table.kmsMultidocMainPost" />
								</td>
								<td width="78%" class="docPosts_td">
									<xform:address required="false" style="width:95%" propertyId="docPostsIds"
									 mulSelect="true"
									 propertyName="docPostsNames" orgType='ORG_TYPE_POST'></xform:address>
								</td>
							</tr>
							<!-- 主分类 -->
							<tr>
								<td class="td_normal_title" width="22%">
									<bean:message key="kmsMultidocKnowledge.fdMainCate" bundle="kms-multidoc" />
								</td>
								<td width="78%" colspan="3" class="docCategory_td">
									<html:hidden property="docCategoryId" /> 
									<span name="docTemp"><bean:write name="kmsMultidocKnowledgeForm" property="docCategoryName" /></span>
									<c:if test="${param.method == 'add' }">
										<a href="javascript:modifyCate(true,true)" style="margin-left:15px;" class="com_btn_link">${lfn:message('kms-multidoc:kmsMultidoc.changeTemplate') }</a>
										<span class="txtstrong">*</span>
									</c:if>
								</td>
							</tr>
							
							<!-- 辅分类 -->
							<c:if test="${!kmsCategoryEnabled}">
								<tr class="kmsCategory_tr">
									<td class="td_normal_title" width="10%">
										<bean:message key="kmsMultidoc.kmsMultidocKnowledge.docProperties" bundle="kms-multidoc" />
									</td>
									<td colspan="3" width="90%" class="docSecondCategories_td">
										<xform:dialog 
											htmlElementProperties="placeholder=${lfn:message('kms-knowledge:kms.knowledge.placeholder.secondCategort')}"
											propertyId="docSecondCategoriesIds" 
											propertyName="docSecondCategoriesNames" 
											style="width:95%" >
											modifySecondCate(true);
										</xform:dialog>	
									</td>
								</tr>
							</c:if>
							<!-- 知识分类-->
							<c:if test="${kmsCategoryEnabled}">
								<tr class="kmsCategory_tr">
									<c:import
										url="/kms/category/kms_category_main/import/kmsCategoryMain_edit.jsp"
										charEncoding="UTF-8">
										<c:param name="formName" value="kmsMultidocKnowledgeForm" />
										<c:param name="modelName"
											value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
									</c:import>
								</tr>
							</c:if>
							<!-- 标签 -->
							<tr class="sysTagMain_tr">
								<c:import url="/sys/tag/import/sysTagMain_edit.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmsMultidocKnowledgeForm" />
									<c:param name="fdKey" value="mainDoc" /> 
									<c:param name="fdQueryCondition" value="docCategoryId;docDeptId" /> 
									<c:param name="fdInputWidth" value="95%" />
									<c:param name="tagJs" value="new" />
								</c:import>
							</tr>
							<c:if test="${hiezEnable&&hiezAutoTag}">
								<tr>
									<c:import url="/kms/common/smartlabel/edit.jsp" charEncoding="UTF-8">
										<c:param name="formName" value="kmsMultidocKnowledgeForm" />
									</c:import>
								</tr>
							</c:if>

							<!-- 智能标签 -->
						   <kmss:ifModuleExist path="/third/intell/">
								<tr class="tagMain_tr">
									<c:import url="/third/intell/ui/tagMain_edit.jsp">
									<c:param name="formName" value="kmsMultidocKnowledgeForm" />
									<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
									<c:param name="modelId" value="${kmsMultidocKnowledgeForm.fdId}" />
									<c:param name="fdKey" value="mainDoc" /> 
									<c:param name="fdQueryCondition" value="docCategoryId;docDeptId" /> 
									<c:param name="fdInputWidth" value="98.8%" /> 
									</c:import>
								</tr>
							</kmss:ifModuleExist>
							<c:if test="${docLifeCycleShowFlag eq 'true' && kmsMultidocKnowledgeForm.docStatus=='10'}">
								<tr>
									<!-- 生效时间 -->
									<td width="22%" class="td_normal_title">
										<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.docEffectiveTime" />
									</td>
									<td width="78%" colspan="3" class="docEffectiveTime_td" class="lif_cycle_show">
										<xform:datetime property="docEffectiveTime" 
											placeholder="${lfn:message('kms-knowledge:kms.knowledge.placeholder.docEffectiveTime') }"
											style="width:95%" validators="checkDocEffectiveTime"/>
									</td>
								</tr>
								<tr>
									<!-- 失效时间 -->
									<td width="22%" class="td_normal_title">
										<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.docFailureTime" />
									</td>
									<td width="78%" colspan="3" class="docFailureTime_td"  class="lif_cycle_show">
										<xform:datetime 
											placeholder="${lfn:message('kms-knowledge:kms.knowledge.placeholder.docFailureTime') }"
											property="docFailureTime" 
											style="width:95%"
											validators="checkDocFailureTime checkDocEffectiveTime"/>
									</td>
								</tr>					
							</c:if>
							<!-- 存放期限 -->
							<c:if test="${kms_professional}">
								<tr>
									<td class="td_normal_title" width="22%">
										<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.docExpireTime" />
									</td>
									<td width="78%" colspan="3" class="docExpireTime_td">
										<xform:datetime
										placeholder="${lfn:message('kms-multidoc:message.storetime.day.myinfo01')}"
										property="docExpireTime"
										dateTimeType="Date"
										style="width:95%" validators="checkDocExpireTime checkDocFailureTime"/>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title"  colspan="4" style="color: #B0B0B0;">
										<div class="lui_icon_s lui_icon_s_icon_question_sign"></div>
										<span style="font-size: 12px;">${lfn:message('kms-multidoc:message.storetime.day.myinfo02')}</span>
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="td_normal_title" width="22%">
									<bean:message bundle="kms-multidoc" key="kmsMultidoc.creator" />
								</td>
								<td width="78%" colspan="3">
									<ui:person personId="${kmsMultidocKnowledgeForm.docCreatorId}" personName="${kmsMultidocKnowledgeForm.docCreatorName}">
									</ui:person>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="22%">
									<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.docCreateTime" />
								</td>
								<td width="78%" colspan="3">
									<kmss:showDate value="${ docCreateTime }" type="date"/>
								</td>
							</tr>
						</table>
						<ul class='lui_form_info'>
							<li>
								<div id="r_info1" class="lui_multidoc_hideLi">
									<div style='margin-left:-8px;margin-right:-8px;margin-bottom:8px;border-bottom: 1px #bbb dashed;height:8px'></div>
									<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.fdMainCate" />：
									<span id="docTemplate_span">
									</span>
								</div>
								<div id="r_info2" class="lui_multidoc_hideLi">
									<bean:message bundle="kms-multidoc" key="kmsMultidoc.kmsMultidocKnowledge.docProperties" />：
									<span id="secondaryCategories_span">
									</span>
								</div>
							</li>
						</ul>
					</div>
				</ui:content>	
				<%--流程右侧信息--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdKey" value="mainDoc" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import>
			</ui:tabpanel>
		</div>
	</template:replace>
	</c:if>
	<c:choose>
		<%-- 流程引擎默认设置是否开启右侧审批模式 --%>
		<c:when test="${showContentType eq 'right'}">
			<%-- 流程右侧审批三级页面模板 --%>
			<c:set var="layout" value="sys.ui.step.fixed"></c:set>
		</c:when>
		<c:otherwise>
		<%-- 普通模式页面模板 --%>
			<c:set var="layout" value="sys.ui.step.arrow"></c:set>
		</c:otherwise>
	</c:choose>
	<template:replace name="content"> 
		<c:if test="${showContentType ne 'right'}">
			<%-- 普通模式页面模板 --%>
			<form name="kmsMultidocKnowledgeForm" method="post" 
				action="${KMSS_Parameter_ContextPath}kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do">
		</c:if>
			<html:hidden property="fdImportInfo" />
			<html:hidden property="fdId" />
		<c:if test="${empty kmsMultidocKnowledgeForm.docSubject}">
			<p class="txttitle" style="display: none;"><bean:message bundle="kms-multidoc" key="kmsMultidoc.form.title" /></p>
		</c:if>
		<c:if test="${!empty kmsMultidocKnowledgeForm.docSubject}">
			<p class="txttitle" style="display: none;">${kmsMultidocKnowledgeForm.docSubject }</p>
		</c:if> 
		<html:hidden property="fdId" />
		<html:hidden property="fdModelId" />
		<html:hidden property="fdModelName" />
		<html:hidden property="fdWorkId" />
		<html:hidden property="docStatus" />
		<html:hidden property="fdPhaseId" />
		<html:hidden property="extendFilePath"/>
		<html:hidden property="extendDataXML"   />
		<html:hidden property="docIsIndexTop" />
		<div>
			<table class="tb_n_simple tb_n_simple_add_right" width="100%" id="validate_ele0">
				<!-- 文档附件 -->
				<tr>
					<td>
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="attachment" />
							<c:param name="extParam" value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
							<c:param name="totalWidth" value="98.5%" />
						</c:import>
					</td>
				</tr>
				<!-- 标题 -->
				<tr>
					<td class="docSubject_td inputCount_td">
						<span class="txtstrongLight">*</span>
						<c:choose>
							<%-- 流程引擎默认设置是否开启右侧审批模式 --%>
							<c:when test="${hiezEnable}">
								<xform:text
										htmlElementProperties="
								oninput='inputCount(this, 200, \"#subjectCount\")'
								placeholder=\"${lfn:message('kms-knowledge:kms.knowledge.placeholder.docSubject')}\""
										isLoadDataDict="false"
										validators="maxLength(200)"
										subject="${ lfn:message('kms-multidoc:kmsMultidocKnowledge.docSubject')}"
										property="docSubject" required="true"
										onValueChange="${isHasHiez?'getHYZCategory();getHYZTag();':'' }"/>
							</c:when>
							<c:otherwise>
								<xform:text
										htmlElementProperties="
								oninput='inputCount(this, 200, \"#subjectCount\")'
								placeholder=\"${lfn:message('kms-knowledge:kms.knowledge.placeholder.docSubject')}\""
										isLoadDataDict="false"
										validators="maxLength(200)"
										subject="${ lfn:message('kms-multidoc:kmsMultidocKnowledge.docSubject')}"
										property="docSubject" required="true"
										onValueChange="${isHasIstorage?'getSimdoc();getBigDataCategory();getBigDataTag();':'' }"/>
							</c:otherwise>
						</c:choose>
						<span id="subjectCount" class="inputCount_span"></span>
					</td>
				</tr>

				<tr>
					<td class="description_td inputCount_td">
						<div class="add_desc_btn" onclick="javascript: $('.desc_input_box').show();$(this).remove()">${lfn:message('kms-multidoc:kmsMultidoc.add.des') }</div>
						<c:choose>
							<c:when test="${hiezEnable}">
								<div class="desc_input_box" style="display: none;">
									<xform:textarea
											isLoadDataDict="false"
											property="fdDescription"
											htmlElementProperties="
								oninput='inputCount(this, 4000, \"#descCount\")'
								placeholder=\"${lfn:message('kms-knowledge:kms.knowledge.placeholder.des') }\""
											validators="maxLength(4000)"
											style="width:97.5%;height:160px"
											className="inputmul"
											onValueChange="${isHasHiez?'getHYZCategory();getHYZTag();':'' }"/>
									<span id="descCount" class="inputCount_span" style="background: #fafafa;top: -17px;right: 15px;"></span>
								</div>
							</c:when>
							<c:otherwise>
								<div class="desc_input_box" style="display: none;">
									<xform:textarea
											isLoadDataDict="false"
											property="fdDescription"
											htmlElementProperties="
								oninput='inputCount(this, 4000, \"#descCount\")'
								placeholder=\"${lfn:message('kms-knowledge:kms.knowledge.placeholder.des') }\""
											validators="maxLength(4000)"
											style="width:97.5%;height:160px"
											className="inputmul"
											onValueChange="${isHasIstorage?'getSimdoc();getBigDataTag();':'' }"/>
									<span id="descCount" class="inputCount_span" style="background: #fafafa;top: -17px;right: 15px;"></span>
								</div>
						   </c:otherwise>
						</c:choose>
						<div>
							<c:if test="${kmsAiSwitch.textSummary}">
								<a class="btn_txt" href="javascript:wpsAutoCreateDescBySummary()">智能摘要</a>
							</c:if>

						</div>
					</td>
				</tr>
				<c:if test="${kms_professional}">
					<tr>
						<td style="padding: 0px;">
							<table class="tb_n_simple property_table" style="float: left;position: relative; left: 5px;">
								<tr>
									<c:if test="${not empty kmsMultidocKnowledgeForm.extendFilePath}">
										<c:import url="/sys/property/include/sysProperty_edit.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="kmsMultidocKnowledgeForm" />
											<c:param name="fdDocTemplateId" value="${kmsMultidocKnowledgeForm.docCategoryId}" />
										</c:import>
									</c:if>
								</tr>
							</table>
						</td>
					</tr>
				</c:if>
				<tr> 
					<td>
						<kmss:editor property="docContent" toolbarSet="Default" height="400" width="98%"/>
					</td>
				</tr>
			</table>
			<c:if test="${ not empty kmsMultidocKnowledgeForm.fdDocAuthorList }">
			    <div style="display: none;">
					<table id="authorsArrary" style="display: none;">
					  <tr>
					     <td>
					         <input type='hidden' name='fdDocAuthorList[0].fdOrgId' value='${kmsMultidocKnowledgeForm.docAuthorId }'>
					         <input type='hidden' name='fdDocAuthorList[0].fdAuthorFag' value='0'>
					     </td>
					  </tr>
					</table>
			   </div>
			</c:if>
			<div class="tabpanel_box">
			<ui:nonepanel>
			<ui:tabpanel layout="sys.ui.tabpanel.sucktop" var-count="6" var-average='false' var-useMaxWidth='true'>
				<!-- 权限及流程 -->
				<ui:content title="${ lfn:message('sys-lbpmservice:lbpm.tab.label') }" toggle="false">
					<c:choose>
						<%-- 流程引擎默认设置是否开启右侧审批模式 --%>
						<c:when test="${showContentType eq 'right'}">
						<%-- 流程右侧审批三级页面模板 --%>
						<ui:nonepanel>
							<%--流程主要信息 --%>
							<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmsMultidocKnowledgeForm" />
								<c:param name="fdKey" value="mainDoc" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
							</c:import>
						</ui:nonepanel>
						</c:when>
						<c:otherwise>
						<%-- 普通模式页面模板 --%>
						<ui:nonepanel>
						<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmsMultidocKnowledgeForm" /> 
							<c:param name="fdKey" value="mainDoc" />
							<c:param name="showHistoryOpers" value="true" />
		                    <c:param name="isExpand" value="true" />
						</c:import>
						</ui:nonepanel>
						</c:otherwise>
					</c:choose>
				</ui:content>
				<ui:content title="${ lfn:message('kms-multidoc:kmsMultidocTemplate.lbl.publish')}">
					<div class="lui_multidoc_publish_news_content">
						<c:import
							url="/sys/news/import/sysNewsPublishMain_edit.jsp"
							charEncoding="UTF-8">
							<c:param
								name="formName"
								value="kmsMultidocKnowledgeForm" />
							<c:param name="fdKey" value="mainDoc" />	 
							<c:param name="isShow" value="true" />
						</c:import>
					</div>
				</ui:content>
				<c:if test="${kms_professional}">
					<kmss:ifModuleExist path="/kms/learn">
						<ui:content title="${lfn:message('kms-multidoc:kmsMultidoc.publish.coash') }">
							<c:import url="/kms/learn/kms_learn_courseware/import/KmsLearnCoursewarePublishMain_edit.jsp" charEncoding="UTF-8">
								<c:param
									name="formName"
									value="kmsMultidocKnowledgeForm" />
								<c:param name="fdKey" value="mainDoc" />
							</c:import>
						</ui:content>
					</kmss:ifModuleExist>
				</c:if>
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
				</c:import>
				<!-- 关联机制(与原有机制有差异) -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="approveType" value="right" />
					<c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
			</ui:nonepanel>
			</div>
		</div>
	<c:if test="${showContentType ne 'right'}">
		</form>
	</c:if>


		<c:choose>
			<c:when test="${hiezEnable}">
				<c:import url="/third/hiez/import/simdocAsyn.jsp"
						  charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
				</c:import>
			</c:when>
			<c:otherwise>
				<kmss:ifModuleExist path="/kms/istorage/">
					<c:import url="/kms/istorage/import/simdocAsyn.jsp"
							  charEncoding="UTF-8">
						<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					</c:import>
				</kmss:ifModuleExist>
			</c:otherwise>
		</c:choose>



</template:replace>
<c:if test="${showContentType ne 'right'}">
	<template:replace name="barLeft">
		<div style="width:252px"></div>
		<ui:panel toggle="false" layout="sys.ui.panel.simpletitle" >
			<ui:content title="${ lfn:message('kms-multidoc:kmsMultidoc.docInfo') }" toggle="true">
				<ul class='lui_form_info'>
					<li>
						<div id="r_info" class="lui_multidoc_hideLi">
							<bean:message bundle="kms-multidoc" key="kmsMultidoc.Author" />：
							<span id="author_span">
							</span>
						</div>
					</li>
					<li>
						<bean:message bundle="kms-multidoc" key="kmsMultidoc.creator" />： 
						<ui:person personId="${kmsMultidocKnowledgeForm.docCreatorId}" personName="${kmsMultidocKnowledgeForm.docCreatorName}">
						</ui:person>
					</li>
					<li><bean:message bundle="sys-doc" key="sysDocBaseInfo.docStatus" />：<sunbor:enumsShow	value="${kmsMultidocKnowledgeForm.docStatus}"	enumsType="common_status" /></li>
					
					<li><bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.docCreateTime" />：<kmss:showDate value="${ docCreateTime }" type="date"/></li>				
				</ul>
				<ul class='lui_form_info'>
					<li>
						<div id="r_info1" class="lui_multidoc_hideLi">
							<div style='margin-left:-8px;margin-right:-8px;margin-bottom:8px;border-bottom: 1px #bbb dashed;height:8px'></div>
							<bean:message bundle="kms-multidoc" key="kmsMultidocKnowledge.fdMainCate" />：
							<span id="docTemplate_span">
							</span>
						</div>
						<div id="r_info2" class="lui_multidoc_hideLi">
							<bean:message bundle="kms-multidoc" key="kmsMultidoc.kmsMultidocKnowledge.docProperties" />：
							<span id="secondaryCategories_span">
							</span>
						</div>
					</li>
				</ul>
			</ui:content>
		</ui:panel>
		
		<%--关联机制(与原有机制有差异)--%>
		<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmsMultidocKnowledgeForm" />
			<c:param name="layout" value="sys.ui.accordionpanel.simpletitle"></c:param>
		</c:import>
		
	</template:replace>
</c:if>
	<template:replace name="bodyBottom">
		<%@ include file="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_add_js.jsp"%>
		<!-- 敏感词过滤 -->
		<%@ include file="/kms/multidoc/kms_multidoc_words/kmsMultidocWords_checkWork_script.jsp"%>
		<script language="JavaScript">
			$KMSSValidation(document.forms['kmsMultidocKnowledgeForm']).addValidators(validations);
		</script>
		<script>
		  $KMSSValidation();
			Com_IncludeFile("jquery.js|dialog.js|data.js");
			seajs.use("kms/knowledge/support/js/kmsKnowledgeUtils.js");
			seajs.use("kms/knowledge/support/style/inputCount.css");
			seajs.use("kms/knowledge/support/style/newTabpanelStyle.css");
			var _validation = $KMSSValidation(document.forms['kmsMultidocKnowledgeForm']);
			LUI.ready(function() {
				initInputCountEvent();
				var validators = {
						'checkName' : 
						{
							error : "<bean:message key='kmsMultidoc.validateOutAuthorFormat' bundle='kms-multidoc'/>",
							test:function(v,e,o) {
								v = $.trim(v);
								if (v.indexOf("；")==-1 
										&& v.indexOf("，")==-1 
										&& v.indexOf(",")==-1
										&& v.indexOf("-")==-1 
										&& v.indexOf("_")==-1 ) {
									return true;
								}else {
									return false;
								}
							}
						}
					};
			      _validation.addValidators(validators);
			});
			
		</script>
	</template:replace>
</template:include>
	