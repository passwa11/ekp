<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<head>
	<title>${HtmlParam.name}</title>
</head>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("optbar.js|list.js");
</script>
<script>
	function expandMethod(thisObj) {
		var isExpand=thisObj.getAttribute("isExpanded");
		if(isExpand==null)
			isExpand="0";
		var trObj=thisObj.parentNode;
		trObj=trObj.nextSibling;
		while(trObj!=null){
			if(trObj!=null && trObj.tagName=="TR"){
				break;
			}
			trObj = trObj.nextSibling;
		}
		var imgObj=thisObj.getElementsByTagName("IMG")[0];
		if(trObj.tagName.toLowerCase()=="tr"){
			if(isExpand=="0"){
				trObj.style.display="";
				thisObj.setAttribute("isExpanded","1");
				imgObj.setAttribute("src","${KMSS_Parameter_StylePath}icons/collapse.gif");
			}else{
				trObj.style.display="none";
				thisObj.setAttribute("isExpanded","0");
				imgObj.setAttribute("src","${KMSS_Parameter_StylePath}icons/expand.gif");
			}
		}
	}
</script>


<div id="optBarDiv"><input type="button"
						   value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}</p>

<center>
	<div style="width: 95%;text-align: left;">
		用于获取流程管理审批实例相关数据
	</div>
	<br/>

	<table border="0" width="95%">
		<tr>
			<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;获取实例列表
				<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
		</tr>
		<tr style=" display:none;">
			<td>
				<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
					<tr>
						<td class="td_normal_title" width="20%">接口url</td>
						<td style="">
							<div>
								/api/km-review/instance/list
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">接口说明</td>
						<td style="">
							<div>
								获取所有实例数据列表
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求方式</td>
						<td style="">
							<div>
								POST
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求格式</td>
						<td style="">
							<div>
								application/json
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求示例</td>
						<td style="">
							<div>
<pre>{
    "pageNo": 1,
    "rowSize": 10,
    "docCreateTime": {
        "start": 1600224757617,
        "end": 1602309221499
    },
    "fdTemplateId": "17433b9ebc7c1a30e2c49764fc380fec",
    "docCreator": "175110cca34aa96b5fdcea94d1899733",
    "fdCurrentHandler": "1735c03b06602f7a7c13827443ea412e;1183b0b84ee4f581bba001c47a78b2d9",
    "fdAlreadyHandler": "1183b0b84ee4f581bba001c47a78b2d9"
}</pre>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求说明</td>
						<td style="">
							<table class="tb_normal" width=100%>
								<tr class="tr_normal_title">
									<td align="center" style="width: 30%"><b>字段名</b></td>
									<td align="center" style="width: 10%"><b>类型</b></td>
									<td align="center" style="width: 30%"><b>说明</b></td>
									<td align="center" style="width: 30%"><b>取值说明</b></td>
								</tr>
								<tr>
									<td>pageNo</td>
									<td>Integer</td>
									<td>当前页数</td>
									<td>非必填</td>
								</tr>
								<tr>
									<td>rowSize</td>
									<td>Integer</td>
									<td>分页大小</td>
									<td>非必填</td>
								</tr>
								<tr>
									<td>docCreateTime</td>
									<td>Long</td>
									<td>创建时间</td>
									<td>时间戳 非必填</td>
								</tr>
								<tr>
									<td>fdTemplateId</td>
									<td>String</td>
									<td>模板ID</td>
									<td>非必填</td>
								</tr>
								<tr>
									<td>docCreator</td>
									<td>String</td>
									<td>创建人</td>
									<td>创建人ID</td>
								</tr>
								<tr>
									<td>fdCurrentHandler</td>
									<td>String</td>
									<td>当前处理人</td>
									<td>非必填 可填多个，用分号";"分割，查询逻辑为"或"</td>
								</tr>
								<tr>
									<td>fdAlreadyHandler</td>
									<td>String</td>
									<td>已处理人</td>
									<td>非必填</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">返回格式</td>
						<td style="">
							<div>
								application/json;charset=UTF-8
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">返回示例</td>
						<td style="">
							<div>
<pre>{
    "pageNo": 1,
    "rowSize": 10,
    "totalPage": 1,
    "totalSize": 1,
    "datas": [
        {
            "fdId": "17494d3aec458c533fcc8df4bcfac1a7",
            "docSubject": "提交返回测试1",
            "fdNumber": "20200916001",
            "docCreateTime": 1600224757617,
            "docCreator": {
                "fdId": "1183b0b84ee4f581bba001c47a78b2d9",
                "fdName": "管理员"
            },
            "docPublishTime": null,
            "docStatus": "20",
            "wfNode": "审批节点",
            "wfHandler": [
                {
                    "fdId": "1183b0b84ee4f581bba001c47a78b2d9",
                    "fdName": "管理员"
                },
                {
                    "fdId": "1735c03b06602f7a7c13827443ea412e",
                    "fdName": "test"
                }
            ]
        }
    ]
}</pre>
						</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">返回说明</td>
						<td style="">
							<table class="tb_normal" width=100%>
								<tr class="tr_normal_title">
									<td align="center" style="width: 30%"><b>字段名</b></td>
									<td align="center" style="width: 10%"><b>类型</b></td>
									<td align="center" style="width: 30%"><b>说明</b></td>
									<td align="center" style="width: 30%"><b>取值说明</b></td>
								</tr>
								<tr>
									<td>fdId</td>
									<td>String</td>
									<td>实例ID</td>
									<td></td>
								</tr>
								<tr>
									<td>docSubject</td>
									<td>String</td>
									<td>标题</td>
									<td></td>
								</tr>
								<tr>
									<td>fdNumber</td>
									<td>String</td>
									<td>编号</td>
									<td></td>
								</tr>
								<tr>
									<td>docCreateTime</td>
									<td>Long</td>
									<td>创建时间</td>
									<td>时间戳</td>
								</tr>
								<tr>
									<td>docCreator</td>
									<td>ID-Name对象</td>
									<td>创建人</td>
									<td>
<pre>fdId:创建人ID
fdName:创建人名称</pre>
									</td>
								</tr>
								<tr>
									<td>docPublishTime</td>
									<td>Long</td>
									<td>结束时间</td>
									<td>时间戳</td>
								</tr>
								<tr>
									<td>docStatus</td>
									<td>String</td>
									<td>文档状态</td>
									<td>
<pre>
00:废弃
10:草稿
11:驳回
20:待审
30:发布
40:取消
</pre>
									</td>
								</tr>
								<tr>
									<td>wfNode</td>
									<td>String</td>
									<td>当前节点</td>
									<td></td>
								</tr>
								<tr>
									<td>wfHandler</td>
									<td>ID-Name对象列表</td>
									<td>当前处理人</td>
									<td>组织架构</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;获取实例详情
				<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
		</tr>
		<tr style=" display:none;">
			<td>
				<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
					<tr>
						<td class="td_normal_title" width="20%">接口url</td>
						<td style="">
							<div>
							/api/km-review/instance/get
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">接口说明</td>
						<td style="">
							<div>
							获取所有模板数据
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求方式</td>
						<td style="">
							<div>
							POST
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求格式</td>
						<td style="">
							<div>
								无需请求体
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求示例</td>
						<td style="">
							<div>
								/api/km-review/instance/get?fdId=17494d3aec458c533fcc8df4bcfac1a7
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求说明</td>
						<td style="">
							<table class="tb_normal" width=100%>
								<tr class="tr_normal_title">
									<td align="center" style="width: 30%"><b>字段名</b></td>
									<td align="center" style="width: 10%"><b>类型</b></td>
									<td align="center" style="width: 30%"><b>说明</b></td>
									<td align="center" style="width: 30%"><b>取值说明</b></td>
								</tr>
								<tr>
									<td>fdId</td>
									<td>String</td>
									<td>实例ID</td>
									<td>必填</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">返回格式</td>
						<td style="">
							<div>
								application/json;charset=UTF-8
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">返回示例</td>
						<td style="">
							<div>
<pre>{
    "fdId": "175111230662f3d08caee394a08b2fc0",
    "docSubject": "张三提交的请假",
    "fdNumber": "20201010001",
    "docCreateTime": 1602309221487,
    "docCreator": {
        "fdId": "175110cca34aa96b5fdcea94d1899733",
        "fdName": "张三"
    },
    "docPublishTime": null,
    "docStatus": "20",
    "wfNode": "审批节点",
    "wfHandler": {
        "fdId": "1183b0b84ee4f581bba001c47a78b2d9",
        "fdName": "管理员"
    },
    "fdTemplate": {
        "fdId": "17433b9ebc7c1a30e2c49764fc380fec",
        "fdName": "请假"
    },
    "fdDepartment": {
        "fdId": "1735c02ef7b3a10bb8ced7b4948876d4",
        "fdName": "深圳蓝凌"
    },
    "authReaders": [],
    "modelData": {
        "unit": "hour",
        "duration": "50",
        "from_half_day": "",
        "leave_code": "9181ba73-78dd-4d0f-96b2-17fdf7108c71",
        "reason": "1",
        "from_time": 1602259200000,
        "attendance": null,
        "calculate_model": "1",
        "leave_txt": "事假",
        "to_half_day": "",
        "to_time": 1602864000000
    }
}</pre>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">返回说明</td>
						<td style="">
							<table class="tb_normal" width=100%>
								<tr class="tr_normal_title">
									<td align="center" style="width: 30%"><b>字段名</b></td>
									<td align="center" style="width: 10%"><b>类型</b></td>
									<td align="center" style="width: 30%"><b>说明</b></td>
									<td align="center" style="width: 30%"><b>取值说明</b></td>
								</tr>
								<tr>
									<td>fdId</td>
									<td>String</td>
									<td>实例ID</td>
									<td></td>
								</tr>
								<tr>
									<td>docSubject</td>
									<td>String</td>
									<td>标题</td>
									<td></td>
								</tr>
								<tr>
									<td>fdNumber</td>
									<td>String</td>
									<td>编号</td>
									<td></td>
								</tr>
								<tr>
									<td>docCreateTime</td>
									<td>Long</td>
									<td>创建时间</td>
									<td>时间戳</td>
								</tr>
								<tr>
									<td>docCreator</td>
									<td>ID-Name对象</td>
									<td>创建人</td>
									<td>
<pre>fdId:创建人ID
fdName:创建人名称</pre>
									</td>
								</tr>
								<tr>
									<td>docPublishTime</td>
									<td>Long</td>
									<td>结束时间</td>
									<td>时间戳</td>
								</tr>
								<tr>
									<td>docStatus</td>
									<td>String</td>
									<td>文档状态</td>
									<td>
<pre>
00:废弃
10:草稿
11:驳回
20:待审
30:发布
40:取消
</pre>
									</td>
								</tr>
								<tr>
									<td>wfNode</td>
									<td>String</td>
									<td>当前节点</td>
									<td></td>
								</tr>
								<tr>
									<td>wfHandler</td>
									<td>ID-Name对象列表</td>
									<td>当前处理人</td>
									<td>组织架构</td>
								</tr>
								<tr>
									<td>fdTemplate</td>
									<td>ID-Name对象</td>
									<td>模板</td>
									<td>
<pre>fdId:模板ID
fdName:模板名称</pre>
									</td>
								</tr>
								<tr>
									<td>fdDepartment</td>
									<td>ID-Name对象</td>
									<td>创建人所属部门</td>
									<td>
<pre>fdId:创建人所属部门ID
fdName:创建人所属部门名称</pre>
									</td>
								</tr>
								<tr>
									<td>authReaders</td>
									<td>ID-Name对象列表</td>
									<td>可阅读者</td>
									<td>组织架构</td>
								</tr>
								<tr>
									<td>modelData</td>
									<td>Key-Value对象列表</td>
									<td>表单数据</td>
									<td>"字段名:字段值"的集合</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>