define(["mui/dialog/Tip","dojo/query","dojo/NodeList-manipulate"],
		function(tip, query){
	var drafterOpt={};
	
	drafterOpt['drafter_abandon']  = {
			click:function(){
			},
			check:function(){
				var txt = query("#OperationView textarea[name='ext_usageContent']").val();
				if(txt!="" && txt.trim()!=""){
					return true;
				}
				tip.fail({text:"请填写处理意见！"});
				return false;
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