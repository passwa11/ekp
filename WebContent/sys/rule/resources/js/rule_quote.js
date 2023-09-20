/*规则引用*/
Com_IncludeFile("rule_template4node.js",Com_Parameter.ContextPath+"sys/rule/resources/js/",'js',true);
(function(){
	/*if(window.sysRuleQuote){
		return;
	}
	window.sysRuleQuote = new createSysRuleQuote();*/
	window.SysRuleQuote = function(idLabel, nameLabel, key, sysRuleTemplate, formKey){
		var sysRuleQuote = new createSysRuleQuote();
		sysRuleQuote.idLabel = idLabel;
		sysRuleQuote.nameLabel = nameLabel;
		sysRuleQuote.key = key;
		sysRuleQuote.formKey = formKey;
		sysRuleQuote.serviceKey = 'com.landray.kmss.sys.rule.service.spring.SysRuleUpdateQuoteInfoService';
		if(sysRuleTemplate){//兼容流程页面显示节点信息（因为初始化不用这个对象也可以），但是编辑一定要传
			sysRuleQuote.sysRuleTemplate = sysRuleTemplate;
			sysRuleQuote.ruleTemplateId = sysRuleTemplate.keyToId[formKey];
			sysRuleQuote.exitRuleTemplate = true;
		}else{
			sysRuleQuote.sysRuleTemplate = sysRuleTemplate4Node;
			sysRuleQuote.ruleTemplateId = sysRuleTemplate4Node.id;
			try{
				if(FlowChartObject && FlowChartObject.otherContentInfo){
					var otherContentInfo = eval('('+FlowChartObject.otherContentInfo+')');
					sysRuleQuote.ruleTemplateId = otherContentInfo[sysRuleQuote.serviceKey].fdId;
					sysRuleQuote.processId = otherContentInfo[sysRuleQuote.serviceKey].processId;
					var quoteInfo = otherContentInfo[sysRuleQuote.serviceKey].quoteInfo;
					if(typeof quoteInfo === 'string'){
						quoteInfo = eval('('+quoteInfo+')');
					}
					sysRuleQuote.sysRuleTemplate.quoteInfos[sysRuleQuote.ruleTemplateId] = quoteInfo;
				}
			}catch(e){}
		}
		return sysRuleQuote;
	}
	function createSysRuleQuote(){
		/*属性定义*/
		this.unids = Data_GetRadomId(200);//唯一id集
		this.idLabel = null;//操作的对象id
		this.nameLabel = null;//操作的对象name
		this.key = null;//该对象的唯一标识，也用于dom元素的访问
		this.formKey = null;//多个form时的标记key
		this.initRuleQuoteNum = 0;//初始化时记录的角标
		this.delMapContentIds = [];//待删除的无效引用
		this.sysRuleTemplate = null;//规则机制对象
		this.ruleTemplateId = null;//对于规则机制的对象id
		this.exitRuleTemplate = false;//是否存在规则机制
		/*函数定义*/
		this.initRuleQuote = initRuleQuote;//初始化
		this.initRuleMap = initRuleMap;//映射表初始化
		this.recordDelMapContentIds = recordDelMapContentIds;//记录切换模式时需要删除的引用
		this.delMapContents = delMapContents;//删除引用
		this.getAllMapDocIds = getAllMapDocIds;//获取所有映射集规则集id
		this.getAllMapObjs = getAllMapObjs;//获取所有全局映射对象
		this.selectRuleSet = selectRuleSet;//选择规则集
		this.selectRule = selectRule;//选择规则
		this.writeBack = writeBack;//回写
		this.createRuleMap = createRuleMap;//创建当前映射表
		this.getMapContent = getMapContent;//根据id获取引用信息
		this.updateMapContent = updateMapContent;//更新引用信息
		this.getOtherInfoByType = getOtherInfoByType;//获取其他描述信息
		this.writeData = writeData;//写数据
		this.getElemIndex = getElemIndex;//获取事件源角标
		this.getUnid = getUnid;//获取唯一id
		this.isCompleteMapping = isCompleteMapping;//校验是否完全映射
		this.isCompleteRuleMapping = isCompleteRuleMapping;//校验是否完全映射
		this.checkData = checkData;//校验
		this.setFieldOption = setFieldOption;//设置select option
		this.changeQuoteInfoId = changeQuoteInfoId;//修改在节点上的引用信息，保证最新
	}

	function initRuleQuote(json,index,idLabel,nameLabel,method){
		if(idLabel && nameLabel){
			this.idLabel = idLabel;
			this.nameLabel = nameLabel;
		}
		if(!json || json==''){
			return;
		}
		try{
			json = eval('('+json+')');
		}catch(err){
			return -1;
		}
		if(json.type != "rule"){
			return -1;
		}
		//var targetWin = this.getTargetWin();
		//var index = this.initRuleQuoteNum;
		var quoteInfo;
		var fdId = json.sysRuleTemplateId;
		var processId = json.fdProcessId || "";
		if(method == "edit"){
			quoteInfo = this.sysRuleTemplate.quoteInfos[fdId];
		}
		//兼容在流程文档修改流程图
		if(!this.exitRuleTemplate){
			try{
				//从节点上取
				if(processId && json.quoteInfo){
					quoteInfo = json.quoteInfo;
					if(typeof quoteInfo === 'string'){
						quoteInfo = eval('('+quoteInfo+')');
					}
				}

				if(!quoteInfo && FlowChartObject && FlowChartObject.otherContentInfo){
					var otherContentInfo = eval('('+FlowChartObject.otherContentInfo+')');
					quoteInfo = otherContentInfo[this.serviceKey].quoteInfo;
					if(typeof quoteInfo === 'string'){
						quoteInfo = eval('('+quoteInfo+')');
					}
				}
			}catch(e){}
		}
		var beanName = "sysRuleTemplateService&fdId="+fdId;
		if(!quoteInfo || quoteInfo.lenght==0){
			//从后台获取
			var rtnVal = new KMSSData().AddBeanData(beanName).GetHashMapArray()[0];
			if(!rtnVal || !rtnVal.quoteInfo){
				return;
			}
			quoteInfo = eval('('+rtnVal.quoteInfo+')');
		}
		var sysRuleQuoteInfoId = json.sysRuleQuoteInfoId;
		var mapContent = this.getMapContent(sysRuleQuoteInfoId, quoteInfo);
		if(mapContent.id){
			var docId = mapContent.docId;
			var ruleId = mapContent.ruleId;
			var parent = $(".rule."+this.key)[index];
			var mapContentStr = JSON.stringify(mapContent);
			$(parent).find("[name='sysRuleQuoteInfoId']").eq(0).val(sysRuleQuoteInfoId);
			$(parent).find("[name='mapContent']").eq(0).val(mapContentStr);
			$(parent).find("[name='lastMapContent']").eq(0).val(mapContentStr);
			if(mapContent.mode == "rule"){//引用规则
				var beanName = 'sysRuleSetRuleService&fdId='+ruleId;
				var ruleObj = new KMSSData().AddBeanData(beanName).GetHashMapArray()[0];
				$(parent).find("[name='ruleId']").eq(0).val(ruleId);
				$(parent).find("[name='ruleName']").eq(0).val(ruleObj.fdName);
			}else{//引用规则集
				var beanName = 'sysRuleSetDocService&fdId='+docId;
				var docObj = new KMSSData().AddBeanData(beanName).GetHashMapArray()[0];
				$(parent).find("[name='ruleId']").eq(0).val(docId);
				$(parent).find("[name='ruleName']").eq(0).val(docObj.fdName);
			}
			if(mapContent.mapEntryId){
				//全局映射
				var mapObj;
				if(method == "edit"){
					mapObj = this.sysRuleTemplate.getMapObjById(mapContent.mapEntryId);
				}
				//如果为空，从后台获取
				if(!mapObj || !mapObj.id){
					var beanName = 'sysRuleTemplateEntryService&fdId='+mapContent.mapEntryId;
					mapObj = new KMSSData().AddBeanData(beanName).GetHashMapArray()[0];
				}
				$(parent).find(".alreadyMapType").eq(0).find("input[name='alreadyMapId']").val(mapContent.mapEntryId);
				$(parent).find(".alreadyMapType").eq(0).find("input[name='alreadyMapName']").val(mapObj.name);
				$(parent).find(".alreadyMapType").eq(0).find("input[name='alreadyMapName']").show();
				$(parent).find(".alreadyMapType").eq(0).css("display","");
				$(parent).find(".mapArea").eq(0).css("display","none");//不显示映射表格
			}else{
				//非全局映射
				var beanName,params;
				if(mapContent.mode == "ruleSet"){
					//引用规则集
					beanName = "sysRuleSetDocService&fdId="+docId+"&type=ruleParam";
					params = new KMSSData().AddBeanData(beanName).GetHashMapArray();
				}else{
					//引用规则
					beanName = "sysRuleSetRuleService&fdId="+ruleId+"&type=param";
					params = new KMSSData().AddBeanData(beanName).GetHashMapArray();
				}
				if(params && params.length>0){
					//情况1：存在参数
					$(parent).find(".alreadyMapType").eq(0).css("display","none");
					$(parent).find(".mapArea").eq(0).css("display","inline-block");
					this.initRuleMap(mapContent,params,index,method);
				}else{
					//情况2：不存在参数
					$(parent).find(".alreadyMapType").eq(0).css("display","none");
					$(parent).find(".mapArea").eq(0).css("display","none");
				}
			}
		}
		//this.initRuleQuoteNum = index+1;
		return index;
	}

	function initRuleMap(mapContent, params, index, method){
		//var targetWin = this.getTargetWin();
		var maps = mapContent.maps;
		//获取参数
		var docId = mapContent.docId;
		if(!params){
			var beanName = 'sysRuleSetDocService&fdId='+docId+'&type=ruleParam';
			params = new KMSSData().AddBeanData(beanName).GetHashMapArray();
		}
		var parent = $(".rule."+this.key)[index];
		var $table = $(parent).find("table.mapTable").eq(0);
		var $trObj = $table.find("tr.pivotRow").eq(0).clone(true);
		$table.find("tr.pivotRow").remove();
		for(var i=0; i<params.length; i++){
			var paramObj = params[i];
			var fieldId = null;
			var fieldName = null;
			var mapId = null;
			var isExist = false;
			for(var j=0; j<maps.length; j++){
				var paramId = maps[j].paramId;
				if(paramId && paramId == paramObj.paramId){
					fieldId = maps[j].fieldId;
					fieldName = maps[j].fieldName;
					mapId = maps[j].id;
					isExist = true;
					break;
				}
			}
			var $newTrObj = $trObj.clone(true);
			//设置id
			mapId = mapId || this.sysRuleTemplate.getUnid();
			$newTrObj.find("input[name='mapId']").val(mapId);
			//设置参数
			$newTrObj.find("input[name='ruleSetParamId']").val(paramObj.paramId);
			$newTrObj.find("input[name='ruleSetParamName']").val(paramObj.paramName);
			//新增的参数需要添加到map中，保持和writeback一样的逻辑
			if(!isExist){
				//设置映射
				var map = {
					paramId:paramObj.paramId,
					fieldId:"",
					fieldName:"",
					id:mapId
				};
				maps.push(map);
				mapContent.maps = maps;
				$(parent).find("[name='mapContent']").eq(0).val(JSON.stringify(mapContent));
			}
			if(method == "view"){//查看
				//若field为空，则不显示该行
				if(!fieldId || fieldId == ""){
					$newTrObj.hide();
				}else{
					//设置字段
					$newTrObj.find("[name='xformFieldId']").val(fieldId);
					$newTrObj.find("[name='xformFieldName']").attr("type","text");
					$newTrObj.find("[name='xformFieldName']").val(fieldName);
					$newTrObj.find("[name='xformField']").hide();
				}
			}else{//编辑
				//设置字段
				var fields = this.sysRuleTemplate.getFields(this.formKey);
				var xformFieldSelect  = $newTrObj.find("[name='xformField']")[0];
				if(xformFieldSelect){
					this.setFieldOption(xformFieldSelect,fields,paramObj,fieldId);
				}
				//$(xformFieldSelect).val(fieldId || "");
			}
			$table.append($newTrObj);
		}
	}

	function recordDelMapContentIds(index){
		var parent = $(".rule."+this.key)[index];
		var mapContent = $(parent).find("[name='mapContent']").eq(0).val();
		if(!mapContent || mapContent == ""){
			return;
		}
		mapContent = eval('('+mapContent+')');
		this.delMapContentIds.push(mapContent.id);
	}

	function delMapContents(){
		if(this.delMapContentIds.length == 0){
			return;
		}
		//var targetWin = this.getTargetWin();
		//if(!targetWin){
		//	return;
		//}
		var quoteInfo = this.sysRuleTemplate.quoteInfos[this.ruleTemplateId] || [];
		var newQuoteInfo = [];
		for(var i=0; i<quoteInfo.length; i++){
			var mapContent = quoteInfo[i];
			var isExit = false;
			for(var j=0; j<this.delMapContentIds.length; j++){
				var id = this.delMapContentIds[j];
				if(id && id==mapContent.id){
					isExit = true;
					break;
				}
			}
			if(isExit && !(mapContent.isNew || mapContent.isAdd)){
				mapContent.isDel = true;
				mapContent.isChange = true;
				newQuoteInfo.push(mapContent);
			}
			if(!isExit){
				newQuoteInfo.push(mapContent);
			}
		}
		this.delMapContentIds = [];
		this.sysRuleTemplate.quoteInfos[this.ruleTemplateId] = newQuoteInfo;
	}

	function getAllMapDocIds(){
		//var targetWin = this.getTargetWin();
		//if(!targetWin){
		//	return;
		//}
		var allMaps = this.sysRuleTemplate.allMaps || [];
		var mapDocIds = "";
		for(var i=0; i<allMaps.length; i++){
			var mapObj = allMaps[i];
			if(mapObj.key && mapObj.key == this.formKey){
				if(mapDocIds.indexOf(mapObj.docId) == -1 && mapObj.name && mapObj.name!=""){
					mapDocIds += mapObj.docId + ";";
				}
			}
		}
		return mapDocIds;
	}

	function getAllMapObjs(id){
		//var targetWin = this.getTargetWin();
		//if(!targetWin){
		//	return;
		//}
		var allMaps = this.sysRuleTemplate.allMaps;
		var mapObjs = [];
		for(var i=0; i<allMaps.length; i++){
			var mapObj = allMaps[i];
			if(mapObj.key && mapObj.key == this.formKey){
				if(id){
					if(mapObj.id == id && mapObj.name && mapObj.name!=""){//未定义名称的映射也会被过滤
						mapObjs.push(mapObj);
					}
				}else{
					if(mapObj.name && mapObj.name!=""){
						var obj = {
							id:mapObj.id,
							name:mapObj.name,
							docId:mapObj.docId,
							docName:mapObj.docName
						}
						if(mapObj.isChange){
							obj['isChange'] = mapObj.isChange;
						}
						if(mapObj.isUpdate){
							obj['isUpdate'] = mapObj.isUpdate;
						}
						if(mapObj.isDel){
							obj['isDel'] = mapObj.isDel;
						}
						mapObjs.push(obj);
					}
				}
			}
		}
		return mapObjs;
	}

	function selectRule(returnType,mode){
		var index = getIndex()-1;
		var mapIds = this.getAllMapDocIds();
		var mapObjs = this.getAllMapObjs();
		if(!returnType || returnType==""){
			returnType = "Boolean";//默认
		}
		var thisObj = this;
		var parent = $(".rule."+this.key)[index];
		var idField = $(parent).find("[name='ruleId']")[0];
		var nameField = $(parent).find("[name='ruleName']")[0];
		var treeBean = "sysRuleSetDocService&getType=true&type=!{typeValue}&parentId=!{value}&alreadyMapIds="+mapIds+"&mapObjs="+encodeURIComponent(JSON.stringify(mapObjs))+"&mode=ruleSet&isAuth=false";
		var treeTitle = Data_GetResourceString("sys-rule:tree.title.sysRuelQuoteInfo.1");
		var searchBean = "sysRuleSetDocService&key=!{keyword}";
		var dataBean = "sysRuleSetDocService&parentId=!{value}&alreadyMapIds="+mapIds+"&mapObjs="+encodeURIComponent(JSON.stringify(mapObjs))+"&dataMode=ruleSet";
		var winTitle = Data_GetResourceString("sys-rule:tree.title.sysRuelQuoteInfo.2");
		var dialog = new KMSSDialog(false, true);
		var action = function(rtnData){
			thisObj.writeBack(rtnData,index,dialog);
		}
		var node = dialog.CreateTree(treeTitle);
		dialog.winTitle = winTitle;
		node.AppendBeanData(treeBean, dataBean, null, null, null);
		node.parameter = dataBean;
		dialog.BindingField(idField, nameField, "", false);
		dialog.SetAfterShow(action);
		dialog.SetSearchBeanData(searchBean);
		dialog.notNull = true;
		dialog.URL = Com_Parameter.ContextPath + "sys/rule/sys_ruleset_temp/dialog.jsp";
		dialog.parameters = {};
		dialog.parameters.firstTreeBean = "sysRuleSetDocService&getType=true&type=!{typeValue}&parentId=!{value}&alreadyMapIds="+mapIds+"&mapObjs="+encodeURIComponent(JSON.stringify(mapObjs))+"&isAuth=false";
		dialog.parameters.mapObjs = encodeURIComponent(JSON.stringify(mapObjs));
		dialog.parameters.mapIds = mapIds;
		dialog.parameters.returnType = returnType;
		dialog.parameters.treeTitle = treeTitle;
		dialog.parameters.initMode = mode;
		dialog.parameters.nameFieldValue = nameField.value;
		dialog.parameters.Com_Parameter = window.Com_Parameter;
		//读取上一次选择的模式
		var mapContent = $(parent).find("[name='mapContent']").eq(0).val();
		if(mapContent && mapContent != ""){
			//非空
			mapContent = eval('('+mapContent+')');
			dialog.parameters.selectMode = mapContent.mode;
		}
		dialog.Show();
	}

	function selectRuleSet(returnType){
		var index = window.getIndex()-1;
		var mapDocIds = this.getAllMapDocIds();
		var mapObjs = this.getAllMapObjs();
		if(!returnType || returnType==""){
			returnType = "Boolean";//默认
		}
		var thisObj = this;
		var parent = $(".rule."+this.key)[index];
		var action = function(rtnData){
			thisObj.writeBack(rtnData,index);
		}
		Dialog_Tree(
			false,
			$(parent).find("[name='ruleId']")[0],
			$(parent).find("[name='ruleName']")[0],
			null,
			"sysRuleSetDocService&getType=true&type=!{typeValue}&parentId=!{value}&mapDocIds="+mapDocIds+"&mapObjs="+encodeURIComponent(JSON.stringify(mapObjs)),
			Data_GetResourceString("sys-rule:tree.title.sysRuelQuoteInfo.1"),
			false,
			action);
	}

	//若是没有mapId并且docId是已经映射的文档，那么就构造map参数，按已经映射的方式走，主要是兼容搜索
	function getRtnVal4Search(rtnVal,mode,allMaps){
		var docId;

		if(mode == "ruleSet"){
			docId = rtnVal.id;
		}else{
			docId = rtnVal.sysRuleSetDocId;
		}

		for(var i=0; i<allMaps.length; i++){
			var mapDocId = allMaps[i].docId;
			if(mapDocId == docId){//存在一个文档对于多个映射，默认取第一个
				rtnVal.mapId = allMaps[i].id;
				rtnVal.mapName = allMaps[i].name;
				break;
			}
		}

		return rtnVal;
	}

	function writeBack(rtnData,index,dialog){
		if(!rtnData){
			return;
		}
		var rtnVal = rtnData.GetHashMapArray()[0];
		if(!rtnVal.mapId){
			rtnVal = getRtnVal4Search(rtnVal,dialog.parameters.mode,this.sysRuleTemplate.allMaps || []);
		}

		var mapObjs = [];
		var ruleId,docId,mapId,mapName;
		var mode = dialog.parameters.mode;
		var parent = $(".rule."+this.key)[index];
		if(mode == "ruleSet"){
			docId = rtnVal.id;
			if(rtnVal.mapId && rtnVal.mapId != ""){
				mapId = rtnVal.mapId;
				mapName = rtnVal.mapName;
				mapObjs = this.getAllMapObjs(mapId);
				//校验当前引用的规则集的映射是否映射完全
				if(!this.isCompleteMapping(mapId,docId)){
					var msg = Data_GetResourceString("sys-rule:sysRuleQuoteInfo.validate.2");
					var result = window.confirm(msg);
					if(!result){
						$(parent).find("[name='ruleId']")[0].value = "";
						$(parent).find("[name='ruleName']")[0].value = "";
						return;
					}
				}
			}else{
				docId = rtnVal.id;;
			}
			$(parent).find("[name='ruleId']")[0].value = docId;
			$(parent).find("[name='ruleName']")[0].value = rtnVal.name;
		}else{
			ruleId = rtnVal.id;
			docId = rtnVal.sysRuleSetDocId;
			if(rtnVal.mapId && rtnVal.mapId != ""){
				mapId = rtnVal.mapId;
				mapName = rtnVal.mapName;
				mapObjs = this.getAllMapObjs(mapId);
				//校验当前引用的规则的映射是否映射完全
				if(!this.isCompleteRuleMapping(mapId,ruleId)){
					var msg = Data_GetResourceString("sys-rule:sysRuleQuoteInfo.validate.3");
					var result = window.confirm(msg);
					if(!result){
						$(parent).find("[name='ruleId']")[0].value = "";
						$(parent).find("[name='ruleName']")[0].value = "";
						return;
					}
				}
			}
			$(parent).find("[name='ruleId']")[0].value = ruleId;
			$(parent).find("[name='ruleName']")[0].value = rtnVal.name;
		}

		//var targetWin = this.getTargetWin();
		var idLabel = this.idLabel;
		var nameLabel = this.nameLabel;
		//给定唯一id
		var unid = $(parent).find("[name='sysRuleQuoteInfoId']").eq(0).val();
		var mapContent;
		if(unid){
			var quoteInfo = this.sysRuleTemplate.quoteInfos[this.ruleTemplateId] || [];
			mapContent = this.getMapContent(unid, quoteInfo);
		}
		if(!unid || unid == "" || (mapContent && !mapContent.isAdd && !mapContent.isNew)){
			//只要改变了规则，都设置成新的引用id
			unid = this.sysRuleTemplate.getUnid();
			$(parent).find("[name='sysRuleQuoteInfoId']").eq(0).val(unid);
		}
		var obj = {
			sysRuleQuoteInfoId:unid,
			sysRuleTemplateId:this.ruleTemplateId,
			type:'rule'
		};
		$("[name='"+nameLabel+"']").eq(index).val(rtnVal.name || "");
		var refType = $(parent).find("[name='reference.type']").eq(0).val();
		//根据docId获取规则集的具体信息
		var beanName = "sysRuleSetDocService&fdId="+docId;
		var docObj = new KMSSData().AddBeanData(beanName).GetHashMapArray()[0];
		var	mapContent = {
			id:unid,
			docId:docId,
			docVersion:docObj?docObj.fdVersion:"",
			ruleId:ruleId || "",
			maps:[],
			desc:this.getOtherInfoByType(refType,index),
			type:refType,
			mode:mode
		};
		if(mapObjs.length > 0){
			//已经映射
			mapContent.mapEntryId = mapId;
			$(parent).find(".alreadyMapType").eq(0).find("input[name='alreadyMapId']").val(mapId);
			$(parent).find(".alreadyMapType").eq(0).find("input[name='alreadyMapName']").val(mapName);
			$(parent).find(".alreadyMapType").eq(0).find("input[name='alreadyMapName']").show();
			$(parent).find(".alreadyMapType").eq(0).css("display","");
			$(parent).find(".mapArea").eq(0).css("display","none");//不显示映射表格
		}else{
			//未映射
			var params;
			if(mode=="ruleSet"){
				var beanName = "sysRuleSetDocService&fdId="+docId+"&type=ruleParam";
				params = new KMSSData().AddBeanData(beanName).GetHashMapArray();
			}else{
				var beanName = "sysRuleSetRuleService&fdId="+ruleId+"&type=param";
				params = new KMSSData().AddBeanData(beanName).GetHashMapArray();
			}
			if(params && params.length>0){
				//情况1：存在参数
				$(parent).find(".alreadyMapType").eq(0).css("display","none");
				$(parent).find(".mapArea").eq(0).css("display","inline-block");
				this.createRuleMap(params,index,mapContent);
			}else{
				//情况2：不存在参数
				$(parent).find(".alreadyMapType").eq(0).css("display","none");
				$(parent).find(".mapArea").eq(0).css("display","none");
			}
		}
		//设置到页面暂存
		$(parent).find("[name='mapContent']").eq(0).val(JSON.stringify(mapContent));
		//如果在流程修改流程图，直接将数据保存到节点上
		if(this.processId){
			obj['fdProcessId'] = this.processId;
			var datas = [];
			datas.push(mapContent);
			obj['quoteInfo'] = JSON.stringify(datas);
		}
		$("[name='"+idLabel+"']").eq(index).val(JSON.stringify(obj));
	}

	function isCompleteMapping(mapId,docId){
		//var targetWin = this.getTargetWin();
		//if(!targetWin){
		//	return false;
		//}
		var mapObj = this.sysRuleTemplate.getMapObjById(mapId);
		var params = this.sysRuleTemplate.allParams[docId];
		if(mapObj.maps && params){
			if(params.length > mapObj.maps.length){
				return false;
			}else{
				for(var i=0; i<params.length; i++){
					var paramId = params[i].paramId;
					for(var j=0; j<mapObj.maps.length; j++){
						var map = mapObj.maps[j];
						if(paramId == map.paramId && (!map.fieldId || map.fieldId=="")){
							return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	function isCompleteRuleMapping(mapId, ruleId){
		//var targetWin = this.getTargetWin();
		//if(!targetWin){
		//	return false;
		//}
		var beanName = "sysRuleSetRuleService&fdId="+ruleId+"&type=param";
		var ruleParams = new KMSSData().AddBeanData(beanName).GetHashMapArray();
		var mapObj = this.sysRuleTemplate.getMapObjById(mapId);
		if(mapObj.maps){
			for(var i=0; i<ruleParams.length; i++){
				var ruleParam = ruleParams[i];
				var isMap = false;
				for(var j=0; j<mapObj.maps.length; j++){
					var map = mapObj.maps[j];
					if(map.paramId == ruleParam.paramId && map.fieldId && map.fieldId != ""){
						isMap = true;
					}
				}
				if(!isMap){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	function createRuleMap(params,index,mapContent){
		//var targetWin = this.getTargetWin();
		//if(!targetWin)
		//	return;

		var maps = [];
		//获取字段
		var parent = $(".rule."+this.key)[index];
		var paramIds = "";
		var fields = this.sysRuleTemplate.getFields(this.formKey);
		var $table = $(parent).find("table.mapTable").eq(0);
		var $trObj = $table.find("tr.pivotRow").eq(0).clone(true);
		$table.find("tr.pivotRow").remove();
		for(var i=0; i<params.length; i++){
			var param = params[i];
			var $newTrObj = $trObj.clone(true);
			var unid = this.sysRuleTemplate.getUnid();
			$newTrObj.find("input[name='mapId']").val(unid);
			//设置参数
			$newTrObj.find("input[name='ruleSetParamId']").val(param.paramId);
			$newTrObj.find("input[name='ruleSetParamName']").val(param.paramName);

			//设置映射
			var map = {
				paramId:param.paramId,
				fieldId:"",
				fieldName:"",
				id:unid
			};
			//设置字段
			var xformFieldSelect  = $newTrObj.find("[name='xformField']")[0];
			//xformFieldSelect.options.length = 0;
			$(xformFieldSelect).empty();
			this.setFieldOption(xformFieldSelect,fields,param);
			maps.push(map);
			$table.append($newTrObj);
		}
		mapContent.maps = maps;
	}

	function getMapContent(id, quoteInfo){
		for(var i=0; i<quoteInfo.length; i++){
			var mapContent = quoteInfo[i];
			if(mapContent.id == id){
				return mapContent;
			}
		}
		return {};
	}

	function updateMapContent(value, fieldSelect){
		//var targetWin = this.getTargetWin();
		//if(!targetWin){
		//	return;
		//}
		var index = this.getElemIndex()-1;
		var idLabel = this.idLabel;
		//获取唯一id
		var parentObj = $(".rule."+this.key)[index];
		var unid = $("[name='"+idLabel+"']").eq(index).val();
		var $parent = $(fieldSelect).parents("tr.pivotRow").eq(0);
		var paramId = $parent.find("[name='ruleSetParamId']").val();
		var mapId = $parent.find("[name='mapId']").val();
		var mapContent = $(parentObj).find("[name='mapContent']").eq(0).val();
		mapContent = eval('(' + mapContent + ')');
		var maps = mapContent.maps;
		var fields = this.sysRuleTemplate.getFields(this.formKey);
		var fieldObj = this.sysRuleTemplate.getFieldById(value,fields);
		var isChange = false;
		for(var i=0; i<maps.length; i++){
			if(maps[i].id == mapId){
				if(!mapContent.isAdd && !mapContent.isNew){
					//参数改变，需要改动参数的id
					var unid = this.sysRuleTemplate.getUnid();;
					maps[i].id = unid;
					$parent.find("[name='mapId']").val(unid);
					isChange = true;
				}
				maps[i].fieldId = value;
				maps[i].fieldName = fieldObj.label;
				break;
			}
		}
		//如果映射表中有一行数据改动，则所有行的id都得进行改动，因为即将会生成一个新的引用
		for(var i=0; i<maps.length; i++){
			var unid = this.sysRuleTemplate.getUnid();;
			maps[i].id = unid;
			$parent.parents("table:eq(0)").find("tr.pivotRow").eq(i).find("[name='mapId']").val(unid);
		}
		mapContent.maps = maps;
		$(parentObj).find("[name='mapContent']").eq(0).val(JSON.stringify(mapContent));
		//如果在流程修改流程图，直接将数据保存到节点上
		var obj = $("[name='"+idLabel+"']").eq(index).val();
		try{
			obj = eval('('+obj+')');
			if(obj && this.processId){
				obj['fdProcessId'] = this.processId;
				var datas = [];
				datas.push(mapContent);
				obj['quoteInfo'] = JSON.stringify(datas);
			}
			$("[name='"+idLabel+"']").eq(index).val(JSON.stringify(obj));
		}catch(e){}
	}

	function getOtherInfoByType(type,index){
		var nodeObj = {};
		if(type == "autobranchnode"){
			//分支节点引用，获取该节点id，线id和每一条线的开始节点和结束节点
			var nodeId = AttributeObject.NodeObject.Data.id;
			nodeObj.id = nodeId;
			var lineOuts = AttributeObject.NodeObject.LineOut;
			var endNodeName = $("[name='nextNodeName']").eq(index).val();
			for(var i=0; i<lineOuts.length; i++){
				var lineOut = lineOuts[i];
				var lineId = lineOut.Data.id;
				var endNode = lineOut.EndNode;
				var endNodeId = endNode.Data.id;
				if(endNodeName.indexOf(endNodeId) != -1){
					var lineObj = {
						id:lineId,
						endNodeId:endNodeId
					}
					nodeObj.line = lineObj;
				}
			}
		}else if(type == "reviewnode"){
			var nodeId = AttributeObject.NodeObject.Data.id;
			nodeObj.id = nodeId;
		}
		return nodeObj;
	}

	function checkData(){
		var parents = $(".rule."+this.key);
		for(var i=0; i<parents.length; i++){
			if($(parents[i]).css("display") != "none"){
				var mapAreaObj = $(parents[i]).find(".mapArea")[0];
				if($(mapAreaObj).css("display") != "none"){
					var $fieldObjs = $(mapAreaObj).find("[name='xformField']");
					for(var j=0; j<$fieldObjs.length; j++){
						var fieldObj = $fieldObjs[j];
						if($(fieldObj).val() == ""){
							var msg = Data_GetResourceString("sys-rule:sysRuleQuoteInfo.validate.4");
							window.alert(msg);
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	function writeData(name,data){
		//var targetWin = this.getTargetWin();
		//if(!targetWin){
		//	return;
		//}
		var parents = $(".rule."+this.key);
		//删除无效引用
		this.delMapContents();
		//var $mapContentObjs = $(parent).find("[name='mapContent']");
		for(var i=0; i<parents.length; i++){
			//quoteInfo一次循环更新一次，所以需要实时获取
			var quoteInfo = this.sysRuleTemplate.quoteInfos[this.ruleTemplateId] || [];

			//新的映射内容
			var mapContentObj = $(parents[i]).find("[name='mapContent']")[0];
			var value = $(mapContentObj).val();
			if(!value || value==""){
				continue;
			}
			//旧的映射内容
			var lastMapContentObj = $(parents[i]).find("[name='lastMapContent']")[0];
			var lastVal = $(lastMapContentObj).val();

			var mapContentData = eval('('+value+')');
			var lastMapContentData = lastVal ? eval('('+lastVal+')') : null;
			var isExit = false;
			var newQuoteInfo = [];
			for(var j=0; j<quoteInfo.length; j++){
				var mapContent = quoteInfo[j];
				if(lastMapContentData && lastMapContentData.id==mapContent.id){
					isExit = true;
					//若选择的模式已经不是规则了，则需要删除该数据
					if(name){
						var mode = $("input[name^='"+name+"']:checked").eq(i).val();
						if(mode && mode != "rule"){
							if(mapContent.isAdd || mapContent.isNew){
								//quoteInfo.splice(j,1);
							}else{
								//这里不再删除数据，而是给一个标志
								mapContent.isDel = true;
								mapContent.isChange = true;
								newQuoteInfo.push(mapContent);
							}
							break;
						}else{
							//存在，则更新
							//quoteInfo[j] = mapContentData;
							//这里不再进行更新，而是新增数据，进行新增
							if(value != lastVal && !mapContent.isNew && !mapContent.isAdd){
								mapContent.isUpdate = true;
								mapContent.isChange = true;
								var newMapContentData = eval('('+JSON.stringify(mapContentData)+')');
								var unid = this.sysRuleTemplate.getUnid();
								newMapContentData.id = unid;
								var idLabelObj = $("[name='"+this.idLabel+"']").eq(i)[0];
								this.changeQuoteInfoId(idLabelObj,unid,data);
								newMapContentData.isNew = true;
								//quoteInfo.push(newMapContentData);
								newQuoteInfo.push(mapContent);
								newQuoteInfo.push(newMapContentData);
							}else{
								//quoteInfo[j] = mapContentData;
								newQuoteInfo.push(mapContentData);
							}
						}
					}else{
						//判断ruleId是否存在
						var ruleId = $(parents[i]).find("[name='ruleId']").eq(0).val();
						if(ruleId){
							//存在，则更新
							//quoteInfo[j] = mapContentData;
							//这里不再进行更新，而是新增数据，进行新增
							if(value != lastVal && !mapContent.isNew && !mapContent.isAdd){
								mapContent.isUpdate = true;
								mapContent.isChange = true;
								var newMapContentData = eval('('+JSON.stringify(mapContentData)+')');
								var unid = this.sysRuleTemplate.getUnid();
								newMapContentData.id = unid;
								var idLabelObj = $("[name='"+this.idLabel+"']").eq(i)[0];
								this.changeQuoteInfoId(idLabelObj,unid,data);
								newMapContentData.isNew = true;
								//quoteInfo.push(newMapContentData);
								newQuoteInfo.push(mapContent);
								newQuoteInfo.push(newMapContentData);
							}else{
								//quoteInfo[j] = mapContentData;
								newQuoteInfo.push(mapContentData);
							}
						}else{
							if(mapContent.isAdd || mapContent.isNew){
								//quoteInfo.splice(j,1);
							}else{
								mapContent.isDel = true;
								mapContent.isChange = true;
								newQuoteInfo.push(mapContent);
							}

							break;
						}
					}
				}else{
					newQuoteInfo.push(mapContent);
				}
			}
			if(!isExit){
				//不存在，则新增
				mapContentData.isAdd = true;
				//quoteInfo.push(mapContentData);
				newQuoteInfo.push(mapContentData);
			}
			//this.sysRuleTemplate.quoteInfos[this.ruleTemplateId] = quoteInfo;
			try{
				//兼容历史数据(因为之前的缺陷留下来的数据问题）
				if(typeof AttributeObject != "undefined" && AttributeObject){
					var nodeId = AttributeObject.NodeData.id;
					var mapContents = [];
					for(var j=0; j<newQuoteInfo.length; j++){
						var mapContent = newQuoteInfo[j];
						if(mapContent.type == "reviewnode" || mapContent.type == 'autobranchnode'){
							if(nodeId == mapContent.desc.id){
								mapContents.push(mapContent);
							}
						}
					}
					if(mapContents.length > 0){
						var handlerSelectType = AttributeObject.NodeData.handlerSelectType;
						var optHandlerSelectType = AttributeObject.NodeData.optHandlerSelectType;
						var ids = [];
						if(handlerSelectType == "rule"){
							var handlerIds = AttributeObject.NodeData.handlerIds;
							var handlerObj = eval('('+handlerIds+')');
							ids.push(handlerObj.sysRuleQuoteInfoId);
						}
						if(optHandlerSelectType == "rule"){
							var optHandlerIds = AttributeObject.NodeData.optHandlerIds;
							var optHandlerObj = eval('('+optHandlerIds+')');
							ids.push(optHandlerObj.sysRuleQuoteInfoId);
						}
						for(var j=0; j<mapContents.length; j++){
							var mapContent = mapContents[j];
							if(ids.indexOf(mapContent.id) == -1 && !mapContent.isChange && !mapContent.isAdd && !mapContent.isNew){
								mapContent.isChange = true;
								mapContent.isDel = true;
							}
						}
					}
				}
				//兼容历史数据(因为之前的缺陷留下来的数据问题）
			}catch (e){}
			this.sysRuleTemplate.quoteInfos[this.ruleTemplateId] = newQuoteInfo;
			//解决浏览器兼容，克隆对象
			this.sysRuleTemplate.addObjectData("quoteInfos",this.sysRuleTemplate.quoteInfos);
			if(!this.exitRuleTemplate && FlowChartObject && FlowChartObject.otherContentInfo){//兼容在流程文档修改流程图问题
				var otherContentInfo = eval('('+FlowChartObject.otherContentInfo+')');
				var ruleInfo = otherContentInfo[this.serviceKey] || {};
				ruleInfo.fdId = this.ruleTemplateId;
				//ruleInfo.quoteInfo = quoteInfo;
				ruleInfo.quoteInfo = newQuoteInfo;
				otherContentInfo[this.serviceKey] = ruleInfo;
				FlowChartObject.otherContentInfo = JSON.stringify(otherContentInfo);
			}
		}
	}

	function changeQuoteInfoId(idLabelObj, unid, data){
		var val = $(idLabelObj).val();
		if(!val){
			return;
		}
		var json = eval('('+val+')');
		json.sysRuleQuoteInfoId = unid;
		$(idLabelObj).val(JSON.stringify(json));
		data.handlerIds = JSON.stringify(json);
	}

	function setFieldOption(selectObj, fields, param, value){
		$(selectObj).html("");
		var options = '<option value="">请选择</option>';
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			if(param.paramType == 'String'){
				options += '<option value="'+field.name+'">'+field.label+'</option>';
				continue;
			}
			if(field.orgType && param.paramType){
				if(field.orgType == param.paramType){
					options += '<option value="'+field.name+'">'+field.label+'</option>';
					continue;
				}
			}
			var paramType = getMutilTypeVal(param.paramType,param.isMulti);
			if(field.type){
				var fieldType = getCompatibleType(field.type);
				if(compareTypes(fieldType,getRealTypeVal(paramType))){
					options += '<option value="'+field.name+'">'+field.label+'</option>';
				}
			}
		}
		$(selectObj).html(options);
		if(value){
			$(selectObj).val(value);
		}
	}

	function getElemIndex(){
		var childTable = window.GetParentByTagName("TABLE");
		var row = window.GetParentByTagName("TR", childTable);
		var table = window.GetParentByTagName("TABLE",row);
		var id = $(table).attr("id");
		//判断table是否为docList的表格，不是就返回默认的
		var isDocList = false;
		if(id){
			if(DocList_Info && DocList_Info.length > 0){
				for(var i=0; i<DocList_Info.length; i++){
					var tableId = DocList_Info[i];
					if(tableId === id){
						isDocList = true;
						break;
					}
				}
			}
		}
		if(isDocList){
			for(var i=1; i<table.rows.length; i++){
				if(table.rows[i]==row){
					return i;
				}
			}
		}else{
			//默认返回1
			return 1;
		}
		return -1;
	}

	function getUnid(){
		if(this.unids.length <= 1){
			//重新加载
			this.unids = window.Data_GetRadomId(200);
		}
		var unid = this.unids[0];
		this.unids.splice(0,1);
		return unid;
	}
})();