<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
var addressComponent = layout.parent.ancestor,
	selectType = addressComponent.selectType;
{$
	<select class="lui-address-select-filter" data-lui-mark="lui-address-select-filter">
		<option value="-1">
			<bean:message bundle="sys-ui" key="address.all"/>
		</option>
$}

	if( (selectType & ORG_TYPE_ORG) == ORG_TYPE_ORG){
		{$
		<option value="1">
			<bean:message bundle="sys-organization" key="sysOrgElement.org"/>
		</option>
		$}
	}
	
	if( (selectType & ORG_TYPE_DEPT) == ORG_TYPE_DEPT){
		{$
		<option value="2">
			<bean:message bundle="sys-organization" key="sysOrgElement.dept"/>
		</option>
		$}
	}
	
	if( (selectType & ORG_TYPE_POST) == ORG_TYPE_POST){	
		{$
		<option value="4">
			<bean:message bundle="sys-organization" key="sysOrgElement.post"/>
		</option>
		$}
	}
	
	if( (selectType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON){	
		{$
		<option value="8">
			<bean:message bundle="sys-organization" key="sysOrgElement.person"/>
		</option>
		$}
	}
	
{$		
	</select>
$}