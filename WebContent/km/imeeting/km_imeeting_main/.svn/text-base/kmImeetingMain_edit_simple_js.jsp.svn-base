<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var validation=$KMSSValidation();//校验框架
	Com_IncludeFile("security.js",null,"js");
</script>
<script>
	seajs.use([
		  'km/imeeting/resource/js/dateUtil',     
	      'lui/jquery',
	      'lui/dialog'
	        ],function(util,$,dialog){
		
		var $help = $('.com_help'),
			$moreInfoContainer = $('.tb_simple_more_container');
		//完善更多信息事件
		$help.on('click',function(){
			$moreInfoContainer.slideToggle();
			$help.toggleClass('lui_arrowDn lui_arrowUp');
		});
		
		//校验资源是否空闲
		function _validateFree(){
			
		}
		
		window.changeNeedFeedback = function(isinit){
			var isChecked = "true" == $("input[name='fdNeedFeedback']").val();
			if(isinit){
				isChecked = "true" == "${kmImeetingMainForm.fdNeedFeedback}";
			}
			
			if(isChecked) {
				$("#feedBackDeadlineRow").show();
				validation.addElements($('[name="fdFeedBackDeadline"]')[0],'required after valDeadline');
				
			}else {
				$("#feedBackDeadlineRow").hide();
				//var formObj = document.kmImeetingMainForm;
				//validation.removeElements($('[name="fdFeedBackDeadline"]')[0],'required after valDeadline');
				$('[name="fdFeedBackDeadline"]').attr("validate","");
			}
		};
		
		window.changeNeedPlace = function(isinit){//isini是否是初始化
			var isChecked = "true" == $("input[name='fdNeedPlace']").val();
			if(isinit){
				isChecked = "true" == "${kmImeetingMainForm.fdNeedPlace}";
			}
			if(isChecked) {
				$("#placeTr").show();
				validation.addElements($('[name="fdPlaceName"]')[0],'validateplace');
			}else {
				$("#placeTr").hide();
				var formObj = document.kmImeetingMainForm;
				if($("input[name='fdPlaceId']")){
					$("input[name='fdPlaceId']").val("");
					$("input[name='fdPlaceName']").val("");
				}
				validation.removeElements(formObj,'validateplace');//不校验地点不全为空
			}
		};
		
		$(document).ready(function(){
			//初始化
			changeNeedPlace(true);
			
			changeNeedFeedback(true);
		});
		
		//校验最大使用时长
		function _validateUserTime(){
			var userTime = $('[name="fdPlaceUserTime"]'),
				fdPlaceId = $('[name="fdPlaceId"]');
			if(fdPlaceId.val() && userTime.val() && parseFloat(userTime.val()) ){
				var duration = parseFloat($('[name="fdHoldDurationHour"]').val()) * 3600 * 1000;
				if( duration > userTime.val() * 3600 * 1000 ){
					return false;
				}else{
					return true;
				}
			}
			return true;
		}
		
		//提交前校验资源是否被占用
		function _checkResConflict(){
			var result = false;
			var fdHoldDate=$('[name="fdHoldDate"]').val();//召开时间
			var fdHoldDurationHour = $('[name="fdHoldDurationHour"]').val();
			var fdFinishDate= '';
			if(fdHoldDate && fdHoldDurationHour){
				fdFinishDate  = util.parseDate(fdHoldDate).getTime() + parseFloat(fdHoldDurationHour) * 3600 * 1000;
				fdFinishDate = util.formatDate(new Date(fdFinishDate),'${dateTimeFormatter}');
				$('[name="fdFinishDate"]').val(fdFinishDate);
			}
			var fdPlaceId = $('[name="fdPlaceId"]').val();
			if(fdPlaceId!=''){
				//资源冲突检测
				$.ajax({
					url: "${LUI_ContextPath}/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=checkConflict",
					type: 'POST',
					async: false,
					dataType: 'json',
					data: { fdPlaceId: $('[name="fdPlaceId"]').val(), "fdHoldDate":fdHoldDate , "fdFinishDate":fdFinishDate },
					success: function(data, textStatus, xhr) {//操作成功
						if(data && !data.result ){
							result = true;
						}else{
							//冲突
							dialog.alert("${lfn:message('km-imeeting:kmImeetingMain.conflict.tip')}".replace('%Place%',$('[name="fdPlaceName"]').val()));
						}
					}
				});
			}else{
				result = true;	
			}
			return result;
		}
		
		validation.addValidator('valDeadline','回执截止时间不能晚于会议开始时间',function(v, e, o){
			if(v){
				var feedBackDeadVal = util.parseDate(v);
				var result=true;
				var fdHoldDate=$('[name="fdHoldDate"]');
				if( fdHoldDate.val()){
					var start=util.parseDate(fdHoldDate.val());
					if( feedBackDeadVal.getTime() > start.getTime()){
						result=false;
					}
				}
				return result;
			}
		});
		
		//自定义校验器:校验当前会议用时是否超过了该会议室最大使用时长
		validation.addValidator('validateUserTime','${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}',function(v,e,o){
			var result = _validateUserTime();
			if(result == false){
				validator = this.getValidator('validateUserTime');
				var error = '${lfn:message("km-imeeting:kmImeetingRes.fdUserTime.error.tip")}';
				error = error.replace('%fdPlaceName%',$('[name="fdPlaceName"]').val()).replace('%fdUserTime%',$('[name="fdPlaceUserTime"]').val());
				validator.error = error;
			}
			return result;
		});
		
		//选择会议室
		window.selectHoldPlace = function(){
			var fdHoldDate=$('[name="fdHoldDate"]').val();//召开时间
			var fdHoldDurationHour = $('[name="fdHoldDurationHour"]').val();
			var fdFinishDate= '';
			if(fdHoldDate && fdHoldDurationHour){
				fdFinishDate  = util.parseDate(fdHoldDate).getTime() + parseFloat(fdHoldDurationHour) * 3600 * 1000;
				fdFinishDate = util.formatDate(new Date(fdFinishDate),'${dateTimeFormatter}');
			}
			var resId=$('[name="fdPlaceId"]').val();//地点ID
			var resName=$('[name="fdPlaceName"]').val();//地点Name
			var url="/km/imeeting/km_imeeting_res/kmImeetingRes_showResDialog.jsp?fdHoldDate="+fdHoldDate+"&fdFinishDate="+fdFinishDate+"&resId="+resId+"&resName="+encodeURI(resName);
			dialog.iframe(url,'${lfn:message("km-imeeting:kmImeetingMain.selectHoldPlace")}',function(arg){
				if(arg){
					$('[name="fdPlaceId"]').val(arg.resId);
					$('[name="fdPlaceName"]').val(arg.resName);
					$('[name="fdPlaceUserTime"]').val(arg.resUserTime);
				}
				validation.validateElement( $('[name="fdPlaceName"]')[0] );
			},{width:800,height:520});
		};
		
		//提交
		window.commitMethod = function(){
			$('[name="docStatus"]').val('30');
			var formObj = document.kmImeetingMainForm;
			if(_checkResConflict())
				Com_Submit(formObj, 'save', 'fdId');
		};
		
		//变更
		window.updateMethod = function(){
			//校验
			if(validation.validate()==false){
				return;
			}
			$('[name="docStatus"]').val('30');
			var formObj = document.kmImeetingMainForm;
			if(_checkResConflict())
				Com_Submit(formObj,'update');
		};
		
		//关闭
		window.closeMethod = function(){
			//window.close();
			Com_CloseWindow();
		};
	});
</script>