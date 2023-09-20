<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link href="${LUI_ContextPath}/sys/datainit/resource/css/helpTip.css" rel="stylesheet" type="text/css">

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("treeview.js");
		</script>
		<style>
			body.lui_config_form{padding-top:40px;}
			.lui_tree_operation {
			   width:100%;
			   height:38px;
			   border-bottom:1px solid #ccc!important;
			   margin-bottom: 10px;
			   padding-top: 5px;
			   background-color: #eee;
			   position:fixed;
			   z-index:1;
			   top:0px;
			}
		</style>
	</template:replace>
	<template:replace name="toolbar">
		<div class="lui_tree_operation">
		   <ui:toolbar id="toolbar" style="float:right;margin-right:15px;" count="7">
			   <ui:button text="${lfn:message('global.init.data.zip.download')}" onclick="submitForm('startExport');" order="2" ></ui:button>
			   <ui:button text="${lfn:message('button.delete')}" onclick="deleteInitData('deleteDataInit');" order="2" ></ui:button>
		   </ui:toolbar>
		</div>
	</template:replace>
	<template:replace name="content">
		<script type="text/javascript">
		var LKSTree;
		var progressTimeout;
		var count = 0;
		LUI.ready(function() {
			if(count == 0) {
				count++;
				seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog) {
					var dialog_loading = dialog.loading();
					// 请求数据
					$.post('<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=showImport&type=export"/>', function(data) {
						dialog_loading.hide();
						if(data.state == 0) {
							dialog.failure('<bean:message key="return.optFailure" />');
							if(progressTimeout)
								clearTimeout(progressTimeout);
							if(window.progress)
								window.progress.hide();
						} else {
							generateTree(eval(data.filePaths));
						}
					}, 'json');
					// 开启进度条
					window.progress = dialog.progress(false);
					progressTimeout = setTimeout("_progress()", 200);
				});
			}
		});
		
		function generateTree(json){
			LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-datainit" key="sysDatainitMain.export.title"/>", document.getElementById("treeDiv"));
			var n1, n2, n3;
			LKSTree.isShowCheckBox=true;
			LKSTree.isMultSel=true;
			LKSTree.isAutoSelectChildren = true;
			n1 = LKSTree.treeRoot;
			n1.value = " ";
			// var json = eval(${filePaths});
			for(var i=0; i<json.length; i++) {
				if(json[i]) {
					n2 = n1.AppendChild(json[i].text);
					n2.value = " ";
					for(var j=0; j<json[i].children.length; j++) {
						if(json[i].children[j]) {
							n3=n2.AppendChild(json[i].children[j].text);
							n3.value=json[i].children[j].value;
						}
					}
				}
			}
			LKSTree.Show();
			// 关闭进度条
			if(progressTimeout)
				clearTimeout(progressTimeout);
			if(window.progress)
				window.progress.hide();
			count = 0;
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
		
		function submitForm(methodValue) {
			seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog) {
				// 开启进度条
				window.progress = dialog.progress(false);
				progressTimeout = setTimeout(function(){_progress('sysDatainitProcessService')}, 200);
			});
			var form = document.getElementsByName('configForm')[0];
			form.action += '?method=' + methodValue + encodeURI('&s_path=${fn:escapeXml(param.s_path)}');
			if (List_CheckSelect()) {
				form.submit();
			}
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
		function List_ConfirmDel(){
			return List_CheckSelect() && confirm("<bean:message key="page.comfirmDelete"/>");
		}
		</script>
		<form action="<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do" />" name="configForm" method="POST">
			<input type="hidden" value="" name="methodValue">
			<div id="optBarDiv">
			</div>
				<table class="tb_noborder">
				<tr>
					<td width="10pt"></td>
					<td>
						<div id=treeDiv class="treediv"></div>
					</td>
					<td style="vertical-align: top;padding-top: 10px;padding-left: 50px;color: #999999;">
						<bean:message bundle="sys-datainit" key="sysDatainitMain.export.desc" />
					</td>
				</tr>
			</table>
			
		</form>
	</template:replace>
</template:include>
