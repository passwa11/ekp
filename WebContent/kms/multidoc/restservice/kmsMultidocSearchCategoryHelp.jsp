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

<p class="txttitle">获取文档库分类接口</p>

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
			<p>&nbsp;&nbsp;获取文档库分类接口</p>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;查询用户所能查看的分类接口
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
							/api/kms-multidoc/kmsMultidocSeachCategoryRestService/searchCategoryList
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							查询用户所能查看的分类接口
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
					<td style="padding: 0px;"><div style="margin: 8px;">详细说明如下：</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" style="width: 30%" ><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>userId</td>
								<td>用户（组织架构字段），非用户id</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>parentId</td>
								<td>父类ID</td>
								<td>非必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>{</p>
			            <p>"userId": "{PersonNo:'80'}"</p>
						<p>}</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">响应说明</td>
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
								</div><div class="hljs-line">    [{
								</div><div class="hljs-line">    <span class="hljs-string">"fdId"</span>: <span class="hljs-string">""</span>,<span class="hljs-comment">//分类id，不为空</span>
								</div><div class="hljs-line">    <span class="hljs-string">"fdName"</span>: <span class="hljs-string">""</span>,<span class="hljs-comment">//分类名称，不为空</span>
								</div><div class="hljs-line">    <span class="hljs-string">"parentId"</span>: <span class="hljs-literal">""</span>,<span class="hljs-comment">//父类名称</span>
								</div><div class="hljs-line">    <span class="hljs-string">"order"</span>: <span class="hljs-string">""</span>,<span class="hljs-comment">// 序号</span>
								</div><div class="hljs-line">    <span class="hljs-string">"hierarchyId"</span>: <span class="hljs-string">""</span>,<span class="hljs-comment">//层级ID</span>
								</div><div class="hljs-line">    }]
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
	
	<tr style=" display:none;">
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
							<td>{Id:”2343434”}</td>
							<td>按照Id的方法对接</td>
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