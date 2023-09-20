<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.oms.temp.SysOmsExcelUtil,com.landray.kmss.sys.oms.temp.SysOmsJdbcConfig,net.sf.json.JSONObject"%>
<%
	//获取ekp动态字段
	JSONArray ldFieldsJson = SysOmsExcelUtil.getLandingExtendFields();
	request.setAttribute("ldingExtendField", ldFieldsJson);
	SysOmsJdbcConfig  jdbcConfig =  new SysOmsJdbcConfig();
	String extendFields = jdbcConfig.getDataMap().get("kmss.oms.temp.person_extendFields");
	request.setAttribute("expand", JSONArray.parseArray(extendFields));
	
%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title">组织架构接入数据库同步配置</template:replace>
		<template:replace name="head">
 		<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>
		<style type="text/css"> 
			.tb_normal td {
    //padding: 5px;
    //border: 1px #d2d2d2 solid;
    //word-break: break-all;
}
.table{
	display:none;
}
.toDataSourceButton{
	border: 1px solid #d5d5d5;
    border-radius: 4px;
    height: 24px;
    line-height: 24px;
    background-color: rgba(22, 155, 213, 1);
    transition-duration: .3s;
    color: #fff;
    width: 94px;
    font-size: 12px;
}
.header_sheet_style{
	height: 40px;
    width: 110px;
    border: 1px solid #d2d2d2;
    border-radius: 4px;
    text-align: center;
}
.header_sheet_style_style{
	font-size: 13px;
    padding-top: 4px;
}
.select_table_true{
	background-color: rgba(0, 102, 255, 1);
    color: #fff;
    border: 1px solid rgba(0, 102, 255, 1);
}
div{ 
    cursor: pointer;
}

		</style> 
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">${lfn:message('sys-oms:oms.setting')}</span>
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
	<table class="tb_simple" width=95% >
		<tr>
			<td class="td_normal_title" width=15%>是否开启：</td>
				<td><ui:switch property="value(kmss.oms.in.db.enabled)"
						onValueChange="config_chgEnabled();"
						enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
						disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
				</td>
			</tr>	
	</table>		
	<table id="dbenable" class="tb_simple" width=95%  cellpadding="20" cellspacing="20" style="width: 95%;display: none;">
							
	<!-- <tr>
		<td class="td_normal_title" width="15%"></td>
		<td>
			<div style="margin-top: -12px;">
			<font color="red">说明：当选择全量同步为是的时候，那么在保存数据的同时会执行全量同步操作；当选择全量同步为否的时候则只保存数据。</font>
			</div>
		</td>
	</tr> -->
	<tr>
		<td class="td_normal_title" width="15%">数据源：</td>
		<td>
			<div>
				<div>
					<xform:select style="width: 50%;" className="datasource" onValueChange="selectDatasource(this.value,'change')" property="value(kmss.oms.temp.datasource.name)">
					</xform:select>
					<input class="toDataSourceButton" type="button" value="管理数据源" onclick="toDataSource();">
				</div>
			</div>
		</td>
	</tr>
	<tr>
			<td  class="td_normal_title" width="15%">保存配置：</td>
			<td>
				<div>
				<input type="checkbox" id="isall" onchange="checkboxOnclick(this);" /><span style="color:red;">点击保存按钮时，除了保存配置外，也会进行一次全量同步，该操作会删除EKP有数据源没有的所有数据，请慎重。</span></div>
			</td>
		</tr>
	<tr>
		<td class="td_normal_title" width="15%"></td>
		<td>
		<div>
			<div id="dept_table"  class="header_sheet_style" style="float: left;" onclick="selectTable('dept');">
				<div class="header_sheet_style_style">
					<span>部门表</span></br><span style="color: red;">（必选）</span>
				</div>
			</div>
			<div id="person_table" class="header_sheet_style" style="float: left;margin-left: 5px;" onclick="selectTable('person');">
				<div class="header_sheet_style_style">
					<span>人员表</span></br><span style="color: red;">（必选）</span>
				</div>
			</div>
			<div id="deptperson_table" class="header_sheet_style" style="float: left;width: 128px;margin-left: 5px;" onclick="selectTable('deptperson');">
				<div class="header_sheet_style_style">
					<span>部门人员关系表</span></br><span>（可选）</span>
				</div>
			</div>
			<div id="post_table" class="header_sheet_style" style="float: left;margin-left: 5px;width: 180px;" onclick="selectTable('post');">
				<div class="header_sheet_style_style">
					<span>岗位表与岗位人员关系表</span></br><span>（可选）</span>
				</div>
			</div>
			<!-- <div id="postperson_table" class="header_sheet_style" style="float: left;width: 128px;margin-left: 5px;" onclick="selectTable('postperson');">
				<div class="header_sheet_style_style">
					<span>岗位人员关系表</span></br><span>（可选）</span>
				</div>
			</div> -->
		</div>	
		</td>	
	</tr>
	<!-- 部门 -->
	<tr class="table dept">
		<td width="15%" style="text-align: end;">表名：</td>
		<td>
			<xform:select style="width: 65%;" className="table_name dept_table_name" onValueChange="selectTablename(this.value,'dept_colum_name','change')" property="value(kmss.oms.temp.dept_table_name)">
			</xform:select>
		</td>
	</tr>
	<tr class="table dept">
		<td width="15%"  style="text-align: end;">返回字段：</td>
		<td>
			<table width="80%" style="text-align: center;">
				<tr width="30%">
					<th>
                          <div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">名称</div>
                    </th>
                    <th style="padding-left: 1px;">
                     	<div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">返回属性</div>
                     </th>
                 </tr>
                 <tr class="table dept">
					<td width="30%" style="padding-top: 10px;">部门ID ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dept_colum_name dept_id" property="value(kmss.oms.temp.dept_id)" required="true"></xform:select>
					</td>
				</tr>
                 <tr class="table dept">
					<td  width="30%" style="padding-top: 10px;">部门名称：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dept_colum_name dept_name" property="value(kmss.oms.temp.dept_name)" required="true"></xform:select>
					</td>
				</tr>
                 <tr class="table dept">
					<td  width="30%" style="padding-top: 10px;">父部门ID：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dept_colum_name dept_parent_id" property="value(kmss.oms.temp.dept_parent_id)" required="true"></xform:select>
					</td>
				</tr>
				<tr class="table dept">
					<td width="30%" style="padding-top: 10px;">修改时间：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dept_colum_name dept_alter_time" property="value(kmss.oms.temp.dept_alter_time)" required="true"></xform:select>
					</td>
				</tr>
				<tr class="table dept">
					<td width="30%" style="padding-top: 10px;">排序号：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dept_colum_name dept_order" property="value(kmss.oms.temp.dept_order)"></xform:select>
					</td>
				</tr>
				<tr class="table dept">
					<td width="30%" style="padding-top: 10px;">是否有效：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 43%;" className="columname dept_colum_name dept_status" property="value(kmss.oms.temp.dept_status)" required="true"></xform:select>
						有效值为：<xform:text style="width: 40px;border: 0px;border-bottom: 1px solid #b4b4b4;" property="value(kmss.oms.temp.dept_valid_status)" required="true"></xform:text>
					</td>
				</tr>
				<tr class="table dept">
					<td width="30%" style="padding-top: 10px;">部门排序方式：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:radio property="value(kmss.oms.temp.dept_deptisasc)">
							<xform:simpleDataSource value="1">顺序(序号小的优先)</xform:simpleDataSource>
							<xform:simpleDataSource value="0">逆序(序号大的优先)</xform:simpleDataSource>	
						</xform:radio>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<!-- 人员 -->
	<tr class="table person">
		<td width="15%" style="text-align: end;">表名：</td>
		<td>
			<xform:select style="width: 55%;" className="table_name person_table_name" onValueChange="selectTablename(this.value,'person_colum_name','change')" property="value(kmss.oms.temp.person_table_name)"></xform:select>
		</td>
	</tr>
	<tr class="table person">
		<td width="15%"  style="text-align: end;">返回字段：</td>
		<td>
			<table width="80%" style="text-align: center;">
				<tr width="30%">
					<th>
                          <div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">名称</div>
                    </th>
                    <th style="padding-left: 1px;">
                     	<div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">返回属性</div>
                     </th>
                 </tr>
                 <tr class="table person">
					<td width="30%" style="padding-top: 10px;">人员ID ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_id" property="value(kmss.oms.temp.person_id)" required="true"></xform:select>
					</td>
				</tr>
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">人员姓名 ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_name" property="value(kmss.oms.temp.person_name)" required="true"></xform:select>
				</tr>
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">手机号 ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_mobile" property="value(kmss.oms.temp.person_mobile)"></xform:select>
					</td>
				</tr>
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">登录名 ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_login_name" property="value(kmss.oms.temp.person_login_name)"></xform:select>
					</td>
				</tr>
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">邮箱 ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_email" property="value(kmss.oms.temp.person_email)"></xform:select>
					</td>
				</tr>
				<tr class="table person">
					<td class="main_dept_id" width="30%" style="padding-top: 10px;">所属父部门ID：</td>
					<td class="main_dept_id" style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_dept_id" property="value(kmss.oms.temp.person_dept_id)"></xform:select>
					</td>
				</tr>
	
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">修改时间：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_alter_time" property="value(kmss.oms.temp.person_alter_time)" required="true"></xform:select>
					</td>
				</tr>
	
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">排序号：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_order" property="value(kmss.oms.temp.person_order)"></xform:select>
					</td>
				</tr>
				
						<tr class="table person">
					<td width="30%" style="padding-top: 10px;">工号：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_no" property="value(kmss.oms.temp.person_no)" required="false"></xform:select>
					</td>
				</tr>
				
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">办公电话：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_work_phone" property="value(kmss.oms.temp.person_work_phone)" required="false"></xform:select>
					</td>
				</tr>
				
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">备注：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname person_colum_name person_desc" property="value(kmss.oms.temp.person_desc)" required="false"></xform:select>
					</td>
				</tr>
	
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">是否有效：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 43%;" className="columname person_colum_name person_status" property="value(kmss.oms.temp.person_status)" required="true"></xform:select>
						有效值为：<xform:text style="width: 40px;border: 0px;border-bottom: 1px solid #b4b4b4;" property="value(kmss.oms.temp.person_valid_status)"  required="true"></xform:text>
					</td>
				</tr>
				
				<c:forEach items="${ldingExtendField}" var="personExtendField">
					<tr>
						<td width="30%" style="padding-top: 10px;">${personExtendField.fieldName}【拓展】：</td>
						<td style="text-align: left;padding-top: 10px;">
							<input type="hidden" id="person_expandekp_${personExtendField.field}" value="${personExtendField.field}"/>
							<xform:select htmlElementProperties='id="person_expand_${personExtendField.field}"' style="width: 85%;" className="columname person_colum_name expand_${personExtendField.field}" property="value(kmss.oms.temp.person_expand_${personExtendField.field})"></xform:select>
						</td>
					</tr>
				</c:forEach>
				<tr class="table person">
					<td width="30%" style="padding-top: 10px;">人员排序方式：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:radio property="value(kmss.oms.temp.person_personisasc)">
							<xform:simpleDataSource value="1">顺序(序号小的优先)</xform:simpleDataSource>
							<xform:simpleDataSource value="0">逆序(序号大的优先)</xform:simpleDataSource>	
						</xform:radio>
					</td>
				</tr>
            </table>
          </td>
     </tr>
	<!-- 岗位 -->
	<tr class="table post">
		<td width="15%" style="text-align: end;">岗位信息表名：</td>
		<td>
			<xform:select style="width: 55%;" className="table_name post_table_name" onValueChange="selectTablename(this.value,'post_colum_name','change')" property="value(kmss.oms.temp.post_table_name)"></xform:select>
		</td>
	</tr>
	<tr class="table post">
		<td width="15%"  style="text-align: end;">返回字段：</td>
		<td>
			<table width="80%" style="text-align: center;">
				<tr width="30%">
					<th>
                          <div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">名称</div>
                    </th>
                    <th style="padding-left: 1px;">
                     	<div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">返回属性</div>
                     </th>
                 </tr>
                 <tr class="table post">
					<td width="30%" style="padding-top: 10px;">岗位ID ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname post_colum_name post_id" property="value(kmss.oms.temp.post_id)" required="true"></xform:select>
					</td>
				</tr>
				<tr class="table post">
					<td width="30%" style="padding-top: 10px;">岗位名称：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname post_colum_name post_name" property="value(kmss.oms.temp.post_name)" required="true"></xform:select>
					</td>
				</tr>
				<tr class="table post">
					<td width="30%" style="padding-top: 10px;">所属部门ID：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname post_colum_name post_dept_id" property="value(kmss.oms.temp.post_dept_id)"></xform:select>
					</td>
				</tr>
				<tr class="table post">
					<td width="30%" style="padding-top: 10px;">修改时间：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname post_colum_name post_alter_time" property="value(kmss.oms.temp.post_alter_time)" required="true"></xform:select>
					</td>
				</tr>
				<tr class="table post">
					<td width="30%" style="padding-top: 10px;">排序号：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname post_colum_name post_order" property="value(kmss.oms.temp.post_order)"></xform:select>
					</td>
				</tr>
				<tr class="table post">
					<td width="30%" style="padding-top: 10px;">是否有效：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 43%;" className="columname post_colum_name post_status" property="value(kmss.oms.temp.post_status)" required="true"></xform:select>
						有效值为：<xform:text style="width: 40px;border: 0px;border-bottom: 1px solid #b4b4b4;" property="value(kmss.oms.temp.post_valid_status)" required="true"></xform:text>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr class="table postperson">
		<td width="15%" style="text-align: end;">岗位人员关系表名：</td>
		<td>
			<xform:select style="width: 55%;"  className="table_name pp_table_name" onValueChange="selectTablename(this.value,'pp_colum_name','change')" property="value(kmss.oms.temp.pp_table_name)"></xform:select>
		</td>
	</tr>
	<tr class="table post">
		<td width="15%"  style="text-align: end;">返回字段：</td>
		<td>
			<table width="80%" style="text-align: center;">
				<tr width="30%">
					<th>
                          <div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">名称</div>
                    </th>
                    <th style="padding-left: 1px;">
                     	<div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">返回属性</div>
                     </th>
                 </tr>
                 <tr class="table postperson">
					<td  width="30%" style="padding-top: 10px;">人员ID：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname pp_colum_name pp_person_id" property="value(kmss.oms.temp.pp_person_id)" required="true"></xform:select>
					</td>
				</tr>
                 <tr class="table postperson">
					<td width="30%" style="padding-top: 10px;">岗位ID ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname pp_colum_name pp_post_id" property="value(kmss.oms.temp.pp_post_id)" required="true"></xform:select>
					</td>
				</tr>
				<tr class="table postperson">
					<td width="30%" style="padding-top: 10px;">修改时间：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname pp_colum_name pp_alter_time" property="value(kmss.oms.temp.pp_alter_time)"></xform:select>
					</td>
				</tr>
				<tr class="table postperson">
					<td width="30%" style="padding-top: 10px;">是否有效：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 43%;" className="columname pp_colum_name pp_status" property="value(kmss.oms.temp.pp_status)" required="false"></xform:select>
						有效值为：<xform:text style="width: 40px;border: 0px;border-bottom: 1px solid #b4b4b4;" property="value(kmss.oms.temp.pp_valid_status)" required="false"></xform:text>
					</td>
				</tr>
              </table>
          </td>
     </tr>
	

	<!-- 部门人员关系 -->
	<tr class="table deptperson">
		<td width="15%" style="text-align: end;">表名：</td>
		<td>
			<xform:select style="width: 55%;" className="table_name dp_table_name" onValueChange="selectTablename(this.value,'dp_colum_name','change')" property="value(kmss.oms.temp.dp_table_name)"></xform:select>
		</td>
	</tr>
	<tr class="table deptperson">
		<td width="15%"  style="text-align: end;">返回字段：</td>
		<td>
			<table width="80%" style="text-align: center;">
				<tr width="30%">
					<th>
                          <div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">名称</div>
                    </th>
                    <th style="padding-left: 1px;">
                     	<div style="border: 1px solid #f6f6f6; background-color: #f6f6f6;">返回属性</div>
                     </th>
                 </tr>
                 <tr class="table deptperson">
					<td  width="30%" style="padding-top: 10px;">人员ID：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dp_colum_name dp_person_id" property="value(kmss.oms.temp.dp_person_id)" required="true"></xform:select>
					</td>
				</tr>
				 <tr class="table deptperson">
					<td  width="30%" style="padding-top: 10px;">部门ID ：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dp_colum_name dp_dept_id" property="value(kmss.oms.temp.dp_dept_id)" required="true"></xform:select>
					</td>
				</tr>
				<tr class="table deptperson">
					<td  width="30%" style="padding-top: 10px;">修改时间：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dp_colum_name dp_alter_time" property="value(kmss.oms.temp.dp_alter_time)"></xform:select>
					</td>
				</tr>
				<tr class="table deptperson">
					<td width="30%" style="padding-top: 10px;">排序号：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select style="width: 85%;" className="columname dp_colum_name dp_order" property="value(kmss.oms.temp.dp_order)"></xform:select>
					</td>
				</tr>
				<tr class="table deptperson">
					<td width="30%" style="padding-top: 10px;">是否有效：</td>
					<td style="text-align: left;padding-top: 10px;">
						<xform:select className="columname dp_colum_name dp_status" property="value(kmss.oms.temp.dp_status)"></xform:select>
						有效值为：<xform:text style="width: 40px;border: 0px;border-bottom: 1px solid #b4b4b4;" property="value(kmss.oms.temp.dp_valid_status)"></xform:text>
					</td>
				</tr>
			</table>
			</td>
		</tr>			
	</table>
	
</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.oms.SysOmsJdbcConfig" />
			<input type="hidden" id="person_extendFields" name="value(kmss.oms.temp.person_extendFields)"  value="" />
			<input type="hidden" id="kmss_oms_in_db_isall" name="value(kmss.oms.in.db.isall)"  value="" />
			<input type="hidden" id="syn_model" name="value(kmss.oms.temp.syn.model)"  value="" />
			<input type="hidden" id="person_is_main_dept" name="value(kmss.oms.temp.syn.person_is_main_dept)"  value="" />
			<input type="hidden" name="autoclose" value="false" />
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="updateConfig();"></ui:button>
			<%-- <ui:button text="全量同步" height="35" width="120" onclick="cleanTime();"></ui:button> --%>
			</center>
</html:form>
		
	 	<script type="text/javascript">
			$KMSSValidation();
			window.updateConfig = function() {
				var form = document.sysAppConfigForm;
				var method = 'update';
				
				var trPre = $("[id^='person_expandekp_']");
				var expandjsonarr = [];
				for (var i=0; i<trPre.length; i++){ 
					 var person_expandekp =  trPre[i].value;  //人员的推展字段
					 var person_expandsele = $("#person_expand_"+person_expandekp).find("option:selected").val();
					 if(person_expandsele != null && person_expandsele != ""){
						 var json = {};
						 json.expandekp = person_expandekp;
						 json.expandsele = person_expandsele;
						 expandjsonarr.push(json);
					 } 
				}
				if("[]" != JSON.stringify(expandjsonarr)){
					$("#person_extendFields").val(JSON.stringify(expandjsonarr));
				}
				//确定模式
				//部门ID
				var dept_id = $("[name='value(kmss.oms.temp.dept_table_name)']").find("option:selected").val();
				//人员
				var person_id = $("[name='value(kmss.oms.temp.person_table_name)']").find("option:selected").val();
				var person_dept_id = $(".person_dept_id").find("option:selected").val(); //主部门
				//岗位
				var post_id = $("[name='value(kmss.oms.temp.post_table_name)']").find("option:selected").val();
				//岗位人员关系
				var pp_person_id = $("[name='value(kmss.oms.temp.pp_table_name)']").find("option:selected").val();
				//部门人员关系
				var dp_person_id = $("[name='value(kmss.oms.temp.dp_table_name)']").find("option:selected").val(); 
				
				if(dept_id != "" && person_id != "" && post_id != "" && pp_person_id !="" && dp_person_id != ""){ //模式400
					$("#syn_model").val("400");
				}else if(dept_id != "" && person_id != "" && post_id != "" && pp_person_id !="" && dp_person_id == ""){
					$("#syn_model").val("300");
				}else if(dept_id != "" && person_id != "" && dp_person_id != ""  && post_id == "" && pp_person_id ==""){
					$("#syn_model").val("200");
					if(person_dept_id != ""){
						$("#person_is_main_dept").val("1");
					}else{
						$("#person_is_main_dept").val("0");
					}
				}else if(dept_id != "" && person_id != "" && dp_person_id == ""  && post_id == "" && pp_person_id ==""){
					$("#syn_model").val("100");
				}
				if(dp_person_id ==""){  //人员部门关系没选 去掉必填校验
					$("[name='value(kmss.oms.temp.dp_person_id)']").attr("validate","");
					$("[name='value(kmss.oms.temp.dp_dept_id)']").attr("validate","");
					$("[name='value(kmss.oms.temp.dp_status)']").attr("validate","");					
					$("[name='value(kmss.oms.temp.dp_valid_status)']").attr("validate","");					
					
				}
				if(post_id == ""){
					$("[name='value(kmss.oms.temp.post_id)']").attr("validate","");
					$("[name='value(kmss.oms.temp.post_name)']").attr("validate","");
					$("[name='value(kmss.oms.temp.post_alter_time)']").attr("validate","");
					$("[name='value(kmss.oms.temp.post_status)']").attr("validate","");
					$("[name='value(kmss.oms.temp.post_valid_status)']").attr("validate","");
					
					$("[name='value(kmss.oms.temp.pp_person_id)']").attr("validate","");
					$("[name='value(kmss.oms.temp.pp_post_id)']").attr("validate","");
					$("[name='value(kmss.oms.temp.pp_status)']").attr("validate","");
					$("[name='value(kmss.oms.temp.pp_valid_status)']").attr("validate","");
				}
				var isChecked = "true" == $("input[name='value(kmss.oms.in.db.enabled)']").val();
				if(isChecked){
					function validateAppConfigForm(thisObj) {
						return true;
					}
				}else{
					$("#kmss_oms_in_db_isall").val("0");
					var required = $("[validate='required']");
					for(var i = 0;i< required.length; i++ ){
						$(required[i]).attr("validate","");
					}
				}
				//校验必填字段是否有为空的
				var required = $("[validate='required']");
				for(var i = 0;i< required.length; i++ ){
					if("" ==required[i].value){
						alert("有必填校验为空，请填写！");
						return;
					}
				}				
				
				Com_Submit(form, method);
			}
			//数据源名称
			var datasource = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.datasource.name')}";
			
			//模式
			var model = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.syn.model')}";
			
			//表名
			var dept_table_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dept_table_name')}";
			var post_table_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.post_table_name')}";
			var person_table_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_table_name')}";
			var pp_table_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.pp_table_name')}";
			var dp_table_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dp_table_name')}";
			//部门字段名
			var dept_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dept_name')}";
			var dept_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dept_id')}";
			var dept_parent_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dept_parent_id')}";
			var dept_alter_time = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dept_alter_time')}";
			var dept_status = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dept_status')}";
			var dept_order = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dept_order')}";
			var deptisasc = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dept_deptisasc')}";
			//岗位字段名
			var post_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.post_name')}";
			var post_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.post_id')}";
			var post_dept_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.post_dept_id')}";
			var post_alter_time = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.post_alter_time')}";
			var post_status = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.post_status')}";
			var post_order = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.post_order')}";
			//人员字段名
			var person_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_name')}";
			var person_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_id')}";
			var person_dept_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_dept_id')}";
			var person_alter_time = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_alter_time')}";
			var person_status = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_status')}";
			var person_mobile = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_mobile')}";
			var person_login_name = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_login_name')}";
			var person_email = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_email')}";
			var person_order = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_order')}";
			var personisasc = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_personisasc')}";
			
			var person_no = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_no')}";
			var person_work_phone = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_work_phone')}";
			var person_desc = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.person_desc')}";
			
			//岗位人员关系字段名
			var pp_post_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.pp_post_id')}";
			var pp_person_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.pp_person_id')}";
			var pp_alter_time = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.pp_alter_time')}";
			var pp_status = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.pp_status')}";
			//部门人员关系字段名
			var dp_dept_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dp_dept_id')}";
			var dp_person_id = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dp_person_id')}";
			var dp_alter_time = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dp_alter_time')}";
			var dp_status = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dp_status')}";
			var dp_order = "${sysAppConfigForm.dataMap.get('kmss.oms.temp.dp_order')}";
			
			//shu ju yuan guan li
			function toDataSource(){
				window.open("${LUI_ContextPath}/component/dbop/cp/index.jsp","_blank");
			}
			
			function checkboxOnclick(thiss){
				if ( thiss.checked == true){
					$("#kmss_oms_in_db_isall").val("1");
				}else{
					$("#kmss_oms_in_db_isall").val("0");	 
				}
	     	}

			function config_chgEnabled() {
				var $enabledEle = $("input[name='value(kmss.oms.in.db.enabled)']");
				if($enabledEle.length == 0 ){
					setTimeout(config_chgEnabled, 500);
				}
				var isChecked = $enabledEle.val();
				if (isChecked) {
					$("#dbenable").show();
					init();
				} else {
					$("#dbenable").hide();
				}
			}
			
			seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function($, dialog, topic, dialogCommon) {
			//选择表
			function selectTable(val){
				$(".table").hide();
				if(val=='dept'){
					$(".dept").show();
					$("#post_table").removeClass("select_table_true");
					$("#deptperson_table").removeClass("select_table_true");
					$("#person_table").removeClass("select_table_true");
					$("#dept_table").addClass("select_table_true");
					//selectTablename2(dept_table_name,"dept_colum_name");
					
				}else if(val == "person"){
					$(".person").show();
					$("#dept_table").removeClass("select_table_true");
					$("#post_table").removeClass("select_table_true");
					$("#deptperson_table").removeClass("select_table_true");
					$("#person_table").addClass("select_table_true");
					//selectTablename2(person_table_name,"person_colum_name");
				}else if(val == "post"){
					$(".post").show();
					$(".postperson").show();
					$("#dept_table").removeClass("select_table_true");
					$("#person_table").removeClass("select_table_true");
					$("#deptperson_table").removeClass("select_table_true");
					$("#post_table").addClass("select_table_true");
					//selectTablename2(post_table_name,"post_colum_name");
					//selectTablename2(pp_table_name,"pp_colum_name");
				}else if(val == "deptperson"){
					$(".deptperson").show();
					$("#dept_table").removeClass("select_table_true");
					$("#person_table").removeClass("select_table_true");
					$("#post_table").removeClass("select_table_true");
					$("#deptperson_table").addClass("select_table_true");
					//selectTablename2(dp_table_name,"dp_colum_name");
				}
			
			}
						
			//当前数据源 
	        $.ajax({
				url : "${LUI_ContextPath}/component/dbop/compDbcp.do?method=list&rowsize=99999",
				type : 'get',
				async : true,
				dataType : "json",
				success : function(data) {		
					var list = data.datas;
					for(var i in list){  
						var data = list[i];
						var fdName = data[1].value;
						var option = "<option value='"+fdName+"'>"+fdName+"</option>";
						if(datasource == fdName){
							option = "<option  selected='selected' value='"+fdName+"'>"+fdName+"</option>";
						}
					
						$(".datasource").append(option);
					}
				}
			});
	     	
	     	//选择数据源
	     	function selectDatasource(value,str){
				if(str =="init"){
	     		}else{  //切换数据源
	     			$(".inputsgl").val("");
            		$("[name='value(kmss.oms.temp.dept_deptisasc)']").each(function () {
		    			if($(this).val()=="1"){
		    		  		$(this).attr("checked",true);
		    		  }
		     		});
		    		$("[name='value(kmss.oms.temp.person_personisasc)']").each(function () {
		    			if($(this).val()=="1"){
		    		  		$(this).attr("checked",true);
		    		  }
		     		});
	     		}
	     		$(".table_name").each(function () {
	     			$(this).val("");
	     			$(this).find("option:not(:first)").remove();
                	$(this).find("option:first").attr("selected",true);
	     		});
	     		//遍历所有的columname 然后清空除去第一行的option
	     		$(".columname").each(function () {
                   	$(this).val("");
                   	$(this).find("option:not(:first)").remove();
                	$(this).find("option:first").attr("selected",true);
            	});
	     		if(value==''){
		     		return;
	     		}
	     		
	     		datasource = value; 	
	     		var del_load = dialog.loading();
				$.ajax({
	     			url : "${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=getDataSourceTableName&fdSourceName="+encodeURI(datasource),
					type : 'get',
					dataType : "json",
					success : function(data) {
						$(".table_name").empty();
						var option1 = "<option value=''>==请选择==</option>";
						var list = data.data;
						$(".table_name").append(option1);
						var option="";
						for(var i in list){
							var data = list[i];
							option += "<option value='"+data+"'>"+data+"</option>";	
						}
						$(".table_name").append(option);
						if("init" == str ){
							if(dept_table_name != ""){
								$(".dept_table_name").val(dept_table_name);
					     	}
							if(post_table_name != ""){
					     		$(".post_table_name").val(post_table_name);
					     	}
							if(person_table_name != ""){
					     		$(".person_table_name").val(person_table_name);
					     	}
							if(pp_table_name != ""){
					     		$(".pp_table_name").val(pp_table_name);
					     	}
							if(dp_table_name != ""){
					     		$(".dp_table_name").val(dp_table_name);
					     	}
						}	
						//加载完成
						del_load.hide();
					} ,
					error : function(req) {
                        dialog.failure('操作失败: '+req.status+" " + req.statusText);
                        del_load.hide();
					}
				});
	     	}
	     	//选择表
	     	function selectTablename(value,colum_name,str){	
	     		//遍历所有的columname 然后清空除去第一行的option
	     		if("ini" != str ){
	     			$("."+colum_name).each(function () {
	                   	$(this).val("");
	                   	$(this).find("option:not(:first)").remove();
	                	$(this).find("option:first").attr("selected",true);
	            	})
	     			$("[name='value(kmss.oms.temp.dept_deptisasc)']").each(function () {
			    		if($(this).val()=="1"){
			    		  		$(this).attr("checked",true);
			    		  }
			     	}); 
	     			$("[name='value(kmss.oms.temp.person_personisasc)']").each(function () {
			    			if($(this).val()=="1"){
			    		  		$(this).attr("checked",true);
			    		  }
			     	});
	     			
	     			$("[name='value(kmss.oms.temp.person_desc)']").each(function () {
		    			if($(this).val()=="1"){
		    		  		$(this).attr("checked",true);
		    		  	}
		     		});
	     			
	     			$("[name='value(kmss.oms.temp.person_no)']").each(function () {
		    			if($(this).val()=="1"){
		    		  		$(this).attr("checked",true);
		    		  	}
		     		});
	     			
	     			$("[name='value(kmss.oms.temp.person_work_phone)']").each(function () {
		    			if($(this).val()=="1"){
		    		  		$(this).attr("checked",true);
		    		  	}
		     		});
	    			
	     		}
	     		if(value==''){
	     			 $("."+colum_name).find("option:first").attr("selected",true);
	     			return;
	     		}
	     		var del_load = dialog.loading();
	        	$.ajax({
					url : "${LUI_ContextPath}/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=getTableColumName&fdSourceName="+datasource+"&fdTableName="+value,
					type : 'get',
					async : true,
					dataType : "json",
					success : function(data) {
				  		var list = data.data;
				  		$("."+colum_name).empty();
				  		var option1 = "<option value=''>==请选择==</option>";
						$("."+colum_name).append(option1);
						var option="";
						for(var i in list){
							var data = list[i];
							option += "<option value='"+data+"'>"+data+"</option>";	
						}
						$("."+colum_name).append(option);	
						if("ini" == str){
							if(dept_name != ""){
								$(".dept_name").val(dept_name);
							}
							if(dept_id != ""){
								$(".dept_id").val(dept_id);
							}
							if(dept_parent_id != ""){
								$(".dept_parent_id").val(dept_parent_id);
							}
							if(dept_alter_time != ""){
								$(".dept_alter_time").val(dept_alter_time);
							}
							if(dept_order != ""){
								$(".dept_order").val(dept_order);
							}
							if(dept_status != ""){
								$(".dept_status").val(dept_status);
							}
							if(post_name != ""){
								$(".post_name").val(post_name);
							}
							if(post_id != ""){
								$(".post_id").val(post_id);
								$(".post_id").find("option:contains('"+post_id+"')").attr("selected",true);
							}
							if(dept_parent_id != ""){
								$(".post_dept_id").val(post_dept_id);
							}
							if(post_alter_time != ""){
								$(".post_alter_time").val(post_alter_time);
							}
							if(post_order != ""){
								$(".post_order").val(post_order);
							}
							if(dept_status != ""){
								$(".post_status").val(post_status);
							}
							if(person_name != ""){
								$(".person_name").val(person_name);
							}
							if(person_id != ""){
								$(".person_id").val(person_id);
								$(".person_id").find("option:contains('"+person_id+"')").attr("selected",true);
							}
							if(person_dept_id != ""){
								$(".person_dept_id").val(person_dept_id);
								$(".person_dept_id").find("option:contains('"+person_dept_id+"')").attr("selected",true);
							}
							if(dept_alter_time != ""){
								$(".person_alter_time").val(person_alter_time);
							}
							if(person_status != ""){
								$(".person_status").val(person_status);
							}
							if(person_mobile != ""){
								$(".person_mobile").val(person_mobile);
							}
							if(person_email != ""){
								$(".person_email").val(person_email);
							}
							if(person_login_name != ""){
								$(".person_login_name").val(person_login_name);
							}
							if(person_order != ""){
								$(".person_order").val(person_order);
							}			
							if(person_desc != ""){
								$(".person_desc").val(person_desc);
							}
							if(person_no != ""){
								$(".person_no").val(person_no);
							}
							if(person_work_phone != ""){
								$(".person_work_phone").val(person_work_phone);
							}
							//人员拓展字段
							var trPre = $("[id^='person_expandekp_']");
							for (var i=0; i<trPre.length; i++){ 
								 var person_expandekp =  trPre[i].value;  //人员的推展字段
								 var json = '${expand}';
								 if(json.length >0){
									 json= $.parseJSON(json);
									 for(var j=0;j<json.length;j++){
										if(json[j].expandekp == person_expandekp){
											$(".expand_"+person_expandekp).val(json[j].expandsele);
										} 
									 } 
								 } 
							}
							if(pp_post_id != ""){
								$(".pp_post_id").val(pp_post_id);
							}
							if(pp_person_id != ""){
								$(".pp_person_id").val(pp_person_id);
							}
							if(pp_alter_time != ""){
								$(".pp_alter_time").val(pp_alter_time);
							}
							if(pp_status != ""){
								$(".pp_status").val(pp_status);
							}
							if(dp_dept_id != ""){
								$(".dp_dept_id").val(dp_dept_id);
							}
							if(dp_person_id != ""){
								$(".dp_person_id").val(dp_person_id);
							}
							if(dp_alter_time != ""){
								$(".dp_alter_time").val(dp_alter_time);
							}
							if(dp_status != ""){
								$(".dp_status").val(dp_status);
							}
							if(dp_order != ""){
								$(".dp_order").val(dp_order);
							}
						}
						//加载完成
						del_load.hide();
					} ,
					error : function(req) {
                        dialog.failure('操作失败');
                        del_load.hide();
					}
				});
	     	}
	     	
	     	
	    	
	    	function init(){
	    		$(".dept").show();
	    		$("#dept_table").addClass("select_table_true");
	    		if(deptisasc ==""){
	    			$("[name='value(kmss.oms.temp.dept_deptisasc)']").each(function () {
		    			if($(this).val()=="1"){
		    		  		$(this).attr("checked",true);
		    		  }
		     		});
		    		$("[name='value(kmss.oms.temp.person_personisasc)']").each(function () {
		    			if($(this).val()=="1"){
		    		  		$(this).attr("checked",true);
		    		  }
		     		});
	    		}
	    		selectDatasource(datasource,"init");
	    		selectTablename(dept_table_name,"dept_colum_name","ini");
				selectTablename(person_table_name,"person_colum_name","ini");
				selectTablename(dp_table_name,"dp_colum_name","ini");
				selectTablename(post_table_name,"post_colum_name","ini");
				selectTablename(pp_table_name,"pp_colum_name","ini");
	    	}
	     	
	     	function cleanTime(){
	            dialog.confirm('是否执行全量同步(数据库接入)？', function(ok) {
	            	 if(ok == true) {
	            		var del_load = dialog.loading();
	            		var url = '<c:url value="/sys/oms/sys_oms_temp_config/sysOmsTempConfig.do?method=cleanTime" />';
	 					$.ajax({
	 					   type: "POST",
	 					   url: url,
	 					   async:true,
	 					   dataType: "json",
	 					   success: function(data){
	 						  	del_load.hide();
	 							if(data.status=="1"){
	 								dialog.success("刷新数据源成功");
	 							}else{
	 								dialog.failure("刷新数据源失败,"+data.msg);
	 							}
	 					   },
	 					   error: function(req) {
		                    	del_load.hide();
		                        dialog.failure("刷新数据源失败,详细请查看后台日志！");
		                    }
	 					}); 
	            	 }
	            });
			}

	    	window.cleanTime=cleanTime;
	    	window.selectTable=selectTable;
	    	window.selectDatasource=selectDatasource;
	    	window.selectTablename=selectTablename;
	    	window.config_chgEnabled=config_chgEnabled;
	    	window.init=init;
			config_chgEnabled();
		});
		</script>
	</template:replace>
</template:include>
