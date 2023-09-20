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
		<link rel="stylesheet" 
			href="${LUI_ContextPath}/hr/organization/resource/css/organization.css">
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
	            <div class="ld-setting-compile-form">
                    <div class="enableSwitch">
                        <h3>启用组织编制</h3>
                        <%-- 是否启用--%>
                        <html:hidden property="fdIsCompileOpen" /> 
						<label class="weui_switch">
							<span class="weui_switch_bd" id="weui_switch_compile_open">
								<input type="checkbox" ${not empty param.isOpen ? 'checked' : '' } />
								<span></span>
								<small></small>
							</span>
							<span id="fdIsCompileOpenText"></span>
						</label>
						<script type="text/javascript">
							function setFdIsCompileOpenText(status) {
								if(status) {
									$("#fdIsCompileOpenText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.true" />');
								} else {
									$("#fdIsCompileOpenText").text('<bean:message bundle="hr-organization" key="hrOrganizationPost.fdIsAvailable.false" />');
								}
								changeCompileOpen(status);
							}
							function changeCompileOpen(fdIsCompileOpen){
						    	if(fdIsCompileOpen == 'true' || fdIsCompileOpen == true){
						    		$(".prepareNumber").show();
									$("#fdIsLimitNumId").show();
						    	}else{
						    		$(".prepareNumber").hide();
									$("#fdIsLimitNumId").hide();
						    	}
						    }
							$("#weui_switch_compile_open :checkbox").on("click", function() {
								var status = $(this).is(':checked');
								$("input[name=fdIsCompileOpen]").val(status);
								setFdIsCompileOpenText(status);
							});
							setFdIsCompileOpenText('${hrOrganizationElementForm.fdIsCompileOpen}');
						</script>
                    </div>
                    <div class="prepareNumber" style="display:${not empty param.isOpen ? 'block' : 'none' }">
                        <span>编制人数</span>
                        <div>
                        	<html:hidden property="fdIsLimitNum" />
                            <label for=""><input type="radio" name="prepareNumber" value="1" onchange="setCompileNum();">不限人数</label><p>
                            <label><input type="radio" checked name="prepareNumber" value="2" onchange="setCompileNum();">设定人数</label>
                            <xform:text property="fdCompileNum" validators="min(1)" htmlElementProperties="id='fdCompileNum'" showStatus="edit"/></p>
                        </div>
                    </div>
	            </div>
	           
	            <div id="fdIsLimitNumId" style="${not empty param.isOpen ? 'display:block;':'display:none;' }">
	            <div class="ld-setting-compile-info clearfix">
	                <h3>组织内部岗位编制</h3>
	                <div class="ld-setting-compile-info-btn">
	                    <i></i>
	                    <span onclick="addPost();">新增</span>
	                </div>
	            </div>
	            <table>
	            	<c:set value="0" var="totalCompileNum"/>
	                <thead>
	                    <tr>
	                        <th>岗位名称</th>
	                        <th>人员编制</th>
	                        <th>在职人数</th>
	                        <th>缺编/超编</th>
	                        <th>操作</th>
	                    </tr>
	                    <tbody id="compile-list">
	                    	<c:if test="${posts.size()>0 }">
		                    	<c:forEach var="post" items="${posts }">
			                        <tr>
			                            <td>${post.fdName }</td>
			                            <td>
			                            	<c:choose>
			                            		<c:when test="${post.fdIsLimitNum == 'true' }">
			                            			${post.fdCompileNum }
			                            		</c:when>
			                            		<c:when test="${post.fdIsLimitNum == 'false' }">
			                            			不限
			                            		</c:when>
			                            		<c:otherwise>
			                            			未设置
			                            		</c:otherwise>
			                            	</c:choose>
			                            </td>
			                            <td>${fn:length(post.fdPersons)}</td>
			                            <td class="ld-org-compile">
			                            	<c:choose>
			                            		<c:when test="${post.fdIsLimitNum == 'true' }">
			                                		<c:if test="${post.fdCompileNum > fn:length(post.fdPersons) }">
								        				<div class="ld-org-compile">
									        				<span class="vacancy"></span>
									                        <span>缺编${post.fdCompileNum - fn:length(post.fdPersons)}人</span>
								                        </div>
								        			</c:if>
								        			<c:if test="${post.fdCompileNum < fn:length(post.fdPersons) }">
								        				<div class="ld-org-compile">
									        				<span class="exceed"></span>
									                        <span>超编${fn:length(post.fdPersons) - post.fdCompileNum}人</span>
								                        </div>
								        			</c:if>
								        			<c:if test="${post.fdCompileNum == fn:length(hrOrganizationPost.fdBePersons) }">
								        				<div class="ld-org-compile">
									        				<span class="all"></span>
									                        <span>满编</span>
								                        </div>
								        			</c:if>
			                            		</c:when>
			                            		<c:otherwise>
			                            			-
			                            		</c:otherwise>
			                            	</c:choose>
			                            </td>
			                            <td class='deleteTr' onclick="delPost(this);"><span style="cursor:pointer">删除</span></td>
			                           <%--  <c:if test="${post.fdIsLimitNum == 'true' }">
			                            	<c:set value="${totalCompileNum + post.fdCompileNum}" var="totalCompileNum"/>
			                            </c:if> --%>
			                        </tr>
		                        </c:forEach>
	                        </c:if>
				            <c:if test="${posts.size() <1 }">
				            	<div class="ld-setting-compile-info-empty">
				            		<p>暂无数据</p>
				            	</div>
				            </c:if>
	                    </tbody>
	                </thead>
	            </table>
            	</div>
	        </div>
	    </div>
	    <html:hidden property="fdId" />
	    <html:hidden property="method_GET" />
    </html:form>
	</template:replace>
</template:include>
<script type="text/javascript">
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			var posts = [];
			//当前编制人数
			var currCompileNum = Number("${postsCompileNum}");
			
			//当前部门下所有岗位
			var postRelation = JSON.parse('${postsJson}');
			
			$(function(){
				var fdIsLimitNum = '${hrOrganizationElementForm.fdIsLimitNum}';
				if(fdIsLimitNum == 'false'){
					$("input[name='prepareNumber'][value='1']").attr('checked','true');
					$("#fdCompileNum").hide();
				}else{
					$("input[name='prepareNumber'][value='2']").attr('checked','true');
					$("#fdCompileNum").show();
				}
			});
		    function delLastOneStr(str) {
		    	if(null == str || str == 'undefined'){
		    		return null;
		    	}
		        return str.substr(0, str.length - 1);
		    }
		    window.setCompileNum = function(){
		    	var prepareNumber = $("input[name='prepareNumber']:checked").val();
		    	if(prepareNumber == '1'){
		    		$("input[name='fdIsLimitNum']").val(false);
		    		$("#fdCompileNum").hide();
		    	}else{
		    		$("input[name='fdIsLimitNum']").val(true);
		    		$("#fdCompileNum").show();
		    	}
		    }
		    
		    window._submit = function(){
		    	var isSubmit = true;
		    	if($("input[name=prepareNumber]:checked").val()=="2"){
		    		if($("#fdCompileNum").val()<1){
		    			isSubmit=false;
		    		}
		    		if(Number($("#fdCompileNum").val())%1!=0){
		    			isSubmit=false;
		    		}
		    	}
		    	//不限人数或者关闭编制的时候关闭校验
		    	if($("#weui_switch_compile_open :checkbox").is(':checked')){
		    		if($("input[name=prepareNumber]:checked").val()=="1"){
		    			isSubmit=true;
		    		}
		    	}else{
		    		isSubmit=true;
		    	}		    	
		    	if(isSubmit){
					var url = '${LUI_ContextPath}/hr/organization/hr_organization_element/hrOrganizationElement.do?method=update';
					var compileUrl = "${KMSS_Parameter_ContextPath}hr/organization/hr_organization_post/hrOrganizationPost.do?method=setPostParent";
					$.post(url, $(document.hrOrganizationElementForm).serialize(),function(data){
					   if(data!=""){
						   $.ajax({     
					       	     type:"post",   
					       	     url:compileUrl,
					       	     data:{postJson:JSON.stringify(postRelation)},    
					       	     async:false,
					       	     success:function(data){
					       	    	seajs.use(["lui/dialog"],function(dialog){
					       	    		dialog.success('<bean:message key="return.optSuccess" />');
										window.$dialog.hide("success");
					       	    	})
					       		 }    
				         	});
						}else{
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					})		    		
		    	}else{
		    		dialog.failure("设定人数必须为大于0的整数")
		    	}	
		    }
		    
		    //新增部门内岗位编制
		    window.addPost = function(){
		    	var fdIsLimitNum = $("input[name='fdIsLimitNum']").val();
		    	var totalCompileNum = "${totalCompileNum}";
		    	var fdCompileNum = $('input[name="fdCompileNum"]').val();
		    	var surCompileNum = fdCompileNum-currCompileNum;
		    	var path = "/hr/organization/hr_organization_compile/hrOrganizationElement_compile_post_add.jsp?fdId=${hrOrganizationElementForm.fdId}&fdIsLimitNum="+fdIsLimitNum+"&surCompileNum="+surCompileNum+"&fdCompileNum="+fdCompileNum;
		    	var dialogObj = dialog.iframe(path,"新增部门内岗位编制",function(value){
    	   			if(value == "success"){
    	    			location.reload();
    	   			}
        		},{
        			buttons:[{
        				name:"确定",
        				fn:function(){
        					var res = dialogObj.element.find("iframe").get(0).contentWindow._submit();
        					if(res){
        						$('.ld-setting-compile-info-empty').css("display", "none");
        						var flag = true;
        						for(var i=0; i<postRelation.length; i++){
        							if(postRelation[i].postId == res.postId){
        								flag = false;
        							}
        						}
        						if(flag){
	        						addItem(res);
	        						postRelation.push(res);
	        						if(res.fdIsLimitNum =='1'){
	        							currCompileNum += Number(res.fdCompileNum);
	        						}
	        						console.log(postRelation);
        						}
	        					dialogObj.hide();
        					}
        				}
        			},{
        				name:"取消",
        				fn:function(){
        					dialogObj.hide();
        				}
        			}],
    				width : 700,
    				height : 400
    			});
		    }
		    
		    function addItem(data){
				var newTr =$("<tr class='compile-list-tr'></tr>");
				var compileNum = data.fdCompileNum-data['postsNum'];
				if(compileNum>0){
					compileNum="<div class='ld-org-compile'><span class='vacancy'></span><span class=''>缺编"+compileNum+"人</span></div>";
				}else{
					compileNum = "<div class='ld-org-compile'><span class='all'></span><span class=''>超编"+Math.abs(compileNum)+"人</span></div>";
				}
				newTr.append("<td>"+data.postName+"</td>");
				newTr.append("<td>"+data.fdCompileNum+"</td>");
				newTr.append("<td>"+data['postsNum']+"</td>");
				newTr.append("<td>"+compileNum+"</td>");
				newTr.append("<td><span class='deleteTr' onclick='window.delPost(this)'>删除</span></td>");
				console.log(newTr);
				$("#compile-list").append(newTr);
			}
		    
		    window.delPost = function(target){
		    	/* dialog.confirm('确认是否删除该组织内岗位？',function(value){
					if(value==true){
						$.ajax({     
			        	     type:"post",   
			        	     url:"${KMSS_Parameter_ContextPath}hr/organization/hr_organization_post/hrOrganizationPost.do?method=delPostParent",    
			        	     data:{postId:postId},    
			        	     async:false,    //用同步方式 
			        	     success:function(data){
			        	    	 if(data.result){
			        	    		 dialog.alert("删除成功！");
			        	    		 location.reload();
			        	    	 }else{
			        	    		 dialog.alert("删除失败，请联系管理员！");
			        	    	 }
			        		 }    
			          	});
					}
				}); */
		    	var parentTr = $(target).closest("tr");
				var index = $(".deleteTr").index($(target));
				currCompileNum = currCompileNum-Number(postRelation[index]['fdCompileNum']);
				postRelation.splice(index,1);
				parentTr.remove();
		    }
		    
		    
	});
</script>