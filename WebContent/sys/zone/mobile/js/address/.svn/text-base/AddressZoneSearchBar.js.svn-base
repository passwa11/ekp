define([ "dojo/_base/declare","mui/search/SearchBar" ,"mui/i18n/i18n!sys-mobile:mui"],
		function(declare, SearchBar,Msg) {

			return declare("mui.address.AddressSearchBar",
					[ SearchBar ],{
						
						//搜索请求地址
						searchUrl : "/sys/organization/mobile/address.do?method=searchList&keyword=!{keyword}&orgType=!{orgType}",
						
						//搜索结果直接挑转至searchURL界面
						jumpToSearchUrl:false,
						
						//搜索关键字
						keyword : "",
						
						//例外类别id
						exceptValue:'',
						
						//提示文字
						placeHolder : Msg['mui.search.search'],

						//是否需要输入提醒
						needPrompt:false,
						
						showLayer:false,
						
						orgType:null
		});
});
