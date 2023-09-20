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
		用于获取流程管理审批模板相关数据
	</div>
	<br/>

	<table border="0" width="95%">
		<tr>
			<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;获取模板列表
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
								/api/km-review/template/list
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">接口说明</td>
						<td style="">
							<div>
								获取所有模板数据列表
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
	"pageNo": 2,
	"rowSize": 5
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
    "pageNo": 2,
    "rowSize": 2,
    "totalPage": 79,
    "totalSize": 157,
    "datas": [
        {
            "fdId": "15f99c23a06a6ed5c242391458fabdc7",
            "fdName": "档案销毁申请流程",
            "fdIsAvailable": true,
            "fdOrder": 1,
            "docCreateTime": 1510113240000,
            "docCreator": {
                "fdId": "1183b0b84ee4f581bba001c47a78b2d9",
                "fdName": "管理员"
            }
        },
        {
            "fdId": "15fa3e66147e5a34c1f419b4a5a832ca",
            "fdName": "员工离职申请流程",
            "fdIsAvailable": true,
            "fdOrder": 2,
            "docCreateTime": 1510284600000,
            "docCreator": {
                "fdId": "1183b0b84ee4f581bba001c47a78b2d9",
                "fdName": "管理员"
            }
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
									<td>模板ID</td>
									<td></td>
								</tr>
								<tr>
									<td>fdName</td>
									<td>String</td>
									<td>模板名称</td>
									<td></td>
								</tr>
								<tr>
									<td>fdIsAvailable</td>
									<td>Boolean</td>
									<td>是否启用</td>
									<td></td>
								</tr>
								<tr>
									<td>fdOrder</td>
									<td>Integer</td>
									<td>排序号</td>
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
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;获取模板详情
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
							/api/km-review/template/get
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
								/api/km-review/template/get?fdId=17433b9ebc7c1a30e2c49764fc380fec
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
									<td>模板ID</td>
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
    "fdId": "17433b9ebc7c1a30e2c49764fc380fec",
    "fdName": "请假",
    "fdIsAvailable": true,
    "fdOrder": null,
    "docCreateTime": 1598595960000,
    "docCreator": {
        "fdId": "1183b0b84ee4f581bba001c47a78b2d9",
        "fdName": "管理员"
    },
    "docAlteror": {
        "fdId": "1183b0b84ee4f581bba001c47a78b2d9",
        "fdName": "管理员"
    },
    "docAlterTime": 1600070470457,
    "authReaders": [],
    "authEditors": [
        {
            "fdId": "1735c03b06602f7a7c13827443ea412e",
            "fdName": "test"
        },
        {
            "fdId": "1721290e87636092006413a442d9126f",
            "fdName": "test1"
        }
    ],
    "sysFormTemplateModels": [
        {
            "label": "请假类型(显示文字)",
            "name": "leave_txt",
            "column": null,
            "type": "String",
            "notNull": false,
            "unique": false,
            "canDisplay": false,
            "readOnly": false,
            "enumType": null,
            "enumValues": null,
            "businessType": "inputText"
        },
        {
            "label": "开始时间",
            "name": "from_time",
            "column": null,
            "type": "DateTime",
            "notNull": true,
            "unique": false,
            "canDisplay": true,
            "readOnly": false,
            "enumType": null,
            "enumValues": null,
            "businessType": "datetime"
        },
        {
            "label": "开始时间时间段",
            "name": "from_half_day",
            "column": null,
            "type": "String",
            "notNull": false,
            "unique": false,
            "canDisplay": true,
            "readOnly": false,
            "enumType": null,
            "enumValues": "上午|AM;下午|PM",
            "businessType": "select"
        },
        {
            "label": " 结束时间",
            "name": "to_time",
            "column": null,
            "type": "DateTime",
            "notNull": true,
            "unique": false,
            "canDisplay": true,
            "readOnly": false,
            "enumType": null,
            "enumValues": null,
            "businessType": "datetime"
        },
        {
            "label": "结束时间时间段",
            "name": "to_half_day",
            "column": null,
            "type": "String",
            "notNull": false,
            "unique": false,
            "canDisplay": true,
            "readOnly": false,
            "enumType": null,
            "enumValues": "上午|AM;下午|PM",
            "businessType": "select"
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
									<td>模板ID</td>
									<td></td>
								</tr>
								<tr>
									<td>fdName</td>
									<td>String</td>
									<td>模板名称</td>
									<td></td>
								</tr>
								<tr>
									<td>fdIsAvailable</td>
									<td>Boolean</td>
									<td>是否启用</td>
									<td></td>
								</tr>
								<tr>
									<td>fdOrder</td>
									<td>Integer</td>
									<td>排序号</td>
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
									<td>docAlteror</td>
									<td>ID-Name对象</td>
									<td>修改人</td>
									<td>
<pre>fdId:修改人ID
fdName:修改人名称</pre>
									</td>
								</tr>
								<tr>
									<td>docAlterTime</td>
									<td>Long</td>
									<td>文档修改时间</td>
									<td>时间戳</td>
								</tr>
								<tr>
									<td>authReaders</td>
									<td>ID-Name对象列表</td>
									<td>模板可使用者</td>
									<td>组织架构</td>
								</tr>
								<tr>
									<td>authEditors</td>
									<td>ID-Name对象列表</td>
									<td>模板可维护者</td>
									<td>组织架构</td>
								</tr>
								<tr>
									<td>sysFormTemplateModels</td>
									<td>Key-Value对象列表</td>
									<td>表单控件字段数据结构</td>
									<td>
<pre>label:控件名称
name:控件ID
column:列名(需表单映射)
type:字段类型
notNull:控件必填
canDisplay:是否显示
readOnly:控件只读
enumType:枚举类型
enumValues:枚举值
businessType:控件类型</pre>
									</td>
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