<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<div class="lui_list_nav_expand_frame">
<ui:dataview>
	${varParams.source}
	<ui:render ref="sys.ui.nav.expand"/>
</ui:dataview> 
</div>