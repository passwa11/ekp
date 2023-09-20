define([ "dojo/_base/declare","mui/search/SearchBar"],
		function(declare,SearchBar){
	
	return declare("mui.commondialog.template.DialogSearchBar",[ SearchBar],{
		
		searchUrl : "",
		
		//搜索结果直接挑转至searchURL界面
		jumpToSearchUrl:false,
				
		//是否需要输入提醒
		needPrompt:false
		
	});
	
	
	
});