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
			trObj.style.display="block";
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

<p class="txttitle">${HtmlParam.name}接口说明</p>

<center>
<div style="width: 95%;text-align: left;">
	<p>&nbsp;&nbsp;EKP系统提供了考勤webservice服务，增加考勤记录（addAttend）接口，以下是对上述接口的具体说明。
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;增加考勤记录(addAttend)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width:85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;增加考勤记录上下文（SysAttendAddContext），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>appName</td>
								<td>考勤来源</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>标识考勤记录来源系统</td>
							</tr>
							<tr>
								<td>dataType</td>
								<td>数据类型</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>标识考勤记录数组类型</td>
							</tr>
							<tr>
								<td>datas</td>
								<td>考勤记录</td>
								<td>字符串(json数组)</td>
								<td>不允许为空</td>
								<td>考勤记录数组,当dataType=1时,数据结构如下:<br/>
									<font style="font-weight: nomarl;">
									&nbsp;&nbsp;[<br/>
									&nbsp;&nbsp;&nbsp;{<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"docCreator": {"PersonNo":"001"}, //考勤用户,不允许为空。数据格式为JSON(单值)，格式描述请查看"《2.1 组织架构数据说明》"。<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"createTime": "打卡时间",     //不允许为空,格式为:yyyy-MM-dd HH:mm:ss  <br/>
							        &nbsp;&nbsp;&nbsp;}<br/>
							        &nbsp;&nbsp;...<br/>
							        &nbsp;&nbsp;]<br/>
									<br/></font>
								</td>
							</tr>
							<tr>
								<td>others</td>
								<td>扩展参数</td>
								<td>字符串(json)</td>
								<td>可为空</td>
								<td>备用参数，方便以后参数的扩展。格式如：{key1:value1,key2:value2}。</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;待办发送后返回信息（SysAttendResult），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>returnState</td>
								<td>返回状态</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td>0:表示未操作<br/>
									1:表示操作失败<br/>
									2:表示操作成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值返回空。<br/>
									返回状态值为1时，该值错误信息。<br/>
									返回状态值为2时， 该值返回空。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td><br/><b>2、各种数据格式说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="orgInfo"><br/>&nbsp;&nbsp;2.1&nbsp;&nbsp;组织架构数据说明
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">
						数据格式
					</td>
					<td>单值格式为:  {类型: 值}，    如{"PersonNo":"001"}。<br/>
						多值格式为: [{类型1:值1}，{类型2:值2}...]，如:  [{"PersonNo":"001"}，{"LoginName":"admin"}]。
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">说明</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;对于"格式"中的类型，以下是对应的类型说明表:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td><b>类型名</b></td>
								<td><b>描述</b></td>
							</tr>
							<tr>
								<td>Id</td>
								<td>EKP系统组织架构唯一标识</td>
							</tr>
							<tr>
								<td>PersonNo</td>
								<td>EKP系统组织架构个人编号</td>
							</tr>
							
							<tr>
								<td>LoginName</td>
								<td>EKP系统组织架构个人登录名</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>

	<tr>
		<td><br/><b>3、参考代码</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;样例
		<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
		<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;"><tr><td>
				public static void main(String[] args) throws Exception {<br/>
					&nbsp;&nbsp;WebServiceConfig cfg = WebServiceConfig.getInstance();<br/>
					&nbsp;&nbsp;ISysAttendWebService service = (ISysAttendWebService)callService(cfg.getAddress(), cfg.getServiceClass());<br/>
					&nbsp;&nbsp;// 请在此处添加业务代码<br/>
					&nbsp;&nbsp;SysAttendAddContext context = new SysAttendAddContext();<br/>
					&nbsp;&nbsp;context.setAppName("Test");<br/>
					&nbsp;&nbsp;context.dataType("1");<br/>
					&nbsp;&nbsp;context.datas("[{\"docCreator\":{\"PersonNo\":\"001\"},\"createTime\":\"2018-05-01 08:00:00\"}]");<br/>
					&nbsp;&nbsp;SysAttendResult result = service.sendTodo(context);<br/>
					&nbsp;&nbsp;if (result != null) {<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;if (result.getReturnState() != 2)<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(result.getMessage());<br/>
					&nbsp;&nbsp;}<br/>
				}<br/>
				
				/**<br/>
				 * 调用服务，生成客户端的服务代理<br/>
				 * <br/>
				 * @param address WebService的URL<br/>
				 * @param serviceClass 服务接口全名<br/>
				 * @return 服务代理对象<br/>
				 * @throws Exception<br/>
				 */<br/>
				public static Object callService(String address, Class serviceClass)<br/>
						throws Exception {<br/>
					&nbsp;&nbsp;JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();<br/>
					&nbsp;&nbsp;// 记录入站消息<br/>
					&nbsp;&nbsp;factory.getInInterceptors().add(new LoggingInInterceptor());<br/>
					&nbsp;&nbsp;// 记录出站消息<br/>
					&nbsp;&nbsp;factory.getOutInterceptors().add(new LoggingOutInterceptor());<br/>
					
					&nbsp;&nbsp;// 添加消息头验证信息。如果服务端要求验证用户密码，请加入此段代码<br/>
					&nbsp;&nbsp;// factory.getOutInterceptors().add(new AddSoapHeader());<br/>
			
					&nbsp;&nbsp;factory.setServiceClass(serviceClass);<br/>
					&nbsp;&nbsp;factory.setAddress(address);<br/>
					
					&nbsp;&nbsp;// 使用MTOM编码处理消息。如果需要在消息中传输文档附件等二进制内容，请加入此段代码<br/>
					&nbsp;&nbsp;// Map<String, Object> props = new HashMap<String, Object>();<br/>
					&nbsp;&nbsp;// props.put("mtom-enabled", Boolean.TRUE);<br/>
					&nbsp;&nbsp;// factory.setProperties(props);<br/>
			        
			        &nbsp;&nbsp;// 创建服务代理并返回<br/>
					&nbsp;&nbsp;return factory.create();<br/>
				}
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

</center>

<%@ include file="/resource/jsp/view_down.jsp"%>