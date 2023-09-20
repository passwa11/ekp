<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils"%>
<%@ page import="com.landray.kmss.kms.common.util.KmsBorrowCategoryUtil" %>
<%@ page import="com.landray.kmss.kms.knowledge.borrow.util.KmsKnowledgeBorrowUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	// 回填所选分类的名称
	String fdCateIds = (String)TagUtils.getFieldValue(request, "value(kms.knowledge.borrow.categoryIdList)");
	String fdCateNames = KmsBorrowCategoryUtil.queryCateNameByIds(fdCateIds, "KmsKnowledgeCategory");
	request.setAttribute("fdCateNames", fdCateNames);

	String fdAttAuthCateIds = (String)TagUtils.getFieldValue(request, "value(kms.knowledge.borrow.attAuthCategoryIdList)");
	String fdAttAuthCateNames = KmsBorrowCategoryUtil.queryCateNameByIds(fdAttAuthCateIds, "KmsKnowledgeCategory");
	request.setAttribute("fdAttAuthCateNames", fdAttAuthCateNames);
%>
<template:include ref="config.profile.edit" sidebar="no">

	<template:replace name="title">
		${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.setting.set')}
	</template:replace>

	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">

			<span style="color: #35a1d0;">
				${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.setting.set')}
			</span>

		</h2>

		<html:form action="/kms/knowledge/borrow/config/KmsKnowledgeBorrowConfig.do">
			<center>

				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.setting.borrowEnabled')}</td>
						<td>
							<ui:switch property="value(kms.knowledge.borrow.fdBorrowEnabled)"
								checkVal="1" 
								unCheckVal="0"
                                onValueChange="configBorrowEnabledChange(this);"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}">
							</ui:switch>
							<span class="message"> <font color="red">${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.setting.borrowEnabled.desc')}</font></span>
                        </td>
					</tr>
                    <tr id="trBorrowSpan" style="<%=KmsKnowledgeBorrowUtil.borrowEnabled() ? "" : "display: none" %>">
                        <td class="td_normal_title" width=15%>${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.setting.categoryList')}</td>
                        <td colspan=3>
                            <%-- 模板--%>
                            <xform:text property="value(kms.knowledge.borrow.categoryIdList)" showStatus="noShow"/>
                            <div id="_xform_fdTemplateId" _xform_type="dialog">
                                <xform:dialog propertyId="categoryNameList" propertyName="categoryNameListStr" nameValue="${fdCateNames }" showStatus="edit" style="width:95%;">
                                    modifyCategoryAndTemplate();
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
					<tr id="trBorrowAttAuthSpan" style="<%=KmsKnowledgeBorrowUtil.borrowEnabled() ? "" : "display: none" %>">
						<td class="td_normal_title" width=15%>${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.setting.attAuthCategoryList')}</td>
						<td colspan=3>
								<%-- 模板--%>
							<xform:text property="value(kms.knowledge.borrow.attAuthCategoryIdList)" showStatus="noShow"/>
							<div id="_xform_docAttAuthCategoryId" _xform_type="dialog">
								<xform:dialog propertyId="attAuthCategoryNameList" propertyName="attAuthCategoryNameListStr" nameValue="${fdAttAuthCateNames }" showStatus="edit" style="width:95%;">
									modifyAttAuthCategoryAndTemplate();
								</xform:dialog>
							</div>
						</td>
					</tr>
				</table>
			</center>

			<html:hidden property="method_GET" />

			<input type="hidden" name="modelName"
				value="com.landray.kmss.kms.knowledge.borrow.config.KmsKnowledgeBorrowConfig" />

			<center style="margin-top: 10px;">
				<ui:button text="${lfn:message('button.save')}" height="35"
					width="120"
					onclick="submitForm();"></ui:button>
			</center>
		</html:form>
		
		<script>
			window.submitForm = function(){
			    // 触发页面刷新
	               try {
	                   if (window.opener != null) {
	                       try {
	                           if (window.opener.LUI) {
	                               window.opener.LUI
	                                   .fire({
	                                       type : "topic",
	                                       name : "successReloadPage"
	                                   });
	                           }
	                       } catch (e) {
	                           console.log(e)
	                       }
	                   }
	               } catch (e) {
	                   console.log(e)
	               }
	       		Com_Submit(document.sysAppConfigForm, 'update');
	        }

            window.modifyCategoryAndTemplate = function () {
                seajs.use(['lui/dialog', 'lui/jquery'], function (dialog, $) {
                    var templateId = $("input[name='value(kms.knowledge.borrow.categoryIdList)']").val();
                    // alert("templateId=>" + templateId)
                    if (templateId && templateId !== "") {
                        dialog.simpleCategoryForNewFile({
                            idVal: 'categoryNameList',
                            modelName: "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
                            mulSelect: true,
                            action: function MyAction(rtnVal) {
                                if (rtnVal == null)
                                    return;
                                var ids = rtnVal.id;
                                var names = rtnVal.name;
                                names = decodeURI(names);
                                if (ids == null)
                                    return;
                                $("input[name='value(kms.knowledge.borrow.categoryIdList)']").val(ids)
                                $("input[name='categoryNameListStr']").val(decodeURIComponent(names))
                            },
                            winTitle: null,
                            canClose: true,
                            ___urlParam: null,
                            url: "/kms/knowledge/kms_knowledge_category/simple-category.jsp",
                            notNull: false
                        }, "", false, null, "", templateId, null, true);
                    } else {
                        dialog.simpleCategory({
                            modelName: "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
                            idField: "categoryNameList",
                            nameField: "categoryNameListStr",
                            mulSelect: true,
                            action: function (params) {
                                // var idList = $("input[name='value(kms.knowledge.borrow.categoryIdList)']").val();
                                // alert(idList)
                                // console.log(params)
                                $("input[name='value(kms.knowledge.borrow.categoryIdList)']").val(params.id)
                                $("input[name='categoryNameListStr']").val(params.name)
                            },
                            winTitle: null,
                            canClose: null,
                            notNull: false
                        });
                    }
                });
            }

			window.modifyAttAuthCategoryAndTemplate = function () {
				seajs.use(['lui/dialog', 'lui/jquery'], function (dialog, $) {
					var templateId = $("input[name='value(kms.knowledge.borrow.attAuthCategoryIdList)']").val();
					// alert("templateId=>" + templateId)
					if (templateId && templateId !== "") {
						dialog.simpleCategoryForNewFile({
							idVal: 'attAuthCategoryNameList',
							modelName: "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
							mulSelect: true,
							action: function MyAction(rtnVal) {
								if (rtnVal == null)
									return;
								var ids = rtnVal.id;
								var names = rtnVal.name;
								names = decodeURI(names);
								if (ids == null)
									return;
								$("input[name='value(kms.knowledge.borrow.attAuthCategoryIdList)']").val(ids)
								$("input[name='attAuthCategoryNameListStr']").val(decodeURIComponent(names))
							},
							winTitle: null,
							canClose: true,
							___urlParam: null,
							url: "/kms/knowledge/kms_knowledge_category/simple-category.jsp",
							notNull: false
						}, "", false, null, "", templateId, null, true);
					} else {
						dialog.simpleCategory({
							modelName: "com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory",
							idField: "attAuthCategoryNameList",
							nameField: "attAuthCategoryNameListStr",
							mulSelect: true,
							action: function (params) {
								$("input[name='value(kms.knowledge.borrow.attAuthCategoryIdList)']").val(params.id)
								$("input[name='attAuthCategoryNameListStr']").val(params.name)
							},
							winTitle: null,
							canClose: null,
							notNull: false
						});
					}
				});
			}

            window.configBorrowEnabledChange = function (obj) {
                var flag = $("input[name='value(kms.knowledge.borrow.fdBorrowEnabled)']").val();
                if (flag == 0) {
                    $("#trBorrowSpan").hide();
					$("#trBorrowAttAuthSpan").hide();
                } else {
                    $("#trBorrowSpan").show();
					$("#trBorrowAttAuthSpan").show();
                }
            }
		</script>
	</template:replace>
</template:include>