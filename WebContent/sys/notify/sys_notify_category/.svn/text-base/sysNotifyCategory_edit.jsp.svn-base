<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@page import="java.util.*,com.landray.kmss.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("common.js|dialog.js|json2.js");
Com_IncludeFile("select.js|doclist.js");

	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = setFdModelNames;

	var _selectedJsonObjects=null;

	Com_AddEventListener(window,"load",function(){
		var fdModelNames = document.getElementsByName("fdModelNames")[0].value;
		if(fdModelNames != null && fdModelNames != "")
			_selectedJsonObjects = JSON.parse(document.getElementsByName("fdModelNames")[0].value);
	});

	function setFdModelNames(){
		var names = [];
		/*
		var tmp_MNameSel = document.getElementsByName("tmp_MNameSel")[0];
		if(tmp_MNameSel.length>10){
			alert("<bean:message bundle="sys-notify" key="sysNotifyCategory.fdModelNames.note"/>");
			return false;
		}
		var n = 0;
		for(var i=0;i<tmp_MNameSel.length;i++){
			var param={};
			param["name"]=tmp_MNameSel[i].value;
			param["label"]=tmp_MNameSel[i].text;
			names[n++]=param;
		}
		*/

		if(_selectedJsonObjects!=null){
			names = names.concat(_selectedJsonObjects);
		}

		
		var count = document.getElementsByName("other.param.name").length;
		if(count>10){
			alert("<bean:message bundle="sys-notify" key="sysNotifyCategory.fdModelNames.note"/>");
			return false;
		}

		//#161653 #127529 需要把已经选择了的outer类型数据去除，否则后面的明细数据会导致重复
		var reNames = [];

		for(var i=0;i<names.length;i++){
			var name = names[i];
			if("outer" != name.type){
				reNames.push(name);
			}
		}

		for(var i=0;i<count;i++){
			var param={};
			param["name"]=document.getElementsByName("other.param.name")[i].value;
			param["label"]=document.getElementsByName("other.param.label")[i].value;
			param["type"]="outer";
			reNames.push(param);
		}
		if (reNames.length > 20) {
			alert("<bean:message bundle="sys-notify" key="sysNotifyCategory.fdModelNames.note2"/>");
			return false;
		}
		document.getElementsByName("fdModelNames")[0].value=JSON.stringify(reNames);
		return true;
	}

	function importModelCateDialog(){
		Dialog_Tree(true, 'tempValues', 
				'hiddentexts', 
					 ';', 
					 'modelCategoryTreeService&top=true', 
					 '<bean:message key="lbpmAuthorize.lbpmAuthorizeScope" bundle="sys-lbpmext-authorize"/>',
					  null, afterClickedDialogAction,
					   null, null, null,
					   '<bean:message key="lbpmAuthorize.lbpmAuthorizeScope" bundle="sys-lbpmext-authorize"/>');
	}
	
	function afterClickedDialogAction(obj){
		if(obj == null){
			return null;
		}
		_selectedJsonObjects=[];
		var fdModelNamesShowtextsObj = document.getElementsByName("fdModelNamesShowtexts")[0];
		var fdModelNamesShowtexts = "";
		var hiddentextsObj = document.getElementsByName("hiddentexts")[0];
		var hiddentexts = "";
		var showText = "";
		for(var i = 0; i < obj.data.length; i++){
			var urlValue = obj.data[i].id;
			showText = GetUrlParameter_Unescape(urlValue, "showText");
			var categoryId = GetUrlParameter_Unescape(urlValue, "categoryId");
			var categoryName = GetUrlParameter_Unescape(urlValue, "categoryName");
			var modelName = GetUrlParameter_Unescape(urlValue, "modelName");
			var moduleName = GetUrlParameter_Unescape(urlValue, "moduleName");
			var templateId = GetUrlParameter_Unescape(urlValue, "templateId");
			var templateName = GetUrlParameter_Unescape(urlValue, "templateName");
			var _selectedJsonObject = {};
			_selectedJsonObject["name"] = modelName;
			_selectedJsonObject["label"] = showText;
			_selectedJsonObject["module"] = moduleName;
			_selectedJsonObject["type"] = "model";
			if(categoryId!= null){
				_selectedJsonObject["cateId"] = categoryId;
				_selectedJsonObject["cateName"] = categoryName;
				_selectedJsonObject["type"] = "cate";
			}
			if(templateId!= null){
				_selectedJsonObject["tempId"] = templateId;
				_selectedJsonObject["tempName"] = templateName;
				_selectedJsonObject["type"] = "template";
			}
			_selectedJsonObjects.push(_selectedJsonObject);
			fdModelNamesShowtexts += (showText == null || showText==""?" ":showText) + "\n";
			hiddentexts += (showText == null?" ":showText) + ";";
		}
		fdModelNamesShowtextsObj.value = fdModelNamesShowtexts;
		hiddentextsObj.value = hiddentexts;
	}

	/**
	 * 获取URL中的参数（使用unescape对返回参数值解码）
	 */
	function GetUrlParameter_Unescape(url, param){
		var re = new RegExp();
		re.compile("[\\?&]"+param+"=([^&]*)", "i");
		var arr = re.exec(url);
		if(arr==null) {
			return null;
		} else {
			return unescape(arr[1]);
		}
	}

</script>

<script>DocList_Info.push('paramTable');</script>

<kmss:windowTitle
	subject="${sysNotifyCategoryForm.fdName}"
	moduleKey="sys-notify:table.sysNotifyCategory" />

<html:form action="/sys/notify/sys_notify_category/sysNotifyCategory.do">
<div id="optBarDiv">
	<c:if test="${sysNotifyCategoryForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysNotifyCategoryForm, 'update');">
	</c:if>
	<c:if test="${sysNotifyCategoryForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysNotifyCategoryForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysNotifyCategoryForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-notify" key="table.sysNotifyCategory"/></p>

<center>
<table class="tb_normal" width=95%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-notify" key="sysNotifyCategory.fdCateType"/>
		</td>
		<td colspan="3">
			<xform:radio property="fdCateType" onValueChange="onFdCateTypeChange" showStatus="edit">
				<xform:simpleDataSource value="0">
					<bean:message bundle="sys-notify" key="sysNotifyCategory.type.module" /> 
				</xform:simpleDataSource>
				<xform:simpleDataSource value="1">
					<bean:message bundle="sys-notify" key="sysNotifyCategory.type.system" /> 
				</xform:simpleDataSource>
				
			</xform:radio>
		</td>
		
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-notify" key="sysNotifyCategory.fdName"/>
		</td>
		<td width=35% >
			<xform:text property="fdName" style="width:85%" required="true"/>
			<div class="appNameTip"><bean:message bundle="sys-notify" key="sysNotifyCategory.fdAppNames.tip"/></div>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-notify" key="sysNotifyCategory.fdOrder"/>
		</td>
		<td width=35%>
			<xform:text property="fdOrder" style="width:85%" validators="number"/>
		</td>
	</tr>
	<!-- 发起人范围 -->
	<tr class="module">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-notify" key="sysNotifyCategory.createrScope"/>
		</td>
		<td colspan="3" >
			<xform:address textarea="true" propertyName="fdOrgOrDeptNames" mulSelect="true" propertyId="fdOrgOrDeptIds" style="width:80%;" orgType="ORG_TYPE_ORGORDEPT"></xform:address>
		</td>
	</tr>
	<tr class="module">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-notify" key="sysNotifyCategory.fdModelNames"/>
		</td>
		<td colspan="3" >
			<html:hidden property="tempValues"/>
			<input type="hidden" name="hiddentexts" value="${hiddentexts}">
			<textarea style="width:80%;height:150px;vertical-align:middle;" readonly name="fdModelNamesShowtexts">${showtexts}</textarea>
			<!-- <br> -->
			<input class="btnopt" type="button"
				onclick="importModelCateDialog();"
				value="<bean:message key="dialog.selectOther" />">

			<!--
			<table class="tb_normal" width="60%">
				<tr class="tr_normal_title">
					<td><bean:message key="dialog.optList"/></td>
					<td>&nbsp;</td>
					<td><bean:message key="dialog.selList"/></td>
				</tr>
				<tr>
					<td style="width:280px">
						<select name="tmp_MNameOpt" multiple="true" size="15" style="width:280px"
							ondblclick="Select_AddOptions('tmp_MNameOpt', 'tmp_MNameSel');">
							<%
							List<Map<String,String>> optMNames = (List<Map<String,String>>)request.getAttribute("optMNames");
							for(Map<String,String> optMName:optMNames){
								out.print("<option value='"+optMName.get("value")+"'>"+optMName.get("text")+"</option>");
							}
							%>
						</select>
					</td>
					<td>
						<center>
							<input class="btnopt" type="button" value="<bean:message key="dialog.add"/>"
								onclick="Select_AddOptions('tmp_MNameOpt', 'tmp_MNameSel');"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.delete"/>"
								onclick="Select_DelOptions('tmp_MNameSel');"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.addAll"/>"
								onclick="Select_AddOptions('tmp_MNameOpt', 'tmp_MNameSel', true);"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.deleteAll"/>"
								onclick="Select_DelOptions('tmp_MNameSel', true);"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.moveUp"/>"
								onclick="Select_MoveOptions('tmp_MNameSel', -1);"><br><br>
							<input class="btnopt" type="button" value="<bean:message key="dialog.moveDown"/>"
								onclick="Select_MoveOptions('tmp_MNameSel', 1);">
						</center>
					</td>
					<td style="width:280px">
						<select name="tmp_MNameSel" multiple="true" size="15" style="width:280px"
							ondblclick="Select_DelOptions('tmp_MNameSel');">
							<%
							JSONArray selMNames = (JSONArray)request.getAttribute("selMNames");
							if(selMNames!=null){
								for(int i=0;i<selMNames.size();i++){
									JSONObject selMName = selMNames.getJSONObject(i);
									out.print("<option value='"+selMName.getString("name")+"'>"+selMName.getString("label")+"</option>");
								}
							}
							%>
						</select>
					</td>
				</tr>
			</table>
			-->
			<html:hidden property="fdModelNames" />
		</td>
	</tr>

	<tr class="module">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-notify" key="sysNotifyCategory.outer.fdModelNames"/>
		</td>
		<td colspan="3" width="85%">
				<table class="tb_normal" id="paramTable" style="width:100%">
					<tr class="tr_normal_title">
						<td style="width:30px"><span style="white-space:nowrap;"><bean:message key="page.serial"/></span></td>
						<td style="width:40%"><bean:message bundle="sys-notify" key="sysNotifyCategory.fdModelNames"/></td>
						<td style="width:55%"><bean:message bundle="sys-notify" key="sysNotifyCategory.fdClassNames"/></td>
						<td style="width:100px"><div style="text-align:center"><img style='cursor:pointer' class=optStyle src='<c:url value='/resource/style/default/icons/add.gif'/>' onclick='DocList_AddRow();' alt="<bean:message key="button.insert"/>"></div></td>
					</tr>
					<tr KMSS_IsReferRow="1" style="display:none">
						<td KMSS_IsRowIndex="1"></td> 
						<td>
							<input type="text" name="other.param.label" subject="<bean:message bundle="sys-notify" key="sysNotifyCategory.fdModelNames"/>" validate="required" style="width:90%" class="inputSgl"><span class="txtstrong">*</span>
						</td>
						<td>
							<input type="text" name="other.param.name" subject="<bean:message bundle="sys-notify" key="sysNotifyCategory.fdClassNames"/>" validate="required" style="width:90%" class="inputSgl"><span class="txtstrong">*</span>
						</td>
						<td>
							<div style="text-align:center">
							<img src="<c:url value="/resource/style/default/icons/delete.gif" />" alt="<bean:message key="button.delete"/>" onclick="DocList_DeleteRow();" style="cursor:pointer">&nbsp;&nbsp;
							<img src="<c:url value="/resource/style/default/icons/up.gif" />" alt="<bean:message key="dialog.moveUp"/>" onclick="DocList_MoveRow(-1);" style="cursor:pointer">&nbsp;&nbsp;
							<img src="<c:url value="/resource/style/default/icons/down.gif" />" alt="<bean:message key="dialog.moveDown"/>" onclick="DocList_MoveRow(1);" style="cursor:pointer">&nbsp;&nbsp;
							</div>
						</td>
					</tr>

					<%
					JSONArray others = (JSONArray)request.getAttribute("others");
					if(others!=null){
						for(int i=0;i<others.size();i++){
							JSONObject other = others.getJSONObject(i);
					%>
					<tr KMSS_IsContentRow="1">
						<td><%=(i+1)%></td>
						<td>
							<input type="text" name="other.param.label" subject="<bean:message bundle="sys-notify" key="sysNotifyCategory.fdModelNames"/>"  validate="required" value="<%=other.getString("label")%>" style="width:90%" class="inputSgl"><span class="txtstrong">*</span>
						</td>
						<td>
							<input type="text" name="other.param.name"  subject="<bean:message bundle="sys-notify" key="sysNotifyCategory.fdClassNames"/>" validate="required" value="<%=other.getString("name")%>"  style="width:90%" class="inputSgl"><span class="txtstrong">*</span>
						</td>
						<td>
							<div style="text-align:center">
							<img src="<c:url value="/resource/style/default/icons/delete.gif" />" alt="<bean:message key="button.delete"/>" onclick="DocList_DeleteRow();" style="cursor:pointer">&nbsp;&nbsp;
							<img src="<c:url value="/resource/style/default/icons/up.gif" />" alt="<bean:message key="dialog.moveUp"/>" onclick="DocList_MoveRow(-1);" style="cursor:pointer">&nbsp;&nbsp;
							<img src="<c:url value="/resource/style/default/icons/down.gif" />" alt="<bean:message key="dialog.moveDown"/>" onclick="DocList_MoveRow(1);" style="cursor:pointer">&nbsp;&nbsp;
							</div>
						</td>
					</tr>
				<%
							}
						}
				%>
				</table>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-notify" key="sysNotifyCategory.MobileLink"/></td>
			<td colspan="3"> <input type="input"  id="mobileUrl" style="width:700px;"/>
				<input type=button class="btnopt"
					value="<bean:message bundle="sys-notify" key="sysNotifyCategory.makeTodoUrl"/>"
					onclick="makeMobileTodoUrl();">
				<input type=button class="btnopt"
					value="<bean:message bundle="sys-notify" key="sysNotifyCategory.makeToviewUrl"/>"
					onclick="makeMobileToviewUrl();">
				<input type=button class="btnopt"
					value="<bean:message bundle="sys-notify" key="sysNotifyCategory.copyUrlToClipboard"/>"
					onclick="copyMobileUrl();">
				<input type=button class="btnopt"
					value="<bean:message bundle="sys-notify" key="sysNotifyCategory.OpenPage"/>"
					onclick="OpenPage();">	
			</td>
	</tr>
		<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-notify" key="sysNotifyCategory.PCLink"/></td>
			<td colspan="3"> <input type="input"  id="PCUrl" style="width:700px;"/>
				<input type=button class="btnopt"
					value="<bean:message bundle="sys-notify" key="sysNotifyCategory.makeTodoUrl"/>"
					onclick="makePCTodoUrl();">
				<input type=button class="btnopt"
					value="<bean:message bundle="sys-notify" key="sysNotifyCategory.makeToviewUrl"/>"
					onclick="makePCToviewUrl();">
				<input type=button class="btnopt"
					value="<bean:message bundle="sys-notify" key="sysNotifyCategory.copyUrlToClipboard"/>"
					onclick="copyPCUrl();">
			</td>
	</tr>
			<%-- 创建时间 --%>
	<c:if test="${sysNotifyCategoryForm.method_GET=='edit'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-notify" key="sysNotifyCategory.fdCreatorName"/>
			</td><td>
				<html:text property="fdCreatorName" readonly="true" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-notify" key="sysNotifyCategory.docCreateTime"/>
			</td><td>
				<html:text property="docCreateTime" readonly="true" />
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-notify" key="sysNotifyCategory.fdModifyName"/>
			</td><td>
				<html:text property="fdModifyName" readonly="true" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-notify" key="sysNotifyCategory.fdModifyTime"/>
			</td><td>
				<html:text property="fdModifyTime" readonly="true" />
			</td>
		</tr>
	</c:if>
</table>
</center>
<html:hidden property="method_GET"/>
<script>
    function makeMobileTodoUrl(){
    	var value = $('input:radio[name="fdCateType"]:checked').val();
    	var url="";
    	if(value=='1'){
    		var appName=$('input[name="fdName"]').val();
			 url="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=1&dataType=todo&mobile=true&fdAppName="+encodeURIComponent(appName);
		}else{
			 url="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=1&dataType=todo&mobile=true&cateFdId=${sysNotifyCategoryForm.fdId}";
		}
    	$("#mobileUrl").val(url);
    }
    function makeMobileToviewUrl(){
    	var value = $('input:radio[name="fdCateType"]:checked').val();
    	var url="";
    	if(value=='1'){
    		var appName=$('input[name="fdName"]').val();
			 url="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=1&dataType=toview&mobile=true&fdAppName="+encodeURIComponent(appName);
		}else{
			 url="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=1&dataType=toview&mobile=true&cateFdId=${sysNotifyCategoryForm.fdId}";
		}
    	$("#mobileUrl").val(url);
    }
    function makePCTodoUrl(){
    	var value = $('input:radio[name="fdCateType"]:checked').val();
    	var url="";
    	if(value=='1'){
    		var appName=$('input[name="fdName"]').val();
			 url="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=1&dataType=todo&pc=true&showDocCreator=true&showFdCreateTime=true&isShowBtLable=0&target=_blank&fdAppName="+encodeURIComponent(appName);
		}else{
			 url="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=1&dataType=todo&pc=true&showDocCreator=true&showFdCreateTime=true&isShowBtLable=0&target=_blank&cateFdId=${sysNotifyCategoryForm.fdId}";
		}
    	$("#PCUrl").val(url);
    }
    function makePCToviewUrl(){
    	var value = $('input:radio[name="fdCateType"]:checked').val();
    	var url="";
    	if(value=='1'){
    		var appName=$('input[name="fdName"]').val();
			 url="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=1&dataType=toview&pc=true&showDocCreator=true&showFdCreateTime=true&isShowBtLable=0&target=_blank&fdAppName="+encodeURIComponent(appName);
		}else{
			 url="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=listDatasByCate&home=1&dataType=toview&pc=true&showDocCreator=true&showFdCreateTime=true&isShowBtLable=0&target=_blank&cateFdId=${sysNotifyCategoryForm.fdId}";
		}
    	$("#PCUrl").val(url);
    }
    function copyMobileUrl(){
    	 var Url=document.getElementById("mobileUrl");  
    	  Url.select(); // 选择对象  
    	  document.execCommand("Copy"); // 执行浏览器复制命令  
    	  alert('<bean:message bundle="sys-notify" key="sysNotifyCategory.copySuccess.tip"/>');  
    }
    function copyPCUrl(){
   	 var Url=document.getElementById("PCUrl");  
   	  Url.select(); // 选择对象  
   	  document.execCommand("Copy"); // 执行浏览器复制命令  
   	  alert('复制成功');  
   }
    function OpenPage(){
    	var url=Com_Parameter.ContextPath+"sys/mobile/sys_mobile_module_category_config/sysMobileModuleCategoryConfig.do?fdModelName=com.landray.kmss.sys.notify.model.SysNotifyTodo&method=edit";
    	window.open(url, '_blank');  
    }
	$KMSSValidation();
	function onFdCateTypeChange(value){
		if(value=='1'){
			$('.module').hide();
			$('.appNameTip').show();
		}else{
			$('.module').show();
			$('.appNameTip').hide();
		}
		$("#mobileUrl").val("");
		$("#PCUrl").val("");
	}
	$(function(){
		var value = $('input:radio[name="fdCateType"]:checked').val();
		onFdCateTypeChange(value);
	});
</script>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
