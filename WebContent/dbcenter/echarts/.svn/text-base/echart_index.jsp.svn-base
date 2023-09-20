<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<template:include ref="default.simple" spa="true" rwd="true">
<link rel="stylesheet" href="${LUI_ContextPath}/resource/style/default/list/listview.css?s_cache=${LUI_Cache}" />
    <link rel="stylesheet" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/common/echart_index.css" />
    <script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript" src="<c:url value="/dbcenter/echarts/common/echart_index.js"/>?s_cache=${LUI_Cache}"></script>
    <style>
    .lui-theChart-main-content-right-category-dataList-text{
	    position: absolute;
	    left:24px;
	    top:0;
	    line-height: 40px;
	    height:40px;
	    max-width: 140px;
	    right:10px;
	    overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: nowrap;
	}
    </style>

    <template:replace name="title">
        <c:out value="${ lfn:message('dbcenter-echarts:module.echarts.index') }"></c:out>
    </template:replace>


    <template:replace name="body" >
    	<ui:tabpanel id="echartsPanel" layout="sys.ui.tabpanel.list" >
			<ui:content id="customContent" title="${ lfn:message('dbcenter-echarts:module.echarts.index') }">
			
        <div class="lui-theChart-main">
            
            <!-- 主题部分 -->
            <div class="lui-theChart-main-content" >
                <div class="lui-theChart-main-content-left" >
	                <div class="lui-theChart-main-content-left-box">
	                	<div class="lui-theChart-main-content-left-title">${ lfn:message('dbcenter-echarts:module.echarts.data') }</div>
		                <div class="lui-theChart-main-content-left-item" id="all_echarts">
		                    <span>${ lfn:message('dbcenter-echarts:module.echarts.allData') } </span>
		                    <span id="allEchartCount" class="lui-theChart-main-content-left-item-num">0</span>
		                    <br/>
		                    <font class="lui-theChart-main-content-left-tip">${ lfn:message('dbcenter-echarts:module.echarts.allData.tip') } </font>
		                </div>
		                <div class="lui-theChart-main-content-left-item" id="my_attention_echarts">
		                    <span>${ lfn:message('dbcenter-echarts:module.echarts.following') }</span>
		                    <span id="myAttentionEchartsCount" class="lui-theChart-main-content-left-item-num">0</span>
		                    <br/>
		                    <font class="lui-theChart-main-content-left-tip">${ lfn:message('dbcenter-echarts:module.echarts.following.tip') }</font>
		                </div>
		                <div class="lui-theChart-main-content-left-item" id="my_create_echarts">
		                    <span>${ lfn:message('dbcenter-echarts:module.echarts.createMyEcharts') }</span>
		                    <span id="myCreateEchartsCount" class="lui-theChart-main-content-left-item-num">0</span>
		                    <br/>
		                    <font class="lui-theChart-main-content-left-tip">${ lfn:message('dbcenter-echarts:module.echarts.createMyEcharts.tip') }</font>
		                </div>
	                </div>
                </div>
                <div class="lui-theChart-main-content-right">
                    <div class="lui-theChart-main-content-right-top">

                            <ui:dataview format="sys.ui.treeMenu2" cfg-channel="switchAppNav">
                                <ui:source type="AjaxJson">
                                    {"url":"/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=getDbEchartTotalList"}
                                </ui:source>
                                <ui:render ref="sys.render.echarts.app" cfg-for="switchAppNav"></ui:render>
                            </ui:dataview>

                    </div>

                    <div class="lui-theChart-main-content-right-data">

                    </div>
                </div>
            </div>
        </div>
</ui:content>
	</ui:tabpanel>

    </template:replace>
</template:include>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/data.js?s_cache=${LUI_Cache}"></script>
<script type="text/javascript">

	$(document).ready(function(){
		console.log(window.top.innerHeight-81);
		$('.lui-theChart-main').height(window.top.innerHeight-120);
		
		$('.lui-theChart-main-content-right-data').css("min-height",window.top.innerHeight-120-67-7);
		
         $("#all_echarts").addClass('lui-theChart-active');
         
	});
	
	
    seajs.use(['lui/jquery','lui/dialog','lui/topic'],function($,dialog,topic){
    	
    	
         

        //请求所有图表数据
        $.ajax({
            type : "get",
            url : '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do" />?method=getDbEchartTotalList',
            dataType:'json',
            async : false,
            success : function(data){
                if(data.resultCode=="1"){
                    $('.lui-theChart-main-content-right-data').html("");
                    var echartCount=allEcharts(data.data,$('.lui-theChart-main-content-right-data'));
                    $("#allEchartCount").html(echartCount);
                    iconEvent();
                }
            }
        });

        //请求我关注的图表
        $.ajax({
            type : "get",
            url : '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do" />?method=getMyAttentionEcharts',
            dataType:'json',
            async : false,
            success : function(data){
                if(data.resultCode=="1"){
                    $("#myAttentionEchartsCount").html(data.data.length);
                }

            }
        });

        //请求我创建的图表
        $.ajax({
            type : "get",
            url : '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do" />?method=getMyCreateEcharts',
            dataType:'json',
            async : false,
            success : function(data){
                if(data.resultCode=="1"){
                    $("#myCreateEchartsCount").html(data.data.length);
                }
            }
        });




        $('#all_echarts').click(function(){
            $('.lui-theChart-main-content-left .lui-theChart-main-content-left-item').removeClass('lui-theChart-active');
            $(this).addClass('lui-theChart-active');

            var url = '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do" />';
            //请求所有图表数据
            $.ajax({
                type : "get",
                url : url+'?method=getDbEchartTotalList',
                dataType:'json',
                async : false,
                success : function(data){
                    if(data.resultCode=="1"){
                        $('.lui-theChart-main-content-right-data').html("");
                        var echartCount=allEcharts(data.data,$('.lui-theChart-main-content-right-data'));
                        $("#allEchartCount").html(echartCount);
                        iconEvent();
                    }
                }
            });
        });


        $('#my_attention_echarts').click(function(){
            $('.lui-theChart-main-content-left .lui-theChart-main-content-left-item').removeClass('lui-theChart-active');
            $(this).addClass('lui-theChart-active');

            var url = '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do" />';
            //请求我关注的图表
            $.ajax({
                type : "get",
                url : url+'?method=getMyAttentionEcharts',
                dataType:'json',
                async : false,
                success : function(data){
                    if(data.resultCode=="1"){
                        $('.lui-theChart-main-content-right-data').html("");
                        var echartCount=getMyAttentionEcharts(data.data,$('.lui-theChart-main-content-right-data'));
                        $("#myAttentionEchartsCount").html(echartCount);
                        iconEvent();
                    }

                }
            });


        });

        $('#my_create_echarts').click(function(){
            $('.lui-theChart-main-content-left .lui-theChart-main-content-left-item').removeClass('lui-theChart-active');
            $(this).addClass('lui-theChart-active');

            var url = '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do" />';
            //请求我创建的图表
            $.ajax({
                type : "get",
                url : url+'?method=getMyCreateEcharts',
                dataType:'json',
                async : false,
                success : function(data){
                    if(data.resultCode=="1"){
                        $('.lui-theChart-main-content-right-data').html("");
                        var echartCount=getMyCreateEcharts(data.data,$('.lui-theChart-main-content-right-data'));
                        $("#myCreateEchartsCount").html(echartCount);
                        iconEvent();
                    }
                }
            });

        });
        
    });
    
    //重新生成之后重新点击事件
    function iconEvent(){
    	 $('.lui-theChart-main-content-right-data-categoryItem-title-icon').click(function(){
             console.log($('.lui-theChart-main-content-dataLsit').height());
             
             if($(this).parent().next().height()==0){
                 $(this).removeClass('theUnfoldStyle')
                 // $('.lui-theChart-main-content-dataLsit').removeClass('foldListStyle')
                 $(this).parent().next().css({
                     'max-height':'2000px',
                     'overflow':'hidden',
                     'transition':'max-height 0.5s linear'
                 });
                 $(this).css({
                	 'background':'url(\'common/images/down.png\') no-repeat center center'
                 });
             }else{
                 $(this).addClass('theUnfoldStyle')
                 $(this).parent().next().css({
                     'max-height':'0',
                     'overflow':'hidden',
                     'transition':'max-height 0.5s linear'
                 });
                 $(this).css({
                	 'background':'url(\'common/images/up.png\') no-repeat center center'
                 });
             }
             
         });
    }


    function categoryUrl(categoryId){
        LUI.pageOpen( "echart_total.jsp?categoryId="+categoryId+"&rwd=tru",'_rIframe');
    }

    function getview(fdId) {
            var customUrl="/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=view&fdId="+fdId;
            var chartUrl="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=view&fdId="+fdId+"&chartDefault=1";
            var tableUrl="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=view&fdId="+fdId+"&chartDefault=1";
            var setUrl="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=view&fdId="+fdId;

            var urlParm="";
        var url = '<c:url value="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do" />';
        var param = {
            method : "getDbEchartTotalView",
            fdId:fdId
        };

        $.ajax({
            type : "post",
            url : url,
            data : param,
            dataType:'json',
            async : false,
            success : function(data){
                if(data.resultCode=="1"){//接口返回成功
                    if(data.data.echartType=="1"){
                        urlParm=customUrl;
                    }else if(data.data.echartType=="2"){
                        urlParm=chartUrl;
                    }else if(data.data.echartType=="3"){
                        urlParm=tableUrl;
                    }else if(data.data.echartType=="4"){
                        urlParm=setUrl;
                    }else{
                        urlParm="";
                    }
                }
            }
        });
            console.log("urlParm:"+urlParm);
            
          	//js  兼容各浏览器
            var dialogH = parent.document.documentElement.clientHeight;
            var dialogW = parent.document.documentElement.clientWidth;
            console.log("dialogH:"+dialogH);
            console.log("dialogW:"+dialogW);
            dialogW=dialogW-dialogW*0.1*2;
            dialogH=dialogH-dialogH*0.05*2;
            console.log("dialogH:"+dialogH);
            console.log("dialogW:"+dialogW);
            
            
            seajs.use(['lui/dialog','lui/jquery'], function(dialog, $){
                dialog.build({
                    config : {
                        width : dialogW,
                        height : dialogH,
                        title : "${lfn:escapeJs(lfn:message('dbcenter-echarts:module.dbcenter.piccenter'))}",
                        content : {
                            type : "iframe",
                            url : urlParm
                        }
                    },
                    callback :  function(value, dialog) {}
                }).show();
            });
    }

    function openUrl(categoryId){
        parent.moduleAPI.dbEcharts.openPreview(categoryId,true);
    }


</script>