define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryItemMixin",
	"mui/i18n/i18n!sys-lbpmservice-support:lbpmSummaryApprovalConfig"
	], function(declare, _TemplateItemListMixin, LbpmSummaryItemMixin, Msg) {
	
	return declare("sys.lbpmservice.mobile.lbpmSummaryApproval.LbpmSummaryItemListMixin", [_TemplateItemListMixin], {
		isNotHandler:'',
		
		templateName:'',
		
		nodataText:'',
		
		itemTemplateString : null,
		
		itemRenderer: LbpmSummaryItemMixin,
		
		startup: function () {
			if(this.isNotHandler == 'true'){
				this.nodataText = Msg['lbpmSummaryApprovalConfig.listview.notHandler.message'];
			}else{
				this.nodataText = Msg['lbpmSummaryApprovalConfig.listview.empty.message'];
				this.nodataText = this.nodataText.replace(/\{0}/g, this.templateName);
			}
			this.inherited(arguments);
		}
	});
});
