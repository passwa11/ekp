<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
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

<p class="txttitle">${param.name}接口说明</p>

<center>
<div style="width: 95%;text-align: left;">
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>kms日志库webserivce的服务</b></td>
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
						<div style="margin: 8px;">&nbsp;&nbsp;服务地址：http://localhost:8080/ekp/sys/webservice/kmsMaintainLogWSService?wsdl<br/>
						&nbsp;&nbsp;类别维护服务接口：IKmsMaintainLogWSServiceService
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
								<td>addLog</td>
								<td>新增日志（单条）</td>
								<td>success或者failed</td>
								<td>KmsMaintainLogRequest</td>
								<td>KmsFaultException</td>
							</tr>
							<tr>
								<td>addLogs</td>
								<td>新增日志（多条）</td>
								<td>success或者failed</td>
								<td>KmsMaintainLogRequest</td>
								<td>KmsFaultException</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求对象</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;请求对象：KmsMaintainLogRequest</div>
					<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="29%"><b>字段名</b></td>
								<td align="center" width="50%"><b>描述</b></td>
								<td align="center" width="20%"><b>取值说明</b></td>
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
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回对象：AttachmentForm<br/>&nbsp;&nbsp;<font style="color: red"> 由于在推送文档可以带附件，最大限制为100M，但是建议文件较大的时候不要使用webservice传输，性能会比较低。<font></div>
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
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;属性对象:PropertyEntity<br/>&nbsp;&nbsp;<font style="color: red"> 由于在推送文档可以带附件，最大限制为100M，但是建议文件较大的时候不要使用webservice传输，性能会比较低。<font></div>
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
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;2.2&nbsp;&nbsp;新增日志（单条）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：addLog</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;搜索所有专家的SOAP请求示例如下:</p>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Envelopexmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<ser:addLog>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<arg0>"/><br/>					
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdCreateTime>2018-12-03 10:26:56</fdCreateTime>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdIp>0:0:0:0:0:0:0:1</fdIp>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdOperator>1183b0b84ee4f581bba001c47a78b2d9</fdOperator>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdOperatorName>管理员</fdOperatorName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdOprateMethod>read</fdOprateMethod>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdSubject>aaaa</fdSubject>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdTargetId>15d597230246338f924f88244afb3f66</fdTargetId>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<modelName>com.landray.kmss.kms.learn.model.KmsLearnMain</modelName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<moduleKey>com.landray.kmss.kms.learn.model.KmsLearnMain</moduleKey>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Envelope>"/><br/>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="extendContentInfo"><br/>&nbsp;&nbsp;2.3&nbsp;&nbsp;新增日志（多条）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;对应的接口方法为：addLogs</p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;完整的soap请求：</p>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Envelopexmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Header>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<ser:addLog>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<arg0>"/><br/>					
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdCreateTime>2018-12-03 10:26:56</fdCreateTime>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdIp>0:0:0:0:0:0:0:1</fdIp>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdOperator>1183b0b84ee4f581bba001c47a78b2d9</fdOperator>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdOperatorName>管理员</fdOperatorName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdOprateMethod>read</fdOprateMethod>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdSubject>aaaa</fdSubject>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<fdTargetId>15d597230246338f924f88244afb3f66</fdTargetId>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<modelName>com.landray.kmss.kms.learn.model.KmsLearnMain</modelName>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="<moduleKey>com.landray.kmss.kms.learn.model.KmsLearnMain</moduleKey>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Body>"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<c:out  value="</soap:Envelope>"/><br/>
		</td>
	</tr>
		<tr>
		<td><br/><b>4、错误描述</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;4.1&nbsp;&nbsp;错误描述说明
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
		<td><br/><b>5、附录</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;5.1&nbsp;&nbsp;组织架构字段的同步方式
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
