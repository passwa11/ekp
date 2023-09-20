<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="java.util.Set" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%
	String tmpModelName = request.getParameter("tmpModelName");
	Set tmpSet =  SysDataDict.getInstance().getModel(tmpModelName).getPropertyMap().keySet();
	String modelName = request.getParameter("mainModelName");
	Set mSet =  SysDataDict.getInstance().getModel(modelName).getPropertyMap().keySet();
	String[] cs = new String[]{"authReaders","authEditors","authTmpReaders","authTmpEditors","authTmpAttCopys","authTmpAttDownloads","authTmpAttPrints"};
	String[] ms = new String[]{"authReaders","authEditors","authAttCopys","authAttDownloads","authAttPrints"};
	int cc = 0,cm=0;
	for(int i=0;i<cs.length;i++){
		if(tmpSet.contains(cs[i])){
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

<kmss:windowTitle subjectKey="sys-right:right.change.title.tmp"
	 moduleKey="${param.moduleMessageKey}" />

<script>
var kvc = ["authTemplateReader","authTemplateEditor","authTmpReader","authTmpEditor","authTmpAttCopy","authTmpAttDownload","authTmpAttPrint"];
var kvd = ["authReader","authEditor","authAttCopy","authAttDownload","authAttPrint"];
var tsc =["<bean:message  bundle="sys-right" key="right.change.authCateReaderIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authCateEditorIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authTmpReaderIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authTmpEditorIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authTmpAttCopyIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authTmpAttDownloadIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authTmpAttPrintIds"/>"
		]
var tsd =["<bean:message  bundle="sys-right" key="right.change.authReaderIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authEditorIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authAttCopyIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authAttDownloadIds"/>",
		"<bean:message  bundle="sys-right" key="right.change.authAttPrintIds"/>"
		];

function validateCateAuthForm(of){
	clearDocAuth();
	clearCateAuth();
	if(!validateEmpty()){
		return false;
	}
	return true;
}

function clearDocAuth(){
	if(!(document.getElementsByName("thisTemplateDocCheck")[0].checked)){
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
	if(!(document.getElementsByName("thisTemplateCheck")[0].checked)){
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
	if(!(document.getElementsByName("thisTemplateCheck")[0].checked
		|| document.getElementsByName("thisTemplateDocCheck")[0].checked)){
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
	var flag = document.getElementsByName("thisTemplateDocCheck")[0].checked
	for(var i=0;i<kvd.length;i++){
		var zone = document.getElementById(kvd[i]+"Zone");
		if(zone){
			zone.style.display=(flag?"":"none"); 
		}
	}
}

function showTmpZone(){
	var flag = document.getElementsByName("thisTemplateCheck")[0].checked
	for(var i=0;i<kvc.length;i++){
		var zone = document.getElementById(kvc[i]+"Zone");
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

	if(el.value=="1" || el.value=="2"){
		if(document.getElementById("authReaderNoteFlagEmpty")){
			document.getElementById("authReaderNoteFlagEmpty").style.display="none";
		}
	}else{
		if(document.getElementById("authReaderNoteFlagEmpty")){
			document.getElementById("authReaderNoteFlagEmpty").style.display="";
		}
	}

}

window.onload=function(){
	showDocZone();

	var fdIds = window.opener.document.getElementById('fdIds').value;
	document.getElementsByName('fdIds')[0].value = fdIds;
};
</script>
<p class="txttitle"><bean:message bundle="sys-right" key="right.category.change.title.tmp"/><bean:message key="button.edit"/></p>

<html:form action="/sys/right/cChangeTmpRight.do" method="post" onsubmit="return validateCateAuthForm(this);">
<div id="optBarDiv">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.cChangeTmpRightForm, 'tmpRightUpdate','fdIds');">

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
			<input type="checkbox" name="thisTemplateCheck" checked value="true" onclick="showTmpZone();">
			<bean:message  bundle="sys-right" key="right.category.change.thisTmpCheck"/>
			<input type="checkbox" name="thisTemplateDocCheck" value="true" onclick="showDocZone();">
			<bean:message  bundle="sys-right" key="right.category.change.thisTmpDocCheck"/>
		</td>
	</tr>	

	<tr>
		<td class="td_normal_title" colspan="2">
			<bean:message  bundle="sys-right" key="right.category.change.updateOption"/>
		</td>
	</tr>	
   <% if(tmpSet.contains("authEditors")){ %>
	<tr id="authTemplateEditorZone">
		<td class="td_normal_title" rowspan="<%=cc%>" width=10%>
			<bean:message  bundle="sys-right" key="right.category.change.tmp"/>
		</td>
		<td width=90%>
			<input type="checkbox" name="authTemplateEditorCheck" value="true" onclick="showElementInput(this)">
            <bean:message  bundle="sys-right" key="right.change.authCateEditorIds"/>
			<div id="authTemplateEditorCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTemplateEditorIds" propertyName="authTemplateEditorNames" style="width:97%;height:90px;" ></xform:address>
			</div>
		</td>
	</tr>
	<%}%>
	<% if(tmpSet.contains("authReaders")){ %>
	<tr id="authTemplateReaderZone">
		<td width=90%>
			<input type="checkbox" name="authTemplateReaderCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authCateReaderIds"/>
			<div id="authTemplateReaderCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTemplateReaderIds" propertyName="authTemplateReaderNames" style="width:97%;height:90px;" ></xform:address>
			</div>
		</td>
	</tr>
	<%}%>
	<% if(tmpSet.contains("authTmpReaders")){ %>
	<tr id="authTmpReaderZone">
		<td width=90%>
			<input type="checkbox" name="authTmpReaderCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authTmpReaderIds"/><br>
			<span id="authTmpReaderCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpReaderIds" propertyName="authTmpReaderNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</span>
		</td>
	</tr>
	<%}%>
	<% if(tmpSet.contains("authTmpEditors")){ %>
	<tr id="authTmpEditorZone">
		<td width=90%>
			<input type="checkbox" name="authTmpEditorCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authTmpEditorIds"/><br>
			<span id="authTmpEditorCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpEditorIds" propertyName="authTmpEditorNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</span>
		</td>
	</tr>
	<%}%>
	<% if(tmpSet.contains("authTmpAttCopys")){ %>
	<tr id="authTmpAttCopyZone">
		<td width=90%>
			<input type="checkbox" name="authTmpAttCopyCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authTmpAttCopyIds"/>
			<div id="authTmpAttCopyCheckInput" style="display:none">
			
			<div id="authTmpAttCopyCheckNotFlag" style="display:none">
			<input type="checkbox" name="authTmpAttNocopy" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNocopy" bundle="sys-right"/>
			</div>

			<div id="authTmpAttNocopyInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpAttCopyIds" propertyName="authTmpAttCopyNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authTmpAttCopyCheckEmpty" style="display:none">									
			<!-- <bean:message key="right.category.att.authAttCopys.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
			        <!-- （为空则所有内部人员可拷贝,若外部人员创建则其所在组织的所有人员可拷贝） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttCopys.mul" />
			    <% } else { %>
			        <!-- （为空则所有内部人员可拷贝,若外部人员创建则其所在组织的所有人员可拷贝）-->
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
		<td width=90%>
			<input type="checkbox" name="authTmpAttDownloadCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authTmpAttDownloadIds"/>
			<div id="authTmpAttDownloadCheckInput" style="display:none">
			
			<div id="authTmpAttDownloadCheckNotFlag" style="display:none">
			<input type="checkbox" name="authTmpAttNodownload" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNodownload" bundle="sys-right"/>
			</div>

			<div id="authTmpAttNodownloadInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpAttDownloadIds" propertyName="authTmpAttDownloadNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authTmpAttDownloadCheckEmpty" style="display:none">			
<!-- 			<bean:message key="right.category.att.authAttDownloads.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
			        <!-- （为空则所有内部人员可下载,若外部人员创建则其所在组织的所有人员可下载） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul" />
			    <% } else { %>
			        <!-- （为空则所有内部人员可下载,若外部人员创建则其所在组织的所有人员可下载） -->
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
		<td width=90%>
			<input type="checkbox" name="authTmpAttPrintCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authTmpAttPrintIds"/>
			<div id="authTmpAttPrintCheckInput" style="display:none">
			
			<div id="authTmpAttPrintCheckNotFlag" style="display:none">
			<input type="checkbox" name="authTmpAttNoprint" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNoprint" bundle="sys-right"/>
			</div>

			<div id="authTmpAttNoprintInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authTmpAttPrintIds" propertyName="authTmpAttPrintNames" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE" style="width:97%;height:90px;" ></xform:address>
			</div>
			<div id="authTmpAttPrintCheckEmpty" style="display:none">						
			<!-- <bean:message key="right.category.att.authAttPrints.note" bundle="sys-right"/> -->
			<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
			        <!-- （为空则所有内部人员可打印,若外部人员创建则其所在组织的所有人员可打印）-->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
			    <% } else { %>
			        <!-- （为空则所有内部人员可打印,若外部人员创建则其所在组织的所有人员可打印） -->
			        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
			    <% } %>
			<% } else { %>
			    <!-- （为空则所有人可打印） -->
			    <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.note" />
			<% } %>
			
			<div>
			</div>								
		</td>
	</tr>
	<%}%>
	<% if(mSet.contains("authReaders")){ %>
	<tr id="authReaderZone">
		<td class="td_normal_title" rowspan="<%=cm%>" width=10%>
			<bean:message  bundle="sys-right" key="right.change.doc"/>
		</td>
		<td width=90%>
			<input type="checkbox" name="authReaderCheck" value="true" onclick="showElementInput(this)">&nbsp;
			<bean:message  bundle="sys-right" key="right.change.authReaderIds"/><br>
			<span id="authReaderCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:97%;height:90px;" ></xform:address>
				
				<html:hidden property="authReaderNoteFlag" value="${HtmlParam.authReaderNoteFlag}"/>
				<c:if test="${empty param.authReaderNoteFlag or param.authReaderNoteFlag=='1'}">
				<div id="authReaderNoteFlagEmpty" style="display:none"><bean:message
				bundle="sys-right" key="right.category.read.authReaders.note" />
				</div>
				</c:if>
				<c:if test="${param.authReaderNoteFlag=='2'}">
				<div id="authReaderNoteFlagEmpty" style="display:none"><bean:message
				bundle="sys-right" key="right.category.read.authReaders.note1" />
				</div>
				</c:if>
			</span>
		</td>
	</tr>
	<%}%>
	<% if(mSet.contains("authEditors")){ %>
	<tr id="authEditorZone">
		<td width=90%>
			<input type="checkbox" name="authEditorCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authEditorIds"/><br>
			<span id="authEditorCheckInput" style="display:none">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" style="width:97%;height:90px;" ></xform:address>
			</span>
		</td>
	</tr>
	<%}%>
	<% if(mSet.contains("authAttCopys")){ %>
	<tr id="authAttCopyZone">
		<td width=90%>
			<input type="checkbox" name="authAttCopyCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authAttCopyIds"/>
			<div id="authAttCopyCheckInput" style="display:none">
			
			<div id="authAttCopyCheckNotFlag" style="display:none">
			<input type="checkbox" name="authAttNocopy" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNocopy" bundle="sys-right"/>
			</div>

			<div id="authAttNocopyInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authAttCopyIds" propertyName="authAttCopyNames" style="width:97%;height:90px;" ></xform:address>
			<div>
			<div id="authAttCopyCheckEmpty" style="display:none">												
<!-- 			<bean:message key="right.category.att.authAttCopys.note" bundle="sys-right"/> -->
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
			<div>
			</div>	
		</td>
	</tr>	
	<%}%>
	<% if(mSet.contains("authAttDownloads")){ %>
	<tr id="authAttDownloadZone">
		<td width=90%>
			<input type="checkbox" name="authAttDownloadCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authAttDownloadIds"/>
			<div id="authAttDownloadCheckInput" style="display:none">
			
			<div id="authAttDownloadCheckNotFlag" style="display:none">
			<input type="checkbox" name="authAttNodownload" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNodownload" bundle="sys-right"/>
			</div>

			<div id="authAttNodownloadInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authAttDownloadIds" propertyName="authAttDownloadNames" style="width:97%;height:90px;" ></xform:address>
			<div>
			<div id="authAttDownloadCheckEmpty" style="display:none">									
<!-- 			<bean:message key="right.category.att.authAttDownloads.note" bundle="sys-right"/> -->
				<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可下载） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul" />
				    <% } else { %>
				        <!-- （为空则所有内部人员可下载） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.mul" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可下载） -->
				    <bean:message  bundle="sys-right" key="right.category.att.authAttDownloads.all" />
				<% } %>
			<div>
			</div>		
		</td>
	</tr>
	<%}%>
	<% if(mSet.contains("authAttPrints")){ %>
	<tr id="authAttPrintZone">
		<td width=90%>
			<input type="checkbox" name="authAttPrintCheck" value="true" onclick="showElementInput(this)">
			<bean:message  bundle="sys-right" key="right.category.change.authAttPrintIds"/>
			<div id="authAttPrintCheckInput" style="display:none">
			
			<div id="authAttPrintCheckNotFlag" style="display:none">
			<input type="checkbox" name="authAttNoprint" value="true" onclick="swapNotFlag(this)">
			<bean:message key="right.category.att.authAttNoprint" bundle="sys-right"/>
			</div>

			<div id="authAttNoprintInput">
				<xform:address showStatus="edit" textarea="true" mulSelect="true" propertyId="authAttPrintIds" propertyName="authAttPrintNames" style="width:97%;height:90px;" ></xform:address>
			<div>
			<div id="authAttPrintCheckEmpty" style="display:none">												
<!-- 			<bean:message key="right.category.att.authAttPrints.note" bundle="sys-right"/> -->
                     <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
				    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
				        <!-- （为空则本组织人员可使用） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
				    <% } else { %>
				        <!-- （为空则所有内部人员可使用） -->
				        <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.mul" />
				    <% } %>
				<% } else { %>
				    <!-- （为空则所有人可使用） -->
				    <bean:message  bundle="sys-right" key="right.category.att.authAttPrints.note" />
				<% } %>
			<div>
			</div>	
		</td>
	</tr>
	<%}%>
</table>
</center>
<input type="hidden" name="fdIds"/>
<input type="hidden" name="templateModelName" value="${HtmlParam.tmpModelName}"/>
<input type="hidden" name="modelName" value="${HtmlParam.mainModelName}"/>
<input type="hidden" name="templateName" value="${HtmlParam.templateName}"/>
<html:hidden property="method_GET"/>
</html:form>
<html:javascript formName="cChangeTmpRightForm" 
                 cdata="false" 
                 dynamicJavascript="true" 
                 staticJavascript="false" />
<%@ include file="/resource/jsp/edit_down.jsp"%>

