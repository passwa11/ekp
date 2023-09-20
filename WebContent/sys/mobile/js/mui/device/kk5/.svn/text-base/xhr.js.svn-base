/**
 * override dojo xhr for kk5
 */
define(['dojo/_base/declare',
		'dojo/_base/lang',
		'dojo/io-query',
		'dojo/errors/RequestError',
		'dojo/request/handlers',
		'dojo/request/util',
		'dojo/request/watch',
		'dojo/request/xhr',
		'lib/kk5/kk5',
		'mui/util',
		'mui/device/kk5/kkHttpRequest'],
	function(declare,lang,ioq,RequestError,handlers,util,watch,nativeXhr,kk,muiutil,kkHttpRequest){
	
	var KKHttpRequest = kkHttpRequest;
	
	function handleResponse(response, error){
		var _xhr = response.xhr;
		response.status = response.xhr.status;
		try {
			response.text = _xhr.responseText;
		} catch (e) {}
		if(response.options.handleAs === 'xml'){
			response.data = _xhr.responseXML;
		}
		if(!error){
			try{
				handlers(response);
			}catch(e){
				error = e;
			}
		}
		if(error){
			this.reject(error);
		}else if(util.checkStatus(_xhr.status)){
			this.resolve(response);
		}else{
			error = new RequestError('Unable to load ' + response.url + ' status: ' + _xhr.status, response);
			this.reject(error);
		}
	}
	
	isValid = function(response){
		return response.xhr.readyState; //boolean
	};
	isReady = function(response){
		return 4 === response.xhr.readyState; //boolean
	};
	cancel = function(dfd, response){
		//Nothing to do now
	};
	addListeners = function(_xhr, dfd, response){
		function onLoad(evt){
			dfd.handleResponse(response);
		}
		function onError(evt){
			var _xhr = evt.target;
			var error = new RequestError('Unable to load ' + response.url + ' status: ' + _xhr.status, response);
			dfd.handleResponse(response, error);
		}
		_xhr.addEventListener('load', onLoad, false);
		_xhr.addEventListener('error', onError, false);
		return function(){
			_xhr.removeEventListener('load', onLoad, false);
			_xhr.removeEventListener('error', onError, false);
			_xhr = null;
		};
	};
	
	var nativeResponseTypes = {
		'blob': 'arraybuffer',
		'document': 'document',
		'arraybuffer': 'arraybuffer'
	};
	
	function getHeader(headerName){
		return this.xhr.getResponseHeader(headerName);
	}
	
	var undefined,
		defaultOptions = {
			data: null,
			query: null,
			sync: false,
			method: 'GET'
		};
	
	function xhr(url, options, returnDeferred){
		//本地存在对应文件
		var path = url.indexOf('?') > -1 ? url.substring(0,url.indexOf('?')) : url;
		if(dojoConfig.fileMapping && dojoConfig.fileMapping[path]){
			var _url = dojoConfig.fileMapping[path] + (url.indexOf('?') > -1 ? url.substring(url.indexOf('?'),url.length) : '');
			return nativeXhr(_url, options, returnDeferred);
		}
		var response = util.parseArgs(
			url,
			util.deepCreate(defaultOptions, options),
			false
		);
		url = response.url;
		options = response.options;
		
		var remover,
			last = function(){
				remover && remover();
			};

		var dfd = util.deferred(
			response,
			cancel,
			isValid,
			isReady,
			handleResponse,
			last
		);
		
		var _xhr = response.xhr = xhr._create();
		
		if(!_xhr){
			dfd.cancel(new RequestError('XHR was not created'));
			return returnDeferred ? dfd : dfd.promise;
		}

		response.getHeader = getHeader;

		if(addListeners){
			remover = addListeners(_xhr, dfd, response);
		}

		var data = options.data,
			async = !options.sync,
			method = options.method;

		try{
			_xhr.open(method, url, async);
			
			_xhr.handleAs = options.handleAs;
			if(options.handleAs in nativeResponseTypes) {
				_xhr.responseType = nativeResponseTypes[options.handleAs];
			}

			var headers = options.headers,
				contentType = 'application/x-www-form-urlencoded';
			if(headers){
				for(var hdr in headers){
					if(hdr.toLowerCase() === 'content-type'){
						contentType = headers[hdr];
					}else if(headers[hdr]){
						_xhr.setRequestHeader(hdr, headers[hdr]);
					}
				}
			}
			if(contentType && contentType !== false){
				_xhr.setRequestHeader('Content-Type', contentType);
			}
			if(!headers || !('X-Requested-With' in headers)){
				_xhr.setRequestHeader('X-Requested-With', 'KKProxyHttpRequest');
			}
			_xhr.setRequestHeader('Landray-Resonponse-Type','offline');
			if(util.notify){
				util.notify.emit('send', response, dfd.promise.cancel);
			}
			_xhr.send(data);
		}catch(e){
			dfd.reject(e);
		}
		watch(dfd);
		_xhr = null;
		
		return returnDeferred ? dfd : dfd.promise;	
	}
	
	xhr._create = function(){
		return new KKHttpRequest();
	};
	
	util.addCommonMethods(xhr);
	
	return xhr;
});