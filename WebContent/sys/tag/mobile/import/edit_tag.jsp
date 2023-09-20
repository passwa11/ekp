<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		编辑标签
	</template:replace>
	<template:replace name="head">
  		<link rel="stylesheet" href="<%=request.getContextPath()%>/sys/tag/mobile/import/style/tag.css">
  		<style>
  			html,body{
  				height:100%;
  			}
  			#content{
  				height:100%;
  			}
  			#sysTags{
  				height:100%;
  			}
			#hr-staff-tag-dialog .mblView {
				height: 100%;
			}
  		</style>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do" styleId="sysTags">
			<input type="hidden" name="zoneQueryCondition" value="sysZonePersonInfo"/>
			<input type="hidden" name="sysTagMainForm.fdId" value="${sysTagMainForm.fdId}"/>   
			<input type="hidden" name="sysTagMainForm.fdKey" value="${JsParam.fdKey}"/>
			<input type="hidden" name="sysTagMainForm.fdModelName" value="${JsParam.fdModelName }"/>
			<input type="hidden" name="sysTagMainForm.fdModelId" value="${tag_MainForm.fdId}"/> 
			<input type="hidden" name="sysTagMainForm.fdQueryCondition" value="${JsParam.fdQueryCondition}"/> 
			<input type="hidden" name="sysTagMainForm.fdTagIds" />
			<div class="hr-staff-tag">
				<div class="hr-staff-tag-list"></div>
			</div>
			<div id="saveTags">保存${lfn:escapeHtml(sysTagMainForm.fdTagNames)}</div>
		</html:form>
		<script>
		require([ 'dojo/on','dojo/topic',
		          'dojo/query', 
		          'dojo/touch', 
		          'dojo/dom-construct',
		          'dojo/request',
		          'mui/dialog/Dialog',
		          'dijit/registry',
		          "dojo/ready",
		          "dojo/dom-form"
		          ],
        		function(on,topic,query,touch,domConstruct,request,Dialog,registry,ready,domForm){
			var url = dojo.config.baseUrl+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getTagsByModelId";
			
			var containerNode = query(".hr-staff-tag-list");
			window.selectValue=[];
			var saveValue=[]
			function rendreItem(dataJson){
				saveValue=[];
				dataJson=dataJson.filter((res)=>{return res})
				dataJson.map(function(item,i){
					saveValue[i]=item.value;
					var itemNode = domConstruct.create("div",{className:'tag-item',innerHTML:item.value},containerNode[0]);
					var delBtn = domConstruct.create("div",{className:'tag-item-delete-btn'},itemNode);
					domConstruct.create("div",{className:'tag-item-delete-v'},delBtn);
					domConstruct.create("div",{className:'tag-item-delete-h'},delBtn)
					on(delBtn,"click",function(){
						saveValue.splice(i,1);
						query(".hr-staff-tag-list")[0].removeChild(itemNode);
					})
					window.selectValue.push(item);
				})
			}
			ready(function(){
				request.post(url,{data:{
					modelId:'${param.fdModelId}'
				}}).then(function(data){
					if(data){
						try{
							var dataJson = JSON.parse(data);
							rendreItem(dataJson)
							var addTagBtn = domConstruct.create("div",{className:'tag-btn',innerHTML:''},containerNode[0]);
							 domConstruct.create("div",{className:'tag-btn-icon-left',innerHTML:''},addTagBtn);
							 domConstruct.create("div",{className:'tag-btn-icon-top',innerHTML:''},addTagBtn);
								
							on(addTagBtn,"click",function(){
								var dialogObj = Dialog.element({
									canClose : true,
									id:'hr-staff-tag-dialog',
									element :"<iframe id='hr-tag-iframe' frameborder='no' border='0' width='100%' height='100%' src='<%=request.getContextPath()%>/sys/tag/mobile/import/tagList.jsp'></iframe>",
									buttons : [{
										title:'取消',
										fn:function(){
											dialogObj.hide();
										}
									},{
										title:'确定',
										fn:function(){
											var value = query("#hr-tag-iframe")[0].contentWindow.getValue();
											domConstruct.place(addTagBtn,document.body);
											query(".hr-staff-tag-list")[0].innerHTML="";
											rendreItem(value);
											domConstruct.place(addTagBtn,query(".hr-staff-tag-list")[0]);
											dialogObj.hide();
										}
									}],	
									privateHeight:500,
								});
								//纠正弹出窗位置
								//dialogObj.containerNode.style.marginTop=-(dialogObj.containerNode.offsetHeight/2)+'px';
							
							})
						}catch(e){
							console.log(e)
						}
						
					}
					
					on(query("#saveTags"),"click",function(){
						var formData =domForm.toObject("sysTags");
						formData['sysTagMainForm.fdTagNames']=saveValue.join(";")
						var url="<%=request.getContextPath()%>/sys/tag/sys_tag_main/sysTagMain.do?method=updateTag";
						url+="&fdModelId=${tag_MainForm.fdId}&fdModelName=${JsParam.fdModelName}";
						request.post(url,{data:formData}).then(function(data){
							if(data){
								 window.location.href="<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId="+"${param.fdModelId}"
							} 
						})
					})
				})
			})

		})
		</script>
	</template:replace>
</template:include>