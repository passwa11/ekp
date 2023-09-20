// 计算布局宽度
function calcWidth(){
    var docWidth = document.body.clientWidth;
    var docHeight = document.body.clientHeight;
    var leftContentWidth = docWidth - 250;
    $(".lui_chattingRecords_left_content").css("width",leftContentWidth);
    $(".lui_chattingRecords_left_content").children(0).css("min-height","200px");
    $(".lui_chattingRecords_right_content").css("height",docHeight - 64);
    $(".lui_chattingRecords_mask").css("width",docWidth).css("height",docHeight);
    $(".lui_chattingRecords_detail_content").css("height",docHeight - 164);
    $(".lui_chattingRecords_item").each(function(){
        var numWidth = $(this).find(".lui_chattingRecords_item_num").width();
        $(this).find(".lui_chattingRecords_item_chatcontent_detail").css("width", (leftContentWidth / 2 ) - 100 - numWidth);
    })
}
// 切换选中状态
// $(".lui_chattingRecords_sidebar_type_select>li,.lui_chattingRecords_detail>li").click(function(){
//     if(!$(this).hasClass("active")){
//         $(this).addClass("active").siblings().removeClass("active");
//     }
// })
// 侧边栏显示隐藏
$(".lui_chattingRecords_item").click(function(){
    $(".lui_chattingRecords_mask").show().on("click", function(){
        $(".lui_chattingRecords_mask").hide();
        $(".lui_chattingRecords_mask_content").css("transform","translateX(100%)");
    });
    $(".lui_chattingRecords_mask_content").css("transform","translateX(0)");
})
$(".lui_chattingRecords_mask_close").on("click", function(){
    $(".lui_chattingRecords_mask").hide();
    $(".lui_chattingRecords_mask_content").css("transform","translateX(100%)");
})
// 文本超出后展开收起按钮
$(function() {
    var collapseDefaultContent = '... 展开';
    var collapseActiveContent = '收起';
    Array.from($('.lui_chattingRecords_item_type_text')).forEach(function(v, i) {
        // 2: 超过两行省略
        if (v.clientHeight > (1.5 * 14 * 2)) {
        var el = document.createElement('div');
        el.innerHTML = collapseDefaultContent;
        el.className = 'collapse';
        v.appendChild(el);
        // multi 是显示溢出的标志
        $(v).addClass('multi ellipsis');
        }
    })
    $('.multi').on('click', '.collapse', function() {
        var $this = $(this)
        var $parent = $this.closest('.multi')
        if($parent.hasClass('ellipsis')) {
        $parent.removeClass('ellipsis');
        $this.html(collapseActiveContent);
        } else {
        $parent.addClass('ellipsis');
        $this.html(collapseDefaultContent);
        }
    })
})
$(document).ready(function(){
    //calcWidth();
    //$(".lui_chattingRecords_item").click();
})
// 兼容IE
$(window).load(function(){
   // calcWidth();
   // $(".lui_chattingRecords_item").click();
})
$(window).resize(function(){
    calcWidth();
})