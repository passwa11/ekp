//切换选中的位置
var flag = true;
var rightContent=$(".model-edit-right .model-edit-view-content")
var _calendar_Position = {
    panel:$(".model-edit-right .model-edit-view-content"),
    local: function (position, barMark) {
        if (barMark) {
            $("[calendar-bar-mark='" + barMark + "']").trigger($.Event("click"));
        } else {
            $("[calendar-bar-mark=\"design\"]").trigger($.Event("click"));
        }
        var local = $("[data-lui-local='" + position + "_local']");
        //背景闪烁
        $("[data-lui-local]").css({"background-color": "rgba(66,133,244,0)"});
        local.css({"background-color": "rgba(230,230,230,1)"});
        local.animate({"background-color": "rgba(230,230,230,0)"},"slow");
        local.animate({"background-color": "rgba(230,230,230,1)"},"slow");
        local.animate({"background-color": "rgba(230,230,230,0)"},"slow");
        //标题变色
        $("[data-lui-local]").removeClass("localFocus")
        local.addClass("localFocus");
        if (local.length > 1) {
            local = $(local.get(0));
        }

        rightContent.scrollTop(0)
        if (local.offset()) {
            rightContent.scrollTop(local.offset().top-150);
        }

    },
    "fdCondition": function () {
        this.local("fdCondition");
    },
    "fdDisplay": function () {
        this.local("fdDisplay");
    },
    "tableTitle": function () {
        this.local("tableTitle");
        rightContent.scrollTop(0)
    },
    "showMode": function () {
        this.local("showMode");
    },
    "fdOperation": function () {
        this.local("fdOperation");
    }
}

function switchSelectPositionItem(obj, direct) {
    Com_EventStopPropagation();
    $(".model-source-table-main td").first().removeClass("source-dialog-preview");
    if (flag) {
        $("[data-lui-position]").removeClass("active");
        var position = $(obj).attr("data-lui-position");
        $("[data-lui-position='" + position + "']").addClass("active");
        $("[data-lui-position='" + position.split("-")[0] + "']").addClass("active");
        if (_calendar_Position[position]) {
            _calendar_Position[position]()
        }
    } else {
        flag = true;
    }
    return false;
}

