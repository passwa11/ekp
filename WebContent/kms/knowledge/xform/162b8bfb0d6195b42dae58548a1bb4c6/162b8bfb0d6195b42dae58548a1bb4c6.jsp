<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@ page import="com.landray.kmss.util.*" %>
<% Object method = TagUtils.getFieldValue(request, "method");%>
<script>
 Com_IncludeFile('propertyUtil.js','../sys/property/define/');
 var _values = [];
 var str;
 var method = '<%=TagUtils.getFieldValue(request, "method")%>'
</script>


	<tr>
		<td valign="top" width="15%" class="td_normal_title">字符串来的</td>
		<td valign="top" id="162b8be43161cbc9b213dfd442580447"colspan="3">
			<xform:text property="extendDataFormInfo.value(att_20180412152459547daji)" style="width: 85%" subject="字符串来的" title="字符串来的" validators="maxLength(255)" isLoadDataDict="false" />
		</td>
	</tr>
	<tr>
		<td valign="top" width="15%" class="td_normal_title">整数</td>
		<td valign="top" id="162f64f872175a7b53d3d904c92abee2"colspan="3">
			<xform:text property="extendDataFormInfo.value(att_20180424142029862rSwA)" style="width: 85%" subject="整数" title="整数" validators="number maxLength(10)" isLoadDataDict="false" />
		</td>
	</tr>
	<tr>
		<td valign="top" width="15%" class="td_normal_title">单选</td>
		<td valign="top" id="1641c2888f2099e469abe104c6e94972"colspan="3">
			<xform:radio property="extendDataFormInfo.value(att_20180620154615925DFLo)" subject="单选" title="单选" mock="true" isLoadDataDict="false">
				<xform:simpleDataSource value="sz">深圳</xform:simpleDataSource>
				<xform:simpleDataSource value="gz">广州</xform:simpleDataSource>
				<xform:simpleDataSource value="sh">上海</xform:simpleDataSource>
				<xform:simpleDataSource value="bj">北京</xform:simpleDataSource>
			</xform:radio>
			<% if(!method.equals("view")&&!method.equals("view4adm")){%><a style="color:#1b84d5;" href="javascript:cancelSelect('1641c2888f2099e469abe104c6e94972')">不选</a><%}%>
		</td>
	</tr>
	<tr>
		<td valign="top" width="15%" class="td_normal_title">组织架构</td>
		<td valign="top" id="1670ae5e88ddbd1ac4b44d444539ef8d"colspan="3">
			<script>Com_IncludeFile("dialog.js");</script>
			<xform:address propertyId="extendDataFormInfo.value(att_20181113102818707MLXz.id)" propertyName="extendDataFormInfo.value(att_20181113102818707MLXz.name)" mulSelect="true" orgType="ORG_TYPE_PERSON" style="width: 98%" subject="组织架构" title="组织架构" isLoadDataDict="false" />
		</td>
	</tr>
	<tr>
		<td valign="top" width="15%" class="td_normal_title">组织架构部门</td>
		<td valign="top" id="1671a5ebad7358a2b7a0e89409c8dc91"colspan="3">
			<script>Com_IncludeFile("dialog.js");</script>
			<xform:address propertyId="extendDataFormInfo.value(att_20181116103435100lPTB.id)" propertyName="extendDataFormInfo.value(att_20181116103435100lPTB.name)" mulSelect="false" orgType="ORG_TYPE_ORGORDEPT" style="width: 98%" subject="组织架构部门" title="组织架构部门" isLoadDataDict="false" />
		</td>
	</tr>
