<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
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
	String tmpModelName = request.getParameter("tmpModelName");
	Set tmpSet =  SysDataDict.getInstance().getModel(tmpModelName).getPropertyMap().keySet();
	String cateName = "com.landray.kmss.sys.category.model.SysCategoryMain";
	Set cateSet =  SysDataDict.getInstance().getModel(cateName).getPropertyMap().keySet();
	String modelName = request.getParameter("mainModelName");
	Set mSet =  SysDataDict.getInstance().getModel(modelName).getPropertyMap().keySet();
	String[] ts = new String[]{"authReaders","authEditors","authTmpReaders","authTmpEditors","authTmpAttCopys","authTmpAttDownloads","authTmpAttPrints"};
	String[] cs = new String[]{"authReaders","authEditors"};
	String[] ms = new String[]{"authReaders","authEditors","authAttCopys","authAttDownloads","authAttPrints"};
	// 页面显示的属性名称先取业务模块的数据字典，如果没有找到数据字典属性，再取权限机制固定的名称
	JSONArray tsc = new JSONArray();
	tsc.add(getLabel(tmpModelName, "authReaders", "sys-right:right.change.authCateReaderIds")); // 可使用者
	tsc.add(getLabel(tmpModelName, "authEditors", "sys-right:right.change.authCateEditorIds")); // 可维护者
	tsc.add(getLabel(tmpModelName, "authTmpReaders", "sys-right:right.change.authTmpReaderIds")); // 默认可阅读者
	tsc.add(getLabel(tmpModelName, "authTmpEditors", "sys-right:right.change.authTmpEditorIds")); // 默认可编辑者
	tsc.add(getLabel(tmpModelName, "authTmpAttCopys", "sys-right:right.change.authTmpAttCopyIds")); // 默认附件可拷贝者
	tsc.add(getLabel(tmpModelName, "authTmpAttDownloads", "sys-right:right.change.authTmpAttDownloadIds")); // 默认附件可下载者
	tsc.add(getLabel(tmpModelName, "authTmpAttPrints", "sys-right:right.change.authTmpAttPrintIds")); // 默认附件可打印者
	JSONArray tsd = new JSONArray();
	tsd.add(getLabel(modelName, "authReaders", "sys-right:right.change.authReaderIds")); // 可阅读者
	tsd.add(getLabel(modelName, "authEditors", "sys-right:right.change.authEditorIds")); // 可编辑者
	tsd.add(getLabel(modelName, "authAttCopys", "sys-right:right.change.authAttCopyIds")); // 附件可拷贝者
	tsd.add(getLabel(modelName, "authAttDownloads", "sys-right:right.change.authAttDownloadIds")); // 附件可下载者
	tsd.add(getLabel(modelName, "authAttPrints", "sys-right:right.change.authAttPrintIds")); // 附件可打印者
	
	int ct = 0,cc = 0, cm = 0;
	for(int i=0;i<ts.length;i++){
		if(tmpSet.contains(ts[i])){
			ct++;
		}
	}
	for(int i=0;i<cs.length;i++){
		if(cateSet.contains(cs[i])){
			cc++;
		}
	}
	for(int i=0;i<ms.length;i++){
		if(mSet.contains(ms[i])){
			cm++;
		}
	}
%>
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
</script>

<kmss:windowTitle subjectKey="sys-right:right.change.title.cate"
	 moduleKey="${param.moduleMessageKey}" />

<script>
var kvc = ["authTemplateReader","authTemplateEditor","authTmpReader","authTmpEditor","authTmpAttCopy","authTmpAttDownload","authTmpAttPrint"];
var kcc = ["authCateReader","authCateEditor"];
var kvd = ["authReader","authEditor","authAttCopy","authAttDownload","authAttPrint"];
var tsc = eval('<%=tsc%>');
var tsd = eval('<%=tsd%>');
var tcd =["<bean:message  bundle="sys-right" key="right.category.change.authCateReaderIds"/>",
  		"<bean:message  bundle="sys-right" key="right.category.change.authCateEditorIds"/>"
  		];

function validateCateAuthForm(of){
	clearDocAuth();
	clearCateAuth();
	clearTmpAuth();
	if(!validateEmpty()){
		return false;
	}
	return true;
}
 
function clearDocAuth(){
	if(!(document.getElementsByName("thisCateChildDocCheck")[0].checked
		|| document.getElementsByName("thisCateDocCheck")[0].checked)){
		for(var i=0;i<kvd.length;i++){
			if(document.getElementsByName(kvd[i]+"Ids").length>0){
				document.getElementsByName(kvd[i]+"Ids")[0].value="";
				document.getElementsByName(kvd[i]+"Names")[0].value="";
				document.getElementsByName(kvd[i]+"Check")[0].checked=false;
			}
		}  
	}
}
 
function clearCateAuth(){
	if(!(document.getElementsByName("thisCateCheck")[0].checked
		|| document.getElementsByName("thisCateChildAndTmpCheck")[0].checked)){ 
		for(var i=0;i<kcc.length;i++){
			if(document.getElementsByName(kcc[i]+"Ids").length>0){
				document.getElementsByName(kcc[i]+"Ids")[0].value="";
				document.getElementsByName(kcc[i]+"Names")[0].value="";
				document.getElementsByName(kcc[i]+"Check")[0].checked=false;
			}
		}
	}
}

function clearTmpAuth(){  
	if(!(document.getElementsByName("thisCateChildAndTmpCheck")[0].checked)){
		for(var i=0;i<kvc.length;i++){
			if(document.getElementsByName(kvc[i]+"Ids").length>0){
				document.getElementsByName(kvc[i]+"Ids")[0].value="";
				document.getElementsByName(kvc[i]+"Names")[0].value="";
				document.getElementsByName(kvc[i]+"Check")[0].checked=false;
			}
		}
	}
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

function validateEmpty(){
	var oprValue = getOprValue();
	if(!(document.getElementsByName("thisCateCheck")[0].checked
		|| document.getElementsByName("thisCateChildAndTmpCheck")[0].checked
		|| document.getElementsByName("thisCateDocCheck")[0].checked
		||document.getElementsByName("thisCateChildDocCheck")[0].checked)){
		alert("<bean:message  bundle="sys-right" key="right.category.change.applyto.alert"/>");
		return false;
	}

	if(oprValue=="1" || oprValue=="2"){
		if(!checkProperty("<bean:message  bundle="sys-right" key="right.category.change.tmp"/>",kvc,tsc)){
			return false;
		}
		if(!checkProperty("<bean:message  bundle="sys-right" key="right.category.change.doc"/>",kvd,tsd)){
			return false;
		}
		if(!checkProperty("<bean:message  bundle="sys-right" key="right.category.change.cate"/>",kcc,tcd)){
			return false;
		}
	}
	return true;
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

function showDocZone(){
	var flag = document.getElementsByName("thisCateDocCheck")[0].checked || document.getElementsByName("thisCateChildDocCheck")[0].checked
	for(var i=0;i<kvd.length;i++){
		var zone = document.getElementById(kvd[i]+"Zone");
		if(zone){
			zone.style.display=(flag?"":"none"); 
		}
	}
}

function showTmpZone(){
	var flag = document.getElementsByName("thisCateChildAndTmpCheck")[0].checked
	for(var i=0;i<kvc.length;i++){
		var zone = document.getElementById(kvc[i]+"Zone");
		if(zone){
			zone.style.display=(flag?"":"none");
		}
	}
}

function showCateZone(){
	var flag = document.getElementsByName("thisCateCheck")[0].checked || document.getElementsByName("thisCateChildAndTmpCheck")[0].checked
	for(var i=0;i<kcc.length;i++){
		var zone = document.getElementById(kcc[i]+"Zone");
		if(zone){
			zone.style.display=(flag?"":"none");
		}
	}
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

var _tmp =["authTmpAttDownloadCheck","authTmpAttPrintCheck","authTmpAttCopyCheck","authAttDownloadCheck","authAttPrintCheck","authAttCopyCheck"];
var _tmpFlag =["authTmpAttNodownload","authTmpAttNoprint","authTmpAttNocopy","authAttNodownload","authAttNoprint","authAttNocopy"];
function oprOnclickFunc(el){
	for(var i=0;i<_tmp.length;i++){
		if(el.value=="1" || el.value=="2"){
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
	showTmpZone();
	showDocZone();
	var fdIds = window.opener.document.getElementById('fdIds').value;
	document.getElementById('fdIds').value = fdIds;
};
</script>
<p class="txttitle"><bean:message bundle="sys-right" key="right.category.change.title.cate"/><bean:message key="button.edit"/></p>

<html:form action="/sys/right/cChangeCateRight.do" method="post" onsubmit="return validateCateAuthForm(this);">
<div id="optBarDiv">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.cChangeCateRightForm, 'cateRightUpdate','fdIds');">

	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=10%>
			<bean:message  bundle="sys-right" key="right.category.change.opr"/>
		</td>
		<td width=90%>
			<sunbor:enums
				property="oprType"
				enumsType="cchange_right_opr"
				elementType="radio" htmlElementProperties="onclick='oprOnclickFunc(this);'"/>
		</td>
	</tr>	

	<tr>
		<td class="td_normal_title" width=10%>
			<bean:message  bundle="sys-right" key="right.category.change.applyto"/>
		</td>
		<td width=90%> 
			<label>
				<input type="checkbox" name="thisCateCheck" checked value="true" onclick="showCateZone();">
				<bean:message  bundle="sys-right" key="right.change.thisCateCheck"/>
			</label>
			<label>
				<input type="checkbox" name="thisCateChildAndTmpCheck" value="true" onclick="showCateZone();showTmpZone();">
				<bean:message  bundle="sys-right" key="right.category.change.thisCateChildAndTmpCheck"/>
			</label>
			<label>
				<input type="checkbox" name="thisCateDocCheck"  value="true" onclick="showDocZone();">
				<bean:message  bundle="sys-right" key="right.category.change.thisCateDocCheck"/>
			</label>
			<label>
				<input type="checkbox" name="thisCateChildDocCheck"  value="true" onclick="showDocZone();">
				<bean:message  bundle="sys-right" key="right.category.change.thisCateChildDocCheck"/>
			</label>
		</td>
	</tr>	

	<tr>
		<td class="td_normal_title" colspan="2">
			<bean:message  bundle="sys-right" key="right.category.change.updateOption"/>
		</td>
	</tr>	
	
	<tr id="authCateEditorZone">
		<td class="td_normal_title" rowspan="<%=cc%>" width=10%>
			<bean:message  bundle="sys-right" key="right.category.change.cate"/>
		</td>
		<td width=90%>
			<label>
				<input type="checkbox" name="authCateEditorCheck" value="true" onclick="showElementInput(this)">
				<bean:message  bundle="sys-right" key="right.change.authCateEditorIds"/>
			</label>
			<span id="authCateEditorCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authCateEditorIds" propertyName="authCateEditorNames" style="width:97%;height:90px;" ></xform:address>
			</span>
		</td>
	</tr>
	<% if(cateSet.contains("authReaders")){ %>
	<tr id="authCateReaderZone">
		<td width=90%>
			<label>
				<input type="checkbox" name="authCateReaderCheck" value="true" onclick="showElementInput(this)">
				<bean:message  bundle="sys-right" key="right.category.change.authCateReaderIds"/>
			</label>
			<div id="authCateReaderCheckInput" style="display:none">
			
			<div id="authNotReaderFlagInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authCateReaderIds" propertyName="authCateReaderNames" style="width:97%;height:90px;" ></xform:address>
			<div>
			<div id="authCateReaderCheckEmpty" style="display:none">
			<bean:message bundle="sys-right" key="right.category.allUse1" />
			<div>

			</div>
		</td>
	</tr>
	<%}%>
	<tr id="authTemplateEditorZone">
		<td class="td_normal_title" rowspan="<%=ct%>" width=10%>
			<bean:message  bundle="sys-right" key="right.category.change.tmp"/>
		</td>
		<!-- 可维护者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authTemplateEditorCheck"  value="true" onclick="showElementInput(this)">
				<%=tsc.get(1)%>
			</label>
			<span id="authTemplateEditorCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTemplateEditorIds" propertyName="authTemplateEditorNames" style="width:97%;height:90px;" ></xform:address>
			</span>
		</td>
	</tr>
	<% if(tmpSet.contains("authReaders")){ %>
	<tr id="authTemplateReaderZone">
	    <!-- 可使用者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authTemplateReaderCheck" value="true" onclick="showElementInput(this)">
				<%=tsc.get(0)%>
			</label>
			<div id="authTemplateReaderCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTemplateReaderIds" propertyName="authTemplateReaderNames" style="width:97%;height:90px;" ></xform:address>
			</div>
		</td>
	</tr>
	<%}%>
	<% if(tmpSet.contains("authTmpReaders")){ %>
	<tr id="authTmpReaderZone">
	    <!-- 默认可阅读者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authTmpReaderCheck" value="true" onclick="showElementInput(this)">
				<%=tsc.get(2)%>
			</label>
			<span id="authTmpReaderCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpReaderIds" propertyName="authTmpReaderNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</span>
		</td>
	</tr>
	<%}%>
	<% if(tmpSet.contains("authTmpEditors")){ %>
	<tr id="authTmpEditorZone">
	    <!-- 默认可编辑者 -->
		<td width=90% colspan="2">
			<label>
				<input type="checkbox" name="authTmpEditorCheck"  value="true" onclick="showElementInput(this)">
				<%=tsc.get(3)%>
			</label>
			<span id="authTmpEditorCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpEditorIds" propertyName="authTmpEditorNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</span>
		</td>
	</tr>
	<%}%>
	<% if(tmpSet.contains("authTmpAttCopys")){ %>
	<tr id="authTmpAttCopyZone">
	    <!-- 默认附件可拷贝者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authTmpAttCopyCheck"  value="true" onclick="showElementInput(this)">
				<%=tsc.get(4)%>
			</label>
			<div id="authTmpAttCopyCheckInput" style="display:none">
			
			<div id="authTmpAttCopyCheckNotFlag" style="display:none">
			<input type="checkbox" name="authTmpAttNocopy"  value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNocopy" bundle="sys-right"/>
			</div>

			<div id="authTmpAttNocopyInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpAttCopyIds" propertyName="authTmpAttCopyNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authTmpAttCopyCheckEmpty" style="display:none">		
			<!-- 拷贝 -->							
			<!-- <bean:message key="right.category.att.authAttCopys.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可拷贝） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.mul" />
				    <% } else { %>
				        <!-- （为空则所有内部人员可拷贝） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.mul" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可拷贝） -->
				    <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.all" />
				<% } %>
			</div>
			</div>	
		</td>
	</tr>	
	<%}%>
	<% if(tmpSet.contains("authTmpAttDownloads")){ %>
	<tr id="authTmpAttDownloadZone">
	    <!-- 默认附件可下载者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authTmpAttDownloadCheck"  value="true" onclick="showElementInput(this)">
				<%=tsc.get(5)%>
			</label>
			<div id="authTmpAttDownloadCheckInput" style="display:none">
			
			<div id="authTmpAttDownloadCheckNotFlag" style="display:none">
			<input type="checkbox" name="authTmpAttNodownload"  value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNodownload" bundle="sys-right"/>
			</div>

			<div id="authTmpAttNodownloadInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpAttDownloadIds" propertyName="authTmpAttDownloadNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authTmpAttDownloadCheckEmpty" style="display:none">			
			<!-- <bean:message key="right.category.att.authAttDownloads.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可下载） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul" arg0="${ecoName}" />
				    <% } else { %>
				        <!-- （为空则所有内部人员可下载） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可下载） -->
				    <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.all" />
				<% } %>		
			</div>
			</div>						
		</td>
	</tr>
	<%}%>
	<% if(tmpSet.contains("authTmpAttPrints")){ %>
	<tr id="authTmpAttPrintZone">
	    <!-- 默认附件可打印者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authTmpAttPrintCheck"  value="true" onclick="showElementInput(this)">
				<%=tsc.get(6)%>
			</label>
			<div id="authTmpAttPrintCheckInput" style="display:none">
			
			<div id="authTmpAttPrintCheckNotFlag" style="display:none">
			<input type="checkbox" name="authTmpAttNoprint"  value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNoprint" bundle="sys-right"/>
			</div>

			<div id="authTmpAttNoprintInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpAttPrintIds" propertyName="authTmpAttPrintNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authTmpAttPrintCheckEmpty" style="display:none">						
			<!-- <bean:message key="right.category.att.authAttPrints.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
			        <!-- （为空则本组织人员可打印） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
			    <% } else { %>
			        <!-- （为空则所有内部人员可打印） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
			    <% } %>
			<% } else { %>
			    <!-- （为空则所有人可打印） -->
			    <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.note" />
			<% } %>
			</div>
			</div>								
		</td>
	</tr>
	<%}%>
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
			<span id="authReaderCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:97%;height:90px;" ></xform:address>
				
				<html:hidden property="authReaderNoteFlag" value="${HtmlParam.authReaderNoteFlag}"/>
				<c:if test="${empty param.authReaderNoteFlag or param.authReaderNoteFlag=='1'}">
				<div id="authReaderNoteFlagEmpty" style="display:none"><bean:message bundle="sys-right" key="right.category.read.authReaders.note" /></div>
				</c:if>
				<c:if test="${param.authReaderNoteFlag=='2'}">
				<div id="authReaderNoteFlagEmpty" style="display:none"><bean:message bundle="sys-right" key="right.category.read.authReaders.note1" /></div>
				</c:if>
			
			</span>
		</td>
	</tr>
	<% if(mSet.contains("authEditors")){ %>
	<tr id="authEditorZone">
	    <!-- 可编辑者 -->
		<td width=90%>
			<label>
				<input type="checkbox" name="authEditorCheck"  value="true" onclick="showElementInput(this)">
				<%=tsd.get(1)%>
			</label>
			<span id="authEditorCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" style="width:97%;height:90px;" ></xform:address>
			</span>
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
			<bean:message key="right.category.att.authAttNocopy" bundle="sys-right"/>
			</div>

			<div id="authAttNocopyInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authAttCopyIds" propertyName="authAttCopyNames" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authAttCopyCheckEmpty" style="display:none">												
<!-- 			<bean:message key="right.category.att.authAttCopys.note" bundle="sys-right"/> -->
				<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可拷贝） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.mul" arg0="${ecoName}" />
				    <% } else { %>
				        <!-- （为空则所有内部人员可拷贝） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.mul" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可拷贝） -->
				    <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.all" />
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
				<input type="checkbox" name="authAttDownloadCheck"  value="true" onclick="showElementInput(this)">
				<%=tsd.get(3)%>
			</label>
			<div id="authAttDownloadCheckInput" style="display:none">
			
			<div id="authAttDownloadCheckNotFlag" style="display:none">
			<input type="checkbox" name="authAttNodownload"  value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNodownload" bundle="sys-right"/>
			</div>

			<div id="authAttNodownloadInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authAttDownloadIds" propertyName="authAttDownloadNames" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authAttDownloadCheckEmpty" style="display:none">
			<!-- 下载	 -->
			<!-- <bean:message key="right.category.att.authAttDownloads.note" bundle="sys-right"/> -->
				<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可下载） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul" arg0="${ecoName}" />
				    <% } else { %>
				        <!-- （为空则所有内部人员可下载） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可下载） -->
				    <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.all" />
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
				<input type="checkbox" name="authAttPrintCheck"  value="true" onclick="showElementInput(this)">
				<%=tsd.get(4)%>
			</label>
			<div id="authAttPrintCheckInput" style="display:none">
			
			<div id="authAttPrintCheckNotFlag" style="display:none">
			<input type="checkbox" name="authAttNoprint" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNoprint" bundle="sys-right"/>
			</div>

			<div id="authAttNoprintInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authAttPrintIds" propertyName="authAttPrintNames" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authAttPrintCheckEmpty" style="display:none">												
			<!-- <bean:message key="right.category.att.authAttPrints.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
			        <!-- （为空则本组织人员可打印） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
			    <% } else { %>
			        <!-- （为空则所有内部人员可打印） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
			    <% } %>
			<% } else { %>
			    <!-- （为空则所有人可打印） -->
			    <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.note" />
			<% } %>		
			</div>
			</div>	
		</td>
	</tr>
	<%}%>
</table>
</center>
<input type="hidden" name="fdIds" id="fdIds" />
<input type="hidden" name="templateModelName" value="${HtmlParam.tmpModelName}"/>
<input type="hidden" name="modelName" value="${HtmlParam.mainModelName}"/>
<input type="hidden" name="templateName" value="${HtmlParam.templateName}"/>
<input type="hidden" name="categoryName" value="${HtmlParam.categoryName}"/>
<html:hidden property="method_GET"/>
</html:form>
<html:javascript formName="cChangeCateRightForm"   
                 cdata="false" 
                 dynamicJavascript="true" 
                 staticJavascript="false" />
<%@ include file="/resource/jsp/edit_down.jsp"%>

