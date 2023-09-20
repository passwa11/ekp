<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="lui_portal_header" style="">


























    <script type="text/javascript">
        var userName = '冯友友';
        var loginName = 'admin';
        var dept = '产品研发中心';
        var telphone = '15968798079';
        var email = '';
        var title = '前端工程师';
        var licenseId = '';
    </script>

    <div class="lui_portal_header_simple_frame_h"></div>
    <div class="lui_portal_header_simple_frame">

        <div class="lui_portal_head_notice" style="display: none;">
            <div class="lui_portal_head_notic_box">
                <div style="width:1100px;overflow:hidden;height:20px;position:absolute;"><span
                        class="lui_portal_head_notic_content" style="left:0;"></span></div><i class="close">x</i>
            </div>
            <script>
                Com_IncludeFile("jquery.js", null, "js");
            </script>
            <script id="jquery_js" src="/ekp/resource/js/jquery.js?s_cache=1597654882776"></script>

            <script>
                $(function () {
                    var $luiPortalHeadNotice = $(".lui_portal_head_notice");
                    var $luiPortalHeaderZoneFrame = $('.lui_portal_header_zone_frame');
                    var $luiPortalHeaderZoneFrameH = $(".lui_portal_header_zone_frame_h");
                    $(".lui_portal_head_notice .close").click(function () {
                        $luiPortalHeadNotice.hide();
                        $luiPortalHeaderZoneFrameH.css({
                            height: ''
                        });
                        $luiPortalHeaderZoneFrame.removeClass('has-noticebar');
                    });
                    var url = Com_Parameter.ContextPath +
                        'sys/portal/sys_portal_notice/sysPortalNotice.do?method=getPortalNotice';
                    $.post(url, function (data) {
                        if (data.isShow == "1") {
                            $(".lui_portal_head_notice span").html(data.docContent);
                            $luiPortalHeadNotice.show();
                            $luiPortalHeaderZoneFrame.removeClass('has-noticebar').addClass(
                                'has-noticebar');
                            if ($luiPortalHeaderZoneFrame.height() > 0)
                                $luiPortalHeaderZoneFrameH.css({
                                    'height': $luiPortalHeaderZoneFrame.height() + 'px'
                                });

                            var noticContWidth = $(".lui_portal_head_notic_content").width();
                            if (noticContWidth >= 1000) {
                                $(".lui_portal_head_notic_content").css("left", "550px");
                                var timer = setInterval(function () {
                                    if ($(".lui_portal_head_notice").css("display") ===
                                        "none") {
                                        clearInterval(timer);
                                    }
                                    var compTime = 20;
                                    var runNum = Math.floor(compTime / (0.042));
                                    var speedPx = Math.floor(noticContWidth / runNum);
                                    var noticLeft = $(".lui_portal_head_notic_content")
                                        .position().left;
                                    if (noticLeft <= (0 - noticContWidth)) {
                                        $(".lui_portal_head_notic_content").css("left",
                                        "550px");
                                    } else {
                                        $(".lui_portal_head_notic_content").css("left", (
                                            noticLeft - speedPx) + "px");
                                    }
                                }, 42);
                            } else {
                                $(".lui_portal_head_notic_box").width(noticContWidth + 100);
                            }

                        } else {
                            $luiPortalHeadNotice.hide();
                        }
                    }, "json").error(function () {
                        $luiPortalHeadNotice.hide();
                    });
                });
            </script>
        </div>























        <script>
            window.__POPS_TARGET__ = "1183b0b84ee4f581bba001c47a78b2d9"
        </script>



        <script type="text/javascript">
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                window.setPersonDefaultPortal = function (fdPortalId, fdPortalName, obj) {
                    var $statusTarget = $(obj).find('.default-icon');
                    var flag;
                    if ($statusTarget.hasClass('default-icon-solid')) {
                        flag = false;
                    } else {
                        flag = true;
                    }
                    var url =
                        '/ekp/sys/portal/sys_portal_person_default/sysPortalPersonDefault.do?method=setDefault';
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: {
                            fdPortalId: fdPortalId,
                            fdPortalName: fdPortalName,
                            flag: flag
                        },
                        dataType: 'json',
                        success: function (result) {
                            if ($statusTarget.hasClass('default-icon-solid')) {
                                $statusTarget.removeClass('default-icon-solid');
                                $statusTarget.addClass('default-icon-line');
                                dialog.success("取消成功！");
                            } else {
                                $(".lui_dataview_treemenu_flat").find('.default-icon').each(
                                    function () {
                                        if ($(this).hasClass('default-icon-solid')) {
                                            $(this).removeClass('default-icon-solid');
                                            $(this).addClass('default-icon-line');
                                        }
                                    });
                                $statusTarget.removeClass('default-icon-line');
                                $statusTarget.addClass('default-icon-solid');
                                dialog.success("设置成功！");
                            }
                        },
                        error: function (result) {}
                    });
                };
            });
        </script>



        <div style="width: 1200px;min-width:980px;max-width:100%;" class="lui_portal_header_simple_content clearfloat">
            <div class="lui_portal_header_simple_portal">

                <div class="lui_portal_header_simple_portal_switch">
                    <div class="lui_portal_header_text" data-lui-switch-class="lui_portal_header_text_over">
                        切换门户
                        <div class="lui_icon_s lui_portal_header_icon_arrow"></div>

                    </div>
                </div>

            </div>
            <div class="lui_portal_header_simple_person">



                <div class="lui_portal_header_hitch">
                    <span> <a class="lui_portal_header_text" id="_qywechat">
                            <div class="lui_icon_s lui_icon_hitch"> </div>
                            我要报障
                        </a>
                    </span>
                    <div id="_qywechatimg"></div>
                </div>



                <div class="lui_portal_header_notify">
























                    <div class="lui_portal_header_text">
                        <div title="待审"
                            onclick="window.open('/ekp/sys/notify?dataType=todo#j_path=%2Fprocess','_blank')"
                            class="lui_portal_header_daiban_div">
                            <div class="lui_icon_s lui_icon_s_daiban" style="vertical-align: text-top;"></div>
                            <div id="__notify_daiban__" class="lui_portal_header_daiban">13</div>
                        </div>
                        <div title="待阅" onclick="window.open('/ekp/sys/notify?dataType=toview#j_path=%2Fread','_blank')"
                            class="lui_portal_header_daiyue_div">
                            <div class="lui_icon_s lui_icon_s_daiyue" style="vertical-align: text-top;"></div>
                            <div id="__notify_daiyue__" class="lui_portal_header_daiyue">11</div>
                        </div>
                    </div>
                    <script>
                        seajs.use(['lui/topic'], function (topic) {
                            LUI.ready(function () {
                                var refreshTime = parseInt();
                                if (isNaN(refreshTime) || refreshTime < 1) {
                                    refreshTime = 0;
                                }
                                var refreshNotify = function () {
                                    LUI.$.getJSON(Com_Parameter.ContextPath +
                                        "sys/notify/sys_notify_todo/sysNotifyTodo.do?method=sumTodoCount",
                                        function (json) {
                                            if (json != null) {
                                                LUI.$("#__notify_daiban__").html(json.type_1 ==
                                                    null ? 0 : json.type_1);
                                                LUI.$("#__notify_daiyue__").html(json.type_2 ==
                                                    null ? 0 : json.type_2);
                                            }
                                        });
                                    if (refreshTime > 0)
                                        window.setTimeout(refreshNotify, refreshTime * 1000 * 60);
                                };
                                refreshNotify();
                                topic.subscribe('portal.notify.refresh', function () {
                                    refreshNotify();
                                });
                            });
                        });
                    </script>
                </div>


                <div class="lui_portal_header_favorite">



                    <div id="__my_bookmark__" data-lui-switch-class="lui_portal_header_text_over"
                        class="lui_portal_header_text">
                        <i class="lui_icon_s lui_icon_s_collect"></i>
                        收藏
                        <div class="lui_portal_header_icon_arrow lui_icon_s"></div>
                    </div>



                </div>


                <div class="lui_portal_header_userinfo">







                    <div data-lui-switch-class="lui_portal_header_text_over" class="lui_portal_header_text"
                        onclick="window.open('/ekp/sys/person/sys_person_zone/sysPersonZone.do?method=view','_blank')">
                        <span class="lui_portal_header_welcome">欢迎您</span>&nbsp;
                        <span class="lui_portal_header_username">冯友友</span>
                        <div class="lui_icon_s lui_portal_header_icon_arrow"></div>

                    </div>

                </div>

            </div>
        </div>
        <div class="lui_portal_header_simple_menu_wrapper">
            <div style="width: 1200px;min-width:980px;max-width:100%;" class="lui_portal_header_simple_menu clearfloat">
                <div class="lui_portal_header_simple_logo">
                    <div class="lui_portal_header_logo_div" onclick="location.href='/ekp/'" title="返回首页"><img
                            class="lui_portal_header_logo_img"
                            src="/ekp/ui-ext/logo/170a8a15ef967458714669a482abe14e.png"></div>
                </div>
                <div class="lui_portal_header_simple_switch">





                    <div data-lui-type="lui/base!DataView" style="" id="lui-id-13" class="lui-component"
                        data-lui-cid="lui-id-13" data-lui-parse-init="12">
                        <div class="lui_portal_header_menu_l">
                            <div class="lui_portal_header_menu_r">
                                <div class="lui_portal_header_menu_c">
                                    <div class="lui_portal_header_menu_item_div">
                                        <div class="lui_portal_header_menu_item_left" style=""></div>
                                        <div class="lui_portal_header_menu_item_frame" style="left: 16px; right: 16px;">
                                            <div class="lui_portal_header_menu_item_body"
                                                style="white-space: nowrap; width: 1712px;">
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="1709a99541babb2df8e51484454b2b04"
                                                    class="lui_portal_header_menu_item_current">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">个人中心</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="1709a995425d1540d25f1a44d48bddb4"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">服务门户</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="1709a99542747b08708023f41f4acb2a"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">流程中心</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="1709a995428e91882e2edfd4198b889e"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">信息门户</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="170a4e69f6ec5859becbfa7475ba3011"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">公共-单标签</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="170a874eee034ff13b937b04a69b2f54"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">公共-多标签</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="170a4f30c844577f32479264fca8bfeb"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">公共-其他数据视图</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="170a4f30c8630ba9a46b0d847ed994b4"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">公共-门户组件（二级树和三级树）
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="170a4f30c887732d98930134e5eada11"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">公共-导航门户</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="170a4f30c889dbcb7c9946c4e7fa22a8"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">公共-待办门户</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="170a4f5afd931e63ee428db4c499186f"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">门户组件（日程相关部件）
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="170a4f5afdb84c46cc33995472db4d57"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">门户组件（图文摘要）</div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_portal_header_menu_item_line"
                                                    style="display: inline-block;vertical-align: top;"></div>
                                                <div style="display: inline-block;vertical-align: top;"
                                                    data-portal-id="1737011442f28d23112338a4bd8bffe6"
                                                    class="lui_portal_header_menu_item_div">
                                                    <div class="lui_portal_header_menu_item_l">
                                                        <div class="lui_portal_header_menu_item_r">
                                                            <div class="lui_portal_header_menu_item_c">知识门户</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="lui_portal_header_menu_item_right" style=""></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="lui_portal_header_simple_search">
























                    <div id="lui_portal_header_search_include" class="lui_portal_header_search_include"
                        onmouseover="showCategory()" onmouseout="hideCategory()" style="display: block;">
                        <div class="lui_portal_header_search_input  ">
                            <div class="lui_portal_header_search_keyword_l">
                                <div class="lui_portal_header_search_keyword_r">
                                    <div class="lui_portal_header_search_keyword_c">

                                        <input type="text" placeholder="找人、搜知识、查会议" name="SEARCH_KEYWORD"
                                            onkeydown="if (event.keyCode == 13 &amp;&amp; this.value !='') __portal_search__full();">
                                    </div>
                                </div>
                            </div>
                            <div id="searchCategory" class="searchCategory" onmouseover="showCategory()"
                                onmouseout="hideCategory()" style="display: none;">
                                <div id="searchAll" class="search_item">
                                    <label>
                                        <span class="search_radio">
                                            <input name="search" type="radio" id="searchAll" value="searchAll"
                                                checked="checked">
                                            <i class="item_radio"></i>
                                        </span>
                                        <span class="search_text">全部</span>
                                    </label>
                                </div>
                                <div id="searchPeople" class="search_item">
                                    <label>
                                        <span class="search_radio">
                                            <input name="search" type="radio" id="searchPeople" value="searchPeople">
                                            <i class="item_radio"></i>
                                        </span>
                                        <span class="search_text">人员</span>
                                    </label>
                                </div>
                                <div id="searchModel" class="search_item">
                                    <label>
                                        <span class="search_radio">
                                            <input name="search" type="radio" id="searchApp" value="searchModel">
                                            <i class="item_radio"></i>
                                        </span>
                                        <span class="search_text">应用</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="lui_portal_header_search_btn search_btn_icon">
                            <div class="lui_portal_header_search_full_l">
                                <div class="lui_portal_header_search_full_r">
                                    <div class="lui_portal_header_search_full_c">
                                        <input type="button" value="搜全系统" onclick="__portal_search__full()">
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="lui_portal_header_search_btn" id="_SEARCH_MODEL_" style="display: none">
                            <div class="lui_portal_header_search_model_l">
                                <div class="lui_portal_header_search_model_r">
                                    <div class="lui_portal_header_search_model_c">
                                        <input type="button" value="搜本模块" onclick="__portal_search__()">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <script>
                        var newLUI = "true";
                        var separator = false ? "&" : "#";

                        if ("false" == "true") {
                            document.getElementById("lui_portal_header_search_include").style.display = 'none';
                        } else {
                            document.getElementById("lui_portal_header_search_include").style.display = 'block';
                        }

                        function __portal_search__() {
                            var keyField = document.getElementsByName("SEARCH_KEYWORD")[0];
                            if (keyField.value == "") {
                                keyField.focus();
                                return;
                            } else {
                                if (typeof (DB_SEARCH) == "undefined") {
                                    var searchHost = ""
                                    if (searchHost != "") {
                                        var url = '';
                                    } else {
                                        var url = Com_Parameter.ContextPath +
                                            "sys/ftsearch/searchBuilder.do?method=search&queryString=";
                                    }
                                    url = url + encodeURIComponent(keyField.value);
                                    url = url + "&newLUI=" + newLUI;
                                    url = url + "&modelName=" + encodeURIComponent(SYS_SEARCH_MODEL_NAME);
                                    url = url + "&seq=" + Math.random();
                                    window.open(url, "_blank");
                                } else { //重定向为数据库搜索
                                    db_search(keyField.value);
                                }
                            }
                        }

                        function __portal_search__full() {
                            var searchFlag = $("input[name=search]:checked").val();
                            var keyField = document.getElementsByName("SEARCH_KEYWORD")[0];
                            if (keyField.value == "") {
                                keyField.focus();
                                return;
                            } else {
                                if ("searchAll" == searchFlag) {
                                    var searchHost = ""
                                    if (searchHost != "") {
                                        var url = '';
                                    } else {
                                        var url = Com_Parameter.ContextPath +
                                            "sys/ftsearch/searchBuilder.do?method=search&queryString=";
                                    }
                                    url = url + encodeURIComponent(keyField.value);
                                    url = url + "&newLUI=" + newLUI;
                                    url = url + "&seq=" + Math.random();
                                    url = url + "&searchAll=true";
                                    window.open(url, "_blank");
                                }
                                if ("searchPeople" == searchFlag) {
                                    if (keyField.value == null || trim(keyField.value) == "") {
                                        seajs.use('lui/dialog', function (dialog) {
                                            // 请输入内容
                                            dialog.alert("请输入搜索词", function () {
                                                $("#topKeyword").focus();
                                            });
                                        });
                                    } else {
                                        var url = Com_Parameter.ContextPath +
                                            'sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getPersons';
                                        url = url + "&fdSearchName=" + encodeURIComponent(trim(keyField.value)) +
                                            "&searchPeople=true";
                                        window.open(url, "_blank");
                                    }


                                }
                                if ("searchModel" == searchFlag) {
                                    if (keyField.value == null || trim(keyField.value) == "") {
                                        seajs.use('lui/dialog', function (dialog) {
                                            // 请输入内容
                                            dialog.alert("请输入搜索词", function () {
                                                $("#topKeyword").focus();
                                            });
                                        });
                                    } else {
                                        var url = Com_Parameter.ContextPath + 'sys/common/searchModel.jsp?query=' +
                                            encodeURIComponent(trim(keyField.value));
                                        url = url + "&searchModel=true";
                                        window.open(url, "_blank");
                                    }
                                }

                            }
                        }

                        function showCategory() {

                            if ($('#_SEARCH_MODEL_').css('display') == "none") {
                                $("#searchCategory").show();
                            }
                        }

                        function hideCategory() {
                            if ($('#_SEARCH_MODEL_').css('display') == "none") {
                                $("#searchCategory").hide();
                            }
                        }


                        function trim(str) { //删除左右两端的空格
                            return str.replace(/(^\s*)|(\s*$)/g, "");
                        }

                        var SYS_SEARCH_MODEL_NAME;
                        LUI.ready(function () {
                            $("#searchCategory").hide();
                            if (SYS_SEARCH_MODEL_NAME != null) {
                                LUI.$("#_SEARCH_MODEL_").show();
                            }
                            var itEnabled = "false";
                            if (itEnabled == 'true') {
                                $('.lui_portal_header_search_intell .icon_robot').click(function () {
                                    Com_OpenWindow("", '_blank');
                                });
                            }

                        });
                    </script>
                </div>

            </div>
        </div>
    </div>

    <script type="text/javascript">
        var messagetips = '当前用户邮箱为空';
        Com_IncludeFile("ticketcode.js", '/ekp/sys/portal/designer/js/', "js", true);
    </script>
    <script id="ticketcode_js" src="/ekp/sys/portal/designer/js/ticketcode.js?s_cache=1597654882776"></script>
    <script id="tag_js" src="/ekp/sys/zone/import/js/tag.js?s_cache=1597654882776"></script>
    <link rel="stylesheet" href="/ekp/sys/tag/resource/css/tag.css?s_cache=1597654882776">


</div>