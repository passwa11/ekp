		require(["mui/form/ajax-form!hrRatifyOtherForm"]);
		window.addDeptPost=function(){}
		require(['dojo/ready','dijit/registry','dojo/query','dojo/ready','dojo/topic',"dojo/request"],function(ready,registry,query,ready,topic,request){
			ready(function(){
				var posAddress = registry.byId('fdSalaryDept');
				var staffAddress = registry.byId('fdSalaryStaff')
				var baseUrl = dojo.config.baseUrl
				 window.addDeptPost=function(obj){
					if(obj){
						$.ajax({
							url : baseUrl+'hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getPersonInfo',
							type: 'POST',
							dataType: 'json',
							data : {
								id : obj
							},
							success : function(data, textStatus, xhr){
								var d = eval(data);
								if(d&&d.dept){
									  var dept = d.dept;
									  posAddress._setCurIdsAttr(dept.id);
									  posAddress._setCurNamesAttr(dept.name);
								}
								
							},
							error:function(e){
								//console.log(e)
							}
						});
					}else{
						  posAddress._setCurIdsAttr("");
						  posAddress._setCurNamesAttr("");
					}
					
					
				 } 
			})
		})
		function review_submit(){
			var status = document.getElementsByName("docStatus")[0];
			var method = Com_GetUrlParameter(location.href,'method');
			if(method=='add'){
				status.value="20"
				Com_Submit(document.forms[0],'save'); 
				
			}else{
				status.value="20"
				Com_Submit(document.forms[0],'update');
			}
		}