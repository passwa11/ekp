<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:event event="indexChanged" args="evt">
	if(lbpm && lbpm.globals && lbpm.globals.load_Frame && evt && evt.index){
		if(this.contents[evt.index.after] && this.contents[evt.index.after].id=="auditNoteRightContent"){
			lbpm.globals.load_Frame('auditNoteRightDiv', "${LUI_ContextPath}/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_right.jsp?fdModelId=${param.fdModelId}&fdModelName=${param.fdModelName}&formBeanName=${param.formName}&showPersonal=true");
		}
	}
</ui:event>
<ui:content id="auditNoteRightContent" title="${ lfn:message('sys-lbpmservice:lbpmProcess.history.title') }" titleicon="lui-fm-icon-4">
	<div id="auditNoteRightDiv">
		<iframe id="auditNoteRight" width="100%" style="margin-bottom: -4px;border: none;" scrolling="no" FRAMEBORDER=0></iframe>
	</div>
</ui:content>