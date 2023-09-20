<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.*" %>	

	<tr>
		<td class='muiTitle'>
			字符串来的</td><td>
			<xform:text property="extendDataFormInfo.value(att_20180412152459547daji)" subject="字符串来的" title="字符串来的" mobile="true" validators="maxLength(255)" isLoadDataDict="false" />
		</td>
	</tr>
	<tr>
		<td class='muiTitle'>
			整数</td><td>
			<xform:text property="extendDataFormInfo.value(att_20180424142029862rSwA)" subject="整数" title="整数" mobile="true" validators="number maxLength(10)" isLoadDataDict="false" />
		</td>
	</tr>
	<tr>
		<td class='muiTitle'>
			单选</td><td>
			<xform:radio property="extendDataFormInfo.value(att_20180620154615925DFLo)" subject="单选" title="单选" mobile="true" concentrate="true" isLoadDataDict="false">
				<xform:simpleDataSource value="sz">深圳</xform:simpleDataSource>
				<xform:simpleDataSource value="gz">广州</xform:simpleDataSource>
				<xform:simpleDataSource value="sh">上海</xform:simpleDataSource>
				<xform:simpleDataSource value="bj">北京</xform:simpleDataSource>
			</xform:radio>
		</td>
	</tr>
	<tr>
		<td class='muiTitle'>
			组织架构</td><td>
			<xform:address propertyId="extendDataFormInfo.value(att_20181113102818707MLXz.id)" propertyName="extendDataFormInfo.value(att_20181113102818707MLXz.name)" mulSelect="true" orgType="ORG_TYPE_PERSON" mobile="true" subject="组织架构" title="组织架构" isLoadDataDict="false" />
		</td>
	</tr>
	<tr>
		<td class='muiTitle'>
			组织架构部门</td><td>
			<xform:address propertyId="extendDataFormInfo.value(att_20181116103435100lPTB.id)" propertyName="extendDataFormInfo.value(att_20181116103435100lPTB.name)" mulSelect="false" orgType="ORG_TYPE_ORGORDEPT" mobile="true" subject="组织架构部门" title="组织架构部门" isLoadDataDict="false" />
		</td>
	</tr>
