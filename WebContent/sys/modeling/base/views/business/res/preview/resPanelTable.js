//切换选中的位置
var flag = true;
var rightContent=$(".model-edit-right .model-edit-view-content")
var _resPanel_Position = {
    panel:$(".model-edit-right .model-edit-view-content"),
    local: function (position, barMark) {
        if (barMark) {
            $("[respanel-bar-mark='" + barMark + "']").trigger($.Event("click"));
        } else {
            $("[respanel-bar-mark=\"frame\"]").trigger($.Event("click"));
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
       // rightContent.scrollTop(100)
    },
    "dayOrWeek": function () {
        this.local("dayOrWeek");
       // rightContent.scrollTop(0)
    },
    "tableTitle": function () {
        this.local("tableTitle");
        rightContent.scrollTop(0)
    },
    "tableCol": function () {
        this.local("tableCol");
    },
    "tableRow": function () {
        this.local("tableRow");
    },
    "fdColor": function () {
        this.local("fdColor", "content");
    },
    "contentTable": function () {
        this.local("contentTable", "content");

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
        if (_resPanel_Position[position]) {
            _resPanel_Position[position]()
        }
    } else {
        flag = true;
    }
    return false;
}

//切换选择位置
function switchSelectPosition(obj, direct, toPosition) {
    Com_EventStopPropagation();
    $(".model-source-table-main td").first().removeClass("source-dialog-preview");
    $("[data-lui-position]").removeClass('active');
    var position = $(obj).attr("data-lui-position");
    if (!position) {
        $(obj).addClass("active");
        $('[data-lui-position="' + toPosition + '"]').addClass("active");
    } else {
        if (_resPanel_Position[position]) {
            _resPanel_Position[position]()
        }
        $("[data-lui-position='" + position + "']").addClass("active");
        $("[data-lui-position='" + position.split("-")[0] + "']").addClass("active");
        $("[data-lui-position='" + position + "']").parents(".model-edit-view-content-top").addClass("active");
        $("[data-lui-position='" + position + "']").parents(".model-edit-view-content-bottom").addClass("active");
        $("[data-lui-position='" + position + "']").parents(".model-edit-view-content-center-wrap").addClass("active");
    }
}

//展示详情展示-弹出层
function dialogDetailPreview() {
    Com_EventStopPropagation();
    $("[data-lui-position]").removeClass('active');
    $(".model-source-table-main td").first().addClass("source-dialog-preview");
    var content = $(".preview-content");
    content.find("[preview-content-mark]").html("[示例文字]")
    if ( window.resPanel) {
        var resPanelConfig = window.resPanel.getKeyData();
        if (resPanelConfig && resPanelConfig.source) {
            var source = resPanelConfig.source;
            if (source.show) {
                content.find("[preview-content-mark=\"showTitle\"]").html(source.show.text)
            }
            if (source.dialog && source.dialog.data) {
                var dd = source.dialog.data;
                for (var key in dd) {
                    content.find("[preview-content-mark=\"" + key + "\"]").html(dd[key].text)
                }
            }
        }
    }
}