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

<center>
<div style="width: 95%;text-align: left;">
	<p>&nbsp;&nbsp;EKP系统提供了组织架构接入RestFul接口服务，包含两部分，同步组织架构基本信息（syncOrgElementsBaseInfo）以及
	       同步需要更新的组织架构信息(syncOrgElements)接口。接口syncOrgElementsBaseInfo的作用是，根据已传递的所有组织架构的基本信息，构建组织架构信息或删除组织架构信息。
	      接口syncOrgElements的作用是，根据传递的详细的组织架构信息，更新具体对应的组织架构信息。 以下是对该接口的具体说明。</br>
	      
	</p>
	</br>
	<p style="color:red">
		syncOrgElementsBaseInfo和syncOrgElements需要配合使用，调用逻辑如下：</br>
	      1、调用syncOrgElementsBaseInfo接口，把所有组织数据的基本信息传过来。ekp会根据传过来的数据跟ekp数据库的数据进行对比，判断哪些是新增的、哪些是删除的，新增的会插入一条记录到数据库，删除的会置为无效。注意，如果
	      传过来的数据不全，那么没传过来的记录都会被当成删除处理（置为无效）。</br>
	      2、调用syncOrgElements接口，把新更新的记录的详细信息传过来。ekp会根据传过来的数据更新对应的记录的详细信息。
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1同步所有组织架构基本信息（/api/sys-organization/sysSynchroSetOrg/syncOrgElementsBaseInfo）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;组织架构接入body数据（JSON对象），详细说明如下:</div>
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
								<td>组织架构来源</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td> 该字段信息会存于系统组织架构表sys_org_element字段fd_import_info中，<br/>
									格式:  系统来源 +传入的组织架构唯一标识。<br/>
	 								若为空 则默认格式为 "com.landray.kmss.sys.organization.webservice.in.ISysSynchroSetOrgWebService" + 传入的组织架构唯一标识。
	 								<br/>
	 								<br/>
	 								新增数据时，如appName为空，系统组织架构表sys_org_element的字段fd_import_info中存储为：
	 								"com.landray.kmss.sys.organization.webservice.in.ISysSynchroSetOrgWebService"+传入ID
									如appName不为空，系统组织架构表sys_org_element的字段fd_import_info中存储为："appName"+传入ID，例：dingding10000。
									<br/>
									<font color="red">
									appName在接口的多次调用以及调用组织接入的其它接口时，值必须要要一致（如果不设置，则调用各个接口时都不要设置；如果设置了值，那调用各个接口时必须传一样的值）
	 								</font>
	 								</td>
							</tr>
							<tr>
								<td>orgJsonData</td>
								<td>所有组织机构基本信息json数组</td>
								<td>字符串（Json数组转字符串）</td>
								<td>不允许为空</td>
								<td>设置和ekpJ系统做组织架构同步系统的所有组织架构基本数据.<br/>
									<font style="font-weight: bolder;">格式为:
									[{\"id\":\"13425\",\"lunid\":\"eaac7762243cc6\",\"name\":\"测试部\",\"type\":\"dept\",\"no\":\"CSDL01\",\"keyword\":\"dept_01\",\"order\":\"1001\"},...].</font><br/>
									其中id（唯一标识）、lunid(唯一标示，可替换为主健),name（名称），type（类型有：org，dept，group，person，post）不允许为空。
									其他字段（no：编号、keyword：关键字、order：排序号）可为空。
								</td>
							</tr>
							<tr>
								<td>deleteNoInOrgJsonData</td>
								<td>是否删除orgJsonData之外的记录</td>
								<td>布尔</td>
								<td>允许为空，默认为true</td>
								<td><font color="red">设置为true的场景：</font><br>
								主要是针对第三方系统组织数据物理删除的情况，因为这种情况下第三方系统无法告知EKP哪些记录需要删除，所以只能通过全量对比判断出需要删除的记录。
								主要逻辑为对比本次传过来的orgJsonData数据以及之前同步到EKP中的数据，EKP中存在但是orgJsonData中不包含的则表示是第三方系统已经删除的，
								则EKP会把相应的记录置为无效（注意，EKP中手动创建的记录不受影响），所以这种情况下orgJsonData必须包含第三方系统当前的所有记录。
								<br>
								<br>
								<font color="red">设置为false的场景：</font><br>
								第三方系统的记录不会被物理删除，这种情况下可以通过调用syncOrgElements时设置isAvailable属性把对应的记录置为无效。
								这种情况下orgJsonData只需要包含本次增量更新的记录</td>
							</tr>
							<tr>
								<td>orgSyncConfig</td>
								<td>同步所需配置</td>
								<td>字符串（Json对象转字符串）</td>
								<td>允许为空</td>
								<td>根据业务要求,设置和ekpj同步时,需要同步到ekpj系统的字段信息,该<font color="red">参数在本接口中无效</font></td>
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
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.2 同步需要更新的组织架构信息（api/sys-organization/sysSynchroSetOrg/syncOrgElements）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;组织架构接入body数据（JSON对象），详细说明如下:</div>
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
								<td>组织架构来源</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td> 该字段信息会存于系统组织架构表sys_org_element字段fd_import_info中，<br/>
									格式:  系统来源 +传入的组织架构唯一标识。<br/>
	 								若为空 则默认格式为 "com.landray.kmss.sys.organization.webservice.in.ISysSynchroSetOrgWebService" + 传入的组织架构唯一标识。
	 								<br/>
	 								<br/>
	 								新增数据时，如appName为空，系统组织架构表sys_org_element的字段fd_import_info中存储为：
	 								"com.landray.kmss.sys.organization.webservice.in.ISysSynchroSetOrgWebService"+传入ID
									如appName不为空，系统组织架构表sys_org_element的字段fd_import_info中存储为："appName"+传入ID，例：dingding10000。
									<br/>
									<font color="red">
									appName在接口的多次调用以及调用组织接入的其它接口时，值必须要要一致（如果不设置，则调用各个接口时都不要设置；如果设置了值，那调用各个接口时必须传一样的值）
	 								</font>
	 								</td>
							</tr>
							<tr>
								<td>orgJsonData</td>
								<td>所有组织机构基本信息json数组</td>
								<td>字符串（Json数组转字符串）</td>
								<td>不允许为空</td>
								<td>设置和ekpJ系统做组织架构同步系统的所有组织架构基本数据.<br/>
									<font style="font-weight: bolder;">格式为:<br/>
									[<br/>
									&nbsp;&nbsp;{<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;\"id\": \"12d552352ac1dd47c57c307401f958f7\", //唯一标识,不允许为空<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;\"lunid\": \"12d552352ac1dd47c57c307401f958f7\", //唯一标识，若不为空，直接作为主健存入数据库,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"name\": \"深圳蓝凌\",     //名称 不允许为空  <br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"type\": \"dept\",        //组织架构类型,不允许为空,org(机构),dept(部门),group(群组),post(岗位),person(人员)<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"no\":\"981\",			   //编号,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"order\":\"00210\",        //排序号,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"keyword\":\"18391297432nm\",//关键字,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"memo\":\"说明\",			//组织架构说明,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"isAvailable\": true,   //是否有效,该属性决定该组织架构是否删除，不允许为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"parent\": \"1331a26cb4bcf91583c264b4803b69ae\" ,   //设置该组织架构的父部门,对type为org,dept,post,person起作用.<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"thisLeader\":\"1331a26cb4bcf91583c264b4803b69ad\"  //设置部门领导,对type为org,dept,post起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"superLeader\":\"1331a26cb4bcf91583c264b4803b69a1\"//设置部门上级领导,对type为org,dept起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"members\":[\"113\",\"43424\",..],                    //成员,仅对type为group起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"persons\":[\"13868123\",\"12313\",..],				 //包含人员,仅对type为post起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"posts\":[\"cbda73\",\"bac67843\"..]                  //所属岗位,仅对type为person起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"loginName\":\"test01\",							 //登录名,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"password\":\"c4ca4238a0b923820dcc509a6f75849b\",   //密码,base64加密后的信息,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"mobileNo\":\"1578277221\",						 //手机号,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"email\":\"sin@126.com\",							 //邮件地址,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"attendanceCardNumber\":\"20080901\",               //考勤号,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"workPhone\":\"0755878787782\",					 //办公电话,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"rtx\":\"9527\"									 // rtx账号,仅当type为person时,有此信息<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;\"wechat\":\"test\"								// rtx账号,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"sex\":\"M\"								// 性别，M表示男性，F表示女性,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"shortNo\":\"123\"								// 短号,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"customProps\":{\"birthDate\":\"1900-01-01\",\"education\":\"本科\",\"isSocialRecruitment\":\"true\"}							// 自定义属性，只对person数据生效，值为json格式，key值为定义的属性名称，value值都为字符串格式。“日期”类型格式为“yyyy-MM-dd”，“日期时间”格式为“yyyy-MM-dd HH:mm”，布尔类型的值为“true”或“false”<br/>
							        &nbsp;&nbsp;}<br/>
									&nbsp;&nbsp;...<br/>
									]<br/></font>
									各个字段的含义以及适用范围,请参考查阅 <a href="#orgfiled">2.2 组织架构字段类型说明</a>
								</td>
							</tr>
							<tr>
								<td>orgSyncConfig</td>
								<td>同步所需配置</td>
								<td>字符串（Json对象转字符串）</td>
								<td>允许为空</td>
								<td>
									根据业务要求,设置和ekpj同步时,需要同步到ekpj系统的字段信息.<font style="font-weight: bolder;">格式如下:<br/>
									{
	<br>\"org\": [\"no\", \"order\", \"keyword\", \"memo\", \"parent\", \"thisLeader\", \"superLeader\", \"isBusiness\"],
	<br>\"dept\": [\"no\", \"order\", \"keyword\", \"memo\", \"parent\", \"thisLeader\", \"superLeader\", \"isBusiness\"],
	<br>\"group\": [\"no\", \"order\", \"keyword\", \"memo\", \"members\", \"isBusiness\"],
	<br>\"post\": [\"no\", \"order\", \"keyword\", \"memo\", \"parent\", \"thisLeader\", \"persons\", \"isBusiness\"],
	<br>\"person\": [\"no\", \"order\", \"keyword\", \"memo\", \"loginName\", \"email\", \"mobileNo\", \"attendanceCardNumber\", \"workPhone\", \"rtx\", \"wechat\", \"parent\", \"sex\", \"isBusiness\"]
<br>}
<br/></font>
									用于配置哪些信息需要同步到ekpJ系统中。上面的配置内容为没有设置此信息时的默认值,
									从以上的配置可以看出:此次同步是不同步个人密码信息(password)的,个人和岗位之间的关系是靠岗位中的成员(persons)来维护的等.
									字段配置来源及含义,请查阅 <a href="#orgfiled">2.2 组织架构字段类型说明</a>
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
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.3 实时更新组织架构信息（api/sys-organization/sysSynchroSetOrg/updateOrgElement）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">该接口是针对实时更新的场景，如果是批量更新，请使用1.1和1.2接口</font></br>
		</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;组织架构接入body数据（JSON对象），详细说明如下:</div>
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
								<td>组织架构来源</td>
								<td>字符串（String）</td>
								<td>允许为空</td>
								<td> 该字段信息会存于系统组织架构表sys_org_element字段fd_import_info中，<br/>
									格式:  系统来源 +传入的组织架构唯一标识。<br/>
	 								若为空 则默认格式为 "com.landray.kmss.sys.organization.webservice.in.ISysSynchroSetOrgWebService" + 传入的组织架构唯一标识。
	 								<br/>
	 								<br/>
	 								新增数据时，如appName为空，系统组织架构表sys_org_element的字段fd_import_info中存储为：
	 								"com.landray.kmss.sys.organization.webservice.in.ISysSynchroSetOrgWebService"+传入ID
									如appName不为空，系统组织架构表sys_org_element的字段fd_import_info中存储为："appName"+传入ID，例：dingding10000。
									<br/>
									<font color="red">
									appName在接口的多次调用以及调用组织接入的其它接口时，值必须要要一致（如果不设置，则调用各个接口时都不要设置；如果设置了值，那调用各个接口时必须传一样的值）
	 								</font>
	 								</td>
							</tr>
							<tr>
								<td>orgJsonData</td>
								<td>所有组织机构基本信息json数组</td>
								<td>字符串（Json数组转字符串）</td>
								<td>不允许为空</td>
								<td><br/>
									<font style="font-weight: bolder;">格式为:<br/>
									&nbsp;&nbsp;{<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;\"id\": \"12d552352ac1dd47c57c307401f958f7\", //唯一标识,不允许为空。如果在ekp中根据该ID找到记录，则更新；找不到则新增。<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;\"lunid\": \"12d552352ac1dd47c57c307401f958f7\", //唯一标识，若不为空，直接作为主健存入数据库,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"name\": \"深圳蓝凌\",     //名称 不允许为空  <br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"type\": \"dept\",        //组织架构类型,不允许为空,org(机构),dept(部门),group(群组),post(岗位),person(人员)<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"no\":\"981\",			   //编号,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"order\":\"00210\",        //排序号,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"keyword\":\"18391297432nm\",//关键字,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"memo\":\"说明\",			//组织架构说明,可为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"isAvailable\": true,   //是否有效,该属性决定该组织架构是否删除，不允许为空<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"parent\": \"1331a26cb4bcf91583c264b4803b69ae\" ,   //设置该组织架构的父部门,对type为org,dept,post,person起作用.<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"thisLeader\":\"1331a26cb4bcf91583c264b4803b69ad\"  //设置部门领导,对type为org,dept,post起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"superLeader\":\"1331a26cb4bcf91583c264b4803b69a1\"//设置部门上级领导,对type为org,dept起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"members\":[\"113\",\"43424\",..],                    //成员,仅对type为group起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"persons\":[\"13868123\",\"12313\",..],				 //包含人员,仅对type为post起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"posts\":[\"cbda73\",\"bac67843\"..]                  //所属岗位,仅对type为person起作用<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"loginName\":\"test01\",							 //登录名,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"password\":\"c4ca4238a0b923820dcc509a6f75849b\",   //密码,base64加密后的信息,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"mobileNo\":\"1578277221\",						 //手机号,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"email\":\"sin@126.com\",							 //邮件地址,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"attendanceCardNumber\":\"20080901\",               //考勤号,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"workPhone\":\"0755878787782\",					 //办公电话,仅当type为person时,有此信息<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"rtx\":\"9527\"									 // rtx账号,仅当type为person时,有此信息<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;\"langProps\":{\"fdNameCN\":\"简体中文名称\",\"fdNameUS\":\"英文名称\",\"fdNameHK\":\"繁体名称\"}							// 多语言属性，值为json格式，key值为语言标志（'fdName'代表名称属性，'CN'代表对应的语言）。<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;\"customProps\":{\"birthDate\":\"1900-01-01\",\"education\":\"本科\",\"isSocialRecruitment\":\"true\"}							// 自定义属性，只对person数据生效，值为json格式，key值为定义的属性名称，value值都为字符串格式。“日期”类型格式为“yyyy-MM-dd”，“日期时间”格式为“yyyy-MM-dd HH:mm”，布尔类型的值为“true”或“false”<br/>
							        &nbsp;&nbsp;}<br/>
									<br/></font>
									各个字段的含义以及适用范围,请参考查阅 <a href="#orgfiled">2.2 组织架构字段类型说明</a>
								</td>
							</tr>
							<tr>
								<td>orgSyncConfig</td>
								<td>同步所需配置</td>
								<td>字符串（Json对象转字符串）</td>
								<td>允许为空</td>
								<td>
									对应本接口，无需设置
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
		<td><br/><b>2、通用数据类型说明</b></td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" style="width: 85%;margin-left: 40px;">
				<tr>
					<td>
						2.1 组织架构类型说明:<br/>
						该接口中,组织架构类型标识分别为org(机构),dept(部门),group(群组),post(岗位),person(人员),如果在参数中设置了其他类型,会被认定为非法,不予处理.
					</td>
				</tr>
				<tr>
					<td id="orgfiled" style="padding: 0px;">
						2.2 组织架构字段类型说明:<br/>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="20%"><b>字段名</b></td>
								<td align="center" width="40%"><b>含义</b></td>
								<td align="center" width="40%"><b>适用组织架构类型</b></td>
							</tr>
							<tr >
								<td>id</td>
								<td>集成系统中组织架构的唯一标识,不允许为空</td>
								<td>所有组织架构类型</td>
							</tr>
							<tr >
								<td>name</td>
								<td>组织架构名称, 不允许为空</td>
								<td>所有组织架构类型</td>
							</tr>
							<tr >
								<td>type</td>
								<td>组织架构类型,不允许为空</td>
								<td>值为:org(机构),dept(部门),group(群组),post(岗位),person(人员)中的一个</td>
							</tr>
							<tr >
								<td>no</td>
								<td>编号,可为空</td>
								<td>所有组织架构类型</td>
							</tr>
							<tr >
								<td>order</td>
								<td>排序号,可为空</td>
								<td>所有组织架构类型</td>
							</tr>
							<tr >
								<td>keyword</td>
								<td>关键字,可为空</td>
								<td>所有组织架构类型</td>
							</tr>
							<tr >
								<td>memo</td>
								<td>组织架构说明,可为空</td>
								<td>所有组织架构类型</td>
							</tr>
							<tr >
								<td>isAvailable</td>
								<td>是否有效,该属性决定该组织架构是否删除</td>
								<td>所有组织架构类型,布尔类型</td>
							</tr>
							<tr >
								<td>parent</td>
								<td>设置该组织架构的父部门</td>
								<td>org(机构),dept(部门),post(岗位),person(人员)</td>
							</tr>
							<tr >
								<td>thisLeader</td>
								<td>设置组织架构领导</td>
								<td>org(机构),dept(部门),post(岗位)</td>
							</tr>
							<tr >
								<td>superLeader</td>
								<td>设置部门上级领导</td>
								<td>org(机构),dept(部门)</td>
							</tr>
							<tr >
								<td>members</td>
								<td>成员</td>
								<td>group(群组)</td>
							</tr>
							<tr >
								<td>persons</td>
								<td>包含人员</td>
								<td>post(岗位)</td>
							</tr>
							<tr >
								<td>posts</td>
								<td>所属岗位</td>
								<td>person(人员)</td>
							</tr>
							<tr >
								<td>loginName</td>
								<td>登录名</td>
								<td>person(人员)</td>
							</tr>
							<tr >
								<td>password</td>
								<td>密码,base64加密后的信息</td>
								<td>person(人员)</td>
							</tr>
							<tr >
								<td>mobileNo</td>
								<td>手机号</td>
								<td>person(人员)</td>
							</tr>
							<tr >
								<td>email</td>
								<td>邮件地址</td>
								<td>person(人员)</td>
							</tr>
							<tr >
								<td>attendanceCardNumber</td>
								<td>考勤号</td>
								<td>person(人员)</td>
							</tr>
							<tr >
								<td>workPhone</td>
								<td>办公电话</td>
								<td>person(人员)</td>
							</tr>
							<tr >
								<td>rtx</td>
								<td>rtx账号</td>
								<td>person(人员)</td>
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