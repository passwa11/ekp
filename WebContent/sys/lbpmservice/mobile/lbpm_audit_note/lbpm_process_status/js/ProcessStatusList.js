define( [ 
	"dojo/_base/declare",
	"dojo/topic", 
	"dojo/dom-construct", 
	"dojo/_base/array",
	"dojo/_base/lang", 
    "mui/util", 
    "mui/list/JsonStoreList" 
    ], function(declare, topic, domConstruct, array, lang,
		util, JsonStoreList) {
	return declare("sys.lbpmservice.mobile.lbpm_audit_note.lbpm_process_status.js.ProcessStatusList", [ JsonStoreList ], {
		
		postCreate : function() {
			this.inherited(arguments);
			
		}		
	});
});