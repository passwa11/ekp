//切换选中的位置
var flag = true;
var rightContent=$(".model-edit-right .model-edit-view-content")
var _gantt_Position = {
    panel:$(".model-edit-right .model-edit-view-content"),
    local: function (position, barMark) {
        if (barMark) {
            $("[gantt-bar-mark='" + barMark + "']").trigger($.Event("click"));
        } else {
            $("[gantt-bar-mark=\"frame\"]").trigger($.Event("click"));
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
    "fdDisplay": function () {
        this.local("fdDisplay");
        // rightContent.scrollTop(100)
    },
    "fdOrderBy": function () {
        this.local("fdOrderBy");
        // rightContent.scrollTop(100)
    },
    "viewContent": function () {
        this.local("viewContent");
        // rightContent.scrollTop(100)
    },
    "tableName": function () {
        this.local("tableName",'basic');
        // rightContent.scrollTop(100)
    },
    "fdOperation": function () {
        this.local("fdOperation");
        // rightContent.scrollTop(100)
    },
    "timeDimension": function () {
        this.local("timeDimension");
        // rightContent.scrollTop(100)
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
        if (_gantt_Position[position]) {
            _gantt_Position[position]()
        }
    } else {
        flag = true;
    }
    return false;
}

//切换选择位置
function switchSelectPosition(obj, direct, toPosition) {
    Com_EventStopPropagation();
    lastSelectPostionObj = obj;
    lastSelectPostionDirect = direct;
    lastSelectPosition = toPosition;
    $(".model-source-table-main td").first().removeClass("source-dialog-preview");
    $("[data-lui-position]").removeClass('active');
    // $("[data-lui-local]").removeClass('localFocus');
    var position = $(obj).attr("data-lui-position");
    if (!position) {
        $(obj).addClass("active");
        $('[data-lui-position="' + toPosition + '"]').addClass("active");
    } else {
        if (_gantt_Position[position]) {
            _gantt_Position[position]()
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
    // Com_EventStopPropagation();
    $("[data-lui-position]").removeClass('active');
    // $(".model-source-table-main td").first().addClass("source-dialog-preview");

    var text =  $("#dialogDetailPreview").text();
    if(text ==ganttOption.lang.preview){
        $(".model-source-table-main .gantt-view-block-text").first().addClass("source-dialog-preview");
        $("#dialogDetailPreview").text(ganttOption.lang.closePreview);
    }else {
        $(".model-source-table-main .gantt-view-block-text").first().removeClass("source-dialog-preview");
        $("#dialogDetailPreview").text(ganttOption.lang.preview);
    }

    var content = $(".preview-content");
    var showTitle = $('input[name="cfgGanttShowField_name"]').val();
    if (showTitle) {
        var showTitleHtml = showTitle.replace(/$/g,"");
        content.find("[preview-content-mark=\"showTitle\"]").html(showTitleHtml);
    }else{
        content.find("[preview-content-mark=\"showTitle\"]").html(ganttOption.lang.docSubject);
    }
}

//切换查询选项卡
function switchFdWhereTab(thisObj,select) {
    Com_EventStopPropagation();
    $("#customFdWhereSelect").removeClass("fdwhere-tabActive");
    $("#customFdWhereSelect").removeClass("fdwhere-tabActiveSelect");
    $("#builtInFdWhereSelect").removeClass("fdwhere-tabActive");
    $("#builtInFdWhereSelect").removeClass("fdwhere-tabActiveSelect");
    if(select == 'customFdWhereTab'){
        $("#customFdWhereSelect").addClass("fdwhere-tabActiveSelect");
        $("#builtInFdWhereSelect").addClass("fdwhere-tabActive");

        $("#builtInFdWhereTab").css("display","none");
        $("#customFdWhereTab").css("display","inline");
    }else if(select == 'builtInFdWhereTab'){
        $("#builtInFdWhereSelect").addClass("fdwhere-tabActiveSelect");
        $("#customFdWhereSelect").addClass("fdwhere-tabActive");

        $("#customFdWhereTab").css("display","none");
        $("#builtInFdWhereTab").css("display","inline");
    }

}

function closeDialogDetailPreview() {
    $(".model-source-table-main .gantt-view-block-text").first().removeClass("source-dialog-preview");
    $("#dialogDetailPreview").text(ganttOption.lang.preview);
}


function getGanttData(){
    var datas = [];
    var data = {};
    var fdConditions = [];//筛选项
    var fdOrderBys = [];//排序项
    var listOperations = [];//业务操作
    var fdDisplays = [];//显示项
    data.fdConditions = fdConditions;
    data.fdOrderBys = fdOrderBys;
    data.listOperations = listOperations;
    data.fdDisplays = fdDisplays;

    //筛选项
    var fdConditionTexts = ($("[name='tableConditionText']").val() || "").split(";") || [];
    for(var i=0; i<fdConditionTexts.length; i++){
        if(fdConditionTexts[i]){
            var fdCondition = {};
            fdCondition.text = fdConditionTexts[i];
            fdConditions.push(fdCondition);
        }
    }
    //排序项
    var $orderbyTrs = $("#xform_main_data_orderbyTable").find("tr");
    for(var i=0; i<$orderbyTrs.length; i++){
        var fdAttrFieldId = $($orderbyTrs[i]).find("[name='fdAttrField']").val();
        var fdAttrFieldText = $($orderbyTrs[i]).find("[name='fdAttrField']").find("option[value='"+fdAttrFieldId+"']").text();
        var fdOrderBy = {};
        var fdAttrField = {};
        fdAttrField.text = fdAttrFieldText;
        fdOrderBy.fdAttrField = fdAttrField;
        fdOrderBys.push(fdOrderBy);
    }
    var listOperationNameObjs = $("[name^='listOperationNameArr']");
    for(var i=0; i<listOperationNameObjs.length; i++){
        var listOperationNameObj = listOperationNameObjs[i];
        var listOperationName = $(listOperationNameObj).val() || "";
        var listOperation = {};
        listOperation.name = listOperationName;
        listOperations.push(listOperation);
    }
    //显示项
    var fdDisplayTexts = ($("[name='fdDisplayText']").val() || "").split(";") || [];
    for(var i=0; i<fdDisplayTexts.length; i++){
        if(fdDisplayTexts[i]){
            var fdDisplay = {};
            fdDisplay.text = fdDisplayTexts[i];
            fdDisplays.push(fdDisplay);
        }
    }

    datas.push(data);
    return datas;
}



function selectOnChange(id){
    var value;
    if('ganttStartTime'==id){
        $("#ganttEndTime option").each(function () {
            $(this).css("display","inline");
        })
        value=$("#ganttStartTime").val();
        $("#ganttEndTime option[value='"+value+"']").css("display","none");
    }
    if('ganttEndTime'==id){
        $("#ganttStartTime option").each(function () {
            $(this).css("display","inline");
        })
        value=$("#ganttEndTime").val();
        $("#ganttStartTime option[value='"+value+"']").css("display","none");
    }
}