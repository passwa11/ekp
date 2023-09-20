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
								<td>startDateTime</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>开始时间</td>
							</tr>
							<tr>
								<td>endDateTime</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>结束时间</td>
							</tr>
							<tr>
								<td>type</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>同步类型：0：全量（startDateTime和endDateTime必填），1：增量，startDateTime必填</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回参数说明</td>
					<td style="padding: 0px;">
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数</b></td>
								<td align="center" width="10%"><b>参数位置</b></td>
								<td align="center" width="10%"><b>参数类型</b></td>
								<td align="center" width="15%"><b>是否必须</b></td>
								<td align="center" width="55%"><b>说明</b></td>
							</tr>

							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>文档主键</td>
							</tr>
							<tr>
								<td>docSubject</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>标题</td>
							</tr>
							<tr>
								<td>fdDescription</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>摘要</td>
							</tr>
							<tr>
								<td>fdModelName</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>模块名</td>
							</tr>
							<tr>
								<td>readNum</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>阅读数</td>
							</tr>
							<tr>
								<td>publishTime</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>发布时间</td>
							</tr>
							<tr>
								<td>fdLastModifyTime</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>最后修改时间</td>
							</tr>
							<tr>
								<td>fdCategorys</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>分类</td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>分类id</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>分类名称</td>
							</tr>
							<tr>
								<td>fdCategorys</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>分类</td>
							</tr>
							<tr>
								<td>fdAuthors</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>作者信息</td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>作者id（当为空时间，则为外部作者）</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>作者名称</td>
							</tr>
							<tr>
								<td>fdAuthors</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>作者信息</td>
							</tr>

							<tr>
								<td>fdCreator</td>
								<td>body</td>
								<td>jsonObject</td>
								<td></td>
								<td>创建者信息</td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>创建者id</td>
							</tr>
							<tr>
								<td>fdLoginName</td>
								<td>body</td>
								<td>string</td>
								<td>required</td>
								<td>登录名称</td>
							</tr>
							<tr>
								<td>posts</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>岗位信息</td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>岗位id</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>岗位名称</td>
							</tr>
							<tr>
								<td>posts</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>岗位信息</td>
							</tr>
							<tr>
								<td>department</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>部门信息</td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>部门id</td>
							</tr>
							<tr>
								<td>depName</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>部门名称</td>
							</tr>
							<tr>
								<td>department</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>部门信息</td>
							</tr>
							<tr>
								<td>fdCreator</td>
								<td>body</td>
								<td>jsonObject</td>
								<td></td>
								<td>创建者信息</td>
							</tr>


							<tr>
								<td>fdAttmains</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>附件信息</td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>附件id</td>
							</tr>
							<tr>
								<td>fdFName</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>附件名称</td>
							</tr>
							<tr>
								<td>downloadUrl</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>附件下载地址</td>
							</tr>
							<tr>
								<td>fdAttmains</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>附件信息</td>
							</tr>
							<tr>
								<td>fdTags</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>标签信息</td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>标签id</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>body</td>
								<td>string</td>
								<td></td>
								<td>标签名称</td>
							</tr>
							<tr>
								<td>fdTags</td>
								<td>body</td>
								<td>jsonArray</td>
								<td></td>
								<td>标签信息</td>
							</tr>

						</table>
						<div style="margin: 8px;"><p>温馨提示返回字段参考如下:</p>{
							"code": "200",
							"message": "success",
							"data": [{
							"fdId": "主键ID",
							"docSubject": "标题",
							"fdDescription": "摘要",
							"docContent": "正文",
							"fdModelName": "模块名",
							"fdLastModifyTime": "最后修改时间",
							"readNum": "阅读数",
							"publishTime": "发布时间",
							"fdCategorys": [{
							"fdId": "分类ID",
							"fdName": "分类名称"
							}],
							"fdAuthors": [{
							"fdId": "用户ID",
							"fdName": "用户名"
							}],
							"fdCreator": {
							"fdId": "用户ID",
							"fdLoginName": "登陆名",
							"fdName": "用户名",
							"department": {
							"fdId": "部门ID",
							"depName": "部门名称"
							},
							"posts": [{
							"fdId": "岗位Id",
							"fdName": "岗位名称"
							}]
							},
							"fdAttmains": [{
							"fdId": "ID",
							"fdFName": "文件名.docx",
							"downloadUrl": "附件下载地址"
							}],
							"fdTags": [{
							"fdId": "标签ID",
							"fdName": "标签名称"
							}],
							"readNum": 4,
							"publishTime": "2021-09-01 10:31"
							}]

							}</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</center>

<%@ include file="/resource/jsp/view_down.jsp"%>