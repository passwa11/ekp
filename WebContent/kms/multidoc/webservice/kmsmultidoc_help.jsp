<%@ page import="com.landray.kmss.kms.common.filestore.constant.KmssMessageKeyConstant" %>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConstant" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
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

<p class="txttitle">${param.name}接口说明</p>

<center>
<div style="width: 95%;text-align: left;">
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、知识文档维护服务</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;维护服务说明
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">服务说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">&nbsp;&nbsp;服务地址：http://localhost:8080/guosen_km_new/sys/webservice/kmsMultidocMaintainDocWSService<br/>
						&nbsp;&nbsp;类别维护服务接口：IKmsMultidocMaintainDocWSService
						</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="20%"><b>服务方法名</b></td>
								<td align="center" width="20%"><b>功能说明</b></td>
								<td align="center" width="20%"><b>返回值</b></td>
								<td align="center" width="20%"><b>请求对象</b></td>
								<td align="center" width="20%"><b>异常</b></td>
							</tr>
							<tr>
								<td>addDoc</td>
								<td>推送文档到多维库</td>
								<td>“Successful”或者是”Failed”</td>
								<td>KmsMaintainMultidocDocRequest</td>
								<td>KmsFaultException</td>
							</tr>
							<tr>
								<td>updateDoc</td>
								<td>更新文档</td>
								<td>“Successful”或者是”Failed”</td>
								<td>KmsMaintainMultidocDocRequest</td>
								<td>KmsFaultException</td>
							</tr>
							<tr>
								<td>delDoc</td>
								<td>删除文档</td>
								<td>“Successful”或者是”Failed”</td>
								<td>KmsMaintainMultidocDocRequest</td>
								<td>KmsFaultException</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求对象</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;请求对象：KmsMaintainMultidocDocRequest</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="29%"><b>字段名</b></td>
								<td align="center" width="50%"><b>描述</b></td>
								<td align="center" width="20%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>attachmentForms</td>
								<td>附件对象列表</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>propertyEntities</td>
								<td>扩展属性列表</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>extendFilePath</td>
								<td>扩展属性对应的xml文件路径（在更新某个文档，而且文档有扩展属性的情况下，必须指定该文件的路径地址）</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docSubject</td>
								<td>文档主题</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdDocTemplateId</td>
								<td>文档的类别Id</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docExpire</td>
								<td>文档期限</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>outerAuthor</td>
								<td>外部作者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdDescription</td>
								<td>摘要</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docPostsIds</td>
								<td>所属岗位</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docContent</td>
								<td>文档内容</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docAlterorId</td>
								<td>更新者</td>
								<td>更新文档必填</td>
							</tr>
							<tr>
								<td>fdDocCreator</td>
								<td>文档创建者</td>
								<td>新建文档必填</td>
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
								<td>authAttCopyIds</td>
								<td>附件可拷贝者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authAttDownloadIds</td>
								<td>附件可下载者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>authAttPrintIds</td>
								<td>附件可打印者</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docDeptId</td>
								<td>部门</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docAuthorType</td>
								<td>作者类型</td>
								<td>0或者1<br/>0：内部作者<br/>1：外部作者</td>
							</tr>
							<tr>
								<td>docAuthorId</td>
								<td>作者ID</td>
								<td>非必填，仅当作者类型为内部作者时生效</td>
							</tr>
							<tr>
								<td>docSecondCategories</td>
								<td>辅类别</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>docMainVersion</td>
								<td>主版本号</td>
								<td>新版本更新时必填</td>
							</tr>
							<tr>
								<td>docAuxiVersion</td>
								<td>副版本号</td>
								<td>新版本更新时必填</td>
							</tr>
							<tr>
								<td>originId</td>
								<td>要更新版本的源文档ID</td>
								<td>新版本更新时必填</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">KmsAuthRequest</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="19%"><b>字段名</b></td>
								<td align="center" width="40%"><b>描述</b></td>
								<td align="center" width="40%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>priviledgeKey</td>
								<td>维护标识,作为第三方系统维护KM文档的一个权限标识</td>
								<td>如果业务系统会有更新的场景出现，建议在新增时该字段为必填字段</td>
							</tr>
							<tr>
								<td>relateId</td>
								<td>业务系统的文档ID，可以自行指定长度为36位的任意字符</td>
								<td>如果业务系统会有更新的场景出现，建议在新增时该字段为必填字段</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回对象</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回对象：AttachmentForm<br/>&nbsp;&nbsp;${attInfoMess}<br/>&nbsp;&nbsp;<font style="color: red">由于在推送文档可以带附件，最大限制为100M，但是建议文件较大的时候不要使用webservice传输，性能会比较低。<font></div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="19%"><b>字段名</b></td>
								<td align="center" width="40%"><b>描述</b></td>
								<td align="center" width="40%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>fdFileName</td>
								<td>字符串（String）</td>
								<td>无</td>
							</tr>
							<tr>
								<td>fdAttachment</td>
								<td>字节数组（byte[]）</td>
								<td>无</td>
							</tr>
							<tr>
								<td>fdKey</td>
								<td>字符串（String）</td>
								<td>“attachment”,这个值对于文档库是固定的，如果写错了，附件在页面上不会显示</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">属性对象</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;属性对象:PropertyEntity</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="19%"><b>字段名</b></td>
								<td align="center" width="40%"><b>描述</b></td>
								<td align="center" width="40%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>columnName</td>
								<td>字符串（String）</td>
								<td>无</td>
							</tr>
							<tr>
								<td>value</td>
								<td>字符串（String）</td>
								<td>无</td>
							</tr>
							<tr>
								<td colspan="3">
								关于添加属性的说明:<br/>
								目前在我们产品中，有几种动态属性类型，主要是为了满足当产品中的字段无法满足客户的需求的时候，可以自定义属性，扩展功能,必须有管理员先创建好需要的属性。由于扩展属性是动态生成的，所有他不是一个规则的名称，例如我们产品中不存在一个叫做，合同编号的字段，我们需要在动态属性库中创建一个属性，那么这个属性的名称就是动态生成的如：att_20130701095055220dj8<br/>
								所以当客户端需要有动态属性支持的时候，这些数据都需要去数据库对应的表看，或者直接去产品的页面上看该属性的名称；<br/>
								产品页面：<br/>
								/moduleindex.jsp?nav=/kms/common/tree.jsp&main=%2Fkms%2Fcommon%2Fkms_common_main%2FkmsCommonMain.do%3Fmethod%3Dlist%26s_path%3D!{message(kms-common:manu.moduleInfo.config)}<br/>
								也可以去看对应的数据库表：sys_property_define
								</td>
							</tr>
							<tr>
								<td colspan="3"><br/>常用的类型描述：</td>
							</tr>
							<tr class="tr_normal_title">
								<td align="center"><b>属性类型</b></td>
								<td align="center"><b>描述</b></td>
								<td align="center"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>字符串</td>
								<td>所有字符串类型</td>
								<td>columnName= att_20130701095055220dj8<br/>value=”jjjjjjjj”</td>
							</tr>
							<tr>
								<td>浮点数</td>
								<td>任意类型（Object）</td>
								<td>columnName= att_20130701095055220dj8<br/>value=”4445.5”</td>
							</tr>
							<tr>
								<td>日期</td>
								<td>日期可以是仅仅是日期，也可以是日期+时间</td>
								<td>columnName= att_20130701095055220dj8<br/>value=”2012-11-20”或者是”2012-11-20 11:11:11”</td>
							</tr>
							<tr>
								<td>自定义组织架构</td>
								<td> </td>
								<td>
									att_20130715025749620v5ch={name=zhangsan, id=13f97e3c6f4c0c5f378b1df4e6b96fb0}<br/>
									这种属于复杂的类型：<br/>
									给出java版本的赋值例子：<br/>
									PropertyEntity p=new PropertyEntity()<br/>
									p.setColumnName(“20130715025749620v5ch”);<br/>
									p.setValue(“{name:”zhangsan”,id: {PersonNo:"112323232"});<br/>
									此格式为固定的通信格式，不能随便改变，否则服务端无法取到。
								</td>
							</tr>
							<tr>
								<td colspan="3">1：字符串<br/>2：浮点数<br/>3: 日期<br/>4：自定义组织架构</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
 	 
	 
	
	<tr>
		<td><br/><b>2、测试描述</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="orgInfo"><br/>&nbsp;&nbsp;2.1&nbsp;&nbsp;须知
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<soap:Header>"/>	<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<tns:RequestSOAPHeader>"/><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<tns:user></tns:user>"/><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<tns:password></tns:password>"/><br/>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="<tns:service></tns:service>"/><br/>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="</tns:RequestSOAPHeader>"/><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="</soap:Header>"/><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必须要在SOAP请求头加入这一段，给定用户名和密码,密码就是经过md5加密过的那一串，不能明文传输,否则无法通过验证。
 		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;2.2&nbsp;&nbsp;文档新增
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：addDoc</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;文档新增的SOAP请求示例如下(不包括文件,因为是通过诸如fiddler直接发送soap请求，如果需要测试附件，可能需要借助代码)：</p>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Envelopexmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:RequestSOAPHeader xmlns:tns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:user xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>chenliang</tns:user>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:password xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>b700ba5df2e2d5a4fef24d020ab8c47e</tns:password>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:service	xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>kmsMultidocWebserviceService</tns:service>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</tns:RequestSOAPHeader>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<ns2:addDoc xmlns:ns2='http://service.doc.webservice.multidoc.kms.kmss.landray.com/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<priviledgeKey>maintain multidoc doc</priviledgeKey>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<relateId>663344444444444444455555</relateId>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttCopyIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttCopyIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttDownloadIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttDownloadIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttPrintIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttPrintIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authEditorIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authEditorIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authReaderIds>[{PersonNo:"1234567890"}]</authReaderIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<docSubject>开发流推送21111111111111111111111外部作者22222</docSubject>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDocCreator>{PersonNo:"1234567890"}</fdDocCreator>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tags>标签1;标签2</tags>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAuthorId>{PersonNo:"112323232"}</docAuthorId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAuthorType>0</docAuthorType>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docContent>will see that air you breath is not very fresh</docContent>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docDeptId>{DeptNo:"5ttttttt"}</docDeptId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docExpire>10</docExpire>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docPostsIds>{PostNo:"rrt444444"}</docPostsIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDescription>这是文档的摘要信息</fdDescription>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</ns2:addDoc>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Envelope>"/><br/>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="extendContentInfo"><br/>&nbsp;&nbsp;2.3&nbsp;&nbsp;文档更新
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：updateDoc</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;更新文档须知</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;1：暂不支持转移文档的分类</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;2：不支持内部作者更新。</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;完整的soap请求：（同样没有附件，如果需要测试在原有的文档上新增附件，需要借助代码）：</p>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Envelopexmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:RequestSOAPHeader xmlns:tns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:user xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>chenliang</tns:user>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:password xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>b700ba5df2e2d5a4fef24d020ab8c47e</tns:password>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:service	xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>kmsMultidocWebserviceService</tns:service>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</tns:RequestSOAPHeader>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<ns2:updateDoc xmlns:ns2='http://service.doc.webservice.multidoc.kms.kmss.landray.com/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<priviledgeKey>maintain multidoc doc</priviledgeKey>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<relateId>663344444444444444455555</relateId>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttPrintIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"},{PersonNo:"2343434"},{PersonNo:"112323232"}]</authAttPrintIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAlterorId>{PersonNo:"2343434"}</docAlterorId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDescription>这是文档的摘要信息</fdDescription>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<outerAuthor>远征22222</outerAuthor>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</ns2:updateDoc>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Envelope>"/><br/>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="extendContentInfo"><br/>&nbsp;&nbsp;2.4&nbsp;&nbsp;文档删除
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：delDoc</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;文档新增的SOAP请求示例如下(不包括文件,因为是通过诸如fiddler直接发送soap请求，如果需要测试附件，可能需要借助代码)：</p>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Envelopexmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:RequestSOAPHeader xmlns:tns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:user xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>chenliang</tns:user>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:password xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>b700ba5df2e2d5a4fef24d020ab8c47e</tns:password>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:service	xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>kmsMultidocWebserviceService</tns:service>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</tns:RequestSOAPHeader>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<ns2:delDoc xmlns:ns2='http://service.doc.webservice.multidoc.kms.kmss.landray.com/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<priviledgeKey>maintain multidoc doc</priviledgeKey>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<relateId>663344444444444444455555</relateId>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</ns2:delDoc>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Envelope>"/><br/>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="extendContentInfo"><br/>&nbsp;&nbsp;2.5&nbsp;&nbsp;文档新版本
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：newEditionDoc</p>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;文档新增的SOAP请求示例如下(不包括文件,因为是通过诸如fiddler直接发送soap请求，如果需要测试附件，可能需要借助代码)：</p>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Envelopexmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:RequestSOAPHeader xmlns:tns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:user xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>chenliang</tns:user>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:password xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>b700ba5df2e2d5a4fef24d020ab8c47e</tns:password>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:service	xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>kmsMultidocWebserviceService</tns:service>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</tns:RequestSOAPHeader>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<ser:newEditionDoc>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<priviledgeKey>maintain multidoc doc</priviledgeKey>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<relateId>663344444444444444455555</relateId>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttCopyIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttCopyIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttDownloadIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttDownloadIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttPrintIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttPrintIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authEditorIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authEditorIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authReaderIds>[{PersonNo:"1234567890"}]</authReaderIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAlterorId>{PersonNo:"2343434"}</docAlterorId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<docSubject>开发流推送21111111111111111111111外部作者22222</docSubject>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDocCreator>{PersonNo:"1234567890"}</fdDocCreator>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tags>标签1;标签2</tags>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAuthorId>{PersonNo:"112323232"}</docAuthorId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAuthorType>0</docAuthorType>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docContent>will see that air you breath is not very fresh</docContent>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docDeptId>{DeptNo:"5ttttttt"}</docDeptId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docExpire>10</docExpire>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docPostsIds>{PostNo:"rrt444444"}</docPostsIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDescription>这是文档的摘要信息</fdDescription>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<outerAuthor>远征22222</outerAuthor>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<docAuxiVersion>1</docAuxiVersion>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<docMainVersion>2</docMainVersion>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<originId>663344444444444444455555</originId>"/><br/>

			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</ser:newEditionDoc>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Envelope>"/><br/>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;2.6&nbsp;&nbsp;文档多作者新增
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：addDocAuthors</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;文档新增的SOAP请求示例如下(不包括文件,因为是通过诸如fiddler直接发送soap请求，如果需要测试附件，可能需要借助代码)：</p>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Envelopexmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:RequestSOAPHeader xmlns:tns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:user xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>chenliang</tns:user>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:password xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>b700ba5df2e2d5a4fef24d020ab8c47e</tns:password>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:service	xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>kmsMultidocWebserviceService</tns:service>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</tns:RequestSOAPHeader>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<ns2:addDoc xmlns:ns2='http://service.doc.webservice.multidoc.kms.kmss.landray.com/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<priviledgeKey>maintain multidoc doc</priviledgeKey>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<relateId>663344444444444444455555</relateId>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttCopyIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttCopyIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttDownloadIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttDownloadIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttPrintIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttPrintIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authEditorIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authEditorIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authReaderIds>[{PersonNo:"1234567890"}]</authReaderIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAlterorId>{PersonNo:"2343434"}</docAlterorId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<docSubject>开发流推送21111111111111111111111外部作者22222</docSubject>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDocCreator>{PersonNo:"1234567890"}</fdDocCreator>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tags>标签1;标签2</tags>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAuthorId>{PersonNo:"112323232"}</docAuthorId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAuthorType>0</docAuthorType>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docContent>will see that air you breath is not very fresh</docContent>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docDeptId>{DeptNo:"5ttttttt"}</docDeptId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docExpire>10</docExpire>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<outerAuthor>远征22222</outerAuthor>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docPostsIds>{PostNo:"rrt444444"}</docPostsIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDescription>这是文档的摘要信息</fdDescription>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</ns2:addDoc>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Envelope>"/><br/>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="extendContentInfo"><br/>&nbsp;&nbsp;2.7&nbsp;&nbsp;文档多作者更新
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：updateDocAuthors</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;更新文档须知</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;1：暂不支持转移文档的分类</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;2：不支持内部作者更新。</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;完整的soap请求：（同样没有附件，如果需要测试在原有的文档上新增附件，需要借助代码）：</p>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Envelopexmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:RequestSOAPHeader xmlns:tns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:user xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>chenliang</tns:user>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:password xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>b700ba5df2e2d5a4fef24d020ab8c47e</tns:password>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tns:service	xmlns='http://localhost:8080/ekp10.0_last_new/sys/webservice/kmsMultidocMaintainDocWSService'>kmsMultidocWebserviceService</tns:service>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</tns:RequestSOAPHeader>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<ns2:updateDoc xmlns:ns2='http://service.doc.webservice.multidoc.kms.kmss.landray.com/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<priviledgeKey>maintain multidoc doc</priviledgeKey>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<relateId>663344444444444444455555</relateId>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttPrintIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"},{PersonNo:"2343434"},{PersonNo:"112323232"}]</authAttPrintIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAlterorId>{PersonNo:"2343434"}</docAlterorId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttCopyIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttCopyIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authAttDownloadIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authAttDownloadIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authEditorIds>[{DeptNo:"5ttttttt"},{PostNo:"rrt444444"}]</authEditorIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<authReaderIds>[{PersonNo:"1234567890"}]</authReaderIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<docSubject>开发流推送21111111111111111111111外部作者22222</docSubject>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDocCreator>{PersonNo:"1234567890"}</fdDocCreator>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<tags>标签1;标签2</tags>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAuthorId>{PersonNo:"112323232"}</docAuthorId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docAuthorType>0</docAuthorType>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docContent>will see that air you breath is not very fresh</docContent>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docDeptId>{DeptNo:"5ttttttt"}</docDeptId>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docExpire>10</docExpire>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<docPostsIds>{PostNo:"rrt444444"}</docPostsIds>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<columnName>att_20130718033548467laNM</columnName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<value>2012-11-22</value>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</propertyEntities>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<fdDescription>这是文档的摘要信息</fdDescription>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value='<outerAuthor>远征22222</outerAuthor>'/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</arg0>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</ns2:updateDoc>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Envelope>"/><br/>
		</td>
	</tr>

	<tr>
		<td><br/><b>3、错误描述</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;3.1&nbsp;&nbsp;错误描述说明
		<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">错误编号</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="20%"><b>错误编号</b></td>
								<td align="center" width="79%"><b>编号描述</b></td>
							</tr>
							<tr>
								<td>4000</td>
								<td>用户名在组织架构中不存在，拒绝访问</td>
							</tr>
							<tr>
								<td>4001</td>
								<td>当前用户无权操作或者是访问某个类别</td>
							</tr>
							<tr>
								<td>200</td>
								<td>请求中的某个必填字段缺失</td>
							</tr>
							<tr>
								<td>300</td>
								<td>请求中的参数输入有误，逻辑存在问题，或者是为非法的格式</td>
							</tr>
							<tr>
								<td>5000</td>
								<td>服务端内部处理错误，如果出现该错误，表示服务端的代码处理逻辑有问题。</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">异常对象</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;异常对象：KmsFaultException</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="20%"><b>字段名</b></td>
								<td align="center" width="79%"><b>描述</b></td>
							</tr>
							<tr>
								<td>faultInfo</td>
								<td>包含faultCode和faultMessage的对象实体</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">异常实体对象</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;异常实体对象：KmsFault</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="19%"><b>字段名</b></td>
								<td align="center" width="40%"><b>描述</b></td>
							</tr>
							<tr>
								<td>faultMessage</td>
								<td>详细的错误信息</td>
							</tr>
							<tr>
								<td>faultCode</td>
								<td>错误编码（上述所给出的编码）</td>
							</tr>
					</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td><br/><b>4、附录</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;4.1&nbsp;&nbsp;组织架构字段的同步方式
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