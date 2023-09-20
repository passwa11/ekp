<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="../sys_time_area/resource/css/maincss.css"/>
		<link type="text/css" rel="stylesheet" href="../sys_time_area/resource/css/css.css"/>
	</template:replace>
	<template:replace name="title">
		<bean:message bundle="sys-time" key="table.sysTimeHoliday"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ sysTimeHolidayForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysTimeHolidayForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysTimeHolidayForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysTimeHolidayForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysTimeHolidayForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
<html:form action="/sys/time/sys_time_holiday/sysTimeHoliday.do">
 
<p class="txttitle"><bean:message bundle="sys-time" key="table.sysTimeHoliday"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHoliday.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<%-- 所属场所 --%>
    <%if(ISysAuthConstant.IS_AREA_ENABLED){ %>
		<tr>
			<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field_single.jsp" charEncoding="UTF-8">
                <c:param name="id" value="${sysTimeHolidayForm.authAreaId}"/>
            </c:import>
		</tr>
	<%} %>
	<tr>
		<td width="100%" colspan="4">
		<div class="lui_workforce_management_container" style="width: 100%">
			<div class="lui_workforce_management_tab">
		      <div class="lui_workforce_management_tab-content" style="padding: 5px 5px">
		        <ul class="lui_workforce_management_tab-content-list">
		          <li class="active">
		            <div class="lui_workforce_management-inner-tab">
		              <div class="lui_workforce_management-inner-tab-wrap">
		                <ul class="lui_workforce_management-inner-tab-list"  data-style="calendar">
		                	<li class="lui_icon_arrow arrowL"></li>
		                	<c:forEach items="${years}" var="tyear" varStatus="vstatus">
		                	<c:choose>
								<c:when test="${vstatus.index==0 }">
				                  <li id="fli" <c:if test="${tyear==year }">class="active"</c:if> >
				                  	${tyear }
				                  </li>
			                	</c:when>
			                	<c:when test="${vstatus.index<10 }">
				                   <li <c:if test="${tyear==year }">class="active"</c:if> >
				                  	${tyear }
				                  </li>
			                	</c:when>
			                	<c:when test="${vstatus.index>=10 }">
				                   <li style="display:none">
				                  	${tyear }
				                  </li>
				                 </c:when>
			                </c:choose>
		                	</c:forEach>
		                	<li class="lui_icon_arrow arrowR"></li>
		                </ul>
		              </div>
		              <div class="lui_workforce_management-inner-tab-content-wrap">
		                <ul class="lui_workforce_management-inner-tab-content-list">
		                  <li class="active">
		                  	<c:import url="/sys/time/sys_time_holiday_detail/sysTimeHolidayDetail_edit.jsp"
								charEncoding="UTF-8">
							</c:import>
		                  </li>
		                </ul>
		              </div>
		            </div>
		          </li>
		        </ul>
	      	</div>
	      </div>
	    </div>	      
	</td>			
	</tr>
	<c:if test="${ sysTimeHolidayForm.method_GET == 'edit' }">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHoliday.docCreator"/>
		</td><td width="85%" colspan="3">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHoliday.docCreateTime"/>
		</td><td width="85%" colspan="3">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>
	</tr>
	</c:if>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHoliday.remark"/>
		</td><td width="85%">
			<bean:message bundle="sys-time" key="sysTimeHoliday.remark.desc"/>
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
	Com_IncludeFile("doclist.js|jquery.js");
</script>
<script>
function init(){
		if (typeof document.addEventListener === 'function') Kalendae.util.domReady(function () {
			var els = Kalendae.util.$$('.auto-kal'),
				i = els.length,
				e,
				options,
				optionsRaw;
			while (i--) {
				e = els[i];
				optionsRaw = e.getAttribute('data-kal');
				options = (optionsRaw == null || optionsRaw == "") ? {} : (new Function('return {' + optionsRaw + '};'))();
				if (e.tagName === 'INPUT') {
					new Kalendae.Input(e, options);
				} else {
					new Kalendae(Kalendae.util.merge(options, {attachTo:e}));
				}

			}
		});
	}
	
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = check;
	function check(){
		var len = $("input[name$='.fdName']").length;
		var flag = false;
		var startDay,endDay,sDay,eDay,sd,ed;
		var ty = "";
		//休假日期开始时间要小于休假的结束时间
		for(var n=0;n<len;n++){
			startDay = getDT($("input[name='fdHolidayDetailList["+n+"].fdStartDay']").val());
			endDay = getDT($("input[name='fdHolidayDetailList["+n+"].fdEndDay']").val());
			if(startDay.getTime()>endDay.getTime()){
				ty = $("input[name='fdHolidayDetailList["+n+"].fdStartDay']").parentsUntil("tr").parent().attr("name");
				alert(ty+'<bean:message key="resource.period.type.year.name"/>('+$("input[name='fdHolidayDetailList["+n+"].fdName']").val()+')<bean:message bundle="sys-time" key="sysTimeHoliday.check1"/>');
				return false;
			}
		}
		//休假日期与补班日期的检查
		for(var n=0;n<len;n++){
			flag = innerDay(n);
			if(flag){
				ty = $("input[name='fdHolidayDetailList["+n+"].fdStartDay']").parentsUntil("tr").parent().attr("name");
				alert(ty+'<bean:message key="resource.period.type.year.name"/>('+$("input[name='fdHolidayDetailList["+n+"].fdName']").val()+')<bean:message bundle="sys-time" key="sysTimeHoliday.check2"/>');
				return false;
			}
			/* flag = innerHolidayDay(n);
			if(flag){
				ty = $("input[name='fdHolidayDetailList["+n+"].fdStartDay']").parentsUntil("tr").parent().attr("name");
				alert(ty+'<bean:message key="resource.period.type.year.name"/>('+$("input[name='fdHolidayDetailList["+n+"].fdName']").val()+')<bean:message bundle="sys-time" key="sysTimeHoliday.check2.holiday"/>');
				return false;
			} */
			
		}
		//同一个年份标签里面不能设置其他年份的数据
		for(var n=0;n<len;n++){
		
			startDay = $("input[name='fdHolidayDetailList["+n+"].fdStartDay']").val();
			
			if(startDay!=""&&startDay.length>4){
				if ("en-us"==Com_Parameter.Lang) {
					startDay = startDay.substring(6,10);
				}else{
					startDay = startDay.substring(0,4);
				}
			}
			ty = $("input[name='fdHolidayDetailList["+n+"].fdStartDay']").parentsUntil("tr").parent().attr("name");
			if($.trim(startDay)!=ty){
				alert(ty+'<bean:message bundle="sys-time" key="sysTimeHolidayDetail.fdYear.repeat"/>');
				return false;
			}
			var patchDay = $("input[name='fdHolidayDetailList["+n+"].fdPatchDay']").val();
			if(patchDay){
				var pds = patchDay.split(",");
				var pd;
				for(var dd=0;dd<pds.length;dd++){
					pd = pds[dd];
					if(pd!=""&&pd.length>4){
						if ("en-us"==Com_Parameter.Lang) {
							pd = pd.substring(6,10);
						}else{
							pd = pd.substring(0,4);
						}
						if($.trim(pd)!=ty){
							alert(ty+'<bean:message bundle="sys-time" key="sysTimeHolidayDetail.fdYear.patch.repeat"/>');
							return false;
						}
					}
				}
			}
			
			var patchHolidayDay = $("input[name='fdHolidayDetailList["+n+"].fdPatchHolidayDay']").val();
			if(patchHolidayDay){
				var pds = patchHolidayDay.split(",");
				var pd;
				for(var dd=0;dd<pds.length;dd++){
					pd = pds[dd];
					if(pd!=""&&pd.length>4){
						if ("en-us"==Com_Parameter.Lang) {
							pd = pd.substring(6,10);
						}else{
							pd = pd.substring(0,4);
						}
						if($.trim(pd)!=ty){
							alert(ty+'<bean:message bundle="sys-time" key="sysTimeHolidayDetail.fdYear.patch.repeat"/>');
							return false;
						}
					}
				}
			}
		}
		//节假日的休假日期检查
		for(var n=0;n<len;n++){
			startDay = getDT($("input[name='fdHolidayDetailList["+n+"].fdStartDay']").val());
			endDay = getDT($("input[name='fdHolidayDetailList["+n+"].fdEndDay']").val());
			for(var x=0;x<len;x++){
				if(n==x)
					continue;
				sDay = getDT($("input[name='fdHolidayDetailList["+x+"].fdStartDay']").val());
				eDay = getDT($("input[name='fdHolidayDetailList["+x+"].fdEndDay']").val());
				if(!(eDay.getTime()<startDay.getTime()||endDay.getTime()<sDay.getTime())){
					ty = $("input[name='fdHolidayDetailList["+n+"].fdStartDay']").parentsUntil("tr").parent().attr("name");
					alert(ty+'<bean:message key="resource.period.type.year.name"/>('+$("input[name='fdHolidayDetailList["+n+"].fdName']").val()+')<bean:message bundle="sys-time" key="sysTimeHoliday.check3"/>'+(startDay.getYear()+1900)+'<bean:message key="resource.period.type.year.name"/>('+$("input[name='fdHolidayDetailList["+x+"].fdName']").val()+')<bean:message bundle="sys-time" key="sysTimeHoliday.check4"/>');
					return false;
				}
			}
		}
		
	    //节假日补班日期检查
	    var patchDayArray = new Array(); 
	    var rowArray = new Array(); //保存行数，即i
	    
		for(var i= 0; i < len; i++)
		{
			 var patchDay = $("input[name='fdHolidayDetailList["+i+"].fdPatchDay']").val();
			 var pds = patchDay.split(",");
			 if(patchDay != "")
			{
				 for(var j = 0; j < pds.length; j++)
					{
						    var index = patchDayArray.indexOf(pds[j]);
							if( index > -1)
							{
								alert(ty+'<bean:message key="resource.period.type.year.name"/>('+
										$("input[name='fdHolidayDetailList["+rowArray[index]+"].fdName']").val()+
										')<bean:message bundle="sys-time" key="sysTimeHoliday.check3"/>'+
										(startDay.getYear()+1900)+'<bean:message key="resource.period.type.year.name"/>('+
												$("input[name='fdHolidayDetailList["+i+"].fdName']").val()+
												')<bean:message bundle="sys-time" key="sysTimeHoliday.check5"/>');
								return false;
							}
							else
							{
								patchDayArray.push(pds[j]);
								rowArray.push(i);
							} 
					}
			}
		}
	    
		//节假日补班日期检查
	   /*  var patchHolidayDayArray = new Array(); 
	    var rowHolidayArray = new Array(); //保存行数，即i
	    
		for(var i= 0; i < len; i++)
		{
			 var patchDay = $("input[name='fdHolidayDetailList["+i+"].fdPatchHolidayDay']").val();
			 var pds = patchDay.split(",");
			 if(patchDay != "")
			{
				 for(var j = 0; j < pds.length; j++)
					{
						    var index = patchHolidayDayArray.indexOf(pds[j]);
							if( index > -1)
							{
								alert(ty+'<bean:message key="resource.period.type.year.name"/>('+
										$("input[name='fdHolidayDetailList["+rowHolidayArray[index]+"].fdName']").val()+
										')<bean:message bundle="sys-time" key="sysTimeHoliday.check3"/>'+
										(startDay.getYear()+1900)+'<bean:message key="resource.period.type.year.name"/>('+
												$("input[name='fdHolidayDetailList["+i+"].fdName']").val()+
												')<bean:message bundle="sys-time" key="sysTimeHoliday.check5.holiday"/>');
								return false;
							}
							else
							{
								patchHolidayDayArray.push(pds[j]);
								rowHolidayArray.push(i);
							} 
					}
			}
		} */
		
		return true;
	}
	
	function innerDay(len){
		var flag = false;//默认补班时间不在节假日里面
		var patch = $("input[name='fdHolidayDetailList["+len+"].fdPatchDay']").val();
		if(patch==null||patch=="")
			return flag;
		var startDay = getDT($("input[name='fdHolidayDetailList["+len+"].fdStartDay']").val());
		var endDay = getDT($("input[name='fdHolidayDetailList["+len+"].fdEndDay']").val());
		var patchDay;
		if(patch.indexOf(",")!=-1){
			for(var n=0;n<patch.split(",").length;n++){
				patchDay = getDT($.trim(patch.split(",")[n]));
				if(patchDay.getTime()>=startDay.getTime()&&patchDay.getTime()<=endDay.getTime()){
					flag = true;
					break;
				}
			}
		}else{
			patchDay = getDT($.trim(patch));
			if(patchDay.getTime()>=startDay.getTime()&&patchDay.getTime()<=endDay.getTime()){
				flag = true;
			}
		}
		return flag;
	}
	function innerHolidayDay(len){
		var flag = false;//默认补班时间不在节假日里面
		var patch = $("input[name='fdHolidayDetailList["+len+"].fdPatchHolidayDay']").val();
		if(patch==null||patch=="")
			return flag;
		var startDay = getDT($("input[name='fdHolidayDetailList["+len+"].fdStartDay']").val());
		var endDay = getDT($("input[name='fdHolidayDetailList["+len+"].fdEndDay']").val());
		var patchDay;
		if(patch.indexOf(",")!=-1){
			for(var n=0;n<patch.split(",").length;n++){
				patchDay = getDT($.trim(patch.split(",")[n]));
				if(patchDay.getTime()>=startDay.getTime()&&patchDay.getTime()<=endDay.getTime()){
					flag = true;
					break;
				}
			}
		}else{
			patchDay = getDT($.trim(patch));
			if(patchDay.getTime()>=startDay.getTime()&&patchDay.getTime()<=endDay.getTime()){
				flag = true;
			}
		}
		return flag;
	}
	
	function getDT(strDate){
		var date = new Date();
		 if(strDate==null||strDate=="")
			return date;
		date = Com_GetDate(strDate); 
		return date;
	}
</script>
<script>
	function addRow(){
		var row = DocList_AddRow();
		var len = $("input[name$='.fdName']").length-1;
		$($("input[name$='.fdName']")[len]).parentsUntil("tr").parent().attr("name",$.trim($('.lui_workforce_management-inner-tab-list>li.active').text()));
		$($("input[name$='.fdName']")[len]).parentsUntil("tr").parent().attr("nm","dls");
	}
	/*内选项卡*/
	$('.lui_workforce_management-inner-tab-list>li').not(".lui_icon_arrow").click(function() {
	  $(this).parent().find('li').removeClass('active');
	  $(this).addClass('active');
	  var year = $.trim($('.lui_workforce_management-inner-tab-list>li.active').text());
	  $("#TABLE_DocList tr[name="+year+"][nm='dls']").show();
	  $("#TABLE_DocList tr[name!="+year+"][nm='dls']").hide();
	})
	$('.lui_workforce_management-inner-tab-list>li.arrowR').click(function() {
		var init = $(this).prev().is(':visible');
		if(!init){
			var len = $(this).prevUntil(":visible").length;
			var alen = $('.lui_workforce_management-inner-tab-list>li').length;
			$('.lui_workforce_management-inner-tab-list>li:eq('+(alen-len-1)+')').show();
			$('.lui_workforce_management-inner-tab-list>li:eq('+(alen-11-len)+')').hide();
			$('.lui_workforce_management-inner-tab-list>li:eq('+(alen-11-len+1)+')').click();
		}else{
			var year = $.trim($(this).prev().text());
		 	$(this).before("<li>"+(parseInt(year)+1)+"</li>");
		 	$(this).prev().click(function(){
		 		$(this).parent().find('li').removeClass('active');
		 		$(this).addClass('active');
		 		$("#TABLE_DocList tr[name="+(parseInt(year)+1)+"][nm='dls']").show();
		 		$("#TABLE_DocList tr[name!="+(parseInt(year)+1)+"][nm='dls']").hide();
		 	});
		 	var len = $('.lui_workforce_management-inner-tab-list>li:hidden').length;
		 	$('.lui_workforce_management-inner-tab-list>li:eq('+(len+1)+')').hide();
		 	$('.lui_workforce_management-inner-tab-list>li:eq('+(len+2)+')').click();
		}
	})
	$('.lui_workforce_management-inner-tab-list>li.arrowL').click(function() {
	 	var len = $(this).nextUntil("li:visible").length;
	 	if(len>0){
	 		var hl = $('.lui_workforce_management-inner-tab-list>li:eq('+(len)+')');
	 		hl.show();
	 		hl.click();
	 		$('.lui_workforce_management-inner-tab-list>li:not(".lui_icon_arrow"):eq('+(len+9)+')').hide();
	 	}
	})
	$(function(){
		var year = $.trim($('.lui_workforce_management-inner-tab-list>li.active').not(".lui_icon_arrow").text());
		$("#TABLE_DocList tr[name!="+year+"][nm='dls']").hide();
		$("#TABLE_DocList tr[name="+year+"][nm='dls']").show();
	});
</script>
</html:form>

	</template:replace>
</template:include>