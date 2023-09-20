<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|optbar.js");
</script>
<script type="text/javascript">
document.title = "生态组织接入接出";
function expandMethod(domObj) {
	var thisObj = $(domObj);
	var isExpand = thisObj.attr("isExpanded");
	if(isExpand == null)
		isExpand = "0";
	var trObj=thisObj.parents("tr");
	trObj = trObj.next("tr");
	var imgObj = thisObj.find("img");
	if(trObj.length > 0){
		if(isExpand=="0"){
			trObj.show();
			thisObj.attr("isExpanded","1");
			imgObj.attr("src","${KMSS_Parameter_StylePath}icons/collapse.gif");
		}else{
			trObj.hide();
			thisObj.attr("isExpanded","0");
			imgObj.attr("src","${KMSS_Parameter_StylePath}icons/expand.gif");
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
	<p>&nbsp;&nbsp;EKP系统提供了生态组织架构接入接出RestFul接口服务，包含两部分，获取生态组织(findEcoElement)和保存（新增/更新）生态组织（saveEcoElement）。以下是对该接口的具体说明。</br>
	      
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" isExpanded="0" onclick="expandMethod(this);">&nbsp;&nbsp;1.1获取生态组织（/api/sys-organization/sysSynchroEco/findEcoElement）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;接入body数据（JSON对象），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>id</td>
								<td>ID</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td>
									根据ID获取组织类型、组织、岗位、人员数据
	 							</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>上级组织ID</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td>
									以传入的parent为条件，获取该组织下的所有直接组织，包括：子组织，岗位，人员
								</td>
							</tr>
							<tr>
								<td>isAvailable</td>
								<td>是否有效</td>
								<td>布尔（Boolean）</td>
								<td>允许为空</td>
								<td>
									为空查询所有数据，true：仅查询有效数据，false：仅查询无效数据（与id参数互斥）
								</td>
							</tr>
							<tr>
								<td>returnOrgType</td>
								<td>返回的数据类型</td>
								<td>字符串（JSONArray）</td>
								<td>允许为空</td>
								<td>
									格式：[{'type':'person'},{'type':'post'},{'type':'dept'}]<br>
									为空返回所有类型（仅与parent参数结合使用）
								</td>
							</tr>
							<tr>
								<td>search</td>
								<td>增强搜索条件</td>
								<td>字符串（JSONObject）</td>
								<td>允许为空</td>
								<td>
									格式：
									<pre>
{
    "fdName": "精确搜索字符串",       // 默认精确搜索
     "fdNo": {
        "opt": "eq/like",         // 操作符，指定“精确”或“模糊”搜索，这里只能填写 eq 或 like
        "value": "搜索内容"         // 查询内容
    },
    ......
}
									</pre>
									<br>
									JSONObject对象中，KEY为搜索字段，目前仅支持以下字段：
									<ul style="padding-left: 30px;">
									    <li>fdName：名称（组织、岗位、人员）</li>
									    <li>fdNo：编号（组织、岗位、人员）</li>
									    <li>fdLoginName：登录名（人员）</li>
									    <li>fdMobileNo：手机号（人员）</li>
									    <li>fdEmail：邮箱（人员）</li>
									</ul>
									<br>
									为空时不追加搜索条件，（与id和parent互斥）
								</td>
							</tr>
							<tr>
								<td colspan="5">
									<font color="red">如果调用该接口不传入任何参数，将返回所有根组织数据。</font>
								</td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%" rowspan="2">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回body数据（JSON对象），详细说明如下:</div>
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
								<td>数字（int）</td>
								<td>不允许为空</td>
								<td>0:表示未操作。<br/>
									1:表示操作失败。<br/>
									2:表示操作成功。
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值返回空。<br/>
									返回状态值为1时，该值错误信息。<br/>
									返回状态值为2时，该值返回数据。
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<div style="margin: 8px;font-weight: bold;">&nbsp;&nbsp;1. 返回组织类型数据:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>id</td>
								<td>组织类型ID</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>name</td>
								<td>组织类型名称</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>isAvailable</td>
								<td>是否有效</td>
								<td>布尔（Boolean）</td>
								<td></td>
							</tr>
							<tr>
								<td>no</td>
								<td>编号</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>order</td>
								<td>排序号</td>
								<td>数字（int）</td>
								<td></td>
							</tr>
							<tr>
								<td>admins</td>
								<td>管理员</td>
								<td>对象数组（array）</td>
								<td>格式为：[{"id":"17452caaefcceb373f08e2f4b58a7d2b", "name":"管理员"}]</td>
							</tr>
							<tr>
								<td>readers</td>
								<td>组织管理员</td>
								<td>对象数组（array）</td>
								<td>格式为：[{"id":"17452caaefcceb373f08e2f4b58a7d2b", "name":"组织管理员"}]</td>
							</tr>
							<tr>
								<td>deptProps</td>
								<td>组织扩展属性</td>
								<td>对象数组（array）</td>
								<td rowspan="2">
									单个对象格式如下：<br>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
											<td align="center" width="55%"><b>备注说明</b></td>
										</tr>
										<tr>
											<td>type</td>
											<td>属性类型</td>
											<td>字符串（String）</td>
											<td>属性类型（组织/人员）(dept/person)</td>
										</tr>
										<tr>
											<td>name</td>
											<td>显示名称</td>
											<td>字符串（String）</td>
											<td></td>
										</tr>
										<tr>
											<td>order</td>
											<td>排序号</td>
											<td>数字（int）</td>
											<td></td>
										</tr>
										<tr>
											<td>field</td>
											<td>属性名称</td>
											<td>字符串（String）</td>
											<td>程序中使用，如果要更新此扩展属性时，需要传该属性名称和属性值</td>
										</tr>
										<tr>
											<td>column</td>
											<td>字段名称</td>
											<td>字符串（String）</td>
											<td>数据库使用</td>
										</tr>
										<tr>
											<td>fieldType</td>
											<td>数据类型</td>
											<td>字符串（String）</td>
											<td>数据类型【字符串：java.lang.String、整数：java.lang.Integer、浮点：java.lang.Double、日期：java.util.Date】</td>
										</tr>
										<tr>
											<td>fieldLength</td>
											<td>字段长度</td>
											<td>数字（int）</td>
											<td></td>
										</tr>
										<tr>
											<td>scale</td>
											<td>精度</td>
											<td>数字（int）</td>
											<td>适用于浮点类型</td>
										</tr>
										<tr>
											<td>required</td>
											<td>是否必填</td>
											<td>布尔（Boolean）</td>
											<td></td>
										</tr>
										<tr>
											<td>status</td>
											<td>是否启用</td>
											<td>布尔（Boolean）</td>
											<td></td>
										</tr>
										<tr>
											<td>showList</td>
											<td>是否列表展示</td>
											<td>布尔（Boolean）</td>
											<td>后台管理列表页面显示该字段</td>
										</tr>
										<tr>
											<td>displayType</td>
											<td>显示的类型</td>
											<td>字符串（String）</td>
											<td>
												字符串【单行文本框：text、多行文本框：textarea、单选按钮：radio、复选框：checkbox、下拉列表：select】<br>
                                                整数【单行文本框：text、单选按钮：radio、下拉列表：select】<br>
                                                浮点【单行文本框：text】<br>
                                                日期【日期时间：datetime、日期：date、时间：time】<br>
											</td>
										</tr>
										<tr>
											<td>fieldEnums</td>
											<td>枚举集合</td>
											<td>JSON数组（JSONArray）</td>
											<td>
												<table class="tb_normal" width=100%>
													<tr class="tr_normal_title">
														<td align="center" width="10%"><b>程序名</b></td>
														<td align="center" width="10%"><b>描述</b></td>
														<td align="center" width="10%"><b>类型</b></td>
													</tr>
													<tr>
														<td>order</td>
														<td>排序号</td>
														<td>数字（int）</td>
													</tr>
													<tr>
														<td>name</td>
														<td>显示名称</td>
														<td>字符串（String）</td>
													</tr>
													<tr>
														<td>value</td>
														<td>枚举值</td>
														<td>字符串（String）</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>personProps</td>
								<td>人员扩展属性</td>
								<td>对象数组（array）</td>
							</tr>
						</table>
						
						<div style="margin: 8px;font-weight: bold;">&nbsp;&nbsp;2. 返回组织数据:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>id</td>
								<td>组织类型ID</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>type</td>
								<td>组织类型</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>name</td>
								<td>组织类型名称</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>isAvailable</td>
								<td>是否有效</td>
								<td>布尔（Boolean）</td>
								<td></td>
							</tr>
							<tr>
								<td>no</td>
								<td>编号</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>order</td>
								<td>排序号</td>
								<td>数字（int）</td>
								<td></td>
							</tr>
							<tr>
								<td>cate</td>
								<td>组织类型</td>
								<td>JSON对象（JSONObject）</td>
								<td>格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>所在组织</td>
								<td>JSON对象（JSONObject）</td>
								<td>格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>admins</td>
								<td>负责人</td>
								<td>JSON数组（JSONArray）</td>
								<td>格式：[{"id":"xxx", "name":"xxx"}]</td>
							</tr>
							<tr>
								<td>range</td>
								<td>组织查看范围</td>
								<td>JSON对象（JSONObject）</td>
								<td>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
											<td align="center" width="55%"><b>备注说明</b></td>
										</tr>
										<tr>
											<td>state</td>
											<td>状态</td>
											<td>布尔（Boolean）</td>
											<td>是否开启（生态组织强制开启）</td>
										</tr>
										<tr>
											<td>type</td>
											<td>查看类型</td>
											<td>数字（Integer）</td>
											<td>0：仅自己，1：仅所在组织及下级组织/人员，2：指定组织/人员</td>
										</tr>
										<tr>
											<td>subType</td>
											<td>查看子类型</td>
											<td>数字（Integer）</td>
											<td>当类型为2时，子类型可以设置【1：所在组织及下级组织/人员，2：其他组织/人员】</td>
										</tr>
										<tr>
											<td>others</td>
											<td>其他可查看范围</td>
											<td>JSON数组（JSONArray）</td>
											<td>当子类型选择2时，可以设置其他可查看范围，格式：[{"id":"xxx", "name":"xxx"}]</td>
										</tr>
										<tr>
											<td>url</td>
											<td>钉钉邀请成员时地址</td>
											<td>字符串（String）</td>
											<td></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>extProps</td>
								<td>扩展属性</td>
								<td>JSON数组（JSONArray）</td>
								<td>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
										</tr>
										<tr>
											<td>name</td>
											<td>属性显示名称</td>
											<td>字符串（String）</td>
										</tr>
										<tr>
											<td>field</td>
											<td>属性名称</td>
											<td>字符串（String）</td>
										</tr>
										<tr>
											<td>value</td>
											<td>属性值</td>
											<td>字符串（String）</td>
										</tr>
										<tr>
											<td>text</td>
											<td>枚举属性显示名称</td>
											<td>字符串（String）</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						
						<div style="margin: 8px;font-weight: bold;">&nbsp;&nbsp;3. 返回岗位数据:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>id</td>
								<td>组织类型ID</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>type</td>
								<td>组织类型</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>name</td>
								<td>组织类型名称</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>isAvailable</td>
								<td>是否有效</td>
								<td>布尔（Boolean）</td>
								<td></td>
							</tr>
							<tr>
								<td>no</td>
								<td>编号</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>order</td>
								<td>排序号</td>
								<td>数字（int）</td>
								<td></td>
							</tr>
							<tr>
								<td>cate</td>
								<td>组织类型</td>
								<td>JSON对象（JSONObject）</td>
								<td>格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>所在组织</td>
								<td>JSON对象（JSONObject）</td>
								<td>格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>thisLeader</td>
								<td>岗位领导</td>
								<td>JSON对象（JSONObject）</td>
								<td>格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>persons</td>
								<td>人员列表</td>
								<td>JSON数组（JSONArray）</td>
								<td>格式：[{"id":"xxx", "name":"xxx"}]</td>
							</tr>
							<tr>
								<td>memo</td>
								<td>备注</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
						</table>
						
						<div style="margin: 8px;font-weight: bold;">&nbsp;&nbsp;4. 返回人员数据:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>id</td>
								<td>组织类型ID</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>type</td>
								<td>组织类型</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>name</td>
								<td>组织类型名称</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>isAvailable</td>
								<td>是否有效</td>
								<td>布尔（Boolean）</td>
								<td></td>
							</tr>
							<tr>
								<td>no</td>
								<td>编号</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>order</td>
								<td>排序号</td>
								<td>数字（int）</td>
								<td></td>
							</tr>
							<tr>
								<td>cate</td>
								<td>组织类型</td>
								<td>JSON对象（JSONObject）</td>
								<td>格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>所在组织</td>
								<td>JSON对象（JSONObject）</td>
								<td>格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>loginName</td>
								<td>登录名</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>mobileNo</td>
								<td>手机号码</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>email</td>
								<td>邮件地址</td>
								<td>字符串（String）</td>
								<td></td>
							</tr>
							<tr>
								<td>posts</td>
								<td>岗位列表</td>
								<td>JSON数组（JSONArray）</td>
								<td>格式：[{"id":"xxx", "name":"xxx"}]</td>
							</tr>
							<tr>
								<td>extProps</td>
								<td>扩展属性</td>
								<td>JSON数组（JSONArray）</td>
								<td>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
										</tr>
										<tr>
											<td>name</td>
											<td>属性显示名称</td>
											<td>字符串（String）</td>
										</tr>
										<tr>
											<td>field</td>
											<td>属性名称</td>
											<td>字符串（String）</td>
										</tr>
										<tr>
											<td>value</td>
											<td>属性值</td>
											<td>字符串（String）</td>
										</tr>
										<tr>
											<td>text</td>
											<td>枚举属性显示名称</td>
											<td>字符串（String）</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.2 保存生态组织（/api/sys-organization/sysSynchroEco/saveEcoElement）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;font-weight: bold;font-size: 16px;">&nbsp;&nbsp;批量新增或更新生态组织，当没有传入ID或ID不存在时，执行新增操作；当ID存在时，执行更新操作；详细说明如下:</div>
						<div style="background-color: aliceblue">
							<pre>
							{
								"orgs": [
									{
										"id": "1234567890",
										"name": "xxx"
									},
									......
								]
							}
							</pre>
						</div>
						
						<br><bbr>
						
						<div style="margin: 8px;font-weight: bold;">&nbsp;&nbsp;1. 保存组织:</div>
						<div style="background-color: aliceblue">
							<pre>
							{
							    "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							    "name": "组织",
							    "type": "dept",
							    "no": "NO.001",
							    "order": 1,
							    "isAvailable": true,
							    "parent": {
							        "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							        "name": "上级组织"
							    },
							    "admins": [
							        {
							            "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							            "name": "负责人"
							        }
							    ],
							    "range": {
							        "state": true,
							        "type": 2,
							        "subType": "1;2",
							        "others": [
							            {
							                "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							                "name": "组织1"
							            },
							            {
							                "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							                "name": "人员1"
							            }
							        ]
							        "url": "http://xxxxxxxxxxxx"
							    },
							    "extProps": [
							        {
							            "field": "test01",
							            "value": "1"
							        }
							    ]
							}
							</pre>
						</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>id</td>
								<td>更新的组织ID</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>name</td>
								<td>组织名称</td>
								<td>字符串（String）</td>
								<td>不能为空</td>
								<td></td>
							</tr>
							<tr>
								<td>type</td>
								<td>组织类型</td>
								<td>字符串（String）</td>
								<td>不能为空</td>
								<td>固定值：dept</td>
							</tr>
							<tr>
								<td>no</td>
								<td>组织编号</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>order</td>
								<td>排序号</td>
								<td>数字（int）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>isAvailable</td>
								<td>是否有效</td>
								<td>布尔（Boolean）</td>
								<td>允许为空</td>
								<td>仅更新时有效，新增组织默认为有效</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>上级组织</td>
								<td>JSON对象（JSONObject）</td>
								<td>不能为空</td>
								<td>更新时不能调换到其它组织类型，格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>admins</td>
								<td>负责人</td>
								<td>JSON数组（JSONArray）</td>
								<td>允许为空</td>
								<td>格式：[{"id":"xxx", "name":"xxx"}]</td>
							</tr>
							<tr>
								<td>range</td>
								<td>查看组织范围</td>
								<td>JSON对象（JSONObject）</td>
								<td>允许为空</td>
								<td>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
											<td align="center" width="15%"><b>可否为空</b></td>
											<td align="center" width="55%"><b>备注说明</b></td>
										</tr>
										<tr>
											<td>type</td>
											<td>查看类型</td>
											<td>数字（int）</td>
											<td>不能为空</td>
											<td>0：仅自己，1：仅所在组织及下级组织/人员，2：指定组织/人员</td>
										</tr>
										<tr>
											<td>subType</td>
											<td>子类型</td>
											<td>字符串（String）</td>
											<td>当type=2时，不能为空</td>
											<td>1：所在组织及下级组织/人员，2：其他组织/人员<br>可多选：1;2</td>
										</tr>
										<tr>
											<td>others</td>
											<td>子类型</td>
											<td>JSON数组（JSONArray）</td>
											<td>允许为空</td>
											<td>当subType=2时，可以设置其他查看范围，格式：[{"id":"xxx", "name":"xxx"}]</td>
										</tr>
										<tr>
											<td>url</td>
											<td>钉钉邀请成员时地址</td>
											<td>字符串（String）</td>
											<td>允许为空</td>
											<td>需要在钉钉端查看部门的邀请二维码地址</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>extProps</td>
								<td>扩展属性</td>
								<td>JSON数组（JSONArray）</td>
								<td>允许为空</td>
								<td>
									<div style="color: red;">如果不传入扩展属性，则不对其进行校验，否则会对该组织所有开启的扩展属性进行校验。如果不想对某个属性修改，可以不传该属性。</div>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
										</tr>
										<tr>
											<td>field</td>
											<td>属性名称</td>
											<td>字符串（String）</td>
										</tr>
										<tr>
											<td>value</td>
											<td>属性值</td>
											<td>字符串（String）</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						
						<div style="margin: 8px;font-weight: bold;">&nbsp;&nbsp;2. 保存岗位:</div>
						<div style="background-color: aliceblue">
							<pre>
							{
							    "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							    "name": "岗位",
							    "type": "post",
							    "no": "NO.001",
							    "order": 1,
							    "isAvailable": true,
							    "parent": {
							        "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							        "name": "上级组织"
							    },
							    "thisLeader": {
							        "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							        "name": "岗位领导"
							    },
							    "persons": [
							        {
							            "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							            "name": "人员1"
							        },
							        {
							            "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							            "name": "人员2"
							        }
							    ],
							    "memo": "备注信息"
							}
							</pre>
						</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>id</td>
								<td>更新的岗位ID</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>name</td>
								<td>岗位名称</td>
								<td>字符串（String）</td>
								<td>不能为空</td>
								<td></td>
							</tr>
							<tr>
								<td>type</td>
								<td>岗位类型</td>
								<td>字符串（String）</td>
								<td>不能为空</td>
								<td>固定值：post</td>
							</tr>
							<tr>
								<td>no</td>
								<td>岗位编号</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>order</td>
								<td>排序号</td>
								<td>数字（int）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>isAvailable</td>
								<td>是否有效</td>
								<td>布尔（Boolean）</td>
								<td>允许为空</td>
								<td>仅更新时有效，新增岗位默认为有效</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>上级组织</td>
								<td>JSON对象（JSONObject）</td>
								<td>不能为空</td>
								<td>更新时不能调换到其它组织类型，格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>thisLeader</td>
								<td>岗位领导</td>
								<td>JSON对象（JSONObject）</td>
								<td>允许为空</td>
								<td>格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>persons</td>
								<td>人员</td>
								<td>JSON数组（JSONArray）</td>
								<td>允许为空</td>
								<td>格式：[{"id":"xxx", "name":"xxx"}]</td>
							</tr>
							<tr>
								<td>memo</td>
								<td>备注信息</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
						</table>
						
						<div style="margin: 8px;font-weight: bold;">&nbsp;&nbsp;3. 保存人员:</div>
						<div style="background-color: aliceblue">
							<pre>
							{
							    "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							    "name": "人员",
							    "type": "person",
							    "no": "NO.001",
							    "order": 1,
							    "isAvailable": true,
							    "parent": {
							        "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							        "name": "上级组织"
							    },
							    "loginName": "test01",
							    "password": "1",
							    "mobileNo": "13888888888",
							    "email": "13888888888@139.com",
							    "posts": [
							        {
							            "id": "1746d2e06a77a0fbfe44c8f40c4b3fbf",
							            "name": "岗位"
							        }
							    ],
							    "extProps": [
							        {
							            "field": "test01",
							            "value": "1"
							        }
							    ]
							}
							</pre>
						</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>id</td>
								<td>更新的人员ID</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>name</td>
								<td>姓名</td>
								<td>字符串（String）</td>
								<td>不能为空</td>
								<td></td>
							</tr>
							<tr>
								<td>type</td>
								<td>人员类型</td>
								<td>字符串（String）</td>
								<td>不能为空</td>
								<td>固定值：person</td>
							</tr>
							<tr>
								<td>no</td>
								<td>人员编号</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>order</td>
								<td>排序号</td>
								<td>数字（int）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>isAvailable</td>
								<td>是否有效</td>
								<td>布尔（Boolean）</td>
								<td>允许为空</td>
								<td>仅更新时有效，新增组织默认为有效</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>上级组织</td>
								<td>JSON对象（JSONObject）</td>
								<td>不能为空</td>
								<td>更新时不能调换到其它组织类型，格式：{"id":"xxx", "name":"xxx"}</td>
							</tr>
							<tr>
								<td>posts</td>
								<td>岗位列表</td>
								<td>JSON数组（JSONArray）</td>
								<td>允许为空</td>
								<td>格式：[{"id":"xxx", "name":"xxx"}]</td>
							</tr>
							<tr>
								<td>loginName</td>
								<td>登录名</td>
								<td>字符串（String）</td>
								<td>不能为空</td>
								<td></td>
							</tr>
							<tr>
								<td>password</td>
								<td>密码</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td>仅新增时使用，为空则取后台配置的默认初始密码</td>
							</tr>
							<tr>
								<td>mobileNo</td>
								<td>手机号</td>
								<td>字符串（String）</td>
								<td>不能为空</td>
								<td></td>
							</tr>
							<tr>
								<td>email</td>
								<td>邮箱地址</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td></td>
							</tr>
							<tr>
								<td>extProps</td>
								<td>扩展属性</td>
								<td>JSON数组（JSONArray）</td>
								<td>允许为空</td>
								<td>
									<div style="color: red;">如果不传入扩展属性，则不对其进行校验，否则会对该组织所有开启的扩展属性进行校验。如果不想对某个属性修改，可以不传该属性。</div>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
										</tr>
										<tr>
											<td>field</td>
											<td>属性名称</td>
											<td>字符串（String）</td>
										</tr>
										<tr>
											<td>value</td>
											<td>属性值</td>
											<td>字符串（String）</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回body数据（JSON对象），详细说明如下:</div>
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
								<td>数字（int）</td>
								<td>不允许为空</td>
								<td>0:表示未操作。<br/>
									1:表示操作失败。<br/>
									2:表示操作成功。
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值返回空。<br/>
									返回状态值为1时，该值错误信息。<br/>
									返回状态值为2时，该值返回空。
								</td>
							</tr>
							<tr>
								<td>success</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回保存成功的数据
								</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回信息</td>
								<td>字符串（int）</td>
								<td>可为空</td>
								<td>
									返回保存成功的数量
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.3 生态组织（内转外）（/api/sys-organization/sysSynchroEco/updateInToOut）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;font-weight: bold;font-size: 16px;">&nbsp;&nbsp;</div>当某个人员离职后，可以调用此接口将该人员归属到某个生态组织中，处理逻辑是将该人员原来的账号禁用，
							同时创建一个生态账号，将原有的基础信息复制到新创建的生态账号上，该用户可以无感知的使用原来的登录名与密码登录系统，此时登录后的账号是新创建的生态，原来的所有权限将不再拥有，调用的参数如下：
						<div style="background-color: aliceblue">
							<pre>
							{
							  "orgs": [
							    {
							      "id": "17669522e3d510f64022e1e4a5faf556",
							      "parent": {
							        "id": "17660530f4821d07cd6d4674422ac7bd"
							      }
							    }
							  ],
							  "parent": "17660530f4821d07cd6d4674422ac7bd(通用父组织)"
							}
							</pre>
						</div>
						
						<br><bbr>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>orgs</td>
								<td>待处理的人员列表</td>
								<td>JSON数组（JSONArray）</td>
								<td>不能为空</td>
								<td>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
											<td align="center" width="50%"><b>备注说明</b></td>
										</tr>
										<tr>
											<td>id</td>
											<td>人员ID</td>
											<td>字符串（String）</td>
											<td></td>
										</tr>
										<tr>
											<td>value</td>
											<td>属性值</td>
											<td>JSON对象（JSONObject）</td>
											<td>新上级组织，如果这里没有设置新上级组织，统一使用公共parent，格式：{"id":"xxx", "name":"xxx"}</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>新上级组织ID</td>
								<td>字符串</td>
								<td>允许为空</td>
								<td>当人员列表中没有指定parent时，统一使用这里的信息</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回body数据（JSON对象），详细说明如下:</div>
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
								<td>数字（int）</td>
								<td>不允许为空</td>
								<td>0:表示未操作。<br/>
									1:表示操作失败。<br/>
									2:表示操作成功。
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值返回空。<br/>
									返回状态值为1时，该值错误信息。<br/>
									返回状态值为2时，该值返回空。
								</td>
							</tr>
							<tr>
								<td>success</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回保存成功的数据
								</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回信息</td>
								<td>字符串（int）</td>
								<td>可为空</td>
								<td>
									返回保存成功的数量
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.4 生态组织（外转内）（/api/sys-organization/sysSynchroEco/updateOutToIn）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;font-weight: bold;font-size: 16px;">&nbsp;&nbsp;</div>当某个人员应聘人员入职后，可以通过此接口调整为内部人员，调用的参数如下：
						<div style="background-color: aliceblue">
							<pre>
							{
							  "orgs": [
							    {
							      "id": "17669522e3d510f64022e1e4a5faf556",
							      "parent": {
							        "id": "17660530f4821d07cd6d4674422ac7bd"
							      }
							    }
							  ],
							  "parent": "17660530f4821d07cd6d4674422ac7bd(通用父组织)"
							}
							</pre>
						</div>
						
						<br><bbr>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>orgs</td>
								<td>待处理的人员列表</td>
								<td>JSON数组（JSONArray）</td>
								<td>不能为空</td>
								<td>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
											<td align="center" width="50%"><b>备注说明</b></td>
										</tr>
										<tr>
											<td>id</td>
											<td>人员ID</td>
											<td>字符串（String）</td>
											<td></td>
										</tr>
										<tr>
											<td>value</td>
											<td>属性值</td>
											<td>JSON对象（JSONObject）</td>
											<td>新上级组织，如果这里没有设置新上级组织，统一使用公共parent，格式：{"id":"xxx", "name":"xxx"}</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>新上级组织ID</td>
								<td>字符串</td>
								<td>允许为空</td>
								<td>当人员列表中没有指定parent时，统一使用这里的信息</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回body数据（JSON对象），详细说明如下:</div>
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
								<td>数字（int）</td>
								<td>不允许为空</td>
								<td>0:表示未操作。<br/>
									1:表示操作失败。<br/>
									2:表示操作成功。
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值返回空。<br/>
									返回状态值为1时，该值错误信息。<br/>
									返回状态值为2时，该值返回空。
								</td>
							</tr>
							<tr>
								<td>success</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回保存成功的数据
								</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回信息</td>
								<td>字符串（int）</td>
								<td>可为空</td>
								<td>
									返回保存成功的数量
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.5 生态组织（外转外）（/api/sys-organization/sysSynchroEco/updateOutToOut）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;font-weight: bold;font-size: 16px;">&nbsp;&nbsp;</div>生态人员之间允许相互调整，但是调整后，原来的扩展数据将不再拥有，调用的参数如下：
						<div style="background-color: aliceblue">
							<pre>
							{
							  "orgs": [
							    {
							      "id": "17669522e3d510f64022e1e4a5faf556",
							      "parent": {
							        "id": "17660530f4821d07cd6d4674422ac7bd"
							      }
							    }
							  ],
							  "parent": "17660530f4821d07cd6d4674422ac7bd(通用父组织)"
							}
							</pre>
						</div>
						
						<br><bbr>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>orgs</td>
								<td>待处理的人员列表</td>
								<td>JSON数组（JSONArray）</td>
								<td>不能为空</td>
								<td>
									<table class="tb_normal" width=100%>
										<tr class="tr_normal_title">
											<td align="center" width="10%"><b>程序名</b></td>
											<td align="center" width="10%"><b>描述</b></td>
											<td align="center" width="10%"><b>类型</b></td>
											<td align="center" width="50%"><b>备注说明</b></td>
										</tr>
										<tr>
											<td>id</td>
											<td>人员ID</td>
											<td>字符串（String）</td>
											<td></td>
										</tr>
										<tr>
											<td>value</td>
											<td>属性值</td>
											<td>JSON对象（JSONObject）</td>
											<td>新上级组织，如果这里没有设置新上级组织，统一使用公共parent，格式：{"id":"xxx", "name":"xxx"}</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>parent</td>
								<td>新上级组织ID</td>
								<td>字符中</td>
								<td>允许为空</td>
								<td>当人员列表中没有指定parent时，统一使用这里的信息</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回body数据（JSON对象），详细说明如下:</div>
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
								<td>数字（int）</td>
								<td>不允许为空</td>
								<td>0:表示未操作。<br/>
									1:表示操作失败。<br/>
									2:表示操作成功。
								</td>
							</tr>
							<tr>
								<td>message</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回状态值为0时，该值返回空。<br/>
									返回状态值为1时，该值错误信息。<br/>
									返回状态值为2时，该值返回空。
								</td>
							</tr>
							<tr>
								<td>success</td>
								<td>返回信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									返回保存成功的数据
								</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回信息</td>
								<td>字符串（int）</td>
								<td>可为空</td>
								<td>
									返回保存成功的数量
								</td>
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