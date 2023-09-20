<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>


<script type="text/javascript" src="${KMSS_Parameter_ContextPath}third/im/kk/resource/syntaxhighlighter/scripts/shCore.js"></script>
	<script type="text/javascript" src="${KMSS_Parameter_ContextPath}third/im/kk/resource/syntaxhighlighter/scripts/shBrushBash.js"></script>
	<script type="text/javascript" src="${KMSS_Parameter_ContextPath}third/im/kk/resource/syntaxhighlighter/scripts/shBrushXml.js"></script>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/im/kk/resource/syntaxhighlighter/styles/shCore.css"/>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/im/kk/resource/syntaxhighlighter/styles/shThemeDefault.css"/>
	<script type="text/javascript">
		SyntaxHighlighter.config.clipboardSwf ="${KMSS_Parameter_ContextPath}third/im/kk/resource/syntaxhighlighter/scripts/clipboard.swf";
		SyntaxHighlighter.all();
	</script>
<script type="text/javascript">
Com_IncludeFile("optbar.js|list.js");
</script>
<script>
 function expandMethod(imgSrc,divSrc) {
	var imgSrcObj = document.getElementById(imgSrc);
	var divSrcObj = document.getElementById(divSrc);
	if(divSrcObj.style.display!=null && divSrcObj.style.display!="") {
		divSrcObj.style.display = "";
		imgSrcObj.src = "${KMSS_Parameter_StylePath}icons/collapse.gif";
	}else{
		divSrcObj.style.display = "none";
		imgSrcObj.src = "${KMSS_Parameter_StylePath}icons/expand.gif";		
	}
 }

 List_TBInfo = new Array(
			{TBID:"List_ViewTable1"},{TBID:"List_ViewTable2"},{TBID:"List_ViewTable3"}
		);
</script>

<link href="${KMSS_Parameter_ContextPath}third/erp/sap/resource/google-code-prettify/prettify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}third/erp/sap/resource/google-code-prettify/prettify.js"></script>
<%-- 
<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div> --%>

<p class="txttitle">KK在线感知使用帮助</p>
<center>
<table class="tb_normal" width=80%>
	<tr>
		<td class="td_normal_title" width=15%><strong>功能说明</strong></td>
		<td width="85%">
		  在线感知提供功能：在浏览器上可以知道KK客户端对应人物是否在线,并且通过浏览器发起<br>
		  会话,短信,查看对方信息等操作。功能依赖于<strong>sys_ims</strong>模块
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%><strong>配置说明</strong></td>
		<td width="85%">
		实施人员通过admin.do KK集成配置<br>
		①启用在线感知。<br>
		②配置KK服务器地址(地址跟KK待办集成配置一致)<br>
		③配置端口(KK在线感知端口)<br>
		④项目开发人员根据需求引入在线感知图标页面代码片段(重要)
		</td>
	</tr>
	
		<tr>
		<td rowspan="4" class="td_normal_title" width=15%><strong>代码片段样例</strong>
		<br>本页代码路径： \third\im\kk\kk_awareness\kk_awareness_help.jsp
		</td>
		<td width="85%">
		<br>
		<div>
		<pre class="brush: xml;">
		&lt;c:import url="/sys/ims/sysIms_manage.jsp" charEncoding="UTF-8"&gt;
		&lt;c:param name="imName" value="KK" /&gt;
		&lt;c:param name="imParams" value="{orgs:'duany',leftHtml:'段一',rightHtml:'你好!',menuCfg:{gravity:'n'}}" /&gt;
		&lt;/c:import&gt;
		</pre>
		</div>
		<br>
		 <c:import url="/sys/ims/sysIms_manage.jsp" charEncoding="UTF-8">
					    <c:param name="imName" value="KK" />
					    <c:param name="imParams" value="{orgs:'duany',leftHtml:'段一',rightHtml:'你好!',menuCfg:{gravity:'n'}}" />
		</c:import>
		</td>
	</tr>
	<tr>
	<td width="85%">
		<br>
		调整gravity 参数更改弹出层的位置 n:上&nbsp;&nbsp;&nbsp;s:下&nbsp;&nbsp;&nbsp;w：右&nbsp;&nbsp;&nbsp;e：左
		<br>
		<div>
		<pre class="brush: xml;">
		&lt;c:import url="/sys/ims/sysIms_manage.jsp" charEncoding="UTF-8"&gt;
		&lt;c:param name="imName" value="KK" /&gt;
		&lt;c:param name="imParams" value="{orgs:'duany',leftHtml:'段一',rightHtml:'你好!',menuCfg:{gravity:'w'}}" /&gt;
		&lt;/c:import&gt;
		</pre>
		</div>
		<br>
		<c:import url="/sys/ims/sysIms_manage.jsp" charEncoding="UTF-8">
					    <c:param name="imName" value="KK" />
					    <c:param name="imParams" value="{orgs:'duany',leftHtml:'段一',rightHtml:'你好!',menuCfg:{gravity:'w'}}" />
		
		</c:import>
		</td>
	</tr>
	<tr>
	<td width="85%">
		调整showMenu 参数控制层
		<br>
		<div>
		<pre class="brush: xml;">
		&lt;c:import url="/sys/ims/sysIms_manage.jsp" charEncoding="UTF-8"&gt;
		&lt;c:param name="imName" value="KK" /&gt;
		&lt;c:param name="imParams" value="{showMenu:0,orgs:'duany',leftHtml:'段一',rightHtml:'你好!',menuCfg:{gravity:'w'}}" /&gt;
		&lt;/c:import&gt;
		</pre>
		</div>
		<br>
		<c:import url="/sys/ims/sysIms_manage.jsp" charEncoding="UTF-8">
					    <c:param name="imName" value="KK" />
					    <c:param name="imParams" value="{showMenu:0,orgs:'duany',leftHtml:'段一',rightHtml:'你好!',menuCfg:{gravity:'w'}}" />
		
		</c:import>
		</td>
		</tr>
		<tr>
		<td width="85%">
		调整showMenu 参数控制层
		<br>
		<div>
		<pre class="brush: xml;">
		&lt;c:import url="/sys/ims/sysIms_manage.jsp" charEncoding="UTF-8"&gt;
		&lt;c:param name="imName" value="KK" /&gt;
		&lt;c:param name="imParams" value="{ orgs:'duany;zenghp',linkType:'tempgroup',showMenu:0,leftHtml:'KK',rightHtml:'讨论',menuCfg:{gravity:'n'}}" /&gt;
		&lt;/c:import&gt;
		</pre>
		</div>
		<br>
		<c:import url="/sys/ims/sysIms_manage.jsp" charEncoding="UTF-8">
					    <c:param name="imName" value="KK" />
					    <c:param name="imParams" value="{ orgs:'duany;zenghp',linkType:'tempgroup',showMenu:0,leftHtml:'KK',rightHtml:'讨论',menuCfg:{gravity:'n'}}" />
		
		</c:import>
		</td>
		
		
		</tr>
		
	
	
		
</table>
</center>


<%@ include file="/resource/jsp/view_down.jsp"%>