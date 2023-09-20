<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/relation.css?s_cache=${LUI_Cache}"/>      
        <style type="text/css">
            body{

                -moz-user-select: none; /*火狐*/

                -webkit-user-select: none; /*webkit浏览器*/

                -ms-user-select: none; /*IE10*/

                -khtml-user-select: none; /*早期浏览器*/

                user-select: none;

            }
            
            ::-webkit-scrollbar-track {
                background-color: #F5F6FA;
            }
            .model-mask-panel-edit-drag-item{
                margin-top: 10px;
            }
        </style>
    </template:replace>
    <template:replace name="content">
    <div class="model-panel-drag-box">
        <div class="model-panel-drag  " id="paramSelect_main" style="position: relative">
        
             <div style="display:none;" id="searchNumber">
            	<div class="number_title">${lfn:message('sys-modeling-base:relation.display.filter.items')}
                    <span class="desc">${lfn:message('sys-modeling-base:relation.display.two.filter.items')}</span></div>
            	<div>
            		<div style="display:inline-block;">
	            		<input type="radio" name="showCount" value="1"/><span>1</span>
	            		<input type="radio" name="showCount" value="2" checked/><span>2</span>
	            		<input type="radio" name="showCount" value="3"/><span>3</span>
	            		<input type="radio" name="showCount" value="4"/><span>4</span>
            		</div>
            	</div>
            </div>
        
            <div class="model-mask-panel-edit">
                <div class="model-mask-panel-edit-title">
                    <p>${lfn:message('sys-modeling-base:relation.from.selection.area')}
                        <span style="color:#4285f4">${lfn:message('sys-modeling-base:relation.drag')}</span>
                            ${lfn:message('sys-modeling-base:relation.filter.items.nodify.order')}</p><i></i>
                    <div class="model-mask-panel-edit-title-opt">
<%--                        <div class="model-mask-panel-edit-title-width">设置宽度</div>--%>
                        <div class="model-mask-panel-edit-title-reset" onclick="reset()">${lfn:message('sys-modeling-base:button.reset')}</div>
                    </div>
                </div>
                <div class="model-mask-panel-edit-drag" id="paramSelect_placeholder">
                </div>
            </div>
            
            <div class="model-mask-panel-flag">
                <div class="model-mask-panel-flag-title">${lfn:message('sys-modeling-base:relation.selection.area')}</div>
                <div class="model-mask-panel-flag-content" id="paramSelect">

                </div>
            </div>
            
        </div>
        <div class="toolbar-bottom">
            <ui:button text="${lfn:message('button.cancel') }" onclick="cancel();" styleClass="lui_toolbar_btn_gray"/>
            <ui:button text="${lfn:message('button.select')}" onclick="doSubmit();"/> <%--取消--%>

        </div>
    </div>
        <script type="text/javascript">
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/str'], function ($, dialog, topic, str) {
                //-------------------- 拖拽参数定义

                //扩大10px的冗余
                var holder = {}
                var range = {x: 0, y: 0};//鼠标元素偏移量
                var lastPos = {x: 0, y: 0, x1: 0, y1: 0}; //拖拽对象的四个坐标
                var tarPos = {x: 0, y: 0, x1: 0, y1: 0}; //目标元素对象的坐标初始化

                var dragArea;
                var theDiv = null, move = false;//拖拽对象 拖拽状态
                var theDivId = 0, theDivHeight = 0, theDivHalf = 0, tarIdx = 0; //拖拽对象的索引、高度、的初始化。
                var souDiv = null, tarDiv = null;
                //-------------------- 拖拽参数定义 end


                //以后有空组件化
                //监听数据传入
                var _param ;
                var intervalEndCount = 10;
                var interval = setInterval(__interval,"50");
                function __interval(){
                    if(intervalEndCount==0){
                        console.error("数据解析超时。。。");
                        clearInterval(interval);
                    }
                    intervalEndCount--;
                    if(!window['$dialog']){
                        return;
                    }

                    _param = $dialog.___params;
                    //console.debug(JSON.stringify(_param))
                    initData();
                    clearInterval(interval);


                }
                function initData() {
                    //筛选项显示个数生成
                    if(_param.type == "fdOutSearch"){
                        $("#searchNumber").show();
                        var number = _param.ext.searchNumber;
                        $(":radio[name='showCount'][value='"+number+"']").prop("checked","checked");
                    }
                    if(_param.type == "fdOutSelect"){
                        $("#paramSelect").css("height","266px");
                    }

                    //选择框生成
                    build(_param)
                    buildSelected();
                    //#126299 初始化拖拽区域
                    holder = {
                        minX: $("#paramSelect_placeholder").offset().left - 10,
                        minY: $("#paramSelect_placeholder").offset().top - 10,
                        maxX: $("#paramSelect_placeholder").offset().left + $("#paramSelect_placeholder").width() + 10,
                        maxY: $("#paramSelect_placeholder").offset().top + $("#paramSelect_placeholder").height() + 10
                    }
                }

                //要插入的div ，源目标div，要插入的目标元素的对象, 临时的虚线对象
                //class=holder locked fixedDiv movingDiv
                //构建数据
                function build(_param) {
                    for (var k in _param.data) {
                        var d = _param.data[k]
                        //过滤附件
                        if(d.type && d.type.toLowerCase().indexOf("attachment")>-1)
                            continue;
                        //Clob不处理
                        if(d.isClob)
                            continue;

                        if (_param.type=="fdOutSearch"&&d.type &&d.type.indexOf("[]")>0) {
                            //多选不处理
                            continue;
                        }
                        if (_param.type=="fdOutSearch"&&d.businessType &&d.businessType == "textarea") {
                            //#171719 多行不处理
                            continue;
                        }
                        //过滤明细表
                        if ((_param.isDetail && d.name && d.name.indexOf(".") > 0 && d.name.indexOf(_param.targetDetail)<0)
                            || (!_param.isDetail && d.name && d.name.indexOf(".") > 0))
                            continue;

                        var showLabel = d.label;
                        var $div = $("  <div class=\"model-mask-flag-item\">\n" +
                            "                        <div>\n" +
                            "                   <i onclick='delParam(\""+k+"\")'></i>" +
                            "                    <p>" + showLabel + "</p>\n" +
                            "                        </div>\n" +
                            "                    </div>");
                        $div.attr("lui_param_id", k);
                        $("#paramSelect").append($div);
                    }
                    bindEvent();
                }
                //构建旧数据
                function buildSelected() {
                    if(_param.oldData){
                        if (typeof  _param.oldData === "string"){
                            _param.oldData =  JSON.parse(_param.oldData);
                        }
                        $.each(_param.oldData,function(i,v){
                            var s=$("[lui_param_id='"+v.name+"']");
                            var t = s.clone();
                            t.attr("class", "  model-mask-panel-edit-drag-item  fixedDiv");
                            s.addClass("locked");
                            _bind_FixedDivMousedown(t);
                            $("#paramSelect_placeholder").append(t);
                            $("#paramSelect_placeholder").addClass("nobg");
                        })
                    }
                }
                //绑定事件
                function bindEvent() {
                    var $element = $("#paramSelect_main");
                    var $doms = $(".model-mask-flag-item");
                    $doms.each(function (idx, ele) {
                        //拖拽开始，
                        _bind_flagDivMousedown($(ele));
                    });
                    //全局鼠标移动监听
                    $element.mousemove(function (event) {
                        if (!move) return false;
                        //定位
                        lastPos.x = event.pageX - range.x;
                        lastPos.y = event.pageY - range.y;
                        // 拖拽元素随鼠标移动
                        theDiv.css({left: lastPos.x + 'px', top: lastPos.y + 'px'});
                        //theDiv.find("p").html(lastPos.x + 'px  |  ' + lastPos.y + 'px');
                        // if( dragArea === "selectedItem"){
                        //     return;
                        // }
                        // 拖拽元素随鼠标移动 查找插入目标元素
                        console.log(" holder.minY", holder.minY," holder.minX", holder.minX,
                            " holder.maxY", holder.maxY," holder.maxX", holder.maxX);
                        console.log(" lastPos.y", lastPos.y," lastPos.x", holder.x);
                        if (lastPos.y <= holder.minY || lastPos.x <= holder.minX ||
                            lastPos.y >= holder.maxY || lastPos.x >= holder.maxX) {
                            //if 越界
                            $(".holder").remove();
                            tarDiv = null;
                            return;
                        }
                        var tempDiv = "<div class=\"model-mask-panel-edit-drag-item  holder\"></div>";
                        var firstDiv = null;//预计插入位置的后一个元素
                        var $fixedList = $('.fixedDiv'); // 局部变量：按照重新排列过的顺序  再次获取 各个元素的坐标，
                        var fidx = 0;
                        $fixedList.each(function (idx, dom) {
                            //找到之后就break；
                            if (firstDiv)
                                return;
                            var itemDiv = $(this);
                            tarPos.x = itemDiv.offset().left;
                            tarPos.y = itemDiv.offset().top;
                            //找到插入位置
                            if (lastPos.y <= (tarPos.y + theDivHalf) && lastPos.x <= tarPos.x) {
                                firstDiv = itemDiv;
                                fidx = idx;
                            }

                        });

                        if (firstDiv) {
                            //如果firstDiv不为空则表示是在中间插入的
                            if (fidx === tarIdx && fidx != "0") {
                                //避免dom操作频繁，同一dom不进行改变
                                return;
                            }
                            $(".holder").remove();
                            tarDiv = $(tempDiv);
                            tarDiv.insertBefore(firstDiv);
                            tarIdx = fidx;
                        } else {
                            //从最后添加
                            $(".holder").remove();
                            tarDiv = $(tempDiv);
                            $("#paramSelect_placeholder").append(tarDiv);
                            $("#paramSelect_placeholder").addClass("nobg");

                        }

                    }).mouseup(function (event) {
                        //全局鼠标放开监听
                        if (!move) {
                            return false;
                        }
                        move = false;
                        if (tarDiv) {
                            //有目标插入，插入目标，
                            theDiv.attr("class", " model-mask-panel-edit-drag-item fixedDiv");
                            theDiv.css({left: "", top: ""});
                            theDiv.insertAfter(tarDiv);
                            if (dragArea === "flagItem") {
                                tarDiv = null;
                                //为选中的参数重新添加拖拽事件
                                _bind_FixedDivMousedown(theDiv);
                            }
                        } else {
                            //无目标插入
                            if (dragArea === "selectedItem") {
                                theDiv.attr("class", " model-mask-panel-edit-drag-item fixedDiv");
                                theDiv.css({left: "", top: ""});
                            } else {
                                souDiv.removeClass("locked");
                                theDiv.remove();
                                tarDiv = null;
                                var contentHtml = $("#paramSelect_placeholder").html();
                                if(contentHtml == null || contentHtml == "" || contentHtml.length == 0 || /^\s*?$/.test(contentHtml)){
                                	 $("#paramSelect_placeholder").removeClass("nobg");
                                }
                            }
                        }
                        $(".holder").remove();

                    });

                }

                function _bind_flagDivMousedown($ele){
                    $ele.mousedown(function (event) {
                        dragArea = "flagItem";
                        if (move || $(this).hasClass("locked")) {
                            return false;
                        }
                        //初始化变量
                        souDiv = $(this);
                        theDivId = souDiv.index();
                        theDivHeight = souDiv.height();
                        theDivHalf = theDivHeight / 2;
                        //鼠标元素相对偏移量
                        range.x = event.pageX - souDiv.offset().left;
                        range.y = event.pageY - souDiv.offset().top;
                        //拖拽对象克隆
                        theDiv = souDiv.clone();
                        //源对象加锁
                        souDiv.addClass("locked");
                        //拖拽对象脱离文档流，开始移动
                        theDiv.addClass("movingDiv");
                        theDiv.css({left: souDiv.offset().left + 'px', top: souDiv.offset().top + 'px'});
                        souDiv.after(theDiv);
                        move = true;
                    });
                }
                function _bind_FixedDivMousedown($ele){

                    $ele.mousedown(function (event) {
                        //console.log($(event.target)[0].localName)
                        var targetName  = $(event.target)[0].localName;
                        if(targetName === "i"){
                            return ;
                        }
                        dragArea = "selectedItem";
                        if (move)
                            return false;
                        //初始化变量
                        souDiv = $(this);
                        theDivId = souDiv.index();
                        theDivHeight = souDiv.height();
                        theDivHalf = theDivHeight / 2;
                        //鼠标元素相对偏移量
                        range.x = event.pageX - souDiv.offset().left;
                        range.y = event.pageY - souDiv.offset().top;
                        // consoleBox("event.pageX=" + event.pageX + "    event.pageY" + event.pageY);
                        theDiv = souDiv;
                        theDiv.css({left: souDiv.offset().left + 'px', top: souDiv.offset().top + 'px'});
                        theDiv.addClass("movingDiv");
                        theDiv.removeClass("fixedDiv");

                        move = true;
                    });
                }
                window.doSubmit = function () {
                    var idxP = 0;
                    var ParamObj = {};
                    var paramArray = [];
                    var eles = $(".fixedDiv");
                    $.each(eles, function (idx, e) {
                        var id = $(e).attr("lui_param_id");
                        var p = _param.data[id];
                        p.id = idxP;
                        paramArray.push(p);
                        idxP++;
                    });
                    ParamObj = paramArray;
                    var searchNumber = $("input[name='showCount']:checked").val();
                    if(_param.type == "fdOutSearch"){
                    	ParamObj = {
                    		paramArray : paramArray,
                    		searchNumber : searchNumber
                    	}
                    }
                    $dialog.hide(ParamObj);
                };
                window.reset = function () {
                    $("#paramSelect_placeholder").empty();
                    $("#paramSelect").empty();
                    initData();

                };
                window.cancel = function () {
                    $dialog.hide(null);
                };
                window.delParam = function (idx) {
                    event.stopPropagation()
                    var idxDoms = $("[lui_param_id='"+idx+"']");
                    idxDoms.each(function (idx,dom) {
                        //console.log($(dom).attr("class"))
                        if($(dom).hasClass("locked")){
                            $(dom).removeClass("locked");
                        }

                        if($(dom).hasClass("fixedDiv")){
                            $(dom).remove();
                        }
                    });
                    var contentHtml = $("#paramSelect_placeholder").html();
                    if(contentHtml == null || contentHtml == "" || contentHtml.length == 0 || /^\s*?$/.test(contentHtml)){
                    	 $("#paramSelect_placeholder").removeClass("nobg");
                    }
                };
                function consoleBox(str) {
                    var $p = $("<p style='padding:20px;line-height:24px;'/>");
                    $p.append("<br/> version = ").append("V 0.0");
                    $p.append("<br/> holder = ").append(JSON.stringify(holder));
                    $p.append("<br/> range = ").append(JSON.stringify(range));
                    $p.append("<br/> lastPos = ").append(JSON.stringify(lastPos));
                    $p.append("<br/> tarPos = ").append(JSON.stringify(tarPos));
                    $p.append("<br/> other = ").append(JSON.stringify(str));
                    $("#consoleBox").html($p)
                }
                
            });
            
        </script>
    </template:replace>
</template:include>