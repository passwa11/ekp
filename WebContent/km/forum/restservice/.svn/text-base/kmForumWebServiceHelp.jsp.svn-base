<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
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


<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}服务接口说明</p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>服务接口</td>
		<td width="85%">addTopic(KmForumPostParamterForm webForm)</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>功能描述</td>
		<td width="85%">WebService分享论坛贴子</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回值</td>
		<td width="85%">主文档的数据库主键值（fd_id）</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>接口参数webForm</td>
		<td width="85%"><img id="viewSrc1"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc1','paramDiv1')" style="cursor: pointer"><br>
		<div id="paramDiv1" style="display:none">
		<table id="List_ViewTable1" class="tb_noborder">
			<tr>
				<sunbor:columnHead htmlTag="td">
					<td width="40pt">序号</td>
				    <td width="20%">属性名</td>
				    <td width="20%">类 型</td>
				    <td width="10%">缺省值</td>
				    <td width="50%">描 述</td>
				</sunbor:columnHead>
			</tr>
			<tr>
				<td align="center">1</td>
				<td>fdPoster</td>
				<td>字符串（Json）</td>
				<td>无(必填)</td>
				<td>帖子发起人，为单值，格式详见发贴者的说明</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>fdForum</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>版块名称，为空时取论坛后台配置的默认版块</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>docSubject</td>
				<td>字符串（String）</td>
				<td>无(必填)</td>
				<td>帖子标题</td>
			</tr>
			<tr>
				<td align="center">4</td>
				<td>docContent</td>
				<td>字符串（String）</td>
				<td>无(必填)</td>
				<td>帖子内容</td>
			</tr>
			<tr>
				<td align="center">5</td>
				<td>attachmentForms</td>
				<td>链表（List&lt;AttachmentForm&gt;）</td>
				<td>无</td>
				<td>附件列表</td>
			</tr>														
		</table></div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>附件AttachmentForm的定义</td>
		<td width="85%"><img id="viewSrc3"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc3','paramDiv3')" style="cursor: pointer;"><br>
		<div id="paramDiv3" style="display:none">
		<table id="List_ViewTable3" class="tb_noborder">
			<tr>
				<sunbor:columnHead htmlTag="td">
					<td width="40pt">序号</td>
				    <td width="30%">属性名</td>
				    <td width="20%">类 型</td>
				    <td width="20%">缺省值</td>
				    <td width="30%">描 述</td>
				</sunbor:columnHead>
			</tr>
			<tr>
				<td align="center">1</td>
				<td>fdKey</td>
				<td>字符串（String）</td>
				<td>"attachment"</td>
				<td>附件的关键字</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>fdFileName</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>附件文件名</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>fdAttachment</td>
				<td>字节数组（byte[]）</td>
				<td>无</td>
				<td>附件内容，格式为字节编码</td>
			</tr>									
		</table>
		</div>
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>发贴者的说明</td>
		<td width="35%">发贴者采用JSon格式传输，值的格式为{类型: 值}。支持的类型有主键（Id）、编号（PersonNo）、登录名（LoginName）、关键字（Keyword）、LDAP（LdapDN）。<br>例：{"LoginName":"panyh"}</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>参考代码</td>
		<td width="35%">
             <iframe width="100%" height="100%" src="KmForumServiceClient.txt"></iframe>
        </td>
	</tr>	
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>