define(['dojo/query', "dijit/registry"],function( query, registry){
	var extOperation={};
	extOperation['history_handler_addOpinion'] = {
		click: function(){
			
		},
		
		check: function(){
			return true;
		},
		
		setOperationParam : function(param){
			param["notifyLevel"] = query("#OperationView input[name='ext_notifyLevel']").val();
			param["auditNote"]  = query("#OperationView textarea[name='ext_usageContent']").val();
		}
	};
	
	extOperation.init = function(){
		
	};
	return extOperation;
});
