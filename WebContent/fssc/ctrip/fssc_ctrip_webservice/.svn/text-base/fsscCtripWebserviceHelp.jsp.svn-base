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
			{TBID:"List_ViewTable1"},{TBID:"List_ViewTable2"},{TBID:"List_ViewTable3"},{TBID:"List_ViewTable4"},{TBID:"List_ViewTable5"},{TBID:"List_ViewTable6"}
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
		<td width="85%">invokeBusinessBill(String jsonParams)</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>功能描述</td>
		<td width="85%">修改对应影像主数据和对应单据的影像状态</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回值</td>
		<td width="85%">修改结果（jsonObject）</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>接口入参JSONObject对应参数</td>
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
				<td>productType</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>产品类型</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>orderId</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>订单号</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>orderStatus</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>订单状态</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>corpId</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>携程提供的公司ID(corpId)</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>sign</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>携程提供的公司ID(corpId)</td>
			</tr>
		</table></div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回结果JSONObject说明</td>
		<td width="85%"><img id="viewSrc2"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc2','paramDiv2')" style="cursor: pointer"><br>
		<div id="paramDiv2" style="display:none">
		<table id="List_ViewTable2" class="tb_noborder">
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
				<td>result</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>调用接口是否成功，0为成功，1为失败</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>errormsg</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>失败原因</td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>参数样例</td>
		<td width="35%">参数是采用JSon格式传输，如：{"type": "1","state": "1"}。
		</td>
	</tr>				
	<tr>
		<td class="td_normal_title" width=15%>返回参数样例</td>
		<td width="35%">返回结果是采用JSon格式传输，如：<br />
			{<br />
				"result": "0",<br />
				"errormsg": ""
			}	<br />

			错误信息：<br />
			{<br />
				&nbsp;&nbsp;"result": "1",<br />
				&nbsp;&nbsp;"errormsg": "status不能为空"<br />
			}<br />
		</td>
	</tr>				
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>
