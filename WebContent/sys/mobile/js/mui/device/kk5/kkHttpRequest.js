define(['dojo/io-query','lib/kk5/kk5'],
		function(ioq,kk) {

	//事件代理能力
	function Emitter() {
		var eventTarget = document.createDocumentFragment();
		function addEventListener(type, listener, useCapture, wantsUntrusted) {
			return eventTarget.addEventListener(type, listener, useCapture, wantsUntrusted);
		}
		function dispatchEvent(event) {
			return eventTarget.dispatchEvent(event);
		}
		function removeEventListener(type, listener, useCapture) {
			return eventTarget.removeEventListener(type, listener, useCapture);
		}
		this.addEventListener = addEventListener;
		this.dispatchEvent = dispatchEvent;
		this.removeEventListener = removeEventListener;
	}
	
	var KKHttpRequest = function(){
		Emitter.call(this)
	};
	KKHttpRequest.prototype.method = 'GET';
	KKHttpRequest.prototype.async = true;
	KKHttpRequest.prototype.headers = {};
	//open方法暂时不支持鉴权
	KKHttpRequest.prototype.open = function(method, url, async){
		this.method = method || 'GET';
		this.url = url;
		this.async = async;//没意义参数,kk.proxy.request暂时不支持同步方式
		this.readyState = 1;//open() 方法已调用,readyState置为1
	};
	KKHttpRequest.prototype.send = function(body){
		var self = this;
		this.readyState = 2; 
		kk.ready(function(){
			var kkdef = kk.proxy.request({
				url : self.url,
				data : (body && typeof(body)=='string')  ? ioq.queryToObject(body) : body,
				headers : self.headers
			});
			kkdef.then(function(data){
				//success
				self.responseText = data;
				self.status = 200;
				self.statusText = 'OK';
				self.readyState = 4;
				self.onreadystatechange && self.onreadystatechange();
				self.dispatchEvent(self.__evt('load'));
			},function(code,msg){
				self.status = code;
				self.statusText = msg;
				self.readyState = 4;
				self.onreadystatechange && self.onreadystatechange();
				self.dispatchEvent(self.__evt('error'));
			});
		});
	};
	KKHttpRequest.prototype.setRequestHeader = function(name,value){
		this.headers[name] = value;
	};
	KKHttpRequest.prototype.getResponseHeader = function(name){
		//暂不支持,KK.proxy.request似乎没有返回相关信息
	};
	KKHttpRequest.prototype.__evt = function(type){
		var evt = document.createEvent('Event');
		evt.initEvent(type,true,true);
		evt.target = this;
		return evt;
	};
	
	return KKHttpRequest;
	
});