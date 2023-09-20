<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/km/imeeting/resource/css/book.css"></link>
	</template:replace>
	<template:replace name="content">
		<html:form action="/km/imeeting/km_imeeting_book/kmImeetingBook.do"  styleId="bookform">
			<html:hidden property="fdId" />
			<html:hidden property="docCreatorId" />
			<table class="tb_normal" width="98%" style="margin-top: 15px;">
				<tr>
					<td colspan="4" class="com_subject" style="font-weight: bold;"><bean:message bundle="km-imeeting" key="kmImeetingBook.baseInfo" /></td>
				</tr>
				<tr>
	     			<%--会议名称--%>
	              	<td width="15%" class="td_normal_title" >
	              		<bean:message bundle="km-imeeting" key="kmImeetingBook.fdName" />
	              	</td>
	              	<td width="85%" colspan="3" >
	              		<xform:text property="fdName" style="width:90%"></xform:text>
	              	</td>
	            </tr>
				<tr>
					<%--召开时间/结束时间--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate" />
					</td>
					<td width="85%" colspan="3">
						<xform:datetime property="fdHoldDate" dateTimeType="datetime" validators="after compareTime" required="true" onValueChange="changeHoldDate"></xform:datetime>
						<span style="position: relative;top:-5px;">~</span>
						<xform:datetime property="fdFinishDate" dateTimeType="datetime" validators="after compareTime" required="true"></xform:datetime>
						<input type="hidden" name="fdHoldDuration" />
						<div style="display: inline-block;">
							<ui:recurrence id="fdRecurrence" property="fdRecurrenceStr" customContainer="#customContainer" cfg-finishDate="fdFinishDate"></ui:recurrence>
						</div>
					</td>
				</tr>
				<%-- 所属场所 --%>
				<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
			            <c:param name="id" value="${kmImeetingBookForm.authAreaId}"/>
			    </c:import>
				<tr>
					<td colspan="4" class="customContainerTD">
						<div id="customContainer"></div>
					</td>
				</tr>
				<tr>
	     			<%--备注--%>
	              	<td width="15%" class="td_normal_title"  valign="top">
	              		<bean:message bundle="km-imeeting" key="kmImeetingBook.fdRemark" />
	              	</td>
	              	<td width="85%" colspan="3" >
	              		<xform:textarea property="fdRemark" style="width:90%"/>
	              	</td>
	            </tr>
	            <tr>
					<td colspan="4" class="com_subject" style="font-weight: bold;"><bean:message bundle="km-imeeting" key="kmImeetingBook.fdPlace.detail" /></td>
				</tr>
				<tr>
					<%--会议地点--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingBook.fdPlace" />
					</td>
					<td width="35%"  >
						<div style="display: none;">
							<input type="text" name="fdUserTime" value="${res.fdUserTime}" validate="validateUserTime"/>
						</div>
						<c:if test="${empty res }">
							<xform:dialog propertyId="fdPlaceId" propertyName="fdPlaceName" showStatus="edit" validators="validateUserTime"
				 				className="inputsgl" style="width:95%;" required="true"
				 				subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }">
						  	 	selectHoldPlace();
							</xform:dialog>
						</c:if>
						<c:if test="${not empty res }">
							<input type="hidden" name="fdPlaceId" value="${kmImeetingBookForm.fdPlaceId }" />
							<input type="hidden" name="fdPlaceName" value="${kmImeetingBookForm.fdPlaceName }" />
							<c:out value="${res.fdName}"></c:out>
						</c:if>
					</td>
					<%--保管员--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingRes.docKeeper" />
					</td>
					<td width="35%" >
						<span id="resKeeperName">
							<c:if test="${not empty res.docKeeper}">
								<c:choose>
									<c:when test="${res.docKeeper.fdIsAvailable }">
										<c:out value="${res.docKeeper.fdName}"></c:out>
									</c:when>
									<c:otherwise>
										<c:out value="${res.docKeeper.fdName}"></c:out><bean:message bundle="km-imeeting" key="kmImeetingRes.docKeeper.disable" />
									</c:otherwise>
								</c:choose>
							</c:if>
						</span>
					</td>
				</tr>
				<tr>
					<%--地点楼层--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingRes.fdAddressFloor" />
					</td>
					<td width="35%" >
						<span id="resAddressFloor">
							<c:out value="${res.fdAddressFloor}"></c:out>
						</span>
					</td>
						<%--容纳人数--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingRes.fdSeats" />
					</td>
					<td width="35%" >
						<span id="resSeats">
							<c:out value="${res.fdSeats}"></c:out>
						</span>
					</td>
				</tr>
				<tr>
					<%--设备详情--%>
					<td width="15%" class="td_normal_title" >
						<bean:message bundle="km-imeeting" key="kmImeetingRes.fdDetail" />
					</td>
					<td width="85%" colspan="3" >
						<span id="resDetail">
							<xform:textarea property="fdPlaceDetail" showStatus="view" style="width:95%" />
						</span>
					</td>
				</tr>
			</table>
			<div style="margin: 15px auto;width: 200px;">
				<center>
					<ui:button text="${lfn:message('button.save')}"  onclick="save();" />&nbsp;
					<c:if test="${kmImeetingBookForm.method_GET=='edit' }">
						<ui:button text="${lfn:message('button.delete')}" styleClass="lui_toolbar_btn_gray" onclick="del();" />&nbsp;
					</c:if>
			        <ui:button text="${lfn:message('button.close')}"  styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();"/> 
		        </center>
			</div>
		</html:form>
	</template:replace>
</template:include>
<script>Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js",null,"js");</script>
<script>
	seajs.use(['theme!form']);
	Com_Parameter.CloseInfo='<bean:message key="message.closeWindow"/>';
	seajs.use([
		'km/imeeting/resource/js/dateUtil',
	    'lui/jquery',
	    'lui/dialog',
	    'lui/topic',
	    'lui/toolbar'
	    ], function(dateUtil,$, dialog,topic,toolbar) {
			var validation=$KMSSValidation();
			
			//选择会议室
			window.selectHoldPlace = function(){
				var fdHoldDate=$('[name="fdHoldDate"]').val(); // 召开时间
				var fdFinishDate= $('[name="fdFinishDate"]').val(); // 结束时间
				var resId=$('[name="fdPlaceId"]').val(); // 地点ID
				var resName=$('[name="fdPlaceName"]').val();//地点Name
				var url="/km/imeeting/km_imeeting_res/kmImeetingRes_showResDialog.jsp?fdHoldDate="+fdHoldDate+"&fdFinishDate="+fdFinishDate+"&resId="+resId+"&resName="+encodeURI(resName);
				dialog.iframe(url,'${lfn:message("km-imeeting:kmImeetingMain.selectHoldPlace")}',function(arg){
					if(arg){
						$('[name="fdPlaceId"]').val(arg.resId);
						$('[name="fdPlaceName"]').val(arg.resName);
						$('[name="fdUserTime"]').val(arg.resUserTime); //使用时长
						$("#resKeeperName").html(arg.docKeeperName);
						$("#resAddressFloor").html(arg.resFdAddressFloor);
						$("#resSeats").html(arg.resFdSeats);
						$("#resDetail").html(arg.resFdDetail);
					}
					validation.validateElement( $('[name="fdPlaceName"]')[0] );
				},{width:800,height:520});
			};
			
			//校验召开时间不能晚于结束时间
			var _compareTime=function(){
				var fdHoldDate=$('[name="fdHoldDate"]');
				var fdFinishedDate=$('[name="fdFinishDate"]');
				var result=true;
				if( fdHoldDate.val() && fdFinishedDate.val() ){
					var start=Com_GetDate(fdHoldDate.val());
					var end=Com_GetDate(fdFinishedDate.val());
					if( start.getTime()>=end.getTime() ){
						result=false;
					}
				}
				return result;
			};
			
			//自定义校验器:校验召开时间不能晚于结束时间
			validation.addValidator('compareTime','${lfn:message("km-imeeting:kmImeetingMain.fdDate.tip")}',function(v, e, o){
				 var docStartTime=document.getElementsByName('fdHoldDate')[0];
				 var docFinishedTime=document.getElementsByName('fdFinishDate')[0];
				 var result= _compareTime();
				 if(result==false){
					KMSSValidation_HideWarnHint(docStartTime);
					KMSSValidation_HideWarnHint(docFinishedTime);
				}
				//存在地点,进行会议时长校验
				 var fdUserTime = $('[name="fdUserTime"]');
				 if(fdUserTime.val()){
					 validation.validateElement(fdUserTime[0]);
				 }
				return result;
			});
			
			//校验最大使用时长
			var _validateUserTime = function(){
				var userTime = $('[name="fdUserTime"]'),
					fdPlaceId = $('[name="fdPlaceId"]');
				if(fdPlaceId.val() && userTime.val()){
					var start = $('[name="fdHoldDate"]').val(),
						end = $('[name="fdFinishDate"]').val(),
						duration = _caculateDuration(start,end)[0];
					var canUseLength =  userTime.val() * 3600 * 1000;
					if( parseInt(canUseLength)!=0 && duration > userTime.val() * 3600 * 1000 ){
						return false;
					}else{
						return true;
					}
				}
				return true;
			};
			
			//自定义校验器:校验当前会议用时是否超过了该会议室最大使用时长
			validation.addValidator('validateUserTime','${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}',function(v,e,o){
				var result = _validateUserTime();
				if(result == false){
					validator = this.getValidator('validateUserTime');
					var error = '${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}';
					error = error.replace('%fdPlaceName%',$('[name="fdPlaceName"]').val()).replace('%fdUserTime%',$('[name="fdUserTime"]').val());
					validator.error = error;
				}
				return result;
			});
			
			//召开时间变化时触发
			window.changeHoldDate=function(){
				var fdHoldDate=$('[name="fdHoldDate"]').val();
				var fdRecurrenceComponment = LUI('fdRecurrence');
				if(fdHoldDate!=''){
					var fdFinishedDate=$('[name="fdFinishDate"]');
					if(!fdFinishedDate.val() 
							|| Com_GetDate(fdFinishedDate.val()).getTime()<Com_GetDate(fdHoldDate).getTime() ){
						var ___finishDate = Com_GetDate(fdHoldDate).getTime() + 3600 * 1000;
						___finishDate = dateUtil.formatDate(new Date(___finishDate),'${dateTimeFormatter}');
						$('[name="fdFinishDate"]').val(___finishDate);
					}
					fdRecurrenceComponment.setBaseDate(fdHoldDate);
				}else{
					fdRecurrenceComponment.setBaseDate(new Date());
				}
			};
			
			//计算会议历时时间,返回数组,依次为:总时差、小时时差、分钟时差、……
			var _caculateDuration=function(start,end){
				if( start && end ){
					start=Com_GetDate(start);
					end=Com_GetDate(end);
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
			
			//删除会议室预约
			window.del=function(){
				var fdId=$('[name="fdId"]').val();
				var url="${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=delete&fdId="+fdId;
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
					if(value==true){
						$.get(url,function(data){
							if (data && data['status'] === true) {
								if(window.$dialog!=null){
									$dialog.hide("success");
								}else{
									window.close();
								}
							}
						},'json');
					}
				});
			};
			
			//保存会议室预约
			window.save=function(){
				//校验
				if(validation.validate()==false){
					return;
				}
				//校验使用时长
				if( !_validateUserTime() ){
					var error = '${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}';
					error = error.replace('%fdPlaceName%',$('[name="fdPlaceName"]').val()).replace('%fdUserTime%',$('[name="fdUserTime"]').val());
					dialog.alert(error);
					return;
				}
				var method = "save";
				var last_method = Com_GetUrlParameter(window.location.href, "method");
				if("edit"==last_method){
					method = "update";
				}
				//资源冲突检测
				var fdPlaceId=$('[name="fdPlaceId"]').val();
				var loading = dialog.loading('正在进行资源冲突检测...');
				$.ajax({
					url: "${LUI_ContextPath}/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=checkConflict",
					type: 'POST',
					dataType: 'json',
					data: {
						bookId : $('[name="fdId"]').val() , 
						fdPlaceId : $('[name="fdPlaceId"]').val(), 
						fdHoldDate : $('[name="fdHoldDate"]').val() , 
						fdFinishDate : $('[name="fdFinishDate"]').val(),
						recurrenceStr : $('[name="fdRecurrenceStr"]').val()
					},
					success: function(data, textStatus, xhr) {//操作成功
						loading.hide();
						if(data && !data.result ){
							//不冲突
							ajaxSubmit(method);
						}else{
							//冲突
							dialog.alert("${lfn:message('km-imeeting:kmImeetingBook.conflict.tip')}".replace('%Place%',$('[name="fdPlaceName"]').val()));
						}
					}
				});
			};

		var ajaxSubmit=function(method){
			var start=Com_GetDate($('[name="fdHoldDate"]').val()),
				end=Com_GetDate($('[name="fdFinishDate"]').val());
			$('[name="fdHoldDuration"]').val(((end.getTime() - start.getTime()) / 3600000).toFixed(1));//记录会议历时
			var url='${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method='+method;
			$.ajax({
				url: url,
				type: 'POST',
				dataType: 'json',
				async: false,
				data: $("#bookform").serialize(),
				success: function(data, textStatus, xhr) {//操作成功
					if (data && data['status'] === true) {
						if(window.$dialog!=null){
							$dialog.hide("success");
						}else{
							window.close();
						}
					}
				},
				error:function(xhr, textStatus, errorThrown){//操作失败
					dialog.failure('<bean:message key="return.optFailure" />');
					if(window.$dialog!=null){
						$dialog.hide(null);
					}else{
						window.close();
					}
				}
			});
		};
	});
</script>