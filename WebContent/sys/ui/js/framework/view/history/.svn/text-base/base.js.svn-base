/**
 * history基类
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	
	if (typeof String.prototype.startsWith != 'function') {
		String.prototype.startsWith = function (start){
			return this.slice(0, start.length) === start;
		};
	}
	
	var BaseHistory = base.Base.extend({
		
		//state : { uuId,url(原链接),___url(修正后的链接，将会出现在地址栏),target(链接目标),features(特性) }
		states : new Object(),
		
		generateId : function(){
			return '$lui$framework$history$' + new Date().getTime();
		},
		
		generateState : function(state){
			var uuId = this.generateId(),
				___url = this.ensureURL({
					'j_start' : state.url,
					'j_target' : state.target
				}),
				state = $.extend({ uuId : uuId, ___url : ___url },state || {});
			this.states[uuId] = state;
			return state;
		},
		
		listen : function(){
			//for override
		},
		
		push : function(state){
			//for override
		},
		
		replace : function(state){
			//for override
		},
		
		ensureURL : function(params){
			//for override
		},
		
		getParameter : function(param){
			//for override
		}
		
	});
	
	module.exports = BaseHistory;
	
});