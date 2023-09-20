/*!
 * KK-JS-SDK v1.1.29
 * Copyright© 2016 深圳市蓝凌软件股份有限公司版权所有
 */

!function(a,b){"function"==typeof define&&(define.amd||define.cmd)?define(function(){return b(a)}):"object"==typeof exports?module.exports=b(a):a.Easymi=a.kk=b(a)}("undefined"!=typeof window?window:this,function(a){"use strict";var b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A=[].slice,B={}.hasOwnProperty;return z=a.kk,t=function(){var b,c;return c=[],b=function(b){a.Was&&Was.ready?b(Was.readyArgs):(c.push(b),"undefined"!=typeof a&&null!==a&&a.addEventListener("kkJsBridgeReady",function(){var a,b;for(b=Was.readyArgs;a=c.shift();)a(b)}))},{ready:b,version:"1.1.29"}}(),t.noConflict=function(){return a.kk=z,t},t.isKK=function(){return/kk5/i.test(navigator.userAgent)},y=function(a,b){return function(){var d,e;d=1<=arguments.length?A.call(arguments,0):[],e="["+a+"][success]",c.detailLog&&(e+="[arguments]: "+JSON.stringify(d)),r.debug(e),"function"==typeof b&&b.apply(null,d)}},x={exec:function(){var b,c,d;return c=1<=arguments.length?A.call(arguments,0):[],b=c.shift(),"undefined"==typeof Was?(a.console.log("Was is not defined, can not call native api"),void("function"==typeof c[4]&&c[4](-999,"Native is not ready yet, API should be called in kk.ready"))):(r.debug("["+b+"][call-arguments] : "+JSON.stringify(c[2])),d=c[3],c.length>=4&&null!=d&&(c[3]=y(b,d)),void Was.exec.apply(Was,c))}},o={noop:function(){},type:function(a,b){var c;return null!=a?(c=Object.prototype.toString.call(a),1===arguments.length?c.slice(8,-1):c.toLowerCase()===("[object "+b+"]").toLowerCase()):(c=String(a),arguments.length>1?c.toLowerCase()===String(b).toLowerCase():c)},isArray:Array.isArray||function(a){return u.type(a,"Array")},isWindow:function(a){return!(null==a||!a.window||a!==a.window)},isObject:function(a){return u.type(a,"Object")},isPlainObject:function(a){return u.isObject(a)&&!u.isWindow(a)&&Object.getPrototypeOf(a)===Object.prototype},isEmpty:function(a){return null===a||void 0===a||""===a||u.isArray(a)&&!a.length},isFunction:function(a){return u.type(a,"Function")},extend:function(a,b){var c,d,e,f,g,h,i;for(c=[].slice.call(arguments),"boolean"==typeof c[0]?(e=c.shift(),a=c[0]):e=!1,h="boolean"!=typeof c[c.length-1]||c.pop(),f=1,1===c.length&&(a=u.isWindow(this)?{}:this,f=0),a||(a={});f<c.length;)if(b=c[f++])for(g in b)B.call(b,g)&&(i=b[g],!h&&g in a||(e&&i&&(u.isArray(i)||u.isPlainObject(i))?(d=u.isArray(i)?a[g]||[]:a[g]||{},a[g]=u.extend(e,d,i,h)):void 0!==i&&(a[g]=i)));return a},toQueryObjects:function(a,b,c){var d,e,f,g,h,i,j,k;if(d=u.toQueryObjects,f=[],u.isArray(b))if(c)for(e=h=0,j=b.length;h<j;e=++h)g=b[e],f=f.concat(d(""+a+"["+e+"]",g,!0));else for(e=i=0,k=b.length;i<k;e=++i)g=b[e],f.push({name:a,value:g});else if(u.isObject(b))if(c)for(e in b)B.call(b,e)&&(g=b[e],f=f.concat(d(""+a+"["+e+"]",g,!0)));else for(e in b)B.call(b,e)&&(g=b[e],f.push({name:a,value:g}));else f.push({name:a,value:b});return f},toQueryString:function(a){var b,c,d,e,f,g,h;e=[],d=[];for(b in a)B.call(a,b)&&(f=a[b],d=d.concat(u.toQueryObjects(b,f,!0)));for(g=0,h=d.length;g<h;g++)c=d[g],f=c.value,u.isEmpty(f)?f="":u.type(f,"Date")&&(f=u.formatTime(f)),e.push(""+encodeURIComponent(c.name)+"="+encodeURIComponent(String(f)));return e.join("&")}},u=o,s=function(a,b){r.error("[kk api defaultFailCB]code:"+a+", msg:"+b)},v=function(a,b,c){return null==c&&(c=!0),"boolean"==typeof b&&(c=b),u.isFunction(b)||(b=s),u.isFunction(a)||!c&&(c||null==a)?a?[a,b]:[u.noop,b]:(b(-1,"success callback must be a function"),[])},w=function(a){return a=(""+a).toLowerCase(),"jpg"===a&&(a="jpeg"),"jpeg"!==a&&"png"!==a&&(a="png"),a},t.utils=o,c={debug:!0,detailLog:!1},t.config=function(){var a,b,d,e,f;if(a=1<=arguments.length?A.call(arguments,0):[],!a.length)return u.extend({},c);if(d=a[0],e=typeof d,1!==a.length)return"string"===e&&d in c?c[d]=a[1]:void 0;if("string"===e)return c[d];if("object"===e){for(b in c)B.call(c,b)&&(f=c[b],b in d&&(c[b]=d[b]));return u.extend({},c)}},r=function(){var b,d;return b=["log","debug","error","warn"],d={},a.console?b.forEach(function(b){d[b]=function(){c.debug&&a.console[b].apply(a.console,arguments)}}):b.forEach(function(a){d[a]=function(){}}),d}(),m=function(){var b,c,d,e;return d={},b={},e=function(c){var d,e,f,g,h,i;if(g={},h=c[0],f=c[1]||{},"string"==typeof h)f.url=h;else{if(!u.isObject(h))return!1;f=h}return null==f.contentType&&(f.contentType=b.contentType||"form"),f=u.extend(!0,{},b,f),g.dataType=f.dataType,g.__url=f.url,f.data||(f.data={_easymi_random_:(new Date).getTime()}),e=f.contentType&&"form"!==f.contentType?"json"===f.contentType?"application/json":f.contentType:"application/x-www-form-urlencoded",d=f.data,"string"!=typeof d&&(d=e.indexOf("x-www-form-urlencoded")>-1?u.toQueryString(d):e.indexOf("application/json")>-1?JSON.stringify(d):String(d)),e.indexOf("charset")===-1&&(e=e.replace(/;?\s*$/,"; charset=UTF-8")),i=a.navigator.userAgent,window.Was&&window.Was.isEmulator&&(i+=" KKEmulator"),g["User-Agent"]=i,g["Content-Type"]=e,g.__content=d,null==f.headers&&(f.headers={}),u.extend(g,f.headers,!1),g.failure=f.error,g.success=f.success,g},d.requestSetup=function(a){return b=a||{},this},d.request=function(){var a,b,c,d,f;d=e([].slice.call(arguments)),f=v(d.success,d.failure),b=f[0],c=f[1],b&&d.__url&&(a=d.dataType,delete d.dataType,delete d.success,delete d.failure,x.exec("proxy.request","message","send",d,function(d){var e;if(d=d.content,"json"===a)try{d=JSON.parse(d)}catch(a){return e=a,void c(-1,"data from server isn't a valid json string")}b(d)},c))},c={init:function(a,b,c,d,e){var f;return this.options=a,this.plugin=d,this.methods=e,f=v(b,c),b=f[0],c=f[1],this.options.taskflag=!this.options.isContinuous,this.done=b,this.fail=c,this},start:function(){return x.exec("proxy."+this.plugin+".start",this.plugin,this.methods[0],this.options,this.done,this.fail),this},pause:function(){return x.exec("proxy."+this.plugin+".pause/resume",this.plugin,this.methods[1],{url:this.options.url}),this},resume:function(){return this.start()},stop:function(){return x.exec("proxy."+this.plugin+".stop",this.plugin,this.methods[2],{url:this.options.url}),this}},d.Download=function(a,b,c){this.init(a,b,c,"download",["download","pausedown","stopdown"])},u.extend(d.Download.prototype,c),d.Upload=function(a,b,c){this.init(a,b,c,"upload",["upload","pauseupload","stopupload"])},u.extend(d.Upload.prototype,c),d.ekpUpload=function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&(a.userkey&&!a.token&&(a.token=a.userkey),x.exec("proxy.uploadView","ekpupload","upload",a,b,c))},d.uploadView=d.ekpUpload,d}(),t.proxy=m,b={exit:function(){Was.exec("app","exitApp",{})},callApp:function(a,b,c,d){var e;e=v(c,d,!1),c=e[0],d=e[1],c&&(b||(b={}),b.__command=a,x.exec("app.callApp","app","callApp",b,c,d))},callNativeApp:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&(a.__otherParam&&u.extend(a,a.__otherParam,!1),x.exec("app.callNativeApp","app","callNativeApp",a,b,c))},on:function(a,b){a="on"+a.charAt(0).toUpperCase()+a.slice(1),Was.addEventListener(a,b)},setBadge:function(a){x.exec("app.setBadge","message","setAppBadge",a,null,s)},getBadge:function(a){var b,c;c=v(a),a=c[0],b=c[1],a&&x.exec("app.getBadge","message","getAppBadge",{},a,b)},getNetState:function(){return{netState:Was.netState}},getDeviceInfo:function(){var a,b,c,d;return(a=Was.deviceInfo)?(b=a.pixel.split("x"),c={os:a.os,type:a.type,largerPixel:parseInt(b[0]),smallerPixel:parseInt(b[1]),pixel:a.pixel}):(d=window.screen,c={os:"windows",type:"pad",largerPixel:d.width,smallerPixel:d.height,pixel:""+d.width+"x"+d.height}),c},getUserInfo:function(){return Was.userInfo.loginName||(Was.userInfo.loginName=Was.userInfo.userName),u.extend({},Was.userInfo)},setCookie:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("app.setCookie","cookie","setCookie",a,b,c)},getCookie:function(a,b,c){var d;d=v(b,c),b=d[0],c=d[1],b&&x.exec("app.getCookie","cookie","getCookie",a,b,c)},captureScreen:function(a,b,c){var d,e;u.isFunction(a)&&(c=b,b=a,a={}),e=v(b,c),b=e[0],c=e[1],b&&(d={targetWidth:100,targetHeight:100,encodingType:"png",quality:50,destinationType:"data"},a=u.extend({},d,a||{}),a.encodingType=w(a.encodingType),x.exec("app.captureScreen","app","screenCapture",a,function(c){"data"===a.destinationType&&(c.imageData="data:image/"+a.encodingType+";base64,"+c.imageData),b(c)},c))},showTitleBar:function(){x.exec("app.showTitleBar","app","config",{isTitleBarVisible:!0},null,null)},hideTitleBar:function(){x.exec("app.hideTitleBar","app","config",{isTitleBarVisible:!1},null,null)},setTitle:function(a){""!==a&&(a=""+a,x.exec("app.setTitle","app","config",{title:a},null,null))}},t.app=b,f={setOrientation:function(a){x.exec("device.setOrientation","device","setOrientation",{orientation:a},!0,s)},getNetType:function(a,b){var c;c=v(a,b),a=c[0],b=c[1],a&&x.exec("device.getNetType","device","getCurrentNetType",{},function(b){a(b)},b)},setClipboard:function(a){var b,c,d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("device.setClipboard","device","setClipboardData",{data:a},null,null)}},t.device=f,k={getPicture:function(a,b,c){var d,e;u.isFunction(a)&&(c=b,b=a,a={}),e=v(b,c),b=e[0],c=e[1],b&&(d={sourceType:"camera",targetWidth:100,targetHeight:100,encodingType:"png",quality:50,destinationType:"data"},a=u.extend({},d,a||{}),a.encodingType=w(a.encodingType),x.exec("media.getPicture","image","getPicture",a,function(c){var d;"data"===a.destinationType&&(c.imageData="data:image/"+a.encodingType+";base64,"+c.imageData),c.imageTime&&(c.imageTimeString=c.imageTime,d=c.imageTime.match(/(\w{4})(\w{2})(\w{2})(\w{2})(\w{2})(\w{2})/).map(function(a){return parseInt(a,10)}),c.imageTime=new Date(d[1],d[2]-1,d[3],d[4],d[5],d[6])),b(c)},c))},save2album:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("media.save2album","image","save2album",a,b,c)},previewImage:function(a,b){var c,d;a&&(a.start_index=+a.current||0,u.isArray(a.paths)&&(a.paths=a.paths.join(",")),d=v(null,b,!1),c=d[0],b=d[1],x.exec("media.previewImage","image","gallery",a,c,b))},captureVideo:function(a,b,c){var d,e;u.isFunction(a)&&(c=b,b=a,a={}),e=v(b,c),b=e[0],c=e[1],b&&(d={quality:"LOW"},a=u.extend({},d,a||{}),x.exec("media.captureVideo","media","captureVideo",a,b,c))},captureAudio:function(a,b){var c;c=v(a,b),a=c[0],b=c[1],a&&x.exec("media.captureAudio","media","captureAudioView",{},a,b)},playAudio:function(a,b){var c,d;d=v(null,b,!1),c=d[0],b=d[1],x.exec("media.playAudio","media","playAudioView",{fileurl:a},!0,b)},getSignImage:function(a,b,c){var d,e;e=v(b,c),b=e[0],c=e[1],b&&(d={penSize:2,penColor:1},a=u.extend({},d,a||{}),x.exec("media.getSignImage","image","getSignImage",a,b,c))}},t.media=k,h=function(){function a(a){this.fileName=a}return a.prototype.getOSPath=function(a,b){var c;c=v(a,b),a=c[0],b=c[1],a&&x.exec("file.getOSPath","file","getFileOSPath",{EasyMIPath:this.fileName},a,b)},a.prototype.info=function(a,b){var c;c=v(a,b),a=c[0],b=c[1],a&&x.exec("file.info","file","info",{filepath:this.fileName},function(b){b.isDir=1===+b.isDir,a(b)},b)},a.prototype.exists=function(a,b){var c;if(c=v(a,b),a=c[0],b=c[1],a)return x.exec("file.exists","file","exists",{filepath:this.fileName},a,function(c,d){r.debug("[file][exist][failCB]"+c+", "+d),1===c?a({exists:!1}):b(c,d)})},a.prototype.exist=a.prototype.exists,a.prototype.remove=function(a,b){var c;if(c=v(a,b,!1),a=c[0],b=c[1],a)return x.exec("file.remove","file","delete",{filepath:this.fileName,needConfirm:0},a,b)},a.prototype.view=function(a,b,c){var d,e;u.isFunction(a)&&(c=b,b=a,a={}),e=v(b,c,!1),b=e[0],c=e[1],b&&(d={useWebview:!1},a=u.extend({},d,a||{}),a.filepath=this.fileName,x.exec("file.view","file","viewdoc",a,b,c))},a.prototype.readAsText=function(a,b,c){var d;u.isFunction(a)&&(c=b,b=a,a="utf-8"),d=v(b,c),b=d[0],c=d[1],b&&x.exec("file.readAsText","file","getFileContent",{filepath:this.fileName,encode:a},b,c)},a.prototype.readAsBase64=function(a,b){var c;if(c=v(a,b),a=c[0],b=c[1],a)return x.exec("file.readAsBase64","file","getFileContent",{filepath:this.fileName,filetype:2},a,b)},a.prototype.copyTo=function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("file.copy","file","copy",{source:this.fileName,target:a},b,c)},a}(),t.File=h,d={add:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("contact.add","contact","add",a,b,c)},remove:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("contact.remove","contact","delete",{id:a},b,c)},update:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("contact.update","contact","update",a,b,c)},find:function(a,b,c){var d;d=v(b,c),b=d[0],c=d[1],b&&x.exec("contact.find","contact","find",a,function(a){var c,d,e,f,g;for(c=[],f=a.count,e=0;e<f;)d="return "+a["record"+e],g=new Function(d)(),c.push(g),++e;b(c)},c)},choose:function(a,b,c){var d,e;if(d={mode:1},"function"==typeof a&&(c=b,b=a,a={}),a=u.extend(d,a),e=v(b,c),b=e[0],c=e[1],b)return x.exec("contact.choose","contact","choose",a,function(a){var c;c=new Function("return "+a.contactors)(),b(c)},c)}},t.contact=d,n={scanBarCode:function(a,b){var c;c=v(a,b),a=c[0],b=c[1],a&&x.exec("scaner.scanBarCode","barcode","getBarcode",{},a,b)},scanTDCode:function(a,b){var c;c=v(a,b),a=c[0],b=c[1],a&&x.exec("scaner.scanTDCode","barcode","getTdBarcode",{},a,b)},getQRCode:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("scaner.getQRCode","barcode","genQRCode",a,function(c){"data"===a.destinationType&&(c.imageData="data:image/png;base64,"+c.imageData),b(c)},c)}},t.scaner=n,g={choose:function(a,b,c){var d,e;u.isFunction(a)&&(c=b,b=a,a={}),e=v(b,c),b=e[0],c=e[1],b&&(d={initList:[],isSticky:!1,stickyList:[],maxCount:0,isSelfSticky:!1},a=u.extend(d,a||{}),u.isArray(a.initList)&&(a.initList=a.initList.join(",")),a.initList=String(a.initList),u.isArray(a.stickyList)&&(a.stickyList=a.stickyList.join(",")),a.stickyList=String(a.stickyList),x.exec("econtact.choose","contactor","chooseContactors",a,function(a){a=a&&a.contactors?new Function("return "+a.contactors)():[],b(a)},c))},getUserInfoById:function(a,b,c){var d;if(d=v(b,c),b=d[0],c=d[1],b)return u.isArray(a)||(a=[a]),a=a.join(","),x.exec("econtact.getUserInfoById","contactor","getContactorDetail",{ids:a},function(a){a=a&&a.contactors?new Function("return "+a.contactors)():[],b(a)},c)}},t.econtact=g,e={encrypt:function(a,b,c){var d;d=v(b,c),b=d[0],c=d[1],b&&x.exec("crypto.encrypt","crypto","AESEncode",a,b,c)},decrypt:function(a,b,c){var d;d=v(b,c),b=d[0],c=d[1],b&&x.exec("crypto.decrypt","crypto","AESDecode",a,b,c)},obscureFile:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("crypto.obscureFile","crypto","obscureFile",a,b,c)},restoreFile:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("crypto.restoreFile","crypto","restoreFile",a,b,c)}},t.crypto=e,j={getLocation:function(a,b){var c;c=v(a,b),a=c[0],b=c[1],a&&x.exec("location.getLocation","location","locate",{type:"Cell"},a,b)}},t.location=j,l={call:function(a,b){null==b&&(b=!0),x.exec("phone.call","phone","call",{number:a,confirm:b})},sms:function(a,b){var c;null==b&&(b=""),c={number:a,content:b},x.exec("phone.sms","sms","send",c)}},t.phone=l,q={zip:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&(a.folderPath&&(a.FolderPath=a.folderPath),a.filePaths&&(u.isArray(a.filePaths)&&(a.filePaths=a.filePaths.join(",")),a.toZipFiles=a.filePaths),x.exec("zip.zip","zip","zip",a,b,c))},unzip:function(a,b,c){var d;d=v(b,c,!1),b=d[0],c=d[1],b&&x.exec("zip.unzip","zip","unzip",{zipFilePath:a.zipFilePath,outPath:a.outPath},b,c)}},t.zip=q,i={canGo:function(a){var b,c;c=v(a),a=c[0],b=c[1],a&&x.exec("history.canGo","app","canGo",{},a,b)},hasPrev:function(a){var b,c;c=v(a),a=c[0],b=c[1],a&&i.canGo(function(b){a({hasPrev:b.canGoBack})})},back:function(a){u.isFunction(a)||(a=s),x.exec("history.back","app","goBack",{},null,a)},forward:function(a){u.isFunction(a)||(a=s),x.exec("history.forward","app","goForward",{},null,a)}},t.history=i,p=function(){var a,b,c;return a=function(a){return function(b,c){u.isFunction(a)?a({errMsg:c,code:b}):s(b,c)}},b=null,c={ready:function(a){t.ready(a)},error:function(){},checkJsApi:function(a){var b,d,e,f,g;for(b=a.jsApiList,e={},f=0,g=b.length;f<g;f++)d=b[f],e[d]=!!c[d];a.success({checkResult:e,errMsg:"checkJsApi:ok"})},previewImage:function(a){a.current&&(a.current=a.urls.indexOf(current)),a.paths=a.urls,a.current=a.current||0,t.media.previewImage(a)},startRecord:function(){t.media.captureAudio(function(a){"function"==typeof b&&b(a),b=null})},stopRecord:function(a){b=a.success},onVoiceRecordEnd:function(){},playVoice:function(a){t.media.playAudio(a.localId)},getNetworkType:function(a){t.device.getNetType(function(b){a.success({networkType:b.netType.toLowerCase()})})},getLocation:function(a){t.location.getLocation(a.success)},closeWindow:function(){t.app.exit()},scanQRCode:function(a){var b;b=a.scanType,b=b?b[0]:"qrCode",b="barCode"===b?"scanBarCode":"scanTDCode",t.scaner[b](function(b){"function"==typeof a.success&&a.success({resultStr:b.code})})}},c.kk=!0,c}(),t.wx=p,t});
//# sourceMappingURL=kk-1.1.29.min.js.map