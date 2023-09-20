define([
  "dojo/parser",
  "mui/main",
  "mui/pageLoading",
  "dojo/_base/window",
  "dojox/mobile/sniff",
  "dojo/topic",
  "mui/form",
  "dojo/request",
  "mui/util",
  "dojo/_base/lang",
  "dojo/_base/query",
  "mui/device/adapter",
  "mui/device/device",
  "dojo/dom-style"
], function (
  parser,
  main,
  pageLoading,
  win,
  has,
  topic,
  form,
  request,
  util,
  lang,
  query,
  adapter,
  device,
  domStyle
) {
  return {
    // 保持跟pc端链接统一，收藏机制会使用
    recoverUrl: function (url) {
        if (history.replaceState) {
            history.replaceState(null, null, url);
        }
    },

    // 异常状态码
    errorCode: {
      "403": "/third/pda/resource/jsp/e403.jsp",
    },

    // 渲染头部
    renderTitle: function () {
      var titles = query("[data-dojo-block='title']");
      if (titles.length == 0) {
        return;
      }
      var title = titles[0].innerText;
      if (title) {
        adapter.setTitle(title);
      }
    },

    // 解码到编码前
    decodeUrlOriginal: function(targetUrl) {
    	var decodeUrl = decodeURIComponent(targetUrl);
	    if(decodeUrl === targetUrl) {
	        return targetUrl;
	    } else {
	        return this.decodeUrlOriginal(decodeUrl);
	    }
	},

	// num次编码
	encodeStr: function(str, num, isDecode) {
		if(!str) return "";
		var url = isDecode? decodeUrlOriginal(str) : str;
		for(var i=0; i<num; i++){
			url = encodeURIComponent(url);
		}
		return url;
	},

	// 设置钉钉鉴权url
	setOriginalUrl: function() {
        var locahrefUrl= location.href;
        var checkUrl = this.decodeUrlOriginal(locahrefUrl);
        var urlIndex = checkUrl.indexOf("#");
        var field = "q.docCategory";

        // 此处对特殊字符做处理保证与鉴权URL一致 详细原因查看 #137043
        if(urlIndex > -1 && checkUrl.indexOf(field) > urlIndex) {
        	var paramField = "_referer";
        	var paramUrl = this.encodeStr(util.getUrlParameter(locahrefUrl, paramField), 1);
    		locahrefUrl = util.setUrlParameter(locahrefUrl, paramField, paramUrl);
        }
        // 此处保存recoverUrl之前的URL 解决replaceState改变URL后钉钉鉴权url不一致导致鉴权失败。
        // 该变量在 mui/device/ding/adapter.js 中使用
        window.oldLocaHrefUrl = locahrefUrl;
	},

    loadAndParse: function (url) {
      dojoConfig._native = true;
      topic.subscribe("parser/done", this.renderTitle.bind(this));
      dojoConfig.tiny = true;
      try {
        var url = util.formatUrl(url);
        var searchJSON = util.urlParse(location.href).searchJSON,
        url = util.setUrlParameterMap(url, searchJSON);

        this.setOriginalUrl();

        this.recoverUrl(url);

        // 设置数据请求参数
        url = util.setUrlParameter(url, "_data", "true");
    	var errorData = "";
        request
          .get(url, {
            handleAs: "json",
            headers: { accept: "application/json" },
          })
          .then(
            function (data) {

            	if(data.dataUrl&&data.dataUrl!=""){
                    var url = util.formatUrl(data.dataUrl);
                    var deviceType = device.getClientType();
                    //钉钉浏览器走特定方法
                    if(!has("ios") && deviceType === 11 || deviceType === 14){
                        if(history.replaceState) {
                            history.replaceState(null, document.title, url);
                            history.go(0);
                        }else{
                            location.replace(url);
                        }
                    }else{
                        location.replace(url);
                    }
                 }
            	
              if (!data.text) {
                return;
              }
              var html = data.text;
              var content = document.getElementById("content");
              //pageLoading.opcity();
              util.setInnerHTML(content, html).then(
                lang.hitch(this, function () {
                  parser.parse().then(function () {
                    win.doc.dojoClick = !has("ios") || has("ios") > 13;
                    pageLoading.hide();
                    topic.publish("parser/done");
                    // setTimeout(function () {
                    //     topic.publish("parser/detail/done");
                    // }, 50);
                  });
                })
              );
            },
            lang.hitch(this, function (res) {
              var status = res.response.status;
              if (this.errorCode[status]) {
                //location.href = util.formatUrl(this.errorCode[status]);
            	// 请求403页面
            	var errorJsp = util.formatUrl(this.errorCode[status]);
            	var promise = request.post(errorJsp,{});
                promise.response.then(function(response){
                	errorData =  response.data;
                	document.write(errorData);
            	});
              }
            })
          );
      } catch (e) {}
    },
  };
});
