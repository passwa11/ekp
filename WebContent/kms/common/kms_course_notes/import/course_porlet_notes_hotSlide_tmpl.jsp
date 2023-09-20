<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var datas = data;
var columnNum = template.parent.columnNum;
var width = 100/columnNum+'%';
if (datas.length!=0){
	var obj = datas[0];
	var pageno = obj['pageno'].text;
	var total = obj['total'].text;
	$(".hot_slide #pageno").val(pageno);
	$(".hot_slide #total").val(total);
}


{$
 
	<div class="km-note-list-row">$}
for (var i = 0; i < datas.length; i ++) {
	var grid = datas[i];
	
	if (i % columnNum === 0){
		{$<ul class="km-note-list">$}
	}
			{$<li width="{%width%}">
			<c:set var="fdIdInfo" value="{%grid['fdId'].text%}"></c:set>
				  <div class="km-note-list-item" onclick="showInfo('${fdIdInfo}')">
                        	<h4 class="km-note-list-title" >{%grid['fdCourse'].text%}</h4>
                        	<div class="km-note-list-detail">
             					 <span class="km-note-list-author" title="{%grid['docCreator'].text%}">{%grid['docCreator'].text%}</span>
             					 <span class="km-note-list-date" title="{%grid['docCreateTime'].text%}">
               						{%grid['docCreateTime'].text%}
             					 </span>
            				</div>
            				<div class="km-note-list-content" title="{%grid['fdNotesContent'].text%}">
            					{%grid['fdNotesContentShort'].text%}
            				</div>
            				<div class="km-note-list-footer">
             					 <a href="javascript:void(0);" class="km-note-list-admire">
             					     <span class="icon" title="${lfn:message('kms-common:kmsCourseNotes.praiseCount') }"></span>
	               					 {%grid['docPraiseCount'].text%}
             					 </a>
            					 <a href="javascript:void(0);" class="km-note-list-comment">
                					<span class="icon" title="${lfn:message('kms-common:kmsCourseNotes.evalCount') }"></span>
               						{%grid['docEvalCount'].text%}
             					 </a>
          					 </div>
                    </div>
			</li>$}
	if (i % columnNum === columnNum-1){
		{$</ul>$}
	}
}
var _num = datas.length % columnNum; 
if(_num>0){
	for(var j = 0;j<columnNum-_num;j++){
		{$
			<li width="{%width%}"></li>
		$}
	}
	{$ </ul> $}
}

{$
	  <div class="km-note-list-button-wrap">
        <input type="button" class="left"  onclick="turnLeft();"></button>
        <input type="button" class="right" onclick="turnRight();"></button>
      </div>
      <div class="km-note-list-page">
        <span class="pageno"></span>
        <span>/</span>
        <span class="total"></span>
      </div>

</div>
$}
