<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.third.ding.util.ThirdDingUtil" %>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@page import="com.landray.kmss.util.StringUtil" %>
<%@page import="com.landray.kmss.third.ding.model.DingConfig" %>
<%@page import="java.util.*" %>

 <%
        //kmss.lang.support 语言支持
		String lang = ResourceUtil.getKmssConfigString("kmss.lang.support");  //  中文|zh-CN;English|en-US;中文|zh-HK
		String defLang = ResourceUtil.getKmssConfigString("kmss.lang.official");
		List langList = new ArrayList();
		if(StringUtil.isNull(defLang)){
			defLang = "中文|zh-CN";
			langList.add(defLang);
		}else{
			String defaultLangSort = defLang.split("\\|")[1];
			
			langList.add(defLang.trim());
	        if(StringUtil.isNotNull(lang)){
	        	String [] langArray = lang.split(";");
	        	//List list = Arrays.asList(langArray);
	        	//System.out.println("list:" + list.toString());
	        	//将官方语言放在list的第一位
	        	for(int i=0;i<langArray.length;i++){
	        		if(langArray[i].contains(defaultLangSort)){
	        			continue;
	        		}
	        		langList.add(langArray[i].trim());
	        	}
	        }
	        
		}
		
		String titleColor = DingConfig.newInstance().getDingTitleColor();
		if (StringUtil.isNull(titleColor)) {
			titleColor = "#FF9A89B9";
		}
		request.setAttribute("titleColor", titleColor);
		request.setAttribute("supportLang", langList);
		request.setAttribute("officialLang", defLang);

     String updateStatus = DingConfig.newInstance().getUpdateMessageStatus();
     request.setAttribute("updateStatus", updateStatus);
 %>
    
        <%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.view">
        <template:replace name="head">
             <link rel="stylesheet" href="../resource/css/custom.css">
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
                			
                			.ding-card-notify-head {
							    padding: 12px 15px;
							    padding-right: 22px;
							    position: relative;
							}
							
							.ding-card-agent-head {
							    padding-top: 12px;
							    padding-left: 15px;
							    position: relative;
							}
							
							.ding-card-agent-title{
							   color: #FF9A89B9;
							   font-size: 18px;
							}
                		
                		
            </style>
            <script type="text/javascript">
                var formInitData = {

                };
                var messageInfo = {

                    'fdDetail2': '${lfn:escapeJs(lfn:message("third-ding:table.thirdDingTemplateDetail"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${thirdDingTodoTemplateForm.fdName} - " />
            <c:out value="${ lfn:message('third-ding:table.thirdDingTodoTemplate') }" />
        </template:replace>
        <template:replace name="toolbar">
            <script>
                function deleteDoc(delUrl) {
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                            if(isOk) {
                                Com_OpenWindow(delUrl, '_self');
                            }
                        });
                    });
                }

                function openWindowViaDynamicForm(popurl, params, target) {
                    var form = document.createElement('form');
                    if(form) {
                        try {
                            target = !target ? '_blank' : target;
                            form.style = "display:none;";
                            form.method = 'post';
                            form.action = popurl;
                            form.target = target;
                            if(params) {
                                for(var key in params) {
                                    var
                                    v = params[key];
                                    var vt = typeof
                                    v;
                                    var hdn = document.createElement('input');
                                    hdn.type = 'hidden';
                                    hdn.name = key;
                                    if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                                        hdn.value =
                                        v +'';
                                    } else {
                                        if($.isArray(
                                            v)) {
                                            hdn.value =
                                            v.join(';');
                                        } else {
                                            hdn.value = toString(
                                                v);
                                        }
                                    }
                                    form.appendChild(hdn);
                                }
                            }
                            document.body.appendChild(form);
                            form.submit();
                        } finally {
                            document.body.removeChild(form);
                        }
                    }
                }

                function doCustomOpt(fdId, optCode) {
                    if(!fdId || !optCode) {
                        return;
                    }

                    if(viewOption.customOpts && viewOption.customOpts[optCode]) {
                        var param = {
                            "List_Selected_Count": 1
                        };
                        var argsObject = viewOption.customOpts[optCode];
                        if(argsObject.popup == 'true') {
                            var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                            for(var arg in argsObject) {
                                param[arg] = argsObject[arg];
                            }
                            openWindowViaDynamicForm(popurl, param, '_self');
                            return;
                        }
                        var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
                        Com_OpenWindow(optAction, '_self');
                    }
                }
                window.doCustomOpt = doCustomOpt;
                var viewOption = {
                    contextPath: '${LUI_ContextPath}',
                    basePath: '/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingTodoTemplate.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=delete&fdId=${param.fdId}">
                   <c:if test="${thirdDingTodoTemplateForm.fdIsdefault == 0}">
                      <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdDingTodoTemplate.do?method=delete&fdId=${param.fdId}');" order="4" />
                   </c:if>                 
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
       
        <template:replace name="content">
               
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('third-ding:table.thirdDingTodoTemplate')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                     <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingTodoTemplate.fdName')}
                            </td>
                            <td  width="35.0%">
                                <%-- 模版名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:35%;" />
                                </div>
                                
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingTodoTemplate.fdIsdefault')}
                            </td>
                             <td width="35%">
                                <%-- 是否为默认模版--%>
                                <div id="_xform_fdIsdefault" _xform_type="select">
                                    <xform:select property="fdIsdefault" htmlElementProperties="id='fdIsdefault'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_isDefault" />
                                    </xform:select>
                                </div>
                            </td>
                        </tr>
                        <c:if test="${thirdDingTodoTemplateForm.fdIsdefault == 0}">
                                            
	                        <tr>
	                            <td class="td_normal_title" width="15%">
	                                ${lfn:message('third-ding:thirdDingTodoTemplate.fdModelName')}
	                            </td>
	                            <td width="35%">
	                                <%-- 所属模块名称--%>
	                                <div id="_xform_fdModelName" _xform_type="text">
	                                     <xform:text property="fdModelNameText" showStatus="view" style="width:35%;" />
	                                </div>
	                            </td>
	                            
	                            <c:if test="${thirdDingTodoTemplateForm.fdIscustom == 1 }">
		                            <td class="td_normal_title" width="15%">
		                                ${lfn:message('third-ding:thirdDingTodoTemplate.fdTemplateId')}
		                            </td>
		                            <td width="35%">
		                                <%-- 表单模版id--%>
		                                <div id="_xform_fdTemplateId" _xform_type="text">
		                                    <xform:text property="fdTemplateName" showStatus="view" style="width:95%;" />
		                                </div>
		                            </td>
	                            </c:if>
	                        </tr>
                        
                        </c:if>
                        <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingTodoTemplate.todo.content')}
                                </td>
                                <td width="85%" colspan="3">
                             
                                  <table class="tb_normal" width="100%" style="text-align: center"  id="TABLE_DocList_fdDetail_Form">
                                        <tr >                                        
                                           <td style="width:40px;">
                                               ${lfn:message('page.serial')}
                                           </td>
                                           <td class="td_normal_title"> ${lfn:message('third-ding:thirdDingTodoTemplate.choose')}</td>       
                                           <td class="td_normal_title"> ${lfn:message('third-ding:thirdDingTodoTemplate.title')}</td>                                    
                                        <tr> 
                                                                            
                                        <c:forEach items="${fdDetail_Form_keyList}" var="fdDetail_FormItem" varStatus="vstatus">
												<tr  class="docListTr">										
													<td class="docList" align="center">
														${vstatus.index+1}
													</td>
													<td class="docList" align="center">
	                                                    <xform:text property="fdTemplateName" showStatus="view" style="width:95%;" value="${fdDetail_FormItem.name }"/>
	                                                </td>
	                                                 <!--显示标题  -->  
		                                                <td class="docList" align="center">
		                                                     
		                                                    <div id="_form!{index}.title" _xform_type="text" tabindex="0"  hidefocus="true" ">
		                                                      <c:forEach items="${supportLang}" var="lang" varStatus="vstatus2">
		                                                         
		                                                          <input type="text" class="inputsgl" name="form[${vstatus.index}].title" readonly="readonly" lang="${lang}" style="width:90%;padding-bottom:5px;padding-top: 5px"/>
		                                                       
		                                                      </c:forEach>
		                                                    
		                                                    </div>
		                                                </td> 
													 
												</tr>
										</c:forEach>
                                      
                                   </table>
                                   
                                </td>
                               
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:module.third.ding.notify.template.way')}
                                </td>
                                <td width="85%" colspan="3">
                                     <xform:checkbox property="fdType"  className="selectsgl" subject="" style="width:45%"  showStatus="view">
			                             <xform:simpleDataSource value="1"><bean:message key="module.third.ding.notify.template.way.todo" bundle="third-ding"/></xform:simpleDataSource>
										 <xform:simpleDataSource value="2"><bean:message key="module.third.ding.notify.template.way.notify" bundle="third-ding"/></xform:simpleDataSource>
			                         </xform:checkbox>
                                 </td>
                            </tr>
                             <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingTodoTemplate.view.show')}
                                </td>
                                <td width="85%" colspan="3">
                                    <div style="padding-top: 10px;">
								      <table width="100%">
								         <tr>
								           <td width="45%" class="wayTodo"><bean:message key="module.third.ding.notify.template.way.todo" bundle="third-ding"/></td>
								           <td width="45%" class="wayNotify"><bean:message key="module.third.ding.notify.template.way.notify" bundle="third-ding"/></td>
								         </tr>
								         <tr>
								           <td class="wayTodo">
								              <%-- 效果预览之待办--%>
			                                    <div class="ding-card-demo">
											        <div class="ding-card-demo-head">
											            <i class="ding-card-demo-icon"></i>
											            <span class="ding-card-demo-title">${lfn:message('third-ding:thirdDingTodoTemplate.view.content')}</span>
											            <i class="ding-card-demo-more"></i>
											        </div>
											        <div class="ding-card-demo-content">
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.view.title')}：</em><span>${lfn:message('third-ding:thirdDingTodoTemplate.view.content')}</span></p>
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.docCreator')}：</em><span>${lfn:message('third-ding:thirdDingTodoTemplate.view.name')}</span></p>
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.docCreateTime')}：</em><span>2020-04-29 13:54</span></p>
											        </div>
											        <div class="ding-card-demo-footer">
											            <!-- 左侧信息 -->
											            <div class="ding-card-demo-footerL">2020-04-29 <em>·</em> EKP</div>
											            <div class="ding-card-demo-footerR">
											               ${lfn:message('third-ding:thirdDingTodoTemplate.view.goto')}
											            </div>
											            <!-- 链接 -->
											        </div>
											    </div>
										   </td>
								           <td class="wayNotify">
								               <!-- 效果预览之工作通知 -->
											    <div class="ding-card-demo">
											        <div class="ding-card-agent-head">
											            <span class="ding-card-agent-title" title="标题颜色可以在'集成配置组件'的'钉钉消息标题的颜色'配置">${lfn:message('third-ding:thirdDingTodoTemplate.view.agent.title')}</span>
											        </div>
											        <div class="ding-card-notify-head">
											            <span class="ding-card-demo-title">${lfn:message('third-ding:thirdDingTodoTemplate.view.content')}</span>
											        </div>
											        <div class="ding-card-demo-content">
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.view.title')}：</em><span>${lfn:message('third-ding:thirdDingTodoTemplate.view.content')}</span></p>
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.docCreator')}：</em><span>${lfn:message('third-ding:thirdDingTodoTemplate.view.name')}</span></p>
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.docCreateTime')}：</em><span>2020-04-29 13:54</span></p>
                                                        <c:if test="${'true' eq updateStatus}">
                                                        <p class="ding-card-demo-info">${lfn:message('third-ding-notify:thirdDingNotifyMessage.status.done')}</p>
                                                        </c:if>
                                                    </div>

											    </div>
								           </td>
								         </tr>
								      </table>
								   </div>
                                </td>                                                         
	                             
                            </tr>
                            <tr>
                            <td class="td_normal_title" width="15%">
                                  ${lfn:message('third-ding:thirdDingTodoTemplate.docCreateTime')}
                            </td>
                            <td  width="35.0%">
                                <%-- 模版名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="docCreateTime" showStatus="view" style="width:35%;" />
                                </div>
                                
                            </td>
                            <td class="td_normal_title" width="15%">
                              		 ${lfn:message('third-ding:thirdDingTodoTemplate.docAlterTime')}
                            </td>
                             <td width="35%">
                                <%-- 最后修改时间--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="docAlterTime" showStatus="view" style="width:35%;" />
                                </div>
                            </td>
                             <html:hidden property="fdDetail" />
                        </tr>
                         <tr>
                             <td class="td_normal_title" width="15%">
                                 待办卡片
                             </td>
                             <td width="85%" colspan="3">
                                 <a href="${LUI_ContextPath}/third/ding/third_ding_todo_card/index.jsp#cri.q=fdTemplateId:${param.fdId}">查看</a>
                             </td>
                         </tr>
                    </table>
                   <script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
					<script>
				            var dataDetail = $("input[name='fdDetail']").val();
				    		var dataJson = $.parseJSON(dataDetail);
				    		var dataArray = dataJson['data'];
				    		for(var i=0;i<dataArray.length;i++){  
				    			var titleArray = dataArray[i]['title'];
				    			var $allLangForm =$("input[name='form["+i+"].title']");
				    			//alert("lang fo"+$allLangForm.size() );
				    			$allLangForm.each(function(index,element){
				    				for(var j=0;j<titleArray.length;j++){  
				        				var lang = titleArray[j]['lang'];
				        				var value = titleArray[j]['value'];
				        				if($(this).attr("lang")== lang){
				        					if(value != "" && value != null){
				        						$(this).val(value+"  ("+lang+")");
				        					}else{
				        						$(this).remove();
				        					}
				        					   
				        				}
				        			}
				         		});
				    			
				    		}
			
				    		
				    		
				    		var _titleColor = "${titleColor}";
			            	var colorSpan = document.createElement("span");
			            	colorSpan.style.color = "${titleColor}";
			            	if(colorSpan.style.color == ""){
			            		colorSpan.style.color = "#"+"${titleColor}";
			            		if(colorSpan.style.color != ""){
			            			_titleColor = "#"+"${titleColor}";  //兼容钉钉配置页面的title颜色设置
			                	}
			            	}
			            	
				    		function preview(data){
				    			var type = "${thirdDingTodoTemplateForm.fdType}";
			            		if(type != ""){
			            			if(type == "1"){
			            				//待办
			            				$(".wayTodo").css("display","");
			            				$(".wayNotify").css("display","none");
			            			}else if(type == "2"){
			            				//工作通知
			            				$(".wayTodo").css("display","none");
			            				$(".wayNotify").css("display","");
			            			}else{
			            				$(".wayTodo").css("display","");
			            				$(".wayNotify").css("display","");
			            			}
			            			$(".ding-card-agent-title").css("color",_titleColor);
			            		}
				    			
				    			var fdIsdefault = "${thirdDingTodoTemplateForm.fdIsdefault}";
				    			var fdModelNameText = $("input[name='fdModelNameText']").val(); 
				    			if(fdIsdefault == "1"){
				    				fdModelNameText = "模块名称";
				    			}
				    			$(".ding-card-demo-title").html(fdModelNameText);
			            		 $(".ding-card-demo-content").empty();
				    			var _info = "";
				    			for(var i=0;i<dataArray.length;i++){ 
				    				var type = dataArray[i]['lastSelectType'];
				    				var name = dataArray[i]['title'][0]['value'];
				    				var info = "xxxxxxxxxx";
				    				 if("DateTime" == type){
			            				 info = "2020-05-13 13:05:55";
			            			 }else if("Date" == type){
			            				 info = "2020-05-13";
			            			 }else if("DateTime" == type){
			            				 info = "13:05:55";
			            			 }else if("Integer" == type){
			            				 info = "1";
			            			 }else if("Double" == type){
			            				 info = "1.0";
			            			 }
			            			 _info += "<p class='ding-card-demo-info'><em>"+name+"：</em><span>"+info+"</span></p>";    
				    			}
                                type = "${thirdDingTodoTemplateForm.fdType}";

				    			 $(".ding-card-demo-content").append(_info);
                                <c:if test="${'true' eq updateStatus}">
                                $(".wayNotify .ding-card-demo .ding-card-demo-content").append("<p class='ding-card-demo-info' style='color:#78C06E'>${lfn:message('third-ding-notify:thirdDingNotifyMessage.status.done')}</p>");
                                </c:if>
                            }
			            	//效果预览
				    		preview(dataArray);
			            </script>
			            

        </template:replace>
        
    </template:include>
    