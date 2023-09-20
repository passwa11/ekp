/**
 * 支持Hash Mixin
 */
define([
	'dojo/_base/declare',
	'mui/hash'
], function(declare, hash){
	
	return declare('mui.list.NavViewHashMixin', [], {
		
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
		},
		
		/**
		 * 切换视图
		 * 	1、替换hash path
		 * 	2、清空query参数
		 */
		handleViewChanged$: function(view, index){
			if(!hash.canHash()){
				return;
			}
			// 替换hash path
			hash.replacePath([index]);
			if(this.shouldClearQuery){
				// 清空query参数
				hash.clearQueryR();
			}
			this.shouldClearQuery = true;
		}
		
	});
	
});