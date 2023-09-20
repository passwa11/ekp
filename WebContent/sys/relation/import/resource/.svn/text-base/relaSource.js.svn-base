define(function(require, exports, module) {
	var source = require("lui/data/source"),
		$ = require("lui/jquery"),
		strutil = require('lui/util/str');          
	
	var relaSource = source.AjaxJson.extend({
		_request: function(url, dataType, done, ctx) {
			var self = this;
	        var callbackId = 'callback_' + this.index++;
	        this.callbacks.push(callbackId);
			url = self.getEnv().fn.formatUrl(url); 
			var reqType = dataType=='json'?'text': dataType;
	        $.ajax({
	        	url: url,
	        	//POST防止关联项太多会报错
	        	method : "post",
	        	dataType: reqType,
	        	data: this.params, 
	        	jsonp:"jsonpcallback",
	        	success: function(data,textStatus, jqXHR) {
	        		var rtnData = data; 
	        		if(typeof(data)=='string' && (dataType=='json' || dataType=='jsonp')){
	        			try {
	        				rtnData = strutil.toJSON(data);
	        			} catch(e) {
	        				if (window.console) {
	        					window.console.error(e, data);
	        				}
	        				rtnData = {};
	        			}
	        		}
	                if ($.inArray(callbackId, self.callbacks) > -1) {
	                    delete self.callbacks[callbackId];
	                    self.onload(rtnData, done, ctx);
	                    self.emit("relaDataLoad", {"relationEntry":self.config.relationEntry,"data":rtnData});
	                }
	            },
	            error: function(request, textStatus, errorThrown) {
	            	if ($.inArray(callbackId, self.callbacks) > -1) {
	                    delete self.callbacks[callbackId];
	                }
	                var __status = request.status;
	                var msg = "source:id="+self.cid+"\r\n"+'<bean:message bundle="sys-relation" key="sysRelationMain.helptips1" />'+url+'<bean:message bundle="sys-relation" key="sysRelationMain.helptips2" />'+'\r\n'+'<bean:message bundle="sys-relation" key="sysRelationMain.helptips17" />'+(textStatus == "parsererror" ? '<bean:message bundle="sys-relation" key="sysRelationMain.helptips3" />' : textStatus)+";\r\n";
	                msg += '<bean:message bundle="sys-relation" key="sysRelationMain.helptips4" />'+strutil.encodeHTML(request.responseText)+"\r\n";
	                msg += '<bean:message bundle="sys-relation" key="sysRelationMain.helptips5" />'+(errorThrown.stack ? errorThrown.stack : errorThrown )+"";
	                if (window.console) 
						window.console.error(msg);
					if (__status == 403) {// 无权限等错误不返回前端显示
						self.onload([], done, ctx);
					} else {
						var e = request.getResponseHeader("error");
						if ($.trim(e) != "") {
							self.emit("error", decodeURIComponent(e));
						} else {
							self
									.emit(
											"error",
											'<bean:message bundle="sys-relation" key="sysRelationMain.helptips6" />'
													+ (textStatus == "parsererror"
															? '<bean:message bundle="sys-relation" key="sysRelationMain.helptips7" />'
															: textStatus)
													+ '<br>'+'<bean:message bundle="sys-relation" key="sysRelationMain.helptips8" />'
													+ (errorThrown.message
															? errorThrown.message
															: errorThrown));
						}
					}
	            }
	        });
		}
	});
	
	
	module.exports = relaSource;
});