define(function(){
	var nodeFilter={};
	if(!window.GetXFormFieldById){
		window.GetXFormFieldById= function(id, nocache){
			var forms = document.forms;
			var obj = [];
			for (var i = 0, l = forms.length; i < l; i ++) {
				var elems = forms[i].elements;
				for (var j = 0, m = elems.length; j < m; j ++) {
					if (elems[j].name != null && elems[j].name.indexOf(id) > -1) {
						obj.push(elems[j]);
					}
				}
			}
			return obj;
		};
	}
	Array.prototype.distinct=function(){
		var a=[],b=[];
		for(var prop in this){
		   var d = this[prop];
		   if (d===a[prop]) continue; //防止循环到prototype
		   if (b[d]!=1){
		    a.push(d);
		    b[d]=1;
		   }
		}
		return a;
	};
	// 判断指定数组是否与当前数组相等
	Array.prototype.equals = function (array) {
	    if (!array)
	        return false;
	    if (!(array instanceof Array)) {
	    	return false;
	    }

	    if (this.length != array.length)
	        return false;

	    for (var i = 0, l = this.length; i < l; i++) {
	        if (this[i] instanceof Array && array[i] instanceof Array) {
	            if (!this[i].equals(array[i]))
	                return false;
	        }
	        else if (this[i] !== array[i]) {
	            return false;
	        }
	    }
	    return true;
	}
	/*
	 * 根据 节点数组 获取节点id 串
	 */
	nodeFilter.getIdsByNodes = lbpm.globals.getIdsByNodes=function(nodes){
		var throughNodeIds = [];
		 for(var i=0;i<nodes.length;i++){
			 throughNodeIds.push(nodes[i].id);
		}
		return throughNodeIds.join(',');
	};

	nodeFilter.objContain= lbpm.globals.objContain=function(o, arr){
		if (arr.length == 0) {
			return false;
		}
		for (var i = 0; i < arr.length; i ++) {
			if (arr[i] == o) {
				return true;
			}
		}
		return false;
	};

	/*
	 * 获取所有通过的节点过滤掉
	 * 自动判断缓存 如果当前数据域缓存数据一致直接从缓存返回
	 * callbackFun:回调函数 用来执行业务处理
	 * beforeRequest：执行业务处理前得执行函数，通常用来提示用户 正在加载。。。
	 * completeRequest：业务处理执行完成后的执行函数
	 * startNodeId: 返回从指定节点开始查找的结果（注：只是返回的结果是指定节点开始查找的，但缓存的结果仍是从开始节点查找的）
	 */
	nodeFilter.getThroughNodes = lbpm.globals.getThroughNodes=function(callbackFun,beforeRequest,completeRequest,async,startNodeId,_async){

		//等待解锁后继续执行 ，防止缓存数据脏读  #作者：曹映辉 #日期：2013年4月26日  
		if(WorkFlow_IsLock){
			setTimeout(function(){lbpm.globals.getThroughNodes(callbackFun,beforeRequest,completeRequest,async,startNodeId);},100);
			return;
		}
		WorkFlow_IsLock=true;
		async = (async != null) ? async : true;
		//获取自动决策节点关联的域信息
		var bundleFilesInfo = lbpm.globals.getBundleFilesInfo();

		var filedsJSON =bundleFilesInfo.filedsJSON;

		/**
		 * 参数值是否发生改变的标志
		 * 0:自动决策域 和 人工决策选择 都没有改变
		 * 1:自动决策域发生了改变（人工决策域不管）
		 * 2：人工决策域发生了改变，自动决策域没有改变
		 */
		var changeFlag=0;

		for(newFiledId in filedsJSON){
			if(changeFlag == 1){
				break;
			}
			for(cacheFiledId in WorkFlow_filedsCache){
				//前值 同缓存不一致 则认为该域的值发生了变化
				if(newFiledId==cacheFiledId){
					if (filedsJSON[newFiledId] instanceof Array) {
						if (!filedsJSON[newFiledId].equals(WorkFlow_filedsCache[cacheFiledId])) {
							changeFlag=1;
							break;
						}
					} else {
						if (filedsJSON[newFiledId]!=WorkFlow_filedsCache[cacheFiledId]) {
							changeFlag=1;
							break;
						}
					}
				}
			}
		}
		if(changeFlag == 0){
			// 检测空时的变化
			var cacheIsEmpty=true;
			var newFiledIsEmpty=true;
			for(newFiledId in filedsJSON){
				newFiledIsEmpty = false;
				break;
			}
			for(cacheFiledId in WorkFlow_filedsCache){
				cacheIsEmpty = false;
				break;
			}
			if (cacheIsEmpty != newFiledIsEmpty) {
				changeFlag = 1;
			}
		}
		// 自动决策域没变的前提下 人工决策域发生了改变则设为2
		if(changeFlag==0){
			if(WorkFlow_MuaualTarget.toString() != (lbpm.globals.getFutureNodeId()).toString()){
				changeFlag = 2;
			}
		}

		//alert("变了");
		lbpm.globals.getFilterInfo(changeFlag,bundleFilesInfo,callbackFun,beforeRequest,completeRequest,async,startNodeId,_async);
	};
	//检查值自动决策缓存值是否发送变化 TODO:暂未使用
	nodeFilter.CheckBindFieldValueChange = lbpm.globals.CheckBindFieldValueChange=function(){
		//获取自动决策节点关联的域信息
		var bundleFilesInfo = lbpm.globals.getBundleFilesInfo();

		var filedsJSON =bundleFilesInfo.filedsJSON;

		/**
		 * 参数值是否发生改变的标志
		 * 0:自动决策域 和 人工决策选择 都没有改变
		 * 1:自动决策域发生了改变（人工决策域不管）
		 * 2：人工决策域发生了改变，自动决策域没有改变
		 */
		var changeFlag=0;

		for(newFiledId in filedsJSON){
			for(cacheFiledId in WorkFlow_filedsCache){
				//前值 同缓存不一致 则认为该域的值发生了变化
				if(newFiledId==cacheFiledId){
					if (filedsJSON[newFiledId] instanceof Array) {
						if (!filedsJSON[newFiledId].equals(WorkFlow_filedsCache[cacheFiledId])) {
							return true;
						}
					} else {
						if (filedsJSON[newFiledId]!=WorkFlow_filedsCache[cacheFiledId]) {
							return true;
						}
					}
				}
			}
		}
		// 检测空时的变化
		var cacheIsEmpty=true;
		var newFiledIsEmpty=true;
		for(newFiledId in filedsJSON){
			newFiledIsEmpty = false;
			break;
		}
		for(cacheFiledId in WorkFlow_filedsCache){
			cacheIsEmpty = false;
			break;
		}
		if (cacheIsEmpty != newFiledIsEmpty) {
			return true;
		}
		// 自动决策域没变的前提下 人工决策域发生了改变则设为2
		if(changeFlag==0){
			if(WorkFlow_MuaualTarget.toString() != (lbpm.globals.getFutureNodeId()).toString()){
				return true;
			}
		}
		return false;
	};
	var WorkFlow_IsLock=false;
	//缓存域属性和值
	var WorkFlow_filedsCache;
	//缓存通过的节点
	var WorkFlow_throughNodesCache;
	//缓存当前节点 对应人工决策节点的节点id
	var WorkFlow_MuaualTarget=new Array();
	//缓存服务器端计算的节点映射值
	var WorkFlow_ServiceMapJSON=[];

	nodeFilter.isAutoDecisionNode = lbpm.globals.isAutoDecisionNode = function(node) {
		var nodeDesc = lbpm.nodedescs[node.nodeDescType];
		return nodeDesc.isBranch(node) && nodeDesc.isAutomaticRun(node);
	};
	nodeFilter.isDecisionNode = lbpm.globals.isDecisionNode = function(node) {
		var nodeDesc = lbpm.nodedescs[node.nodeDescType];
		return nodeDesc.isBranch(node);
	};
	nodeFilter.isConcurrentNode = lbpm.globals.isConcurrentNode = function(node) {
		var nodeDesc = lbpm.nodedescs[node.nodeDescType];
		return nodeDesc.isConcurrent(node);
	};
	nodeFilter.isHandleDecisionNode = lbpm.globals.isHandleDecisionNode = function(node) {
		var nodeDesc = lbpm.nodedescs[node.nodeDescType];
		return nodeDesc.isBranch(node) && !nodeDesc.isAutomaticRun(node) && nodeDesc.isHandler(node);
	};

	nodeFilter.getThroughNodesCache = lbpm.globals.getThroughNodesCache = function() {
		return WorkFlow_throughNodesCache;
	};

	function _getPassedNodeIds(targetNode, passedNodeIds) {
		if(targetNode && targetNode.targetId) {
			if(targetNode.targetId.indexOf(";") > -1) {
				// 并发
				$.each(targetNode.targetId.split(';'), function(i, nodeId) {
					var node = lbpm.globals.getNodeObj(nodeId);
					_getPassedNodeIds(node, passedNodeIds);
				});
			} else {
				if(lbpm.globals.objContain(targetNode.targetId, passedNodeIds)){
					return;
				}
				passedNodeIds.push(targetNode.targetId);
				var node = lbpm.globals.getNodeObj(targetNode.targetId);
				_getPassedNodeIds(node, passedNodeIds);
			}
		}
	};

	function __getPassedHistoryNodes(runningNodes, toRefuseThisNodeId) {
		  var passedNodeIds = []
		  if (!toRefuseThisNodeId) {
		    // 非驳回返回本节点
		    $.each(runningNodes, function(index, nodeData) {
		      _getPassedNodeIds(nodeData, passedNodeIds)
		    })
		  }
		  var HistoryNodesArr = new Array()
		  $.each(lbpm.nodes, function(index, nodeData) {
		    if (
		      nodeData.Status == lbpm.constant.STATUS_PASSED &&
		      !lbpm.globals.objContain(nodeData.id, passedNodeIds)
		    ) {
		      HistoryNodesArr.push(nodeData)
		    }
		  })

		  return HistoryNodesArr
		}

		/**
		 * 根据当前节点计算已经流经的历史节点（排除曾经经过）<br>
		 * 如果有回调方法，则走异步
		 */
	function _getPassedHistoryNodes(runningNodes, callback) {
		  //当前节点的下一个节点状态是STATUS_PASSED[曾经流过]（驳回时会出现这个情况）时忽略历史节点。重新计算 #作者：曹映辉 #日期：2013年4月23日

		  // 异步
		  if (callback) {
		    lbpm.globals.getOperationParameterJson(
		      "toRefuseThisNodeId",
		      null,
		      null,
		      function(toRefuseThisNodeId) {
		        var HistoryNodesArr = __getPassedHistoryNodes(
		          runningNodes,
		          toRefuseThisNodeId
		        )
		        callback(HistoryNodesArr)
		      }
		    )
		    return
		  }

		  // 同步
		  var toRefuseThisNodeId = lbpm.globals.getOperationParameterJson(
		    "toRefuseThisNodeId"
		  )
		  return __getPassedHistoryNodes(runningNodes, toRefuseThisNodeId)
		}


	function _getPreviousTargetNodesMapJSON(node, availableHistoryNodes, targetNodesMapJSON) {
		var preNodes = lbpm.globals.getPreviousNodeObjs(node.id);
		if(preNodes.length == 1) {
			var preNode = preNodes[0];
			if(lbpm.globals.isDecisionNode(preNode) && !lbpm.globals.isConcurrentNode(preNode)) {
				availableHistoryNodes.push(preNode);
				targetNodesMapJSON.push({nodeId:preNode.id, targetNodeId:node.id});
			}
			_getPreviousTargetNodesMapJSON(preNode, availableHistoryNodes, targetNodesMapJSON);
		}
	}

	function _getPassedTargetNodesMapJSON(availableHistoryNodes, runningNodes) {
		var targetNodesMapJSON = [];
		$.each(availableHistoryNodes, function() {
			if(lbpm.globals.isDecisionNode(this)){
				var self = this;
				targetNodesMapJSON.push({nodeId:self.id,
	                targetNodeId:self.targetId});
			}
		});

		// 根据当前节点计算已经流经的分支（排除并发分支），避免由于一分支已经流经，但特权人跳转到另一分支（这分支不曾流经）情况下，过滤计算错误问题
		$.each(runningNodes, function() {
			_getPreviousTargetNodesMapJSON(this, availableHistoryNodes, targetNodesMapJSON);
		});
		return targetNodesMapJSON;
	};

	function _getFilterInfo(
			  changeFlag,
			  bundleFilesInfo,
			  callbackFun,
			  beforeRequest,
			  completeRequest,
			  async,
			  startNodeId,
			  availableHistoryNodes,
			  runningNodes
			) {
			  //获取历史节点流转顺序
			  //用来记录决策节点的目标节点(json数据格式)
			  var targetNodesMapJSON = _getPassedTargetNodesMapJSON(
			    availableHistoryNodes,
			    runningNodes
			  )

			  //只有人工决策选择的分支变化的时候 直接拼接当前历史（包括人工决策的选项）和后台缓存的分支流向
			  if (changeFlag == 2) {
			    if (beforeRequest) {
			      beforeRequest()
			    }
			    //拼接后台计算的决策分支的映射关系
			    targetNodesMapJSON = targetNodesMapJSON.concat(WorkFlow_ServiceMapJSON)
			    var throughtNodes = lbpm.globals.getAllThroughNodes(
			      "N1",
			      targetNodesMapJSON
			    )
			    //缓存通过的节点
			    WorkFlow_throughNodesCache = throughtNodes
			    //回调用户传入的业务函数
			    if (callbackFun) {
			      if (startNodeId != "N1") {
			        callbackFun(
			          lbpm.globals.getAllThroughNodes(startNodeId, targetNodesMapJSON)
			        )
			      } else {
			        callbackFun(throughtNodes)
			      }
			    }
			    //业务处理完成后处理的函数
			    if (completeRequest) {
			      completeRequest()
			    }
			    WorkFlow_IsLock = false
			    //没有需要向后台计算的节点，直接返回
			    return
			  }
			  //获取自动决策节点关联的域信息
			  if (!bundleFilesInfo) {
			    bundleFilesInfo = lbpm.globals.getBundleFilesInfo()
			  }
			  //所有自动决策节点
			  var autoNodes = bundleFilesInfo.autoNodes

			  var filedsJSON = bundleFilesInfo.filedsJSON

			  //缓存属性和值
			  WorkFlow_filedsCache = filedsJSON

			  //请求参数数组
			  var paramArray = new Array()

			  //url格式的参数
			  for (filedId in filedsJSON) {
			    if (filedsJSON[filedId] != "null") {
			      paramArray.push(
			        encodeURIComponent(filedId) +
			          "=" +
			          encodeURIComponent(filedsJSON[filedId])
			      )
			    }
			  }

			  //根据历史节点 排除已经流转过的自动决策节点。该部分节点流向已明确不需要在重新计算
			  for (var i = 0; i < autoNodes.length; i++) {
			    for (var j = 0; j < availableHistoryNodes.length; j++) {
			      if (autoNodes[i].id == availableHistoryNodes[j].id) {
			        autoNodes.splice(i, 1)
			        i--
			        break
			      }
			    }
			  }
			  //如果没有决策节点需要向后台发起请求 则直接返回
			  if (!autoNodes || autoNodes.length == 0) {
			    if (beforeRequest) {
			      beforeRequest()
			    }

			    var throughtNodes = lbpm.globals.getAllThroughNodes(
			      "N1",
			      targetNodesMapJSON
			    )
			    //缓存通过的节点
			    WorkFlow_throughNodesCache = throughtNodes

			    //回调用户传入的业务函数
			    if (callbackFun) {
			      if (startNodeId != "N1") {
			        callbackFun(
			          lbpm.globals.getAllThroughNodes(startNodeId, targetNodesMapJSON)
			        )
			      } else {
			        callbackFun(throughtNodes)
			      }
			    }
			    //业务处理完成后处理的函数
			    if (completeRequest) {
			      completeRequest()
			    }
			    WorkFlow_IsLock = false
			    //没有需要向后台计算的节点，直接返回
			    return
			  }

			  //设置自动决策过滤节点id 如”N5“
			  for (var i = 0; i < autoNodes.length; i++) {
				  //当并行分支是自定义启动时则不自动计算后续节点
				  if(autoNodes[i].nodeDescType=="splitNodeDesc"&&autoNodes[i].splitType&&autoNodes[i].splitType=="custom"){
					  continue;
				  }
			  	  paramArray.push("factNodeId=" + encodeURIComponent(autoNodes[i].id))
			  }

			  paramArray.push(
			    "processId=" + encodeURIComponent(lbpm.globals.getWfBusinessFormModelId())
			  )
			  paramArray.push(
			    "modelName=" + encodeURIComponent(lbpm.globals.getWfBusinessFormModelName())
			  )

			  var _extendFilePath = document.getElementsByName(
			    "extendDataFormInfo.extendFilePath"
			  )[0]
			  if (_extendFilePath) {
			    var extendFilePath = _extendFilePath.value
			    paramArray.push("extendFilePath=" + encodeURIComponent(extendFilePath))
			  }

			  //流程集成组件参数传递
			  if (typeof _thirdSysFormList != "undefined") {
			    paramArray.push(
			      "_thirdSysFormList=" +
			        encodeURIComponent(JSON.stringify(_thirdSysFormList))
			    )
			  }

			  // 节点过滤的标识
			  paramArray.push("nodeFilter="+"true");

			  var params = paramArray.join("&")

			var successFun = function (data) {
				var jsonAry = [];
				if(data){
					if(data instanceof KMSSData){
						jsonAry = JSON.parse(data.data[0].data);
					}else if(data instanceof Array){
						jsonAry = JSON.parse(data[0].data);
					}
				}
				if(!jsonAry){
					jsonAry=[];
				}
				//缓存服务端计算的节点映射值
				WorkFlow_ServiceMapJSON=jsonAry;

				//合并历史节点中数据和后台计算的结果
				targetNodesMapJSON=targetNodesMapJSON.concat(jsonAry);

				var throughtNodes = lbpm.globals.getAllThroughNodes("N1",targetNodesMapJSON);

				//缓存通过的节点
				WorkFlow_throughNodesCache=throughtNodes;

				//回调用户传入的业务函数
				if(callbackFun){
					if (startNodeId != "N1") {
						callbackFun(lbpm.globals.getAllThroughNodes(startNodeId,targetNodesMapJSON));
					} else {
						callbackFun(throughtNodes);
					}
				}
				//业务处理完成后处理的函数
				if(completeRequest){
					completeRequest();
				}
				WorkFlow_IsLock=false;
			}

			var data = new KMSSData();
			data.AddBeanData("lbpmRouteDecisionRuleDataBean&"+params);
			//请求开始前调用的函数
			if(beforeRequest){
				beforeRequest();
			}
			if(async){
				data.GetHashMapArray(successFun);
			}else{
				successFun(data.GetHashMapArray());
			}
			  //Ajax请求后台计算决策节点的分支
			  /*$.ajax({
			    type: "post",
			    async: async, //指定是否异步处理
			    data: params,
			    dataType: "json",
			    url:
			      Com_Parameter.ContextPath +
			      "sys/lbpm/engine/jsonp.jsp?s_bean=lbpmRouteDecisionRuleDataBean",
			    beforeSend: function(XMLHttpRequest) {
			      //请求开始前调用的函数
			      if (beforeRequest) {
			        beforeRequest()
			      }
			    },
			    success: function(data, textStatus) {
			      var jsonAry = data
			      if (!jsonAry) {
			        jsonAry = []
			      }
			      //缓存服务端计算的节点映射值
			      WorkFlow_ServiceMapJSON = jsonAry

			      //合并历史节点中数据和后台计算的结果
			      targetNodesMapJSON = targetNodesMapJSON.concat(jsonAry)

			      var throughtNodes = lbpm.globals.getAllThroughNodes(
			        "N1",
			        targetNodesMapJSON
			      )

			      //缓存通过的节点
			      WorkFlow_throughNodesCache = throughtNodes

			      //回调用户传入的业务函数
			      if (callbackFun) {
			        if (startNodeId != "N1") {
			          callbackFun(
			            lbpm.globals.getAllThroughNodes(startNodeId, targetNodesMapJSON)
			          )
			        } else {
			          callbackFun(throughtNodes)
			        }
			      }
			    },
			    complete: function(XMLHttpRequest, textStatus) {
			      //业务处理完成后处理的函数
			      if (completeRequest) {
			        completeRequest()
			      }
			      WorkFlow_IsLock = false
			    }
			  })*/
			}

			/**
			 * 节点过滤，节点计算函数
			 */
			nodeFilter.getFilterInfo = lbpm.globals.getFilterInfo = function(
			  changeFlag,
			  bundleFilesInfo,
			  callbackFun,
			  beforeRequest,
			  completeRequest,
			  async,
			  startNodeId,
			  _async
			) {
			  async = async != null ? async : true
			  startNodeId = startNodeId != null ? startNodeId : "N1"
			  //数据没有改变，并且缓存已经初始化
			  if (changeFlag == 0 && WorkFlow_throughNodesCache && startNodeId == "N1") {
			    if (beforeRequest) beforeRequest()

			    //回调用户传入的业务函数 ，直接取缓存的节点数据给用户
			    if (callbackFun) callbackFun(WorkFlow_throughNodesCache)

			    if (completeRequest) completeRequest()
			    WorkFlow_IsLock = false
			    //alert("没变");
			    return
			  }

			  //当前运行节点
			  var runningNodes = lbpm.globals.getAvailableRunningNodes()

			  if (_async) {
			    _getPassedHistoryNodes(runningNodes, function(availableHistoryNodes) {
			      _getFilterInfo(
			        changeFlag,
			        bundleFilesInfo,
			        callbackFun,
			        beforeRequest,
			        completeRequest,
			        async,
			        startNodeId,
			        availableHistoryNodes,
			        runningNodes
			      )
			    })
			    return
			  }

			  var availableHistoryNodes = _getPassedHistoryNodes(runningNodes)
			  _getFilterInfo(
			    changeFlag,
			    bundleFilesInfo,
			    callbackFun,
			    beforeRequest,
			    completeRequest,
			    async,
			    startNodeId,
			    availableHistoryNodes,
			    runningNodes
			  )
			}

	/**
	 * 根据公式条件获取条件中关联的域
	 * 同后台获取域的逻辑一直
	 */
	nodeFilter.getFiledsIdByLines = lbpm.globals.getFiledsIdByLines=function(allLines){
		var filedsId=[];
		var SCRIPT_VARFLAG_LEFT="$";
		var SCRIPT_VARFLAG_RIGHT="$";
		//截取条件中的绑定的域id
		for(var i=0;i<allLines.length;i++){
			var rightScript= allLines[i].condition;

			if(!rightScript){
				continue;
			}
			rightScript=rightScript.replace(/^\\s+|\\s+$/g,"");
			var index;
			// 下面代码将解释script代码 截取条件中的绑定的域id
			for (index = rightScript.indexOf(SCRIPT_VARFLAG_LEFT); index > -1; index = rightScript
					.indexOf(SCRIPT_VARFLAG_LEFT)) {
				var nxtIndex = rightScript.indexOf(SCRIPT_VARFLAG_RIGHT,
						index + 1);
				// index为开始点，nxtIndex为结束点，无结束点则退出循环
				if (nxtIndex == -1)
					break;
				var varName = rightScript.substring(index + 1, nxtIndex);

				rightScript = rightScript.substring(nxtIndex + 1);

				//函数
				if (rightScript.length > 0 && rightScript.charAt(0) == '(') {
					continue;
				}
				//变量
				filedsId.push(varName);
			}

		}
		//去掉重复的域
		return filedsId.distinct();
	};
	/*
	 * 获取自动决策节点关联的域信息结果集保存为json
	 * 如：{autoNodes:[node1,node2],
		    filedsJSON:{fd_134343:aaaa,fd_dfdafafd:bbbb}};
	 */
	nodeFilter.getBundleFilesInfo = lbpm.globals.getBundleFilesInfo=function(){
		//所有自动决策节点
		var autoNodes=new Array();
		//所有自动决策分支关联的节点line
		var autoLines=new Array();
		$.each(lbpm.nodes, function(index, nodeData) {
			if(lbpm.globals.isAutoDecisionNode(nodeData)) {
				autoNodes.push(nodeData);
				$.each(nodeData.endLines, function() {
					autoLines.push(this);
				});
			}
		});
		//用来存储 自动决策节点绑定域的属性和值如：{fd_134343:aaaa,fd_dfdafafd:bbbb}
		var filedsJSON = {};
		//流程图中所有自动决策节点关联的域id
		var autoFiledIds = lbpm.globals.getFiledsIdByLines(autoLines);
		for(var i=0;i<autoFiledIds.length;i++){
			var filedsInfo = GetXFormFieldById(autoFiledIds[i], true);
			// 针对docCreator（申请人），强制要求其关联的域name必须完全匹配docCreator，避免获取到的是value值非组织架构id但名称又包含docCreator的域传到后台计算时可能导致的问题
			if (filedsInfo && autoFiledIds[i] == "docCreator") {
				var filedsInfoTemp = [];
				for(var j=0;j<filedsInfo.length;j++) {
					if (filedsInfo[j].name==autoFiledIds[i]) {
						filedsInfoTemp.push(filedsInfo[j]);
					}
				}
				filedsInfo = filedsInfoTemp;
			}
			//文本框根据同一个名字获取多个对象后，取同名称最匹配的对象。#作者：曹映辉 #日期：2013年4月25日
			if(filedsInfo&&filedsInfo.length>1)
			{
				var isMatch = false;
				for(var j=0;j<filedsInfo.length;j++)
				{
					if(filedsInfo[j].type=='text' && filedsInfo[j].name==autoFiledIds[i])
					{
						var temp=filedsInfo[j];
						filedsInfo=[];
						filedsInfo.push(temp);
						isMatch = true;
						break;
					}
				}
				//这里是对文档fdId的特殊处理，避免文档id出现多个导致分支解析失败，限制为fdId，避免影响原有功能
				if(!isMatch){
					for(var j=0;j<filedsInfo.length;j++)
					{
						if(autoFiledIds[i] == 'fdId' && filedsInfo[j].type=='hidden' && filedsInfo[j].name==autoFiledIds[i])
						{
							var temp=filedsInfo[j];
							filedsInfo=[];
							filedsInfo.push(temp);
							break;
						}
					}
				}
			}
			var valueInfo=[];
			for(var j=0;j<filedsInfo.length;j++){
				if (filedsInfo[j].type == 'radio' || filedsInfo[j].type == 'checkbox' || filedsInfo[j].type == 'select' || filedsInfo[j].type == 'select-one') {
					if(filedsInfo[j].name == "_"+autoFiledIds[i] || filedsInfo[j].name == ("_extendDataFormInfo.value("+autoFiledIds[i]+")")) {
						// 排除标签的特殊处理
						continue;
					}
				}
				// 动态表单控件取实际值时要过滤掉无关值
				if (filedsInfo[j].name && filedsInfo[j].name.indexOf("extendDataFormInfo.value("+autoFiledIds[i]+"_") == 0) {
					continue;
				}
				if (filedsInfo[j].type == 'radio' || filedsInfo[j].type == 'checkbox') {
					if (filedsInfo[j].checked) valueInfo.push(filedsInfo[j].value);
					continue;
				}
				if(filedsInfo[j].type == 'input'){
					valueInfo.push(filedsInfo[j].originValue || filedsInfo[j].value);
					continue;
				}
				valueInfo.push(filedsInfo[j].value);
			}

			if(!valueInfo || valueInfo.length==0){
				valueInfo = "null";
			}
			filedsJSON[autoFiledIds[i]] = valueInfo;
		}
		return {autoNodes:autoNodes,
		        filedsJSON:filedsJSON};
	};
	//根据当前id计算出所有通过的节点 如“N1,N2,N4,N5,N3,N4,N3” 可以通过该节点列表过滤节点连线
	nodeFilter.getAllThroughNodes = lbpm.globals.getAllThroughNodes=function(id,autoBranchTargets,parentNode, _throughNodesTemp){
		var allNodeObjs = new Array();
		var currentNode = lbpm.globals.getNodeObj(id);
		allNodeObjs.push(currentNode);
		if(!_throughNodesTemp) { //避免死循环
			_throughNodesTemp=new Array();
		}
		_throughNodesTemp.push(currentNode);
		if(currentNode.XMLNODENAME=="embeddedSubFlowNode"){
			id = currentNode.startNodeId;
			currentNode = lbpm.globals.getNodeObj(currentNode.startNodeId);
			allNodeObjs.push(currentNode);
			_throughNodesTemp.push(currentNode);
		}else if(currentNode.XMLNODENAME=="groupEndNode"){
			var _nextNode = lbpm.globals.getNextNodeObj(currentNode.groupNodeId);
			id = _nextNode.id;
			currentNode = lbpm.globals.getNodeObj(id);
			allNodeObjs.push(currentNode);
			_throughNodesTemp.push(currentNode);
		}
		var nextNodes = lbpm.globals.getNextNodeObjs(id);

		var breachId=[];
		var breachContain = function(id) {
			if (breachId.length == 0) {
				return true;
			}
			for (var i = 0; i < breachId.length; i ++) {
				if (breachId[i] == id) {
					return true;
				}
			}
			return false;
		};
		//判断是否为分支节点
		if(lbpm.globals.isDecisionNode(currentNode)){
			var his = true;
			// 判断是否为人工决策节点
			if (lbpm.globals.isNodeRunning() && lbpm.globals.isHandleDecisionNode(currentNode)) {
				var nodeDesc = lbpm.nodedescs[currentNode.nodeDescType];
				var endLines = nodeDesc.getLines(parentNode, currentNode);
				if(!lbpm.globals.isSameEndLines(endLines, currentNode.endLines)) {
					// 如果当前重新选择人工决策分支，不从历史节点计算
					for (var i = 0; i < endLines.length; i++) {
						breachId.push(endLines[i].endNode.id);
					}
					his = false;
				}
			}
			if(his) {
				//获取并行分支自定义启动的流向
				if(currentNode.nodeDescType=="splitNodeDesc" && currentNode.splitType
					&& currentNode.splitType=="custom" && lbpm.globals.isNodeRunning()){
					//当下一个节点就是并行分支时，获取页面选中的节点
					if(parentNode.Status == lbpm.constant.STATUS_RUNNING){
						if(lbpm.globals.getSelectedFurtureNode) {
							var jsonObj = lbpm.globals.getSelectedFurtureNode();
							for(var i=0;i<jsonObj.length;i++){
								breachId.push(jsonObj[i].NextRoute);
							}
						}
					}
					else{
						if(currentNode.targetId){
							//已经流过时获取历史节点
							var nextNodeIds=currentNode.targetId.split(";");
							for(var i=0;i<nextNodeIds.length;i++){
								breachId.push(nextNodeIds[i]);
							}
						}
					}
				} else {
					for(var i=autoBranchTargets.length-1;i>=0;i--){
						// 节点可能多次经过，只取最近的一次
						if(currentNode.id==autoBranchTargets[i].nodeId){
							if(autoBranchTargets[i].targetNodeId.indexOf(";") > -1) {
								// 并发
								$.each(autoBranchTargets[i].targetNodeId.split(';'), function() {
									breachId.push(this);
								});
							} else {
								breachId.push(autoBranchTargets[i].targetNodeId);
							}
							break;
						}
					}
				}
			}
		}

		for(var i=0;i<nextNodes.length;i++){
			if(!breachContain(nextNodes[i].id)){
				//对计算后不经过的分支跳过
				continue;
			}
			//需要记录当前分支正在分叉是的节点，每个分支都需要记录当前分支的起始节点
			if(i>=1){
				allNodeObjs.push(currentNode);
			}
			//标记该节点是否已经被遍历过，重复的分支无需重新遍历 ，防止死循环
			var hasIn = false;
			for(var j=0;j<_throughNodesTemp.length-1;j++){
				if(currentNode.id==_throughNodesTemp[j].id && !lbpm.globals.isDecisionNode(currentNode)){ // 非分支才判定为重复
					hasIn = true;
					break;
				}
			}
			//重复的分支无需重新遍历
			if(hasIn){
				continue;
			}
			allNodeObjs=allNodeObjs.concat(lbpm.globals.getAllThroughNodes(nextNodes[i].id,autoBranchTargets, currentNode, _throughNodesTemp));
		}
		return allNodeObjs;
	};

	/**
	 * 获取目标节点流向的节点id值
	 */
	nodeFilter.getFutureNodeId = lbpm.globals.getFutureNodeId = function() {
		var arr = new Array();
		//如果有分支设置分支
		$("input[manualBranchNodeId]:checked").each(function(i){
			var self = $(this);
			arr.push(self.attr('manualBranchNodeId'));
			arr.push(self.val());
		});
		WorkFlow_MuaualTarget=arr;
		return arr;
	};

	nodeFilter.isSameEndLines = lbpm.globals.isSameEndLines = function(lines1, lines2) {
		if(lines1 && lines2) {
			if(lines1.length != lines2.length) {
				return false;
			}
			var find = false;
			for (var i = 0; i < lines1.length; i++) {
				find = false;
				for (var j = 0; j < lines2.length; j++) {
					if(lines1[i].id == lines2[j].id) {
						find = true;
						break;
					}
				}
				if(find) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	};

	nodeFilter.isNodeRunning = lbpm.globals.isNodeRunning = function() {
		var running = false;
		$.each(lbpm.nodes, function(index, nodeData) {
			if(nodeData.Status==lbpm.constant.STATUS_RUNNING) {
				running = true;
				return false; // 中断循环
			}
		});
		return running;
	};

	var WorkFlow_DocEle = function () {
	  return document.getElementById(arguments[0]) || false;
	};
	nodeFilter.clearIndicatorDiv = lbpm.globals.clearIndicatorDiv=function(obj,win){
		win = win|| window;
		win.document.body.removeChild(obj.newMaskDiv);
		win.document.body.removeChild(obj.infoDiv);
	};

	nodeFilter.openIndicatorDiv = lbpm.globals.openIndicatorDiv=function(win,_id) {
		  win = win|| this;
		  var m = "mask_div";
		  if (WorkFlow_DocEle(_id)) win.document.removeChild(WorkFlow_DocEle(_id));
		  if (WorkFlow_DocEle(m)) win.document.removeChild(WorkFlow_DocEle(m));
		  // 新激活图层
		  var newDiv = win.document.createElement("div");
		  newDiv.id = _id;
		  newDiv.style.position = "absolute";
		  newDiv.style.zIndex = "9999";
		  newDiv.style.top = "100px";
		  newDiv.style.left = (parseInt(win.document.body.clientWidth-300)) / 2 + "px"; // 屏幕居中
		  newDiv.style.background = "#FFFFFF";
		  newDiv.style.filter = "alpha(opacity=60)";
		  newDiv.style.opacity = "0.60";
		  newDiv.style.border = "0px solid #860001";
		  newDiv.style.padding = "5px";
		  newDiv.innerHTML = "<span style='font-size:12px;font-weight:bolder'><img  width=160px height=20px  src='"+Com_Parameter.ContextPath+"sys/lbpm/flowchart/images/indicator_long.gif'></img><br/>系统正在计算路径...</span>";
		  win.document.body.appendChild(newDiv);
		  // mask图层
		  var newMask = win.document.createElement("div");
		  newMask.id = m;
		  newMask.style.position = "absolute";
		  newMask.style.zIndex = "1";
		  newMask.style.width = win.document.body.clientWidth + "px";
		  newMask.style.height = win.document.body.clientHeight + "px";
		  newMask.style.top = "0px";
		  newMask.style.left = "0px";
		  newMask.style.background = "#FFFFFF";
		  newMask.style.filter = "alpha(opacity=60)";
		  newMask.style.opacity = "0.60";
		  win.document.body.appendChild(newMask);
	  return  {newMaskDiv:newMask,infoDiv:newDiv};
	};

	nodeFilter.throughNodesSorter = lbpm.globals.throughNodesSorter = function(node1, node2) {
		if (WorkFlow_throughNodesCache == null) {
			return lbpm.globals.normalSorter(node1, node2);
		}
		var nodes = WorkFlow_throughNodesCache||[];
		//节点过滤后缓存的顺序
		//优化 去重复节点算法 #作者：曹映辉 #日期：2013年5月15日 
		var tempNodes=[];
		for(var i=0;i<nodes.length;i++){
			var hasIn=false;
			for(var j=0;j<tempNodes.length;j++){
				if(nodes[i].id==tempNodes[j].id){
					hasIn=true;
				}
			}
			if(!hasIn){
				tempNodes.push(nodes[i]);
			}
		}
		var listNodeStr = (lbpm.globals.getIdsByNodes(tempNodes)||'')+",";

		var index1=listNodeStr.indexOf(node1.id+",");
		var index2=listNodeStr.indexOf(node2.id+",");
		if(index1==-1 || index2==-1 || index1==index2)
		{
			return 0;
		}
		return index1>index2?1:-1;
	};
	return nodeFilter;
});
