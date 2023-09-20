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

<p class="txttitle">文档库文档维护接口</p>

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
			<p>&nbsp;&nbsp;文档库文档维护接口</p>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;新增文档接口
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
							/api/kms-multidoc/kmsMultidocRestService/addDoc
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							新增文档
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
								<td>relateId</td>
								<td>业务系统的文档ID，可以自行指定长度为36位的任意字符</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>priviledgeKey</td>
								<td>维护标识,作为第三方系统维护KM文档的一个权限标识</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>dataSource</td>
								<td>数据源</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdDocTemplateId</td>
								<td>分类ID</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docContent</td>
								<td>内容</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docAuthorType</td>
								<td>作者类型</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docSubject</td>
								<td>标题</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdDocCreator</td>
								<td>创建者</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAuthorId</td>
								<td>作者ID</td>
								<td>非必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
							{
								"relateId":"sbbbaff",
								"priviledgeKey":"bbssbbavv",
								"dataSource":"webservice",
								"docSubject":"hello",
								"fdDocCreator":"{Id:'1183b0b84ee4f581bba001c47a78b2d9'}",
								"docAuthorId":"{Id:'1183b0b84ee4f581bba001c47a78b2d9'}"
							}
						</p>
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
							<p>{</p>
							<p>"success": success</p>
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
	<!-- 2 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;删除接口
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
							/api/kms-multidoc/kmsMultidocRestService/delDoc
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							删除文档
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
								<td>relateId</td>
								<td>业务系统的文档ID，可以自行指定长度为36位的任意字符</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>priviledgeKey</td>
								<td>维护标识,作为第三方系统维护KM文档的一个权限标识</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>dataSource</td>
								<td>数据源</td>
								<td>必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
							{
								"relateId":"aadss",
								"priviledgeKey":"bbdss",
								"dataSource":"ccdss"
							}
						</p>
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
							<p>{</p>
							<p>"success": success</p>
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
	<!-- 3 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.3&nbsp;&nbsp;更新接口
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
							/api/kms-multidoc/kmsMultidocRestService/updateDoc
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							更新文档
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
								<td>relateId</td>
								<td>业务系统的文档ID，可以自行指定长度为36位的任意字符</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>priviledgeKey</td>
								<td>维护标识,作为第三方系统维护KM文档的一个权限标识</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>dataSource</td>
								<td>数据源</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAlterorId</td>
								<td>修改者ID</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAuthorId</td>
								<td>作者ID</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdDocCreator</td>
								<td>创建者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docSubject</td>
								<td>标题</td>
								<td>非必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
							{
								  "relateId":"sbbvsbaff",
								  "priviledgeKey":"bbvsssbbavv",
								  "dataSource":"webservice",
								  "docSubject":"hello3",
								  "fdDocCreator":"{Id:'1183b0b84ee4f581bba001c47a78b2d9'}",
								  "docAuthorId":"{Id:'1183b0b84ee4f581bba001c47a78b2d9'}",
								  "docAlterorId":"{Id:'1183b0b84ee4f581bba001c47a78b2d9'}"
								}
						</p>
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
							<p>{</p>
							<p>"success": success</p>
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
	<!-- 4 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.4&nbsp;&nbsp;新版本接口
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
						/api/kms-multidoc/kmsMultidocRestService/newEditionDoc
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							文档新版本
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
								<td>relateId</td>
								<td>业务系统的文档ID，可以自行指定长度为36位的任意字符</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>priviledgeKey</td>
								<td>维护标识,作为第三方系统维护KM文档的一个权限标识</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>dataSource</td>
								<td>数据源</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAlterorId</td>
								<td>修改者ID</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docMainVersion</td>
								<td>主版本号</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAuxiVersion</td>
								<td>副版本号</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docContent</td>
								<td>内容</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docSubject</td>
								<td>标题</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docAuthorType</td>
								<td>作者类型</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docAuthorId</td>
								<td>作者ID</td>
								<td>非必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
						{
	"relateId":"aadsms",
	"priviledgeKey":"bbmdss",
	"dataSource":"ccdmss",
	"fdDocTemplateId":"15d54b7e7008b2ccd00ad4d4ddc99187",
	"docContent":"abcd",
	"docAuthorType":"1",
	"docSubject":"gooddddssss",
	"fdDocCreator":"1183b0b84ee4f581bba001c47a78b2d9",
	"docAuthorId":"1183b0b84ee4f581bba001c47a78b2d9",
	"docAuxiVersion":"0",(副版本号)
	"docMainVersion":"1"(主版本号)
}
						</p>
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
							<p>{</p>
							<p>"success": success</p>
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
	<!-- 5 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.5&nbsp;&nbsp;更新多作者
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
						/api/kms-multidoc/kmsMultidocRestService/updateDocAuthors
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							更新多作者
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
								<td>relateId</td>
								<td>业务系统的文档ID，可以自行指定长度为36位的任意字符</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>priviledgeKey</td>
								<td>维护标识,作为第三方系统维护KM文档的一个权限标识</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>dataSource</td>
								<td>数据源</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAlterorId</td>
								<td>修改者ID</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAuthorId</td>
								<td>作者ID</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdDocCreator</td>
								<td>创建者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docSubject</td>
								<td>标题</td>
								<td>非必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
										{
											"fdName":"0328abcdef",
											"relateId":"aad",(与前面新增时所填要一致)
											"priviledgeKey":"bbd",(与前面新增时所填要一致)
											"dataSource":"ccd",(与前面新增时所填要一致)
											"docAlterorId":"1183b0b84ee4f581bba001c47a78b2d9"
										}
						</p>
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
							<p>{</p>
							<p>"success": success</p>
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
	<!-- 6 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.6&nbsp;&nbsp;文档多作者新增
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
						/api/kms-multidoc/kmsMultidocRestService/addDocAuthors
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							文档多作者新增
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
								<td>relateId</td>
								<td>业务系统的文档ID，可以自行指定长度为36位的任意字符</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>priviledgeKey</td>
								<td>维护标识,作为第三方系统维护KM文档的一个权限标识</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>dataSource</td>
								<td>数据源</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAuthorsIds</td>
								<td>修改者ID</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdDocCreator</td>
								<td>创建者</td>
								<td>非必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
										{
											  "relateId":"sbbvdfgaggff",
											  "priviledgeKey":"bbvsgggdsfsbbavv",
											  "dataSource":"webservice",
											  "docSubject":"hello6",
											  "fdDocCreator":"{Id:'1183b0b84ee4f581bba001c47a78b2d9'}",
											  "docAuthorsIds":"1183b0b84ee4f581bba001c47a78b2d9,16796f424052d4649d95ad745ae9cc20"
											}
						</p>
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
							<p>{</p>
							<p>"success": success</p>
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