/**
 * @fileoverview 百度地图的检索控件，对外开放。
 * 控制搜索框控件和导航控件。
 * 基于Baidu Map API 1.4。
 *
 * @author baidu
 * @version 1.4
 */
/**
 * @namespace BMap的所有library类均放在BMapLib命名空间下
 */
var BMapLib = window.BMapLib = BMapLib || {};

(function () {
    //声明baidu包
    var T, baidu = T = baidu || {
        version: "1.3.9"
    };
    //提出guid，防止在与老版本Tangram混用时
    //在下一行错误的修改window[undefined]
    baidu.guid = "$BAIDU$";
    //闭包，里边是所用到的tangram的方法
    (function () {
        /** @ignore
         * @namespace baidu.dom 操作dom的方法。
         */
        baidu.dom = baidu.dom || {};

        /* @ignore
         * @namespace baidu.event 屏蔽浏览器差异性的事件封装。
         * @property target 	事件的触发元素
         * @property pageX 		鼠标事件的鼠标x坐标
         * @property pageY 		鼠标事件的鼠标y坐标
         * @property keyCode 	键盘事件的键值
         */
        baidu.event = baidu.event || {};

        /* @ignore
         * @namespace baidu.lang 对语言层面的封装，包括类型判断、模块扩展、继承基类以及对象自定义事件的支持。
         */
        baidu.lang = baidu.lang || {};

        /* @ignore
         * @namespace baidu.browser 判断浏览器类型和特性的属性。
         */
        baidu.browser = baidu.browser || {};
        /**
         * 为目标元素添加className
         * @name baidu.dom.addClass
         * @function
         * @grammar baidu.dom.addClass(element, className)
         * @param {HTMLElement|string} element 目标元素或目标元素的id
         * @param {string} className 要添加的className，允许同时添加多个class，中间使用空白符分隔
         * @remark
         * 使用者应保证提供的className合法性，不应包含不合法字符，className合法字符参考：http://www.w3.org/TR/CSS2/syndata.html。
         * @shortcut addClass
         * @meta standard
         * @see baidu.dom.removeClass
         *
         *
         * @returns {HTMLElement} 目标元素
         */
        baidu.dom.addClass = function (element, className) {
            element = baidu.dom.g(element);
            var classArray = className.split(/\s+/),
                result = element.className,
                classMatch = " " + result + " ",
                i = 0,
                l = classArray.length;

            for (; i < l; i++) {
                if (classMatch.indexOf(" " + classArray[i] + " ") < 0) {
                    result += (result ? ' ' : '') + classArray[i];
                }
            }

            element.className = result;
            return element;
        };

        // 声明快捷方法
        baidu.addClass = baidu.dom.addClass;
        /**
         * 移除目标元素的className
         * @name baidu.dom.removeClass
         * @function
         * @grammar baidu.dom.removeClass(element, className)
         * @param {HTMLElement|string} element 目标元素或目标元素的id
         * @param {string} className 要移除的className，允许同时移除多个class，中间使用空白符分隔
         * @remark
         * 使用者应保证提供的className合法性，不应包含不合法字符，className合法字符参考：http://www.w3.org/TR/CSS2/syndata.html。
         * @shortcut removeClass
         * @meta standard
         * @see baidu.dom.addClass
         *
         * @returns {HTMLElement} 目标元素
         */
        baidu.dom.removeClass = function (element, className) {
            element = baidu.dom.g(element);

            var oldClasses = element.className.split(/\s+/),
                newClasses = className.split(/\s+/),
                lenOld, lenDel = newClasses.length,
                j, i = 0;
            //考虑到同时删除多个className的应用场景概率较低,故放弃进一步性能优化
            // by rocy @1.3.4
            for (; i < lenDel; ++i) {
                for (j = 0, lenOld = oldClasses.length; j < lenOld; ++j) {
                    if (oldClasses[j] == newClasses[i]) {
                        oldClasses.splice(j, 1);
                        break;
                    }
                }
            }
            element.className = oldClasses.join(' ');
            return element;
        };

        // 声明快捷方法
        baidu.removeClass = baidu.dom.removeClass;

        /**
         * 获取目标元素的computed style值。如果元素的样式值不能被浏览器计算，则会返回空字符串（IE）
         *
         * @author berg
         * @name baidu.dom.getComputedStyle
         * @function
         * @grammar baidu.dom.getComputedStyle(element, key)
         * @param {HTMLElement|string} element 目标元素或目标元素的id
         * @param {string} key 要获取的样式名
         *
         * @see baidu.dom.getStyle
         *
         * @returns {string} 目标元素的computed style值
         */

        baidu.dom.getComputedStyle = function (element, key) {
            element = baidu.dom._g(element);
            var doc = baidu.dom.getDocument(element),
                styles;
            if (doc.defaultView && doc.defaultView.getComputedStyle) {
                styles = doc.defaultView.getComputedStyle(element, null);
                if (styles) {
                    return styles[key] || styles.getPropertyValue(key);
                }
            }
            return '';
        };
        /**
         * 获取目标元素的样式值
         * @name baidu.dom.getStyle
         * @function
         * @grammar baidu.dom.getStyle(element, key)
         * @param {HTMLElement|string} element 目标元素或目标元素的id
         * @param {string} key 要获取的样式名
         * @remark
         *
         * 为了精简代码，本模块默认不对任何浏览器返回值进行归一化处理（如使用getStyle时，不同浏览器下可能返回rgb颜色或hex颜色），也不会修复浏览器的bug和差异性（如设置IE的float属性叫styleFloat，firefox则是cssFloat）。<br />
         * baidu.dom._styleFixer和baidu.dom._styleFilter可以为本模块提供支持。<br />
         * 其中_styleFilter能对颜色和px进行归一化处理，_styleFixer能对display，float，opacity，textOverflow的浏览器兼容性bug进行处理。
         * @shortcut getStyle
         * @meta standard
         * @see baidu.dom.setStyle,baidu.dom.setStyles, baidu.dom.getComputedStyle
         *
         * @returns {string} 目标元素的样式值
         */

        baidu.dom.getStyle = function (element, key) {
            var dom = baidu.dom;

            element = dom.g(element);
            var value = element.style[key] || (element.currentStyle ? element.currentStyle[key] : "") || dom.getComputedStyle(element, key);

            return value;
        };

        // 声明快捷方法
        baidu.getStyle = baidu.dom.getStyle;

        /**
         * 获取目标元素所属的document对象
         * @name baidu.dom.getDocument
         * @function
         * @grammar baidu.dom.getDocument(element)
         * @param {HTMLElement|string} element 目标元素或目标元素的id
         * @meta standard
         * @see baidu.dom.getWindow
         *
         * @returns {HTMLDocument} 目标元素所属的document对象
         */
        baidu.dom.getDocument = function (element) {
            element = baidu.dom.g(element);
            return element.nodeType == 9 ? element : element.ownerDocument || element.document;
        };


        /**
         * 从文档中获取指定的DOM元素
         * @name baidu.dom.g
         * @function
         * @grammar baidu.dom.g(id)
         * @param {string|HTMLElement} id 元素的id或DOM元素
         * @shortcut g,T.G
         * @meta standard
         * @see baidu.dom.q
         *
         * @returns {HTMLElement|null} 获取的元素，查找不到时返回null,如果参数不合法，直接返回参数
         */
        baidu.dom.g = function (id) {
            if ('string' == typeof id || id instanceof String) {
                return document.getElementById(id);
            } else if (id && id.nodeName && (id.nodeType == 1 || id.nodeType == 9)) {
                return id;
            }
            return null;
        };
        // 声明快捷方法
        baidu.g = baidu.G = baidu.dom.g;
        /**
         * 从文档中获取指定的DOM元素
         * **内部方法**
         *
         * @param {string|HTMLElement} id 元素的id或DOM元素
         * @meta standard
         * @return {HTMLElement} DOM元素，如果不存在，返回null，如果参数不合法，直接返回参数
         */
        baidu.dom._g = function (id) {
            if (baidu.lang.isString(id)) {
                return document.getElementById(id);
            }
            return id;
        };

        // 声明快捷方法
        baidu._g = baidu.dom._g;


        /**
         * 判断目标参数是否string类型或String对象
         * @name baidu.lang.isString
         * @function
         * @grammar baidu.lang.isString(source)
         * @param {Any} source 目标参数
         * @shortcut isString
         * @meta standard
         * @see baidu.lang.isObject,baidu.lang.isNumber,baidu.lang.isArray,baidu.lang.isElement,baidu.lang.isBoolean,baidu.lang.isDate
         *
         * @returns {boolean} 类型判断结果
         */
        baidu.lang.isString = function (source) {
            return '[object String]' == Object.prototype.toString.call(source);
        };

        // 声明快捷方法
        baidu.isString = baidu.lang.isString;

        /**
         * 事件监听器的存储表
         * @private
         * @meta standard
         */
        baidu.event._listeners = baidu.event._listeners || [];
        /**
         * 为目标元素添加事件监听器
         * @name baidu.event.on
         * @function
         * @grammar baidu.event.on(element, type, listener)
         * @param {HTMLElement|string|window} element 目标元素或目标元素id
         * @param {string} type 事件类型
         * @param {Function} listener 需要添加的监听器
         * @remark
         *
        1. 不支持跨浏览器的鼠标滚轮事件监听器添加<br>
        2. 改方法不为监听器灌入事件对象，以防止跨iframe事件挂载的事件对象获取失败

         * @shortcut on
         * @meta standard
         * @see baidu.event.un
         *
         * @returns {HTMLElement|window} 目标元素
         */
        baidu.event.on = function (element, type, listener) {
            type = type.replace(/^on/i, '');
            element = baidu.dom._g(element);

            var realListener = function (ev) {
                    // 1. 这里不支持EventArgument,  原因是跨frame的事件挂载
                    // 2. element是为了修正this
                    listener.call(element, ev);
                },
                lis = baidu.event._listeners,
                filter = baidu.event._eventFilter,
                afterFilter, realType = type;
            type = type.toLowerCase();
            // filter过滤
            if (filter && filter[type]) {
                afterFilter = filter[type](element, type, realListener);
                realType = afterFilter.type;
                realListener = afterFilter.listener;
            }

            // 事件监听器挂载
            if (element.addEventListener) {
                element.addEventListener(realType, realListener, false);
            } else if (element.attachEvent) {
                element.attachEvent('on' + realType, realListener);
            }

            // 将监听器存储到数组中
            lis[lis.length] = [element, type, listener, realListener, realType];
            return element;
        };

        // 声明快捷方法
        baidu.on = baidu.event.on;

        /**
         * 为目标元素移除事件监听器
         * @name baidu.event.un
         * @function
         * @grammar baidu.event.un(element, type, listener)
         * @param {HTMLElement|string|window} element 目标元素或目标元素id
         * @param {string} type 事件类型
         * @param {Function} listener 需要移除的监听器
         * @shortcut un
         * @meta standard
         * @see baidu.event.on
         *
         * @returns {HTMLElement|window} 目标元素
         */
        baidu.event.un = function (element, type, listener) {
            element = baidu.dom._g(element);
            type = type.replace(/^on/i, '').toLowerCase();

            var lis = baidu.event._listeners,
                len = lis.length,
                isRemoveAll = !listener,
                item, realType, realListener;

            //如果将listener的结构改成json
            //可以节省掉这个循环，优化性能
            //但是由于un的使用频率并不高，同时在listener不多的时候
            //遍历数组的性能消耗不会对代码产生影响
            //暂不考虑此优化
            while (len--) {
                item = lis[len];

                // listener存在时，移除element的所有以listener监听的type类型事件
                // listener不存在时，移除element的所有type类型事件
                if (item[1] === type && item[0] === element && (isRemoveAll || item[2] === listener)) {
                    realType = item[4];
                    realListener = item[3];
                    if (element.removeEventListener) {
                        element.removeEventListener(realType, realListener, false);
                    } else if (element.detachEvent) {
                        element.detachEvent('on' + realType, realListener);
                    }
                    lis.splice(len, 1);
                }
            }

            return element;
        };

        // 声明快捷方法
        baidu.un = baidu.event.un;

        ///import baidu.browser;
        if (/msie (\d+\.\d)/i.test(navigator.userAgent)) {
            //IE 8下，以documentMode为准
            //在百度模板中，可能会有$，防止冲突，将$1 写成 \x241
            /**
             * 判断是否为ie浏览器
             * @property ie ie版本号
             * @grammar baidu.browser.ie
             * @meta standard
             * @shortcut ie
             * @see baidu.browser.firefox,baidu.browser.safari,baidu.browser.opera,baidu.browser.chrome,baidu.browser.maxthon
             */
            baidu.browser.ie = baidu.ie = document.documentMode || +RegExp['\x241'];
        }

        
        /**
         * @namespace baidu.platform 判断平台类型和特性的属性。
         */
        baidu.platform = baidu.platform || {};

        /**
         * 判断是否为iphone平台
         * @property iphone 是否为iphone平台
         * @grammar baidu.platform.iphone
         * @meta standard
         * @see baidu.platform.x11,baidu.platform.windows,baidu.platform.macintosh,baidu.platform.ipad,baidu.platform.android
         */
        baidu.platform.isIphone = /iphone/i.test(navigator.userAgent);

        /**
         * 判断是否为android平台
         * @property android 是否为android平台
         * @grammar baidu.platform.android
         * @meta standard
         * @see baidu.platform.x11,baidu.platform.windows,baidu.platform.macintosh,baidu.platform.iphone,baidu.platform.ipad
         * @author jz
         */
        baidu.platform.isAndroid = /android/i.test(navigator.userAgent);
        /*
         * Tangram
         * Copyright 2009 Baidu Inc. All rights reserved.
         */

        ///import baidu.platform;

        /**
         * 判断是否为ipad平台
         * @property ipad 是否为ipad平台
         * @grammar baidu.platform.ipad
         * @meta standard
         * @see baidu.platform.x11,baidu.platform.windows,baidu.platform.macintosh,baidu.platform.iphone,baidu.platform.android   
         * @author jz
         */
        baidu.platform.isIpad = /ipad/i.test(navigator.userAgent);

        /**
         * 是否为移动平台
         * @returns {Boolean}
         */
        baidu.isMobile = function() {
            return !!(baidu.platform.isIphone || baidu.platform.isIpad || baidu.platform.isAndroid);
        }

    })();


    /**
     * 阻止默认事件处理
     * @param {Event}
     */
    function preventDefault(e){
        var e = window.event || e;
        e.preventDefault ? e.preventDefault() : e.returnValue = false;
        return false;
    }


    /**
     * 常量searchType
     */
    window.LOCAL_SEARCH   = "1";
    window.TRANSIT_ROUTE  = "2";
    window.DRIVING_ROUTE  = "3";

    /**
     * SearchControl类的构造函数
     * @class 检索控件 <b>入口</b>。
     * @constructor
     */
    function SearchControl (options) {
        this.map       = options.map;
        this.mapLocation = options.mapLocation;
        this.i18n = options.i18n;
        this.projection = this.map.getMapType().getProjection();
        this.container = baidu.isString(options.container) ? document.getElementById(options.container) : options.container;
        this.type = options.type || LOCAL_SEARCH;
        this.enableAutoLocation = options.enableAutoLocation === false ? false : true; //是否根据IP定位当前城市
        this.onMarkerSetdown = options.onMarkerSetdown || new Function();
        this.isAutoComplete = options.isAutoComplete === false ? false : true;
        this.initialize();
    }

    SearchControl.prototype = {
        constructor: SearchControl

        /**
         * 初始化操作
         */
        , initialize: function () {
            this.container.innerHTML = this._getHtml();
            var that=this;
            setTimeout(function(){
            	that._initDom();
                if (that.enableAutoLocation) {
                	that._initLocalCity();
                }
                that._initService();
                that._bind();
                that.setType(that.type);
            },30);
        }

        /**
         * 生成控件所需要的dom元素
         */
        , _getHtml: function () {
            var html = [
                '<div id="BMapLib_searchBoxContent" class="BMapLib_schbox">',
                    '<div id="BMapLib_normalBox" class="BMapLib_sc_t sc_box_bg">',
                        '<div id="BMapLib_sc0">',
                            '<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">',
                                '<tr>',
                                    '<td width="100%">',
                                        '<form id="BMapLib_formPoi" class="BMapLib_seBox"><input placeholder="'+this.i18n.searchTip+'" data-widget="quickdelete" id="BMapLib_PoiSearch" class="txtPoi" type="search"/><em id="btnPClear" class="BMapLib_xx"></em></form>',
                                    '</td>',
                                    '<td>',
                                        '<div class="BMapLib_sc_t_b sc_btn" id="BMapLib_sc_b0">'+this.i18n.searchBtn+'</div>',
                                    '</td>',
                                '</tr>',
                            '</table>',
                        '</div>',
                        '<div id="BMapLib_sc1" style="display:none;">',
                            '<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">',
                                '<tr>',
                                    '<td>',
                                        '<div id="BMapLib_sc_b1" class="BMapLib_sc_t_sw sc_btn">',
                                            '<div class="BMapLib_sc_t_sw1"></div>',
                                        '</div>',
                                    '</td>',
                                    '<td width="100%">',
                                        '<div class="BMapLib_dbseBox" style="margin-bottom: 5px;">',
                                            '<em class="BMapLib_ipt_icon BMapLib_txtSta"></em><input class="ipt_txt" type="search" id="BMapLib_txtNavS"/><em id="btnSClear" class="xx"></em>',
                                        '</div>',
                                        '<div class="BMapLib_dbseBox"><em class="BMapLib_ipt_icon BMapLib_txtEnd"></em><input class="ipt_txt" type="search" id="BMapLib_txtNavE"/><em id="btnEClear" class="xx"></em></div>',
                                    '</td>',
                                    '<td>',
                                        '<div class="BMapLib_sc_t_b sc_btn" id="BMapLib_sc_b2">',
                                            '<div class="BMapLib_sc_t_b1"></div>',
                                        '</div>',
                                    '</td>',
                                '</tr>',
                            '</table>',
                        '</div>',
                    '</div>',
                    '<div id="BMapLib_tipBox">',
                    '<div>',
                '</div>'
            ].join("");
            return html;
        }

        /**
         * 获取相关的DOM元素
         */
        , _initDom: function () {
            this.dom = {
                searchBoxContent : baidu.g("BMapLib_searchBoxContent") //容器
                , sc0        : baidu.g("BMapLib_sc0") //普通检索容器
                , sc1        : baidu.g("BMapLib_sc1")     //驾车和公交检索容器，公用一个容器
                , searchText : baidu.g("BMapLib_PoiSearch") //普通检索文本框
                , nSearchBtn : baidu.g("BMapLib_sc_b0")     //普通检索按钮
                , startText  : baidu.g("BMapLib_txtNavS")   //检索起点文本框
                , endText    : baidu.g("BMapLib_txtNavE")   //检索终点文本框
                , hSearchBtn : baidu.g("BMapLib_sc_b2")     //公交或驾车检索按钮
                , changeBtn  : baidu.g("BMapLib_sc_b1")     //交换起终点数据按钮
                , formPoi    : baidu.g("BMapLib_formPoi")   //普通检索表单
                , tipBox     : baidu.g("BMapLib_tipBox")    //提示信息条
            }
            //存储城市dom
            this.cityListSub = {};
        }

        /**
         * ip定位当前城市
         */
        , _initLocalCity: function () {
            var myCity = new BMap.LocalCity(),
                map = this.map;
            myCity.get(function(result){
                var cityName = result.name;
                map.setCenter(cityName);
            });
            
        }

        /**
         * 初始化各检索服务
         */
        , _initService: function () {
            var map  = this.map;
            this.localSearch = new BMap.LocalSearch(map, {
                renderOptions:{map: map}
                , onSearchComplete : function (result) {
                    var status = me.localSearch.getStatus();
                    if (status != BMAP_STATUS_SUCCESS) {
                        me.showTipBox(status);
                    }
                },
                onMarkersSet : function(pois){
                	me.onMarkerSetdown(pois);
                }
            });
            var me = this;
            this.transitRoute = new BMap.TransitRoute(map, {
                renderOptions : {map: map}
                , onSearchComplete : function () {
                    var status = me.transitRoute.getStatus();
                    if (status != BMAP_STATUS_SUCCESS) {
                        me.showTipBox(status);
                    }
                }
            });
            this.drivingRoute  = new BMap.DrivingRoute(map, {
                renderOptions:{map: map, autoViewport: true}
                , onSearchComplete : function () {
                    var status = me.drivingRoute.getStatus();
                    if (status != BMAP_STATUS_SUCCESS) {
                        me.showTipBox(status);
                    }
                }
            });
        }

        /**
         * 绑定事件
         */
        , _bind: function () {
            var eventName = "click"
                , me  = this;
            baidu.on(this.dom.nSearchBtn, eventName, function(e) {
                preventDefault(e);
                me.localSearchAction();
            });
            baidu.on(this.dom.changeBtn, eventName, function(e) {
                preventDefault(e);
                me.changeStartAndEnd();
            });
            if(this.dom.searchText){
            	this.dom.searchText.onkeydown = function(e) {
                	if(e.keyCode==13) {
                		me.localSearchAction();
                	}
                };
            }
            if(this.isAutoComplete){
            	this.autoCompleteInit();
                this.searchAC.onconfirm = function(e) {
                	me.localSearchAction();
                }
            }
        }

        /**
         * 触发localsearch事件
         */
        , localSearchAction: function () {
            this.reset();
            //失去焦点收起手机上的键盘
            this.dom.searchText.blur();
            //隐藏autocomplete
            if(this.searchAC) {
            	this.searchAC.hide();
            }
            var keyword = $.trim(this.dom.searchText.value);
            if(keyword && keyword.indexOf(',')>-1){
            	//根据坐标反查
            	var coords = keyword.split(',');
            	var __point = new BMap.Point(coords[1], coords[0]);
            	this.getLocation(__point);
            	return;
            }
            this.localSearch.search(keyword);
        },
        getLocation:function(point){
        	var geocoder = new BMap.Geocoder();
        	var me = this;
        	geocoder.getLocation(point, function(result){
        		if(!result || !result.address){
        			me.showTipBox(BMAP_STATUS_UNKNOWN_LOCATION);
        			return;
        		}
        		me.mapLocation.getLocationRender(point,{title:result.title || result.address,address:result.address,province:result.addressComponents.province,city:result.addressComponents.city,district:result.addressComponents.district});
    		});
        }

        /**
         * 公交换乘查询
         */
        , transitRouteAction: function () {
            this.reset();
            var startKeyword    = this.dom.startText.value
                , endKeyword    = this.dom.endText.value
                ;
            this.transitRoute.search(startKeyword, endKeyword);
        }

        /**
         * 驾车路径检索
         */
        , drivingRouteAction : function () {
            this.reset();
            var startKeyword    = this.dom.startText.value
                , endKeyword    = this.dom.endText.value
                ;
            this.drivingRoute.search(startKeyword, endKeyword);
        }

        /**
         * 显示提示信息
         */
        , showTipBox: function(status) {
            var message = "未搜索到准确的结果";
            switch (status) {
                case BMAP_STATUS_UNKNOWN_LOCATION:
                    message = "位置结果未知";
                    break;
                case BMAP_STATUS_UNKNOWN_ROUTE:
                    message = "导航结果未知";
                    break;
            }
            var tipBox = this.dom.tipBox;
            tipBox.innerHTML = message;
            tipBox.style.display = "block";
            window.setTimeout(function(){
                tipBox.style.display = "none";
            }, 4000);
        }

        /**
         * 修改起终点的文本信息
         */
        , changeStartAndEnd: function () {
            var temp = this.dom.startText.value;
            this.dom.startText.value = this.dom.endText.value;
            this.dom.endText.value = temp;
        }

        /**
         * 绑定自动完成事件
         */
        , autoCompleteInit: function () {
            this.searchAC= new BMap.Autocomplete({
                "input"      : this.dom.searchText
                , "location" : this.map
                , "baseDom"  : this.dom.searchBoxContent
            });
            this.startAC = new BMap.Autocomplete({
                "input"      : this.dom.startText
                , "location" : this.map
                , "baseDom"  : this.dom.searchBoxContent
            });
            this.endAC = new BMap.Autocomplete({
                "input"      : this.dom.endText
                , "location" : this.map
                , "baseDom"  : this.dom.searchBoxContent
            });
        }

        /**
         * 设置当前的检索类型
         */
        , setType : function (searchType) {
            var me = this;
            switch (searchType) {
                case LOCAL_SEARCH  :
                    this.showBox(0);
                    break;
                case TRANSIT_ROUTE :
                    this.showBox(1);
                    this.dom.hSearchBtn.onclick = function (e) {
                        preventDefault(e);
                        me.transitRouteAction();
                    }
                    break;
                case DRIVING_ROUTE :
                    this.showBox(1);
                    this.dom.hSearchBtn.onclick = function (e) {
                        preventDefault(e);
                        me.drivingRouteAction();
                    };
                    break;
            }
        }

        /**
         * 清除最近的结果
         */
        , reset : function () {
            this.localSearch.clearResults();
            this.transitRoute.clearResults();
            this.drivingRoute.clearResults();
        }

        /**
         * 显示面板
         * 0 ：普通检索
         * 1 : 公交检索或驾车检索
         */
        , showBox : function (type) {
        	if(this.dom.sc0){
        		this.dom.sc0.style.display = type ? "none"  : "block";
        	}
        	if(this.dom.sc1){
        		this.dom.sc1.style.display = type ? "block" : "none";
        	}
        }

    }

    /**
     * @exports SearchControl as BMapLib.SearchControl
     */
    BMapLib.SearchControl = SearchControl;

})();
