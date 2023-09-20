<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.webservice2.util.SysWsUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%
	String clsid = ResourceUtil.getKmssConfigString("sys.att.jg.clsid");
	//控件类型
	String plugintype = ResourceUtil.getKmssConfigString("sys.att.jg.plugintype");
	String fileType = ".doc";
	
	if(StringUtil.isNull(clsid)){
		clsid = "8B23EA28-2009-402F-92C4-59BE0E063499";
		if ("2003".equals(plugintype)) {
			clsid = "23739A7E-5741-4D1C-88D5-D50B18F7C347";
			String jgOfficeType = ResourceUtil.getKmssConfigString("sys.att.jg.office.plugintype");
			if ("wps".equals(jgOfficeType)) {
				fileType = ".wps";
			}
		}
	}
	request.setAttribute("clsid",clsid);
	//ie浏览器识别
	Boolean isIE = Boolean.FALSE;
	if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1
			|| request.getHeader("User-Agent").toUpperCase()
					.indexOf("TRIDENT") > -1) {
		isIE = Boolean.TRUE;
	}
	//edge浏览器识别
	Boolean isEdge = Boolean.FALSE;
	if (request.getHeader("User-Agent").toUpperCase().indexOf("EDGE") > -1) {
		isEdge = Boolean.TRUE;
	}
	//chrome浏览器45版本识别
	Boolean isChrome45 = Boolean.FALSE;
	int index = request.getHeader("User-Agent").toUpperCase().indexOf("CHROME/");
	if(index > 0){
		if(Integer.parseInt(request.getHeader("User-Agent").toUpperCase().substring(index+7, index+9)) > 44){
			isChrome45 = Boolean.TRUE;
		}
	}
	if ((!JgWebOffice.isJGMULEnabled() && !isIE) || (JgWebOffice.isJGMULEnabled() && (isEdge || isChrome45))) {
%>
<div class="jg_tip_container" style="margin-right:4px;border: 1px solid #b4b4b4;">
	<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_prompt.jsp" charEncoding="UTF-8"></c:import>
</div>
<%
		return;
	}
%>
<script>
var pluginType = '<%=plugintype%>';
function JG_GetWebOffice() {
	return document.getElementById("JGWebOffice_${JsParam.fdKey}");
}
JG_Lang = {
	JGLang : '<bean:message bundle="sys-attachment" key="JG.lang" />',
	JGToolsNew : '<bean:message bundle="sys-attachment" key="JG.tools.new" />',
	JGToolsOpen : '<bean:message bundle="sys-attachment" key="JG.tools.open" />',
	JGToolsSave : '<bean:message bundle="sys-attachment" key="JG.tools.save" />',
	JGToolsOffice : '<bean:message bundle="sys-attachment" key="JG.tools.office" />',
	JGToolsHandwrite : '<bean:message bundle="sys-attachment" key="JG.tools.handwrite" />',
	JGToolsComparison : '<bean:message bundle="sys-attachment" key="JG.tools.comparison" />',
	JGToolsRewrite : '<bean:message bundle="sys-attachment" key="JG.tools.rewrite" />',
	JGToolsFullsize : '<bean:message bundle="sys-attachment" key="JG.tools.fullsize" />',
	JGToolsBack : '<bean:message bundle="sys-attachment" key="JG.tools.back" />',
	JGToolsShowRevision : '<bean:message bundle="sys-attachment" key="JG.tools.showRevision" />',
	JGToolsHiddenRevision : '<bean:message bundle="sys-attachment" key="JG.tools.hiddenRevision" />',
	JGStatusOpening : '<bean:message bundle="sys-attachment" key="JG.status.opening" />',
	JGStatusCloseing : '<bean:message bundle="sys-attachment" key="JG.status.closeing" />'
};
var webform = document.forms["${JsParam.formBeanName}"];

function OnMenuClick(vIndex,vCaption){
	if (vIndex==1){  //保存到服务器上
		JG_SaveDocument();    //保存正文
	}
	if (vIndex==2){  //保存并退出
		JG_SaveDocument();    //保存正文
		webform.submit();
	}
	if (vIndex==3){  //保存到服务器上
		JG_WebSaveAsHtml();    //保存正文
	}
}

function OnToolsClick(vIndex,vCaption){
	if (vIndex==1){         //打开文件
		WebOpenLocalFile();
	} else if (vIndex==3){  //文字批注
		JG_GetWebOffice().ShowType = "1";
	} else if (vIndex==4){  //手写批注
		JG_GetWebOffice().ShowType = "2";
	} else if (vIndex==5){  //文档清稿
		JG_GetWebOffice().ShowType = "0";
	} else if (vIndex==6){  //重新批注
		JG_GetWebOffice().Rewrite();
	} else if (vIndex==7){  //显示痕迹
		JG_ShowRevision();
	} else if (vIndex==8){  //隐藏痕迹
		JG_HiddenRevision();
	} else {
		JG_SwitchPage(vCaption);
	}
}

//作用：显示或隐藏痕迹[隐藏痕迹时修改文档有痕迹保留]  true表示隐藏痕迹  false表示显示痕迹
function JG_ShowRevision(){
	var webOffice = JG_GetWebOffice();
	webOffice.VisibleTools(JG_Lang.JGToolsShowRevision,false);
	webOffice.VisibleTools(JG_Lang.JGToolsHiddenRevision,true);
	webOffice.WebShow(true);;  //显示痕迹
}

function JG_HiddenRevision(){
	var webOffice = JG_GetWebOffice();
	if (webOffice.WebObject == null) {
		return false;
	}
	webOffice.VisibleTools(JG_Lang.JGToolsShowRevision,true);
	webOffice.VisibleTools(JG_Lang.JGToolsHiddenRevision,false);
	webOffice.WebObject.ShowRevisions=false;  //隐藏痕迹但保留痕迹修订状态
}

//作用：显示操作状态
function JG_StatusMsg(mString){
	window.status=mString;
}

function JG_SetWebFormOtherParamter(){
	var webOffice = JG_GetWebOffice();
	webOffice.WebSetMsgByName("_attType","office");
	webOffice.WebSetMsgByName("_fdModelId", "${JsParam.fdModelId}");
	webOffice.WebSetMsgByName("_fdModelName","${JsParam.fdModelName}");
	webOffice.WebSetMsgByName("_fdKey","${JsParam.fdKey}");
	webOffice.WebSetMsgByName("_fdTemplateModelId","${JsParam.fdTemplateModelId}");
	webOffice.WebSetMsgByName("_fdTemplateModelName","${JsParam.fdTemplateModelName}");
	webOffice.WebSetMsgByName("_fdTemplateKey","${JsParam.fdTemplateKey}");
	webOffice.WebSetMsgByName("_fdCopyId","${JsParam.fdCopyId}");
}

function JG_SwitchPage(vCaption){
	var webOffice = JG_GetWebOffice();
	if (webOffice.WebObject == null) {
		return false;
	}
	var _ShowRevisions = "false";
	var _ShowRevisionsEle = document.getElementsByName("_ShowRevisions")[0];
	if(vCaption==(JG_Lang.JGToolsFullsize+"_BEGIN")){   //全屏操作之前
		_ShowRevisions = webOffice.WebObject.ShowRevisions;
		_ShowRevisionsEle.value=_ShowRevisions;
	} else if(vCaption==JG_Lang.JGToolsBack){
		_ShowRevisions = _ShowRevisionsEle.value;
		if (_ShowRevisions == "true") {
			webOffice.WebObject.ShowRevisions=true;
		} else {
			webOffice.WebObject.ShowRevisions=false;
		}
	} else if(vCaption==(JG_Lang.JGToolsBack+"_BEGIN")){ //返回操作之前
		_ShowRevisions = webOffice.WebObject.ShowRevisions;
		_ShowRevisionsEle.value=_ShowRevisions;
	} else if(vCaption==JG_Lang.JGToolsFullsize){
		_ShowRevisions = _ShowRevisionsEle.value;
		if (_ShowRevisions == "true") {
			webOffice.WebObject.ShowRevisions=true;
		} else {
			webOffice.WebObject.ShowRevisions=false;
		}
	}
}

function openLocal(){
	var webOffice = JG_GetWebOffice();
	webOffice.WebOpenLocal();
}
function fullSize(){
	var webOffice = JG_GetWebOffice();
	webOffice.FullSize();
}

var JG_Loaded = false;
//作用：载入iWebOffice
function JG_Load(){
	//防止重复加载
  if (JG_Loaded) {
    return true;
  }
 try{
    //以下属性必须设置，实始化iWebOffice
    var webOffice = JG_GetWebOffice();   
    setTimeout(function(){
    	webOffice.WebUrl = Com_GetCurDnsHost() + "${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/jg_service.jsp";
    	//alert("JG_Load:" + webOffice.WebUrl);
        webOffice.RecordID = "${JsParam.fdModelId}"; // 根据fdModelId、fdModelName、fdKey获取文档
        webOffice.FileName = "${JsParam.fdModelId}";
        webOffice.UserName = "<%=UserUtil.getUser().getFdName()%>";
        webOffice.FileType = "<%=fileType%>";
        webOffice.MaxFileSize = 100 * 1024;
        webOffice.Language = JG_Lang.JGLang; //CH,EN
        webOffice.ToolsSpace = 0;
        //webOffice.ShowWindow = "1";

    	//webOffice.EditType="2,1";
        //webOffice.EditType="1,1";
        webOffice.EditType="-1,0,0,0,0,1,1,1";

        //Start  iwebOffice2009属性  以下属性可以不要
        webOffice.PenColor="#FF0000";              
        webOffice.PenWidth="1";                    
        webOffice.Print="1";                        
        webOffice.ShowToolBar= "0";
        webOffice.VisibleTools(JG_Lang.JGToolsNew,false);
        webOffice.VisibleTools(JG_Lang.JGToolsOpen,false);
        webOffice.VisibleTools(JG_Lang.JGToolsSave,false);
        webOffice.VisibleTools(JG_Lang.JGToolsOffice,false);
        webOffice.VisibleTools(JG_Lang.JGToolsHandwrite,false);
        webOffice.VisibleTools(JG_Lang.JGToolsComparison,false);
        webOffice.VisibleTools(JG_Lang.JGToolsRewrite,false);
    	webOffice.AppendTools("10","-",0);
    	webOffice.AppendTools("8",JG_Lang.JGToolsHiddenRevision,21);
    	webOffice.AppendTools("7",JG_Lang.JGToolsShowRevision,21);
    	webOffice.AppendTools("6",JG_Lang.JGToolsRewrite,6);
    	webOffice.AppendTools("5",JG_Lang.JGToolsComparison,5);
        webOffice.AppendTools("4",JG_Lang.JGToolsHandwrite,4);
        webOffice.AppendTools("3",JG_Lang.JGToolsOffice,3);
        webOffice.AppendTools("2","-",0);
        webOffice.AppendTools("1",JG_Lang.JGToolsOpen,1); 
    	webOffice.VisibleTools(JG_Lang.JGToolsHiddenRevision,false);
    	webOffice.ShowMenu = "0";
    	/*
        webOffice.ShowMenu="1";
        webOffice.AppendMenu("1","保存远程文件(&U)");
        webOffice.AppendMenu("2","保存并退出(&E)");
        webOffice.AppendMenu("3","存为HTML");
    	*/
        //End
        JG_SetWebFormOtherParamter();
    	webOffice.RMMode = true;
        webOffice.WebOpen();
        webOffice.ShowType = 1;
     	webOffice.WebObject.ShowRevisions = false;
     	JG_StatusMsg(webOffice.Status);    	
        //alert("jg_ocx.jsp");
     	webOffice.Active(true);
        },100);		
    JG_Loaded = true;		
 }catch(e){
	JG_Loaded = false;
    alert("JG_Load error: " + e);									
  }
}

//作用：退出iWebOffice
function JG_UnLoad(){
	try{
		var webOffice = JG_GetWebOffice();
		if (!webOffice.WebClose()){
			JG_StatusMsg(webOffice.Status);
		}else{
			JG_StatusMsg(JG_Lang.JGStatusCloseing);
		}
	}catch(e){alert("JG_UnLoad error: " + e);}
}

//作用：打开文档
function JG_LoadDocument(){
	try{
		var webOffice = JG_GetWebOffice();
		JG_StatusMsg(JG_Lang.JGStatusOpening);
		if (!webOffice.WebOpen()){  	
			JG_StatusMsg(webOffice.Status);
		}else{
			JG_StatusMsg(webOffice.Status);
		}
	}catch(e){alert("JG_LoadDocument error: " + e);}
}

//作用：保存文档
function JG_SaveDocument(){
	var webOffice = JG_GetWebOffice();
	webOffice.ClearRevisions(); // 清除所有留痕
	JG_SetWebFormOtherParamter();
	try{
		webOffice.AllowEmpty = true;//内容为空默认不校验
		if (pluginType == '2003') {
			if (!webOffice.WebSave()){    
				JG_StatusMsg(webOffice.Status);
				alert(webOffice.Status);
				return false;
			}
		} else {
			if (!webOffice.WebSave(true)){    
				JG_StatusMsg(webOffice.Status);
				alert(webOffice.Status);
				return false;
			}
		}

		JG_StatusMsg(webOffice.Status);
		return true;
	}catch(e){alert("JG_SaveDocument error: " + e);}
}

//作用：保存为HTML文档
function JG_WebSaveAsHtml(){
	var webOffice = JG_GetWebOffice();
	webOffice.ClearRevisions(); // 清除所有留痕
	try{
		if (!webOffice.WebSaveAsHtml('10')) {
			JG_StatusMsg(webOffice.Status);
			alert(webOffice.Status);
			return false;
		}
		JG_StatusMsg(webOffice.Status);
		return true;
	}catch(e){alert("JG_WebSaveAsHtml error: " + e);}
}

//作用：下载服务器文件到本地
function JG_WebGetFile(){
  var webOffice = JG_GetWebOffice();
  if (webOffice.WebGetFile("c:\\WebGetFile.doc","DownLoad.doc")){   //交互OfficeServer的OPTION="GETFILE"  参数1表示本地路径  参数2表示服务器文件名称
	  JG_StatusMsg(webOffice.Status);
  }else{
	  JG_StatusMsg(webOffice.Status);
  }
  alert(webOffice.Status+"\r\n"+"文件放在c:\\WebGetFile.doc");
}

//作用：上传本地文件到服务器
function JG_WebPutFile(){
  var webOffice = JG_GetWebOffice();
  var mLocalFile = webOffice.WebOpenLocalDialog();
  if (mLocalFile!=""){
    alert(mLocalFile);
    if (webOffice.WebPutFile(mLocalFile,"Test.doc")){   //交互OfficeServer的OPTION="PUTFILE"  参数1表示本地路径，可以任何格式文件  参数2表示服务器文件名称
    	JG_StatusMsg(webOffice.Status);
    }else{
    	JG_StatusMsg(webOffice.Status);
    }
    alert(webOffice.Status);
  }
}
//作用：判断是否WPS编译器
function JG_isWpsEditer(){
	return JG_GetWebOffice().WebApplication(".wps");
}
</script>
<%
if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>-1 || request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT")>-1){
	request.setAttribute("isIE",true);
}else{
	request.setAttribute("isIE",false);
}
String plugOcxUrl = "";
String plugOcxVersion = "";
String plugMulVersion = "";
if ("2003".equals(plugintype)) {
	//2003金格控件
	plugOcxUrl = JgWebOffice.getJGOcxURL2003();
	if (null != plugOcxUrl && ("/sys/attachment/plusin/iWebOffice2003.ocx").equals(plugOcxUrl)) {
		plugOcxUrl = SysWsUtil.getUrlPrefix(request) + plugOcxUrl;
	}
	plugOcxVersion = JgWebOffice.getJGOcxVersion2003();
	plugMulVersion = JgWebOffice.getJGVersion("mul");
} else {
	//2009金格控件
	plugOcxUrl = JgWebOffice.getJGDownLoadUrl("ocx");
	if (null != plugOcxUrl && ("/sys/attachment/plusin/iWebOffice2009.cab").equals(plugOcxUrl)) {
		plugOcxUrl = SysWsUtil.getUrlPrefix(request) + plugOcxUrl;
	}
	plugOcxVersion = JgWebOffice.getJGVersion("ocx");
	plugMulVersion = JgWebOffice.getJGVersion("mul");
}

request.setAttribute("jgOcxUrl",plugOcxUrl);
request.setAttribute("jgOcxVersion",plugOcxVersion);
request.setAttribute("jgMulVersion",plugMulVersion);	

%>
<c:if test="${isIE}">
	<object 
		id="JGWebOffice_${param.fdKey}" 
		width="100%" height="100%" 
		classid="clsid:${clsid}"
		codebase="<c:url value='${jgOcxUrl}'/>#version=${jgOcxVersion}" 
		OnMenuClick="OnMenuClick(vIndex,vCaption);" OnToolsClick="OnToolsClick(vIndex,vCaption);">
	</object>
</c:if>
<c:if test="${!isIE}">
	<object 
		id="JGWebOffice_${param.fdKey}" 
		type="application/kg-activex" 
		width="100%" height="100%" 
		clsid="{${clsid}}" 
		copyright="${jgMulVersion}" 
		event_OnMenuClick="OnMenuClick" event_OnToolsClick="OnToolsClick">
	</object>
</c:if>
<input type="hidden" name="_ShowRevisions">
<script>
<c:if test="${param.fdAttType == 'office' || param.fdAttType == 'word'}">
Com_AddEventListener(window,"load",JG_Load);
</c:if>
Com_AddEventListener(window,"unload",JG_UnLoad);
</script>