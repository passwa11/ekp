<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
//console.log(grid);
{$
	<div class="cardClassifyContent" data-id="{%grid['fdId']%}" data-href="<c:url value="/sys/modeling/main/modelingAppView.do"/>?method=modelView&fdId={%grid['fdId']%}&&listviewId={%grid['listviewId']%}" target="_blank" onclick="Com_OpenNewWindow(this)">
        <div class="cardClassifyDetails">
        $}
       	{$
        	<input type="checkbox" class="cardClassifyDetailsOpt" onclick="changeCardDetailsOpt(this);" data-lui-mark="table.content.checkbox" name="List_Selected" value="{%grid['fdId']%}">
        $}
        var coverImg = grid['coverImg'] || "{}";
        coverImg = JSON.parse(coverImg);

        if(coverImg && coverImg[grid['fdId']]){
        var imgUrl = coverImg[grid['fdId']];
       	{$
            <div class="cardClassifyDetailsCover" style="background:url('<c:url value="/"/>{%imgUrl%}')  no-repeat center;background-size: contain;background-repeat: no-repeat;">

            </div>
        $}
        }
            {$
            <div class="cardClassifyDetailsText">
            $}
            if(grid['subject']){
            var subjectKey = grid['subject'];
            var title = grid[subjectKey] || '无';
            {$
            <div class="cardClassifyDetailsTitle panelShowText">
               {%title%}
            </div>
            $}
            }
        var summaryList = grid['summaryList'] || "[]";
            summaryList = JSON.parse(summaryList);
        var summaryFlag = (grid['summaryFlag']=='1');
        //var summaryLength = summaryList.length >= 8 ? 8 : summaryList.length;
        for(var i =0 ; i < summaryList.length ; i++){
            var summaryTemp = summaryList[i];
            var key = summaryTemp['field'];
            var title = summaryTemp['text'];
        {$
        <div class="cardClassifyAbstract">
            $}
            if(summaryFlag){
            {$
                <div class="cardClassifyAbstractTitle">{%title%}</div>
            $}
            }
            {$
                <div class="cardClassifyAbstractContent {%key%}">{%grid[key] || '暂无数据'%}</div>
        </div>
        $}
        }
       	{$
            </div>
        </div>
    </div>
$}