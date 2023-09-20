<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<style type="text/css">
		.oldValue , .newValue {
			width: 100%;
		}
		.tb_noborder .tb_normal td {
			padding: 8px;
			border: 1px #e8e8e8 solid;
		}
		</style>
		<script type="text/javascript">
		Com_IncludeFile("treeview.js|jquery.js|dialog.js");
		Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
		</script>
		<script type="text/javascript">
		var LKSTree;
		var progressTimeout;
		var count = 0;
		window.onload=function (){
			generateTree();
		};
		function generateTree(){
			LKSTree = new TreeView("LKSTree", '<bean:message bundle="sys-datainit" key="sysDatainitMain.import.title"/>', document.getElementById("treeDiv"));
			var n1, n2, n3;
			LKSTree.isShowCheckBox=true;
			LKSTree.isMultSel=true;
			LKSTree.isAutoSelectChildren = true;
			n1 = LKSTree.treeRoot;
			n1.value = " ";
			var json=eval(${filePaths});
			for(var i=0; i<json.length; i++) {
				n2 = n1.AppendChild(json[i].text);
				n2.value = " ";
				for(var j=0; j<json[i].children.length; j++) {
					n3=n2.AppendChild(json[i].children[j].text);
					n3.value=json[i].children[j].value;
				}
			}
			LKSTree.Show();
		}
		function submitForm(obj){
			if(!verification_val()){
				return false;
			}
			var form = document.getElementsByName('configForm')[0];
			if(List_CheckSelect()){
				form.submit();
				$(form).hide();
				$('#loading').show();
				$(obj).attr('disabled','disabled');
			}
		
		}
		function verification_val(){
			if($("#isRel").is(":checked")){
				var inputs = $("#rel_body").find("input");
				for(var i=0;i<inputs.length;i++){ 
					if($(inputs[i]).val()==""){
						alert("<bean:message bundle="sys-datainit" key="sysDatainitMain.import.verification"/>");
						return false;
					}
				}
			}
			return true;
		}
		function List_CheckSelect(){
			expandNode(LKSTree.treeRoot);
			var fields = document.getElementsByName("List_Selected");
			var selected = false;
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				if(field.checked && field.value!=" "){
					selected = true;
					break;
				}
			}
			if(!selected){
				alert("<bean:message key="page.noSelect"/>");
				return false;
			}
			for(var i=0; i<fields.length; i++){
				var field = fields[i];
				if(field.checked && field.value==" "){
					field.checked = false;
				}
			}
			return true;
		}
		
		function expandNode(node){
			if(!node.isExpanded){
				LKSTree.ExpandNode(node);
			}
			for(var child = node.firstChild; child!=null; child = child.nextSibling){
				expandNode(child);
			}
		}
		
		function selectValue(){
			var row = $("<tr></tr>");
			
			var xtd1 = $("<td></td>"); 
			xtd1.append('<input class="oldValue" type="text" name="oldValue" value="">'); 
			row.append(xtd1);				
			
			var xtd2 = $("<td></td>");				
			xtd2.append('<input class="newValue" type="text" name="newValue" value="" />');  
			row.append(xtd2);
			
			var xtd3 = $("<td align='center'></td>");
			xtd3.append('<img src="../../../resource/style/default/icons/delete.gif" alt="del" onclick="deleteValue(this);" style="cursor:pointer">&nbsp;&nbsp;');
			//xtd3.append('<img src="../../../resource/style/default/icons/up.gif" alt="up" onclick="pages_moveUp(this);" style="cursor:pointer">&nbsp;&nbsp;');
			//xtd3.append('<img src="../../../resource/style/default/icons/down.gif" alt="down" onclick="pages_moveDown(this);" style="cursor:pointer">');
			row.append(xtd3);
			
			$("#rel_body").append(row);
		}
		function deleteValue(obj){
			var xtr = $(obj).closest("tr");
			xtr.remove();
		}
		function relClick(obj){
			if(obj.checked){
				$("#rel_tr").show();
			}else{
				$("#rel_tr").hide();	
			}		
		}
		
		function deleteInitData(methodValue){
			if (List_CheckSelect()) {
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog) {
					dialog.confirm('<bean:message bundle="sys-datainit" key="sysDatainitMain.data.base.delete.confirm"/>',function(value){
						if(value == true) {
							if(count == 0) {
								count++;
								// 请求数据
								$.post('<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do" />?method='+methodValue,$.param({"List_Selected":values},true),function(data) {
									if(data.state == 0) {
										dialog.failure('<bean:message key="return.optFailure" />');
										if(progressTimeout)
											clearTimeout(progressTimeout);
										if(window.progress)
											window.progress.hide();
									} else {
										// 关闭进度条
										if(progressTimeout)
											clearTimeout(progressTimeout);
										if(window.progress)
											window.progress.hide();
										window.location.href = '<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=success"/>';
									}
									count = 0;
								}, 'json');
								// 开启进度条
								window.progress = dialog.progress(false);
								progressTimeout = setTimeout("_progress()", 200);
							}
						}
					});
				});
			}
		}
		
		function _progress(springBean) {
			seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog) {
				springBean = springBean || "sysDatainitMainService";
				var data = new KMSSData();
				data.UseCache = false;
				data.AddBeanData(springBean);
				var rtn = data.GetHashMapArray()[0];
				if(window.progress) {
					// 在导出数据时，会展开所有的树节点，此时的进度条会显示在底部（不可见），所以这里强行将进度条拉到上面
					window.progress.element[0].style.top = "150px";
					// 这里的进度条会显示一些进度信息，有些长，需要调整宽度
					window.progress.element[0].style.width = "350px";
					var current = parseInt(rtn.current || 0);
					var total = parseInt(rtn.total || 0);
					var msg = rtn.msg;
					// 设置进度值
					if(total == -1 || total == 0) {
						window.progress.setProgress(0);
					} else {
						window.progress.setProgress(current, total);
						window.progress.setProgressText(msg);
					}
					if(rtn.state == 1) {
						window.progress.hide();
					}
				}
				if(rtn.state != 1) {
					progressTimeout = setTimeout(function(){
						_progress(springBean);
					}, 500);
				}
			});
		}
		</script>
	</template:replace>
	<template:replace name="toolbar">
		<div class="lui_tree_operation">
		   <ui:toolbar id="toolbar" style="float:right;margin-right:15px;" count="7">
			   <ui:button text="${lfn:message('sys-datainit:sysDatainitMain.import')}" onclick="submitForm(this);" order="1" ></ui:button>
			   <ui:button text="${lfn:message('button.delete')}" onclick="deleteInitData('deleteDataInit');" order="2" ></ui:button>
		   </ui:toolbar>
		</div>
	</template:replace>
	<template:replace name="content">
	
		<form action="<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=startImport" />" name="configForm" method="POST">
			<table class="tb_noborder">
				<tr>
					<td width="10pt"></td>
					<td>
					<div id=treeDiv class="treediv"></div>
					</td>
					<td style="vertical-align: top;padding-top: 10px;padding-left: 50px;color: #999999;">
						<bean:message bundle="sys-datainit" key="sysDatainitMain.import.desc" />
					</td>
				</tr>
			</table>
			
			<table class="tb_noborder">
				<tr>
					<td width="10pt"></td>
					<td>
						<label><input type="checkbox" name="isReplacePerson" value="true" /><bean:message bundle="sys-datainit" key="sysDatainitMain.import.isReplacePerson" /></label>
						<input type="hidden" name="replacePersonId"/>
						<input type="text" style="width:100px" name="replacePersonName" readonly="true" styleClass="inputsgl"/>
						<a href="#" onclick="Dialog_Address(false, 'replacePersonId', 'replacePersonName', null, ORG_TYPE_PERSON);">
							<bean:message key="dialog.selectOrg"/>
						</a>
						<bean:message bundle="sys-datainit" key="sysDatainitMain.import.isReplacePerson.info" />
					</td>
				</tr>
				<tr>
					<td width="10pt"></td>
					<td>
						<label><input type="checkbox" name="isImportRequired" value="true" /><bean:message bundle="sys-datainit" key="sysDatainitMain.import.isImportRequired" /></label>
						<bean:message bundle="sys-datainit" key="sysDatainitMain.import.isImportRequired.info" />
					</td>
				</tr>
				<tr>
					<td width="10pt"></td>
					<td>
						<label><input type="checkbox" name="isUpdate" value="true" checked="checked" /><bean:message bundle="sys-datainit" key="sysDatainitMain.import.isUpdate" /></label>
						<bean:message bundle="sys-datainit" key="sysDatainitMain.import.isUpdate.info" />
					</td>
				</tr>
				
				<tr>
					<td width="10pt"></td>
					<td colspan="3"><label><input type="checkbox" id="isRel" name="isRel" value="true" onclick="relClick(this)" /><bean:message bundle="sys-datainit" key="sysDatainitMain.import.isReplace" /></label></td>
				</tr>
				<tr id="rel_tr" style="display: none;">
					<td width="10pt"></td>
					<td>
					
						<table id="TABLE_DocList" class="tb_normal" width=100%>
							<tbody>
								<tr>
									<td align="center" class="td_normal_title"><bean:message bundle="sys-datainit" key="sysDatainitMain.import.oldValue" /></td>
									<td align="center" class="td_normal_title"><bean:message bundle="sys-datainit" key="sysDatainitMain.import.newValue" /></td>
									<td width="15%" align="center" class="td_normal_title">
										<img src="../../../resource/style/default/icons/add.gif" alt="add" onclick="selectValue();" style="cursor:pointer">
									</td>
								</tr>
							</tbody>
							<tbody id="rel_body">
							</tbody>
						</table>  
					
					</td>
				</tr>
			</table>
		</form>
		<div align="center" style="display: none;" id="loading">
			<img src="../../../resource/style/common/images/loading.gif" border="0" /><bean:message bundle="sys-datainit" key="sysDatainitMain.import.loading"/>
		</div>
	</template:replace>
</template:include>