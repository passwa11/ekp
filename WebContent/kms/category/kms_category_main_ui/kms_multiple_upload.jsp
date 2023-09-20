<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<script type="text/javascript">
	function openCategorySelectDialog() {

		var fdKey = '${param.fdKey}';
		var fdTitle = '${param.pathTitle}';
		var fdCategoryModelName = '${param.fdCategoryModelName}';
		var categoryIndicateName = '${param.categoryIndicateName}';
		var fdModelName = '${param.fdMainModelName}';
		seajs
				.use(
						[ 'lui/dialog', 'lui/spa/Spa' ],
						function(dialog, Spa) {

							dialog
									.simpleCategory(
											'${param.fdCategoryModelName}',null,
											null,
											false,
											function(param) {

												if (param == null) {
													return;
												}
												var idStr = param.id;
												//点击取消
												if (idStr == undefined) {
													return;
												}
												dialog
														.iframe(
																"/kms/knowledge/kms_knowledge_multiple_upload/kmsMultipleUploadMain.do?method=forwordEditor&title="
																		+ fdTitle
																		+ "&categoryIndicateName="
																		+ categoryIndicateName
																		+ "&fdCategoryModelName="
																		+ fdCategoryModelName
																		+ "&fdModelName="
																		+ fdModelName
																		+ "&fdKey="
																		+ fdKey
																		+ "&cateId="
																		+ idStr,
																"${lfn:message('kms-knowledge:kmsKnowledge.button.batchImport') }",
																null,
																{
																	width : 990,
																	height : 500
																});
											},
											"${lfn:message('kms-knowledge:kmsKnowledge.selectKnowledgeCategory.categoryTrue')}",
											true, {
												'fdTemplateType' : '1,3'
											});
						});
	}
</script>
<ui:button
	text="${lfn:message('kms-knowledge:kmsKnowledge.button.batchImport') }"
	onclick="openCategorySelectDialog();" order="2" cfg-if="criteria('template')==1"></ui:button>

