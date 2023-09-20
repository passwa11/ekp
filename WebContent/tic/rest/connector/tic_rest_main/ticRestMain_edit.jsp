<%@page import="com.landray.kmss.tic.rest.connector.forms.*"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="com.landray.kmss.tic.core.register.RegisterPlugin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<% 
    	pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); 
    	request.setAttribute("registers", RegisterPlugin.getExtensionArray());
    	
    	String fdAppType = request.getParameter("fdAppType");
    	if(StringUtil.isNull(fdAppType)){
    		TicRestMainForm restForm = (TicRestMainForm)request.getAttribute("ticRestMainForm");
    		if(restForm!=null){
    			fdAppType = restForm.getFdAppType();
    		}
    	}
    	
    	String whereBlock="ticRestSetting.fdAppType='"+fdAppType+"'";
    	if(StringUtil.isNotNull(request.getParameter("fdEnviromentId"))){
    		whereBlock=whereBlock+" and ticRestSetting.fdEnviromentId="+ "'"+request.getParameter("fdEnviromentId")+ "'";
    	}
    	request.setAttribute("whereBlock", whereBlock);
    	request.setAttribute("thisFdAppType", fdAppType);
    %>

<template:include ref="default.edit">
	<template:replace name="head">
		<link
			href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css"
			rel="stylesheet" type="text/css" />
		<script
			src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js"
			type="text/javascript"></script>

		<script type="text/javascript">
			Com_IncludeFile("jquery.js|data.js|json2.js");
			Com_IncludeFile("doclist.js|dialog.js", null, "js");
			Com_IncludeFile("base64.js");
			Com_IncludeFile("json2.js");
		</script>
		<style>
.lui_tabhead {
	list-style: none;
}

.lui_tabhead>li {
	padding: 5px 12px;
	float: left;
	position: relative;
	border: 1px solid;
}

.lui_tabhead>li.active {
	background-color: #4285f4;
	color: #fff;
}

.detail {
	margin: 5px 0;
}
</style>
	</template:replace>

	<template:replace name="title">
		<c:choose>
			<c:when test="${ticRestMainForm.method_GET == 'add' }">
				<c:out
					value="${ lfn:message('operation.create') } - ${ lfn:message('tic-rest-connector:table.ticRestMain') }" />
			</c:when>
			<c:otherwise>
				<c:out value="${ticRestMainForm.fdName} - " />
				<c:out
					value="${ lfn:message('tic-rest-connector:table.ticRestMain') }" />
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<ui:button text="${lfn:message('home.help')}" order="1"
				onclick="Com_OpenWindow(Com_Parameter.ContextPath+'tic/rest/help/main_setting.html','_blank');">
			</ui:button>
			<c:choose>
				<c:when test="${ticRestMainForm.method_GET=='edit'}">
					<ui:button text="${lfn:message('tic-core-common:ticCoreCommon.funcTest')}" order="2"
						onclick="Com_OpenWindow('ticRestMain.do?method=viewQueryEdit&funcId=${param.fdId}','_blank');">
					</ui:button>
					<ui:button text="${ lfn:message('button.update') }" order="2"
						onclick="doSubmit('update');">
					</ui:button>
				</c:when>
				<c:when test="${ticRestMainForm.method_GET=='add'}">
					<ui:button text="${ lfn:message('button.save') }" order="2"
						onclick="doSubmit('save');">
					</ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" order="2"
				onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home" />
			<ui:menu-item
				text="${ lfn:message('tic-rest-connector:table.ticRestMain') }" />
		</ui:menu>
	</template:replace>
	<template:replace name="content">

		<html:form action="/tic/rest/connector/tic_rest_main/ticRestMain.do">

			<p class="txttitle">
				<bean:message bundle="tic-rest-connector" key="ticRestMain.manager" />
			</p>
			<table class="tb_normal" id="Label_Tabel" width=95%>
				<tr LKS_LabelName="${lfn:message('tic-rest-connector:table.ticRestMain.main')}">
					<td>
						<center>
							<table class="tb_normal" width=95%>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message
											bundle="tic-rest-connector" key="ticRestMain.docSubject" /></td>
									<td width="35%"><xform:text property="fdName"
											style="width:85%" required="true" /></td>
									<td class="td_normal_title" width="15%"><bean:message
											bundle="tic-rest-connector" key="ticRestMain.fdServerSetting" />
									</td>
									<td width="35%"><xform:select property="ticRestSettingId"
											style="float: left;" showStatus="edit"
											value="${param.ticRestSettingId }" required="true">
											 <xform:beanDataSource serviceBean="ticRestSettingService"
												selectBlock="fdId,docSubject"
												whereBlock="${whereBlock}"
												orderBy="" />
										</xform:select></td>
								</tr>

								<tr>
									<td class="td_normal_title" width="15%"><bean:message
											bundle="tic-rest-connector" key="ticRestMain.docCategory" />
									</td>
									<td width="35%"><xform:dialog
											subject="${lfn:message('tic-rest-connector:ticRestMain.docCategory') }"
											propertyId="fdCategoryId" style="width:90%"
											propertyName="fdCategoryName" dialogJs="Tic_treeDialog()">
										</xform:dialog></td>
									<td class="td_normal_title" width="15%"><bean:message
											bundle="tic-rest-connector" key="ticRestMain.fdIsAvailable" />
									</td>
									<td width="35%"><xform:radio property="fdIsAvailable">
											<xform:enumsDataSource enumsType="rest_yesno" />
										</xform:radio></td>
								</tr>
								<tr>
									<td class="td_normal_title" width=15%><bean:message
											bundle="tic-core-common" key="ticCoreFuncBase.fdKey" /></td>
									<td width="35%" colspan="3"><xform:text property="fdKey"
											required="true" style="width:35%"
											htmlElementProperties="ONBLUR=\"key_unique_Submit()\""></xform:text>
										<div id="key_error" style="display: inline-block; color: red;"></div>
									</td>
								</tr>
								<tr>
									<td colspan="4" class="com_subject"
										style="font-size: 110%; font-weight: bold;">${lfn:message('tic-rest-connector:ticRestMain.funcOAuthAuthorizeSet')}</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message
											bundle="tic-rest-connector" key="ticRestMain.fdOauthEnable" />
									</td>
									<td width="85%" colspan="3"><xform:radio
											property="fdOauthEnable" onValueChange="changeOauthEnable();">
											<xform:enumsDataSource enumsType="rest_yesno" />
										</xform:radio>
										<div id="openOauth" style="display: inline">
											<span style="color: #47b5ea; cursor: pointer;"
												onclick="addOauth();">${lfn:message('tic-rest-connector:ticRestMain.addAuthorize')}</span><span
												style="color: #47b5ea; cursor: pointer;"
												onclick="chooseOauth();">${lfn:message('tic-rest-connector:ticRestMain.selectAuthorize')}</span>
										</div></td>
								</tr>
								<tr id="explain">
									<td class="td_normal_title" width="15%">${lfn:message('tic-core-common:ticCoreCommon.description')}</td>
									<td width="85%" colspan="3"><bean:message
											bundle="tic-rest-connector"
											key="ticRestMain.fdOauthEnable.note" /><br> ${lfn:message('tic-core-common:ticCoreCommon.example')}
										https://java.landray.com.cn/km/getList.do?access_token={ACCESSTOKEN}
									</td>
								</tr>
								<tr>
									<td colspan="4" class="com_subject"
										style="font-size: 110%; font-weight: bold;">${lfn:message('tic-rest-connector:ticRestMain.cookieSessionKeepSet')}
									</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%">${lfn:message('tic-rest-connector:ticRestMain.isEnableCookieSessionKeep')}</td>
									<td width="85%" colspan="3"><xform:radio
											property="fdCookieEnable"
											onValueChange="changeCookieEnable();">
											<xform:enumsDataSource enumsType="rest_yesno" />
										</xform:radio>
										<div id="openCookie" style="display: inline">
											<span style="color: #47b5ea; cursor: pointer;"
												onclick="addCookieSetting();">${lfn:message('tic-rest-connector:ticRestMain.addCookieSetting')}</span><span
												style="color: #47b5ea; cursor: pointer;"
												onclick="chooseCookieSetting();">${lfn:message('tic-rest-connector:ticRestMain.selectCookieSetting')}</span>
										</div></td>
								</tr>
								<tr id="explain_cookie">
									<td class="td_normal_title" width="15%">${lfn:message('tic-core-common:ticCoreCommon.description')}</td>
									<td width="85%" colspan="3">${lfn:message('tic-rest-connector:ticRestMain.K3.cookieSetting.note')}
									</td>
								<tr>

									<td colspan="4" class="com_subject"
										style="font-size: 110%; font-weight: bold;">${lfn:message('tic-rest-connector:ticRestMain.preRequest')}</td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%">${lfn:message('tic-rest-connector:ticRestMain.isEnablePreRequest')}</td>
									<td width="85%" colspan="3"><xform:radio
											property="fdPrefixReqEnable"
											onValueChange="changePrefixReqEnable();">
											<xform:enumsDataSource enumsType="rest_yesno" />
										</xform:radio>
										<div id="openPrefixReq" style="display: inline">
											<span style="color: #47b5ea; cursor: pointer;"
												onclick="addPrefixReqSetting();">${lfn:message('tic-rest-connector:ticRestMain.addPreRequestSetting')}</span><span
												style="color: #47b5ea; cursor: pointer;"
												onclick="choosePrefixReqSetting();">${lfn:message('tic-rest-connector:ticRestMain.selectPreRequestSetting')}</span>
										</div></td>
								</tr>
								<tr id="explain_prefixReq">
									<td class="td_normal_title" width="15%">${lfn:message('tic-core-common:ticCoreCommon.description')}</td>
									<td width="85%" colspan="3">${lfn:message('tic-rest-connector:ticRestMain.K3.preRequestSetting.note')}
									</td>
								<tr>
								<tr>
									<td colspan="4" class="com_subject"
										style="font-size: 110%; font-weight: bold;"><bean:message
											bundle="tic-rest-connector" key="ticRestMain.reqMethod.set" />
									</td>
								</tr>
								<tr>
									<td colspan="4"><bean:message bundle="tic-rest-connector"
											key="ticRestMain.fdReqMethod" /> <xform:select
											property="fdReqMethod">
											<xform:enumsDataSource enumsType="rest_fdReqMethod_values" />
										</xform:select> <bean:message bundle="tic-rest-connector"
											key="ticRestMain.fdReqURL" /> <xform:text
											property="fdReqURL" style="width:65%" /> <input
										type="button" class="btnopt" value="${lfn:message('tic-core-common:ticCoreCommon.extractParam')}"
										onclick="loadURLParam();"></td>
								</tr>
								<tr id="func_tab" style="border-bottom: none;">
									<td colspan="4" style="border-bottom: none;">
										<div class="lui_tab_heading">
											<ul class="lui_tabhead">
												<li name="lui_head_tab" class="active"
													onclick="setFunc_param(this,'func_url');"><a
													href="javascript:void(0)">${lfn:message('tic-rest-connector:ticRestSetting.urlParam')}</a></li>
												<li name="lui_head_tab"
													onclick="setFunc_param(this,'func_header');"><a
													href="javascript:void(0)">${lfn:message('tic-rest-connector:ticRestSetting.headerParam')}</a></li>
												<li name="lui_head_tab"
													onclick="setFunc_param(this,'func_cookie');"><a
													href="javascript:void(0)">${lfn:message('tic-rest-connector:ticRestMain.cookieSetting')}</a></li>
												<li name="lui_head_tab"
													onclick="setFunc_param(this,'func_body');"><a
													href="javascript:void(0)">${lfn:message('tic-rest-connector:ticRestSetting.bodyParam')}</a></li>
												<li name="lui_head_tab"
													onclick="setFunc_param(this,'func_return');"><a
													href="javascript:void(0)">${lfn:message('tic-core-common:ticCoreCommon.returnParam')}</a></li>
											</ul>
										</div>
									</td>
								</tr>
								<tr style="border-top: none;">
									<td colspan="4" id="func_table" style="border-top: none;">
										<table class="tb_normal" id="func_url" width=100%>
											<tr>
												<td class="td_normal_title" width="40%">${lfn:message('tic-core-common:ticCoreTransSett.paramName')}</td>
												<td class="td_normal_title" width="50%">${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}</td>
												<td class="td_normal_title" width="10%" align='center'>
													<img src="${KMSS_Parameter_StylePath}icons/add.gif"
													title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addRow('func_url');" />
												</td>
											</tr>
										</table>
										<table class="tb_normal" id="func_header" width=100%
											style="display: none">
											<tr>
												<td colspan="4">
													<div class="detail">
														${lfn:message('tic-rest-connector:ticRestSetting.fastDefineHeaderParam')}：
														<xform:checkbox property="fdReqHeader"
															onValueChange="headerSelect(this)">
															<xform:enumsDataSource enumsType="rest_fdReqHeader" />
														</xform:checkbox>
													</div>
												</td>
											</tr>
											<tr>
												<td class="td_normal_title" width="40%">${lfn:message('tic-core-common:ticCoreTransSett.paramName')}</td>
												<td class="td_normal_title" width="50%">${lfn:message('tic-core-common:ticCoreCommon.fieldValue')}</td>
												<td class="td_normal_title" width="10%" align='center'>
													<img src="${KMSS_Parameter_StylePath}icons/add.gif"
													title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addRow('func_header');" />

												</td>
											</tr>
										</table>

										<table class="tb_normal" id="func_cookie" width=100%
											style="display: none">

											<tr>
												<td class="td_normal_title" width="40%">${lfn:message('tic-rest-connector:ticRestMain.cookieName')}</td>
												<td class="td_normal_title" width="50%">${lfn:message('tic-rest-connector:ticRestMain.cookieValue')}</td>
												<td class="td_normal_title" width="10%" align='center'>
													<img src="${KMSS_Parameter_StylePath}icons/add.gif"
													title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addRow('func_cookie');" />
												</td>
											</tr>
										</table>

										<table class="tb_normal" id="func_body" width=100%
											style="display: none">
											<tr>
												<td width=40% valign="top"> 
													<xform:textarea
														property="bodyJson" style="width:95%;height:160px;"></xform:textarea>
												
												<input type="button" class="btnopt"
													value="抽取XML(去除命名空间)" onclick="loadJSON('bodyJson','body_table','true');"
													style="float: right; margin: 5px 20px;">
												<input type="button" class="btnopt"
													value="${lfn:message('tic-core-common:ticCoreTransSett.extractJSON')}" onclick="loadJSON('bodyJson','body_table','false');"
													style="float: right; margin: 5px 20px;">
													
												</td>
												<td width=60% style="vertical-align: top;">
													<table class="tb_normal" width=100% id="body_table">
														<tr>
															<td class="td_normal_title" width="35%">${lfn:message('tic-core-common:ticCoreTransSett.paramName')}</td>
															<td class="td_normal_title" width="35%">${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}</td>
															<td class="td_normal_title" width="20%">${lfn:message('tic-core-common:ticCoreTransSett.paramType')}</td>
															<td class="td_normal_title" width="10%" align='center'>
																<img src="${KMSS_Parameter_StylePath}icons/add.gif"
																title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addField('body_table');" /> <img
																src="../../../core/resource/images/recycle.png"
																title="${lfn:message('tic-core-common:ticCoreCommon.clear')}" onClick="clearField('body_table');" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
										<table class="tb_normal" id="func_return" width=100%
											style="display: none">
											<tr>
												<td width=40% valign="top"><input type="button" class="btnopt"
													value="${lfn:message('tic-core-common:ticCoreTransSett.extractJSON')}"
													onclick="loadJSON('returnJson','return_table','false');"
													style="float: right; margin: 5px 20px;"> <xform:textarea
														property="returnJson" style="width:95%"></xform:textarea>
												</td>
												<td width=60% style="vertical-align: top;">
													<table class="tb_normal" width=100% id="return_table">
														<tr>
															<td class="td_normal_title" width="35%">${lfn:message('tic-core-common:ticCoreTransSett.paramName')}</td>
															<td class="td_normal_title" width="35%">${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}</td>
															<td class="td_normal_title" width="20%">${lfn:message('tic-core-common:ticCoreTransSett.paramType')}</td>
															<td class="td_normal_title" width="10%" align='center'>
																<img src="${KMSS_Parameter_StylePath}icons/add.gif"
																title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addField('return_table');" /> <img
																src="../../../core/resource/images/recycle.png"
																title="${lfn:message('tic-core-common:ticCoreCommon.clear')}" onClick="clearField('return_table');" />
															</td>
														</tr>
													</table>
												</td>
											</tr>

										</table>
									</td>
								</tr>

								<tr>
									<td colspan="4" class="com_subject"
										style="font-size: 110%; font-weight: bold;"><bean:message
											bundle="tic-rest-connector"
											key="ticRestMain.fdReqBizParam.set" /></td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%"><bean:message
											bundle="tic-rest-connector" key="ticRestMain.fdReMark" /></td>
									<td colspan="3" width="85%"><xform:textarea
											property="fdRemark" style="width:85%" /></td>
								</tr>
								<tr>
									<td class="td_normal_title" width="15%">
										${lfn:message('tic-core-common:ticCoreFuncBase.fdInvoke')}</td>
									<td colspan="3" width="85.0%"><xform:checkbox
											property="fdInvoke"
											htmlElementProperties="on_change_invoke=\"invokeSelect(source)\"">
											<xform:enumsDataSource enumsType="tic_core_invoke" />
										</xform:checkbox> <br> <bean:message bundle="tic-core-common"
											key="ticCoreFuncBase.fdInvoke.explain" /></td>
								</tr>
								<tr id="auth_readers" style="display: none">
									<td class="td_normal_title" width="15%">
										${lfn:message('tic-core-common:ticCoreFuncBase.authReaders')}
									</td>
									<td colspan="3" width="85.0%">
										<div id="_xform_authReaderIds" _xform_type="address">
											<xform:address propertyId="authReaderIds"
												propertyName="authReaderNames" mulSelect="true"
												orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true"
												style="width:95%;" />
										</div>
									</td>
								</tr>

							</table>
						</center>
					</td>
				</tr>
				<!-- 查询历史 -->
				<c:if test="${ticRestMainForm.method_GET == 'edit' }">
					<c:import
						url="/tic/rest/connector/tic_rest_query/tic_rest_view_history.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="ticRestMainForm" />
					</c:import>
					<c:import
						url="/tic/core/common/tic_core_trans_sett/ticCore_searchInfo_view_history.jsp"
						charEncoding="UTF-8">
					</c:import>
				</c:if>
			</table>
			<input type="hidden" id="ticRestAuthId" name="ticRestAuthId"
				value="${ticRestMainForm.ticRestAuthId}">
			<input type="hidden" id="ticRestCookieSettingId"
				name="ticRestCookieSettingId"
				value="${ticRestMainForm.ticRestCookieSettingId}">
			<input type="hidden" id="ticRestPrefixReqSettingId"
				name="ticRestPrefixReqSettingId"
				value="${ticRestMainForm.ticRestPrefixReqSettingId}">
			<input id="fdReqParam" name="fdReqParam" type="hidden"></input>
			<input id="fdParaIn" name="fdParaIn" type="hidden"></input>
			<input id="fdParaOut" name="fdParaOut" type="hidden"></input>
			<input type="hidden" id="cacheParamDefine" name="cacheParamDefine"
				value="">
			<html:hidden property="fdId" />
			<html:hidden property="fdOriParaIn" />
			<html:hidden property="fdOriParaOut" />
			<%-- 
<html:hidden property="fdAppType"  value="${ticRestMainForm.fdAppType}"/>
--%>
			<html:hidden property="method_GET" />
			<script>
				DocList_Info.push("TABLE_DocList_Input");
				DocList_Info.push("TABLE_DocList_Output");
				DocList_Info.push("TABLE_DocList_Header");

				var validation = $KMSSValidation();

				//自定义校验方法
				validation
						.addValidator(
								'myAlphanum',
								'${lfn:message("sys-xform-maindata:sysFormJdbcDataSet.fdKeyWaring") }',
								function(v, e, o) {
									return this.getValidator('isEmpty').test(v)
											|| !/\W/.test(v);
								});

				function Tic_treeDialog() {
					Dialog_Tree(
							false,
							'fdCategoryId',
							'fdCategoryName',
							',',
							'ticCoreBusiCateTreeService&parentId=!{value}&fdAppType=${param.fdAppType}',
							'所属分类', null, null,
							'${ticCoreBusiCateForm.fdCategoryId}', null, null,
							'所属分类');
				}

				function doSubmit(method) {
					window.can_submit = true;
					formatPostData();
					if (document.getElementsByName("fdOauthEnable")[0].checked
							&& $("#ticRestAuthId").val() == "") {
						alert("请选择授权！");
						return;
					}
					if (document.getElementsByName("fdCookieEnable")[0].checked
							&& $("#ticRestCookieSettingId").val() == "") {
						alert("请选择cookie设置！");
						return;
					}
					if (document.getElementsByName("fdPrefixReqEnable")[0].checked
							&& $("#ticRestPrefixReqSettingId").val() == "") {
						alert("请选择前置请求设置！");
						return;
					}
					if (window.can_submit){
						key_unique_Submit(document.ticRestMainForm, method);
					}
				}

				Com_AddEventListener(window, "load", function() {
					changeOauthEnable();
					changeCookieEnable();
					changePrefixReqEnable();
					init();
				});
				function buildParaJson(tableId) {
					var paras = [];
					$("#" + tableId).find("tbody tr").each(
							function(index) {
								if (index > 0) {
									var isRootNode = ($(this)[0].className
											.search("child-of") == -1);
									if (isRootNode) {
										paras.push(buildParaField($(this)));
									}
								}
							});
					return paras;
				}
				window.can_submit;
				function buildParaField(node) {
					var name = node.find("input[name='name']").val();
					var title = node.find("input[name='title']").val();
					var type = node.find("select[name='type'] option:selected")
							.val();
					var field = {};

					field.name = name;
					field.title = title;
					field.type = type;
					var childNodes = getChildrenOf(node);
					if (node.hasClass("parent")) {
						var children = [];
						childNodes.each(function() {
							children.push(buildParaField($(this)));
						});
						field.children = children;
					}
					return field;
				}

				function getChildrenOf(node) {
					var id = node.attr("id");
					id = id.replace(new RegExp("/","g"), "\\/");
					return $(node).siblings("tr.child-of-" + id);
				};

				function changeOauthEnable() {
					if (document.getElementsByName("fdOauthEnable")[0].checked) {
						$("#openOauth").show();
						var authId = "${ticRestMainForm.ticRestAuthId}";
						if (authId) {
							var data = new KMSSData();
							var url = "ticRestLoadAuthInfoService&authId="
									+ authId;
							var data = new KMSSData();
							data.SendToBean(url, function(rtn) {
								loadAuthInfo(rtn)
							});
						}
					} else {
						$("#openOauth").hide();
						$("#authDetail").remove();
						$("#ticRestAuthId").val("");
					}
				}

				function changeCookieEnable() {
					if (document.getElementsByName("fdCookieEnable")[0].checked) {
						$("#openCookie").show();
						var settingId = "${ticRestMainForm.ticRestCookieSettingId}";
						if (settingId) {
							var data = new KMSSData();
							var url = "ticRestLoadCookieSettingService&settingId="
									+ settingId;
							var data = new KMSSData();
							data.SendToBean(url, function(rtn) {
								loadCookieSettingInfo(rtn)
							});
						}
					} else {
						$("#openCookie").hide();
						$("#cookieSettingDetail").remove();
						$("#ticRestCookieSettingId").val("");
					}
				}
				
				function changePrefixReqEnable() {
					if (document.getElementsByName("fdPrefixReqEnable")[0].checked) {
						$("#openPrefixReq").show();
						var settingId = "${ticRestMainForm.ticRestPrefixReqSettingId}";
						if (settingId) {
							var data = new KMSSData();
							var url = "ticRestLoadPrefixReqSettingService&settingId="
									+ settingId;
							var data = new KMSSData();
							data.SendToBean(url, function(rtn) {
								loadPrefixReqSettingInfo(rtn)
							});
						}
					} else {
						$("#openPrefixReq").hide();
						$("#prefixReqSettingDetail").remove();
						$("#ticRestPrefixReqSettingId").val("");
					}
				}

				function loadURLParam() {
					var url = $("input[name='fdReqURL']").val();
					if (url == '') {
						return;
					}
					if (url.indexOf("?") != -1) {
						url_main = url.split("?")[0];
						var url_params = url.split("?")[1].split("&");
						for (var i = 0; i < url_params.length; i++) {
							if (url_params[i].indexOf("ACCESSTOKEN") != -1) {
								url_main = url_main + "?" + url_params[i];
							}
							if(url_params[i].match(/\{(.+?)\}/g)){
								 var param = url_params[i].match(/\{(.+?)\}/g)[0].replace(/\{|}/g,'');
								 var paramContains = false;
								 var urlTrs = $("#func_url").find("tr");
								 for(var i=1;i<urlTrs.length;i++){
									 if($(urlTrs[i]).find("input")[0].value == param){
										 paramContains = true;
										 break;
									 } 
								 }
								 if(!paramContains){
									 var tr_in="<tr id='"+param+"'><td><input  type='text' name='name' style='width:85%' validate='required' class='inputsgl' value='"+param+"'></input></td>"
									 +"<td><input  type='text' name='title' style='width:85%' validate='required' class='inputsgl' value='"+param+"'></td>"+
									 "<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></td></tr>";
									 $("#func_url").append(tr_in);
								 }
								 
							 }
							 //$("input[name=fdReqURL]").val( url_main);
						}
					}
				}
				function setUrl_param(li, tableId) {
					$("#oauthSet li").each(function() {
						$(this).removeClass("active");
					});
					$("#oauthSet_table table").each(function(index, obj) {
						$(this).hide();
					});
					$(li).addClass("active");
					$("#" + tableId).show();
				}
				function setFunc_param(li, tableId) {
					$("#func_tab li").each(function() {
						$(this).removeClass("active");
					});
					$("#func_table > table").each(function(index, obj) {
						$(this).hide();
					});
					$(li).addClass("active");
					$("#" + tableId).show();
				}
				function invokeSelect(source) {
					if (source.value == "0") {
						if (source.checked)
							document.getElementById("auth_readers").style.display = "";
						else
							document.getElementById("auth_readers").style.display = "none";
					}
				}
				function addOauth() {
					seajs
							.use(
									[ 'lui/jquery', 'lui/dialog' ],
									function($, dialog) {
										dialog
												.iframe(
														'/tic/rest/connector/tic_rest_auth/ticRestAuth.do?method=add&' +
														'' +
														'fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}',
														"新增授权", function() {
														}, {
															height : '600',
															width : '850'
														});
									});
				}
				function addCookieSetting() {
					seajs
							.use(
									[ 'lui/jquery', 'lui/dialog' ],
									function($, dialog) {
										dialog
												.iframe(
														'/tic/rest/connector/tic_rest_cookie_setting/ticRestCookieSetting.do?method=add&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}',
														"新增cookie设置",
														function() {
														}, {
															height : '600',
															width : '850'
														});
									});
				}
				
				function addPrefixReqSetting() {
					seajs
							.use(
									[ 'lui/jquery', 'lui/dialog' ],
									function($, dialog) {
										dialog
												.iframe(
														'/tic/rest/connector/tic_rest_prefixReq_setting/ticRestPrefixReqSetting.do?method=add&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}',
														"新增前置请求设置",
														function() {
														}, {
															height : '600',
															width : '850'
														});
									});
				}
				
				function selectHeaderInfo() {
					seajs
							.use(
									[ 'lui/jquery', 'lui/dialog' ],
									function($, dialog) {
										dialog
												.iframe(
														'/tic/rest/connector/tic_rest_main/selectHeader_diagram.jsp',
														"HTTP基本认证",
														function(json) {
															$("#authorization")
																	.empty();
															var b64 = Base64
																	.encode(json["username"]
																			+ ":"
																			+ json["pwd"]);
															$("#authorization")
																	.text("Basic "+b64);
														}, {
															height : '200',
															width : '450'
														});
									});
				}
				function chooseOauth() {
					seajs
							.use(
									[ 'lui/jquery', 'lui/dialog' ],
									function($, dialog) {
										dialog
												.iframe(
														'/tic/rest/connector/tic_rest_auth/ticRestAuth_ui_include.jsp?fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}',
														"选择授权",
														function(authId) {
															if (authId) {
																var data = new KMSSData();
																var url = "ticRestLoadAuthInfoService&authId="
																		+ authId;
																$(
																		"#ticRestAuthId")
																		.val(
																				authId);
																var data = new KMSSData();
																data
																		.SendToBean(
																				url,
																				function(
																						rtn) {
																					loadAuthInfo(rtn)
																				});
															}
														}, {
															height : '600',
															width : '850'
														});
									});
				}
				
				function chooseCookieSetting() {
					seajs
							.use(
									[ 'lui/jquery', 'lui/dialog' ],
									function($, dialog) {
										dialog
												.iframe(
														'/tic/rest/connector/tic_rest_cookie_setting/ticRestCookieSetting_ui_include.jsp?fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}',
														"选择cookie设置",
														function(settingId) {
															if (settingId) {
																var data = new KMSSData();
																var url = "ticRestLoadCookieSettingService&settingId="
																		+ settingId;
																$("#ticRestCookieSettingId").val(settingId);
																var data = new KMSSData();
																data
																		.SendToBean(
																				url,
																				function(
																						rtn) {
																					loadCookieSettingInfo(rtn)
																				});
															}
														}, {
															height : '600',
															width : '850'
														});
									});
				}
				
				
				function choosePrefixReqSetting() {
					seajs
							.use(
									[ 'lui/jquery', 'lui/dialog' ],
									function($, dialog) {
										dialog
												.iframe(
														'/tic/rest/connector/tic_rest_prefixReq_setting/ticRestPrefixReqSetting_ui_include.jsp?fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}',
														"选择前置请求设置",
														function(settingId) {
															if (settingId) {
																var data = new KMSSData();
																var url = "ticRestLoadPrefixReqSettingService&settingId="
																		+ settingId;
																$("#ticRestPrefixReqSettingId").val(settingId);
																var data = new KMSSData();
																data.SendToBean(
																				url,
																				function(
																						rtn) {
											
																					loadPrefixReqSettingInfo(rtn)
																				});
															}
														}, {
															height : '600',
															width : '850'
														});
									});
				}
				
				function loadAuthInfo(rtn) {
					if (!rtn) {
						return;
					}
					var data = rtn.GetHashMapArray();
					if (data) {
						$("#authDetail").remove();
						data = JSON.parse(data[0]["key0"]);
						var docSubject = data.docSubject;
						var fdUseCustAt = data.fdUseCustAt;
						var agentID = data.AgentID;
						var fdAccessTokenClazz = data.fdAccessTokenClazz;
						var authDetail_html = '<tr id="authDetail"><td class="td_normal_title" width="15%">授权详情</td><td   width="85%" colspan="3"><div class="detail">授权名称：'
								+ docSubject
								+ ' </div><div class="detail">授权方式：'
								+ fdUseCustAt + '</div>';
						if (agentID) {
							authDetail_html += '<div class="detail">AgentID：'
									+ agentID + '</div>';
						}
						if (fdAccessTokenClazz) {
							authDetail_html += '<div class="detail">自定义JAVA类全名：'
									+ fdAccessTokenClazz + '</div>';
						}
						authDetail_html += '</td></tr>';
						$('#explain').after($(authDetail_html));
					}
				}
				function loadCookieSettingInfo(rtn) {
					if (!rtn) {
						return;
					}
					var data = rtn.GetHashMapArray();
					if (data) {
						$("#cookieSettingDetail").remove();
						data = JSON.parse(data[0]["key0"]);
						var docSubject = data.docSubject;
						var fdUseCustCt = data.fdUseCustCt;
						//var agentID=data.AgentID;
						var fdCookieSettingClazz = data.fdCookieSettingClazz;
						var cookieSettingDetail_html = '<tr id="cookieSettingDetail"><td class="td_normal_title" width="15%">cookie设置详情</td><td   width="85%" colspan="3"><div class="detail">cookie设置名称：'
								+ docSubject
								+ ' </div><div class="detail">cookie设置方式：'
								+ fdUseCustCt + '</div>';

						if (fdCookieSettingClazz) {
							cookieSettingDetail_html += '<div class="detail">自定义JAVA类全名：'
									+ fdCookieSettingClazz + '</div>';
						}
						cookieSettingDetail_html += '</td></tr>';
						$('#explain_cookie').after($(cookieSettingDetail_html));
					}
				}
				
				function loadPrefixReqSettingInfo(rtn) {
					if (!rtn) {
						return;
					}
					var data = rtn.GetHashMapArray();
					if (data) {
						$("#prefixReqSettingDetail").remove();
						data = JSON.parse(data[0]["key0"]);
						var docSubject = data.docSubject;
						var fdUseCustCt = data.fdUseCustCt;
						//var agentID=data.AgentID;
						var fdPrefixReqSettingClazz = data.fdPrefixReqSettingClazz;
						var prefixReqSettingDetail_html = '<tr id="prefixReqSettingDetail"><td class="td_normal_title" width="15%">前置请求设置详情</td><td   width="85%" colspan="3"><div class="detail">前置请求设置名称：'
								+ docSubject
								+ ' </div><div class="detail">前置请求设置方式：'
								+ fdUseCustCt + '</div>';

						if (fdPrefixReqSettingClazz) {
							prefixReqSettingDetail_html += '<div class="detail">自定义JAVA类全名：'
									+ fdPrefixReqSettingClazz + '</div>';
						}
						prefixReqSettingDetail_html += '</td></tr>';
						$('#explain_prefixReq').after($(prefixReqSettingDetail_html));
					}
				}
				
				function addRow(tableId, type) {
					var new_row = "<tr";
					if (type) {
						new_row += " id=" + type;
					}
					new_row += "><td><input  type='text' name='name' style='width:85%' validate='required' class='inputsgl'";
					if (type) {
						new_row += "value=" + type;
					}
					new_row += "></input></td>";
					if (tableId == 'func_url') {
						new_row += "<td><input  type='text' name='title' style='width:85%' validate='required' class='inputsgl'></input></td>";
					}
					if (tableId == 'func_header' && !type) {
						new_row += "<td><input  type='text' name='value' style='width:85%' validate='required' class='inputsgl'></input></td>";
					}
					if (tableId == 'func_cookie') {
						new_row += "<td><input  type='text' name='value' style='width:85%' validate='required' class='inputsgl'></input></td>";
					}
					if (type == 'Accept') {
						new_row += '<td><xform:radio property="fdReqAccept" htmlElementProperties="on_change_fdReqAccept=\"fdReqSelect(this)\""><xform:enumsDataSource enumsType="rest_fdReqHeader_reqAccept" /></xform:radio></td>';
					}
					if (type == 'Content-type') {
						new_row += '<td><xform:radio property="fdReqContentType" htmlElementProperties="on_change_fdReqContentType=\"fdReqSelect(this)\""><xform:enumsDataSource enumsType="rest_fdReqHeader_reqContentType" /></xform:radio></td>';
					}
					if (type == 'Authorization') {
						new_row += '<td>请求认证：<a class="btn_txt" style="color:#4285f4;" href="javascript:selectHeaderInfo()">HTTP基本认证</a><span id="authorization"></span></td>';
					}
					new_row += "<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></td></tr>";
					$("#" + tableId).append($(new_row));
				}
				function deleteRow(obj) {
					$(obj).parent().parent().remove();
					var name = $(obj).parent().parent().find('[name="name"]')
							.val();
					if (name) {
						if (name == "Accept" || name == "Content-type"
								|| name == "Authorization") {
							$("input[name=_fdReqHeader][value=" + name + "]")
									.attr("checked", false);
						}
					}
				}
				var field_id = 0;
				function addField(tableId, parent) {
					var val = $(parent).parent().parent().find('[name="type"]')
							.val()
							|| "object";
					var tableObj = $('#' + tableId);
					tableObj.removeClass("treeTable").find("tbody tr").each(
							function() {
								$(this).removeClass('initialized');
							});
					var id = field_id++;
					if (parent) {
						id = $(parent).parent().parent().attr("id") + "-" + id;
					} else {
						id = "para" + id;
					}
					var tr_new = $("<tr id='"+id+"'><td><input  type='text' name='name' validate='required' class='inputsgl'></input></td><td><input type='text' name='title' validate='required' class='inputsgl'></input></td>"
							+ "<td><select name='type'><option value='string'>字符串</option>"
							+ "<option value='boolean'>布尔</option><option value='int'>整型</option><option value='long'>长整型</option><option value='double'>浮点型</option>"
							+ "<option value='object'>对象</option><option value='arrayObject'>对象数组</option><option value='arrayInt'>整型数组</option>"
							+ "<option value='arrayDouble'>浮点数组</option><option value='arrayBoolean'>布尔数组</option><option value='arrayString'>字符串数组</option></select></td>"
							+ "<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteField(this);\"/></td>"
							+ "</tr>");
					tr_new
							.find("select")
							.on(
									"change",
									function() {
										var type = $(this).val();
										var img_td = $(this).parent().parent()
												.find("img").parent();
										img_td.find('#img_add').remove();
										if (type == "object"
												|| type == "arrayObject") {
											img_td
													.prepend("<img id='img_add'  src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\"addField('"
															+ tableId
															+ "',this);\"/> ");
										}
									});
					if (!parent) {

					} else {
						tr_new.addClass("child-of-"
								+ $(parent).parent().parent().attr("id"));
					}
					if (parent) {
						$(parent).parent().parent().after(tr_new);
					} else {
						tableObj.append(tr_new);
					}
					tableObj.treeTable({
						initialState : true,
						indent : 15
					}).expandAll();
				}
				function deleteField(obj) {
					var id = $(obj).parent().parent().attr("id");
					id = id.replace(new RegExp("/","g"), "\\/");
					$(obj).parent().parent().siblings(
							"tr.child-of-"
									+ id)
							.each(function(index) {
								$(this).remove();
							});
					$(obj).parent().parent().remove();
				}
				function key_unique_Submit(form, method) {
					var key = $('input[name="fdKey"]')[0].value;
					var fdId = '${param.fdId}';
					$
							.ajax({
								type : "POST",
								url : "${KMSS_Parameter_ContextPath}tic/core/common/tic_core_func_base/ticCoreFuncBase.do?method=checkKeyUnique",
								data : "key=" + key + "&fdId=" + fdId+"&fdEnviromentId=${param.fdEnviromentId}",
								success : function(data) {
									if (data == "false") {
										$("#key_error").text("该关键字已存在!");
									} else {
										$("#key_error").text("");
										if (method){
											var bodyJsonStr = $("textarea[name='bodyJson']").val();
											if(bodyJsonStr){
												$("textarea[name='bodyJson']").val(encodeURIComponent(bodyJsonStr));
											}
											var returnJsonStr = $("textarea[name='returnJson']").val();
											if(returnJsonStr){
												$("textarea[name='returnJson']").val(encodeURIComponent(returnJsonStr));
											}
											Com_Submit(form, method);
										}
									}
								}
							});

				}
				function loadJSON(name, tableId, skipNamespace) {
					var jsonstr = $('textarea[name=' + name + ']').val();
					if (jsonstr == 'undefined' || !jsonstr || !/[^\s]/.test(jsonstr)) {
				        alert("请输入抽取的内容");
				        return;
				    }
					if (jsonstr) {
						$("#" + tableId + " tbody ").find("tr:not(:first)")
						.remove();
						var integrateType="";
						$('#func_header tr:not(:first)').each(function() {
							var json = {};
							var name = $(this).find("input[name=name]").val();
							var value = $(this).find(
							"input[name=value]").val();
							if(name=='integrateType' && value=='ekpListType'){
								integrateType="ekpListType";
								return false;
							}
						});
						$
								.ajax({
									type : "POST",
									url : "${KMSS_Parameter_ContextPath}tic/rest/connector/tic_rest_main/ticRestMain.do?method=paseJsonTransParamInJson&skipNamespace="+skipNamespace,
									data:{jsonstr:encodeURIComponent(jsonstr),integrateType:integrateType},
									dataType : "JSON",
									success : function(data) {
										console.info(data);
										if (data.isSuccess) {
											buildTableHtml(data.result, tableId);
										} else {
											alert(data.errorMsg);
										}
									}
								});
					}
				}

				function buildTableHtml(data, tableId) {
					trs = new Array();
					$.each(data, function(idx, obj) {
						genParaInHtml(obj, "");
					});
					for (i = 0; i < trs.length; i++) {
						var tr = trs[i];
						var img_add = "";
						if(tr.id){
							var id=tr.id;
							id=id.replace(/\|/g, '');  
							tr.id=id; 
						}
						if (tr.type == "object" || tr.type == "arrayObject") {
							img_add = "<img id='img_add'  src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\"addField('"
									+ tableId + "',this);\"/> ";
						}
						var tr_new = $("<tr id='"+tr.id+"'><td><input  type='text' name='name' value='"+tr.name+"' validate='required' class='inputsgl'></input></td><td><input type='text' name='title'  value='"+tr.title+"'  validate='required' class='inputsgl'></input></td>"
								+ "<td><select name='type' onchange='selectChange(this,\""+tableId+"\")'><option value='string'>字符串</option>"
								+ "<option value='boolean'>布尔</option><option value='int'>整型</option><option value='long'>长整型</option><option value='double'>浮点型</option>"
								+ "<option value='object'>对象</option><option value='arrayObject'>对象数组</option><option value='arrayInt'>整型数组</option>"
								+ "<option value='arrayDouble'>浮点数组</option><option value='arrayBoolean'>布尔数组</option><option value='arrayString'>字符串数组</option></select></td>"
								+ "<td align='center'>"
								+ img_add
								+ "<img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteField(this);\"/></td>"
								+ "</tr>");

						tr_new.find("select").val(tr.type);
						if (tr.parentId) {
							tr_new.addClass("child-of-" + tr.parentId);
						}
						$("#" + tableId).append(tr_new);

					}
					$("#" + tableId).treeTable({
						initialState : true,
						indent : 15
					}).expandAll();
				}
				function buildRow(trs, tableId) {
					$(trs)
							.each(
									function() {
										var value = this.value;
										if(value && value.indexOf("BASE64ENCODERED:") == 0) {
											value = value.substring(16);
											value = Base64.decode(value);
										}
										var new_row = "<tr><td><input  type='text' name='name' value='"
												+ this.name
												+ "' style='width:85%' validate='required' class='inputsgl'></input></td>";
										if (tableId == 'func_url') {
											new_row += "<td><input  type='text' name='title' value='"
													+ this.title
													+ "'  style='width:85%' validate='required' class='inputsgl'></input></td>";
										}
										if (tableId == 'func_header') {
											new_row += "<td><input  type='text' name='value' value='"
													+ value
													+ "' style='width:85%' validate='required' class='inputsgl'></input></td>";
										}
										if (tableId == 'func_cookie') {
											new_row += "<td><input  type='text' name='value' value='"
													+ value
													+ "' style='width:85%' validate='required' class='inputsgl'></input></td>";
										}
										new_row += "<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></td></tr>";
										$("#" + tableId).append($(new_row));
									});
				}
				function genParaInHtml(obj, parentId) {
					var name = obj.name;
					var title = obj.title;
					var type = obj.type;
					var id = name;
					if (parentId) {
						id = parentId + "-" + name;
					}
					var tr = {};
					//判断是否有children,做个标识用于页面展示去除填值信息显示
					if (obj.children) {
						tr.child = 1;
					}
					tr.id = id.replace(":", "_");
					tr.name = name;
					tr.title = title;
					tr.type = type;
					tr.parentId = parentId;
					trs.push(tr);
					var children = obj.children;
					if (children) {
						$.each(children, function(idx2, obj2) {
							genParaInHtml(obj2, tr.id);
						});
					}
				}

				function clearField(tableId) {
					$("#" + tableId).find("tbody tr").each(function(index) {
						if (index != 0) {
							$(this).remove();
						}

					});
				}
				function formatPostData() {
					var fdReqParam = {};
					var fdParaIn = [];

					var fdUrl = [];
					$.each($("#return_table tr"),function(i,e){
			    		   var id=$(e).attr("id");
			    		   if(id){
			    			   id=id.replace(/\|/g, '');
			        		   $(e).attr("id",id); 
			    		   }
			    		});
					$.each($("#body_table tr"),function(i,e){
			    		   var id=$(e).attr("id");
			    		   if(id){
			    			   id=id.replace(/\|/g, '');
			        		   $(e).attr("id",id); 
			    		   }
			    		});
					$('#func_url tr:not(:first)').each(function() {
						var json = {};
						json.name = $(this).find("input[name=name]").val();
						json.title = $(this).find("input[name=title]").val();
						json.type = "string";
						fdUrl.push(json);
					});
					fdReqParam["url"] = fdUrl;
					var fdUrlJson = {
						"name" : "url",
						"title" : "url",
						"type" : "object"
					};
					fdUrlJson.children = fdUrl;
					fdParaIn.push(fdUrlJson);
					var fdReqHeaderValue = $("input[name=fdReqHeader]").val();
					var fdReqHeaderValueArray = fdReqHeaderValue.split(";");
					var fdHeader = [];
					var fdParaInHeader = [];
					$('#func_header tr:not(:first):not(:first)')
							.each(
									function() {
										var json = {};
										json.name = $(this).find(
												"input[name=name]").val();
										if (containValue(fdReqHeaderValueArray,
												json.name)) {
											if (json.name == 'Accept') {
												var otherInput = $(this).find(
														"input[name=other]")
														.val();
												if (typeof (otherInput) != "undefined") {
													json.value = otherInput;
												} else {
													json.value = $(this)
															.find(
																	"input[name=fdReqAccept]:checked")
															.val();
													console.log(json.value);
												}
											} else if (json.name == 'Content-type') {
												var otherInput = $(this).find(
														"input[name=other]")
														.val();
												if (typeof (otherInput) != "undefined") {
													json.value = otherInput;
												} else {
													json.value = $(this)
															.find(
																	"input[name=fdReqContentType]:checked")
															.val();
												}
											} else if (json.name == 'Authorization') {
												json.value = $("#authorization")
														.text();
											}
										} else {
											json.value = $(this).find(
													"input[name=value]").val();
										}
										json.type = "string";
										fdHeader.push(json);
										if ($(this).find("input[name=value]")
												.val() == "") {
											fdParaInHeader.push(json);
										}
									});
					fdReqParam["header"] = fdHeader;
					var fdHeaderJson = {
						"name" : "header",
						"title" : "header",
						"type" : "object"
					};
					fdHeaderJson.children = fdParaInHeader;
					fdParaIn.push(fdHeaderJson);

					var fdCookie = [];
					//var fdParaInHeader=[];
					$('#func_cookie tr:not(:first)').each(function() {
						var json = {};
						json.name = $(this).find("input[name=name]").val();
						json.value = $(this).find("input[name=value]").val();
						json.type = "string";
						fdCookie.push(json);
					});
					fdReqParam["cookie"] = fdCookie;

					var fdBody = buildParaJson("body_table");
					fdReqParam["body"] = fdBody;
					var fdBodyJson = {
						"name" : "body",
						"title" : "body",
						"type" : "object"
					};
					fdBodyJson.children = fdBody;
					fdParaIn.push(fdBodyJson);
					var fdReturn = buildParaJson("return_table");
					fdReqParam["return"] = fdReturn;

					$("#fdReqParam").val(JSON.stringify(fdReqParam));
					$("#fdParaIn").val(JSON.stringify(fdParaIn));
					$("#fdParaOut").val(JSON.stringify(fdReturn));

				}
				function containValue(array, value) {
					for (var i = 0; i < array.length; i++) {
						if (array[i] == value) {
							return true;
						}
					}
					return false;
				}

				function headerSelect(source) {
					var headerArray = [];
					if (source.checked) {
						$('#func_header tr:not(:first):not(:first)').each(
								function() {
									headerArray.push($(this).find(
											"input[name=name]").val());
								});
						if (containValue(headerArray, source.value)) {
							$(
									"input[name=_fdReqHeader][value="
											+ source.value + "]").attr(
									"checked", false);
							alert(source.value + "已存在");
						} else {
							addRow('func_header', source.value);
						}
					} else
						$("#" + source.value).remove();
				}
				function fdReqSelect(source) {
					var input_other = "<input  type='text' name='other' style='width:85%' validate='required' class='inputsgl'></input>";
					if (source.checked && source.value == "other") {
						$(source).parent().parent().append($(input_other));
					} else {
						$(source).parent().parent().find("input[name=other]")
								.remove();
					}
				}

				//编辑
				function init() {
					if ('${ticRestMainForm.fdReqParam}') {
						var fdReqParam = JSON
								.parse('${ticRestMainForm.fdReqParam}');
						console.log(fdReqParam);

						var fdUrl = fdReqParam["url"];
						buildRow(fdUrl, "func_url");
						var fdHeader = fdReqParam["header"];
						//$("input[name=fdReqAccept][value="+fdHeader["accept"]+"]").attr("checked",true); 
						buildRow(fdHeader, "func_header");
						var fdCookie = fdReqParam["cookie"];
						buildRow(fdCookie, "func_cookie");
						var fdBody = fdReqParam["body"];
						buildTableHtml(fdBody, "body_table");
						var fdReturn = fdReqParam["return"];
						buildTableHtml(fdReturn, "return_table");
					}
					if ('${ticRestMainForm.fdInvoke}' == "0") {
						document.getElementById("auth_readers").style.display = "";
					}
				}
				
				
				function selectChange(select,tableId) {
					var type = $(select).val();
					var img_td = $(select).parent().parent()
							.find("img").parent();
					img_td.find('#img_add').remove();
					if (type == "object"
							|| type == "arrayObject") {
						img_td
								.prepend("<img id='img_add'  src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\"addField('"
										+ tableId
										+ "',this);\"/> ");
					}
				}
			</script>
		</html:form>

	</template:replace>
</template:include>
<%@ include file="/resource/jsp/edit_down.jsp"%>