/**
 * 具备hash能力的JsonStoreList
 */
define([
    "dojo/_base/declare",
	"./JsonStoreList",
	"./JsonStoreListHashMixin"
	], function(declare, JsonStoreList, JsonStoreListHashMixin) {
	
	return declare("mui.list.HashJsonStoreList", [JsonStoreList, JsonStoreListHashMixin], {
		
	});
});