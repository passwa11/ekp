(function(window, undefined){
	/**
	 * 模块通用工具类,提供一些common没有的方法
	 */
	var sysPrintUtil={};
	
	 /**
	  * DOM类操作
	  */
	//返回一个或多个DOM对象
	sysPrintUtil.$ = function() {
		var results = [], element;
		for (var i = 0; i < arguments.length; i++) {
			element = arguments[i];
			if (typeof element == 'string') element = document.getElementById(element);
			results.push(element);
		}
		return results.length < 2 ? results[0] : results;
	};
	
	//返回多个指定标签的DOM对象
	sysPrintUtil.getElementsByClassName = function(className, parent) {
		var _parent = parent || document, matches = [], nodes = _parent.getElementsByTagName('*');
		for (var i = nodes.length - 1; i >= 0; i--) {
			if (nodes[i].className == className || nodes[i].className.indexOf(className) + 1 ||
				nodes[i].className.indexOf(className + ' ') + 1 || nodes[i].className.indexOf(' ' + className) + 1)
				matches.push(nodes[i]);
		}
		return matches;
	};
	
	//是否指定标签
	sysPrintUtil.checkTagName = function(element, tagName) {
		return element && element.tagName && element.tagName.toLowerCase() == tagName.toLowerCase();
	};
	
	/**
	 * 位置类操作
	 */
	//返回节点位置
	sysPrintUtil.absPosition = function(node, stopNode) {
		var x = y = 0;
		var offset = $(node).offset();
		return {'x':offset.left, 'y':offset.top};
	};
	
	//鼠标所在位置
	sysPrintUtil.getMousePosition = function(event) {
		var pos = null;
		if (event.clientX || event.clientY) {
			pos = {
				x:event.clientX + document.body.scrollLeft - document.body.clientLeft,
				y:event.clientY + document.body.scrollTop  - document.body.clientTop
			};
		}
		else {
			//console.info('use page x y');
			pos = {x:event.pageX, y:event.pageY};
		}
		//console.info("pos x = " + pos.x + ", y = " + pos.y);
		return pos;
	};
	
	sysPrintUtil.isIntersect = function(position, element) {
		if (!element) return false;
		var area = sysPrintUtil.absPosition(element);
		return !(position.x < area.x || position.y < area.y ||
			position.x > (area.x + element.offsetWidth) || position.y > (area.y + element.offsetHeight));
	};
	
	
	/**
	 * 对象、数组相关
	 */
	// arguments[0]=target
	sysPrintUtil.extend = function() {
		var target = arguments[0] || {}, i = 1, length = arguments.length, deep = false, options;
		if ( target.constructor == Boolean ) {
			deep = target;
			target = arguments[1] || {};
			i = 2;
		}
		if ( typeof target != "object" && typeof target != "function" ) target = {};
		if ( length == i ) {
			target = this;
			--i;
		}
		for ( ; i < length; i++ )
			if ( (options = arguments[ i ]) != null )
				for ( var name in options ) {
					var src = target[ name ], copy = options[ name ];
					if ( target === copy ) continue;
					if ( deep && copy && typeof copy == "object" && !copy.nodeType )
						target[ name ] = sysPrintUtil.extend( deep, src || ( copy.length != null ? [ ] : { } ), copy );
					else if ( copy !== undefined )
						target[ name ] = copy;
				}
		return target;
	};
	
	//删除指定元素
	sysPrintUtil.spliceArray = function(array, spliced) {
		for (var i = 0; i < array.length; i++)	{
			if (array[i] == spliced) {
				array.splice(i, 1);
				break;
			}
		}
	};
	
	/**
	 * 浏览器相关
	 */
	//浏览器判断
	sysPrintUtil.UserAgent = function() {
		var userAgent = navigator.userAgent.toLowerCase();
		if (/msie/.test( userAgent ) && !/opera/.test( userAgent )) return 'msie';
		if (/mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent )) return 'mozilla';
		if (/webkit/.test( userAgent )) return 'safari';
		if (/opera/.test( userAgent )) return 'opera';
	};
	
	//浏览器版本
	sysPrintUtil.getBrowserVersion = function() {
		var userAgent = navigator.userAgent.toLowerCase();
		return (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1];
	};
	
	//鼠标键，以IE返回值为准
	sysPrintUtil.eventButton = function(event) {
		var button = event.button;
		if (event.which != null) { // 用which确定按键
			if (1 == event.which) {
				return 1;
			}
			if (2 == event.which) {
				return 4;
			}
			if (3 == event.which) {
				return 2;
			}
		}
		if ('msie' == sysPrintUtil.UserAgent()) {
			return button;
		}
		if (0 == button) {
			return 1;
		}
		if (1 == button) {
			return 4;
		}
		return button;
	};
	
	sysPrintUtil.addEvent = function(element, eventHandle, method) {
		if(element.attachEvent){
			element.attachEvent("on" + eventHandle, method);
		}else if(element.addEventListener){
			element.addEventListener(eventHandle, method, false);
		}
	};

	sysPrintUtil.removeEvent = function(element, eventHandle, method) {
		if(sysPrintUtil.UserAgent() == 'msie')
			element.detachEvent("on" + eventHandle, method);
		else
			element.removeEventListener(eventHandle, method, false);
	};
	
	/**
	 * 其他
	 */
	//ID生成器
	sysPrintUtil.generateID = function (){
		return parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
	};
	
	//escape
	sysPrintUtil.HtmlEscape = function (s){
		if (s == null || s ==' ') return '';
		s = s.replace(/&/g, "&amp;");
		s = s.replace(/\"/g, "&quot;");
		s = s.replace(/</g, "&lt;");
		return s.replace(/>/g, "&gt;");
	};
	//是否打印设计控件
	sysPrintUtil.isDesignElement = function(node) {
		return node && node.getAttribute('printcontrol') && node.getAttribute('printcontrol') == 'true';
	};
	//是否表单设计控件
	sysPrintUtil.isXFormDesignElement = function(node) {
		return node && node.getAttribute('formDesign') && node.getAttribute('formDesign') == 'landray';
	};
	//获取自定义表单设计控件
	sysPrintUtil.getDesignElement = function(node, attr) {
		if (attr == null) attr = 'parentNode';
		for (var findNode = node; findNode && findNode.tagName && findNode.tagName.toLowerCase() != 'body'; findNode = findNode[attr]) {
			if (sysPrintUtil.isXFormDesignElement(findNode)) return findNode;
		}
		return null;
	};
	
	//获取打印表单父控件信息
	sysPrintUtil.getParentDesignElementInfo = function(node) {
		var ctrlNode = sysPrintUtil.getPrintDesignElement(node);
		if(ctrlNode) {
			var result = {
					isDetailTable:ctrlNode.getAttribute('fd_type') && ctrlNode.getAttribute('fd_type') == 'detailsTable',
					id : ctrlNode.getAttribute('id'),
					label : ctrlNode.getAttribute('label')
			};
			return result;
		}
		return null;
	};
	
	//获取打印表单设计控件
	sysPrintUtil.getPrintDesignElement = function(node, attr) {
		if (attr == null) attr = 'parentNode';
		for (var findNode = node; findNode && findNode.tagName && findNode.tagName.toLowerCase() != 'body'; findNode = findNode[attr]) {
			if (sysPrintUtil.isDesignElement(findNode)) return findNode;
		}
		return null;
	};
	//元素所属Td对象
	sysPrintUtil.getTdElement = function(node) {
		if (node && sysPrintUtil.checkTagName(node,'td')) return node;
		var attr = 'parentNode';
		var resultNode = null;
		for (var findNode = node; findNode && findNode.tagName && findNode.tagName.toLowerCase() != 'body'; findNode = findNode[attr]) {
			if (sysPrintUtil.checkTagName(findNode,'td')){
				resultNode = findNode;
				break;
			};
		}
		if(resultNode){
			if(sysPrintUtil.getPrintDesignElement(resultNode)) return resultNode;
		}
		return null;
	};
	//打印机制上下文
	sysPrintUtil.getSysPrintContextPath = function(){
		return sysPrintKMSS_Parameter_ContextPath +"sys/print/designer/";
	}
	window.sysPrintUtil=sysPrintUtil;//打印机制通用工具
	
})(window);