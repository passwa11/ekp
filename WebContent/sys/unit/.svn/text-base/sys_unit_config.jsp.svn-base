<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<html:form action="/sys/unit/sys_unit_appconfig/sysUnitAppConfig.do">
<div style="margin-top:25px">
<p class="configtitle"><bean:message key="sysUnit.tree.displayConfig" bundle="sys-unit" /></p>
<center>
<table class="tb_normal" width=95%>
	
	<tr>
	  <td class="td_normal_title" width=20%>
			<bean:message bundle="sys-unit" key="setting.outCategory.default"/>
	  </td>
		<td colspan="3">
			<xform:dialog propertyName="value(outCategoryName)" propertyId="value(outCategoryId)" className="inputsgl" style="width:95%">
				 Dialog_Tree(false, 'value(outCategoryId)', 'value(outCategoryName)', ',', 'kmImissiveUnitCategoryTreeService&parentId=!{value}', '<bean:message key="kmImissiveUnit.fdCategoryId" bundle="sys-unit"/>', null, null, 'null', null, null, '<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdCategoryId"/>');
			</xform:dialog><br>
			<bean:message bundle="sys-unit" key="setting.outCategory.default.hint"/>
		</td>
	</tr>
	<tr>
	  <td class="td_normal_title" width=20%>
	  	<bean:message bundle="sys-unit" key="sysUnit.config.dataChange"/> 
	  </td>
		<td colspan="3">
			<ui:switch property="value(exchange)" checkVal="1" unCheckVal="0"
					   enabledText="${lfn:message('sys-unit:sysUnit.config.open')}"
					   disabledText="${lfn:message('sys-unit:sysUnit.config.close')}"></ui:switch>
		</td>
	</tr>
	<%--<tr class="decConfig">
	  <td class="td_normal_title" width=20%>
	  	<bean:message bundle="sys-unit" key="sysUnit.config.sysCode"/> 
	  </td>
		<td colspan="3">
			<xform:text property="value(unitCode)" subject="${lfn:message('sys-unit:sysUnit.config.sysCode')}"  style="width:95%" showStatus="edit"/>
			<br>
			<bean:message bundle="sys-unit" key="sysUnit.config.sysCode.desc"/> 
		</td>
	</tr>--%>
	<%-- <tr class="decConfig">
	  <td class="td_normal_title" width=20% rowspan="3">
			数据交换中心配置
	  </td>
		<td colspan="3">
			访问地址配置:<xform:text property="value(fdDecUrl)" subject="交换中心地址" style="width:84%"  showStatus="edit"/>
		</td>
	</tr>
	<tr class="decConfig">
		<td>
			数据对接账号:<xform:text property="value(fdDecUser)" subject="数据对接账号"  style="width:84%" showStatus="edit"/>
		</td>
	</tr>
	<tr class="decConfig">
		<td>
			数据对接密码:<xform:text property="value(fdDecPassword)" subject="数据对接密码"  style="width:84%" showStatus="edit"/>
			<br>
			(注:账号和密码是数据交换中心的对接账号和密码)
		</td>
	</tr> --%>
	<%--<tr class="decConfig">
	  <td class="td_normal_title" width=20%> 
			<bean:message bundle="sys-unit" key="sysUnit.config.sysCode.desc"/> 
	  </td>
		<td colspan="3">
			<xform:address mulSelect="true" orgType="ORG_TYPE_PERSON" propertyName="value(fdSecretaryNames)" propertyId="value(fdSecretaryIds)" 
			subject="${lfn:message('sys-unit:sysUnit.config.sysDocument')}"  style="width:95%" showStatus="edit"></xform:address>
			<br>
			<bean:message bundle="sys-unit" key="sysUnit.config.sysDocument.desc"/> 
			
		</td>
	</tr>--%>
	<%-- <tr class="decConfig">
	  <td class="td_normal_title" width=20%>
			附件传输方式
	  </td>
		<td colspan="3">
			<xform:radio property="value(fdDecFileType)" subject="附件传输方式" showStatus="edit" >
				<xform:simpleDataSource value="base64">Base64</xform:simpleDataSource>
				<xform:simpleDataSource value="rest">Rest接口下载</xform:simpleDataSource>
			</xform:radio>
			<br>
			(注:默认使用Base64方式传输附件)
		</td>
	</tr>
	<tr class="decConfig">
	  <td class="td_normal_title" width=20%>
			公文传输方式
	  </td>
		<td colspan="3">
			<xform:radio property="value(fdDecContentType)" subject="公文传输方式" showStatus="edit" >
				<xform:simpleDataSource value="xml">xml</xform:simpleDataSource>
				<xform:simpleDataSource value="json">json</xform:simpleDataSource>
			</xform:radio>
			<br>
			(注:默认使用xml方式传输)
		</td>
	</tr> --%>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
</div>
</center>
</div>
<script>

LUI.ready(function(){
	changeExchange();
});

function changeExchange(){
	  var showImgDoc=document.getElementsByName("value(exchange)")[0];
	  if(showImgDoc.value == "0"){
			$('.decConfig').hide();
	  }else{
		  $('.decConfig').show();
	  }
	}
</script>
</html:form> 
</template:replace>
</template:include>