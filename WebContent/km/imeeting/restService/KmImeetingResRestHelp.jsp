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
	<p>&nbsp;&nbsp;EKP系统提供了会议室资源的restful服务，包含会议室资源查询列表(getKmImeetingResList)、查询会议室详情(getKmimeetingResById)、
		新增会议室接口(addKmImeetingRes)、更新会议室接口(updateKmImeetingRes)、删除会议室接口(deleteKmImeetingRes)，以下是对上述接口的具体说明。
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;会议室资源查询列表(getKmImeetingResList)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;会议室资源上下文（KmImeetingResContext），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>targets</td>
								<td>操作人员</td>
								<td>字符串(JSON)</td>
								<td>操作人员</td>
								<td>为空则查询所有会议室资源列表。
									不为空则查询当前操作人可查看的会议室资源列表
								</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingResResult），详细说明如下:</div>
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
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;查询会议室详情(getKmimeetingResById)
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
								<td>会议室ID</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingResResult），详细说明如下:</div>
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
								<td>0:失败<br/>
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
									返回状态值为1时，该值返回会议室详情的数据，格式为JSON。（具体字段参考2.2）
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.3&nbsp;&nbsp;新增会议室接口(addKmImeetingRes)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;会议室资源上下文（KmImeetingResParamterForm ），详细说明如下:</div>
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
								<td>会议室名称</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>fdDetail</td>
								<td>设备详情</td>
								<td>字符串(String)</td>
								<td>允许为空</td>
								<td>会议室设备详情</td>
							</tr>
							<tr>
								<td>fdAddressFloor</td>
								<td>会议室楼层</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>fdSeats</td>
								<td>容纳人数</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>docCreator</td>
								<td>创建人员</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>数据格式为JSON(单值)，格式描述请查看"<a href="#orgInfo">《2.1  组织架构数据说明》</a>"
								</td>
							</tr>
							<tr>
								<td>docKeeper</td>
								<td>会议室保管人</td>
								<td>字符串(JSON)</td>
								<td>可为空</td>
								<td>数据格式为JSON(单值)，格式描述请查看"<a href="#orgInfo">《2.1  组织架构数据说明》</a>"</td>
							</tr>
							<tr>
								<td>fdIsAvailable</td>
								<td>是否有效</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>true:有效 （默认）<br/>
									false:无效</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>排序号</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td></td>
							</tr>
							<tr>
								<td>docCategoryId</td>
								<td>所属分类</td>
								<td>字符串（String）</td>
								<td>不允许为空</td>
								<td>所属分类id</td>
							</tr>
							<tr>
								<td>fdUserTime</td>
								<td>最大使用时长(小时)</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td>空值和0代表不限使用时长</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingResResult），详细说明如下:</div>
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
								<td>0:失败<br/>
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。<br/>
									返回状态值为1时，该值返回空
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.4&nbsp;&nbsp;更新会议室接口(updateKmImeetingRes)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;会议室信息上下文（KmImeetingResParamterForm ），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>会议室fdId</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>会议室的唯一标识</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>会议室名称</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>fdDetail</td>
								<td>设备详情</td>
								<td>字符串(String)</td>
								<td>允许为空</td>
								<td>会议室设备详情</td>
							</tr>
							<tr>
								<td>fdAddressFloor</td>
								<td>会议室楼层</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>fdSeats</td>
								<td>容纳人数</td>
								<td>数字(int)</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>docCreator</td>
								<td>创建人员</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>数据格式为JSON(单值)，格式描述请查看"<a href="#orgInfo">《2.1  组织架构数据说明》</a>"
								</td>
							</tr>
							<tr>
								<td>docKeeper</td>
								<td>会议室保管人</td>
								<td>字符串(JSON)</td>
								<td>可为空</td>
								<td>数据格式为JSON(单值)，格式描述请查看"<a href="#orgInfo">《2.1  组织架构数据说明》</a>"</td>
							</tr>
							<tr>
								<td>fdIsAvailable</td>
								<td>是否有效</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>true:有效 （默认）<br/>
									false:无效</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>排序号</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td></td>
							</tr>
							<tr>
								<td>docCategoryId</td>
								<td>所属分类</td>
								<td>字符串（String）</td>
								<td>不允许为空</td>
								<td>所属分类id</td>
							</tr>
							<tr>
								<td>fdUserTime</td>
								<td>最大使用时长(小时)</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td>空值和0代表不限使用时长</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingResResult），详细说明如下:</div>
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
								<td>0:失败<br/>
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。<br/>
									返回状态值为1时，该值返回空。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.5&nbsp;&nbsp;删除会议室接口(deleteKmImeetingRes)
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">
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
								<td>会议室fdId</td>
								<td>字符串(String)</td>
								<td>不允许为空</td>
								<td>会议室的唯一标识</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（KmImeetingResResult），详细说明如下:</div>
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
								<td>0:失败<br/>
									1:成功
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串(String)</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值错误信息。<br/>
									返回状态值为1时，该值返回空。
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
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="immetingResInfo"><br/>&nbsp;&nbsp;2.2&nbsp;&nbsp;会议室详情返回格式说明
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
							&nbsp;	"count":"100" //返回结果总条数 <br/>
							&nbsp;&nbsp;"resultState":"1"//返回结果 1表示成功0表示失败<br/>
								"message":[ //返回数据信息<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
								"fdId":"123443debc"， //会议室ID<br/>
								"fdName":"405会议室"， //会议室名称<br/>
								"fdDetail":"投影仪、显示器..."， //设备详情<br/>
								"fdAddressFloor":"4"， //所在楼层<br/>
								"fdSeats":"150"， //容纳人数<br/>
								"docCreator":"管理员"， //创建人<br/>
								"docKeeper":"张三"， //会议室保管人<br/>
								"fdIsAvailable":"true"， //是否有效<br/>
								"fdOrder":"1"， //排序号<br/>
								"docCategory":"公共会议室类"， //所属分类<br/>
								"fdUserTime":"1"， //最大使用时长(小时)<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;}，<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;……<br/>
							&nbsp;&nbsp;]<br/>
						}<br/>
						<b>没有结果集的时候返回信息:</b><br/>
						{<br/>
						"count":"0", //返回结果总条数<br/>
						"resultState":"0"， // 返回结果<br/>
						"message":"很抱歉，未找到相应的记录！" //错误信息<br/>
						}
						<b>必要参数不能为空返回信息：:</b><br/>
						{<br/>
						"resultState":"0"， // 返回结果<br/>
						"message":"会议预定ID不能为空" //错误信息<br/>
						}
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</center>

<%@ include file="/resource/jsp/view_down.jsp"%>