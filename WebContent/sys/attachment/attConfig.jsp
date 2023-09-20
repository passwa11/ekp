<%@page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil"%>
<%@page import="com.landray.kmss.sys.filestore.location.model.SysFileLocation"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js|jquery.js");
function config_att_jg_pdfurl(){
	var isJGPDFEnabled = document.getElementsByName("value(sys.att.isJGPDFEnabled)")[0];	
	var jgpdfurl = document.getElementsByName("value(sys.att.jg.pdfurl)")[0];
	var jgpdfversion = document.getElementsByName("value(sys.att.jg.pdfversion)")[0];
	jgpdfurl.disabled = !isJGPDFEnabled.checked;
	jgpdfversion.disabled = !isJGPDFEnabled.checked;
	if (jgpdfurl.disabled) {
		KMSSValidation_HideWarnHint(jgpdfurl);
		document.getElementById("JGPDFConfig").style.display = "none";
	}else{
		document.getElementById("JGPDFConfig").style.display = "block";
	}
	if (jgpdfversion.disabled) {
		KMSSValidation_HideWarnHint(jgpdfversion);
	}		
}

function config_att_jg_pdfurl2018(){
	var isJGPDFEnabled = document.getElementsByName("value(sys.att.isJGPDF2018Enabled)")[0];	
	var jgpdfurl = document.getElementsByName("value(sys.att.jg.pdfurl2018)")[0];
	var jgpdfversion = document.getElementsByName("value(sys.att.jg.pdfversion2018)")[0];
	jgpdfurl.disabled = !isJGPDFEnabled.checked;
	jgpdfversion.disabled = !isJGPDFEnabled.checked;
	if (jgpdfurl.disabled) {
		KMSSValidation_HideWarnHint(jgpdfurl);
		document.getElementById("JGPDFConfig2018").style.display = "none";
	}else{
		document.getElementById("JGPDFConfig2018").style.display = "block";
	}
	if (jgpdfversion.disabled) {
		KMSSValidation_HideWarnHint(jgpdfversion);
	}		
}
function config_att_lattice_change() {
	var enable = document.getElementsByName("value(sys.tstudy.enable)")[0].checked;
	if(enable) {
		document.getElementById("sys_tstudy_websocket_ip_div").style.display = "block";
	} else {
		document.getElementById("sys_tstudy_websocket_ip_div").style.display = "none";
	}
}

function config_att_foxit_change() {
	var enable = document.getElementsByName("value(sys.foxit.enable)")[0].checked;
	if(enable) {
		document.getElementById("sys_foxit_license_div").style.display = "block";
	} else {
		document.getElementById("sys_foxit_license_div").style.display = "none";
	}
}
/**
 * 选择控件类型
 */
 function config_att_jg_plugintype() {
	var plugintype = "";
	var plugintypeArray = document.getElementsByName("value(sys.att.jg.plugintype)");
	if (null != plugintypeArray) {
		for(var i = 0; i < plugintypeArray.length; i++) {
			if(plugintypeArray[i].checked) {
				plugintype = plugintypeArray[i].value;
				break;
			}
		}
	}
	var switchVersion=document.getElementsByName("value(sys.att.jg.switchversion)")[0].value;
	if (null != switchVersion && "false" == switchVersion) {
		plugintype = "2009";
	}
	if ("2009" == plugintype) {
		//iWebOffice2009控件
		document.getElementById("JGConfig2003").style.display = "none";
		document.getElementById("JGConfig2009").style.display = "block";
		document.getElementById("JGConfig2015").style.display = "none";
	} else if ("2003" == plugintype){
		//iWebOffice2003控件
		document.getElementById("JGConfig2003").style.display = "block";
		document.getElementById("JGConfig2009").style.display = "none";
		document.getElementById("JGConfig2015").style.display = "none";
	} else {
		//iWebOffice2015控件 
		document.getElementById("JGConfig2003").style.display = "none";
		document.getElementById("JGConfig2009").style.display = "none";
		document.getElementById("JGConfig2015").style.display = "block";
	}
}

 /**
  * 选择PDF控件类型
  */
  function config_att_pdf_plugintype() {
 	var plugintype = "";
 	var plugintypeArray = document.getElementsByName("value(sys.att.pdf.plugintype)");
 	if (null != plugintypeArray) {
 		for(var i = 0; i < plugintypeArray.length; i++) {
 			if(plugintypeArray[i].checked) {
 				plugintype = plugintypeArray[i].value;
 				break;
 			}
 		}
 	}
 	if ("iWebPDF" == plugintype) {
 		document.getElementById("pdfEnabledButton").style.display = "block";
 		config_att_jg_pdfurl(); 		
 		document.getElementById("pdf2018EnabledButton").style.display = "none";
 		document.getElementById("JGPDFConfig2018").style.display = "none";
 	} else if ("iWebPDF2018" == plugintype) {
 		document.getElementById("pdfEnabledButton").style.display = "none";
 		document.getElementById("JGPDFConfig").style.display = "none";
 		document.getElementById("pdf2018EnabledButton").style.display = "block";
 		config_att_jg_pdfurl2018();
 	} else {
 		document.getElementById("pdfEnabledButton").style.display = "none";
 		document.getElementById("pdf2018EnabledButton").style.display = "none";
 	}
 }

/**
 * 是否显示金格版本切换
 */
function config_att_jg_switchversion(){
	var switchVersion=document.getElementsByName("value(sys.att.jg.switchversion)")[0].value;
	if (null != switchVersion && "false" == switchVersion) {
		document.getElementById("switchversion").style.display = "none";
	} else if (null != switchVersion && "true" == switchVersion) {
		document.getElementById("switchversion").style.display = "block";
	}
}

function config_att_jg_mulurl(){
	var isJGMULEnabled = document.getElementsByName("value(sys.att.isJGMULEnabled)")[0];	
	var jgmulurl = document.getElementsByName("value(sys.att.jg.mulurl)")[0];
	var jgmulversion = document.getElementsByName("value(sys.att.jg.mulversion)")[0];
	jgmulurl.disabled = !isJGMULEnabled.checked;
	jgmulversion.disabled = !isJGMULEnabled.checked;
	if (jgmulurl.disabled) {
		document.getElementById("JGMULConfig").style.display = "none";
	}else{
		document.getElementById("JGMULConfig").style.display = "block";
	}
}

function config_att_jg_sightml(){
	var isJGHTMLEnabled = document.getElementsByName("value(sys.att.isJGSignatureHTMLEnabled)")[0];	
	var jghtmlsigurl = document.getElementsByName("value(sys.att.jg.signaturehtmlurl)")[0];
	var jghtmlsigversion = document.getElementsByName("value(sys.att.jg.signaturehtmlversion)")[0];
	jghtmlsigurl.disabled = !isJGHTMLEnabled.checked;
	jghtmlsigversion.disabled = !isJGHTMLEnabled.checked;
	if (jghtmlsigurl.disabled) {
		document.getElementById("JGSignatureHTMLConfig").style.display = "none";
	}else{
		document.getElementById("JGSignatureHTMLConfig").style.display = "block";
	}
}

function config_att_jg_handsign(){
	var isJGHandSignEnabled = document.getElementsByName("value(sys.att.isJGHandSignatureEnabled)")[0];	
	var jghandsigurl = document.getElementsByName("value(sys.att.jg.handsignatureurl)")[0];
	var jghandsigversion = document.getElementsByName("value(sys.att.jg.handsignatureversion)")[0];
	jghandsigurl.disabled = !isJGHandSignEnabled.checked;
	jghandsigversion.disabled = !isJGHandSignEnabled.checked;
	if (jghandsigurl.disabled) {
		document.getElementById("JGHandSignatureConfig").style.display = "none";
	}else{
		document.getElementById("JGHandSignatureConfig").style.display = "block";
	}
}

/*
 * 是否启用金格国产化控件
 */
function config_att_jg_zzkk(){
	var isJGHandZzkkEnabled = document.getElementsByName("value(sys.att.isJGHandZzkkEnabled)")[0];	
	var jgcopyright = document.getElementsByName("value(sys.att.jg.copyright.zzkk)")[0];
	jgcopyright.disabled = !isJGHandZzkkEnabled.checked;
	if (jgcopyright.disabled) {
		document.getElementById("JGConfigzzkk").style.display = "none";
	}else{
		document.getElementById("JGConfigzzkk").style.display = "block";
	}
}

function config_big_img_size(thisObj){
	if(thisObj==null){
		var bigImageSizeObj=document.getElementsByName("value(sys.att.bigImageWidth)")[0];
		if(bigImageSizeObj.value=='')
			bigImageSizeObj.value = '1024';
	}
}

function config_small_img_size(thisObj){
	if(thisObj==null){
		var smallImageSizeObj=document.getElementsByName("value(sys.att.smallImageWidth)")[0];
		if(smallImageSizeObj.value=='')
			smallImageSizeObj.value = '512';
	}
}
/**屏蔽大附件上传
function config_bigatt(thisObj){
	if(thisObj==null){
		thisObj=document.getElementsByName("value(sys.att.useBigAtt)")[0];
		var maxSizeObj=document.getElementsByName("value(sys.att.smallMaxSize)")[0];
		if(maxSizeObj.value=='')
			maxSizeObj.value = '100';
	}else{
		if(thisObj.checked==true){
			document.getElementById("bigAttMsg").style.display = "block";
		}else{
			document.getElementById("bigAttMsg").style.display = "none";
	    }
	}
	var cfgAttServer=document.getElementById('lab_bigatt');
	if(thisObj.checked==true){
		cfgAttServer.style.display="";
	}else{
		cfgAttServer.style.display="none";
	}
}
**/
function config_img_maxSize(){
	var obj = document.getElementsByName("value(sys.att.imageMaxSize)")[0];
	if(obj.value == '')
		obj.value = '5';
}
/* function config_disable_fileType(){
	var obj = document.getElementsByName("value(sys.att.disabledFileType)")[0];
	if(obj.value == '')
		obj.value = '.js;.bat;.exe;.sh;.cmd;.jsp;.jspx';
} */

function config_limit_file(){
	var _type = document.getElementsByName("value(sys.att.fileLimitType)"), type = null;
	var obj = document.getElementById("message");
	var obj2 = document.getElementById("message2");
	for(var i = 0; i < _type.length; i++) {
		if(_type[i].checked) {
			type = _type[i].value;
			break;
		}
	}
	if(type == null) {
		_type[1].checked = true;
		type = "1";
		var obj = document.getElementsByName("value(sys.att.disabledFileType)")[0];
		if(obj.value == '')
			obj.value = '.js;.bat;.exe;.sh;.cmd;.jsp;.jspx';
	}
	if("1"==type){
		obj2.style.display = "none";
		document.getElementsByName("value(sys.att.fileLimitType)")[0].checked = true;
		document.getElementsByName("limitValue")[0].value = "1";
	}else if("2"==type){
		obj.style.display = "none";
		document.getElementsByName("value(sys.att.fileLimitType)")[1].checked = true;
		document.getElementsByName("limitValue")[0].value = "2";
	}
}

function config_att_file_limit(thisObj){
	
	var obj = document.getElementsByName("value(sys.att.disabledFileType)")[0];
	if("1"==thisObj && thisObj!=document.getElementsByName("limitValue")[0].value){
		document.getElementsByName("limitValue")[0].value = "1";
		obj.value = ".js;.bat;.exe;.sh;.cmd;.jsp;.jspx";
	}else if("2"==thisObj && thisObj!=document.getElementsByName("limitValue")[0].value){
		document.getElementsByName("limitValue")[0].value = "2";
		obj.value = "";
	}
	
	var obj = document.getElementById("message");
	var obj2 = document.getElementById("message2");
	if("1"==thisObj){
		obj.style.display = "block";
		obj2.style.display = "none";
	}else if("2"==thisObj){
		obj.style.display = "none";
		obj2.style.display = "block";
	}
}

function config_att_location(){
	var locations  = document.getElementsByName("value(sys.att.location)");
	var value;
	for(var i = 0; i < locations.length; i++) {
		if(locations[i].checked) {
			value = locations[i].value;
			break;
		}
	}
	if(!value) {
		value = locations[0].value;
		locations[0].checked = true;
	}
	
	config_att_change(value);
}

function config_att_change(key){
	var func = window['config_att_location_' + key + '_out'];
	if(func) {
		func(key);
	}
	$('[data-key]').hide();
	$('[data-key="'+key+'"]').show();
	update_config_other(key);
}

var sysOtherLocation;

function config_other_location(){
	var locations  = document.getElementsByName("value(sys.other.location)");
	var value;
	for(var i = 0; i < locations.length; i++) {
		if(locations[i].checked) {
			value = locations[i].value;
			break;
		}
	}
	if (sysOtherLocation && sysOtherLocation != value) {
		for (var i = 0; i < locations.length; i++) {
			if (locations[i].value == sysOtherLocation) {
				locations[i].checked = true;
				break;
			}
		}
	} else {
		if (!value) {
			value = locations[0].value;
			locations[0].checked = true;
		}
	}
}

function update_config_other(v){
	var locations  = document.getElementsByName("value(sys.other.location)");
	for(var i = 0; i < locations.length; i++) {
		if(locations[i].checked) {
			sysOtherLocation = locations[i].value;
			break;
		}
	}
	for(var i = 0; i < locations.length; i++) {
		if(locations[i].value == v) {
			locations[i].checked = true;
			break;
		}
	}
	for(var i = 0; i < locations.length; i++) {
		locations[i].disabled = false;
	}
	if (v == 'server') {
		for(var i = 0; i < locations.length; i++) {
			locations[i].disabled = true;
		}
	}else if (v == 'aliyun') {
		for(var i = 0; i < locations.length; i++) {
			if(locations[i].value == 'f4oss') {
				locations[i].disabled = true;
				break;
			}
		}
	}else if (v == 'f4oss') {
		for(var i = 0; i < locations.length; i++) {
			if(locations[i].value == 'aliyun') {
				locations[i].disabled = true;
				break;
			}
		}
	}
}

config_addOnloadFuncList(config_att_location);
config_addOnloadFuncList(config_other_location);

//屏蔽大附件上传
//config_addOnloadFuncList(config_bigatt);
config_addOnloadFuncList(config_big_img_size);
config_addOnloadFuncList(config_small_img_size);
config_addOnloadFuncList(config_img_maxSize);
//config_addOnloadFuncList(config_disable_fileType);


config_addOnloadFuncList(config_att_jg_pdfurl);
config_addOnloadFuncList(config_att_jg_pdfurl2018);
config_addOnloadFuncList(config_att_jg_mulurl);
config_addOnloadFuncList(config_att_jg_sightml);
config_addOnloadFuncList(config_att_jg_plugintype);
config_addOnloadFuncList(config_att_jg_switchversion);
config_addOnloadFuncList(config_att_jg_handsign);
config_addOnloadFuncList(config_att_jg_zzkk);
config_addOnloadFuncList(config_limit_file);
config_addOnloadFuncList(config_att_lattice_change);
config_addOnloadFuncList(config_att_foxit_change);
config_addOnloadFuncList(config_att_pdf_plugintype);
</script>
<table class="tb_normal" width=100%>
	
	<tr>
		<td class="td_normal_title" colspan="2">
			<b>存储配置</b>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width="15%">
			附件存储类型
		</td>
		<td>
			<xform:radio 
				property="value(sys.att.location)" 
				showStatus="edit" 
				required="true" 
				onValueChange="config_att_change(this.value);">
			<%
				for (SysFileLocation location : SysFileLocationUtil.getLocations()) {
			%> 
					<xform:simpleDataSource value="<%=location.getKey()%>"><%=location.getTitle()%></xform:simpleDataSource>
			<%
	 			}
 			%>
 			</xform:radio>
 		</td>
	</tr>
	
	<tr>
		<td colspan="2">
		
	
	<%
		for (SysFileLocation location : SysFileLocationUtil.getLocations()) {
	%>
			<table style="width: 100%;display: none;" data-key="<%=location.getKey()%>">
				<c:import url="<%=location.getConfigJspUrl()%>" charEncoding="utf-8" />
			</table>
	<%
		}
	%>
		</td>
	
	</tr>
	
	<tr>
		<td class="td_normal_title" width="15%">
			其它文件存储类型
		</td>
		<td>
			<xform:radio
					property="value(sys.other.location)"
					showStatus="edit"
					required="true">
				<%
					for (SysFileLocation location : SysFileLocationUtil.getLocations()) {
				%>
				<xform:simpleDataSource value="<%=location.getKey()%>"><%=location.getTitle()%></xform:simpleDataSource>
				<%
					}
				%>
			</xform:radio>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" colspan="2"><b>附件配置</b></td>
	</tr>
	
	<tr>
	<td class="td_normal_title" width="15%">附件存放位置</td>
	<td><xform:text property="value(kmss.resource.path)"
			subject="文件路径" required="true" style="width:85%" showStatus="edit" /><br>
		<span class="message">
			文件（包括其他资源、临时文件、附件等）保存路径，例：windows环境为“c:/landray/kmss/resource”,linux和unix为“/usr/landray/kmss/resource”<br>
			如附件保存方式选择为文件存储方式，请保证该目录有足够的存储空间，集群环境下该目录请使用序列化共享存储设备
	</span></td>
</tr>

	<tr>
		<td class="td_normal_title" width="15%">普通附件大小限制</td>
		<td>
			<xform:text property="value(sys.att.smallMaxSize)" subject="普通附件大小限制" validators="number" required="false" style="width:150px" showStatus="edit"/><br>
			<span class="message">单个附件上传大小限制，例：100，单位：M &nbsp;&nbsp;&nbsp;</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">图片附件大小限制</td>
		<td>
			<xform:text property="value(sys.att.imageMaxSize)" subject="图片附件大小限制" validators="number" style="width:150px" showStatus="edit"/><br>
			<span class="message">图片附件上传大小限制，例：5，单位：M &nbsp;&nbsp;&nbsp;</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">大图宽度</td>
		<td>
			<xform:text property="value(sys.att.bigImageWidth)" subject="大图宽度" required="true" style="width:150px" showStatus="edit"/><br>
			<span class="message">1）涉及部件类型：A )单图片上传；B )多图片上传；C )RTF中图片上传；D )表单中图片上传<br/>2）应用场景：大图适用于在电脑版和移动版的view页面调用，小图适用于在电脑版和移动版的list页面调用，原图适用于图片预览时调用<br/>3）例：1024,默认设置为1024，如果上传图片宽度大于设定值，那么图片按照原图的宽度和该设定值的比例进行等比压缩；设置为0表示不压缩</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">小图宽度</td>
		<td>
			<xform:text property="value(sys.att.smallImageWidth)" subject="小图宽度" required="true" style="width:150px" showStatus="edit" /><br>
			<span class="message">1）涉及部件类型：A )单图片上传；B )多图片上传；C )RTF中图片上传<br/>2）应用场景：大图适用于在电脑版和移动版的view页面调用，小图适用于在电脑版和移动版的list页面调用，原图适用于图片预览时调用<br/>3）例：512,默认设置为512，如果上传图片宽度大于设定值，那么图片按照原图的宽度和该设定值的比例进行等比压缩；设置为0表示不压缩</span>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width="15%">附件上传类型限制</td>
		<td>
			<xform:radio property="value(sys.att.fileLimitType)" showStatus="edit" onValueChange="config_att_file_limit(this.value);">
				<xform:simpleDataSource value="1">不允许上传的附件类型设置</xform:simpleDataSource>
				<xform:simpleDataSource value="2">只允许上传的附件类型设置</xform:simpleDataSource>
			</xform:radio><br>
			<xform:text property="limitValue" showStatus="noShow"></xform:text>		
			<xform:text property="value(sys.att.disabledFileType)" subject="限制上传附件类型" style="width:85%" showStatus="edit" /><br>
			<span class="message" id="message">
				基于安全考虑，设定不允许上传的附件类型，配置格式为:.js;.bat;.exe;.sh;.cmd;.jsp;.jspx，多种文件后缀以分号分隔。
			</span>
			<span class="message" id="message2">
				基于安全考虑，设定只允许上传的附件类型，配置格式为:.docx;.xlsx;.txt;.pdf（注意wps文件的实际扩展名为.wps或者.jar），多种文件后缀以分号分隔。
			</span>							
		</td>
	</tr> 
	<tr>
		<td class="td_normal_title" width="15%">是否启用金格控件</td>
		<td>
			<xform:text property="value(sys.att.jg.switchversion)" showStatus="noShow"></xform:text>
			<div id="switchversion" style="display: block;">
				控件类型：
				<xform:radio property="value(sys.att.jg.plugintype)" showStatus="edit" onValueChange="config_att_jg_plugintype(this.value);">
					<xform:simpleDataSource value="2015">iWebOffice2015</xform:simpleDataSource>
					<xform:simpleDataSource value="2009">iWebOffice2009</xform:simpleDataSource>
					<xform:simpleDataSource value="2003">iWebOffice2003</xform:simpleDataSource>
				</xform:radio>
				（适配windows客户端）
			</div>
			
			<div id="JGConfig2003" style="display: none;">
			<span style="font-weight:bold;">金格iWebOffice2003控件：支持在IE内核浏览器中在线打开、在线编辑和批注文档，支持对MS Office/WPS文档强制痕迹保留，不支持OFFICE与WPS混用编辑
			</span><br>
			控件地址：
			<xform:text property="value(sys.att.jg.ocxurl.2003)" subject="金格控件地址" style="width:85%" showStatus="edit" /><br>
			<span class="message">
				当您第一次启用金格控件以及控件版本更新，客户端IE访问时会提醒用户下载控件，同时访问人数过多将会造成系统阻塞，建议将控件放到其他能访问的服务器，等高峰期过后更改回来即可<br>
				控件地址为空将读取系统默认路径：/sys/attachment/plusin/iWebOffice2003.ocx<br>
				控件地址格式：http://*******/iWebOffice2003.ocx
			</span><br>
			控件版本号：
			<xform:text property="value(sys.att.jg.ocxversion.2003)" subject="金格控件版本" style="width:35%" showStatus="edit" /><br>
			<span class="message">
				用于金格控件版本更新，输入的版本号高于当前安装的版本号，客户端IE会提示用户下载并更新控件。<br>
				例如：8,8,8,72（以英文逗号分隔）
			</span>			
			<br>
			<div id="switchOfficeType">
				办公软件类型：
				<xform:radio property="value(sys.att.jg.office.plugintype)" showStatus="edit">
					<xform:simpleDataSource value="office">OFFICE软件</xform:simpleDataSource>
					<xform:simpleDataSource value="wps">WPS软件</xform:simpleDataSource>
				</xform:radio>
			</div>
			</br>
			</div>
		
			<div id="JGConfig2009" style="display: block;">
			<span style="font-weight:bold;">金格iWebOffice2009控件：支持在IE内核浏览器中在线打开、在线编辑和批注文档，支持对MS Office/WPS文档强制痕迹保留，支持OFFICE与WPS混用编辑
			</span><br>
			控件地址：
			<xform:text property="value(sys.att.jg.ocxurl)" subject="金格控件地址" style="width:85%" showStatus="edit" /><br>
			<span class="message">
				当您第一次启用金格控件以及控件版本更新，客户端IE访问时会提醒用户下载控件，同时访问人数过多将会造成系统阻塞，建议将控件放到其他能访问的服务器，等高峰期过后更改回来即可<br>
				控件地址为空将读取系统默认路径：/sys/attachment/plusin/iWebOffice2009.cab<br>
				控件地址格式：http://*******/iWebOffice2009.cab
			</span><br>
			控件版本号：
			<xform:text property="value(sys.att.jg.ocxversion)" subject="金格控件版本" style="width:35%" showStatus="edit" /><br>
			<span class="message">
				用于金格控件版本更新，输入的版本号高于当前安装的版本号，客户端IE会提示用户下载并更新控件。<br>
				例如：10,8,0,2（以英文逗号分隔）
			</span>			
			<br>
			</br>
			</div>
			
			<div id="JGConfig2015"  style="display: none;">
			<div style="clear: both;"></div>
			<span style="font-weight:bold;">金格iWebOffice2015控件：支持在IE内核、谷歌、火狐浏览器中在线打开、在线编辑和批注文档，支持对MS Office/WPS文档强制痕迹保留
			</span><br>
			控件地址：
			<xform:text property="value(sys.att.jg.ocxurl.2015)" subject="金格控件地址" style="width:85%" showStatus="edit" /><br>
			<span class="message">
				当您第一次启用金格控件以及控件版本更新，客户端IE访问时会提醒用户下载控件，同时访问人数过多将会造成系统阻塞，建议将控件放到其他能访问的服务器，等高峰期过后更改回来即可<br>
				控件地址为空将读取系统默认路径：/sys/attachment/plusin/iWebOffice2015.cab<br>
				控件地址格式：http://*******/iWebOffice2015.cab
			</span><br>
			控件版本号：
			<xform:text property="value(sys.att.jg.ocxversion.2015)" subject="金格控件版本" style="width:35%" showStatus="edit" /><br>
			<span class="message">
				用于金格控件版本更新，输入的版本号高于当前安装的版本号，客户端IE会提示用户下载并更新控件。<br>
				例如：12,4,0,474（以英文逗号分隔）
			</span>			
			<br>
			版权支持信息：
			<xform:text property="value(sys.att.jg.copyright.2015)" subject="金格控件版权信息" style="width:85%" showStatus="edit"/><br>
			<span class="message">
				金格控件能否使用的授权信息，只有经过授权后方能使用，在采购的安装包中有版权copyright信息。
			</span>
			<br>
			<font color="red">*2015控件下，必须填写正确的版权支持信息（copyright）。</font>
			<br></br>
			</div>
			
			<div id="switchpdf">
				PDF控件类型：
				<xform:radio property="value(sys.att.pdf.plugintype)" showStatus="edit" onValueChange="config_att_pdf_plugintype(this.value);">
					<xform:simpleDataSource value="iWebPDF">iWebPDF</xform:simpleDataSource>
					<xform:simpleDataSource value="iWebPDF2018">iWebPDF2018</xform:simpleDataSource>
				</xform:radio>
			</div>
			
			<label style="float: left;" id="pdf2018EnabledButton">
				<html:checkbox property="value(sys.att.isJGPDF2018Enabled)" value="true" onclick="config_att_jg_pdfurl2018()"/>
				<span style="font-weight:bold;">启用金格iWebPDF2018控件：支持在IE内核浏览器中直接浏览PDF文档，提供对文档的打印、下载权限控制
				</span>
			</label>
			<div style="clear: both;"></div>			
			<div id="JGPDFConfig2018" style="display: none;">
				控件地址：
				<xform:text property="value(sys.att.jg.pdfurl2018)" subject="金格PDF控件地址" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
				<span class="message">
					当您第一次启用金格控件以及控件版本更新，客户端IE访问时会提醒用户下载控件，同时访问人数过多将会造成系统阻塞，建议将控件放到其他能访问的服务器，等高峰期过后更改回来即可<br>
					控件地址为空将读取系统默认路径：/sys/attachment/plusin/iWebPDF2018.cab<br>
					控件地址格式：http://*******/iWebPDF2018.cab
				</span><br>
				控件版本号：
				<xform:text property="value(sys.att.jg.pdfversion2018)" subject="金格PDF2018控件版本" style="width:35%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
				<span class="message">
					用于金格控件版本更新，输入的版本号高于当前安装的版本号，客户端IE会提示用户下载并更新控件。<br>
					例如：3,1,6,2254（以英文逗号分隔）
				</span>
				<br>
				版权支持信息：
				<xform:text property="value(sys.att.pdf2018.copyright)" subject="iWebPDF2018版权信息" style="width:85%" showStatus="edit"/><br>
				<span class="message">
					PDF2018控件能否使用的授权信息，只有经过授权后方能使用，在采购的安装包中有版权copyright信息。
				</span>
				<br>
				<!-- PDF签章 --> 
				<label> 
					<html:checkbox property="value(sys.att.isJGSignaturePDFEnabled)" 
					value="true"/> 
					<span style="font-weight: bold;">
						启用金格iSignaturePDF网页签章：支持通过iWebPDF2018中间件打开的PDF中进行盖章与签字
					</span>
				</label>
				<br></br>
			</div>
			
			<label style="float: left;" id="pdfEnabledButton">
				<html:checkbox property="value(sys.att.isJGPDFEnabled)" value="true" onclick="config_att_jg_pdfurl()"/>
				<span style="font-weight:bold;">启用金格iWebPDF控件：支持在IE内核浏览器中直接浏览PDF文档，提供对文档的打印、下载权限控制
				</span>
			</label>
			<div style="clear: both;"></div>
			
			<div id="JGPDFConfig" style="display: none;">
				控件地址：
				<xform:text property="value(sys.att.jg.pdfurl)" subject="金格PDF控件地址" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
				<span class="message">
					当您第一次启用金格控件以及控件版本更新，客户端IE访问时会提醒用户下载控件，同时访问人数过多将会造成系统阻塞，建议将控件放到其他能访问的服务器，等高峰期过后更改回来即可<br>
					控件地址为空将读取系统默认路径：/sys/attachment/plusin/iWebPDF.cab<br>
					控件地址格式：http://*******/iWebPDF.cab
				</span><br>
				控件版本号：
				<xform:text property="value(sys.att.jg.pdfversion)" subject="金格PDF控件版本" style="width:35%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
				<span class="message">
					用于金格控件版本更新，输入的版本号高于当前安装的版本号，客户端IE会提示用户下载并更新控件。<br>
					例如：8,0,0,538（以英文逗号分隔）
				</span><br></br>
			</div>	
			
			
			<div style="clear: both;">
			</div>
			
			
			<label id="JGMULControl" style="display: block;">
				<html:checkbox property="value(sys.att.isJGMULEnabled)" value="true" onclick="config_att_jg_mulurl()"/>
				<span style="font-weight:bold;">启用金格 iWebPlugin多浏览器插件：支持在WIN平台上运行的Chrome（44版本及以下）、FireFox、Safari 32位浏览器中使用，必须结合iWebOffice或iWebPDF控件使用，如果独立使用则无任何功能效果（不支持Mac OS等平台）
				</span>
			</label>
			<div id="JGMULConfig" style="display: none;">
			插件地址：
			<xform:text property="value(sys.att.jg.mulurl)" subject="金格多浏览器插件地址" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">
				金格多浏览器插件下载地址，注意由于是exe文件，此插件无法自动升级.自动安装，客户需要在客户端浏览器输入插件地址，下载到本地后手动安装，同时访问人数过多将会造成系统阻塞，建议将插件放到其他能访问的服务器，等高峰期过后更改回来即可。<br>
				插件地址为空将读取系统默认路径：/sys/attachment/plusin/iWebPlugin.zip<br>
				插件地址格式：http://*******/iWebPlugin.zip
			</span><br>
			版权支持信息：
			<xform:text property="value(sys.att.jg.mulversion)" subject="金格多浏览器插件版权信息" style="width:35%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">
				金格多浏览器插件能否使用的授权信息，只有经过授权后方能使用，为空则默认的授权信息为：www.landray.com.cn。
			</span>
			<br></br>
			</div>
							
			<!-- 网页签章 -->
			<label style="float: left;">
				<html:checkbox property="value(sys.att.isJGSignatureHTMLEnabled)" value="true" onclick="config_att_jg_sightml()"/>
				<span style="font-weight:bold;">启用金格SignatureHTML网页签章：支持在IE内核浏览器中直接在稿纸上进行盖章和签字
				</span>
			</label><br><div style="clear: both;"></div>
			<div id="JGSignatureHTMLConfig" style="display: none;">
			网页签章地址：
			<xform:text property="value(sys.att.jg.signaturehtmlurl)" subject="金格SignatureHTML网页签章地址" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">
				网页签章地址为空将读取系统默认路径：/sys/attachment/plusin/iSignatureHTML.cab<br>
				控件地址格式：http://*******/iSignatureHTML.cab
			</span><br>
			网页签章版本号：
			<xform:text property="value(sys.att.jg.signaturehtmlversion)" subject="金格SignatureHTML网页签章版本" style="width:35%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">
				例如：8,2,2,56（以英文逗号分隔）
			</span><br></br>
			</div>
			
			<!-- 手写签批 -->
			<label style="float: left;">
				<html:checkbox property="value(sys.att.isJGHandSignatureEnabled)" value="true" onclick="config_att_jg_handsign()"/>
				<span style="font-weight:bold;">启用金格iWebRevision控件：支持在IE内核浏览器中打开进行手写签批,需在流程引擎服务开启"审批时支持手写签批"开关
				</span>
			</label><br><div style="clear: both;"></div>
			<div id="JGHandSignatureConfig" style="display: none;">
			插件地址：
			<xform:text property="value(sys.att.jg.handsignatureurl)" subject="插件地址" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">
				当您第一次使用手写签批控件以及控件版本更新，客户端IE访问时会提醒用户下载控件，同时访问人数过多将会造成系统阻塞，建议将控件放到其他能访问的服务器，等高峰期过后更改回来即可。<br>
				控件地址为空将读取系统默认路径：/sys/attachment/plusin/iWebRevision.cab<br>
				控件地址格式：http://*******/iWebRevision.cab
			</span><br>
			控件版本号：
			<xform:text property="value(sys.att.jg.handsignatureversion)" subject="控件版本" style="width:35%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">
				例如：5,0,0,0（以英文逗号分隔）
			</span><br></br>
			</div>
									
			<!-- 国产化 -->
			<label style="float: left;">
				<html:checkbox property="value(sys.att.isJGHandZzkkEnabled)" value="true" onclick="config_att_jg_zzkk()"/>
				<span style="font-weight:bold;">启用iWebOffice信创控件：适配国产化客户端，支持在火狐52以下，360企业安全浏览器中在线打开、在线编辑和批注文档，支持对WPS文档强制痕迹保留
				</span>
			</label><br><div style="clear: both;"></div>
			<div id="JGConfigzzkk" style="display: none;">
			版权支持信息：
			<xform:text property="value(sys.att.jg.copyright.zzkk)" subject="金格控件版权信息" style="width:85%" showStatus="edit"/><br>
			<span class="message">
				金格控件能否使用的授权信息，只有经过授权后方能使用，在采购的安装包中有版权copyright信息。
				<br>
				<font color="red">*若启用国产化控件，此时若中间件是tomcat，请检查server.xml文件是否开启压缩compression="on"，若有请将on改成off。</font>
			</span>
			<br></br>
			</div>						
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">福昕阅读授权</td>
		<td>
		<label>
		<html:checkbox value="true" property="value(sys.foxit.enable)" onclick="config_att_foxit_change();"/>
		开启
		</label><br>
		<div id="sys_foxit_license_div">
		licenseSN：
		<xform:text property="value(sys.foxit.license.sn)" subject="licenseSN" style="width:90%" showStatus="edit"/><br>
		licenseKey：
		<xform:text property="value(sys.foxit.license.key)" subject="licenseKey" style="width:90%" showStatus="edit"/><br>
		</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">点阵笔手写控件</td>
		<td>
		<label>
		<html:checkbox value="true" property="value(sys.tstudy.enable)" onclick="config_att_lattice_change();"/>
		开启
		</label><br>
		<div id="sys_tstudy_websocket_ip_div">
		点阵笔websocket服务的ip地址（默认127.0.0.1）：
		<xform:text property="value(sys.tstudy.websocket.ip)" subject="点阵笔websocket服务的ip地址" style="width:55%" showStatus="edit"/><br>
		</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">附件转换注册心跳服务监听端口</td>
		<td>
			<xform:text property="value(sys.att.convert.register.listenport)" subject="附件转换注册心跳服务监听端口" required="false" style="width:150px" showStatus="edit"/><br>
			<span class="message">例：5664,不是必须，没有设置时是默认为5664，一台机器部署多个系统的时候必须设置，并且各个系统不能相同，上下这两个也不能相同</span>
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width="15%">附件转换服务监听端口</td>
		<td>
			<xform:text property="value(sys.att.convert.listenport)" subject="附件转换服务监听端口" required="false" style="width:150px" showStatus="edit"/><br>
			<span class="message">例：5665,不是必须，没有设置时是默认为5665，一台机器部署多个系统的时候必须设置，并且各个系统不能相同，上下这两个也不能相同</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">附件转换服务处理客户端请求端口</td>
		<td>
			<xform:text property="value(sys.att.convert.processport)" subject="附件转换服务处理客户端请求端口" required="false" style="width:150px" showStatus="edit"/><br>
			<span class="message">例：5656,不是必须，没有设置时是默认为5656，一台机器部署多个系统的时候必须设置，并且各个系统不能相同，上下这两个也不能相同</span>
		</td>
	</tr>	
	<tr style="display:none">
		<td class="td_normal_title" width="15%">控件id</td>
		<td>
			<xform:text property="value(sys.att.jg.clsid)" subject="控件id" style="width:85%" showStatus="edit"/><br>
		</td>
	</tr>	
</table>
 