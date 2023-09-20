(function (root, factory) {
  if (typeof define === 'function' && (define.amd || define.cmd) ) {
    // 针对AMD,CMD注册一个匿名模块
    define(function () {
      return factory(root);
    });
  } else if (typeof exports === 'object') {
    // Nodejs的环境
    // 实际环境中能力无法使用
    module.exports = factory(root);
  } else {
    root['Easymi'] = root['kk'] = factory(root);
  }
}(typeof window !== 'undefined' ? window : this, function (root) {

/**
 * @module      kk
 * @namespace   kk(小写), @alias Easymi
 * @description kk入口模块
 * 整个库的核心入口模块, 定义的库的命名空间kk(以及别名 Easymi, 为兼容旧有项目), 并暴露给全局使用.
 *
 * 提供了 kk.ready, kk.noConflict 两个核心方法
 * @author      Saiya
*/

'use strict';
var App, Config, Contact, Crypto, Deferred, Device, Econtact, File, History, KK_WEBSERVER_HTTP_URL, Location, Media, Menu, Pay, Phone, Proxy, Scaner, Share, Utils, Wx, Zip, addKKEventListener, console, defaultFailCB, eventCallbacks, file, kevent, kk, normalizeEContactList, serializeNativeAppParams, utils, validateCallbacks, validateImgType, wasWrapper, wrapperDone, wrapperFail, _kk,
  __slice = [].slice,
  __hasProp = {}.hasOwnProperty,
  __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
  __indexOf = [].indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; };

_kk = root.kk;

KK_WEBSERVER_HTTP_URL = '';

kk = (function() {
  var fixReadyArgs, ready, readyCbs;
  readyCbs = null;
  /**
   * 为了解决客户端无法解析深层次复杂对象，先对参数进行特殊处理
   * 如果客户端传过来的参数已转成JSON字符串__JSON__，将其解析还原成对象
   * 如果从其他地方传过来复杂对象__JSON__，直接使用
  */

  fixReadyArgs = function() {
    var e, readyArgs;
    readyArgs = Was.readyArgs;
    if (readyArgs.callArgs && readyArgs.callArgs.__JSON__) {
      try {
        readyArgs.callArgs = JSON.parse(readyArgs.callArgs.__JSON__);
      } catch (_error) {
        e = _error;
        readyArgs.callArgs = readyArgs.callArgs.__JSON__;
      }
    }
  };
  /**
   * 监听ready事件
   * kk.ready(fn)
   *
   * KK.app.addEventListener('onCreate',fn)的快捷写法
   * @param  {Function} fn 回调, fn(lanchInfo), 接收一个对象作为参数
   *                       {
   *                         netState: 网络状态 0 离线, 1 在线
   *                         mode: 应用的打开位置 'tab' 页签中打开, 'app' 新窗口打开
   *                         startType: 应用的启动类型 1 用户请求使用应用, 2 被推送消息拉起, 3 被其他应用拉起
   *                         callArgs: 调用参数, startType为2时为 推送消息的数据, 为3 时为调用参数
   *                       }
  */

  ready = function(fn) {
    if (root.Was && Was.ready) {
      fixReadyArgs();
      fn(Was.readyArgs);
    } else {
      if (readyCbs) {
        readyCbs.push(fn);
      } else {
        readyCbs = [fn];
        if (typeof root !== "undefined" && root !== null) {
          root.addEventListener('kkJsBridgeReady', function() {
            var cb, readyArgs;
            fixReadyArgs();
            readyArgs = Was.readyArgs;
            while (cb = readyCbs.shift()) {
              cb(readyArgs);
            }
          });
        }
      }
    }
  };
  return {
    ready: ready,
    version: '1.2.37'
  };
})();

/**
 * 解决命名冲突, 将全局KK的值改回JS执行之前的值
 * @return {Object} KK自身
*/


kk.noConflict = function() {
  root.kk = _kk;
  return kk;
};

/**
 * 判断JS SDK是否运行在kk环境中
 * 使用UA检测来判断
 * @return {Boolean}
*/


kk.isKK = function() {
  return /kkPlus/i.test(navigator.userAgent);
};

/**
 * 将KK的回调函数 promise 化
 * @param  {Function|String} fn      KK能力函数，或者能力名称(如 media.getPicture)
 * @param  {Array}   args... 除了回调的其他参数
 * @return {Promise}         Promise 对象
*/


kk.defy = function() {
  var args, e, f, fn, md, mds;
  fn = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
  if (typeof fn === 'string') {
    mds = fn.split('.');
    try {
      f = kk;
      md = '';
      while (md = mds.shift()) {
        f = f[md];
      }
      if (typeof f !== 'function') {
        throw new Error();
      }
      fn = f;
    } catch (_error) {
      e = _error;
      return kk.Deferred.reject(-999, "" + fn + " is not function in KK");
    }
  }
  return new kk.Deferred(function(resolve, reject) {
    var argsLen, m, p, params;
    m = /^function\s+\w*\(([^)]+)\)/.exec(fn.toString());
    if (m) {
      params = m[1].split(',').map(function(item) {
        return item.trim();
      });
      params = params.slice(-2);
      argsLen = args.length;
      while (p = params.shift()) {
        if (p === 'done') {
          args.push(resolve);
        } else if (p === 'fail') {
          args.push(reject);
        }
      }
      if (args.length > argsLen) {
        fn.apply(null, args);
        return;
      }
    }
    resolve(fn.apply(null, args));
  });
};

wrapperDone = function(apiName, done) {
  return function() {
    var args, loginfo;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    loginfo = "[" + apiName + "][success]";
    if (Config.detailLog) {
      loginfo += "[arguments]: " + (JSON.stringify(args));
    }
    console.debug(loginfo);
    if (typeof done === "function") {
      done.apply(null, args);
    }
  };
};

wrapperFail = function(apiName, fail) {
  return function() {
    var args, loginfo;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    loginfo = "[" + apiName + "][fail]";
    if (Config.detailLog) {
      loginfo += "[arguments]: " + (JSON.stringify(args));
    }
    console.debug(loginfo);
    if (typeof fail === "function") {
      fail.apply(null, args);
    }
  };
};

wasWrapper = {
  exec: function() {
    var apiName, args, done, errMsg, fail;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    apiName = args.shift();
    done = args[3];
    args[3] = wrapperDone(apiName, done);
    fail = args[4];
    args[4] = wrapperFail(apiName, fail);
    if (typeof Was === 'undefined') {
      errMsg = '';
      if (kk.isKK()) {
        errMsg = 'KK is not ready yet, JS API should be called in kk.ready. For more information, check http://kk5.landray.com.cn:6789/jssdk/index.html#core';
      } else {
        errMsg = 'KK JS APIs are only available in KK Mobile Apps. You should open your web app in KK Mobile App.';
      }
      root.console.warn(errMsg);
      args[4](-999, errMsg);
      return;
    }
    console.debug("[" + apiName + "][call-arguments] : " + (JSON.stringify(args[2])));
    Was.exec.apply(Was, args);
  }
};

/**
 * @module      Utils
 * @description 工具模块
 * 提供了很多工具函数, 工具函数在整个库中被广泛使用, 所以是函数库的依赖库, 打包时必须包含.
 * 函数库中大多函数也可以在库之外使用, 但是以下划线开头(_)的函数为本库专用, 通用性小
 * @author      Saiya
*/


utils = Utils = {
  /**
   * 空函数
  */

  noop: function() {},
  /**
   * 判断参数obj类型
   * kk.utils.type(obj) 返回类型名称(null返回null)
   * kk.utils.type(obj, type) 若obj的类型名称和type一致,则返回true, 否则false
   *
   * 如果传递了type(类型名称), 则判断 obj类型是否与type一致, 返回Boolean
   * 否则返回类型名称
   * @param  {Object}  obj    要判断的便利
   * @param  {String}  [type] 类型名称, 可省略
   *                          省略即返回obj类型名称, 否则判断类型与type是否一致
   * @return {Boolean|String}
  */

  type: function(obj, type) {
    var t;
    if (obj != null) {
      t = Object.prototype.toString.call(obj);
      if (arguments.length === 1) {
        return t.slice(8, -1);
      } else {
        return t.toLowerCase() === ("[object " + type + "]").toLowerCase();
      }
    } else {
      t = String(obj);
      if (arguments.length > 1) {
        return t.toLowerCase() === (String(type)).toLowerCase();
      } else {
        return t;
      }
    }
  },
  /**
   * 判断变量是否为数组
   * kk.utils.isArray(arr)
   *
   * @param  {Any} arr  要判断的参数
   * @return {Boolean}  为数组则为true
  */

  isArray: Array.isArray || function(arr) {
    return utils.type(arr, 'Array');
  },
  /**
   * 判断变量是否为window对象
   * kk.utils.isWindow(obj)
   *
   * @param  {Any}  obj
   * @return {Boolean}
  */

  isWindow: function(obj) {
    return !!((obj != null) && obj.window && obj === obj.window);
  },
  /**
   * 判断参数是否为对象
   * kk.utils.isObject(obj)
   *
   * @param  {Any}  obj    要判断的变量
   * @return {Boolean}   为对象则为true
  */

  isObject: function(obj) {
    return utils.type(obj, 'Object');
  },
  /**
   * 判断变量是否为简单对象(即JSON格式对象)
   * kk.utils.isPlainObject(obj)
   *
   * window, Function, Element等不属于简单对象
   * @param  {Any}  obj 要判断的对象
   * @return {Boolean}     [description]
  */

  isPlainObject: function(obj) {
    return utils.isObject(obj) && !utils.isWindow(obj) && Object.getPrototypeOf(obj) === Object.prototype;
  },
  /**
   * splitURI / isURI / isURL 均 fork 自 https://github.com/ogt/valid-url，有做简化修改
  */

  /**
   * 分割URI 若分割成功，即返回一个数组
   *    [1] scheme(必有)
   *    [2] host(可为空字符串)
   *    [3] path(可为空字符串)
   *    [4] query(可无)
   *    [5] fragment(可无)
  */

  splitURI: function(uri) {
    return ("" + uri).match(/(?:([^:\/?#]+):)?(?:\/\/([^\/?#]*))?([^?#]*)(?:\?([^#]*))?(?:#(.*))?/);
  },
  /**
   * 判断字符串是否为合法的URI
   *   比如 tel:+1-816-555-1212
   *       telnet://192.0.2.16:80/
   *       ftp://ftp.is.co.za/rfc/rfc1808.txt
   *       http://www.ietf.org/rfc/rfc2396.txt
   *       ldap://[2001:db8::7]/c=GB?objectClass?one
   * 实现规范 https://tools.ietf.org/html/rfc3986#section-1.1.2
   * @param  {String}  value
   * @return {Boolean}       为URI则返回true
  */

  isURI: function(value) {
    var authority, path, scheme, splitted;
    if (!value) {
      return false;
    }
    if (/[^a-z0-9\:\/\?\#\[\]\@\!\$\&\'\(\)\*\+\,\;\=\.\-\_\~\%]/i.test(value)) {
      return false;
    }
    if (/%[^0-9a-f]/i.test(value)) {
      return false;
    }
    if (/%[0-9a-f](:?[^0-9a-f]|$)/i.test(value)) {
      return false;
    }
    splitted = utils.splitURI(value);
    if (!(splitted && splitted.length)) {
      return false;
    }
    scheme = splitted[1];
    authority = splitted[2];
    path = splitted[3];
    if (!(scheme && path.length >= 0)) {
      return false;
    }
    if (authority && authority.length) {
      if (!(path.length === 0 || /^\//.test(path))) {
        return false;
      }
    } else {
      if (/^\/\//.test(path)) {
        return false;
      }
    }
    if (!/^[a-z][a-z0-9\+\-\.]*$/.test(scheme.toLowerCase())) {
      return false;
    }
    return true;
  },
  /**
   * 判断字符串是否为合法的浏览器网址
   * @param  {String}  value 
   * @return {Boolean}       为网址则为true
  */

  isURL: function(value) {
    var authority, scheme, splitted;
    if (!utils.isURI(value)) {
      return false;
    }
    splitted = utils.splitURI(value);
    scheme = splitted[1];
    authority = splitted[2];
    if (!scheme) {
      return false;
    }
    if (!/^https?$/i.test(scheme)) {
      return false;
    }
    if (!authority) {
      return false;
    }
    return true;
  },
  /**
   * 判断参数是否为空(null, undefined, '', 空数组)
   * kk.utils.isEmpty(val)
   *
   * null, undefined, []及 ''被判断为空
   * @param  {Any}  val
   * @return {Boolean}   为空则为true
  */

  isEmpty: function(val) {
    return val === null || val === void 0 || val === '' || (utils.isArray(val) && !val.length);
  },
  /**
   * 判断变量是否为函数
   * kk.utils.isFunction(fn)
   *
   * @param  {Any} fn
   * @return {Boolean}
  */

  isFunction: function(fn) {
    return utils.type(fn, 'Function');
  },
  /**
   * 扩展对象
   * kk.utils.extend([deep], [dest], source, [source...], [ride])
   *
   * @param  {Boolean} deep 是否深度拷贝, 可选, 默认 false,
   * @param  {Object}  dest 要扩展的对象, 如果省略则扩展调用环境(context)
   * @param  {Object}  source 要扩展的属性来源, 可支持多个source
   * @param  {Boolean} ride 是否覆盖已有属性, 默认 true
   *                          即 source会覆盖dest中同名属性,
   *                          后面的source会覆盖前面source的同名属性
   * @return {Object}        扩展之后得到的对象
  */

  extend: function(dest, source) {
    var args, clone, deep, destK, i, k, ride, v;
    args = [].slice.call(arguments);
    if (typeof args[0] === 'boolean') {
      deep = args.shift();
      dest = args[0];
    } else {
      deep = false;
    }
    ride = typeof args[args.length - 1] === 'boolean' ? args.pop() : true;
    i = 1;
    if (args.length === 1) {
      dest = !utils.isWindow(this) ? this : {};
      i = 0;
    }
    dest || (dest = {});
    while (i < args.length) {
      if (!(source = args[i++])) {
        continue;
      }
      for (k in source) {
        if (!__hasProp.call(source, k)) continue;
        v = source[k];
        if (ride || !(k in dest)) {
          if (deep && v && (utils.isArray(v) || utils.isPlainObject(v))) {
            destK = dest[k];
            if (utils.isArray(v)) {
              clone = utils.isArray(destK) ? destK : [];
            } else {
              clone = destK && utils.isPlainObject(destK) ? destK : {};
            }
            dest[k] = utils.extend(deep, clone, v, ride);
          } else if (v !== void 0) {
            dest[k] = v;
          }
        }
      }
    }
    return dest;
  },
  /**
   * 将一个键值(可以是任意类型)对(pair)扁平化, 方便将其转换为query string
   * kk.utils.toQueryObjects(name, val, deep)
   *
   * 配合toQuerySting使用
   * @param  {String} name 键名
   * @param  {Any}    val  值
   * @param  {Boolean} deep 是否递归
   * @return {Array}
  */

  toQueryObjects: function(name, val, deep) {
    var fnSelf, k, objects, v, _i, _j, _len, _len1;
    fnSelf = utils.toQueryObjects;
    objects = [];
    if (utils.isArray(val)) {
      if (deep) {
        for (k = _i = 0, _len = val.length; _i < _len; k = ++_i) {
          v = val[k];
          objects = objects.concat(fnSelf("" + name + "[" + k + "]", v, true));
        }
      } else {
        for (k = _j = 0, _len1 = val.length; _j < _len1; k = ++_j) {
          v = val[k];
          objects.push({
            name: name,
            value: v
          });
        }
      }
    } else if (utils.isObject(val)) {
      if (deep) {
        for (k in val) {
          if (!__hasProp.call(val, k)) continue;
          v = val[k];
          objects = objects.concat(fnSelf("" + name + "[" + k + "]", v, true));
        }
      } else {
        for (k in val) {
          if (!__hasProp.call(val, k)) continue;
          v = val[k];
          objects.push({
            name: name,
            value: v
          });
        }
      }
    } else {
      objects.push({
        name: name,
        value: val
      });
    }
    return objects;
  },
  /**
   * 将对象转换为query string
   * kk.utils.toQueryString(obj)
   *
   * @param  {Object} obj
   * @return {String}
  */

  toQueryString: function(obj) {
    var k, object, objects, params, v, _i, _len;
    params = [];
    objects = [];
    for (k in obj) {
      if (!__hasProp.call(obj, k)) continue;
      v = obj[k];
      objects = objects.concat(utils.toQueryObjects(k, v, true));
    }
    for (_i = 0, _len = objects.length; _i < _len; _i++) {
      object = objects[_i];
      v = object.value;
      if (utils.isEmpty(v)) {
        v = '';
      } else if (utils.type(v, 'Date')) {
        v = utils.formatTime(v);
      }
      params.push("" + (encodeURIComponent(object.name)) + "=" + (encodeURIComponent(String(v))));
    }
    return params.join('&');
  },
  /**
   * 按keysMap中的关系，重命名 obj中的key
   * kk.utils.renameKeys(obj, keysMap, [removeOldKeys])
  
   *   keysMap: { key: 'new Name'}
   * 仅 obj 中存在 keysMap 中 key 命名的字段(即不为undefined) 且 不存在以新名称命名的键名才重命名
   * @param  {Object} obj           需要重命名键名的对象
   * @param  {Object} keysMap       旧名称(key)与新名称(val)的映射关系
   * @param  {Boolean} removeOldKeys 是否移除旧名称，默认false 即保留
   * @return {Object}               重命名后的对象
  */

  renameKeys: function(obj, keysMap, removeOldKeys) {
    var k, result, v;
    if (!(keysMap && obj)) {
      return obj;
    }
    result = utils.extend(true, {}, obj);
    for (k in keysMap) {
      if (!__hasProp.call(keysMap, k)) continue;
      v = keysMap[k];
      if (result[k] !== void 0) {
        if (result[v] === void 0) {
          result[v] = result[k];
        }
        if (removeOldKeys) {
          delete result[k];
        }
      }
    }
    return result;
  }
};

/**
 * 默认的失败回调, 打印错误日志
 * @param  {Number} code 错误代码
 * @param  {String} msg  错误消息
*/


defaultFailCB = function(code, msg) {
  console.error("[kk api defaultFailCB]code:" + code + ", msg:" + msg);
};

/**
 * 校验接口的函数是否正确, 内部使用
 * validateCallbacks(doneFn ,[failFn], [isStrict])
 *
 * failFn不为函数时, 使用默认的失败函数
 * isStrict 为true时, doneFn不为函数则返回空数组
 * isStrict 为false时, doneFn不为函数也不为空(null或undefined)也返回空数组
 * isStrict 为false时, doneFn为空时, doneFn使用空函数
 *
 * @param  {Function} doneFn 成功回调, isStrict
 * @param  {Function} failFn 失败回调, 可选, 默认为默认失败回调
 * @param  {Boolean} isStrict 是否严格校验必传成功回调, 可选
 *                              默认严格校验(即为true), 为false则允许不传递成功回调.
 * @return {Array}          包含校验过的成功失败函数数组
 *                             校验通过则有值, 不通过则为空数组
*/


validateCallbacks = function(doneFn, failFn, isStrict) {
  if (isStrict == null) {
    isStrict = true;
  }
  if (isStrict) {
    if (!utils.type(doneFn, 'function')) {
      return false;
    }
  } else {
    if ((doneFn != null) && !utils.type(doneFn, 'function')) {
      return false;
    }
  }
  if ((failFn != null) && !utils.type(failFn, 'function')) {
    return false;
  }
  return true;
};

/**
 * 校验图片文件类型
 * 接口中有多处涉及到图片的, 如截屏, 拍照
 * 目前支持的图片类型为 jpeg/png
 * 默认为png
 * @param  {String} type 文件类型
 * @return {String}      校验后的文件类型
*/


validateImgType = function(type) {
  type = ("" + type).toLowerCase();
  if (type === 'jpg') {
    type = 'jpeg';
  }
  if (type !== 'jpeg' && type !== 'png') {
    type = 'png';
  }
  return type;
};

kk.utils = Utils;

/**
 * @module      Config
 * @description 配置模块
 * @author      Saiya
*/


Config = {
  debug: true,
  detailLog: false
};

/**
 * 配置函数
 * config()         返回所有的配置项的副本
 * config(key)      获取配置中键名为key的配置项  
 * config(key, value) 设置配置项中键名为key的值为value, 返回value.
 *                       如果key不存在, 则设置失败, 返回 undefined
 * config(obj)      JSON对象, 如果obj中的键名在配置项中也存在, 则修改之
 *                      返回所有配置项的副本
 * @param  {String} args... key
*/


kk.config = function() {
  var args, k, key, type, v;
  args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
  if (!args.length) {
    return utils.extend({}, Config);
  }
  key = args[0];
  type = typeof key;
  if (args.length === 1) {
    if (type === 'string') {
      return Config[key];
    } else if (type === 'object') {
      for (k in Config) {
        if (!__hasProp.call(Config, k)) continue;
        v = Config[k];
        if (k in key) {
          Config[k] = key[k];
        }
      }
      return utils.extend({}, Config);
    } else {
      return;
    }
  }
  if (type === 'string') {
    if (key in Config) {
      return Config[key] = args[1];
    } else {

    }
  } else {

  }
};

/**
 * class Deferred
 * Promise class
 * modified from https://github.com/chrisdavies/plite
*/


Deferred = (function() {
  function Deferred(resolver) {
    this.setSuccess = __bind(this.setSuccess, this);
    this.setError = __bind(this.setError, this);
    var e, me, reject, resolve;
    this.chain = this.noop;
    this.resultGetter = null;
    me = this;
    resolve = function() {
      var args;
      args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
      return me.resolve.apply(me, args);
    };
    reject = function() {
      var args;
      args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
      return me.reject.apply(me, args);
    };
    try {
      resolver && resolver(resolve, reject);
    } catch (_error) {
      e = _error;
      reject(e);
    }
  }

  Deferred.prototype.noop = function() {};

  Deferred.prototype.processResult = function(args, callback, reject) {
    var me;
    me = this;
    if (args[0] && args[0].then) {
      args[0].then(function() {
        var args;
        args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
        me.processResult(args, callback, reject);
      })["catch"](function() {
        var args;
        args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
        me.processResult(args, reject, reject);
      });
    } else {
      callback.apply(null, args);
    }
  };

  Deferred.prototype.setResult = function(callbackRunner) {
    this.resultGetter = function(done, fail) {
      var e;
      try {
        callbackRunner(done, fail);
      } catch (_error) {
        e = _error;
        fail(e);
      }
    };
    this.chain();
    this.chain = void 0;
  };

  Deferred.prototype.setError = function() {
    var args;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    this.setResult(function(done, fail) {
      fail.apply(null, args);
    });
  };

  Deferred.prototype.setSuccess = function() {
    var args;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    this.setResult(function(done) {
      done.apply(null, args);
    });
  };

  Deferred.prototype.buildChain = function(onDone, onFail) {
    var me, prevChain;
    prevChain = this.chain;
    me = this;
    this.chain = function() {
      prevChain();
      me.resultGetter(onDone, onFail);
    };
  };

  Deferred.prototype.resolve = function() {
    var args;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    !this.resultGetter && this.processResult(args, this.setSuccess, this.setError);
  };

  Deferred.prototype.reject = function() {
    var args;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    return !this.resultGetter && this.processResult(args, this.setError, this.setError);
  };

  Deferred.prototype.then = function(done, fail) {
    var me, resolveCallback;
    me = this;
    if (this.resultGetter) {
      resolveCallback = function() {
        var args;
        args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
        return me.resultGetter.apply(me, args);
      };
    } else {
      resolveCallback = function() {
        var args;
        args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
        return me.buildChain.apply(me, args);
      };
    }
    return new Deferred(function(resolve, reject) {
      resolveCallback(function() {
        var args;
        args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
        if (typeof done === 'function') {
          return resolve(done.apply(null, args));
        } else {
          return resolve.apply(null, args);
        }
      }, function() {
        var args;
        args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
        if (typeof fail === 'function') {
          return reject(fail.apply(null, args));
        } else {
          return reject.apply(null, args);
        }
      });
    });
  };

  Deferred.prototype["catch"] = function(callback) {
    return this.then(null, callback);
  };

  Deferred.prototype.done = function(callback) {
    return this.then(callback);
  };

  Deferred.prototype.always = function(callback) {
    return this.then(callback, callback);
  };

  Deferred.prototype["finally"] = function() {
    var args;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    return this.always.apply(this, args);
  };

  Deferred.prototype.fail = function(callback) {
    return this.then(null, callback);
  };

  Deferred.resolve = function() {
    var args;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    return new Deferred(function(resolve) {
      return resolve.apply(null, args);
    });
  };

  Deferred.reject = function() {
    var args;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    return new Deferred(function(resolve, reject) {
      return reject.apply(null, args);
    });
  };

  Deferred.race = function(promises) {
    if (promises == null) {
      promises = [];
    }
    return new Deferred(function(resolve, reject) {
      var p, _i, _len;
      if (!promises.length) {
        return resolve();
      }
      for (_i = 0, _len = promises.length; _i < _len; _i++) {
        p = promises[_i];
        p && p.then && p.then(resolve, reject);
      }
    });
  };

  Deferred.all = function(promises) {
    if (promises == null) {
      promises = [];
    }
    return new Deferred(function(resolve, reject) {
      var count, decrement, k, v, waitfor, _i, _len;
      count = promises.length;
      if (!count) {
        return resolve();
      }
      decrement = function() {
        --count <= 0 && resolve(promises);
      };
      waitfor = function(p, i) {
        if (p && p.then) {
          p.then(function() {
            var args;
            args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
            promises[i] = args;
            return decrement();
          }, reject);
        } else {
          decrement();
        }
      };
      for (k = _i = 0, _len = promises.length; _i < _len; k = ++_i) {
        v = promises[k];
        waitfor(v, k);
      }
    });
  };

  return Deferred;

})();

kk.Deferred = Deferred;

/**
 * @module      console
 * @description 日志模块
 * 内部使用, 不导出到kk. 控制日志输出
 * 如果kk.isDebug为false则不输出日志
 * 目前只支持三个日志: log, debug, error
 * @author      Saiya
*/


console = (function() {
  var logFns, logger;
  logFns = ['log', 'debug', 'error', 'warn'];
  logger = {};
  if (root.console) {
    logFns.forEach(function(v) {
      logger[v] = function() {
        Config.debug && root.console[v].apply(root.console, arguments);
      };
    });
  } else {
    logFns.forEach(function(v) {
      logger[v] = function() {};
    });
  }
  return logger;
})();

/**
 * KK事件模块，支持同一事件多次绑定
 * 也支持取消事件绑定
*/


eventCallbacks = {};

addKKEventListener = function(eventName) {
  var evt;
  evt = "on" + (eventName.charAt(0).toUpperCase()) + (eventName.slice(1));
  Was.addEventListener(evt, function() {
    var args, cb, cbs, loginfo, result, ret, _i, _len;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    cbs = eventCallbacks[eventName];
    if (!(cbs && cbs.length)) {
      return;
    }
    loginfo = "" + eventName + " was triggered";
    if (Config.detailLog) {
      loginfo += ", with params:" + JSON.stringify(args);
    }
    console.debug(loginfo);
    result = void 0;
    ret = void 0;
    for (_i = 0, _len = cbs.length; _i < _len; _i++) {
      cb = cbs[_i];
      ret = cb.apply(null, args);
      if (ret != null) {
        result = ret;
      }
      if (result === false) {
        break;
      }
    }
    if (result === false) {
      return result;
    }
  });
};

kevent = {
  /**
   * 监听KK事件
   * @param  {String}   eventName 事件名
   * @param  {Function} cb        事件回调
  */

  on: function(eventName, cb) {
    var cbs;
    if (typeof cb !== 'function') {
      return;
    }
    cbs = eventCallbacks[eventName];
    if (cbs) {
      if (__indexOf.call(cbs, cb) < 0) {
        cbs.push(cb);
      }
    } else {
      eventCallbacks[eventName] = [cb];
      addKKEventListener(eventName);
    }
  },
  /**
   * 取消事件监听
   * 只支持取消某一个监听函数，不支持取消某一事件所有监听函数
   * @param  {String}   eventName 事件名
   * @param  {Function} cb        事件回调
  */

  off: function(eventName, cb) {
    var cbs, len;
    cbs = eventCallbacks[eventName];
    if (!(cbs && cbs.length && typeof cb === 'function')) {
      return;
    }
    len = cbs.length;
    while (len--) {
      if (cbs[len] === cb) {
        cbs.splice(len, 1);
        break;
      }
    }
  }
};

/**
 * @module      App
 * @description app模块
 * 提供了控制app的基础能力, 如
 * 应用退出, 拉起引用, 监听应用事件, 应用徽章控制等等
 * @author      Saiya
*/


App = {
  /**
   * 退出应用, 即关闭webview
  */

  exit: function() {
    Was.exec('app', 'exitApp', {});
  },
  /**
   * 判断当前应用是否为混合应用
   * 根据location的协议判断
   * @return {Boolean}
  */

  isOfflineApp: function() {
    return /^(file|kkapp)/i.test(location.protocol);
  },
  /**
   * 调用客户端内置的其他混合应用
   * kk.app.callApp(appInfo, [done], [fail])
   * kk.app.callApp(appInfo, args, [done], [fail])
   *
   * @param  {Object}   appInfo  应用信息； 亦可以为字符串，为URL则当作URL，否则当作code
   *                             {
   *                               url: '', // 应用的URL
   *                               code: '' // 应用代码
   *                             }
   * @param  {Object}   args 调用的参数
   * @param  {Function} done 成功回调, done()
   * @param  {Function} fail 失败回调
  */

  callApp: function(appInfo, args, done, fail) {
    if (typeof args === 'function' || !args) {
      fail = done;
      done = args;
      args = null;
    }
    if (args != null) {
      args = {
        __JSON__: JSON.stringify(args)
      };
    }
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    if (!appInfo) {
      fail(-1, 'appInfo should not be empty');
      return;
    }
    args || (args = {});
    if (typeof appInfo === 'string') {
      if (utils.isURL(appInfo)) {
        appInfo = {
          url: appInfo
        };
      } else {
        appInfo = {
          code: appInfo
        };
      }
    }
    appInfo = utils.renameKeys(appInfo, {
      code: '__command',
      url: '__startUrl'
    });
    args = utils.extend(appInfo, args);
    wasWrapper.exec('app.callApp', 'app', 'callApp', args, done, fail);
  },
  /**
   * 启动第三方native应用
   * kk.app.callNativeApp(options, [done], [fail])
   *
   * @param  {Object}   options 调用应用的参数
   *  iOS输入参数：
   *      {
   *          url:Native应用注册处理的url。
   *      }
   *  Android输入参数：
   *      {
   *          url:Intent url
   *          type:Intent MimeType
   *          action:Intent Action
   *          category:Intent category。暂不支持多个。
   *          package:Intent package。
   *          class:Intent class。
   *          otherParam:作为extra放到启动Intent中.
   *            key为extra的name,value为char(3)+char(type)+extra_value,其中:
   *            type==1为字符串，此时value可以直接为extra_value。
   *            type==2为整型。
   *            type==3为浮点型。
   *            type==4为字符串数组。数组元素用char(4)分隔。
   *            type==5为整型数组。数组元素用char(4)分隔。
   *            type==6为浮点型数组。数组元素用char(4)分隔。
   *            type==8为HTML内容，需要执行HTML.parse操作。
   *            type==9为url。
   *      }
   * @param  {Function} done   成功回调, done()
   * @param  {Function}   fail   失败回调
   *
  */

  callNativeApp: function(options, done, fail) {
    var k, params, v;
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    params = {};
    for (k in options) {
      if (!__hasProp.call(options, k)) continue;
      v = options[k];
      params['__' + k.replace(/^__/, '')] = v;
    }
    if (params.__otherParam) {
      utils.extend(params, serializeNativeAppParams(params.__otherParam), false);
    }
    wasWrapper.exec('app.callNativeApp', 'app', 'callNativeApp', params, done, fail);
  },
  /**
   * 添加应用事件, 新的快捷方法
   * kk.app.on(eventName, eventCB)
   *
   * back事件，3秒内没有阻止返回事件，则执行默认行为
   *
   * @param  {String}   eventName 事件名称, 不带 on 前缀
   * @param  {Function} eventCB   事件回调, eventCB(args)
  */

  on: kevent.on,
  /**
   * 取消应用事件, 新的快捷方法
   * kk.app.off(eventName, eventCB)
   * 只支持取消某一个监听函数，不支持取消某一事件所有监听函数
   *
   * @param  {String}   eventName 事件名称, 不带 on 前缀
   * @param  {Function} eventCB   事件回调, eventCB(args)
  */

  off: kevent.off,
  /**
   * 设置应用的数字标记
   * kk.app.setBadge(options, [done], [fail])
   *
   * @param {Object} options 参数选项
   *                         {
   *                           num: 图标数字
   *                         }
   * @param  {Function} done 成功回调, done({num:3})
   * @param  {Function} fail 失败回调
  */

  setBadge: function(options, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('app.setBadge', 'message', 'setAppBadge', options, done, fail);
  },
  /**
   * 获取应用数字图标
   * kk.app.getBadge(done, [fail])
   *
   * @param  {Function} done 成功回调, done({num:3})
   * @param  {Function} fail 失败回调
  */

  getBadge: function(done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('app.getBadge', 'message', 'getAppBadge', {}, done, fail);
  },
  /**
   * 获取网络状态
   * kk.app.getNetState()
   *
   * @return {Object} 网络状态。{
   *                               netState: 1/0; //1在线；0离线；
   *                            }
   *
  */

  getNetState: function() {
    return {
      netState: Was.netState
    };
  },
  /**
   * 获取设备信息
   * kk.app.getDeviceInfo()
   *
   * @return {Object}
   * {
   *     os: 操作系统,iOS/Android/windows(模拟器)
   *     type: 类型，phone/pad
   *     pixel: 分辨率，形如:1024x767（大数在前）
   *     largerPixel: pixel中教大的一边
   *     smallerPixel: pixel中的较小的一边
   *     deviceID: 设备物理唯一ID
   * }
  */

  getDeviceInfo: function() {
    var deviceInfo, pixels, result, screen;
    if (deviceInfo = Was.deviceInfo) {
      pixels = deviceInfo.pixel.split('x');
      result = utils.renameKeys(deviceInfo, {
        deviceUUID: 'deviceID'
      }, true);
      result = utils.extend(result, {
        largerPixel: parseInt(pixels[0]),
        smallerPixel: parseInt(pixels[1])
      });
    } else {
      screen = window.screen;
      result = {
        os: 'windows',
        type: 'pad',
        largerPixel: screen.width,
        smallerPixel: screen.height,
        pixel: "" + screen.width + "x" + screen.height
      };
    }
    return result;
  },
  /**
   * 获取用户信息
   * kk.app.getUserInfo()
   *
   * 返回用户信息的副本, 防止被窜改
   * @return {Object} 用户信息一般包含以下内容
   *         userId: 用户id
   *         loginName: 用户登录名
   *         userName: 用户显示名称
  */

  getUserInfo: function() {
    return Econtact.getUserInfo();
  },
  /**
   * 获取应用信息
   * kk.app.getAppInfo()
   *
   * 返回应用信息的副本，防止被篡改
   * @return {Object} 应用信息包括以下内容
   *         appID: 应用ID
   *         name: 应用名称
   *         code: 应用代码
  */

  getAppInfo: function() {
    return utils.extend({}, Was.appInfo);
  },
  /**
   * 根据应用代码获取应用图标，若不传参数则返回自身应用的图标
   * kk.app.getAppIcon([appCode])
   * 
   * @param  {String} appCode 应用代码
   * @return {String}         应用图标url
  */

  getAppIcon: function(appCode) {
    appCode = appCode || Was.appInfo.code;
    return KK_WEBSERVER_HTTP_URL + '/serverj/getAppIcon?appCode=' + encodeURIComponent(appCode);
  },
  /**
   * 获取客户端信息
   * kk.app.getClientInfo()
   *
   * 返回客户端信息的新描述副本，防止被篡改，与Was.kkClientInfo略有不同
   * @return {Object} 客户端信息包括以下内容
   *         name: 客户端名称
   *         version: 客户端可见版本号: v5.2.16.1R2034
   *         semver: 语义化版本号 5.2.6.1
   *         innerVersion: 客户端内部版本号
   *         corpName: 企业名称
   *         corpID: 企业ID,
   *         lang: 客户端语言，zh-CN 中文， en-US 英文
   *         webServer: kk web server 地址
  */

  getClientInfo: function() {
    var kkClientInfo, m, semverReg;
    if (!Was.kkClientInfo) {
      return {};
    }
    semverReg = /^v?(\d(\.\d)+)/;
    kkClientInfo = utils.renameKeys(Was.kkClientInfo, {
      version: 'innerVersion'
    }, true);
    kkClientInfo = utils.renameKeys(kkClientInfo, {
      visibleVersion: 'version'
    }, true);
    m = semverReg.exec(kkClientInfo.version);
    if (m) {
      kkClientInfo.semver = m[1];
    }
    return kkClientInfo;
  },
  /**
   * 获取客户端配置信息
   * kk.app.getClientConfig(configName, [done], [fail])
   * 
   * @param  {String}   configName 配置项名称
   * @param  {Function} done       成功回调，回调参数为配置项内容
  */

  getClientConfig: function(configName, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('app.getClientConfig', 'config', 'getConfigValue', {
      configName: configName
    }, function(v) {
      return done(v.configValue);
    }, fail);
  },
  /**
   * 给webview设置cookie, 一般用于离线页面跳转至在线页面时设置用户身份验证信息
   * kk.app.setCookie(options, [done], [fail])
   *
   * @param {Object}   options    参数选项
   *                              {
   *                                url: cookie生效的域
   *                                cookie: cookie字符串
   *                              }
   * @param {Function} done   成功回调, done()
   * @param {Function}   fail   失败回调
  */

  setCookie: function(options, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('app.setCookie', 'cookie', 'setCookie', options, done, fail);
  },
  /**
   * 从webview中获取cookie
   * kk.app.getCookie(options, done, [fail])
   *
   * @param  {Object}   options  参数选项
   *                             {
   *                               url: 要获取cookie的url
   *                             }
   * @param  {Function} done 成功回调, done({cookie: cookieStr})
   *                           接收属性有cookie字符串的对象作为参数
   * @param  {Function}   fail 失败回调
  */

  getCookie: function(options, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('app.getCookie', 'cookie', 'getCookie', options, done, fail);
  },
  /**
   * 应用截屏
   * kk.app.captureScreen([options,] done, [fail])
   *
   * @param  {Object}   option 配置参数, 可选, 不传则全部使用默认参数
   *                        所有的配置项有
   *                        targetWidth:输出图片宽, 默认100
   *                        targetHeight:输出图片高, 需和targetHeight配合使用, 默认100
   *                        encodingType:输出格式 (jpeg,png), 默认png
   *                        quality: 输出图片质量 (0-100), 默认50
   *                        destinationType：输出形式(file,data), 默认 data
   *                        savePath：保存路径(只支持协议格式,如果不指定默认保存在
   *                            sdcard://easymi/ 下
   * @param  {Function} done   成功回调, done(obj)
   *                        成功回调函数接收一个对象作为参数, 对象中的参数有:
   *                        imageURI 处理后的文件保存路径(当destinationType为file时)
   *                        imageFileOSPath 处理后的文件的操作系统路径(当destinationType为file时)
   *                        imageData 图片内容的base编码(当destinationType为data时)"
   * @param  {Function}   fail   失败回调
   *                         失败毁掉接收错误代码作为参数, 目前定义的错误代码有:
   *                             -2: 参数错误
  */

  captureScreen: function(options, done, fail) {
    var defaultOpts;
    if (utils.isFunction(options)) {
      fail = done;
      done = options;
      options = {};
    }
    if (!validateCallbacks(done, fail)) {
      return;
    }
    defaultOpts = {
      targetWidth: 100,
      targetHeight: 100,
      encodingType: 'png',
      quality: 50,
      destinationType: 'data'
    };
    options = utils.extend({}, defaultOpts, options || {});
    options.encodingType = validateImgType(options.encodingType);
    wasWrapper.exec('app.captureScreen', 'app', 'screenCapture', options, function(args) {
      if (options.destinationType === 'data') {
        args.imageData = "data:image/" + options.encodingType + ";base64," + args.imageData;
      }
      done(args);
    }, fail);
  },
  /**
   * 显示webview标题栏/导航栏
   * kk.app.showTitleBar()
  */

  showTitleBar: function() {
    wasWrapper.exec('app.showTitleBar', 'app', 'config', {
      isTitleBarVisible: true
    }, null, null);
  },
  /**
   * 隐藏webview标题栏/导航栏
   * kk.app.hideTitleBar()
  */

  hideTitleBar: function() {
    wasWrapper.exec('app.hideTitleBar', 'app', 'config', {
      'isTitleBarVisible': false
    }, null, null);
  },
  /**
   * 设置native导航栏标题
   * kk.app.setTitle(title)
   * 
   * @param {String} title 新标题
  */

  setTitle: function(title) {
    if (title === '') {
      return;
    }
    title = "" + title;
    wasWrapper.exec('app.setTitle', 'app', 'config', {
      'title': title
    }, null, null);
  },
  onUpdate: function(version, cb) {
    var key;
    if (kk.app.onUpdate.triggered) {
      return;
    }
    key = 'kk-app-' + kk.app.getAppInfo().appID + 'ver';
    version += '';
    kk.app.onUpdate.triggered = true;
    if (localStorage.getItem(key) !== version) {
      if (typeof cb === "function") {
        cb();
      }
    }
    localStorage.setItem(key, version);
  }
};

/**
 * 获取KK webserver的http地址，由于地址是定值，在获取后保存至全局
*/


kk.ready(function() {
  var clientInfo;
  clientInfo = App.getClientInfo();
  if (clientInfo.webServer) {
    KK_WEBSERVER_HTTP_URL = clientInfo.webServer.replace(/\/$/, '');
  } else {
    App.getClientConfig('kk_config_webserver_http_url', function(v) {
      KK_WEBSERVER_HTTP_URL = v && v.replace(/\/$/, '');
    });
  }
});

serializeNativeAppParams = function(params) {
  var first, floatReg, k, result, v;
  if (!params) {
    return;
  }
  floatReg = /^\d+\.\d+$/;
  result = {};
  for (k in params) {
    if (!__hasProp.call(params, k)) continue;
    v = params[k];
    if (utils.isArray(v)) {
      if (v.length && typeof v[0] === 'number') {
        if (floatReg.test(v[0])) {
          result[k] = String.fromCharCode(3) + '06' + v.join(String.fromCharCode(4));
        } else {
          result[k] = String.fromCharCode(3) + '05' + v.join(String.fromCharCode(4));
        }
      } else {
        result[k] = String.fromCharCode(3) + '04' + v.join(String.fromCharCode(4));
      }
    } else {
      if (typeof v === 'number') {
        if (floatReg.test(v)) {
          result[k] = String.fromCharCode(3) + '03' + v;
        } else {
          result[k] = String.fromCharCode(3) + '02' + v;
        }
      } else if (typeof v === 'string') {
        first = v.charAt(0);
        switch (first) {
          case String.fromCharCode(128):
            result[k] = String.fromCharCode(3) + '08' + v.slice(1);
            break;
          case String.fromCharCode(129):
            result[k] = String.fromCharCode(3) + '09' + v.slice(1);
            break;
          default:
            result[k] = String.fromCharCode(3) + '01' + v;
        }
      }
    }
  }
  return result;
};

kk.app = App;

/**
 * @module      Proxy
 * @description 代理请求模块
 * 封装了与服务器进行数据交互的能力
 *
 * 其中Proxy.request类似于$.ajax, 可以与服务器进行文本数据交互.
 *
 * Proxy.Download和Proxy.Upload可以和服务器之间进行文件的下载和上传, 支持断点续传. 这两个能力被封装成了类, 需要 new 创建实例来使用.
 *
 * Proxy.uploadView 也可以上传文件, 使用的时http post方式, 上传大文件不稳定. 目前仅限于EKP项目中使用
 * @author      Saiya
*/


/**
 * @author      Saiya
 * @date        2015-03-20
 * @description 代理请求模块
 * 本模块包含了与服务器有请求交互的能力
*/


Proxy = (function() {
  var defaultOpts, fileTransProto, proxy, setOptions;
  proxy = {};
  defaultOpts = {};
  /**
   * 发送请求前, 预处理请求参数
   * @param {Array} args 参数数组
   * @return {Object} 预处理之后的参数
   * 参数使用jQuery风格
   * jQuery.ajax([url], options)
  */

  setOptions = function(args) {
    var content, contentType, options, result, url, userAgent;
    result = {};
    url = args[0];
    options = args[1] || {};
    if (typeof url === 'string') {
      options.url = url;
    } else if (utils.isObject(url)) {
      options = url;
    } else {
      return false;
    }
    if (options.contentType == null) {
      options.contentType = defaultOpts.contentType || 'form';
    }
    options = utils.extend(true, {}, defaultOpts, options);
    result.dataType = options.dataType;
    result.__url = options.url;
    if (!options.data) {
      options.data = {
        '_easymi_random_': new Date().getTime()
      };
    }
    if (!options.contentType || options.contentType === 'form') {
      contentType = 'application/x-www-form-urlencoded';
    } else if (options.contentType === 'json') {
      contentType = 'application/json';
    } else {
      contentType = options.contentType;
    }
    content = options.data;
    if (typeof content !== 'string') {
      if (contentType.indexOf('x-www-form-urlencoded') > -1) {
        content = utils.toQueryString(content);
      } else if (contentType.indexOf('application/json') > -1) {
        content = JSON.stringify(content);
      } else {
        content = String(content);
      }
    }
    if (contentType.indexOf('charset') === -1) {
      contentType = contentType.replace(/;?\s*$/, "; charset=UTF-8");
    }
    userAgent = root.navigator.userAgent;
    if (window.Was && window.Was.isEmulator) {
      userAgent += ' KKEmulator';
    }
    result['User-Agent'] = userAgent;
    result['Content-Type'] = contentType;
    result.__content = content;
    if (options.headers == null) {
      options.headers = {};
    }
    utils.extend(result, options.headers, false);
    result.failure = options.error;
    result.success = options.success;
    return result;
  };
  /**
   * 设置请求的默认参数
   * kk.proxy.requestSetup(options)
   *
   * @param  {Object} options 默认参数
   * @return {Proxy}         proxy自身
  */

  proxy.requestSetup = function(options) {
    defaultOpts = options || {};
    return this;
  };
  /**
   * 代理请求
   * @param {Obeject} options 请求参数, 参数如下
   * {
   *      url:'/add.do',      //请求的URL
   *      data:{            //请求的参数,JSON Object
   *          x:'xxx',
   *          y:'yyy'
   *     },
   *     encoding:发送参数是的编码格式，缺省为UTF-8
   *     contentType:参数格式。form|json。缺省为form。
   *          如果是form,则到业务服务器请求的Content-Type为
   *              application/x-www-form-urlencoded
   *          如果是json,则到业务服务器请求的Content-Type为
   *              application/json
   *     headers:额外的http头，Object
   *     success:function({content:'content'}){...}         //成功回调
   *     error:function(code,msg){...}          //失败回调
   * }
  */

  proxy.request = function() {
    var dataType, done, fail, options;
    options = setOptions([].slice.call(arguments));
    if (!validateCallbacks(options.success, options.failure, false)) {
      return;
    }
    if (!options.__url) {
      return;
    }
    dataType = options.dataType;
    done = options.success;
    fail = options.failure;
    delete options.dataType;
    delete options.success;
    delete options.failure;
    return new Deferred(function(resolve, reject) {
      return wasWrapper.exec('proxy.request', 'message', 'send', options, function(res) {
        var e;
        res = res.content;
        if (dataType === 'json') {
          try {
            res = JSON.parse(res);
          } catch (_error) {
            e = _error;
            reject(-1, 'data from server isn\'t a valid json string');
            return;
          }
        }
        resolve(res);
      }, reject);
    }).then(done, fail);
  };
  /**
   * 文件上传下载的原型(共有函数)
  */

  fileTransProto = {
    /**
     * 初始化
     * @param  {Object}   @options     {
     *                                   url: '',  //  上传地址
     *                                   path: '', //  文件在本地的地址
     *                                   isContinuous: '' // 是否断点续传
     *                                 }
     * @param  {Function} done     成功回调, done({progress: 98, path: 'xxx.png'})
     *                             接收含有进度和文件在本地的地址的对象作为参数
     * @param  {Function} fail     失败回调
     * @param  {String}   @plugin  插件API名, 为区分是上传还是下载
     * @param  {Array}    @methods 上传/下载 的 开始/暂停/停止 的方法名称
     * @return {Instance}
    */

    init: function(options, done, fail, plugin, methods) {
      this.options = options;
      this.plugin = plugin;
      this.methods = methods;
      if (!validateCallbacks(done, fail)) {
        return;
      }
      this.options.taskflag = !this.options.isContinuous;
      this.done = done;
      this.fail = fail;
      return this;
    },
    /**
     * 开始下载/上传
     * @param  {Boolean} isImmediate 是否为即时任务开始 上传/下载
     *                               如果为即时任务, 则不支持断点 上传/下载
     *                               默认不立即开始
    */

    start: function() {
      wasWrapper.exec("proxy." + this.plugin + ".start", this.plugin, this.methods[0], this.options, this.done, this.fail);
      return this;
    },
    /**
     * 暂停下载/上传
    */

    pause: function() {
      wasWrapper.exec("proxy." + this.plugin + ".pause/resume", this.plugin, this.methods[1], {
        url: this.options.url
      });
      return this;
    },
    /**
     * 恢复下载/上传, 再次调用pause即恢复
    */

    resume: function() {
      return this.start();
    },
    /**
     * 停止上传/下载
    */

    stop: function() {
      wasWrapper.exec("proxy." + this.plugin + ".stop", this.plugin, this.methods[2], {
        url: this.options.url
      });
      return this;
    }
  };
  /**
   * 下载的构造函数
   * @param {Object} options 参数
   *                         {
   *                           url: '', // 下载地址
   *                           path: '', // 文件本地地址
   *                           isContinuous: false // 是否断点续传, true 断点续传
   *                         }
  */

  proxy.Download = function(options, done, fail) {
    this.init(options, done, fail, 'download', ['download', 'pausedown', 'stopdown']);
  };
  utils.extend(proxy.Download.prototype, fileTransProto);
  /**
   * 上传的构造函数
   * @param {Object} options 参数
   *                         {
   *                           url: '', // 上传地址
   *                           path: '', // 文件本地地址
   *                           isContinuous: false // 是否断点续传, true 断点续传
   *                         }
  */

  proxy.Upload = function(options, done, fail) {
    this.init(options, done, fail, 'upload', ['upload', 'pauseupload', 'stopupload']);
  };
  utils.extend(proxy.Upload.prototype, fileTransProto);
  /**
   * 针对EKP的上传接口
   * kk.proxy.uploadView(options, [done], [fail])
   *
   * @param  {Object}   options 上传的配置项, 配置项需包含以下参数
   *                         {
   *                             token 上传的token
   *                             url 上传至服务器的url
   *                             fullPath 文件在本地的路径
   *                         }
   * @param  {Function} done    成功回调, done(info), 可选, 接收一个对象作为参数, 包含以下字段
   *                         {
   *                             progress 进度。0-100，100表示上传成功
   *                             // 以下参数只有上传成功才返回。
   *                             filekey 服务端返回的filekey。
   *                             name 上传文件名。
   *                             fullPath  上传文件全路径。
   *                             type 上传文件mime-type
   *                             size  上传文件大小（单位为字节）
   *                         }
   * @param  {Function} fail    失败回调
  */

  proxy.ekpUpload = function(options, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    if (options.userkey && !options.token) {
      options.token = options.userkey;
    }
    wasWrapper.exec('proxy.uploadView', 'ekpupload', 'upload', options, done, fail);
  };
  proxy.uploadView = proxy.ekpUpload;
  return proxy;
})();

kk.proxy = Proxy;

/**
 * @module      Device
 * @description 设备模块
 * 获取或设备的信息
 * @author      Saiya
*/


Device = {
  /**
   * 设置设备屏幕旋转方向
   * kk.device.setOrientation(orientation)
   *
   * @param {Number} orientation 旋转方向, 可选值有
   *                    portrait, 竖屏
   *                    landscape, 横屏
   *                    auto: 自动旋转
  */

  setOrientation: function(orientation) {
    var orientations;
    orientations = {
      portrait: 1,
      landscape: 2,
      auto: 3
    };
    orientation = orientations[orientation] || orientation;
    wasWrapper.exec('device.setOrientation', 'device', 'setOrientation', {
      orientation: orientation
    }, true, defaultFailCB);
  },
  /**
   * 获取设备屏幕旋转方向设置
   * @param  {Function} done 成功回调，接收一个字符串作为参数，可能的值有
   *                           portrait, 竖屏
   *                           landscape, 横屏
   *                           auto: 自动旋转
  */

  getOrientation: function(done) {
    if (!validateCallbacks(done)) {
      return;
    }
    wasWrapper.exec('device.getOrientation', 'device', 'getOrientation', null, function(res) {
      var orientations;
      orientations = ['portrait', 'landscape', 'auto'];
      done(orientations[res.orientation - 1]);
    });
  },
  /**
   * 获取设备使用的网络类型
   * kk.device.getNetType(done, [fail])
   *
   * @param  {Function} done      成功回调, done({netType: 'netType'})
   *                              接收网络类型作为参数, 可选类型为:
   *                              2G/3G/WIFI，如果无网络返回''.
   *                              iOS下2G, 3G均返回 3G
   * @param  {Function}   fail    失败回调
  */

  getNetType: function(done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('device.getNetType', 'device', 'getCurrentNetType', {}, function(netInfo) {
      done(netInfo);
    }, fail);
  },
  /**
   * 设置操作系统剪贴板
   * kk.device.setClipboard(text)
   * @param {String} text 要复制的内容
  */

  setClipboard: function(text) {
    wasWrapper.exec('device.setClipboard', 'device', 'setClipboardData', {
      data: text
    }, null, null);
  }
};

kk.device = Device;

/**
 * @module      Menu
 * @description menu模块
 * 提供了对webview右上角的菜单项的相关能力
 * 如显示或隐藏菜单项、分享等等
 * @author      Saiya
*/


Menu = {
  /**
   * 展示右上角菜单按钮
   * kk.menu.showMenuItems([menuList])
   * 
   * @param {String | Array} menuList 要展示的菜单项，可选
   *                                  可为单一项的字符串或由多个单一项组成的数组
  */

  showMenuItems: function(menuList) {
    var options;
    options = {
      isMenuButtonVisible: true
    };
    if (menuList != null) {
      if (!utils.isArray(menuList)) {
        menuList = [menuList];
      }
      options.menuList = menuList;
    }
    wasWrapper.exec('menu.showMenuItems', 'app', 'config', options, null, null);
  },
  /**
   * 隐藏右上角菜单按钮
   * kk.menu.hideMenuItems([menuList])
   * 
   * @param {String | Array} menuList 要隐藏的菜单项，可选
   *                                  可为单一项的字符串或由多个单一项组成的数组
  */

  hideMenuItems: function(menuList) {
    var options;
    options = {
      isMenuButtonVisible: false
    };
    if (menuList != null) {
      if (!utils.isArray(menuList)) {
        menuList = [menuList];
      }
      options.menuList = menuList;
    }
    wasWrapper.exec('menu.hideMenuItems', 'app', 'config', options, null, null);
  }
};

kk.menu = Menu;

/**
 * @module      Media
 * @description 媒体模块
 * 封装拍照, 录音, 录制视频, 选择相册, 存储图片, 播放音视频等功能
 * @author      Saiya
*/


Media = {
  /**
   * 获取照片
   * kk.media.getPicture([options,] done, [fail])
   *
   * @param  {Object}   options 配置参数, 可配置的参数项如下:
   *          sourceType      : 数据来源  album/camera,缺省为camera
   *          destinationType : 期望的数据类型 file|data,缺省为data
   *          targetWidth     : 期望的图片宽度，缺省为100
   *          targetHeight    : 期望的图片高度  (必须和targetWidth配合，同时使用)，缺省为100
   *          encodingType    : 期望得到的图片的格式  jpeg/ png，缺省为png
   *          quality         : 图片压缩率 0-100，缺省为50，数值越高，体积越大，清晰度越高
   *          count           : 需要的图片数量, 默认一张
   *          savePath        : 如果destinationType为file,savePath可以设置保存的文件路径
   *                            文件路径的格式参见《EasyMI JS API参考手册.doc》中的说明
   *          exifFlag        : 是否在照片中保存GPS位置信息
   *                            只有在 sourceType为camera,destinationType为file, encodingType为jpeg
   *                            三种条件下时，才生效
   * @param  {Function} done    成功回调, done({
   *                                            imageData: 'data:image/gif;base64,232323',  // destinationType = data 图片的base64编码, 可以直接设置为img元素的src来使用；
   *                                            imageFileOSPath: 'abc.png', // destinationType = file 为图片在操作系统上的实际路径;
   *                                            imageURI: 'sdcard://abc.png' // destinationType = file 为参数传入的路径(若没有传入，则系统自动生成一个路径)；
   *                                           })；
   * @param  {Function}   fail    失败回调
  */

  getPicture: function(options, done, fail) {
    var defaultOpts;
    if (utils.isFunction(options)) {
      fail = done;
      done = options;
      options = {};
    }
    if (!validateCallbacks(done, fail)) {
      return;
    }
    defaultOpts = {
      sourceType: 'camera',
      targetWidth: 100,
      targetHeight: 100,
      encodingType: 'png',
      quality: 50,
      destinationType: 'file'
    };
    options = utils.extend({}, defaultOpts, options || {});
    options.encodingType = validateImgType(options.encodingType);
    if (utils.isArray(options.savePath)) {
      options.savePath = options.savePath.join(',');
    }
    options.resultFormat = 1;
    wasWrapper.exec('media.getPicture', 'image', 'getPicture', options, function(args) {
      var e, result;
      result = [];
      if (args.pictures) {
        try {
          result = new Function("return " + args.pictures)();
        } catch (_error) {
          e = _error;
          console.warn('failed to transform pictures to array: ' + args.pictures);
          fail(-1, 'invalid arguments from native');
          return;
        }
      } else {
        result = [args];
      }
      result = result.map(function(item) {
        var matches;
        if (options.destinationType === 'data') {
          item.imageData = "data:image/" + options.encodingType + ";base64," + item.imageData;
        }
        if (item.imagePath) {
          item.imageFileOSPath = item.imagePath;
        }
        if (item.imageTime) {
          item.imageTimeString = item.imageTime;
          matches = item.imageTime.match(/(\w{4})(\w{2})(\w{2})(\w{2})(\w{2})(\w{2})/).map(function(v) {
            return parseInt(v, 10);
          });
          item.imageTime = new Date(matches[1], matches[2] - 1, matches[3], matches[4], matches[5], matches[6]);
        }
        return item;
      });
      if (options.count === void 0 && result.length === 1) {
        done(result[0]);
      } else {
        done(result);
      }
    }, fail);
  },
  /**
   * 一次拍摄多张图片
   * 该能力客户端不支持，暂时禁用
   * kk.media.getMultiPicture(options. done, [fail])
   *
   * @param  {Object}   options 配置参数, 可选参数项如下
   *          targetWidth     : 期望的图片宽度，缺省为100
   *          targetHeight    : 期望的图片高度  (必须和targetWidth配合，同时使用)，缺省为100
   *          encodingType    : 期望得到的图片的格式  jpeg/ png，缺省为 jpeg
   *          quality         : 图片压缩率 0-100，缺省为50
   *          savePath        : 照片的保存路径数组(只支持协议格式), 必填, 也用来限制拍照张数
   *                            文件路径的格式参见《EasyMI JS API参考手册.doc》中的说明
   *          limitTime       : 拍照限制时间，单位为秒。不传认为不限制
   * @param  {Function} done    成功回调, done({imageURI: [], imageFileOSPath: []})
   *               接受一个对象作为参数
   *               imageURI属性 为实际拍照的图片路径（传入的路径格式）数组
   *               imageFileOSPath属性 为实际拍照的图片绝对路径, 数组
   * @param  {Function} fail    失败回调
  */

  getMultiPicture: function(options, done, fail) {
    var defaultOpts;
    if (!validateCallbacks(done, fail)) {
      return;
    }
    defaultOpts = {
      targetWidth: 100,
      targetHeight: 100,
      encodingType: 'jpeg',
      quality: 50,
      limitTime: -1
    };
    options = utils.extend({}, defaultOpts, options);
    options.exifFlag = false;
    if (utils.isArray(options.savePath)) {
      options.savePath = options.savePath.join(',');
    }
    options.encodingType = validateImgType(options.encodingType);
    wasWrapper.exec('media.getMultiPicture', 'image', 'getMultiCameraPics', options, function(args) {
      done({
        imageURI: args.imageURI.split(','),
        imageFileOSPath: args.imageFileOSPath.split(',')
      });
    }, fail);
  },
  /**
   * 保存图片到相册
   * kk.media.save2album(options, [done], [fail])
   *
   * @param  {Object}   options 配置选项, 包含以下参数
   *                         filepath 要保存的照片路径, 必传
   * @param  {Function} done    成功回调, done()
   * @param  {Function} fail    失败回调
  */

  save2album: function(options, done, fail) {
    var url;
    url = options.uri || options.filepath;
    if (utils.isURL(url)) {
      options.fileURI = url;
      delete options.filepath;
    } else {
      options.filepath = url;
    }
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('media.save2album', 'image', 'save2album', options, done, fail);
  },
  /**
   * 预览图片
   * kk.media.previewImage(options, [done], [fail])
   *
   * @param  {Object} options 配置选项
   *                          {
     *                          paths: 数组, 要预览的图片路径, 目前只支持协议路径
     *                          startIndex: 数字, 当前显示的图片的索引,
     *                           从1开始，非法地址则默认第一张
   *                            operations: true // 是否需要操作(发送到微信，保存图片)
   *                          }
   * @param {Function} fail 失败回调
  */

  previewImage: function(options, done, fail) {
    if (!options) {
      return;
    }
    if (utils.isArray(options.paths)) {
      options.paths = options.paths.join(',');
    }
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    options = utils.renameKeys(options, {
      operations: 'operation'
    }, true);
    options.operation = options.operation ? 1 : 0;
    wasWrapper.exec('media.previewImage', 'image', 'gallery', options, done, fail);
  },
  /**
   * 录制视频, 录制的格式取决于操作系统
   * kk.media.captureVideo([options], done, [fail])
   *
   * Android录制的格式为3gp(video/3gpp)
   * iOS录制的格式为mov(video/quicktime)
   * @param  {Object}   options 参数选项, 可选参数有
   *                            quality:质量参数，来源于getSupportedVideoQuality方法，默认为LOW
   *                            savePath:要保存的路径 (如果不指定，将使用sdcard://协议保存一个随机文件名)。
   *                            duration:录像时常(单位:秒;如果不指定，则默认长度不做限制)。
   * @param  {Function} done    成功回调, 接收一个对象最为参数, 包含以下字段:
   *                              name:不含路径信息的文件名
   *                              fullPath： 包含文件名的文件全路径
   *                              type： MIME类型
   *                              lastModifiedDate:文件最后修改的日期和时间
   *                              size：以字节数表示的文件大小
   *                              endingType:录制结束方式，0：手工结束，1：电话或其他因素打断
   * @param  {Function} fail    失败回调
  */

  captureVideo: function(options, done, fail) {
    var defaultOpts;
    if (utils.isFunction(options)) {
      fail = done;
      done = options;
      options = {};
    }
    if (!validateCallbacks(done, fail)) {
      return;
    }
    defaultOpts = {
      quality: 'LOW'
    };
    options = utils.extend({}, defaultOpts, options || {});
    wasWrapper.exec('media.captureVideo', 'media', 'captureVideo', options, done, fail);
  },
  /**
   * 带native UI的录音能力
   * kk.media.captureAudio(done, [fail])
   *
   * @param  {Function} done 成功回调, done(fileInfo)
   *                          成功回调接收一个对象作为参数, 该对象拥有的字段有:
   *                          name 文件名
   *                          fullPath 文件全路径
   *                          type 文件mime-type
   *                          size 文件大小（单位为字节）
   * @param  {Function} fail    失败回调
   *                          失败回调接收错误代码作为参数, 目前定义的错误代码有:
   *                          -3:录音被取消了
   *                          -9:录音错误
  */

  captureAudio: function(done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('media.captureAudio', 'media', 'captureAudioView', {}, done, fail);
  },
  /**
   * 待native UI的音频播放能力
   * kk.media.playAudio(filePath, [fail])
   *
   * @param  {String}   filePath  音频文件路径
   * @param  {Function} fail    失败回调
   *                           失败回调接收错误代码作为参数, 目前定义的错误代码有:
   *                           -3:播放被取消了
   *                           -9:播放错误
  */

  playAudio: function(filePath, fail) {
    var _holder, _ref;
    _ref = validateCallbacks(null, fail, false), _holder = _ref[0], fail = _ref[1];
    wasWrapper.exec('media.playAudio', 'media', 'playAudioView', {
      fileurl: filePath
    }, true, fail);
  },
  /**
   * 获取签名图片
   * kk.media.getSignImage(options, done, [fail])
   * @param  {Object}   options 配置选项
   *                            width: 输出图片宽度，必填
   *                            height: 输出图片高度，必填
   *                            pen_size: 画笔的粗细，选填
   *                                      1: 细
   *                                      2: 一般(默认选项)
   *                                      3: 粗
   *                            pen_color: 画笔的颜色，选填
   *                                      1: 黑(默认选项)
   *                                      2: 蓝
   *                                      3: 红
   * @param  {Function} done    成功回调，done({result_path: 保存签名图片的路径})
   * @param  {Function} fail    失败回调
  */

  getSignImage: function(options, done, fail) {
    var defaultOpts;
    if (!validateCallbacks(done, fail)) {
      return;
    }
    defaultOpts = {
      penSize: 2,
      penColor: 1
    };
    options = utils.extend({}, defaultOpts, options || {});
    wasWrapper.exec('media.getSignImage', 'image', 'getSignImage', options, done, fail);
  }
};

kk.media = Media;

/**
 * @module      File
 * @description 文件操作模块
 * 该模块为一个类, 使用时需要new 一个实例.
 *
 * 可以获取文件信息, 查看文件, 删除文件等
 * @author      Saiya
*/


File = (function() {
  /**
   * 构造函数
   * kk.File(filePath)
   *
   * @param  {String} filePath 文件路径(支持public,app,及相对路径)
  */

  function File(filePath) {
    this.fileName = filePath;
  }

  /**
   * 获取文件才操作系统上的路径
   * kk.File::getOSPath(done, [fail])
   *
   * @param  {Function} done 成功回调, done({osPath:'xxx.pdf'}) 接收路径作为参数
   * @param  {Function}   fail 失败回调
  */


  File.prototype.getOSPath = function(done, fail) {
    return file.getOSPath(this.fileName, done, fail);
  };

  /**
   * 获取文件路径
   * kk.File::info(done, [fail])
   *
   * @param  {Function} done 成功回调, done(fileInfo) 接收一个对象作为参数
   *                      {
   *                        isDir: boolean, 是否为文件夹
   *                        size: 大小, 单位byte
   *                        lastModifyTime: 最后修改时间
   *                      }
   * @param  {Function}   fail 失败回调
  */


  File.prototype.info = function(done, fail) {
    return file.getFileInfo(this.fileName, done, fail);
  };

  /**
   * 判断文件是否存在
   * kk.File::exists(done, [fail])
   *
   * @param  {Function} done 成功回调 done({exists: false}), 接收有exists属性的对象作为参数, 存在则exists属性为true
   * @param  {Function}   fail 失败回调
  */


  File.prototype.exists = function(done, fail) {
    return file.exists(this.fileName, done, fail);
  };

  /**
   * exist 为 exists 别名兼容旧有的不标准(错别字)写法
   * @type {Function}
  */


  File.prototype.exist = File.prototype.exists;

  /**
   * 删除文件
   * kk.File::remove([done], [fail])
   *
   * @param  {Function} done 成功回调, done()
   * @param  {Function} fail 失败回调
  */


  File.prototype.remove = function(done, fail) {
    return file.remove(this.fileName, done, fail);
  };

  /**
   * 查看文件
   * kk.File::view([options], done, [fail])
   *
   * @param  {Object}   options 参数选项, 可选参数有:
   *                            useWebview 是否使用webview打开，android Only
   *                            otherButton:可以定义其他的按钮文字（缺省带返回按钮，
   *                              如果多个用逗号分隔
   *                              mimeType:文件的类型，如果不传，从文件名分析。
   * @param  {Function} done    成功回调, done({buttonIndex: 0}), 接收按钮顺序号作为参数, 一般值为1~N,0表示按了返回
   * @param  {Function}   fail    失败回调
  */


  File.prototype.view = function(options, done, fail) {
    if (utils.type(options, 'function')) {
      fail = done;
      done = options;
      options = this.fileName;
    } else {
      options = utils.extend(true, options, {
        filepath: this.fileName
      });
    }
    return file.view(options, done, fail);
  };

  /**
   * 读取文本文件内容
   * kk.File::readAsText([encoding], done, [fail])
   *
   * @param  {String}   encoding 编码方式
   * @param  {Function} done     成功回调done({content:text}), 接收有content属性的对象作为参数，content为文本内容
   * @param  {Function}   fail   失败回调
  */


  File.prototype.readAsText = function(encoding, done, fail) {
    var options;
    if (utils.isFunction(encoding)) {
      fail = done;
      done = encoding;
      encoding = 'utf-8';
    }
    options = {
      filepath: this.fileName,
      encoding: encoding
    };
    return file.readAsText(options, done, fail);
  };

  /**
   * 将文件类容转换为base64字符串
   * kk.File::readAsBase64(done, [fail])
   *
   * @param  {Function} done     成功回调done({content:text}), 接收有content属性的对象作为参数，content为文本内容
   * @param  {Function}   fail   失败回调
  */


  File.prototype.readAsBase64 = function(done, fail) {
    return file.readAsBase64(this.fileName, done, fail);
  };

  /**
   * 拷贝文件
   * kk.File::copyTo(targetPath, [done], [fail])
   *
   * @param  {String}   targetPath 文件的目标位置
   * @param  {Function} done       成功回调, done()
   * @param  {Function} fail       失败回调
  */


  File.prototype.copyTo = function(targetPath, done, fail) {
    var options;
    options = {
      source: this.fileName,
      target: targetPath
    };
    return file.copy(options, done, fail);
  };

  return File;

})();

kk.File = File;

file = {
  /**
   * 获取文件在操作系统上的路径
   * kk.file.getOSPath(filePath, done, [fail])
   *
   * @param  {Function} done 成功回调, done({osPath:'xxx.pdf'}) 接收路径作为参数
   * @param  {Function} fail 失败回调
  */

  getOSPath: function(filePath, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('file.getOSPath', 'file', 'getFileOSPath', {
      EasyMIPath: filePath
    }, done, fail);
  },
  /**
   * 获取文件路径
   * kk.file.info(filePath, done, [fail])
   *
   * @param  {Function} done 成功回调, done(fileInfo) 接收一个对象作为参数
   *                      {
   *                        isDir: boolean, 是否为文件夹
   *                        size: 大小, 单位byte
   *                        lastModifyTime: 最后修改时间
   *                      }
   * @param  {Function} fail 失败回调
  */

  getFileInfo: function(filePath, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('file.getFileInfo', 'file', 'info', {
      filepath: filePath
    }, function(info) {
      info.isDir = (+info.isDir) === 1;
      done(info);
    }, fail);
  },
  /**
   * 判断文件是否存在
   * kk.file.exists(filePath, done, [fail])
   *
   * @param  {Function} done 成功回调 done({exists: false}), 接收有exists属性的对象作为参数, 存在则exists属性为true
   * @param  {Function} fail 失败回调
  */

  exists: function(filePath, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    return wasWrapper.exec('file.exists', 'file', 'exists', {
      filepath: filePath
    }, done, function(code, msg) {
      console.debug("[file][exist][failCB]" + code + ", " + msg);
      if (code === 1) {
        done({
          exists: false
        });
      } else {
        fail(code, msg);
      }
    });
  },
  /**
   * 删除文件
   * kk.file.remove(filePath, [done], [fail])
   *
   * @param  {Function} done 成功回调, done()
   * @param  {Function} fail 失败回调
  */

  remove: function(filePath, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    return wasWrapper.exec('file.remove', 'file', 'delete', {
      filepath: filePath,
      needConfirm: 0
    }, done, fail);
  },
  /**
   * 查看文件
   * kk.file.view(options, [done], [fail])
   *
   * @param  {String|Object}   options 必填，对象，参数有:
   *                           {
   *                             filepath: 必填，查看的文档路径
   *                             via: 使用什么方式查看: 可选值 wps, webview, auto
   *                             mode: 打开方式 readonly, edit
   *                             title: 使用webview 打开时，页面上的标题
   *                             mimeType: 文件的类型，如果不传，从文件名分析。非 wps 时有效
   *                           }
   *
   * @param  {Function} done    成功回调, done({buttonIndex: 0}), 接收按钮顺序号作为参数, 一般值为1~N,0表示按了返回
   * @param  {Function} fail    失败回调
   *
   * @example
   * //查看文件
   * kk.file.view({
   *   filepath: 'sdcard://kktest/fileTest.txt'
   *   via: 'webview',
   *   mimeType: 'text/txt'
   * }, function(res) {
   *   console.log('文件信息:' + JSON.stringify(res));
   *   if( res.buttonIndex == 1 ){
   *     console.log('自定义 按钮1 被触发');
   *   }
   * }, function(code, msg) {
   *   console.log('错误信息：' + msg + ',错误代码:' + code);
   * });
  */

  view: function(options, done, fail) {
    var WPS_OPERATIONS, defaultOpts, modes;
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    defaultOpts = {};
    if (utils.type(options, 'string')) {
      options = {
        filepath: options
      };
      if (utils.isURL(options.filepath)) {
        options.useWebview = true;
      }
    }
    if (utils.isURL(options.filepath)) {
      options.mimeType = 'text/html';
    }
    options = utils.extend(true, defaultOpts, options);
    switch (options.via) {
      case 'wps':
        modes = {
          readonly: 0,
          edit: 1
        };
        options.mode = modes[options.mode] || modes.readonly;
        delete options.via;
        delete options.title;
        delete options.mimeType;
        WPS_OPERATIONS = ['open', 'save', 'close'];
        wasWrapper.exec('file.view', 'wps', 'openFile', options, function(args) {
          var operation;
          if (args) {
            operation = parseInt(args.operation, 10);
            args.operation = WPS_OPERATIONS[operation - 1] || '';
          }
          return done && done(args);
        }, fail);
        break;
      default:
        if (options.via != null) {
          if (options.via === 'webview') {
            options.useWebview = true;
          } else {
            delete options.useWebview;
          }
        }
        wasWrapper.exec('file.view', 'file', 'viewdoc', options, done, fail);
    }
  },
  /**
   * 读取文本文件内容
   * kk.file.readAsText(options, done, [fail])
   *
   * @param  {String|object}   options  必填，对象，包含文件路径及编码方式
   *                           {
   *                             filepath: 要读取的文件路径
   *                             encoding: 选填，默认为utf-8，读取的编码方式
   *                           }
   * @param  {Function} done   成功回调done({content:text}), 接收有content属性的对象作为参数，content为文本内容
   * @param  {Function} fail   失败回调
   *
   * @example
   * kk.file.readAsText({
   *   // encoding: 'utf-8', // 选填，默认为utf-8
   *   filepath: 'sdcard://kktest/fileTest.txt'
   * }, function (res) {
   *   console.log('文本内容:' + res.content);
   * }, function (code, msg) {
   *   console.log('错误信息：' + msg + ',错误代码:' + code);
   * })
  */

  readAsText: function(options, done, fail) {
    var defaultOpts;
    if (!validateCallbacks(done, fail)) {
      return;
    }
    defaultOpts = {
      encode: 'utf-8'
    };
    if (utils.type(options, 'string')) {
      defaultOpts.filepath = options;
    } else if (utils.type(options, 'object')) {
      defaultOpts = utils.extend(true, defaultOpts, options);
    } else {
      return;
    }
    options = utils.extend(true, {}, defaultOpts);
    if (options.encoding) {
      options.encode = options.encoding;
    }
    wasWrapper.exec('file.readAsText', 'file', 'getFileContent', options, done, fail);
  },
  /**
   * 将文件类容转换为base64字符串
   * kk.file.readAsBase64(filePath, done, [fail])
   *
   * @param  {Function} done     成功回调done({content:text}), 接收有content属性的对象作为参数，content为文本内容
   * @param  {Function} fail   失败回调
  */

  readAsBase64: function(filePath, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    return wasWrapper.exec('file.readAsBase64', 'file', 'getFileContent', {
      filepath: filePath,
      filetype: 2
    }, done, fail);
  },
  /**
   * 拷贝文件
   * kk.file.copyTo(options, [done], [fail])
   *
   * @param  {Object}   options    必填，对象，包含源文件路径及目标路径信息
   *                               {
   *                                 source: 源文件路径
   *                                 target: 目标路径
   *                               }
   * @param  {Function} done       成功回调, done()
   * @param  {Function} fail       失败回调
  */

  copy: function(options, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('file.copy', 'file', 'copy', options, done, fail);
  }
};

kk.file = file;

/**
 * @module      Contact
 * @description native通讯录模块
 * 提供了操作手机操作系统自带通讯录的能力
 * @author      Saiya
*/


/**
 * 联系人信息结构
 * {
 *    id :一个全局的唯一标识
 *    name :名字
 *    namePY :名字拼音/音标
 *    cname :姓氏
 *    cnamePY :姓氏拼音
 *    company :公司
 *    phones :联系人的电话号码列表, 类型是 String, 多个值之间用逗号分隔, 其中每个位置的含义为:
 *        //[0]:  "移动电话",
 *        //[1]:  "住宅电话",
 *        //[2]:  "工作电话",
 *        //[3]:  "主要电话",
 *        //[4]:  "住宅传真",
 *        //[5]:  "工作传真",
 *        //[6]:  "传呼",
 *        //[7]:  "其他电话"
 *    emails :联系人的邮件地址列表, 类型是 String, 多个值之间用逗号分隔, 其中每个位置的含义为:
 *        //[0]:  "电子邮件",
 *        //[1]:  "工作邮件",
 *        //[2]:  "个人邮件",
 *        //[3]:  "主要邮件",
 *        //[4]:  "其他邮件" (下标4以后的都认为是其他邮件, 包括4)
 *    addresses :联系人的地址列表, 类型是 String, 多个值之间用逗号分隔, 其中每个位置的含义为:
 *        //[0]:  "主要地址",
 *        //[1]:  "其他地址" (下标1以后的都认为是其他地址 包括1)
 *    firstLetter :名字的首字母
 *    prefix :前缀
 *    middleName :中间名
 *    suffix :后缀
 *    nickname :昵称
 *    jobTitle :职务
 *    department :部门
 *    birthday :生日
 *    notes :备注
 * }
*/


Contact = {
  /**
   * 添加联系人
   * kk.contact.add(contactInfo, [done], [fail])
   *
   * @param {Object}   contactInfo 联系人信息, 无需指定id信息
   * @param {Function} done        成功回调, done()
   * @param {Function} fail        失败回调
  */

  add: function(contactInfo, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('contact.add', 'contact', 'add', contactInfo, done, fail);
  },
  /**
   * 删除联系人
   * kk.contact.remove(contactId, [done], [fail])
   *
   * @param  {String}   contactId 联系人Id
   * @param  {Function} done      成功回调, done()
   * @param  {Function} fail      失败回调
  */

  remove: function(contactId, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('contact.remove', 'contact', 'delete', {
      id: contactId
    }, done, fail);
  },
  /**
   * 更新联系人信息
   * kk.contact.update(contactInfo, [done], [fail])
   *
   * @param  {Object}   contactInfo 联系人信息, id必填
   * @param  {Function} done        成功回调, done()
   * @param  {Function} fail        失败回调
  */

  update: function(contactInfo, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('contact.update', 'contact', 'update', contactInfo, done, fail);
  },
  /**
   * 查找联系人
   * kk.contact.find(options, done, [fail])
   *
   * @param  {Object}   options 查找条件
   *                          {
   *                            name: 联系人姓名, 可选
   *                            phone: 联系人手机号
   *                          }
   * @param  {Function} done    成功回调, done({
   *                                              contacts: contacts // 接收数组为参数, 数组元素为联系人信息对象
   *                                            })
   * @param  {Function} fail    失败回调
  */

  find: function(options, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('contact.find', 'contact', 'find', options, function(response) {
      var contacts, fnStr, i, len, record;
      contacts = [];
      len = response.count;
      i = 0;
      while (i < len) {
        fnStr = 'return ' + response["record" + i];
        record = new Function(fnStr)();
        contacts.push(record);
        ++i;
      }
      done(contacts);
    }, fail);
  },
  /**
   * 从手机通讯录选人
   * kk.contact.choose([options], done, [fail])
   * @param  {Object}   options 选项
   * @param  {Function} done    成功回调
   * @param  {Function} fail    失败回调
   *   ｛
   *       id: 联系人唯一ID
   *       name: 姓名
   *       phones: 手机号码
   *       emails: 邮箱地址
   *    }
   *    注意：如果mode为1，则phones是字符串，其它情况下mobile是数组
   *    如果mode为2，则emails是字符串，其它情况下emails是数组
  */

  choose: function(options, done, fail) {
    var defOptions;
    defOptions = {
      mode: 1
    };
    if (typeof options === 'function') {
      fail = done;
      done = options;
      options = {};
    }
    options = utils.extend(defOptions, options);
    if (!validateCallbacks(done, fail)) {
      return;
    }
    return wasWrapper.exec('contact.choose', 'contact', 'choose', options, function(response) {
      var contacts;
      contacts = new Function('return ' + response.contactors)();
      done(contacts);
    }, fail);
  }
};

kk.contact = Contact;

/**
 * @module      Scaner
 * @description 扫描器相关文件
 * 提供扫描二维码条形码的功能
 * @author      Saiya
*/


Scaner = {
  /**
   * 扫描条形码
   * kk.scaner.scanBarCode(done, [fail])
   *
   * @param  {Function} done 成功回调, done({code: 'content'}) 接收条形码的内容作为参数
   * @param  {Function} fail 失败回调
  */

  scanBarCode: function(done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('scaner.scanBarCode', 'barcode', 'getBarcode', {}, done, fail);
  },
  /**
   * 扫描二维码
   * kk.scaner.scanQRCode(done, [fail])
   *
   * @param  {Function} done 成功回调, done({code: 'content'}) 接收二维码内容作为参数
   * @param  {Function} fail 失败回调
  */

  scanQRCode: function(done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('scaner.scanQRCode', 'barcode', 'getTdBarcode', {}, done, fail);
  },
  /**
   * 生成二维码
   * kk.scaner.getQRCode(options, [done], [fail])
   * 生成的二维码图片的是png格式的
   * @param  {Object}   options 配置项, 包含以下项目
   *                           {
   *                             content: String 二维码内容
   *                             targetSize: 输出的正方形图片的边长
   *                             destinationType: 文件输出形式 file, data
   *                             savePath: 输出类型为file时有效, 不指定则保存至 sdcard://easymi/...
   *                           }
   * @param  {Function}  done  成功回调, done(fileInfo), 接收一个对象作为参数
   *                           {
   *                             imageURI: 处理后的文件保存路径(当destinationType为file时)
   *                             imageFileOSPath: 处理后的文件的操作系统路径(当destinationType为file时)
   *                             imageData: 图片内容的base编码(当destinationType为data时)
   *                           }
   * @param  {Function}  fail
  */

  getQRCode: function(options, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('scaner.getQRCode', 'barcode', 'genQRCode', options, function(args) {
      if (options.destinationType === 'data') {
        args.imageData = "data:image/png;base64," + args.imageData;
      }
      done(args);
    }, fail);
  },
  /**
   * 扫描身份证
   * kk.scaner.scanIDCard([options], [done], [fail])
   * @param  {Object}   options 选项
   *                            {
   *                              hint: '请对准身份证' // 扫描界面提示信息
   *                            }
   * @param  {Function} done    成功回调
   * @param  {Function} fail    失败回调
  */

  scanIDCard: function(options, done, fail) {
    var type;
    type = typeof options;
    switch (type) {
      case 'function':
        fail = done;
        done = options;
        options = {};
        break;
      case 'string':
        options = {
          hintMsg: options
        };
        break;
      default:
        options = utils.renameKeys(options, {
          hint: 'hintMsg'
        }, true);
    }
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('scaner.scanIDCard', 'ocr', 'scanIDCard', options, function(args) {
      if (args && typeof args.isFront === 'string') {
        args.isFront = args.isFront === '1';
      }
      done(args);
    }, fail);
  },
  /**
   * 扫描车辆行驶证
   * kk.scaner.scanVLCard([options], [done], [fail])
   * @param  {Object}   options 选项
   *                            {
   *                              hint: '请对行驶证' // 扫描界面提示信息
   *                            }
   * @param  {Function} done    成功回调
   * @param  {Function} fail    失败回调
  */

  scanVLCard: function(options, done, fail) {
    var type;
    type = typeof options;
    switch (type) {
      case 'function':
        fail = done;
        done = options;
        options = {};
        break;
      case 'string':
        options = {
          hintMsg: options
        };
        break;
      default:
        options = utils.renameKeys(options, {
          hint: 'hintMsg'
        }, true);
    }
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('scaner.scanVLCard', 'ocr', 'scanVLCard', options, done, fail);
  }
};

Scaner.scanTDCode = Scaner.scanQRCode;

kk.scaner = Scaner;

/**
 * @module      Kkcontact
 * @description 门户通讯录模块
 * 提供了访问门户(客户端)自身通讯录的能力
 * @author      Saiya
*/


Econtact = {
  /**
   * 联系人选择API
   * kk.kkcontact.choose([options], done, [fail])
   *
   * @param  {Object} options      配置项
   *                   结构为 {
   *                      initList: [], // 初始选中的联系人的ID(userID),可以为数组, 也可以为逗号连接的字符串
   *                                    // 默认为空数组
   *                      isSticky: false, // 被选中的联系人是否被取消选中(可否编辑),
   *                                       // true为不可取消. 默认为false, 即可以被编辑
   *                      stickyList: [] // 初始化联系人中不可被移除的联系人的ID(userID)
   *                                    //isSticky 为true时该参数无效
   *                      maxCount: 0 // 最多可选择的人数, 0 表示不限制人数
   *                      isSelfSticky: false // 是否默认带上自己并且不可移除
   *                  }
   * @param  {Function} done 成功回调, done(obj)
   *                    成功回调接收的参数结构为
   *                    ［{
   *                                userID: '9092', // 联系人ID
   *                                name: 'ABC89张明',  // 联系人姓名
   *                                loginName: 'zhangmin',   // 登录名
   *                                depart: 'UE工程师',   // 联系人所在部门(直属部门)
   *                                departPath: ['蓝凌公司', 'KK BU'],
   *                                        // 部门路径，从顶级部门到所在部门
   *                                mobile: '13900023',      // 手机号
   *                                email: 'q@qq.com'  // 邮箱地址
   *                                jobTitle: '软件工程师' // 职位
   *                                signature: '天若有情天亦老' // 个性签名
   *                    }］
   * @param  {Function} fail    失败回调， 错误代码 －1 用户取消操作
  */

  choose: function(options, done, fail) {
    var defaultOpts;
    if (utils.isFunction(options)) {
      fail = done;
      done = options;
      options = {};
    }
    if (!validateCallbacks(done, fail)) {
      return;
    }
    defaultOpts = {
      initList: [],
      isSticky: false,
      stickyList: [],
      maxCount: 0,
      isSelfSticky: false
    };
    options = utils.extend(defaultOpts, options || {});
    if (utils.isArray(options.initList)) {
      options.initList = options.initList.join(',');
    }
    options.initList = String(options.initList);
    if (utils.isArray(options.stickyList)) {
      options.stickyList = options.stickyList.join(',');
    }
    options.stickyList = String(options.stickyList);
    wasWrapper.exec('econtact.choose', 'contactor', 'chooseContactors', options, function(res) {
      done(normalizeEContactList(res));
    }, fail);
  },
  /**
   * 使用用户ID来获取用户信息
   * 调用 getUserInfo 实现，兼容旧的写法
   * @param  {String|Array}   id   用户ID，或者ID的数组
   * @param  {Function} done 成功回调, 接受包含所查询人员信息的数组作为参数，如果所查ID不存在， 则无其对应详情
   * @param  {Function}   fail 失败回调， 错误代码 -9: 参数错误
  */

  getUserInfoById: function() {
    var args;
    args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    if (args[0]) {
      args[0] = {
        userList: args[0]
      };
    }
    return Econtact.getUserInfo.apply(Econtact, args);
  },
  /**
   * 获取用户信息
   * kk.econtact.getUserInfo([opts], done, [fail])
   * 
   *   如果不传参数，则返回自身的简单信息
   *   如果第一个参数为回调函数，则对自身详细信息进行回调处理
   *   如果第一个参数为对象，在对象内userList中传用户ID，不填代表自身，回调处理返回的用户信息
   *   如果第一个参数为数组或字符串，则查询对应用户信息，并回调处理
   * @param  {Function|Object|String|Array}   opts 用户ID，或者ID的数组，
   *                                               或者封装起来的关于用户的对象:
   *                                               {
   *                                                 type: 'userID' || 'user'
   *                                                 userList: String|Array
   *                                               }
   *                                               或者是关于自己信息的成功回调
   * @param  {Function} done 成功回调，接受包含所查询人员信息的数组作为参数，如果所查ID不存在， 则无其对应详情
   * @param  {Function} fail 失败回调， 错误代码 -9: 参数错误
   * @return {Object|undefined}        在无参数的情况下返回自身简单信息，其他情况无返回值
  */

  getUserInfo: function(opts, done, fail) {
    var defaultOpts;
    defaultOpts = {
      type: 'userID',
      userList: Was.userInfo.userID
    };
    if (arguments.length === 0) {
      if (!Was.userInfo.loginName) {
        Was.userInfo.loginName = Was.userInfo.userName;
      }
      return utils.extend({}, Was.userInfo);
    }
    if (utils.type(opts, 'function')) {
      fail = null;
      if (utils.type(done, 'function')) {
        fail = done;
      }
      done = opts;
      opts = {};
    } else if (utils.type(opts, 'object')) {
      if (!validateCallbacks(done, fail)) {
        return;
      }
    } else {
      if (!validateCallbacks(done, fail)) {
        return;
      }
      opts = {
        userList: opts
      };
    }
    opts = utils.extend(true, defaultOpts, opts);
    if (!utils.isArray(opts.userList)) {
      opts.userList = [opts.userList];
    }
    opts.ids = opts.userList.join(',');
    wasWrapper.exec('econtact.getUserInfo', 'contactor', 'getContactorDetail', opts, function(res) {
      done(normalizeEContactList(res));
    }, fail);
  },
  /**
   * 使用用户信息获取用户头像，如不传参数则获取自身头像，头像尺寸为: 120px*120px
   * kk.econtact.getUserAvatar(loginName, [isById])
   * @param  {String}  loginName 登录名，当isById为true时该项为用户ID
   * @param  {Boolean} isById    第一个参数是否是用户ID
   * @return {String}            头像url
  */

  getUserAvatar: function(loginName, isById) {
    var key;
    if (utils.type(loginName, 'boolean')) {
      loginName = false;
    }
    isById = loginName && isById;
    loginName = loginName || Was.userInfo.loginName;
    if (isById) {
      key = 'userId=';
    } else {
      key = 'user=';
    }
    return KK_WEBSERVER_HTTP_URL + '/serverj/readUserIcon?' + key + encodeURIComponent(loginName);
  },
  /**
   * 与其它用户发起会话
   * kk.econtact.startChat(options, [done], [fail])
   * @param  {Object}   options 选项
   *                            {
   *                              userID: '', // 需要聊天人的 ID
   *                              loginName: '', // 需要聊天人的登录名。优先使用 recieverID
   *                              words: '' // 附带的聊天文字，若传则显示在聊天输入框中
   *                            }
   * @param  {Function} done    成功回调
   * @param  {Function}   fail   失败回调
  */

  startChat: function(options, done, fail) {
    options = utils.renameKeys(options, {
      userID: 'recieverID',
      loginName: 'recieverName'
    });
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('econtact.startChat', 'im', 'sendP2P', options, done, fail);
  },
  /**
   * 显示联系人资料页面
   * @param  {Object}   options 选项信息
   *                            {
   *                              uesrID: '用户ID',
   *                              loginName: '用户登录名'
   *                            }
   * @param  {Function} done    成功回调
   * @param  {Function}   fail    失败回调
   *                               -2:找不到对应的员工
   *                               -3:网络不可用
   *                               * 。。。参数不可用，请参见公用错误码
  */

  showECard: function(options, done, fail) {
    options = utils.renameKeys(options, {
      userID: 'personID',
      loginName: 'personName'
    });
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('econtact.showECard', 'contactor', 'showPersonDetailView', options, done, fail);
  }
};

normalizeEContactList = function(res) {
  if (res && res.contactors) {
    res = new Function("return " + res.contactors)();
    res = res.map(function(item) {
      item.deptPath = item.deptPath ? item.deptPath.split(String.fromCharCode(3)) : [];
      item.jobTitle = item.job;
      item.signature = item.sign;
      delete item.sign;
      delete item.job;
      return item;
    });
  } else {
    res = [];
  }
  return res;
};

kk.econtact = Econtact;

/**
 * @module      Crypto
 * @description 加密模块
 * 提供文件,字符串的加密和解密功能
 * @author      Saiya
*/


Crypto = {
  /**
   * 加密字符串
   * kk.crypto.encrypt(options, done, [fail])
   *
   * @param  {Object}   options 配置项
   *                            {
   *                              text: 要加密的文字
   *                              key: 密钥
   *                            }
   * @param  {Function} done 成功回调, done({text: secretStr}) 接收加密后的字符串作为参数
   * @param  {Function} fail 失败回调
  */

  encrypt: function(options, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('crypto.encrypt', 'crypto', 'AESEncode', options, done, fail);
  },
  /**
   * 解密字符串
   * kk.crypto.decrypt(options, done, [fail])
   *
   * @param  {Object}   options 配置项
   *                            {
   *                              text: 被加密的字符串
   *                              key: 密钥
   *                            }
   * @param  {Function} done   成功回调, done({text: text}) 接收解密后的字符串作为参数
   * @param  {Function} fail   失败回调
  */

  decrypt: function(options, done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('crypto.decrypt', 'crypto', 'AESDecode', options, done, fail);
  },
  /**
   * 混淆文件(加密文件)
   * kk.crypto.obscureFile(options, [done], [fail])
   *
   * @param  {Object}   options 配置选项
   *                         {
   *                           obscure: 混淆因子 String
   *                           sourceFile: 源文件路径 String
   *                           dealSourceFile: 直接处理源文件, true时，忽略outputFile属性
   *                           outputFile: 文件输出路径 String
   *                         }
   * @param  {Function} done    成功回调, done()
   * @param  {Function} fail    失败回调
  */

  obscureFile: function(options, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('crypto.obscureFile', 'crypto', 'obscureFile', options, done, fail);
  },
  /**
   * 恢复文件, 与obscureFile相对, 参数说明同上
   * kk.crypto.restoreFile(options, [done], [fail])
  */

  restoreFile: function(options, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('crypto.restoreFile', 'crypto', 'restoreFile', options, done, fail);
  }
};

kk.crypto = Crypto;

/**
 * @module      Location
 * @description 定位模块
 * 封装了地理定位的能力
 * @author      Saiya
*/


Location = {
  /**
   * 获取当前位置 (使用Baidu SDK)
   * kk.location.getLocation(done, [fail])
   *
   * @param  {Function} done 成功回调, done(geoInfo) 接收一个对象作为参数
   *                       {
   *                         longitude: 经度
   *                         latitude: 维度
   *                         altitude: 海拔
   *                       }
   * @param  {Function} fail 失败回调
  */

  getLocation: function(done, fail) {
    if (!validateCallbacks(done, fail)) {
      return;
    }
    wasWrapper.exec('location.getLocation', 'location', 'locate', {
      type: 'Cell'
    }, done, fail);
  }
};

kk.location = Location;

/**
 * @module      Share
 * @description share模块
 * 提供了分享消息的相关功能
 * 如分享至微信，分享给KK同事等等
 * @author      Saiya
*/


Share = {
  /**
   * 分享至客户端
   * kk.share.to(dest, [done], [fail])
   * kk.share.to(dest, url, [done], [fail])
   * kk.share.to(dest, options, [done], [fail])
   * 
   * 
   * @param  {String}   dest    目标应用，可选值有
   *                            [
   *                              'kk'(分享至KK),
   *                              'weixin'(分享至微信好友)
   *                            ]
   * @param  {Object}   options 配置参数，可配置参数如下：
   *                       url: 分享的url地址。可选。如果不填则默认分享当前页面;
   *                     title: 分享标题。可选。如果不填则分享链接里面获取;
   *                   summary: 分享摘要。可选。如果不填则分享链接里面获取; 亦支持使用 键名 content 设置摘要
   *                  imageUri: 分享缩略图。可选。
   *                            支持http(s)协议，以及KK协议路径。
   *                            如果不填则分享链接里面获取;
   *                            微信缩略图大小限制为32KB
   *                  以下参数只在dest为'kk'时有效:
   *                      type: 消息类型，可选值有['text', 'card']，默认值为'card';
   *            showChooseView: 是否打开选人界面，默认值为true;
   *              recieverType: 接收方类型，可选值有['user', 'group'];
   *              recieverList: 消息接收方列表，多个用逗号分隔，内容为接收方类型的id列表
   *
   * @param  {Function} done    成功回调
   * @param  {Function} fail    失败回调
  */

  to: function(dest, options, done, fail) {
    var defaultOptions, revType, sendType;
    revType = {
      user: 1,
      group: 2
    };
    sendType = {
      text: 1,
      card: 10
    };
    defaultOptions = {
      type: 'card',
      showChooseView: true
    };
    if (!options || utils.type(options, 'function')) {
      fail = done;
      done = options;
      options = {
        url: kk.app.isOfflineApp() ? Share.getAppShareLink() : location.href
      };
    } else if (utils.type(options, 'string')) {
      if (utils.isURL(options)) {
        options = {
          url: options
        };
      } else if (dest === 'kk') {
        options = {
          content: options,
          type: 'text'
        };
      }
    }
    if (!utils.type(options, 'object')) {
      fail(-6, '参数错误');
      return;
    }
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    options = utils.extend(true, {}, defaultOptions, options);
    options.type = sendType[options.type] || 1;
    if (options.content && !options.summary) {
      options.summary = options.content;
    }
    switch (dest) {
      case 'weixin':
        options.shareTo = 'weixin_msg';
        wasWrapper.exec('share.to', 'share', 'shareUrl', options, done, function(code, msg) {
          switch (code) {
            case 3:
              return fail(-1, msg);
            default:
              return fail(code, msg);
          }
        });
        break;
      case 'kk':
        if (options.recieverType) {
          options.recieverType = revType[options.recieverType];
        }
        if (options.recieverList) {
          options.recieverList = ([].concat(options.recieverList)).join(',');
        }
        if (options.type === 10) {
          if (options.imageUri) {
            options.picUrl = options.imageUri;
          }
          if (options.url) {
            options.linkUrl = options.url;
          }
        }
        wasWrapper.exec('share.to', 'im', 'send', options, done, fail);
        return;
      default:
        fail(-6, '参数错误');
    }
  },
  /**
   * 发送卡片消息至KK
   * kk.share.appMessage(opts, [done], [fail])
   *
   * @param  {Object}   opts 相关消息信息
   *                         {
   *                           data: 选填，附带消息，JSON对象
   *                           targetPlatform: 选填，该消息适用的目标平台，可选值如下，默认'mobile'
   *                                           ['ios', 'android', 'mobile', 'desktop', 'universal']
   *                           targetType: 选填，目标平台类型，可选值如下，默认'universal'
   *                                       ['pad', 'phone', 'universal']
   *                           type: 必填，消息内容类型，可选值如下，默认'text'
   *                                     ['text', 'card']
   *                           title: 消息类型为card下必填，为卡片消息标题，text下无作用
   *                           content: 必填，消息类型为text时表示消息内容，为card时表示摘要信息
   *                           recieverType: 选填，消息接收方类型，可选值如下
   *                                     ['user', 'group']
   *                           recieverList: 选填，消息接收方列表，多个用逗号分隔，内容为接收方类型的id列表
   *                           showChooseView: 选填，是否打开选择接收方界面，默认打开
   *                         }
   * @param  {Function} done 成功回调
   * @param  {Function} fail 失败回调
  */

  appMessage: function(opts, done, fail) {
    var options, shareOpts;
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    options = utils.extend(true, {}, opts, {
      type: 'card'
    });
    shareOpts = utils.extend(true, {}, options);
    delete shareOpts.type;
    delete shareOpts.title;
    delete shareOpts.content;
    delete shareOpts.recieverType;
    delete shareOpts.recieverList;
    delete shareOpts.showChooseView;
    options.linkUrl = options.linkUrl || Share.getAppShareLink(shareOpts);
    delete options.data;
    return Share.to('kk', options, done, fail);
  },
  /**
   * 获取分享链接
   * kk.share.getAppShareLink(opts)
   *
   * @example
   * var link = kk.share.getAppShareLink({
   *   targetType: 'phone',
   *   targetPlatform: 'ios',
   *   data: {
   *     name: 'Sean',
   *     age: 24,
   *     job: 'front-end'
   *   }
   * });
   * console.log(link.split('?')[1]);
  */

  getAppShareLink: function(opts) {
    var link, options, queryData;
    options = utils.extend(true, {}, opts);
    if (options.data) {
      options.data = JSON.stringify(options.data);
    }
    queryData = utils.extend(true, {
      targetType: 'universal',
      targetPlatform: 'mobile'
    }, options, {
      appCode: Was.appInfo.code,
      appName: Was.appInfo.name
    });
    link = KK_WEBSERVER_HTTP_URL + '/serverj/open-kk-app/';
    link += '?' + utils.toQueryString(queryData);
    return link;
  }
};

kk.share = Share;

/**
 * @module      Pay
 * @description 支付模块
 * @author      Yjp
*/


Pay = {
  /**
   * 微信支付
   * kk.pay.weixin(options, done, [fail])
   * 目前仅限iOS
   * @param  {object} options 配置参数如下：
   *                 appid ： 微信开放平台审核通过的应用APPID，必填。
   *                 partnerid： 微信支付分配的商户号必填。
   *                 prepayid： 微信返回的支付交易会话ID。必填。
   *                 package： 暂填写固定值Sign=WXPay。必填。
   *                 noncestr： 随机字符串，不长于32位。必填。
   *                 timestamp： 时间戳，标准北京时间，自1970年1月1日 0点0分0秒以来的秒数。必填。
   *                 sign： 签名，详见微信支付官网上的签名算法。必填。
   *                 
   * @param {Function} done 成功回调
   * @param {Function} fail 失败回调
  */

  weixin: function(options, done, fail) {
    var defaultOptions;
    if (!validateCallbacks(done, fail)) {
      return;
    }
    defaultOptions = {};
    options = utils.extend(defaultOptions, options || {});
    wasWrapper.exec('pay.weixin', 'pay', 'weixinAppPay', options, done, fail);
  }
};

kk.pay = Pay;

/**
 * @module      Phone
 * @description 电话短信模块
 * @author      Saiya
*/


Phone = {
  /**
   * 拨打电话
   * kk.phone.call(phoneNumber, [needConfirm])
   *
   * @param  {Number} phoneNumber      电话号码
   * @param  {Boolean} needConfirm 是否需要确认, 默认true
   *                                 如果为true, 则进入系统的拨号界面, 再由用户发起拨号
   *                                 若为false, 则直接拨号
   *                                 注: iOS只支持true
  */

  call: function(phoneNumber, needConfirm) {
    if (needConfirm == null) {
      needConfirm = true;
    }
    wasWrapper.exec('phone.call', 'phone', 'call', {
      number: phoneNumber,
      confirm: needConfirm
    });
  },
  /**
   * 发送短信
   * kk.phone.sms(phoneNumber, [content])
   *
   * @param  {Number} phoneNumber     电话号码
   * @param  {String} content='' 短信内容, 可选, 默认为空
  */

  sms: function(phoneNumber, content) {
    var options;
    if (content == null) {
      content = '';
    }
    options = {
      number: phoneNumber,
      content: content
    };
    wasWrapper.exec('phone.sms', 'sms', 'send', options);
  }
};

kk.phone = Phone;

/**
 * @module      Zip
 * @description 文件压缩解压缩模块
 * @author      Saiya
*/


Zip = {
  /**
   * 压缩文件
   * kk.zip.zip(options, [done], [fail])
   *
   * @param  {Object}   options 配置选项, 包含以下参数
   *                            {
   *                              folderPath: 要压缩的文件夹路径
   *                              filePaths: 数组, 要压缩的文件路径,
   *                                         folderPath和filePaths二选一
   *                                         优先使用folderPath
   *                              zipFilePath: 压缩生成的zip文件路径。
   *                            }
   * @param  {Function} done    成功回调, done()
   *                            接受一个对象作为参数：
   *                            {
   *                              "zipFilePath":"sdcard://test.zip",     // 压缩后的zip文件路径
   *                              "FolderPath":"sdcard://baiduplist/"    // 为需要压缩的文件夹,  (Android无此属性)
   *                            }
   * @param  {Function} fail    失败回调
  */

  zip: function(options, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    if (options.folderPath) {
      options.FolderPath = options.folderPath;
    }
    if (options.filePaths) {
      if (utils.isArray(options.filePaths)) {
        options.filePaths = options.filePaths.join(',');
      }
      options.toZipFiles = options.filePaths;
    }
    wasWrapper.exec('zip.zip', 'zip', 'zip', options, done, fail);
  },
  /**
   * 解压文件
   * kk.zip.unzip(options, [done], [fail])
   *
   * @param  {Object}   options 参数选项, 包含以下参数:
   *                            zipFilePath 压缩文件路径
   *                            outPath     压缩文件解压后存放的路径
   * @param  {Function} done        成功回调, done()
   *                                返回一个对象
   *                                {
   *                                  zipFilePath: 'Documents/test.zip',           // 需要解压文件的路径  (Android无此属性)
   *                                  outPath: 'sdcard://testzip'                  // 解压后的输出目录
   *                                }
   * @param  {Function} fail        失败回调
  */

  unzip: function(options, done, fail) {
    if (!validateCallbacks(done, fail, false)) {
      return;
    }
    wasWrapper.exec('zip.unzip', 'zip', 'unzip', {
      zipFilePath: options.zipFilePath,
      outPath: options.outPath
    }, done, fail);
  }
};

kk.zip = Zip;

/**
 * @module      History
 * @description 历史记录模块
 * 提供操作webview历史记录的能力(前进, 后退等)
 * @author      Saiya
*/


History = {
  /**
   * 获取当前页面可以前进及后退的页数
   * kk.history.canGo(done)
   *
   * @param  {Function}   done  成功回调, done(obj)
   *              成功回调接收一个对象作为参数， 该对象拥有如下字段
   *              canGoBack: 是否能够后退
   *              canGoForward: 是否能能够前进
  */

  canGo: function(done) {
    if (!validateCallbacks(done)) {
      return;
    }
    wasWrapper.exec('history.canGo', 'app', 'canGo', {}, done, defaultFailCB);
  },
  /**
   * 判断当前页面是否有上一页
   * kk.history.hasPrev(done)
   *
   * @param  {Function} done 成功回调, done({hasPrev: false})
   *              成功回调接收一个对象作为参数，有上一页对象的hasPrev则为true, 否则为false
  */

  hasPrev: function(done) {
    if (!validateCallbacks(done)) {
      return;
    }
    History.canGo(function(res) {
      done({
        hasPrev: res.canGoBack
      });
    });
  },
  /**
   * 返回到上一页
   * kk.history.back([fail])
   *
   * @param  {Function} fail 失败回调
   *              如果没有上一页，则会调用该函数， 该函数不接受参数
  */

  back: function(fail) {
    if (!utils.isFunction(fail)) {
      fail = defaultFailCB;
    }
    wasWrapper.exec('history.back', 'app', 'goBack', {}, null, fail);
  },
  /**
   * 前进到下一页
   * kk.history.forward([fail])
   *
   * @param  {Function} fail 失败回调
   *              如果没有下一页，则会调用该函数， 该函数不接受参数
  */

  forward: function(fail) {
    if (!utils.isFunction(fail)) {
      fail = defaultFailCB;
    }
    wasWrapper.exec('history.forward', 'app', 'goForward', {}, null, fail);
  }
};

kk.history = History;

/**
 * @module      Wx
 * @description 兼容微信的能力模块
 * 目前仅兼容微信和kk共有的能力, 能力调用方式和微信一致
 *
 * 如果项目需要在微信和kk两个平台中运行, 则可以在加载kk sdk后
 *   声明变量 var wx = kk.wx;
 *   能力调用地方不用变化.
 * @author      Saiya
*/


Wx = (function() {
  /**
   * 兼容微信的错误事件包裹
   * @param  {Function} fail 错误处理函数
   * @return {Function}      包裹后的错误处理函数
  */

  var failWrap, onAudioRecordEnd, wx;
  failWrap = function(fail) {
    return function(code, msg) {
      if (utils.isFunction(fail)) {
        fail({
          errMsg: msg,
          code: code
        });
      } else {
        defaultFailCB(code, msg);
      }
    };
  };
  onAudioRecordEnd = null;
  wx = {
    /**
     * wx的ready, 兼容微信, 比微信多了回调参数
     * @param  {Function} fn
    */

    ready: function(fn) {
      kk.ready(fn);
    },
    /**
     * 接口验证失败处理
     * 无任何操作, 仅为兼容微信
    */

    error: function() {},
    /**
     * 检查是否支持该能力, 完全兼容
     * @param  {Object} options 选项
     *                          {
     *                            jsApiList: ['chooseImage']
     *                            success: 成功回调, success(res)
     *                              res: {"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
     *                          }
    */

    checkJsApi: function(options) {
      var apiList, k, res, _i, _len;
      apiList = options.jsApiList;
      res = {};
      for (_i = 0, _len = apiList.length; _i < _len; _i++) {
        k = apiList[_i];
        res[k] = !!wx[k];
      }
      options.success({
        checkResult: res,
        errMsg: 'checkJsApi:ok'
      });
    },
    /**
     * 浏览图片, 微信图片浏览器
     * @param  {Object} options
     *           {
     *               current: '', // 当前显示的图片链接
     *               urls: [] // 需要预览的图片链接列表
     *           }
    */

    previewImage: function(options) {
      if (options.current) {
        options.current = options.urls.indexOf(current);
      }
      options.paths = options.urls;
      options.current = options.current || 0;
      kk.media.previewImage(options);
    },
    /**
     微信上传图片
     wx.uploadImage({
         localId: '', // 需要上传的图片的本地ID，由chooseImage接口获得
         isShowProgressTips: 1, // 默认为1，显示进度提示
         success: function (res) {
             var serverId = res.serverId; // 返回图片的服务器端ID
         }
     });
    */

    /**
     微信下载图片
     wx.downloadImage({
         serverId: '', // 需要下载的图片的服务器端ID，由uploadImage接口获得
         isShowProgressTips: 1, // 默认为1，显示进度提示
         success: function (res) {
             var localId = res.localId; // 返回图片下载后的本地ID
         }
     });
    */

    /**
     * 开始录音, 完全兼容
    */

    startRecord: function() {
      kk.media.captureAudio(function(res) {
        if (typeof onAudioRecordEnd === "function") {
          onAudioRecordEnd(res);
        }
        onAudioRecordEnd = null;
      });
    },
    /**
     * 停止录音, 兼容, 但是参数不一致
     * @param  {Object} options 选项
     *                          {
     *                            success: fn..
     *                          }
    */

    stopRecord: function(options) {
      onAudioRecordEnd = options.success;
    },
    /**
     * 录音自动结束事件
     *
     * 微信有录音1分钟的限制, kk没有, 提供此函数仅为兼容
    */

    onVoiceRecordEnd: function() {},
    /**
     * 播放语音
     * @param  {Object} options 参数选项
     *                          {
     *                            localId: '' // 此处得传kk约定的地址
     *                          }
    */

    playVoice: function(options) {
      kk.media.playAudio(options.localId);
    },
    /**
     * 获取当前网络类型, 完全兼容微信
     * @param  {Object} options 选项
     *                          {
     *                            success: fn, fn(res)
     *                          }
    */

    getNetworkType: function(options) {
      kk.device.getNetType(function(res) {
        options.success({
          networkType: res.netType.toLowerCase()
        });
      });
    },
    /**
     * 获取当前位置, 兼容微信, 单缺少部分数据
     * var speed = res.speed; // 速度，以米/每秒计
     * var accuracy = res.accuracy; // 位置精度
     * @param  {Object} options 选项
     *                          {
     *                            success: fn
     *                          }
    */

    getLocation: function(options) {
      kk.location.getLocation(options.success);
    },
    /**
     * 关闭窗口(退出应用), 完全兼容微信
    */

    closeWindow: function() {
      kk.app.exit();
    },
    /**
     * 扫描二维码, 部分兼容
     * 只支持 needResult 为1(即需要应用自己处理结果)
     *   scanType为qrCode或barCode之一, 不能有俩, 默认二维码
     * @param  {Object} options 选项
     *                          {
     *                            needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
     *                            scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
     *                            success: function (res) { res.resultStr}
     *                          }
    */

    scanQRCode: function(options) {
      var type;
      type = options.scanType;
      type = type ? type[0] : 'qrCode';
      if (type === 'barCode') {
        type = 'scanBarCode';
      } else {
        type = 'scanTDCode';
      }
      kk.scaner[type](function(res) {
        if (typeof options.success === "function") {
          options.success({
            resultStr: res.code
          });
        }
      });
    }
  };
  wx.kk = true;
  return wx;
})();

kk.wx = Wx;

return kk;

}));
