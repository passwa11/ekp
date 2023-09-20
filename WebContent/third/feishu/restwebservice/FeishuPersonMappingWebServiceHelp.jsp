<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("optbar.js|list.js");
</script>
<script>
 function expandMethod_tr(domObj) {
	 var thisObj = $(domObj);
		var isExpand = thisObj.attr("isExpanded");
		if(isExpand == null)
			isExpand = "0";
		var trObj=thisObj.parents("tr");
		trObj = trObj.next("tr");
		var imgObj = thisObj.find("img");
		if(trObj.length > 0){
			if(isExpand=="0"){
				trObj.show();
				thisObj.attr("isExpanded","1");
				imgObj.attr("src","${KMSS_Parameter_StylePath}icons/collapse.gif");
			}else{
				trObj.hide();
				thisObj.attr("isExpanded","0");
				imgObj.attr("src","${KMSS_Parameter_StylePath}icons/expand.gif");
			}
		}
 }
 
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
			{TBID:"List_ViewTable1_1"},{TBID:"List_ViewTable1_2"},{TBID:"List_ViewTable1_3"},
			{TBID:"List_ViewTable2_1"},{TBID:"List_ViewTable2_2"},{TBID:"List_ViewTable2_3"},
			{TBID:"List_ViewTable3_1"},{TBID:"List_ViewTable3_2"},{TBID:"List_ViewTable3_3"}
		);
</script>


<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}服务接口说明</p>

<center>
<br/>
<table border="0" width="95%">
	<tr>
		<td><b>接口说明</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1. 根据组织架构ids获取飞书用户映射信息集
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">/getBySysOrgIds</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">获取飞书用户映射信息集</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数</td>
					<td>
						EKP组织架构id数组
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>请求参数示例</td>
					<td width="85%">
						["17b1a5d8ed93ea7ee71fbe343d7a4425","17b135d8ed93ea3dd71fbe343d7a4423"]
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求方式</td>
					<td>POST</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求格式</td>
					<td>application/json</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>响应说明</td>
					<td width="85%">
						响应返回json格式数据
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>成功响应报文</td>
					<td width="85%">
						[{
							"approvor_id" : "17b1a5d8ed93ea7ee71fbe343d7a4425",
							"approvor_mobile" : "13723407802",
							"approvor_email" : "23412412@landray.com",
							"ex_employee_id" : "232432424",
							"ex_open_id" : "43423423423",
						},
						{
							"approvor_id" : "17b135d8ed93ea3dd71fbe343d7a4423",
							"approvor_mobile" : "13723407804",
							"approvor_email" : "23412414@landray.com",
							"ex_employee_id" : "2324324234",
							"ex_open_id" : "43423423412",
						}
						]
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>失败响应报文</td>
					<td width="85%">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>