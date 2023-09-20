if (data == null || data.length == 0) {
    done();
    return;
}

//部件容器
var container = $('<div class="kms_common_left_first_pic_wrap">');
//列表容器
var listContainer = $('<div class="kms_common_left_first_pic_data_list_warp">');

/* 列表数据处理
 * data json格式
 * href 文档跳转链接
 */
for (var i = 0; i < data.length; i++) {
    var item = data[i];
    console.log("item",item);
    //跳转页面
    var href=render.env.fn.formatUrl(item.portalHref);
    var docSubject=item.portalDocSubject;
    var time=item.portalTime;
    if(i<1){
        //首行封面地址
        var firstItemPic=render.env.fn.formatUrl(item.portalImgUrl);
        var firstItemPicHtml='<div class="kms_common_left_first_pic_img_warp" onclick="Com_OpenNewWindow(this)" data-href="'+href+'">' +
                             '        <img class="kms_common_left_first_pic_img" src="'+firstItemPic+'">' +
                             '    </div>';
        $(firstItemPicHtml).appendTo(container);
    }
    var itemHtml=   '<a class="kms_common_left_first_pic_data_item" onclick="Com_OpenNewWindow(this)" data-href="'+href+'">' +
                    '            <div class="kms_common_left_first_pic_data_item_title">'+docSubject+'</div>\n' +
                    '            <div class="kms_common_left_first_pic_data_item_time">'+time+'</div>\n' +
                    '        </a>';
    $(itemHtml).appendTo(listContainer);
}
listContainer.appendTo(container);
//渲染dom
done(container);