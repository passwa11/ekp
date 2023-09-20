<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<style>
.subTable {
	border:none ! important;
}
.subTable td {
	border:none ! important;
}
.subTable a{
	cursor: pointer;
} 
</style>
<table class="tb_normal subTable" width=100% id="TABLE_DocList_Print" align="center" style="table-layout:fixed;" frame=void>
	<tr ischecked="true" id="defaultPrint">
		<td width=65%>
			<input type="hidden" name="MyfdId" />
			<a name="subPrintText" style="font-weight:bold;color:#47b5e6;margin-left:4px;" onclick="SubPrint_Click(this);" title="<kmss:message key="sys-print:sysPrint.default_print_nonhandlers" />"><kmss:message key="sys-print:sysPrint.default_print" /></a>
			<input type="hidden" name="MyfdName" value="<kmss:message key="sys-print:sysPrint.default_print" />"/>
			<input type="hidden" name="MyfdTmpXml" />
			<input type="hidden" name="MyfdCss"/>
		</td>
		<%--操作按钮--%>
		<td width="35%" align="right">
			<a href="javascript:void(0)" onclick="SubPrint_Copy(this);" style="margin-left:4px;">
				<img src="${LUI_ContextPath}/sys/print/resource/icon/copy.png" border="0" title="<kmss:message key="sys-print:sysPrint.copy" />"/>
			</a>
			<%-- <a href="javascript:void(0)" onclick="addRow_print(this,false);">
				<img src="${KMSS_Parameter_StylePath}/icons/add.gif" border="0" title="<kmss:message key="sys-print:sysPrint.add" />"/>
			</a> --%>
			<%-- <a href="javascript:void(0)" onclick="SubPrint_Load(this);">
				<img src="${KMSS_Parameter_StylePath}/icons/link_info.gif" border="0" title="<kmss:message key="sys-print:sysPrint.load" />"/>
			</a> --%>
		</td>
	</tr>
	<!-- 基准行 -->
	<tr KMSS_IsReferRow="1" style="display: none">
		<td>
			<a name="subPrintText" style="font-weight:bold;margin-left:4px;" onclick="SubPrint_Click(this);" ondblclick="SubPrint_Edit(this);"></a>
			<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[!{index}].fdId" />
			<xform:text	property="sysPrintTemplateForm.fdSubTemplates[!{index}].fdName" showStatus="edit" style="width:90%" htmlElementProperties="onblur='SubPrint_TextBlur(this);'" validators="maxLength(200)"/>
			<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[!{index}].fdTmpXml" />
			<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[!{index}].fdCss"/>
			<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[!{index}].fdModelName"/>
		</td>
		<!-- 编辑、复制、删除按钮 -->
		<td align="right">
			<a href="javascript:void(0)" onclick="SubPrint_Copy(this);">
				<img src="${LUI_ContextPath}/sys/print/resource/icon/copy.png" border="0" title="<kmss:message key="sys-print:sysPrint.copy" />"/>
			</a>
			<a href="javascript:void(0)" onclick="SubPrint_Edit(this);">
				<img src="${LUI_ContextPath}/sys/print/resource/icon/edit.png" border="0" title="<kmss:message key="sys-print:sysPrint.edit" />"/>
			</a>
			<a href="javascript:void(0)" onclick="deleteRow_print(this);">
				<img src="${LUI_ContextPath}/sys/print/resource/icon/delete.png" border="0" title="<kmss:message key="sys-print:sysPrint.delete" />"/>
			</a>
		</td>
	</tr>
	
	<c:forEach items="${sysPrintTemplateForm.fdSubTemplates}" var="item" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" ischecked="false" id="${item.fdId}">
			<td>
				<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[${vstatus.index}].fdId" value="${item.fdId}" /> 
				<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[${vstatus.index}].fdParentId" value="${sysPrintTemplateForm.fdId }" />
				<xform:text showStatus='edit' property="sysPrintTemplateForm.fdSubTemplates[${vstatus.index}].fdName" value="${item.fdName}" style="width:90%;display:none" htmlElementProperties="onblur='SubPrint_TextBlur(this);'" validators="maxLength(200)" />
				<a name="subPrintText" style="font-weight:bold;margin-left:4px;" onclick="SubPrint_Click(this);" ondblclick="SubPrint_Edit(this);" title="<c:out value='${item.fdName}'/>"><c:out value='${item.fdName}'/></a>
				<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[${vstatus.index}].fdTmpXml" value="<c:out value='${item.fdTmpXml}'/>"/>
				<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[${vstatus.index}].fdCss" value="<c:out value='${item.fdCss}'/>"/>
				<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[${vstatus.index}].fdOldId" value="<c:out value='${item.fdOldId}'/>"/>
				<input type="hidden" name="sysPrintTemplateForm.fdSubTemplates[${vstatus.index}].fdModelName" value="<c:out value='${item.fdModelName}'/>"/>
			</td>
			<!-- 操作按钮 -->
			<td align="right">
				<a href="javascript:void(0)" onclick="SubPrint_Copy(this);">
					<img src="${LUI_ContextPath}/sys/print/resource/icon/copy.png" border="0" title="<kmss:message key="sys-print:sysPrint.copy" />"/>
				</a>
				<a href="javascript:void(0)" onclick="SubPrint_Edit(this);">
					<img src="${LUI_ContextPath}/sys/print/resource/icon/edit.png" border="0" title="<kmss:message key="sys-print:sysPrint.edit" />"/>
				</a>
				<c:if test="${'3' eq sysPrintTemplateForm.fdFormMode}">
					<a href="javascript:void(0)" onclick="deleteRow_print(this);">
						<img src="${LUI_ContextPath}/sys/print/resource/icon/delete.png" border="0" title="<kmss:message key="sys-print:sysPrint.delete" />"/>
					</a>
				</c:if>

			</td>
		</tr>
	</c:forEach>
</table>

<script>
var subPrintItem = 1;
var needLoad_subprint = true;
var SubPring_CopyDom = null;
DocList_Info.push("TABLE_DocList_Print");

function addRow_print(dom,isCopy){
	var url = Com_Parameter.ContextPath + "sys/print/sys_print_template/sysPrintTemplate.do?method=generateID";
	$.ajax({
		type : "GET",
		url : url,
		success : function(data){
			if(data){
				var fieldValues = new Object();
				fieldValues["sysPrintTemplateForm.fdSubTemplates[!{index}].fdId"]=data;
				fieldValues["sysPrintTemplateForm.fdSubTemplates[!{index}].fdName"]='<kmss:message key="sys-print:sysPrint.print" />'+subPrintItem;
				fieldValues["sysPrintTemplateForm.fdSubTemplates[!{index}].fdModelName"]='${HtmlParam.templateModelName}';
				DocList_AddRow("TABLE_DocList_Print",null,fieldValues);
				subPrintItem++;
				var input = $("#TABLE_DocList_Print").find("input[name$='fdName']");
				$(input[input.length-1]).select();
				$(input[input.length-1]).closest("tr").attr("id",data);
				SubPrint_SetChecked($(input[input.length-1]).parent().parent(),isCopy,dom);
				if($("#SubPrintDiv")[0].scrollHeight > $("#SubPrintDiv")[0].clientHeight){
					$('#SubPrintDiv').scrollTop( $('#SubPrintDiv')[0].scrollHeight );
				}
			}
		}
	});
}

function SubPrint_Copy(dom){
	SubPring_CopyDom = dom;
	var toTr = $("#TABLE_DocList_Print").find("tr[ischecked='true']").find("input[name$='fdName']").val();
	var fromTr = $(dom).parents("tr[ischecked]").find("input[name$='fdName']").val();
	var msg = '<div style="text-align:left;">'
		+ '<label><input type="radio" name="subPrint_copy" value="copy" checked="checked"><bean:message bundle="sys-print" key="sysPrint.copyMsg" />'+fromTr+'<bean:message bundle="sys-print" key="sysPrint.copyInfo" /></input></label>'
		+ '<br/><br/>'
	if(toTr!=fromTr){
		msg += '<label><input type="radio" name="subPrint_copy" value="cover"><bean:message bundle="sys-print" key="sysPrint.copyMsg" />'+fromTr+'<bean:message bundle="sys-print" key="sysPrint.coverInfo" />'+toTr+'</input></label>'
			
	}	
	msg += '</div>';
	seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
		dialog.build({
			config : {
				width : 420,
				cahce : false,
				title : "<bean:message bundle="sys-print" key="sysPrint.copyTempalte" />",
				content : {
					type : "common",
					html : msg,
					iconType : 'question',
					buttons : [ {
						name : "${lfn:message('button.ok')}",
						value : true,
						focus : true,
						fn : function(value, dialog) {
							SubPrint_Copy_Ok();
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

function SubPrint_Copy_Ok(){
	var isCopy = $("input[name='subPrint_copy']:checked").val();
	if(SubPring_CopyDom != null){
		if(isCopy=='copy'){
			addRow_print(SubPring_CopyDom,true);
		}else{
			var tr = $("#TABLE_DocList_Print").find("tr[ischecked='true']");
			var mytr = $(SubPring_CopyDom).parents("tr[ischecked]");
			if(tr[0]!=mytr[0]){
				var newHtml = mytr.find("input[name$='fdTmpXml']").val();
				sysPrintDesigner.instance.builder.setHTML(newHtml);
				if(needLoad_subprint){
					SubPrint_Load();
				}
			}
		}
	}
}

function SubPrint_SetChecked(dom,isCopy,dom2){
	//切换前选中的打印模板
	var tr = $("#TABLE_DocList_Print").find("tr[ischecked='true']");
	$("#TABLE_DocList_Print").find("tr").each(function(){
		if($(this)[0]!=dom[0]){
			$(this).attr("ischecked","false");
			$(this).find("a").css("color","");
		}else{
			$(this).find("a").css("color","#47b5e6");
			$(this).attr("ischecked","true");
		}
	});
	//切换后选中的打印模板
	var tr2 = $("#TABLE_DocList_Print").find("tr[ischecked='true']");
	if(tr[0]!=tr2[0]){
		//切换前保存上一个打印模板
		var fdDesignerHtml = sysPrintDesigner.instance.builder.getHTML();
		var fdCss = $("input[name='sysFormTemplateForms.${JsParam.fdKey}.fdCss']").val();
		var myfdDesignerHtml = tr.find("input[name$='fdTmpXml']");
		var myfdCss = tr.find("input[name$='fdCss']");
		if(fdDesignerHtml!=myfdDesignerHtml.val()){
			myfdDesignerHtml.val(fdDesignerHtml);
		}
		if(fdCss!=myfdCss.val()){
			myfdCss.val(fdCss);
		}
		if(isCopy){
			var tr3 = $(dom2).parents("tr[ischecked]");
			newHtml = tr3.find("input[name$='fdTmpXml']").val();
		}else{
			newHtml = tr2.find("input[name$='fdTmpXml']").val();
		}
		if(newHtml){
			sysPrintDesigner.instance.builder.setHTML(newHtml);
		}else{
			sysPrintDesigner.instance.builder.setHTML("&nbsp;");
			sysPrintDesigner.instance.init(document.getElementById('sysPrintdesignPanel'));
			new sysPrintDesignerTableControl(sysPrintDesigner.instance.builder,"table").draw();
		}
		var height = $("#SubForm_Print_table").outerHeight(false)-5;
		$("#DIV_SubForm_Print").css("height",height);
		$("#SubPrintDiv").css("height",(height-20-37)*0.35);
		$("#SubPrintControlsDiv").css("height",(height-20-37)*0.65);
		if(needLoad_subprint){
			SubPrint_Load();
		}
	}
}

function deleteRow_print(dom){
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.confirm("<kmss:message key="sys-print:sysPrint.confirm_deletion" />",function(data){
			if(data){
				var opTr = $(dom).parents("tr[ischecked]");
				//删除行
				DocList_DeleteRow(opTr[0]);
				var tr = $("#TABLE_DocList_Print").find("tr[ischecked='true']");
				if(tr.length==0){
					var defaultTr = $("#TABLE_DocList_Print").find('tr:eq(0)');
					defaultTr.find("a").css("color","#47b5e6");
					defaultTr.attr("ischecked","true");
					var html = defaultTr.find("input[name$='fdTmpXml']").val();
					sysPrintDesigner.instance.builder.setHTML(html);
					$('#SubPrintDiv').scrollTop(0);
					if(needLoad_subprint){
						SubPrint_Load();
					}
				}
			}
		},"","","",{autoCloseTimeout:2,topWin:window});
	});
}

function SubPrint_TextBlur(self){
	var value = self.value;
	//校验打印模板名称是否存在
	var input = $("#TABLE_DocList_Print").find("[name$='fdName']");
	for(var i =0;i<input.length;i++){
		if(input[i]!=self && input[i].value==value){
			$(self).attr("onblur","");
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				dialog.alert("<kmss:message key="sys-print:sysPrint.check_name" />",function(){
					setTimeout(function() {
						$(self).attr("onblur","SubPrint_TextBlur(this)");
					},0);
					$(self).select();
				});
			});
			return
		}
	}
	
	var a = $(self).parent().find("a[name='subPrintText']");
	a.attr("title",value);
	if(value.length>10){
		value = value.substring(0,10)+"..."
	}
	a.text(value);
	a.show();
	$(self).hide();
}

function SubPrint_Edit(self){
	var parent = $(self).parents("tr[ischecked]");
	parent.find("a[name='subPrintText']").hide();
	parent.find("input[name$='fdName']").show();
	parent.find("input[name$='fdName']").select();
	SubPrint_SetChecked(parent,false);
}

function SubPrint_Click(dom){
	SubPrint_SetChecked($(dom).parents("tr[ischecked]"),false);
}

function SubPrint_Load(){
	if(IS_PRINT_SUB_TEMPLATE){
		//编辑历史打印模板版本
		SubPrint_History_Load();
		return;
	}
	//先保存当前选中的表单信息,防止切换时未保存操作报错
	var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
	var fdDesignerHtml,fdMetadataXml;
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		fdDesignerHtml = customIframe.Designer.instance.getHTML();
		fdMetadataXml = customIframe.Designer.instance.getXML();
		var myfdDesignerHtml = tr.find("input[name$='fdDesignerHtml']");
		var myfdMetadataXml = tr.find("input[name$='fdMetadataXml']");
		if(fdDesignerHtml!=myfdDesignerHtml.val()){
			myfdDesignerHtml.val(fdDesignerHtml);
		}
		if(fdMetadataXml!=myfdMetadataXml.val()){
			//是否保存新版本
			if(SubForm_filterXml(fdMetadataXml)!=SubForm_filterXml(myfdMetadataXml.val())){
				saveasNew_subform = true;
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
					SubPrint_Load_Info(json,customIframe);
				}
			},
			dataType: 'json'
		});
		
	}
}

function SubPrint_History_Load(){
	var fdKey = "${JsParam.fdKey}";
	var modelName = "${JsParam.modelName}";
	var baseObjs = sysPrintCommon.getDocDict(fdKey,modelName);
	//过滤非表单属性
	var formBaseObjs = [];
	for(var i=0;i<baseObjs.length;i++){
		var record = baseObjs[i];
		if(record.isXFormDict==true && record.type!="docQRCode" && record.type!="RTF"){
			formBaseObjs.push(record);
		}
	}
	SubPrint_history_Load_Info(formBaseObjs);
}
function SubPrint_history_Load_Info(data){
	//当前模板没有的控件
	var otherData = [];
	//当前模板中含有的控件转换的dom结构
	var nodes = $('#sys_print_designer_draw').find('[printcontrol="true"]');
	//所有控件（去掉重复）
	var allData = [];
	for(var i=0;i<data.length;i++){
		var isRepeat = false;
		for(var j=0;j<allData.length;j++){
			if(data[i].name==allData[j].name){
				isRepeat = true;
				break;
			}
		}
		if(!isRepeat){
			allData.push(data[i]);
		}
	}
	for(var k=0;k<allData.length;k++){
		var isExit = false;
		for(var l=0;l<nodes.length;l++){
			if($(nodes[l]).attr("id")==allData[k].name){
				isExit = true;
				break;
			}
		}
		if(!isExit){
			otherData.push(allData[k])
		}
	}
	var SubPrintControlsDiv = $("#SubPrintControlsDiv");
	//清空控件div中信息
	SubPrintControlsDiv.html("");
	if(otherData.length>0){
		$("#SubPrintLoadMsg").show();
	}else{
		$("#SubPrintLoadMsg").hide();
	}
	if(IS_PRINT_SUB_TEMPLATE){
		//编辑历史打印模板版本
		for(var m = 0;m<otherData.length;m++){
			SubPrintControlsDiv.append('<div class="subform_panel_normal" onmouseover="SubForm_LoadInfo_Mouseover(this);" onmouseout="SubForm_LoadInfo_Mouseout(this);" id="subprint_'+otherData[m].name+'">'+otherData[m].label+'</div>');
	
			$("#subprint_"+otherData[m].name).click(function(){
				SubPrint_History_LoadInfo_Click(this,otherData);
			});
		}
		return;
	}
}

function SubPrint_Load_Info(data,win){
	//当前模板没有的控件
	var otherData = [];
	//当前模板中含有的控件转换的dom结构
	var nodes = $('#sys_print_designer_draw').find('[printcontrol="true"]');
	//所有控件（去掉重复）
	var allData = [];
	for(var i=0;i<data.length;i++){
		var isRepeat = false;
		for(var j=0;j<allData.length;j++){
			if(data[i].id==allData[j].id){
				isRepeat = true;
				break;
			}
		}
		if(!isRepeat){
			allData.push(data[i]);
		}
	}
	for(var k=0;k<allData.length;k++){
		var isExit = false;
		for(var l=0;l<nodes.length;l++){
			if($(nodes[l]).attr("id")==allData[k].id){
				isExit = true;
				break;
			}
		}
		if(!isExit){
			otherData.push(allData[k])
		}
	}
	var SubPrintControlsDiv = $("#SubPrintControlsDiv");
	//清空控件div中信息
	SubPrintControlsDiv.html("");
	if(otherData.length>0){
		$("#SubPrintLoadMsg").show();
	}else{
		$("#SubPrintLoadMsg").hide();
	}
	for(var m = 0;m<otherData.length;m++){
		//所有控件对象
		var mycontrols = win.Designer.instance.subFormControls;
		for(var key in mycontrols){
			var state = false;
			for(var p = 0;p<mycontrols[key].length;p++){
			    var otherControlId = otherData[m].id;
			    if (otherControlId && otherControlId.indexOf(".") > -1) {
                    otherControlId = otherControlId.substring(otherControlId.indexOf(".")+1);
                }
				if(otherControlId==mycontrols[key][p].options.values.id){
					var label = mycontrols[key][p].options.values.label || mycontrols[key][p].options.values.id;
					if(mycontrols[key][p].type=="uploadTemplateAttachment"){
						label = "<kmss:message key='sys-print:sysPrint.templateAttachment' />";
					}
					SubPrintControlsDiv.append('<div class="subform_panel_normal" onmouseover="SubForm_LoadInfo_Mouseover(this);" onmouseout="SubForm_LoadInfo_Mouseout(this);" id="subprint_'+otherControlId+'">'+label+'('+win.Designer_Config.controls[mycontrols[key][p].type].info.name+')</div>');
					$("#subprint_"+otherControlId).click(function(){
						SubPrint_LoadInfo_Click(this,otherData,mycontrols);
                        SubPrint_Load();
					});
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

function SubPrint_History_LoadInfo_Click(self,otherData,controls){
	var id = $(self).attr("id").replace("subprint_","");
	var name = '';
	for(var i=0;i<otherData.length;i++){
		if(id==otherData[i].name){
			name = otherData[i].label;
			otherData = otherData[i];
			break;
		}
	}
	//sysPrintDesigner.instance.builder.parseCtrl($('#print_control_load')[0]);
	if(sysPrintDesigner.instance.builder.$selectDomArr.length>0){
		var selectedDom = sysPrintDesigner.instance.builder.$selectDomArr[0][0];
		if(selectedDom && selectedDom.tagName && selectedDom.tagName == 'TD' && $(selectedDom).hasClass('table_select')){
			var next = $(selectedDom).next();
			if($(selectedDom).hasClass('td_normal_title') && next.length>0){
				//单元格为标题栏时，附带插入名称
				var label=new sysPrintLabelControl(sysPrintDesigner.instance.builder,'label',{text:name});
				label.draw();
				//数据
				var data = {"id":id,"name":name,"type":otherData.type,"isformdtable":"","isxformdict":"true"};
				var field=new sysPrintDesignerFieldControl(sysPrintDesigner.instance.builder,'field',{data:data});
				field.draw(next);
				if(next.html()=="&nbsp;"){
					//next.html($("#print_control_load").html());
				}else{
					//next.append($("#print_control_load").html());
				}
				sysPrintDesigner.instance.builder.parse(null, next[0], true);
			}else{
				var data = {"id":id,"name":name,"type":otherData.type,"isformdtable":"","isxformdict":"true"};
				var field=new sysPrintDesignerFieldControl(sysPrintDesigner.instance.builder,'field',{data:data});
				field.draw();
				sysPrintDesigner.instance.builder.parse(null, selectedDom);
			}
			$(self).remove();
		}else{
			alert('<kmss:message key="sys-print:sysPrint.load_msg" />');
		}
	}else{
		alert('<kmss:message key="sys-print:sysPrint.load_msg" />');
	}
}
function SubPrint_LoadInfo_Click(self,otherData,controls){
	var id = $(self).attr("id").replace("subprint_","");
	var name = "";
	for(var i=0;i<otherData.length;i++){
		if(id==otherData[i].id){
			name = otherData[i].label;
			break;
		}
	}
	var currentControl;
	for(var key in controls){
		var state = false;
		for(var p = 0;p<controls[key].length;p++){
			if(id==controls[key][p].options.values.id){
                currentControl = controls[key][p];
				$("#print_control_load").html(controls[key][p].options.domElement.outerHTML);
				state = true;
				break;
			}
		}
		if(state){
			break;
		}
	}
	sysPrintDesigner.instance.builder.parseCtrl($('#print_control_load')[0]);
	if(sysPrintDesigner.instance.builder.$selectDomArr.length>0){
		var selectedDom = sysPrintDesigner.instance.builder.$selectDomArr[0][0];
		if(selectedDom && selectedDom.tagName && selectedDom.tagName == 'TD' && $(selectedDom).hasClass('table_select')){
		    if (currentControl && currentControl.parent) {
                var parentControl = currentControl.parent;
                var closestDetailsTable = $(selectedDom).closest("[fd_type='detailsTable']");
                if (parentControl.type == "detailsTable") {
                    if (closestDetailsTable.length == 1) {
                        var parentControlId = parentControl.options.values.id;
                        var closestDetailsTableId = closestDetailsTable.attr("id");
                        if (parentControlId != closestDetailsTableId) {
                            alert('<kmss:message key="sys-print:sysPrint.insert_msg" />');
                            return;
                        }
                    } else {
                        alert('<kmss:message key="sys-print:sysPrint.insert_msg" />');
                        return;
                    }
                }
            }
		    var next = $(selectedDom).next();
			if($(selectedDom).hasClass('td_normal_title') && next.length>0){
				//单元格为标题栏时，附带插入名称
				var label=new sysPrintLabelControl(sysPrintDesigner.instance.builder,'label',{text:name});
				label.draw();
				//数据
				if(next.html()=="&nbsp;"){
					next.html($("#print_control_load").html());
				}else{
					next.append($("#print_control_load").html());
				}
				sysPrintDesigner.instance.builder.parse(null, next[0], true);
			}else{
				if($(selectedDom).html()=="&nbsp;"){
					$(selectedDom).html($("#print_control_load").html());
				}else{
					$(selectedDom).append($("#print_control_load").html());
				}
				sysPrintDesigner.instance.builder.parse(null, selectedDom);
			}
			$(self).remove();
		}else{
			alert('<kmss:message key="sys-print:sysPrint.load_msg" />');
		}
	}else{
		alert('<kmss:message key="sys-print:sysPrint.load_msg" />');
	}
}

function Print_getSubPrintInfo_${JsParam.fdKey}() {
	var subObj = [];
	if (IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory' && sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_SUBFORM) {
		$("#TABLE_DocList_Print").find("tr[ischecked]").each(function(i) {
			var subformObj = {};
			if(i==0){
				subformObj.id='default'
				subformObj.name='<kmss:message key="sys-print:sysPrint.default_print" />';
			}else{
				subformObj.id=$(this).find("input[name$='fdId']").val();
				subformObj.name=$(this).find("input[name$='fdName']").val();
				var fdOldId = $(this).find("input[name$='fdOldId']").val();
				if (fdOldId) {
					subformObj.fdOldFormId= fdOldId;
				}
			}
			subObj.push(subformObj);
		});
	}
	return subObj;
}

Com_AddEventListener(window, 'load', function(){
	var method = Com_GetUrlParameter(location.href,"method");
	//再次编辑时，给基础模板行设置数据，防止未做操作直接保存时取不到值
	if(IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory' && sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_SUBFORM && method=="edit"){
		var defaultTr = $("#TABLE_DocList_Print").find('tr:eq(0)');
		var html = $("[name='sysPrintTemplateForm.fdTmpXml']").val();
		var css = $("[name='sysPrintTemplateForm.fdCss']").val();
		var name = $("[name='sysPrintTemplateForm.fdName']").val();
		defaultTr.find("input[name$='fdTmpXml']").val(html);
		defaultTr.find("input[name$='fdCss']").val(css);
		defaultTr.find("input[name$='fdName']").val(name);
	}
});

function _Designer_SubPrintAddHide(dom){
	if($("#SubPrint_main_tr").is(":hidden")){
		$("#SubPrint_main_tr").show();
		$(dom).attr("src","${LUI_ContextPath}/sys/xform/base/resource/icon/varrowleft.gif");
	}else{
		$("#SubPrint_main_tr").hide();
		$(dom).attr("src","${LUI_ContextPath}/sys/xform/base/resource/icon/varrowright.gif");
	}
}
</script>