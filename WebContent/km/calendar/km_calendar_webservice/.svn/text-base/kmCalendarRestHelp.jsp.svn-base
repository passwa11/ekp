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
			{TBID:"List_ViewTable1_1"},{TBID:"List_ViewTable1_2"}
		);
</script>


<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}服务接口说明</p>

<center>
<br/>
<table border="0" width="95%">
	<!-- 接口01 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;"><br>&nbsp;&nbsp;1.&nbsp;&nbsp;日程今日工作同步接口<br><br>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>接口地址</td>
					<td width="85%">/api/km-calendar/main/get</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">同步日程管理中的日程列表到今日工作中，日程本身没有状态，因此不会删除已经同步过去的日程。</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>输入参数：beginTime</td>
					<td width="35%">
						样例：<br>
						{	
						“beginTime”: 	1557908847000
						}<br>
						同步时，当传递beginTime参数时，只获取在beginTime之后更新过，并且日程的时间周期包括今日的日程，如果不传递参数，则获取时间周期包括今日的日程。	
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值对象格式</td>
					<td width="85%"><img id="viewSrc1_1"
						src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0"
						onclick="expandMethod('viewSrc1_1','paramDiv1_1')" style="cursor: pointer"><br>
					<div id="paramDiv1_1">
					<table id="List_ViewTable1_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">参数名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">是否必须</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>success</td>
							<td>Boolean</td>
							<td>是</td>
							<td>成功与否标记</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>code</td>
							<td>字符串（String）</td>
							<td>是</td>
							<td>状态码</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>msg</td>
							<td>字符串（String）</td>
							<td>是</td>
							<td>操作是否成功的消息</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>datas</td>
							<td>对象（Json）</td>
							<td>否</td>
							<td>返回的结果列表</td>
						</tr>												
					</table></div>
					</td>
				</tr>	
				<tr>
					<td class="td_normal_title" width=15%>返回结果datas的单条数据定义</td>
					<td width="85%"><img id="viewSrc1_2"
						src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0"
						onclick="expandMethod('viewSrc1_2','paramDiv1_2')" style="cursor: pointer"><br>
					<div id="paramDiv1_2">
					<table id="List_ViewTable1_2" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="30%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="20%">是否必须</td>
							    <td width="30%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdWorkTitle</td>
							<td>字符串（String）</td>
							<td>是</td>
							<td>工作标题</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>workItemId</td>
							<td>字符串（String）</td>
							<td>是</td>
							<td>日程或工作的id(即当前工作的fdId)</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>bgTime</td>
							<td>Long</td>
							<td>是</td>
							<td>日程开始时间</td>
						</tr>	
						<tr>
							<td align="center">4</td>
							<td>endTime</td>
							<td>Long</td>
							<td>是</td>
							<td>日程结束时间</td>
						</tr>	
						<tr>
							<td align="center">5</td>
							<td>brief</td>
							<td>String</td>
							<td>是</td>
							<td>摘要</td>
						</tr>	
						<tr>
							<td align="center">6</td>
							<td>jobStatus</td>
							<td>Enum</td>
							<td>是</td>
							<td>日程的状态(DELETE(0), UPDATE(1), ADD(2);)</td>
						</tr>	
						<tr>
							<td align="center">7</td>
							<td>loginNames</td>
							<td>List&gt;String&lt;</td>
							<td>是</td>
							<td>用户的唯一登录名的list集合</td>
						</tr>	
						<tr>
							<td align="center">8</td>
							<td>detailUrl</td>
							<td>String</td>
							<td>是</td>
							<td>工作详情的地址接口(绝对路径)</td>
						</tr>									
					</table>
					</div>
					</td>
				</tr>	
			</table>
		</td>
	</tr>
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>