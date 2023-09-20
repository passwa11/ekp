<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<html>
<head>
<style>
body, td, input, select, textarea{
	font-size: 12px;
	color: #333333;
	line-height: 20px;
}
body{
	margin: 0px;
	padding: 10 0;
}
.tb_normal{
	border-collapse:collapse;
	border: 1px #C0C0C0 solid;
	background-color: #FFFFFF;
	margin-bottom: 20px;
	width: 100%;
}
.td_normal, .tb_normal td{
	border-collapse:collapse;
	border: 1px #C0C0C0 solid;
	padding:3px;
}
.tr_normal_title{
	background-color: #F0F0F0;
	border-collapse:collapse;
	border: 1px #C0C0C0 solid;
	padding:3px;
	text-align:center;
	word-break:keep-all;
}
.td_normal_title{
	background-color: #F0F0F0;
	border-collapse:collapse;
	border: 1px #C0C0C0 solid;
	padding:3px;
	word-break:keep-all;
}
.inputsgl{
	color: #0066FF;
	border-color: #999999;
	border-style: solid;
	border-width: 0px 0px 1px 0px;
}
.btn{
	color: #0066FF; 
	background-color: #F0F0F0; 
	border: 1px #999999 solid; 
	font-weight: normal; 
	padding: 0px 1px 1px 0px;
	height: 18px;
	clip:  rect();
}
</style>
<title>
<bean:message bundle="sys-formula" key="formula.exampleLibrary"/>
</title>
</head>
<body>
<center>
<div style="width: 750px">
<h1 style="font-size: 18px;" ><bean:message bundle="sys-formula" key="formula.exampleLibrary"/></h1>

<table class="tb_normal">
	<tr>
		<td class="td_normal_title" colspan="2" align="center">
			<bean:message bundle="sys-formula" key="formula.label.commonFormula"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 判断表单多值属性框（复选框/下拉菜单）是否勾选了某个选项 -->
			<bean:message bundle="sys-formula" key="formula.arrayContainKeyDesc"/>
		</td>
		<td>
			<!-- $列表.包含$($多值属性框$.split(";"), "选项的值") -->
			<bean:message bundle="sys-formula" key="formula.arrayContainKey"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 获取员工编号 -->
			<bean:message bundle="sys-formula" key="formula.getFdNoDesc"/>
		</td>
		<td>
			<!-- $申请人$.getFdNo() -->
			<bean:message bundle="sys-formula" key="formula.getFdNo"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 获取所属机构 -->
			<bean:message bundle="sys-formula" key="formula.getFdOrgDesc"/>
		</td>
		<td>
			<!-- $申请人$.getFdParentOrg() -->
			<bean:message bundle="sys-formula" key="formula.getFdOrg"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 获取组织架构全路径，以“/”为分隔 -->
			<bean:message bundle="sys-formula" key="formula.getOrgParentsNameDesc"/>
		</td>
		<td>
			<!-- $申请人$.getFdParentsName("/") -->
			<bean:message bundle="sys-formula" key="formula.getOrgParentsName"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 判断某部门是否在某机构之内 -->
			<bean:message bundle="sys-formula" key="formula.deptIsWithInOrgDesc"/>
		</td>
		<td>
			<!-- $XX部门$.getFdParentsName().contains("XX机构的名称") -->
			<bean:message bundle="sys-formula" key="formula.deptIsWithInOrg"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 获取申请人所在部门的名称 -->
			<bean:message bundle="sys-formula" key="formula.getFdDeptNameDesc"/>
		</td>
		<td>
			<!-- $申请人$.getFdParent().getFdName() -->
			<bean:message bundle="sys-formula" key="formula.getFdDeptName"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 获取申请人所在部门的部门领导 -->
			<bean:message bundle="sys-formula" key="formula.getCurUserDeptLeaderDesc"/>
		</td>
		<td>
			<!-- $申请人$.getFdParent().getHbmThisLeader() -->
			<bean:message bundle="sys-formula" key="formula.getCurUserDeptLeader"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 获取部门领导 -->
			<bean:message bundle="sys-formula" key="formula.getDeptLeaderDesc"/>
		</td>
		<td>
			<!-- $部门$.getHbmThisLeader() -->
			<bean:message bundle="sys-formula" key="formula.getDeptLeader"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 根据提交人身份判断是否属于某部门 -->
			<bean:message bundle="sys-formula" key="formula.curUserWithInDeptDesc"/>
		</td>
		<td>
			<!-- $组织架构.岗位所在群组判断$($流程.获取提交人身份$(), "部门名称") -->
			<bean:message bundle="sys-formula" key="formula.curUserWithInDept"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 获取某节点（如"N4"）的实际处理人的直线领导 -->
			<bean:message bundle="sys-formula" key="formula.getDealerLeaderByNodeDesc"/>
		</td>
		<td>
			<!-- $组织架构.解释通用岗位$($流程.获取节点实际处理人$("N4"), "<直线领导>") -->
			<bean:message bundle="sys-formula" key="formula.getDealerLeaderByNode"/>
		</td>
	</tr>
	<tr>
		<td>
			<!-- 判断角色线解析结果是否为空 -->
			<bean:message bundle="sys-formula" key="formula.parseSysOrgRoleResultIsNullDesc"/>
		</td>
		<td>
			<!-- $组织架构.解释角色线$($申请人$,"角色线名称", "上一级").isEmpty() -->
			<bean:message bundle="sys-formula" key="formula.parseSysOrgRoleResultIsNull"/>
		</td>
	</tr>
</table>

<table class="tb_normal">
	<tr class="tr_normal_title">
		<td colspan="2"><bean:message bundle="sys-formula" key="formula.label.examples"/></td>
	</tr>
	<tr>
		<td width="45%">
			<!-- 流程采用表单，表单中要求提交人选择部门信息（多值），在流程的某个节点需要按照表单所选择的部门获取部门领导 -->
			<bean:message bundle="sys-formula" key="formula.exampleLibrary.getDeptLeader"/>
		</td>
		<td><pre>
import java.util.ArrayList;
import com.landray.kmss.sys.organization.model.SysOrgElement;
List rtn = new ArrayList();
<!-- var x = $部门选择$; -->
var x = <bean:message bundle="sys-formula" key="formula.deptChoose"/>;
for(var i=0; i < x.size(); i++){
     SysOrgElement o =(SysOrgElement) x.get(i);
     rtn.add(o.getHbmThisLeader().getFdId());
}
return rtn;</pre>
		</td>
	</tr>
		<tr>
		<td><!-- 同一节点的人需要会审或者需要同时抄送多人 --><bean:message bundle="sys-formula" key="formula.exampleLibrary.sendPeople"/>
		</td>
		<td><pre>
java.util.ArrayList list = new java.util.ArrayList();
<!-- list.addAll($组织架构.解释角色线$($XX部门$, "XXX秘书线 ", "部门秘书"));
list.addAll($组织架构.解释角色线$($XX部门$, "XXX行政线", "上级领导")); -->
list.addAll(<bean:message bundle="sys-formula" key="formula.parseSysOrgRole1"/>);
list.addAll(<bean:message bundle="sys-formula" key="formula.parseSysOrgRole2"/>);
return list;</pre></td>
	</tr>
	<tr>
		<td><!-- 根据自定义表单的多选框获取处理人 --><bean:message bundle="sys-formula" key="formula.exampleLibrary.getHandler"/>
		</td>
		<td><pre>
import java.util.ArrayList;
List list = new ArrayList();
<!-- String personStr = $多选框$; -->
String personStr = <bean:message bundle="sys-formula" key="formula.checkbox"/>;
String[] persons = personStr.split(";");
for(int i = 0; i < persons.length; i++){
	if(persons[i].equals("0")){
		<!-- list.add($组织架构.根据登录名取用户$("admin")); -->
		list.add(<bean:message bundle="sys-formula" key="formula.getSysOrgPersonByLoginName0"/>);
	}else if(persons[i].equals("1")){
		<!-- list.add($组织架构.根据登录名取用户$("t1")); -->
		list.add(<bean:message bundle="sys-formula" key="formula.getSysOrgPersonByLoginName1"/>);
	}else if(persons[i].equals("2")){
		<!-- list.add($组织架构.根据登录名取用户$("t2")); -->
		list.add(<bean:message bundle="sys-formula" key="formula.getSysOrgPersonByLoginName2"/>);
	}else if(persons[i].equals("3")){
		<!-- list.add($组织架构.根据登录名取用户$("t3"));  -->
		list.add(<bean:message bundle="sys-formula" key="formula.getSysOrgPersonByLoginName3"/>);
	}
}
return list;</pre></td>
	</tr>
	<tr>
		<td><!-- 条件分支判断表单多选地址本是否包含张三或者李四 --><bean:message bundle="sys-formula" key="formula.exampleLibrary.determineAddressContain"/>
		</td>
		<td><pre>
import java.util.ArrayList;
import com.landray.kmss.sys.organization.model.SysOrgElement;
List rtn = new ArrayList();
boolean flag = false;
<!-- var x = $多选地址本$; -->
var x = <bean:message bundle="sys-formula" key="formula.multiSelectAddress"/>;
for(var i=0; i < x.size(); i++){
    SysOrgElement o =(SysOrgElement) x.get(i);
    if(o.fdName.equals(<bean:message bundle="sys-formula" key="formula.userName1"/>) 
    || o.fdName.equals(<bean:message bundle="sys-formula" key="formula.userName2"/>)){
		flag = true; 
		break;
	}      
}
return flag;</pre></td>
	</tr>
</table>

</div>
</center>
</body>
</html>