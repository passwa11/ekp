var dataView = render.parent;
var iframeObj = null;
var iframeLoad = null;
var new_src = null;
if (render.preDrawing) {            //预加载执行
    sendMessage();
    return;
}
var heig = dataView.element.parent().height();
if (data && data.src != null) {
    var resizeFun = function (argu) {
        if(iframeObj.parents(".lui_panel_content_inside").css("overflow")){
            iframeObj.css({"height": argu.height});
        }
    };
    var src = env.fn.formatUrl(data.src);
    seajs.use(['lui/util/str'], function (strutil) {
        src = strutil.decodeHTML(src);
    });
    var tempData = [];
    tempData["lui.element.id"] = render.parent.id;
    new_src = env.fn.variableResolver(src, tempData);
    var frameName = "";
    if ($.trim(render.vars.frameName) != "") {
        frameName = "name='" + $.trim(render.vars.frameName) + "'";
    }
    if (new_src.indexOf('=' + render.parent.id) > -1) {
        var initHeight = "100%";
        if (heig > 0) {
            initHeight = heig;
        }
        iframeObj = $('<iframe width="100%" height="' + initHeight + '" allowTransparency="true" scrolling="no" frameborder="0" ' + frameName + '></iframe>');

        render.parent.on('resize', resizeFun, render);
        render.parent.onErase(function () {
            render.parent.off('resize', resizeFun);
        });
    } else {
        if (data.height != null) {
            heig = data.height;
        }
        iframeObj = $('<iframe width="100%" height="' + heig + '" allowTransparency="true" scrolling="auto" frameborder="0" ' + frameName + '></iframe>');
    }
    //#37734配置门户组件时选引用其他网页会出现4px的白边, 加类名标示
    $(dataView.element).addClass("lui_component_iframe_wrap");
    iframeLoad = $("<img style='position: absolute;' src='" + env.fn.formatUrl('/sys/ui/js/ajax.gif') + "' />");
}

function sendMessage() {
    if (data && data.counturl != null) {
        var countUrl = env.fn.formatUrl(data.counturl);
        if (dataView && dataView.parent) {
            $.getJSON(countUrl, function (data) {
                dataView.parent.emit("count", data);
            });
        }
    }
}

function portalNotifyTitleCount(evt) {
    var _dataView = window.LUI(evt.luiId);
    var _data = _dataView.data;

    if (evt && _data && _data.counturl != null) {
        if (_dataView && _dataView.parent) {
            _dataView.parent.emit("count", {count: evt.count});
        }
    }
}

seajs.use(['lui/topic'], function (topic) {//待办事件监听
    topic.subscribe('portal.notify.title.count', portalNotifyTitleCount);
    topic.subscribe('portal.email.title.count', refreshEmailPortalTiltleCount);
});

function refreshEmailPortalTiltleCount(evt) {
    var _dataView = window.LUI(evt.luiId);
    var _data = _dataView.data;

    if (evt && _data && _data.counturl != null) {
        var countUrl = env.fn.formatUrl(_data.counturl);
        if (_dataView && _dataView.parent) {
            $.getJSON(countUrl, function (data) {
                _dataView.parent.emit("count", data);
            });
        }
    }
}

dataView.onErase(function () {
    try {
        seajs.use(['lui/topic'], function (topic) {//待办事件监听
            topic.unsubscribe('portal.notify.title.count', portalNotifyTitleCount);
            topic.unsubscribe('portal.email.title.count', refreshEmailPortalTiltleCount);
        });
        iframeObj.remove();
    } catch (e) {
        if (window.console) console.error(e);
    }
});
done(iframeObj);
if (new_src != null) {
    if (iframeObj) {
        try {
            var iframeW = iframeObj[0].contentWindow;
            iframeObj.attr("src", 'about:blank');
            iframeW.document.write('');
            iframeW.document.clear();
            window.CollectGarbage && window.CollectGarbage();
        } catch (e) {
            if (window.console) console.error(e);
        }
        ;
        iframeObj.attr("src", new_src);
        iframeObj.load(function () {
            if (iframeLoad)
                iframeLoad.remove();
        });
        if (iframeLoad)
            render.parent.element.prepend(iframeLoad);
    }
}