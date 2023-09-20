
function Relation_Convert_HTML_ForHTML(str){
	if(!str) return str;
	str= str.replace(/&amp;/g, "&");
	str= str.replace(/&#34;/g, "\"");
	str= str.replace(/&#39;/g, "\'");
	return str;
}
function RelationRuleRun(bindDomId,bindEvent,destDomIdsStr,opsStr,assemblyId){
	var self=this;
	if(/-fd(\w+)/g.test(bindDomId)){
		bindDomId = bindDomId.match(/(\S+)-/g)[0].replace("-","");
	}
	this.bindDomId=bindDomId;
	this.bindEvent=bindEvent;
	this.ops=[];
	this.bindStr = document.getElementById(bindDomId)?"#" + bindDomId:'[name*=' + bindDomId + ']';

	//执行操作
	this.doOpts=function(area){
		var	destIds = destDomIdsStr.split(";");
		var ops=JSON.parse(opsStr.replace(/quot;/g,"\""));
		//遍历条件和操作
		for(var j=0;j<ops.length;j++){
			var op=ops[j];
			var condition=op.cndId;
			//将条件中的参数值替换为实际值
			condition=condition.replace(/\$(\w+)\$/g,function($1,$2){
				var val=GetXFormFieldValueById_ext($2,true);
				if(val==""){
					if(xform_data_hide&&xform_data_hide[$2]){
						val=xform_data_hide[$2];
					}
					if(val=='null'||val==null||val=='undefined'){
						val="";
					}
				}
				if(isNumber(val)){
					return  Number(val);
				}
				return "'"+val+"'";
			});
			var isContinue = false;
			//处理明细表值替换
			condition=condition.replace(/\$(\w+.\w+)\$/g,function($1,$2){
				if($2.indexOf(".")>=0 && area){
					var $table = $(area).closest("[fd_type='detailsTable']");
					if ($table.length === 0) {
						$table = $(area).closest("[fd_type='seniorDetailsTable']");
					}
					var titleRow = $table.find("[type='titleRow']").length;
					var getRowIndex = function(tObj,rObj){
						for(var i = 0;i<tObj.rows.length;i++){
							if(tObj.rows[i]==rObj){
								return i;
							}
						}
						return 0;
					}
					var rowIndex = getRowIndex($table[0],$(area)[0]) - titleRow;
					$2 = $2.split(".")[0]+'.'+rowIndex+'.'+$2.split(".")[1];
					var val=GetXFormFieldValueById_ext($2,true);
					if(val==""){
						if(xform_data_hide&&xform_data_hide[$2]){
							val=xform_data_hide[$2];
						}
						if(val=='null'||val==null||val=='undefined'){
							val="";
						}
					}
					if(isNumber(val)){
						return  Number(val);
					}
					return "'"+val+"'";
				}else{
					var tempArray = assemblyId.split(".");
					if(tempArray !=null && tempArray.length == 3 ){
						var tempIndex=Number(tempArray[1]);

						if(typeof tempIndex === 'number' && !isNaN(tempIndex)){
							var table =$("#TABLE_DL_"+tempArray[0]);
							if(table.length>0 && table[0].rows){
								self.doOpts($(table[0].rows[tempIndex+1]));
							}
						}
					}

					isContinue = true;
				}
				return $2;
			});
			if(isContinue){
				continue;
			}
			var condition = Relation_Convert_HTML_ForHTML(condition);
			//如果不条件成立
			var isTrue = true;
			try{
				if(!eval(condition)){
					isTrue = false;
				}
			}catch(e){//排除一种情况：若触发控件没有明细表控件，但是条件选择了明细表控件，可能会出错
				isTrue = false;
			}
			if(!isTrue){
				continue;
			}
			//遍历所有目标控件
			for(var i=0;i<destIds.length;i++){
				var destDomId=destIds[i];
				var attrObj;
				var controlId;
				//明细表
				if(destDomId.indexOf(".")>=0){
					var detailTableId  =destDomId.split(".")[0];
					var filedId=destDomId.split(".")[1];
					var $xformflag;
					if(area){
						$xformflag = $(area).find("xformflag[flagid*="+filedId+"]");
					}else{
						$xformflag = $("xformflag[flagid*="+filedId+"]");
					}
					$xformflag.each(function(){
						//模板行不处理
						if($(this).attr("flagid").indexOf("!{index}")>=0){
							return;
						}
						controlId = Relation_Rule_ParseControlId($(this));
						// 由于明细表中的部分控件存在动态赋值，影响了属性变更控件的赋值，所以这里对明细表中的所有控件进行延迟处理（主表暂时没发现问题所以没延迟处理）
						var tempOp=op;
						var tempControlId=controlId;
						setTimeout(
							function(){
								self.setTimeoutExeOpt(tempControlId,tempOp)},100);
					});
					//非明细表处理
				}else{
					controlId = Relation_Rule_ParseControlId(destDomId);
					attrObj = new Relation_Rule_ControlAttrChange(controlId);
					self.exeOpt(attrObj,op);
				}
			}
		}
	};
	//延迟执行
	this.setTimeoutExeOpt = function(controlId,op){
		attrObj = new Relation_Rule_ControlAttrChange(controlId);
		self.exeOpt(attrObj,op);
	};

	//判断是否为number
	isNumber = function (value){
		var number =  Number.isNaN || function(value) {
			return  (typeof value) === 'number' && window. isNaN(value);
		}
		return !number(Number(value));
	}

	this.exeOpt = function(attrObj,op){
		if(attrObj.baseField.field){
			//被权限区段包裹的控件不处理，编辑只读
			if(!attrObj.isRightControl()){
				attrObj.readonly(op.readonly);
			}
			if(op.required){
				attrObj.required(op.required);
			}
			if(op.display){
				attrObj.display(op.display);
			}
		}
	}

	this.init=function(){
		// 如果是在明细表里面，处理id
		if(bindDomId.indexOf(".")>=0){
			bindDomId = bindDomId.substr(bindDomId.lastIndexOf(".") + 1);
		}
		var destIds=destDomIdsStr.split(";");
		//把明细表相关的目标控件取出来。明细表行增加的时候需要触发对应明细表的 操作，与该明细表无关的不触发
		var tableIdMapControls={};
		for(var i=0; i<destIds.length;i++){
			if(destIds[i].indexOf(".")<0){
				continue;
			}
			var tableId=destIds[i].split(".")[0];
			if(tableIdMapControls[tableId]){
				tableIdMapControls[tableId].push(destIds[i]);
			}
			else{
				tableIdMapControls[tableId]=[destIds[i]];
			}

		}
		//表格行追加的时候 需要执行校验，只处理新增行，提高性能
		for(var tableId in tableIdMapControls){
			$(document).on('table-add','table[showStatisticRow][id$='+tableId+']',function(e,row){
				self.doOpts(row);
			});
		}
		$(document).on("exceRelationRule",function(e,row){
			if(!self.isDetailTable(row)){
				row = self.getDetailTr(row);
			}
			self.doOpts(row);
		});
	};
	this.init();

	//获取某一行所在的父元素（明细表行）
	this.getDetailTr = function(row){
		var tableObj = XForm_GetDetailsTable(row);
		if(tableObj){
			var detailTrs = $(tableObj).find(">tbody>tr");
			for(var i=0; i<detailTrs.length; i++){
				if($(detailTrs[i]).find(row).length > 0){
					return detailTrs[i];
				}
			}
		}
		return null;
	}

	this.isDetailTable = function(row){
		if(row){
			var tableObj = XForm_GetDetailsTable(row);
			if(tableObj && $(row).parents("table")[0] == tableObj){
				return true;
			}
		}
		return false;
	};

	//监听change事件，支持地址本，动态控件 
	if(AttachXFormValueChangeEventById&&this.bindEvent=='change'){
		if ($("[xform_type][id='_" + bindDomId + "']").length > 0 || $("[_xform_type][id='_xform_" + bindDomId + "']").length > 0) { // 兼容属性变更作为触发控件
			var _controlId = $("[xform_type][id='_" + bindDomId + "']").attr("valfield") || bindDomId;
			var _bindStr = document.getElementById(_controlId)?"#" + _controlId:'[name*=' + _controlId + ']';
			$(document).on(this.bindEvent,this._bindStr,function(){
				self.doOpts();
			});
		} else {
			AttachXFormValueChangeEventById(bindDomId,function(value,dom){
				//获取对应的行
				var trObj = $(dom).parents("tr:eq(0)")[0] || null;
				if(!self.isDetailTable(trObj)){
					trObj = self.getDetailTr(trObj);
				}
				//延时加载，等待change事件之后执行。
				setTimeout(function(){
					self.doOpts(trObj)
				},10);
				//self.doOpts(trObj);
			});
		}
	}
	//自定义事件
	else if('relation_rule_event'==bindEvent){
		$(document).bind(bindEvent,function(event,param1){
			if(param1==bindDomId){
				var target = event.target;
				//获取对应的行
				var trObj = null;
				var isRun = false;
				if(param1 && GetXFormFieldById){
					var doms = GetXFormFieldById(param1,true);
					if(doms.length > 1){//自定义事件中，目标控件是整个明细表
						for(var i=0; i<doms.length; i++){
							isRun = true;
							trObj = $(doms[i]).parents("tr:eq(0)")[0] || null;
							if(!self.isDetailTable(trObj)){
								trObj = self.getDetailTr(trObj);
							}
							self.doOpts(trObj);
						}
					}else if(doms.length == 1){
						trObj = $(doms[0]).parents("tr:eq(0)")[0] || null;
						if(!self.isDetailTable(trObj)){
							trObj = self.getDetailTr(trObj);
						}
					}
				}
				if(!isRun)
					self.doOpts(trObj);
			}
		});
	}
	else{
		//监听绑定的事件，触发动作
		$(document).on(this.bindEvent,this.bindStr,function(){
			self.doOpts();
		});
	}

	//加载时 初始化
	Com_AddEventListener(window,'load',function () {
		Relation_Rule_Ready.call(self);
	});

	/** 监听高级明细表 */
	$(document).on("detailsTable-init", function(e, tbObj){
		Relation_Rule_Ready.call(self);;
	});
}

function Relation_Rule_Ready() {
	//动态单选和动态多选 不支持 加载时初始化
	// 附件机制需要初始化，把变更属性任务放到后面
	var self = this;
	setTimeout(function(){
		self.doOpts()
	},100);
}

function Relation_Rule_ParseControlId(xformflag){
	var controlId = xformflag;
	if(typeof(xformflag) == 'string'){
		xformflag = $("xformflag[flagid*="+ xformflag +"]");
	}
	var id = xformflag.attr("flagid");
	if (!id) {
		id = controlId;
		if (typeof controlId == 'string' && xformflag.length == 0) {
			var $wrap = $("[xform_type][id='_" + controlId + "']");
			id = $wrap.attr("valField") || controlId;
		}
	}
	// 地址本的需要获取到xxx.id
	var a = /\(([^()]+)\)/g.exec(xformflag.attr("id"));
	if(a && a.length > 1){
		//明细表的需要处理索引的问题 
		if(/\.(\d+)\./g.test(id)){
			var index = id.match(/\.(\d+)\./g);
			id = a[1].replace(/\.(\d+)\./g,index);
		}else{
			id = a[1];
		}
	}
	return id;
}

function Relation_Rule_ControlAttrChange(objflag){
	this.baseField = $form(objflag);
	__fixMaxLevel(this.baseField);
//	this.owner=(function(){
//		return $(objflag).find("[name*='" + objflag + "']");
//	})();
	var self=this;
	//判断objflag是不是包含在权限区段之中
	this.isRightControl=function(){
		var $dom;
		if(this.baseField.target.element){
			$dom = $(this.baseField.target.element);
		}else{
			$dom = $(this.baseField.target.root);
		}
		return $dom.parents("xformflag[flagtype='xform_right']").length>0;
	}
	this.isFlagTrue=function(flag){
		if(flag=='1'||flag==true) return true;
		return false;
	}
	this.isFlagFalse=function(flag){
		if(flag=='0'|| flag==false) return true;
		return false;
	}
	this.isRowFlagTrue=function(flag){
		if(flag=='21') return true;
		return false;
	}
	this.isRowFlagFalse=function(flag){
		if(flag=='20') return true;
		return false;
	}
	this.display=function(flag){
		if(self.displayRow(flag)){
			return;
		}
		if(self.isFlagTrue(flag)){
			this.baseField.display(true);
		}else if(self.isFlagFalse(flag)){
			this.baseField.display(false);
		}
	};
	this.displayRow=function(flag){
		if(self.isRowFlagTrue(flag)){
			if(this.baseField.target.element){
				$(this.baseField.target.element).parents("tr:first").show();
				var rowspan = this.baseField.target.element.closest("td").attr("rowspan");
				if(rowspan && parseInt(rowspan)>1){
					$(this.baseField.target.element).parents("tr:first").nextAll().each(function(index,dom){
						if(index<parseInt(rowspan)-1){
							$(dom).show();
						}
					})
				}
			}else{
				$(this.baseField.target.root).parents("tr:first").show();
			}
			return true;
		}else if(self.isRowFlagFalse(flag)){
			if(this.baseField.target.element){
				$(this.baseField.target.element).parents("tr:first").hide();
				var rowspan = this.baseField.target.element.closest("td").attr("rowspan");
				if(rowspan && parseInt(rowspan)>1){
					$(this.baseField.target.element).parents("tr:first").nextAll().each(function(index,dom){
						if(index<parseInt(rowspan)-1){
							$(dom).hide();
						}
					})
				}
			}else{
				$(this.baseField.target.root).parents("tr:first").hide();
			}
			return true;
		}
		return false;
	};
	this.required=function(flag){
		if(self.isFlagTrue(flag)){
			this.baseField.required(true);
		}else if(self.isFlagFalse(flag)){
			this.baseField.required(false);
		}
	};
	this.readonly=function(flag){
		if(self.isFlagTrue(flag)){
			this.baseField.readOnly(true);
		}else if(self.isFlagFalse(flag)){
			this.baseField.readOnly(false);
		}
	};
}

/*
判断控件是否套了权限区段并且是权限只读，若是，则返回false，不可以修改MaxLevel，否则返回true，可以修改MaxLevel
只针对muiTab里边控件的情况，所以别在其他位置使用该方法（注意）
 */
function isChangeMaxLevelWhenXformRightAndReadOnly(baseField){
	debugger;
	var $dom,rightStatus;
	if(baseField.target.element){
		$dom = $(baseField.target.element).parents("xformflag[flagtype='xform_right']");
		rightStatus = $(baseField.target.element).parents("xformflag").eq(0).attr("data-right");
	}else{
		$dom = $(baseField.target.root).parents("xformflag[flagtype='xform_right']");
		if(baseField.field){
			var elements = $("[name='"+baseField.field+"']",baseField.target.root);
			if(elements.length > 0){
				rightStatus = $(baseField.target.root).attr("data-right");
			}
		}
	}
	if($dom.length>0 && rightStatus == "view") {
		return false;
	}
	return true;
}

function __fixMaxLevel(baseField) {
	if(baseField && baseField.target.element){
		/*if(!baseField.target.element.is(":visible") && baseField.target.cache.maxLevel == 1 && baseField.target.element.closest("[fd_type='mutiTab']").length > 0 && isChangeMaxLevelWhenXformRightAndReadOnly(baseField)){
			baseField.target.cache.maxLevel = 2;
		}*/
		if(!(baseField.target.element.prop("disabled") || baseField.target.element.prop("readOnly")|| !baseField.target.element.is(":visible")) && baseField.target.cache.maxLevel == 1){
			if(baseField.target.type === "textarea") {
				var isReadOnly = $("[name='_" + baseField.target.field + "']").attr("isreadonly");
				if (isReadOnly === "true") {
					return;
				}
			}
			baseField.target.cache.maxLevel = 2;
		}
		if(baseField.target.cache.maxLevel == 0 && baseField.target.root && baseField.target.root.attr('flagtype') == "xform_calculate") {
			baseField.target.cache.maxLevel = 1;
		}
		if(baseField.target.cache.maxLevel == 0 && baseField.target.root && baseField.target.root.attr('flagtype') == "xform_select") {
			baseField.target.cache.maxLevel = 1;
		}
		if(baseField.target.cache.maxLevel == 0 && baseField.target.root && baseField.target.root.attr('flagtype') == "xform_relation_select" && baseField.target.element && baseField.target.element[0].type=='hidden') {
			baseField.target.cache.maxLevel = 1;
		}
		if(baseField.target.cache.maxLevel == 0 && baseField.target.root && baseField.target.root.attr('flagtype') == "relevance") {
			baseField.target.cache.maxLevel = 2;
		}
		if(baseField.target.cache.maxLevel == 1 && baseField.target.root && baseField.target.root.attr('flagtype') == "xform_fSelect" && baseField.target.element &&  !baseField.target.element[0].type != 'hidden' &&  baseField.target.display && baseField.target.display[0].type=='hidden') {
			baseField.target.cache.maxLevel = 2;
		}
		if(baseField.target.cache.maxLevel == 0 && baseField.target.root && baseField.target.root.attr('flagtype') == "xform_fSelect") {
			baseField.target.cache.maxLevel = 2;
		}
		if(baseField.target.cache.maxLevel == 0 && baseField.target.root && baseField.target.root.attr('flagtype') == "detailSummary") {
			baseField.target.cache.maxLevel = 2;
		}
		//动态多选，动态单选实际值和显示值同时必填时，让其必填element指向同一处
		if(baseField.target.cache.maxLevel == 2 && baseField.target.root && (baseField.target.root.attr('flagtype') == "xform_relation_radio"||baseField.target.root.attr('flagtype') == "xform_relation_checkbox")) {
			var flagtype = baseField.target.root.attr('flagtype');
			baseField.target.labelElement = baseField.target.root.find("div."+flagtype+">div label");
		}
	}
}

