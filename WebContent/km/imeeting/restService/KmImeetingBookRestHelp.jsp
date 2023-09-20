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
	<p>&nbsp;&nbsp;EKP系统提供了会议室预定信息restful服务，包含了获取所有会议室预定列表（getImeetingBookLists）、查询会议预定信息（getImeetingBookDetail）、
		根据会议室ID查询该会议室所有预定信息（getImeetingBookById）、查询指定人员的会议预定（getImeetingBook）、预约会议室（addImeetingBook）、
		删除会议室预定(deleteImeetingBook)、变更会议室预定(updateImeetingBook)等7个接口，以下是对上述接口的具体说明。
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;获取所有会议室预定列表（getImeetingBookLists）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>fdHoldDate</td>
								<td>召开时间</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>格式为:yyyy-MM-dd HH:mm:ss
								</td>
							</tr>
							<tr>
								<td>fdFinishDate</td>
								<td>结束时间</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>格式为:yyyy-MM-dd HH:mm:ss
								</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingBookResulut），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>resultState</td>
								<td>返回状态</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td>0:失败
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。
									返回状态值为1时，该值返回空。
								</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回数据的总条目</td>
								<td>数字(int)</td>
								<td>可为0</td>
								<td>
									返回数据的条目数
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;根据会议室预订ID查询预定详细信息（getImeetingBookDetail）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>会议室预定ID</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingBookResulut），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>resultState</td>
								<td>返回状态</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td>0:失败
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。
									返回状态值为1时，该值返回空。
								</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回数据的总条目</td>
								<td>数字(int)</td>
								<td>可为0</td>
								<td>
									返回数据的条目数
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.3&nbsp;&nbsp;根据会议室ID查询该会议室所有预定信息（getImeetingBookById）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>会议室ID</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingBookResulut），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>resultState</td>
								<td>返回状态</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td>0:失败
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。
									返回状态值为1时，该值返回空。
								</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回数据的总条目</td>
								<td>数字(int)</td>
								<td>可为0</td>
								<td>
									返回数据的条目数
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.4&nbsp;&nbsp;查询指定人员的会议预定（getImeetingBook）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
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
					          <TD>会议室预定人</TD>
					          <TD>字符串(JSON)</TD>
					          <TD>不允许为空</TD>
					          <TD>
								  表示获取指定人员的会议预定列表
					          </TD></TR>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingBookResulut），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>resultState</td>
								<td>返回状态</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td>0:失败
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。
									返回状态值为1时，该值返回会议室预定详情的数据，格式描述请查看"<a href="#kmImeetingBookInfo">《2.2 会议室预定返回格式说明》</a>"</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.5&nbsp;&nbsp;预约会议室（addImeetingBook）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;会议室预定上下文（KmImeetingBookParamterForm），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							 <tr>
					          <td>fdName</td>
					          <td>会议名称</td>
					          <td>字符串（String）</td>
					          <td>不允许为空</td>
					          <td></td>
							 <tr/>
					        <tr>
					          <TD>fdHoldDate</TD>
					          <TD>召开时间</TD>
					          <TD>字符串（String）</TD>
					          <TD>不允许为空</TD>
					          <td>格式为:yyyy-MM-dd HH:mm:ss</td>
					        </tr>
							<tr>
								<TD>fdFinishDate</TD>
								<TD>结束时间</TD>
								<TD>字符串（String）</TD>
								<TD>不允许为空</TD>
								<td>格式为:yyyy-MM-dd HH:mm:ss</td>
							</tr>
							<tr>
								<TD>fdHoldDuration</TD>
								<TD>会议历时</TD>
								<TD>字符串（String）</TD>
								<TD>允许为空</TD>
								<td></td>
							</tr>
							<tr>
								<TD>fdPlace</TD>
								<TD>会议地点</TD>
								<TD>字符串（String）</TD>
								<TD>不允许为空</TD>
								<td>会议室FdId</td>
							</tr>
							<tr>
								<TD>fdRemark</TD>
								<TD>备注</TD>
								<TD>字符串（String）</TD>
								<TD>不允许为空</TD>
								<td></td>
							</tr>
							<tr>
								<TD>docCreator</TD>
								<TD>会议登记人</TD>
								<TD>字符串(JSON)</TD>
								<TD>不允许为空</TD>
								<td>数据格式为JSON(单值)，格式描述请查看"<a href="#orgInfo">《2.1  组织架构数据说明》</a>"</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingBookResulut），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>resultState</td>
								<td>返回状态</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td>0:失败
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。<br>
									返回状态值为1时，该值返回空。</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回数据的总条目</td>
								<td>数字(int)</td>
								<td>可为0</td>
								<td>
									返回数据的条目数
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.6&nbsp;&nbsp;删除会议室预定(deleteImeetingBook)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>会议预定ID</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>会议预定的唯一标识</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingBookResulut），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>resultState</td>
								<td>返回状态</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td>0:失败
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。<br>
									返回状态值为1时，该值返回空。</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回数据的总条目</td>
								<td>数字(int)</td>
								<td>可为0</td>
								<td>
									返回数据的条目数
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.7&nbsp;&nbsp;变更会议室预定(updateImeetingBook)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;会议预定上下文（KmImeetingBookParamterForm ），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>会议名称</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
							<tr>
								<TD>fdHoldDate</TD>
								<TD>召开时间</TD>
								<TD>字符串（String）</TD>
								<TD>不允许为空</TD>
								<td>格式为:yyyy-MM-dd HH:mm:ss</td>
							</tr>
							<tr>
								<TD>fdFinishDate</TD>
								<TD>结束时间</TD>
								<TD>字符串（String）</TD>
								<TD>不允许为空</TD>
								<td>格式为:yyyy-MM-dd HH:mm:ss</td>
							</tr>
							<tr>
								<TD>fdHoldDuration</TD>
								<TD>会议历时</TD>
								<TD>字符串（String）</TD>
								<TD>允许为空</TD>
								<td></td>
							</tr>
							<tr>
								<TD>fdPlace</TD>
								<TD>会议地点</TD>
								<TD>字符串（String）</TD>
								<TD>不允许为空</TD>
								<td>会议室FdId</td>
							</tr>
							<tr>
								<TD>fdRemark</TD>
								<TD>备注</TD>
								<TD>字符串（String）</TD>
								<TD>不允许为空</TD>
								<td></td>
							</tr>
							<tr>
								<TD>docCreator</TD>
								<TD>会议登记人</TD>
								<TD>字符串(JSON)</TD>
								<TD>不允许为空</TD>
								<td>数据格式为JSON(单值)，格式描述请查看"<a href="#orgInfo">《2.1  组织架构数据说明》</a>"</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingBookResulut），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>resultState</td>
								<td>返回状态</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td>0:失败
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。<br>
									返回状态值为1时，该值返回空。</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回数据的总条目</td>
								<td>数字(int)</td>
								<td>可为0</td>
								<td>
									返回数据的条目数
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br>
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
						多值格式为: [{类型1:值1}，{类型2:值2}...]，如:  [{"PersonNo":"001"}，{"KeyWord":"2EDF6"}]。
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
								<td>DeptNo</td>
								<td>EKP系统组织架构部门编号</td>
							</tr>
							<tr>
								<td>PostNo</td>
								<td>EKP系统组织架构岗位编号</td>
							</tr>
							<tr>
								<td>GroupNo</td>
								<td>EKP系统组织架构常用群组编号</td>
							</tr>
							<tr>
								<td>LoginName</td>
								<td>EKP系统组织架构个人登录名</td>
							</tr>
							<tr>
								<td>Keyword</td>
								<td>EKP系统组织架构关键字</td>
							</tr>
							<tr>
								<td>LdapDN</td>
								<td>和LDAP集成时LDAP中DN值</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="kmImeetingBookInfo"><br/>&nbsp;&nbsp;2.2&nbsp;&nbsp;会议室预定返回格式说明
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
						{<br/>
							&nbsp;&nbsp;"pageCount":"100"，                //总页数 <br/>
							&nbsp;&nbsp;"pageno":"37"，                    //当前页码<br/>
							&nbsp;&nbsp;"count":"37001"                   //文档总数<br/>
							&nbsp;&nbsp;"docs":[						  //文档列表<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"id":"123443debc"，         //EKP系统待办唯一标识 ，不为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"subject":"请处理:XXXX"，	   //标题，不为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"type":"1"，                //待办类型， 不为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"key":"reviewmain"，        //待办关键字， 可为空。<br/>
						            &nbsp;&nbsp;&nbsp;&nbsp;"param1":""，               //参数1，可为空。<br/>
						          	&nbsp;&nbsp;&nbsp;&nbsp;"param2":""，               //参数2，可为空。<br/>
						          	&nbsp;&nbsp;&nbsp;&nbsp;"appName":"HR"，			   //待办来源，可为空，为空表示为ekp系统待办。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"modelName":"com.landray.kmss.km.review.model.KmReviewMain"，        //模块名，不为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"moduleName":"流程管理"，        //模块名，可为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"modelId":"12342"，         //待办对应的主文档ID，不为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"createTime":""，           //创建时间 ，不为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"creator":"admin"，           //创建者登录名 ，可为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"creatorName":"admin"，           //创建者名称 ，可为空。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"level":"3"，           //待办优先级(1:紧急、2:急、3:一般)，不为空<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"link":"/sys/notify/sys_notify_todo.do?method=view&fdId=12345"   //不为空<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"mobileLink":"http://news.sina.com.cn/"   //可为空<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"padLink":"http://news.sina.com.cn/"   //可为空<br/>								
								&nbsp;&nbsp;&nbsp;&nbsp;}，<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;……<br/>
							&nbsp;&nbsp;]<br/>
						}<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
							&nbsp;&nbsp;"errorPage":"true"，                  // 请求数据错误<br/>
							&nbsp;&nbsp;"message":"很抱歉，未找到相应的记录！" //错误信息<br/>
						}
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>