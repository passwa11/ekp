/*******************************************************************************
 * 功能：分支特权人-结束分支操作
 ******************************************************************************/
(function(operations) {
	operations["branchadmin_endbranch"] = {
			click:OperationClick,
			check:OperationCheck,
			setOperationParam:setOperationParam
	};		
	// 分支特权人操作：结束分支操作
	function OperationClick(operationName) {
		var operationsRow = document.getElementById("operationsRow");
		var operationsTDTitle = document.getElementById("operationsTDTitle");
		var operationsTDContent = document.getElementById("operationsTDContent");
		operationsTDTitle.innerHTML = lbpm.constant.opt.endbranch;
		var splitNode=_getSplitNode(lbpm.nodes[lbpm.nowNodeId]);
		var kmssData = new KMSSData();
		kmssData.AddBeanData("getConBranchStatusService&type=runing&splitNode="+splitNode.id+"&processId="+lbpm.modelId);
		var data = kmssData.GetHashMapArray();
		var html="";
		for(var j=0;j<data.length;j++){
			var name = data[j].name;
			if(/L\d+\.\S+/g.test(name)){
				name = /L\d+\.(\S+)/g.exec(name)[1];
			}else if (/N\d+\.\S+/g.test(name) && lbpm.settingInfo && lbpm.settingInfo.isHideNodeIdentifier === "true" && lbpm.settingInfo.hideNodeIdentifierType === "isHideAllNodeIdentifier"){
				name = /N\d+\.(\S+)/g.exec(name)[1];
			}
			html += "<label class='lui-lbpm-checkbox'><input type='checkbox' name='endConbranchsObj' key='endConbranchs'  value='" + data[j].id
			+ "' onclick=''><span class='checkbox-label'>"+name+"</span></input></label>";
			if(j<data.length-1){
				html += "<br/>";
			}
		}
		operationsTDContent.innerHTML=html;
		if(lbpm.approveType == "right"){
			$(operationsTDTitle).prepend("<span class='txtstrong'>*</span>");
		}
		lbpm.globals.hiddenObject(operationsRow, false);
	};
	//“结束分支操作”操作的检查
	function OperationCheck(workitemObjArray){
		if($('input[name="endConbranchsObj"]:checked').length==0){
			alert(lbpm.constant.opt.endbranchMsg);
			return false;
		}
		return true;
	};	
	//设置"结束分支操作"操作的参数
	function setOperationParam(){
		var checkedBranchNodes=[];
		$.each($('input[name="endConbranchsObj"]:checked'),function(){
			 checkedBranchNodes.push($(this).val());
        });
		lbpm.globals.setOperationParameterJson(checkedBranchNodes.join(";"),"endConbranchs", "param");
	};
	//获取启动分支节点
	function _getSplitNode(nowNode){
		for(var i=0;i<nowNode.startLines.length;i++){
			var lineObj=nowNode.startLines[i];
			var startNode = lineObj.startNode;
			if(!startNode){
				continue;
			}
			if(startNode.XMLNODENAME == "splitNode"){
				//考虑分支嵌套的情况，要跳过对应的启动分支
				if(window.routeJoinNodeRelatedNodeIds && window.routeJoinNodeRelatedNodeIds.indexOf(startNode.id) != -1){
					var result = _getSplitNode(startNode);
					if(result == null){
						continue;
					}else{
						return result;
					}
				}else{
					return startNode;
				}
			}else{
				if(startNode.XMLNODENAME == "joinNode"){
					//考虑分支嵌套的情况，要跳过对应的启动分支
					if(!window.routeJoinNodeRelatedNodeIds){
						window.routeJoinNodeRelatedNodeIds = [];
					}
					if(window.routeJoinNodeRelatedNodeIds.indexOf(startNode.relatedNodeIds) == -1){
						window.routeJoinNodeRelatedNodeIds.push(startNode.relatedNodeIds);
					}
				}
				var result = _getSplitNode(startNode);
				if(result == null){
					continue;
				}else{
					return result;
				}
			}
		}
		return null;
	};
})(lbpm.operations);