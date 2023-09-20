Com_IncludeFile("treeview.js");

seajs.use(['lui/dialog','lui/topic',
	Com_Parameter.ContextPath+'hr/organization/resource/js/address/address.js',
	'lang!hr-organization'
	],function(dialog,topic,address,lang){
	window.eidtOrg=function(fdId,type){
		var url="hr/organization/hr_organization_element/hrOrganizationElement.do?method=!{method}&fdId=!{fdId}";
		var deptUrl = "/hr/organization/hr_organization_dept/hrOrganizationDept.do?method=!{method}&fdId=!{fdId}"
		var orgUrl = "/hr/organization/hr_organization_org/hrOrganizationOrg.do?method=!{method}&fdId=!{fdId}";
		if(fdId){
			var dialogObj = dialog.iframe("/"+Com_ReplaceParameter(url,{method:'edit',fdId:fdId}),lang['hr.organization.info.editOrgUnit'],function(data){
				if(data=='success'){
					window.parent.refreshOrgTree();
				}
			},{
				width:840,
				height:550,
				buttons:[{
					name:lang['hr.organization.info.button.ok'],
					value:'',
					fn:function(){
						var data = dialogObj.element.find("iframe").get(0).contentWindow.orgEditSubmit("update")
					}
			},{
				name:lang['hr.organization.info.button.cancel'],
				value:'',
				fn:function(){
					dialogObj.hide();
				}
			}
			]
			})
		}
	}
	window.stopOrg=function(info,type,name){
			if(typeof info =='string'&&type){
				info ={
					depts:type=='2'?[{id:info,name:name}]:[],
					orgs:type=='1'?[{id:info,name:name}]:[]
				}
			}
			window.top.stopOrgInfo=info
			var stopFormUrl ="/hr/organization/hr_organization_tree/orgInfo/stopOrg.jsp?orgName="+encodeURIComponent(name)+"&orgType="+type;
			var config = {
					buttons:[{
						name:lang['hr.organization.info.button.ok'],
						fn:function(){
							 dialogObj.element.find("iframe").get(0).contentWindow._submitForm();
						}
					},{
						name:lang['hr.organization.info.button.cancel'],
						fn:function(){
							dialogObj.hide();
						}
					}],
					width:500,
					height:400
				}
			var dialogObj = dialog.iframe(stopFormUrl,lang['hr.organization.info.stopOrg'],function(result){
				if(result=='success'){
					topic.publish("list.refresh");
				}
			},config);
	}

	window.invalidated = function() {
		var listData = LUI("subordinate_list_view")._data;
		var allInput = $("#subordinate_list_view").find("input[name=List_Selected]");
		var selectInput = $("#subordinate_list_view").find("input[name=List_Selected]:checked");
		var depts =[],orgs=[];
		selectInput.each(function(index,item){
			var dataIndex = allInput.index($(item));
			var itemData = listData&&listData['datas'][dataIndex]
			itemData[7]['value']=="2"?depts.push({id:itemData[0]['value'],name:itemData[4]['value']}):orgs.push({id:itemData[0]['value'],name:itemData[4]['value']});
		});
		
		if((depts.length+orgs.length)>0){
			window.stopOrg({depts:depts,orgs:orgs});
		}else{
			dialog.alert(lang['hr.organization.info.stopOrg.tip']);
		}
	};	
	
})
