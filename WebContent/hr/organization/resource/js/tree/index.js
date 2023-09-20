
var treeWidget = Com_Parameter.ContextPath+"hr/organization/resource/js/tree/tree.js";
var tabpageWidge = Com_Parameter.ContextPath+"hr/organization/resource/js/tree/tabPage.js";
seajs.use([treeWidget,"lui/topic","lui/dialog",tabpageWidge,'lang!hr-organization'],function(TreeWidget,topic,dialog,Tab,lang){
			var dialogObj = {};
			var iframeHeight = window.parent.orgIframeHeight;			
			function setDialogButtons(ok){
				return [{name:lang['hr.organization.info.button.ok'],fn:ok},{name:lang['hr.organization.info.button.cancel'],fn:function(){
					dialogObj.hide();
				}}]
			}
			window.refreshOrgTree=function(){
				topic.publish("hr/org/search");
			}
			//新增组织
			window.addOrg = function (type){
				var deptUrl = '/hr/organization/hr_organization_dept/hrOrganizationDept.do';
				var orgUrl = '/hr/organization/hr_organization_element/hrOrganizationElement.do';
				/* var forward =(type==1?orgUrl:deptUrl)+'?method=add&orgType='+type+'&curOrgId='+tree.curSelectedData['title']+'&curOrg='+tree.curSelectedData['value']; */
				var forward =(type==1?orgUrl:deptUrl)+'?method=add&orgType='+type
				var callback=function(){
					dialogObj.frame.find("iframe").get(0).contentWindow.orgEditSubmit("save");
				}
				var title = type==2?lang['hr.organization.info.adddept']:lang['hr.organization.info.add']
				dialogObj = dialog.iframe(forward,title,function(data){
					if(data=='success'){
						topic.publish("hr/org/search");
					}
				},{buttons:setDialogButtons(callback),width:800,height:550})
			}
			//合并组织
			function combainOrg(){
				if(tree.curSelectedData){
					var onpostNum = 0;
					try{
						var num = JSON.parse(tree.curSelectedData['onAllPost']);
						onpostNum = num;
					}catch(e){
						
					}
					
					var orgUrl = '/hr/organization/hr_organization_tree/orgInfo/combine.jsp?onpostNum='+onpostNum+'&curOrg='+tree.curSelectedData['title']+'&curOrgId='+tree.curSelectedData['value'];
					var callback=function(){
						dialogObj.frame.find("iframe").get(0).contentWindow.orgEditSubmit("save");
					}
					var config = {buttons:setDialogButtons(callback),width:650,height:386}
					dialogObj = dialog.iframe(encodeURI(orgUrl),lang['hr.organization.info.mergingOrg'],function(data){
						if(data=='success'){
							topic.publish("hr/org/search");
						}
					},config)
				}
			}
			//批量导入组织
			function importElement(){
				var path = "/hr/organization/hr_organization_element/hrOrganizationElement_import.jsp";
				var callback=function(){
					
				}
				var config = {
						width:650,
						height:550
				}
				dialogObj = dialog.iframe(path,lang['hr.organization.info.batchImportOrg'],function(value){
    	   			topic.publish('list.refresh');
        		},config)				
			}
			
			var btnConfig = [
				{name:lang['hr.organization.info.add'],iconCls:'addIcon',event:addOrg.bind(this,1)},
				{name:lang['hr.organization.info.adddept'],iconCls:'addIcon',event:addOrg.bind(this,2)},
				{name:lang['hr.organization.info.mergingOrg'],event:combainOrg,iconCls:'combineIcon'},
				{name:lang['hr.organization.info.batchImportOrg'],event:importElement,iconCls:'importIcon'}]
			//加载选选中的组织页签内容
			topic.subscribe("hr/click/node/text",function(data){
				var infoUrl = Com_Parameter.ContextPath+"hr/organization/hr_organization_element/hrOrganizationElement.do?method=view&fdId="+data['value'];
				var staffUrl = Com_Parameter.ContextPath+"hr/organization/hr_organization_tree/subordinateStaff/list.jsp?fdParentId="+data['value'];
				var personUrl = Com_Parameter.ContextPath+"hr/organization/hr_organization_tree/person/list.jsp?fdParentId="+data['value'];
				$("#org-info").length&&$("#org-info").attr("src",infoUrl)
				$("#org-subordinate-staff").length&&$("#org-subordinate-staff").attr("src",staffUrl);
				$("#org-subordinate-staff").length&&$("#org-person").attr("src",personUrl);
			})
			var hasBottom = $("#ishrToEkpEnable").val();
			//权限控制
			var authUpdateCompile = $("#authUpdateCompile").get(0)?true:false;
			var autoAddOrg = $("#autoAddOrg").get(0)?true:false;
			var treeConfig = {id:'hr-organization-cont',authCompile:authUpdateCompile,autoAddOrg:autoAddOrg,treeHeight:iframeHeight-85-120,icon:true};
			if(hasBottom=="true"){
				treeConfig['bottom'] = btnConfig;
			}
			//初始化页签
			var orgInfoUrl = Com_Parameter.ContextPath+"hr/organization/hr_organization_tree/orgInfo/orgInfo.jsp";
			var suborUrl = Com_Parameter.ContextPath+"hr/organization/hr_organization_tree/subordinateStaff/list.jsp";
			var personUrl = Com_Parameter.ContextPath+"hr/organization/hr_organization_tree/person/list.jsp";
			new Tab.tabPage(
				{
					id:"tablePage",tabButtons:[
						{text:lang['hr.organization.info'],active:true},
						{text:lang['hr.organization.info.nextOrg']},
						{text:lang['hr.organization.info.staff']}],
					tabPages:[{url:orgInfoUrl,id:"org-info"},{url:suborUrl,id:"org-subordinate-staff"},{url:personUrl,id:"org-person"}]
				}
			);
			$(".hr_org_info_iframe").height(iframeHeight-30-50);
			//初始化组织架构树
			var tree = new TreeWidget.OrgTree(treeConfig);
			//tree 高度设置
			function topicSearch(){
				var btn = $("#hr-organization-search span");
				var searchValue = $("#hr-organization-search input").val();
				if(!btn.attr("searched")){
					topic.publish("hr/org/search",searchValue);
					btn.attr("searched","true");
					btn.addClass("hr-org-search-cancel");
				}else{
					topic.publish("hr/org/search","");
					btn.attr("searched","");
					btn.removeClass("hr-org-search-cancel");
				}
			}
			
			$("#hr-organization-search span").on("click",function(){
				var btn = $("#hr-organization-search span");
				var searchValue = $("#hr-organization-search input").val();
				if(!btn.attr("searched")){
					topic.publish("hr/org/search",searchValue);
					btn.attr("searched","true");
					btn.addClass("hr-org-search-cancel");
				}else{
					//刷新
					topic.publish("hr/org/search/cancel","");
					$("#hr-organization-search input").val("");
					btn.attr("searched","");
					btn.removeClass("hr-org-search-cancel");
				}
				return false;
			});
			$("#hr-organization-search input").on("keydown",function(e){
				var btn = $("#hr-organization-search span");
				var searchValue = $("#hr-organization-search input").val();
				 var theEvent = e || window.event;
				 var searchValue = $(this).val();
				 var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
				 if(code==13){
						if(searchValue){
							topic.publish("hr/org/search",searchValue);
							
							btn.attr("searched","true");
							btn.addClass("hr-org-search-cancel");
						}else{
							topic.publish("hr/org/search/cancel","");
							btn.attr("searched","");
							btn.removeClass("hr-org-search-cancel");
						}
				 }				
				
			})
		})