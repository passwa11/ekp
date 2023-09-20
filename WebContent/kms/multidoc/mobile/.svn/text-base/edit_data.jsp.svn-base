<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>
<c:set var="tiny" value="true" scope="request" />
<c:set var="kmsMultidocTemplateDocCategory" value="${lfn:message('kms-multidoc:kmsMultidocTemplate.docCategory')}"></c:set>
<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
		String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled)) {
	%>
		<c:set var="kmsCategoryEnabled" value="true"></c:set>
		<c:set var="kmsMultidocTemplateDocCategory" value="${lfn:message('kms-multidoc:kmsMultidocTemplate.docCategory.categoryTrue')}"></c:set>
	<%
		}
	%>
</c:if>
<ui:ajaxtext>
	<%-- 此处为标题 --%>
	<div data-dojo-block="title">
			<c:out value="${kmsMultidocKnowledgeForm.docCategoryName }"></c:out>
	</div>
	<%-- 此处为内容 --%>
	<div data-dojo-block="content">
		<html:form action="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=save">
			<div>
				<div data-dojo-type="mui/fixed/Fixed" data-dojo-props="fixedOrder:1" class="muiFlowEditFixed">
					<div data-dojo-type="mui/fixed/FixedItem">
						<div  data-dojo-type="mui/nav/NavBarStore" data-dojo-mixins="mui/nav/NavBarStepMixin,kms/multidoc/mobile/js/MultidocNavMixin" id="_flowNav"></div>
					</div>
				</div>
				<div data-dojo-type="mui/view/DocScrollableView"
					data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" id="scrollView">
					<div class="muiFlowInfoW muiFormContent">
					<div data-dojo-type="mui/panel/NavPanel" data-dojo-props="fixedOrder:2">
						<div data-dojo-type="mui/panel/Content"
							data-dojo-props="title:'${lfn:message('kms-multidoc:kmsMultidocKnowledge.docInfo')}'">
							<div class="muiFormContent">
								<html:hidden property="fdId" />
								<input type="hidden" name="cateId" value="${param.fdTemplateId}"/>
								<html:hidden property="fdModelId" />
								<html:hidden property="fdModelName" />
								<html:hidden property="docStatus" value="20" />
								<html:hidden property="docIsIndexTop" value="false" />
								<table class="muiSimple" cellpadding="0" cellspacing="0">
									<tr>
										<td class="muiTitle">${lfn:message('kms-multidoc:kmsMultidocKnowledge.docSubject') }</td>
										<td><xform:text property="docSubject" mobile="true" /></td>
									</tr>

									<c:if test="${!kmsCategoryEnabled}">
										<tr style="height: 3rem">
											<td class="muiTitle">${lfn:message('kms-multidoc:kmsMultidoc.kmsMultidocKnowledge.docProperties') }</td>
											<td>
												<div style="height:3rem;line-height:3rem;width:100%;"
													data-dojo-type="kms/multidoc/mobile/js/secCategory"
													data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"
													data-dojo-props="modelName:'com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory',
																	isMul:true,icon1:''"></div>
											</td>
										</tr>
									</c:if>
									<tr>
										<td width="15%" class="muiTitle">
											<bean:message bundle="kms-multidoc" key="kmsMultidoc.authorType" />
										</td>
										<td width="85%">
											<xform:radio mobile="true" property="authorType" onValueChange="changeAuthorType" value="${not empty kmsMultidocKnowledgeForm.fdDocAuthorList?1:2}">
												<xform:enumsDataSource enumsType="kmsKnowledgeAuthorType">
												</xform:enumsDataSource>
											</xform:radio>
										</td>
									</tr>
									<tr>
										<td width="15%" class="muiTitle">
											<bean:message bundle="sys-doc" key="sysDocBaseInfo.docAuthor" />
										</td>
										<!-- 内部作者 -->
										<td width="100%" id="innerAuthor" <c:if test="${empty kmsMultidocKnowledgeForm.fdDocAuthorList }">style="display: none;"</c:if> >
											<c:if test="${empty kmsMultidocKnowledgeForm.fdDocAuthorList }">
												<xform:address mobile="true" mulSelect="true" onValueChange="changeAuthodInfo" isLoadDataDict="false" style="width:100%" propertyId="docAuthorId" propertyName="docAuthorName" orgType='ORG_TYPE_PERSON'  subject="${lfn:message('sys-doc:sysDocBaseInfo.docAuthor') }" ></xform:address>
											</c:if>
											<c:if test="${not empty kmsMultidocKnowledgeForm.fdDocAuthorList }">
												<xform:address
												    mulSelect="true"
													required="true"
													isLoadDataDict="false"
													style="width:100%"
													propertyId="docAuthorId"
													propertyName="docAuthorName"
													orgType='ORG_TYPE_PERSON'
													mobile="true"
													onValueChange="changeAuthodInfo"
													subject="${lfn:message('sys-doc:sysDocBaseInfo.docAuthor') }"
													 />
											</c:if>
										</td>
										<!-- 外部作者 -->
										<td width="100%" id="outerAuthor" <c:if test="${not empty kmsMultidocKnowledgeForm.fdDocAuthorList }">style="display: none;position:relative"</c:if>>
											<c:if test="${not empty kmsMultidocKnowledgeForm.fdDocAuthorList }">
												<xform:text mobile="true" property="outerAuthor" style="width:100%" subject="${lfn:message('sys-doc:sysDocBaseInfo.docAuthor') }"></xform:text>
											</c:if>
											<c:if test="${empty kmsMultidocKnowledgeForm.fdDocAuthorList }">
												<xform:text mobile="true" property="outerAuthor"  validators="checkName maxLength(200)" required="true" style="width:100%" subject="${lfn:message('sys-doc:sysDocBaseInfo.docAuthor') }"></xform:text>
											</c:if>
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="sys-doc" key="sysDocBaseInfo.docDept" />
										</td>
										<td width="85%">
											<xform:address required="false" validators=""
												style="width:100%" propertyId="docDeptId"
												 propertyName="docDeptName" mobile="true" orgType='ORG_TYPE_ORGORDEPT'></xform:address>
										</td>
									</tr>
									<tr>
										<td width="15%" class="td_normal_title">
											<bean:message bundle="kms-multidoc" key="table.kmsMultidocMainPost" />
										</td>
											<td width="85%">
											<xform:address
												required="false"
												style="width:100%"
												propertyId="docPostsIds"
												propertyName="docPostsNames"
												mulSelect="true"
												mobile="true"
												orgType='ORG_TYPE_POST'>
											</xform:address>
										</td>
									</tr>
									<c:import url="/sys/tag/mobile/import/sysTagMain_edit_pda.jsp" charEncoding="UTF-8">
										<c:param name="formName" value="kmsMultidocKnowledgeForm" />
										<c:param name="fdKey" value="mainDoc" />
										<c:param name="showTagInfo" value="true"></c:param>
										<c:param name="fdQueryCondition" value="docCategoryId;docDeptId" />
									</c:import>
								</table>
								<c:import url="/sys/right/mobile/edit_hidden.jsp"
									charEncoding="UTF-8">
									<c:param name="formName" value="kmsMultidocKnowledgeForm" />
									<c:param name="moduleModelName"
										value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
								</c:import>
							</div>
						</div>
						<div data-dojo-type="mui/panel/Content"
							data-dojo-props="title:'${lfn:message('kms-multidoc:kmsMultidoc.search.docContent')}'">
							<div class="muiFormContent">
								<table class="muiSimple" cellpadding="0" cellspacing="0">
								<xform:config orient="vertical" >
									<tr>
										<td colspan="3"><xform:textarea property="fdDescription"
												validators="maxLength(1500)" mobile="true" /></td>

									</tr>
								</xform:config>
									<tr>
										<td colspan="3">
											<div class="muiFormEleTip"><span class="muiFormEleTitle">正文</span></div>
											<div><xform:rtf property="docContent" mobile="true"></xform:rtf></div>
										</td>
									</tr>
									<tr>
										<td class="muiTitle">
							              	附件上传
							            </td>
										<td colspan="2"><c:import
												url="/sys/attachment/mobile/import/edit.jsp"
												charEncoding="UTF-8">
												<c:param name="formName" value="kmsMultidocKnowledgeForm"></c:param>
												<c:param name="fdKey" value="attachment" />
												<c:param name="align" value="right" />
												<c:param name="extParam"
													value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
											</c:import></td>
									</tr>
									<tr>
									  	<td class="muiTitle">
											封面上传
										</td>
										<td colspan="2">
											<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
												<c:param name="formName" value="kmsMultidocKnowledgeForm"></c:param>
												<c:param name="fdKey" value="spic"></c:param>
												<c:param name="align" value="right"></c:param>
												<c:param name="fdAttType" value="pic" />
												<c:param name="fdMulti" value="false"></c:param>
												<c:param name="extParam"
													value="{'thumb':[{'name':'s1','w':'800','h':'800'},{'name':'s2','w':'2250','h':'1695'}]}" />
											</c:import>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<c:if test="${kms_professional}">
							<div data-dojo-type="mui/panel/Content"
								data-dojo-props="title:'${lfn:message('kms-multidoc:kmsMultidocKnowledge.fdProperty') }'">

								<div class="muiFormContent">

									<table class="muiSimple" cellpadding="0" cellspacing="0">
										<c:import url="/sys/property/include/sysProperty_pda.jsp"
											charEncoding="UTF-8">
											<c:param name="formName" value="kmsMultidocKnowledgeForm" />
										</c:import>
									</table>

								</div>
							</div>
						</c:if>
					</div>
					</div>
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom"
						data-dojo-props='fill:"grid"'>
						<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext "
							data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>${lfn:message('kms-multidoc:kmsMultidoc.4m.next') }</li>
					</ul>
				</div>

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

				<c:import url="/sys/lbpmservice/mobile/import/edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="kmsMultidocKnowledgeForm" />
					<c:param name="fdKey" value="mainDoc" />
					<c:param name="viewName" value="lbpmView" />
					<c:param name="backTo" value="scrollView" />
					<c:param name="onClickSubmitButton" value="knowledge_submit();" />
				</c:import>


			</div>

			<c:if test="${empty param.fdTemplateId}">
				<!--  无分类下默认弹开分类选择框 -->
				<script>
				require([ "kms/multidoc/mobile/js/multidocCategory"]);
				</script>
			</c:if>
			<script type="text/javascript">
			var multidocEdit = null;
			require(["kms/multidoc/mobile/js/mui-multidoc-edit"], function(edit) {
				var edit = new edit();
				edit.init();
				multidocEdit = edit;
			});
			window.changeAuthodInfo = function(value, e) {
				if(multidocEdit) {
					multidocEdit.changeAuthodInfo(value, e);
				}
			}
			require([ "mui/form/ajax-form!kmsMultidocKnowledgeForm"]);
			</script>
		</html:form>
	</div>
</ui:ajaxtext>