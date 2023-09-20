<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">

	<template:replace name="title">
		${lfn:message('kms-lservice:module.kms.lservice.config')}
	</template:replace>

	<template:replace name="content">

		<h2 align="center" style="margin: 10px 0">

			<span style="color: #35a1d0;">
				模块配置</span>

		</h2>

		<html:form action="/kms/lservice/lserviceConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" colspan="1">个人学习中心模块配置</td>
						<td colspan="2" >
							<div style="margin: 10px 10px 30px 20px;">
							 	<xform:checkbox  property="value(kmss.lservice.study.enabled)" dataType="String"  
									subject="学习管理" showStatus="edit">
									<xform:simpleDataSource value="1">学习管理</xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox  property="value(kmss.lservice.exam.enabled)" dataType="String" 
									subject="学习管理" showStatus="edit">
									<xform:simpleDataSource value="1">考试管理</xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox  property="value(kmss.lservice.train.enabled)" dataType="String" 
									subject="学习管理" showStatus="edit">
									<xform:simpleDataSource value="1">培训管理</xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox  property="value(kmss.lservice.live.enabled)" dataType="String" 
									subject="学习管理" showStatus="edit">
									<xform:simpleDataSource value="1">直播管理</xform:simpleDataSource>
								</xform:checkbox>
							</div>
							<div style="margin: 10px 10px 10px 20px;">
								<xform:checkbox  property="value(kmss.lservice.tutor.enabled)" dataType="String" 
									subject="学习管理" showStatus="edit">
									<xform:simpleDataSource value="1">导师管理</xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox  property="value(kmss.lservice.homeword.enabled)" dataType="String" 
									subject="学习管理" showStatus="edit">
									<xform:simpleDataSource value="1">作业管理</xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox  property="value(kmss.lservice.reminder.enabled)" dataType="String" 
									subject="学习管理" showStatus="edit">
									<xform:simpleDataSource value="1">每日提醒</xform:simpleDataSource>
								</xform:checkbox>
								<xform:checkbox  property="value(kmss.lservice.tcourse.enabled)" dataType="String" 
									subject="学习管理" showStatus="edit">
									<xform:simpleDataSource value="1">培训班</xform:simpleDataSource>
								</xform:checkbox>
							</div>
						</td>
					</tr>
				</table>
			</center>


			<html:hidden property="method_GET" />

			<input type="hidden" name="modelName"
				value="com.landray.kmss.kms.lservice.config.PstudyConfig" />
			<input type="hidden" name="autoclose" value="false" />


			<center style="margin-top: 10px;">
				<ui:button text="${lfn:message('button.save')}" height="35"
					width="120"
					onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>

		</html:form>


	</template:replace>
</template:include>
