<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
        <link href="${LUI_ContextPath}/sys/datainit/resource/css/helpTip.css" rel="stylesheet" type="text/css">	
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
		<script type="text/javascript">
			Com_IncludeFile("treeview.js");
		</script>	
	</template:replace>
	<template:replace name="toolbar">
		<div class="lui_tree_operation">
		   <ui:toolbar id="toolbar" style="float:right;margin-right:15px;" count="7">
			   <ui:button text="${lfn:message('global.init.data.on')}" id="_open" onclick="setCookie('open');" order="1" style="display: none"></ui:button>
			   <ui:button text="${lfn:message('global.init.data.off')}" id="_close" onclick="setCookie('close');" order="1" style="display: none"></ui:button>
			   <ui:button text="${lfn:message('sys-datainit:sysDatainitMain.data.base.export')}" onclick="submitForm();" order="2" ></ui:button>
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
						// 请求数据
						$.post('<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=selectModel"/>&dev=${JsParam.dev}', function(data) {
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
			
			function _progress() {
				seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog) {
					var data = new KMSSData();
					data.UseCache = false;
					data.AddBeanData("sysDatainitMainService");
					var rtn = data.GetHashMapArray()[0];
					if(window.progress) {
						var current = parseInt(rtn.current || 0);
						var total = parseInt(rtn.total || 0);
						var state = parseInt(rtn.state || 0);
						var msg = rtn.msg;
						if(current == total || state == 1) {
							clearTimeout(progressTimeout);
							window.progress.hide();
						} else {
							// 设置进度值
							if(total == -1 || total == 0) {
								window.progress.setProgress(0);
							} else {
								window.progress.setProgress(current, total);
								window.progress.setProgressText(msg);
							}
						}
					}
					if(rtn.state != 1) {
						progressTimeout = setTimeout("_progress()", 500);
					}
				});
			}
			
			function generateTree(json) {
				LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-datainit" key="sysDatainitMain.import.selectModel.title"/>", document.getElementById("treeDiv"));
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
				showHelpTip();
				// 关闭进度条
				if(progressTimeout)
					clearTimeout(progressTimeout);
				if(window.progress)
					window.progress.hide();
				count = 0;
			}
			
			/** 显示帮助tip图标 **/
			function showHelpTip(){
				var tipIconSpanObj = $("#helpTipIconSpan");
				var rootNodeLink = $("#treeDiv>#TVN_0").find("a[lks_nodeid='0']");
				tipIconSpanObj.insertAfter(rootNodeLink);
				tipIconSpanObj.show();
		        $(".lui_prompt_tooltip").hover(function() {
		            $(this).find(".lui_prompt_tooltip_drop").addClass('hover').next(".lui_dropdown_tooltip_menu").fadeIn(300);
		          }, function() {
		            $(this).find(".lui_prompt_tooltip_drop").removeClass('hover').next(".lui_dropdown_tooltip_menu").fadeOut(300);
		        });
			}
			
			function submitForm() {
				if (List_CheckSelect()) {
					
	 				var values = [];
	 				$("input[name='List_Selected']:checked").each(function(){
	 						values.push($(this).val());
	 					});
					
	 				seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog) {
						dialog.confirm('<bean:message bundle="sys-datainit" key="sysDatainitMain.data.base.export.confirm"/>',function(value){
							if(value == true) {
								//dialog.loading();
								if(count == 0) {
									count++;
									// 请求数据
									$.post('<c:url value="/sys/datainit/sys_datainit_main/sysDatainitMain.do?method=exportToBasics"/>',$.param({"List_Selected":values},true),function(data) {
										if(data.state == 0) {
											dialog.failure('<bean:message key="return.optFailure" />');
											if(progressTimeout)
												clearTimeout(progressTimeout);
											if(window.progress)
												window.progress.hide();
										} else {
											//generateTree(eval(data.filePaths));
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
					seajs.use(['lui/dialog' ], function(dialog) {
						dialog.alert("<bean:message key="page.noSelect"/>");
					});
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
		</script>
		<form action="" name="configForm" method="POST">
			<table class="tb_noborder">
				<tr>
					<td width="10pt"></td>
					<td>
					    <div id=treeDiv class="treediv"></div>
					</td>
					<td style="vertical-align: top;padding-top: 10px;padding-left: 50px;color: #999999;">
						${lfn:message('sys-datainit:sysDatainitMain.export.help.tip')}
					</td>
				</tr>
			</table>
			
		</form>
		
		<script type="text/javascript">
			function setCookie(value) {
				var Days = 30;
				var exp = new Date();
				exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
				document.cookie = "isopen=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=" + Com_Parameter.ContextPath;
				cookieManager();
				window.location.reload();
			}
			//读取cookies   
			function getCookie() {
				var arr, reg = new RegExp("(^| )isopen=([^;]*)(;|$)");
				if (arr = document.cookie.match(reg)) return unescape(arr[2]);
				else return null;
			}
			function cookieManager() {
				var mark = getCookie();
				if (mark == '' || mark == null) {
					setCookie('close')
					return;
				}
				if (mark == 'open') {
					document.getElementById("_close").style.display = 'block';
					document.getElementById("_open").style.display = 'none';
				}
				if (mark == 'close') {
					document.getElementById("_open").style.display = 'block';
					document.getElementById("_close").style.display = 'none';
				}
			}
			// 标准浏览器下onload下的cookieManager()在optbar加载完事件后面，导致按钮不初始化，故修改
			LUI.ready(function(){
				cookieManager();
			});
		</script>
	</template:replace>
</template:include>
