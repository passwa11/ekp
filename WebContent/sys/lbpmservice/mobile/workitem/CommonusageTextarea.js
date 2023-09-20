define(["dojo/_base/declare","mui/form/Textarea"],function(declare,Textarea){
	return declare("sys.lbpmservice.mobile.workitem.CommonusageTextarea",[Textarea],{
		postCreate : function(){
			this.subscribe("/lbpm/commonUsageView/rest","_setValue");
		},
		
		_setValue:function(args){
			var value = args.value;
			this._setValueAttr(value);
		}
	})
})