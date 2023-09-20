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
		<td width="85%">outsideBaseSupplier(String jsonData)</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>功能描述</td>
		<td width="85%">导入客商</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>返回值</td>
		<td width="85%">添加结果（jsonObject）</td>
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
				    <td width="30%">是否为空</td>
				    <td width="30%">描 述</td>
				</sunbor:columnHead>
			</tr>
			<tr>
				<td align="center">1</td>
				<td>fdCompanyCode</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>所属公司编号（需和基础数据保持一致），多个公司编号用;分隔</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>fdCode</td>
				<td>字符串（String）</td>
				<td>否</td>
				<td>编码</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>fdName</td>
				<td>字符串（String）</td>
				<td>否</td>
				<td>名称</td>
			</tr>
			<tr>
				<td align="center">4</td>
				<td>fdAbbreviation</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>简称</td>
			</tr>
			<tr>
				<td align="center">5</td>
				<td>fdTaxNo</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>纳税人识别号</td>
			</tr>
			<tr>
				<td align="center">6</td>
				<td>fdErpNo</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>ERP号</td>
			</tr>
			<tr>
				<td align="center">7</td>
				<td>fdCreditCode</td>
				<td>字符串（String）</td>
				<td>否</td>
				<td>统一社会信用代码</td>
			</tr>
			<tr>
				<td align="center">8</td>
				<td>fdCodeValidityPeriod</td>
				<td>字符串（String）</td>
				<td>否</td>
				<td>信用证有效截止日期，格式2022-01-01</td>
			</tr>
			<tr>
				<td align="center">9</td>
				<td>fdIndustry</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>所属行业</td>
			</tr>
			<tr>
				<td align="center">10</td>
				<td>fdLegalPerson</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>法人代表</td>
			</tr>
			<tr>
				<td align="center">11</td>
				<td>fdRegistCapital</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>注册资金</td>
			</tr>
			<tr>
				<td align="center">12</td>
				<td>fdEstablishDate</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>成立日期，格式2022-01-01</td>
			</tr>
			<tr>
				<td align="center">13</td>
				<td>fdAddress</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>企业地址</td>
			</tr>
			<tr>
				<td align="center">14</td>
				<td>fdUrl</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>企业网址</td>
			</tr>
			<tr>
				<td align="center">15</td>
				<td>fdBusinessScope</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>经营范围</td>
			</tr>
			<tr>
				<td align="center">16</td>
				<td>fdDesc</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>企业简介</td>
			</tr>
			<tr>
				<td align="center">17</td>
				<td>fdIsAvailable</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>是否有效（0-否，1-是）</td>
			</tr>
			<tr>
				<td colspan="2" align="left">accountDetail—银行账号信息</td>
				<td colspan="3"></td>
			</tr>
			<tr>
				<td align="center">18</td>
				<td>fdSupplierArea</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>所在区域（0-境内，1-境外）</td>
			</tr>
			<tr>
				<td align="center">19</td>
				<td>fdAccountName</td>
				<td>字符串（String）</td>
				<td>有银行账号时不可为空</td>
				<td>收款账户名</td>
			</tr>
			<tr>
				<td align="center">20</td>
				<td>fdBankName</td>
				<td>字符串（String）</td>
				<td>有银行账号时不可为空</td>
				<td>开户行</td>
			</tr>
			<tr>
				<td align="center">21</td>
				<td>fdBankNo</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>联行号</td>
			</tr>
			<tr>
				<td align="center">22</td>
				<td>fdBankAccount</td>
				<td>字符串（String）</td>
				<td>有银行账号时不可为空</td>
				<td>账号</td>
			</tr>
			<tr>
				<td align="center">23</td>
				<td>fdBankSwift</td>
				<td>字符串（String）</td>
				<td>境外账号时不可为空</td>
				<td>收款银行swift号</td>
			</tr>
			<tr>
				<td align="center">24</td>
				<td>fdReceiveCompany</td>
				<td>字符串（String）</td>
				<td>境外账号时不可为空</td>
				<td>收款公司名称</td>
			</tr>
			<tr>
				<td align="center">25</td>
				<td>fdReceiveBankName</td>
				<td>字符串（String）</td>
				<td>境外账号时不可为空</td>
				<td>收款银行名称（境外）</td>
			</tr>
			<tr>
				<td align="center">26</td>
				<td>fdReceiveBankAddress</td>
				<td>字符串（String）</td>
				<td>境外账号时不可为空</td>
				<td>收款银行地址（境外）</td>
			</tr>
			<tr>
				<td align="center">27</td>
				<td>fdInfo</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>其他信息</td>
			</tr>
			<tr>
				<td colspan="2" align="left">contactDetail—联系人信息</td>
				<td colspan="3"></td>
			</tr>
			<tr>
				<td align="center">28</td>
				<td>fdName</td>
				<td>字符串（String）</td>
				<td>有联系人时不可为空</td>
				<td>姓名</td>
			</tr>
			<tr>
				<td align="center">29</td>
				<td>fdPosition</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>职务</td>
			</tr>
			<tr>
				<td align="center">30</td>
				<td>fdPhone</td>
				<td>字符串（String）</td>
				<td>有联系人时不可为空</td>
				<td>联系电话</td>
			</tr>
			<tr>
				<td align="center">31</td>
				<td>fdEmail</td>
				<td>字符串（String）</td>
				<td>有联系人时不可为空</td>
				<td>电子邮箱</td>
			</tr>
			<tr>
				<td align="center">32</td>
				<td>fdAddress</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>联系地址</td>
			</tr>
			<tr>
				<td align="center">33</td>
				<td>fdRemarks</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>备注</td>
			</tr>
			<tr>
				<td align="center">34</td>
				<td>fdIsfirst</td>
				<td>字符串（String）</td>
				<td>是</td>
				<td>第一联系人（0-否，1-是）</td>
			</tr>
		</table>
		</div>
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
		<td width="35%">参数是采用JSon格式传输，如：[{
			"fdCompanyCode": "1001;1002",
			"fdCode": "001",
			"fdName": "深圳市蓝凌软件股份有限公司",
			"fdAbbreviation": "蓝凌",
			"fdTaxNo": "111",
			"fdErpNo": "222",
			"fdCreditCode": "333",
			"fdCodeValidityPeriod": "2022-01-01",
			"fdIndustry": "软件",
			"fdLegalPerson": "张三",
			"fdRegistCapital": "5000万",
			"fdEstablishDate": "2022-01-01",
			"fdAddress": "广东深圳",
			"fdUrl": "www.baidu.com",
			"fdBusinessScope": "软件、互联网",
			"fdDesc": "公司坐落深圳",
			"accountDetail": [
			{
			"fdSupplierArea": "0",
			"fdAccountName": "张三",
			"fdBankName": "工商银行",
			"fdBankNo": "111",
			"fdBankAccount": "123456",
			"fdBankSwift": "",
			"fdReceiveCompany": "",
			"fdReceiveBankName": "",
			"fdReceiveBankAddress": "",
			"fdInfo": ""
			}, {
			"fdSupplierArea": "1",
			"fdAccountName": "李四",
			"fdBankName": "招商银行",
			"fdBankNo": "111",
			"fdBankAccount": "123456",
			"fdBankSwift": "swift",
			"fdReceiveCompany": "美国公司",
			"fdReceiveBankName": "美国银行",
			"fdReceiveBankAddress": "美国纽约",
			"fdInfo": "其他"
			}
			],
			"contactDetail": [
			{
			"fdName": "张三",
			"fdPosition": "总经理",
			"fdPhone": "13333333333",
			"fdEmail": "123@qq.com",
			"fdAddress": "广东深圳",
			"fdRemarks": "无",
			"fdIsfirst": "1"
			}, {
			"fdName": "李四",
			"fdPosition": "",
			"fdPhone": "15555555555",
			"fdEmail": "123@126.com",
			"fdAddress": "",
			"fdRemarks": "",
			"fdIsfirst": "0"
			}
			]
			},{
			"fdCompanyCode": "1001",
			"fdCode": "002",
			"fdName": "深圳市蓝凌软件股份有限公司",
			"fdAbbreviation": "蓝凌",
			"fdTaxNo": "111",
			"fdErpNo": "222",
			"fdCreditCode": "333",
			"fdCodeValidityPeriod": "2022-01-01",
			"fdIndustry": "软件",
			"fdLegalPerson": "张三",
			"fdRegistCapital": "5000万",
			"fdEstablishDate": "2022-01-01",
			"fdAddress": "广东深圳",
			"fdUrl": "www.baidu.com",
			"fdBusinessScope": "软件、互联网",
			"fdDesc": "公司坐落深圳",
			"accountDetail": [
			{
			"fdSupplierArea": "0",
			"fdAccountName": "张三",
			"fdBankName": "工商银行",
			"fdBankNo": "111",
			"fdBankAccount": "123456",
			"fdBankSwift": "",
			"fdReceiveCompany": "",
			"fdReceiveBankName": "",
			"fdReceiveBankAddress": "",
			"fdInfo": ""
			}
			],
			"contactDetail": [
			{
			"fdName": "张三",
			"fdPosition": "总经理",
			"fdPhone": "13333333333",
			"fdEmail": "123@qq.com",
			"fdAddress": "广东深圳",
			"fdRemarks": "无",
			"fdIsfirst": "1"
			}
			]
			}]
		</td>
	</tr>				
	<tr>
		<td class="td_normal_title" width=15%>返回参数样例</td>
		<td width="35%">返回结果是采用JSon格式传输，如：<br />
			成功：<br />
			{<br />
				"result": "0",<br />
				"errormsg": ""<br />
			}	<br />

			失败：<br />
			{<br />
				"result": "1",<br />
				"errormsg": "名称不能为空！"<br />
			}<br />
		</td>
	</tr>				
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>
