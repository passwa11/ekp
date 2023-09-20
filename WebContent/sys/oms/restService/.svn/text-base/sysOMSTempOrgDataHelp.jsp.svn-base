<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|optbar.js");
</script> 
<script type="text/javascript">
 function expandMethod(domObj) {
	var thisObj = $(domObj);
	var isExpand = thisObj.attr("isExpanded");
	if(isExpand == null)
		isExpand = "1";
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
	<div style="margin-left:40px;">
	接入背景：基于一些项目实践，抽取了企业常见的组织模式，到时企业选择一种适合自己企业的一种组织模式进行API调用。模式定义参考
			接口1.1 参数synModel说明。<br/>
	接入场景：原则上支持批量和实时的接入，但是推荐在批量场景中使用。	<br/>	
	接入步骤： 1、调用接口1.1，开启一个事物，并且获取事物ID，<br/>
			  <div style="margin-left:63px;">2、通过步骤1中获取的事物ID，调用接口1.2到1.6，将企业数据推送至本系统组织架构临时表，</div>
			  <div style="margin-left:63px;">3、调用接口1.7，此时后台会将该事物ID下的所有数据同步到本系统组织架构</div>
	接口文档如下：
	</div>
<center>
<table border="0" width="95%" style="margin-top:-80px;margin-bottom:30px;">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1、开始同步（<font color="red">请求方式：GET</font>）（/api/sys-oms/tempOrg/begin）
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认证方式： basic auth 
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
		<br/>	
		
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>是否必填</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>synModel</td>
								<td>同步模式</td>
								<td>整形（int）</td>
								<td>是</td>
								<td>模式100：同步部门、人员<br/>
									模式200：同步部门、人员、部门人员关系<br/>
									模式300：同步部门、岗位、人员、岗位人员关系<br/>
									模式400：同步部门、岗位、人员、岗位人员关系、部门人员关系，说明：这种模式部门人员关系只用来做排序号<br/>
								</td>
							</tr>
							<tr>
								<td>fdPersonIsMainDept</td>
								<td>人员所属部门是否生效</td>
								<td>布尔类型（boolean）</td>
								<td>是</td>
								<td>如果为true，则人员信息中的人员所属部门字段生效，该字段为空表示这个人没有所属主部门，<br/>
									如果为false，则人员信息中人员所属部门字段不生效，系统会无视该字段，并且会从人员部门关系中自动选择一个部门作为该人员主部门
							</tr>
							<tr>
								<td>fdPersonDeptIsFull</td>
								<td>人员部门关系是否为全量</td>
								<td>布尔类型（boolean）</td>
								<td>是</td>
								<td>如果为true，则表示人员部门关系信息是这个人所有有效的部门关系，系统会用这次传过来的关系覆盖原有的关系<br/>
									如果为false，则表示人员部门关系信息是这个人变更的部门关系，如果人员部门关系信息中的是否有效字段为false<br/>
									，则系统会删除该条关系，如果为true，则新增该条关系，其它的关系不变。
							</tr>
							<tr>
								<td>fdPersonPostIsFull</td>
								<td>人员岗位关系是否为全量</td>
								<td>布尔类型（boolean）</td>
								<td>是</td>
								<td>如果为true，则表示人员岗位关系信息是这个人所有有效的岗位关系，系统会用这次传过来的关系覆盖原有的关系<br/>
									如果为false，则表示人员岗位关系信息是这个人变更的岗位关系，如果人员岗位关系信息中的是否有效字段为false<br/>
									，则系统会删除该条关系，如果为true，则新增该条关系，系统原有的关系不变。
							</tr>
							<tr>
								<td>fdDeptIsAsc</td>
								<td>部门排序号是否是顺序</td>
								<td>布尔类型（boolean）</td>
								<td>是</td>
								<td>如果为true，序号小的优先<br/>
									如果为false，序号大的优先
							</tr>
							<tr>
								<td>fdPersonIsAsc</td>
								<td>人员排序号是否是顺序</td>
								<td>布尔类型（boolean）</td>
								<td>是</td>
								<td>如果为true，序号小的优先<br/>
									如果为false，序号大的优先
							</tr>
					</table>
					</td>
				</tr>
				
					<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>字段名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>success</td>
								<td>结果</td>
								<td>boolean</td>
								<td>不为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>msg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									success为true时，该值返回空。<br/>
									success为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>数据对象</td>
								<td>JSON对象</td>
								<td>可为空</td>
								<td>
									success为true时，该值不为空。<br/>
									success为false时，该值返回空。
								</td>
							</tr>
							<tr>
								<td style="padding-left:25px;">trxId</td>
								<td>事务ID</td>
								<td>字符串</td>
								<td>不为空</td>
								<td>
									后续接口需要传递该参数
								</td>
							</tr>
							
							
							
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.2、批量部门新增（<font color="red">请求方式：POST</font>）（/api/sys-oms/tempOrg/dept/add）
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认证方式： basic auth 
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
		<br/>	
		
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>是否必填</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>trxId</td>
								<td>通过1.1接口返回的事务ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>body</td>
								<td>请求体</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td>需要添加的部门集合或数组JSON字符串</td>
							</tr>
							<tr>
								<td colspan="5">部门定义</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>部门名称</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdDeptId</td>
								<td>部门ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdIsAvailable</td>
								<td>有效状态</td>
								<td>布尔（Boolean）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdAlterTime</td>
								<td>修改时间</td>
								<td>整形（Long）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdParentid</td>
								<td>上级部门ID</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>为空则挂在根下</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>排序号</td>
								<td>整形（int）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdExtend</td>
								<td>扩展字段</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>一般是源系统数据本批次的标识，不做业务处理</td>
							</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>字段名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>success</td>
								<td>结果</td>
								<td>boolean</td>
								<td>不为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>msg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									success为true时，该值返回空。<br/>
									success为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>数据对象</td>
								<td>JSON对象</td>
								<td>可为空</td>
								<td>
									返回不合法的数据列表
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.3、批量岗位新增（<font color="red">请求方式：POST</font>）（/api/sys-oms/tempOrg/post/add）
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认证方式： basic auth 
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
		<br/>	
		
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>是否必填</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>trxId</td>
								<td>通过1.1接口返回的事务ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>body</td>
								<td>请求体</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td>需要添加的岗位集合或数组JSON字符串</td>
							</tr>
							<tr>
								<td colspan="5">岗位定义</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>岗位名称</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdPostId</td>
								<td>岗位ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdIsAvailable</td>
								<td>有效状态</td>
								<td>布尔（Boolean）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdAlterTime</td>
								<td>修改时间</td>
								<td>整形（Long）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdParentid</td>
								<td>所属部门ID</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>为空则挂在根下</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>排序号</td>
								<td>整形（int）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdExtend</td>
								<td>扩展字段</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>一般是源系统数据本批次的标识，不做业务处理</td>
							</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>字段名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>success</td>
								<td>结果</td>
								<td>boolean</td>
								<td>不为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>msg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									success为true时，该值返回空。<br/>
									success为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>数据对象</td>
								<td>JSON对象</td>
								<td>可为空</td>
								<td>
									返回不合法的数据列表
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.4、批量人员新增（<font color="red">请求方式：POST</font>）（/api/sys-oms/tempOrg/person/add）
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认证方式： basic auth 
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
		<br/>	
		
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>是否必填</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>trxId</td>
								<td>通过1.1接口返回的事务ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>body</td>
								<td>请求体</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td>需要添加的岗位集合或数组JSON字符串</td>
							</tr>
							<tr>
								<td colspan="5">人员定义</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>人员名称</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdPersonId</td>
								<td>人员ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdIsAvailable</td>
								<td>有效状态</td>
								<td>布尔（Boolean）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdAlterTime</td>
								<td>修改时间</td>
								<td>整形（Long）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdParentid</td>
								<td>所属主部门ID</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>
									当选同步参数fdPersonIsMainDept为true时：不填代表该人员没有主部门，挂在根下
									当选同步参数fdPersonIsMainDept为false时：不管填不填，都会忽略该值
								</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>人员在主部门内的排序号</td>
								<td>整形（int）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdMobileNo</td>
								<td>手机号</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdLoginName</td>
								<td>登录名</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdEmail</td>
								<td>邮箱</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdSex</td>
								<td>性别</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>男，女</td>
							</tr>
							<tr>
								<td>fdNo</td>
								<td>编号</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdWorkPhone</td>
								<td>办公电话</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdDesc</td>
								<td>备注</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdExtra</td>
								<td>扩展字段</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>JSON字符串如{“fdExtra1”:1,“fdExtra2”:2}
									该扩展字段的键如fdExtra1需要去ekp定义生成
								</td>
							</tr>
							<tr>
								<td>fdExtend</td>
								<td>扩展字段</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>一般是源系统数据本批次的标识，不做业务处理</td>
							</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>字段名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>success</td>
								<td>结果</td>
								<td>boolean</td>
								<td>不为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>msg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									success为true时，该值返回空。<br/>
									success为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>数据对象</td>
								<td>JSON对象</td>
								<td>可为空</td>
								<td>
									返回不合法的数据列表
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.5、批量部门人员关系新增（<font color="red">请求方式：POST</font>）（/api/sys-oms/tempOrg/deptperson/add）
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认证方式： basic auth 
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
		<br/>	
		
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>是否必填</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>trxId</td>
								<td>通过1.1接口返回的事务ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>body</td>
								<td>请求体</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td>需要添加的岗位集合或数组JSON字符串</td>
							</tr>
							<tr>
								<td colspan="5">部门人员关系定义</td>
							</tr>
							<tr>
								<td>fdPersonId</td>
								<td>人员ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdDeptId</td>
								<td>部门ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdIsAvailable</td>
								<td>有效状态</td>
								<td>布尔（Boolean）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdAlterTime</td>
								<td>修改时间</td>
								<td>整形（Long）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdExtend</td>
								<td>扩展字段</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>一般是源系统数据本批次的标识，不做业务处理</td>
							</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>字段名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>success</td>
								<td>结果</td>
								<td>boolean</td>
								<td>不为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>msg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									success为true时，该值返回空。<br/>
									success为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>数据对象</td>
								<td>JSON对象</td>
								<td>可为空</td>
								<td>
									返回不合法的数据列表
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.6、批量岗位人员关系新增（<font color="red">请求方式：POST</font>）（/api/sys-oms/tempOrg/postperson/add）
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认证方式： basic auth 
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
		<br/>	
		
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>是否必填</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>trxId</td>
								<td>通过1.1接口返回的事务ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>body</td>
								<td>请求体</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td>需要添加的岗位集合或数组JSON字符串</td>
							</tr>
							<tr>
								<td colspan="5">岗位人员关系定义</td>
							</tr>
							<tr>
								<td>fdPersonId</td>
								<td>人员ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdPostId</td>
								<td>岗位ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
							<tr>
								<td>fdIsAvailable</td>
								<td>有效状态</td>
								<td>布尔（Boolean）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdAlterTime</td>
								<td>修改时间</td>
								<td>整形（Long）</td>
								<td>否</td>
								<td></td>
							</tr>
							<tr>
								<td>fdExtend</td>
								<td>扩展字段</td>
								<td>字符串（String）</td>
								<td>否</td>
								<td>一般是源系统数据本批次的标识，不做业务处理</td>
							</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>字段名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>success</td>
								<td>结果</td>
								<td>boolean</td>
								<td>不为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>msg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									success为true时，该值返回空。<br/>
									success为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>数据对象</td>
								<td>JSON对象</td>
								<td>可为空</td>
								<td>
									返回不合法的数据列表
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.7、结束同步事务（<font color="red">请求方式：GET</font>）（/api/sys-oms/tempOrg/end）
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认证方式： basic auth 
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
		<br/>	
		
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>参数名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>是否必填</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>trxId</td>
								<td>通过1.1接口返回的事务ID</td>
								<td>字符串（String）</td>
								<td>是</td>
								<td></td>
							</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>字段名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>success</td>
								<td>结果</td>
								<td>boolean</td>
								<td>不为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>msg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									success为true时，该值返回空。<br/>
									success为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>数据对象</td>
								<td>JSON对象</td>
								<td>可为空</td>
								<td>
									返回不合法的数据列表
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td><br/><b>2、参考代码</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;模式1的调用实例
		<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
		</td>
	</tr>
	<tr style="display: none">
		<td>
		<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;"><tr><td>
		     
				&nbsp;&nbsp;&nbsp;&nbsp;import java.nio.charset.Charset;<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;import java.util.HashMap;<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;import java.util.Map;	<br/>			
				&nbsp;&nbsp;&nbsp;&nbsp;import org.springframework.security.crypto.codec.Base64;<br/>				
				&nbsp;&nbsp;&nbsp;&nbsp;import com.landray.kmss.lding.oapi.util.HttpUtil;<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;import com.landray.kmss.util.StringUtil;<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;import net.sf.json.JSONArray;<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;import net.sf.json.JSONObject;<br/>
		        <br/>
		        public class Test {<br/><br/>
		        public static void main(String args[]) {<br/>
		          		&nbsp;&nbsp;&nbsp;&nbsp;// 认证用户名<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;String accountID = "admin";<br/>     
						&nbsp;&nbsp;&nbsp;&nbsp;//认证密码<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;String accountPassword = "123456"; 
				&nbsp;&nbsp;&nbsp;&nbsp;// 如果EKP对该接口启用了Basic认证，那么客户端需要加入认证header信息<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;Map<String,String> headers = getAuthHeaders(accountID+":"+accountPassword);<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;//开始同步<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;String trxId = begin(headers);<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;if(StringUtil.isNotNull(trxId)){<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;//新增部门<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;addDept(headers,trxId);<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;//新增人员<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;addPerson(headers,trxId);<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;//结束同步<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;end(headers,trxId);<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
		          
		          }<br/><br/>
                      
               	//新增部门<br/>
	private static void addDept(Map<String,String> headers,String trxId){<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;String url = "http://127.0.0.1:8080/ekp/api/sys-oms/tempOrg/dept/add?trxId="+trxId;<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;JSONArray jsonArray = new JSONArray();<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;JSONObject dept = null;<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;for (int i = 0; i < 10; i++) {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dept = new JSONObject();<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dept.put("fdName","部门"+i);<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dept.put("fdDeptId",i);<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dept.put("fdIsAvailable",true);<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dept.put("fdAlterTime", System.currentTimeMillis());<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;jsonArray.add(dept);<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;try {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;String result = HttpUtil.doPostForJson(url, headers, jsonArray.toString());<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(result);<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;} catch (Exception e) {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;// <br/>
			&nbsp;&nbsp;&nbsp;&nbsp;e.printStackTrace();<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
	}<br/><br/>
	
	//新增人员<br/>
	private static void addPerson(Map<String,String> headers,String trxId){<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;String url = "http://127.0.0.1:8080/ekp/api/sys-oms/tempOrg/person/add?trxId="+trxId;<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;JSONArray jsonArray = new JSONArray();<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;JSONObject person = null;<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;for (int i = 0; i < 10; i++) {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;person = new JSONObject();<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;person.put("fdName","人员"+i);<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;person.put("fdPersonId",i);<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;person.put("fdParentid","0");<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;person.put("fdMobileNo","13656586987");<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;person.put("fdLoginName","test"+i);<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;person.put("fdIsAvailable",true);<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;person.put("fdAlterTime", System.currentTimeMillis());<br/>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;jsonArray.add(person);<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;try {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;String result = HttpUtil.doPostForJson(url, headers, jsonArray.toString());<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(result);<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;} catch (Exception e) {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;// TODO Auto-generated catch block<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;e.printStackTrace();<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
	}<br/><br/>
	
	//开始同步<br/>
	private static String begin(Map<String,String> headers){<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;String url = "http://127.0.0.1:8080/ekp/api/sys-oms/tempOrg/begin?synModel=1";<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;String trxId = "";<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;try {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;String result = HttpUtil.doGetForJson(url, headers);<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;JSONObject jsonObject = JSONObject.fromObject(result);<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;boolean success = jsonObject.getBoolean("success");<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;if(success){<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;return jsonObject.getJSONObject("data").getString("trxId");<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;} catch (Exception e) {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;e.printStackTrace();<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;return trxId;<br/>
	}<br/><br/>
	
	//结束同步<br/>
	private static void end(Map<String,String> headers,String trxId){<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;String url = "http://127.0.0.1:8080/ekp/api/sys-oms/tempOrg/end?trxId="+trxId;<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;try {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;String result = HttpUtil.doGetForJson(url, headers);<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;JSONObject jsonObject = JSONObject.fromObject(result);<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;boolean success = jsonObject.getBoolean("success");<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;if(success){<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(jsonObject.getString("msg"));<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;} catch (Exception e) {<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;e.printStackTrace();<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
	}<br/><br/>

    //添加RESTFUL接口header<br/>
	private static Map<String,String> getAuthHeaders(String yourEncryptedWorlds){<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;Map<String,String> headers = new HashMap<String,String>();<br/>
      	&nbsp;&nbsp;&nbsp;&nbsp;byte[] encodedAuth = Base64.encode(yourEncryptedWorlds.getBytes(Charset.forName("UTF-8")));<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;String authHeader = "Basic " + new String( encodedAuth );<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;headers.put("Authorization", authHeader );<br/>
        &nbsp;&nbsp;&nbsp;&nbsp;return headers;<br/>
	}  <br/><br/>
		          
		        }<br/>
		        
	
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>