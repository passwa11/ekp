<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<html:form action="/km/imeeting/km_imeeting_main/kmImeetingMain.do">
			<html:hidden property="fdId" />
				<div style="margin-top: 80px;">
					<table class="tb_normal" width="98%" style="margin-top: 15px;">
						<tr>
							<%--原定会议时间--%>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOriginalDate"/>
							</td>			
							<td width="80%" colspan="3">
								<xform:text property="fdHoldDate" showStatus="readOnly"></xform:text>
								<span style="position: relative;">~</span>
								<xform:text property="fdFinishDate" showStatus="readOnly"></xform:text>
							</td>
						</tr>
						<tr>
							<%--选择提前结束时间--%>
							<td width="20%" class="td_normal_title" >
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdEarlyFinishDate" />
							</td>
							<td width="80%" colspan="3">
								<xform:datetime property="fdEarlyFinishDate" dateTimeType="datetime" validators="compareHoldTime compareFinishTime" required="true" showStatus="edit" subject="${lfn:message('km-imeeting:kmImeetingMain.fdEarlyFinishDate')}" onValueChange="changeInitHoldDuration"></xform:datetime>
							</td>
						</tr>
						<tr>
							<%--设置历时--%>
							<td class="td_normal_title" width=20%>
								<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
							</td>			
							<td width="80%" colspan="3">
								<input type="text" name="fdHoldDurationHour" validate="digits min(0) maxLength(4) validateDuration" class="inputsgl"
								 	style="width:50px;text-align:center;" onchange="changeDuration();" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
								<bean:message key="date.interval.hour"/>
								<input type="text" name="fdHoldDurationMin" validate="digits min(0) maxLength(4) validateDuration" class="inputsgl" 
									style="width:50px;text-align:center;" onchange="changeDuration();" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
								<bean:message key="date.interval.minute"/>
									<xform:text property="fdHoldDuration" showStatus="noShow" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}"/>
							</td>
						</tr>
					</table>		
				</div>
				<div style="margin: 20px auto;width: 200px;">
					<center>
						<ui:button text="${lfn:message('button.submit')}"  onclick="save();" id="earlyEndBtn"/>
		        	</center>
				</div>
		</html:form>
	</template:replace>
</template:include>
<script>Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js",null,"js");</script>
<script>
	
	seajs.use(['theme!form']);
	Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
	seajs.use([
		'km/imeeting/resource/js/dateUtil',
	    'lui/jquery',
	    'lui/dialog',
	    'lui/topic',
	    'lui/toolbar'
	    ], function(dateUtil,$, dialog,topic,toolbar) {
			//设置初始值
			var date=new Date();
			var month=date.getMonth()+1;
			var day=date.getDate();
			var hours=date.getHours();
			var minutes=date.getMinutes();
			if(month>=1&&month<=9){
				month="0"+month;
			}
			if(day>=1&&day<=9){
				day="0"+day;
			}
			if(hours>=0&&hours<=9){
				hours="0"+hours;
			}
			if(minutes>=0&&minutes<=9){
				minutes="0"+minutes;
			}
			var currentDate=date.getFullYear()+"-"+month+"-"+day+" "+hours+":"+minutes;
			//设置原定会议时间
			var fdHoldDate=Com_GetUrlParameter(window.location,"fdHoldDate");
			var fdFinishDate=Com_GetUrlParameter(window.location,"fdFinishDate");
			var fdEarlyFinishDate=Com_GetUrlParameter(window.location,"fdEarlyFinishDate");
			$('[name="fdHoldDate"]').val(fdHoldDate);
			$('[name="fdFinishDate"]').val(fdFinishDate);
			//初始化会议历时
			var fdHoldDuration;
			var fdHoldTime = fdHoldDate;
			var fdFinishTime=fdFinishDate;
			if(fdEarlyFinishDate==""){
				$('[name="fdEarlyFinishDate"]').val(currentDate);
				fdHoldDuration=dateUtil.parseDate(currentDate).getTime()-dateUtil.parseDate(fdHoldDate).getTime();
			}else{
				$('[name="fdEarlyFinishDate"]').val(fdEarlyFinishDate);
				fdHoldDuration=dateUtil.parseDate(fdEarlyFinishDate).getTime()-dateUtil.parseDate(fdHoldDate).getTime();
				//fdFinishTime=fdEarlyFinishDate;
			}
			var timeObj=dateUtil.splitTime({"ms":fdHoldDuration});
			$('[name="fdHoldDurationHour"]').val(timeObj.hour);
			$('[name="fdHoldDurationMin"]').val(timeObj.minute);
			window.changeInitHoldDuration=function(){
				var fdEarlyFinishDate = $('[name="fdEarlyFinishDate"]').val();
				if(fdEarlyFinishDate!=''){
					var fdHoldDuration=dateUtil.parseDate(fdEarlyFinishDate).getTime()-dateUtil.parseDate(fdHoldDate).getTime();
					var timeObj=dateUtil.splitTime({"ms":fdHoldDuration});
					$('[name="fdHoldDurationHour"]').val(timeObj.hour);
					$('[name="fdHoldDurationMin"]').val(timeObj.minute); 
				}else{
					$('[name="fdHoldDurationHour"]').val('');
					$('[name="fdHoldDurationMin"]').val('');
				}
			}
			//修改会议历时时触发
			window.changeDuration=function(){
				var fdHoldDurationHour=$('[name="fdHoldDurationHour"]').val();
				var fdHoldDurationMin=$('[name="fdHoldDurationMin"]').val();
				var totalHour=dateUtil.mergeTime({"hour":fdHoldDurationHour,"minute":fdHoldDurationMin},"ms");
				$('[name="fdHoldDuration"]').val(totalHour);
			};
			var validation=$KMSSValidation();
			//校验函数:校验选择的提前结束时间不能晚于结束时间
			var _compareFinishTime=function(){ 
				var fdEarlyFinishDate=$('[name="fdEarlyFinishDate"]').val();
				var result=true;
				//提前结束时间不能小于开始时间，不能大于结束时间
				if(dateUtil.parseDate(fdEarlyFinishDate).getTime()>=dateUtil.parseDate(fdFinishTime).getTime()){
					result=false;
					$("#earlyEndBtn").hide();
				}else{
					$("#earlyEndBtn").show();
				} 
				return result;
			}
			
			//校验函数:校验选择的提前结束时间不能早于开始时间
			var _compareHoldTime = function() {
				var fdEarlyFinishDate=$('[name="fdEarlyFinishDate"]').val();
				var result=true;
				if(dateUtil.parseDate(fdEarlyFinishDate).getTime() <= dateUtil.parseDate(fdHoldTime).getTime()){
					result=false;
					$("#earlyEndBtn").hide();
				}else{
					$("#earlyEndBtn").show();
				}
				return result;
			}
		
			//自定义校验器:校验选择的提前结束时间不能晚于结束时间
			validation.addValidator('compareFinishTime','${lfn:message("km-imeeting:kmImeetingMain.fdEarlyFinishDate.tip")}',function(v, e, o){
				 var fdEarlyFinishDate=document.getElementsByName('fdEarlyFinishDate')[0];
				 var result=_compareFinishTime();
				 if(!result){
					 KMSSValidation_HideWarnHint(fdEarlyFinishDate); 
				 }
				 return result;
			});
			
			//自定义校验器:校验选择的提前结束时间不能早于开始时间
			validation.addValidator('compareHoldTime','${lfn:message("km-imeeting:kmImeetingMain.fdEarlyFinishDate.tip2")}',function(v, e, o){
				 var fdEarlyFinishDate=document.getElementsByName('fdEarlyFinishDate')[0];
				 var result=_compareHoldTime();
				 if(!result){
					 KMSSValidation_HideWarnHint(fdEarlyFinishDate); 
				 }
				 return result;
			});
			
			var _validateDuration = function(){
				var fdHoldDurationHour = $('[name="fdHoldDurationHour"]'),
					fdHoldDurationMin = $('[name="fdHoldDurationMin"]'),
					fdHoldDate=$('[name="fdHoldDate"]'),
					fdEarlyFinishDate=$('[name="fdEarlyFinishDate"]');
				if(fdHoldDurationHour.val() && fdHoldDurationMin.val() && fdHoldDate.val() && fdEarlyFinishDate.val()){
					var realDuration =  _caculateDuration(fdHoldDate.val(),fdEarlyFinishDate.val())[0],
						duration = fdHoldDurationHour.val()* 3600 * 1000 + fdHoldDurationMin.val() * 60 * 1000,
						durationValidator = validation.getValidator('validateDuration');
					var ___name = '${lfn:message("km-imeeting:kmImeetingMain.fdHoldDuration")}';
					if(duration > realDuration){
						var ___error = '${lfn:message("km-imeeting:kmImeetingMain.fdHoldDuration.tip")}';
						___error = ___error.replace(___name,'{name} ');
						durationValidator.error = ___error;
						$("#earlyEndBtn").hide();
						return false;
					}else if(duration <= 0){
						var ___error = '${lfn:message("km-imeeting:kmImeetingMain.fdHoldDuration.zore.tip")}';
						___error = ___error.replace(___name,'{name} ');
						durationValidator.error = ___error;
						$("#earlyEndBtn").hide();
						return false;
					}else{
						$("#earlyEndBtn").show();
					}
				}
				return true;
			};
			//计算会议历时时间,返回数组,依次为:总时差、小时时差、分钟时差、……
			var _caculateDuration=function(start,end){
				if( start && end ){
					start=dateUtil.parseDate(start);
					end=dateUtil.parseDate(end);
					if(start.getTime()<end.getTime()){
						var total=end.getTime()-start.getTime();
						var hour=parseInt((end.getTime()-start.getTime() )/(1000*60*60));
						var minute=parseInt((end.getTime()-start.getTime() )%(1000*60*60)/(1000*60));
						return [total,hour,minute];
					}else{
						return [0.0,0,0];
					}
				}
			};
			//自定义校验器:校验会议历时不能大于会议实际时差
			validation.addValidator('validateDuration','${lfn:message("km-imeeting:kmImeetingMain.fdHoldDuration.tip")}',function(v,e,o){
				var fdHoldDurationHour = document.getElementsByName('fdHoldDurationHour')[0],
					result = true;
				if(e.name=='fdHoldDurationMin'){//fdHoldDurationMin的这个校验与fdHoldDurationHour的相同，直接执行fdHoldDurationHour的
					 validation.validateElement(fdHoldDurationHour);
				}else{
					result = _validateDuration();
				}
				return result;
			});
			window.save=function(){
				var msg = "<bean:message bundle='km-imeeting' key='kmImeetingMain.opt.earlyEnd.tip'/>";
				if(!_compareHoldTime()){
					dialog.alert('${lfn:message("km-imeeting:kmImeetingMain.fdEarlyFinishDate.tip")}');
					return;
				}
				if(!_compareFinishTime()){
					dialog.alert('${lfn:message("km-imeeting:kmImeetingMain.fdEarlyFinishDate.tip2")}');
					return;
				}
				if(!_validateDuration()){
					dialog.alert('${lfn:message("km-imeeting:kmImeetingMain.fdHoldDuration.zore.tip")}');
					return;
				}
				dialog.confirm(msg,function(rtn){
					if(rtn == true){
						var earlyEndTime=$('[name="fdEarlyFinishDate"]').val();
						var fdHoldDuration=$('[name="fdHoldDuration"]').val();
						Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=earlyEndMeeting&fdId=${JsParam.fdId}&earlyEndTime='+earlyEndTime+'&fdHoldDuration='+fdHoldDuration,'_self');
					}
				});
				 
			};		
	});
</script>