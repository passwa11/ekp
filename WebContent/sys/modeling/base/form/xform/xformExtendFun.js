/*针对建模进行的表单函数扩展*/
(function(){
	//获取建模当前表单对应的所有流程
	function XForm_GetFlowModels(){
		var fdAppModelId  = window.fdAppModelId;
		var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppFlowMain.do?method=findFlows&fdAppModelId=" + fdAppModelId;
		var flowModels = [];
		$.ajax({
			url: url,
			method: 'GET',
			async: false
		}).success(function (ret) {
			if (ret && ret.data) {
				flowModels = ret.data;
			} else {
				alert(ret);
			}
		});
		return flowModels;
	}
	//获取流程对应的节点
	function XForm_GetWfAuditNodes_Extend(){
		var fdAppModelId  = window.fdAppModelId;
		if(!fdAppModelId){
			return null;
		}
		var wfNodes = [];
		var flowModels = XForm_GetFlowModels();
		if(flowModels){
			var fdModelIds = "";
			var fdNames = "";
			for (var index in flowModels) {
				var fdModelId = flowModels[index].id;
				var fdName = flowModels[index].text;
				if(!fdModelId){
					continue;
				}
				fdModelIds += fdModelId + ";";
				fdNames += fdName + ";";
			}
			if(!fdModelIds){
				return;
			}
			fdModelIds = fdModelIds.substring(0, fdModelIds.length-1);
			fdNames = fdNames.substring(0, fdNames.length-1);
			var fdModelName = "com.landray.kmss.sys.modeling.base.model.ModelingAppFlow";
			var fdKey = "modelingApp";
			var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=findNodesByModels&fdModelIds=" + fdModelIds + "&fdNames="+fdNames+"&fdModelName="+fdModelName+"&fdKey="+fdKey;
			$.ajax({
				url: encodeURI(url),//#171337 需要对url特殊字符进行编码，以防请求失败
				method: 'GET',
				async: false
			}).success(function (ret) {
				if(ret && typeof ret == 'string'){
					try{
						ret = eval('('+ret+')');
					}catch(e){
						ret = {};
					}
				}
				if (ret && ret.data) {
					wfNodes = getNodes(ret.data);
				}
			}).error(function(ret){
				alert(ret);
			});
		}
		return wfNodes;
	}
	
	function getNodes(processJsons){
		if(!processJsons){
			return [];
		}
		var allNodes = [];
		for(var index in processJsons){
			var processJson = processJsons[index];
			var processData = loadXmlData(processJson.xml);
			if(processData.nodes){
				var obj = {};
				obj.id = processJson.id;//业务模块目标id
				obj.name = processJson.name;
				obj.nodes = [];
				for(var i=0; i<processData.nodes.length; i++) {
					var node = processData.nodes[i];
					if(node.XMLNODENAME == "embeddedSubFlowNode"){
						//嵌入子流程
						var fdContent = getContentByRefId(node.embeddedRefId);
						if(fdContent){
							//嵌入的流程图对象
							var embeddedFlow = loadXmlData(fdContent);
							for(var j = 0;j<embeddedFlow.nodes.length;j++){
								var eNode = embeddedFlow.nodes[j];
								obj.nodes.push({value:node.id+"-"+eNode.id, name:node.id+" "+node.name+"("+eNode.id+"."+eNode.name+")",type:eNode.XMLNODENAME});
							}
						}
					}else if(node.XMLNODENAME == "dynamicSubFlowNode"){
						//动态子流程
						var _groups = getGroupsByFdId(node.dynamicGroupId);
						if(_groups){
							var __groups = JSON.parse(_groups);
							for(var h=0;h<__groups.length;h++){
								var param = __groups[h];
								var fdContent = param.fdContent;
								if(fdContent){
									//动态子流程的流程图对象
									var embeddedFlow = loadXmlData(fdContent);
									for(var j = 0;j<embeddedFlow.nodes.length;j++){
										var eNode = embeddedFlow.nodes[j];
										obj.nodes.push({value:node.id+"-"+param.fdId+"-"+eNode.id, name:node.id+" "+node.name+"["+param.fdAlias+"]"+"("+eNode.id+"."+eNode.name+")",type:eNode.XMLNODENAME});
									}
								}
							}
						}
					}else if(node.XMLNODENAME == "adHocSubFlowNode"){
						//即席子流程节点
						var fdContent = node.adHocSubFlowData;
						if(fdContent){
							//即席的流程图对象
							var adHocSubFlow = loadXmlData(fdContent);
							for(var j = 0;j<adHocSubFlow.nodes.length;j++){
								var sNode = adHocSubFlow.nodes[j];
								obj.nodes.push({value:node.id+"-"+sNode.id, name:node.id+" "+node.name+"("+sNode.id+"."+sNode.name+")",type:sNode.XMLNODENAME});
							}
						}
					}else if(node.XMLNODENAME == "recoverSubProcessNode"){
						//回收子流程节点
						obj.nodes.push({value:node.id,name:node.id+" "+node.name,type:node.XMLNODENAME,recoverSubProcessNote:node.recoverSubProcessNote});
					}else if (!node.groupNodeId) {
						obj.nodes.push({value:node.id,name:node.id+" "+node.name,type:node.XMLNODENAME});
					}
				}
				allNodes.push(obj);
			}
		}
		return allNodes;
	}
	
	function loadXmlData(xml, isArray,nodeNames){
		var xmlObj = xml;
		if(typeof(xml) == "string"){
			if(xml == null || xml == ""){
				return;
			}
			if(window.ActiveXObject){
				xmlObj = new ActiveXObject("MSXML2.DOMDocument.3.0");
				xmlObj.loadXML(xml);
				xmlObj = xmlObj.firstChild;
			}else{
				var dp = new DOMParser();
			    var newDOM = dp.parseFromString(xml, "text/xml");
			    xmlObj = newDOM.documentElement;
			}
		}
		var rtnVal = new Array();

		//读取属性
		rtnVal.XMLNODENAME = xmlObj.nodeName;
		var attNodes = xmlObj.attributes;
		for(var i=0; i<attNodes.length; i++)
			rtnVal[attNodes[i].nodeName] = attNodes[i].value;
		if(rtnVal.CHILDRENISARRAY!=null){
			isArray = rtnVal.CHILDRENISARRAY=="true";
		}

		//读取节点内容
		if(nodeNames!=null){
			if(nodeNames.indexOf(xmlObj.nodeName)!=-1){
				return xmlObj.text;  
			}
		}

		//读取子对象
		for(var node=xmlObj.firstChild; node!=null; node=node.nextSibling){
			if(node.nodeType==1){
				if(isArray)
					rtnVal[rtnVal.length] = loadXmlData(node, false,nodeNames);
				else
					rtnVal[node.nodeName] = loadXmlData(node, true,nodeNames);
			}
		}
		return rtnVal;
	}

	//嵌入子流程根据redId获得流程图xml
	function getContentByRefId(fdRefId){
		var fdContent = "";
		var ajaxurl = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=getContentByRefId&fdRefId='+fdRefId;
		var kmssData = new KMSSData();
		kmssData.SendToUrl(ajaxurl, function(http_request) {
			var responseText = http_request.responseText;
			var json = eval("("+responseText+")");
			if (json.fdContent){
				fdContent = json.fdContent;
			}
		},false);
		return fdContent;
	}

	//动态子流程根据fdId获得nodeGroups
	var getGroupsByFdId = function(fdId){
		var kmssData = new KMSSData();
		kmssData.UseCache = false;
		var fields = kmssData.AddBeanData("lbpmDynamicSubFlowTreeServiceImp&type=groups&fdId="+fdId).GetHashMapArray();
		if(fields && fields.length>0 && fields[0].groups){
			return fields[0].groups;
		}
		return null;
	}
	
	window.XForm_GetFlowModels = XForm_GetFlowModels;
	window.XForm_GetWfAuditNodes_Extend = XForm_GetWfAuditNodes_Extend;
})()