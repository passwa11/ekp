( function() {
	if (window.LUI != null)
		return;

	LUI = function(id) {
		if (Object.isString(id))
			return LUI.cachedInstances[id];
		else if (id.id)
			return LUI.cachedInstances[id.id];
		return null;
	};
	
	LUI.cachedInstances = {};
	
	LUI.syncAjax = function(requestUrl) {
		var http = {};
		if (window.XMLHttpRequest) {
			http = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			http = new ActiveXObject("Microsoft.XMLHTTP");
		}
		http.open("GET", requestUrl, false);
		http.setRequestHeader("Accept", "text/plain");
		http.setRequestHeader("Content-Type", "text/plain; charset=utf-8");
		http.send(null);
		return http.responseText;
	};
	
	LUI.toJSON = function(str) {
		return (new Function("return (" + str + ");"))();
	};
	
	LUI.stringify = function(val) {
		return domain.stringify(val);
	};
	
	var zindex = 50;
	LUI.zindex = function() {
		zindex = zindex + 10;
		return zindex;
	};
	
	LUI._ready = [];

	LUI.ready = function(fn) {
		if(LUI.luiBeginReady){
			try {
				fn.call(window,LUI.cachedInstances);
			} catch (e) {
				if(window.console && window.console.error) {
					console.error(e);
				}
			}
			return;
		}
		if (!LUI._ready)
			LUI._ready = [];
		LUI._ready.push(fn);
	};
	
	LUI.unplaceholder = function(dom) {
		if (document.documentMode != null && document.documentMode < 10) {
			$(dom).css("background-image", "");
		}
	};
	
	LUI.placeholder = function(dom) {
		
		if (document.documentMode != null && document.documentMode < 10) {
			seajs.use([ 'lui/jquery','lui/util/str' ],function($,str) {
				dom = $(dom);
				var base = seajs.data.base;
				
				dom.find("[placeholder]").each(function() {
					var obj = $(this);
					placeh();
					obj.focus(function(evt){
						if(!evt)
							return;
						obj = $(evt.target);
						obj.css("background-image","");
						obj.css("background-repeat","");
					});
					
					obj.blur(placeh);
					//obj.keyup(placeh);
//					obj.change(placeh);
					function placeh(evt) {
						
						if(evt)
							obj = $(evt.target);
						
						if (obj.val() == "") {
							
//							obj.isBack = true;
							obj.css("background-image",	"url('" + base
									+ "/resource/placeholder.jsp?text="
									+ str.encryptStr(encodeURIComponent(obj.attr("placeholder"))) 
									+ "&fontSize=" + str.encryptStr(obj.css("fontSize"))
									+ "&paddingLeft=" + str.encryptStr(obj.css("paddingLeft"))
									+ "')");
							
							obj.css("background-repeat","no-repeat");
							obj.css("background-position","left center");
						} else {
//							if (obj.isBack) {
								obj.css("background-image","");
								obj.css("background-repeat","");
//							}
						}
					}
				});
			});
		}
	};

	//跨域事件处理
	domain.register("fireEvent", function(event) {
		if (event.type == 'event') {
			seajs.use( [ 'lui/base' ], function(base) {
				var evented = base.byId(event.target);
				if (evented) {
					evented.emit(event.name, event.data);
				}
			});
		} else if (event.type == 'topic') {
			seajs.use( [ 'lui/topic' ], function(topic) {
				if (event.target) {
					topic.group(event.target).publish(event.name, event);
				} else {
					topic.publish(event.name, event);
				}
			});
		}
	});
	
	var dialogAgent = function(luiid){
		this.luiid = luiid;
	};
	
	dialogAgent.prototype.hide =  function(data) {
		domain.call(window.parent,"dialogAgentCall",[this.luiid,"hide",data]);
	};
	
	domain.register("dialogAgent",function(luiid){
		window['$dialog'] = new dialogAgent(luiid);
	});
	domain.register("dialogAgentCall",function(luiid,method,data){
		LUI(luiid)[method](data);
	});
	
	var lastReloadTime = 0;
	//当前页位于顶层,上次无需重新登录请求后10分钟后，再接受其他请求
	domain.register("login",function(){
		seajs.use(['lui/jquery'],function($){
			var now = new Date().getTime();
			if(window.self == window.top && (now-lastReloadTime>1000*60*10)){
				lastReloadTime = now;
				var loginInfo = LUI.syncAjax(Com_Parameter.ContextPath +"sys/ui/resources/user.jsp?s_ajax=true");
				loginInfo = loginInfo!=null?$.trim(loginInfo):loginInfo;
				if(loginInfo!="" && loginInfo == "anonymous"){
					window.location.reload();
				}
			}
		});
	});
	
	LUI.fire = function(event, win) {
		if (win && window != win) {
			domain.call(win, 'fireEvent', [ event ]);
		} else {
			// {type:"event", target:"id", name="selectChanged", data{}}
			if (event.type == 'event') {
				seajs.use( [ 'lui/base' ], function(base) {
					var evented = base.byId(event.target);
					evented.emit(event.name, event.data);
				});
			} else if (event.type == 'topic') {
				seajs.use( [ 'lui/topic' ], function(topic) {
					if (event.target) {
						topic.group(event.target).publish(event.name, event);
					} else {
						topic.publish(event.name, event);
					}
				});
			}
		}
	};
	
	// 判断返回的内容里面是否包含密码域
	var regLoginPage = /<input[^>]+type=(\"|\')?(username|password)(\"|\')?[^>]*>/gi;
	LUI.ajaxComplete = function(xhr) {
		if (xhr.getResponseHeader("isloginpage") != null
				&& xhr.getResponseHeader("isloginpage") == "true") {
			if(window.self == window.top){//当前页的ajax出问题了
				window.location.reload();
			}else{
				domain.call(window.top,"login");
			}
		} else {
			if (xhr.responseText && xhr.responseText.search && xhr.responseText.search(regLoginPage) > 0) {
				if(window.self == window.top){
					window.location.reload();
				}else{
					domain.call(window.top,"login");
				}
			}
		}
	};
	
	
	var pinArray = [];
	
	//判断是否给元素绑定了卡片弹出框
	___hasPin = function(curObj){
		var isBind = false;
		for(var i=0; i<pinArray.length; i++){
			if(pinArray[i] && pinArray[i].obj == curObj){
				isBind = true;
				break;
			}
		}
		return isBind;
	};
	
	LUI.person = function(event, element){
		seajs.use(['lui/jquery','lui/parser','lui/pinwheel'],function($,parser,p){
			
			p($,parser);
			var events = $._data($(element)[0],'events');
			if(events && events["click"] ){ 
				//alert(111);
			}else{
				$(element).pinwheel();
			}
		});
	};
	
	LUI.maindata = function(event, element){
		seajs.use(['lui/jquery','lui/parser','lui/pinwheel'],function($,parser,p){
			p($,parser,441,190);
			var events = $._data($(element)[0],'events');
			if(events && events["click"] ){ 
				
			}else{
				$(element).pinwheel();
			}
			
		});
	};
	
	// RTF标签中的主数据链接替换成卡片方式
	LUI.replaceRtfMainDataCard = function(objName){
		//debugger;
		seajs.use(['lui/jquery'],function($){
			$("[name='"+objName+"']").find("a").each(
					function(){
					     var href = $(this).attr("href");
					     if(href && href.indexOf('/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=show')>=0){
							$(this).addClass("com_author");
							$(this).removeAttr("target");
							$(this).attr("href","javascript:void(0)");
							$(this).attr("ajax-href",href.replace('method=show','method=cardInfo'));
							$(this).attr("onmouseover","if(window.LUI && window.LUI.maindata)window.LUI.maindata(event,this);");
						} 
					}
				);
			
		});
		
	};
	
	// 浏览器兼容检测
	if (navigator.userAgent.indexOf("MSIE") > -1
			&& document.documentMode == null || document.documentMode < 8) {
		if (!!window.ActiveXObject || "ActiveXObject" in window && (parseFloat(navigator.appVersion.split("MSIE")[1]) >7)) {
			return;
    	}
		function use(url, isCss) {
			var type = isCss ? 'link' : 'script';
			var node = document.createElement(type), head = document
					.getElementsByTagName("head")[0];
			if (isCss) {
				node.rel = "stylesheet";
				node.href = url;
			} else {
				node.src = url;
			}
			node.src = url;
			head.appendChild(node);
		}
		use(Com_Parameter.ContextPath + 'resource/js/jquery.js', false);
		use(Com_Parameter.ContextPath + 'sys/ui/extend/theme/default/style/common.css', true);
		use(Com_Parameter.ContextPath + 'sys/ui/extend/theme/default/style/icon.css', true);
		window.onload = function(){
			var $tip = $(
					'<div>您正在使用 Internet Explorer 低版本的IE浏览器。为更好的浏览本页，建议您将浏览器升级到IE8以上或以下浏览器：Firefox/Chrome/Safari/Opera</div>')
					.appendTo($(document.body))
					.addClass('browserTip');
			var $close = $('<div class="lui_icon_s_icon_16 lui_icon_s" title="关闭提示">').click(function(evt) {
						$tip.remove();
					});
			$tip.append($close);
			window.onscroll = function() {
				$tip.css('top', $('body,html').scrollTop()+ 'px');
			};
		};
	}
	
	var _____pageOpen = function(url, target, features, customHashParams){
		var targets = '_blank;_self;_parent;_top';
		if(target == '_rIframe' && typeof(openPage) !== 'undefined'){
			openPage(url, {
				closeable : false 
			});
			return;
		}
		if(targets.indexOf(target) == -1){
			target = '_top';
		}
		LUI.addHashParamToCookie(url,customHashParams);
		window.open(url, target);
	};
	
	/**
	* 将自定义Hash参数设置到Cookie中（Cookie失效时间为60秒）
	* @param url      URL地址
	* @param customHashParams  自定义Hash参数
	* @return
	*/
	LUI.addHashParamToCookie = function(url,customHashParams){
		// 兼容IE8，给字符串对象添加startsWith方法
		if (typeof String.prototype.startsWith !== 'function') {
		    String.prototype.startsWith = function(prefix) {
		        return this.slice(0, prefix.length) === prefix;
		    };
		}
        // 定义设置Cookie有效时间60秒的函数
		var setHashParamCookie = function(name,value){
			var exp = new Date();
			exp.setTime(exp.getTime() + 60*1000); // 设置cookie失效时间为60秒
			document.cookie = name+"="+ escape(value) + ";expires="+exp.toGMTString()+";path=/";
		};
		var resultUrl = url;
        if(url && customHashParams && !url.startsWith("http") && !url.startsWith("https")){
			for(key in customHashParams){
				var value = customHashParams[key];
				if(key.indexOf("c_")==0){ 
					var cookieKey = "ekp_url_hash_param_"+key;
					var cookieValue = value;
					setHashParamCookie(cookieKey,cookieValue);
				}
			}     	
        }
	};
	
	/**
	* 从Cookie中读取自定义Hash参数值
	* @param customHashName    自定义Hash参数变量名
	* @return
	*/
	LUI.getHashParamFromCookie = function(customHashName){
		var getHashParamCookie = function(name){
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
			else
			return null;
		};
		var cookieKey = "ekp_url_hash_param_"+customHashName;
		var cookieValue = getHashParamCookie(cookieKey);
		LUI.deleteHashParamFromCookie(customHashName);
		return cookieValue;
	};
	
	/**
	* 从Cookie中删除自定义Hash参数值
	* @param customHashName    自定义Hash参数变量名
	* @return
	*/
	LUI.deleteHashParamFromCookie = function(customHashName){
		var getHashParamCookie = function(name){
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
			else
			return null;
		}
		var deleteHashParamCookie = function(name){
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);
			var value=getHashParamCookie(name);
			if(value!=null){
				document.cookie = name+"="+ escape(value) + ";expires="+exp.toGMTString()+";path=/";
			}
		};
	    var cookieKey = "ekp_url_hash_param_"+customHashName;
	    deleteHashParamCookie(cookieKey);
	};

	LUI.getPageView = function(){
		if(LUI.$GetRootView$){
			return LUI.$GetRootView$();
		}
		try{
			var win = window;
			while(1){
				win = win.parent;
				if(win.LUI && win.LUI.$GetRootView$){
					return win.LUI.$GetRootView$();
				}
				if(win == top){
					break;
				}
			}
		}catch(e){
			return null;
		}
		return null;
	};
	
	/**
	 * 设置页签标题
	 */
	LUI.setPageTitle = function(value){
		var content = LUI.getPageView();
		if(content){
			content.setPageTitle(value);
		}
	}
	
	/**
	 * 打开页面
	 * @param {String} url:打开的链接。根据target的不同请求内容可能是完整网页，可能是HTML片段
	 * @param {String} target:链接目标。_blank(打开新窗口)、_self(刷新当前页面)、_content(刷新content)、_iframe(刷新Iframe)、_rIframe(刷新右侧Iframe)
	 * @param {Object} features:特性,如{ transition : 'slideDown' , pageClass : 'my custom pageClass'  }
	 * @param customHashParams {Object} 自定义hash参数，除j_start、j_target、j_path等系统公共hash参数之外的自定义hash参数，参数名必须以"c_"作为起始，如c_app_title、c_app_url
	 */
	LUI.pageOpen = function(url, target, features, customHashParams){
		var view = LUI.getPageView();
		
		if("_rIframe" == target) {
			seajs.use( [ 'lui/topic' ], function(topic) {
				topic.publish("lui/page/show/rIrame", features);
			});
		}
		
		if(view){
			features = features || {};
			features.curWindow = window;
			view.open(url, target, features, customHashParams);
		}else{
			_____pageOpen(url, target, features, customHashParams);
		}
	};
	
	/**
	 * 隐藏页面
	 * @param {String} target:隐藏目标。_content(内容区)、_iframe(Iframe区域)、_rIframe(右侧Iframe区域)
	 */
	LUI.pageHide = function(target, features){
		var view = LUI.getPageView();
		if(view){
			features = features || {};
			features.curWindow = window;
			view.hide(target, features);
		}else{
			if(target == '_rIframe' && typeof(openQuery) !== 'undefined'){ //bad hack
				
				seajs.use( [ 'lui/topic' ], function(topic) {
					topic.publish("lui/page/hide/rIrame", features);
				});
				
				openQuery();
			}
		}
	};
	
	/**
	 * 获取当前页面所处的模式,default:旧模式、quick:极速模式
	 */
	LUI.pageMode = function(){
		var view = LUI.getPageView();
		if(view){
			return view.mode;
		}
		return 'default';
	};
	
	/**
	 * 打开页面
	 * 注意:不建议使用(请使用LUI.pageOpen()打开页面)
	 */
	LUI.pageQuickOpen = function(url, target, features, customHashParams){
		LUI.pageOpen(url, target, features, customHashParams);
	};
	
	/**
	 * 是否处在极速模式页面中
	 * 注意:不建议使用(请使用LUI.pageMode()获取当前门户模式)
	 */
	LUI.pageQuickMode = function(){
		return LUI.pageMode() == 'quick';
	}
	
})();