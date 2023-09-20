var OtherVarInfos;
/*function getOtherVarInfos(){
	if(!OtherVars){
		var handler = Data_GetResourceString("sys-rule:rule.otherVar.handler");
		var creator = Data_GetResourceString("sys-rule:rule.otherVar.creator");
		OtherVars = [{'text':handler,'value':'%handler%','title':handler, 'type':'com.landray.kmss.sys.organization.model.SysOrgPerson'},
						{'text':creator,'value':'%creator%','title':creator, 'type':'com.landray.kmss.sys.organization.model.SysOrgPerson'}
		];
	}
	return OtherVars;
}*/
function getOtherVarInfos(){
	if(!OtherVarInfos){
		var beanName = "getAllInternalVarService&";
		var otherVarInfos = new KMSSData().AddBeanData(beanName).GetHashMapArray();
		var newVars = [];
		if(otherVarInfos && Object.prototype.toString.call(otherVarInfos) === '[object Array]'){
			OtherVarInfos = [];
			otherVarInfos.forEach(function(item,index){
				var key = item.key;
				var value = item.value;
				var vars = item.otherVars;
				if(typeof vars === "string"){
					vars = eval('('+vars+')');
				}
				for(var i=0; i<vars.length; i++){
					var varItem = vars[i];
					var obj = {
						'text':varItem.text,
						'value':"%"+key+"."+varItem.value+"%",
						'type':varItem.type
					}
					newVars.push(obj);
				}
				var newItem ={
						"key":key,
						"value":value,
						"vars":newVars
				}
				OtherVarInfos.push(newItem);
			})
		}
	}
	return OtherVarInfos;
}
