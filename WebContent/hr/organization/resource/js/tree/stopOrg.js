seajs.use(['lui/dialog','lui/topic','lang!hr-organization','lang!sys-organization'],function(dialog,topic,lang,langorg){
	var info = window.top.stopOrgInfo;
	var dataSize = info['depts'].length+info['orgs'].length,deptIds=[],orgIds=[];
	if(dataSize>1){
		initBtn(info['depts'],2);
		initBtn(info['orgs'],1);
	}else{
		info['depts'].length>0?$(".orgnamevalue").html(info['depts'][0].name):null;
		info['orgs'].length>0?$(".orgnamevalue").html(info['orgs'][0].name):null;
	}

	filterData(null,1);
	filterData(null,2);
	function filterData(id,type){
		if(type==1){
			var temp =[]
			orgIds=[];
			for(var i = 0;i<info['orgs'].length;i++){
				if(info['orgs'][i]['id']!=id){
					temp.push(info['orgs'][i]);
					orgIds.push(info['orgs'][i]['id']);
				}
			}
			info['orgs'] = temp;
		}else{
			deptIds=[];
			var tsp =[]
			for(var j = 0;j<info['depts'].length;j++){
				if(info['depts'][j]['id']!=id){
					tsp.push(info['depts'][j]);
					deptIds.push(info['depts'][j]['id']);
				}
			}
			info['depts'] = tsp;
		}
		
	}
	function initBtn(data,type){
		var valueDom = $(".orgnamevalue")
		for(var i = 0;i<data.length;i++){
			var span = $("<span class='stoporgbtn'>"+data[i]['name']+"</span>");
			var icon = $("<i></i>");
			icon.attr("dtype",type);
			icon.attr("fdId",data[i]['id']);
			span.append(icon);
			valueDom.append(span);
		}
		valueDom.on("click",function(e){
			if(e.target.tagName="i"){
				var id = $(e.target).attr("fdId");
				var type =$(e.target).attr('dtype');
				filterData(id,type)
				try{
					if(e.target.parentNode){
						this.removeChild(e.target.parentNode);
					}
				}catch(e){
					
				}
			}	
		})
	}
	function BanOrgList(){
		window.del_load = dialog.loading();
		var deptUrl =Com_Parameter.ContextPath+'hr/organization/hr_organization_dept/hrOrganizationDept.do?method=changeDisabledList'
		var orgUrl = Com_Parameter.ContextPath+'hr/organization/hr_organization_org/hrOrganizationOrg.do?method=changeDisabledList'
			var flag = 0;
		var totalFlag = 0;
		//promise.all
		if(info['depts'].length>0){
			totalFlag++;
			invalidOrg(deptUrl,{fdId:deptIds.join(";"),fdorgDemo:$("#hrorgDemo").val()});
		}
		if(info['orgs'].length>0){
			totalFlag++;
			invalidOrg(orgUrl,{fdId:orgIds.join(";"),fdorgDemo:$("#hrorgDemo").val()});	
		}
		
		function invalidOrg(url,data){
			$.ajax({
				url : url,
				type : 'POST',
				data : data,
				dataType : 'json',
				error : function(data) {
					if(window.del_load != null) {
						window.del_load.hide(); 
					}
					dialog.alert(langorg['sysOrgDept.error.existChildren']);
				},
				success: function(data) {
					flag++;
					if(window.del_load != null&&flag==totalFlag){
						window.del_load.hide(); 
					}
					dialog.success(lang['hr.organization.info.button.ok']);
					//window.$dialog.hide();
					window.$dialog.hide("success");
				}
		   }); 
		} 				
	}
	window._submitForm =function(){
		BanOrgList();	
	}			
})