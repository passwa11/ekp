<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<%@page import="com.landray.kmss.third.im.kk.util.KK5Util"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%><script>
	Com_IncludeFile("doclist.js|dialog.js");
</script>
<script>
	var gzhInfo=new Array();
	var gzhSelected = "<%=ResourceUtil
									.getKmssConfigString("kmss.ims.notify.kk5.service.select")%>";
	function config_kk_onloadFunc() {
		var tbObj = document.getElementById("config.integrate.kk");
		var field = tbObj.rows[0].getElementsByTagName("INPUT")[0];
		for ( var i = 1; i < tbObj.rows.length; i++) {
			tbObj.rows[i].style.display = field.checked ? "" : "none";
			var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
			for ( var j = 0; j < cfgFields.length; j++) {
				cfgFields[j].disabled = !field.checked;
			}
		}
	}

	function config_kk5_onloadFunc() {
		var tbObj = document.getElementById("config.integrate.kk5");
		var field = tbObj.rows[0].getElementsByTagName("INPUT")[0];
		for ( var i = 1; i < tbObj.rows.length; i++) {
			tbObj.rows[i].style.display = field.checked ? "" : "none";
			var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
			for ( var j = 0; j < cfgFields.length; j++) {
				cfgFields[j].disabled = !field.checked;
			}
		}
		var _type = document.getElementsByName("value(kmss.kk5.notify.type)"), type = null;
		for(var i = 0; i < _type.length; i++) {
			if(_type[i].checked) {
				type = _type[i].value;
				break;
			}
		}
		if(type == null) {
			_type[0].checked = true;
			type = "corpNotify";
		}
		config_notify_type(field.checked,type);
	}

	function config_kk2_onloadFunc() {
		var tbObj = document.getElementById("config.integrate.kk2");
		var enable = field.checked && field2.checked;
		for ( var i = 1; i < tbObj.rows.length; i++) {
			tbObj.rows[i].style.display = field.checked ? "" : "none";
			var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
			for ( var j = 0; j < cfgFields.length; j++) {
				cfgFields[j].disabled = !enable;
			}
		}
	}
	var commonAccountListTdParam = {
			"border" : false,
			"width" : "60px",
			"type" : "select",
			"id" : "commonAccountList",
			"defaultInputText" : "",
			"checkInputText" : "",
			"defaultSelectValue" : "",
			"checkSelectValue" : "",
			"innerText" : ""
		};

		var commonAccountDescriptionTdParam = {
			"border" : false,
			"width" : "150px",
			"type" : "",
			"id" : "",
			"defaultInputText" : "",
			"checkInputText" : "",
			"defaultSelectValue" : "",
			"checkSelectValue" : "",
			"innerText" : ""
		};

		var commonAccountValues;

		function getCommonAccountValues() {
			if (commonAccountValues === undefined) {
				return new Array();
			}
			return commonAccountValues;
		}

		function createTdElement(trElement, tdParam, options) {
			var tdElement = trElement.insertCell(trElement.cells.length);
			if (tdParam.border == true) {
				tdElement.style.borderTop = "0px";
			}
			if (tdParam.width != "") {
				tdElement.style.width = tdParam.width;
			}
			if (tdParam.type == "select") {
				tdElement.innerHTML = getSelectElementHTML(tdParam.id, options,
						tdParam.checkSelectValue, tdParam.defaultSelectValue)
						+ tdParam.innerText;
				return tdElement;
			}
			tdElement.innerHTML = tdParam.innerText;
			return tdElement;
		}

		function getSelectElementHTML(id, options, checkSelectValue,
				defaultSelectValue) {
			var rtnResult = new Array();
			rtnResult.push('<option value=\'choose\'>${ lfn:message("third-im-kk:robotnode_hint_3") }</option>');
			if (options == null || options.length == 0) {
				var value = checkSelectValue || defaultSelectValue;
				if (value != '')
					rtnResult.push('<option value=\'' + value + '\' selected>'
							+ value + '</option>');
			} else {
				var value = checkSelectValue || defaultSelectValue;
				for (var i = 0, length = options.length; i < length; i++) {
					var option = options[i], optionValue = option.value;
					rtnResult.push('<option value=\'' + optionValue + '\'');
					if (optionValue == value) {
						rtnResult.push(' selected');
					}
					rtnResult.push('>' + option.name + '</option>');
				}
			}
			if (id == "commonAccountList") {
				return '<select name="value(kmss.ims.notify.kk5.service.select)" onchange="changeDescription(this);" id="' + id
						+ '"' + ' style=\'width: 160px;\'>' + rtnResult.join('')
						+ '</select>';
			}
			return '<select id="' + id + '"' + ' style=\'width: 160px;\'>'
					+ rtnResult.join('') + '</select>';
		}

		function changeDescription(select) {
			if (select.value == "choose") {
				document.getElementById("commonAccountDescription").innerHTML = "";
			} else {
				var values = getCommonAccountValues();
				for (var i = 0, length = values.length; i < length; i++) {
					if (values[i].value == select.value) {
						document.getElementById("commonAccountDescription").innerHTML = values[i].description;
						break;
					}
				}
			}
		}

		function createCommonAccountList(trElement, options) {
			if (trElement != null && options != null) {
				var tdElement = createTdElement(trElement,
						commonAccountListTdParam, options);
				tdElement = createTdElement(trElement,
						commonAccountDescriptionTdParam, null);
				tdElement.id = "commonAccountDescription";
			}
		}

		function transCommonAccountList(jsonArray) {
			var rtnResult = new Array();
			if (!jsonArray)
				return rtnResult;
			for (var i = 0, length = jsonArray.length; i < length; i++) {
				if(jsonArray[i].serviceState==0){
					continue;
				}
				rtnResult.push({
					value : jsonArray[i].corp+"&"+jsonArray[i].service,
					name : jsonArray[i].serviceName,
					description : jsonArray[i].serviceDesc
				});
			}
			return rtnResult;
		};

		function initCommonAccountList(enable) {
			var tdCommonAccount=document.getElementById("commonAccountList");
			var tdCommonAccountDescription=document.getElementById("commonAccountDescription");
			if(enable){
				if(tdCommonAccount==null){
					commonAccountListTdParam.checkSelectValue = gzhSelected;
					commonAccountValues=transCommonAccountList(gzhInfo);
					var values = getCommonAccountValues();
					for (var i = 0, length = values.length; i < length; i++) {
						if (values[i].value == commonAccountListTdParam.checkSelectValue) {
							commonAccountDescriptionTdParam.innerText = values[i].description;
							break;
						}
					}
					createCommonAccountList(document
							.getElementById("tr_kk5_gzh_Notify"), commonAccountValues);
				}else{
					document.getElementById("commonAccountList").value=gzhSelected;
					var values = getCommonAccountValues();
					for (var i = 0, length = values.length; i < length; i++) {
						if (values[i].value == gzhSelected) {
							document.getElementById("commonAccountDescription").innerHTML = values[i].description;
							break;
						}
					}
					tdCommonAccount.style.display="";
					tdCommonAccountDescription.style.display="";
				}
			}else{
				if(tdCommonAccount!=null){
					tdCommonAccount.style.display="none";
					tdCommonAccountDescription.style.display="none";
				}
			}
		}

	function handle(data){
		gzhInfo=data;
		if(gzhInfo.length==0){
			alert("没有获取到公众号列表，检查KK5服务器地址配置，以及KK5是否设置了公众号");
		}else{
			initCommonAccountList(true);
		}
	}

	function initialGzhInfo(){
		var kk5ServerUrl=document.getElementsByName("value(kmss.kk5.serverURL)")[0].value;
		var getGzhUrl=kk5ServerUrl+"/info/listService.ajax";
		$.getJSON(Com_Parameter.ContextPath+"third/im/kk/webparts/getGzhInfo.jsp?getGzhUrl="+getGzhUrl,function (data){handle(data);})
	}

	function enableChoosegzh() {
		var enableGzh = document.getElementsByName("_value(kmss.ims.notify.kk5.service.enable)")[0];
		if(enableGzh.checked){
			initialGzhInfo();
		}else{
			initCommonAccountList(false);
		}
	}

	function config_notify_type(kk5enable,notifyType){
		var tr_kk5_notifyToDo=document.getElementById("tr_kk5_notifyToDo");
		var tr_kk5_notifyToRead=document.getElementById("tr_kk5_notifyToRead");
		var tr_kk5_gzh_Notify=document.getElementById("tr_kk5_gzh_Notify");
		var corpNotifyMessage=document.getElementById("corpNotifyMessage");
		var gzhNotifyMessage=document.getElementById("gzhNotifyMessage");

		var kk5_notifyToDo = document.getElementsByName("value(kmss.ims.notify.todo.kk5.enable)")[0];
		var kk5_notifyToRead = document.getElementsByName("value(kmss.ims.notify.toread.kk5.enable)")[0];
		//
		if(typeof(kk5enable) == "undefined"){
			kk5enable=false;
		}
		if("corpNotify"==notifyType){
			tr_kk5_gzh_Notify.style.display = 'none';
			tr_kk5_notifyToDo.style.display = kk5enable?'':'none';
			tr_kk5_notifyToRead.style.display = kk5enable?'':'none';
			tr_kk5_notifyToDo.disabled = false;
			tr_kk5_notifyToRead.disabled = false;
			corpNotifyMessage.style.display = kk5enable?'':'none';
			gzhNotifyMessage.style.display = 'none';
			initCommonAccountList(false);
		}
		if("gzhNotify"==notifyType){
			tr_kk5_gzh_Notify.style.display = kk5enable?'':'none';
			tr_kk5_notifyToDo.style.display = 'none';
			tr_kk5_notifyToRead.style.display = 'none';
			tr_kk5_notifyToDo.disabled = true;
			tr_kk5_notifyToRead.disabled = true;
			corpNotifyMessage.style.display = 'none';
			gzhNotifyMessage.style.display = kk5enable?'':'none';
			initialGzhInfo();
		}
	}

	config_addOnloadFuncList(config_kk_onloadFunc);
	config_addOnloadFuncList(config_kk5_onloadFunc);
</script>
<table class="tb_normal" width=100%>
	<tr>
		<td>
		<table class="tb_normal" width=100% id="config.integrate.kk">
			<tr>
				<td class="td_normal_title" colspan="4"><b> <label>
				<xform:checkbox property="value(kmss.integrate.kk.enabled)"
					onValueChange="config_kk_onloadFunc()" showStatus="edit"
					htmlElementProperties="id=\"kmss.integrate.kk.enabled\"">
					<xform:simpleDataSource value="true">集成KK4</xform:simpleDataSource>
				</xform:checkbox> </label> </b></td>

			</tr>
			<tr>
				<td class="td_normal_title" width="15%">kk服务器地址</td>
				<td colspan="3"><xform:text
					property="value(third.im.kk.serverIp)" style="width:30%"
					subject="服务器IP" required="true" showStatus="edit"
					htmlElementProperties="disabled='true'" /> <span class="message">服务器地址：ip
				或者域名 如kk.landray.com 或 192.168.1.69</span></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">kk业务ID</td>
				<td colspan="3"><xform:text
					property="value(kmss.ims.notify.kk.requestid)" style="width:30%"
					subject="kk业务ID" required="true" showStatus="edit"
					htmlElementProperties="disabled='true'" /> <span class="message">(KK业务ID,requestid)</span>
				</td>
			</tr>

			<tr>
				<td class="td_normal_title" width="15%">kk推送待办待阅端口</td>
				<td colspan="3"><xform:text
					property="value(kmss.ims.notify.kk.port)" style="width:30%"
					subject="kk推送待办待阅端口" required="true" showStatus="edit"
					htmlElementProperties="disabled='true'" /> <span class="message">例：8002</span>
				</td>
			</tr>

			<tr>
				<td class="td_normal_title" width="15%">KK在线感知配置</td>
				<td><xform:checkbox property="value(kmss.ims.kk.enable)"
					showStatus="edit" htmlElementProperties="disabled='true'">
					<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
				</xform:checkbox> <span class="message">(在线感知激活项,启用以后配置的感知小图标会出现)</span></td>

				<td class="td_normal_title" width="15%">所需端口</td>
				<td><xform:text property="value(kmss.ims.kk.awareport)"
					style="width:30%" subject="kk在线感知所需端口" required="false"
					showStatus="edit" htmlElementProperties="disabled='true'" /> <span
					class="message">例：8081</span></td>
			</tr>

			<tr>
				<td class="td_normal_title" width="15%">启用KK待办推送</td>
				<td colspan=3><xform:checkbox
					property="value(kmss.ims.notify.todo.kk.enable)" showStatus="edit"
					htmlElementProperties="disabled='true'">
					<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
				</xform:checkbox> <span class="message">(根据配置的地址实时推送待办到KK客户端)</span></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">启用KK待阅推送</td>
				<td colspan=3><xform:checkbox
					property="value(kmss.ims.notify.toread.kk.enable)"
					showStatus="edit" htmlElementProperties="disabled='true'">
					<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
				</xform:checkbox> <span class="message">(根据配置的地址实时推送待阅到KK客户端)</span></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td>
		<table class="tb_normal" width=100% id="config.integrate.kk5">
			<tr>
				<td class="td_normal_title" colspan="4"><b> <label>
				<xform:checkbox property="value(kmss.integrate.kk5.enabled)"
					onValueChange="config_kk5_onloadFunc()" showStatus="edit"
					htmlElementProperties="id=\"kmss.integrate.kk5.enabled\"">
					<xform:simpleDataSource value="true">集成KK5</xform:simpleDataSource>
				</xform:checkbox> </label> </b></td>
			</tr>

			<tr>
				<td class="td_normal_title" width="15%">服务器地址</td>
				<td colspan="3"><xform:text
					property="value(kmss.kk5.serverURL)" style="width:30%"
					subject="kk5服务器地址" required="true" showStatus="edit"
					htmlElementProperties="disabled='true'" /> <span class="message">KK5服务器地址，例：http://192.168.1.69:8100/serverj</span>
				</td>
			</tr>

			<tr>
				<td class="td_normal_title" width="15%">待办推送方式</td>
				<td><xform:radio property="value(kmss.kk5.notify.type)"
					showStatus="edit"
					onValueChange="config_notify_type(true,this.value);"
					subject="待办推送方式" required="true">
					<xform:simpleDataSource value="corpNotify">应用消息</xform:simpleDataSource>
					<xform:simpleDataSource value="gzhNotify">公众号</xform:simpleDataSource>
				</xform:radio></td>
				<td colspan="2"><span id="corpNotifyMessage" class="message">将待办发送到KK，登录KK在待办待阅处查看消息</span>
				<span id="gzhNotifyMessage" class="message">将待办发送到公众号，需要关注该公众号才能看到待办消息</span></td>
			</tr>

			<tr id="tr_kk5_notifyToDo">
				<td class="td_normal_title" width="15%">待审推送</td>
				<td colspan=3><xform:checkbox
					property="value(kmss.ims.notify.todo.kk5.enable)" showStatus="edit"
					htmlElementProperties="disabled='true'">
					<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
				</xform:checkbox></td>
			</tr>
			<tr id="tr_kk5_notifyToRead">
				<td class="td_normal_title" width="15%">待阅推送</td>
				<td colspan=3><xform:checkbox
					property="value(kmss.ims.notify.toread.kk5.enable)"
					showStatus="edit" htmlElementProperties="disabled='true'">
					<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
				</xform:checkbox></td>
			</tr>
			<tr id="tr_kk5_gzh_Notify">
				<td class="td_normal_title" width="15%">公众号</td>

			</tr>

		</table>

		</td>
	</tr>

	<tr>
		<td>
		<table>
			<tr>
				<td class="td_normal_title" width="15%">待办URL地址前缀</td>
				<td colspan=3><xform:text
					property="value(kmss.ims.notify.urlPrefix)" style="width:5%"
					showStatus="edit" /><span class="message">(推送到kk的待办的URL地址前缀，默认情况下填ekp的DNS地址即可；如果留空，则取admin.do中配置的“服务器DNS”的值)</span>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">kk待办归档日志保存期限</td>
				<td colspan=3><xform:text
					property="value(kmss.ims.notify.kk.logBak.days)" style="width:5%"
					showStatus="edit" /> 天 <span class="message">(kk待办待阅归档日志保存期限，系统定时清理该期限之前的归档日志，默认按30天处理)</span>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">kk待办发送失败次数设置</td>
				<td colspan=3><xform:text
					property="value(kmss.ims.notify.kk.failure.times)" style="width:5%"
					showStatus="edit" /> 次 <span class="message">(kk待办发送失败次数超过此设置值后，停止发送，这个是为了访问KK服务器连不上后一直发送待办导致的性能消耗，
				管理员可在处理完kk服务器的连接问题后，在此处重新启用同步：<a target="_blank"
					href='<c:url value="/third/im/kk.index"/>'>/third/im/kk.index</a>；
				默认按1000次处理，设置为-1则不限制)</span></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">失败次数超过后发待办到该账号</td>
				<td colspan=3><xform:text
					property="value(kmss.ims.notify.kk.failure.notify)"
					style="width:10%" showStatus="edit" /> <span class="message">(这里填账号的登录名，不设的话则不发送)</span>
				</td>
			</tr>

			<tr>
				<td class="td_normal_title" width="15%">WebKK服务器地址</td>
				<td colspan="3"><xform:text
					property="value(third.im.kk.webkk.urlPrefix)" subject="WebKK服务器地址"
					showStatus="edit" style="width:30%" /> <span class="message">WebKK服务器地址，例：http://webkk.landray.com.cn:8081，</span></td>
			</tr>

		</table>
		</td>
	</tr>
</table>
