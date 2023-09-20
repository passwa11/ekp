<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ page import="java.util.Set" %>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.right.plugin.PluginUtil" %>

<%!
	private String getLabel(String modelName, String prop, String def) {
		try {
			String label = PluginUtil.getDisplayLabel(modelName, prop);
			if (StringUtil.isNull(label)) {
				label = ResourceUtil.getString(def);
			}
			return label;
		} catch (Exception e) {
			return ResourceUtil.getString(def);
		}
	}
%>
<%
	String modelName = request.getParameter("modelName");
	Set mSet =  SysDataDict.getInstance().getModel(modelName).getPropertyMap().keySet();
	String[] ms = new String[]{"authReaders","authEditors","authAttCopys","authAttDownloads","authAttPrints"};
	// 页面显示的属性名称先取业务模块的数据字典，如果没有找到数据字典属性，再取权限机制固定的名称
	JSONArray tsd = new JSONArray();
	tsd.add(getLabel(modelName, "authReaders", "sys-right:right.change.authReaderIds")); // 可阅读者
	tsd.add(getLabel(modelName, "authEditors", "sys-right:right.change.authEditorIds")); // 可编辑者
	tsd.add(getLabel(modelName, "authAttCopys", "sys-right:right.change.authAttCopyIds")); // 附件可拷贝者
	tsd.add(getLabel(modelName, "authAttDownloads", "sys-right:right.change.authAttDownloadIds")); // 附件可下载者
	tsd.add(getLabel(modelName, "authAttPrints", "sys-right:right.change.authAttPrintIds")); // 附件可打印者
	
	int cm=0;
	for(int i=0;i<ms.length;i++){
		if(mSet.contains(ms[i])){
			cm++;
		}
	}
%>
	
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
</script>

<script>
var kvd = ["authReader","authEditor","authAttCopy","authAttDownload","authAttPrint"];
var tsd = eval('<%=tsd%>');

function validateDocAuthForm(of){
	if(!validateEmpty()){
		return false;
	}
	return true;
}

function validateEmpty(){
	var oprType = document.getElementsByName("oprType");
	var oprValue = getOprValue();
	if(oprValue=="1" || oprValue=="2"){
		if(!checkProperty("<bean:message  bundle="sys-right" key="right.change.doc"/>",kvd,tsd)){
			return false;
		}
	}
	return true;
}

function getOprValue(){
	var oprType = document.getElementsByName("oprType");
	var oprValue = "";
	for(var i=0;i<oprType.length;i++){
		if(oprType[i].checked){
			oprValue = oprType[i].value;
		}
	}
	return oprValue;
}

function checkProperty(zt,pn,pt){
	for(var i=0;i<pn.length;i++){
		var ids = document.getElementsByName(pn[i]+"Ids")[0];
		var chk = document.getElementsByName(pn[i]+"Check")[0];
		if(ids && chk.checked){
			if(ids.value==""){
				var re = /\{0\}/gi;
				var msg ="<bean:message key="errors.required"/>";
				msg = msg.replace(re, pt[i]);
				alert(zt+" "+msg);
				return false;
			}
		}
	}
	return true;
}

function showElementInput(el){
	document.getElementById(el.name+"Input").style.display=el.checked?"":"none";
	var oprValue = getOprValue();
	if(document.getElementById(el.name+"NotFlag")){
		document.getElementById(el.name+"NotFlag").style.display=(oprValue=="3")?"":"none";
	}
	if(document.getElementById(el.name+"Empty")){
		document.getElementById(el.name+"Empty").style.display=(oprValue=="3")?"":"none";
	}
}

function swapNotFlag(el){
	document.getElementById(el.name+"Input").style.display=el.checked?"none":"";
}

var _tmp =["authAttDownloadCheck","authAttPrintCheck","authAttCopyCheck"];
var _tmpFlag =["authAttNodownload","authAttNoprint","authAttNocopy"];
function oprOnclickFunc(el){
	for(var i=0;i<_tmp.length;i++){
		if(el.value=="1"||el.value=="2"){
			document.getElementById(_tmp[i]+"NotFlag").style.display="none";
			document.getElementById(_tmp[i]+"Empty").style.display="none";
			document.getElementsByName(_tmpFlag[i])[0].checked=false;
			document.getElementById(_tmpFlag[i]+"Input").style.display="";
		}else{
			document.getElementById(_tmp[i]+"NotFlag").style.display="";
			document.getElementById(_tmp[i]+"Empty").style.display="";
			if(document.getElementsByName(_tmpFlag[i])[0].checked){
				document.getElementById(_tmpFlag[i]+"Input").style.display="none";
			}else{
				document.getElementById(_tmpFlag[i]+"Input").style.display="";
			}
		}

	}
	if(el.value=="1"||el.value=="2"){
		if(document.getElementById("authReaderNoteFlagEmpty")){
			document.getElementById("authReaderNoteFlagEmpty").style.display="none";
		}
	}else{
		if(document.getElementById("authReaderNoteFlagEmpty")){
			document.getElementById("authReaderNoteFlagEmpty").style.display="";
		}
	}
}

window.onload = function(){
	setTimeout(function(){
		var values="";
		var __win;
		if(window.opener){
			__win = window.opener;
		}else if($dialog && $dialog.config.opener){
			__win = $dialog.config.opener;
		}else if(window.parent){
			__win = window.parent;
		}
		if(!__win){
			values = '${JsParam.fdIds}';
		} else {
			var	select = __win.document.getElementsByName("List_Selected");
			for(var i=0;i<select.length;i++) {
				if(select[i].checked){
					values+=select[i].value;
					values+=",";
				}
			}
		}
		document.getElementsByName("fdIds")[0].value=values; 
	},1);
};
</script>

<c:if test="${_dingxform=='true'}">
	<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/third/ding/third_ding_xform/resource/css/ding.css?s_cache=${LUI_Cache}" />
</c:if>
<kmss:windowTitle subjectKey="sys-right:right.change.title.doc"
	 moduleKey="${param.moduleMessageKey}" />

<p class="txttitle"><bean:message bundle="sys-right" key="right.change.title.doc"/><bean:message key="button.edit"/></p>

<html:form action="/sys/right/cChangeDocRight.do" method="post" onsubmit="return validateDocAuthForm(this);">
<div id="optBarDiv">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.cChangeDocRightForm, 'docRightUpdate','fdIds');">

	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=10%>
			<bean:message  bundle="sys-right" key="right.change.opr"/>
		</td>
		<td width=90%>
			<sunbor:enums
				property="oprType"
				enumsType="cchange_right_opr"
				elementType="radio" htmlElementProperties="onclick='oprOnclickFunc(this);'"/>
		</td>
	</tr>	

	<tr>
		<td class="td_normal_title" colspan="2">
			<bean:message  bundle="sys-right" key="right.change.updateOption"/>
		</td>
	</tr>	

	<tr id="authReaderZone">
		<td class="td_normal_title" rowspan="<%=cm%>" width=10%>
			<bean:message  bundle="sys-right" key="right.change.doc"/>
		</td>
		<!-- 可阅读者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authReaderCheck" value="true" onclick="showElementInput(this)">
				<%=tsd.get(0)%>
			</label>
			<div id="authReaderCheckInput" style="display:none">
			<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:90%;height:90px;" showStatus="edit"></xform:address>
				<html:hidden property="authReaderNoteFlag" value="${HtmlParam.authReaderNoteFlag}"/>
				<c:if test="${empty param.authReaderNoteFlag or param.authReaderNoteFlag=='1'}">
				<div id="authReaderNoteFlagEmpty" style="display:none"><bean:message
				bundle="sys-right" key="right.read.authReaders.note" />
				</div>
				</c:if>
				<c:if test="${param.authReaderNoteFlag=='2'}">
				<div id="authReaderNoteFlagEmpty" style="display:none">
				<bean:message
				bundle="sys-right" key="right.read.authReaders.note1" />
				</div>
				</c:if>
			</div>
		</td>
	</tr>
	<% if(mSet.contains("authEditors")){ %>
	<tr id="authEditorZone">
	    <!-- 可编辑者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authEditorCheck" value="true" onclick="showElementInput(this)">
				<%=tsd.get(1)%>
			</label>
			<div id="authEditorCheckInput" style="display:none">
			<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" style="width:90%;height:90px;" showStatus="edit"></xform:address>
			</div>
		</td>
	</tr>
	<%}%>
	<% if(mSet.contains("authAttCopys")){ %>
	<tr id="authAttCopyZone">
	    <!-- 附件可拷贝者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authAttCopyCheck" value="true" onclick="showElementInput(this)">
				<%=tsd.get(2)%>
			</label>
			<div id="authAttCopyCheckInput" style="display:none">
			
			<div id="authAttCopyCheckNotFlag" style="display:none">
			<input type="checkbox" name="authAttNocopy" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.att.authAttNocopy" bundle="sys-right"/>
			</div>

			<div id="authAttNocopyInput">
            <xform:address textarea="true" mulSelect="true" propertyId="authAttCopyIds" propertyName="authAttCopyNames" style="width:90%;height:90px;" showStatus="edit"></xform:address>
			</div>
			<div id="authAttCopyCheckEmpty" style="display:none">															
			<!-- <bean:message key="right.att.authAttCopys.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可拷贝） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.mul"  />
				    <% } else { %>
				        <!-- （为空则所有内部人员可拷贝） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.mul" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可拷贝） -->
				    <bean:message  bundle="sys-right" key="right.att.authAttCopys.nonOrganizationNote" />
				<% } %>
			</div>
			</div>
		</td>

	</tr>	
	<%}%>
	<% if(mSet.contains("authAttDownloads")){ %>
	<tr id="authAttDownloadZone">
	    <!-- 附件可下载者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authAttDownloadCheck" value="true" onclick="showElementInput(this)">
				<%=tsd.get(3)%>
			</label>
			<div id="authAttDownloadCheckInput" style="display:none">
			
			<div id="authAttDownloadCheckNotFlag" style="display:none">
			<input type="checkbox" name="authAttNodownload" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.att.authAttNodownload" bundle="sys-right"/>
			</div>

			<div id="authAttNodownloadInput">
			<xform:address textarea="true" mulSelect="true" propertyId="authAttDownloadIds" propertyName="authAttDownloadNames" style="width:90%;height:90px;" showStatus="edit"></xform:address>
			</div>
			<div id="authAttDownloadCheckEmpty" style="display:none">												
			<!-- <bean:message key="right.att.authAttDownloads.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
			        <!-- （为空则本组织人员可下载） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul"/>
			    <% } else { %>
			        <!-- （为空则所有内部人员可下载） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul" />
			    <% } %>
			<% } else { %>
			    <!-- （为空则所有人可下载） -->
			    <bean:message  bundle="sys-right" key="right.att.authAttDownloads.nonOrganizationNote" />
			<% } %>		
			</div>
			</div>		
		</td>
	</tr>
	<%}%>
	<% if(mSet.contains("authAttPrints")){ %>
	<tr id="authAttPrintZone">
	    <!-- 附件可打印者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authAttPrintCheck" value="true" onclick="showElementInput(this)">
				<%=tsd.get(4)%>
			</label>
			<div id="authAttPrintCheckInput" style="display:none">
			
			<div id="authAttPrintCheckNotFlag" style="display:none">
			<input type="checkbox" name="authAttNoprint" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.att.authAttNoprint" bundle="sys-right"/>
			</div>

			<div id="authAttNoprintInput">
			<xform:address textarea="true" mulSelect="true" propertyId="authAttPrintIds" propertyName="authAttPrintNames" style="width:90%;height:90px;" showStatus="edit"></xform:address>
			</div>
			<div id="authAttPrintCheckEmpty" style="display:none">															
<!-- 			<bean:message key="right.att.authAttPrints.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
			        <!-- （为空则本组织人员可打印） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul"  />
			    <% } else { %>
			        <!-- （为空则所有内部人员可打印） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
			    <% } %>
			<% } else { %>
			    <!-- （为空则所有人可打印） -->
			    <bean:message  bundle="sys-right" key="right.att.authAttPrints.nonOrganizationNote" />
			<% } %>
			</div>
			</div>	
		</td>
	</tr>
	<%}%>
	
</table>
</center>
<html:hidden property="fdIds" value="${HtmlParam.fdIds}"/>
<html:hidden property="modelName" value="${HtmlParam.modelName}"/>
<html:hidden property="method_GET"/>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>