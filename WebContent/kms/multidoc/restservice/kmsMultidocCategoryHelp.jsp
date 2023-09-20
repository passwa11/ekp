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

<p class="txttitle">文档库分类维护接口</p>

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
			<p>&nbsp;&nbsp;文档库分类维护接口</p>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;新增分类
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
							/api/kms-multidoc/kmsMultidocCategoryRestService/addCategories
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							新增分类
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
								<td>fdName</td>
								<td>名称</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdTemplateType</td>
								<td>类型</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdMoveToModelId</td>
								<td>删除类别，将关联的该类别的子类别以及文档移动指定的类别下</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>排序号</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdParentId</td>
								<td>父ID</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpAttCopyIds</td>
								<td>附件可可拷贝者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpAttDownloadIds</td>
								<td>附件可下载者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpAttPrintIds</td>
								<td>附件可打印者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authReaderIds</td>
								<td>可阅读者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authEditorIds</td>
								<td>可编辑者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpReaderIds</td>
								<td>默认可阅读者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpEditorIds</td>
								<td>默认可编辑者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdNumberPrefix</td>
								<td>编号前缀</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAlterorId</td>
								<td>修改者ID</td>
								<td>修改类别为必填项</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
							[{
	"fdName":"go777",
	"relateId":"aad",
	"priviledgeKey":"bbd",
	"dataSource":"ccd",
	"fdTemplateType":"2",
	"fdOrder":"1"
}]
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
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;更新分类
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
							/api/kms-multidoc/kmsMultidocCategoryRestService/updateCategory
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							更新分类
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
								<td>fdName</td>
								<td>名称</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdTemplateType</td>
								<td>类型</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdMoveToModelId</td>
								<td>删除类别，将关联的该类别的子类别以及文档移动指定的类别下</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>排序号</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdParentId</td>
								<td>父ID</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpAttCopyIds</td>
								<td>附件可可拷贝者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpAttDownloadIds</td>
								<td>附件可下载者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpAttPrintIds</td>
								<td>附件可打印者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authReaderIds</td>
								<td>可阅读者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authEditorIds</td>
								<td>可编辑者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpReaderIds</td>
								<td>默认可阅读者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authTmpEditorIds</td>
								<td>默认可编辑者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdNumberPrefix</td>
								<td>编号前缀</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAlterorId</td>
								<td>修改者ID</td>
								<td>修改类别为必填项</td>
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
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.3&nbsp;&nbsp;删除分类
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
							/api/kms-multidoc/kmsMultidocCategoryRestService/delCategory
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							删除分类
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