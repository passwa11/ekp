<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ include file="/resource/jsp/common.jsp"%>
<style>
.subTable {
	border:none ! important;
}
.subTable td {
	border:none ! important;
}
.subTable tr {
	border:none ! important;
}
.subTable a{
	font-size:9pt;
	cursor: pointer;
}
.subform_panel_normal{
    width: 80%;
    margin: 2px 5px;
    padding: 1px 5px;
}
.subform_panel_mouseover{
    width: 80%;
    margin: 2px 5px;
    padding: 1px 5px;
    cursor: pointer;
    border: 1px solid #ccc;
}
</style>
<table class="tb_normal subTable" width=100% id="TABLE_DocList_SubForm" align="center" style="table-layout:fixed;" frame=void>
	<tr ischecked="true" id="subform_default">
		<td width=52%>
			<input type="hidden" name="defaultfdId" />
			<a name="subFormText" style="font-weight:bold;color:#47b5e6;margin-left:4px;position: relative" onclick="SubForm_Click(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form_nonhandlers" />"><bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form_nonhandlers" /></a>
			<input type="hidden" name="defaultfdName" value="<bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form_nonhandlers" />"/>
			<input type="hidden" name="defaultfdDesignerHtml" />
			<input type="hidden" name="defaultfdMetadataXml" />
			<input type="hidden" name="defaultfdCss"/>
			<input type="hidden" name="defaultfdCssDesigner"/>
		</td>
		<%--操作按钮--%>
		<td style="white-space:nowrap" align="right" width="48%">
			<div class="xform_operation" style="display:inline-block;margin-right:2px;">
					<div class="xform_icon_phone_copy" style="margin-left:2px;" onclick="MobileDesigner.instance.bindEvent(event,'copyPcFormToMobile');" title="复制移动端表单">
						<i></i>
					</div>
				<div class="xform_icon_copy" style="margin-left:10px;"  onclick="SubForm_Copy(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />">
					<i></i>
				</div>
			</div>
			<%-- <a href="javascript:void(0)" onclick="SubForm_Copy(this);" style="margin-left:4px;position: relative">
				<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/copy.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />"/>
			</a> --%>
			<%-- <a href="javascript:void(0)" onclick="addRow(this,false);">
				<img src="${KMSS_Parameter_StylePath}/icons/add.gif" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.add" />"/>
			</a> --%>
			<%-- <a href="javascript:void(0)" onclick="SubForm_Load();">
				<img src="${KMSS_Parameter_StylePath}/icons/link_info.gif" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.load" />"/>
			</a> --%>
		</td>
	</tr>
	<!-- 基准行 -->
	<tr KMSS_IsReferRow="1" style="display: none">
		<td>
			<a name="subFormText" style="font-weight:bold;margin-left:4px;position: relative" onclick="SubForm_Click(this);" ondblclick="SubForm_Edit(this);"></a>
			<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdId" />
			<xform:text	property="${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdName" style="width:90%" htmlElementProperties="onblur='SubForm_TextBlur(this);'" validators="maxLength(200)"/>
			<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdDesignerHtml" />
			<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdMetadataXml" />
			<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdCss"/>
			<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdCssDesigner"/>
			<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdIsDefWebForm"/>
		</td>
		<!-- 编辑、复制、删除按钮 -->
		<td style="white-space:nowrap" align="right" width="48%">
			<div class="xform_operation"  style="display:inline-block;margin-right:2px;">
					<div class="xform_icon_phone_copy" style="margin-left:2px;" onclick="MobileDesigner.instance.bindEvent(event,'copyPcFormToMobile');" title="复制移动端表单">
						<i></i>
					</div>
				<div class="xform_icon_copy" style="margin-left:10px;"  onclick="SubForm_Copy(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />">
					<i></i>
				</div>
				<div class="xform_icon_edit" style="margin-left:10px;"  onclick="SubForm_Edit(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.edit" />">
					<i></i>
				</div>
				<div class="xform_icon_delete" style="margin-left:10px;"  onclick="SubForm_DeleteRow(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.delete" />">
					<i></i>
				</div>
				<%-- <a href="javascript:void(0)" onclick="SubForm_Copy(this);" style="position: relative;">
					<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/copy.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />"/>
				</a>
				<a href="javascript:void(0)" onclick="SubForm_Edit(this);">
					<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/edit.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.edit" />"/>
				</a>
				<a href="javascript:void(0)" onclick="SubForm_DeleteRow(this);">
					<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/delete.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.delete" />"/>
				</a> --%>
			</div>
		</td>
	</tr>
	<c:set var="index" value="0" />
	<c:forEach items="${xFormTemplateForm.fdSubForms}" var="item" varStatus="vstatus">
	 	<c:choose>
			<c:when test="${mobile eq 'true'}">
	 			<c:if test="${'true' ne item.fdIsDefWebForm}">
	 				<tr KMSS_IsContentRow="1" ischecked="false" id="${item.fdId}" defaultWebForm="<c:out value="${item.fdIsDefWebForm}" />">
						<td>
							<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdId" value="${item.fdId}" /> 
							<%-- <input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${vstatus.index}].fdParentFormId" value="${xFormTemplateForm.fdId}" /> --%>
							<xform:text property="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdName" value="${item.fdName}" style="width:90%;display:none" htmlElementProperties="onblur='SubForm_TextBlur(this);'" validators="maxLength(200)" />
							<a name="subFormText" style="font-weight:bold;margin-left:4px;position: relative" onclick="SubForm_Click(this);" ondblclick="SubForm_Edit(this);" title="<c:out value='${item.fdName}'/>"><c:out value='${item.fdName}'/></a>
							<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdDesignerHtml" value="<c:out value='${item.fdDesignerHtml}'/>"/>
							<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdMetadataXml" value="<c:out value='${item.fdMetadataXml}'/>"/>
							<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdCss" value="<c:out value='${item.fdCss}'/>"/>
							<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdCssDesigner" value="<c:out value='${item.fdCssDesigner}'/>"/>
							<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdIsDefWebForm" value="<c:out value='${item.fdIsDefWebForm}'/>"/>
							<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdOldFormId" value="<c:out value='${item.fdOldFormId}'/>"/>
						</td>
						<!-- 操作按钮 -->
						<td align="right" style="white-space:nowrap" width="48%">
							<%-- <a href="javascript:void(0)" onclick="MobileDesigner.instance.bindEvent(event,'copyFormByPc');" class="xform_phone_copy" style="position: relative;">
								<img src="${KMSS_Parameter_ContextPath}sys/xform/designer/mobile/css/img/phone_copy@2x.png" border="0" title="复制移动端表单"/>
							</a> --%>
							<div class="xform_operation"  style="display:inline-block;margin-right:2px;">
									<div class="xform_icon_phone_copy" style="margin-left:2px;" onclick="MobileDesigner.instance.bindEvent(event,'copyPcFormToMobile');" title="复制移动端表单">
										<i></i>
									</div>
								<div class="xform_icon_copy" style="margin-left:10px;"  onclick="SubForm_Copy(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />">
									<i></i>
								</div>
								<div class="xform_icon_edit" style="margin-left:10px;"  onclick="SubForm_Edit(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.edit" />">
									<i></i>
								</div>
							</div>
							<%-- <a href="javascript:void(0)" onclick="SubForm_Copy(this);" style="position: relative;">
								<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/copy.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />"/>
							</a> --%>
							<%-- <a href="javascript:void(0)" onclick="SubForm_Edit(this);">
								<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/edit.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.edit" />"/>
							</a> --%>
							<%-- <a href="javascript:void(0)" onclick="SubForm_DeleteRow(this);">
								<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/delete.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.delete" />"/>
							</a> --%>
						</td>
					</tr>
					<c:set var="index" value="${index+1}" />
				</c:if>
 			</c:when>
 			<c:otherwise>
 				<tr KMSS_IsContentRow="1" ischecked="false" id="${item.fdId}" defaultWebForm="<c:out value="${item.fdIsDefWebForm}" />">
					<td>
						<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdId" value="${item.fdId}" /> 
						<%-- <input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${vstatus.index}].fdParentFormId" value="${xFormTemplateForm.fdId}" /> --%>
						<xform:text property="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdName" value="${item.fdName}" style="width:90%;display:none" htmlElementProperties="onblur='SubForm_TextBlur(this);'" validators="maxLength(200)" />
						<a name="subFormText" style="font-weight:bold;margin-left:4px;position: relative" onclick="SubForm_Click(this);" ondblclick="SubForm_Edit(this);" title="<c:out value='${item.fdName}'/>"><c:out value='${item.fdName}'/></a>
						<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdDesignerHtml" value="<c:out value='${item.fdDesignerHtml}'/>"/>
						<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdMetadataXml" value="<c:out value='${item.fdMetadataXml}'/>"/>
						<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdCss" value="<c:out value='${item.fdCss}'/>"/>
						<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdCssDesigner" value="<c:out value='${item.fdCssDesigner}'/>"/>
						<input type="hidden" name="${sysFormTemplateFormPrefix}fdSubForms[${index}].fdIsDefWebForm" value="<c:out value='${item.fdIsDefWebForm}'/>"/>
					</td>
					<!-- 操作按钮 -->
					<td align="right" style="white-space:nowrap" width="48%">
						<%-- <a href="javascript:void(0)" onclick="MobileDesigner.instance.bindEvent(event,'copyFormByPc');" class="xform_phone_copy" style="position: relative;">
							<img src="${KMSS_Parameter_ContextPath}sys/xform/designer/mobile/css/img/phone_copy@2x.png" border="0" title="复制移动端表单"/>
						</a> --%>
						<div class="xform_operation"  style="display:inline-block;margin-right:2px;">
								<div class="xform_icon_phone_copy" style="margin-left:2px;" onclick="MobileDesigner.instance.bindEvent(event,'copyPcFormToMobile');" title="复制移动端表单">
									<i></i>
								</div>
							<div class="xform_icon_copy" style="margin-left:10px;"  onclick="SubForm_Copy(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />">
								<i></i>
							</div>
							<div class="xform_icon_edit" style="margin-left:10px;"  onclick="SubForm_Edit(this);" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.edit" />">
								<i></i>
							</div>
						</div>
						<%-- <a href="javascript:void(0)" onclick="SubForm_Copy(this);" style="position: relative;">
							<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/copy.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.copy" />"/>
						</a> --%>
						<%-- <a href="javascript:void(0)" onclick="SubForm_Edit(this);">
							<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/edit.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.edit" />"/>
						</a> --%>
						<%-- <a href="javascript:void(0)" onclick="SubForm_DeleteRow(this);">
							<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/delete.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.delete" />"/>
						</a> --%>
					</td>
				</tr>
				<c:set var="index" value="${index+1}" />
 			</c:otherwise>
		</c:choose>
	</c:forEach>
</table>
<script>
Com_IncludeFile('doclist.js');
Com_IncludeFile("mobileDesigner.css",Com_Parameter.ContextPath+'sys/xform/designer/mobile/css/','css',true);
</script>
<script>
DocList_Info.push("TABLE_DocList_SubForm");

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


function SubForm_AddRow(dom,isCopy){
	var url = Com_Parameter.ContextPath + "sys/xform/sys_form_template/sysFormTemplate.do?method=generateID";
	$.ajax({
		type : "GET",
		url : url,
		success : function(data){
			if(data){
				var fieldValues = new Object();
				fieldValues["${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdId"]=data;
				fieldValues["${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdName"]="<bean:message bundle="sys-xform" key="sysSubFormTemplate.form" source="js" />"+SubFormData.subFormItem;
				fieldValues["${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdIsDefWebForm"]=false;
				var newRow = DocList_AddRow("TABLE_DocList_SubForm",null,fieldValues);
				$(newRow).css("white-space","nowrap");
				SubFormData.subFormItem++;
				var input = $("#TABLE_DocList_SubForm").find("input[name$='fdName']");
				$(input[input.length-1]).select();
				$(input[input.length-1]).closest("tr").attr("id",data);
				SubForm_SetChecked($(input[input.length-1]).parent().parent(),isCopy,dom);
				if($("#SubFormDiv")[0].scrollHeight > $("#SubFormDiv")[0].clientHeight){
					$('#SubFormDiv').scrollTop( $('#SubFormDiv')[0].scrollHeight );
				}
                SubForm_ResetUndo();
			}
		}
	});
}

function addMobileDefaultRow(){
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.confirm("<bean:message bundle="sys-xform" key="sysSubFormTemplate.webform_configuration" source="js" />",function(data){
			if(data){
				var url = Com_Parameter.ContextPath + "sys/xform/sys_form_template/sysFormTemplate.do?method=generateID";
				$.ajax({
					type : "GET",
					url : url,
					success : function(data){
						if(data){
							//配置初始化参数
							var fieldValues = new Object();
							fieldValues["${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdId"]=data;
							fieldValues["${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdName"]="<bean:message bundle="sys-xform" key="sysSubFormTemplate.webform" source="js" />";
							fieldValues["${sysFormTemplateFormPrefix}fdSubForms[!{index}].fdIsDefWebForm"]=true;
							//新增一行
							DocList_AddRow("TABLE_DocList_SubForm",null,fieldValues);
							//拿到当前明细表
							var optTB = document.getElementById("TABLE_DocList_SubForm");
							var tbInfo = DocList_TableInfo[optTB.id];
							//设置延时，否则新增的一行还未生成，导致后续操作失败
							setTimeout(function(){
								//拿到明细表行数
								var rowIndex = optTB.rows.length;
								//拿到新增的这一行
								var opTR = optTB.rows[optTB.rows.length-1];
								//向上移动到第二行
								DocList_MoveRow(-(rowIndex-2),opTR);
								//刷新下标
								for(var i = tbInfo.firstIndex; i<tbInfo.lastIndex; i++){
									DocListFunc_RefreshIndex(tbInfo, i);
								} 
								$(opTR).find("input[name$='fdName']").select().blur();
								$(opTR).attr("id",data);
								SubForm_SetChecked($(opTR),true,$("#TABLE_DocList_SubForm").find("#subform_default").find("input[name$='fdName']")[0]);
								var title = "<bean:message bundle="sys-xform" key="sysSubFormTemplate.edit" />";
								$(opTR).find("img[title="+title+"]").parent().hide();
								$(opTR).find("a[name='subFormText']").removeAttr("ondblclick");
								$("#newDefaultWebForm").hide();
								$(opTR).attr("defaultWebForm",true);
							},0);
						}
					}
				});
			}
		});
	});
}

function SubForm_Copy(dom){
	SubFormData.CopyDom = dom;
	var toTr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']").find("input[name$='fdName']").val();
	var fromTr = $(dom).parents("tr[ischecked]").find("input[name$='fdName']").val();
	var msg = '<div style="text-align:left;">'
		+ '<label><input type="radio" name="subform_copy" value="copy" checked="checked"><bean:message bundle="sys-xform" key="sysSubFormTemplate.copyMsg" />'+fromTr+'<bean:message bundle="sys-xform" key="sysSubFormTemplate.copyInfo" /></input></label>'
		+ '<br/><br/>';
	if(toTr!=fromTr){
		msg	+= '<label><input type="radio" name="subform_copy" value="cover"><bean:message bundle="sys-xform" key="sysSubFormTemplate.copyMsg" />'+fromTr+'<bean:message bundle="sys-xform" key="sysSubFormTemplate.coverInfo" />'+toTr+'</input></label>'
	}
	msg += '</div>';
	seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
		dialog.build({
			config : {
				width : 420,
				cahce : false,
				title : "<bean:message bundle="sys-xform" key="sysSubFormTemplate.copyForm" />",
				content : {
					type : "common",
					html : msg,
					iconType : 'question',
					buttons : [ {
						name : "${lfn:message('button.ok')}",
						value : true,
						focus : true,
						fn : function(value, dialog) {
							SubForm_Copy_Ok();
							dialog.hide(value);
						}
					}, {
						name : "${lfn:message('button.cancel')}",
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					} ]
				}
			}
		}).show();
	});
}

function SubForm_Copy_Ok(){
	var isCopy =  $("input[name='subform_copy']:checked" ).val();
	//#135384 start 判断是否显示于业务建模，业务建模采用ifram内嵌该页面，需要从父级文档取值
	var fdMainModelName = '${JsParam.fdMainModelName}';
	if(fdMainModelName && -1< fdMainModelName.indexOf('com.landray.kmss.sys.modeling')){
		isCopy =  $(parent.document).find("input[name='subform_copy']:checked" ).val();
	}
	//#135384 end
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		if(SubFormData.CopyDom != null){
			if(isCopy=='copy'){
				SubForm_AddRow(SubFormData.CopyDom,true);
			}else{
				//当前表单行
				var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
				//用来覆盖的行
				var mytr = $(SubFormData.CopyDom).parents("tr[ischecked]");
				//防止有未同步的控件
				SubFormData.needLoad_subform = false;
				mytr.find("[name='subFormText']").click();
				tr.find("[name='subFormText']").click();
				SubFormData.needLoad_subform = true;
				if(tr[0]!=mytr[0]){
					var newHtml = mytr.find("input[name$='fdDesignerHtml']").val();
					var vChar="\u4645\u5810\u4d40";
					if(newHtml.indexOf(vChar)>=0){
						var vData={"fdDesignerHtml":newHtml};
						var vHtml=postRequestServers(vData);
						newHtml=vHtml.html;
					}
					customIframe.Designer.instance.setHTML(newHtml);
					mytr.find("[name='subFormText']").click();
					tr.find("[name='subFormText']").click();
					SubForm_afterSwitch(customIframe);
					subform_rebuildControl();
				}
			}
		}
	}
}

function SubForm_Click(dom){
	SubForm_SetChecked($(dom).parents("tr[ischecked]"),false);
    SubForm_ResetUndo();
}

function SubForm_ResetUndo() {
    var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
    if(!customIframe){
        customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
    }
    if(customIframe && typeof customIframe.Designer != "undefined" && typeof customIframe.Designer.instance != "undefined"){
        var history = customIframe.Designer.instance.historys;
        if (history != null) {
            history.redos=[];//重做区
            history.undos=[];//撤销区
            customIframe.DesignerUndoSupport.saveOperation();
        }
    }
}

function SubForm_filterXml(str){
	if(!str){
		return "";
	}
	//将xml中连续两个以上的空格 替换为 一个
	str=str.replace(/\s{2,}/g," ");
	//替换掉换行
	str=str.replace(/[\r\n]+/g," ");
	//将xml中label 属性去除。（数据字典中label属性的改变不列如入默认作为新模版的条件）
	str=str.replace(/\s+label=\s*[\S]+\s*/gi," ");
	return str;
}

function SubForm_SetChecked(dom,isCopy,copyDom){
	//切换前选中的表单行
	var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	$("#TABLE_DocList_SubForm").find("tr").each(function(){
		if($(this)[0]!=dom[0]){
			$(this).attr("ischecked","false");
			$(this).find("a").css("color","");
		}else{
			$(this).find("a").css("color","#47b5e6");
			$(this).attr("ischecked","true");
		}
	});
	//切换后选中的表单行
	var tr2 = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		if(tr[0]==tr2[0]){
			
		}else{
			var mySubform = {};
			mySubform.id = tr2.attr("id");
			mySubform.link = tr2.find("[name='subFormText']");
			mySubform.fdDesignerHtmlObj = tr2.find("input[name$='fdDesignerHtml']");
			customIframe.Designer.instance.subForm = mySubform;
			//切换前保存上一个表单信息
			var fdDesignerHtml = customIframe.Designer.instance.getHTML();
			var fdMetadataXml = customIframe.Designer.instance.getXML();
			var myfdDesignerHtml = tr.find("input[name$='fdDesignerHtml']");
			var myfdMetadataXml = tr.find("input[name$='fdMetadataXml']");
			if(fdDesignerHtml!=myfdDesignerHtml.val()){
				myfdDesignerHtml.val(fdDesignerHtml);
				SubFormData.isChanged = true;
			}
			if(fdMetadataXml!=myfdMetadataXml.val()){
				//是否保存新版本
				if(SubForm_filterXml(fdMetadataXml)!=SubForm_filterXml(myfdMetadataXml.val())){
					SubFormData.saveasNew_subform = true;
				}
				myfdMetadataXml.val(fdMetadataXml);
			}
			var newHtml = "";
			if(isCopy){
				customIframe.Designer.instance.subForms.push(mySubform);
				var tr3 = $(copyDom).parents("tr[ischecked]");
				newHtml = tr3.find("input[name$='fdDesignerHtml']").val();
			}else{
				newHtml = tr2.find("input[name$='fdDesignerHtml']").val();
			}
			document.getElementsByName("${sysFormTemplateFormPrefix}fdCss")[0].value=tr2.find("input[name$='fdCss']").val();
			document.getElementsByName("${sysFormTemplateFormPrefix}fdCssDesigner")[0].value=tr2.find("input[name$='fdCssDesigner']").val();
			if(newHtml){
				var vChar="\u4645\u5810\u4d40";
				if(newHtml.indexOf(vChar)>=0){
					var vData={"fdDesignerHtml":newHtml};
					var vHtml=postRequestServers(vData);
					newHtml=vHtml.html;
				}
				customIframe.Designer.instance.subFormAddRow = true;
				customIframe.Designer.instance.setHTML(newHtml);
				customIframe.Designer.instance.subFormAddRow = false;
				if(!isCopy && customIframe.Designer.instance.synchronousInfos.length>0){
					//同步控件信息
					SubForm_Synchronous_control(customIframe,mySubform.id);
				}
			}else{
				customIframe.Designer.instance.subForms.push(mySubform);
				customIframe.Designer.instance.subFormAddRow = true;
				customIframe.Designer.instance.setHTML("&nbsp;");
				customIframe.Designer.instance.subFormAddRow = false;
				customIframe.Designer.instance.control = null;
				customIframe.Designer.instance.builder.createControl('standardTable'); 
				var currentFormId = customIframe.Designer.instance.subForm.id;
				if (currentFormId) {
					var controls = customIframe.Designer.instance.subFormControls[currentFormId];
					var tableControl = controls[0];
					if (tableControl && tableControl.type == "standardTable") {
						tableControl.options.values.isDefault = true;
						
					}
				}
			}
			SubForm_afterSwitch(customIframe);
			//切换后显示需要加载的信息
			if(SubFormData.needLoad_subform){
				SubForm_Load();
			}
		}
	}
}

function SubForm_Synchronous_control(win,currId){
	var synchronousInfos = win.Designer.instance.synchronousInfos;
	var allControls = win.Designer.instance.subFormControls;
	for(var i = 0;i<synchronousInfos.length;i++){
		var subformId = synchronousInfos[i].subformId;
		if(subformId!=currId){
			continue;
		}
		var control = synchronousInfos[i].control;
		var values = control.options.values;
		for(var p = 0;p<allControls[subformId].length;p++){
			if(values.id == allControls[subformId][p].options.values.id){
				var myControl = allControls[subformId][p];
				var attrs = myControl.attrs;
				for(var mykey in attrs){
					if((!attrs[mykey]["synchronous"] && !attrs[mykey]["lang"]) || values[mykey] === undefined){
						continue;
					}
					if(typeof values[mykey] !== "undefined"){
						myControl.options.values[mykey] = values[mykey];
					}
				}
				if(values["_label_bind"]){
					myControl.options.values["_label_bind"] = values["_label_bind"];
				}
				if(myControl.options.values["_label_bind"]&&values["_label_bind_id"]!=myControl.options.values["_label_bind_id"]){
					myControl.options.values["_label_bind"] = false;
				}
				win.Designer.instance.builder.setProperty(myControl);
				synchronousInfos.splice(i,1);
				i--;
				break;
			}
		}
	}
}

function SubForm_afterSwitch(customIframe){
	// 切换表单后调整虚线框位置
	customIframe.Designer.instance.builder.resetDashBoxPos();
	//取消工具栏选中状态
	customIframe.Designer.instance.builder.owner.toolBar.cancelSelect();
	//恢复鼠标样式
	customIframe.Designer.instance.builder.domElement.style.cursor = 'default';
	//清掉载入功能待选控件对象
	customIframe.Designer.instance.createSubformControl = "";
	//关闭开着的属性面板
	var attrPanel = customIframe.Designer.instance.attrPanel;
	var treePanel = customIframe.Designer.instance.treePanel;
	if (!treePanel.isClosed){
		treePanel.close();
		//关闭权限区段
		customIframe.Designer_Control_Rigth_LoopRightControls(customIframe.Designer.instance.builder.controls, function(c) {
			c.showRightConfig(false);
			c.showRightDefaultCell();
		});
	}
	if (!attrPanel.isClosed){
		attrPanel.close();
	}
}

function postRequestServers(arg) {
	var result = null;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_bean=convertBase64ToHtmlService",
		async: false,
		data: arg,
		type: "POST",
		dataType: 'json',
		success: function (data) {
			result = data;
		},
		error: function (er) {

		}
	});
	return result;
}

function SubForm_Edit(self){
	var parent = $(self).parents("tr[ischecked]");
	parent.find("a[name='subFormText']").hide();
	parent.find("input[name$='fdName']").show();
	parent.find("input[name$='fdName']").select();
	SubForm_SetChecked(parent,false);
}

function SubForm_TextBlur(self){
	var value = self.value;
	//校验表单名称是否为空
	if(!value){
		$(self).attr("onblur","");
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			dialog.alert("<bean:message bundle="sys-xform" key="sysSubFormTemplate.notnull" />",function(){
				$(self).attr("onblur","SubForm_TextBlur(this)");
				$(self).select();
			});
		});
		return
	}
	//校验表单名称是否存在
	var input = $("#TABLE_DocList_SubForm").find("[name$='fdName']");
	for(var i =0;i<input.length;i++){
		if(input[i]!=self && input[i].value==value){
			$(self).attr("onblur","");
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				dialog.alert("<bean:message bundle="sys-xform" key="sysSubFormTemplate.check_name" />",function(){
					$(self).attr("onblur","SubForm_TextBlur(this)");
					$(self).select();
				});
			});
			return
		}
	}
	var a = $(self).parent().find("a[name='subFormText']");
	a.attr("title",value);
	if(value.length>10){
		value = value.substring(0,10)+"..."
	}
	a.text(value);
	a.show();
	$(self).hide();
}

function SubForm_Load(){
	//先保存当前选中的表单信息
	var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	var fdDesignerHtml,fdMetadataXml;
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		fdDesignerHtml = customIframe.Designer.instance.getHTML();
		fdMetadataXml = customIframe.Designer.instance.getXML();
	}
	var myfdDesignerHtml = tr.find("input[name$='fdDesignerHtml']");
	var myfdMetadataXml = tr.find("input[name$='fdMetadataXml']");
	if(fdDesignerHtml!=myfdDesignerHtml.val()){
		myfdDesignerHtml.val(fdDesignerHtml);
		SubFormData.isChanged = true;
	}
	if(fdMetadataXml!=myfdMetadataXml.val()){
		//是否保存新版本
		if(SubForm_filterXml(fdMetadataXml)!=SubForm_filterXml(myfdMetadataXml.val())){
			SubFormData.saveasNew_subform = true;
		}
		myfdMetadataXml.val(fdMetadataXml);
	}
	
	var fdId = [];
	var fdXml = [];
	$("#TABLE_DocList_SubForm").find("tr").each(function(){
		fdId.push($(this).attr("id"));
		var myfdMetadataXml = $(this).find("input[name$='fdMetadataXml']");
		fdXml.push(encodeURIComponent(myfdMetadataXml.val()));
	});
	var url = Com_Parameter.ContextPath + "sys/xform/sys_form_template/sysFormTemplate.do?method=parseXML";
	var data = {"fdId":fdId.join("&&"),"fdMetadataXml":fdXml.join("&&")};
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		success : function(json){
			if(json){
				SubForm_LoadInfo(json,customIframe);
			}
		},
		dataType: 'json'
	});
}

function SubForm_LoadInfo(data,win){
	//清空控件div中信息
	$("#controlsDiv").html("");
	var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	var myId = tr.attr("id");
	//当前选中表单有的控件
	var myData = [];
	//其他表单有的控件
	var otherData = [];
	//当前选中表单未有的控件ids
	var needData = [];
	for(var i =0;i<data.length;i++){
		if(data[i].subFormId==myId){
			myData.push(data[i]);
		}else{
			otherData.push(data[i]);
		}
	}
	for(var j =0;j<otherData.length;j++){
		var isExit = false;
		for(var k =0;k<myData.length;k++){
			if(otherData[j].id==myData[k].id){
				isExit = true;
				break;
			}
		}
		if(!isExit){
			var isNeed = true;
			for(var l=0;l<needData.length;l++){
				if(otherData[j].id.indexOf(".")>=0){
					if(otherData[j].id.substring(otherData[j].id.indexOf(".")+1) == needData[l]){
						isNeed = false;
						break;
					}
				}else{
					if(otherData[j].id == needData[l]){
						isNeed = false;
						break;
					}
				}
			}
			if(isNeed){
				var myId = otherData[j].id;
				if(myId.indexOf(".")>=0){
					myId = myId.substring(myId.indexOf(".")+1);
				}
				needData.push(myId);
			}
		}
	}
	var controlsDiv = $("#controlsDiv");
	if(needData.length>0){
		$("#SubFormLoadMsg").show();
	}else{
		$("#SubFormLoadMsg").hide();
	}
	for(var m = 0;m<needData.length;m++){
		//所有控件对象
		var mycontrols = win.Designer.instance.subFormControls;
		for(var key in mycontrols){
			var state = false;
			for(var p = 0;p<mycontrols[key].length;p++){
				if(needData[m]==mycontrols[key][p].options.values.id){
					if (mycontrols[key][p].type === "uploadTemplateAttachment"){
						break;
					}
					var __text = mycontrols[key][p].options.values.label || mycontrols[key][p].options.values.id;
					controlsDiv.append('<div class="subform_panel_normal" onclick="SubForm_LoadInfo_Click(this);" onmouseover="SubForm_LoadInfo_Mouseover(this);" onmouseout="SubForm_LoadInfo_Mouseout(this);" id="subform_'+needData[m]+'">'+__text+'('+win.Designer_Config.controls[mycontrols[key][p].type].info.name+')</div>');
					state = true;
					break;
				}
			}
			if(state){
				break;
			}
		}
	}
}

function SubForm_LoadInfo_Click(self){
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		var myControl = null;
		var mycontrols = customIframe.Designer.instance.subFormControls;
		for(var key in mycontrols){
			var state = false;
			for(var p = 0;p<mycontrols[key].length;p++){
				if(self.id=="subform_"+mycontrols[key][p].options.values.id){
					myControl = mycontrols[key][p];
					state = true;
					break;
				}
			}
			if(state){
				break;
			}
		}
		if(myControl){
			var builder = customIframe.Designer.instance.builder;
			var control = new customIframe.Designer_Control(builder, myControl.type, false);
			//设置初始值
			control.options.values = myControl.options.values;
			control.options.values._label_bind = false;
			control.options.values._label_bind_id = '';
			var selectedDom = customIframe._Designer_FieldPanel_GetSelectedDom();
			if(selectedDom && selectedDom.tagName && selectedDom.tagName == 'TD'){
				builder.createControl(control,selectedDom);
				$("#subform_"+control.options.values.id).remove();
			}else{
				builder.owner.toolBar.selectButton(myControl.type);	
				if(customIframe.Designer.instance.createSubformControl){
					if(customIframe.Designer.instance.createSubformControl.options.values.id==control.options.values.id){
						$("#subform_"+control.options.values.id).css("color","black");
						customIframe.Designer.instance.createSubformControl = '';
						builder.owner.toolBar.cancelSelect();
					}else{
						$("#subform_"+customIframe.Designer.instance.createSubformControl.options.values.id).css("color","black");
						customIframe.Designer.instance.createSubformControl = control;
						$("#subform_"+control.options.values.id).css("color","blue");
					}
				}else{
					customIframe.Designer.instance.createSubformControl = control;
					$("#subform_"+control.options.values.id).css("color","blue");
				}
			}
		}
	}
}

function SubForm_LoadInfo_Mouseover(self){
	$(self).attr("class","subform_panel_mouseover");
}

function SubForm_LoadInfo_Mouseout(self){
	$(self).attr("class","subform_panel_normal");
}

function SubForm_DeleteRow(dom){
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog,topic) {
		dialog.confirm("<bean:message bundle="sys-xform" key="sysSubFormTemplate.confirm_deletion" />",function(data){
			if(data){
				var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
				if(!customIframe){
					customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
				}
				//删除行
				var parent = $(dom).parents("tr[ischecked]");
				if(parent.attr("defaultWebForm") && parent.attr("defaultWebForm")=="true"){
					$("#newDefaultWebForm").show();
				}
				if(customIframe.Designer != null && customIframe.Designer.instance != null){
					var subForms = customIframe.Designer.instance.subForms;
					for (var i = 0; i < subForms.length; i++){
						if (subForms[i].id == parent.attr("id")) {
							subForms.splice(i, 1);
							break;
						}
					}
				}
				var delId = parent.attr("id");
				DocList_DeleteRow(parent[0]);
				var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
				if(tr.length==0){
					var defaultTr = $("#TABLE_DocList_SubForm").find('tr:eq(0)');
					var mySubform = {};
					mySubform.id = defaultTr.attr("id");
					mySubform.link = defaultTr.find("[name='subFormText']");
					mySubform.fdDesignerHtmlObj = defaultTr.find("input[name$='fdDesignerHtml']");
					customIframe.Designer.instance.subForm = mySubform;
					defaultTr.find("a").css("color","#47b5e6");
					defaultTr.attr("ischecked","true");
					if(customIframe.Designer != null && customIframe.Designer.instance != null){
						var html = defaultTr.find("input[name$='fdDesignerHtml']").val();
						document.getElementsByName("${sysFormTemplateFormPrefix}fdCss")[0].value=defaultTr.find("input[name$='fdCss']").val();
						document.getElementsByName("${sysFormTemplateFormPrefix}fdCssDesigner")[0].value=defaultTr.find("input[name$='fdCssDesigner']").val();
						customIframe.Designer.instance.setHTML(html);
						SubForm_afterSwitch(customIframe);
					}
					$('#SubFormDiv').scrollTop(0);
				}
				subform_rebuildControl();
				topic.publish("subForm_del",{id:delId});
			}
		});
	});
}

function subform_rebuildControl(){
	SubFormData.needLoad_subform = false;
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	//初始化前选中的表单
	var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		//多表单删除时，需要重新初始化所有控件对象
		customIframe.Designer.instance.subFormControls={};
		
		for(var i = 0; i< customIframe.UU_lang_arr.length; ++i){
			
			if(customIframe.UU_lang_arr[i].model !== undefined){
				
				var model = customIframe.UU_lang_arr[i].model;
				
				// model 临时数据
				customIframe._UU_lang_arr_models[model.get("c_id")] = $.extend(true,{},model.attributes);
				// 销毁中间件
				model.destroy();
			}
		}
		customIframe.UU_lang_arr = [];
		//当前选中的表单为默认表单时，先初始化默认表单
		if(tr.attr("id")=="subform_default"){
			customIframe.Designer.instance.setHTML(tr.find("input[name$='fdDesignerHtml']").val());
		}
		$("#TABLE_DocList_SubForm").find('tr:gt(0)').each(function(){
			$(this).find("a[name='subFormText']").click();
		});
		//防止默认表单为当前选中表单时未初始化
		var defaultTr = $("#TABLE_DocList_SubForm").find('tr:eq(0)');
		defaultTr.find("a[name='subFormText']").click();
	}
	tr.find("a[name='subFormText']").click();
	SubFormData.needLoad_subform = true;
	SubFormData.isChanged = true;
	SubFormData.saveasNew_subform = true;
	if(SubFormData.needLoad_subform){
		SubForm_Load();
	}
}

function Designer_SubForm_Xml(){
	var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	var fdDesignerHtml,fdMetadataXml;
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		//先保存当前选中的表单
		fdDesignerHtml = customIframe.Designer.instance.getHTML();
		fdMetadataXml = customIframe.Designer.instance.getXML();
		var myfdDesignerHtml = tr.find("input[name$='fdDesignerHtml']");
		var myfdMetadataXml = tr.find("input[name$='fdMetadataXml']");
		if(fdDesignerHtml!=myfdDesignerHtml.val()){
			myfdDesignerHtml.val(fdDesignerHtml);
			SubFormData.isChanged = true;
		}
		if(fdMetadataXml!=myfdMetadataXml.val()){
			//是否保存新版本
			if(SubForm_filterXml(fdMetadataXml)!=SubForm_filterXml(myfdMetadataXml.val())){
				SubFormData.saveasNew_subform = true;
			}
			myfdMetadataXml.val(fdMetadataXml);
		}
		
		//防止有未同步的控件
		SubFormData.needLoad_subform = false;
		$("#TABLE_DocList_SubForm").find("tr").each(function(){
			$(this).find("a[name='subFormText']").click();
		});
		tr.find("a[name='subFormText']").click();
		SubFormData.needLoad_subform = true;
	}
	this.width=window.screen.width*900/1366;
	this.height=window.screen.height*480/768;
	var left = (screen.width-this.width)/2;
	var top = (screen.height-this.height)/2;
	var url = '<c:url value="/sys/xform/base/sysSubFormTemplate_xml.jsp"/>';
	var winStyle = "resizable=1,scrollbars=1,width="+this.width+",height="+this.height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
	window.open(url, "_blank", winStyle);
}

function _Designer_SubFormSetCss(str,styleStr){
	var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	if(tr.length>0){
		tr.find("input[name$='fdCssDesigner']").val(str);
		tr.find("input[name$='fdCss']").val(styleStr);
	}
}

function SUBForm_Loading(){
	SubForm_AdjustEditHeight();
	var fdModeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
	var fdModelValue = 0;
	if(fdModeObj && fdModeObj != null){
		fdModelValue = fdModeObj.value;
	}
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	var method = Com_GetUrlParameter(location.href,"method");
	if(fdModelValue=="<%=XFormConstant.TEMPLATE_SUBFORM%>" && method=='edit'){
		//再次编辑时，给基础表单行设置数据，防止未做操作直接保存时取不到值
		var defaultTr = $("#TABLE_DocList_SubForm").find('tr:eq(0)');
		var fdIdObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdId")[0];
		var fdDesignerHtmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
		var fdMetadataXmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMetadataXml")[0];
		var fdCssObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdCss")[0];
		var fdCssDesignerObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdCssDesigner")[0];
		defaultTr.find("input[name$='fdId']").val(fdIdObj.value);
		defaultTr.find("input[name$='fdDesignerHtml']").val(fdDesignerHtmlObj.value);
		defaultTr.find("input[name$='fdMetadataXml']").val(customIframe.Designer.instance.getXML());
		defaultTr.find("input[name$='fdCss']").val(fdCssObj.value);
		defaultTr.find("input[name$='fdCssDesigner']").val(fdCssDesignerObj.value);
		//多表单编辑时，载入功能需要初始化所有控件对象
		if(customIframe.Designer != null && customIframe.Designer.instance != null){
			if($("#TABLE_DocList_SubForm").find('tr[ischecked]').length>1){
				SubFormData.needLoad_subform = false;
				$("#TABLE_DocList_SubForm").find('tr:gt(0)').each(function(){
					if ($(this).attr("id")) {
						$(this).find("a[name='subFormText']").click();
						var mySubform = {};
						mySubform.id = $(this).attr("id");
						if (typeof mySubform.id !== "undefined") {
							mySubform.link = $(this).find("[name='subFormText']");
							mySubform.fdDesignerHtmlObj = $(this).find("input[name$='fdDesignerHtml']");
							customIframe.Designer.instance.subForms.push(mySubform);
							customIframe.Designer.instance.storeOldFdValues();
						}
					}
				});
				SubFormData.needLoad_subform = true;
				defaultTr.find("a[name='subFormText']").click();
			}else{
				if(fdDesignerHtmlObj.value){
					customIframe.Designer.instance.setHTML(fdDesignerHtmlObj.value);
				}
			}
		}
		//多表单编辑时，加载是否映射信息
		var url = Com_Parameter.ContextPath + "sys/xform/sys_form_template/sysFormTemplate.do?method=isWriteToDb";
		var data = {"id":fdIdObj.value};
		$.ajax({
			type : "POST",
			data : data,
			url : url,
			async: false,
			success : function(json){
				if(json){
					SubFormData.isWriteDbInfos = json;
				}
			},
			dataType: 'json'
		});
	} 
	var webform = $("#TABLE_DocList_SubForm").find("tr[defaultWebForm='true']");
	if(webform.length>0){
		$("#newDefaultWebForm").hide();
		var title = "<bean:message bundle="sys-xform" key="sysSubFormTemplate.edit" />";
		webform.find("img[title="+title+"]").parent().hide();
		webform.find("a[name='subFormText']").removeAttr("ondblclick");
	}
}

function SubForm_checkAll(){
	var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	var fdDesignerHtml,fdMetadataXml;
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		fdDesignerHtml = customIframe.Designer.instance.getHTML();
		fdMetadataXml = customIframe.Designer.instance.getXML();
	}
	var myfdDesignerHtml = tr.find("input[name$='fdDesignerHtml']");
	var myfdMetadataXml = tr.find("input[name$='fdMetadataXml']");
	
	if(fdDesignerHtml!=myfdDesignerHtml.val()){
		myfdDesignerHtml.val(fdDesignerHtml);
		SubFormData.isChanged = true;
	}
	
	if(fdMetadataXml!=myfdMetadataXml.val()){
		//校验是否保存新版本
		if(SubForm_filterXml(fdMetadataXml)!=SubForm_filterXml(myfdMetadataXml.val())){
			SubFormData.saveasNew_subform = true;
		}
		myfdMetadataXml.val(fdMetadataXml);
	}
	
	var fdId = [];
	var fdXml = [];
	$("#TABLE_DocList_SubForm").find("tr").each(function(){
		fdId.push($(this).attr("id"));
		var myfdMetadataXml = $(this).find("input[name$='fdMetadataXml']");
		fdXml.push(encodeURIComponent(myfdMetadataXml.val()));
	});
	var url = Com_Parameter.ContextPath + "sys/xform/sys_form_template/sysFormTemplate.do?method=parseXML";
	var data = {"fdId":fdId.join("&&"),"fdMetadataXml":fdXml.join("&&")};
	var bool = false;
	$.ajax({
		type : "POST",
		data : data,
		url : url,
		async: false,
		success : function(json){
			if(json){
				bool = SubForm_submit_check(json);
			}
		},
		dataType: 'json'
	});
	return bool;
}

function getSubFormNameByTrId(id){
	return name = $("#TABLE_DocList_SubForm").find("tr#"+id).find("input[name$='fdName']").val();
}

function SubForm_submit_check(data){
	for(var i = 0;i<data.length;i++){
		for(var j = i+1;j<data.length;j++){
			if(data[i].id.indexOf(".")>0){
				if(data[j].id.indexOf(".")>0){
					if(data[i].id.substring(data[i].id.indexOf(".")+1)==data[j].id.substring(data[j].id.indexOf(".")+1)&&data[i].id.substring(0,data[i].id.indexOf("."))!=data[j].id.substring(0,data[j].id.indexOf("."))){
						alert(data[i].label+"<bean:message bundle="sys-xform" key="sysSubFormTemplate.check" />"+getSubFormNameByTrId(data[i].subFormId)+"、"+getSubFormNameByTrId(data[j].subFormId)+"<bean:message bundle="sys-xform" key="sysSubFormTemplate.check_msg" />");
						return false;
					}
				}else{
					if(data[i].id.substring(data[i].id.indexOf(".")+1)==data[j].id){
						alert(data[i].label+"<bean:message bundle="sys-xform" key="sysSubFormTemplate.check" />"+getSubFormNameByTrId(data[i].subFormId)+"、"+getSubFormNameByTrId(data[j].subFormId)+"<bean:message bundle="sys-xform" key="sysSubFormTemplate.check_msg" />");
						return false;
					}
				}
			}
			if(data[i].id==data[j].id && data[i].type!=data[j].type){
				alert(data[i].label+" <bean:message bundle="sys-xform" key="sysSubFormTemplate.check_type_msg" />");
				return false;
			}
			if(data[i].id==data[j].id && data[i].label!=data[j].label){
				alert(data[i].label+" <bean:message bundle="sys-xform" key="sysSubFormTemplate.check_label_msg" />");
				return false;
			}
		}
	}
	return true;
}

function SubForm_AdjustEditHeight(){
	var context = document.getElementById("TB_FormTemplate_${JsParam.fdKey}");
	if (!context) {
		context =  document.getElementById("TD_FormTemplate_${JsParam.fdKey}");
	}
	var height = context.offsetHeight-2;
	var myhight = height-38-19;
	$("#SubFormDiv").css("height",myhight*0.5);
	$("#controlsDiv").css("height",myhight*0.5);
	var div = document.getElementById("DIV_SubForm_${JsParam.fdKey}");
	$(div).css("height",height);
}

function SubForm_OnLabelSwitch(tableName,index){
	var trs = document.getElementById(tableName).rows;
	var tr = $("#TB_FormTemplate_${JsParam.fdKey}").closest("tr[LKS_LabelName]");
	if(trs[index] == tr[0]){
		setTimeout(function (){
			SubForm_AdjustEditHeight();
		},0);
	}
}

function XForm_getSubFormInfo_${JsParam.fdKey}() {
	var subObj = [];
	var modeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
	var modeValue = null;
	if(modeObj && modeObj != null){
		modeValue = modeObj.value;
	}
	if (modeValue == '<%=XFormConstant.TEMPLATE_SUBFORM%>') {
		var fdIdObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdId")[0];
		if (fdIdObj) {
			var pcDefaultId = fdIdObj.value;
		}
		$("#TABLE_DocList_SubForm").find("tr[ischecked]").each(function(i) {
			var subformObj = {};
			if(i==0){
				subformObj.id='default';
				subformObj.name='<bean:message bundle="sys-xform" key="sysSubFormTemplate.default_form" />';
			}else{
				if($(this).attr("defaultWebForm") && $(this).attr("defaultWebForm")=="true"){
					subformObj.id='default';
					subformObj.name='<bean:message bundle="sys-xform" key="sysSubFormTemplate.webform" />';
					subformObj.defaultWebForm = true;
				}else{
					subformObj.id=$(this).find("input[name$='fdId']").val();
					subformObj.name=$(this).find("input[name$='fdName']").val();
					var fdOldFormId = $(this).find("input[name$='fdOldFormId']").val();
					if (fdOldFormId) {
						subformObj.fdOldFormId = fdOldFormId;
					}
				}
			}
			subObj.push(subformObj);
		});
		//加入移动表单
		var hasMobileForm = false;
		var mobileDefaultForm;
		var defaultLayoutName = "${sysFormTemplateFormPrefix}fdUseDefaultLayout";
        var defaultLayout = $("[name='" + defaultLayoutName + "']").val();
        if (defaultLayout!="true") {
            $("#table_docList_${JsParam.fdKey}_form").find("tr[ischecked]").each(function(i) {
                var subformObj = {};
                hasMobileForm = true;
                subformObj.id=$(this).find("input[name$='fdId']").val();
                subformObj.name=$(this).find("input[name$='fdName']").val();
                var fdPcFormId = $(this).find("input[name$='fdPcFormId']").val();
                subformObj.fdPcFormId=fdPcFormId;
                var fdOldFormId = $(this).find("input[name$='fdOldFormId']").val();
                if (pcDefaultId === fdPcFormId) {
                    subformObj.mobileDefault = true;
                    mobileDefaultForm = subformObj;
                }
                if (fdOldFormId) {
                    subformObj.fdOldFormId = fdOldFormId;
                }
                subformObj.defaultWebForm = true;
                subformObj.mobileForm = "true";
                subObj.push(subformObj);
            });
            //移动默认放到移动第一个
            var mobileDefaultIndex;
            var mobileStartIndex;
            for (var i = 0; i < subObj.length; i++) {
                if (subObj[i].defaultWebForm) {
                    mobileStartIndex = i;
                    break;
                }
            }
            for (var i = 0; i < subObj.length; i++) {
                if (subObj[i].mobileDefault) {
                    mobileDefaultIndex = i;
                    break;
                }
            }
            if (mobileDefaultIndex && mobileStartIndex) {
                subObj[mobileDefaultIndex] = subObj[mobileStartIndex];
                subObj[mobileStartIndex] = mobileDefaultForm;
            }
        }
		//没有移动表单,则用默认pc表单
		if (!hasMobileForm) {
			if (subObj.length > 0) {
				var length = subObj.length;
				for (var i = 0; i < length; i++) {
					var subformObj = {};
					subformObj.id=subObj[i].id;
					subformObj.name=subObj[i].name;
					if(subObj[i].fdOldFormId) {
						subformObj.fdOldFormId = subObj[i].fdOldFormId;
					}
					subformObj.mobileForm = "true";
					subObj.push(subformObj);
				}
			}				
		}
	}
	return subObj;
}

function _Designer_SubFormAddHide(dom){
	if($("#SubForm_main_tr").is(":hidden")){
		$("#SubForm_main_tr").show();
		$(dom).attr("src","${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowleft.gif");
	}else{
		$("#SubForm_main_tr").hide();
		$(dom).attr("src","${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowright.gif");
	}
}
</script>