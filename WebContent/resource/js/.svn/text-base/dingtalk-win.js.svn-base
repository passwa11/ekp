/*压缩类型：标准*/
Com_RegisterFile("dingtalk-win.js");

seajs.use(['lui/parser', 'lui/jquery', 'lui/dialog', 'theme!common', 'theme!icon', 'theme!iconfont'],
    function (parser, $) {
    window.dingTalkEvent = function () {
        var clientType = Com_Parameter.clientType;
        if (clientType == '-3') {
            Com_AddEventListener(document, "click", function () {
                var eventObj = Com_GetEventObject();
                var eleObj = eventObj.srcElement || eventObj.target;
                var tagName = eleObj.tagName;
                //待办页面内容是<a>标签里包着<span>标签   #106486
                if(tagName.toLowerCase() == 'span'){
                    eleObj = eleObj.parentElement;
                    tagName = eleObj.tagName;
                }
                
                if (tagName.toLowerCase() == 'a') {
                    var target = $(eleObj).attr('target');
                    var href = $(eleObj).attr('href');
                    if (target == '_blank' && href && href.indexOf('javacript') == -1) {
                        $(eleObj).attr('href', Com_SetUrlParameter(href, 'ddtab', true));
                    }
                }
                return true;
            });

            var curl = window.location.href;
            // 如果打开的是流程预览页面
            if (window.hasReviewButtons()) {
                // 以起草人身份操作处理
               /* $("#drafterOptButton").attr("onclick", "").off("click").on("click", function () {
                    showTipWindow(curl);
                });

                // 以特权人身份操作
                $("#authorityOptButton").attr("onclick", "").off("click").on("click", function () {
                    showTipWindow(curl);
                });*/
            }

            // 后台管理
            $(".lui_list_left_sidebar_td div").each(function (i, n) {
                var item = $(n).html();
                if ($(n).attr("data-lui-type") == "lui/data/source!Static" && item.indexOf("lui_iconfont_navleft_com_background") >= 0) {
                    console.log("item=>" + item);
                    // /javascript:LUI.pageOpen\(\'\/.*sys\/profile.*?_blank\'\);/
                    // var rgx = "javascript:LUI.pageOpen('/ekp/sys/profile/index.jsp#app/ekp/km/review','_blank');";
                    var replaceText = "javascript:showTipWindow('" + curl + "');";
                    // console.log(item.replace(/javascript:LUI.pageOpen\(\'\/.*sys\/profile.*?[_blank|_rIframe]\'\);/,replaceText));
                    $(n).html(item.replace(/javascript:LUI.pageOpen\(\'\/.*sys\/profile.*?[_blank|_rIframe]\'\);/, replaceText));
                }
            });
        }
    };

    // 检测流程审批以起草人处理、以特权人处理点击
    window.hasReviewButtons = function () {
        var flag = false;
        var curl = window.location.href;
        // 流程预览页面
        if ($("#drafterOptButton").length > 0 || $("#authorityOptButton").length > 0) {
            flag = true;
        }
        return flag;
    };

    // 打开弹框提示在外部打开
    window.showTipWindow = function (url) {
        // 必须编码，防止#导致参数无法接收
        url = url.replace(/#/g, "%23");

        var innerHTML = "<div class='model'>" +
            "	<div class='model-header'>" +
            "		<span class='close'>×</span>" +
            "	</div>" +
            "<div class='model-body no-support-msg'>" +
            "	<div class='pic'><img src='" + Com_Parameter.ContextPath + "resource/images/no_support_ding.png?s_cache=${ LUI_Cache }'/></div>" +
            "	<div class='msg'>暂不支持在钉钉PC端内打开，如有需要，请打开到外部浏览器。</div>" +
            "	<div class='btn'>打开到外部浏览器</div>" +
            "</div>" +
            "<div class='model-footer'></div>" +
            "</div>" +
            "<div class='mask'></div>";
        // 防止弹出层重复添加
        if (typeof $(".model").html() == "undefined") {
            $("body").append(innerHTML);
            // 打开事件
            $(".no-support-msg .btn").on("click", function () {
                window.open(url, '_blank');
            });
        }

        // 展示弹窗
        showModel();

        function showModel() {
            // 模态窗里面必须是在外部打开
            try {
                window.open = function (url, target, specs, replace) {
                    if (target && target == '_blank' && url) {
                        url = Com_SetUrlParameter(url);
                    }
                    var win = window.Com_openWin(url, target, specs, replace);
                    return win;
                };
            } catch (e) {
                console.log(e);
            }

            var wW = $(window).width();  //浏览器可视区域宽度和高度
            var wH = $(window).height();
            var oW = $(".model").innerWidth(); //获取类叫model的宽度和高度
            var oH = $(".model").innerHeight();
            $(".model").show().css({"top": (wH - oH) / 2 + "px", "left": (wW - oW) / 2 + "px"});
            $(".mask").fadeIn();
        }

        $(window).resize(function () {
            if ($(".model").is(":visible")) { //弹出框必须可见后 才能调用showModel()
                showModel();
            }
        });
        $(".close").click(function () {
            // 关闭模态窗之后恢复钉钉打开窗口方式
            try {
                window.open = function (url, target, specs, replace) {
                    if (target && target == '_blank' && url) {
                        url = Com_SetUrlParameter(url, "ddtab", "true");
                    }
                    var win = window.Com_openWin(url, target, specs, replace);
                    return win;
                };
            } catch (e) {
                console.log(e);
            }

            $(".mask").fadeOut();
            $(".model").hide();
        });
        $(document).keydown(function (ev) {
            if (ev.keyCode == 27) {  //当按下键盘Esc时===》close关闭按钮
//							$(".model").hide();
//							$(".mask").fadeOut();
                $(".close").trigger("click");//trigger("事件名")  模拟事件
            }
        })
    };

    $(document).ready(function () {
        parser.parse();
        dingTalkEvent();
    });
});

//适配钉钉客户端pc
if(Com_Parameter.clientType=='-3'){	
	// 模态窗口的兼容,满足此条件必须在外部打开
	if(checkAdminUrl()){
		var curl = window.location.href;
		var errurl = Com_Parameter.ContextPath + "resource/jsp/no_support_ding.jsp";
		console.log("curl=>" + curl);
		console.log("errurl=>" + errurl);
		// 防止循环跳转
		if(curl != errurl){
			console.log("dingCurrentUrl=>" + curl);
			window.location.href = errurl;	
		}
	}else{
		if (typeof window.dingtalkinit === "undefined") {
			window.dingtalkinit = true;
            if(typeof(window.Com_openWin) == undefined || window.Com_openWin ==null ){
                window.Com_openWin = window.open;
            }
			window.open = function(url,target,specs,replace){
				if(target && target=='_blank' && url){
					url = Com_SetUrlParameter(url, "ddtab","true");
				}
				var win = window.Com_openWin(url,target,specs,replace);
                if(win == null || typeof win == "undefined") {
                    win = "";
                }
				return win;
			};
			//重写window.close方法
			window.close = function(){
				dd.biz.navigation.close();
			};
		}
	}
}

// 检测是否钉钉内部打开后台管理
function checkAdminUrl(){
	var flag = false;
	var curl = window.location.href;
	// 后台管理
    if(curl.indexOf("ekp/sys/profile") >= 0){
    	flag = true;
    }
	return flag;
}