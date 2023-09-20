/**
 * 关系图对象工具
 */
(function(){
	var JSONComponent = new JSONComponent();
	
	var _radioNameNum = 0;
	
	function JSONComponent(){
		this.type = "";
		this.source = {};
		this.blocks = [];
		
		this.init = _JSONComponent_InitSource;
		this.findItemByPath = JSONComponent_FindItemByPath;
		this.getItem = _JSONComponent_GetItem;
		this.getHtmlByType = JSONComponent_GetHtmlByType;
		this.getDateControlHtml = _JSONComponent_GetDateControlHtml;
		this.getDateFormatHtml = _JSONComponent_GetDateFormatHtml;
		
		this.triggleSelectDateTime = JSONComponent_triggleSelectDateTime;
	}
	
	function _JSONComponent_InitSource(source){
		if(source){
			this.source = source;
			if(source.blocks){
				this.blocks = source.blocks;
				_JSONComponent_CreateAllItemsPath(this.blocks);
			}else{
				console.error("类型关系图异常：blocks不能为null！");
			}
		}
	}
	
	/**
	 * 构建path
	 * @param root
	 * @returns
	 */
	function _JSONComponent_CreateAllItemsPath(root){
		var node = root;
		_JSONComponent_CreateItemPath(node,null);
	}
	
	/**
	 * @param node
	 * @returns
	 */
	function _JSONComponent_CreateItemPath(node,parentNode){
		for(var i = 0;i < node.length;i++){
			var n = node[i];
			if(parentNode){
				n.path = parentNode.path + "." + n.name.split("|")[0];
				n.parent = parentNode;
			}else if(n.name){
				n.path = n.name.split("|")[0];
			}
			if(n.hasOwnProperty("options")){
				_JSONComponent_CreateItemPath(n.options,n);	
			}
		}
	}
	
	/**
	 * 根据路径找到options
	 * @param path
	 * @returns 
	 */
	function JSONComponent_FindItemByPath(path){
		
		var self = this;
		if(path){
			var paths = path.split(".");
			var obj = self.blocks;
			var item;
			for(var i = 0;i < paths.length;i++){
				if(paths[i] != ''){
					item = self.getItem(paths[i],obj);
					if(item && item.hasOwnProperty("options")){
						obj = item["options"];
					}	
				}
			}
			return item;
		}else{
			return self.blocks;
		}
	}
	
	/**
	 * @param field
	 * @param obj
	 * @returns 返回null，即报错
	 */
	function _JSONComponent_GetItem(field,obj){
		// Array.isArray函数IE8不支持，下面这段逻辑用于兼容IE8
		if (!Array.isArray) {
			  Array.isArray = function(arg) {
			    return Object.prototype.toString.call(arg) === '[object Array]';
			  };
		}		
		if(Array.isArray(obj)){
			var nameField = "name";
			for(var i = 0;i < obj.length;i++){
				var o = obj[i];
				if(o.hasOwnProperty(nameField)){
					var n = o[nameField];
					var nArr = n.split("|");
					for(var j = 0;j < nArr.length;j++){
						if(nArr[j] == field){
							return o;
						}
					}
				}
			}
		}else if(typeof(obj) == 'object'){
			if(obj.hasOwnProperty(field)){
				return obj[field];
			}
		}
	}
	
	// config : {type : xxx,formatName: xx, name : xxx, val : [{name:xx,val:xxx},{name:xx,val:xxx}]}
	function JSONComponent_GetHtmlByType(config){
		
		var html = "";
		var self = this;
		var val = config.val?config.val:[];
		html += "<div class='component' style='display:inline-block;'>";
		if(config.type == 'inputText'){
			html += "<input type='text' class='inputsgl' name='"+ config.name +"'";
			
			if('formatVal'==config.name){
				html += "validate='number number scaleLength(0)'";
			}
			if(config.canInputMulti && config.canInputMulti == 'true'){
				html += " placeholder='"+ DbcenterLang.multiInputTip +"'";
			}
			html += " value='"+ _JSONComponent_GetValByName(val,config.name) +"'";
			html += " />";
		}else if(config.type == 'dataTime'){
			
			var v = config;
			v.val = _JSONComponent_GetValByName(val,config.name);
			html += self.getDateControlHtml(v);
		}else if(config.type == 'doubleInputText'){
			html += DbcenterLang.from;
			html += "<input type='text' class='inputsgl' name='"+ config.name +"_start' ";
			html += " value='"+ _JSONComponent_GetValByName(val,config.name+"_start") +"'";
			html += "/>";
			html += DbcenterLang.to;
			html += "<input type='text' class='inputsgl' name='"+ config.name +"_end' ";
			html += " value='"+ _JSONComponent_GetValByName(val,config.name+"_end") +"'";
			html += "/>";
		}else if(config.type == 'doubleDate'){
			var dateConfig = {};
			dateConfig.name = config.name + "_start";
			dateConfig.val = _JSONComponent_GetValByName(val,config.name+"_start");
			html += "</br>";
			html += DbcenterLang.from;
			html += self.getDateControlHtml(dateConfig);
			html += "</br>";
			html += DbcenterLang.to;
			dateConfig = {};
			dateConfig.name = config.name + "_end";
			dateConfig.val = _JSONComponent_GetValByName(val,config.name+"_end");
			html += self.getDateControlHtml(dateConfig);
		}else if(config.type == 'enumDraw'){
			//枚举型
			var items = config.option;
			if(items){
				var valuesArray = items.enumValues;
				var values = [];
				// 默认隔开空格个数
				var appendSpaceHtml = '&nbsp;&nbsp;';
				// 例如inputValue为： m;f
				val = config.val?config.val:'';
				if(val != ''){
					for(var i = 0;i < val.length;i++){
						values.push(val[i].value);
					}
				}
				var enumNameNum = ++_radioNameNum;
				// 枚举的修改为单选
				for(var i = 0; i < valuesArray.length;i++){
					html += "<label><input type='radio' name='"+ config.name + "_" + enumNameNum + "' ";
					// 只要存在于该数组里面
					if(values && $.inArray(valuesArray[i].fieldEnumValue,values) > -1){
						html += " checked";
					}
					html += " value='" + valuesArray[i].fieldEnumValue + "'>"+ valuesArray[i].fieldEnumLabel +"</label>" + appendSpaceHtml;
				}	
			}
		}
		html += "</div>";
		return html;
	}
	
	function _JSONComponent_GetDateControlHtml(config){
		
		var html = "";
		var val = config.val?config.val:'';
		html += "<div class='inputselectsgl' style='width:150px;' onclick='JSONComponent.triggleSelectDateTime(event,this);'>";
		html += "<div class='input'><input name='" + config.name + "' type='text' validate='__datetime' value='"+ val +"' /></div>";
		html += "<div class='inputdatetime'></div>";
		html += "</div>";
		return html;
	}
	
	function _JSONComponent_GetDateFormatHtml(config){
		var html = "";
		// 添加日期格式化的下拉框
		var formatOptions = [{"value":"year","text":DbcenterLang.year},{"value":"yearMonth","text":DbcenterLang.yearMonth},{"value":"yearMonthDay","text":DbcenterLang.yearMonthDay},
							{"value":"dateTime","text":DbcenterLang.dateTime}];
		html += "<select name='"+ config.formatName +"' style='width:90%;'>";
		for(var i = 0;i < formatOptions.length;i++){
			html += "<option value='" + formatOptions[i].value + "' ";
			if(config.format && config.format == formatOptions[i].value){
				html += " selected ";
			}
			html += ">";
			html += formatOptions[i].text;
			html += "</option>";
		}
		html += "</select>";
		return html;
	}
	
	//日期控件触发
	function JSONComponent_triggleSelectDateTime(event,dom){
		
		var input = $(dom).find("input");
		var t = input.attr("attr");
		if(t == "date"){
			selectDate(event,input);
		}else if(t == "time"){
			selectTime(event,input);
		}else{
			selectDateTime(event,input);
		}
	}
	
	// 根据name获取arr里面对应的属性值，当key不设置的时候，默认取value
	function _JSONComponent_GetValByName(arr,name,key){
		var val = '';
		var keyAtt = "value";
		if(typeof(key) != 'undefined'){
			keyAtt = key;
		}
		for(var i = 0;i < arr.length;i++){
			if(name == arr[i].name){
				val = arr[i][keyAtt];
				break;
			}
		}
		return val;
	}
	
	window.JSONComponent = JSONComponent;
})();