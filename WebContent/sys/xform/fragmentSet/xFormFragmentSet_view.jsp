<%@ page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="xFormTemplateForm" value="${sysFormFragmentSetForm}" />
<script>
	Com_IncludeFile("dialog.js");
	Com_Parameter.IsAutoTransferPara = true;
	function confirmDelete(msg) {
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	
	/*  Com_AddEventListener(window, 'load', function () {
		var table = document.getElementById("Label_Tabel");
		if(table!=null && window.Doc_AddLabelSwitchEvent){
			Doc_AddLabelSwitchEvent(table, "fragmentSet_OnLabelSwitch");
		} 
	});
	
	function fragmentSet_OnLabelSwitch(tableName,index){
		var trs = document.getElementById(tableName).rows;
		var tr = $("#TB_FormTemplate_${HtmlParam.fdKey}").closest("tr[LKS_LabelName]");
		var customIframe = window.frames['IFrame_FormTemplate_${HtmlParam.fdKey}'].contentWindow;
		if(!customIframe){
			customIframe = window.frames['IFrame_FormTemplate_${HtmlParam.fdKey}'];
		}
		if(trs[index] == tr[0]){
			setTimeout(function (){
				fragmentSet_AdjustViewHeight(customIframe);
			},300);
		}
	}
	
	function fragmentSet_AdjustViewHeight(iframe){
		iframe.height = screen.height - 350;
		if (iframe.Designer != null && iframe.Designer.instance != null){
			_height = iframe.document.getElementById("designPanel").offsetHeight;
			var _width = iframe.document.getElementById("designPanel").offsetWidth;
			var isIE = true;
			if(!(window.attachEvent && navigator.userAgent.indexOf('Opera') === -1)){
				isIE = false;
			}
			var widht = iframe.Designer.getDocumentAttr("scrollWidth");
			if (widht > _width){
				$(iframe.document.getElementById("designPanel")).css("overflow-x","scroll");
			}
			if(isIE){
				var height = document.body.scrollHeight + 17;
				if (height < 50) {
					iframe.height = 100;
					setTimeout(function(){fragmentSet_AdjustViewHeight(iframe);}, 300);
					return;
				} else {
					iframe.height = height;
				}	
				if (height < _height) {
					iframe.height = _height + 17;
				}
			}else{
				$(iframe).height(_height + 17);
			}
		}
	}   */
	
	
	 seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) { 
		var cateId = "${JsParam.categoryId}";
	 	//同步
 		window.synchronous = function(id){
 			var values = [];
 			var fdId = document.getElementsByName("fdId")[0].value;
 			if(id) {
 				values.push(id);
	 		} else {
	 			var context = document.getElementById("templateRefFragmentSet").contentWindow.document;
				$("input[name='List_Selected']:checked",context).each(function() {
					values.push($(this).val());
				});
	 		}
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			var iframe = document.getElementById("templateRefFragmentSet").contentWindow || document.getElementById("templateRefFragmentSet");
			
			//选择同步版本
			var url  = '<c:url value="/sys/xform/fragmentSet/xFormFragmentSet.do?method=synchronous"/>';
			action = function (rtnVal){
				if(!rtnVal || !rtnVal.data){
					return ;
				}
				$.ajax({
					url : url,
					type : 'POST',
					//fdId 指定需要同步到的片段集版本,fdRefId片段集,List_Selected为需要同步的表单模板
					data : $.param({"List_Selected" : values,"fdId":rtnVal.data[0].id,"isHistory":true,fdRefId:fdId,isLatest:true}, true),
					dataType : 'json',
					error : function(data) {
						if(window.del_load != null) {
							window.del_load.hide(); 
						}
						iframe.location.href=iframe.location.href;
						/* dialog.result(data.responseJSON); */
					},
					success: function(data) {
						if(window.del_load != null){
							window.del_load.hide(); 
							topic.publish("list.refresh"); 
						}
						iframe.location.href=iframe.location.href;
						dialog.result(data);
					}
			   });
			}
			var kmssDialog = new KMSSDialog(false);
			kmssDialog.BindingField("megerFragmentSetId", "megerFragmentSetName", null, null);
			kmssDialog.SetAfterShow(action);
			kmssDialog.URL = Com_Parameter.ContextPath + "sys/xform/fragmentSet/meger/sysFormFragmentSetHistory_select.jsp?fdId=" + fdId;
			kmssDialog.Show(window.screen.width*710/1366,window.screen.height*550/768);
		};
 	 }); 
	
</script>
<kmss:windowTitle moduleKey="sys-xform-fragmentSet:module.xform.manage"
	subjectKey="sys-xform-fragmentSet:table.sysFormFragmentSet"
	subject="${sysFormFragmentSetForm.fdName}" />

<div id="optBarDiv">
	<input type="button" value="<bean:message bundle="sys-xform-fragmentSet" key="button.synchronous"/>"
		onclick="synchronous();">
	<kmss:auth
		requestURL="/sys/xform/fragmentSet/xFormFragmentSet.do?method=edit&fdId=${param.fdId}"
		requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('xFormFragmentSet.do?method=edit&fdId=${JsParam.fdId}&fdModelName=${param.fdModelName}&fdKey=${param.fdKey}&fdMainModelName=${param.fdMainModelName}','_self');">
	</kmss:auth>
	<kmss:auth
		requestURL="/sys/xform/fragmentSet/xFormFragmentSet.do?method=delete&fdId=${param.fdId}"
		requestMethod="GET">
		<input type="button" value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('xFormFragmentSet.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle">
	<bean:message bundle="sys-xform-fragmentSet" key="table.sysFormFragmentSet" />
</p>
<script>
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
</script> 
<center>
	<html:hidden name="sysFormFragmentSetForm" property="fdId" />
	<table id="Label_Tabel" width=95%>
		<!-- 基本信息 -->
		<tr LKS_LabelName="<bean:message bundle='sys-xform-fragmentSet' key='sysFormFragmentSet.basicInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdName" />
							<!-- 需要合并的片段集id -->
							<div style="display:none;">
								<input  type="hidden" name="megerFragmentSetId" value=""/>
								<input  type="hidden" name="megerFragmentSetName" value=""/>		
							</div>
						</td>
						<td width=85% colspan="3">
							<bean:write name="sysFormFragmentSetForm" property="fdName" />
						</td>
					</tr>
					<%--适用类别--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdCatoryName" />
						</td>
						<td width=85% colspan="3">
							<bean:write name="sysFormFragmentSetForm" property="fdCategoryName" />
						</td>
					</tr>
					<!-- 排序号 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message	bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdOrder" />
						</td>
						<td width=85% colspan="3">
							<bean:write property="fdOrder" name="sysFormFragmentSetForm" />
						</td>
					</tr>
					<!-- 使用范围 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.scope" />
						</td>
						<td width=85% colspan="3">
							<bean:write property="fdScopeName" name="sysFormFragmentSetForm" />
						</td>
					</tr>
					
					<tr>
						<!-- 创建人员 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docCreatorId" />
						</td>
						<td width=35%>
							<bean:write property="docCreatorName" name="sysFormFragmentSetForm"/>
						</td>
						
						<!-- 创建时间 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docCreateTime" />
						</td>
						<td width=35%>
							<bean:write property="docCreateTime"  name="sysFormFragmentSetForm"/>
						</td>
					</tr>
					
					<tr>
						<!-- 修改人 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docAlteror" />
						</td>
						<td width=35%>
							<bean:write name="sysFormFragmentSetForm" property="docAlterorName" />
						</td>
						
						<!-- 修改时间 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docAlterTime" />
						</td>
						<td width=35%>
							<bean:write name="sysFormFragmentSetForm" property="docAlterTime" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<!-- 片段集 -->
		<tr LKS_LabelName="<bean:message bundle='sys-xform-fragmentSet' key='sysFormFragmentSet.templateSet'/>">
			<td>
				<table class="tb_normal" width=100% id="TB_FormTemplate_${HtmlParam.fdKey}">
					<%@ include file="/sys/xform/base/sysFormTemplateDisplay_view.jsp"%>
					<%--片段集变更记录 --%>
					<c:import url="/sys/xform/fragmentSet/history/xFormFragmentSetHistory_view.jsp"	charEncoding="UTF-8">
					</c:import>
				</table>
			</td>
		</tr>
		
		<!-- 被引用表单模板 -->
		<c:import url="/sys/xform/fragmentSet/ref/sysFormRefFragmentSet_view.jsp" charEncoding="UTF-8">
			<c:param name="isHistory" value="false"></c:param>
		</c:import>
	</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>