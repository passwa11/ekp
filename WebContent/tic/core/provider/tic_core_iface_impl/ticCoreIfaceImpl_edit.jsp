<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="org.springframework.web.util.HtmlUtils"%>
<%@ page import="com.landray.kmss.tic.core.util.TicCoreUtil"%>
<%@ page import="com.landray.kmss.tic.core.provider.plugins.TicCoreProviderPlugins" %>

<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/tic/core/resource/plumb/jsp/includePlumb.jsp"%>

<%-- 引入js文件 --%> 
<%
List<Map<String, String>> pluginList = TicCoreProviderPlugins.getConfigs();
StringBuffer buf = new StringBuffer("{");
for (Map<String, String> map : pluginList) {
	buf.append("\""+map.get("providerKey")+"\":{");
	String convertXmlJsPath = map.get("convertXmlJsPath");
	if (StringUtil.isNotNull(convertXmlJsPath)) {
		String convertXmlJsFunc = map.get("convertXmlJsFunc");
		String jsPath = convertXmlJsPath.substring(0, convertXmlJsPath.lastIndexOf("/") + 1);
		String jsName = convertXmlJsPath.substring(convertXmlJsPath.lastIndexOf("/") + 1);
		buf.append("\"convertXmlJsFunc\":\""+ convertXmlJsFunc +"\",");
		buf.append("\"jsPath\":\""+ jsPath +"\",");
		buf.append("\"jsName\":\""+ jsName +"\",");
	}
	buf.append("\"providerName\":\""+ map.get("providerName") +"\"");
	buf.append("},");
}
buf = new StringBuffer(buf.substring(0, buf.length() - 1));
buf.append("}");
String handInfoStr = TicCoreUtil.addSprit(buf.toString());
%>

<link href="${KMSS_Parameter_ContextPath}tic/core/provider/resource/tree/css/dtree.css" rel="StyleSheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/provider/resource/tree/dtree.js" type="text/javascript"></script>
<script>
	Com_IncludeFile("dialog.js|data.js|json2.js");
</script>
<script>
	Com_IncludeFile("tic_validations.js","${KMSS_Parameter_ContextPath}tic/core/resource/js/","js",true);
	Com_IncludeFile("tic_sys_util.js","${KMSS_Parameter_ContextPath}tic/core/provider/resource/js/","js",true);
	Com_IncludeFile("tic_ifaceImpl.js","${KMSS_Parameter_ContextPath}tic/core/provider/resource/js/","js",true);
	var TicProvider_Lang = {
	    getError : "<bean:message bundle="tic-core-provider" key="ticCore.lang.getError"/>",
	    fdName : "<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.required.fdName"/>",
	    fdImplRefName : "<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.required.fdImplRefName"/>",
	    ticCoreIfaceId : "<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.required.ticCoreIfaceId"/>",
	    fdOrderBy : "<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.required.fdOrderBy"/>"
	};
	
	var Plugin_HandInfo = eval("(<%=handInfoStr%>)");
	for (var key in Plugin_HandInfo) {
		var jsPath = Plugin_HandInfo[key]["jsPath"];
		var jsName = Plugin_HandInfo[key]["jsName"];
		if (jsPath != null && jsName != null) {
			// 引入扩展点js文件，作用是转换xml
			Com_IncludeFile(jsName, "${KMSS_Parameter_ContextPath}"+ jsPath, "js", true);
		}
	}
</script>
<html:form action="/tic/core/provider/tic_core_iface_impl/ticCoreIfaceImpl.do">
<div id="optBarDiv">
	<c:if test="${ticCoreIfaceImplForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="saveBefore('update');">
	</c:if>
	<c:if test="${ticCoreIfaceImplForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="saveBefore('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.ticCoreIfaceImplForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-provider" key="table.ticCoreIfaceImpl"/></p>
<html:messages id="messages" message="true">
	<table style="margin:0 auto" align="center"><tr><td><img src='${KMSS_Parameter_ContextPath}resource/style/default/msg/dot.gif'>&nbsp;&nbsp;<font color='red'>
      <bean:write name="messages" /></font>
	 </td></tr></table><hr />
</html:messages> 
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%;" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.fdOrderBy"/>
		</td><td width="35%">
			<xform:text property="fdOrderBy" style="width:85%;" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.fdFuncType"/>
		</td><td width="35%">
			<input type="text" name="fdFuncType" style="width:85%; display: none;" value="${ticCoreIfaceImplForm.fdFuncType }"/>
			<input type="text" name="fdFuncTypeName" id="fdFuncTypeName" readonly="readonly" class="inputread"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.ticCoreIface"/>
		</td><td width="35%">
			<xform:select htmlElementProperties="id='ticCoreIfaceId'" property="ticCoreIfaceId" onValueChange="ifaceChange();" value="${ticCoreIfaceImplForm.ticCoreIfaceId }">
				<xform:beanDataSource serviceBean="ticCoreIfaceService" selectBlock="fdId,fdIfaceName" orderBy="" />
			</xform:select>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.fdImplRef"/>
		</td><td width="35%">
			<xform:dialog style="width:90%;"
					propertyId="fdImplRef" propertyName="fdImplRefName"
					dialogJs="Tic_treeDialog();"></xform:dialog>
		</td>
	</tr>
	<!-- 映射配置 -->
	<tr>
		<td width=100% colspan="4" align="center">
			<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.fdImplRefData"/>
		</td>
	</tr>
	<tr>
		<td width=50% colspan="2" valign="top">
			<div style="display: none;"><span class="tleft"></span></div>
			<div id="treeDiv"></div>
		</td><td width=50% colspan="2" valign="top">
			<div id="targetTreeDiv"></div>
			<div style="display: none;"><span class="tright"></span></div>
			<xform:textarea showStatus="noShow" property="fdImplRefData" style="width:85%" />
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script> 
function click(id, locaId, parID,nodeName) {
	// 空方法，防止树可以编辑
}

setTimeout("startLoadInfo();", 10);
function startLoadInfo() {
	// 验证加星*
	IfaceImplValid.init();
	// 编辑页面_选择所属接口
	ifaceChange();
	// 编辑页面_选择实现函数
	call_funcChange();
	//_jsPlumb_endpoint
	
}

function Tic_treeDialog() {
	var t_bean="ticCoreIfaceRefBean&selectId=!{value}&type=cate";
	var d_bean="ticCoreIfaceRefBean&selectId=!{value}&type=func";
	var s_bean="ticCoreIfaceRefBean&keyword=!{keyword}&type=search";
	//输入参数有点眼花,换成这种
	var data = {
		idField : "fdImplRef",
		nameField : "fdImplRefName",
		treeBean : t_bean,
		treeTitle :'<bean:message bundle="tic-core-provider" key="ticCore.fdIfaceRef.treeTitle" />',
		dataBean : d_bean,
		action : function(rtn){
			if(rtn && rtn.GetHashMapArray().length>0){
				// 先移除模版
				//removeMapperTemplate();
				var data=rtn.GetHashMapArray()[0];
				var id = data["id"];
				var providerKey = data["providerKey"];
				var providerName = data["providerName"];
				$("input[name=fdFuncType]").val(providerKey);
				$("input[name=fdFuncTypeName]").val(providerName);
				// 选择函数后的操作
				call_funcChange(id, providerKey);
			}
		},
		searchBean : s_bean,
		winTitle : '<bean:message bundle="tic-core-provider" key="ticCore.fdIfaceRef.winTitle" />'
	};
	TIC_SysUtil.ticTreeDialog(data);
}

$KMSSValidation();
</script>

</html:form>

<%@ include file="/resource/jsp/edit_down.jsp"%>

			