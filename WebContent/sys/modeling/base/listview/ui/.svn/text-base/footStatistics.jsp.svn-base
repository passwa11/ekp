<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
    .allDownPullIcon{
        background: url(resources/images/down-pull@2x.png) no-repeat center;
        height: 20px;
        width: 20px;
        display: inline-block;
        transform: rotate(
                180deg
        );
    }
    .statisticsTypeDiv{
        font-size: 14px;
        color: rgb(102, 102, 102);
        margin-right: 4px;
        min-width: 60px;
        display: inline-block;
    }
    .statisticsValDiv{
        font-size: 14px;
        color: rgb(51, 51, 51);
        font-weight: 500;
        display: inline-block;
    }
    .selectStatisticsTypeDiv{
        position: absolute;
        border: 1px solid rgb(238, 238, 238);
        margin-top: 9px;
        margin-left: -8px;
        width: 70px;
        background: #FFFFFF;
        z-index: 9;
    }
    .selectStatisticsTypeDiv .itemDiv{
        padding: 4px;
    }
    .selectStatisticsTypeDiv .itemDiv:hover{
        color: #4285F4;
        background: rgba(66,133,244,0.10);
    }
    .downIconDiv{
        background: url(resources/images/down@2x.png);
        height: 14px;
        width: 14px;
        background-size: 18px;
        display: inline-block;
    }
</style>
<script>
    Com_IncludeFile('calendar.js');
    var statisticsTypeArr = ["","求和","平均值","最小值","最大值"];
    var cacheUrl = '';
    seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
        topic.subscribe('list.loaded',function(datas) {
            var listData = datas.listview._data.datas;
            if(!listData|| listData.length == 0){
                return;
            }

            var sourceURL = datas.listview.sourceURL;
            var param = sourceURL.substring(sourceURL.indexOf("?")+1,sourceURL.length)
            //发起ajax请求
            var url = Com_Parameter.ContextPath + "sys/modeling/main/collectionView.do?method=footStatisticsData&";
            url = url + param + "&isGetBothValue=0";
            cacheUrl = url;
            $.ajax({
                url:url,
                async:true,
                type:'GET',
                success:function(data){
                    var dataArr = $.parseJSON(data);
                    drawFootStatisticsBody(dataArr);
                }
            });
        })

        $(document).on('click',function(e) {
            $('.selectStatisticsTypeDiv').css("display","none");
        })

        window.drawFootStatisticsBody = function (dataArr) {
            if(!dataArr||dataArr.length==0){
                return;
            }
            var fdDisplay = '${fdDisplay }';
            var fdDisplays = fdDisplay.split(",");
            var $table = $('.lui_listview_columntable_table');
            var $thead = $table.find("thead");

            drawFootStatisticsContent($thead,dataArr,fdDisplays);
            drawFootStatisticsContentDetail($thead,dataArr,fdDisplays);
        }

        window.drawFootStatisticsContent = function ($thead,dataArr,fdDisplays) {
            var $table = $('.lui_listview_columntable_table').find('tbody');
            var $tr = $("<tr class='footStatistics-body'/>");
            $thead.find('th').each(function (index) {
                var $td = $("<td />");
                if(index == 0){
                    //下拉图标
                    var $allDownPullIcon = $("<div />");
                    $allDownPullIcon.addClass("allDownPullIcon")
                    $td.append($allDownPullIcon);
                    $td.on("click", function() {
                        if("none" == $(".footStatisticsContentDetailCur").css("display")){
                            $(".allDownPullIcon").css("transform","rotate(0deg)");
                            $(".footStatisticsContentDetailCur").css("display","table-row");
                            $(".footStatisticsContentDetailAll").css("display","table-row");
                            $(".statisticsValDiv").each(function () {
                                $(this).css("display","none")
                            })
                        }else{
                            $(".allDownPullIcon").css("transform","rotate(180deg)");
                            $(".footStatisticsContentDetailCur").css("display","none");
                            $(".footStatisticsContentDetailAll").css("display","none");
                            $(".statisticsValDiv").each(function () {
                                $(this).css("display","inline-block")
                            })
                        }
                    })
                }else if(index == 1){
                    $(this).width(140)
                    //统计（所有数据）
                    $td.text("统计（所有数据）");
                }else{
                    //遍历对比设值
                    $td.attr("statisticsField",fdDisplays[index-2]);
                    for (var i = 0; i < dataArr.length; i++) {
                        if(fdDisplays[index-2] == dataArr[i].statisticsField){
                            var $statisticsTypeDiv =  $("<div />");
                            var $statisticsValDiv =  $("<div />");
                            var $statisticsTypeDivSpan =  $("<span />");
                            $statisticsTypeDivSpan.text(statisticsTypeArr[ Number(dataArr[i].curType)]);
                            $statisticsTypeDiv.append($statisticsTypeDivSpan);
                            $statisticsTypeDiv.addClass("statisticsTypeDiv");
                            $statisticsValDiv.text( dataArr[i].allVal);
                            $statisticsValDiv.addClass("statisticsValDiv");
                            $td.append($statisticsTypeDiv);
                            $td.append($statisticsValDiv);

                            var statisticsTypeSplit = dataArr[i].statisticsType.split(";");

                            var $selectStatisticsTypeDiv = $("<div style='display: none' />");
                            $selectStatisticsTypeDiv.addClass("selectStatisticsTypeDiv");
                            for (var j = 0; j < statisticsTypeSplit.length-1; j++) {
                                var itemDiv =  $("<div />");
                                itemDiv.addClass("itemDiv");
                                itemDiv.attr("index",Number(statisticsTypeSplit[j]));
                                itemDiv.append("<span>"+statisticsTypeArr[ Number(statisticsTypeSplit[j])]+"</span>")
                                itemDiv.on("click",function () {
                                    $statisticsTypeDivSpan.text($(this).text());
                                    updateFootStatisticsBody( $(this).attr("index"),dataArr[i].statisticsField);
                                })
                                $selectStatisticsTypeDiv.append(itemDiv);
                            }
                            $statisticsTypeDiv.append($selectStatisticsTypeDiv);

                            if(statisticsTypeSplit.length>2){
                                var $downIconDiv =  $("<div />");
                                $downIconDiv.addClass("downIconDiv");
                                $statisticsTypeDiv.append($downIconDiv);
                                $statisticsTypeDiv.on("click",function (e){
                                    e.stopPropagation();
                                    $(".selectStatisticsTypeDiv").css("display","none");
                                    if("none" ==  $(this).find(".selectStatisticsTypeDiv").css("display")){
                                        $(this).find(".selectStatisticsTypeDiv").css("display","block");
                                    }else{
                                        $(this).find(".selectStatisticsTypeDiv").css("display","none");
                                    }

                                })
                            }

                            $td.css({
                                "white-space": "nowrap",
                                "text-align": "center"
                            })
                            break;
                        }
                    }
                }
                // $td.css({
                //     "height": "40px",
                //     "padding": "0"
                // })
                $tr.append($td);
            })
            $tr.css({
                "background": "#F8F8F8"
            })
            $table.append($tr);
        }

        window.drawFootStatisticsContentDetail = function ($thead,dataArr,fdDisplays) {
            drawFootStatisticsContentDetailCur($thead,dataArr,fdDisplays);
            drawFootStatisticsContentDetailAll($thead,dataArr,fdDisplays);
        }

        window.drawFootStatisticsContentDetailCur = function  ($thead,dataArr,fdDisplays) {
            var $table = $('.lui_listview_columntable_table').find('tbody');
            var $tr = $("<tr  class='footStatisticsContentDetailCur'/>");
            $thead.find('th').each(function (index) {
                var $td = $("<td />");
                if(index == 0){
                }else if(index == 1){
                    $(this).width(140)
                    //当前页
                    $td.text("当前页");
                }else{
                    //遍历对比设值
                    $td.attr("statisticsField",fdDisplays[index-2]);
                    $td.attr("showType","cur");
                    for (var i = 0; i < dataArr.length; i++) {
                        if(fdDisplays[index-2] == dataArr[i].statisticsField){
                            $td.text(dataArr[i].curVal);
                            break;
                        }
                    }
                }
                // $td.css({
                //     "height": "40px",
                //     "padding": "0"
                // })
                $tr.append($td);
            })
            $tr.css({
                "display": "none"
            })
            $table.append($tr);
        }

        window.drawFootStatisticsContentDetailAll = function  ($thead,dataArr,fdDisplays) {
            var $table = $('.lui_listview_columntable_table').find('tbody');
            var $tr = $("<tr class='footStatisticsContentDetailAll'/>");
            $thead.find('th').each(function (index) {
                var $td = $("<td />");
                if(index == 0){
                }else if(index == 1){
                    $(this).width(140)
                    //统计（所有数据）
                    $td.text("所有数据");
                }else{
                    //遍历对比设值
                    $td.attr("statisticsField",fdDisplays[index-2]);
                    $td.attr("showType","all");
                    for (var i = 0; i < dataArr.length; i++) {
                        if(fdDisplays[index-2] == dataArr[i].statisticsField){
                            $td.text(dataArr[i].allVal);
                            break;
                        }
                    }
                }
                // $td.css({
                //     "height": "40px",
                //     "padding": "0"
                // })
                $tr.append($td);
            })
            $tr.css({
                "display": "none"
            })
            $table.append($tr);
        }


        window.updateFootStatisticsBody = function (statisticsType,statisticsField) {
            var url = cacheUrl + "&statisticsType="+statisticsType+"&statisticsField="+statisticsField
            $.ajax({
                url:url,
                async:true,
                type:'GET',
                success:function(data){
                    var dataArr = $.parseJSON(data);
                    updateFootStatisticsContent(dataArr);
                    $(".selectStatisticsTypeDiv").css("display","none");
                }
            });
        }

        window.updateFootStatisticsContent = function (dataArr) {
            for(var i=0;i<dataArr.length;i++){
                var $td = $("[statisticsfield='"+dataArr[i].statisticsField+"']");
                $td.each(function () {
                    if($(this).find(".statisticsValDiv").length > 0){
                        $(this).find(".statisticsValDiv").text(dataArr[i].allVal);
                    }else{
                        if('cur' == $(this).attr("showtype")){
                            $(this).text(dataArr[i].curVal);
                        }else{
                            $(this).text(dataArr[i].allVal);
                        }
                    }
                })

            }
        }

    });
</script>