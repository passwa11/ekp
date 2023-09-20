/*规则映射*/
(function(){
	if(window.sysRuleTemplate){
		return;
	}
	window.sysRuleTemplate = new createSysRuleTemplate();
	function createSysRuleTemplate(){
		/*属性定义*/
		this.ids = [];//机制唯一id，可能多个
		this.modelNames = [];//当前model，可能多个
		this.fdKeys = [];//当前文档key,可能有多个
		this.keyToId = {};//key和id的对象，可以通过key获取对于的id
		this.allParams = new Object();//对应规则集的所有参数
		this.allMaps = [];//所有的映射
		this.quoteInfos = {};//所有引用
		this.unids = Data_GetRadomId(200);//唯一id集
		this.newEntryIds = [];
		this.initAllMappsStatus = false;//初始化map的状态，初始化阶段为true，初始化完成为false 
		/*函数定义*/
		this.init = init;//初始化规则引擎（映射）相关内容
		this.initQuoteInfo = initQuoteInfo;//初始化引用内容
		this.initAllMaps = initAllMaps;//初始化所有的映射表
		this.selectRuleSet = selectRuleSet;//选择规则集
		this.resetRuleSet = resetRuleSet;//重置选择的规则集
		this.createRuleSet = createRuleSet;//新增规则集并完成映射操作
		this.updateDoc = updateDoc;//更新规则集文档信息
		this.updateMap = updateMap;//更新映射表
		this.saveMap = saveMap;//保存映射表
		this.recordFieldBeforeUpdateMap = recordFieldBeforeUpdateMap;//更新映射前记录当前操作的映射字段
		this.changeName = changeName;//更新映射实体名称
		this.reloadMap = reloadMap;//重载映射
		this.updateBeforeDel = updateBeforeDel;//删除一行前更新数据
		this.updateMapObjById = updateMapObjById;//根据id更新映射数据
		this.getMapObjById = getMapObjById;//根据id获取映射数据
		this.delMapObjById = delMapObjById;//根据id删除映射数据
		this.getElemIndex = getElemIndex;//获取数据源的index
		this.getParamById = getParamById;//根据参数id从参数数组获取对应得参数
		this.getFieldById = getFieldById;//根据字段id从字段数组获取对应的字段
		this.getFieldByMapsAndParamId = getFieldByMapsAndParamId;//根据参数id从映射表获取对应的映射字段
		this.delInvalidData = delInvalidData;//删除无效数据
		this.writeData = writeData;//写入数据
		this.getFields = getFields;//获取字段集
		this.getUnid = getUnid;//获取唯一id
		this.addRow = addRow;//添加一行
		this.setFieldOption = setFieldOption;//设置select option
		this.addObjectData = addObjectData;
		this.objClone = objClone;
	}
	
	function init(method){
		//初始化引用内容
		this.initQuoteInfo(method);
		//初始化所有的参数
		for(var i=0; i<this.fdKeys.length; i++){
			this.initAllMaps(method,this.fdKeys[i]);
		}
	}
	
	function initQuoteInfo(method){
		for(var i=0; i<this.fdKeys.length; i++){
			var quoteInfo = $("[name='sysRuleTemplateForms."+this.fdKeys[i]+".quoteInfo']").val();
			var fdId = $("[name='sysRuleTemplateForms."+this.fdKeys[i]+".fdId']").val();
			//兼容历史数据因为删除节点留下未清楚的数据导致的映射没发删除问题start
			var getLbpmNodeFun = "LBPM_Template_getNodes"+this.fdKeys[i]+"()";
			var nodeIds = [];
			try{
				var nodes = eval(getLbpmNodeFun) || [];
				for(var j=0; j<nodes.length; j++){
					var node = nodes[j];
					nodeIds.push(node.value);
				}
			}catch (e){
			}
			//兼容历史数据因为删除节点留下未清楚的数据导致的映射没发删除问题end
			if(quoteInfo && quoteInfo != ""){
				var obj = eval('('+quoteInfo+')');
				for(var i=0; i<obj.length; i++){
					if(obj[i].isAdd)
						delete(obj[i]["isAdd"]);
					if(obj[i].isNew)
						delete(obj[i]["isNew"]);
					//兼容历史数据因为删除节点留下未清楚的数据导致的映射没发删除问题start
					try{
						//已经不存在的节点数据状态改为删除
						if(nodeIds.length > 0 && (obj[i].type=="reviewnode" || obj[i].type=="autobranchnode") && nodeIds.indexOf(obj[i].desc.id) == -1){
							obj[i].isChange = true;
							obj[i].isDel = true;
						}
					}catch (e){}
					//兼容历史数据因为删除节点留下未清楚的数据导致的映射没发删除问题end
				}
				
				this.quoteInfos[fdId] = obj;
				
			}
		}
	}
	
	function initAllMaps(method,fdKey){
		this.initAllMappsStatus = true;
		//获取所有的规则集id
		var $sysRuleSetDocObjs = $("[name^='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys'][name$='sysRuleSetDocId']");
		var sysRuleSetDocIds="";
		for(var i=0; i<$sysRuleSetDocObjs.length; i++){
			sysRuleSetDocIds += $sysRuleSetDocObjs[i].value + ";";
		}
		if(sysRuleSetDocIds != ""){
			//请求
			var beanName = "sysRuleSetDocService&fdIds="+sysRuleSetDocIds+"&type=ruleParam";
			var rtnVal = new KMSSData().AddBeanData(beanName).GetHashMapArray();
			var allParams = {};
			for(var i=0; i<rtnVal.length; i++){
				allParams[rtnVal[i].docId] = eval('('+rtnVal[i].value+')');
			}
			this.allParams = allParams;
		}
		
		var $sysRuleSetDocObjs = $("[name^='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys'][name$='sysRuleSetDocId']");
		var $contentObjs = $("[name^='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys'][name$='content']");
		for(var i=0; i<$contentObjs.length; i++){
			var contentObjs = $contentObjs[i];
			var content = contentObjs.value;
			var sysRuleSetDocId = $sysRuleSetDocObjs[i].value;
			var $table = $("#ruleSetMap_"+fdKey).find(".mapContent").eq(i);
			if(!content){
				$table.find("tr.pivotRow").hide();
				continue;
			}
			
			//转换解析到页面
			var mapObjs = [];
			try{
				mapObjs = eval('(' + content + ')');
			}catch (e) {
				mapObjs = $.parseJSON(content);
			}
			//var mapObjs = eval('(' + content + ')');
			var $trObj = $table.find("tr.pivotRow").eq(0).clone(true);
			$table.find("tr.pivotRow").remove();
			//设置表格
			var params = this.allParams[sysRuleSetDocId];
			var fields = [];
			if(method == "edit"){
				fields = this.getFields(fdKey);
			}
			for(var j=0; j<params.length; j++){
				var $newTrObj = $trObj.clone(true);
				var param = params[j];
				//设置参数
				$newTrObj.find("[name='ruleSetParamId']").eq(0).val(param.paramId);
				$newTrObj.find("[name='ruleSetParamName']").eq(0).val(param.paramName);
				//设置参数对应的字段
				if(method == "edit"){
					var xfromFieldSelect = $newTrObj.find("[name='xformField']")[0];
					this.setFieldOption(xfromFieldSelect, fields, param);
				}
				$newTrObj.css("display","");
				$table.append($newTrObj);
			}
			//初始化字段值
			for(var j=0; j<mapObjs.length; j++){
				var mapObj = mapObjs[j];
				var mapId = mapObj.id;
				var paramId = mapObj.paramId;
				var fieldId = mapObj.fieldId;
				var fieldName = mapObj.fieldName;
				if(method == "view"){
					$table.find("[name='ruleSetParamId'][value='"+paramId+"']").eq(0).parents("tr.pivotRow").eq(0).find("[name='xformFieldId']").val(fieldId);
					$table.find("[name='ruleSetParamId'][value='"+paramId+"']").eq(0).parents("tr.pivotRow").eq(0).find("[name='xformFieldName']").val(fieldName);
				}else{
					$table.find("[name='ruleSetParamId'][value='"+paramId+"']").eq(0).parents("tr.pivotRow").eq(0).find("[name='xformField']").val(fieldId);
				}
				$table.find("[name='ruleSetParamId'][value='"+paramId+"']").eq(0).parents("tr.pivotRow").eq(0).find("[name='mapId']").val(mapId);
				//保存映射值
				var paramObj = this.getParamById(paramId,params);
				var fieldObj = this.getFieldById(fieldId,fields);
				var mapObj = {
					id:mapId,
					paramId:paramId,
					fieldId:fieldId,
					fieldName:fieldObj.label ? fieldObj.label : fieldName
				}
				this.saveMap(mapObj,i,fdKey);
			}
		}
		this.initAllMappsStatus = false;
	}
	
	function addRow(table,fdKey){
		DocList_AddRow(table);
		//获取唯一id
		var unid = this.getUnid();
		//参数id
		var rowCount = $("#"+table).children().children("tr").length - 1;
		$("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+(rowCount-1)+"].fdId']").val(unid);
		$("input.lastSysRuleTemplateEntryId").eq(rowCount-1).val(unid);
		
		this.newEntryIds.push(unid);
	}
	
	function updateDoc(mapId,formId,docId){
		var quoteInfos = this.quoteInfos;
		var quoteInfo = this.quoteInfos[formId] || [];
		var mapObj = this.getMapObjById(mapId);
		for(var i=0; i<quoteInfo.length; i++){
			var mapContent = quoteInfo[i];
			if(mapContent.mapEntryId === mapId){
				//该映射被使用过，要更新信息
				if(docId && docId != ""){
					//只更新id
					mapContent.docId = docId;
					mapContent.docVersion = "1";
					quoteInfo[i] = mapContent;
				}else{
					docId = mapObj.docId;
					if(docId && docId != ""){
						var beanName = 'sysRuleSetDocService&fdId='+docId;
						var docObj = new KMSSData().AddBeanData(beanName).GetHashMapArray()[0];
						mapContent.docId = docId;
						mapContent.docVersion = docObj.fdVersion;
						quoteInfo[i] = mapContent;
					}
				}
			}
		}
	}
	
	function selectRuleSet(fdKey){
		if(!fdKey){
			fdKey = this.fdKeys[0];
		}
		var index = getIndex()-1;
		var thisObj = this;
		//选择规则集后初始化参数
		var action = function(rtnData){
			if(!rtnData)
				return;
			var rtnVal = rtnData.GetHashMapArray()[0];
			if(!rtnVal){
				thisObj.resetRuleSet(fdKey,index);
				return;
			}
			//请求规则集规则使用的参数,每次都必须重新请求
			var beanName = "sysRuleSetDocService&fdId="+rtnVal.id+"&type=ruleParam";
			var params = new KMSSData().AddBeanData(beanName).GetHashMapArray();
			//保存对于的参数集
			thisObj.allParams[rtnVal.id] = params;
			//遍历添加到table中
			var $table = $("#ruleSetMap_"+fdKey).find("table.mapContent").eq(index);
			$tr = $table.find("tr.pivotRow").eq(0).clone(true);
			$table.find("tr.pivotRow").remove();

			for(var i=0; i<params.length; i++){
				var $newTrObj = $tr.clone(true);
				var param = params[i];
				//设置id
				var unid = thisObj.getUnid();
				$newTrObj.find("[name='mapId']").eq(0).val(unid);
				//设置参数
				$newTrObj.find("[name='ruleSetParamId']").eq(0).val(param.paramId);
				$newTrObj.find("[name='ruleSetParamName']").eq(0).val(param.paramName);
				//设置参数对应的字段
				var xfromFieldSelect = $newTrObj.find("[name='xformField']")[0];
				var fields = thisObj.getFields(fdKey);
				thisObj.setFieldOption(xfromFieldSelect, fields, param);
				$newTrObj.css("display","");
				$table.append($newTrObj);
			}
			var fdId = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdId']").val();
			thisObj.updateMapObjById(fdId, index, null,fdKey);//若存在则进行更新，不存在进行增加
			//更新引用该映射的位置的doc信息
			var formId = $("[name='sysRuleTemplateForms."+fdKey+".fdId']").val();
			thisObj.updateDoc(fdId,formId);
		}
		Dialog_Tree(
			false,
			'sysRuleTemplateForms.'+fdKey+'.sysRuleTemplateEntrys['+index+'].sysRuleSetDocId',
			'sysRuleTemplateForms.'+fdKey+'.sysRuleTemplateEntrys['+index+'].sysRuleSetDocName',
			null,
			'sysRuleSetDocService&parentId=!{value}&hasParam=true&isAuth=false',
			Data_GetResourceString("sys-rule:tree.node.title.sysRuleSetDoc"),
			false,
			action);
	}
	
	function resetRuleSet(fdKey,index){
		var thisObj = this;
		//清除数据
		var fdId = $("input.lastSysRuleTemplateEntryId").eq(index).val();
		if(!fdId){
			return;
		}
		//删除映射
		thisObj.delMapObjById(fdId);
		//删除映射表
		var $table = $("#ruleSetMap_"+fdKey).find("table.mapContent").eq(index);
		var $tr = $table.find("tr.pivotRow").eq(0).clone(true);
		$table.find("tr.pivotRow").remove();
		var $newTrObj = $tr.clone(true);
		$newTrObj.find("[name='mapId']").eq(0).val("");
		//设置参数
		$newTrObj.find("[name='ruleSetParamId']").eq(0).val("");
		$newTrObj.find("[name='ruleSetParamName']").eq(0).val("");
		//设置参数对应的字段
		var xfromFieldSelect = $newTrObj.find("[name='xformField']")[0];
		$(xfromFieldSelect).val("");
		thisObj.setFieldOption(xfromFieldSelect, [], {});
		$newTrObj.css("display","none");
		$table.append($newTrObj);
	}
	
	function createRuleSet(fdKey){
		if(!fdKey){
			fdKey = this.fdKeys[0];
		}
		var index = getIndex()-1;
		var dialog = new KMSSDialog();
		var thisObj = this;
		dialog.SetAfterShow(function(rtnData){
			var rtnVal = rtnData.GetHashMapArray()[0];
			//规则集的参数
			var params = rtnVal.params;
			//保存对于的参数集
			thisObj.allParams[rtnVal.id] = params;
			//更新当前映射
			var fdId = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdId']").val();
			thisObj.updateMapObjById(fdId, index, null,fdKey);
			//遍历添加到table中
			var $table = $("#ruleSetMap_"+fdKey).find("table.mapContent").eq(index);
			$tr = $table.find("tr.pivotRow").eq(0).clone(true);
			$table.find("tr.pivotRow").remove();
			
			for(var i=0; i<params.length; i++){
				var $newTrObj = $tr.clone(true);
				var param = params[i];
				//设置id
				var unid = thisObj.getUnid();
				$newTrObj.find("[name='mapId']").eq(0).val(unid);
				//设置参数
				$newTrObj.find("[name='ruleSetParamId']").eq(0).val(param.paramId);
				$newTrObj.find("[name='ruleSetParamName']").eq(0).val(param.paramName);
				//获取对于参数的映射字段
				var fieldObj = thisObj.getFieldByMapsAndParamId(rtnVal.maps, param.paramId);
				//设置参数对应的字段
				var xfromFieldSelect = $newTrObj.find("[name='xformField']")[0];
				var fields = thisObj.getFields(fdKey);
				thisObj.setFieldOption(xfromFieldSelect, fields, param, fieldObj.fieldId);
				$newTrObj.css("display","");
				$table.append($newTrObj);
				//保存映射值
				thisObj.updateMap(fieldObj.fieldId, xfromFieldSelect, fdKey, index);
			}
			//更新引用该映射的位置的doc信息
			var formId = $("[name='sysRuleTemplateForms."+fdKey+".fdId']").val();
			thisObj.updateDoc(fdId,formId,rtnVal.id);
		});
		dialog.parameters = {};
		dialog.parameters.fields = this.getFields(fdKey); 
		dialog.BindingField("sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].sysRuleSetDocId", "sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].sysRuleSetDocName");
		dialog.Window = window;
		dialog.URL = Com_Parameter.ContextPath + "sys/rule/sys_ruleset_doc/sysRuleSetDoc.do?method=simpleAdd";
		dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
	}
	
	function updateMap(value, obj, fdKey, index){
		if(!fdKey){
			fdKey = this.fdKeys[0];
		}
		//获取映射内容
		if(!index && index != 0){
			index = this.getElemIndex()-1;
		}
		var fieldId = value;
		var sysRuleSetDocId = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].sysRuleSetDocId']").val();
		var fdId = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdId']").val();
		var $parent = $(obj).parents("tr").eq(0);
		var paramId = $parent.find("[name='ruleSetParamId']").val();
		var mapId = $parent.find("[name='mapId']").val();
		//处理引用内容
		var formId = $("[name='sysRuleTemplateForms."+fdKey+".fdId']").val();
		var quoteInfo = this.quoteInfos[formId] || [];
		var fields = this.getFields(fdKey);
		var fieldObj = this.getFieldById(fieldId, fields);
		//处理映射内容
		if(value==""){
			//移除
			if(this.allMaps.length == 0){
				return;
			}
			var maps = [];
			if(this.getMapObjById(fdId)){
				var mapObj = this.getMapObjById(fdId);
				var mapObjStr = JSON.stringify(mapObj);
				mapObj = eval('('+mapObjStr+')');
				maps = mapObj.maps;
			}
			//处理映射内容
			for(var i=0; i<maps.length; i++){
				var map = maps[i];
				if(map.id == mapId){
					maps.splice(i,1);//移除该对象
				}
			}
			//重新设置
			this.updateMapObjById(fdId, index, maps, fdKey);
			//更新到页面
			$("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].content']").val(JSON.stringify(maps));
		}else{
			//添加
			var params = this.allParams[sysRuleSetDocId];
			var paramObj = this.getParamById(paramId, params);
			if(!mapId || mapId == ""){
				mapId = this.getUnid();
			}
			var obj = {
					id:mapId,
					paramId:paramObj.paramId,
					fieldId:fieldId,
					fieldName:fieldObj.label
			}
			this.saveMap(obj,index,fdKey);
		}
	}

	function saveMap(obj,index,fdKey){
		if(!fdKey){
			fdKey = this.fdKeys[0];
		}
		var fdId = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdId']").val();
		var mapId = obj.id;
		var paramId = obj.paramId;
		var fieldId = obj.fieldId;
		var mapObj = this.getMapObjById(fdId);
		var mapObjStr = JSON.stringify(mapObj);
		mapObj = eval('('+mapObjStr+')');
		var maps = [];
		if(mapObj.maps){
			maps = mapObj.maps;
		}
		if(maps.length > 0){
			//更新
			var isUpdate = false;
			for(var i=0; i<maps.length; i++){
				var map = maps[i];
				if(map.id == mapId){
					maps[i] = obj;
					isUpdate = true;
					break;
				}
			}
			if(!isUpdate){
				//需要增加
				maps.push(obj);
			}
		}else{
			//新增
			maps.push(obj);
		}
		//更新映射表
		this.updateMapObjById(fdId,index,maps,fdKey);
		//更新到页面
		$("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].content']").val(JSON.stringify(maps));
	}
	
	function recordFieldBeforeUpdateMap(obj){
		//记录当前操作的映射值
		this.currentMapFiledVal = $(obj).val();
	}

	function changeName(value, obj, fdKey){
		if(!fdKey){
			fdKey = this.fdKeys[0];
		}
		var index = getIndex()-1;
		//处理映射内容
		var fdId = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdId']").val();
		var fdName = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdName']").val();
		var allMaps = this.allMaps;
		for(var i=0; i<allMaps.length; i++){
			var mapObj = allMaps[i];
			if(fdId && mapObj.id == fdId){
				mapObj.name = fdName;
				break;
			}
		}
	}

	function reloadMap(tableName,labelIndex){
		var ruleTrs = $("#"+tableName).find("tr#sysRule_tab");
		if(ruleTrs.length > 0){
			// 获取规则机制页签的索引
			var lks_labelindex = $(ruleTrs[0]).attr('lks_labelindex');
			// 点击规则机制页签的时候才加载
			if(lks_labelindex != labelIndex){
				return true;
			}
			
			//获取行
			var ruleSetMapObjs = $(".ruleSetMap");
			for(var k=0; k<ruleSetMapObjs.length; k++){
				var $trObjs = $(ruleSetMapObjs[k]).find("tr:not(.tr_normal_title):not(.pivotRow)");
				for(var i=0; i<$trObjs.length; i++){
					var trObj = $trObjs[i];
					var docId = $(trObj).find("[name='sysRuleTemplateForms."+this.fdKeys[k]+".sysRuleTemplateEntrys["+i+"].sysRuleSetDocId']").val();
					var $mapTable = $(trObj).find("table.mapContent").eq(0);
					var $mapTrs = $mapTable.find("tr.pivotRow");
					var params = this.allParams[docId];//现在默认是加载时的参数，暂时切换页签不进行更新参数
					if(!params){
						continue;
					}
					for(var j=0; j<$mapTrs.length; j++){
						var mapTr = $mapTrs[j];
						var paramId = $(mapTr).find("input[name='ruleSetParamId']").val();
						var xformFieldSelect = $(mapTr).find("select[name='xformField']")[0];
						var value = $(xformFieldSelect).val();
						//设置参数对应的字段
						var param = this.getParamById(paramId,params);
						if(param){
							this.setFieldOption(xformFieldSelect, this.getFields(this.fdKeys[k]), param, value);
						}
					}
				}
			}
		}
	}

	function updateBeforeDel(fdKey){
		if(!fdKey){
			fdKey = this.fdKeys[0];
		}
		var index = getIndex()-1;
		var fdId = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdId']").val();
		//若该映射已经被使用，则不允许删除
		var formId = $("[name='sysRuleTemplateForms."+fdKey+".fdId']").val();
		var quoteInfo = this.quoteInfos[formId] || [];
		var allMaps = this.allMaps;
		for(var i=0; i<quoteInfo.length; i++){
			var mapContent = quoteInfo[i];
			if(mapContent.isChange){
				continue;
			}
			if(mapContent.mapEntryId){
				if(mapContent.mapEntryId == fdId){
					alert(Data_GetResourceString("sys-rule:sysRuleTemplate.validate.2"));
					return false;
				}
			}
		}
		this.delMapObjById(fdId);
		return true;
	}

	function updateMapObjById(id, index, maps,fdKey){
		var fdName = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdName']").val();
		var sysRuleSetDocId = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].sysRuleSetDocId']").val();
		var sysRuleSetDocName = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].sysRuleSetDocName']").val();
		var allMaps = this.allMaps;
		var isUpdate = false;
		for(var i=0; i<allMaps.length; i++){
			var mapObj = allMaps[i];
			if(mapObj.id == id){
				if(this.newEntryIds.indexOf(id) != -1 || mapObj.isAdd || mapObj.isNew || this.initAllMappsStatus == true){//初始化时也不进行更新操作
					mapObj.name = fdName;
					mapObj.docId = sysRuleSetDocId;
					mapObj.docName = sysRuleSetDocName;
					mapObj.key = fdKey;
					mapObj.maps = maps || [];
					return;
				}else{
					//若存在，则更新一个标志
					mapObj.isUpdate = true;
					mapObj.isChange = true;
					isUpdate = true;
					break;
				}
			}
		}
		var mapObj = {
				id:id,
				name:fdName,
				docId:sysRuleSetDocId,
				docName:sysRuleSetDocName,
				key:fdKey,
				maps:maps || []
		}
		//创建一个新的对象
		if(isUpdate){
			var unid = this.getUnid();
			mapObj.id = unid;
			mapObj.isNew = true;//代表是更新新增的对象
			$("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].fdId']").val(unid);
			//更新引用的id
			var formId = $("[name='sysRuleTemplateForms."+fdKey+".fdId']").val();
			var quoteInfo = this.quoteInfos[formId] || [];
			for(var i=0; i<quoteInfo.length; i++){
				var mapContent = quoteInfo[i];
				if(mapContent.isChange){
					continue;
				}
				if(mapContent.mapEntryId == id){
					mapContent.mapEntryId = unid;
				}
			}
			//更新maps的ID
			for(var i=0; i<mapObj.maps.length; i++){
				var map = maps[i];
				map.id = this.getUnid();
				//更新mapid到页面，保证后续操作正常
				var $mapTd = $("[name='sysRuleTemplateForms."+fdKey+".sysRuleTemplateEntrys["+index+"].content']").parents("td").eq(0);
				$mapTd.find("input[name='mapId']").eq(i).val(map.id);
			}
		}
		if(!isUpdate && this.newEntryIds.indexOf(id) != -1){
			mapObj.isAdd = true;
		}
		allMaps.push(mapObj);
		this.allMaps = allMaps;
	}

	function getMapObjById(id){
		var allMaps = this.allMaps;
		for(var i=0; i<allMaps.length; i++){
			var mapObj = allMaps[i];
			if(id && mapObj.id == id){
				return mapObj;
			}
		}
		return {};
	}

	function delMapObjById(id){
		var allMaps = this.allMaps;
		for(var i=0; i<allMaps.length; i++){
			var mapObj = allMaps[i];
			if(id && mapObj.id == id){
				if(mapObj.isAdd || mapObj.isNew){
					allMaps.splice(i,1);
				}else{
					mapObj.isDel = true;
					mapObj.isChange = true;
				}
				break;
			}
		}
	}

	function getElemIndex(){
		var childTable = DocListFunc_GetParentByTagName("TABLE");
		var row = DocListFunc_GetParentByTagName("TR", childTable);
		var table = DocListFunc_GetParentByTagName("TABLE",row);
		for(var i=1; i<table.rows.length; i++){
			if(row && table.rows[i]==row){
				return i;
			}
		}
		return -1;
	}

	function getParamById(paramId,params){
		for(var i=0; i<params.length; i++){
			var param = params[i];
			if(paramId && param.paramId == paramId){
				return param;
			}
		}
		return {};
	}

	function getFieldById(fieldId,fields){
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			if(fieldId && field.name == fieldId){
				return field;
			}
		}
		return {};
	}

	function getFieldByMapsAndParamId(maps, paramId){
		var result;
		maps.forEach(function(map, index){
			if(paramId && map.paramId == paramId){
				var fieldObj = {};
				fieldObj.fieldId = map.fieldId;
				result = fieldObj;
			}
		})
		return result;
	}

	function delInvalidData(datas){
		if(!datas){
			return;
		}
		if(!(Object.prototype.toString.call(datas) === '[object Array]')){
			//不为数组，转换成数组
			datas = [datas];
		}
		for(var k=0; k<this.fdKeys.length; k++){
			var fdId = $("[name='sysRuleTemplateForms."+this.fdKeys[k]+".fdId']").val();
			var quoteInfo = this.quoteInfos[fdId] || [];
			for(var i=0; i<datas.length; i++){
				var data = datas[i];
				if(data.sysRuleQuoteInfoId){
					for(var j=0; j<quoteInfo.length; j++){
						var mapContent = quoteInfo[j];
						if(data.sysRuleQuoteInfoId == mapContent.id){
							if(mapContent.isAdd || mapContent.isNew){//历史数据不删除
								quoteInfo.splice(j,1);
							}else{
								//不再删除，而是添加删除的标志
								mapContent.isDel = true;
								mapContent.isChange = true;
							}
							break;
						}
					}
				}
			}
		}
	}

	function writeData(){
		for(var i=0; i<this.fdKeys.length; i++){
			var fdId = $("[name='sysRuleTemplateForms."+this.fdKeys[i]+".fdId']").val();
			var quoteInfo = this.quoteInfos[fdId];
			if(quoteInfo){
				$("[name='sysRuleTemplateForms."+this.fdKeys[i]+".quoteInfo']").val(JSON.stringify(quoteInfo));
			}
		}
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
	
	function getFields(fdKey){
		//处理key 和 modelName
		if(!fdKey){
			fdKey = this.fdKeys[0];
		}
		for(var i=0; i<this.fdKeys.length; i++){
			if(fdKey == this.fdKeys[i]){
				modelName = this.modelNames[i];
				break;
			}
		}
		
		var fields = null;
		try {  
			if(fdKey){
				//带表单
				var funcName = 'XForm_getXFormDesignerObj_' + fdKey
				if(typeof(eval(funcName))=="function"){  
					var func=eval(funcName);
					fields = func();  
				}  
			}
		}catch(e){  
			//不带表单，获取model字段
			if(modelName){
				fields = new KMSSData().AddBeanData("sysFormulaDictVarTree&modelName="+modelName).GetHashMapArray();
			}
		}   
		return fields || [];
	}
	
	//为了解决edge浏览器子窗口中创建的对象无法正常保存到父窗口而特别加的克隆对象到节点属性的方法
	function addObjectData(attr,obj){
		if (typeof obj == "object" && obj != null) {
			this[attr] = this.objClone(obj);
		} else {
			this[attr] = obj;
		}
	}

	function objClone(obj) {
		// edge浏览器下优先通过length属性来判断obj是否为数组
		var o;
		if(obj.length || obj.length == 0){
			o = [];
		}else{
			o = {};
		}
		//var o = obj.length ? [] : {};
		for (var k in obj) {
			 o[k] = typeof obj[k] == "object" ? objClone(obj[k]) : obj[k];
		}
		return o;
	}
	
	function getUnid(){
		if(this.unids.length <= 1){
			//重新加载
			this.unids = Data_GetRadomId(200);
		}
		var unid = this.unids[0];
		this.unids.splice(0,1);
		return unid;
	}
})();