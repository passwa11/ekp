<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<table class="tb_normal mobile_form_tb" width=100% align="center" frame=void>
	<!-- pc or mobile designer switch -->
	<div id="xform_${HtmlParam.fdKey}_design_title" width="100%">
		<div class="xform_client_type">
			<div class="xform_client_btn_wrap" name="sysFormTemplateForms.${HtmlParam.fdKey}.type">
				<span class="xform_client_btn is-active" val="pc" onclick="MobileDesigner.instance.bindEvent(event,'formTypeClick');">
					<i class="cap-icon-pc"></i>
					${lfn:message('sys-xform-base:Designer_Lang.PC')}
				 </span> 
				 <span class="xform_client_btn" val="mobile" onclick="MobileDesigner.instance.bindEvent(event,'formTypeClick');">
					<i class="cap-icon-mobile"></i>
 						 ${lfn:message('sys-xform-base:Designer_Lang.mobile')}
				 </span>
			</div>
			<!-- <div style="float:right;margin-right:5px;" class="xform_client_btn_wrap" name="sysFormTemplateForms.modelingApp.type"> -->
				<div class="xform_preview_btn" id="previewDiv" onclick="MobileDesigner.instance.bindEvent(event,'preview');">
				 	<ui:button text="${lfn:message('sys-xform-base:Designer_Lang.preview')}" order="1" styleClass="lui_toolbar_btn_gray" style="height:24px;width:70px;"></ui:button>
				</div>
			<!-- </div> -->
		</div>
	</div>
	<!--移动端设计 -->
	<tr id="xform_${HtmlParam.fdKey}_design" style="display:none;">
		<td id="td_xform_${HtmlParam.fdKey}_left" class="xform_left">
			<div id="xform_${HtmlParam.fdKey}_left" class="xform_form_left">
				<!-- 标题start -->
				<div class="xform_form_title_wrap">
					<table class="subTable" style="width:100%">
						<tr>
							<td class="xform_form_title_left">
								<div id="xform_${HtmlParam.fdKey}_title" class="xform_form_title">
									${lfn:message('sys-xform-base:Designer_Lang.formConfiguration')}
								</div>
							</td>
							<td class="xform_form_title_right">
								<div id="xform_${HtmlParam.fdKey}_addMobileForm" class="xform_icon_add xform_add_mobile_form" onclick="MobileDesigner.instance.bindEvent(event,'addMobileForm');" title="${lfn:message('sys-xform-base:Designer_Lang.addMobileForm')}">
									<i></i>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<!-- 标题end -->
				
				<!-- 左侧表单配置 start  -->
				<div id="xform_${HtmlParam.fdKey}_form" class="xform_form_wrap"> 
					<table id="table_docList_${HtmlParam.fdKey}_form" class="tb_normal xform_form_tb">
						<%--默认表单
						<tr ischecked="true" id="subform_default">
							<td width=65%>
								<input type="hidden" name="defaultfdId" />
								<a name="subFormText" style="font-weight:bold;color:#47b5e6;margin-left:4px;position: relative" onclick="MobileForm_Click(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form_nonhandlers" />"><bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form_nonhandlers" /></a>
								<input type="hidden" name="defaultfdName" value="<bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form_nonhandlers" />"/>
								<input type="hidden" name="defaultfdDesignerHtml" />
								<input type="hidden" name="defaultfdMetadataXml" />
								<input type="hidden" name="defaultfdCss"/>
								<input type="hidden" name="defaultfdCssDesigner"/>
							</td>
							操作按钮
							<td width="35%" align="right">
								<a href="javascript:void(0)" onclick="SubForm_Copy(this);" style="margin-left:4px;position: relative">
									<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/copy.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />"/>
								</a>
							</td>
							--%>
						</tr>
						<!-- 基准行 -->
						<tr KMSS_IsReferRow="1" style="display: none">
							<td>
								<a name="mobileFormText" class="xform_name_text" onclick="MobileDesigner.instance.bindEvent(event,'formClick');" ondblclick="editName(this);"></a>
								<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[!{index}].fdId" />
								<xform:text	property="${sysFormTemplateFormPrefix}fdMobileForms[!{index}].fdName" style="width:90%" validators="maxLength(200)" htmlElementProperties="onblur='textBlur(this);'"/>
								<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[!{index}].fdDesignerHtml" />
								<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[!{index}].fdMetadataXml" />
								<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[!{index}].fdCss"/>
								<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[!{index}].fdCssDesigner"/>
								<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[!{index}].fdIsDefWebForm"/>
								<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[!{index}].fdPcFormId"/>
							</td>
							<!-- 编辑、复制、删除按钮 -->
							<td align="right">
								<div class="xform_operation">
									<div class="xform_icon_copy" onclick="MobileDesigner.instance.bindEvent(event,'copyMobileForm');"  title="${lfn:message('sys-xform-base:Designer_Lang.copy')}">
										<i></i>
									</div>
									<div class="xform_icon_delete" onclick="MobileDesigner.instance.bindEvent(event,'deleteMobileForm');"  title="${lfn:message('sys-xform-base:Designer_Lang.delete')}">
										<i></i>
									</div>
								</div>
							</td>
						</tr>
						<c:set var="index" value="0" />
						<c:forEach items="${xFormTemplateForm.fdSubForms}" var="item" varStatus="vstatus">
							<c:if test="${item.fdIsDefWebForm eq 'true'}">
								<tr KMSS_IsContentRow="1" ischecked="false" id="${item.fdId}" defaultWebForm="<c:out value="${item.fdIsDefWebForm}" />">
									<td>
										<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdId" value="${item.fdId}" /> 
										<%-- <input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${vstatus.index}].fdParentFormId" value="${xFormTemplateForm.fdId}" /> --%>
										<xform:text property="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdName" value="${item.fdName}" style="width:90%;display:none" validators="maxLength(200)" htmlElementProperties="onblur='textBlur(this);'" />
										<a name="mobileFormText" class="xform_name_text" onclick="MobileDesigner.instance.bindEvent(event,'formClick');" title="<c:out value='${item.fdName}'/>" ondblclick="editName(this);"><c:out value='${item.fdName}'/></a>
										<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdDesignerHtml" value="<c:out value='${item.fdDesignerHtml}'/>"/>
										<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdMetadataXml" value="<c:out value='${item.fdMetadataXml}'/>"/>
										<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdCss" value="<c:out value='${item.fdCss}'/>"/>
										<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdCssDesigner" value="<c:out value='${item.fdCssDesigner}'/>"/>
										<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdIsDefWebForm" value="<c:out value='${item.fdIsDefWebForm}'/>"/>
										<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdPcFormId" value="<c:out value='${item.fdPcFormId}'/>"/>
										<input type="hidden" name="${sysFormTemplateFormPrefix}fdMobileForms[${index}].fdOldFormId" value="<c:out value='${item.fdOldFormId}'/>"/>
									</td>
									
									<td align="right">
										<div class="xform_operation">
											<div class="xform_icon_copy" onclick="MobileDesigner.instance.bindEvent(event,'copyMobileForm');" title="${lfn:message('sys-xform-base:Designer_Lang.copy')}">
												<i></i>
											</div>
										</div>
									</td>
								</tr>
								<c:set var="index" value="${index+1}" />
							</c:if>
						</c:forEach>
					</table>
				</div>
				<!-- 左侧表单配置 end  -->
				
				<!-- 控件列表start -->
				<div id="xform_${HtmlParam.fdKey}_control_title" class="xform_control_title">
					${lfn:message('sys-xform-base:Designer_Lang.control')}
				</div>
				<div id="xform_${HtmlParam.fdKey}_control" class="xform_control_wrap">
				</div>
				<!-- 控件列表end -->
				
				<!-- 提交表单前源码预处理div -->
				<div id=xform_${HtmlParam.fdKey}_preview class="xform_pre_process"></div>
			</div>
		</td>
		
		<!-- 展开/折叠图标start 
		<td class = "xform_left_right">
	    	<image val="toLeft" style="cursor:pointer;" src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowleft.gif" onclick="MobileDesigner.instance.bindEvent(event,'expandOrHide');">
	    	<div val="toLeft" class="model-leftbar-btn active" onclick="MobileDesigner.instance.bindEvent(event,'expandOrHide');"><i></i><p class="model-rightbar-btn-tips">收起</p></div>
		</td>
		 -->
		<!-- 展开/折叠图标end -->
		
		<!--移动端绘制面板 -->
		<td class="td_normal_title xform_mobile_draw_panel"
			id="TD_MobileFormTemplate_${HtmlParam.fdKey}" ${sysFormTemplateFormResizePrefix}onresize="LoadMobileXForm('TD_MobileFormTemplate_${JsParam.fdKey}');">
			<!-- <div class="mobileDesiger-content-wrap">
				<div class="mobileDesiger-content"> -->
				 <div class="xform_app_name_wrap">
				 	<span class="xform_app_name">
	                     <i class="iconfont_nav ${JsParam.appIcon}"></i>
	                  	 <span name="appName"></span>
                  	 </span>
				</div>
			<!-- 	 <div class="xform_mobile_top_bar_wrap">
						<div class="xform_mobile_top_bar">
						</div>
				</div> -->
				<div>
				<iframe id="IFrame_MobileFormTemplate_${HtmlParam.fdKey}" style="min-height:812px;max-width: 450px;min-width:375px;margin-left:3px;" width="40%" height="100%" scrolling="yes" FRAMEBORDER=0 mobile="true"></iframe>
				</div>
			<!-- 	</div>
			</div> -->
			<%-- <div id="MobileFormTemplate_${HtmlParam.fdKey}_wrap" class="mobileFormTempalte_wrap">
				<c:import url="/sys/xform/designer/designPanel.jsp" charEncoding="UTF-8">
					<c:param name="fdKey" value="${JsParam.fdKey}" />	
					<c:param name="fdModelName" value="${sysFormTemplateForm.modelClass.name}" />
					<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix}" />
					<c:param name="encrypt" value="false" />
					<c:param name="mobile" value="true" />
				</c:import>
			</div> --%>
		</td>
		
		<!-- 展开/折叠图标start
		<td class = "xform_left_right">
			<!-- <image val="toRight" id="Subform_operation_toRight" style="cursor:pointer;" src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowright.gif" onclick="MobileDesigner.instance.bindEvent(event,'expandOrHide');">
			<div val="toRight" class="model-rightbar-btn active" onclick="MobileDesigner.instance.bindEvent(event,'expandOrHide');"><i></i><p class="model-rightbar-btn-tips">收起</p></div>
		</td>
		 -->
		
		<!-- 右侧基础设置 -->
		<td class="xform_right" id="xform_${JsParam.fdKey}_right">
			<div id="mobileForm_right_${JsParam.fdKey}_content" class="xform_mobile_operation edui-operation"></div>
		</td>
	</tr>
</table>

<script>
	
	var config = {
		win : window,
		fdKey : '${JsParam.fdKey}',
		defineForm : '<%=XFormConstant.TEMPLATE_DEFINE%>',
		subForm : '<%=XFormConstant.TEMPLATE_SUBFORM%>',
		formPrefix : '${sysFormTemplateFormPrefix}',
		mobileIFrame : 'IFrame_MobileFormTemplate_${JsParam.fdKey}',
		pcIFrame : 'IFrame_FormTemplate_${JsParam.fdKey}',
		templateModelName : '${sysFormTemplateForm.modelClass.name}',
		enableFlow : '${JsParam.enableFlow}',
        mode: '${xFormTemplateForm.fdMode}',
        history : '${JsParam.history}',
		lang:{
			templateFirst:'${lfn:message('sys-xform-base:Designer_Lang.saveTheTemplateFirst')}',
			preview:'${lfn:message('sys-xform-base:Designer_Lang.preview')}',
			formName:'${lfn:message('sys-xform-base:Designer_Lang.enterTheFormName')}',
			duplicateName:'${lfn:message('sys-xform-base:Designer_Lang.duplicateName')}',
			designFirst:'${lfn:message('sys-xform-base:Designer_Lang.designProcessFirst')}',
			mobileNotAllowedDesign:'${lfn:message('sys-xform-base:Designer_Lang.mobileNotAllowedDesign')}',
			mobileAllowedDesign:'${lfn:message('sys-xform-base:Designer_Lang.mobileAllowedDesign')}',
			pcNotLoad:'${lfn:message('sys-xform-base:Designer_Lang.pcNotLoad')}',
			selectRowFirst:'${lfn:message('sys-xform-base:Designer_Lang.selectRowFirst')}',
			notSupportInsert:'${lfn:message('sys-xform-base:Designer_Lang.mobileNotSupportInsert')}',
			delete:'${lfn:message('sys-xform-base:Designer_Lang.delete')}',
			control:'${lfn:message('sys-xform-base:Designer_Lang.control')}',
			form:'${lfn:message('sys-xform-base:Designer_Lang.form')}',
			mobileForm:'${lfn:message('sys-xform-base:Designer_Lang.mobileForm')}',
			copySuccess:'${lfn:message('sys-xform-base:Designer_Lang.copySuccess')}',
			loadSuccess:'${lfn:message('sys-xform-base:Designer_Lang.loadSuccess')}',
			loadFailed:'${lfn:message('sys-xform-base:Designer_Lang.loadFailed')}',
			confirmDelete:'${lfn:message('sys-xform-base:Designer_Lang.confirmDelete')}',
			putAway:'${lfn:message('sys-xform-base:Designer_Lang.putAway')}',
			basicSet:'${lfn:message('sys-xform-base:Designer_Lang.basicSet')}',
			defaultForm:'${lfn:message('sys-xform-base:Designer_Lang.defaultForm')}',
			selectOpt:'${lfn:message('sys-xform-base:Designer_Lang.buttonsSelectOptControl')}'
		}
	};

	Com_IncludeFile("mobileDesigner.js",Com_Parameter.ContextPath+'sys/xform/designer/mobile/js/','js',true);
	Com_IncludeFile("mobileDesigner.css",Com_Parameter.ContextPath+'sys/xform/designer/mobile/css/','css',true);
	Com_IncludeFile("mobileToolbar.js",Com_Parameter.ContextPath+'sys/xform/designer/mobile/js/','js',true);
	Com_IncludeFile("edui-editor-common.css",Com_Parameter.ContextPath+'sys/xform/designer/mobile/css/','css',true);
	Com_IncludeFile("data.js|doclist.js");
	//移动表单配置
	var frame = document.getElementById(config.mobileIFrame);
</script>

<script>

DocList_Info.push("table_docList_${HtmlParam.fdKey}_form");

var SubFormData = {
	//表单名称自动生成时序号
	subFormItem : 1,
	//是否保存新版本
	saveasNew_subform : false,
	//是否有变动
	isChanged : false,
	//是否需要加载
	needLoad_subform : true,
	//复制时保存的document对象
	CopyDom : null,
	//是否做了数据映射
	isWriteDbInfos : null
}

function LoadMobileXForm(dom) {
	XForm_Loading_Show();
	Doc_LoadFrame(dom, '<c:url value="/sys/xform/designer/designPanel.jsp?fdKey=" />${JsParam.fdKey}&fdModelName=${sysFormTemplateForm.modelClass.name}&sysFormTemplateFormPrefix=${sysFormTemplateFormPrefix}&encrypt=false&mobile=true&openBaseInfoDesign=${JsParam.openBaseInfoDesign}&openBaseInfoDesign=${JsParam.openBaseInfoDesign}');
	var frame = document.getElementById('IFrame_MobileFormTemplate_${JsParam.fdKey}');
	Com_AddEventListener(frame, 'load', XForm_Loading_Hide);
}

$(function(){
	var appName = '${HtmlParam.appName}';
	appName =  eval("'" + appName + "'");
	$("[name='appName']").text(appName);
	if (appName == null || appName == "") {
		$(".xform_app_name_wrap").hide();
	}
})
</script>
