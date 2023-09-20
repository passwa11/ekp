<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
{$<select name="labelId" class="fixedSelect" style="width: 85px;">
		<option value="myEvent">
			${ lfn:message('km-calendar:kmCalendar.nav.title') }
		</option>
$}
var showLabelData = data["showLabelData"];
for(var i=0;i<showLabelData.length;i++){
	{$
	<option value="{%showLabelData[i].fdId%}">{%showLabelData[i].fdName%}</option>
	$}
}
{$</select>$}