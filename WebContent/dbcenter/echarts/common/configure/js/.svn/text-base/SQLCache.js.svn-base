(function(){
	var SQLModelCache = new SQLModelCache();
	
	function SQLModelCache(){
		this.modelCache = {};
		
		this.addModelCache = SQLModelCache_AddModelCache;
		this.getModelByKey = SQLModelCache_GetModelByKey;
	}
	
	// 参数类型是SQLDataSource
	function SQLModelCache_AddModelCache(model){
		var key = model.modelName;
		var self = this;
		if(self.modelCache.hasOwnProperty(key)){
			return model;
		}else{
			self.modelCache[key] = model;
			return model;
		}
	}
	
	function SQLModelCache_GetModelByKey(key){
		var model;
		var self = this;
		if(self.modelCache.hasOwnProperty(key)){
			model = self.modelCache[key];
		}
		return model;
	}
	
	window.SQLModelCache = SQLModelCache;
	
})()