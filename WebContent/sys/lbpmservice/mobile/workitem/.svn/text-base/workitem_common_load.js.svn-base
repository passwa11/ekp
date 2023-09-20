define(["dojo/query", "dojo/_base/array", "dijit/registry", "dojo/topic", "dojo/store/Memory","sys/lbpmservice/mobile/common/syslbpmprocess"], 
		function(query, array, registry, topic, Memory, syslbpmprocess) {
	var commonLoad={};
	var loadWorkflowInfo=function(){
		var processorInfoObj = lbpm.globals.getProcessorInfoObj();
		if(processorInfoObj != null){
			var operationItemsRow = query("#operationItemsRow")[0];
			if(operationItemsRow != null){
					var operationItemsSelect = registry.byId("operationItemsSelect");
				  	if(operationItemsSelect != null){
			  			var data = [];
				  		array.forEach(processorInfoObj, function(pObj, i) {
				  			var processInfoShowText = "";
				  			var parentHandlerName= (pObj.parentHandlerName ? pObj.parentHandlerName + "：" : "");
							var langNodeName = WorkFlow_getLangLabel(lbpm.nodes[pObj.nodeId].name,lbpm.nodes[pObj.nodeId]["langs"],"nodeName");
				  			if(pObj.expectedName){
								if(lbpmIsHideAllNodeIdentifier()){
									processInfoShowText=parentHandlerName + langNodeName+"("+pObj.expectedName+")";
								}else{
									if(lbpmIsHideAllNodeIdentifier()){
										processInfoShowText=parentHandlerName + langNodeName;
									}else{
										processInfoShowText=pObj.nodeId +"."+ parentHandlerName + langNodeName;
									}
								}
				  			}else{
								processInfoShowText=pObj.nodeId +"."+ parentHandlerName + langNodeName;
							}
				  			data.push({text:processInfoShowText, value:i + ''});
				  		});
				  		operationItemsSelect.setStore(new Memory({
							data : data
						}));
				  		operationItemsSelect.on("attrmodified-value", function(evt) {
				  			var index = evt.detail.newValue;
				  			lbpm.globals.operationItemsChanged({selectedIndex: parseInt(index)}, false);
				  		});
				  	} else {
				  		operationItemsSelect = query("#operationItemsSelect")[0];
				  		if (operationItemsSelect != null) {
				  			var selectedIndex = 0;
						  	for(var i=0; i < processorInfoObj.length; i++){
								if (processorInfoObj[i] == lbpm.nowProcessorInfoObj) {
									selectedIndex = i;
									break;
								}
							}
							operationItemsSelect.value = selectedIndex;
				  		}
				  	}
				  	if(processorInfoObj.length == 1) lbpm.globals.hiddenObject(operationItemsRow, true); else lbpm.globals.hiddenObject(operationItemsRow, false);
			}	
		}
	};
	
	var loadDefaultParameters=function(){
		var operatorInfo = lbpm.globals.getProcessorInfoObj();
		if(operatorInfo == null){
			lbpm.globals.validateControlItem();
			return;
		}
		lbpm.globals.getCurrentNodeDescription();
		var operationItemsSelect = registry.byId("operationItemsSelect") || query("#operationItemsSelect")[0];
		if (operationItemsSelect && operationItemsSelect.value && operationItemsSelect.value != "") {
			lbpm.globals.operationItemsChanged({selectedIndex: parseInt(operationItemsSelect.value)},false);
		}
	};
	var lbpmIsHideAllNodeIdentifier=function(){
		var isHideAllNodeIdentifier = false;
		if (lbpm && lbpm.settingInfo){
			if (lbpm.settingInfo.isHideNodeIdentifier === "true" && lbpm.settingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier"){
				isHideAllNodeIdentifier = true;
			}
		}
		return isHideAllNodeIdentifier;
	};
	commonLoad.init = function() {
		if(lbpm.nowProcessorInfoObj==null) return;
		loadWorkflowInfo();
		setTimeout(function(){
			loadDefaultParameters();
		},0);
		topic.subscribe("/lbpm/operation/switch", function(wgt,ctx){
			// 只在切换事务时触发
			if(ctx && !ctx.methodSwitch){
				loadDefaultParameters();
			}
		});
		setTimeout("lbpm.globals.getThroughNodes(null,null,null,null,null,true)",1);
	};
	return commonLoad;
});