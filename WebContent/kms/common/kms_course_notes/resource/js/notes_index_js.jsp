<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script type="text/javascript">

	function List_CheckSelect(){
		var obj = document.getElementsByName("List_Selected");
		if (obj == null || obj.length == 0){
			seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
				dialog.alert("<bean:message bundle='kms-common' key='kms.opera.noSelectData'/>");
			}
		)
			return false;
		}
		return true;
		
	}

	//删除笔记
	function delDoc() {
		var url = '/kms/common/kms_notes/kmsCourseNotes.do?method=deleteall';
		var values = [],
	     selected,
	     select = document.getElementsByName("List_Selected");
	     var data = {"successForward":"lui-source", "failureForward":"lui-failure"};
		for (var i = 0; i < select.length; i++) {
			if (select[i].checked) {
				values.push(select[i].value);
				selected = true;
			}
		}
		if (selected) {
			seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery', 'lui/util/env'],
					function(dialog, topic, $, env) {
						var dataObj = $.extend({},data,{"List_Selected":values});
						dialog.confirm("${lfn:message('kms-common:notes_prompt_del_confirm')}", function(flag, d) {
							if (flag) {
								var loading = dialog.loading();
								$.ajax({
										url : env.fn.formatUrl(url),
										cache : false,
										data : $.param(dataObj,true),
										type : 'post',
										dataType :'json',
										success : function(data) {
													dialog.success("${lfn:message('kms-common:notes_prompt_success_del')}",
															'#listview');
													topic.channel("notes_index").publish("list.refresh");
													//topic.publish('list.refresh');
												
												loading.hide();	
										},
										error : function(error) {//删除失败
											loading.hide();	
											dialog.failure(
													"${lfn:message('kms-common:notes_prompt_del_fail')}",
														'#listview');
										}
									}
								);
							}
						});
					});
		} else {
			seajs.use(['lui/dialog'], function(dialog) {
						dialog.alert("${lfn:message('page.noSelect')}");
					});
		}
	}
	
 
</script>