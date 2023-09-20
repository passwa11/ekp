define(["dojo/_base/lang", 
         "dojo/_base/array",
         "dojo/topic",
         "dijit/registry", 
         "dojo/ready", 
         "mui/util",
         "mui/form/Address",
         'dojo/query',
		 "dojo/request",
		 "mui/dialog/Tip",
		 "dojo/dom-style",
         'dojo/NodeList-dom', 
         'dojo/NodeList-html', 
         "dojo/domReady!"], function(lang, array, topic, registry, ready, util, Address, query, request, Tip, domStyle) {

	lbpm.globals.canAddOtherNode = function(nodeId){
		if(lbpm.freeFlow.isCanAddOtherNode && lbpm.freeFlow.nextNodes){
			if(lbpm.freeFlow.nextNodes.length==0){
				this.getNextFreeFlowNodes(lbpm.nowNodeId);
			}
			if(Com_ArrayGetIndex(lbpm.freeFlow.nextNodes, nodeId) == -1){
				return false;
			}
		}
		return true;
	};

	lbpm.globals.getNextFreeFlowNodes = function(nodeId){
		if(Com_ArrayGetIndex(lbpm.freeFlow.nextNodes, nodeId) == -1){
			lbpm.freeFlow.nextNodes.push(nodeId);
		}
		var nodeObj = lbpm.globals.getNodeObj(nodeId);
		for(var i = 0 ;i<nodeObj.endLines.length;i++){
			var nextNode = nodeObj.endLines[i].endNode;
			if(nextNode.XMLNODENAME != "endNode" && nextNode.XMLNODENAME != "joinNode"){
				this.getNextFreeFlowNodes(nextNode.id);
			}else if(nextNode.XMLNODENAME == "joinNode" && Com_ArrayGetIndex(lbpm.freeFlow.nextNodes, nextNode.relatedNodeIds) > -1){
				this.getNextFreeFlowNodes(nextNode.id);
			}
		}
	};

	//自由流锁更新
	lbpm.globals.saveOrUpdateFreeflowVersion=function(){
		var url = util.formatUrl('/sys/lbpmservice/support/lbpmFreeflowVersionAction.do?method=saveOrUpdateFreeflowVersion');
		var data = {"fdProcessId":lbpm.modelId};
		request.post(url, {handleAs:'json', sync:false, data:data}).then(function(json) {
			if(json){
				lbpm.freeFlow.editTime = new Date();
			}
		});
	};

	//自由流是否可编辑
	lbpm.globals.isCanEdit=function(){
		var isCanEdit = true;
		if(lbpm.freeFlow.editTime){
			var splitTime = parseInt(Lbpm_SettingInfo["freezeFreeFlowTime"]);
			if(new Date() - lbpm.freeFlow.editTime < splitTime*60*1000){
				return isCanEdit;
			}
		}
		var url = util.formatUrl('/sys/lbpmservice/support/lbpmFreeflowVersionAction.do?method=isCanEdit');
		var data = {"fdProcessId":lbpm.modelId};
		request.post(url, {handleAs:'json', sync:true, data : data}).then(function(json) {
			if(json){
				isCanEdit = json.isCanEdit;
				if(isCanEdit){
					if(json.needCheck){
						var oldXmlObj = XML_CreateByContent(lbpm.globals.getProcessXmlString());
						var oldXml = (oldXmlObj.xml || new XMLSerializer().serializeToString(oldXmlObj));
						var nowXmlObj = null;
						var data = new KMSSData();
						data.UseCache = false;
						data.AddBeanData("lbpmProcessDefinitionDetailService&processId="+lbpm.globals.getWfBusinessFormModelId());
						var result = data.GetHashMapArray();
						if(result && result.length>0){
							if(result[0]["key0"]){
								processXml = result[0]["key0"];
								nowXmlObj = XML_CreateByContent(processXml);
							}
						}
						var nowXml = (nowXmlObj.xml || new XMLSerializer().serializeToString(nowXmlObj));
						if(nowXml != oldXml){
							isCanEdit = false;
							var tip = Tip["warn"]({text:json.msg});
							domStyle.set(tip.textNode,"-webkit-line-clamp",'inherit');
							domStyle.set(query("span",tip.containerNode)[0],"-webkit-line-clamp",'inherit');
						}
					}
				}else{
					if (json.msg) {
						var tip = Tip["warn"]({text:json.msg});
						domStyle.set(tip.textNode,"-webkit-line-clamp",'inherit');
						domStyle.set(query("span",tip.containerNode)[0],"-webkit-line-clamp",'inherit');
					}
				}
			}
		});
		return isCanEdit;
	};

	Com_Parameter.event["confirm"].push(function(){
		//清除锁信息
		var url = util.formatUrl('/sys/lbpmservice/support/lbpmFreeflowVersionAction.do?method=deleteVersionOnConfirm');
		var data = {"fdProcessId":lbpm.modelId};
		request.post(url, {handleAs:'json', sync:true, data:data}).then(function(json) {
			if(json){

			}
		});
		return true;
	});

	//自由流保存时，需校验流程是否可以编辑
	Com_Parameter.event["submit"].push(function(){
		if(lbpm.isFreeFlow){
			return lbpm.globals.isCanEdit();
		}
		return true;
	});

	var freeflow={};
	
	freeflow.init=function(){
		var fdIsModify=$("input[name='sysWfBusinessForm.fdIsModify']")[0];
		if(fdIsModify==null || fdIsModify.value!="1"){
			var processXml = lbpm.globals.getProcessXmlString();
			document.getElementsByName("sysWfBusinessForm.fdFlowContent")[0].value = processXml;
		}
		var processDefine=WorkFlow_LoadXMLData(lbpm.globals.getProcessXmlString());
		if(processDefine && processDefine.isCanAddOtherNode && processDefine.isCanAddOtherNode=="true"){
			lbpm.freeFlow.isCanAddOtherNode = true;
		}else{
			lbpm.freeFlow.isCanAddOtherNode = false;
		}
		setTimeout(function(){
			lbpm.flow_chart_load_Frame();
			// 记录新添加的节点
			lbpm.myAddedNodes=new Array();

			var html = '<div data-dojo-type="sys/lbpmservice/mobile/freeflow/freeflowNodes" data-dojo-props="validate:\'freeflowNodes\'"></div>';
			
			var detailArea = query("#freeFlowNodeDIV");
			detailArea.html(html, {parseContent: true});
		},0);
	};
	
	return freeflow;
});