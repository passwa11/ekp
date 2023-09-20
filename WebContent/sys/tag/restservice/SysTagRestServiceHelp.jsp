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
						</td>
				</tr>
				<tr>
					<td style="cursor: pointer;" onclick="expandMethod(this);"><br />&nbsp;&nbsp;1.1&nbsp;&nbsp;
						查询分类接口 <img src="${KMSS_Parameter_StylePath}icons/expand.gif"
						border="0" align="bottom" /></td>
				</tr>
				<tr style="display: none;">
					<td>
						<table class="tb_normal" cellpadding="0" cellspacing="0"
							style="width: 85%;">
							<tr>
								<td class="td_normal_title" width="20%">接口url</td>
								<td>/api/sys-tag/sysTagRestService/getCategories</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">接口说明</td>
								<td>查询分类信息</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">对应方法</td>
								<td>TagGetResult getCategories(String type)</td>
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
											<td>type</td>
											<td>类型(string)<br>该值会影响返回信息的数据格式，详见返回信息
											</td>
											<td>非必填</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="20%">返回信息</td>
								<td>TagGetResult对象
									<table class="tb_normal" width=85%>

										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>
										<tr>
											<td>returnState</td>
											<td>状态(int)</td>
											<td>0代表未操作 1代表失败 2代表成功</td>
										</tr>
										<tr>
											<td>datas</td>
											<td>数据(String[])</td>
											<td style="width: 30%"><pre>当参数type为1时，字符串格式为：
{"text":"标签名","value":"标签id","isAutoFetch":"0"}
当参数type为2时，字符串格式为：
{"name":"标签名","value":"标签id","isAutoFetch":"0"}</pre></td>
										</tr>
										<String>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td style="cursor: pointer;" onclick="expandMethod(this);"><br />&nbsp;&nbsp;1.2&nbsp;&nbsp;
						查询标签接口 <img src="${KMSS_Parameter_StylePath}icons/expand.gif"
						border="0" align="bottom" /></td>
				</tr>
				<tr style="display: none">
					<td>
						<table class="tb_normal" cellpadding="0" cellspacing="0"
							style="width: 85%;">
							<tr>
								<td class="td_normal_title" width="20%">接口url</td>
								<td>/api/sys-tag/sysTagRestService/getTags</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">接口说明</td>
								<td>查询标签</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">对应方法</td>
								<td>TagGetResult getTags(@RequestBody TagGetTagsContext
									context)</td>
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
								<td class="td_normal_title" width="20%">请求参数</td>
								<td>TagGetTagsContext对象，必填
									<table class="tb_normal" width=85%>
										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>

										<tr>
											<td>paramId</td>
											<td>分类id(string)</td>
											<td>依赖type</td>
										</tr>
										<tr>
											<td>key</td>
											<td>搜索关键字(string)</td>
											<td>依赖type</td>
										</tr>
										<tr>
											<td>type</td>
											<td>类型(string)</td>
											<td>必填<br>getTag代表根据分类id获取标签，search代表根据key查询标签名
											</td>
										</tr>
										<tr>
											<td>loginName</td>
											<td>登录名(string)</td>
											<td>必填</td>
										</tr>

									</table>
								</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="20%">返回信息</td>
								<td>TagGetResult对象
									<table class="tb_normal" width=85%>

										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>
										<tr>
											<td>returnState</td>
											<td>状态(int)</td>
											<td>0代表未操作 1代表失败 2代表成功</td>
										</tr>
										<tr>
											<td>datas</td>
											<td>数据(String[])</td>
											<td style="width: 30%"><pre>{"text":"标签名","value":"标签id",name:"标签名",id:"标签id"}
</pre></td>
										</tr>
										<String>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td style="cursor: pointer;" onclick="expandMethod(this);"><br />&nbsp;&nbsp;1.3&nbsp;&nbsp;
						获取配置组接口 <img src="${KMSS_Parameter_StylePath}icons/expand.gif"
						border="0" align="bottom" /></td>
				</tr>
				<tr style="display: none">
					<td>
						<table class="tb_normal" cellpadding="0" cellspacing="0"
							style="width: 85%;">
							<tr>
								<td class="td_normal_title" width="20%">接口url</td>
								<td>/api/sys-tag/sysTagRestService/getGroups</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">接口说明</td>
								<td>获取配置组</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">对应方法</td>
								<td>TagResult getGroups(String modelName)</td>
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
											<td>modelName</td>
											<td>模块名(string)</td>
											<td>必填</td>
										</tr>

									</table>
								</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="20%">返回信息</td>
								<td>TagGetResult对象
									<table class="tb_normal" width=85%>

										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>
										<tr>
											<td>returnState</td>
											<td>状态(int)</td>
											<td>0代表未操作 1代表失败 2代表成功</td>
										</tr>
										<tr>
											<td>datas</td>
											<td>数据(String[])</td>
											<td style="width: 30%"><pre>{"text":"标签名",
"value":"标签id",
name:"标签名",id:"标签id"}</pre></td>
										</tr>
										<String>
									</table>
								</td>
							</tr>

						</table>

					</td>
				</tr>


				<tr>
					<td style="cursor: pointer;" onclick="expandMethod(this);"><br />&nbsp;&nbsp;1.4&nbsp;&nbsp;
						根据标签数组获取是否特殊接口 <img
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						align="bottom" /></td>
				</tr>
				<tr style="display: none">
					<td>
						<table class="tb_normal" cellpadding="0" cellspacing="0"
							style="width: 85%;">
							<tr>
								<td class="td_normal_title" width="20%">接口url</td>
								<td>/api/sys-tag/sysTagRestService/getIsSpecialByTags</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">接口说明</td>
								<td>根据标签数组获取是否特殊</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">对应方法</td>
								<td>TagResult getIsSpecialByTags(List<String>
									tags)</td>
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
								<td class="td_normal_title" width="20%">请求参数</td>
								<td>字符串数组，必填
									<table class="tb_normal" width=60%>
										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>样例</b></td>
										</tr>
										<tr>
											<td>标签名(string)</td>
											<td>['标签名1','标签名2','标签名3']</td>
										</tr>

									</table>
								</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="20%">返回信息</td>
								<td>TagResult对象
									<table class="tb_normal" width=85%>

										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>
										<tr>
											<td>returnState</td>
											<td>状态(int)</td>
											<td>0代表未操作 1代表失败 2代表成功</td>
										</tr>
										<tr>
											<td>message</td>
											<td>数据(String)</td>
											<td style="width: 30%"><pre>[{'标签名1':0},{'标签名2':1}] 
0代表非特殊标签，1代表特殊标签
</pre></td>
										</tr>
										<String>
									</table>
								</td>
							</tr>

						</table>
					</td>

				</tr>
				<tr>
					<td style="cursor: pointer;" onclick="expandMethod(this);"><br />&nbsp;&nbsp;1.5&nbsp;&nbsp;
						新增标签接口 <img src="${KMSS_Parameter_StylePath}icons/expand.gif"
						border="0" align="bottom" /></td>
				</tr>
				<tr style="display: none">
					<td>
						<table class="tb_normal" cellpadding="0" cellspacing="0"
							style="width: 85%;">
							<tr>
								<td class="td_normal_title" width="20%">接口url</td>
								<td>/api/sys-tag/sysTagRestService/addTags</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">接口说明</td>
								<td>内部接口，新增标签</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">对应方法</td>
								<td>TagResult addTags(TagAddContext context)</td>
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
								<td class="td_normal_title" width="20%">请求参数</td>
								<td>TagAddContext对象，必填
									<table class="tb_normal" width=60%>
										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>
										<tr>
											<td>appName</td>
											<td>服务器标识(string)</td>
											<td>必填，必须唯一</td>
										</tr>
										<tr>
											<td>tags</td>
											<td>新增内容(string)</td>
											<td><pre>字符串格式为：
[{docAlterTime:"",docCreateTime:"" ...]
各属性备注：
docAlterTime最后修改时间
docCreateTime创建时间
docCreatorName创建者名称
docStatus关联文档状态
docSubject关联文档标题
fdId主键
fdKey机制标志
fdModelId文档主键
fdModelName文档模块名
fdQueryCondition查询条件
fdUrl关联文档链接
fdTagNames标签信息</pre></td>
										</tr>

									</table>
								</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="20%">返回信息</td>
								<td>TagResult对象
									<table class="tb_normal" width=85%>

										<tr class="tr_normal_title">
											<td align="center" style="width: 30%"><b>字段名</b></td>
											<td align="center" style="width: 30%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
										</tr>
										<tr>
											<td>returnState</td>
											<td>状态(int)</td>
											<td>0代表未操作 1代表失败 2代表成功</td>
										</tr>
										<tr>
											<td>message</td>
											<td>错误信息(String)</td>
											<td style="width: 30%"></td>
										</tr>
										<String>
									</table>
								</td>
							</tr>

						</table>
					</td>
				</tr>
				
				
				
				
				
				
				
				<tr>
					<td style="cursor: pointer;" onclick="expandMethod(this);"><br />&nbsp;&nbsp;1.6&nbsp;&nbsp;
						内容标签同步接口<img
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						align="bottom" /></td>
				</tr>
				<tr style="display: none">
					<td>
						<table class="tb_normal" cellpadding="0" cellspacing="0"
							style="width: 85%;">
							<tr>
								<td class="td_normal_title" width="20%">接口url</td>
								<td>/api/sys-tag/sysTagRestService/getTagListbyBeginTime</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">接口说明</td>
								<td>根据请求的时间获取该时间后修改过的所有标签信息</td>
							</tr>
							<tr>
								<td class="td_normal_title" width="20%">对应方法</td>
								<td>TagDataResult getTagListbyBeginTime(Map<String, Object> args)</td>
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
								<td class="td_normal_title" width="20%">请求参数</td>
								<td>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" style="width: 10%"><b>参数名</b></td>
											<td align="center" style="width: 10%"><b>类别</b></td>
											<td align="center" style="width: 12%"><b>是否必填</b></td>
											<td align="center" style="width: 13%"><b>说明</b></td>
											<td align="center" style="width: 25%"><b>限制</b></td>
											<td align="center" style="width: 30%"><b>参数样例</b></td>
										</tr>
										<tr>
											<td>beginTime</td>
											<td>Long</td>
											<td>是</td>
											<td>同步的起始时间</td>
											<td>beginTime为时间戳且不能大于当前时间</td>
											<td rowspan="3">
											&nbsp;&nbsp;{	<br/>
											&nbsp;&nbsp;&nbsp;&nbsp;"beginTime": 1566379200000,<br/>
											&nbsp;&nbsp;&nbsp;&nbsp;"page": 1,<br/>
											&nbsp;&nbsp;&nbsp;&nbsp;"pageSize":1000<br/>
											&nbsp;&nbsp;}
											</td>
										</tr>
										<tr>
											<td>page</td>
											<td>Integer</td>
											<td>是</td>
											<td>当前页</td>
											<td>page必须大于0</td>
										</tr>
										<tr>
											<td>pageSize</td>
											<td>Integer</td>
											<td>是</td>
											<td>每页的大小</td>
											<td>pageSize必须大于0</td>
										</tr>
									</table>
								</td>
							</tr>

							<tr>
								<td class="td_normal_title" width="20%">返回信息</td>
								<td>&nbsp;&nbsp;返回 TagDataResult 对象
									<table class="tb_normal" width=85%>

										<tr class="tr_normal_title">
											<td align="center" style="width: 10%"><b>字段名</b></td>
											<td align="center" style="width: 20%"><b>说明</b></td>
											<td align="center" style="width: 30%"><b>取值说明</b></td>
											<td align="center" style="width: 40%%"><b>返回样例</b></td>
										</tr>
										<tr>
											<td>success</td>
											<td>是否成功(boolean)</td>
											<td>true 代表失败 false 代表成功</td>
											<td rowspan="4">
												{<br/>
												&nbsp;&nbsp;"success": true,<br/>
												&nbsp;&nbsp;"code": "2",<br/>
												&nbsp;&nbsp;"msg": "操作成功",<br/>
												&nbsp;&nbsp;"data":	{<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;"offset": 0,<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;"pageSize": 10,<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;"totalSize": 128,<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;"content": [<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"fdModelId": "XXXX",<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"fdModuleName": " "XXXX",<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; .......<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
												&nbsp;&nbsp;&nbsp;&nbsp;]<br/>
												&nbsp;&nbsp;}<br/>
												&nbsp;&nbsp;}<br/>
											</td>
										</tr>
										


										<tr>
											<td>code</td>
											<td>状态(int)</td>
											<td>1 代表操作失败 2 代表操作成功</td>
										</tr>
										<tr>
											<td>msg</td>
											<td>提示信息(String)</td>
											<td>操作成功或操作失败的提示信息</td>
										</tr>
										<tr>
											<td>datas</td>
											<td>返回数据(Object)</td>
											<td>返回Object类型数据</td>
										</tr>
										<String>
									</table>
								</td>
							</tr>

						</table>
					</td>

				</tr>
			</table>
		</center>

	</template:replace>
</template:include>


