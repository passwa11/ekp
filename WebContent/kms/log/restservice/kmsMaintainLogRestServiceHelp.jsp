<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

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

<p class="txttitle"><bean:message bundle="kms-log" key="kmsMaintainLogRestService.description"/></p>

<center>
<div style="width: 95%;text-align: left;">
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td>
			<b>1、接口说明</b>
			<br>
			<br>
			<p>&nbsp;&nbsp;kms日志库维护服务对外提供了新增单条日志接口和新增多条日志接口</p>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;新增单条日志接口
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style=" display:none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">接口url</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							/api/kms-log/kmsMaintainLogRestService/addLog
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							新增单条日志
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">对应方法</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							KmsMaintianDocResponse addLog(KmsMaintainLogRequest request)
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求方式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							POST
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求格式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							application/json
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数</td>
					<td style="padding: 0px;"><div style="margin: 8px;">KmsMaintainLogRequest对象，详细说明如下：</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" style="width: 30%" ><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>fdCreateTime</td>
								<td>操作时间</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdExtendCreator</td>
								<td>操作对象的创建者ID</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdIp</td>
								<td>IP地址</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdOperator</td>
								<td>操作者（id）</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdOperatorName</td>
								<td>操作者（name）</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdOprateMethod</td>
								<td>操作方法</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdSubject</td>
								<td>标题</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdTargetId</td>
								<td>操作对象id</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>modelName</td>
								<td>模块名</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>moduleKey</td>
								<td>对应日志基础配置的设置</td>
								<td>必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>{</p>
			            <p>"fdCreateTime": "2019-03-23 16:00:12",</p>
			            <p>"fdExtendCreator": "1183b0b84ee4f581bba001c47a123456" ,</p>
			            <p>"fdIp": "127.0.0.1",</p>
			            <p>"fdOperator": "1183b0b84ee4f581bba001c47a123456",</p>
						<p>"fdOperatorName": "管理员",</p>
			            <p>"fdOprateMethod": "read",</p>
			            <p>"fdSubject": "doc subject",</p>
			            <p>"fdTargetId": "1183b0b84ee4f581bba001c47a000000",</p>
			            <p>"modelName": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge",</p>
			            <p>"moduleKey": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"</p>
						<p>}</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%"><bean:message bundle="kms-ask" key="askRestservice.interface.response.descripe"/></td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							 响应返回json格式数据
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">成功响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							<p>{</p>
							<p>"result": "success",</p>
							<p>"wranEntites": []</p>
							<p>}</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">失败响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							<p>{</p>
							<p>"success": false,</p>
							<p>"data": null,</p>
							<p>"msg": "Unauthorized",</p>
							<p>"code": "error.httpStatus.401"</p>
							<p>}</p>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;新增多条日志接口
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style=" display:none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">接口url</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							/api/kms-log/kmsMaintainLogRestService/addLogs
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							新增多条日志
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">对应方法</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							KmsMaintianDocResponse addLogs(List<KmsMaintainLogRequest> list)
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求方式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							POST
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求格式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							application/json
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数</td>
					<td style="padding: 0px;"><div style="margin: 8px;">KmsMaintainLogRequest对象，详细说明如下：</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" style="width: 30%" ><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>fdCreateTime</td>
								<td>操作时间</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdExtendCreator</td>
								<td>操作对象的创建者ID</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdIp</td>
								<td>IP地址</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdOperator</td>
								<td>操作者（id）</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdOperatorName</td>
								<td>操作者（name）</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdOprateMethod</td>
								<td>操作方法</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdSubject</td>
								<td>标题</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdTargetId</td>
								<td>操作对象id</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>modelName</td>
								<td>模块名</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>moduleKey</td>
								<td>对应日志基础配置的设置</td>
								<td>必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>[{</p>
			            <p>"fdCreateTime": "2019-03-23 16:00:12",</p>
			            <p>"fdExtendCreator": "1183b0b84ee4f581bba001c47a123456" ,</p>
			            <p>"fdIp": "127.0.0.1",</p>
			            <p>"fdOperator": "1183b0b84ee4f581bba001c47a123456",</p>
						<p>"fdOperatorName": "管理员",</p>
			            <p>"fdOprateMethod": "read",</p>
			            <p>"fdSubject": "doc subject",</p>
			            <p>"fdTargetId": "1183b0b84ee4f581bba001c47a000000",</p>
			            <p>"modelName": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge",</p>
			            <p>"moduleKey": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"</p>
						<p>},</p>
						<p>{</p>
			            <p>"fdCreateTime": "2019-03-23 16:01:05",</p>
			            <p>"fdExtendCreator": "1183b0b84ee4f581bba001c47a123456" ,</p>
			            <p>"fdIp": "127.0.0.1",</p>
			            <p>"fdOperator": "1183b0b84ee4f581bba001c47a123456",</p>
						<p>"fdOperatorName": "管理员",</p>
			            <p>"fdOprateMethod": "read",</p>
			            <p>"fdSubject": "doc subject2",</p>
			            <p>"fdTargetId": "1183b0b84ee4f581bba001c47a000002",</p>
			            <p>"modelName": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge",</p>
			            <p>"moduleKey": "com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge"</p>
						<p>}]</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%"><bean:message bundle="kms-ask" key="askRestservice.interface.response.descripe"/></td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							 响应返回json格式数据
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">成功响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							<p>{</p>
							<p>"result": "success",</p>
							<p>"wranEntites": []</p>
							<p>}</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">失败响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							<p>{</p>
							<p>"success": false,</p>
							<p>"data": null,</p>
							<p>"msg": "Unauthorized",</p>
							<p>"code": "error.httpStatus.401"</p>
							<p>}</p>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td><br/><b>2、附录</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;2.1&nbsp;&nbsp;组织架构字段的同步方式
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
		<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
			<tr>
				<td>
					<div style="margin-left: 8px;">如果某个字段是多个值组成则使用json数组[{{ID:”2343434”},{LoginName:”2343434”}}]</div>
					<table class="tb_normal" width=100%>
						<tr class="tr_normal_title">
							<td style="text-align: center;">Json参数串</td>
							<td style="text-align: center;">说明</td>
						</tr>
						<tr>
							<td>{ID:”2343434”}</td>
							<td>按照ID的方法对接</td>
						</tr>
						<tr>
							<td>{LoginName:”2343434”}</td>
							<td>按照登录名的方式对接</td>
						</tr>
						<tr>
							<td>{Keyword:”2343434”}</td>
							<td>按照关键字的方式对接</td>
						</tr>
						<tr>
							<td>{PersonNo:”2343434”}</td>
							<td>按照员工编号的方式对接</td>
						</tr>
						<tr>
							<td>{ DeptNo:”2343434”}</td>
							<td>按照部门编号的方式对接</td>
						</tr>
						<tr>
							<td>{ OrgNo:”2343434”}</td>
							<td>按照组织编号的方式对接</td>
						</tr>
						<tr>
							<td>{ PostNo:”2343434”}</td>
							<td>按照职位编号的方式对接</td>
						</tr>
						<tr>
							<td>{ GroupNo:”2343434”}</td>
							<td>按照组编号的方法对接</td>
						</tr>
						<tr>
							<td>{ LdapDN:”2343434”}</td>
							<td>按照LDAP的方式对接</td>
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
