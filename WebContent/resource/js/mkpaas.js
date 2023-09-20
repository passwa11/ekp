/**
 * MKPAAS与EKP通讯，已经完成如下事情:
 * 1、EKP向MKPAAS请求当前环境信息，如多语言、MKPAAS是否开启待看或者多页签打开方式
 * 2、EKP发送页面高度变化信息给MKPAAS
 * 3、EKP将关闭、新开窗口的权力转交给MKPAAS
 */
(function(){
	// 是否处于MKPAAS应用，需要通讯获取实际情况
	Com_Parameter.inMkPaas = false
	
	var __top = Com_Parameter.top;
	// 格式化链接，加上域名
	function formatUrl(url){
		if(/^https?/.test(url)){
			return url;
		}
		var serverPrefix = Com_Parameter.serverPrefix ? Com_Parameter.serverPrefix.replace(/\/$/,'')  :'';
		var contextPath = Com_Parameter.ContextPath ? Com_Parameter.ContextPath.replace(/\/$/,'') : '';
		var domain = serverPrefix;
		if(contextPath){
			var regExp = new RegExp(contextPath + '$');
			domain = domain.replace(regExp, '');
		}
		if(!domain){
			domain = location.protocol + '://' + location.host + contextPath;
		}
		domain.replace('\/$','');
		return domain + '/' + url.replace(/^\//, '');
	}
	// 发送打开链接事件给MKPAAS
	function postOpenMessage(url, target){
		if(!target){
			var eventObj = Com_GetEventObject();
			target = eventObj ? (eventObj.target ? eventObj.target : eventObj.srcElement) : null;
		}
		var title = target ? target.innerText : (document ? document.title : '');
		var openMessage = {
			functionName: 'fireEvent',
			args: [{
				name: 'openWindow',
				data: { url: formatUrl(url), title: title  }
			}]
		};
		if(window === Com_Parameter.top){
			window.parent.postMessage(openMessage, '*');
		}else{
			__top.Com_PostMessage(__top.parent,openMessage,'*');
			//__top.parent.postMessage(openMessage, '*');
		}
	}
	// a标签添加打开窗口事件
	function addAnchorListener(links){
		if(!links || links.length === 0){
			return;
		}
		for(var i = 0; i < links.length; i++){
			Com_AddEventListener(links[i], 'click', function(event){
				if(__top.Com_Parameter && __top.Com_Parameter.inMkPaas){
					var anchor = event.currentTarget;
					if(anchor.href && !/^\s*javascript:/.test(anchor.href)
							&& !anchor.onclick && anchor.target === '_blank'){
						postOpenMessage(anchor.href, anchor);
						event.preventDefault();
					}
				}
				return true;
			});
		}
	}
	// 如果应用处于MkPass中，打开链接的权力交由MK-PAAS控制
	var originalOpen = window.open;
	window.open = function(url, target, options){
		if(__top.Com_Parameter && __top.Com_Parameter.inMkPaas && target === '_blank'){
			postOpenMessage(url);
			return
		}
		return originalOpen(url, target, options);
	};
	// 监听a标签，如果应用处于MkPaas中，打开链接的权力交由MK-PAAS控制
	if(typeof(MutationObserver) !== 'undefined'){
		var observer = new MutationObserver(function(list){
			for(var i = 0; i < list.length; i++){
				if(!list[i].addedNodes || list[i].addedNodes.length === 0){
					continue;
				}
				for(var j = 0; j < list[i].addedNodes.length; j++){
					var node = list[i].addedNodes[j];
					if(node.nodeType === 1){
						var links = node.querySelectorAll('a');
						addAnchorListener(links);
					}
				}
			}
		});
		observer.observe(document, { childList: true, subtree: true })
	}
	// 如果应用处于MkPass中，关闭的权力交由MK-PAAS控制
	var originalClose = window.close;
	window.close = function(){
		var __top = Com_Parameter.top;
		if(__top.Com_Parameter && __top.Com_Parameter.inMkPaas){
			____Com_CloseWindow();
			return;
		}
		originalClose(arguments);
	};
	// EKP顶层窗口做一些事情..如发送消息获取MKPAAS信息、发送消息通知MKPAAS更新页面高度
	if(window.parent === window.top){
		// 通信唯一标示
		var requestId = 'mk-' + new Date().getTime();
		// 监听响应信息
		Com_AddEventListener(window, 'message', function(event){
			var data = event.data;
			if(!data){
				return;
			}
			// MKPAAS消息
			if(data.type === 'response' && data.id === requestId){
				var result = data.result;
				if(result.userDing === true || result.userDing === 'true' 
						|| result.openLinkInTab === true || result.openLinkInTab === 'true'){
					// 当前页面处于MK-PAAS中，设置全局变量为true同时发送设置标题事件
					Com_Parameter.inMkPaas = true;
					Com_AddEventListener(document, 'DOMContentLoaded', function(){
						var titleMesaage = { functionName: 'fireEvent', args: [{ name: 'setTitle', data:{ title : document.title }  }] };
						setTimeout(function(){
							window.top.postMessage(titleMesaage, '*');
						},1);
					});
				}
			}
		});
		// 获取MK-PAAS信息
		var message = { id: requestId, type: 'request', correspondWay: 'twoway', args: ['getAppConfig','locale','userDing', 'openLinkInTab'] };
		window.parent.postMessage(message, '*');
		// 自适应高度
		var previousHeight = null;
		function postHeightMessage(height){
			 // 如果采用实际高度，会使内部弹层高度计算出错，所以暂定内嵌页的高度不能大于屏幕高度。
			var maxHeight = window.screen.height - 100;
			height = (height || document.documentElement.scrollHeight);
			if(height > maxHeight){
				height = maxHeight;
			}
			if(previousHeight === height){
				return;
			}
			previousHeight = height
			var message = { functionName: 'fireEvent', args: [{ name: 'resize', data:{ height : height }  }] };
			window.parent.postMessage(message, '*');
		}
		Com_AddEventListener(document, 'DOMContentLoaded', function(){
			// EKP中有些页面(如二级页面)，高度计算依赖于屏幕高度，所以先发送一个屏幕高度信息让外层容器设置页面高度=屏幕高度...
			postHeightMessage(window.screen.height);
			// 节点发生变化，可能改变高度，发送高度变化事件
			if(typeof(MutationObserver) !== 'undefined'){
				var observer = new MutationObserver(function(){
					postHeightMessage()
				});
				observer.observe(document, { attributes: true, subtree: true })
			}
		});
	}
})();
