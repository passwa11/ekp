define(["dojo/query","dojo/NodeList-manipulate"],
		function(query){
	var drafterOpt={};
	
	drafterOpt['drafter_press']  = {
			click:function(){
				
			},
			check:function(){
				return true;
			},
			setOperationParam: function(param){
				param["notifyLevel"] = query("#OperationView input[name='ext_notifyLevel']").val();
				param["auditNote"]  = query("#OperationView textarea[name='ext_usageContent']").val();
			}
	};	
	
	drafterOpt.init = function(){
	};
	return drafterOpt;
});