define(function(){
	lbpmInitNodeDesc = function(nodeType,nodeDescType,isHandler,isAutomaticRun,isSubProcess,isConcurrent,isBranch,isGroup,isSub,uniqueMark){
		lbpm.nodeDescMap[nodeType]=nodeDescType;
		var nodedesc = lbpm.nodedescs[nodeDescType]={};
		nodedesc.isHandler = function(){return isHandler};
		nodedesc.isAutomaticRun = function(){return isAutomaticRun};
		nodedesc.isSubProcess = function(){return isSubProcess};
		nodedesc.isConcurrent = function(){return isConcurrent};
		nodedesc.isBranch = function(){return isBranch};
		nodedesc.isGroup = function(){return isGroup};
		nodedesc.isSub = function(){return isSub};
		nodedesc.uniqueMark = function(){return uniqueMark};
		nodedesc.getLines = function(nodeObj,nextNodeObj){
			return nodeObj.endLines;
		};
	}
});