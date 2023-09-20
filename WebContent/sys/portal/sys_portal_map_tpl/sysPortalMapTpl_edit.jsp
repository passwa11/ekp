<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="content">
	<style type="text/css">
	    
	    	.lui_paragraph_title{
	    		font-size: 15px;
	    		color: #15a4fa;
	        	padding: 15px 0px 5px 0px;
	    	}
	    	.lui_paragraph_title span{
	    		display: inline-block;
	    		margin: -2px 5px 0px 0px;
	    	}
	    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
	      		border: 0px;
	      		color: #868686
	    	}
	    	.slect_sys_data{
	    		width:400px;
	    		border-left:none;
	    		border-right:none;
	    		border-top:none;
	    		
	    	}
	    	.selectedData{
	    		position:relative;
	    		display:inline-block;
	    		width:50%;
	    		height:20px;
	    	}
	    	.selectedData .maskSelect{
	    		position:absolute;
	    		left:0;
	    		top:0;
	    		display:inline-block;
	    		width:100%;
	    		height:100%;
	    	}
	    	
	    	.luiPortalMapTplHideAtt .lui_upload_img_box,.luiPortalMapTplHideAtt .lui_upload_container{
	    		display: none!important;
	    	}
	</style>
	<script type="text/javascript">
	    var formInitData = {
	
	    };
	    var messageInfo = {
	
	    };
	    Com_IncludeFile("security.js");
	    Com_IncludeFile("domain.js");
	    Com_IncludeFile("form.js");
	    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/sys/portal/sys_portal_map_tpl/resource/js/", 'js', true);
	    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/portal/sys_portal_map_tpl/", 'js', true);
	    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
	    Com_IncludeFile("doclist.js", "${LUI_ContextPath}/resource/js/", 'js', true);
	</script>
	
	    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>
	
	<html:form action="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do">
	    <div id="optBarDiv">
	        <c:choose>
	            <c:when test="${sysPortalMapTplForm.method_GET=='edit'}">
	                <input type="button" value="${ lfn:message('button.update') }" onclick="MapTpl_Submit(document.sysPortalMapTplForm, 'update');">
	                <input type="button" value="${lfn:message('sys-portal:sys.portal.preview')}" onclick="window.preview('sys.ui.MapTpl.desc')"/>
	            </c:when>
	            <c:when test="${sysPortalMapTplForm.method_GET=='add'}">
	                <input type="button" value="${ lfn:message('button.save') }" onclick="MapTpl_Submit(document.sysPortalMapTplForm, 'save');">
	                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="MapTpl_Submit(document.sysPortalMapTplForm, 'saveadd');">
	                <input type="button" value="${lfn:message('sys-portal:sys.portal.preview')}" onclick="window.preview('sys.ui.MapTpl.desc')"/>
	            </c:when>
	        </c:choose>
	        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
	    </div>
	    <p class="txttitle">${ lfn:message('sys-portal:table.sysPortalMapTpl') }</p>
	
	    <center>
	        <div style="width:95%;">
	            <table class="tb_normal" width="100%">
	                <tr >
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('sys-portal:sysPortalMapTpl.fdName')}
	                    </td>
	                    <td >
	                        <div id="_xform_fdName" _xform_type="text">
	                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
	            </table>
	            <br/>
	            
	            <table class="tb_normal" width="100%">
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('sys-portal:sysPortalMapTpl.tplType')}
	                    </td>
	                    <td>
	                        <div id="_xform_tplType" _xform_type="radio">
	                            <xform:radio property="tplType" htmlElementProperties="id='tplType'" showStatus="edit" validators=" digits min(0) max(4)" onValueChange="mapTypeChange" value="${sysPortalMapTplForm.tplType==null?0:sysPortalMapTplForm.tplType}">
	                                <xform:enumsDataSource enumsType="sys_portal_tpl_type" />
	                            </xform:radio>
	                        </div>
	                    </td>
	                </tr>
	            </table>
	            <br/>
	            <input type="hidden" value="" name="fdMapInlets" id="fdMapInlets"/>
	            
	            <c:choose>
		          	<c:when test="${sysPortalMapTplForm.fdIsCustom==true }">
		          		<c:set value="true" var="hasCustom"></c:set>
		          	</c:when>
		          	<c:otherwise>
		          		<c:set value="false" var="hasCustom"></c:set>
		          	</c:otherwise>
		          </c:choose>
		          
		           <c:choose>
		          	<c:when test="${'3' == sysPortalMapTplForm.tplType }">
		          		<c:set value="true" var="hasAtt" scope="request"></c:set>
		          	</c:when>
		          	<c:otherwise>
		          		<c:set value="false" var="hasAtt" scope="request"></c:set>
		          	</c:otherwise>
		          </c:choose>
	            <table class="tb_normal" width="100%" id="navContainer">
	            	<tr>
	            		<td class="td_normal_title" width="15%">${lfn:message('sys-portal:sysPortalMapTpl.nav')}</td>
	            		<td>
	            			<xform:radio property="fdIsCustom" showStatus="edit" onValueChange="mapCustomChange" value="${sysPortalMapTplForm.fdIsCustom==null?false:sysPortalMapTplForm.fdIsCustom}">
                                <xform:enumsDataSource enumsType="sys_portal_tpl_fdIsCustom" />
                            </xform:radio>
                            <table class="tb_normal ${hasAtt?'':'luiPortalMapTplHideAtt'}" width="100%" id="TABLE_DocLink" tbdraggable="true"
				          		style="display:${hasCustom?"none":"table"};margin-top:20px">
				            	<tr KMSS_IsReferRow="1" >
				            		<td align="center" width="10%">
										<input type="checkbox" name="DocList_Selected">
									</td>
				            		<td width="65%">
				            			<span class="selectedData">
				            				<xform:text  value="" property="fdPortalNavForms[!{index}].fdNavName" required="true" showStatus="edit"  style="width:90%;"/>
				            				<span class="maskSelect"></span>
				            				<input type="hidden" name="fdPortalNavForms[!{index}].fdNavId">
				            			</span>
				            			<a class="selectClick" href="javascript:void(0)" >${lfn:message('sys-portal:sysPortal.select')}</a>
				            		</td>
				            		
				            		<td>
				            			<input
											name="fdPortalNavForms[!{index}].fdAttachmentId" value=""
											type="hidden" /> <c:import
												url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
												charEncoding="UTF-8">
												<c:param name="dtable" value="true" />
												<c:param name="fdKey" value="TABLE_DocLink.fdAttachment" />
												<c:param name="fdAttType" value="pic" />
												<c:param name="fdShowMsg" value="true" />
												<c:param name="fdMulti" value="false" />
												<c:param name="fdLayoutType" value="pic" />
												<c:param name="fdPicContentWidth" value="400" />
												<c:param name="fdPicContentHeight" value="150" />
												<c:param name="fdViewType" value="pic_single" />
												<c:param name="idx" value="!{index}" />
											</c:import>
				            		</td>
				            		
				            		<td align="center"  class="td_operate">
										<span style='cursor:pointer' class='optStyle opt_copy_style'  title='<bean:message bundle="sys-xform" key="xform.button.copy" />' onmousedown='DocList_CopyRow();'>
										</span>&nbsp;&nbsp;
										<span style="cursor:pointer" class="optStyle opt_del_style" title="<bean:message bundle="sys-xform" key="xform.button.delete" />" onmousedown="DocList_DeleteRow_ClearLast();">
										</span>
									</td>
									
				            	</tr>
				            	
				            	<c:forEach items="${sysPortalMapTplForm.fdPortalNavForms}" var="nav"
									varStatus="status">
									<tr KMSS_IsContentRow="1" data-index="${status.index}">
										<td align="center" width="10%">
										<input type="checkbox" name="DocList_Selected">
										</td>
					            		<td width="65%">
					            			<span class="selectedData">
					            				<xform:text  value="" property="fdPortalNavForms[${status.index}].fdNavName" required="true" showStatus="edit"  style="width:90%;"/>
					            				<span class="maskSelect"></span>
					            				<input type="hidden" name="fdPortalNavForms[${status.index}].fdNavId" value="${nav.fdNavId }">
					            			</span>
					            			<a class="selectClick" href="javascript:void(0)" >${lfn:message('sys-portal:sysPortal.select')}</a>
					            		</td>
					            		
					            		<td>
					            			<input
												name="fdPortalNavForms[${status.index}].fdAttachmentId" value="${nav.fdAttachmentId }"
												type="hidden" /> <c:import
													url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
													charEncoding="UTF-8">
													<c:param name="dtable" value="true" />
													<c:param name="fdKey" value="TABLE_DocLink.fdAttachmentId" />
													<c:param name="fdAttType" value="pic" />
													<c:param name="fdShowMsg" value="true" />
													<c:param name="fdMulti" value="false" />
													<c:param name="fdLayoutType" value="pic" />
													<c:param name="fdPicContentWidth" value="400" />
													<c:param name="fdPicContentHeight" value="150" />
													<c:param name="fdViewType" value="pic_single" />
													<c:param name="formName" value="sysPortalMapTplForm" />
													<c:param name="formListAttribute" value="fdPortalNavForms" />
													<c:param name="idx" value="${status.index}" />
												</c:import>
					            		</td>
					            		
					            		<td align="center"  class="td_operate">
											<span style='cursor:pointer' class='optStyle opt_copy_style'  title='<bean:message bundle="sys-xform" key="xform.button.copy" />' onmousedown='DocList_CopyRow();'>
											</span>&nbsp;&nbsp;
											<span style="cursor:pointer" class="optStyle opt_del_style" title="<bean:message bundle="sys-xform" key="xform.button.delete" />" onmousedown="DocList_DeleteRow_ClearLast();">
											</span>
										</td>
										
									</tr>
									
								</c:forEach>
		
		
								<tr class="tr_normal_opt" type="optRow" invalidrow="true">
									<td colspan="4" align="center" column="0" row="3" coltype="optCol">
										<div class="tr_normal_opt_content">
											<div class="tr_normal_opt_l">
												<label class="opt_ck_style">
													<input type="checkbox" name="DocList_SelectAll" onclick="DocList_SelectAllRow(this);"><bean:message bundle="sys-xform" key="xform.button.selectAll" />
												</label>
												<span class="optStyle opt_batchDel_style" onclick="DocList_BatchDeleteRow();" title="<bean:message bundle="sys-xform" key="xform.button.delete" />">
												</span>
											</div>
												<div class="tr_normal_opt_c"> 
													<span class="optStyle opt_add_style" title='<bean:message bundle="sys-xform" key="xform.button.add" />'  onclick="DocList_AddRow()"></span>
													<span class="optStyle opt_up_style"  title='<bean:message bundle="sys-xform" key="xform.button.moveup" />'  onclick="DocList_MoveRowBySelect(-1);"></span>
													<span class="optStyle opt_down_style" title='<bean:message bundle="sys-xform" key="xform.button.movedown" />'  onclick="DocList_MoveRowBySelect(1);"></span>
												</div>
										</div>
									</td>
								</tr>
				            </table>
	            		</td>
	            	</tr>
		          </table>
		          
	              <div style="display:${hasCustom?"block":"none"}" id="customContainer">
	              	 <!-- 自定义数据源 -->
		              <c:import url="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl_custom.jsp" charEncoding="utf-8">
		              </c:import>
	              </div>
	              
	              <br>
		          
		          <c:choose>
		          	<c:when test="${sysPortalMapTplForm.tplType==1 || sysPortalMapTplForm.tplType==2  }">
		          		<c:set value="true" var="hasDoc"></c:set>
		          	</c:when>
		          	<c:otherwise>
		          		<c:set value="false" var="hasDoc"></c:set>
		          	</c:otherwise>
		          </c:choose>
		          		          
		          
				 <table class="tb_normal" width="100%" id="conEntrance" style="display:${hasDoc?"table":"none"}">
	            	<tr >
	            		<td class="td_normal_title" width="15%">${lfn:message('sys-portal:table.sysPortalMapInlet')}</td>
	            		<td>
	            		
	            		
				           <table class="tb_normal" width="100%" id="TABLE_DocList">
				           
				           
				           		<tr KMSS_IsReferRow="1">
				           			<td width="10%" align="center">
				           			<input type="checkbox" name="DocList_Selected">
				           			</td>
				           			<td width="55%">
				           				<xform:text htmlElementProperties="class='inletTitle inputsgl'" showStatus="edit" property="fdMapInletForms[!{index}].fdName" style="width:50%;" /><span>${lfn:message('sys-portal:sysPortalMapInlet.fdName')}</span><br/>
					            		<xform:text htmlElementProperties="class='inletUrl inputsgl'" showStatus="edit" property="fdMapInletForms[!{index}].fdUrl" style="width:50%;" /><span>${lfn:message('sys-portal:sysPortalMapInlet.fdURL')}</span>
				           			</td>
				           			<td></td>
				           		</tr>
				           		<c:forEach items="${sysPortalMapTplForm.fdMapInletForms}"  var="fdMapInletForm" varStatus="vstatus">
					            	<tr KMSS_IsContentRow="1" >
					            		<td align="center" width="10%">
											<input type="checkbox" name="DocList_Selected">
										</td>
					            		<td width="55%">
					            			<xform:text htmlElementProperties="class='inletTitle inputsgl'" showStatus="edit" property="fdMapInletForms[${vstatus.index}].fdName" style="width:50%;" /><span>${lfn:message('sys-portal:sysPortalMapInlet.fdName')}</span><br/>
					            			<xform:text htmlElementProperties="class='inletUrl inputsgl'" showStatus="edit" property="fdMapInletForms[${vstatus.index}].fdUrl" style="width:50%;" /><span>${lfn:message('sys-portal:sysPortalMapInlet.fdURL')}</span>
					            			<html:hidden property="fdMapInletForms[${vstatus.index}].fdId" />
					            		</td>
										<td align="center"  class="td_operate">
											<span style='cursor:pointer' class='optStyle opt_copy_style'  title='<bean:message bundle="sys-xform" key="xform.button.copy" />' onmousedown='DocList_CopyRow();'>
											</span>&nbsp;&nbsp;
											<span style="cursor:pointer" class="optStyle opt_del_style" title="<bean:message bundle="sys-xform" key="xform.button.delete" />" onmousedown="DocList_DeleteRow_ClearLast();">
											</span>
										</td>
					            	</tr>
				            	</c:forEach>
				            	
								<tr class="tr_normal_opt" type="optRow" invalidrow="true">
									<td colspan="4" align="center" column="0" row="3" coltype="optCol">
										<div class="tr_normal_opt_content">
											<div class="tr_normal_opt_l">
												<label class="opt_ck_style">
													<input type="checkbox" name="DocList_SelectAll" onclick="DocList_SelectAllRow(this);"><bean:message bundle="sys-xform" key="xform.button.selectAll" />
												</label>
												<span class="optStyle opt_batchDel_style" onclick="DocList_BatchDeleteRow();" title="<bean:message bundle="sys-xform" key="xform.button.delete" />">
												</span>
											</div>
												<div class="tr_normal_opt_c"> 
													<span class="optStyle opt_add_style" title='<bean:message bundle="sys-xform" key="xform.button.add" />'  onclick="DocList_AddRow()"></span>
													<span class="optStyle opt_up_style"  title='<bean:message bundle="sys-xform" key="xform.button.moveup" />'  onclick="DocList_MoveRowBySelect(-1);"></span>
													<span class="optStyle opt_down_style" title='<bean:message bundle="sys-xform" key="xform.button.movedown" />'  onclick="DocList_MoveRowBySelect(1);"></span>
												</div>
										</div>
									</td>
								</tr>
				            </table>	
	            		</td>
	            	</tr>
	              </table>
	              
	             
	            <br style="display:${hasDoc?"block":"none"}"/>
	            <table class="tb_normal" width="100%">
	               
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('sys-portal:sysPortalMapTpl.authEditors')}
	                    </td>
	                    <td colspan="3" width="85.0%">
	                        <div id="_xform_authEditorIds" _xform_type="address">
	                            <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
	            </table>
	            <table class="tb_normal" width="100%">
	                <tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('sys-portal:sysPortalMapTpl.docCreator')}
	                    </td>
	                    <td width="35%">
	                        <div id="_xform_docCreatorId" _xform_type="address">
	                            <ui:person personId="${sysPortalMapTplForm.docCreatorId}" personName="${sysPortalMapTplForm.docCreatorName}" />
	                        </div>
	                    </td>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('sys-portal:sysPortalMapTpl.docCreateTime')}
	                    </td>
	                    <td width="35%">
	                        <div id="_xform_docCreateTime" _xform_type="datetime">
	                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
	                        </div>
	                    </td>
	                </tr>
	            </table>	            
	        </div>
	    </center>
	    <html:hidden property="fdId" />
	    <html:hidden property="method_GET" />
	    <script>
	        $KMSSValidation();
	         
	         DocList_Info=["TABLE_DocList","TABLE_DocLink","TABLE_DocCustom"];
	         //选择系统导航
		   	 seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {

		   		 $("#TABLE_DocLink").on("click","a",function(event){
		   			 var ev = event||window.event;
		   			 var target = ev.target||ev.srcElement;
		   			 if(target.className ==="selectClick" ){
		   				dialog.iframe("./sysPortalNavData.jsp?selectIndex=","${lfn:message('sys-portal:sysPortalMapTpl.selectNav')}",null,{width:700,height:500,canClose:true,close:true});
		   				window.sysportMapObj = target;
		   			 }
		   		 });
				
		         window.selectSysData=function(data){
			   		$(window.sysportMapObj).prev().find("input[name*='fdNavName']").val(data.fdName);
			   		$(window.sysportMapObj).prev().find("input[name*='fdNavId']").val(data.fdId);
		         }
		         //切换type类型
		         window.mapTypeChange=function(newV){
		     			if(newV==='1'||newV=='2'){
		     				$("#conEntrance").css("display","table");
		     			}else{
		     				$("#conEntrance").css("display","none");
		     			}
		     			
		     			// 只有工作地图才显示背景设置
		     			if(newV=='3'){
		     				$('#TABLE_DocLink').removeClass('luiPortalMapTplHideAtt');
		     				$('#TABLE_DocCustom').removeClass('luiPortalMapTplHideAtt');
		     			}else{
		     				$('#TABLE_DocLink').addClass('luiPortalMapTplHideAtt');
		     				$('#TABLE_DocCustom').addClass('luiPortalMapTplHideAtt');
		     			}
		     			
		         }
		         
		         // 切换导航数据源类型
		         window.mapCustomChange = function(val) {
					if(val == 'true'){
						$("#customContainer").css("display","block");
						$("#TABLE_DocLink").css("display","none");
					}else{
	     				$("#customContainer").css("display","none");
	     				$("#TABLE_DocLink").css("display","table");
	     			}	
				
		         }
		         
		        function setForm(data){
		        	
		        	
		        	//获取便捷入口数据
					var len = $(".inletTitle").length>$(".inletURL").length?$(".inletTitle").length:$(".inletURL").length;
					var inletArr = [];
					for(var j = 0;j<len;j++){
						inletArr.push({text:$(".inletTitle").eq(j).val()||'',href:$(".inletURL").eq(j).val()||''})
					}
					
		        	var iframeObj = dialog.iframe("./sysPortalMapTpl_prview.jsp?fdId=",
				 			"${lfn:message('sys-portal:sys.portal.preview')}", null, {
						width : 920,
						height : 560,
					});  
		        	
		        	var temp = setInterval(function(){
						try{
							if($(iframeObj.element[0]).find("iframe").get(0)!=undefined){
								var oIframe = $(iframeObj.element[0]).find("iframe").get(0);
								oIframe.contentWindow.setForm(data,inletArr);
								clearInterval(temp);
							}
						}catch(e){
							console.log(e);
						}
						
					},100);
		        	
		        }
		         
	 			window.preview = function(id) {
	 				
	 				var isCustom = $('input:radio[name="fdIsCustom"]:checked').val();
	 				var type = $('input:radio[name="tplType"]:checked').val();
	 				
	 				// 自定义数据源
	 				if(isCustom == 'true'){
	 					
	 					var datas = []
	 					
	 					var names = $('#TABLE_DocCustom').find('tr[trdraggable="true"]').each(function(i){
	 						
	 						var name = $(this).find('input[name*="fdName"]').val();
	 						var attId = $(this).find('.lui_upload_img_item').attr('id')
	 						
	 						var data = {name:name,attId:attId};
	 						
	 						var json = trees[i].getData();
	 						if(json){
	 							data.content = json;
	 						}
	 						datas.push(data)
	 					});

	 					if(datas.length>0){
	 						datas[0].type = type
	 					}
	 					setForm(datas)
	 					return;
	 				}
	 				
     				var oDocLink = document.querySelector("#TABLE_DocLink");
					var fdIds = oDocLink.querySelectorAll("input[name*='fdNavId']");
					if((fdIds[0].value==""&&fdIds.length<2)||!fdIds){
						dialog.alert("请选择导航数据再进行预览");
						return ;
					}
					var fdIdArr = [];
					for(var i = 0;i<fdIds.length;i++){
						
						if(fdIds.value!=""){
							fdIdArr.push(fdIds[i].value);
						}
					} 
					
					//获取系统导航数据用于预览
					$.ajax({
						type:"get",
						url:"sysPortalMapTpl.do?method=getSysNavInfo&&fdIds="+fdIdArr.join(";"),
						success:function(data){
							data[0].type=type;
							// 附件信息
							$('#TABLE_DocLink').find('tr[trdraggable="true"]').each(function(i){
								var attId = $(this).find('.lui_upload_img_item').attr('id')
								data[i].attId = attId
							})
							//弹出预览窗口
							setForm(data);
							
						}
					});
			 	 }
		 			
		   	 });
		   	


	    </script>
	</html:form>
	<script>
	  /*******************/
	  function MapTpl_Submit(form,method){

		//修复选择导航为空后提交会自动获取焦点，导致能够编辑数据
		$(".selectedData input").on("focus",function(){
			$(this).blur();
		});
			
	 	Com_Submit(form,method);
	  }
	    
	</script>
	
	 </template:replace>
</template:include>