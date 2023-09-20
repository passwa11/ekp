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
		<td width="85%">matchBudget(String jsonData)</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>功能描述</td>
		<td width="85%">预算匹配，入参需根据对应的费用预算控制维度传对应的参数。如：办公费是按照成本中心+预算科目维度，<br />
						则参数只需传成本中心编码+费用类型编码。根据财务制度权责发生制，获取的为当年、当季、当月预算额度</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回值</td>
		<td width="85%">匹配到的预算返回值（jsonObject）</td>
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
				<td>fdCompanyGroupCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>公司组编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>fdCompanyCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>公司编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>fdCostCenterGroupCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>成本中心组编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">4</td>
				<td>fdCostCenterCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>成本中心编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">5</td>
				<td>fdExpenseItemCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>费用类型编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">6</td>
				<td>fdPersonNo</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>人员编码（需和组织架构的编号保持一致）</td>
			</tr>
			<tr>
				<td align="center">6</td>
				<td>fdDeptNo</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>部门编码（需和组织架构的编号保持一致）</td>
			</tr>
			<tr>
				<td align="center">7</td>
				<td>fdProjectCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>项目编码（需和基础数据的编号保持一致）</td>
			</tr>
			<tr>
				<td align="center">8</td>
				<td>fdInnerOrderCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>内部订单编码（需和基础数据的编号保持一致，SAP使用）</td>
			</tr>
			<tr>
				<td align="center">9</td>
				<td>fdWbsCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>WBS号（需和基础数据的编号保持一致，SAP使用）</td>
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
				<td>调用接口是否成功，0为程序运行异常，errorMessage为失败原因,1为匹配预算异常，errorMessage为异常原因，2为成功</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>data</td>
				<td>字符串（JsonArray）</td>
				<td>无</td>
				<td>返回的预算信息json数组</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>fdSubject</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>预算对应的描述</td>
			</tr>									
			<tr>
				<td align="center">4</td>
				<td>fdBudgetId</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>预算ID</td>
			</tr>									
			<tr>
				<td align="center">5</td>
				<td>fdRule</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>刚柔控，1：刚控，2柔控</td>
			</tr>									
			<tr>
				<td align="center">6</td>
				<td>fdElasticPercent</td>
				<td>数字（Double）</td>
				<td>无</td>
				<td>预算可浮动比例</td>
			</tr>									
			<tr>
				<td align="center">7</td>
				<td>fdCanUseAmount</td>
				<td>数字（Double）</td>
				<td>无</td>
				<td>预算可使用金额</td>
			</tr>									
			<tr>
				<td align="center">8</td>
				<td>fdAlreadyUsedAmount</td>
				<td>数字（Double）</td>
				<td>无</td>
				<td>预算已使用金额</td>
			</tr>									
			<tr>
				<td align="center">9</td>
				<td>fdOccupyAmount</td>
				<td>数字（Double）</td>
				<td>无</td>
				<td>预算占用金额</td>
			</tr>									
			<tr>
				<td align="center">10</td>
				<td>fdTotalAmount</td>
				<td>数字（Double）</td>
				<td>无</td>
				<td>预算总额</td>
			</tr>									
			<tr>
				<td align="center">11</td>
				<td>fdAdjustAmount</td>
				<td>数字（Double）</td>
				<td>无</td>
				<td>预算调整额</td>
			</tr>									
			<tr>
				<td align="center">12</td>
				<td>fdCurrenyId</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>预算币种ID</td>
			</tr>									
			<tr>
				<td align="center">13</td>
				<td>fdSymbol</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>预算币种符号</td>
			</tr>									
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>预算匹配参数样例</td>
		<td width="35%">预算匹配参数是采用JSon格式传输，如：{"fdCostCenterCode": "ZJB","fdExpenseItemCode": "CLF"}。	
		</td>
	</tr>				
	<tr>
		<td class="td_normal_title" width=15%>预算匹配返回参数样例</td>
		<td width="35%">预算匹配返回结果是采用JSon格式传输，如：<br />
			{<br />
				"success": true,<br />
				"data": [{<br />
					&nbsp;&nbsp;"fdSubject": "2018/总经办/差旅费/",<br />
					&nbsp;&nbsp;"fdBudgetId": "166bf3e8990f96234972b304f9787499",<br />
					&nbsp;&nbsp;"fdRule": "1",<br />
					&nbsp;&nbsp;"fdElasticPercent": 10,<br />
					&nbsp;&nbsp;"fdCanUseAmount": 120000,<br />
					&nbsp;&nbsp;"fdAlreadyUsedAmount": 0,<br />
					&nbsp;&nbsp;"fdOccupyAmount": 0,<br />
					&nbsp;&nbsp;"fdInitAmount": 120000,<br />
					&nbsp;&nbsp;"fdTotalAmount": 120000,<br />
					&nbsp;&nbsp;"fdAdjustAmount": 0,<br />
					&nbsp;&nbsp;"fdCurrenyId": "165458d488fe137a6d818e24a1babb6a",<br />
					&nbsp;&nbsp;"fdSymbol": "CNY"<br />
				}, {<br />
					&nbsp;&nbsp;"fdSubject": "2018/总经办/差旅费/",<br />
					&nbsp;&nbsp;"fdBudgetId": "166bf3e89b5ab234f8243e547d4ad55b",<br />
					&nbsp;&nbsp;"fdRule": "1",<br />
					&nbsp;&nbsp;"fdElasticPercent": 10,<br />
					&nbsp;&nbsp;"fdCanUseAmount": 10000,<br />
					&nbsp;&nbsp;"fdAlreadyUsedAmount": 0,<br />
					&nbsp;&nbsp;"fdOccupyAmount": 0,<br />
					&nbsp;&nbsp;"fdInitAmount": 10000,<br />
					&nbsp;&nbsp;"fdTotalAmount": 10000,<br />
					&nbsp;&nbsp;"fdAdjustAmount": 0,<br />
					&nbsp;&nbsp;"fdCurrenyId": "165458d488fe137a6d818e24a1babb6a",<br />
					&nbsp;&nbsp;"fdSymbol": "CNY"<br />
				}]<br />
			}	<br />
			
			
			错误信息：<br />
			{<br />
				&nbsp;&nbsp;"success": false,<br />
				&nbsp;&nbsp;"errorMessage": "匹配预算信息出错，错误信息为：没找到费用类型"<br />
			}<br />
		</td>
	</tr>				
	<tr>
		<td class="td_normal_title" width=15%>服务接口</td>
		<td width="85%">addBudgetExecute(String jsonData)</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>功能描述</td>
		<td width="85%">预算执行数据</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回值</td>
		<td width="85%">执行结果（jsonObject）</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>接口入参JSONObject对应参数</td>
		<td width="85%"><img id="viewSrc3"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc3','paramDiv3')" style="cursor: pointer"><br>
		<div id="paramDiv3" style="display:none">
		<table id="List_ViewTable3" class="tb_noborder">
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
				<td>fdModelId</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>单据ID</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>fdModelName</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>单据modelName（用于区分不同的模块）</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>fdCompanyGroupCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>公司组编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">4</td>
				<td>fdCompanyCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>公司编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">5</td>
				<td>fdCostCenterGroupCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>成本中心组编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">6</td>
				<td>fdCostCenterCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>成本中心编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">7</td>
				<td>fdExpenseItemCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>费用类型编码（需和基础数据保持一致）</td>
			</tr>
			<tr>
				<td align="center">6</td>
				<td>fdPersonNo</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>人员编码（需和组织架构的编号保持一致）</td>
			</tr>
			<tr>
				<td align="center">6</td>
				<td>fdDeptNo</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>部门编码（需和组织架构的编号保持一致）</td>
			</tr>
			<tr>
				<td align="center">9</td>
				<td>fdProjectCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>项目编码（需和基础数据的编号保持一致）</td>
			</tr>
			<tr>
				<td align="center">10</td>
				<td>fdInnerOrderCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>内部订单编码（需和基础数据的编号保持一致，SAP使用）</td>
			</tr>
			<tr>
				<td align="center">11</td>
				<td>fdWbsCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>WBS号（需和基础数据的编号保持一致，SAP使用）</td>
			</tr>
			<tr>
				<td align="center">12</td>
				<td>fdType</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>执行数据类型：1，初始化；2、占用；3：已使用；4：调整</td>
			</tr>
			<tr>
				<td align="center">13</td>
				<td>fdDetailId</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>明细Id</td>
			</tr>
			<tr>
				<td align="center">14</td>
				<td>fdMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>金额</td>
			</tr>
		</table></div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回结果JSONObject说明</td>
		<td width="85%"><img id="viewSrc4"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc4','paramDiv4')" style="cursor: pointer"><br>
		<div id="paramDiv4" style="display:none">
		<table id="List_ViewTable4" class="tb_noborder">
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
				<td>success</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>调用接口是否成功，true为成功，false为失败，error为失败原因</td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>预算执行参数样例</td>
		<td width="35%">预算执行参数是采用JSon格式传输，如：{"fdModelId": "165c7f7aca6e0e0c56a70154d14b7523","fdModelName": "com.kmss.landray.fs.model.expense".....}。	
		</td>
	</tr>				
	<tr>
		<td class="td_normal_title" width=15%>服务接口</td>
		<td width="85%">deleteBudgetExecue(String jsonData)</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>功能描述</td>
		<td width="85%">删除预算执行数据</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回值</td>
		<td width="85%">执行结果（jsonObject）</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>接口入参JSONObject对应参数。该接口不会删除已使用执行记录</td>
		<td width="85%"><img id="viewSrc5"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc5','paramDiv5')" style="cursor: pointer"><br>
		<div id="paramDiv5" style="display:none">
		<table id="List_ViewTable5" class="tb_noborder">
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
				<td>fdModelId</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>单据ID</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>fdModelName</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>单据modelName（用于区分不同的模块）</td>
			</tr>
			<tr>
				<td align="center">5</td>
				<td>fdDetailId</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>明细Id</td>
			</tr>
		</table></div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回结果JSONObject说明</td>
		<td width="85%"><img id="viewSrc6"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc6','paramDiv6')" style="cursor: pointer"><br>
		<div id="paramDiv6" style="display:none">
		<table id="List_ViewTable6" class="tb_noborder">
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
				<td>success</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>调用接口是否成功，true为成功，false为失败，error为失败原因</td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>预算删除参数样例</td>
		<td width="35%">预算执行删除参数是采用JSon格式传输，如：{"fdModelId": "165c7f7aca6e0e0c56a70154d14b7523","fdModelName": "com.kmss.landray.fs.model.expense".....}。	
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>服务接口</td>
		<td width="85%">addBudgetData(String jsonData)</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>功能描述</td>
		<td width="85%">预算新增，第三方系统可调用该接口新增预算</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回值</td>
		<td width="85%">新增结果返回值（jsonObject）</td>
	</tr>		
	<tr>
		<td class="td_normal_title" width=15%>接口入参JSONArray对应参数。</td>
		<td width="85%"><img id="viewSrc7"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc7','paramDiv7')" style="cursor: pointer"><br>
		<div id="paramDiv7" style="display:none">
		<table id="List_ViewTable7" class="tb_noborder">
			<tr class="tr_listfirst">
				<sunbor:columnHead htmlTag="td">
					<td width="40pt">序号</td>
				    <td width="20%">属性名</td>
				    <td width="20%">类 型</td>
				    <td width="10%">缺省值</td>
				    <td width="50%">描 述</td>
				</sunbor:columnHead>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">1</td>
				<td>fdCompanyGroupCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>公司组编码（需和基础数据保持一致）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">2</td>
				<td>fdCompanyCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>公司编码（需和基础数据保持一致，必填）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">3</td>
				<td>fdCostCenterGroupCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>成本中心组编码（需和基础数据保持一致）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">4</td>
				<td>fdCostCenterCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>成本中心编码（需和基础数据保持一致）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">5</td>
				<td>fdBudgetItemCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>费用类型编码（需和基础数据保持一致）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">6</td>
				<td>fdPersonNo</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>人员编码（需和组织架构的编号保持一致）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">7</td>
				<td>fdDeptNo</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>部门编码（需和组织架构的编号保持一致）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">8</td>
				<td>fdProjectCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>项目编码（需和基础数据的编号保持一致）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">9</td>
				<td>fdInnerOrderCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>内部订单编码（需和基础数据的编号保持一致，SAP使用）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">10</td>
				<td>fdWbsCode</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>WBS号（需和基础数据的编号保持一致，SAP使用）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">11</td>
				<td>fdMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>金额（不区分期间，总包金额，可跨年）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">12</td>
				<td>fdRule</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>控制规则（1：刚控，2：柔控，3：弹性，总包金额下必填）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">13</td>
				<td>fdApply</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>运用规则（1：固定，2：滚动，总包金额下必填）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">14</td>
				<td>fdYearMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>年度金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">15</td>
				<td>fdFirstQuarterMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>第一季度金额</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">16</td>
				<td>fdSecondQuarterMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>第二季度金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">17</td>
				<td>fdThirdQuarterMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>第三季度金额</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">18</td>
				<td>fdFourthQuarterMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>第四季度金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">19</td>
				<td>fdJanMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>1月预算金额</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">20</td>
				<td>fdFebMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>2月预算金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">21</td>
				<td>fdMarMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>3月预算金额</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">22</td>
				<td>fdAprMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>4月预算金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">23</td>
				<td>fdMayMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>5月预算金额</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">24</td>
				<td>fdJunMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>6月预算金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">25</td>
				<td>fdJulMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>7月预算金额</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">26</td>
				<td>fdAugMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>8月预算金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">27</td>
				<td>fdSeptMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>9月预算金额</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">28</td>
				<td>fdOctMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>10月预算金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">29</td>
				<td>fdNovMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>11月预算金额</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">30</td>
				<td>fdDecMoney</td>
				<td>数字类型（Double）</td>
				<td>无</td>
				<td>12月预算金额</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">31</td>
				<td>fdYearRule</td>
				<td>字符串类型（String）</td>
				<td>无</td>
				<td>年度控制规则（1：刚控，2：柔控，3：弹性，有年度预算时必填）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">32</td>
				<td>fdQuarterRule</td>
				<td>字符串类型（String）</td>
				<td>无</td>
				<td>季度控制规则（1：刚控，2：柔控，3：弹性,有季度预算时必填）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">33</td>
				<td>fdMonthRule</td>
				<td>字符串类型（String）</td>
				<td>无</td>
				<td>月度控制规则（1：刚控，2：柔控，3：弹性，有月度预算时必填）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">34</td>
				<td>fdYearApply</td>
				<td>字符串类型（String）</td>
				<td>无</td>
				<td>年度度运用规则（1：固定，2：滚动,有年度预算时必填）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">35</td>
				<td>fdQuarterApply</td>
				<td>字符串类型（String）</td>
				<td>无</td>
				<td>季度度运用规则（1：固定，2：滚动,有季度预算时必填）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">36</td>
				<td>fdMonthApply</td>
				<td>字符串类型（String）</td>
				<td>无</td>
				<td>月度度运用规则（1：固定，2：滚动,有年度预算时必填）</td>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">37</td>
				<td>docCreator</td>
				<td>字符串类型（String）</td>
				<td>无</td>
				<td>创建者（人员ID或者人员编号）</td>
			</tr>
			<tr class="tr_listrow2">
				<td align="center">38</td>
				<td>fdSchemeCode</td>
				<td>字符串类型（String）</td>
				<td>无</td>
				<td>预算方案编码（必填）</td>
			</tr>
		</table></div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回结果JSONObject说明</td>
		<td width="85%"><img id="viewSrc8"
			src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
			onclick="expandMethod('viewSrc8','paramDiv8')" style="cursor: pointer"><br>
		<div id="paramDiv8" style="display:none">
		<table id="List_ViewTable8" class="tb_noborder" style="width: 100%;">
			<tr class="tr_listfirst">
				<sunbor:columnHead htmlTag="td">
					<td width="40pt">序号</td>
				    <td width="30%">属性名</td>
				    <td width="20%">类 型</td>
				    <td width="20%">缺省值</td>
				    <td width="30%">描 述</td>
				</sunbor:columnHead>
			</tr>
			<tr class="tr_listrow1">
				<td align="center">1</td>
				<td>success</td>
				<td>字符串（String）</td>
				<td>无</td>
				<td>调用接口是否成功，true为成功，false为失败，error为失败原因</td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>
