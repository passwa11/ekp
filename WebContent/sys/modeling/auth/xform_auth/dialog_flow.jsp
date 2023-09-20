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
	background:none!important;
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
							<ui:content id="noneProcessor" title="${lfn:message('sys-modeling-auth:sysModelingAuth.NotProcessingScenario') }" style="text-align: center;display:block;">
								<ui:combin ref="menu.nav.simple">
									<ui:varParam name="source">
										<ui:source type="Static">
											[
											{
											"text" : "${lfn:message('sys-modeling-auth:sysModelingAuth.NotProcessingPeople') }",
											"href" :  "javascript:renderAuthCfg('');"
											}
											]
										</ui:source>
									</ui:varParam>
								</ui:combin>
							</ui:content>
							<%--流程处理人 --%>
							<ui:content title="${lfn:message('sys-modeling-auth:sysModelingAuth.ProcessingPeople') }"  style="text-align: center;display:block;">
								<ui:combin ref="menu.nav.simple">
									<ui:varParam name="source">
										<ui:source type="Static">
											${menus}
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
<%--			<table style="margin-top:10px">--%>
<%--			<tr>--%>
<%--				<td width="200px;" valign="top" >--%>
<%--					<div id="menu_nav" class="lui_list_nav_frame">	--%>
<%--					<ui:accordionpanel style="width:100%;text-align: center;display:block;background-color:inherit">--%>
<%--						<ui:content id="noneProcessor" title="非流程处理场景" style="text-align: center;display:block;">--%>
<%--							<ui:combin ref="menu.nav.simple">--%>
<%--								<ui:varParam name="source">--%>
<%--									<ui:source type="Static">--%>
<%--									[--%>
<%--										{--%>
<%--										"text" : "非流程处理人",--%>
<%--										"href" :  "javascript:renderAuthCfg('');"--%>
<%--										}--%>
<%--									]							--%>
<%--									</ui:source>--%>
<%--								</ui:varParam>--%>
<%--							</ui:combin>	--%>
<%--						</ui:content>--%>
<%--						<ui:content title="流程处理人"  style="text-align: center;display:block;">--%>
<%--							<ui:combin ref="menu.nav.simple">--%>
<%--								<ui:varParam name="source">--%>
<%--									<ui:source type="Static">--%>
<%--										${menus}--%>
<%--									</ui:source>--%>
<%--								</ui:varParam>--%>
<%--							</ui:combin>	--%>
<%--						</ui:content>--%>
<%--					</ui:accordionpanel>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<!----%>
<%--				<td width="500px;" valign="top" style="border: #47b6e4 1px solid">--%>
<%--				-->--%>
<%--				<td width="550px;" valign="top">--%>
<%--					<table width="100%" border="0" style="border-collapse: collapse;" class="tb_normal" id="authcfgId">--%>
<%--					</table>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--			<!-- <tr>--%>
<%--				<td id="tipsId" style="font-weight: bold;font-size: 14px;color:#51b6ec"></td>--%>
<%--				<td>--%>
<%--				  <div class="lui_custom_list_box_content_col_btn">--%>
<%--					<a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="dialogSave()">保存</a>--%>
<%--					<a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="dialogClose()">关闭</a>--%>
<%--				  </div>--%>
<%--				</td>--%>
<%--			</tr> -->--%>
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
			// [ {"id": "N2", "name": "起草节点", "type": "draftNode" },...];
			var nodesCfg = ${nodes};

			//{mains”:[{name:"",label:"",type:""}],"details":[{name:"",label:"",cols:[{name:"",label:"",type:""}]}],"attachments":[{name:"",label:"",type:""}]}
			var xformCfg = ${xform};

			//	{"mode_dg":{"fd_37b6900a4e9c16":{"value":"hidden","readerIds":"15874fb98aaf78a4ef367134fcd82741"}},
			//"mode_n":{"N4":{"fd_37b6900807ce0a":{"value":"edit"}}}};
			var cfgJson = ${cfgJson};

			function getNodeObject(nodeId){
				for(var i=0;i< nodesCfg.length;i++){
					if(nodeId==nodesCfg[i]["id"]){
							return nodesCfg[i];
						}
				}
			}

			function initAuthCfgModel(nodeId){
				var cfgKey = "mode_dg";
				if(nodeId!=""){
					cfgKey = "mode_n";
				}
				if(typeof cfgJson[cfgKey] =="undefined"){
					cfgJson[cfgKey]={};
				}
				return cfgKey;
			}

			function setAuthCfgValue(nodeId,xformId,el,detailId){
				var value = el.value;
				var els = document.getElementsByName(xformId);
				for(var i=0;i<els.length;i++){
					if(els[i].value!=value){
						els[i].checked=false;
					}
				}

				var cfgKey = initAuthCfgModel(nodeId);
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
					if(nodeId==""){
						cfgJson[cfgKey][xformId]= {"value":""};
					}else{
						cfgJson[cfgKey][nodeId][xformId]= {"value":""};
					}
					$("#"+rid).hide();
					return;
				}

				var one = {"value":value};
				if(nodeId==""){
					cfgJson[cfgKey][xformId]=one;
				}else{
					if(typeof  cfgJson[cfgKey][nodeId] =="undefined"){
						cfgJson[cfgKey][nodeId]={};
					}
					cfgJson[cfgKey][nodeId][xformId]=one;
				}

				if(value=="hidden"){
					$("#"+rid).show();
					rid = xformId.replace(".","_")+"_readerBtn";
					$("#"+rid).attr("class","cust_reader_unselect");
				}else{
					$("#"+rid).hide();
				}

			}

			function getAuthCfgValue(nodeId,xformId){
				var cfgKey = initAuthCfgModel(nodeId);
				if(nodeId==""){
					return cfgJson[cfgKey][xformId];
				}else{
					if(typeof  cfgJson[cfgKey][nodeId] =="undefined"){
						cfgJson[cfgKey][nodeId]={};
					}
					return cfgJson[cfgKey][nodeId][xformId];
				}
			}

			function selectReader(nodeId,xformId){
				var _readerIds = document.getElementsByName(xformId+'_readerIds');
				var _readerNames = document.getElementsByName(xformId+'_readerNames');
				Dialog_Address(true,_readerIds[0],_readerNames[0],null,null,function(data) {
					setAuthCfgReader(nodeId,xformId,_readerIds[0].value,_readerNames[0].value);
				});
			}

			function setAuthCfgReader(nodeId,xformId,readerIds,readerNames){
				var cfgKey = initAuthCfgModel(nodeId);
				if(nodeId==""){
					cfgJson[cfgKey][xformId]["readerIds"]=readerIds;
					cfgJson[cfgKey][xformId]["readerNames"]=readerNames;
				}else{
					cfgJson[cfgKey][nodeId][xformId]["readerIds"]=readerIds;
					cfgJson[cfgKey][nodeId][xformId]["readerNames"]=readerNames;
				}
				var rid = xformId.replace(".","_")+"_readerBtn";
				if(readerIds==""){
					$("#"+rid).attr("class","cust_reader_unselect");
				}else{
					$("#"+rid).attr("class","cust_reader_selected");
				}
			}

			function renderAuthCfg(nodeId){
				var html = [];
				html.push(getHeaderHtml());
				html.push(getContentHtml(nodeId));
				$("#authcfgId").html(html.join(""));
				var tips = "${lfn:message('sys-modeling-auth:sysModelingAuth.NotProcessingPeople') }";
				if(nodeId!=""){
					tips = getNodeObject(nodeId)["name"];
				}
				tips="${lfn:message('sys-modeling-auth:sysModelingAuth.CurrentSettings') }"+tips;
				$("#tipsId").html("&nbsp;&nbsp;"+tips);
				var details = xformCfg["details"];
				if(details) {
					for (var i = 0; i < details.length; i++) {
						var xformValue = getAuthCfgValue(nodeId,details[i]["name"]);
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
				arr.push('<tr  class="tr_normal_title">');
				arr.push('<td  style="padding: 10px 10px;">${lfn:message("sys-modeling-auth:sysModelingAuth.FormField") }</td>');
				arr.push('<td align="center"  style="padding: 10px 10px;">${lfn:message("sys-modeling-auth:sysModelingAuth.ReadOnly") }</td>');
				arr.push('<td align="center"  style="padding: 10px 10px;">${lfn:message("sys-modeling-auth:sysModelingAuth.Edit") }</td>');
				arr.push('<td align="center"  style="padding: 10px 10px;">${lfn:message("sys-modeling-auth:sysModelingAuth.Hide") }</td>');
				arr.push('</tr>');
				return arr.join("");
			}

			function getContentHtml(nodeId){
				var arr = [];
				var mains = xformCfg["mains"];
				if(mains){
					for(var i=0;i<mains.length;i++){
						arr.push(getOneHtml(mains[i],"main","${lfn:message('sys-modeling-auth:sysModelingAuth.MainTable') }",nodeId));
					}
				}
				var details = xformCfg["details"];
				if(details){
					for(var i=0;i<details.length;i++){
						var prefix = "【"+details[i]["label"]+"】";
						arr.push(getOneHtml(details[i],"detail","${lfn:message('sys-modeling-auth:sysModelingAuth.MainTable') }",nodeId,details[i]["name"]));
						var cols = details[i]["cols"];
						if(cols){
							for(var j=0;j<cols.length;j++){
								arr.push(getOneHtml(cols[j],"col",prefix,nodeId,details[i]["name"]));
							}
						}
						arr.push(getOperAuthRowHtml(details[i]["label"], nodeId,details[i]["name"]));

						var attachments = details[i]["attachments"];
						if(attachments){
							for(var j=0;j<attachments.length;j++){
								var attach =attachments[j];
								attach.isStore = true;
								arr.push(getOneHtml(attach,"attachment",prefix,nodeId,details[i]["name"]));
							}
						}
					}
				}
				var attachments = xformCfg["attachments"];
				if(attachments){
					for(var i=0;i<attachments.length;i++){
						var attach =attachments[i];
						attach.isStore = true;
						arr.push(getOneHtml(attach,"attachment","${lfn:message('sys-modeling-auth:sysModelingAuth.Enclosure') }",nodeId));
					}
				}

				var labels = xformCfg["labels"];
				if (labels){
					for (var i = 0; i < labels.length; i++) {
						var label = labels[i];
						label.isStore = true;
						arr.push(getOneHtml(label,"textLabel","${lfn:message('sys-modeling-auth:sysModelingAuth.Text') }",nodeId));
					}
				}
				return arr.join("");
			}

			function getOperAuthRowHtml(label, nodeId, detailId) {
				if(!nodeId || nodeId === 'view'){
					return;
				}
				let addDetailName = detailId + "." + "operation_auth_addDetail";
				let delDetailName = detailId + "." + "operation_auth_delDetail";
				let arr = [];
				let addDetailOV = getAuthCfgValue(nodeId, addDetailName);
				let delDetailOV = getAuthCfgValue(nodeId, delDetailName);
				let addDetail="",delDetail="";
				if(addDetailOV) {
					if(addDetailOV["value"]==="addDetail") {
						addDetail = "checked";
					}
				} else if(nodeId==="N2"){
					//起草节点默认勾选
					addDetail = "checked";
				}
				if(delDetailOV) {
					if(delDetailOV["value"]==="delDetail") {
						delDetail = "checked";
					}
				} else if(nodeId==="N2"){
					delDetail = "checked";
				}
				arr.push('<tr>');
				arr.push('<td style="width:50%;padding: 5px 10px;text-align: left;color:#666666">【'+label+' - '+'${lfn:message("sys-modeling-auth:sysModelingAuth.OperationAuthority")}'+ '】</td>');
				arr.push('<td align="center" colspan="3" style="padding: 5px 10px;">');
				arr.push('<div style="width:50%;float:left"><label>${lfn:message("sys-modeling-auth:sysModelingAuth.AllowToAdd") }<input type="checkbox" name="'+addDetailName+'" value="addDetail" '+addDetail+' data-detailid="'+ detailId +'" style="vertical-align: -2px;" onclick="setAuthCfgValue(\''+nodeId+'\',\''+addDetailName+'\',this,\''+detailId+'\')"></label></div>');
				arr.push('<div style="width:50%;float:left"><label>${lfn:message("sys-modeling-auth:sysModelingAuth.AllowToDelete") }<input type="checkbox" name="'+delDetailName+'" value="delDetail" '+delDetail+' data-detailid="'+ detailId +'" style="vertical-align: -2px;" onclick="setAuthCfgValue(\''+nodeId+'\',\''+delDetailName+'\',this,\''+detailId+'\')"></label></div></td>');
				arr.push('</tr>');
				return arr.join("");
			}

			function getOneHtml(one,type,prefix,nodeId,detailId){
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
				var ov = getAuthCfgValue(nodeId,name);
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
				arr.push('<td  style="width:50%;padding: 5px 10px;text-align: left;color:#666666">'+label+'</td>');
				var readonlyDis = "";
				if(one["type"] === "textLabel" || type ==="detail"){
					//文本控件没有只读选项
					readonlyDis = "disabled";
				}
				arr.push('<td align="center"  style="padding: 5px 10px;"><input type="checkbox" name="'+name+'" value="view" '+view+' ' + readonlyDis + ' onclick="setAuthCfgValue(\''+nodeId+'\',\''+name+'\',this,\''+detailId+'\')"></td>');
				var dis = "";
				if(nodeId=="" || nodeId=="N2" || type=="detail" || one["type"] === "textLabel"){
					//非流程处理人和起草节点没有可编辑选项
					dis = "disabled";
				}
				arr.push('<td align="center"  style="padding: 5px 10px;"><input type="checkbox" name="'+name+'" value="edit"  '+edit+' '+dis+' onclick="setAuthCfgValue(\''+nodeId+'\',\''+name+'\',this,\''+detailId+'\')"></td>');
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
				arr.push('<td align="center"  style="padding: 5px 10px;"><input type="checkbox" name="'+name+'" value="hidden" '+hidden+' data-detailid="'+ detailId +'" onclick="setAuthCfgValue(\''+nodeId+'\',\''+name+'\',this,\''+detailId+'\')">');
				arr.push('<input type="hidden" name="'+name+'_readerIds" value="'+readerIds+'"><input type="hidden" name="'+name+'_readerNames" value="'+readerNames+'">');
				if(nodeId==""){
					arr.push('<span id="'+idp+'_readerSpan" title="'+'${lfn:message("sys-modeling-auth:sysModelingAuth.SetReadAbility") }'+'" '+str1+'>&nbsp;<button '+str2+'  id="'+idp+'_readerBtn" onclick="selectReader(\''+nodeId+'\',\''+name+'\')"></span>');
				}
				arr.push('</td>');
				arr.push('</tr>');
				return arr.join("");
			}

			function init(){
				renderAuthCfg("");
				setTimeout(function(){
					$("#noneProcessor").find(".lui_list_nav_list").find("li[data-path]").addClass("lui_list_nav_selected");
				},100);
			}

			Com_AddEventListener(window,'load',init);

			function dialogClose(){
				$dialog.hide();
			}

			seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
				window.dialogSave = function(){
						 $.ajax({
							 url: Com_Parameter.ContextPath + "sys/modeling/auth/xform_auth/sysModelingXformRightConfig.do?method=ajaxSave&fdAppFlowId=${param.fdAppFlowId}",
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

