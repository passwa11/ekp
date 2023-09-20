/**
 * History API形式实现(鉴于ie8、ie9问题暂时不使用)
 */
define(function(require, exports, module) {
	
	var BaseHistory = require('./base');
	
	var History = BaseHistory.extend({
		
		listen : function(callback){
			var self = this;
			window.addEventListener("popstate", function() {
			    var state = history.state;
			    if(state){
			    	var uuId = state.uuId,
			    		stateObject = self.states[uuId];
			    	callback && callback(stateObject);
			    }
			});
		},
		
		push : function(state){
			state = this.generateState(state);
			history.pushState({ uuId : state.uuId }, null, state.___url);
		},
		
		replace : function(state){
			state = this.generateState(state);
			history.replaceState({ uuId : state.uuId }, null, state.___url);
		},
		
		ensureURL : function(params){
			var search = window.location.search,
				href = Com_Parameter.ContextPath + 'sys/portal/page.jsp' + search;
			for(key in params){
				var value = params[key];
				if(key == 'j_start'
					&& value.startsWith(Com_Parameter.ContextPath)){
					value = value.substr(Com_Parameter.ContextPath.length);
				}
				if(key == 'j_target' && value == 'rIframe'){
					value = 'iframe'; //修正target
				}
				href = Com_SetUrlParameter(href, key , value);
			}
			return href;
		},
		
		getParameter : function(param){
			var url = window.location.href;
			return Com_GetUrlParameter(url, param);
		}
		
	});
	
	module.exports = History;
	
});