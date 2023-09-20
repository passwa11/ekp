/**
 * 
 */
(function(){
	// 绘画单元，筛选项的默认值
	function SQLComponent_DrawUnit(container,config){
		this.container = container ? container : {};
		this.block; // 所属板块
		this.domNode;
		
		this.inputName;
		this.dispalyName ="";
		this.val = "";
		this.val1 = ""; //时间的第一个默认值
		this.val2 = ""; //第二个默认值
		
		this.JSONItem;

		this.init = SQLComponent_DrawUnit_Init;
		this.buildNodeByJSONNode = SQLComponent_Common_BuildNodeByJSONNode;
		this.getKeyData = SQLComponent_DrawUnit_GetKeyData;
		
		this.init(config);
	}
	
	function SQLComponent_DrawUnit_Init(config){
		if(config){
			this.val = config.val ? config.val : "";
			this.inputName = config.inputName ? config.inputName : "";
			this.formatName = config.formatName ? config.formatName : "";
			this.block = config.block ? config.block : "";
			var configArray = [];
			configArray =config.val;
			
			if(config.val != 'undefined' && config.val != null){
				for(i=0;i<configArray.length;i++){
					if(i == 0){
						this.val1 =config.val[i].value;
					}
					if(i == 1){
						this.val2 =config.val[i].value;
					}
				}
			}
			
		}
	}
	
	function SQLComponent_DrawUnit_GetKeyData(){
		
		var self = this;
		var data = {};
		var val = [];
		
		if(self.domNode){
			var vals = self.domNode.closest("td").find("[name*='"+ self.inputName +"']");
			
			if(self.JSONItem && self.JSONItem.name == 'enum'){
				var criteria = [];
				for(var i = 0;i < vals.length;i++){
					var v = vals[i];
					criteria.push(v.value + "=" + $(v).closest("label").text());
				}
				data.criteria = criteria.join("\n");
			}

			if(vals.length > 0){
				var arrs = [];
				for(var i = 0;i < vals.length;i++){
					var v = vals[i];
					if(v.type=="radio"){
						if(!v.checked){
							continue;
						}
					}
					var arr = {};
					arr.name = v.name;
					arr.value = v.value;
					val.push(arr);
				}
			}
		}
		// 规定格式为数组
		data.val = val;
		return data;
	}
	
	function SQLComponent_Common_BuildNodeByJSONNode(config){
		var self = this;
		if(self.JSONItem){
			return JSONComponent.getHtmlByType(config);
		}
	}
	
	/******************************SQLComponent_Select start ***********************************/
	// 单个下拉菜单，可用于运算符等
	function SQLComponent_Select(union,config){
		this.union = union ? union : {};
		
		this.name = '';
		this.options; // 固定选项和动态选项的集合
		this.dynamicOptions = [];	// 动态选项，用于统计列表的合计项,元素格式： {option:option,sourceWgt:component}
		
		this.val = '';
		this.type = ''; 
		this.text = '';
		this.parent;
		this.child;
		this.JSONItem;
		
		this.hasPlzOption = false; //解决 所有下拉菜单第一项都为对象，导致溢出的问题
		
		this.domNode;
		
		this.buildNode = function(callback){
			var node;
			var html = "";
			var self = this;
			if(self.options.length == 0){
				console.error("组件的options长度为0，无法生成！");
				return node;
			}
			html += "<select name='"+ self.name +"' validate='required' style='width:90%;'>";
			var isSet = false;
			if(this.hasPlzOption){
				html += "<option value=''>"+ DbcenterLang.plzChoose +"</option>";
			}
			for(var i = 0;i < self.options.length;i++){
				var option = self.options[i];
				html += "<option value='";
				html += option['value'] + "'";
				html += " data-orgtype='" + (option['orgType'] || "") + "'";
				if(self.val && self.val == option['value']){
					html += " selected='selected'";
					// 更新type
					self.type = option.type;
					self.text = option.name;
					isSet = true;
				}
				html += ">";
				html += option['showName'];
				html += "</option>";
			}
			html += "</select>";
			// 如果没有被设值，默认第一个选项是选中
			if(!isSet && !this.hasPlzOption){
				self.val = self.options[0].value;
				self.type = self.options[0].type;
				self.text = self.options[0].name;
			}
			node = $(html);
			self.domNode = node;
			if(callback){
				callback(self);
			}
			return node;
		};
		
		this.init = _SQLComponent_Select_Init;
		this.clear = SQLComponent_Select_Clear;
		this.findOption = SQLComponent_Select_FindOption;
		this.findOptionByJSONNode = SQLComponent_SelectCommon_FindOptionByJSONNode;
		this.buildNodeByJSONNode = SQLComponent_SelectCommon_BuildNodeByJSONNode;
		this.buildByOptions = SQLComponent_SelectCommon_BuildByOptions;
		this.getKeyData = SQLComponent_Select_GetKeyData;
		this.updateDynamicOption = SQLComponent_Select_UpdateDynamicOption;	// 更新动态选项
		
		// 仅用于统计列表的合计
		this.deleteDynamicOptions = SQLComponent_DeleteDynamicOptions;
		this.addNewDynamicOptions = SQLComponent_AddNewDynamicOptions;
		
		this.init(config);
	}

	function SQLComponent_Select_Clear(){
		var self = this;
		if(self.domNode){
			self.domNode.remove();	
		}
		self.domNode = null;
		self.options = [];
		self.dynamicOptions = [];
		self.val = '';
		self.type = ''; 
		self.text = '';
		var child = self.child;
		if(child){
			child.clear();
		}
	}

	function _SQLComponent_Select_Init(config){
		this.name = config.name ? config.name: "";
		
		this.options = config.options ? config.options : [];
		
		this.val = config.val ? config.val : null;
	}

	// key:选项的value（必填），propertyItem：需更新的字段定义组件（可选）
	function SQLComponent_Select_FindOption(key){
		var self = this;
		// 先在固定数据源里面查找
		for(var i = 0;i < self.options.length;i++){
			var option = self.options[i];
			if(option.value === key){
				return option;
			}
		}
	}
	

	function SQLComponent_Select_GetKeyData(){
		var data = {};
		data.val = this.val;
		return data;
	}

	function SQLComponent_SelectCommon_FindOptionByJSONNode(key){
		var self = this;
		var item = self.JSONItem;
		for(var i = 0;i < item.options.length;i++){
			var option = item.options[i];
			if(option.name == key){
				return option;
			}
		}
	}

	function SQLComponent_SelectCommon_BuildNodeByJSONNode(callback){
		var node;
		var html = "";
		var self = this;
		var item = self.JSONItem;
		html += "<select name='"+ self.name +"' style='width:90%;'>";
		var isSet = false;
		if(item && item.options){
			for(var i = 0;i < item.options.length;i++){
				var option = item.options[i];
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
		}else{
			return $("");
		}
		
		html += "</select>";
		// 如果没有被设值，默认第一个选项是选中
		if(!isSet && item && item.options.length > 0){
			self.val = item.options[0].name;
		}
		node = $(html);
		self.domNode = node;
		if(callback){
			callback(self);
		}
		return node;
	}
	
	function SQLComponent_SelectCommon_BuildByOptions(options,callback){
		var node;
		var html = "";
		var self = this;
		html += "<select name='"+ self.name +"' style='width:90%;'>";
		var isSet = false;
		for(var i = 0;i < options.length;i++){
			var option = options[i];
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
		
		html += "</select>";
		// 如果没有被设值，默认第一个选项是选中
		if(!isSet && options.length > 0){
			self.val = options[0].name;
		}
		node = $(html);
		self.domNode = node;
		if(callback){
			callback(self);
		}
		return node;
	}
	
	function SQLComponent_DeleteDynamicOptions(){
		for(var i = this.options.length-1;i >= 0;i--){
			var option = this.options[i];
			if(option.isDynamic){
				this.domNode.find("option[value='"+ option.value +"']").remove();
				this.options.splice(i,1);
			}
		}
	}
	
	function SQLComponent_AddNewDynamicOptions(newDynamicOptions){
		for(var i = 0;i < newDynamicOptions.length;i++){
			this.options.push(newDynamicOptions[i]);
			this.domNode.append("<option value='"+ newDynamicOptions[i].value +"'>"+ newDynamicOptions[i].name +"</option>");
		}
	}

	function SQLComponent_Select_UpdateDynamicOption(newDynamicOptions){
		var self = this;
		self.deleteDynamicOptions();
		self.addNewDynamicOptions(newDynamicOptions)
	}
	/******************************SQLComponent_Select end ***********************************/
	
	/******************************SQLComponent_SelectShow start ***********************************/
	function SQLComponent_SelectShow(container,config){
		this.val = "";
		this.init = SQLComponent_SelectShow_init;
		//alert("=========type:"+type);
		this.getKeyData = SQLComponent_SelectShow_GetKeyData; // 提交时，返回该组件的数据
		this.init(config);
	}
	
	function SQLComponent_SelectShow_init(config){
		this.val = config.val;
		//alert("config的val:"+this.val);
	}
	
	
	function SQLComponent_SelectShow_GetKeyData(){
		
		var data = {};
		
		if(this.elementNode != null){
			data.val = this.elementNode.val();
			
		}
		return data;
	}
	/******************************SQLComponent_SelectShow end ***********************************/
	
	/******************************SQLComponent_SelectdisplayName start ***********************************/
	
	function SQLComponent_SelectdisplayName(container,config){
		this.dispalyName = "";
		this.init = SQLComponent_SelectdisplayName_init;
		this.getKeyData = SQLComponent_SelectdisplayName_GetKeyData; // 提交时，返回该组件的数据
		this.init(config);
	}
	
	function SQLComponent_SelectdisplayName_init(config){
		this.dispalyName = config.val;
		//alert("config的val:"+this.val);
	}
	
	function SQLComponent_SelectdisplayName_GetKeyData(){
		
		var data = {};
		
		if(this.elementNode != null){
			data.val = this.elementNode.val();
		}
		return data;
	}
	
	/******************************SQLComponent_SelectdisplayName end ***********************************/
	
	
	
	
	
	
	
	/******************************SQLComponent_count start ***********************************/
	
	function SQLComponent_count(container,config){
		
		this.countSelect = "";
		this.number = "";
		this.getKeyData = SQLComponent_count_GetKeyData; // 提交时，返回该组件的数据
		this.init = SQLComponent_count_init;
		this.init(config);
	}
	function SQLComponent_count_GetKeyData(){
		self = this;
		data = {};
		data.countSelect = self.elementNode.val();
		data.number = "";
	    if(data.countSelect == 'custom'){
	    	data.number = self.domNode.val();
	    }
		//alert("data.count:"+data.count);
		return data;
	}
	function SQLComponent_count_init(config){
		this.countSelect = config.countSelect;
		if(this.countSelect == 'custom'){
			this.number = config.number;
		}
	}
	
	/******************************SQLComponent_count end ***********************************/
	
	/******************************SQLComponent_FieldSelectUnion start ***********************************/
	// 下拉菜单组合，用于字段名称
	function SQLComponent_FieldSelectUnion(container,config){
		this.container = container ? container : {};
		this.block; // 所属板块
		
		this.val = ""; // 字段值
		this.type = ""; // 字段类型
		this.text = ""; // 显示文字
		this.orgType = ""; // 组织架构类型
		this.selectComponents = [];
		
		this.initChild = SQLComponent_FieldSelectUnion_InitChild; // 初始化子组件的事件，一般为onchange事件
		this.nodeChange = SQLComponent_FieldSelectUnion_NodeChange;
		this.afterNodeChange;
		
		this.buildNode = function(config){
			var self = this;
			var wrap = config.wrap;
			for(var i = 0;i < self.selectComponents.length;i++){
				var c = self.selectComponents[i];
				c.hasPlzOption = true;
				wrap.append(c.buildNode(self.initChild));
			}
			return wrap;
		};
		
		this.init = _SQLComponent_FieldSelectUnion_Init;
		this.addSelectComponent = SQLComponent_FieldSelectUnion_AddSelectComponent;
		this.appendNode = SQLComponent_FieldSelectUnion_AppendNode; // 添加下拉组件
		this.getCurrentSelectComponent = SQLComponent_FieldSelectUnion_GetCurrentSelectComponent; // 获取最后一个下拉菜单组件
		this.getCurrentType = SQLComponent_FieldSelectUnion_GetCurrentType; //获取最后一个下拉菜单的类型
		this.getCurrentVal = SQLComponent_FieldSelectUnion_GetCurrentVal; //获取最后一个下拉菜单的字段值
		this.getCurrentOption = SQLComponent_FieldSelectUnion_GetCurrentOption; //获取最后一个下拉菜单的选项
		this.getKeyData = SQLComponent_FieldSelectUnion_GetKeyData; // 提交时，返回该组件的数据
		this.deleteComponent = SQLComponent_FieldSelectUnion_DeleteComponent; // 删除子组件
		this.clear = SQLComponent_FieldSelectUnion_Clear;
		this.updateDynamicOption = SQLComponent_FieldSelectUnion_UpdateDynamicOption;	//更新动态选项，更新第一个下拉菜单的选项，用于统计行的动态选项
		
		this.init(config);
	}

	// config : {currentModelName : xxx, field :{name:Creator|Creator|fdName,type:sys.org.element|sys.org.element|String}, elementName:xxx , structureBlock:xxxx}
	function _SQLComponent_FieldSelectUnion_Init(config){
		var self = this;
		var fieldSuffix = "Items"; 
		self.block = config.block ? config.block : "";
		if(config && config.field){
			var field = config.field;
			var nameArr = field.name.split("|");
			var typeArr = field.type.split("|");
			if(nameArr.length == typeArr.length){
				var len = nameArr.length;
				for(var i = 0;i < len;i++){
					var params = {};
					params.name = config.elementName;
					var type;
					// 当前下拉菜单的选项是从上一个下拉菜单的类型里面来的
					if(i == 0){
						type = config.currentModelName;
					}else{
						type = typeArr[i - 1];
					}
					var model = SQLModelCache.getModelByKey(type);
					// 缓存找不到，则去数据库里面查询
					if(!model){
						var c = {"modelName":type,"isXform":"false"};
						var rs = SQLDataSource_findFieldDict(c);
						c.data = rs.data;
						model = new SQLDataSource(c);
						SQLModelCache.addModelCache(model);
					}
					// 通过拼接字符串，获取所属的字段集合
					var n = config.structureBlock + fieldSuffix;
					params.options = model[n];
					params.val = nameArr[i];
					var s = new SQLComponent_Select(self,params);
					if(i > 0){
						// 构建父子关系
						s.parent = self.selectComponents[i-1];
						self.selectComponents[i-1].child = s;
					}
					self.selectComponents.push(s);
				}
			}else{
				console.error("SQLComponent_FieldSelectUnion--显示值和类型的长度不一致（name："+field.name+"；type："+field.type+"）");
			}
		}else{
			self.addSelectComponent(config);
		}
	}

	// config : {currentModelName : xxx, elementName:xxx , structureBlock:xxxx}
	function SQLComponent_FieldSelectUnion_AddSelectComponent(config){
		var self = this;
		var fieldSuffix = "Items";
		var params = {};
		params.name = config.elementName;
		var type = config.currentModelName;
		var model = SQLModelCache.getModelByKey(config.currentModelName);
		// 缓存找不到，则去数据库里面查询
		if(!model){
			var c = {"modelName":type,"isXform":"false"};
			var rs = SQLDataSource_findFieldDict(c);
			c.data = rs.data;
			model = new SQLDataSource(c);
			SQLModelCache.addModelCache(model);
		}
		// 通过拼接字符串，获取所属的字段集合
		var n = config.structureBlock + fieldSuffix;
		params.options = model[n];
		var s = new SQLComponent_Select(self,params);
		if(self.selectComponents.length > 0){
			var p = self.selectComponents[self.selectComponents.length - 1];
			while(p.child){
				p = p.child;
			}
			// 构建上下级关系
			p.child = s;
			s.parent = p;
		}
		self.selectComponents.push(s);
		
		return s;
	}

	// config: {currentModelName : xxx, elementName:xxx , structureBlock:xxxx , node : xxx}
	function SQLComponent_FieldSelectUnion_AppendNode(config){
		var self = this;
		var s = self.addSelectComponent(config);
		s.hasPlzOption = true;
		config.node.after(s.buildNode(self.initChild));
		s.domNode.change();
	}

	function SQLComponent_FieldSelectUnion_GetCurrentType(){
		var type = "";
		var self = this;
		var selectType = self.type;
		type = selectType.substring(selectType.lastIndexOf("|") + 1);
		return type;
	}
	
	function SQLComponent_FieldSelectUnion_GetCurrentVal(){
		var val = "";
		var self = this;
		var selectVal = self.val;
		val = selectVal.substring(selectVal.lastIndexOf("|") + 1);
		return val;
	}
	
	function SQLComponent_FieldSelectUnion_GetCurrentOption(){
		var self = this;
		var selectCom = self.getCurrentSelectComponent();
		var option = selectCom.findOption(self.getCurrentVal());
		return option;
	}
	
	function SQLComponent_FieldSelectUnion_GetCurrentSelectComponent(){
		var self = this;
		//一般最后一个组件就是当前的下拉组件
		if(self.selectComponents.length > 0){
			var com = self.selectComponents[self.selectComponents.length - 1];
			var child = com.child;
			while(child){
				com = child;
				child = com.child;
			}
			return com;
		}
	}

	// 删除子组件
	function SQLComponent_FieldSelectUnion_DeleteComponent(com){
		while(com){
			var self = this;
			SQLStructure_SpliceArray(self.selectComponents,com);
			com = com.child;
			self.deleteComponent(com);
		}		
	}
	
	function SQLComponent_FieldSelectUnion_Clear(){
		var self = this;
		self.val = ""; // 字段值
		self.type = ""; // 字段类型
		self.text = ""; // 显示文字
		for(var i in self.selectComponents){
			if(self.selectComponents[i].clear){
				self.selectComponents[i].clear();
			}
		}
		self.selectComponents = [];
	}

	function SQLComponent_FieldSelectUnion_InitChild(component){
		component.domNode.on("change",function(){
			var $option = $(this).find("option:selected"); 
			var val = $option.val();
			
			component.val = val;
			
			// 清空子元素
			var child = component.child;
			if(child){
				child.clear();
			}
			
			component.child = null;
			var option = component.findOption(val);
			if(!option){
				option = {"type":"","name":""};
			}
			if(component.union){
				// 更新组件
				component.union.deleteComponent(child);
				// 更新type
				component.type = option.type;
				component.text = option.name;
				// 更新父的路径
				var parent= component.parent;
				var selectVal = val;
				var selectType = component.type;
				var selectText = component.text;
				while(parent){
					selectVal = selectVal == "" ? parent.val : parent.val + "|" + selectVal;
					selectType = selectType == "" ? parent.type : parent.type + "|" + selectType;
					selectText = selectText == "" ? parent.text : parent.text + "." + selectText;
					parent = parent.parent;
				}
				var argu = {};
				argu.sourceComp = component;
				argu.val = val;
				argu.item = option;
				component.union.val = selectVal;
				component.union.type = selectType;
				component.union.text = selectText;
				if(component.union.nodeChange){
					component.union.nodeChange(argu);	
				}
			}
		});
	};

	function SQLComponent_FieldSelectUnion_NodeChange(argu){
		var self = this;
		if(argu.item && argu.item.type){
			var type = argu.item.type;
			var config = {};
			// 如果属性是对象类型
			if(type.indexOf("com.landray.kmss") > -1){
				config = {
					"currentModelName" : type,
					"elementName" : self.container.defaultPropertyName,
					"structureBlock" : self.block
				};
				// 当前节点
				config.node = argu.sourceComp.domNode;
				self.appendNode(config);
			}
		}
		if(self.afterNodeChange){
			self.afterNodeChange();
		}
	};

	function SQLComponent_FieldSelectUnion_GetKeyData(){
		
		var data = {};
		data.name = this.val;
		data.type = this.type;
		data.text = this.text;
		if(this.selectComponents.length > 0){
			if(this.selectComponents.length == 1){
				data.orgType = this.selectComponents[0].domNode.find("option:selected").attr("data-orgtype") || "";
			}else{
				data.orgType = this.selectComponents[this.selectComponents.length - 2].domNode.find("option:selected").attr("data-orgtype") || "";
			}
		}
		// 如果是枚举类型，保存枚举值
		var option = this.getCurrentOption();
		if(option && option.type == 'enum'){
			data.enumValues = option.enumValues;
		}
		return data;
	}

	function SQLComponent_FieldSelectUnion_UpdateDynamicOption(newDynamicOptions){
		var self = this;
		if(self.selectComponents.length > 0){
			// 仅第一个下拉选项需要更新
			self.selectComponents[0].updateDynamicOption(newDynamicOptions);
		}
	}

	/******************************SQLComponent_FieldSelectUnion end ***********************************/

	/******************************SQLComponent_ValueUnion start ***********************************/
	function SQLComponent_ValueUnion(container,config){
		this.container = container ? container : {};
		
		this.name;
		this.formatName;
		this.val;
		
		this.valName;
		this.fieldVal;
		
		this.JSONItem; 
		
		this.domNode;
		
		this.init = _SQLComponent_ValueUnion_Init;
		this.clear = SQLComponent_ValueUnion_Clear;
		this.findOptionByJSONNode = SQLComponent_SelectCommon_FindOptionByJSONNode;
		this.buildNodeByJSONNode = SQLComponent_SelectCommon_BuildNodeByJSONNode;
		this.getKeyData = SQLComponent_ValueUnion_GetKeyData;
		
		this.init(config);
	}

	function _SQLComponent_ValueUnion_Init(config){
		this.name = config.name?config.name:"";
		this.valName = config.valName?config.valName:"";
		this.formatName = config.formatName?config.formatName:"";
		this.val = config.val?config.val:"";
		this.fieldVal = config.fieldVal?config.fieldVal:{};
	}

	function SQLComponent_ValueUnion_Clear(){
		
	}

	function SQLComponent_ValueUnion_GetKeyData(){
		
		var data = {};
		var self = this;
		if(!self.domNode){
			return data;
		}
		data.val = self.val;
		var val = {};
		
		var vals = self.domNode.closest("td").find("[name*='"+ self.valName +"']");
		if(vals.length > 0){
			val.hasValue = true;
			var arrs = [];
			for(var i = 0;i < vals.length;i++){
				var v = vals[i];
				if(v.type=="radio"){
					if(!v.checked){
						continue;
					}
				}
				var arr = {};
				arr.name = v.name;
				arr.value = v.value;
				
				arrs.push(arr);
			}
			val.data = arrs;
			var option = self.findOptionByJSONNode(self.val);
			// 是否支持输入多个
			if(option.canInputMulti && option.canInputMulti == 'true'){
				val.canInputMulti = true;
			}
		}else{
			val.hasValue = false;
		}
		data.fieldVal = val;
		return data;
	}
	/******************************SQLComponent_ValueUnion end ***********************************/
	
	window.SQLComponent_DrawUnit = SQLComponent_DrawUnit;
	window.SQLComponent_Select = SQLComponent_Select;
	window.SQLComponent_FieldSelectUnion = SQLComponent_FieldSelectUnion;
	window.SQLComponent_ValueUnion = SQLComponent_ValueUnion;
    window.SQLComponent_SelectShow = SQLComponent_SelectShow;
    window.SQLComponent_SelectdisplayName = SQLComponent_SelectdisplayName;
    window.SQLComponent_count = SQLComponent_count;
})()


