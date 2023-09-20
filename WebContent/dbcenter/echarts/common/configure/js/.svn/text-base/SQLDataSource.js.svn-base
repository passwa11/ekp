/**
 * 
 */
(function(){
	// config : {modelName modelNameText isXform data}
	function SQLDataSource(config){
		this.modelName = config.modelName ? config.modelName : "";
		this.modelNameText = config.modelNameText ? config.modelNameText : this.modelName;
		this.isXform = config.isXform ? config.isXform : "false";
		this.propertyItems = [];
		this.whereItems = [];
		this.selectItems = [];
		this.filterItems = [];
		this.colstatsItems = [];
		this.rowstatsItems = [];
		
		this.initItems = SQLDataSource_InitItems;
		this.clear = SQLDataSource_Clear;
		this.findItemByValue = SQLDataSource_FindItemByValue;
		
		this.initItems(config.data);
	}

	function SQLDataSource_InitItems(data){
		for(var i = 0;i < data.length;i++){
			var d = data[i];
			this.propertyItems.push(new SQLPropertyItem(d));
		}
		// SQLDataSource_CustomPropertyItem 业务处理items
		if(typeof SQLDataSource_CustomPropertyItem != 'undefined' && SQLDataSource_CustomPropertyItem instanceof Function){
			SQLDataSource_CustomPropertyItem(this);
		}else{
			this.whereItems = this.propertyItems;
			this.selectItems = this.propertyItems;
			this.filterItems = this.propertyItems;	
			this.colstatsItems = this.propertyItems;	
			this.rowstatsItems = this.propertyItems;	
		}
	}

	function SQLDataSource_Clear(){
		this.modelName = "";
		this.modelNameText = "";
		this.isXform = "false";
		this.propertyItems = [];
		this.whereItems = [];
		this.selectItems = [];
		this.filterItems = [];
		this.colstatsItems = [];
		this.rowstatsItems = [];
	}
	
	// 根据value查找集合中对应的字段定义
	function SQLDataSource_FindItemByValue(value, collection){
		collection = collection || this.propertyItems;
		for(var i = 0;i < collection.length;i++){
			var item = collection[i];
			// 理论上value不能有重复值
			if(value === item.value){
				return {item:item,index:i};
			}
		}
	}

	function SQLDataSource_findFieldDict(config,callback){
		var modelName = config.modelName;
		var isXform = config.isXform;
		var async = config.async ? config.async : false;
		var url = Com_Parameter.ContextPath + "dbcenter/echarts/db_echarts_template/dbEchartsConfigureCommon.do?method=findFieldDictByModelName&modelName="+modelName+"&isxform="+isXform;
		var rs;
		$.ajax({
			url:url,
			type:"POST",
			async:async,
			success:function(result){
				try{
					result = $.parseJSON(result);
					if(callback){
						rs = callback(result);
					}else{
						rs = result;
					}	
				}catch(e){
					console.log("请求数据报错！" + e);
				}
				
			}
		});
		return rs;
	}

	/******************propertyItem start ****************************/
	function SQLPropertyItem(config){
		this.name = config.fieldText ? config.fieldText : "";
		this.showName = config.fieldPinYinText ? config.fieldPinYinText : "";
		this.value = config.field ? config.field : "";
		this.type = config.fieldType ? config.fieldType : "";
		this.enumValues = config.enumValues ? config.enumValues : "";
		this.enumType = config.enumType ? config.enumType : "";
		// 是否为动态选项，仅用于统计列表的合计
		this.isDynamic = config.isDynamic ? config.isDynamic : false;
		this.orgType = config.orgType;
	}

	/******************propertyItem end ****************************/
	
	window.SQLDataSource = SQLDataSource;
	window.SQLPropertyItem = SQLPropertyItem;
	window.SQLDataSource_findFieldDict = SQLDataSource_findFieldDict;
	
})()
