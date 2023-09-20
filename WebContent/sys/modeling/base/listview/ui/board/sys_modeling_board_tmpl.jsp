<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
    <div class="cardClassifyDetails" data-id="{%grid['fdId']%}" data-href="<c:url value='/sys/modeling/main/modelingAppView.do'/>?method=modelView&fdId={%grid['fdId']%}&&listviewId={%grid['listviewId']%}" target="_blank" onclick="Com_OpenNewWindow(this)">
        $}
        {$
        <input type="checkbox" class="cardClassifyDetailsOpt" onclick="changeCardDetailsOpt(this);" data-lui-mark="table.content.checkbox" name="List_Selected" value="{%grid['fdId']%}">
        $}
        var coverImg = grid['coverImg'] || "{}";
        coverImg = JSON.parse(coverImg);

        if(coverImg && coverImg[grid['fdId']]){
        var imgUrl = coverImg[grid['fdId']];
        {$
        <div class="cardClassifyDetailsCover" style="background:url('<c:url value="/"/>{%imgUrl%}')  no-repeat center">

        </div>
        $}
        }
        {$
        <div class="cardClassifyDetailsText">
            $}
            if(grid['subject']){
            var subjectKey = grid['subject'];
            var title = grid[subjectKey] || listViewLang.none;
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
                <div class="cardClassifyAbstractTitle" >{%title%}</div>
                $}
                }
                {$
                <div class="cardClassifyAbstractContent" style="{%grid['style'][key]%}">{%grid[key] || listViewLang.noData%}</div>
            </div>
            $}
            }
            {$
        </div>
    </div>
$}