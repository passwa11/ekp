<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|list.js");
</script>
<script type="text/javascript">
function expandMethod(domObj) {
	var thisObj = $(domObj);
	var isExpand = thisObj.attr("isExpanded");
	if(isExpand == null)
		isExpand = "1";
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
</script>


<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}接口说明</p>

<center>
<div style="width: 95%;text-align: left;">
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;<b>1、接口说明</b>（${base}/api/kms-common-hiezbase/kmsHiezKnowledgeController/getKnowledgeDocInfo?startDateTime=1630930649000&endDateTime=1632140249000&type=0）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">请求参数信说明</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数</b></td>
								<td align="center" width="10%"><b>参数位置</b></td>
								<td align="center" width="10%"><b>参数类型</b></td>
								<td align="center" width="15%"><b>是否必须</b></td>
								<td align="center" width="55%"><b>说明</b></td>
							</tr>
							<tr>
								<td>appId</td>
								<td>header</td>
								<td>string</td>
								<td>required</td>
								<td>应用ID</td>
							</tr>
							<tr>
								<td>token</td>
								<td>header</td>
								<td>string</td>
								<td>required</td>
								<td>应用Token</td>
							</tr>
							<tr>
								<td>expires</td>
								<td>header</td>
								<td>string</td>
								<td>required</td>
								<td>应用过期时间</td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>用户主键</td>
							</tr>
							<tr>
								<td>rowSize</td>
								<td>body</td>
								<td>int</td>
								<td></td>
								<td>如果为空默认为10条
									说明:type为多个则返回每种type对应的rowSize的集合</td>
							</tr>
							<tr>
								<td>statTypes</td>
								<td>body</td>
								<td>jsonArray</td>
								<td>required</td>
								<td>view: 阅读</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回参数说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;"><p>温馨提示返回字段参考如下:</p>
							{
							"code": "200",
							"message": "success",
							"data": [
							{
							"type": "view",
							"stats": [
							{
							"fdId": "17b9f3245c8b05f84fb93324017a415a",
							"fdModelName": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"
							},
							{
							"fdId": "17b9f3245c8b05f84fb93324017a415a",
							"fdModelName": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"
							}
							]
							}
							]
							}
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</center>

<%@ include file="/resource/jsp/view_down.jsp"%>