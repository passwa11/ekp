<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script type="text/javascript">
seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
		// 查看
		window.viewDetail = function(dataviewId, id){
			var _type = dataviewId.slice(0, 1).toUpperCase() + dataviewId.slice(1);
			var url = "${LUI_ContextPath}/hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=view&fdId="+id;
			window.open(url,'_blank');
		}
		// 增加 或 编辑
		window.addOrEdit = function(dataviewId, id) {
			var _type = dataviewId.slice(0, 1).toUpperCase() + dataviewId.slice(1);
			var iframeUrl = "/hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=add&personInfoId=${userId}&type=" + dataviewId;
			var url = "${LUI_ContextPath}/hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=save";
			var title ;
			switch(dataviewId){
			case 'bonusMalus':{
				title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.bonusMalus"/>';
				break;
			}
			case 'qualification':{
				title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.qualification"/>';
				break;
			}
			case 'training':{
				title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.training"/>';
				break;
			}
			case 'education':{
				title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.education"/>';
				break;
			}
			case 'work':{
				title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.work"/>';
				break;
			}
			case 'contract':{
				title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.contract"/>';
				break;
			}
			case 'project':{
				title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.project"/>';
				break;
			}
			case 'brief':{
				title = '<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.brief"/>';
				break;
			}
			}
			if(id) {
				iframeUrl = "/hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=edit&fdId=" + id+ "&personInfoId=${userId}&type=" + dataviewId;
				url = "${LUI_ContextPath}/hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=update";
			}
			dialog.iframe(iframeUrl, title, function(data) {
				if (null != data && undefined != data) {
					if(id) {
						data.fdId = id;
					}
					$.post(url, data, function(result){
						if(result.state) {
							LUI(dataviewId).load();
						} else {
							dialog.alert(result.msg);
						}
					}, "json");
				}
			}, {
				width : 900,
				height : 400
			});
		};
		
		// 删除详情信息
		window.delDetail = function(dataviewId, id) {
			var values = [];
			if(id) {
 				values.push(id);
	 		}
			if(values.length == 0) {
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			var _type = dataviewId.slice(0, 1).toUpperCase() + dataviewId.slice(1);
			var url = "${LUI_ContextPath}/hr/staff/hr_staff_person_experience/" + dataviewId + "/hrStaffPersonExperience" + _type + ".do?method=deleteall";
			dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
				if(value == true) {
					window.del_load = dialog.loading();
					$.ajax({
						url : url,
						type : 'POST',
						data : $.param({"List_Selected" : values}, true),
						dataType : 'json',
						error : function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
						success: function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							LUI(dataviewId).load();
							dialog.result(data);
						}
				   });
				}
			});
		};
	});
</script>
