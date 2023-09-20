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
			{TBID:"List_ViewTable1_1"},{TBID:"List_ViewTable1_2"},
			{TBID:"List_ViewTable2_1"},{TBID:"List_ViewTable2_2"},
			{TBID:"List_ViewTable3_1"},{TBID:"List_ViewTable3_2"},
			{TBID:"List_ViewTable4_1"},{TBID:"List_ViewTable4_2"},
			{TBID:"List_ViewTable5_1"},{TBID:"List_ViewTable5_2"}
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
		<td style="cursor: pointer;font-size: 15px;">&nbsp;&nbsp;1&nbsp;&nbsp;会议管理今日工作同步接口<br><br>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>接口地址</td>
					<td width="85%">/api/km-imeeting/main/get</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">同步所有今天的会议作为会议参与人的工作列表。同步的会议只做新增/更新，不做删除。</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>输入参数：beginTime</td>
					<td width="35%">
						样例：<br>
						{	
						“beginTime”: 	1557908847000
						}	
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值对象格式</td>
					<td width="85%"><img id="viewSrc1_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc1_1','paramDiv1_1')" style="cursor: pointer"><br>
					<div id="paramDiv1_1" style="display:none">
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
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc1_2','paramDiv1_2')" style="cursor: pointer"><br>
					<div id="paramDiv1_2" style="display:none">
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
	<!-- 接口02 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;"><br>&nbsp;&nbsp;2&nbsp;&nbsp;需组织会议工作推荐接口<br><br>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>接口地址</td>
					<td width="85%">/api/km-imeeting/main/checkMeeting?templateId=会议模板Id&checkDept=是否检测部门</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">
					返回指定用户在周期内是否有在指定模板下安排会议<br>
					templateId:会议卡片Id，必须<br>
					checkDept:是否检测部门，非必须，默认为false，设定为true时，只要用户所在部门的其他人有创建会议也算其已经完成该项工作；<br>
					适用场景：当需要某个部门组织周期性会议时，可对秘书或者助理进行工作安排和提醒
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>输入参数</td>
					<td width="35%">
						样例：<br>
						{
							"beginDate": 1556640000000,
							"endDate": 1559318399000,
							"accounts": [
								"user1",
								"user2"
								]
						}<br>
						beginDate:周期开始时间，时间戳，例：1556640000000,非必须;<br>
						endDate:周期结束时间，时间戳，例：1556640000000,非必须;<br>
						accounts:List&gt;String&lt;,用户账号列表,必须
					</td>
				</tr>				
				<tr>
					<td class="td_normal_title" width=15%>返回值对象格式</td>
					<td width="85%"><img id="viewSrc2_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc2_1','paramDiv2_1')" style="cursor: pointer"><br>
					<div id="paramDiv2_1" style="display:none">
					<table id="List_ViewTable2_1" class="tb_noborder">
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
						<tr>
							<td align="center">5</td>
							<td>accounts</td>
							<td>List&gt;String&lt;</td>
							<td>否</td>
							<td>返回的账号列表。与datas任选一项返回</td>
						</tr>													
					</table></div>
					</td>
				</tr>	
				<tr>
					<td class="td_normal_title" width=15%>返回结果datas的单条数据定义</td>
					<td width="85%"><img id="viewSrc2_2"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc2_2','paramDiv2_2')" style="cursor: pointer"><br>
					<div id="paramDiv2_2" style="display:none">
					<table id="List_ViewTable2_2" class="tb_noborder">
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
							<td>account</td>
							<td>字符串（String）</td>
							<td>是</td>
							<td>用户账号。datas子对象</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>key</td>
							<td>字符串（String）</td>
							<td>否</td>
							<td>去重字段，长度128字节内，周期内同一用户下去重</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>validDate</td>
							<td>Long</td>
							<td>否</td>
							<td>此任务截止日期，时间戳，例：1556640000000</td>
						</tr>	
						<tr>
							<td align="center">4</td>
							<td>unknown</td>
							<td>String</td>
							<td>是</td>
							<td>可自行添加第三方自定义的字段；可在任务配置中使用spel表达式获取</td>
						</tr>							
					</table>
					</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>	
<!-- 接口03 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;"><br>&nbsp;&nbsp;3&nbsp;&nbsp;检测需组织会议工作是否完成接口<br><br>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>接口地址</td>
					<td width="85%">/api/km-imeeting/main/finishMeeting?templateId=会议模板Id&checkDept=是否检测部门</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">
					对应上一个接口的完成事件，检测指定用户在周期内是否已经在指定模板下安排会议。
 					<br>接口参数和接口2完全相同
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>输入参数</td>
					<td width="35%">
						样例：<br>
						{
							"beginDate": 1556640000000,
							"endDate": 1559318399000,
							"datas": [
								{"account": "user1", "key": "123456", "validDate": 1559318399000},{"account": "user2", "key": "123457", "validDate": 1559318399000}
								]
							 }<br>
						beginDate:周期开始时间，时间戳，例：1556640000000,非必须;<br>
						endDate:周期结束时间，时间戳，例：1556640000000,非必须;<br>
						datas:数据列表,JSON对象,必须;
						--account:用户账号,String,必须<br>
						--key:去重参数，周期内同一用户下去重,String,非必须;<br>
						--validDate:任务截止日期，时间戳，例：1556640000000,Long,必须。
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值对象格式</td>
					<td width="85%"><img id="viewSrc3_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc3_1','paramDiv3_1')" style="cursor: pointer"><br>
					<div id="paramDiv3_1" style="display:none">
					<table id="List_ViewTable3_1" class="tb_noborder">
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
						<tr>
							<td align="center">5</td>
							<td>accounts</td>
							<td>List&gt;String&lt;</td>
							<td>否</td>
							<td>返回的账号列表。与datas任选一项返回</td>
						</tr>													
					</table></div>
					</td>
				</tr>	
				<tr>
					<td class="td_normal_title" width=15%>返回结果datas的单条数据定义</td>
					<td width="85%"><img id="viewSrc3_2"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc3_2','paramDiv3_2')" style="cursor: pointer"><br>
					<div id="paramDiv3_2" style="display:none">
					<table id="List_ViewTable3_2" class="tb_noborder">
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
							<td>account</td>
							<td>字符串（String）</td>
							<td>是</td>
							<td>用户账号。datas子对象</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>key</td>
							<td>字符串（String）</td>
							<td>否</td>
							<td>去重字段，长度128字节内; 请求参数中有此字段时，返回数据中也需要有此字段</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>finished</td>
							<td>Boolean</td>
							<td>否</td>
							<td>此任务是否结束，不传或传true时默认为结束;传false时任务状态更新为执行中</td>
						</tr>	
						<tr>
							<td align="center">4</td>
							<td>businessStatus</td>
							<td>String</td>
							<td>否</td>
							<td>业务状态，中文描述；如果需要追踪任务详细流程，可使用此字段</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>url</td>
							<td>String</td>
							<td>否</td>
							<td>任务跳转url，需要追踪任务详细流程时使用</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>unknown</td>
							<td>String</td>
							<td>是</td>
							<td>可自行添加第三方自定义的字段；可在任务配置中使用spel表达式获取</td>
						</tr>									
					</table>
					</div>
					</td>
				</tr>	
			</table>
		</td>
	</tr>
	<!-- 接口04 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;"><br>&nbsp;&nbsp;4&nbsp;&nbsp;需组织会议工作推荐接口（通过检测会议纪要判断）<br><br>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>接口地址</td>
					<td width="85%">/api/km-imeeting/main/checkSummarry?templateId=会议模板Id&checkDept=是否检测部门</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">
					返回指定用户在周期内是否有在指定模板下安排会议：<br>
					templateId:会议卡片Id<br>
					checkDept:是否检测部门，非必须，默认为false，设定为true时，只要用户所在部门的其他人有创建会议纪要也算其已经完成该项工作；<br>
					适用场景：当需要某个部门组织周期性会议时，可对秘书或者助理进行会议纪要工作安排和提醒。
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>输入参数</td>
					<td width="35%">
						样例：<br>
						{
							"beginDate": 1556640000000,
							"endDate": 1559318399000,
							"accounts": [
								"user1",
								"user2"
								]
						}<br>
						beginDate:周期开始时间，时间戳，例：1556640000000,非必须;<br>
						endDate:周期结束时间，时间戳，例：1556640000000,非必须;<br>
						accounts:List&gt;String&lt;,用户账号列表,必须
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值对象格式</td>
					<td width="85%"><img id="viewSrc4_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc4_1','paramDiv4_1')" style="cursor: pointer"><br>
					<div id="paramDiv4_1" style="display:none">
					<table id="List_ViewTable4_1" class="tb_noborder">
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
						<tr>
							<td align="center">5</td>
							<td>accounts</td>
							<td>List&gt;String&lt;</td>
							<td>否</td>
							<td>返回的账号列表。与datas任选一项返回</td>
						</tr>													
					</table></div>
					</td>
				</tr>	
				<tr>
					<td class="td_normal_title" width=15%>返回结果datas的单条数据定义</td>
					<td width="85%"><img id="viewSrc4_2"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc4_2','paramDiv4_2')" style="cursor: pointer"><br>
					<div id="paramDiv4_2" style="display:none">
					<table id="List_ViewTable4_2" class="tb_noborder">
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
							<td>account</td>
							<td>字符串（String）</td>
							<td>是</td>
							<td>用户账号。datas子对象</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>key</td>
							<td>字符串（String）</td>
							<td>否</td>
							<td>去重字段，长度128字节内，周期内同一用户下去重</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>validDate</td>
							<td>Long</td>
							<td>否</td>
							<td>此任务截止日期，时间戳，例：1556640000000</td>
						</tr>	
						<tr>
							<td align="center">4</td>
							<td>unknown</td>
							<td>String</td>
							<td>是</td>
							<td>可自行添加第三方自定义的字段；可在任务配置中使用spel表达式获取</td>
						</tr>							
					</table>
					</div>
					</td>
				</tr>	
			</table>
		</td>
	</tr>	
<!-- 接口05 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;"><br>&nbsp;&nbsp;5&nbsp;&nbsp;检测需组织会议工作是否完成接口（通过检测会议纪要判断）<br><br>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>接口地址</td>
					<td width="85%">/api/km-imeeting/main/finishSummary?templateId=会议模板Id&checkDept=是否检测部门</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">
					对应上一个接口的完成事件，检测指定用户在周期内是否已经在指定模板下创建了会议纪要。<br>
 					接口参数含义和上个接口相同。
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>输入参数</td>
					<td width="35%">
						样例：<br>
						{
							"beginDate": 1556640000000,
							"endDate": 1559318399000,
							"datas": [
								{"account": "user1", "key": "123456", "validDate": 1559318399000},{"account": "user2", "key": "123457", "validDate": 1559318399000}
								]
							 }<br>
						beginDate:周期开始时间，时间戳，例：1556640000000,非必须;<br>
						endDate:周期结束时间，时间戳，例：1556640000000,非必须;<br>
						datas:数据列表,JSON对象,必须;
						--account:用户账号,String,必须<br>
						--key:去重参数，周期内同一用户下去重,String,非必须;<br>
						--validDate:任务截止日期，时间戳，例：1556640000000,Long,必须。
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值对象格式</td>
					<td width="85%"><img id="viewSrc5_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc5_1','paramDiv5_1')" style="cursor: pointer"><br>
					<div id="paramDiv5_1" style="display:none">
					<table id="List_ViewTable5_1" class="tb_noborder">
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
						<tr>
							<td align="center">5</td>
							<td>accounts</td>
							<td>List&gt;String&lt;</td>
							<td>否</td>
							<td>返回的账号列表。与datas任选一项返回</td>
						</tr>													
					</table></div>
					</td>
				</tr>	
				<tr>
					<td class="td_normal_title" width=15%>返回结果datas的单条数据定义</td>
					<td width="85%"><img id="viewSrc5_2"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc5_2','paramDiv5_2')" style="cursor: pointer"><br>
					<div id="paramDiv5_2" style="display:none">
					<table id="List_ViewTable5_2" class="tb_noborder">
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
							<td>account</td>
							<td>字符串（String）</td>
							<td>是</td>
							<td>用户账号。datas子对象</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>key</td>
							<td>字符串（String）</td>
							<td>否</td>
							<td>去重字段，长度128字节内; 请求参数中有此字段时，返回数据中也需要有此字段</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>finished</td>
							<td>Boolean</td>
							<td>否</td>
							<td>此任务是否结束，不传或传true时默认为结束;传false时任务状态更新为执行中</td>
						</tr>	
						<tr>
							<td align="center">4</td>
							<td>businessStatus</td>
							<td>String</td>
							<td>否</td>
							<td>业务状态，中文描述；如果需要追踪任务详细流程，可使用此字段</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>url</td>
							<td>String</td>
							<td>否</td>
							<td>任务跳转url，需要追踪任务详细流程时使用</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>unknown</td>
							<td>String</td>
							<td>是</td>
							<td>可自行添加第三方自定义的字段；可在任务配置中使用spel表达式获取</td>
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