/**
 * 
 */
(function(){
	/****************************SQLRowStats start ********************************/
	/**
	 * 行统计
	 * 
	 */
	function SQLRowStats(sqlStructure,condition,key){
		this.sqlStructure = sqlStructure ? sqlStructure : {};
		this.domNode;
		this.block = "rowstats";
		
		this.key = key; // sqlStructure对应改组件的key
		
		this.defaultFieldLabel = "fieldLabel";
		this.defaultFieldName = "fieldName";
		this.defaultFormalText = "formalText";
		this.defaultFormalValue = "formalValue";
		this.defaultFormatName = "format";
		this.defaultFormatValName = "formatVal";
		this.defaultSortName = "sort";
				
		this.fieldLabel; // 列标题
		this.fieldName; // 列名
		this.formalText; // 公式文本
		this.formalValue; // 公式值
		this.formatComponent; // 数据格式
		this.sortComponent; // 排序方式
		
		this.init = _SQLRowStats_Init;
		this.clear = SQLRowStats_Clear;
		this.createTrNode = SQLRowStats_CreateTrNode;
		this.updateElement = SQLRowStats_UpdateElement;
		this.updateSortElement = SQLRowStats_UpdateSortElement;
		this.updateFormatElement = SQLRowStats_UpdateFormatElement;
		this.getKeyData = SQLRowStats_GetKeyData;
		this.isValid = SQLRowStats_IsValid;
		
		// 兼容配置模式
		this.transferData = SQLRowStats_TransferData;
		
		this.rowDefineChange = SQLRowStats_RowDefineChange;

		this.init(condition);
	}

	function _SQLRowStats_Init(condition){
		var self = this;
		var $table = self.sqlStructure.getComponentByKey(self.key)["dom"];
		
		var config = {
				"currentModelName" : self.sqlStructure.dataSource.modelName,
				"elementName" : self.defaultPropertyName,
				"structureBlock" : self.block
		};
		if(condition && condition.field){
			config.field = condition.field;
		}

		var formatConfig = {};
		formatConfig.name = self.defaultFormatName;
		formatConfig.valName = self.defaultFormatValName;
		if(condition && condition.format){
			var format = condition.format;
			for(o in format){
				formatConfig[o] = format[o];
			}
		}
		self.formatComponent = new SQLComponent_ValueUnion(self,formatConfig);

		// 排序方式
		var sortConfig = {};
		sortConfig.name = self.defaultSortName;
		if(condition && condition.sort){
			var sort = condition.sort;
			for(o in sort){
				sortConfig[o] = sort[o];
			}
		}
		self.sortComponent = new SQLComponent_Select(self,sortConfig);
			
	}

	function SQLRowStats_CreateTrNode(value){
		var $tr = $("<tr>");
		$tr.addClass("dbEcharts_Configure_Table_Tr");
		var self = this;
		self.domNode = $tr;
		var val ="";
		if(value && value.fieldLabel){
			val=value.fieldLabel;
			self.fieldLabel= val;
		}
		var $td = $("<td class='fieldLabelTd'/>");
		var $fl = $("<input type='text' class='inputsgl' size='15' name='"+ self.defaultFieldLabel +"'  value='"+val+"'/>");
		$fl.on("change",function(e){
			self.rowDefineChange("change");
		});
		$td.append($fl);
		$tr.append($td);

		val ="";
		if(value && value.fieldName){
			val=value.fieldName;
		}
		$td =  $("<td class='fieldNameTd'/>");
		$fl = $("<input type='text' class='inputsgl' size='15' name='"+ self.defaultFieldName +"'  value='"+val+"'/>");
		$fl.on("change",function(e){
			self.rowDefineChange("change");
		});
		$td.append($fl);
		$tr.append($td);

		// 公式
		var valText ="",valValue="";
		if(value && value.formalText){
			valText=value.formalText;
			valValue=value.formalValue;
		}

		var html = $("<td class='formalTd'/>");
		html.append("<input type='text' class='inputsgl' size='25' name='"+ self.defaultFormalText +"'   value='"+valText+"'/>");
		html.append("<input type='hidden' name='"+ self.defaultFormalValue +"'   value='"+valValue+"'/>");
		var $formula = $("<span class='highLight'><a href='javascrip:void(0);'>选择</a></span>");
		$formula.on("click",function(e){
			var self = this;
			var fieldList = getFieldList4Formual();
			var ide = $(self).parent().find("input[name='formalValue']");
			var texte = $(self).parent().find("input[name='formalText']");
			Formula_Dialog(ide, texte, fieldList, "Object", null, null ,null,null, null);
		});
		html.append($formula);
		$tr.append(html);
		
		$td = $("<td class='formatTd' align='center'></td>");
		$tr.append($td);
	
		$td = $("<td class='sortTd'></td>");
		$tr.append($td);
		$td = $("<td class='opTd'></td>");
		$tr.append($td);
	
		// 添加删除行按钮
		var $del = $("<a href='javascript:void(0);' style='color:#1b83d8;'>"+ DbcenterLang.del +"</a>");
		$del.on('click',function(){
			// 删除当前行和对象
			$tr.remove();
			self.sqlStructure.spliceArray(self.sqlStructure.getComponentByKey(self.key)["arr"],self);
			self.rowDefineChange("delete");
		});
		$tr.find(".opTd").append($del);

		// 添加上移下移按钮
		var $moveUp = $("<a href='javascript:void(0);' style='color:#1b83d8;'> "+ DbcenterLang.moveUp +"</a>");
		var $moveDown = $("<a href='javascript:void(0);' style='color:#1b83d8;'> "+ DbcenterLang.moveDown +"</a>");
		$tr.find(".opTd").append($moveUp).append($moveDown);

		// 切换当前行和对象的位置
		$moveUp.on('click', function () {
			if ($tr.index() > 1) {
				self.sqlStructure.swapArray(self.sqlStructure.getComponentByKey(self.key)["arr"], $tr.index() - 1, $tr.index() - 2);
				$tr.insertBefore($tr.prev());
			}
		});
		$moveDown.on('click', function () {
			if ($tr.index() + 1 < $tr.parent().find("tr").length) {
				self.sqlStructure.swapArray(self.sqlStructure.getComponentByKey(self.key)["arr"], $tr.index() - 1, $tr.index());
				$tr.insertAfter($tr.next());
			}
		});

		self.updateElement();
		return $tr;
	}

	function SQLRowStats_GetKeyData(){
		var data = {};
		var self = this;
		data.fieldLabel =$(self.domNode.find("input[name='fieldLabel']")).val();
		data.fieldName =$(self.domNode.find("input[name='fieldName']")).val();
		data.formalText =$(self.domNode.find("input[name='formalText']")).val();
		data.formalValue =$(self.domNode.find("input[name='formalValue']")).val();
		data.format = self.formatComponent.getKeyData();
		data.sort = self.sortComponent.getKeyData();
		return data;
	}
	
	function SQLRowStats_IsValid(){
		var self = this;
		var flag = true;
		return flag;
	}
	
	function SQLRowStats_UpdateElement(){
		var self = this;
		self.updateFormatElement();
		self.updateSortElement();
		self.rowDefineChange("change");
	}
	
	function SQLRowStats_RowDefineChange(type){
		var self = this;
		type = type || "change";
		self.fieldComponent = {};
		var fl = self.domNode.find("input[name='fieldLabel']");
		var fn = self.domNode.find("input[name='fieldName']");
		if(fl.val()!="" && fn.val()!=""){
			self.fieldComponent.text = fl.val();
			self.fieldComponent.val = fn.val();
			// 更新列表视图的配置
			$(document).trigger($.Event("SQLStructure_SelectComponent_change"),{"component":self,"type":type});
			// 更新列定义选项
			$(document).trigger($.Event("SQLStructure_RowStatsRowUpdate"),{"component":self,"type": type});
		}
	}
	

	// 更新排序方式
	function SQLRowStats_UpdateSortElement(){
		var self = this;
		var $sortTd = self.domNode.find(".sortTd");
		$sortTd.empty();
		//self.sortComponent.clear();
		var sortJson = JSONComponent.findItemByPath(self.block).sort;
		if(sortJson && sortJson.length > 0){
			$sortTd.append(self.sortComponent.buildByOptions(sortJson,function(component){
				component.domNode.on("change",function(){
					var val = $(this).find("option:selected").val();
					component.val = val;
				});
			}));		
		}
	}

	// 更新格式元素
	function SQLRowStats_UpdateFormatElement(){
		var self = this;
		var type = "Double";
		var $formatTd = self.domNode.find(".formatTd");
		$formatTd.empty();
		self.formatComponent.clear();
		if(type != ""){
			var path = self.block + "." + type;
			var item = JSONComponent.findItemByPath(path);
			if(item){
				// 只有找到对应的选项才构建元素
				self.formatComponent.JSONItem = item;
				var dom = self.formatComponent.buildNodeByJSONNode(function(component){
					component.domNode.on("change",function(){
						var val = $(this).find("option:selected").val();
						component.val = val;
						$(this).nextAll().remove();
						var item = component.findOptionByJSONNode(val);
						if(item){
							if(item.type){
								var config = {};								
								config.type = item.type;
								config.name = self.defaultFormatValName;
								var persistenceVal = self.formatComponent.fieldVal;
								if(persistenceVal.hasValue){
									config.val = persistenceVal.data;
								}
								var html="";
								html += "<div class='component' style='display:inline-block;'>";
								html += "<input type='text' class='inputsgl' size='6' name='"+ config.name +"'";
								html += " value='"+ _JSONComponent_GetValByName(config.val,config.name) +"'";
								html += " />";
								$(this).after(html);
								// 初始化完毕之后，清空原有值
								component.fieldVal = {};
							}
						}else{
							console.error("select组件找不到对应的值："+ val);
						}
					});
				});
				$formatTd.append(dom);
				self.formatComponent.domNode.change();	
			}
		}
	}

	function _JSONComponent_GetValByName(arr,name,key){
		var val = '';
		var keyAtt = "value";
		if(typeof(key) != 'undefined'){
			keyAtt = key;
		}
		if(!arr){
			return val;
		}
		for(var i = 0;i < arr.length;i++){
			if(name == arr[i].name){
				val = arr[i][keyAtt];
				break;
			}
		}
		return val;
	}

	// 兼容配置模式所需要做的兼容
	function SQLRowStats_TransferData(data){
		var rs = {};
		if(data){
			rs.label = data.fieldLabel; // 列名
			rs.key = data.fieldName; // 字段名
			rs.expressionValue =data.formalValue;
			rs.expressionText =data.formalText;
			rs.sort = data.sort.val;//排序方式
			var format = data.format.val;// 数据格式
			var argument="";
			if(format =="!{point}"){
				format = "point";
				if(data.format.fieldVal){
					if(data.format.fieldVal.data){
						argument = data.format.fieldVal.data[0].value;
					}
				}
			}
			if(format=="!{percentage}"){
				format = "percentage";
			}
			if(format=="!{thousandth}"){
				format = "thousandth";
			}
			rs.format = format;// 数据格式
			rs.argument = argument;
		}
		return rs;
	}

	function SQLRowStats_Clear(){
		var self = this;
		self.domNode.remove();
		$(document).trigger($.Event("SQLStructure_SelectComponent_change"),{"component":self,"type":"delete"});
	}
	
	window.SQLRowStats = SQLRowStats;
	/***************************SQLSelect end *********************************/
})()