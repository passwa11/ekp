/**
 * 
 */
(function(){
	/****************************SQLColStats start ********************************/
	/**
	 * 列统计
	 * 
	 */
	function SQLColStats(sqlStructure,condition,key){
		this.sqlStructure = sqlStructure ? sqlStructure : {};
		this.domNode;
		this.block = "colstats";
		
		this.key = key; // sqlStructure对应改组件的key
		
		this.defaultPropertyName = "selctProperty";
		this.defaultSummaryName = "summary";
		this.defaultFormatName = "format";
		this.defaultFormatValName = "formatVal";
		
		this.isSummary = true;
		
		this.fieldComponent; // 字段
		this.summaryComponent; // 汇总方式，此处只设置求和
		this.formatComponent; // 数据格式
		
		this.init = _SQLColStats_Init;
		this.clear = SQLColStats_Clear;
		this.createTrNode = SQLColStats_CreateTrNode;
		this.updateElement = SQLColStats_UpdateElement;
		this.updateSummaryElement = SQLColStats_UpdateSummaryElement;
		this.updateFormatElement = SQLColStats_UpdateFormatElement;
		this.getKeyData = SQLColStats_GetKeyData;
		this.isValid = SQLColStats_IsValid;
		
		// 兼容配置模式
		this.transferData = SQLColStats_TransferData;

		this.init(condition);
	}

	function _SQLColStats_Init(condition){
		console.log("conditon:");
		console.log(condition);
		var self = this;
		var $table = self.sqlStructure.getComponentByKey(self.key)["dom"];
		if($table.data("issummary") == true){
			self.isSummary = true;
		}
		
		var config = {
				"currentModelName" : self.sqlStructure.dataSource.modelName,
				"elementName" : self.defaultPropertyName,
				"structureBlock" : self.block
		};
		if(condition && condition.field){
			config.field = condition.field;
		}
		self.fieldComponent = new SQLComponent_FieldSelectUnion(self,config);
		self.fieldComponent.block = self.block;
		
		// 汇总方式（列表查询没有汇总方式）
		if(self.isSummary){
			var summaryConfig = {};
			summaryConfig.name = self.defaultSummaryName;
			if(condition && condition.summary){
				var summary = condition.summary;
				for(o in summary){
					summaryConfig[o] = summary[o];
				}
			}
			self.summaryComponent = new SQLComponent_Select(self,summaryConfig);
			console.log("init summaryComponent:");
			console.log(self.summaryComponent);
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
	}

	function SQLColStats_CreateTrNode(value){
		var $tr = $("<tr>");
		var self = this;
		self.domNode = $tr;
		
		$tr.addClass("dbEcharts_Configure_Table_Tr");
		var $fieldTd = $("<td class='fieldTd'>");
		$tr.append($fieldTd);
		
		// 汇总方式
		if(self.isSummary){
			var $summaryTd = $("<td class='summaryTd'>");
			$tr.append($summaryTd);
		}
		
		var $td;
		$td = $("<td class='formatTd'></td><td class='opTd'></td>");
		$tr.append($td);
		
		// 添加删除行按钮
		var $del = $("<a href='javascript:void(0);' style='color:#1b83d8;'>"+ DbcenterLang.del +"</a>");
		$del.on('click',function(){
			// 删除当前行和对象
			$tr.remove();
			self.sqlStructure.spliceArray(self.sqlStructure.getComponentByKey(self.key)["arr"],self);
			$(document).trigger($.Event("SQLStructure_SelectComponent_change"),{"component":self,"type":"delete"});
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

		var config = {};
		config.wrap = $fieldTd;
		self.fieldComponent.buildNode(config);
		self.fieldComponent.afterNodeChange = function(){
			self.updateElement();
			$(document).trigger($.Event("SQLStructure_SelectComponent_change"),{"component":self,"type":"change"});
		}
		// 脚本触发change
		if(self.fieldComponent.selectComponents.length > 0){
			self.fieldComponent.selectComponents[self.fieldComponent.selectComponents.length - 1].domNode.change();
		}
		return $tr;
	}

	function SQLColStats_GetKeyData(){
		var data = {};
		var self = this;
		data.field = self.fieldComponent.getKeyData();
		
		data.format = self.formatComponent.getKeyData();
		
		if(self.isSummary){
			data.summary = self.summaryComponent.getKeyData();
		}
		
		return data;
	}
	
	function SQLColStats_IsValid(){
		var self = this;
		var flag = true;
		var type = self.fieldComponent.getKeyData().type;
		if(type == null || type == ''){
			flag = false;
		}else{
			var arr = type.split("|");
			if(arr[arr.length - 1].indexOf("com.landray.kmss") > -1){
				flag = false;
			}
		}
		return flag;
	}
	
	function SQLColStats_UpdateElement(){
		var self = this;
		if(self.isSummary){
			//更新汇总方式
			self.updateSummaryElement();
		}
		self.updateFormatElement();
	}
	
	// 更新汇总方式
	function SQLColStats_UpdateSummaryElement(){
		var self = this;
		var type = self.fieldComponent.getCurrentType();
		if(type.indexOf("com.landray.kmss") > -1){
			return;
		}
		var $summaryTd = self.domNode.find(".summaryTd");
		$summaryTd.empty();
		//self.summaryComponent.clear();
		if(type != ""){
			var summaryJson = JSONComponent.findItemByPath(self.block).summary;
			var options;
			if(type == "!{count}"){
				// 总计
				options = summaryJson['!{count}'];
			}else if(type == 'Integer' || type == 'Long' || type == 'Double' || type == 'BigDecimal' || type.indexOf("com.landray.kmss") > -1){
				// 内置函数
				options = summaryJson['built-in'];
			}
			if(options && options.length > 0){
				console.log("重新构建 self.summaryComponent:");
				console.log(self.summaryComponent);
				$summaryTd.append(self.summaryComponent.buildByOptions(options,function(component){
					component.domNode.on("change",function(){
						var val = $(this).find("option:selected").val();
						component.val = val;
					});
				}));				
			}
		}
	}

	// 更新格式元素
	function SQLColStats_UpdateFormatElement(){
		var self = this;
		var type = self.fieldComponent.getCurrentType();
		if(type.indexOf("com.landray.kmss") > -1){
			return;
		}
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
								// 枚举值需要枚举项
								config.option = self.fieldComponent.getCurrentOption();
								
								config.type = item.type;
								config.name = self.defaultFormatValName;
								var persistenceVal = self.formatComponent.fieldVal;
								if(persistenceVal.hasValue){
									config.val = persistenceVal.data;
								}
								var html = JSONComponent.getHtmlByType(config);
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
	
	// 兼容配置模式所需要做的兼容
	function SQLColStats_TransferData(data){
		var rs = {};
		if(data){
			var field = data.field
			rs.name = field.text; // 列名
			rs.key = field.name; // 字段名
			rs.type = data.summary.val;//统计类型
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

	function SQLColStats_Clear(){
		var self = this;
		self.domNode.remove();
		$(document).trigger($.Event("SQLStructure_SelectComponent_change"),{"component":self,"type":"delete"});
	}
	
	window.SQLColStats = SQLColStats;
	/***************************SQLSelect end *********************************/
})()