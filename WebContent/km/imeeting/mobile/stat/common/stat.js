define(['mui/util','dijit/registry','dojo/query'],function(util,registry,query){
	var stat = {
			
		//执行入口	
		statExecutor : function(){
			var url = util.setUrlParameter(location.href, "method", "statChart"),
				chart = registry.byId('chart');
			url = this.param(url);
			chart.set('url',url);
		},
		
		param : function(url){
			var condtionSection = query('#div_condtionSection'),
				conditionItems = query('input',condtionSection[0]);
			for(var i = 0;i < conditionItems.length;i++){
				var conditionItem = conditionItems[i],
					name = conditionItem.name,
					value = conditionItem.value;
				url = util.setUrlParameter(url, name, value);
			}
			return url;
		}
			
	};
	return stat;
});