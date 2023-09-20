define([
    "dojo/_base/declare",
	"dojox/mobile/EdgeToEdgeStoreList",
	"./_JsonStorCardListMixin",
	'./_ListNoDataMixin'
	], function(declare, EdgeToEdgeStoreList, _JsonStorCardListMixin,_ListNoDataMixin) {
	
	return declare("sys.mportal.JsonStoreCardList", 
			[EdgeToEdgeStoreList,  _JsonStorCardListMixin, _ListNoDataMixin], {
		
	});
});