define([ "dojo/_base/declare","mui/search/SearchBar" ,"mui/i18n/i18n!sys-mobile:mui"],
		function(declare, SearchBar,Msg) {

			return declare("sys.zone.mobile.FilterSearchBar",
					[ SearchBar ],{
						
						//员工黄页搜索请求
						searchUrl : " ",
						
						//搜索结果直接挑转至searchURL界面
						jumpToSearchUrl:false,
						
						//搜索关键字
						keyword : "",
						
						//提示文字
						placeHolder : Msg['mui.search.search'],

						//是否需要输入提醒
						needPrompt:false,
						
						orgType:null
		});
});
