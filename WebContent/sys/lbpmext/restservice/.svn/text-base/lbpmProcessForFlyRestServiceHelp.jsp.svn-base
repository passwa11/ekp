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
	<!-- 接口01 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;根据流程实例ID获取所有流程任务
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">getTasksByProcessId()</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">根据流程实例ID获取所有流程任务(待处理：任务ID、任务状态(待处理)、工作项的生成时间、操作。已处理：任务ID、任务状态(已处理)、工作项的生成时间、处理时间（一个人处理多次，例如驳回，通过，沟通过，这些已处理的任务记录都需返回）)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数</td>
					<td>
						<table class="tb_normal" width=85%>

							<tr class="tr_normal_title">
								<td align="center" style="width: 30%"><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>是否必填</b></td>
							</tr>
							<tr>
								<td>lbpmProcessId</td>
								<td>流程实例ID(string)</td>
								<td>是</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求方式</td>
					<td>POST</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求格式</td>
					<td>application/form-data</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>响应说明</td>
					<td width="85%">
						响应返回json格式数据
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值说明</td>
					<td width="85%">{
						"success": true,
							"data": {
								"processTemplateId": "流程模板ID",
								"docStatus": "文档状态",
								"processId": "流程实例ID",
								"processTemplateName": "流程模板名称",
								"creatorId": "文档创建者",
								"startTime": 文档创建时间,
								"finishTime": 文档结束时间,
								"historyTask": [
									{
									"finishTime": "处理时间",
									"fdStatus": "任务状态(已处理)",
									"startTime": "工作项的生成时间",
									"taskId": "任务ID"
									},
									{
									"finishTime": "处理时间",
									"fdStatus": "任务状态(已处理)",
									"startTime": "工作项的生成时间",
									"taskId": "任务ID"
									}
								],
								"currentTask": [
									{
										"task": {
											"handler": [

												 {
													"operations": [
													{
													"handlerType": "处理人类型",
													"name": "通过",
													"type": "操作类型"
													}
													],
													"handlerId": "处理人ID"
												},
												{
													"operations": [
													{
													"handlerType": "处理人类型",
													"name": "通过",
													"type": "操作类型"
													}
													],
													"handlerId": "处理人ID"
												}
											],
											"fdStatus": "任务状态(待处理)",
											"fdActivityType": "工作项类型",
											"startTime": "工作项的生成时间",
											"taskId": "任务ID"
										}
									},
									{
										"task": {
											"handler": [
												{
												"handlerId": "处理人ID"
												}
											],
											"fdStatus": "任务状态(待处理)",
											"fdActivityType": "工作项类型",
											"startTime": "工作项的生成时间",
											"taskId": "任务ID"
										}
									}
								]
								}
							},
							"msg": null,
							"code": null
						}
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>成功响应报文</td>
					<td width="85%">{
						"success": true,
							"data": {
								"processTemplateId": "180414cf97c0cbb87f19577435bb9beb",
								"docStatus": "20",
								"processId": "1804570d0f60c4965de68084e50b88e0",
								"processTemplateName": "多个并行分支",
								"creatorId": "175d9e758b175f22cfdd3c3463f8cc83",
								"startTime": 1650432463000,
								"finishTime": null,
								"historyTask": [
								{
								"finishTime": 1650432485000,
								"fdStatus": "30",
								"startTime": 1650432463000,
								"taskId": "1804570d1f3b966f9c3b1794fad80f4d"
								},
								{
								"finishTime": 1650432519000,
								"fdStatus": "30",
								"startTime": 1650432485000,
								"taskId": "180457127b814bcd01f66f14c90a6291"
								}
								],
								"currentTask": [
								{
								"task": {
								"handler": [
								{
								"operations": [
								{
								"handlerType": "handler",
								"name": "通过",
								"type": "handler_pass"
								},
								{
								"handlerType": "handler",
								"name": "驳回",
								"type": "handler_refuse"
								}
								],
								"handlerId": "175d9e758b175f22cfdd3c3463f8cc83"
								},
								{
								"operations": [
								{
								"handlerType": "handler",
								"name": "通过",
								"type": "handler_pass"
								},
								{
								"handlerType": "handler",
								"name": "驳回",
								"type": "handler_refuse"
								}
								],
								"handlerId": "17586d39fc6c4962966d29149a88be35"
								}
								],
								"fdStatus": "20",
								"startTime": 1650432519000,
								"taskId": "1804571acfc944308aaa2f9418a92d08"
								}
								},
								{
								"task": {
								"handler": [
								{
								"operations": [
								{
								"handlerType": "handler",
								"name": "通过",
								"type": "handler_pass"
								},
								{
								"handlerType": "handler",
								"name": "补签",
								"type": "handler_additionSign"
								}
								],
								"handlerId": "17b1a5d8ed93ea7ee71fbe343d7a4425"
								}
								],
								"fdStatus": "20",
								"fdActivityType": "reviewWorkitem",
								"startTime": 1650432520000,
								"taskId": "1804571add5ee601bbcdee7492a98f41"
								}
								},
								{
								"task": {
								"handler": [
								{
								"handlerId": "175d9e758b175f22cfdd3c3463f8cc83"
								}
								],
								"fdStatus": "40",
								"fdActivityType": "reviewWorkitem",
								"startTime": 1650432520000,
								"taskId": "1804571ae269d82efdf36b4441284606"
								}
								}
								]
							},
							"msg": null,
							"code": null
						}
						</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>失败响应报文</td>
					<td width="85%">{
						"success": false,
						"data":null
						"msg": "查询当前流程任务失败，当前流程实例ID为空！！！",
						"code": null
						}
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.2&nbsp;&nbsp;审批流程（快速通过/驳回）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">approvalProcess()</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">审批流程（快速通过/驳回）</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数(JSONObject)</td>
					<td>
						<table class="tb_normal" width=85%>

							<tr class="tr_normal_title">
								<td align="center" style="width: 30%"><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>是否必填</b></td>
							</tr>
							<tr>
								<td>fdProcessId</td>
								<td>流程实例ID(string)</td>
								<td>是</td>
							</tr>
							<tr>
								<td>fdModelName</td>
								<td>主文档全类名(string)</td>
								<td>是</td>
							</tr>
							<tr>
								<td>fdActivityType</td>
								<td>任务/工作项类型(string)</td>
								<td>是</td>
							</tr>
							<tr>
								<td>taskId</td>
								<td>任务ID(string)</td>
								<td>是</td>
							</tr>
							<tr>
								<td>userId</td>
								<td>用户ID(string)</td>
								<td>是</td>
							</tr>
							<tr>
								<td>operationType</td>
								<td>操作类型(string)</td>
								<td>是</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>请求参数示例</td>
					<td width="85%">
						{
						"fdProcessId": "1804570d0f60c4965de68084e50b88e0",
						"fdModelName": "com.landray.kmss.km.review.model.KmReviewMain",
						"fdActivityType": "reviewWorkitem",
						"taskId": "1804571add5ee601bbcdee7492a98f41",
						"userId": "17b1a5d8ed93ea7ee71fbe343d7a4425",
						"operationType": "handler_pass"
						}
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
						{
						"success": true,
						"data": "",
						"msg": null,
						"code": null
						}
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>失败响应报文</td>
					<td width="85%">
						{
						"success": false,
						"data": null,
						"msg": "飞书调用流程审批接口失败:所有参数必填不能为空!!!",
						"code": null
						}
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>