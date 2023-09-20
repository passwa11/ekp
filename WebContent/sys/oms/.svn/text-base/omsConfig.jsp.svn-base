<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan=2><b>组织架构同步参数配置</b></td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">组织导入提交每批次数量</td>
		<td>
			<html:text property="value(kmss.oms.in.batch.size)" style="width:85%"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">组织导入时删除记录数阀值</td>
		<td>
			<html:text property="value(kmss.oms.in.delete.size)" style="width:10%"/>
			<span class="message">执行oms导入时，如果需要删除的记录数超过这个值，则不直接执行“置为无效”操作，而是发邮件给管理员，由管理员手动执行；不设的话则同步时会执行“置为无效”操作</span>
		</td>
	</tr>
	<tr>
	<td class="td_normal_title" width="15%">每次同步接入之前备份组织架构数据</td>
		<td>
			<label> 
				<html:checkbox property="value(kmss.oms.in.organization.backup)" value="true"/>启用
			</label>
			<br><span class="txtstrong">注意：该选项主要是针对ldap接入时防止ldap配置错误或者ldap数据大批量改动等造成的同步后组织架构数据错乱，还原困难而设置的；从其它地方接入组织架构的话，一般无需启用该项。
			如果同步后组织架构有问题，可以访问该链接还原组织架构“/sys/organization/sys_org_element_bak/sysOrgElementBak.do?method=restore”（请谨慎执行该操作，还原成功后，需到数据库里面手动还原同步时间戳）</span>
		</td>
	</tr>
	<tr>
	<td colspan="2">
	<span class="txtstrong">同步通知配置在这里进行设置：<a target="_blank" href='<c:url value="/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=edit&fdId=1"/>'>/sys/oms/orgsynchro_notify_template_empty/orgSynchroNotifyTemplateEmpty.do?method=edit&fdId=1</a></span>
	</td>
	</tr>
	
</table>