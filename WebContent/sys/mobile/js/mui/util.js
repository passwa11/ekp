define([
  "dojo/_base/declare",
  "dojo/_base/window",
  "dojo/on",
  "dojo/query",
  "mui/base64",
  "dojo/Deferred",
  "dojo/promise/all"
], function(declare, win, on, query, base64, Deferred, all) {
  var claz = declare("mui.util", null, {
    formatUrl: function(url, isFull) {
      var rtnUrl = "";
      if (url == null) {
        rtnUrl = "";
      } else if (url.substring(0, 1) == "/") {
        if (dojoConfig.serverPrefix) {
          var serverPrefix = dojoConfig.serverPrefix;
          serverPrefix =
            serverPrefix.charAt(serverPrefix.length - 1) == "/"
              ? serverPrefix.substring(0, serverPrefix.length - 1)
              : serverPrefix;
          rtnUrl = serverPrefix + url;
          return rtnUrl;
        } else {
          rtnUrl = dojoConfig.baseUrl + url.substring(1);
        }
      } else {
        rtnUrl = url;
      }
      if (
        isFull &&
        !(new RegExp("^http").test(rtnUrl) || new RegExp("^https").test(rtnUrl) || new RegExp("^dingtalk").test(rtnUrl))
      )
        rtnUrl = this.getHost() + rtnUrl;
      return rtnUrl;
    },
    getHost: function() {
      var host = location.protocol.toLowerCase() + "//" + location.hostname;
      if (location.port != "" && location.port != "80") {
        host = host + ":" + location.port;
      }
      return host;
    },
    formatText: function(str) {
      if (str == null || str == undefined || str.length == 0) return "";
      // 既然是字符串转换，如果传入的是数字类型则不允许replace，存在报错 #165201 修复
      return (str + "")
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/\'/g, "&#39;")
        .replace(/\"/g, "&quot;")
        .replace(/\n/g, "<br>");
    },
    // 163465双反斜线转义
    formatTextChar: function(str) {
      if (!str || str.length == 0) return "";
      if ((str + "").indexOf("\\") < 0) {
        return str;
      }
      if ((str + "").indexOf("\\\'") >-1) {
        return str;
      }
      return (str + "").replace(/\\/g,"\\\\");
    },
    decodeHTML: function(str) {
      // 不处理空字符串
      if (!str || str.length == 0) return "";
      return (str + "")
        .replace(/&quot;/g, '"')
        .replace(/&gt;/g, ">")
        .replace(/&lt;/g, "<")
        .replace(/&amp;/g, "&");
    },
    urlResolver: function(url, params) {
      return url.replace(/\!\{([\w\.]*)\}/gi, function(_var, _key) {
        var value = null;
        if (params) value = params[_key];
        return value === null || value === undefined ? "" : value;
      });
    },
    // 首字母转为大写
    capitalize: function(str) {
      if (!str || str.length == 0) return "";
      return str.substr(0, 1).toUpperCase() + str.substr(1);
    },
    getScreenSize: function() {
      return {
        h:
          win.global.innerHeight ||
          win.doc.documentElement.clientHeight ||
          win.doc.documentElement.offsetHeight,
        w:
          win.global.innerWidth ||
          win.doc.documentElement.clientWidth ||
          win.doc.documentElement.offsetWidth
      };
    },
    addTopView: function(view) {
      var parent = query("#content .mblView")[0].parentNode;
      if (view && view.domNode) {
        query(parent).append(view.domNode);
      } else if (view && view.nodeType) {
        query(parent).append(view);
      }
    },
    // 获取url对应参数
    getUrlParameter: function(url, param) {
      var re = new RegExp();
      re.compile("[\\?&]" + param + "=([^&]*)", "i");
      var arr = re.exec(url);
      if (arr == null) return null;
      else return decodeURIComponent(arr[1]);
    },
    setUrlParameter: function(url, param, value) {
      var re = new RegExp();
      re.compile("([\\?&]" + param + "=)[^&]*", "ig");
      if (value == null) {
        if (re.test(url)) {
          url = url.replace(re, "");
        }
      } else {
        value = encodeURIComponent(value);
        if (re.test(url)) {
          url = url.replace(re, "$1" + value);
        } else {
          url += (url.indexOf("?") == -1 ? "?" : "&") + param + "=" + value;
        }
      }
      if (url.charAt(url.length - 1) == "?")
        url = url.substring(0, url.length - 1);
      return url;
    },
    setUrlParameterMap: function(url, paramMap) {
      for (param in paramMap) {
        url = this.setUrlParameter(url, param, paramMap[param]);
      }
      return url;
    },
    disableTouch: function(domNode, touchSign) {
      if (domNode) {
        var disableFun = function(evt) {
          evt.preventDefault();
        };
        on(domNode, touchSign, disableFun);
      }
    },
    base64Encode: function(str,isX) {
      var val = str;
      if (str != null && str.length > 0)
        str = "\u4241\u5345\u3634{" + base64.encode(str) + "}";
      if (val != str) {
    	  if(isX==true){
				val = "\u4645\u5810\u4d40" + str;
			}else{
				val = "\u4649\u5820\u4d45" + str;
			}
      }
      return val;
    },
    preLoading: function(_url) {
      if (_url == null || _url == "") return;
      try {
        var xmlhttp = {};
        if (window.XMLHttpRequest) {
          // 所有浏览器
          xmlhttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
          // IE5 和 IE6
          xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if (!this.getUrlParameter(_url, "s_cache")) {
          if (window.Com_Parameter && window.Com_Parameter.Cache) {
            _url = Com_SetUrlParameter(_url, "s_cache", Com_Parameter.Cache);
          } else {
            _url =
              _url.indexOf("?") > -1
                ? _url + "&" + dojoConfig.cacheBust
                : _url + "?" + dojoConfig.cacheBust;
          }
        }

        // 走异步，否则会因为预加载导致当前页面卡住
        xmlhttp.open("GET", _url, true);
        xmlhttp.setRequestHeader("Accept", "text/plain");
        xmlhttp.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
        xmlhttp.send(null);
      } catch (e) {}
    },

    // 相对路径转绝对路径
    urlParse: function(url, baseURL) {
      var m = String(url)
        .replace(/^\s+|\s+$/g, "")
        .match(
          /^([^:\/?#]+:)?(?:\/\/(?:([^:@\/?#]*)(?::([^:@\/?#]*))?@)?(([^:\/?#]*)(?::(\d*))?))?([^?#]*)(\?[^#]*)?(#[\s\S]*)?/
        );
      if (!m) {
        throw new RangeError();
      }
      var protocol = m[1] || "";
      var username = m[2] || "";
      var password = m[3] || "";
      var host = m[4] || "";
      var hostname = m[5] || "";
      var port = m[6] || "";
      var pathname = m[7] || "";
      var search = m[8] || "";
      var hash = m[9] || "";
      if (baseURL !== undefined) {
        var base = this.urlParse(baseURL);
        var flag = protocol === "" && host === "" && username === "";
        if (flag && pathname === "" && search === "") {
          search = base.search;
        }
        if (flag && pathname.charAt(0) !== "/") {
          pathname =
            pathname !== ""
              ? ((base.host !== "" || base.username !== "") &&
                base.pathname === ""
                  ? "/"
                  : "") +
                base.pathname.slice(0, base.pathname.lastIndexOf("/") + 1) +
                pathname
              : base.pathname;
        }
        // dot segments removal
        var output = [];
        pathname
          .replace(/^(\.\.?(\/|$))+/, "")
          .replace(/\/(\.(\/|$))+/g, "/")
          .replace(/\/\.\.$/, "/../")
          .replace(/\/?[^\/]*/g, function(p) {
            if (p === "/..") {
              output.pop();
            } else {
              output.push(p);
            }
          });
        pathname = output
          .join("")
          .replace(/^\//, pathname.charAt(0) === "/" ? "/" : "");
        if (flag) {
          port = base.port;
          hostname = base.hostname;
          host = base.host;
          password = base.password;
          username = base.username;
        }
        if (protocol === "") {
          protocol = base.protocol;
        }
      }

      var origin =
        protocol + (protocol !== "" || host !== "" ? "//" : "") + host;
      var href =
        protocol +
        (protocol !== "" || host !== "" ? "//" : "") +
        (username !== ""
          ? username + (password !== "" ? ":" + password : "") + "@"
          : "") +
        host +
        pathname +
        search +
        hash;

      return {
        protocol: protocol,
        username: username,
        password: password,
        host: host,
        hostname: hostname,
        port: port,
        search: search,
        searchJSON: (function() {
          var json = {};
          var _search = search.length > 0 ? search.substring(1) : search;
          _search.replace(/([^?&]+)=([^?&]+)/gi, function(a, b, c) {
            json[b] = c;
          });
          return json;
        })(),
        pathname: pathname,
        hash: hash,
        href: href,
        origin: origin
      };
    },

    loadCSS: function(url, callback) {
      this.__csscache = this.__csscache || {};
      var self = this;
      if (this.__csscache[url]) return;
      var head = document.getElementsByTagName("head")[0],
        css = document.createElement("link");
      css.type = "text/css";
      css.rel = "stylesheet";
      css.href = dojoConfig.baseUrl + url.substring(1);
      css.onload = css.onreadystatechange = function() {
        if (
          !this.readyState ||
          this.readyState === "loaded" ||
          this.readyState === "complete"
        ) {
          callback && callback();
          css.onload = css.onreadystatechange = null;
          self.__csscache[url] = true;
        }
      };
      css.onerror = function() {
        if (css.__retry) return;
        css.__retry = true;
        //本地取不到且存在业务服务器,到业务服务器尝试再取一次
        css.href = self.formatUrl(url);
        head.appendChild(css);
      };
      head.appendChild(css);
    },
    //设置innerHTML并运行html中的js
    setInnerHTML: function(dom, html) {
      var allDeferred = new Deferred();
      dom.innerHTML = html;
      var scripts = dom.getElementsByTagName("script");
      var promises = [];
      for (var i = 0; i < scripts.length; i++) {
        evalScript(scripts[i]);
      }

      if (promises.length == 0) {
        allDeferred.resolve();
      } else {
        all(promises).then(function() {
          allDeferred.resolve();
        });
      }

      //执行js
      function evalScript(elem) {
        var deferred = new Deferred();
        var data = elem.text || elem.textContent || elem.innerHTML || "",
          src = elem.src,
          head = document.getElementsByTagName("head")[0],
          script = document.createElement("script");
        script.type = "text/javascript";
        if (src) {
          script.src = src;
          promises.push(deferred.promise);
          script.onload = function() {
            deferred.resolve();
          };
          head.appendChild(script);
        } else {
          allDeferred.then(function() {
            script.appendChild(document.createTextNode(data));
            head.appendChild(script);
          });
        }
      }

      return allDeferred;
    },
    formatDate: function(date, format) {
      if (!date) {
        return;
      }
      var o = {
        "M+": date.getMonth() + 1,
        "d+": date.getDate(),
        "H+": date.getHours(),
        "m+": date.getMinutes(),
        "s+": date.getSeconds()
      };
      if (/(y+)/.test(format)) {
        //要么2位，要么4位
        format = format.replace(
          RegExp.$1,
          (date.getFullYear() + "").substring(RegExp.$1.length == 2 ? 2 : 0)
        );
      }
      for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
          format = format.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ("00" + o[k]).substring(("" + o[k]).length)
          );
        }
      }
      return format;
    },
    parseDate: function(str, format) {
      var _format = format || dojoConfig.DateTime_format;
      var result = new Date();
      if (/(y+)/.test(_format))
        result.setFullYear(
          str.substring(
            _format.indexOf(RegExp.$1),
            _format.indexOf(RegExp.$1) + RegExp.$1.length
          )
        );
      if (/(M+)/.test(_format))
        result.setMonth(
          parseInt(
            str.substring(
              _format.indexOf(RegExp.$1),
              _format.indexOf(RegExp.$1) + RegExp.$1.length
            ),
            10
          ) - 1,
          1
        );
      if (/(d+)/.test(_format))
        result.setDate(
          str.substring(
            _format.indexOf(RegExp.$1),
            _format.indexOf(RegExp.$1) + RegExp.$1.length
          )
        );
      if (/(H+)/.test(_format))
        result.setHours(
          str.substring(
            _format.indexOf(RegExp.$1),
            _format.indexOf(RegExp.$1) + RegExp.$1.length
          )
        );
      if (/(h+)/.test(_format)) {
        var hours = str.substring(
          _format.indexOf(RegExp.$1),
          _format.indexOf(RegExp.$1) + RegExp.$1.length
        );
        hours = hours % 12 == 0 ? 12 : hours % 12;
        result.setHours(hours);
      }
      if (/(m+)/.test(_format))
        result.setMinutes(
          str.substring(
            _format.indexOf(RegExp.$1),
            _format.indexOf(RegExp.$1) + RegExp.$1.length
          )
        );
      if (/(s+)/.test(_format))
        result.setSeconds(
          str.substring(
            _format.indexOf(RegExp.$1),
            _format.indexOf(RegExp.$1) + RegExp.$1.length
          )
        );
      if (/(S+)/.test(_format))
        result.setMilliseconds(
          str.substring(
            _format.indexOf(RegExp.$1),
            _format.indexOf(RegExp.$1) + RegExp.$1.length
          )
        );
      return result;
    },
    isExistFace: function(html) {
      function ucs2decode(string) {
        var output = [],
          counter = 0,
          length = string.length,
          value,
          extra;
        while (counter < length) {
          value = string.charCodeAt(counter++);
          if (value >= 0xd800 && value <= 0xdbff && counter < length) {
            extra = string.charCodeAt(counter++);
            if ((extra & 0xfc00) == 0xdc00) {
              output.push(((value & 0x3ff) << 10) + (extra & 0x3ff) + 0x10000);
            } else {
              output.push(value);
              counter--;
            }
          } else {
            output.push(value);
          }
        }
        return output;
      }

      if (!html) return "";
      var unicodes = ucs2decode(html);
      for (var now = 0; now < unicodes.length; now++) {
        var unicode = unicodes[now];
        var isEmoji = false;
        if (unicode >= 0xe000 && unicode < 0xe538) {
          isEmoji = true;
        } else if (
          (unicode >= 0x2196 && unicode <= 0x2199) ||
          unicode == 0x25c0 ||
          unicode == 0x25b6 ||
          unicode == 0x23ea ||
          unicode == 0x23e9 ||
          (unicode >= 0x2600 && unicode <= 0x3299) ||
          (unicode >= 0x1f000 && unicode <= 0x1f9ff)
        ) {
          isEmoji = true;
          //#82310 解决不能输入。《  部首
          if (
            (unicode >= 0x3001 && unicode <= 0x3002) ||
            (unicode >= 0x3008 && unicode <= 0x300b) ||
            (unicode >= 0x3010 && unicode <= 0x3011) ||
            (unicode >= 0x2f00 && unicode <= 0x2f03) || (unicode == 0x3125) ||
            (unicode >= 0x3040 && unicode <= 0x309F) || //#160386 日文平假名
            (unicode >= 0x30A0 && unicode <= 0x30FF) || //#160386 日文片假名
            (unicode >= 0x31F0 && unicode <= 0x31FF) //#160386 日文片假名拼音扩展
          ) {
            isEmoji = false;
          }
        } else {
          if (unicode == 0x20e3) {
            if (now > 0) {
              var preCode = unicodes[now - 1];
              if (preCode == 0x23 || (preCode >= 0x30 && preCode <= 0x39)) {
                isEmoji = true;
              }
            }
          }
        }
        if (isEmoji) {
          return true;
        }
      }
      return false;
    }
  });
  return new claz();
});
