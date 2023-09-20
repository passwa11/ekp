<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">

	<template:replace name="title">
${HtmlParam.name }接口</template:replace>

	<template:replace name="body">

		<script>
			function expandMethod(thisObj) {

				var isExpand = thisObj.getAttribute("isExpanded");
				if (isExpand == null)
					isExpand = "0";
				var trObj = thisObj.parentNode;
				trObj = trObj.nextSibling;
				while (trObj != null) {
					if (trObj != null && trObj.tagName == "TR") {
						break;
					}
					trObj = trObj.nextSibling;
				}
				var imgObj = thisObj.getElementsByTagName("IMG")[0];
				if (trObj.tagName.toLowerCase() == "tr") {
					if (isExpand == "0") {
						trObj.style.display = "";
						thisObj.setAttribute("isExpanded", "1");
						imgObj
								.setAttribute("src",
										"${KMSS_Parameter_StylePath}icons/collapse.gif");
					} else {
						trObj.style.display = "none";
						thisObj.setAttribute("isExpanded", "0");
						imgObj.setAttribute("src",
								"${KMSS_Parameter_StylePath}icons/expand.gif");
					}
				}
			}
		</script>

		<style>
.txttitle {
	padding-top: 10px;
	color: #3e9ece !important;
}

table.tb_normal {
	margin: 10px !important;
}
</style>

		<ui:toolbar layout="sys.ui.toolbar.float">
			<ui:button onclick="Com_CloseWindow();"
				text="${lfn:message('button.close') }"></ui:button>
		</ui:toolbar>

		<p class="txttitle">${HtmlParam.name }接口</p>

		<center>
			<br />

			<table border="0" width="95%">
				<tr>
					<td><b>1、接口说明</b> <br> <br>
						<p>&nbsp;&nbsp;检查是否已关注某用户和关注某用户接口</p></td>
				</tr>
				<tr>
					<td style="cursor: pointer;" onclick="expandMethod(this);"><br />&nbsp;&nbsp;1.1&nbsp;&nbsp;
						检查是否已关注某用户接口 <img
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						align="bottom" /></td>
				</tr>
				<tr style="display: none;">
					<td>
						<table class="tb_normal" cellpadding="0" cellspacing="0"
							style="width: 85%;">
							<tr>
								<td class="td_normal_title" width="20%">接口url</td>
								<td>/api/sys-fans/sysFansRestService/isFollowPerson</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">接口说明</td>
								<td>检查是否已关注某用户</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">对应方法</td>
								<td>boolean isFollowPerson(String userId,String
									targetUserId)</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">请求方式</td>
								<td>POST</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">请求格式</td>
								<td>application/x-www-form-urlencoded</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">请求参数</td>
								<td>
									<table class="tb_normal" width=85%>

										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>
										<tr>
											<td>userId</td>
											<td>关注者登录名(string)</td>
											<td>必填</td>
										</tr>
										<tr>
											<td>targetUserId</td>
											<td>被关注者登录名(string)</td>
											<td>必填</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="20%">返回信息</td>
								<td>已关注返回true，否则为false</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td style="cursor: pointer;" onclick="expandMethod(this);"><br />&nbsp;&nbsp;1.2&nbsp;&nbsp;
						关注某用户接口 <img src="${KMSS_Parameter_StylePath}icons/expand.gif"
						border="0" align="bottom" /></td>
				</tr>
				<tr style="display: none">
					<td>
						<table class="tb_normal" cellpadding="0" cellspacing="0"
							style="width: 85%;">
							<tr>
								<td class="td_normal_title" width="20%">接口url</td>
								<td>/api/sys-fans/sysFansRestService/followPerson</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">接口说明</td>
								<td>关注某用户</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">对应方法</td>
								<td>boolean followPerson(String userId,String targetUserId)
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">请求方式</td>
								<td>POST</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">请求格式</td>
								<td>application/x-www-form-urlencoded</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">请求参数</td>
								<td>
									<table class="tb_normal" width=85%>
										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>

										<tr>
											<td>userId</td>
											<td>关注者登录名(string)</td>
											<td>必填</td>
										</tr>
										<tr>
											<td>targetUserId</td>
											<td>被关注者登录名(string)</td>
											<td>必填</td>
										</tr>

									</table>
								</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="20%">返回信息</td>
								<td>关注成功返回true，否则为false</td>
							</tr>
						</table>
					</td>
				</tr>

			</table>

		</center>

	</template:replace>
</template:include>


