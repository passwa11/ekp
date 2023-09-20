<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.DateUtil,java.util.Date,net.sf.json.JSONArray,com.landray.kmss.util.DbUtils" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%
	request.setAttribute("nowDateTime", DbUtils.getDbTime().getTime());
	Object array= request.getAttribute("_fdLocations");
	String _fdLocationsArr=array.toString();
	request.setAttribute("_fdLocationsArr", _fdLocationsArr.replace("\"","&quot;"));
%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
		${ lfn:message('sys-attend:mui.tosign') }
	</template:replace>
	
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/map/mobile/resource/css/location.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/edit.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content"> 
		<html:form action="/sys/attend/sys_attend_main/sysAttendMain.do?method=save&pid=${KMSS_Parameter_CurrentUserId }">
			<html:hidden property="fdOsdReviewIsUpload" value="${_fdOsdReviewIsUpload }" />
			<html:hidden property="fdId" value="${fdAttendMainId }" />
			<html:hidden property="fdCategoryId" value="${HtmlParam.fdCategoryId }" />
			<html:hidden property="fdWorkTimeId" value="${HtmlParam.fdWorkTimeId }" />
			<html:hidden property="fdOutside" value="1" />
			<html:hidden property="fdWorkType" value="${HtmlParam.fdWorkType }" />
			<html:hidden property="fdLocation" value="${param.fdLocation }" />
			<html:hidden property="fdLng" value="" />
			<html:hidden property="fdLat" value="" />
			<html:hidden property="fdLatLng" value="" />
			<html:hidden property="fdStatus" value="1" />
			<html:hidden property="fdLimit" value="${_fdLimit }" />
			<html:hidden property="fdLeftTime" value="${_fdLeftTime }" />
			<html:hidden property="fdLateTime" value="${_fdLateTime }" />
			<html:hidden property="signTime" value="${_signTime }" />
			<html:hidden property="fdType" value="${_fdType }" />
			<html:hidden property="fdAddress" value="${param.fdLocation }" />
			<html:hidden property="fdNotifyResult" value="${_fdNotifyResult }" />
			<html:hidden property="fdManagerId" value="${_fdManagerId }" />
			<html:hidden property="isRestDay" value="${isRestDay }" />
			<html:hidden property="fdDeviceId" value="${HtmlParam.fdDeviceId }" />
			<html:hidden property="fdIsAcross" value="${HtmlParam.fdIsAcross }" />
			<html:hidden property="fdIsFlex" value="${_fdIsFlex}" />
			<html:hidden property="fdFlexTime" value="${_fdFlexTime }" />
			<html:hidden property="workTimeMins" value="${_workTimeMins }" />
			<html:hidden property="goWorkTimeMins" value="${_goWorkTimeMins }" />
			<html:hidden property="fdWorkDateLong" value="${_fdWorkDate }" />
			<html:hidden property="lastSignedTime" value="${HtmlParam.lastSignedTime }" />
			<html:hidden property="lastSignedStatus" value="${HtmlParam.lastSignedStatus }" />
			<html:hidden property="lastSignedState" value="${HtmlParam.lastSignedState }" />
			<html:hidden property="fdOverTimeType" value="${HtmlParam.fdOverTimeType }"/>
			<html:hidden property="inside" value="true" />
			
			<div id="map" class="muiAttendEditMap" style="height:350px;" data-dojo-type="sys/attend/mobile/resource/js/edit/AttendMapView" 
				data-dojo-props="fdLocations:${_fdLocationsArr },fdType:'${_fdType }',fdLimit:'${_fdLimit}'">
			</div>
			<div class="muiAttendEditList">
				<div class="muiAttendContentView">
					<div class="muiAttendEditLimitTip"><span class="muiLocationTxt">${ lfn:message('sys-attend:mui.location.here') }</span><span class="muiLimitTxt"></span></div>
					<div class="muiAttendEditAddress"><i class="mui mui-location"></i><span></span></div>
					
					<xform:textarea placeholder="${ lfn:message('sys-attend:mui.add.description') }" title="${ lfn:message('sys-attend:mui.add.description') }" validators="maxLength(50)" property="fdDesc" mobile="true" />
					<div class="muiDescTip">0/50</div>
					<div id="td_attachments">
						<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysAttendMainForm"></c:param>
							<c:param name="fdKey" value="attachment" />
							<c:param name="fdAttType" value="pic"></c:param>
							<c:param name="capture" value="sysCamera"></c:param>
						</c:import>
					</div>
				</div>
				
			</div>
			<div class="muiAttendEditToolbar">
				 <button type="button" onclick="commitMethod()" class="muiAttendEditBtn"> <span class="muiBtnTime"></span><span class="muiBtnTxt">${ lfn:message('sys-attend:mui.locating') }...</span></button>
			 </div>					
		</html:form>
	</template:replace>
</template:include>
<script type="text/javascript">
	require(['dojo/topic','mui/dialog/Tip',"sys/attend/map/mobile/resource/js/common/MapUtil","dojo/ready","dojo/dom-attr","mui/util",
	         "mui/device/adapter","dojo/dom-class","dojo/dom-style","dojo/query","mui/dialog/Confirm","dijit/registry","mui/coordtransform",
	         "sys/attend/mobile/resource/js/attachment/attachment","dojo/_base/lang","mui/form/ajax-form"],
		function(topic,Tip,MapUtil,ready,domAttr,util,adapter,domClass,domStyle,query,Confirm,registry,coordtransform,attachmentUtil,lang,ajaxForm){
		var fdWorkType = "${JsParam.fdWorkType }";
		var currentTimeDom = query('.muiAttendEditToolbar .muiAttendEditBtn .muiBtnTime')[0];
		var nowDateTime = new Date();
		nowDateTime.setTime(${nowDateTime});
		
		ready(function(){
			signTimeCounter();
			setInterval(signTimeCounter,1000);
			setInterval(refreshLocationByTime,30000);
			if(isEnableSecurity()){//开启安全校验
				var fdSecurityMode = "${_fdSecurityMode}";
				if(fdSecurityMode=='camera'){
					//topic.publish('attachmentObject_attachment_addItem' , this, {});
					domStyle.set(query('#td_attachments .muiFormUploadWrap')[0],'display','none');
					var imageFileOSPath = "${JsParam.imageFileOSPath}";
					var html = query('#td_attachments')[0].innerHTML;
					query('#td_attachments')[0].innerHTML = html + "<img style='height: 6rem;' src='" +decodeURIComponent(imageFileOSPath) + "'>";
				}
			}
		});
		ajaxForm.ajaxForm("[name='sysAttendMainForm']", {
			success:function(result){
				window.console.log('打卡成功返回,result:'+JSON.stringify(result));
				if(result.status==true){
					Tip.success({
						text: "${lfn:message('sys-mobile:mui.return.success') }", 
						callback: function(){
							var categoryId = '${JsParam.fdCategoryId}';
							if(categoryId) {
								window.location.href=util.formatUrl("/sys/attend/mobile/index.jsp?categoryId=" + categoryId); 
							} else {
								window.location.href=util.formatUrl("/sys/attend/mobile"); 
							}
						}, 
						cover: true
					});
				}else{
					var errTxt = "${lfn:message('sys-attend:mui.sign.fail') }";
					var options = {
						text : errTxt,
						callback : function(){
							window.location.href=util.formatUrl("/sys/attend/mobile");
						}
					};
					Tip.fail(options);
					return;
				}
			},
			error:function(result){
				window.console.log('打卡失败返回,result:'+result);
				var errTxt = "${lfn:message('sys-attend:mui.sign.fail') }";
				var options = {
					text : errTxt,
					callback : function(){
						window.location.href=util.formatUrl("/sys/attend/mobile");
					}
				};
				if(result && result[0]){
					if(result[0].code=='01'){
						options.text= "${lfn:message('sys-attend:mui.sign.device.fail') }";
						//delete options.callback;
					}
					if(result[0].code=='02'){
						options.text= "${lfn:message('sys-attend:mui.sign.duplicate.fail') }";
						//delete options.callback;
					}
				}
				Tip.fail(options);
			}
		});
		window.signTimeCounter =function(){
			var date = nowDateTime;
			date.setSeconds(date.getSeconds()+1);
			currentTimeDom.innerHTML=timeFormat(date.getHours())+':' + timeFormat(date.getMinutes())+":" + timeFormat(date.getSeconds());
		};
		window.timeFormat = function(v){
			return v >= 10 ? v : "0" + v;
		};
		window.refreshLocationByTime = function(){
			if(window.__currentMapObj){
				window.__currentMapObj.onCurGeoClick();
			}
		};
		window.onPanelToggle =function(){
			var winH = util.getScreenSize().h;
			viewH = 183 + 53;
			domStyle.set(query('.muiAttendEditList .muiAttendContentView')[0],'maxHeight',viewH+'px');
			domStyle.set(query('#map')[0],'height',(winH-viewH)+'px');
			setTimeout(function(){
				domStyle.set(query('.muiAttendEditMap .muiLocationDialog')[0],'height',(winH-viewH)+'px');
			},1000);
		};
		window.isAttendBussStatus = function(fdSignedStatus){
			if(fdSignedStatus=='4' || fdSignedStatus =='5' || fdSignedStatus =='6'){
				return true;
			}
			return false;
		}
		onPanelToggle();
		window.commitMethod=function(){
			//外勤是否必须拍照
			var formObj = document.sysAttendMainForm;
			var imgCount=query('#td_attachments .muiAttachmentEditItem').length-1;
			if(formObj.fdOsdReviewIsUpload.value=="1" && imgCount==0){
				Tip.fail({text:"${ lfn:message('sys-attend:sysAttendCategory.fdOsdReviewType.addphoto') }"});
				return;
			}
			var fdStatusDom = document.getElementsByName("fdStatus")[0];
			var fdLoaction = document.getElementsByName("fdLocation")[0];
			var fdLatLng = document.getElementsByName("fdLatLng")[0];
			var fdLimit = document.getElementsByName("fdLimit")[0];
			var fdAddress = document.getElementsByName("fdAddress")[0];
			var fdType = document.getElementsByName("fdType")[0].value;
			var fdOutside = document.getElementsByName("fdOutside")[0].value
			var fdDesc = document.getElementsByName("fdDesc")[0].value
			
			var fdIsAcross = document.getElementsByName("fdIsAcross")[0].value;
			var fdIsFlex = '${_fdIsFlex}';
			var fdFlexTime = parseInt('${_fdFlexTime}' || 0);
			var workTimeMins = parseInt('${_workTimeMins}' || 0);

			//上班标准时间
			var _goWorkTimeMins = parseInt('${_goWorkTimeMins}' || 0);

			var lastSignedTime = '${JsParam.lastSignedTime}';
			var lastSignedStatus = '${JsParam.lastSignedStatus}';
			var lastSignedState = '${JsParam.lastSignedState}';
			var _fdOutside = "${_outside}";
			
			if(fdDesc && fdDesc.length>50){
				Tip.fail({text:"${ lfn:message('sys-attend:mui.exc.reason.tips') }"});
				return;
			}
			if(!fdLatLng.value){
				Tip.fail({text:"${ lfn:message('sys-attend:mui.relocation') }"});
				return;
			}
			
			if(fdType==2){
				//判断是否范围(暂不处理，200米范围以内的会有问题)
				//if(document.getElementsByName("inside")[0].value=="false"||document.getElementsByName("inside")[0].value==false){
					//Tip.fail({
						//text:"${ lfn:message('sys-attend:mui.signOutside.notAllow') }"
					//});
					//return;
				//}		
				Com_Submit(formObj, 'save','fdLocation:fdLng:fdLat:fdLatLng'); 
				return;
			}
			if(_fdOutside !='true'){
				if(fdOutside=='1'){
					Tip.fail({text:"${ lfn:message('sys-attend:mui.notAllow.signOutside') }"});
					return;
				}
			}

			var fdStatus = "1";
			var now = new Date();
			var fdLateTime = parseInt("${_fdLateTime }" || 0);
			var fdLeftTime = parseInt("${_fdLeftTime }" || 0);
			var signTime = parseInt("${_signTime }" || 0);
			var overTimeType = parseInt("${_overTimeType }" || 1);
			var fdLimitValue = fdLimit.value || 0;
			var nowMins =this.getNowMins();// now.getHours() *60 + now.getMinutes();
			//判断是否早退或迟到
			if(fdIsFlex == 'true'){
				//是否弹性上下班
				if(fdWorkType=='0'){
					//上班，超过弹性时间，则认为是迟到
					if(nowMins > (signTime + fdFlexTime)){
						fdStatus = '2';
					}
				} else {
					//已打卡的上班时间
					lastSignedTime = parseInt(lastSignedTime || 0);
					//提前多少分钟上班的（-30）
					var goWrokFlexMin = _goWorkTimeMins - lastSignedTime;
					//最大弹性时间为设置的。超过最大以设置最大的弹性分钟数为准
					if(goWrokFlexMin < fdFlexTime){
						//如果提前打卡的分钟数，在弹性时间范围内，则以多大的分钟数作为弹性分钟数
						if(goWrokFlexMin < 0){
							//比如设置30分钟，迟到1个小时，则没有弹性分钟数的概念
							if(goWrokFlexMin * -1  > fdFlexTime ){
								fdFlexTime =0;
							}else{
								fdFlexTime =goWrokFlexMin;
							}
						} else {
							fdFlexTime =goWrokFlexMin;
						}
					}
					//如果弹性时间小于0.则表示 上班迟到，则要下班标准时间 加上 迟到的时间，才不会早退
					if(fdFlexTime < 0 ){
						fdFlexTime = fdFlexTime * -1;
						if(nowMins < signTime){
							//打卡时间是标准打卡时间之前，则是迟到
							fdStatus = "3";
						}else if(nowMins <  signTime + fdFlexTime ){
							//打卡时间，小于弹性时间
							fdStatus = "3";
						}
					}else{
						//提前上班的弹性时间计算。
						if(nowMins < signTime - fdFlexTime ){
							//打卡时间是标准打卡时间 减去弹性时间 之前。算迟到
							fdStatus = "3";
						}
					}
				}
			} else {
				//允许迟到时间
				if(fdWorkType=='0'){
					//允许迟到时间
					if(nowMins > (signTime + fdLateTime)){
						fdStatus = "2";
					}
				} else {
					//允许早退
					if(nowMins < (signTime - fdLeftTime)){
						fdStatus = "3";
					}
					//
					// if(fdIsAcross == 'true'){
					// 	fdStatus = "1";
					// 	if(overTimeType==2){
					// 		if(nowMins < signTime){
					// 			fdStatus = "3";
					// 		}
					// 	}
					// } else {
					// 	if(nowMins < (signTime - fdLeftTime)){
					// 		fdStatus = "3";
					// 	}
					// }
				}
			}
			var _isResDay = "${isRestDay}";
			if(_isResDay=='true'){
				fdStatus = "1";
			}
			//出差/请假/外出
			var fdAttendMainId = "${fdAttendBussStatus }";
			if(isAttendBussStatus(fdAttendMainId)){
				fdStatus = fdAttendMainId;
			}
			fdStatusDom.value = fdStatus;
			if(fdStatus=='3'){
				Confirm('<div style="line-height:2rem;">'+"${ lfn:message('sys-attend:mui.sign.leftOrNot') }"+'</div>','',function(value){
					if(value==true){
						doOnSubmit(formObj);
					}
				});
				return;
			}else{
				doOnSubmit(formObj);
			}
		};
		var doSubmited = false;
		window.doOnSubmit = function(formObj){
			window.console.log('开始进入打卡...');
			if(doSubmited){
				return;
			}
			if(!checkAttRules()){
				Tip.fail({text:"${ lfn:message('sys-attend:mui.sign.fail') }"});
				return;
			}
			doSubmited = true;
			window.console.log('开始打卡...');
			Com_Submit(formObj, 'save','fdLocation:fdLng:fdLat:fdLatLng');
		};
		
		//打卡前检查提交附件
		window.checkAttRules =function(){
			var fileKey = "${JsParam.fileKey}";
			var fileName = "${JsParam.fileName}";
			if(!(isEnableSecurity() && fileKey)){
				return true;
			}
			var fdId = attachmentUtil.registFile({'filekey':fileKey,
				'name':fileName});
			if(!fdId){
				return false;
			}
			query('input[name="attachmentForms.attachment.attachmentIds"]')[0].value=fdId;
			return true;
		};
	
		window.setAttShowChange = function(){
			setTimeout(function(){
				var count = query('#td_attachments .muiAttachmentEditItem').length-1;
				if(count>=4){
					domStyle.set(query('#td_attachments .muiAttachmentEditOptItem')[0],'display','none');
				}else{
					domStyle.set(query('#td_attachments .muiAttachmentEditOptItem')[0],'display','');
				}
				var descDoms = query('#td_attachments .muiAttachmentEditItem .muiAttachmentItemB');
				for(var i = 0 ; i < descDoms.length;i++){
					if (domClass.contains(descDoms[i], "muiAttachmentMsg")){
					    continue;
					}
					domStyle.set(descDoms[i],'display','none');
				}
			},1);
			
		};
		
		window.isEnableSecurity = function(){
			var fdType = "${_fdType}";
			var fdSecurityMode = "${_fdSecurityMode}";
			if(fdType=='1' && (fdSecurityMode=='camera' || fdSecurityMode=='face')){
				return true;
			}
			return false;
		};
		window.formatDistance = function(value,fdLimit){
			var msg = "";
			var fdLimit = parseFloat(fdLimit);
			value = value-fdLimit;
			if(value>=1000){
				msg = Math.round(10 * (value/1000))/10 + "${ lfn:message('sys-attend:mui.kilometer') }";
			}else{
				msg = Math.round(10 * value)/10 + "${ lfn:message('sys-attend:mui.meter') }";
			}
			return msg;
		};
		window.validateDesc = function(){
			
		};
		
		window.getNowMins=function(){
			var now=nowDateTime;
			var tmpFdWorkDate = parseInt("${_fdWorkDate}");
			var workDate = new Date(tmpFdWorkDate);
			var overTimeType = "${_overTimeType}"=="2" ? 2 : 1;
			var nowMins = now.getHours() *60 + now.getMinutes();
			//跨天排班
			if(overTimeType==2){
				nowMins = (24*(now.getDate()-workDate.getDate())+now.getHours()) *60 + now.getMinutes();
			}else{
				var fdIsAcross = document.getElementsByName("fdIsAcross")[0].value;
				if(fdIsAcross=="true"){
					//如果标准打卡时间在当日，但是当前属于跨天时。则需要把当前打卡时间加上一天
					nowMins = (24*(now.getDate()-workDate.getDate())+now.getHours()) *60 + now.getMinutes();
				}
			}
			return nowMins;
		}
		
		topic.subscribe('attachmentObject_attachment_success',function(srcObj,evt){
			setAttShowChange();
		});
		topic.subscribe('attachmentObject_attachment_del',function(srcObj,evt){
			setAttShowChange();
		});
		topic.subscribe('/mui/textarea/onInput',function(srcObj,value){
			if(srcObj.name=='fdDesc'){
				var len =lang.trim(value).length;
				var descTipNode = query('.muiAttendContentView .muiDescTip');
				if(len<=50){
					domClass.remove(descTipNode[0],'muiLimitTxtCount')
				}else{
					domClass.add(descTipNode[0],'muiLimitTxtCount')
				}
				descTipNode.html(len+'/50');
			}
		});
		topic.subscribe('sys/attend/relocation/start',function(widget,evt){
			query('.muiAttendEditToolbar .muiAttendEditBtn .muiBtnTxt').html("${ lfn:message('sys-attend:mui.locating') }"+'...');
		});
		topic.subscribe('sys/attend/relocation/complete',function(widget,evt){
			if(!window.__currentMapObj){
				window.__currentMapObj = widget;
			}
			var latLng = evt.fdLatLng;//返回的最近打卡点的坐标
			var curLimit=evt.curLimit;//返回的最近打卡点的范围（新数据）
			var address = evt.address;
			var distance = evt.distance;//把所有打卡地点放到百度地图返回离当前人最近的打卡地点的距离
			var inside = evt.inside;
			document.getElementsByName("inside")[0].value=evt.inside;
			var fdLimit = curLimit||'${_fdLimit}' || 0;
			fdLimit = parseFloat(fdLimit);
			var latLngObj = MapUtil.getCoord(latLng);
			var fdType = document.getElementsByName("fdType")[0].value;
			
			var fdLoaction = document.getElementsByName("fdLocation")[0];
			var fdLat = document.getElementsByName("fdLat")[0];
			var fdLng = document.getElementsByName("fdLng")[0];
			var fdLatLng = document.getElementsByName("fdLatLng")[0];
			var fdAddress = document.getElementsByName("fdAddress")[0];
			var _fdOutside = "${_outside}";
			
			fdLat.value= latLngObj.lat;
			fdLng.value = latLngObj.lng;
			fdLatLng.value=latLng;
			fdAddress.value=address;
			fdLoaction.value = evt.fdLocation;
			
			query('.muiAttendEditAddress span').html(address);
			var btnNode = query('.muiAttendEditToolbar .muiAttendEditBtn');
			var btnTxt = "${ lfn:message('sys-attend:mui.outside.sign') }";
			if(fdType==1){
				//考勤
				var limitTxtNode = query('.muiAttendEditLimitTip .muiLimitTxt');
				if(inside){
					query('.muiAttendEditLimitTip .muiLimitTxt').html("${ lfn:message('sys-attend:mui.inside.sign.zone') }");
					domClass.remove(btnNode[0],'muiAttendEditOutside');
					domClass.remove(limitTxtNode[0],'muiOutside');
					document.getElementsByName("fdOutside")[0].value="0";
					btnTxt = "${ lfn:message('sys-attend:mui.normal.sign') }";
				}else{
					if(distance) {
						query('.muiAttendEditLimitTip .muiLimitTxt').html("${ lfn:message('sys-attend:mui.zone.distance') }".replace('{0}',formatDistance(distance,fdLimit)));
					}
					if(_fdOutside !='true'){
						//不允许外勤打卡
						domClass.add(btnNode[0],'muiSignInBtnOutsideDis');
					}else{
						domClass.add(btnNode[0],'muiAttendEditOutside');
					}
					domClass.add(limitTxtNode[0],'muiOutside');
					document.getElementsByName("fdOutside")[0].value="1";
				}
			}
			if(fdType==2){
				//签到
				btnTxt = "${ lfn:message('button.submit') }";
				if(fdLimit && distance){
					var limitTxtNode = query('.muiAttendEditLimitTip .muiLimitTxt');
					if(distance<=fdLimit){
						limitTxtNode.html("${ lfn:message('sys-attend:mui.inside.sign.zone') }");
						domClass.remove(limitTxtNode[0],'muiOutside');
					}else{
						limitTxtNode.html("${ lfn:message('sys-attend:mui.zone.distance') }".replace('{0}',formatDistance(distance,fdLimit)));
						domClass.add(limitTxtNode[0],'muiOutside');
						//domClass.add(btnNode[0],'muiSignInBtnOutsideDis');
						fdLoaction.value = address;
					}
				}else{
					fdLoaction.value = address;
				}
			}
			
			query('.muiAttendEditToolbar .muiAttendEditBtn .muiBtnTxt').html(btnTxt);
		});
		
});	


</script>


