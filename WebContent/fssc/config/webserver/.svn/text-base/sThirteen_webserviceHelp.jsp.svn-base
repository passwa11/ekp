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
		<td width="85%">getData(String startDate,String endDate,String dataType)</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>功能描述</td>
		<td width="85%">资产领用、资产调拨</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回值</td>
		<td width="85%">String</td>
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
				    <td width="30%">是否必填</td>
				    <td width="30%">描 述</td>
				</sunbor:columnHead>
			</tr>
			<tr>
				<td align="center">1</td>
				<td>startDate</td>
				<td>字符串（String）</td>
				<td>否</td>
				<td>开始日期（格式：yyyy-MM-DD）</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>endDate</td>
				<td>字符串（String）</td>
				<td>否</td>
				<td>结束日期（格式：yyyy-MM-DD）</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>dataType</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>1：资产领用；2：资产调拨</td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回结果说明</td>
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
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>请求参数</td>
		<td width="35%">
		</td>
	</tr>				
	<tr>
		<td class="td_normal_title" width=15%>返回参数样例</td>
		<td width="35%">[
    {
        "fd_date": "2022-09-22",
        "fd_code": "S0420220922001",
        "fd_person": "1829eefc033969261cc245043689e42d",
        "fd_desc": "说明",
        "detail": [
            {
                "fd_dc_dept": null,
                "fd_zc_code": null,
                "fd_yjgh_date": null,
                "fd_user_person": "1829eefc033969261cc245043689e42d",
                "fd_code": "S0420220922001"
            }
        ],
        "fd_dept": "182119142982e9bb09009b4481789e81",
        "fd_tp_person": null
    }
]
		</td>
	</tr>				
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>
