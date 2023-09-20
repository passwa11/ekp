define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/lbpmservice/mobile/lbpm_audit_note/lbpm_process_status/js/ProcessStatusItemMixin"
	], function(declare, _TemplateItemListMixin, ProcessStatusItemMixin) {
	
	return declare("sys.lbpmservice.mobile.lbpm_audit_note.lbpm_process_status.js.ProcessStatusItemListMixin", [_TemplateItemListMixin], {
		
		itemTemplateString : null,
		
		itemRenderer: ProcessStatusItemMixin
	});
});