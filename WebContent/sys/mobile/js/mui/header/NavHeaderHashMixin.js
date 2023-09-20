/**
 * 支持Hash Mixin
 */
define([
	'dojo/_base/declare',
	'mui/hash'
], function(declare, hash){
	
	return declare('mui.header.NavHeaderHashMixin', [], {
		
		/**
		 * 从Hash中取Tab路径 
		 */
		ensureloadIndex: function(){
			var index = null;
			if(!hash.canHash()){
				return index;
			}
			var pathArray = hash.getPath();
			if(pathArray && pathArray.length > 0){
				var newIndex = Number(pathArray[0])
				return isNaN(newIndex) ? index : newIndex;
			}
			return index;
		}
		
		
	});
	
});