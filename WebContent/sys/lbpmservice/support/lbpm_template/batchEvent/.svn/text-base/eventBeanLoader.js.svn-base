define(function(require, exports, module) {
	var $ = require('lui/jquery');
	exports.initColumns = function(cfg){
		// 格式化列定义，返回以字段名为key的json：{docSubject:{title:xxx}}，方便数据通过key定位
		cfg.columns = formatColumns(cfg);
	}

	exports.request = function(cfg,cb){
		var records = formatRequestRecords(cfg.data);
		cb(records);
	}
	
	// params : {}
	function formatColumns(params){
		var columns = ["序号","节点编号","节点名称","事件类型","事件名称","事件侦听器","操作"];
		return columns;
	}
	
	//生成固定结构--{"records":[{docSubject:{value:xx},fdId:{value:xx},fdName:{value:xx},docCreateTime:{value:xx}}]}
	function formatRequestRecords(data){
		var rs = {};
		var records = rs["records"] = [];
		var rowIndex = 0;
		for(var key in data){
			if(key){
				var nodeId = "";
				var nodeName = "";
				if(key.indexOf(".")!=-1){
					var nodes = key.split(".");
					nodeId = nodes[0];
					nodeName = nodes[1];
				}
				// 只传序号，节点编号，节点名称，id,listenerId,listenerName，name，type，typeName等属性
				for(var i = 0;i < data[key].length;i++){
					var fieldInfo = {};
					fieldInfo["rowIndex"] = ++rowIndex;
					fieldInfo["nodeId"] = nodeId;
					fieldInfo["nodeName"] = nodeName;
					fieldInfo["id"] = data[key][i].id;
					fieldInfo["type"] = data[key][i].type;
					fieldInfo["typeName"] = data[key][i].typeName;
					fieldInfo["name"] = data[key][i].name;
					fieldInfo["listenerId"] = data[key][i].listenerId;
					fieldInfo["listenerName"] = data[key][i].listenerName;
					records.push(fieldInfo);
				}
			}
		}

		return rs;
	}

})