<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<link rel="Stylesheet" href="<c:url value="/km/calendar/resource/css/calendar_label.css"/>" />
<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>"></script>
<script type="text/javascript" src="<c:url value="/km/calendar/resource/js/jquery.colorpicker.js"/>"></script>
<script type="text/javascript">
function ecodeHtml(str){
	return str.replace(/&/g,"&amp;")
	.replace(/</g,"&lt;")
	.replace(/>/g,"&gt;")
	.replace(/\"/g,"&quot;")
	.replace(/¹/g, "&sup1;")
	.replace(/²/g, "&sup2;")
	.replace(/³/g, "&sup3;")
}
function save(method) {
	var color = $(".color_ul li.select a").css("background-color");
	$('[name="fdColor"]').val(ecodeHtml(color));
	Com_Submit(document.kmCalendarAgendaLabelForm,method);
}

$(function () {
    $(".color_ul li").each(function () {
        $(this).click(function () {
            var color = $(this).css("background-color");
            $(".color_ul li.select a").css("background-color", color);
            $("#fdColor").val(color);
        });
    });
    $('.sel_color_txt').colorpicker({
        ishex: true, //是否使用16进制颜色值
        fillcolor: false,  //是否将颜色值填充至对象的val中
        target: null, //目标对象
        event: 'click', //颜色框显示的事件
        success: function (o, color) {
            $(o).css("background-color", color);
            $(".color_ul li.select a").css("background-color", color);
        }, //回调函数
        reset: function () { }
    });
});

window.onload = function(){
	$(".color_ul li.select a").css("background-color", "${kmCalendarAgendaLabelForm.fdColor}");
}
</script>
<html:form action="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do">
<div id="optBarDiv">
	<c:if test="${kmCalendarAgendaLabelForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="save('update');">
	</c:if>
	<c:if test="${kmCalendarAgendaLabelForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="save('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="save('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="km-calendar" key="km.calendar.tree.calendar.label.config"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-calendar" key="kmCalendarAgendaLabel.fdName"/>
		</td><td width="35%">
			<c:choose>
				<c:when test="${kmCalendarAgendaLabelForm.isAgendaLabel }">
					<xform:text property="fdName" style="width:85%" required="true" showStatus="readOnly"/>
				</c:when>
				<c:otherwise>
					<xform:text property="fdName" style="width:85%" required="true"/>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-calendar" key="kmCalendarAgendaLabel.fdColor"/>
		</td>
		<td>
			<input type="hidden" id="fdColor" name="fdColor" value="<c:out value='${kmCalendarAgendaLabelForm.fdColor}'></c:out>"/>
		 <div class="lui_calendar_label" style="display: block; width: 100%;min-height: 10px;">
               
                 <ul class="clrfix color_ul">
                     <li class="select"><a></a></li>
                     <li class="line"></li>
                     <li class="color_1"></li>
                     <li class="color_2"></li>
                     <li class="color_3"></li>
                     <li class="color_4"></li>
                     <li class="color_5"></li>
                     <li class="color_6"></li>
                     <li class="color_7"></li>
                     <li class="color_8"></li>
                     <li class="color_9"></li>
                     <li class="color_10"></li>
                     <li class="color_11"></li>
                 </ul>
                 </div>
       </td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-calendar" key="kmCalendarAgendaLabel.enable"/>
		</td>
		<td colspan="3">
			<xform:radio property="fdIsAvailable">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
		
	</tr>
	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-calendar" key="kmCalendarAgendaLabel.fdModelName"/>
		</td><td colspan="3">
		<c:choose>
			<c:when test="${kmCalendarAgendaLabelForm.isAgendaLabel==true || param.method=='edit'}">
				<xform:text property="fdAgendaModelName" style="width:85%" showStatus="readOnly" />
			</c:when>
			<c:otherwise>
				<xform:text property="fdAgendaModelName" style="width:85%" required="true"/>
			</c:otherwise>
		</c:choose>
			
		</td>
		
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-calendar" key="kmCalendarAgendaLabel.remark"/>
		</td><td colspan="3">
			<xform:text property="fdDescription" style="width:85%" />
		</td>
		
	</tr>
	
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="isAgendaLabel" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>