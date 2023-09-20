<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<div>
<ui:dataview>
	${varParams.source}
	<ui:render ref="sys.ui.nav.no.expand"/>
</ui:dataview> 
</div>