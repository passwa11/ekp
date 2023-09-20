<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|list.js");
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
	<p>&nbsp;&nbsp;EKP系统提供了人事档案RestFul接口服务，服务有一个接口，获取所人事档案的基本信息（getHrStaffElements）
	接口getHrStaffElements的作用是暴露本系统人事档案唯一标示信息，以便于异构系统做人事档案关系对应，另外也方便异构系统做人事档案删除检测。
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;获取所有人事档案基本信息（getHrStaffElements）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;人事档案基本信息接出body数据，详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>count</td>
								<td>设置此次同步需要获取条目数</td>
								<td>int</td>
								<td>不允许为空</td>
								<td>
	 							</td>
							</tr>
							<tr>
								<td>beginTimeStamp</td>
								<td>设置开始时间</td>
								<td>String</td>
								<td>允许为空</td>
								<td>为空， 则取系统所有count(见)条信息。不为空，则取系统中，大于beginTimeStamp的组织架构信息中count条数据。格式：yyyy-MM-dd HH:mm:ss 或 时间戳（毫秒数：1608953689000）
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
									[<br/>
									&nbsp;&nbsp;{<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"id":  //唯一标识<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"orgParent":     //部门或机构：若引用组织架构，则取fdOrgPerson           <br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"orgParentsName":        // 完整的部门名称<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"staffingLevel":        //职务：若引用组织架构，则取fdOrgPerson数据<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"name"://姓名<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"sex":			//性别<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"dateOfBirth": true,   //出生日期<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"birthdayOfYear":   //生日<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"age": //年龄<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"staffNo"://工号<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"idCard":       //身份证号码<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"workTime":    //参加工作时间<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"uninterruptedWorkTime":      // 连续工龄<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"workingYears":"test01",		 //到本单位时间<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"trialExpirationTime":    //试用到期时间<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"employmentPeriod":	  // 用工期限（年）<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"staffType":	  //人员类别	<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"nation":      //民族<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"politicalLandscape":		//政治面貌<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"dateOfGroup":	 //  入团日期<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"dateOfParty":		// 入党日期<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"highestEducation":		// 最高学历<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"faritalStatus":	//  婚姻情况<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"health":			// 健康情况<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"stature":		// 身高（厘米）<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;"weight":	// 体重（千克）<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"livingPlace":		// 现居地	<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"nativePlace":		// 籍贯	<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"homeplace":	// 出生地<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"accountProperties":	// 户口性质<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"registeredResidence":	// 户口所在地<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"residencePoliceStation":	// 户口所在派出所<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"mobileNo":	// 手机<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"email":	// 邮箱<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"officeLocation":	// 办公地点<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"emergencyContact":	// 紧急联系人<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"emergencyContactPhone":	// 紧急联系人电话<br/>
							         &nbsp;&nbsp;&nbsp;&nbsp;"status":	// 层级ID<br/>
							        &nbsp;&nbsp;&nbsp;&nbsp;"hierarchyId":	// 层级ID
							        &nbsp;&nbsp;}<br/>
									&nbsp;&nbsp;...<br/>
									]<br/>
								</td>
							</tr>
							<tr>
								<td>count</td>
								<td>返回数据的条目数</td>
								<td>数字（int）</td>
								<td>可为0</td>
								<td>
									返回数据的条目数。
								</td>
							</tr>
							<tr>
								<td>timeStamp</td>
								<td>返回此次调用后的时间戳</td>
								<td>字符串（String）</td>
								<td>不为空</td>
								<td>
									<font style="font-weight: bolder;">如：1608953689000</font>
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