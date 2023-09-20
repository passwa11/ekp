<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css" />
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
<style>
html,body{
	overflow: hidden;
}
.cust_reader_unselect, .cust_reader_selected {
	cursor:pointer;
	width:16px;
	height:16px;
	border:0px;
}
 .cust_reader_unselect {
	background:url(${LUI_ContextPath}/sys/xform/designer/style/img/right_icon.gif) no-repeat 0px -16px;
}
.cust_reader_selected {
	background:url(${LUI_ContextPath}/sys/xform/designer/style/img/right_icon.gif) no-repeat 0px 0px;
}
.lui_custom_list_boxs{
	border-top: 1px solid #d5d5d5;
	position:fixed;
	bottom:0;
	width:100%;
	background-color: #fff;
	z-index:1000;
	height:63px;
}

/* 修改下拉样式 */
.lui_accordionpanel_header_l{
	display:none!important;
}

.lui_tabpanel_navs_item_title{
	color:#333!important;
}

.lui_accordionpanel_toggle_up{
	background:url(${LUI_ContextPath}/sys/modeling/base/resources/images/icon-panel_close.png) no-repeat center!important;
	background-size: 80%!important;
	right: 25px!important;
}

.lui_accordionpanel_toggle_down{
	background:url(${LUI_ContextPath}/sys/modeling/base/resources/images/icon-panel_open.png) no-repeat center!important;
	right: 25px!important;
}
.lui_accordionpanel_nav_c{
	text-align: left;
}
.lui_accordionpanel_content_c .lui_list_nav_list li a:hover{
	color: #4285f4!important;
	background-color: inherit!important;
	text-shadow: none!important;
}
.lui_accordionpanel_content_c .lui_list_nav_list li.lui_list_nav_selected a{
	color: #4285f4!important;
	background-color: inherit!important;
	text-shadow: none!important;
}
.lui_list_nav_list li a{
	text-align: left!important;
	padding-left: 20%!important;
}

</style>

<template:include ref="default.simple" spa="true" rwd="true">
	<template:replace name="body">
		<script type="text/javascript">
		Com_IncludeFile("dialog.js");
		seajs.use(['theme!list', 'theme!portal']);
		</script>
		<div id='contentContainer' style="overflow: auto;">
		<center>
			<div class="modeling-form-authority">
				<div class="modeling-form-authority-left">
					<div id="menu_nav" class="lui_list_nav_frame">
						<ui:accordionpanel style="width:100%;text-align: center;display:block;background-color:inherit">
							<ui:content title="${lfn:message('sys-modeling-auth:sysModelingAuth.NoProcessForm') }" style="text-align: center;display:block;">
								<ui:combin ref="menu.nav.simple">
									<ui:varParam name="source">
										<ui:source type="Static">
											[
											{
											"text" : "${lfn:message('sys-modeling-auth:sysModelingAuth.AddForm') }",
											"href" :  "javascript:renderAuthCfg('add');"
											},
											{
											"text" : "${lfn:message('sys-modeling-auth:sysModelingAuth.EditForm') }",
											"href" :  "javascript:renderAuthCfg('edit');"
											},
											{
											"text" : "${lfn:message('sys-modeling-auth:sysModelingAuth.ViewForm') }",
											"href" :  "javascript:renderAuthCfg('view');"
											}
											]
										</ui:source>
									</ui:varParam>
								</ui:combin>
							</ui:content>
						</ui:accordionpanel>
					</div>
				</div>
				<div class="modeling-form-authority-right">
					<div class="modeling-form-authority-right-wrap">
						<table width="100%" border="0" style="border-collapse: collapse;" class="tb_normal" id="authcfgId">
						</table>
					</div>
				</div>
			</div>


<%--		<table style="margin-top:10px">--%>
<%--			<tr>--%>
<%--				<td width="200px;" valign="top" >--%>
<%--					<div id="menu_nav" class="lui_list_nav_frame">--%>
<%--						<ui:accordionpanel style="width:100%;text-align: center;display:block;background-color:inherit">--%>
<%--							<ui:content title="无流程表单" style="text-align: center;display:block;">--%>
<%--								<ui:combin ref="menu.nav.simple">--%>
<%--									<ui:varParam name="source">--%>
<%--										<ui:source type="Static">--%>
<%--											[--%>
<%--											{--%>
<%--											"text" : "新建表单",--%>
<%--											"href" :  "javascript:renderAuthCfg('add');"--%>
<%--											},--%>
<%--											{--%>
<%--											"text" : "编辑表单",--%>
<%--											"href" :  "javascript:renderAuthCfg('edit');"--%>
<%--											},--%>
<%--											{--%>
<%--											"text" : "查看表单",--%>
<%--											"href" :  "javascript:renderAuthCfg('view');"--%>
<%--											}--%>
<%--											]--%>
<%--										</ui:source>--%>
<%--									</ui:varParam>--%>
<%--								</ui:combin>--%>
<%--							</ui:content>--%>
<%--						</ui:accordionpanel>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td width="750px;" valign="top">--%>
<%--					<table width="100%" border="0" style="border-collapse: collapse;" class="tb_normal" id="authcfgId">--%>
<%--					</table>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--		</table>--%>
			</center>
		</div>
		<div class="lui_custom_list_boxs">
			<center>
				<div class="lui_custom_list_box_content_col_btn" style="width:750px">
					<a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="dialogSave()">${lfn:message('sys-modeling-auth:sysModelingAuth.Save') }</a>
					<a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="dialogClose()">${lfn:message('sys-modeling-auth:sysModelingAuth.Cancel') }</a>
				  </div>
			</center>
		</div>

		<script>

			//{mains”:[{name:"",label:"",type:""}],"details":[{name:"",label:"",cols:[{name:"",label:"",type:""}]}],"attachments":[{name:"",label:"",type:""}]}
			var xformCfg = ${xform};

			//	{"mode_dg":{"fd_37b6900a4e9c16":{"value":"hidden","readerIds":"15874fb98aaf78a4ef367134fcd82741"}}};
			var cfgJson = ${cfgJson};

			function initAuthCfgModel(method){
				var cfgKey = "mode_dg";
				if(typeof cfgJson[cfgKey] =="undefined"){
					cfgJson[cfgKey]={};
				}
				//兼容旧数据
				if(typeof cfgJson[cfgKey]['add'] =="undefined"){
					let history = JSON.stringify(cfgJson[cfgKey]);
					cfgJson[cfgKey] = {};
					cfgJson[cfgKey]['add'] = JSON.parse(history);
					cfgJson[cfgKey]['edit'] = JSON.parse(history);
					cfgJson[cfgKey]['view'] = JSON.parse(history);
				}
				return cfgKey;
			}

			function setAuthCfgValue(method,xformId,el,detailId){
				var value = el.value;
				var els = document.getElementsByName(xformId);
				for(var i=0;i<els.length;i++){
					if(els[i].value!=value){
						els[i].checked=false;
					}
				}
				var cfgKey = initAuthCfgModel(method);
				var rid = xformId.replace(".","_")+"_readerSpan";
				if(xformId === detailId){
					$("input[type=checkbox]").not(el).each(function(){
						var dataDetailId = $(this).data("detailid");
						var val = $(this).val();
						if(dataDetailId && dataDetailId.indexOf(detailId) > -1
								&& (val === "hidden" || val ==='addDetail' || val ==='delDetail')){
							if(el.checked){
								$(this).prop("disabled","disabled");
							}else{
								$(this).removeAttr("disabled");
							}
						}
					})
				}
				if(!el.checked){
					cfgJson[cfgKey][method][xformId]= {"value":""};
					$("#"+rid).hide();
					return;
				}
				var one = {"value":value};
				if(typeof  cfgJson[cfgKey][method] =="undefined"){
					cfgJson[cfgKey][method]={};
				}
				cfgJson[cfgKey][method][xformId]=one;
				if(value=="hidden"){
					$("#"+rid).show();
					rid =  xformId.replace(".","_")+"_readerBtn";
					$("#"+rid).attr("class","cust_reader_unselect");
				}else{
					$("#"+rid).hide();
				}
			}

			function getAuthCfgValue(method,xformId){
				var cfgKey = initAuthCfgModel(method);
				return cfgJson[cfgKey][method][xformId];
			}

			function selectReader(method,xformId){
				var _readerIds = document.getElementsByName(xformId+'_readerIds');
				var _readerNames = document.getElementsByName(xformId+'_readerNames');
				Dialog_Address(true,_readerIds[0],_readerNames[0],null,null,function(data) {
					setAuthCfgReader(method,xformId,_readerIds[0].value,_readerNames[0].value);
				});
			}

			function setAuthCfgReader(method,xformId,readerIds,readerNames){
				var cfgKey = initAuthCfgModel(method);
				cfgJson[cfgKey][method][xformId]["readerIds"]=readerIds;
				cfgJson[cfgKey][method][xformId]["readerNames"]=readerNames;
				var rid =  xformId.replace(".","_")+"_readerBtn";
				if(readerIds==""){
					$("#"+rid).attr("class","cust_reader_unselect");
				}else{
					$("#"+rid).attr("class","cust_reader_selected");
				}
			}

			function renderAuthCfg(method){
				var html = [];
				html.push(getHeaderHtml());
				html.push(getContentHtml(method));
				$("#authcfgId").html(html.join(""));
				var details = xformCfg["details"];
				if(details) {
					for (var i = 0; i < details.length; i++) {
						var xformValue = getAuthCfgValue(method,details[i]["name"]);
						if(!xformValue || xformValue.value != "hidden"){
							continue;
						}
						$("input[type=checkbox]").each(function () {
							var dataDetailId = $(this).data("detailid");
							var val = $(this).val();
							var name = $(this).attr("name");
							if (name !== dataDetailId && dataDetailId && dataDetailId.indexOf(details[i]["name"]) > -1
									&& (val === "hidden" || val === 'addDetail' || val === 'delDetail')) {
								$(this).prop("disabled", "disabled");
							}
						})
					}
				}
			}

			function getHeaderHtml(){
				var arr = [];
				arr.push('<tr class="tr_normal_title">');
				arr.push('<td  style="padding: 10px 10px;">${lfn:message("sys-modeling-auth:sysModelingAuth.FormField") }</td>');
				arr.push('<td align="center"  style="padding: 10px 10px;">${lfn:message("sys-modeling-auth:sysModelingAuth.ReadOnly") }</td>');
				arr.push('<td align="center"  style="padding: 10px 10px;">${lfn:message("sys-modeling-auth:sysModelingAuth.Hide") }</td>');
				arr.push('</tr>');
				return arr.join("");
			}

			function getContentHtml(method){
				var arr = [];
				var mains = xformCfg["mains"];
				if(mains){
					for(var i=0;i<mains.length;i++){
						arr.push(getOneHtml(mains[i],"main","${lfn:message('sys-modeling-auth:sysModelingAuth.MainTable') }",method));
					}
				}
				var details = xformCfg["details"];
				if(details){
					for(var i=0;i<details.length;i++){
						var prefix = "【"+details[i]["label"]+"】";
						arr.push(getOneHtml(details[i],"detail","${lfn:message('sys-modeling-auth:sysModelingAuth.MainTable') }",method,details[i]["name"]));
						var cols = details[i]["cols"];
						if(cols){
							for(var j=0;j<cols.length;j++){
								arr.push(getOneHtml(cols[j],"col",prefix,method,details[i]["name"]));
							}
						}
						arr.push(getOperAuthRowHtml(details[i]["label"], method,details[i]["name"]));

						var attachments = details[i]["attachments"];
						if(attachments){
							for(var j=0;j<attachments.length;j++){
								var attach =attachments[j];
								attach.isStore = true;
								arr.push(getOneHtml(attach,"attachment",prefix,method,details[i]["name"]));
							}
						}
					}
				}
				var attachments = xformCfg["attachments"];
				if(attachments){
					for(var i=0;i<attachments.length;i++){
						var attach =attachments[i];
						attach.isStore = true;
						arr.push(getOneHtml(attach,"attachment","${lfn:message('sys-modeling-auth:sysModelingAuth.Enclosure') }",method));
					}
				}
				var labels = xformCfg["labels"];
				if (labels){
					for (var i = 0; i < labels.length; i++) {
						var label = labels[i];
						label.isStore = true;
						arr.push(getOneHtml(label,"textLabel","${lfn:message('sys-modeling-auth:sysModelingAuth.Text') }",method));
					}
				}
				return arr.join("");
			}

			function getOperAuthRowHtml(label, method, detailId) {
				if(!method || method === 'view'){
					return;
				}
				let addDetailName = detailId + "." + "operation_auth_addDetail";
				let delDetailName = detailId + "." + "operation_auth_delDetail";
				let arr = [];
				let addDetailOV = getAuthCfgValue(method, addDetailName);
				let delDetailOV = getAuthCfgValue(method, delDetailName);
				let addDetail="",delDetail="";
				if(addDetailOV) {
					if(addDetailOV["value"]==="addDetail") {
						addDetail = "checked";
					}
				} else {
					//默认勾选
					addDetail = "checked";
				}
				if(delDetailOV) {
					if(delDetailOV["value"]==="delDetail") {
						delDetail = "checked";
					}
				} else {
					delDetail = "checked";
				}
				arr.push('<tr>');
				arr.push('<td style="width:50%;padding: 5px 10px;text-align: left;color:#666666">【'+label+' - '+'${lfn:message("sys-modeling-auth:sysModelingAuth.OperationAuthority")}'+'】</td>');
				arr.push('<td align="center" colspan="2" style="padding: 5px 10px;">');
				arr.push('<div style="width:50%;float:left"><label>${lfn:message("sys-modeling-auth:sysModelingAuth.AllowToAdd") }<input type="checkbox" name="'+addDetailName+'" value="addDetail" '+addDetail+' data-detailid="'+ detailId +'" style="vertical-align: -2px;" onclick="setAuthCfgValue(\''+method+'\',\''+addDetailName+'\',this,\''+detailId+'\')"></label></div>');
				arr.push('<div style="width:50%;float:left"><label>${lfn:message("sys-modeling-auth:sysModelingAuth.AllowToDelete") }<input type="checkbox" name="'+delDetailName+'" value="delDetail" '+delDetail+' data-detailid="'+ detailId +'" style="vertical-align: -2px;" onclick="setAuthCfgValue(\''+method+'\',\''+delDetailName+'\',this,\''+detailId+'\')"></label></div></td>');
				arr.push('</tr>');
				return arr.join("");
			}

			function getOneHtml(one,type,prefix,method,detailId){
				if(!one["isStore"]){
					return;
				}
				var label=prefix+one["label"];
				if(one["control"]){
					label += "（"+one["control"]+"）";
				}
				var name = one["name"];
				var idp = name;
				if(name=="fdId"){
					return "";
				}
				if(detailId && one["type"] !== "textLabel" && type !=="detail"){
					idp = detailId+"_"+name;
					name = detailId+"."+name;
				}
				// if(type=="detail"){
				// 	label = prefix;
				// }
				var arr = [];
				var ov = getAuthCfgValue(method,name);
				var view="",edit="",hidden="";
				if(ov){
					if(ov["value"]=="view"){
						view = "checked";
					}
					if(ov["value"]=="edit"){
						edit = "checked";
					}
					if(ov["value"]=="hidden"){
						hidden = "checked";
					}
				}
				arr.push('<tr>');
				arr.push('<td><div  style="padding: 5px 10px; width:400px ;text-align: left;color:#666666 "><span title='+label+'>'+label+'</span></div></td>');
				var dis = "";
				if(method && method === 'view' || one["type"] === "textLabel" || type ==="detail"){
					//查看页面没有只读选项
					dis = "disabled";
				}
				arr.push('<td align="center"  style="padding: 5px 10px;"><input type="checkbox" name="'+name+'" value="view" '+view+' '+dis+' onclick="setAuthCfgValue(\''+method+'\',\''+name+'\',this,\''+detailId+'\')"></td>');
				var str1="",str2="",readerIds="",readerNames="";
				if(!ov){
					str1='style="display:none"';
					str2='class="cust_reader_unselect"';
				}else{
					if(ov["value"]!="hidden"){
						str1='style="display:none"';
					}else{
						if(ov["readerIds"] && ov["readerIds"]!=""){
							str2='class="cust_reader_selected"';
							readerIds=ov["readerIds"];
						}else{
							str2='class="cust_reader_unselect"';
						}
					}
				}
				arr.push('<td align="center"  style="padding: 5px 10px;"><input type="checkbox" name="'+name+'" value="hidden" '+hidden+' data-detailid="'+ detailId +'" onclick="setAuthCfgValue(\''+method+'\',\''+name+'\',this,\''+detailId+'\')">');
				arr.push('<input type="hidden" name="'+name+'_readerIds" value="'+readerIds+'"><input type="hidden" name="'+name+'_readerNames" value="'+readerNames+'">');
				arr.push('<span id="'+idp+'_readerSpan" title="'+'${lfn:message("sys-modeling-auth:sysModelingAuth.SetReadAbility") }'+'" '+str1+'>&nbsp;<button '+str2+'  id="'+idp+'_readerBtn" onclick="selectReader(\''+method+'\',\''+name+'\')"></span></td>');
				arr.push('</tr>');
				return arr.join("");
			}

			function init(){
				renderAuthCfg('add');
				setTimeout(function(){
					$(".lui_list_nav_list").find("li[data-path]:eq(0)").addClass("lui_list_nav_selected");
				},100);
			}

			Com_AddEventListener(window,'load',init);

			function dialogClose(){
				$dialog.hide();
			}

			seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
				window.dialogSave = function(){
						 $.ajax({
							 url: Com_Parameter.ContextPath + "sys/modeling/auth/xform_auth/sysModelingXformRightConfig.do?method=ajaxSave&fdAppModelId=${param.fdAppModelId}",
							 dataType : 'json',
							 type : 'post',
							 data:{  cfgJson : LUI.stringify(cfgJson) },
							 async : false,
							 success : function(data){
								 if(data.errcode==0){
									 dialog.success(data.errmsg);
									 setTimeout(function(){
										 dialogClose();
									 },2500);
								 }else{
									 dialog.failure(data.errmsg);
								 }
							 }
						 });
					}
			});
			Com_AddEventListener(window,"load",function(){
				var parentDoc = window.parent.document;
				$("#contentContainer").height($(parentDoc).find(".lui_dialog_content").eq(0).height() - 73);
			})





		</script>

	</template:replace>
</template:include>

