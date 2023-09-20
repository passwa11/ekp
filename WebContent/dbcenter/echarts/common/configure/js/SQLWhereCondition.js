/**
 * 
 */
(function(){
	
	/***************************SQLWhere start *********************************/
	/**
	 * whereblock
	 * @returns
	 */
	function SQLWhereCondition(sqlStructure,condition,key){
		this.sqlStructure = sqlStructure ? sqlStructure : {};
		this.domNode;
		this.block = "where";
		
		this.key = key; // sqlStructure对应改组件的key
		
		this.defaultPropertyName = "selctProperty";
		this.defaultOperateName = "operate";
		this.defaultValueItemName = "valueItem";
		this.defaultValueValName = "valueVal";
		this.defaultValueFormatName = "valueFormat"; // 默认值的格式，日期控件需要
		
		this.fieldComponent; // 字段
		this.operateComponent; // 运算符
		this.valueComponent; // 值
		this.vlaueFormatComponent; // 值的格式
		
		this.init = SQLWhere_Init;
		this.clear = SQLWhere_Clear;
		this.createTrNode = SQLWhere_CreateTrNode;
		this.updateOperateElement = _SQLWhere_UpdateOperateElement;
		this.updateValueElement = _SQLWhere_UpdateValueElement;
		this.updateValueFormatElement = _SQLWhere_UpdateValueFormatElement;
		this.getKeyData = SQLWhere_GetKeyData;
		this.isValid = SQLWhere_IsValid;
		
		this.init(condition);
	}

	// condition : {field:{name:xx|xxx,type:xx|xxx} , operate:{name:xxx} , fieldVal:{name:xxx , val:xxx}}
	function SQLWhere_Init(condition){
		var self = this;
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
		
		var operateConfig = {};
		operateConfig.name = self.defaultOperateName;
		if(condition && condition.operate){
			var operate = condition.operate;
			for(o in operate){
				operateConfig[o] = operate[o];
			}
		}
		self.operateComponent = new SQLComponent_Select(self,operateConfig);
		
		var fieldValConfig = {};
		fieldValConfig.name = self.defaultValueItemName;
		fieldValConfig.valName = self.defaultValueValName;
		if(condition && condition.fieldVal){
			var fieldVal = condition.fieldVal;
			for(o in fieldVal){
				fieldValConfig[o] = fieldVal[o];				
			}
		}
		self.valueComponent = new SQLComponent_ValueUnion(self,fieldValConfig);
		
		var formatConfig = {};
		formatConfig.name = self.defaultValueFormatName;
		if(condition && condition.format){
			var format = condition.format;
			for(o in format){
				formatConfig[o] = format[o];
			}
		}
		self.vlaueFormatComponent = new SQLComponent_Select(self,formatConfig);
		
	}

	function SQLWhere_Clear(){
		this.domNode.remove();
	}

	function SQLWhere_GetKeyData(){
		var data = {};
		var self = this;
		data.field = self.fieldComponent.getKeyData();
		
		data.operate = self.operateComponent.getKeyData();
		
		data.fieldVal = self.valueComponent.getKeyData();
		
		data.format = self.vlaueFormatComponent.getKeyData();
		
		return data;
	}
	
	function SQLWhere_IsValid(){
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

	function SQLWhere_CreateTrNode(value){
		var $tr = $("<tr>");
		var self = this;
		self.domNode = $tr;
		
		$tr.addClass("dbEcharts_Configure_Table_Tr");
		var $fieldTd = $("<td class='fieldTd'>");
		$tr.append($fieldTd);
		var $td;
		$td = $("<td class='operateTd'></td><td class='fieldValueTd'></td><td class='formatTd'></td><td class='opTd'></td>");
		$tr.append($td);
		
		// 添加删除行按钮
		var $del = $("<a href='javascript:void(0);' style='color:#1b83d8;'>"+ DbcenterLang.del +"</a>");
		$del.on('click',function(){
			// 当初当前行和对象
			$tr.remove();
			self.sqlStructure.spliceArray(self.sqlStructure.getComponentByKey(self.key)["arr"],self);
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
			self.updateOperateElement();
		}
		// 脚本触发change
		if(self.fieldComponent.selectComponents.length > 0){
			self.fieldComponent.selectComponents[self.fieldComponent.selectComponents.length - 1].domNode.change();
		}
		return $tr;
	}

	function _SQLWhere_UpdateOperateElement(){
		var self = this;
		var type = self.fieldComponent.getCurrentType();
		if(type.indexOf("com.landray.kmss") > -1){
			return;
		}
		var $operateTd = self.domNode.find(".operateTd");
		$operateTd.empty();
		if(type != ''){
			var path = self.block + "." + type;
			var item = JSONComponent.findItemByPath(path);
			self.operateComponent.JSONItem = item;
			var dom = self.operateComponent.buildNodeByJSONNode(function(component){
				component.domNode.on("change",function(){
					var val = $(this).find("option:selected").val();
					component.val = val;
					self.updateValueElement();
				});
			});
			$operateTd.append(dom);
			self.operateComponent.domNode.change();
		}else{
			self.operateComponent.clear();
			self.updateValueElement();
		}
		
	}

	function _SQLWhere_UpdateValueElement(){
		var self = this;
		var $fieldValueTd = self.domNode.find(".fieldValueTd");
		$fieldValueTd.empty();
		if(self.operateComponent.domNode == null){
			return;
		}
		var val = self.operateComponent.domNode.find("option:selected").val();
		var item = self.operateComponent.JSONItem;
		self.valueComponent.JSONItem = JSONComponent.findItemByPath(item.path + "." + val);
		var dom = self.valueComponent.buildNodeByJSONNode(function(component){
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
						config.name = self.defaultValueValName;
						// 多选的，支持placeholder
						config.canInputMulti = item.canInputMulti
						var persistenceVal = self.valueComponent.fieldVal;
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
				self.updateValueFormatElement();
			});
		});
		
		$fieldValueTd.append(dom);
		self.valueComponent.domNode.change();
	}
	
	function _SQLWhere_UpdateValueFormatElement(){
		var self = this;
		var type = self.valueComponent.JSONItem.path + "." + self.valueComponent.val;
		var $formatTd = self.domNode.find(".formatTd");
		$formatTd.empty();
		if(type != ""){
			var item = JSONComponent.findItemByPath(type);
			// 如果不含选项，则清空原有值，以免日期切换字符串时，没有清空原有值
			if(!item.options){
				self.vlaueFormatComponent.clear();
			}
			self.vlaueFormatComponent.JSONItem = item;
			var dom = self.vlaueFormatComponent.buildNodeByJSONNode(function(component){
				component.domNode.on("change",function(){
					var val = $(this).find("option:selected").val();
					component.val = val;
				});
			});
			$formatTd.append(dom);
			dom.change();
		}
	}
	/****************************SQLWhere end ********************************/
	
	window.SQLWhereCondition = SQLWhereCondition;
})()