<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<link href="http://cdn.bootcss.com/highlight.js/8.0/styles/monokai_sublime.min.css" rel="stylesheet">  
<script src="http://cdn.bootcss.com/highlight.js/8.0/highlight.min.js"></script>  
<script >hljs.initHighlightingOnLoad();</script>  
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
	<p>&nbsp;&nbsp;EKP系统提供了以下服务接口<br>
	1. 获取EKP应用列表信息(getEkpAppInfo)<br>
	2. 获取EKP手机端详情（getEkpMobileInfo）<br>
	3. 获取 SSO详细配置信息（getSSOInfo）<br>
	4. 获取组织架构配置信息（getOrgSyncCfgInfo）<br>
	5. 获取代办信息(getTodo)<br>
	6. 获取待办数（getTodoCount）<br>
	7. 获取EKP机器人配置信息（getEkpRobot）<br>
	8. 获取EKP所有的扩展点的配置信息（getExtendApp）<br>
	9. 更新组织架构信息服务（updateOrgInfo）<br>
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1. 接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;获取EKP应用列表信息(getEkpAppInfo)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取EKP应用列表信息(getEkpAppInfo)，详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD colspan="6">无</TD>
					         </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取EKP应用列表信息(getEkpAppInfo)，详细说明如下，详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo21">数据格式说明</a></b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;获取EKP手机端详情（getEkpMobileInfo）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取EKP手机端详情（getEkpMobileInfo），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD colspan="6">无</TD>
					         </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取EKP手机端详情（getEkpMobileInfo），详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo22">数据格式说明</a> </b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.3&nbsp;&nbsp;获取 SSO详细配置信息（getSSOInfo）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取 SSO详细配置信息（getSSOInfo），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD colspan="6">无</TD>
					         </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取 SSO详细配置信息（getSSOInfo），详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo23">数据格式说明</a> </b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.4&nbsp;&nbsp;获取组织架构配置信息（getOrgSyncCfgInfo）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取组织架构配置信息（getOrgSyncCfgInfo），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD colspan="6">无</TD>
					         </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取组织架构配置信息（getOrgSyncCfgInfo），详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo24">数据格式说明</a> </b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.5&nbsp;&nbsp;获取待办信息(getTodo)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取代办信息(getTodo)，详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD>targets</TD>
					          <TD>待办所属对象</TD>
					          <TD>字符串(JSON)</TD>
					          <TD>不允许为空</TD>
					          <TD>
					          	    表示获取指定人待办列表。<BR>数据格式为JSON，格式描述请查看"《2.1  组织架构数据说明》"。
					          </TD></TR>
					        <TR>
					          <TD>type</TD>
					          <TD>待办类型</TD>
					          <TD>数字(int)</TD>
					          <TD>可为空</TD>
					          <TD>	
					          		-1:所有已办<BR>
					          		0：所有待办<BR>
					                1:为审批类待办<BR>
					          		2:为通知类待办<BR>
					          		3:为暂挂类待办<BR>
					          		4:已处理的待办<BR>
					          		13:为审批类待办、暂挂类待办<BR>
					          		为空，表示获取所有待办列表<BR>
					          		不为空，表示获取指定类型待办列表。 
					        	</TD>
					        </TR>
					        <TR>
					          <TD>otherCond</TD>
					          <TD>其他条件</TD>
					          <TD>字符串(JSON)</TD>
					          <TD>可为空</TD>
					          <TD>辅助上两个查询条件，来定位要获取的待办列表。<BR/>
					          	  数据格式为JSON数组，为:[{类型1:值1}，{类型2:值2}...]<BR/>
					          	 如:[{"key":"reviewmain"}，{"modelId":"1edc3456bf"}]表示除待办所属人、待办类型外，
					          	 附加待办关键字和待办唯一标识来查询待办信息。<br/>
					          	 类型可为:appName（待办来源）、modelName（所属模块）、modelId（唯一标识）、subject（主题）、
					          	 link（链接）、key（关键字）、param1（参数1）、param2（参数2）
							  </TD>
					        </TR>
					        <TR>
					          <TD>rowSize</TD>
					          <TD>分页大小</TD>
					          <TD>数字(int)</TD>
					          <TD>可为空</TD>
					          <TD>设置获取待办每页条数<BR>
					          	为空，则取EKP系统默认每页条数。<BR>
					          	不为空，则取设定的每条条目数 </TD>
					        </TR>
					        <TR>
					          <TD>pageNo</TD>
					          <TD>页码</TD>
					          <TD>数字(int)</TD>
					          <TD>可为空</TD>
					          <TD>获取设置的第几页数据。<BR>为空表示获取第1页数据（1为ekp系统的起始页）。 </TD>
					          </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取代办信息(getTodo)，详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo25">数据格式说明</a> </b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.6&nbsp;&nbsp;获取待办数（getTodoCount）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取待办数（getTodoCount），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD colspan="6">无</TD>
					         </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取待办数（getTodoCount），详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo26">数据格式说明</a> </b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.7&nbsp;&nbsp;获取EKP机器人配置信息（getEkpRobot）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取EKP机器人配置信息（getEkpRobot），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD colspan="6">无</TD>
					         </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取EKP机器人配置信息（getEkpRobot），详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo27">数据格式说明</a> </b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.8&nbsp;&nbsp;获取EKP所有的扩展点的配置信息（getExtendApp）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取EKP所有的扩展点的配置信息（getExtendApp），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD colspan="6">无</TD>
					         </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;获取EKP所有的扩展点的配置信息（getExtendApp），详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo28">数据格式说明</a> </b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.9&nbsp;&nbsp;更新组织架构信息服务（updateOrgInfo）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;更新组织架构信息服务（updateOrgInfo），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <TR>
					          <TD>thirdImKKOrgSyncContext</TD>
					          <TD>要更新组织架构信息</TD>
					          <TD>JSON字符串</TD>
					          <TD>不为空</TD>
					          <TD>
								&nbsp;&nbsp;[<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"org":{<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"id":"" //组织架构fdId<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"info":{<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"workPhone":"",//办公电话<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"mobileNo":"",//手机号码<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"shortNo":"",//短号<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"aliasName":""//花名<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;},<br/>
								&nbsp;&nbsp;....<br/>
								&nbsp;&nbsp;]<br/>
					          </TD>
					         </TR>
					        
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;更新组织架构信息服务（updateOrgInfo），详细说明如下:</div>
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
								<td>0未操作<br/>
									1失败<br/>
									2成功
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
									返回状态值为2时，返回系统信息列表的数据，格式为JSON，格式说明请查看"<b><a href="#dataInfo29">数据格式说明</a> </b>"。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td><br/><b>2. 各种数据格式说明</b></td>
	</tr>

	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo21"><br/>&nbsp;&nbsp;2.1&nbsp;&nbsp;EKP系统详细信息数据格式说明
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;[<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"id":"123443debc"，        // EKP系统应用唯一标识 ，不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"name":{"zh-CN":"待办事宜"}，//应用国际化名称，不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"url":"/sys/notify.index"，     // 跳转地址， 不为空。<br/>
				            &nbsp;&nbsp;&nbsp;&nbsp;"icon":"/third/im/kk/resource/images/icon/notify.png"，//应用图表，不为空。<br/>
				          	&nbsp;&nbsp;&nbsp;&nbsp;"category":{"zh-CN":"协同管理"}，               //应用国际化分类名称，不为空。<br/>
				          	&nbsp;&nbsp;&nbsp;&nbsp;"detail":{"todo":"....","read":"...."} //待办信息url地址和阅读信息地址 可为空<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;}，<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;……<br/>
							&nbsp;&nbsp;]<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;[]// 空数组<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo22"><br/>&nbsp;&nbsp;2.2&nbsp;&nbsp;EKP手机端信息数据格式说明
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;[<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"id":"123443debc"，        // 唯一标识 ，不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"name":{"zh-CN":"","en-US":""}，//国际化名称，不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"url":"/sys/notify.index"，     // 跳转地址， 不为空。<br/>
				            &nbsp;&nbsp;&nbsp;&nbsp;"icon":"/third/im/kk/resource/images/icon/notify.png"，//应用图表，不为空。<br/>
				          	&nbsp;&nbsp;&nbsp;&nbsp;"category":{"zh-CN":"协同管理"}，               //应用国际化分类名称，不为空。<br/>
				          	&nbsp;&nbsp;&nbsp;&nbsp;"detail":{"todo":"....","read":"...."}, //待办信息url地址和阅读信息地址 可为空<br/>
				          	&nbsp;&nbsp;&nbsp;&nbsp;"readers":[{"id":"","type":""},...]//访问人员列表 可为空<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;}，<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;……<br/>
							&nbsp;&nbsp;]<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;[]// 空数组<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo23"><br/>&nbsp;&nbsp;2.3&nbsp;&nbsp;获取 SSO详细配置信息（getSSOInfo）
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;[<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"enable":"true"，        // 是否启用SSO ，不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"config":{"type":"","domain":"","maxAge":"","cookieName":"","security":"","publicSecurity":"","privateSecurity":"","userKey":""} //配置信息，不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;}，<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;……<br/>
							&nbsp;&nbsp;]<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;[]// 空数组<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo24"><br/>&nbsp;&nbsp;2.4&nbsp;&nbsp;获取组织架构配置信息（getOrgSyncCfgInfo）
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"enable":"true"，        // 是否启用 ，不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"level":""，//级别，可为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"excepts":[
								{
								"targets":[
									{"id":"","type":""},...
								],
								"sources":[
									{"id":"","type":""},...
								]
								},...
							]     // 架构信息， 可为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;[]// 空数组<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo25"><br/>&nbsp;&nbsp;2.5&nbsp;&nbsp;获取代办信息(getTodo)
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;[<br/>
							&nbsp;&nbsp;]<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;[]// 空数组<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo26"><br/>&nbsp;&nbsp;2.6&nbsp;&nbsp;获取待办数（getTodoCount）
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;[<br/>
							&nbsp;&nbsp;]<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;[]// 空数组<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo27"><br/>&nbsp;&nbsp;2.7&nbsp;&nbsp;获取EKP机器人配置信息（getEkpRobot）
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"open":"true"，        // 是否开启 ，不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"consoleUrl":""，//可为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"clientUrl":""     //  可为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
							&nbsp;&nbsp;]<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;{}// 空对象<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo24"><br/>&nbsp;&nbsp;2.8&nbsp;&nbsp;获取EKP所有的扩展点的配置信息（getExtendApp）
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;"attend":{"fdSpeedAttend":false,"fdSpeedStartTime":"","fdSpeedEndTime":""}        // 不为空。<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
							&nbsp;&nbsp;]<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;{}// 空对象<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo29"><br/>&nbsp;&nbsp;2.9&nbsp;&nbsp;更新组织架构信息服务（updateOrgInfo）
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
					<td>
						<b>如果结果集大于0.则数据格式为:</b><br>
							&nbsp;&nbsp;<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;""// 空字符串<br/>
						}<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
</table>

</center>

<%@ include file="/resource/jsp/view_down.jsp"%>	