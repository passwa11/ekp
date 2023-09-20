<%@ page import="com.landray.kmss.kms.common.filestore.constant.KmssMessageKeyConstant" %>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

<%@ include file="/resource/jsp/view_top.jsp"%>
<%
	String attInfoMess = ResourceUtil.getString(KmssMessageKeyConstant.FTP_ERROR_SECURITY_FILE_TYPE);
	attInfoMess = attInfoMess.replace("{0}", SysAttConstant.DISABLED_FILE_TYPE);
	request.setAttribute("attInfoMess", attInfoMess);
	%>
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

<p class="txttitle">文档知识库文档维护接口</p>

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
				<p>&nbsp;&nbsp;文档知识库文档维护接口</p>
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
								/api/kms-multidoc/kmsMultidocKnowledgeRestService/addDoc
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
								multipart/form-data
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
									<td>fdDocTemplateId</td>
									<td>分类ID</td>
									<td>必填</td>
								</tr>
								<tr>
									<td>docContent</td>
									<td>内容</td>
									<td>必填</td>
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
									<td>authorType</td>
									<td>作者类型</td>
									<td>非必填，可取值1,2<br>
										1: 内部作者<br>
										2: 外部作者
									</td>
								</tr>
								<tr>
									<td>docAuthor</td>
									<td>作者信息</td>
									<td>非必填，为空时为创建者<br>
										authorType为1时: 填写内部人员Id<br>
										authorType为2时: 外部作者，多作者时用英文分号(;)隔开
									</td>
								</tr>
								<tr>
									<td>attachmentForms[0].fdKey</td>
									<td>附件fdkey</td>
									<td>上传附件时必填</td>
								</tr>
								<tr>
									<td>attachmentForms[0].fdFileName</td>
									<td>附件名称</td>
									<td>上传附件时必填<br/>${attInfoMess}</td>
								</tr>
								<tr>
									<td>attachmentForms[0].fdAttachment</td>
									<td>附件</td>
									<td>上传附件时必填</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求示例</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								<p>
									public static void main(String[] args){<br>
									try {<br>
									RestTemplate yourRestTemplate = new RestTemplate();<br>
									String url = "http://localhost:8080/ekp/api/kms-multidoc/kmsMultidocKnowledgeRestService/addDoc"; //指向EKP的接口url<br>
									//把ModelingAppModelParameterForm转换成MultiValueMap<br>
									MultiValueMap<String,Object> wholeForm = new LinkedMultiValueMap<>();<br>
									wholeForm.add("docSubject", new String("FromRest".getBytes("UTF-8"),"ISO-8859-1"));<br>
									//		     wholeForm.add("fdDocTemplateId", "17b57c144f42e99e6f2b5594c84a3dcc");<br>
									wholeForm.add("fdDocTemplateId", "17a7048795947b260dc893a403fb011b");<br>
									wholeForm.add("fdDocCreator", "1183b0b84ee4f581bba001c47a78b2d9");<br>
									wholeForm.add("docContent", "ceshi");<br>
									wholeForm.add("authorType", "1");<br>
									wholeForm.add("docAuthor", "1183b0b84ee4f581bba001c47a78b2d9");<br>

									LinkedMultiValueMap<String,Object> innerMap = new LinkedMultiValueMap<>();<br>
									// 注意附件列表的key是一样的<br>
									wholeForm.add("attachmentForms[0].fdKey", "attachment");// 第一个附件<br>
									wholeForm.add("attachmentForms[0].fdFileName",<br>
									new String("Rest.txt".getBytes("UTF-8"), "ISO-8859-1"));<br>
									wholeForm.add("attachmentForms[0].fdAttachment",<br>
									new FileSystemResource(new File("C:/Rest.txt")));<br>
									HttpHeaders headers = new HttpHeaders();<br>
									// 如果EKP对该接口启用了Basic认证，那么客户端需要加入<br>
									// addAuth(headers,"yourAccount"+":"+"yourPassword");是VO，则使用APPLICATION_JSON<br>
									headers.setContentType(MediaType.MULTIPART_FORM_DATA);<br>
									// 必须设置上传类型，如果入参是字符串，使用MediaType.TEXT_PLAIN；如果<br>
									HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(<br>
									wholeForm, headers);<br>

									// 有返回值的情况 VO可以替换成具体的JavaBean<br>
									ResponseEntity<String> obj = yourRestTemplate.exchange(url,<br>
									HttpMethod.POST, entity, String.class);<br>
									String body = obj.getBody();<br>
									System.out.println(body);<br>

									} catch (Exception e) {<br>
									System.out.println(e);<br>
									}<br>

									}<br>
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
								{<br/>
								&nbsp;&nbsp;"code": "200",<br/>
								&nbsp;&nbsp;"success": "success",<br/>
								&nbsp;&nbsp;"data": {<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreateTime": 1591682713139,<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docSubject": "文档知识库1404",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCategory": "1728384e76aa3bb8d2f953b44619dc85",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"fdId": "17297aec5d3703ab241dd2145d7a672c",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreatorId": "170b04d8d5aa1142664955347689527d",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docContent": "内容11111"<br/>
								&nbsp;&nbsp;},<br/>
								&nbsp;&nbsp;"msg": "操作成功"<br/>
								}
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">失败响应报文</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								{	<br/>
								&nbsp;&nbsp;"code": "401",<br/>
								&nbsp;&nbsp;"success": "false",<br/>
								&nbsp;&nbsp;"data": [],<br/>
								&nbsp;&nbsp;"msg": "fdDocTemplateId不存在，找不着这个分类！"<br/>
								}
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
								/api/kms-multidoc/kmsMultidocKnowledgeRestService/delDoc
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
									<td>fdId</td>
									<td>文档Id</td>
									<td>必填,批量删除时请使用英文分号(;)隔开</td>
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
									"fdId":"17298c90d5542f19f9feb22427c89a8c;1728384e76aa3bb8d2f953b44619dc81"
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
								{<br/>
								&nbsp;&nbsp;"code": "200",<br/>
								&nbsp;&nbsp;"success": "success",<br/>
								&nbsp;&nbsp;"data": [],<br/>
								&nbsp;&nbsp;"msg": "操作成功"<br/>
								}
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">失败响应报文</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								{	<br/>
								&nbsp;&nbsp;"code": "401",<br/>
								&nbsp;&nbsp;"success": "false",<br/>
								&nbsp;&nbsp;"data": [],<br/>
								&nbsp;&nbsp;"msg": "请检查fdId是否正确！"<br/>
								}
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
								/api/kms-multidoc/kmsMultidocKnowledgeRestService/updateDoc
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
								application/form-data
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
									<td>fdId</td>
									<td>文档ID</td>
									<td>必填</td>
								</tr>
								<tr>
									<td>fdDocTemplateId</td>
									<td>分类ID</td>
									<td>非必填,为空不更新</td>
								</tr>
								<tr>
									<td>docContent</td>
									<td>内容</td>
									<td>非必填,为空不更新</td>
								</tr>
								<tr>
									<td>docSubject</td>
									<td>标题</td>
									<td>非必填,为空不更新</td>
								</tr>
								<tr>
									<td>fdDocCreator</td>
									<td>创建者</td>
									<td>非必填,为空不更新</td>
								</tr>
								<tr>
									<td>authorType</td>
									<td>作者类型</td>
									<td>非必填，可取值1,2 为空不更新<br>
										1: 内部作者<br>
										2: 外部作者
									</td>
								</tr>
								<tr>
									<td>docAuthor</td>
									<td>作者信息</td>
									<td>非必填，为空时不更新<br>
										authorType为1时: 填写内部人员Id<br>
										authorType为2时: 外部作者，多作者时用英文分号(;)隔开
									</td>
								</tr>
								<tr>
									<td>attachmentForms[0].fdKey</td>
									<td>附件fdkey</td>
									<td>上传附件时必填,上传附件会覆盖以前的附件</td>
								</tr>
								<tr>
									<td>attachmentForms[0].fdFileName</td>
									<td>附件名称</td>
									<td>上传附件时必填,上传附件会覆盖以前的附件<br/>${attInfoMess}</td>
								</tr>
								<tr>
									<td>attachmentForms[0].fdAttachment</td>
									<td>附件</td>
									<td>上传附件时必填,上传附件会覆盖以前的附件</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求示例</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								<p>
									public static void main(String[] args){<br>
									try {<br>
									RestTemplate yourRestTemplate = new RestTemplate();<br>
									String url = "http://localhost:8080/ekp/api/kms-multidoc/kmsMultidocKnowledgeRestService/addDoc"; //指向EKP的接口url<br>
									//把ModelingAppModelParameterForm转换成MultiValueMap<br>
									MultiValueMap<String,Object> wholeForm = new LinkedMultiValueMap<>();<br>
									wholeForm.add("docSubject", new String("FromRest".getBytes("UTF-8"),"ISO-8859-1"));<br>
									//		     wholeForm.add("fdDocTemplateId", "17b57c144f42e99e6f2b5594c84a3dcc");<br>
									wholeForm.add("fdDocTemplateId", "17a7048795947b260dc893a403fb011b");<br>
									wholeForm.add("fdDocCreator", "1183b0b84ee4f581bba001c47a78b2d9");<br>
									wholeForm.add("docContent", "ceshi");<br>
									wholeForm.add("authorType", "1");<br>
									wholeForm.add("docAuthor", "1183b0b84ee4f581bba001c47a78b2d9");<br>

									LinkedMultiValueMap<String,Object> innerMap = new LinkedMultiValueMap<>();<br>
									// 注意附件列表的key是一样的<br>
									wholeForm.add("attachmentForms[0].fdKey", "attachment");// 第一个附件<br>
									wholeForm.add("attachmentForms[0].fdFileName",<br>
									new String("Rest.txt".getBytes("UTF-8"), "ISO-8859-1"));<br>
									wholeForm.add("attachmentForms[0].fdAttachment",<br>
									new FileSystemResource(new File("C:/Rest.txt")));<br>
									HttpHeaders headers = new HttpHeaders();<br>
									// 如果EKP对该接口启用了Basic认证，那么客户端需要加入<br>
									// addAuth(headers,"yourAccount"+":"+"yourPassword");是VO，则使用APPLICATION_JSON<br>
									headers.setContentType(MediaType.MULTIPART_FORM_DATA);<br>
									// 必须设置上传类型，如果入参是字符串，使用MediaType.TEXT_PLAIN；如果<br>
									HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(<br>
									wholeForm, headers);<br>

									// 有返回值的情况 VO可以替换成具体的JavaBean<br>
									ResponseEntity<String> obj = yourRestTemplate.exchange(url,<br>
									HttpMethod.POST, entity, String.class);<br>
									String body = obj.getBody();<br>
									System.out.println(body);<br>

									} catch (Exception e) {<br>
									System.out.println(e);<br>
									}<br>

									}<br>
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
								{<br/>
								&nbsp;&nbsp;"code": "200",<br/>
								&nbsp;&nbsp;"success": "success",<br/>
								&nbsp;&nbsp;"data": {<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreateTime": 1591682713139,<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docSubject": "文档知识库1404",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCategory": "1728384e76aa3bb8d2f953b44619dc85",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"fdId": "17297aec5d3703ab241dd2145d7a672c",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreatorId": "170b04d8d5aa1142664955347689527d",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docContent": "内容11111"<br/>
								&nbsp;&nbsp;},<br/>
								&nbsp;&nbsp;"msg": "操作成功"<br/>
								}
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">失败响应报文</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								{	<br/>
								&nbsp;&nbsp;"code": "401",<br/>
								&nbsp;&nbsp;"success": "false",<br/>
								&nbsp;&nbsp;"data": [],<br/>
								&nbsp;&nbsp;"msg": "fdDocTemplateId不存在，找不着这个分类！"<br/>
								}
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
								/api/kms-multidoc/kmsMultidocKnowledgeRestService/newEditionDoc
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
									<td>fdId</td>
									<td>文档ID</td>
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
									<td>fdDocTemplateId</td>
									<td>分类ID</td>
									<td>非必填,为空时使用原版本信息</td>
								</tr>
								<tr>
									<td>docContent</td>
									<td>内容</td>
									<td>非必填,为空时使用原版本信息</td>
								</tr>
								<tr>
									<td>docSubject</td>
									<td>标题</td>
									<td>非必填,为空时为原版本的标题</td>
								</tr>
								<tr>
									<td>fdDocCreator</td>
									<td>创建者</td>
									<td>非必填,为空时使用原版本信息</td>
								</tr>
								<tr>
									<td>authorType</td>
									<td>作者类型</td>
									<td>非必填，可取值1,2 为空时使用原版本信息<br>
										1: 内部作者<br>
										2: 外部作者
									</td>
								</tr>
								<tr>
									<td>docAuthor</td>
									<td>作者信息</td>
									<td>非必填，为空时使用原版本信息<br>
										authorType为1时: 填写内部人员Id<br>
										authorType为2时: 外部作者，多作者时用英文分号(;)隔开
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求示例</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								<p>
									{	<br/>
									&nbsp;&nbsp;"fdId":"1728384e76aa3bb8d2f953b44619dc81", <br>
									&nbsp;&nbsp;"docSubject":"文档知识库223", <br>
									&nbsp;&nbsp;"fdDocTemplateId":"1728384e76aa3bb8d2f953b44619dc81",<br>
									&nbsp;&nbsp;"docContent":"内容内容",<br>
									&nbsp;&nbsp;"authorType":"1",<br>
									&nbsp;&nbsp;"docAuthor":"17292a06e02741414f560104effae969;17292a06e02741414f560104effae969",<br>
									&nbsp;&nbsp;"fdDocCreator":"1183b0b84ee4f581bba001c47a78b2d9",<br>
									&nbsp;&nbsp;"docMainVersion":"1",<br>
									&nbsp;&nbsp;"docAuxiVersion":"1"<br>
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
								{<br/>
								&nbsp;&nbsp;"code": "200",<br/>
								&nbsp;&nbsp;"success": "success",<br/>
								&nbsp;&nbsp;"data": {<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreateTime": 1591682713139,<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docSubject": "文档知识库1404",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCategory": "1728384e76aa3bb8d2f953b44619dc85",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"fdId": "17297aec5d3703ab241dd2145d7a672c",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreatorId": "170b04d8d5aa1142664955347689527d",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docContent": "内容11111"<br/>
								&nbsp;&nbsp;},<br/>
								&nbsp;&nbsp;"msg": "操作成功"<br/>
								}
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">失败响应报文</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								{	<br/>
								&nbsp;&nbsp;"code": "401",<br/>
								&nbsp;&nbsp;"success": "false",<br/>
								&nbsp;&nbsp;"data": [],<br/>
								&nbsp;&nbsp;"msg": "fdDocTemplateId不存在，找不着这个分类！"<br/>
								}
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<!-- 5 -->
		<tr>
			<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.5&nbsp;&nbsp;查询接口
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
								/api/kms-multidoc/kmsMultidocKnowledgeRestService/queryDoc
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">接口说明</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								查询文档知识
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
									<td>fdId</td>
									<td>文档ID，根据ID查询，多值时使用英文逗号(;)隔开</td>
									<td>选填</td>
								</tr>
								<tr>
									<td>docCategory</td>
									<td>分类ID， 根据分类查询</td>
									<td>选填</td>
								</tr>
								<tr>
									<td>docSubject</td>
									<td>标题，根据标题查询，该查询为模糊查询</td>
									<td>选填</td>
								</tr>
								<tr>
									<td colspan="3">参数三选一，参数为一个以上时作用效果为且，既需同时满足</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">请求示例</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								<p>
									{	<br/>
									&nbsp;&nbsp;"fdId":"1728384e76aa3bb8d2f953b44619dc81;1728384e76aa3bb8d2f953b44619dc81", <br>
									&nbsp;&nbsp;"docSubject":"知识", <br>
									&nbsp;&nbsp;"docCategory":"1728384e76aa3bb8d2f953b44619dc81"<br>
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
								{<br/>
								&nbsp;&nbsp;"code": "200",<br/>
								&nbsp;&nbsp;"success": "success",<br/>
								&nbsp;&nbsp;"data": {<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docSubject": "维基知识_新建_1592793534",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreateTime": "2020-06-22 10:39",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docStatus": "30",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docDeptId": "",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docAlterorId": "",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCategoryId": "172c028b3a21e32f8016e9b4a7c90fbc",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreatorName": "管理员",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docAlterorName": "",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docDeptName": "",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"fdId": "172d9e4c1b4c81a456df0ab464097bcd",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCreatorId": "1183b0b84ee4f581bba001c47a78b2d9",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docCategoryName": "文档知识库接口类"<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;"docContent": "内容内容内容"<br/>
								&nbsp;&nbsp;},<br/>
								&nbsp;&nbsp;"msg": "操作成功"<br/>
								}
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">失败响应报文</td>
						<td style="padding: 0px;">
							<div style="margin: 8px;">
								{	<br/>
								&nbsp;&nbsp;"code": "401",<br/>
								&nbsp;&nbsp;"success": "false",<br/>
								&nbsp;&nbsp;"data": [],<br/>
								&nbsp;&nbsp;"msg": "错误！"<br/>
								}
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
			<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;2.1&nbsp;&nbsp;组织架构字段同步
				<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
		</tr>
		<tr style="display: none">
			<td>
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关于组织架构字段的同步方式，系统目前支持如下</p>
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果某个字段是多个值组成则使用json数组[{{ID:”2343434”},{LoginName:”2343434”}}]</p>
				<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
					<tr>
						<td style="padding: 0px;">
							<table class="tb_normal" width=100%>
								<tr class="tr_normal_title">
									<td align="center" style="width: 20%"><b>Json参数串</b></td>
									<td align="center" style="width: 79%"><b>说明</b></td>
								</tr>
								<tr>
									<td>{ID:”2343434”}</td>
									<td>按照ID的方法对接</td>
								</tr>
								<tr>
									<td>{LoginName:”2343434”}</td>
									<td>按照登录名的方法对接</td>
								</tr>
								<tr>
									<td>{Keyword:”2343434”}</td>
									<td>按照关键字的方法对接</td>
								</tr>
								<tr>
									<td>{PersonNo:”2343434”}</td>
									<td>按照员工编号的方法对接</td>
								</tr>
								<tr>
									<td>{DeptNo:”2343434”}</td>
									<td>按照部门编号的方法对接</td>
								</tr>
								<tr>
									<td>{OrgNo:”2343434”}</td>
									<td>按照组织的方法对接</td>
								</tr>
								<tr>
									<td>{PostNo:”2343434”}</td>
									<td>按照岗位编号的方法对接</td>
								</tr>
								<tr>
									<td>{GroupNo:”2343434”}</td>
									<td>按照群组编号的方法对接</td>
								</tr>
								<tr>
									<td>{LdapDN:”2343434”}</td>
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