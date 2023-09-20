<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/lib/form.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/common.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/hrorg.css">
		<style>
			.ld-setting-isopencompile div{
				float:left;
			}
			.ld-setting-isopencompile-title{
				font-size: 14px;
				color: #333333;
				font-weight:600;
				margin-right:8px;
			}
			.ld-setting-compile-number{
				margin:23px 0 0 18px;
			}
			.ld-setting-compile-number>div{
				float:left;
				height:45px;
			}
			.ld-setting-compile-number>div:last-child{
				
			}
			.ld-setting-compile-number input[type=radio]{
				margin:0 6px 0 8px;
			}
			.ld-setting-compile-number-limit{
				height:32px;
				line-height:32px;
			}
			.ld-setting-compile-number-limit input[type=text]{
				height:30px;
				background: #FFFFFF;
				border: 1px solid #DDDDDD;
				border-radius: 3px;
				margin-left:8px;
				padding-left:10px;
			}
			.ld-setting-compile-list-title div{
				margin-top:26px;
			}
			.ld-setting-compile-list-title div:first-child{
				font-size: 14px;
				color:#333;
				font-weight:600;
				float:left;
				margin-left:16px;
			}
			.ld-setting-compile-list-title div:last-child{
				float:right;
				margin-right:60px;
				font-size: 12px;
				color: #4285F4;				
			}
			#compile-list{
				margin-top:16px;
			}
			#compile-list tr:first-child>td{
				font-size: 14px;
				color: #333333;
				font-weight:500;
			}
			#compile-list tr:first-child{
				background: #F9F9F9;
				height:35px;
				text-align:center;
			}
			.ld-setting-compile-list-add{
				cursor:pointer;
			}
			.compile-list-tr{
				
			}
			.compile-list-tr td{
				text-align:center;
				height:37px;
				border-bottom:1px solid #f1f1f1;
			}
			.deleteTr{
				font-size: 12px;
				color: #4285F4;
				cursor: pointer;
			}
			.dolt-blue,.dolt-full,.dolt-over{
				width:5px;
				height:5px;
				display:inline-block;
				vertical-align:middle;
				border-radius:50%;
				margin-right:5px;
			}
			.dolt-blue{
				background: #4285F4;
			}
			.dolt-full{
				background: #28A648;
			}
			.dolt-over{
				background: #FF0000;
			}
		</style>	
	</template:replace>
	<template:replace name="content">
		<script src="../resource/weui_switch.js"></script>
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<html:form action="/hr/organization/hr_organization_element/hrOrganizationElement.do">
			<div class="ld-setting-compile">
		        <div class="ld-person-account-content">
		        	 <c:import url="/hr/organization/hr_organization_compile/compile_statiscs.jsp" charEncoding="UTF-8">
		        		<c:param name="map" value="${map}"></c:param>
		        	 </c:import>
				</div>
				<div class="ld-setting-isopencompile clearfix">
					<div class="ld-setting-isopencompile-title">
						${lfn:message('hr-organization:hr.organization.info.tip.17')}
					</div>
					<div>
						<ui:switch onValueChange="window.isOpenCompile()" id="switchWidget" showText="true" property="fdIsCompileOpen"></ui:switch>
					</div>
				</div>
				<div id="setting-compile-content">
				<div class="ld-setting-compile-number clearfix">
					<div>${lfn:message('hr-organization:hr.organization.info.setup.number')}</div>
					<div class="ld-setting-compile-radio">
						<div>
							<input class='noLimit' type="radio" value="false"  name="yes"/><span>${lfn:message('hr-organization:hr.organization.info.setup.unlimited.num')}</span>
						</div>
						<div  class="ld-setting-compile-number-limit">
							<input type="radio" name="yes"  value="true" checked /><span>${lfn:message('hr-organization:hr.organization.info.setup.limited.num')}</span></span>
							<input placeholder="${lfn:message('hr-organization:hr.organization.info.tip.16')}" type="text" value=""/>
						</div>
						<!-- 是否限制人数 -->
						<input type="hidden" name="fdIsLimitNum"/>
					</div>	
				</div>
				<div id="ld-setting-compile-box">
					<div class="ld-setting-compile-list-title clearfix">
						<div>${lfn:message('hr-organization:hr.organization.info.setup.deptpost')}</div>
						<div class="ld-setting-compile-list-add">+${lfn:message('hr-organization:hr.organization.info.setup.add')}</div>
					</div>
					<div class="ld-setting-compile-list">
						<table id="compile-list">
							<tr>
								<td>${lfn:message('hr-organization:hrOrganizationPost.fdName')}</td>
								<td>${lfn:message('hr-organization:hrOrganizationPost.compile')}</td>
								<td>${lfn:message('hr-organization:hrOrganizationPost.job.num')}</td>
								<td>${lfn:message('hr-organization:hrOrganizationPost.compile.desc')}</td>
								<td>${lfn:message('hr-organization:hr.organization.info.opertion')}</td>
							</tr>
	                    	<c:forEach var="post" items="${posts }">
		                        <tr class="compile-list-tr">
		                            <td>${post.fdName }</td>
		                            <td>
										<c:choose>
		                            		<c:when test="${post.fdIsCompileOpen && post.fdIsLimitNum == 'true' }">
												${post.fdCompileNum }
											</c:when>
		                            		<c:when test="${post.fdIsCompileOpen && post.fdIsLimitNum == 'false' }">
												${lfn:message('hr-organization:hr.organization.info.setup.unlimited')}
											</c:when>
											<c:otherwise>
												${lfn:message('hr-organization:hr.organization.info.setup.noset')}
											</c:otherwise>
										</c:choose>
		                            </td>
		                            <td>${fn:length(post.fdPersons)}</td>
		                            <td class="ld-org-compile">
		                            	<c:choose>
		                            		<c:when test="${post.fdIsCompileOpen && post.fdIsLimitNum == 'true' }">
		                                		<c:if test="${post.fdCompileNum > fn:length(post.fdPersons) }">
							        				<div class="ld-org-compile">
								        				<span class="dolt-blue"></span>
								                        <span>
								                        ${lfn:message('hr-organization:hr.organization.info.setup.shortage')}
								                        ${post.fdCompileNum - fn:length(post.fdPersons)}${lfn:message('hr-organization:hr.organization.info.emp.p')}</span>
							                        </div>
							        			</c:if>
							        			<c:if test="${post.fdCompileNum < fn:length(post.fdPersons) }">
							        				<div class="ld-org-compile">
								        				<span class="dolt-over"></span>
								                        <span>
								                        ${lfn:message('hr-organization:hr.organization.info.setup.overbooking')}
								                        ${fn:length(post.fdPersons) - post.fdCompileNum}${lfn:message('hr-organization:hr.organization.info.emp.p')}</span>
							                        </div>
							        			</c:if>
							        			<c:if test="${post.fdCompileNum == fn:length(post.fdPersons) }">
							        				<div class="ld-org-compile">
								        				<span class="dolt-full"></span>
								                        <span>
								                        ${lfn:message('hr-organization:hr.organization.info.setup.manbian')}								                        
								                        </span>
							                        </div>
							        			</c:if>
		                            		</c:when>
		                            		<c:otherwise>
		                            			-
		                            		</c:otherwise>
		                            	</c:choose>
		                            </td>
		                            <td class='deleteTr' onclick='window.onDeleteTr(this)'><span style="cursor:pointer">${lfn:message('button.delete')}</span></td>
		                            <c:if test="${post.fdIsLimitNum == 'true' }">
		                            	<c:set value="${totalCompileNum + post.fdCompileNum}" var="totalCompileNum"/>
		                            </c:if>
		                        </tr>
	                        </c:forEach>
						</table>
					</div>
				</div>
				</div>
			</div>
   		</html:form>
		<script type="text/javascript">
			var isLimitNum = "${hrOrganizationElementForm.fdIsLimitNum }";
			var form ={};
			//init
			//已有编制数
			var postsCompileNum = Number("${postsCompileNum}");
			form['fdId']= "${hrOrganizationElementForm.fdId}"
			//开启编制后是否限制人数
			form['fdIsLimitNum']=isLimitNum;
			//是否开启编制
			form['fdIsCompileOpen']="${hrOrganizationElementForm.fdIsCompileOpen}"
			form['fdCompileNum']="${hrOrganizationElementForm.fdCompileNum}";
			//不限人数的行索引
			var noLimitTrIndex = [];
			//岗位编制记录
			var postRelation = [];
			//要删除的岗位编制
			var deletePostRelation=[];
			//json字符串
			var postRelationStr ='${postsJson}';
			try{
				postRelation = JSON.parse(postRelationStr);
			}catch(e){
				if(console){
					console.log(e)
				}
			}
			function initIsLimit(){
				if(isLimitNum=="true"||isLimitNum==true){
					$(".ld-setting-compile-radio input[type=radio]").eq(1).attr("checked",true);
					form['fdIsLimitNum'] =true;
					$(".ld-setting-compile-radio input[type=text]").show();
					$(".ld-setting-compile-radio input[type=text]").val(form['fdCompileNum']);
				}else{
					$(".ld-setting-compile-radio input[type=radio]").eq(0).attr("checked",true);
					$(".ld-setting-compile-radio input[type=text]").hide();
					form['fdIsLimitNum']=false;
				}
			}
			//初始化是否限制
			if(form['fdIsCompileOpen']=="true"){
				initIsLimit();
			}else{
				initIsLimit();
				$("#setting-compile-content").hide();
			}
			
			function _submit(windowParent){
				var url = '${LUI_ContextPath}/hr/organization/hr_organization_element/hrOrganizationElement.do?method=update';
				var compileUrl = "${KMSS_Parameter_ContextPath}hr/organization/hr_organization_post/hrOrganizationPost.do?method=setPostParent";
				var _compilenum = $(".ld-setting-compile-number-limit input[type=text]").val();
				var isOpen = $("input[name=fdIsCompileOpen]").val();
				if(noLimitTrIndex.length>0){
					if(form['fdIsLimitNum']==true||form['fdIsLimitNum']=="true"){
					 	seajs.use(["lui/dialog"],function(dialog){
		       	    		dialog.alert("${lfn:message('hr-organization:hr.organization.info.tip.11')}");
		       	    	})
		       	    	var orgLimitNum =$(".ld-setting-compile-number-limit input[type=text]").val();
		       	    	if(orgLimitNum&&isNaN(orgLimitNum)){
		       	    		
		       	    	}
		       	    	return;
					}
				}

				if(isOpen == "true"){
					if(form['fdIsLimitNum']==true||form['fdIsLimitNum']=="true"){
						if(Number(_compilenum)<postsCompileNum){
							seajs.use(["lui/dialog"],function(dialog){
								dialog.alert("${lfn:message('hr-organization:hr.organization.info.tip.12')}");
							})
							return;
						}
					}
				}
			     $.ajax({     
		       	     type:"post",   
		       	     url:url,
		       	     data:form,    
		       	     async:false,
		       	     success:function(data){
		       	    	if(data){
						     $.ajax({     
					       	     type:"post",   
					       	     url:compileUrl,
					       	  	 dataType:"json",
					       	     data:{postJson:JSON.stringify(postRelation),deletePostJson:deletePostRelation.join(";")},    
					       	     async:false,
					       	     success:function(data){
					       	    	seajs.use(["lui/dialog"],function(dialog){
					       	    		dialog.success("${lfn:message('hr-organization:hr.organization.info.tip.13')}")
					       	    		windowParent.refreshTable()
					       	    		window.$dialog.hide();
					       	    	})
					       		 }    
				         	}); 	       	    		
		       	    	}
		       	    	
		       		 }    
	         	}); 

			}
			seajs.use(["lui/dialog"],function(dialog){
				LUI.ready(function(){
					var tableList = [];
					$(".ld-setting-compile-radio input[type=radio]").on("click",function(e){
						if(e.target.value==="false"){
							$(".ld-setting-compile-radio input[type=text]").hide();
							form['fdIsLimitNum']=false
							
						}else{
							$(".ld-setting-compile-radio input[type=text]").show();
							form['fdIsLimitNum']=true
							
						}
					});
					$(".ld-setting-compile-radio input[type=text]").on("change",function(e){
						if(isNaN(Number(e.target.value))){
							dialog.alert("${lfn:message('hr-organization:hr.organization.info.tip.14')}");
						}else{
							form['fdCompileNum']=Number(e.target.value);
						}
						
					})
					//删除岗位编制
					window.onDeleteTr = function(target){
						var parentTr = $(target).closest("tr");
						var index = $(".deleteTr").index($(target));
						postsCompileNum = postsCompileNum-Number(postRelation[index]['fdCompileNum']);
						deletePostRelation.push(postRelation[index]['postId']);
						postRelation.splice(index,1);
						var noLimitIndex = noLimitTrIndex.indexOf(index)
						if(noLimitIndex!=-1){
							noLimitTrIndex.splice(noLimitIndex,1);
						}
						parentTr.remove();
					}
					//是否开启编制回调
					window.isOpenCompile=function(v){
						var isOpen = $("#switchWidget input[name=fdIsCompileOpen]").val();
						form['fdIsCompileOpen'] = isOpen;
						if(isOpen=="true"){
							$("#setting-compile-content").show();
							initIsLimit()
						}else{
							$("#setting-compile-content").hide();
							initIsLimit()
						}
					}
					//新增一行编制内容
					function addItem(data,noAppend){
						var newTr =$("<tr class='compile-list-tr'></tr>");
						var compileNum = data.fdCompileNum-data['postsNum'];
						var personNum;
						if(compileNum>0){
							compileNum="<span class='dolt-blue'></span><span class=''>${lfn:message('hr-organization:hr.organization.info.setup.shortage')} "+compileNum+"人</span>";
						}else if(compileNum==0){
							compileNum="<span class='dolt-full'></span><span class=''>${lfn:message('hr-organization:hr.organization.info.setup.manbian')}</span>";
						}else{
							if(isNaN(compileNum)){
								compileNum="<span class=''>${lfn:message('hr-organization:hr.organization.info.setup.unlimited.num')}</span>"
							}else{
								compileNum = "<span class='dolt-over'></span><span class=''>${lfn:message('hr-organization:hr.organization.info.setup.overbooking')}"+Math.abs(compileNum)+"人</span>";
							}
						}
						if(isNaN(compileNum)){
							personNum = 0;
						}else{
							personNum = data['postsNum'];
						}
						newTr.append("<td>"+data.postName+"</td>");
						newTr.append("<td>"+data.fdCompileNum+"</td>");
						newTr.append("<td>"+personNum+"</td>");
						newTr.append("<td>"+compileNum+"</td>");
						newTr.append("<td><span class='deleteTr' onclick='window.onDeleteTr(this)'>${lfn:message('button.delete')}</span></td>");
						if(noAppend){
							return newTr;
						}else{
							$("#compile-list").append(newTr);
						}
					}
					//新增岗位编制
					$(".ld-setting-compile-list-add").on("click",function(){
						var limitNum = $(".ld-setting-compile-number-limit input[type=text]").val();
				    	var path = "/hr/organization/hr_organization_compile/hrOrganizationElement_compile_post_add.jsp?fdId=${hrOrganizationElementForm.fdId}&fdIsLimitNum="+form['fdIsLimitNum'];
				    	path=path+"&currIsLimitNum="+"&surCompileNum="+(Number(limitNum)-Number(postsCompileNum))+"&currCompileNum="+limitNum;
				    	var isLimit = $(".ld-setting-compile-radio input[type=radio]:checked").val();
					    	if(isLimit=="true"){
					    		if(!limitNum||isNaN(Number(limitNum))||Number(limitNum)<0){
					    			dialog.alert("${lfn:message('hr-organization:hr.organization.info.tip.15')}");
					    			return
						    	}
					    	}
					    	var dialogObj = dialog.iframe(path,
					    			"${lfn:message('button.add')}${lfn:message('hr-organization:hr.organization.info.setup.deptpost')}"
					    			,function(value){
			    	   			if(value == "success"){
			    	    			location.reload();
			    	   			}
			        		},{
			        			buttons:[{
			        				name:"${lfn:message('button.ok')}",
			        				fn:function(){
			        					var res = dialogObj.element.find("iframe").get(0).contentWindow._submit();
			        					if(res){
			        						//数据更新
			        						var postIdArr=[]
			        						if(deletePostRelation.indexOf(res['postId'])!=-1){
			        							deletePostRelation.splice(deletePostRelation.indexOf(res['postId']),1);
			        						}
											for(var i = 0;i<postRelation.length;i++){
												postIdArr.push(postRelation[i]['postId']);
											}
											//添加的岗位是否已经存在
			        						var hasIndex = postIdArr.indexOf(res['postId']);
			        						if(hasIndex!=-1){
			        							$("#compile-list tr").eq(hasIndex+1).remove();
			        							var overTr = addItem(postRelation[hasIndex],true);
			        							var aTr = $("#compile-list tr").eq(hasIndex+1).get(0);
			        							if(aTr){
			        								overTr.insertBefore($(aTr));
			        							}else{
			        								overTr.insertAfter($("#compile-list tr").eq(hasIndex));
			        							}
			        						}else{
			        							addItem(res);
				        						postRelation.push(res);
			        						}
			        						if(res["fdIsLimitNum"]==true||res["fdIsLimitNum"]=="true"){
			        							postsCompileNum=Number(postsCompileNum)+Number(res['fdCompileNum']);
			        						}else{
			        							noLimitTrIndex.push(postRelation.length-1);
			        						}
			        						dialogObj.hide();
			        					}
			        				}
			        			},{
			        				name:"${lfn:message('button.cancel')}",
			        				fn:function(){
			        					dialogObj.hide();
			        				}
			        			}],
			    				width : 700,
			    				height : 400
			    			});				    		
				    	
				    	//
				    	
				    })
					
				})	
			})
		</script>   			
	</template:replace>
</template:include>
