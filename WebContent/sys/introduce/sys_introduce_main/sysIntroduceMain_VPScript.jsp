<script type="text/javascript">
function changereason(fdIntroId){
    seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
 var newreason=document.getElementsByName("fdIntroduceReason")[0].value;
 var changeURL = Com_Parameter.ContextPath + "sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=changeReason";
 changeURL = Com_SetUrlParameter(changeURL, "fdIntroId", fdIntroId);
 changeURL = Com_SetUrlParameter(changeURL, "fdModelName", '${sysIntroduceMainForm.fdModelName}');
 var newreasonLength = document.getElementsByName("fdIntroduceReason")[0].value.length;
 if(newreasonLength > 1000) {
         console.log(newreasonLength);
         // dialog.alert("推荐字数超过一千，修改失败");
         dialog.alert("${lfn:message('sys-introduce:sysIntroduceMain.changetrue.countSize')}");
         return;
 }


	 $.ajax({
		 url: changeURL,
			type: 'post',
			dataType: 'json',
			data:{newreason :newreason},
			success: function(data, textStatus, xhr) {
				dialog.alert("${lfn:message('sys-introduce:sysIntroduceMain.changetrue')}");
			   
			},
			error: function(data, textStatus, xhr) {
				dialog.alert("${lfn:message('sys-introduce:sysIntroduceMain.changetruefalse')}");
			}
		 })



	 
 })
	
}

</script>