<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
	//此处添加js代码
	function loadView() {
		if (document.readyState === "complete") {
			debugger;
			window.clearInterval(t1);
			var gsrjg = $("input[name*='fd_3b2b56e4d501c6_text']").val(); //费用归属法人机构
			var fdPersonId = $("input[name*='applicant.id']").val();
			var subject = $("input[name*='subject']").val();
			if (!gsrjg || gsrjg == '') {
				var value = $.ajax({
					url: Com_Parameter.ContextPath + 'fssc/config/fssc_config_score/fsscConfigScore.do?method=getGsrjg',
					type: 'GET',
					dataType: 'json',
					async: false,
					data: {
						'key': 'gsrjg',
						'fdPersonId': fdPersonId
					},
				}).responseText;
				console.log(value);
				$("input[name*='extendDataFormInfo.value(fd_3b2b56e4d501c6)']").val(value);
			}
			var needFind=false;
			$("#TABLE_DL_fd_3ae55da5d77c54 tr:gt(0)").find("[name*=fd_3bf8753e1e8fd0]").each(function(){
				var stringObject = this.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
				var k=stringObject.substring(stringObject.indexOf('fd_3ae55da5d77c54.')+18,stringObject.indexOf('.fd_3bf8753e1e8fd0'));
				var subject = $("[name*='"+k+".subject']").val();
							console.log(this.value);
							if(subject&&!this.value){
								needFind=true;
								return;
							}
			});
			if(needFind){
				$.ajax({
					url: Com_Parameter.ContextPath + 'fssc/config/fssc_config_score/fsscConfigScore.do?method=getFylx',
					data: {},
					async: false,
					success: function(rtn) {
						if (!rtn) {
							return;
						}
						rtn = JSON.parse(rtn);
						console.log(rtn);
						$("#TABLE_DL_fd_3ae55da5d77c54 tr:gt(0)").find("[name*=subject]").each(function(){
							var key = this.value;
							if(key){
								var stringObject = this.name.replace(/\S+\[(\d+)\]\S+/g,'$1');
								var k=stringObject.substring(stringObject.indexOf('fd_3ae55da5d77c54.')+18,stringObject.indexOf('.subject'));
								 $("[name*='"+k+".fd_3bf8753e1e8fd0']").val(rtn[key]);
							}
						});
					}
				});
			}
				
		}
	}

var t1 = window.setInterval(loadView, 50); 
</script>
