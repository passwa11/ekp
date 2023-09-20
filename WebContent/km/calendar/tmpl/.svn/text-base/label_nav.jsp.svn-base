<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
var __exceptLabelIds = exceptLabelIds || '';
//ajax返回数据参数
var showLabelData = data["showLabelData"];
var allLabelData = data["allLabelData"];

var rsJson = getLabelFdId('myEvent');
var labelFdId = rsJson.labelFdId;
//取消-选中（默认选中）
var className = rsJson.selectedFlag == "0" ? 'label_div_off' : 'label_div_on';//__exceptLabelIds.indexOf('myEvent') > -1 ? 'label_div_off' : 'label_div_on';
className += ' lui_calendar_color_event';
{$
<li style="cursor: pointer;" labelFdId="{%labelFdId%}">
	<a href="javascript:void(0);" onclick="clickLabel(this,'myEvent');">
		<div class="{%className%}"></div>
			${ lfn:message('km-calendar:kmCalendar.nav.title') }
	</a>
</li>
$}

for(var i=0;i<showLabelData.length;i++){
    var rsJson = getLabelFdId(showLabelData[i].fdId);
    var labelFdId = rsJson.labelFdId;
    //取消-选中（默认选中）
    var className = rsJson.selectedFlag == "0" ? 'label_div_off' : 'label_div_on';// __exceptLabelIds.indexOf(showLabelData[i].fdId) > -1 ? 'label_div_off' : 'label_div_on';
	{$
	<li style="cursor: pointer;" labelFdId="{%labelFdId%}">
		<a href="javascript:void(0);"  onclick="clickLabel(this,'{%showLabelData[i].fdId%}');">
			<div style="background-color: {%showLabelData[i].fdColor%};" class="{%className%}"></div>
			{%showLabelData[i].fdName%}
		</a>
	</li>
	$}
}

var rsJson = getLabelFdId('myGroupEvent');
var labelFdId = rsJson.labelFdId;
//取消-选中（默认选中）
var className = rsJson.selectedFlag == "0" ? 'label_div_off' : 'label_div_on';// __exceptLabelIds.indexOf('myGroupEvent') > -1 ? 'label_div_off' : 'label_div_on';
className += ' lui_calendar_color_groupEvent';
{$
<li style="cursor: pointer;" labelFdId="{%labelFdId%}">
	<a href="javascript:void(0);" onclick="clickLabel(this,'myGroupEvent');">
		<div class="{%className%}"></div>
			${ lfn:message('km-calendar:kmCalendarMain.group.header.title') }
	</a>
</li>
$}

var rsJson = getLabelFdId('myNote');
var labelFdId = rsJson.labelFdId;
//取消-选中（默认选中）
var className = rsJson.selectedFlag == "0" ? 'label_div_off' : 'label_div_on';//__exceptLabelIds.indexOf('myNote') > -1 ? 'label_div_off' : 'label_div_on';
className += ' lui_calendar_color_note';
{$
<li style="cursor: pointer;" labelFdId="{%labelFdId%}">
  	<a href="javascript:void(0);"  onclick="clickLabel(this,'myNote');">
  		<div  class="{%className%}"></div>
  		${ lfn:message('km-calendar:module.km.calendar.tree.my.note') }
  	</a>
 </li>
 $}

//label-li,比对allLabelData，common=0则比对fdid，=1则比对commonflag，再做selectFlag的选中，以及li的fdid赋值
function getLabelFdId(labelFlag){
  var rtJson = {};
  $.each(allLabelData,function(i,v){
     if(v.isCommon == "0"){
        if(v.fdId == labelFlag){
          rtJson.selectedFlag = v.selectedFlag;
          rtJson.labelFdId = v.fdId;
          return false;
        }
     }else{
		if(v.commonLabel == labelFlag){
          rtJson.selectedFlag = v.selectedFlag;
          rtJson.labelFdId = v.fdId;
          return false;
		}
     }
  })

  return rtJson;

}