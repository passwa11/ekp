/**
 * 
 */
(function(){
	
	function SQLSeries(sqlStructure,condition,key){
		this.sqlStructure = sqlStructure ? sqlStructure : {};
		this.domNode;
		this.block = "series";
		
		this.work = true;
		
		this.key = key; // sqlStructure对应改组件的key
		
		this.defaultPropertyName = "selctProperty";
		this.defaultformatName = "format";
		this.defaultOrderName = "order";
		
		this.fieldComponent; // 字段
		this.formatComponent; // 数据格式
		//this.orderComponent; // 排序项
		
		this.init = SQLSeries_Init;
		this.clear = SQLSeries_Clear;
		this.createTrNode = SQLSeries_CreateTrNode;
		this.updateformatElement = _SQLWhere_UpdateformatElement;
		this.getKeyData = SQLSeries_GetKeyData;
		this.isValid = SQLSeries_IsValid;
		
		this.init(condition);
	}
	
	// condition : {field:{name:xx|xxx,type:xx|xxx} ,  fieldVal:{name:xxx , val:xxx} order:{val:''|asc|desc}}
	function SQLSeries_Init(condition){
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
		
		var formatConfig = {};
		formatConfig.name = self.defaultformatName;
		if(condition && condition.format){
			var format = condition.format;
			for(o in format){
				formatConfig[o] = format[o];
			}
		}
		self.formatComponent = new SQLComponent_Select(self,formatConfig);
		
		// 排序项
//		var orderConfig = {};
//		orderConfig.name = self.defaultOrderName;
//		if(condition && condition.order){
//			var order = condition.order;
//			for(o in order){
//				orderConfig[o] = order[o];
//			}
//		}
//		orderConfig.options = [];
//		orderConfig.options.push({"name":"升序","showName":"升序","type":"string","value":"asc"});
//		orderConfig.options.push({"name":"降序","showName":"降序","type":"string","value":"desc"});
//		self.orderComponent = new SQLComponent_Select(self,orderConfig);
//		self.orderComponent.hasPlzOption = true;
	}

	function SQLSeries_Clear(){
		this.domNode.remove();
	}

	function SQLSeries_GetKeyData(){
		var data = {};
		var self = this;
		data.field = self.fieldComponent.getKeyData();
		
		data.format = self.formatComponent.getKeyData();
		
		//data.order = self.orderComponent.getKeyData();
		
		return data;
	}
	
	function SQLSeries_IsValid(){
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

	function SQLSeries_CreateTrNode(value){
		var $tr = $("<tr>");
		var self = this;
		self.domNode = $tr;
		
		$tr.addClass("dbEcharts_Configure_Table_Tr");
		var $fieldTd = $("<td class='fieldTd'>");
		$tr.append($fieldTd);
		$tr.append("<td class='formatTd'></td><td class='opTd'></td>");
		
//		$tr.find(".orderTd").append(self.orderComponent.buildNode(function(component){
//			component.domNode.on("change",function(){
//				var val = $(this).find("option:selected").val();
//				component.val = val;
//			});
//		})); // 排序项
		
		// 添加删除行按钮
		var $del = $("<a href='javascript:void(0);' data-pair='"+ self.block +"' style='color:#1b83d8;'>"+ DbcenterLang.del +"</a>");
		$del.on('click',function(){
			// 当初当前行和对象
			var com = self.sqlStructure.getComponentByKey(self.key)["arr"];
			$tr.remove();
			self.sqlStructure.spliceArray(com,self);
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
			self.updateformatElement();
		}
		// 脚本触发change
		if(self.fieldComponent.selectComponents.length > 0){
			self.fieldComponent.selectComponents[self.fieldComponent.selectComponents.length - 1].domNode.change();
		}
		return $tr;
	}

	function _SQLWhere_UpdateformatElement(){
		var self = this;
		var type = self.fieldComponent.getCurrentType();
		if(type.indexOf("com.landray.kmss") > -1){
			return;
		}
		var $formatTd = self.domNode.find(".formatTd");
		$formatTd.empty();
		if(self.sqlStructure.isInit){
			self.formatComponent.clear();			
		}
		if(type != ""){
			var path = self.block + "." + type;
			var item = JSONComponent.findItemByPath(path);
			self.formatComponent.JSONItem = item;
			var dom = self.formatComponent.buildNodeByJSONNode(function(component){
				component.domNode.on("change",function(){
					var val = $(this).find("option:selected").val();
					component.val = val;
				});
			});
			$formatTd.append(dom);	
		}
	}
	
	window.SQLSeries = SQLSeries;
})()