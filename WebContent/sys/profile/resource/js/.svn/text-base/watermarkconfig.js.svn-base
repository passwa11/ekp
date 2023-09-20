var watermarkParam;

// 计算布局高度
function getConfigHeight(){
    var leftHeight = $(".watermark_config_viewer_box_left").height();
    if(leftHeight) $(".watermark_config_viewer_box_right").height(leftHeight);
    var barHeight = $(".watermark_config_content").height();
    if(barHeight) $(".watermark_config_container_bar").height(barHeight);
}
// 切换页签
$(".watermark_config_viewer_bar_list>li").click(function(){
    if ($(this).hasClass("computer_bar")) {
        $(".computer").show();
        $(".mobile").hide();
        getConfigHeight();
    } else {
        $(".computer").hide();
        $(".mobile").show();
        getConfigHeight();
    }
})
$(".watermark_config_tab>li").click(function(){
    if(!$(this).hasClass("active")){
        $(this).addClass("active").siblings().removeClass("active")
    }
    waterMarkRefresh();
})
// 开关按钮
function switchChange(){
    var val = $(".watermark_config_switch_button>div>input").val();
    if(val == "true"){
        $(".switch_on_content").hide();
        $(".switch_off_content").show();
    }else{
        $(".switch_on_content").show();
        $(".switch_off_content").hide();
    }
}
LUI.ready(function() {
    switchChange();
    getConfigHeight();
    $(".colorColorDiv").attr("data-color-mark-id","colorColor");
    window.SpectrumColorPicker.init("colorColor");
});

// 水印内容下拉菜单
$('.watermark_configuration_list_add').click(function(e){
    e?e.stopPropagation():event.cancelBubble = true;   
    if($(this).find('.watermark_configuration_list_pop').css('display') == 'none'){
        $(this).find('.watermark_configuration_list_pop').show();
    }else{
        $(this).find('.watermark_configuration_list_pop').hide();
    }
})
// 下拉列表
$('.muiPerformanceDropdownBox').click(function(e){
    e?e.stopPropagation():event.cancelBubble = true;
    if($(this).find('.muiPerformanceDropdownBoxList').css('display') == 'none'&&$(this).hasClass('active')==false){
        $('.muiPerformanceDropdownBox').removeClass('active');
        $(this).addClass('active');
        $('.muiPerformanceDropdownBoxList').hide();
        $(this).find('.muiPerformanceDropdownBoxList').show();
    }else{
        $(this).removeClass('active');
        $(this).find('.muiPerformanceDropdownBoxList').hide();
    }
})
$('.muiPerformanceDropdownBoxList>ul>li').click(function(e){
    e?e.stopPropagation():event.cancelBubble = true;
    $(this).parents('.muiPerformanceDropdownBox').children('span').text($(this).text());
    $(this).parents('.muiPerformanceDropdownBox').find('.muiPerformanceDropdownBoxList').hide();
    $(this).parents('.muiPerformanceDropdownBox').removeClass('active');
    setTimeout(function(){
        waterMarkRefresh()
    },100)
});
// 下拉列表点击外部或者按下ESC后列表隐藏
$(document).click(function(){
    $('.muiPerformanceDropdownBoxList').hide();
    $(this).find('.watermark_configuration_list_pop').hide();
    $('.muiPerformanceDropdownBox').removeClass('active');
    $(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
}).keyup(function(e){
    var key =  e.which || e.keyCode;;
    if(key == 27){
        $('.muiPerformanceDropdownBoxList').hide();
        $(this).find('.watermark_configuration_list_pop').hide();
        $('.muiPerformanceDropdownBox').removeClass('active');
    }
});
$('.muiHrArchivesUploadAccessoryText>p').mouseenter(function(){
    $(this).parent('.muiHrArchivesUploadAccessoryText').append("<div class='muiHrArchivesUploadAccessoryHover'></div>")
    var txt=$(this).text();
    $(this).siblings('.muiHrArchivesUploadAccessoryHover').text(txt).show();
}).mouseleave(function(){
    $(this).siblings('.muiHrArchivesUploadAccessoryHover').remove();
})
// 拖动列表
var nest=document.getElementById('nested');
var nest=new Sortable(nest,{
    handle: ".mui_watermark_icon_drop", 
    animation: 300,
    dragClass: "droping",
    ghostClass:'dropingReplace',
    forceFallback: true, //拖拽时元素透明度
    emptyInsertThreshold: 5,
    // 列表内元素顺序更新的时候触发
    onUpdate: function (/**Event*/evt) {
        var itemEl = evt.item;
        var newIdx = evt.newIndex;
        waterMarkConfigUpdate();
        waterMarkRefresh();
    },
});
// 自定义透明度-进度条
var scroll = document.getElementById('watermark_configuration_scroll');
var bar = document.getElementById('watermark_configuration_bar');
var mask = document.getElementById('watermark_configuration_mask');
var barleft = 0; //已拖拽距离-像素值 
var barWidth = scroll.offsetWidth - bar.offsetWidth; //进度条宽度
bar.onmousedown = function dropBar(){
    var event = event || window.event;
    var leftVal = event.clientX - this.offsetLeft;
    var that = this;
    document.onmousemove = function(event){
    var event = event || window.event;
    barleft = event.clientX - leftVal;
    if(barleft < 0)
        barleft = 0;
    else if(barleft > barWidth)
        barleft = barWidth;
    mask.style.width = barleft +'px' ;
    that.style.left = barleft + "px";
    $(".watermark_configuration_alpha_input_box.drop").children(".watermark_configuration_alpha_number").children("span").text(parseInt(barleft/(scroll.offsetWidth-bar.offsetWidth) * 100));
    $(".watermark_configuration_alpha_btn.drop").children("span").text(parseInt(barleft/(scroll.offsetWidth-bar.offsetWidth) * 100));
    
    //防止选择内容--当拖动鼠标过快时候，弹起鼠标，bar也会移动
    window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();

    }

}
document.onmouseup = function(){
  document.onmousemove = null; //弹起鼠标不做任何操作
  waterMarkRefresh()
}
// 下拉修改数值
function dragInput(percent){
    percent = barWidth*( percent / 100 );
    mask.style.width = percent +'px' ;
    bar.style.left = percent + "px";
}
$(".watermark_configuration_alpha_adjust>span").click(function(){
    var txt = parseInt($(this).parent().siblings("span").text());
    if($(this).hasClass("watermark_configuration_alpha_adjust_upBtn")){
        txt = txt + 10;
        if(txt > 100) txt = 100;
    }else{
        if(txt >= 10) txt = txt - 10;
    }
    $(this).parent().siblings("span").text(txt);
    $(this).parents(".watermark_configuration_alpha_input_box").siblings(".watermark_configuration_alpha_btn").children("span").text(txt);
    // $(".watermark_configuration_alpha_btn").children("span").text(txt+"%");
    if($(this).parent().hasClass("drop")){
        dragInput(txt);
    }
    waterMarkRefresh()
})
// 自定义水印角度
$(".watermark_configuration_alpha_adjust_Btn").click(function(){
    $(this).parents(".watermark_configuration_scale_box").children(".watermark_config_tab.watermark_configuration_box_composing").children("li").removeClass("active");
})

$(".watermark_configuration_alpha_number").click(function(){
    waterMarkRefresh()
})
$(".scale_list>li").click(function(){
    var watermarkScale = $(this).attr("data-scale");
    if($(this).index() == 0){
        $(".scale_list").parent(".watermark_configuration_scale_box").find(".watermark_configuration_alpha_btn").children("span").text(30);
        $(".watermark_configuration_alpha_input_box.scale").children(".watermark_configuration_alpha_number").children("span").text(30);
    }else if($(this).index() == 1){
        $(".scale_list").parent(".watermark_configuration_scale_box").find(".watermark_configuration_alpha_btn").children("span").text(45);
        $(".watermark_configuration_alpha_input_box.scale").children(".watermark_configuration_alpha_number").children("span").text(45);
    }
    else if($(this).index() == 2){
        $(".scale_list").parent(".watermark_configuration_scale_box").find(".watermark_configuration_alpha_btn").children("span").text(60);
        $(".watermark_configuration_alpha_input_box.scale").children(".watermark_configuration_alpha_number").children("span").text(60);
    }
    else if($(this).index() == 3){
        $(".scale_list").parent(".watermark_configuration_scale_box").find(".watermark_configuration_alpha_btn").children("span").text(90);
        $(".watermark_configuration_alpha_input_box.scale").children(".watermark_configuration_alpha_number").children("span").text(90);
    }
    waterMarkRefresh()
})
// 显示修改透明度弹窗
$(".watermark_configuration_alpha_btn").click(function(e){
    e?e.stopPropagation():event.cancelBubble = true;
    if($(this).parent().hasClass("hide")){
        $(this).parent().removeClass("hide");
    }else{
        $(this).parent().addClass("hide");
        $(".watermark_configuration_alpha_number").removeClass("active");
    }
})
$(".watermark_configuration_alpha_number").click(function(){
    if(!$(this).hasClass("active")){
        $(this).addClass("active")
    }
})
$(".watermark_configuration_alpha_input_box,.watermark_configuration_custom_btn").click(function(e){
    e?e.stopPropagation():event.cancelBubble = true;
})
// 自定义横纵轴坐标
$(".watermark_configuration_custom_unedited").on("click",function(e){
    e?e.stopPropagation():event.cancelBubble = true;
    if(!$(this).parent(".watermark_configuration_custom_btn").hasClass("editing")){
        $(this).parent(".watermark_configuration_custom_btn").addClass("editing")
        $(".watermar_editing_horizon_box_input").val(jsonArr.compose.horizontalCoord)
        $(".watermar_editing_vertical_box_input").val(jsonArr.compose.verticalCoord)
    }
})
// 坐标取值范围 0~3440
var minCoords = -1;
var maxCoords = 3440;
$(".watermar_editing_horizon_box_input,.watermar_editing_vertical_box_input").bind("input propertychange",function(){
    if($(this).val() <= minCoords || !$(this).val())
        $(this).val("0");
    else if($(this).val() >= maxCoords) $(this).val(maxCoords);
})
function getCoords(horizonInput,verticalInput){
    if(horizonInput && verticalInput){
        $(".watermark_configuration_coords_horizon>em").text(horizonInput);
        $(".watermark_configuration_coords_vertical>em").text(verticalInput);
        $(this).parents(".coords_content").click();
    }
}
// 取消输入坐标
$(".watermar_editing_operation_cancel").click(function(){
    $(".watermark_configuration_custom_btn").removeClass("editing")
})
// 确认输入坐标
$(".watermar_editing_operation_confirm").click(function(){
    var horizonInput = $(".watermar_editing_horizon_box_input").val();
    var verticalInput = $(".watermar_editing_vertical_box_input").val();
    if(horizonInput && verticalInput){
        $(".watermark_configuration_coords_horizon>em").text(horizonInput);
        $(".watermark_configuration_coords_vertical>em").text(verticalInput);
        $(this).parents(".coords_content").click();
    }
    waterMarkRefresh()
})
$(".coords_content>.muiPerformanceDropdownBoxList>ul>li").click(function(){
    var horizonListInput = $(this).children(".watermark_coords_horizon_num").text();
    var verticaListlInput = $(this).children(".watermark_coords_vertical_num").text();
    getCoords(horizonListInput,verticaListlInput);
})
// 水印字体加粗按钮
$(".watermark_configuration_fontBold_box").click(function(){
    $(this).toggleClass("active");
    if($(this).hasClass("active")){
        jsonArr.compose.fontBold = "bold";
    }else {
        jsonArr.compose.fontBold = "normal";
    }
    waterMarkRefresh();
})




// 读取JSON
var jsonList;
var targetInput = $("#waterMarkJsonTd").find("input").val();
if(targetInput){
    jsonList = $("#waterMarkJsonTd").find("input").val();
}else{
    jsonList = '{\n' + '    "content": {\n' + '        "dept":{\n' + '            "order":1,\n' + '            "label":"部门",\n' + '            "value": "例：财务部",\n' + '            "deleted":false,\n' + '            "editable":false\n' + '        },\n' + '        "loginName":{\n' + '            "order":2,\n' + '            "label":"登录名",\n' + '            "value": "例：admin",\n' + '            "deleted":false,\n' + '            "editable":false\n' + '        },\n' + '        "name":{\n' + '            "order":3,\n' + '            "label":"姓名",\n' + '            "value": "例：韩诗文",\n' + '            "deleted":false,\n' + '            "editable":false\n' + '        },\n' + '        "number":{\n' + '            "order":4,\n' + '            "label":"员工编号",\n' + '            "value": "例：10086",\n' + '            "deleted":true,\n' + '            "editable":false\n' + '        },\n' + '        "phoneNumber":{\n' + '            "order":5,\n' + '            "label":"手机号",\n' + '            "value": "例：16620855507",\n' + '            "deleted":true,\n' + '            "editable":false\n' + '        },\n' + '        "date":{\n' + '            "order":6,\n' + '            "label":"日期",\n' + '            "value": "例：2021-10-26",\n' + '            "deleted":true,\n' + '            "editable":false\n' + '        },\n' + '        "time":{\n' + '            "order":7,\n' + '            "label":"时间",\n' + '            "value": "例：16:41",\n' + '            "deleted":true,\n' + '            "editable":false\n' + '        },\n' + '        "IP":{\n' + '            "order":8,\n' + '            "label":"IP地址",\n' + '            "value": "例：192.108.2.2",\n' + '            "deleted":true,\n' + '            "editable":false\n' + '        },\n' + '        "constant":{\n' + '            "order":9,\n' + '            "label":"常量",\n' + '            "value": "例：蓝凌软件",\n' + '            "deleted":true,\n' + '            "editable":true\n' + '        }\n' + '    },\n' + '    "compose": {\n' + '        "cover": true,\n' + '        "horizontalCoord": "30",\n' + '        "verticalCoord": "35",\n' + '        "scale": "30",\n' + '        "fontFamily": "微软雅黑",\n' + '        "fontSize": "14",\n' + '        "fontBold": false,\n' + '        "fontColor": "#333333",\n' + '        "alpha": "10"\n' + '    }\n' + '}';
}

var jsonArr = JSON.parse(jsonList);
var arr = [];
var viewArr = [];
for (let key in jsonArr.content) {
    if(jsonArr.content[key].deleted == "false" || jsonArr.content[key].deleted == false){
        viewArr.push(jsonArr.content[key].value);
    }
}
for (let key in jsonArr.content) {
    arr.push(jsonArr.content[key].value);
}
function renderData(){
    $(".watermark_configuration_icon_list>.watermark_configuration_icon_delete").unbind("click");
    // 渲染水印内容列表-弹窗
    $(".watermark_configuration_drop_item").remove();
    for(let key in jsonArr.content){
        var popLiDom = "\n            <li class=\"watermark_configuration_list_pop_item\" data-name=".concat(key, " data-order=").concat(jsonArr.content[key].order, " data-label=").concat(jsonArr.content[key].label, " data-deleted=").concat(jsonArr.content[key].deleted, " data-editable=").concat(jsonArr.content[key].editable, ">\n                <label class=\"muiPerformanceManagementCheckbox\">\n                    <input type=\"checkbox\" checked=\"checked\">\n                    <em>").concat(jsonArr.content[key].label, "</em>\n                    <span></span>\n                </label>\n            </li>\n        ");
        var popLiDomDel = "\n            <li class=\"watermark_configuration_list_pop_item\" data-name=".concat(key, " data-order=").concat(jsonArr.content[key].order, " data-label=").concat(jsonArr.content[key].label, " data-deleted=").concat(jsonArr.content[key].deleted, " data-editable=").concat(jsonArr.content[key].editable, ">\n                <label class=\"muiPerformanceManagementCheckbox\">\n                    <input type=\"checkbox\">\n                    <em>").concat(jsonArr.content[key].label, "</em>\n                    <span></span>\n                </label>\n            </li>\n        ");
        if(jsonArr.content[key].deleted == false || jsonArr.content[key].deleted == 'false'){
            $(".watermark_configuration_list_pop").append(popLiDom);
        }else{
            $(".watermark_configuration_list_pop").append(popLiDomDel);
        }
    }
    // 渲染水印内容列表-展示
    for(let key in jsonArr.content){
        var viewLiDom = "                                \n        <li class=\"watermark_configuration_drop_item list-group-item nested-item\" data-name=".concat(key, " data-order=").concat(jsonArr.content[key].order, " data-label=").concat(jsonArr.content[key].label, " data-deleted=").concat(jsonArr.content[key].deleted, " data-editable=").concat(jsonArr.content[key].editable, ">\n            <i class=\"mui_watermark_icon_drop\"></i>\n            <strong>").concat(jsonArr.content[key].label, "</strong>\n            <input type=\"text\" class=\"watermark_configuration_input\" readonly=\"readonly\" value=").concat(jsonArr.content[key].value, ">\n            <div class=\"watermark_configuration_icon_list\">\n                <i class=\"watermark_configuration_icon_delete\"></i>\n            </div>\n        </li>");
        if(jsonArr.content[key].deleted == false || jsonArr.content[key].deleted == 'false'){
            $(".watermark_configuration_box_list").append(viewLiDom);
        }
        if(jsonArr.content[key].editable){
            $(".watermark_configuration_box_list").find("[data-name='"+key+"']").find(".watermark_configuration_icon_delete").before("<i class=\"watermark_configuration_icon_edit\"></i>")
        }
    }
    //排版方式
    if(!jsonArr.compose.cover){
        $(".watermark_configuration_box_composing.watermark_cover>li").eq(0).removeClass("active")
        $(".watermark_configuration_box_composing.watermark_cover>li").eq(1).addClass("active")
    }
    //横纵坐标
    if(jsonArr.compose.horizontalCoord){
        $(".watermark_configuration_coords_horizon").children("em").text(jsonArr.compose.horizontalCoord)
    }
    if(jsonArr.compose.verticalCoord){
        $(".watermark_configuration_coords_vertical").children("em").text(jsonArr.compose.verticalCoord)
    }
    //水印角度
    if(jsonArr.compose.scale){
        $(".watermark_configuration_box_composing.scale_list>li").removeClass("active")
        if(jsonArr.compose.scale==30){
            $(".watermark_configuration_box_composing.scale_list>li").eq(0).addClass("active");
            $(".watermark_configuration_scale_box .watermark_configuration_alpha_btn span").text(30)
        }else if(jsonArr.compose.scale==45){
            $(".watermark_configuration_box_composing.scale_list>li").eq(1).addClass("active");
            $(".watermark_configuration_scale_box .watermark_configuration_alpha_btn span").text(45)
        }
        else if(jsonArr.compose.scale==60){
            $(".watermark_configuration_box_composing.scale_list>li").eq(2).addClass("active");
            $(".watermark_configuration_scale_box .watermark_configuration_alpha_btn span").text(60)
        }
        else if(jsonArr.compose.scale==90){
            $(".watermark_configuration_box_composing.scale_list>li").eq(3).addClass("active");
            $(".watermark_configuration_scale_box .watermark_configuration_alpha_btn span").text(90)
        }
        $(".watermark_configuration_scale_box .watermark_configuration_alpha_btn span").text(jsonArr.compose.scale)
    }
    //字体
    if(jsonArr.compose.fontFamily){
        $(".watermark_configuration_font_box.muiPerformanceDropdownBox").children("span").text(jsonArr.compose.fontFamily)
    }
    //字体大小
    if(jsonArr.compose.fontSize){
        $(".watermark_configuration_fontSize_box").children("span").text(jsonArr.compose.fontSize)
    }
    //字体加粗
    if(jsonArr.compose.fontBold == "bold"){
        $(".watermark_configuration_fontBold_box").addClass("active")
    }
    //字体颜色
    if(jsonArr.compose.fontColor){
        window.SpectrumColorPicker.setColor("colorColor",jsonArr.compose.fontColor);
    }
    //水印透明度
    if(jsonArr.compose.alpha){
        $(".watermark_configuration_alpha_btn.drop").children("span").text(jsonArr.compose.alpha);
        $(".watermark_configuration_alpha_input_box.drop>.watermark_configuration_alpha_number>span").text(jsonArr.compose.alpha);
        dragInput(jsonArr.compose.alpha);
    }
    // 修改水印名称
    $('.watermark_configuration_icon_edit').on("click",function(){
        var tarInput = $(this).parent('.watermark_configuration_icon_list').siblings('.watermark_configuration_input');
        var txt = tarInput.val();
        if (txt == "请输入"){
            $(tarInput).attr("readonly",false).focus().addClass("input_iditing").val(" ");
        }else{
            $(tarInput).attr("readonly",false).focus().addClass("input_iditing").val(txt);
        }
    })
    // 修改水印名称-输入框失去焦点
    $('.watermark_configuration_input').bind("blur",function(){
        var txt= $(this).val();
        var idx = $(this).parent(".watermark_configuration_drop_item").index();
        if(txt == " ") $(this).val(txt);
        $(this).attr("readonly",true).removeClass("input_iditing");
        $(".watermark_configuration_list_pop>li").eq(idx).find("span").text(txt);
        waterMarkRefresh();
    })

    //删除水印内容
    $(".watermark_configuration_icon_list>.watermark_configuration_icon_delete").bind("click", function(){
        var name = $(this).parent(".watermark_configuration_icon_list").parent(".watermark_configuration_drop_item").attr("data-name");
        for (let key in jsonArr.content){
            if(key == name){
                jsonArr.content[key].deleted = true;
                break;
            }
        }
        $(".watermark_configuration_list_pop>li").each(function(){
            if($(this).attr("data-name")==name){
                $(this).find("input").attr("checked",false)
            }
        })
        setTimeout(function(){
            renderData();
            waterMarkRefresh();
        },100)
    })
}
function waterMarkConfigUpdate(){
    var keys =  $(".watermark_configuration_box_list").find("li");
    for (var i = 0; i < keys.length; i++) {
        var nameValue = $(keys[i]).attr("data-name");
        var orderValue = $(keys[i]).attr("data-order");
        var labelValue = $(keys[i]).attr("data-label");
        var value = $(keys[i]).find(".watermark_configuration_input").val();
        var deletedValue = $(keys[i]).attr("data-deleted");
        var editableValue = $(keys[i]).attr("data-editable");
        jsonArr.content[nameValue] = {
            "order": orderValue,
            "label":labelValue,
            "value": value,
            "deleted":deletedValue,
            "editable":editableValue
        }
    }
    if($(".watermark_configuration_box_cover").hasClass("active")){
        jsonArr.compose.cover = true
    }else{
        jsonArr.compose.cover = false
    }
    jsonArr.compose.horizontalCoord = $(".watermark_configuration_coords_horizon").children("em").text();
    jsonArr.compose.verticalCoord = $(".watermark_configuration_coords_vertical").children("em").text();
    jsonArr.compose.scale = $(".scale_list+.watermark_configuration_alpha_btn").children("span").text();
    jsonArr.compose.fontFamily = $(".watermark_configuration_font_box.muiPerformanceDropdownBox").children("span").text();
    jsonArr.compose.fontSize = $(".watermark_configuration_fontSize_box").children("span").text();
    if($(".watermark_configuration_fontBold_box").hasClass("active")){
        jsonArr.compose.fontBold = "bold"
    }else {jsonArr.compose.fontBold = "normal"}
    jsonArr.compose.fontColor = $(".colorText").text();
    jsonArr.compose.alpha = $(".watermark_configuration_alpha_input_box.drop .watermark_configuration_alpha_number").children("span").text();
    $("#waterMarkJsonTd").find("input").val(JSON.stringify(jsonArr));


}

watermarkParam = {
    context: viewArr,//水印内容
    x_space: parseInt(jsonArr.compose.horizontalCoord),//水印起始位置x轴坐标
    y_space: parseInt(jsonArr.compose.verticalCoord),//水印起始位置Y轴坐标
    color: jsonArr.compose.fontColor,//水印字体颜色
    alpha: (jsonArr.compose.alpha)/100,//水印透明度
    fontsize: (jsonArr.compose.fontSize).toString()+"px",//水印字体大小
    font: jsonArr.compose.fontFamily,//水印字体
    angle: parseInt(jsonArr.compose.scale),//水印倾斜度数
    fontbold: jsonArr.compose.fontBold//水印字体加粗
};
var tarDom = $(".watermark_config_viewer_simulation.computer");
var waterMarkObj = new WaterMark(watermarkParam,tarDom);
var tarDoms = $(".watermark_config_viewer_simulation.mobile");
var waterMarkmObj = new WaterMark(watermarkParam,tarDoms);
function updateMark(){
    waterMarkObj.render();
    waterMarkmObj.render();
}
function waterMarkRefresh(){
    waterMarkObj.isRender = false;
    waterMarkmObj.isRender = false;
    waterMarkConfigUpdate();
    arr = [];
    viewArr = [];
    for (let key in jsonArr.content) {
        if(jsonArr.content[key].deleted == "false" || jsonArr.content[key].deleted == false){
            viewArr.push(jsonArr.content[key].value);
        }
    }
    for (let key in jsonArr.content) {
        arr.push(jsonArr.content[key].value);
    }
    var opts = {
            context: viewArr,//水印内容
            x_space: parseInt(jsonArr.compose.horizontalCoord),//水印起始位置x轴坐标
            y_space: parseInt(jsonArr.compose.verticalCoord),//水印起始位置Y轴坐标
            color: jsonArr.compose.fontColor,//水印字体颜色
            alpha: (jsonArr.compose.alpha)/100,//水印透明度
            fontsize: (jsonArr.compose.fontSize).toString()+"px",//水印字体大小
            font: jsonArr.compose.fontFamily,//水印字体
            angle: parseInt(jsonArr.compose.scale),//水印倾斜度数
            fontbold: jsonArr.compose.fontBold//水印字体加粗
        };
    var optsMobile = {
        context: viewArr,//水印内容
        x_space: 0,//水印起始位置x轴坐标
        y_space: 50,//水印起始位置Y轴坐标
        color: jsonArr.compose.fontColor,//水印字体颜色
        alpha: (jsonArr.compose.alpha)/100,//水印透明度
        fontsize: (jsonArr.compose.fontSize).toString()+"px",//水印字体大小
        font: jsonArr.compose.fontFamily,//水印字体
        angle: parseInt(jsonArr.compose.scale),//水印倾斜度数
        fontbold: jsonArr.compose.fontBold,//水印字体加粗
        width: 140,//水印宽度
        height: 80,//水印长度
    };
    for (key in opts) {
        waterMarkObj.opts[key] = opts[key];
    }
    for (key in optsMobile) {
        waterMarkmObj.opts[key] = optsMobile[key];
    }

    waterMarkObj.refresh();
    waterMarkmObj.refresh();
}
LUI.ready(function() {
    renderData();
    updateMark();
    //添加水印
    $(".watermark_configuration_list_pop>li input").on("click", function(){
        var name= $(this).parent('label').parent('li').attr('data-name');
        var checked = $(this).prop("checked");
        for (var keys in jsonArr.content){
            if(keys == name){
                jsonArr.content[keys].deleted = !checked;
                break;
            }
        }
        renderData();
        waterMarkRefresh();
    })
    // 关联水印内容列表
    $(".watermark_configuration_list_pop>li input").on("click", function(){
        var idx = $(this).parent(".muiPerformanceManagementCheckbox").parent("li").index();
        var idxName = $(this).parent(".muiPerformanceManagementCheckbox").parent("li").attr("data-name");
    })
});
