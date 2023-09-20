<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService"%>
<%@page import="com.landray.kmss.kms.multidoc.forms.KmsMultidocKnowledgeForm"%>
<%@page import="com.landray.kmss.util.ArrayUtil"%>
<%@page import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<%@page import="com.landray.kmss.kms.knowledge.forms.KmsKnowledgeMainAuthorForm"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.Integer"%>
<%@page import="java.lang.String"%>
<%@ page import="com.landray.kmss.sys.appconfig.model.BaseAppconfigCache" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.landray.kmss.kms.multidoc.service.IKmsMultidocSubsideParamService" %>
<%@ page import="com.landray.kmss.kms.multidoc.model.KmsMultidocSubsideParam" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<%

	Map<String, String> data =  BaseAppconfigCache.getCacheData("com.landray.kmss.third.hiez.config.ThirdHiezConfig");
	boolean hiezEnable = false;
	boolean introduceKnowlege = false;
	boolean hiezAutoCate = false;
	boolean hiezAutoTag = false;
	if(null != data){
		hiezEnable =  null != data.get("enable") && "true".equals(data.get("enable"))? true:false;
		introduceKnowlege = null != data.get("introduceEnable") && "true".equals(data.get("introduceEnable"))? true:false;
		introduceKnowlege = hiezEnable && introduceKnowlege;
		hiezAutoCate =  null != data.get("cateEnabel") && "true".equals(data.get("cateEnabel"))? true:false;
		hiezAutoTag =  null != data.get("tagEnabel") && "true".equals(data.get("tagEnabel"))? true:false;

	}
	request.setAttribute("hiezEnable", hiezEnable);
	request.setAttribute("introduceKnowlege", introduceKnowlege);
	request.setAttribute("hiezAutoCate", hiezAutoCate);
	request.setAttribute("hiezAutoTag", hiezAutoTag);
	KmsMultidocKnowledgeForm kmsMultidocKnowledgeForm = (KmsMultidocKnowledgeForm) request.getAttribute("kmsMultidocKnowledgeForm");
	List<KmsKnowledgeMainAuthorForm>  llist = kmsMultidocKnowledgeForm.getFdDocAuthorList();
	String Id = "";
	String name = "";
	String Ids = "";
	String names = "";
	String outerAuthorName="";
	if(llist.size() > 3){
		for(Integer i=0; i<3; i++){
			KmsKnowledgeMainAuthorForm kmsKnowledgeMainAuthorForm = llist.get(i);
			if(i==2){
				Id = Id + kmsKnowledgeMainAuthorForm.getFdOrgId();
				name = name+kmsKnowledgeMainAuthorForm.getFdOrgName();
			} else {
				Id = Id + kmsKnowledgeMainAuthorForm.getFdOrgId()+";";
				name = name + kmsKnowledgeMainAuthorForm.getFdOrgName()+";";
			}
		}
		request.setAttribute("authorId", Id);
		request.setAttribute("authorName", name);
	}
	int last=llist.size()-1;
	for(Integer i=0; i<llist.size(); i++){
		KmsKnowledgeMainAuthorForm kmsKnowledgeMainAuthorForm = llist.get(i);
		if(i==last){
		    Ids = Ids + kmsKnowledgeMainAuthorForm.getFdOrgId();
		    names = names + kmsKnowledgeMainAuthorForm.getFdOrgName();
		}else{
		    Ids = Ids + kmsKnowledgeMainAuthorForm.getFdOrgId()+";";
		    names = names + kmsKnowledgeMainAuthorForm.getFdOrgName()+";";
		}
	}
	outerAuthorName=kmsMultidocKnowledgeForm.getOuterAuthor();
	request.setAttribute("authorIds", Ids);
	request.setAttribute("authorNames", names);
	request.setAttribute("outerAuthorName", outerAuthorName);
	//是否展示附件权限申请按钮
	java.util.Map map = new java.util.HashMap<String, String>(
			com.landray.kmss.sys.appconfig.model.BaseAppconfigCache.getCacheData("com.landray.kmss.kms.knowledge.borrow.config.KmsKnowledgeBorrowConfig"));
	if(map !=null&&map.size() !=0){
		String attAuthCaterogyList = (String)map.get("kms.knowledge.borrow.attAuthCategoryIdList");
		request.setAttribute("attAuthCaterogyList",attAuthCaterogyList);
	}
%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></lbpm:lbpmApproveModel>
<c:choose>
	<%-- 流程引擎默认设置是否开启右侧审批模式 --%>
	<c:when test="${lbpmApproveModel eq 'right'}">
		<%-- 流程右侧审批三级页面模板 --%>
		<c:set var="ref" value="lbpm.right"></c:set>
		<c:set var="showContentType" value="right"></c:set>
		<c:set var="showQrcode" value="true"></c:set>
		<c:set var="formName" value="kmsMultidocKnowledgeForm"></c:set>
		<c:set var="formUrl" value="${KMSS_Parameter_ContextPath}kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do"></c:set>
		<c:set var="showPraise" value="yes"></c:set>
		<set var="knowledgeCategoryId" value="${knowledgeCategoryId}"></set>
		<set var="attAuthCaterogyList" value="${attAuthCaterogyList}"></set>
	</c:when>
	<c:otherwise>
		<!-- 左侧审批模板-->
		<c:set var="ref" value="kms.knowledge.content.left"></c:set>
		<!-- 知识仓库优化后默认使用原右侧审批的内容 -->
		<c:set var="showContentType" value="right"></c:set>
		<c:set var="showQrcode" value="true"></c:set>
		<c:set var="formName" value="kmsMultidocKnowledgeForm"></c:set>
		<c:set var="formUrl" value="${KMSS_Parameter_ContextPath}kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do"></c:set>
		<c:set var="showPraise" value="yes"></c:set>
		<set var="knowledgeCategoryId" value="${knowledgeCategoryId}"></set>
		<set var="attAuthCaterogyList" value="${attAuthCaterogyList}"></set>
	</c:otherwise>
</c:choose>
<%
	KmsMultidocKnowledgeForm mainForm = (KmsMultidocKnowledgeForm)request.getAttribute("kmsMultidocKnowledgeForm");
	String fdId = mainForm.getFdId();
	boolean isConverting = false; //是否有正在转换中的附件
	String converterKey = "toPDF";
	String converterType = "aspose";
	IKmsMultidocSubsideParamService kmsMultidocSubsideParamService = (IKmsMultidocSubsideParamService) SpringBeanUtil.getBean("kmsMultidocSubsideParamService");
	KmsMultidocSubsideParam subsideParam = kmsMultidocSubsideParamService.findByPkId(fdId);
	if (subsideParam != null) {
		converterType = subsideParam.getFdConvertType();
		converterKey = subsideParam.getFdMainDocTo();
	}
	ISysFileConvertQueueService queueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
	isConverting = !ArrayUtil.isEmpty(queueService.isAllSuccess(fdId, converterKey, converterType));
	pageContext.setAttribute("isConverting", isConverting);
%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="kmsMultidocKnowledgeTemplateName" value="${ lfn:message('kms-multidoc:kmsMultidocKnowledge.fdTemplateName') }"></c:set>	
<c:set var="lblTemplateinfo" value="${ lfn:message('kms-multidoc:kmsMultidocTemplate.lbl.templateinfo') }"></c:set>

<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
		String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled)) {
	%>
		<c:set var="kmsCategoryEnabled" value="true"></c:set>
		<c:set var="kmsMultidocKnowledgeTemplateName" value="${ lfn:message('kms-multidoc:kmsMultidocKnowledge.fdTemplateName.categoryTrue') }"></c:set>
		<c:set var="lblTemplateinfo" value="${ lfn:message('kms-multidoc:kmsMultidocTemplate.lbl.templateinfo.categoryTrue') }"></c:set>

	<%
		}
	%>
</c:if>
<template:include
		showQrcode="${showQrcode }"
		pathFixed='${pathFixed }' 
		ref="${ref }"
		leftWidth="${leftWidth }"
		leftShow ="${leftShow }"
		leftBar="${leftBar }"
		showPraise="${showPraise }"
		formName="${formName }"
		formUrl="${formUrl }" >
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/multidoc/kms_multidoc_ui/style/view.css" />
		
		<script src="${LUI_ContextPath}/kms/common/resource/js/kms_utils.js"></script>
		<c:set var="attForms"
			value="${kmsMultidocKnowledgeForm.attachmentForms['spic'] }" />
		<c:set var="hasPic" value="${false}" />
		<c:forEach var="sysAttMain" items="${attForms.attachments }"
			varStatus="vsStatus">
			<c:if test="${vsStatus.first }">
				<c:set var="hasPic" value="${true}" />
			</c:if>
		</c:forEach>

		<c:if test="${lbpmApproveModel eq 'right'}">
			<style>

				.lui_accordionpanel_simpletitle_nav_c .lui_accordionpanel_toggle_up{
					right: 0!important;
				}
				.right_model .lui_accordionpanel_simpletitle_nav_c .lui_accordionpanel_toggle_down, .right_model .lui_accordionpanel_simpletitle_header_l:hover .lui_accordionpanel_simpletitle_nav_c .lui_accordionpanel_toggle_down{
					height: 16px!important;
				}
				.lui-fm-slide-btn {
					margin-left: 0;
				}

				.lui-fm-slide-btn-left {
					margin-left: 0px !important;
				}

				.lui-fm-slide-btn{
					margin-left: 4px;
				}

				.lui-fm-flexibleL-inner {
					position: relative;
					left: 10px;
				}

				#spreadBtn {
					margin-right: -10px;
				}
			</style>
		</c:if>
		<c:if test="${lbpmApproveModel ne 'right'}">
			<style>
				.lui-fm-stickyR-inner {
					right: -10px;
				}
			</style>
		</c:if>
	</template:replace>
	
	<template:replace name="title">
		<c:out value="${ kmsMultidocKnowledgeForm.docSubject } - ${ lfn:message('kms-multidoc:table.kmdoc') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<c:if test="${kmsMultidocKnowledgeForm.docDeleteFlag ==1}">
			<ui:toolbar layout="sys.ui.toolbar.float">
				<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow(); " order="5"/>
			</ui:toolbar>
		</c:if>
		<c:if test="${kmsMultidocKnowledgeForm.docDeleteFlag !=1}">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
				<c:if test="${kmsMultidocKnowledgeForm.docIsNewVersion!=false}">
					<c:if test="${showContentType eq 'right'}">
						<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow(); " order="1"/>
						<!-- 编辑 -->
						<c:if test="${kmsMultidocKnowledgeForm.docStatusFirstDigit > '0' }">
							<kmss:auth
								requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=edit&fdId=${param.fdId}"
								requestMethod="GET">
								<ui:button text="${lfn:message('button.edit')}"
									onclick="toEditView();"
									order="2">
								</ui:button>
							</kmss:auth>
						</c:if>
						<!-- 纠错-->
						<c:if test="${kms_professional}">
							<kmss:auth
								requestURL="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=add&fdId=${kmsMultidocKnowledgeForm.fdId}&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
									requestMethod="GET">
								<ui:button parentId="toolbar"
										text="${ lfn:message('kms-common:kmsCommonDocErrorCorrection.error') }"
										onclick="changeErrorCorrection();" order="3">
								</ui:button>
							</kmss:auth>
						</c:if>
					</c:if>
					<c:if test="${showContentType ne 'right'}">
						<c:if test="${kms_professional}">
							<!-- 纠错-->
							<kmss:auth
								requestURL="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do?method=add&fdId=${kmsMultidocKnowledgeForm.fdId}&fdModelName=com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"
									requestMethod="GET">
								<ui:button parentId="toolbar"
										text="${ lfn:message('kms-common:kmsCommonDocErrorCorrection.error') }"
										onclick="changeErrorCorrection();" order="1">
								</ui:button>
							</kmss:auth>
					   </c:if>
						<!-- 编辑 -->
						<c:if test="${kmsMultidocKnowledgeForm.docStatusFirstDigit > '0' }">
							<kmss:auth
								requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=edit&fdId=${param.fdId}"
								requestMethod="GET">
								<ui:button text="${lfn:message('button.edit')}"
									onclick="toEditView();"
									order="2">
								</ui:button>
							</kmss:auth>
						</c:if>
						<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow(); " order="3"/>
					</c:if>

					<c:if test="${kms_professional}">
						<!-- 调整属性-->
						<c:if test="${not empty kmsMultidocKnowledgeForm.extendFilePath}">
						  <kmss:auth
								requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=editProperty&fdId=${param.fdId}"
								requestMethod="GET">
							<ui:button text="${lfn:message('kms-multidoc:kmsMultidoc.button.editProperty') }" order="4" onclick="editProperty();return false;"/>
						 </kmss:auth>
						</c:if>
					</c:if>
					<!-- 分类转移-->
					<c:import url="/sys/simplecategory/import/doc_cate_change_view.jsp" charEncoding="UTF-8">
						<c:param name="fdMmodelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
						<c:param name="docFkName" value="docCategory" />
						<c:param name="fdCateModelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
						<c:param name="fdMmodelId" value="${kmsMultidocKnowledgeForm.fdId }" />
						<c:param name="fdCategoryId" value="${kmsMultidocKnowledgeForm.docCategoryId }" />
						<c:param name="extProps" value="fdTemplateType:1;fdTemplateType:3" />
					</c:import>
					<%-- 权限变更--%>
					<c:import url="/sys/right/import/doc_right_change_view.jsp" charEncoding="UTF-8">
						<c:param name="fdModelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
						<c:param name="fdCategoryId" value="${kmsMultidocKnowledgeForm.docCategoryId }" />
						<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId }" />
					</c:import>
					 <!-- 置顶 -->
					 <c:if test="${kmsMultidocKnowledgeForm.docIsIndexTop==false && kmsMultidocKnowledgeForm.docStatus == '30' && isHasNewVersion != true }">
					 	<kmss:auth
							 requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=setTop&local=view&fdId=${param.fdId}" requestMethod="GET">
								<ui:button text="${lfn:message('kms-multidoc:kmsMultidoc.setTop')}"
									order="4"
								 onclick="setTop();"/>
						 </kmss:auth>
				 	</c:if>

					 <!-- 取消置顶 -->
					 <c:if test="${kmsMultidocKnowledgeForm.docIsIndexTop==true}">
						<c:choose>
							<c:when test="${kmsMultidocKnowledgeForm.docIsIndexTop == null}">
								<kmss:auth
											requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=cancelTop&local=view&fdId=${param.fdId}"
											requestMethod="GET">
								 	<ui:button text="${lfn:message('kms-multidoc:kmsMultidoc.cancelSetTop')}" order="4" onclick="cancelTop();"/>
								</kmss:auth>
							</c:when>
							<c:otherwise>
								<kmss:auth
											requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=cancelTop&local=view&fdId=${param.fdId}"
											requestMethod="GET">
									<ui:button text="${lfn:message('kms-multidoc:kmsMultidoc.cancelSetTop')}" order="4" onclick="cancelTop();"/>
								</kmss:auth>
							</c:otherwise>
						</c:choose>
					</c:if>

					<!-- 附件权限申请 -->
					<c:if test="${kms_professional}">
						<%--超级管理员不显示附件权限申请按钮--%>
						<kmss:authShow roles="SYSROLE_ADMIN">
							<c:set var="kmsKnowledgeBorrowAttAuthButtonShow" value="false"/>
						</kmss:authShow>
						<c:if test="${empty kmsKnowledgeBorrowAttAuthButtonShow}">
							<kmss:auth
									requestURL="/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do?method=checkAttAuthApplication&fdDocId=${param.fdId}"
									requestMethod="GET">
								<c:if test="${fn:contains(attAuthCaterogyList, knowledgeCategoryId)}">
									<ui:button text="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.button.application')}" order="5" onclick="kmsKnowledgeBorrowAttAuthApplication('${param.fdId}');" />
								</c:if>
							</kmss:auth>
						</c:if>
					</c:if>
				</c:if>
				<c:if test="${isHasNewVersion != true }">
					<!-- 删除(包含软删除) -->
					<kmss:auth
						requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=delete&fdId=${param.fdId}"
						requestMethod="GET">
						<ui:button text="${lfn:message('button.delete')}"
								onclick="confirmDelete();" order="5">
						</ui:button>
					</kmss:auth>
				</c:if>
			</ui:toolbar>
		</c:if>
	</template:replace>
	<template:replace name="path">
			<!-- 路径 -->
			<ui:combin ref="menu.path.simplecategory">
				<ui:varParams 
					moduleTitle="${ lfn:message('kms-multidoc:table.kmdoc') }" 
					modulePath="/kms/knowledge/"
					modelName="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" 
					autoFetch="false"
					href="/kms/knowledge/"
					categoryId="${kmsMultidocKnowledgeForm.docCategoryId}" />
			</ui:combin>
	</template:replace>	
	<template:replace name="content">

		<div class="lui_form_content">
			<c:set
				var="sysDocBaseInfoForm"
				value="${kmsMultidocKnowledgeForm}"
				scope="request" />
			<div class='lui_form_content_top_frame'>
			<div class='lui_form_title_frame
				<c:if test="${fn:length(kmsMultidocKnowledgeForm.fdDescription) == 0}"> lui_form_title_frame_blank</c:if>
				<c:if test="${fn:length(kmsMultidocKnowledgeForm.fdDescription) >= 0}"> lui_form_title_frame_able</c:if>'>
				<!-- 文档标题 -->
				<div class='lui_form_title_box'>
					<div class="kms_multidoc_view_title" title="${ kmsMultidocKnowledgeForm.docSubject }">
						<c:out value="${ kmsMultidocKnowledgeForm.docSubject }"></c:out>
						<c:if test="${kmsMultidocKnowledgeForm.docIsIntroduced==true}">
				  	 		 <img style="position: relative;top: 3px;width: 20px; height: 20px" src="${LUI_ContextPath}/kms/knowledge/kms_knowledge_ui/style/images/essence-icon.png"
				  	 		      border=0
				  	 		      title="${lfn:message('kms-knowledge:kmsKnowledge.introduced')}" />
				    	</c:if>
				    	<c:if test="${isHasNewVersion=='true'}">
						     <span style="color:red">(<bean:message bundle="kms-multidoc" key="kmsMultidoc.kmsMultidocKnowledge.has" />
						     <a href="javascript:;" style="font-size:16px;color:red" onclick="Com_OpenWindow('kmsMultidocKnowledge.do?method=view&fdId=${kmsMultidocKnowledgeForm.docOriginDocId}','_self');">
							 <bean:message bundle="kms-multidoc" key="kmsMultidoc.kmsMultidocKnowledge.NewVersion" /></a>)</span>
				        </c:if>
				        <c:if test="${kmsMultidocKnowledgeForm.docDeleteFlag ==1}">
						     <span style="color:red">(${lfn:message('kms-multidoc:kmsMultidocKnowledge.has.deleted') })</span>
				        </c:if>
			        </div>
			        <div class="kms_multidoc_view_star">
						<c:import url="/kms/knowledge/import/kms_knowledge_star_eval.jsp">
							<c:param name="starScore"  value="${ kmsMultidocKnowledgeForm.evaluationForm.fdEvaluateScore }" ></c:param>
							<c:param name="showText"  value="false" ></c:param>
							<c:param name="starBoxStyle"  value="width:	115px;" ></c:param>
						</c:import>
					</div>
				</div>
				<div class="lui_form_title_box_bottom">
					<div class="lui_form_title_box_msg">
						<div class='lui_form_author_box'>
							<span><bean:message bundle="sys-doc" key="sysDocBaseInfo.docAuthor" />:</span>
							<c:if test="${not empty kmsMultidocKnowledgeForm.fdDocAuthorList}">
								<span title="${fn:replace(authorNames, ";", " / ")}">
									<c:forEach var="obj"  items="${kmsMultidocKnowledgeForm.fdDocAuthorList}" varStatus="status">
										<c:choose>
											<c:when test="${status.count==kmsMultidocKnowledgeForm.fdDocAuthorList.size() }">
												<ui:person personId="${obj.fdOrgId}" personName="${obj.fdOrgName}"></ui:person>
											</c:when>
											<c:otherwise>
												<ui:person personId="${obj.fdOrgId}" personName="${obj.fdOrgName}"></ui:person> /
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</span>
							</c:if>
							<c:if test="${empty kmsMultidocKnowledgeForm.fdDocAuthorList  }">
								 <span title="<c:out value="${kmsMultidocKnowledgeForm.outerAuthor }"/>">
								 <c:out value="${kmsMultidocKnowledgeForm.outerAuthor }"/>
								 </span>
							</c:if>
						</div>
						<div class="lui_multidoc_score">
						<span title="${ lfn:message('sys-readlog:sysReadLog.tab.readlog.label') }">
							<span class="row_icon_text">${lfn:message('kms-multidoc:kmsMultidocKnowledge.title.icon1')}：</span>
							<span class="com_number" title="${kmsMultidocKnowledgeForm.readLogForm.fdReadCount}">
								${kmsMultidocKnowledgeForm.readLogForm.fdReadCount}
							</span>
						</span>
							<c:if test="${kmsMultidocKnowledgeForm.docStatus == 30}">
							<span title="${ lfn:message('sys-introduce:sysIntroduceMain.tab.introduce.label') }">
								<span class="row_icon_text">${lfn:message('kms-multidoc:kmsMultidocKnowledge.title.icon2')}：</span>
								<c:if test="${not empty kmsMultidocKnowledgeForm.introduceForm.fdIntroduceCount}">
									<c:set var="fdIntroduceCountReplace" value="${fn:replace(kmsMultidocKnowledgeForm.introduceForm.fdIntroduceCount, '(', '')}" scope="request" />
									<c:set var="IntroduceCountReplace" value="${fn:replace(fdIntroduceCountReplace, ')', '')}" scope="request" />
								</c:if>
								<c:set var="introduceCount" value="${not empty IntroduceCountReplace ? IntroduceCountReplace : '0'}" ></c:set>
								<span class="com_number" title="${ introduceCount }">
									${ introduceCount }
								</span>
							</span>
								<span title="${ lfn:message('sys-evaluation:sysEvaluationMain.tab.evaluation.label') }">
								<span class="row_icon_text">${lfn:message('kms-multidoc:kmsMultidocKnowledge.title.icon3')}：</span>
								<c:set var="evaluateCount" value="${ not empty kmsMultidocKnowledgeForm.evaluationForm.fdEvaluateCount?
										fn:substring(kmsMultidocKnowledgeForm.evaluationForm.fdEvaluateCount, 1, fn:indexOf(kmsMultidocKnowledgeForm.evaluationForm.fdEvaluateCount, ')'))
									: '0' }"></c:set>
								<span class="com_number" title="${ evaluateCount }">
									${ evaluateCount }
								</span>
							</span>
							</c:if>
						</div>
					</div>
					<div class='lui_form_des_box'>
						<!-- 文档摘要 -->
						<c:if test="${ not empty kmsMultidocKnowledgeForm.fdDescription }">
							<div class="lui_multidoc_description" title="${ kmsMultidocKnowledgeForm.fdDescription }">
								<xform:textarea property="fdDescription"></xform:textarea>
							</div>
						</c:if>
					</div>
				</div>
			</div>

			<!-- 文档内容 -->
			<div class="lui_form_content_frame" style="min-height: inherit" >
				<c:if test="${not empty sysDocBaseInfoForm.docContent}">
					<div class="content_title_icon"><span>${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.content')}：</span></div>
					<div data-on="doc.content" style="display: block;" class="intell_tag_content">
						<span class="intell_tag_content_span"></span>
						<xform:rtf property="docContent" imgReader="true" ></xform:rtf>
					</div>
				</c:if>
				<%-- 多媒体阅读器 --%>
				<div id="imgInfo">
					<c:import url="/sys/attachment/sys_att_main/sysAttaMain_media_ui.jsp" charEncoding="UTF-8">
						<c:param name="modelId" value="${param.fdId}" />
						<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
						<c:param name="fdKey" value="attachment" />
						<c:param name="isSupportDirect" value="false" />
					</c:import>
				</div>

				<c:if test="${not empty kmsMultidocKnowledgeForm.attachmentForms['attachment'].attachments}">
				<div>
				 	<div style="background: #F8F8F8;margin-top: 18px;padding: 12px 14px;width: calc(100% + 4px);position: relative;left: -16px;">${ lfn:message('kms-multidoc:kmsMultidoc.filedownload') }</div>
					<c:choose>
	                  <c:when test="${isConverting }">
	                  		<bean:message bundle="kms-multidoc" key="kmsMultidoc.converting"/>
	                  </c:when>
	                  <c:otherwise>
			              	<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysDocBaseInfoForm" />
								<c:param name="fdKey" value="attachment" />
								<c:param name="fdModelName" value="kmsMultidocKnowledge" />
								<%-- <c:param name="fdViewType" value="byte_ui" /> --%>
							</c:import>
	                  </c:otherwise>
	                </c:choose>
				 </div>
				</c:if>
			 </div>
			 </div>
			 <div class="lui_form_tabpanel_frame">
			 	<div class="lui_form_tabpanel_frame_link"></div>
				<c:choose>
					<c:when test="${showContentType eq 'right'}">
						<ui:tabpanel layout="sys.ui.tabpanel.sucktop" var-count="8" var-average='false' var-useMaxWidth='true'>
							<ui:event event="layoutDone">
								$($("#eval_label_title").parents("[data-lui-mark='panel.nav.title']")[0]).attr("title","${lfn:message('sys-evaluation:sysEvaluationMain.tab.evaluation.label')}")
								$($("#intr_label_title").parents("[data-lui-mark='panel.nav.title']")[0]).attr("title","${lfn:message('sys-introduce:sysIntroduceMain.tab.introduce.label')}")
							</ui:event>
							<c:import url="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_view_tabpage.jsp" charEncoding="UTF-8">
								<c:param name="approveModel" value="${showContentType }"></c:param>
								<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId }"></c:param>
							</c:import>
						</ui:tabpanel>
					</c:when>
					<c:otherwise>
						<ui:tabpage expand="false">
							<c:import url="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_view_tabpage.jsp" charEncoding="UTF-8">
								<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId }"></c:param>
							</c:import>
						</ui:tabpage>
					</c:otherwise>
				</c:choose>
			  </div>
		 </div>
	<c:if test="${showContentType ne 'right'}">
		<ui:drawerpanel width="350">
			<c:if test="${!hiezEnable}">
				<c:import url="/kms/common/kms_common_main/doc_cloud.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="template" value="docCategoryName:/kms/multidoc/?categoryId=!{docCategoryId}" />
				</c:import>
			</c:if>
			<c:if test="${hiezEnable}">
				<c:import url="/third/hiez/import/doc_hiez_cloud.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
					<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
				</c:import>
			</c:if>
				<!--相类似知识	-->
				<ui:content
						title="${lfn:message('kms-knowledge:kms.knowledge.similar') }"
						toggle="false"
						titleicon="lui_iconfont_navleft_similar">
					<c:if test="${!introduceKnowlege}">
						<c:import url="/sys/ftsearch/simdocAsyn.jsp" charEncoding="UTF-8">
							<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
							<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
							<c:param name="size" value="5"></c:param>
						</c:import>
					</c:if>
					<c:if test="${introduceKnowlege}">
						<c:import url="/third/hiez/import/doc_hiez_introduce.jsp" charEncoding="UTF-8">
							<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
							<c:param name="formName" value="kmsMultidocKnowledgeForm" />
							<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
						</c:import>
					</c:if>
				</ui:content>
				<c:if test="${kmsMultidocKnowledgeForm.docIsNewVersion!=false}">
					<ui:content
						expand="true"
						title="${lfn:message('kms-knowledge:kms.knowledge.relation') }"
						titleicon="lui_iconfont_navleft_relation">
						<c:import url="/sys/relation/import/sysRelationMain_view_new.jsp"
									charEncoding="UTF-8">
							<c:param name="layout" value="sys.ui.accordionpanel.simpletitle"/>
							<c:param name="formName" value="kmsMultidocKnowledgeForm" />
							<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
							<c:param name="fdCategoryId" value="${kmsMultidocKnowledgeForm.docCategoryId }" />
						</c:import>
					</ui:content>
				</c:if>
		</ui:drawerpanel>
	</c:if>
	<script src="${LUI_ContextPath}/kms/knowledge/resource/js/util.js"></script>
	<script>
	seajs.use("kms/knowledge/support/style/newTabpanelStyle.css");
	window.onload = function(){ 
		var isBitchA = navigator.userAgent.indexOf('Trident') > -1 && navigator.userAgent.indexOf("rv:11.0") > -1; //是否IE11
		var isBitchB = navigator.userAgent.indexOf("compatible") > -1 && navigator.userAgent.indexOf("MSIE") > -1; //是否IE
		setTimeout(function(){
			var drawers = $(".lui_drawerpanel_navs_item_c_hover_r");
			for(var num=0;num<=drawers.length-1;num++){
				var  drawer = $(".lui_drawerpanel_navs_item_c_hover_r")[num];
				var title = drawer.title;
				if(isBitchB||isBitchA){
					drawer.title = "";
				} else {
					drawer.title = $.trim(title);
				}
			}
		}, 1000);
	}

	</script>

	</template:replace>
<c:if test="${showContentType ne 'right'}">
	<template:replace name="barLeft">
		<div style="width: 225px"></div>
		<ui:accordionpanel style="min-width:200px;"  layout="sys.ui.accordionpanel.simpletitle" > 
		<c:import url="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_view_baseinfo.jsp" charEncoding="UTF-8">
			<c:param name="kmsMultidocKnowledgeTemplateName" value="${kmsMultidocKnowledgeTemplateName }" />
			<c:param name="kmsCategoryEnabled" value="${kmsCategoryEnabled}" />
			<c:param name="hasPic" value="true" />
		</c:import>
		</ui:accordionpanel>
	</template:replace>
	</c:if>
<c:if test="${showContentType eq 'right'}">
<template:replace name="barRight">
<div class="barRight_view_info">
<c:choose>
	<c:when test="${kmsMultidocKnowledgeForm.docStatus>='30' || kmsMultidocKnowledgeForm.docStatus=='00'}">

		<ui:accordionpanel>
		<%-- 发布之后或者作废之后没有审批的内容 --%>
		<!-- 基本信息-->
		<c:import url="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_view_baseinfo.jsp" charEncoding="UTF-8">
			<c:param name="kmsMultidocKnowledgeTemplateName" value="${kmsMultidocKnowledgeTemplateName }" />
			<c:param name="kmsCategoryEnabled" value="${kmsCategoryEnabled}" />
			<c:param name="hasPic" value="false" />
			<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId}" />
		</c:import>
			<c:if test="${!hiezEnable}">
				<c:import url="/kms/common/kms_common_main/doc_cloud.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="template" value="docCategoryName:/kms/multidoc/?categoryId=!{docCategoryId}" />
				</c:import>
			</c:if>
			<c:if test="${hiezEnable}">
				<c:import url="/third/hiez/import/doc_hiez_cloud.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
					<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
				</c:import>
			</c:if>
		<!--相类似知识	-->
		<ui:content
				title="${lfn:message('kms-knowledge:kms.knowledge.similar') }"
				toggle="true"
				titleicon="lui-fm-icon-simdoc-01">
			<c:if test="${!introduceKnowlege}">
				<c:import url="/sys/ftsearch/simdocAsyn.jsp" charEncoding="UTF-8">
					<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
					<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
					<c:param name="size" value="5"></c:param>
				</c:import>
			</c:if>
			<c:if test="${introduceKnowlege}">
				<c:import url="/third/hiez/import/doc_hiez_introduce.jsp" charEncoding="UTF-8">
					<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
				</c:import>
			</c:if>
		</ui:content>
		<c:if test="${kmsMultidocKnowledgeForm.docIsNewVersion!=false}">
			<!-- 关联配置 -->
			<ui:content
				expand="true"
				title="${lfn:message('kms-knowledge:kms.knowledge.relation') }">
				<c:import url="/sys/relation/import/sysRelationMain_view_new.jsp" charEncoding="UTF-8">
					<c:param name="layout" value="sys.ui.accordionpanel.simpletitle"/>
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
					<c:param name="fdCategoryId" value="${kmsMultidocKnowledgeForm.docCategoryId }" />
					<c:param name="approveType" value="right" />
				</c:import>
			</ui:content>
		</c:if>
		</ui:accordionpanel>
	</c:when>
	<c:otherwise>
		<div class="bar_tabpanel_box tabpanel_box">
		<ui:tabpanel layout="sys.ui.tabpanel.sucktop" var-average='false' var-useMaxWidth='true'>
			<c:if test="${ kmsMultidocKnowledgeForm.docStatus != '25' }">
				<%-- 流程 --%>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdKey" value="mainDoc" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import>
				<!-- 审批记录 -->
				<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId}" />
					<c:param name="fdModelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
				</c:import>
			</c:if>
			<!-- 基本信息-->
			<c:import url="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_view_baseinfo.jsp" charEncoding="UTF-8">
				<c:param name="kmsMultidocKnowledgeTemplateName" value="${kmsMultidocKnowledgeTemplateName }" />
				<c:param name="fdModelId" value="${kmsMultidocKnowledgeForm.fdId}" />
				<c:param name="hasPic" value="${hasPic}" />
				<c:param name="kmsCategoryEnabled" value="${kmsCategoryEnabled}" />
			</c:import>
		</ui:tabpanel>

		<ui:accordionpanel>

			<c:if test="${!hiezEnable}">
				<c:import url="/kms/common/kms_common_main/doc_cloud.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="template" value="docCategoryName:/kms/multidoc/?categoryId=!{docCategoryId}" />
				</c:import>
			</c:if>
			<c:if test="${hiezEnable}">
				<c:import url="/third/hiez/import/doc_hiez_cloud.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
					<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
				</c:import>
			</c:if>
			<!--相类似知识-->
			<ui:content
					title="${lfn:message('kms-knowledge:kms.knowledge.similar') }"
					toggle="true"
					titleicon="lui-fm-icon-simdoc-01">
				<c:if test="${!introduceKnowlege}">
					<c:import url="/sys/ftsearch/simdocAsyn.jsp" charEncoding="UTF-8">
						<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
						<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
						<c:param name="size" value="5"></c:param>
					</c:import>
				</c:if>
				<c:if test="${introduceKnowlege}">
					<c:import url="/third/hiez/import/doc_hiez_introduce.jsp" charEncoding="UTF-8">
						<c:param name="fdId" value="${kmsMultidocKnowledgeForm.fdId}"></c:param>
						<c:param name="formName" value="kmsMultidocKnowledgeForm" />
						<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"></c:param>
					</c:import>
				</c:if>
			</ui:content>
		</ui:accordionpanel>
		<c:if test="${kmsMultidocKnowledgeForm.docIsNewVersion!=false}">
			<!-- 关联配置 -->
			<c:import url="/sys/relation/import/sysRelationMain_view_new.jsp" charEncoding="UTF-8">
				<c:param name="layout" value="sys.ui.accordionpanel.simpletitle"/>
				<c:param name="formName" value="kmsMultidocKnowledgeForm" />
				<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
				<c:param name="fdCategoryId" value="${kmsMultidocKnowledgeForm.docCategoryId }" />
				<c:param name="approveType" value="right" />
				<c:param name="needTitle" value="true" />
			</c:import>
		</c:if>
		</div>
	</c:otherwise>
</c:choose>
	<c:if test="${ kmsMultidocKnowledgeForm.docStatus == '25' }">
	<div style="display: none">
		<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmsMultidocKnowledgeForm" />
			<c:param name="fdKey" value="mainDoc" />
			<c:param name="showHistoryOpers" value="true" />
			<c:param name="isExpand" value="true" />
			<c:param name="approveType" value="right" />
			<c:param name="approvePosition" value="right" />
		</c:import>
	</div>
	</c:if>
</div>
</template:replace>
</c:if>
	
	<template:replace name="bodyBottom">
		<%@ include file="/kms/multidoc/kms_multidoc_ui/kmsMultidocKnowledge_view_script.jsp"%>
		<c:import url="/sys/recycle/import/redirect.jsp">
			<c:param name="formBeanName" value="kmsMultidocKnowledgeForm"/>
			<c:param name="modelName" value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"/>
		</c:import>
	</template:replace>
</template:include>

