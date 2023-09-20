      
	//切换入职员工填写模式
		function SetEntryNameField() {
			var checked = document.getElementById("isRecruit").value;
			//box区域
			var checkBoxSpan = document.getElementById("checkBoxSpan");
			//招聘模块
			var isRecruitSpan = document.getElementById("isRecruitSpan");	
			var notRecruitSpan = document.getElementById("notRecruitSpan");
			var isRecruitSelect = document.getElementsByName("fdRecruitEntryName")[0];
			var isRecruitInput = document.getElementsByName("fdEntryName")[0];
			var _validation = $KMSSValidation(document.forms['hrRatifyEntryForm']);
			_validation.removeElements(isRecruitInput);
			_validation.removeElements(isRecruitSelect);
			if ("true"== checked) {
				//输入框样式
				//checkBoxSpan.style.bottom="0px";
				isRecruitSpan.style.display="";
				notRecruitSpan.style.display="none";
				_validation.addElements(isRecruitSelect,'required');
				$(isRecruitInput).removeAttr('validate');
				$(isRecruitInput).removeAttr('_validate');
			} else {
			    //输入框样式
				//checkBoxSpan.style.bottom="10px";
				isRecruitSpan.style.display="none";
				notRecruitSpan.style.display="";
				_validation.addElements(isRecruitInput,'required');
				_validation.addElements(isRecruitInput,'maxLength(200)');
				$(isRecruitSelect).removeAttr('validate');
				$(isRecruitSelect).removeAttr('_validate');
			}
		}
		Com_AddEventListener(window, "load", function() {
			if(window.isLoadReady)
				return;
			SetEntryNameField();
			window.isLoadReady = true;
		});
		 seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env'],function($,dialog,topic,env){
	        		window.selectStaffEntry = function(){
	        		var staffId = $('[name="fdRecruitEntryId"]').val();
	        		var staffName = $('[name="fdRecruitEntryName"]').val();
	        		var url="/hr/ratify/import/showStaffEntryDialog.jsp?staffId="+ staffId + "&staffName=" + encodeURI(staffName);
	        		dialog.iframe(url,'选择待入职员工',function(arg){
	        			if(arg)
	        				reloadForm(arg.staffId);
	        		},{width:800,height:520});
	        	};
	        	window.reloadForm = function(value){
	        		document.hrRatifyEntryForm.action = Com_SetUrlParameter(location.href, "fdEntryId", value);
	    			document.hrRatifyEntryForm.submit();
	        	};
		 });