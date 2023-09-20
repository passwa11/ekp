<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var datas = data;
var columnNum = template.parent.columnNum;
var width = 100/columnNum+'%';
if (datas.length!=0){
	var obj = datas[0];
	var pageno = obj['pageno'].text;
	var total = obj['total'].text;
	$(".my_tile #pageno").val(pageno);
	$(".my_tile #total").val(total);
}


{$
 
	<div class="km-note-list-row myNote">$}
for (var i = 0; i < datas.length; i ++) {
	var grid = datas[i];
	
	if (i % columnNum === 0){
		{$<ul class="km-note-list">$}
	}
			{$<li width="{%width%}">
			<c:set var="fdIdInfo" value="{%grid['fdId'].text%}"></c:set>
				  <div class="km-note-list-item" onclick="showInfo('${fdIdInfo}')">
          					  <div class="km-note-list-tag tick"></div>
           					  <div class="km-note-list-content" title="{%grid['fdNotesContent'].text%}">
            					{%grid['fdNotesContentShort'].text%}
            				  </div>         					  
            				  <div class="km-note-list-footer">
             					 <span class="km-note-list-date" title="{%grid['docCreateTime'].text%}">
               						{%grid['docCreateTime'].text%}
             					 </span>
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
        <input type="button" class="left"  onclick="turnMySlideLeft();"></button>
        <input type="button" class="right" onclick="turnMySlideRight();"></button>
      </div>
      <div class="km-note-list-page">
        <span class="pageno"></span>
        <span>/</span>
        <span class="total"></span>
      </div>

</div>
$}
