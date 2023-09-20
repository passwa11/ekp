/**
 * 
 */
(function(){
	/***************************SQLFilter start *********************************/
	/**
	 * 筛选项
	 * 
	 */
	function SQLFilterItem(sqlStructure,condition,key){
		
		this.sqlStructure = sqlStructure ? sqlStructure : {};
		this.domNode;
		this.block = "filter";
		
		this.key = key; // sqlStructure对应改组件的key
		this.displayNameVal ="";
		this.defaultPropertyName = "selctProperty";
		this.defaultDisplayName = "displayName";
		this.defaultformatName = "format";
		this.defaultshowName = "show";
		this.defaultFilterValName = "defaultFilterVal";
		
		this.fieldComponent; // 字段
		this.formatComponent; // 数据格式
		this.defaultValComponent; // 运算符
		this.showComponent; //日期展示格式：区间  单值
		this.displayNameComponent; //展示名称
		
		this.init = _SQLFilterItem_Init;
		this.clear = SQLFilterItem_Clear;
		this.createTrNode = SQLFilterItem_CreateTrNode;
		this.updateformatElement = _SQLFilterItem_UpdateformatElement;
		this.updateDefaultValElement = _SQLFilterItem_UpdateDefaultValElement;
		this.getKeyData = SQLFilterItem_GetKeyData;
		this.isValid = SQLFilterItem_IsValid;
		// 兼容配置模式
		this.transferData = SQLFilterItem_TransferData;
		
		this.init(condition);
	}
	
	function _SQLFilterItem_Init(condition){
		
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
		
		// 数据格式
		var formatConfig = {};
		formatConfig.name = self.defaultformatName;
		if(condition && condition.format){
			var format = condition.format;
			for(o in format){
				formatConfig[o] = format[o];
			}
		}
		self.formatComponent = new SQLComponent_Select(self,formatConfig);
		
		//展示方式
		var showFormConfig = {};
		if(condition && condition.showForm){
			showFormConfig.val = condition.showForm.val;
			
		}
		
		self.showComponent = new SQLComponent_SelectShow(self,showFormConfig);
		
		//展示名称
		var displayNameFormConfig = {};
		if(condition && condition.displayName){
			this.displayNameVal = condition.displayName.val;
			if("undefined" == condition.displayName.val){
				this.displayNameVal = "";
			}
			displayNameFormConfig.val = condition.displayName.val;
		}
		self.displayNameComponent = new SQLComponent_SelectdisplayName(self,displayNameFormConfig);
		
		var defaultValConfig = {};
		if(condition && condition.defaultVal){
			var defaultVal = condition.defaultVal;
			defaultValConfig.val = defaultVal.val;
		}
		defaultValConfig.block = self.block;
		defaultValConfig.inputName = self.defaultFilterValName;
		self.defaultValComponent = new SQLComponent_DrawUnit(self,defaultValConfig);
	}

	function SQLFilterItem_CreateTrNode(){
		var $tr = $("<tr>");
		var self = this;
		self.domNode = $tr;
		$tr.addClass("dbEcharts_Configure_Table_Tr");
		var $fieldTd = $("<td class='fieldTd'>");
		$tr.append($fieldTd);
		var $td;
		$td = $("<td class='displayNameTd'></td><td class='formatTd'></td><td class='showTd'></td><td class='defaultValTd'></td><td class='opTd'></td>");
		$tr.append($td);
		
		// 添加删除行按钮
		var $del = $("<a href='javascript:void(0);' style='color:#1b83d8;'>"+ DbcenterLang.del +"</a>");
		 // 展现名称输入框
		var $displayName = $("<input class='inputsgl' name='displayName' type='text' value='"+this.displayNameVal+"'/>");
		self.displayNameComponent.elementNode = $displayName;
		$tr.find(".displayNameTd").append(self.displayNameComponent.elementNode);
		
		
		$del.on('click',function(){
			// 删除当前行和对象
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
			self.updateformatElement();
			self.updateDefaultValElement();
		}
		// 脚本触发change
		if(self.fieldComponent.selectComponents.length > 0){
			self.fieldComponent.selectComponents[self.fieldComponent.selectComponents.length - 1].domNode.change();
		}
		return $tr;
	}
	
	function _SQLFilterItem_UpdateformatElement(){
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
			//添加展现形式
//			if(type == 'DateTime'){
//				var $showTd = self.domNode.find(".showTd");
//				$showTd.empty();
//				var showSelect ="<select name='showForm' class='showForm' onchange='changeDefaultVal(this)'><option value='fixedQuery' class='fixedQuery'>固定查询</option><option value='rangeQuery' class='rangeQuery'>区间查询</option></select>";
//				$showTd.append(showSelect);
//				
//			}else{
//				var $showTd = self.domNode.find(".showTd");
//				$showTd.empty();
//			}
			var item = JSONComponent.findItemByPath(path);
			if(item){
				self.formatComponent.JSONItem = item;
				var dom = self.formatComponent.buildNodeByJSONNode(function(component){
					component.domNode.on("change",function(){
						var val = $(this).find("option:selected").val();
						var $showTd = self.domNode.find(".showTd");
						$showTd.empty();
						var html ="<select name='showForm' class='showForm' style='width:90%;'>";
						if(val == "Date"){
							for(var i = 0;i < self.formatComponent.JSONItem.options[0]['options'].length;i++){
								var option =  self.formatComponent.JSONItem.options[0]['options'][i];
								html += "<option value='";
								html += option['name'] + "'";
								if(self.val && self.showComponent.val == option['name']){
									html += " selected='selected'";
									isSet = true;
								}
								html += ">";
								html += option['text'];
								html += "</option>";
							}
							
						}else{
							//var $showTd = self.domNode.find(".showTd");
							for(var i = 0;i < self.formatComponent.JSONItem.options[1]['options'].length;i++){
								var option =  self.formatComponent.JSONItem.options[1]['options'][i];
								html += "<option value='";
								html += option['name'] + "'";
								if(self.val && self.val == option['name']){
									html += " selected='selected'";
									isSet = true;
								}
								html += ">";
								html += option['text'];
								html += "</option>";
							}
						}
						html += "</select>";
						//$showTd.append(html);
						var $selectNode = $(html);
						$selectNode.on("change",function(){
							var $defaultValTd =self.domNode.find(".defaultValTd");
							var s =self.domNode.find(".showForm").val();
							$defaultValTd.empty();
							var defaultValHtml="";
							if(s.trim()== "fixedQuery"){
								var calType = "time";
								if(val == "DateTime"){
									calType = "datetime"
								}
								if(val == "Date"){
									calType = "date"
								}
								defaultValHtml += "<div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
								defaultValHtml += "<div class='input'><input name='" + "defaultFilterVal" + "' type='text' validate='__"+calType+"' value='' attr='"+calType+"'/></div>";
								defaultValHtml += "<div class='inputdatetime'></div>";
								defaultValHtml += "</div>";
							}else{
								defaultValHtml += "<span>从</sapn><div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
								defaultValHtml += "<div class='input'><input name='" + "defaultFilterVal" + "' type='text' validate='__date' value='' attr='date'/></div>";
								defaultValHtml += "<div class='inputdatetime'></div>";
								defaultValHtml += "</div>";
								defaultValHtml += "<br><span>到</sapn><div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
								defaultValHtml += "<div class='input'><input name='" + "defaultFilterVal2"+ "' type='text' validate='__date' value='' attr='date'/></div>";
								defaultValHtml += "<div class='inputdatetime'></div>";
								defaultValHtml += "</div>";
							}
							self.defaultValComponent.domNode = $(defaultValHtml);
							$defaultValTd.append(self.defaultValComponent.domNode);
							// 初始化完毕之后，清空原有值
							self.defaultValComponent.val = '';
							
						});
						self.showComponent.elementNode = $selectNode;
						//$showTd.append(html);
						$showTd.append(self.showComponent.elementNode);
						
						//改变默认，点击数据格式，而数据展现形式没变的情况下
						$selectNode.trigger('change');
						component.val = val;
					});
				});
				$formatTd.append(dom);
				
				if(type == 'DateTime' || type =='Date' || type=='Time'){
					var val = self.domNode.find("[name='format']").val();
					var $showTd = self.domNode.find(".showTd");
					$showTd.empty();
					
					var html ="<select name='showForm' class='showForm' style='width:90%;'>";
					
					if(val == "Date"){
						for(var i = 0;i < self.formatComponent.JSONItem.options[0]['options'].length;i++){
							var option =  self.formatComponent.JSONItem.options[0]['options'][i];
							html += "<option value='";
							html += option['name'] + "'";
							if(self.showComponent.val && self.showComponent.val == option['name']){
								html += " selected='selected'";
								isSet = true;
							}
							html += ">";
							html += option['text'];
							html += "</option>";
						}
						
					}else{
						//var $showTd = self.domNode.find(".showTd");
						for(var i = 0;i < self.formatComponent.JSONItem.options[1]['options'].length;i++){
							var option =  self.formatComponent.JSONItem.options[1]['options'][i];
							html += "<option value='";
							html += option['name'] + "'";
							if(self.val && self.val == option['name']){
								html += " " +
										"='selected'";
								isSet = true;
							}
							html += ">";
							html += option['text'];
							html += "</option>";
						}
					}
					html += "</select>";
					
					var $selectNode = $(html);
					$selectNode.on("change",function(){
						var $defaultValTd =self.domNode.find(".defaultValTd");
						var s =self.domNode.find(".showForm").val();
						$defaultValTd.empty();
						var defaultValHtml="";
						if(s == "fixedQuery"){
							var calType = "time";
							if(val == "DateTime"){
								calType = "datetime"
							}
							if(val == "Date"){
								calType = "date"
							}
							defaultValHtml += "<div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
							defaultValHtml += "<div class='input'><input name='" + "defaultFilterVal" + "' type='text' validate='__"+calType+"' value='' attr='"+calType+"'/></div>";
							defaultValHtml += "<div class='inputdatetime'></div>";
							defaultValHtml += "</div>";
						}else{
							defaultValHtml += "<span>从</sapn><div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
							defaultValHtml += "<div class='input'><input name='" + "defaultFilterVal" + "' type='text' validate='__date' value='' attr='date'/></div>";
							defaultValHtml += "<div class='inputdatetime'></div>";
							defaultValHtml += "</div>";
							defaultValHtml += "<br><span>到</sapn><div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
							defaultValHtml += "<div class='input'><input name='" + "defaultFilterVal2"+ "' type='text' validate='__date' value='' attr='date'/></div>";
							defaultValHtml += "<div class='inputdatetime'></div>";
							defaultValHtml += "</div>";
						}
						self.defaultValComponent.domNode = $(defaultValHtml);
						$defaultValTd.append(self.defaultValComponent.domNode);
						// 初始化完毕之后，清空原有值
						self.defaultValComponent.val = '';
						
					});
					self.showComponent.elementNode = $selectNode;
					//$showTd.append(html);
					$showTd.append(self.showComponent.elementNode);
					
					
					
				}
				
				
				
			}
			
			
		}
	}
	
//	window.changeDefaultVal222 = function(t){
//		//alert("将改变默认");
//		var s = $(t).val();
//		//var self = this;
//		alert(s);
//		var $defaultValTd =$(t).closest('tr').find(".defaultValTd");
//		$defaultValTd.empty();
//		var html="";
//		//var self=$(t).closest('tr');
//		if(s == "fixedQuery"){
//			html += "<div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
//			html += "<div class='input'><input name='" + "defaultFilterVal" + "' type='text' validate='__datetime' value='' /></div>";
//			html += "<div class='inputdatetime'></div>";
//			html += "</div>";
//		}else{
//			html += "<span>从</span><div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
//			html += "<div class='input'><input name='" + "defaultFilterVal" + "' type='text' validate='__datetime' value='' /></div>";
//			html += "<div class='inputdatetime'></div>";
//			html += "</div>";
//			html += "<span>到</span><div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
//			html += "<div class='input'><input name='" + "defaultFilterVal2"+ "' type='text' validate='__datetime' value='' /></div>";
//			html += "<div class='inputdatetime'></div>";
//			html += "</div>";
//		}
//		$defaultValTd.append(html);
//		self.defaultValComponent.domNode = $(html);
//		$defaultValTd.append(self.defaultValComponent.domNode);
//		self.defaultValComponent.val = '';
//	}
	function _SQLFilterItem_UpdateDefaultValElement(){
		var self = this;
		var type = self.fieldComponent.getCurrentType();
		if(type.indexOf("com.landray.kmss") > -1){
			return;
		}
		var path = self.block + "." + type;
		//alert("默认值path:"+path+";type"+type);
		var item = JSONComponent.findItemByPath(path);
		
		var $defaultValTd = self.domNode.find(".defaultValTd");
		$defaultValTd.empty();
		if(type == ""){
			return;
		}
		if(item){
			// 只有找到对应的选项才构建元素
			self.defaultValComponent.JSONItem = item;
			if(item.type){
				var config = {};
				// 枚举值需要枚举项
				config.option = self.fieldComponent.getCurrentOption();
				
				config.type = item.type;
				config.name = self.defaultValComponent.inputName;
				config.val = self.defaultValComponent.val;
				
				//单独把DateTime抽取出来，因为要做联动
				var html="";
				if(type == "DateTime" || type == "Date" || type == "Time"){
					//alert("改造DateTime");
					var s = self.domNode.find(".showForm").val();
					if(s == "fixedQuery"){
						var val = self.domNode.find(".formatTd").find("select").val();
						var calType = "time";
						if(val == "DateTime"){
							calType = "datetime"
						}
						if(val == "Date"){
							calType = "date"
						}
						html += "<div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
						html += "<div class='input'><input name='defaultFilterVal' type='text' validate='_"+calType+"' attr='"+calType+"' value='"+ self.defaultValComponent.val1 +"' /></div>";
						html += "<div class='inputdatetime'></div>";
						html += "</div>";
					}else{
						html += "<span>从</span><div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
						html += "<div class='input'><input name='defaultFilterVal' type='text' validate='__date' attr='date' value='"+ self.defaultValComponent.val1 +"' /></div>";
						html += "<div class='inputdatetime'></div>";
						html += "</div>";
						html += "<span>到</span><div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
						html += "<div class='input'><input name='defaultFilterVal' type='text' validate='__date' attr='date' value='"+ self.defaultValComponent.val2+"' /></div>";
						html += "<div class='inputdatetime'></div>";
						html += "</div>";
					}
				}else{
					html = self.defaultValComponent.buildNodeByJSONNode(config);    //跳到SQLComponent.js SQLComponent_ValueUnion_GetKeyData -> SQLFilterItem   _JSONComponent_GetDateFormatHtml
				}
				
				self.defaultValComponent.domNode = $(html);
				$defaultValTd.append(self.defaultValComponent.domNode);
				// 初始化完毕之后，清空原有值
				self.defaultValComponent.val = '';
			}
		}
	}
	
	function SQLFilterItem_GetKeyData(){
		var data = {};
		var self = this;
		data.field = self.fieldComponent.getKeyData();
		
		data.format = self.formatComponent.getKeyData();
		
		data.defaultVal = self.defaultValComponent.getKeyData();
		
		data.showForm = self.showComponent.getKeyData();
		
		data.displayName = self.displayNameComponent.getKeyData();
		return data;
	}
	
	function SQLFilterItem_IsValid(){
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
	
	// 兼容配置模式所需要做的兼容
	function SQLFilterItem_TransferData(data){
		var rs = {};
		if(data){
			var field = data.field
			var defaultVal = data.defaultVal;
			rs.name = field.text; // 列名
			rs.key = field.name; // 字段名
			var val = "";
			// 获取默认值
			if(defaultVal.val && defaultVal.val.length > 0){
				val = defaultVal.val[0].value;	
			}
			rs.value = val; // 初始值
			rs.format = data.format.val;// 数据类型
			rs.split = "";// 分隔符，不知道干嘛用
			rs.criteria = defaultVal.criteria? defaultVal.criteria:"";// 枚举值，一般用于枚举值的筛选项
			rs.outer = "true";
		}
		return rs;
	}
	
	function SQLFilterItem_Clear(){
		var self = this;
		self.domNode.remove();
	}
	/***************************SQLFilter end *********************************/
	
	window.SQLFilterItem = SQLFilterItem;
})()