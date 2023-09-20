<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<template:replace name="barRight">
	<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
		<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="elecEsealInvalidForm" />
			<c:param name="fdKey" value="elecEsealInvalid" />
			<c:param name="showHistoryOpers" value="true" />
			<c:param name="isExpand" value="true" />
			<c:param name="approveType" value="right" />
			<c:param name="approvePosition" value="right" />
		</c:import>
	</ui:tabpanel>
</template:replace>
<template:replace name="content">
	<div class="lui_form_title_frame">
		<!-- 文档标题 -->
		<div class="lui_form_subject">
			协同协作Word插件配置
		</div>
		<div class="lui_form_baseinfo">
			作者:
			<span id="authorBox" class="unfoldOrRetract" onmouseover="showAuthorBox()" onmouseout="hideAuthorBox()">
				<!-- 文档作者 -->
				<a class="com_author" href="javascript:void(0) "
					ajax-href="/ekp/sys/zone/resource/zoneInfo.jsp?fdId=1708492377949409d66096d4db38c71b"
					onmouseover="if(window.LUI &amp;&amp; window.LUI.person)window.LUI.person(event,this);">郑颖玉</a>
				<!-- 外部作者 -->
				<span class="com_author" style="display: none;">
				</span>
			</span>
			<span style="margin-right: 8px;">
				2019-08-23
			</span>
			<!-- 点评 -->
			点评<span data-lui-mark="sys.evaluation.fdEvaluateCount" class="com_number">(0)</span>
			<!-- 推荐 -->
			推荐<span data-lui-mark="sys.introduce.fdIntroduceCount" class="com_number">(0)</span>
			<!-- 阅读信息 -->
			阅读信息<span data-lui-mark="sys.readlog.fdReadCount" class="com_number">(18)</span>
		</div>
		<table class="tb_simple" width="100%">
			<tr>
				<td width="15%" class="td_normal_title" valign="top">
					文档附件
				</td>
				<td width="85%">
					<input type='hidden' name='fdTimeType_7' value='7' />
					<div class="inputselectsgl"
						onclick="selectDate('fdStartDate_7',null,setCurrTimeVal,setCurrTimeVal);" style="width:20%">
						<div class="input"><input type="text" name="fdStartDate_7" value="${queryForm.fdStartDate }">
						</div>
						<div class="inputdatetime"></div>
					</div>
					<span style="position:relative;top:-7px;">
						<bean:message bundle="km-imeeting" key="kmImeetingStat.fdDateType.to" /></span>
					<div class="inputselectsgl" onclick="selectDate('fdEndDate_7',null,setCurrTimeVal,setCurrTimeVal);"
						style="width:20%">
						<div class="input"><input type="text" name="fdEndDate_7" value="${queryForm.fdEndDate }"></div>
						<div class="inputdatetime"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title" valign="top">标签</td>
				<td width="85%">
					<xform:address required="false" validators=""
						subject="${ lfn:message('kms-multidoc:kmsMultidoc.form.main.docDeptId') }" style="width:95%"
						propertyId="docDeptId" propertyName="docDeptName" orgType='ORG_TYPE_ORGORDEPT'>
					</xform:address>
					<div class="inputselectsgl" onclick="modifySecondCate(true);" style="width:96%"><input
							name="docSecondCategoriesIds" value="16c2e033f064574c18a14c04943a1342" type="hidden">
						<div class="input"><input name="docSecondCategoriesNames" value="江远涛" type="text" readonly="">
						</div>
						<div class="selectitem"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title" valign="top">所属部门</td>
				<td width="85%">
					<div class="inputselectsgl" onclick="modifySecondCate(true);" style="width:96%"><input
							name="docSecondCategoriesIds" value="16c2e033f064574c18a14c04943a1342" type="hidden">
						<div class="input"><input name="docSecondCategoriesNames" value="江远涛" type="text" readonly="">
						</div>
						<div class="orgelement"></div>
					</div>
				</td>
			</tr>

			</tr>
		</table>
		<ui:tabpage suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true">
			<ui:content title="基本信息">
				<div style="height: 200px;">基本信息</div>
			</ui:content>
			<ui:content title="基本信息">
				<div style="height: 200px;">基本信息</div>
			</ui:content>
			<ui:content title="基本信息">
				<div style="height: 200px;">基本信息</div>
			</ui:content>
			<ui:content title="基本信息">
				<div style="height: 200px;">基本信息</div>
			</ui:content>
			<ui:content title="基本信息">
				<div style="height: 200px;">基本信息</div>
			</ui:content>
			<ui:content title="基本信息">
				<div style="height: 200px;">基本信息</div>
			</ui:content>
			<ui:content title="基本信息">
				<div style="height: 200px;">基本信息</div>
			</ui:content>
		</ui:tabpage>

	</div>
</template:replace>