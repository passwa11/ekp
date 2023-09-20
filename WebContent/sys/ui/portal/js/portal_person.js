var dataView = render.parent;
var personTyle = typeof (param.personTyle) != 'undefined'
&& param.personTyle == 'expert' ? param.personTyle : 'person';
var columnNum = isNaN(render.vars.columnNum) ? 1
    : parseInt(render.vars.columnNum);
var li_weight = parseInt(100 / columnNum);
if (render.preDrawing) { // 预加载执行
    sendMessage();
    return;
}
if (data == null || data.length == 0) {
    done();
    return;
}



var con_div = $('<div/>').attr('class', 'lui-person-n-phone np-kk').appendTo(
    dataView.element);

var con_ul = $('<ui/>').attr('class', 'lui-person-n-phone-wrap').appendTo(con_div);
con_ul.attr('style', 'list-style:none');
seajs.use( [ 'lang!sys-zone' ], function(zone_lang) {
    for (var i = 0; i < data.length; i++) {
        var con_li = $('<li/>').appendTo(con_ul);
        con_li.attr('style', 'width:' + li_weight + '%');
        var inner_div = $('<a/>').attr('class', 'lui-person-n-phone-item').appendTo(con_li);
        var inner_ul = $('<ui/>').attr('class', 'lui-person-n-phone-item-wrap').appendTo(
            inner_div);
        addLiInfo(data[i], inner_ul,zone_lang );
        var itemId = data[i]['usrid'];
        var showExtend = data[i]['showextend'];
        var hadKK = data[i]['hadkk'];

        var askH = zone_lang['sysZonePerson.prolet.askT']; // 问问TA
        // 专家专有右侧提问按钮
        if (personTyle == 'expert') {
            // 定义专家页面跳转、提问按钮显示以及提问按钮点击功能
            if (showExtend && 'true' == showExtend) {
                // 判断是否显示扩展部分，这里依据有没有爱问模块扩展按钮
                var ask_div = $('<div/>').attr('class', 'lui-person-n-phone-item-slide')
                    .appendTo(inner_div);
                ask_div.text(""+askH+"")
                var fdextendlink = data[i]['fdextendlink'];
                if (fdextendlink) {
                    ask_div.attr('onclick', "Com_OpenWindow('" + fdextendlink
                        + "', '_blank')");

                }
            }

            inner_div.hover(function () {
                $(this).find(".lui-person-n-phone-item-slide").toggleClass("active");
            })

        } else {
            //#122434 【服务问题单】【门户管理-优化】门户配置-讲师管理中，人员信息默认列表展示，显示的KK图标是固定的，客户没有使用kk
            if(hadKK && 'true' == hadKK){
                // 右侧KK小图标（员工列表）
                if (showExtend && 'true' == showExtend) {
                    var inner_li_3 = $('<li/>').attr('class', 'lui-person-n-phone-item-icon')
                        .appendTo(inner_ul);
                    inner_li_3.attr('onclick', "callKKView('" + itemId + "')")

                }else{
                    var inner_li_3 = $('<li/>').attr('style', 'float:right;width:50px;height:50px')
                        .appendTo(inner_ul);
                    inner_li_3.attr('onclick', "callKKView('" + itemId + "')")
                }
            }
        }

        inner_div.attr('onclick', "gotoPersonPage('" + data[i]['fdcontentlink']
            + "',event)");
    }
});


function addLiInfo(item, innerUl, zone_lang) {
    // 头像区
    var inner_li_1 = $('<li/>').attr('class', 'lui-person-n-phone-item-head').appendTo(
        innerUl);
    var img = $('<img/>').attr('src', item['imgurl']).appendTo(inner_li_1);

    // 右侧内容区
    var inner_li_2 = $('<li/>').attr('class', 'lui-person-n-phone-item-desc').appendTo(
        innerUl);
    // 主信息栏
    var li_div_top = $('<div/>').attr('class', 'lui-person-npid-top').appendTo(inner_li_2);
    var li_name = $('<p/>').attr('class', 'lui-person-npid-top-name').appendTo(li_div_top);
    li_name.text(item['name']);
    // 描述内容栏
    var li_div_bom = $('<div/>').attr('class', 'lui-person-npid-bottom').attr('style',
        'height:22px;line-height:22px;margin-top:5px').appendTo(inner_li_2);
    // 根据人员类型添加副栏和描述栏内容
    addAccessoryInfo(personTyle, li_div_top, item, li_div_bom, zone_lang);
}

function addAccessoryInfo(personTyle, domInfo, item, bottomInfo, zone_lang) {
    var bom_con = $('<p/>').attr('class', 'lui-person-npid-bottom-desc').appendTo(
        bottomInfo);

    var post = zone_lang['sysZonePerson.post']; // 岗位
    post = post + ": ";
    var jtly = zone_lang['sysZonePerson.JTLY']; // 精通领域
    jtly = jtly + ": ";

    var typeObj = zone_lang['sysZonePerson.prolet.type'];// 分类
    typeObj = typeObj + ": ";

    var scly = zone_lang['sysZonePerson.prolet.SCLY']; // 擅长领域
    scly = scly + ": ";

    if (personTyle == 'expert') {
        // 专家：注入部门、岗位信息
        var tagCss = {
            "width":"100%",
            "clear":"both",
            "font-size": "10px",
            "padding": "3px 0px",
            "margin-right": "10px",
            "line-height": "1em",
            "white-space": "nowrap",
            "overflow": "hidden",
            "text-overflow": "ellipsis",
        };
        if (item['deptinfo'] != "" && item['deptinfo'] != null) {
            var name_span1 = $('<p/>').css(tagCss).css({ "color": "#ffa26a"})
                .appendTo(domInfo);
            name_span1.text(item['deptinfo']);
        }

        if (item['postinfo'] != "" && item['postinfo'] != null) {
            var name_span2 = $('<p/>').css(tagCss).css({
                "color": "#22b77d",
                "margin-top": "10px"
            }).appendTo(domInfo);
            name_span2.text(item['postinfo']);
        }
        var bomInfo = jtly + item['taginfo'];
        bom_con.css(tagCss).text(bomInfo);
    } else {
        console.log(item);
        var currentPersonType = item['persontype'] || "person";
        if(currentPersonType == "lecturer"){
            // 讲师：注入电话信息
            var tel_info = $('<p/>').attr('class', 'lui-person-npid-top-num')
                .appendTo(domInfo);
            tel_info.text(item['telinfo']);
            var tag_span_1 = $('<span/>').attr('class', 'lui-person-npid-bottom-desc-tit')
                .appendTo(bom_con);
            tag_span_1.text(""+typeObj+"");
            $(bom_con[0]).css({
                "width":"100%",
                "overflow": "hidden",
                "text-overflow": "ellipsis",
                "white-space": "nowrap",
                "font-size": "12px",
                "color": "#999999"
            });

            var tag_span_2 = $('<span/>').attr('class', 'lui-person-npid-bottom-desc-content')
                .appendTo(bom_con);
            tag_span_2.text(item['taginfo']);
            tag_span_2.attr("title",item['taginfo']);

            var bom_con_2 = $('<p/>').attr('class', 'lui-person-npid-bottom-desc').appendTo(
                bottomInfo);
            var tag_span_5 = $('<span/>').attr('class', 'lui-person-npid-bottom-desc-tit')
                .appendTo(bom_con_2);
            tag_span_5.text(""+scly+"");
            var tag_span_6 = $('<span/>').attr('class', 'lui-person-npid-bottom-desc-content')
                .appendTo(bom_con_2);
            tag_span_6.text(item['areainfo']);
            tag_span_6.attr("title",item['areainfo']);
            $(bom_con_2[0]).css({
                "width":"100%",
                "overflow": "hidden",
                "text-overflow": "ellipsis",
                "white-space": "nowrap",
                "font-size": "12px",
                "color": "#999999"
            });

            var bom_con_1 = $('<p/>').attr('class', 'lui-person-npid-bottom-desc').appendTo(
                bottomInfo);
            var tag_span_3 = $('<span/>').attr('class', 'lui-person-npid-bottom-desc-tit')
                .appendTo(bom_con_1);
            var tag_span_4 = $('<span/>').attr('class', 'lui-person-npid-bottom-desc-content')
                .appendTo(bom_con_1);
            tag_span_3.text(post);
            tag_span_4.text(item['postinfo']);
            $(bom_con_1[0]).css({
                "width":"100%",
                "overflow": "hidden",
                "text-overflow": "ellipsis",
                "white-space": "nowrap",
                "font-size": "12px",
                "color": "#999999"
            });
        }else{
            // 普通用户：注入电话信息
            var tel_info = $('<p/>').attr('class', 'lui-person-npid-top-num')
                .appendTo(domInfo);
            tel_info.text(item['telinfo']);
            var tag_span_1 = $('<span/>').attr('class', 'lui-person-npid-bottom-desc-tit')
                .appendTo(bom_con);
            bom_con.text(jtly + item['taginfo']);
            $(bom_con[0]).css({
                "width":"100%",
                "overflow": "hidden",
                "text-overflow": "ellipsis",
                "white-space": "nowrap",
                "font-size": "12px",
                "color": "#999999"
            });

            var bom_con_1 = $('<p/>').attr('class', 'lui-person-npid-bottom-desc').appendTo(
                bottomInfo);
            bom_con_1.text(post + item['postinfo']);
            $(bom_con_1[0]).css({
                "width":"100%",
                "overflow": "hidden",
                "text-overflow": "ellipsis",
                "white-space": "nowrap",
                "font-size": "12px",
                "color": "#999999"
            });
        }
    }
}

// 打开个人页面
window.gotoPersonPage = function (fdLink, event) {
    if (!fdLink || fdLink == null || fdLink.trim() == "") {
        return;
    }
    var goalConClass = event.target.className;
    if (goalConClass != null
        && (goalConClass.indexOf('lui-person-n-phone-item-slide') > -1 || goalConClass
            .indexOf('lui-person-n-phone-item-icon') > -1)) {
        return;
    }
    Com_OpenWindow(fdLink, '_blank');
}

// 打开KK
window.callKKView = function (itemId) {
    var url = Com_Parameter.ContextPath + "third/im/kk/user.do?method=getLoginNameByUserId";
    $.ajax({
        type : "post",
        url : url,
        data : {
            "fdId" : itemId
        },
        async : false,
        dataType : "json",
        success : function (results, textStatus, jqXHR) {
            console.log(results);
            if (results != null) {
                var url = "landray.kk:${loginName}@p2p/open?user="
                    + results.loginName;
                console.log(url);
                window.open(url, "_parent");
            }
        },
        error : function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("获取用户登录名失败，请检查网络！");
        }
    });
}

function sendMessage() {
    if (data != null && data.length > 0 && newDay > 0) {
        var more = false;
        for (var i = 0; i < data.length; i++) {
            if (showNewIcon(data[i])) {
                more = true;
                break;
            }
        }
        if (more) {
            if (dataView && dataView.parent) {
                var countInfo = {};
                countInfo.more = true;
                dataView.parent.emit('count', countInfo);
            }
        }
    }
}
done();
