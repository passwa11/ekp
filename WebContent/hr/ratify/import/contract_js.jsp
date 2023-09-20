<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env'],function($,dialog,topic,env){
		//选择合同
		window.selectContract = function(type){
			var contId = '', contName = '';
			if(type == 'Change'){
				contId = $("[name='fdContractId']").val();
				contName = $("[name='fdContractName']").val();
			}else{
				contId = $("[name='fdRemoveContractId']").val();
				contName = $("[name='fdRemoveContractName']").val();
			}
			var staffId = $("input[name='fd" + type + "StaffId']").val();
			if(staffId != null && staffId != ''){
				var url="/hr/ratify/import/showContractDialog.jsp?contId="+ contId + "&contName=" + encodeURI(contName) +"&staffId="+staffId;
				dialog.iframe(url,'选择合同',function(arg){
					if(arg){
						if(type == 'Change'){
							$("[name='fdContractId']").val(arg.contId);
							$("[name='fdContractName']").val(arg.contName);
							var fdChangeSignBeginDate = $("[name='fdChangeSignBeginDate']")[0];
							if(fdChangeSignBeginDate)
								$("[name='fdChangeSignBeginDate']").val(arg.fdBeginDate);

                            if("长期有效" == arg.fdEndDate){
                                var fdChangeIsLongtermContract = $("[name='fdChangeIsLongtermContract']")[0];
                                if(fdChangeIsLongtermContract){
                                    $("[name='fdChangeIsLongtermContract']").val("true");
                                    $("#changeIsLongtermContract").text(arg.fdEndDate);
                                }
                                var fdChangeSignEndDate = $("[name='fdChangeSignEndDate']")[0];
                                if(fdChangeSignEndDate)
                                    $("[name='fdChangeSignEndDate']").val("");
                            }else{
                                var fdChangeSignEndDate = $("[name='fdChangeSignEndDate']")[0];
                                if(fdChangeSignEndDate)
                                    $("[name='fdChangeSignEndDate']").val(arg.fdEndDate);
                                var fdChangeIsLongtermContract = $("[name='fdChangeIsLongtermContract']")[0];
                                if(fdChangeIsLongtermContract){
                                    $("[name='fdChangeIsLongtermContract']").val("");
                                    $("#changeIsLongtermContract").text("");
                                }
                            }

							var fdChangeSignRemark = $("[name='fdChangeSignRemark']")[0];
							if(fdChangeSignRemark){
								$("[name='_fdChangeSignRemark']").val(arg.fdMemo);
								$("[name='fdChangeSignRemark']").val(arg.fdMemo);
							}
						}else{
							$("[name='fdRemoveContractId']").val(arg.contId);
							$("[name='fdRemoveContractName']").val(arg.contName);
							var fdRemoveBeginDate = $("[name='fdRemoveBeginDate']")[0];
							if(fdRemoveBeginDate)
								$("[name='fdRemoveBeginDate']").val(arg.fdBeginDate);

							if("长期有效" == arg.fdEndDate){
                                var fdIsLongtermContract = $("[name='fdIsLongtermContract']")[0];
                                if(fdIsLongtermContract){
                                    $("[name='fdIsLongtermContract']").val("true");
                                    $("#isLongtermContract").text(arg.fdEndDate);
                                }
                                var fdRemoveEndDate = $("[name='fdRemoveEndDate']")[0];
                                if(fdRemoveEndDate)
                                    $("[name='fdRemoveEndDate']").val("");
                            }else{
                                var fdRemoveEndDate = $("[name='fdRemoveEndDate']")[0];
                                if(fdRemoveEndDate)
                                    $("[name='fdRemoveEndDate']").val(arg.fdEndDate);
                                var fdIsLongtermContract = $("[name='fdIsLongtermContract']")[0];
                                if(fdIsLongtermContract){
                                    $("[name='fdIsLongtermContract']").val("");
                                    $("#isLongtermContract").text("");
                                }
                            }

							var fdRemoveRemark = $("[name='fdRemoveRemark']")[0];
							if(fdRemoveRemark){
								$("[name='_fdRemoveRemark']").val(arg.fdMemo);
								$("[name='fdRemoveRemark']").val(arg.fdMemo);
							}
						}
					}
				},{width:800,height:520});
			}else{
				dialog.alert("请先选择所属员工");
			}
		};
	});
</script>