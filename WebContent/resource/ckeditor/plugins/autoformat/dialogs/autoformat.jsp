<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<style>
.content{
	padding-top:5px;
   font-family: 'Microsoft YaHei',SimSun,Arial;
   font-size: 85.5%;
   color: #787878;
}

.tb_class{
   font-family: 'Microsoft YaHei',SimSun,Arial;
   font-weight:bold;
   font-size: 85.5%;
   color:#000000;
}

td
{
  text-align:left;
  padding-left:8px;
}

input[type=checkbox] {
  margin-right: 5px;
  cursor: pointer;
  font-size: 14px;
  width: 15px;
  height: 12px;
  position: relative;
  background-color: #FFFFFF;
}

input[type=checkbox]:after {
  position: absolute;
  width: 10px;
  height: 15px;
  top: 0;
  content: " ";
  background-color: #FFFFFF;
  color: #fff;
  display: inline-block;
  visibility: visible;
  padding: 0px 3px;
  border-radius: 3px;
  border-style: solid;
  border-color: black;
  border-width: 1px;
}

input[type=checkbox]:checked:after {
  content: "√";
  font-size: 12px;
  background-color: #4184F5;
}

</style>
<div class="content">
    ${ lfn:message('sys.layout.description') }
</div>
<div  style="padding-top:30px;">
	<table class="tb_class">
		<tr>
			<td><input type="checkbox" id="pBlank" checked value="　　">${ lfn:message('sys.layout.segment.space') }</input> </td>
			<td><input type="checkbox"  id="imgCenter" checked value=1>${ lfn:message('sys.layout.picture.center') }</input></td>
			<td><input type="checkbox" id="strongHolder"  checked value=1>${ lfn:message('sys.layout.stay.bold') }</input></td>
		</tr>
		<tr>
			<td><input type="checkbox" id="tableHolder"  checked value=1>${ lfn:message('sys.layout.stay.table') }</input></td>
			<td><input type="checkbox" id="linkHolder"  checked value=1>${ lfn:message('sys.layout.stay.link') }</input></td>
			<td>&nbsp;</td>
		</tr>
	</<table>
</div>
