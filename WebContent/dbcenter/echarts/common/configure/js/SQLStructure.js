
(function(){
	
	/**
	 * sql结构体对象
	 */
	function SQLStructure(components){
		this.isInit = false;
		
		this.relationDiagram;
		
		this.dataSource; // model信息
		
		this.components = {}; // key : {"work": ,"required": ,"fun": ,"arr": ,"dom": } 
		
		this._init = _SQLStructure_Init;
		this.init = SQLStructure_Init;
		this.initStructure = _SQLStructure_InitStructure;
		this.addComponent = SQLStructure_AddComponent;
		this.newComponent = SQLStructure_NewComponent;
		this.getComponentByKey = SQLStructure_GetComponentByKey;
		this.clear = _SQLStructure_Clear;
		this.readData = SQLStructure_ReadData; // 提交时，覆盖数据，该方法一般被覆盖
		this.spliceArray = SQLStructure_SpliceArray;
		this.swapArray = SQLStructure_SwapArray;
		this.isValidByKey = SQLStructure_IsValidByKey;
		this.updateDisplayComponent = SQLStructure_UpdateDisplayComponent;
		
		this._init(components);
	}
	
	function _SQLStructure_Init(components){
		if(components){
			for(var key in components){
				this.addComponent(key,components[key]);
			}
		}
	}

	/**
	 * config config = {modelName modelNameText data isXform whereConditions selectValues filterItems}
	 * 
	 */
	function SQLStructure_Init(config){
		this.clear();
		this.dataSource = new SQLDataSource(config);
		SQLModelCache.addModelCache(this.dataSource);
		this.initStructure(config);
		
		this.isInit = true;
		$(document).trigger($.Event("SQLStructure_Init"),{"structure":this});
	}

	/*
	 * 初始化结构体
	 * */
	function _SQLStructure_InitStructure(config){
		var self = this;
		if(config){
			for(var key in config){
				var arr = config[key];
				var com = self.getComponentByKey(key);
				if(com && com.work){
					for(var i = 0;i < arr.length;i++){
						self.newComponent(key,com["dom"],arr[i]);
					}					
				}
			}	
		}
		
	}
	
	function SQLStructure_AddComponent(key,val){
		this.components[key] = val;
	}
	
	function SQLStructure_GetComponentByKey(key){
		return this.components[key];
	}
	
	function SQLStructure_NewComponent(type,table,value){
		var $tr;
		var self = this;
		var com = this.getComponentByKey(type);
		if(!table){
			table = com["dom"];
		}

		var funInstance = new com["fun"](this,value,type);
		
		$(document).trigger($.Event("SQLStructure_BeforeAddTrWhenInit"),{"component":funInstance,"type":type,"value":value});
		
		// 添加到数组
		com["arr"].push(funInstance);
		
		// 构建行
		$tr = funInstance.createTrNode(value);  //跳到 SQLFilterItem.js SQLFilterItem_CreateTrNode
		
		$(table).append($tr);
		
		$(document).trigger($.Event("SQLStructure_AfterAddTrWhenInit"),{"component":funInstance,"type":type,"collection":com});
		
		return funInstance;
	}
	
	//校验必填
	function SQLStructure_IsValidByKey(key){
		if(key){
			var component = this.getComponentByKey(key);
			if(component.work == true){
				if(component.required){
					var arr = component.arr;
					if(arr.length == 0){
						return false;
					}else{
						for(var i = 0;i < arr.length;i++){
							var component = arr[i];
							// 只要有一个有效，就有效
							if(component.isValid && component.isValid()){
								return true;
							}
						}
					}
				}else{
					return true;
				}
			}else{
				return true;
			}
		}
		return false;
	}
	
	function SQLStructure_UpdateDisplayComponent(){
		// 处理必填的显示和隐藏
		for(var key in this.components){
			var required = this.components[key]["required"];
			var $table = this.components[key]["dom"];
			var $span = $table.next();
			var found = false;
			if($span.length > 0){
				if($span[0].tagName=='SPAN' && $span.text()=='*'){
					found = true;
				}
			}
			if(required){
				if(found){
					$span.show();
				}else{
					$table.after("<span class='txtstrong' style='padding-left:2px'>*</span>");
				}
			}else {
				if(found){
					$span.hide();
				}
			}
		}
	}

	function _SQLStructure_Clear(){
		for(var key in this.components){
			for(var i = 0;i < this.components[key]["arr"].length;i++){
				var w = this.components[key]["arr"][i];
				w.clear();
			}
			this.components[key]["arr"].length = 0;
		}
		
		this.isInit = false;
		this.dataSource = null; // model信息
		
	}


	function SQLStructure_SpliceArray(array, spliced) {
		for (var i = 0; i < array.length; i++)	{
			if (array[i] == spliced) {
				array.splice(i, 1);
				break;
			}
		}
	}

	function SQLStructure_SwapArray(array, index, target) {
		array[index] = array.splice(target, 1, array[index])[0];
	}

	function SQLStructure_ReadData(){
		
	}
	
	window.SQLStructure = SQLStructure;
	window.SQLStructure_SpliceArray = SQLStructure_SpliceArray;
})()


