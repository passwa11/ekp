<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.third.ding.util.ThirdDingUtil" %>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@page import="com.landray.kmss.util.StringUtil" %>
<%@page import="com.landray.kmss.third.ding.model.DingConfig" %>
<%@page import="com.landray.kmss.third.ding.forms.ThirdDingTodoTemplateForm" %>


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
  
        <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.edit">
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
                Com_IncludeFile("validator.jsp|validation.jsp|validation.js|plugin.js", null, "js");
                var _html="";  //构建事项的下拉数据
               
                var formInitData = {

                };
                var messageInfo = {

                    'fdDetail2': '${lfn:message("third-ding:table.thirdDingTemplateDetail") }'
                };

                var initData = {
                    contextPath: '${LUI_ContextPath}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
                
                function chooseTemplate(modelName){
                	modelName = $("input[name='fdModelName']").val();
                	if("com.landray.kmss.km.review.model.KmReviewMain" == modelName){
                		$("#_xform_fdTemplateId_tile").css('display','block');
                		$("#_xform_fdTemplateId").css('display','block');
                	}else{
                		$("#_xform_fdTemplateId_tile").css('display','none');
                		$("#_xform_fdTemplateId").css('display','none');
                	}
                	
                }
                
                function selectForm(){
                	
                	var mainModelName = $("input[name='fdModelName']").val();
                	if(!mainModelName || mainModelName==null){
                		alert("请先选择表单模块");
                		return;
                	}
                	
                	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($,dialog,topic) {
                		               				
                		dialog.categoryForNewFile(
                				'com.landray.kmss.km.review.model.KmReviewTemplate',
                				null,false,null,function(rtn) {
                					// 门户无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
                					console.log(rtn);
                					if(!rtn){             						
                						return;
                					}
                					//校验表单是否存在了
                            		var modelExist = false;
                            		$.ajax({
                            			url:"${LUI_ContextPath}/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=checkModel&fdTemplateId="+rtn.id+"&fdModelName="+mainModelName,
    		            				type:"GET",
    		            				async:false,
    		            				success:function(result){
    		            					if("1" == result){
    		            						alert("表单"+rtn.name+" 模板已存在！");
    		            						modelExist = true; 	            						
    		            					}
    		            					      					
    		            				}
    		            			});
                            		if(modelExist){
                            			return;
                            		}
                					
                					$("#fdTemplateName").val(decodeURIComponent(rtn.name));
        							$("#fdTemplateId").val(rtn.id);
        							$("input[name='fdIscustom']").val("1");
        							$("input[name='fdTemplateClass']").val("com.landray.kmss.km.review.model.KmReviewTemplate");
        							
        							//填充表单信息到下拉框
        							$.ajax({
					        				url:"${KMSS_Parameter_ContextPath}third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=findFieldDictByModelName&fdTemplateId="+rtn.id+"&fdTemplateClass=com.landray.kmss.km.review.model.KmReviewTemplate",
					        				type:"GET",
					        				async:false,
					        				success:function(result){
					        					//alert("表单内容："+result);
					        					var rs = $.parseJSON(result);
					        					switchSelectFormOption(rs);		        					
					        				}
					        			});
        							
        							
                				},'',null,null,true);
                		
                	}
                	);
                }
                
                //把原有的表单内容清空，然后再填充  fromForm="true"
                function switchSelectFormOption(rs){
                	//alert("_html:"+_html);
                	var tempHtml = "";
                	var htmlArray=new Array();
                	htmlArray=_html.split('</option>');
                	//alert(htmlArray);
                	//alert("size:"+htmlArray.length);
                	for(var j=0;j<htmlArray.length;j++){
                	   if(htmlArray[j].indexOf("fromForm='true'") != -1){
                		  // alert("是自定义" +htmlArray[j]);
                		   continue;
                	   }
                	  // alert("添加内容"+htmlArray[j]);
                	   if(htmlArray[j] != ""){
                		   tempHtml += htmlArray[j]+"</option>";
                	   }
                	   
                	}
                 //   alert(tempHtml);
                    _html=tempHtml;
                    var form_html= "";
                	if(rs != null && rs != ''){
            			
                		for(var i=0,l=rs.length;i<l;i++){  
                			var field = rs[i]['key'];
                			var fieldText = rs[i]['name'];		
                			var type = rs[i]['type'];	
                			form_html = form_html+"<option value='"+field+"' fromForm='true' type='"+type+"'>"+fieldText+"</option>";
                		    _html=_html+"<option value='"+field+"' fromForm='true' type='"+type+"'>"+fieldText+"</option>";  
                		 }
            		}
                	
                	//切换表单后，要把已有的事项数据更新
            		var $allSelect = $('#TABLE_DocList_fdDetail_Form').find('.fdDetail_Form_select');
            		var switchModel=_html;
            		$allSelect.each(function(index,element){
            		   // $(this).empty();  不清所有，只是清表单字段
            		   var numbers = $(this).find("option"); //获取select下拉框的所有值
					   for (var j = 0; j < numbers.length; j++) {
							var fromform = $(numbers[j]).attr("fromform");
						    if(fromform == "true"){
						    	$(numbers[j]).remove();
						    }
						}
					   if($(this).attr("index") == 0){
						   $(this).append(form_html);
					   }
            		              		    
            		 });
                	
                }
                
              //弹出系统内数据对话框
            	function XForm_selectModelNameDialog(){
            		window.focus();
            		seajs.use(['lui/dialog'], function(dialog) {
            			var url = "/third/ding/third_ding_todo_template/thirdDingTodoTemplate_edit_dialog.jsp";
            			var height = document.documentElement.clientHeight * 0.78;
            			var width = document.documentElement.clientWidth * 0.6;
            			var dialog = dialog.iframe(url,"待办模版所属模块选择",xform_main_data_setAttr,{width:width,height : height,close:false});
            		});
            	}
              
            	//关闭、确定对话框的回调函数
            	function xform_main_data_setAttr(value){
            		if(value){
            			if(value.modelName){
            				//alert("value.modelName:"+value.modelName);
            				$("input[name='fdModelName']").val(value.modelName);	
            				//insystemContext.modelName = value.modelName;
            				if("com.landray.kmss.km.review.model.KmReviewMain" == value.modelName){
            					$("#_xform_fdTemplateId_tile").css('display','block');
                        		$("#_xform_fdTemplateId").css('display','block');
                        	}else{
                        		//alert("value.modelName:"+value.modelName);
                        		//后台校验模块是否存在了
                        		var modelExist = false;
                        		$.ajax({
		            				url:"${LUI_ContextPath}/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=checkModel&fdModelName="+value.modelName,
		            				type:"GET",
		            				async:false,
		            				success:function(result){
		            					if("1" == result){
		            						alert("在所属模块已存在一个待办模版，请选择其他模块！");
		            						modelExist = true; 	            						
		            					}
		            					      					
		            				}
		            			});
                        		if(modelExist){
                        			return;
                        		}
                        		$("#_xform_fdTemplateId_tile").css('display','none');
                        		$("#_xform_fdTemplateId").css('display','none');
                        		$("input[name='fdIscustom']").val("0");
                        		$("input[name='fdTemplateClass']").val("");
                        		$("input[name='fdTemplateId']").val("");
                        	}
            			}
            			if(value.modelNameText){
            				//alert("value.modelNameText:"+value.modelNameText);
            				$("input[name='fdModelNameText']").val(value.modelNameText);	
            			}
            			
            			if(value.modelName && value.modelNameText){
            				$KMSSValidation().validateElement($("input[name='fdModelNameText']")[0]);
            			}
            			
            			//xform_main_data_insystem_validation.validateElement($("input[name='fdModelNameText']")[0]);
            			if(value.data){
            				// 设置属性
            				var data;
            				try{
            					//alert("value.data:"+value.data);
            					data = $.parseJSON(value.data);
            					$("input[name='fdDetail']").val(value.data);
            					$("input[name='fdCode']").val(value.data);
            					
            				}catch(e){
            					alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.lookLog') }");
            				}
            				if(data){
            					//xform_main_data_setGlobal(data);
            					//处理权限行
            					//xform_main_data_detailAuthTr(insystemContext.auth);
            				}
            			}
            			createSelectOption(data);
            			//xform_main_data_initAfterSetModel();
            		}
            	}
            	
            	function fdDetail_Form_select_change(_index,$select){
            		//var value = $select.val();
            		_self=$($select);
            		var selectIndex =_self.attr("index");
            		var type = _self.find("option:selected").attr("type");
            	//	alert(type);
            		var $allSelect=_self.closest("div").find(".fdDetail_Form_select");
            		//清除 selectIndex后的select数据
            		$allSelect.each(function(index,element){
             		   var tempIndex = $(this).attr("index");  
             		   //alert("tempIndex:"+tempIndex);
             		   if(tempIndex > selectIndex){
             			  $(this).remove();
             		   }
             		 });
            		
            		//var index = $allSelect.size(); //记录一个div的fdDetail_Form_select 个数
            		var index = Number(selectIndex)+1; 
            		//alert("index:"+index);
            		if(type.indexOf("com.landray.kmss") == 0){
            			//_xform_fdDetail_Form[1].key
            			//debugger;
            			var formHtml="<select onchange='fdDetail_Form_select_change("+_index+",this)' index='"+index+"' class='fdDetail_Form_select' style='width: 35%'><option value=''>"+"${lfn:message('third-ding:thirdDingTodoTemplate.select')}"+"</option></select>"
            			
            			var $form = $(formHtml);
            			_self.closest("div").append($form);
            			//$xform_fdDetail_Form.append("<input type='text'/>");
            			//var _index = _self.attr("index");
            			
            			var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=findFieldDictByModelName&modelName="+type;
	        			$.ajax({
	        				url:url,
	        				type:"GET",
	        				async:false,
	        				success:function(result){
	        					//alert(result);
	        					var _formHtml=""
	        					
	        					var rs = $.parseJSON(result);
	        					var fieldArray = rs['fieldArray'];
	                    		//alert("fieldArray:"+fieldArray);
	                    		//var jsonFieldArray = $.parseJSON(fieldArray);
	                    		for(var i=0,l=fieldArray.length;i<l;i++){  
	                    			var field = fieldArray[i]['field'];
	                    			var type = fieldArray[i]['fieldType'];
	                    			var fieldText = fieldArray[i]['fieldText'];
	                    			_formHtml=_formHtml+"<option value='"+field+"' fromForm='false' type='"+type+"'>"+fieldText+"</option>";  
	                    		 }
	                    		$form.append(_formHtml)
	        				}
	        			});
            		}
            		
            	}
            	
            	function createSelectOption(data){
            		_html="";
            		if(data != null && data != ''){
            			
                		var fieldArray = data['fieldArray'];
                		//alert("fieldArray:"+fieldArray);
                		//var jsonFieldArray = $.parseJSON(fieldArray);
                		for(var i=0,l=fieldArray.length;i<l;i++){  
                			var field = fieldArray[i]['field'];
                			var type = fieldArray[i]['fieldType'];
                			var fieldText = fieldArray[i]['fieldText'];
                		        console.log("field:"+fieldArray[i]['field']);
                		        console.log("fieldText:"+fieldArray[i]['fieldText']);
                		        _html=_html+"<option value='"+field+"' fromForm='false' type='"+type+"'>"+fieldText+"</option>";  
                		 }
            		}
            		
            		//方案一：保留原配置
            		//切换模块后，要把已有的事项数据更新
            		/* var $allSelect = $('#TABLE_DocList_fdDetail_Form').find('.fdDetail_Form_select');
            		var switchModel=_html;
            		$allSelect.each(function(index,element){
            		    //alert("切换中"+index);
            		    $(this).empty();
            		    if(index == 0){
            		    	switchModel=" <option value=''>请选择</option>"+_html;
            		    }          		   
            		    $(this).append(switchModel);
            		 }); */
            		
            		//方案二：清理所有
            		//$('#TABLE_DocList_fdDetail_Form').find('.docListTr').remove();
            		 $('#TABLE_DocList_fdDetail_Form').find("a[onclick='deleteRow();']").click();
            		//DocList_DeleteRow();

            	}
            	
            	function createSelectOption2(data){
            		
            		_html="";
            		if(data != null && data != ''){
            			
                		var fieldArray = data['fieldArray'];
                		//alert("fieldArray:"+fieldArray);
                		//var jsonFieldArray = $.parseJSON(fieldArray);
                		for(var i=0,l=fieldArray.length;i<l;i++){  
                			var field = fieldArray[i]['field'];
                			var type = fieldArray[i]['fieldType'];
                			var fieldText = fieldArray[i]['fieldText'];
                		        console.log("field:"+fieldArray[i]['field']);
                		        console.log("fieldText:"+fieldArray[i]['fieldText']);
                		        _html=_html+"<option value='"+field+"' fromForm='false' type='"+type+"'>"+fieldText+"</option>";  
                		 }
            		}
            		

            	}
            	
            	function validateDetail(){
            		
            		var isFlag = true;
            		var fdDetail={};
            		var data = new Array();
            		
            		//判断所属模块的模版是否存在
            		var fdIsdefault = $("input[name='fdIsdefault']").val();
            		var method_GET = $("input[name='method_GET']").val();
            		
            		if(fdIsdefault == 0 && method_GET == "add"){
            			var fdTemplateId = $("input[name='fdTemplateId']").val();
                		var fdModelName = $("input[name='fdModelName']").val();
                		//alert("fdTemplateId:"+fdTemplateId+" fdModelName:"+fdModelName)
                		$.ajax({
            				url:"${LUI_ContextPath}/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=checkModel&fdTemplateId="+fdTemplateId+"&fdModelName="+fdModelName,
            				type:"GET",
            				async:false,
            				success:function(result){
            					if("1" == result){
            						alert("在所属模块已存在一个待办模版，请选择其他模块！");
            						isFlag = false;	  
            					}
            					      					
            				}
            			});
            		}
            		
            		var $allSelectTr= $('#TABLE_DocList_fdDetail_Form').find('.docListTr');
            		
            		if($allSelectTr.size()==0){
            			alert("待办事项列表不能为空");
            			return false;
            		}
            		$allSelectTr.each(function(index,element){
            		   
            			var $allSelectForm=$(this).find(".fdDetail_Form_select");
                		//清除 selectIndex后的select数据
                		var key="";
                		var name="";
                		var fromForm="false";
                		var lastSelectType ="";
                		$allSelectForm.each(function(index2,element){
                			var val = $(this).val(); 
                			if(val == null || val ==""){
                				alert("第"+(index+1)+"行第"+(index2+1)+"个下拉列表不能为空！")
                				isFlag = false;
                			}
                		   if(index2 == 0){
                			   fromForm = $(this).find("option:selected").attr("fromForm"); 
                			   key =$(this).val();   
                			   name =$(this).find("option:selected").text();  
                		   }else{
                			   key =key+"."+ $(this).val();  
                			   name = name+"."+ $(this).find("option:selected").text();  
                		   }
                		   if(index2 == ($allSelectForm.size()-1)){
                			   lastSelectType = $(this).find("option:selected").attr("type"); 
                		   }
                		   
                 		});
                		
                		//遍历标题
                		var title=new Array();
                		var $allLangForm=$(this).find("input[name='form["+index+"].title']");
                		$allLangForm.each(function(index3,element){
                			var val = $(this).val();
                			if((val == null || val =="")&&index3==0){
                				alert("第"+(index+1)+"行的显示标题内容（官方语言）不能为空！")
                				isFlag = false;
                			}
                			var lang = $(this).attr("lang");
                		    var langObj = {'lang':lang,'value':val};
                			title.push(langObj);
                		   
                 		});
                		
                	   var obj_1 = { 'key':key, 'name':name,'fromForm':fromForm,'title':title,'lastSelectType':lastSelectType};
             		   data.push(obj_1);
            		  
            		    
            		 });
            		fdDetail['data']=data;
            		$("input[name='fdDetail']").val(JSON.stringify(fdDetail));
            		//alert(JSON.stringify(fdDetail));
            		
            		if(isFlag)
            		  return true;
            		else
            		  return false;
            	}
            	function addRow(){
            		//检查是否超过3行，钉钉最多显示3行内容
            		var $allSelectTr= $('#TABLE_DocList_fdDetail_Form').find('div[_xform_type=select]');
            		if($allSelectTr.size() >= 3){
            			alert("待办事项列表不能超过3行内容！");
            			return false;
            		}
            		
            		DocList_AddRow();
            		
            		//alert("_html"+_html);
                    if("${thirdDingTodoTemplateForm.fdIsdefault != 1}" == "true"){
                    	//edit页面的基准行会变成初始模块的数据,所以要清除option
                		$('#TABLE_DocList_fdDetail_Form tr:last').find('.fdDetail_Form_select').find("option").remove();
                		//加上 请选择 的option
                		var tempHtml = "<option value='' att=''>"+"${lfn:message('third-ding:thirdDingTodoTemplate.select')}"+"</option>"+_html
                		
                		$('#TABLE_DocList_fdDetail_Form tr:last').find('.fdDetail_Form_select').append(tempHtml);
                		//alert("添加行");
                    }
            		
            	}
            	
            	function langShow(index,lang){
            		//alert(1);
            		var self = $(lang);
            		//var $allName = self.closest("div[_xform_type=text]").find(".mutilLang");
            		var $allName = self.find(".mutilLang");
            		//显示
            		$allName.each(function(index,element){
             		    $(this).css("display","block");
             		});
            	}
            	
            	function langHide(index,lang){
            	//	alert(2);
            		var self = $(lang);
            		//var $allName = self.closest("div[_xform_type=text]").find(".mutilLang");
            		var $allName = self.find(".mutilLang");
            		//隐藏
            		$allName.each(function(index,element){
             		    $(this).css("display","none");
             		});
            		
            	}
            	
            	
            	function deleteRow(){
            		
            		DocList_DeleteRow();
            		//alert("删除成功了");
            		//把行的所有下标改成   序号 -1   事项字段和显示标题
            		var $allListTr = $(".docListTr");
            		$allListTr.each(function(index,element){
            			//alert(index);
            		    var $title = $(this).find("div[_xform_type='text']");
            		    $title.find("input").attr("name","form["+index+"].title");
            		    //事项下拉
            		    var $select = $(this).find("select");
            		    $select.attr("onchange","fdDetail_Form_select_change("+index+",this)")
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
            	
            	function preview(){
            		var type = $("input[name='fdType']").val();
            		debugger;
            		
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
            		
            		var isFlag = true;
            		var fdDetail={};
            		var data = new Array();
            		
            		//判断所属模块的模版是否存在
            		var fdIsdefault = $("input[name='fdIsdefault']").val();
            		var method_GET = $("input[name='method_GET']").val();
            		var fdTemplateId = $("input[name='fdTemplateId']").val();
            		var fdModelName = $("input[name='fdModelName']").val();
            		//var fdModelNameText = $("input[name='fdModelNameText']").val();
            		var fdModelNameText="${lfn:message('third-ding:thirdDingTodoTemplate.view.content')}";
            		
            		//alert("fdModelNameText:"+fdModelNameText);
            		//alert("fdIsdefault："+fdIsdefault+" fdTemplateId："+fdTemplateId+" fdModelName："+fdModelName);
            		if(fdIsdefault == 0){
            			if(fdModelName == ""){
            				alert("所属模块不能为空！");
            				return;
            			}       			
            		}else{
            			fdModelNameText = "${lfn:message('third-ding:thirdDingTodoTemplate.view.content')}";
            		}
            		var $allSelectTr= $('#TABLE_DocList_fdDetail_Form').find('.docListTr');
            		if($allSelectTr.size()==0){
            			alert("待办事项列表不能为空");
            			return;
            		}
            		$allSelectTr.each(function(index,element){
            		   
            			var $allSelectForm=$(this).find(".fdDetail_Form_select");
                		//清除 selectIndex后的select数据
                		var key="";
                		var name="";  //标题
                		var fromForm="false";
                		var type = "" //最后一个下拉列表的类型
                		$allSelectForm.each(function(index2,element){
                			var val = $(this).val(); 
                			if(val == null || val ==""){
                				alert("第"+(index+1)+"行第"+(index2+1)+"个下拉列表不能为空！")
                				isFlag = false;
                				return;
                			}
                		   if(index2 == $allSelectForm.size()-1){
                			   type = $(this).find("option:selected").attr("type"); 
                			  // alert("type:"+type);
                		   }
                		   
                 		});
                		
                		//遍历标题
                		var title=new Array();
                		var $allLangForm=$(this).find("input[name='form["+index+"].title']");
                		$allLangForm.each(function(index3,element){
                			var val = $(this).val();
                			if((val == null || val =="")&&index3==0){
                				alert("第"+(index+1)+"行的显示标题内容（官方语言）不能为空！")
                				isFlag = false;
                				return;
                			}
                			if(index3==0){
                				name = val;
                				return;
                			}
                 		});
                		
                	  // alert("type:"+type+" name:"+name);
                	   var obj_1 = { 'type':type, 'name':name};
             		   data.push(obj_1);
            		  
            		    
            		 });
            		 if(!isFlag) return;
            		 
            		 //将data数据放到预览卡片中
            		 //ding-card-demo-title
            		 //alert("fdModelNameText:"+fdModelNameText);
            		 $(".ding-card-demo-title").html(fdModelNameText);
            		 $(".ding-card-demo-content").empty();
            		 var _info = "";
            		 for(var i=0;i<data.length;i++){
            			// var dataInfo = $.parseJSON(data[i]);
            			 var type = data[i].type;
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
            			 _info += "<p class='ding-card-demo-info'><em>"+data[i].name+"：</em><span>"+info+"</span></p>";          			 
            			 
            		 }
            		 $(".ding-card-demo-content").append(_info);
					<c:if test="${'true' eq updateStatus}">
					 $(".wayNotify .ding-card-demo .ding-card-demo-content").append("<p class='ding-card-demo-info' style='color:#78C06E'>${lfn:message('third-ding-notify:thirdDingNotifyMessage.status.done')}</p>");
					</c:if>
				}
            </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${thirdDingTodoTemplateForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('third-ding:table.thirdDingTodoTemplate') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${thirdDingTodoTemplateForm.fdName} - " />
                    <c:out value="${ lfn:message('third-ding:table.thirdDingTodoTemplate') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ thirdDingTodoTemplateForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingTodoTemplateForm, 'update');}" />
                    </c:when>
                    <c:when test="${ thirdDingTodoTemplateForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingTodoTemplateForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
 
        <template:replace name="content">
            <html:form action="/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do">

                <ui:tabpage expand="false" var-navwidth="90%">
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
                                <td  width="35%">
                                    <%-- 模版名称--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" required="true" subject="${lfn:message('third-ding:thirdDingTodoTemplate.fdName')}"  validators="required"/>
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
                                    <div id="_xform_fdModelName" _xform_type="select">
                                        <%-- <xform:select property="fdModelName" htmlElementProperties="id='fdModelName'" showStatus="edit" onValueChange="chooseTemplate(this.value)">
                                            <xform:enumsDataSource enumsType="third_ding_modelName" />
                                        </xform:select>--%>
                                        
                                        <html:hidden property="fdModelName" /> 
                                        
                                        <xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.fdModleName') }" propertyId="fdModelName" style="width:90%"
												propertyName="fdModelNameText" dialogJs="XForm_selectModelNameDialog();">
										</xform:dialog>
                                    </div>
                                </td>                                                         
	                             <td class="td_normal_title" width="15%">
	                                 <div  style="display: none" id="_xform_fdTemplateId_tile">
	                                                                                                          表单模版
	                                 </div>
	                             </td>
	                             <td width="35%">
	                                    <%-- 表单模版id--%>
	                                 <div id="_xform_fdTemplateId" _xform_type="text" style="display: none">
	                                       <%--  <xform:text property="fdTemplateId" showStatus="edit" style="width:95%;" /> --%>
	                                   
	                                  <input type="hidden" value="${thirdDingTodoTemplateForm.fdTemplateId }" id='fdTemplateId' name='fdTemplateId' />
	                                  <span onclick="selectForm();">
								        <xform:text property="fdTemplateName"  htmlElementProperties="id='fdTemplateName' readonly  autocomplete='off'"  ></xform:text>
			        					<a href="javascript:void(0);">选择</a>
			        				  </span>
			        				</div>
	                            </td>
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
                                           <td class="td_normal_title" style="width:40%;">${lfn:message('third-ding:thirdDingTodoTemplate.choose')}</td>
                                           <td class="td_normal_title" style="width:40%;">${lfn:message('third-ding:thirdDingTodoTemplate.title')}</td>
                                           <td class="td_normal_title" style="width:10%;"> 
                                                   <a href="javascript:void(0);" onclick="addRow();">
                                                        ${lfn:message('third-ding:thirdDingTodoTemplate.add')} 
                                                    </a>
                                            </td>
                                        <tr>
	                                        <tr KMSS_IsReferRow="1" class="docListTr">                                           
	                                                <td class="docList" align="center" KMSS_IsRowIndex="1">
	                                                    !{index}
	                                                </td>
	                                                <td class="docList" align="center">
	                                                    <%-- 描述--%>
	                                                    <input type="hidden" name="fdDetail_Form[!{index}].key" value="" disabled="true" />
	                                                    <div id="_xform_fdDetail_Form[!{index}].key" _xform_type="select">
	                                                        <select class="fdDetail_Form_select" index="0" style="width: 35%" att="" onchange="fdDetail_Form_select_change(!{index},this)">
	                                                          <option value=''>${lfn:message('third-ding:thirdDingTodoTemplate.select')}</option>
	                                                        </select>
	                                                    </div>
	                                                </td>
	                                                  <!--显示标题  -->  
	                                                <td class="docList" align="center">
	                                                     
	                                                    <div id="_form!{index}.title" _xform_type="text" tabindex="0"  hidefocus="true" ">
	                                                      <c:forEach items="${supportLang}" var="lang" varStatus="vstatus">
	                                                          <input type="text" class="inputsgl" name="form[!{index}].title" lang="${lang}" style="width:90%;" autocomplete="off" placeholder="${lang}"/>
	                                                         <c:if test="${vstatus.index == 0}">
	                                                            <span class="txtstrong">*</span>
	                                                         </c:if>
	                                                       <br/><br/>
	                                                        
	                                                      </c:forEach>
	                                                          
	                                                           <%--  <xform:text property="form!{index}.title" subject="显示标题" place style="width:35%;"/> --%>
	                                                    </div>
	                                                </td> 
	                                              
	                                                <td class="docList" align="center">						
	                                                    <a href="javascript:void(0);" onclick="deleteRow();" title="${lfn:message('doclist.delete')}">
	                                                        <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
	                                                    </a>
	                                                </td>
	                                        </tr>
	                                        <c:forEach items="${fdDetail_Form_keyList}" var="fdDetail_FormItem" varStatus="vstatus">
													<tr KMSS_IsContentRow="1" class="docListTr">
														
														<td class="docList" align="center">
															${vstatus.index+1}
														</td>
														<td class="docList" align="center">
		                                                    <input type="hidden" name="fdDetail_Form[${vstatus.index}].key" _index="fdDetail_Form[${vstatus.index}].key" value="${fdDetail_FormItem}" disabled="true" />
		                                                    <div id="_xform_fdDetail_Form[${vstatus.index}].key" _xform_type="select">
		                                                        <select class="fdDetail_Form_select" index="0" style="width: 35%" att="${fdDetail_FormItem.key}" onchange="fdDetail_Form_select_change([${vstatus.index}],this)">
		                                                          <option value="">${lfn:message('third-ding:thirdDingTodoTemplate.select')}</option>
		                                                        </select>
		                                                    </div>
		                                                </td>
		                                                  <!--显示标题  -->  
		                                                <td class="docList" align="center">
		                                                     
		                                                    <div id="_form!{index}.title" _xform_type="text" tabindex="0"  hidefocus="true" ">
		                                                      <c:forEach items="${supportLang}" var="lang" varStatus="vstatus2">
		                                                          <input type="text" class="inputsgl" name="form[${vstatus.index}].title" lang="${lang}" style="width:90%;" autocomplete="off" placeholder="${lang}"/>
		                                                         <c:if test="${vstatus2.index == 0}">
		                                                            <span class="txtstrong">*</span>
		                                                         </c:if>
		                                                       <br/><br/>
		                                                        
		                                                      </c:forEach>
		                                                          
		                                                           <%--  <xform:text property="form!{index}.title" subject="显示标题" place style="width:35%;"/> --%>
		                                                    </div>
		                                                </td> 
			                                                
														 <td class="docList" align="center">						
		                                                    <a href="javascript:void(0);" onclick="deleteRow();" title="${lfn:message('doclist.delete')}">
		                                                        <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
		                                                    </a>
		                                                </td>
													</tr>
											</c:forEach>

                                                                                                                         
                                   </table>
                                   <input type="hidden" name="fdDetail_Flag" value="1">
                                        <script>
                                            Com_IncludeFile("doclist.js");
                                        </script>
                                        <script>
                                            DocList_Info.push('TABLE_DocList_fdDetail_Form');
                                        </script>
                                </td>
                               
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:module.third.ding.notify.template.way')}
                                </td>
                                <td width="85%" colspan="3">
                                  <c:if test="${thirdDingTodoTemplateForm.fdIsdefault == 0}">
                                     <xform:checkbox property="fdType" className="selectsgl" subject="" style="width:45%"  showStatus="edit" required="true">
			                             <xform:simpleDataSource value="1" ><bean:message key="module.third.ding.notify.template.way.todo" bundle="third-ding"/></xform:simpleDataSource>
										 <xform:simpleDataSource value="2"><bean:message key="module.third.ding.notify.template.way.notify" bundle="third-ding"/></xform:simpleDataSource>
			                         </xform:checkbox>
                                  </c:if>
                                  <c:if test="${thirdDingTodoTemplateForm.fdIsdefault == 1}">
                                     <xform:checkbox property="fdType" className="selectsgl" subject="" style="width:45%"  showStatus="edit" validators="todoWayIsRequire">
			                             <xform:simpleDataSource value="1" ><bean:message key="module.third.ding.notify.template.way.todo" bundle="third-ding"/></xform:simpleDataSource>
										 <xform:simpleDataSource value="2"><bean:message key="module.third.ding.notify.template.way.notify" bundle="third-ding"/></xform:simpleDataSource>
			                         </xform:checkbox>
                                  </c:if>
                                 </td>
                            </tr>
                            
                             <tr>
                                <td class="td_normal_title" width="15%">
                                      ${lfn:message('third-ding:thirdDingTodoTemplate.view.show')}
                                </td>
                                <td width="85%" colspan="3">
                                   <div>
                                         <ui:button
											style="margin:6px 0px 6px 0px;"
											text="${lfn:message('third-ding:thirdDingTodoTemplate.view.show')}"
											height="35" width="120"
											onclick="preview();"></ui:button>
											
								   </div>
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
											            <span class="ding-card-demo-title">${lfn:message('third-ding:thirdDingTodoTemplate.view.notify.content')}</span>
											        </div>
											        <div class="ding-card-demo-content">
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.view.title')}：</em><span>${lfn:message('third-ding:thirdDingTodoTemplate.view.content')}</span></p>
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.docCreator')}：</em><span>${lfn:message('third-ding:thirdDingTodoTemplate.view.name')}</span></p>
											            <p class="ding-card-demo-info"><em>${lfn:message('third-ding:thirdDingTodoTemplate.docCreateTime')}：</em><span>2020-04-29 13:54</span></p>
														<c:if test="${'true' eq updateStatus}">
															<p class="ding-card-demo-info" style='color:#78C06E'>${lfn:message('third-ding-notify:thirdDingNotifyMessage.status.done')}</p>
														</c:if>
											        </div>
											        
											    </div>
								           </td>
								         </tr>
								      </table>
								    
								    
								   
								   </div>
								    
                                </td>                                                         
	                             
                            </tr>
                            
                            
                             <%-- <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingTodoTemplate.docCreateTime')}
                                </td>
                                <td width="35%">
                                    创建时间
                                    <div id="_xform_docCreateTime" _xform_type="datetime">
                                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding:thirdDingTodoTemplate.docAlterTime')}
                                </td>
                                <td width="35%">
                                    更新时间
                                    <div id="_xform_docAlterTime" _xform_type="datetime">
                                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                    </div>
                                </td>
                            </tr> --%>
                            <script>
                            var validation = $KMSSValidation();
                			validation.addValidator('todoWayIsRequire','{name}'+" '钉钉待办'为必选项",function(v, e, o){
                				var val = $("input[name='fdType']").val();
                				if(val==""||val.indexOf("1") == -1){
                					return false;
                				}
                				return true;
                			});
							</script>
                            
                             <script>
						       chooseTemplate();					      
						     </script>
						     <script>
						    
						     var _fdmodelName = "${thirdDingTodoTemplateForm.fdModelName}";
						     var _fdIscustom = "${thirdDingTodoTemplateForm.fdIscustom}";
						     var _fdTemplateId = "${thirdDingTodoTemplateForm.fdTemplateId}";
						     var _fdTemplateClass = "${thirdDingTodoTemplateForm.fdTemplateClass}";
						    // alert(_fdIscustom+" _fdTemplateId:" +_fdTemplateId+" _fdTemplateClass: "+_fdTemplateClass)
						 
				                if(_fdmodelName != null && _fdmodelName != ""){
				                	
				                	var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=findFieldDictByModelName&modelName="+_fdmodelName;
				        			if(_fdmodelName.indexOf("com.landray.kmss")==-1){
				        				url+="&isxform=true";
				        			}
				        			$.ajax({
				        				url:url,
				        				type:"GET",
				        				async:false,
				        				success:function(result){
				        					//alert(result);
				        					data = $.parseJSON(result);
				        					createSelectOption2(data);
				        					//表单
				        					 if("1" == _fdIscustom && _fdTemplateId != "" &&_fdmodelName !=null&& _fdTemplateClass != ""&&_fdTemplateClass !=null ){
										    	 //alert("表单");
										    	 ///third/ding/third_ding_todo_template/thirdDingTodoTemplate.do
										    	 $.ajax({
								        				url:"${LUI_ContextPath}/third/ding/third_ding_todo_template/thirdDingTodoTemplate.do?method=findFieldDictByModelName&fdTemplateId="+_fdTemplateId+"&fdTemplateClass="+_fdTemplateClass,
								        				type:"GET",
								        				async:false,
								        				success:function(result){
								        					var rs = $.parseJSON(result);
								        					createSelectFormOption(rs);
								        					//alert(result);
								        				}
								        			});
										     }
				        					
				        				}
				        			});




				                }
						    
				              //在select填充数据
			            		var $allSelect = $('#TABLE_DocList_fdDetail_Form').find('.fdDetail_Form_select');
			            		var switchModel=_html;
			            		$allSelect.each(function(index,element){
			            			
			            			//非默认
			            		   // if("${thirdDingTodoTemplateForm.fdIsdefault != 1}" == "true"){
			            		    	$(this).empty();
				            		    if(index == 0){
				            		    	switchModel=" <option value=''>"+"${lfn:message('third-ding:thirdDingTodoTemplate.select')}"+"</option>"+_html;
				            		    }          		   
				            		    $(this).append(switchModel); 
			            		   // }
			            		    //默认选中的问题，获取通过att属性的值去判断
			            		    var v = $(this).attr("att");
			            		    if(v.indexOf(".") > -1){
			            		    	//alert("复合"+v);
			            		    	var va = v.split(".");
			            		    	for(var k = 0;k<va.length;k++){
			            		    		var numbers = $(this).closest("div").find("select:last").find("option"); //获取select下拉框的所有值
											for (var j = 0; j < numbers.length; j++) {
											    if ($(numbers[j]).val() == va[k]) {
											         $(numbers[j]).attr("selected", "selected");
											    };
											}
			            		    		//$(this).val();
			            		    		//var type = $(this).find("option:selected").attr("type");
			            		    		var type = $(this).closest("div").find("select:last").find("option:selected").attr("type");
			            		    		if(type.indexOf("com.landray.kmss") == 0){
			            		    			fdDetail_Form_select_change(index,$(this));
			            		    		}
			            		    		
			            		    		//alert(type);
				            		    	//alert(va[k]);
			            		    	}
			            		    	
			            		    }else{
			            		    	
			            		    	var isFlag = false;
			            		    	  var numbers = $(this).find("option"); //获取select下拉框的所有值
											for (var j = 0; j < numbers.length; j++) {
											    if ($(numbers[j]).val() == v) {
											         $(numbers[j]).attr("selected", "selected");
											         isFlag = true;
											    };
											}
			            		    	  if(!isFlag){
			            		    		  console.log(v+" 没有匹配上");
			            		    		//兼容 docCreateTime  -> fdCreateTime
			            		    		if(v == 'docCreateTime'){
			            		    			selectOptionByName(this,"fdCreateTime");
			            		    		}
			            		    	  }
			            		    }
			            		 });
			            		
			            		function selectOptionByName(target,name){            			
			            			var numbers = $(target).find("option"); //获取select下拉框的所有值
			            			for (var j = 0; j < numbers.length; j++) {
									    if ($(numbers[j]).val() == name) {
									         $(numbers[j]).attr("selected", "selected");
									         console.log("将docCreateTime  -> fdCreateTime 兼容ok");
									    };
									}
			            		}
			            		
				               
				                function createSelectFormOption(rs){
				                	if(rs != null && rs != ''){
				            			
				                		for(var i=0,l=rs.length;i<l;i++){  
				                			var field = rs[i]['key'];
				                			var fieldText = rs[i]['name'];	
				                			var type = rs[i]['type'];	
				                		    _html=_html+"<option value='"+field+"' fromForm='true' type='"+type+"'>"+fieldText+"</option>";  
				                		 }
				            		}
				                	//alert(_html);
				                }
				                
						     </script>
                        </table>
                </ui:tabpage>
                <html:hidden property="docCreatorId" />
                <html:hidden property="docCreatorName" />
                <html:hidden property="fdId" />
                <html:hidden property="fdIscustom" />
                <html:hidden property="fdIsdefault" />
                <html:hidden property="fdDetail" />
                <html:hidden property="fdTemplateClass" />
                <html:hidden property="docCreateTime" />
                <html:hidden property="docAlterTime" />
                <html:hidden property="method_GET" />
            </html:form>
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
	        					$(this).val(value);
	        				}
	        			}
	         		});
	    			
	    		}
            </script>
            <script>
               if($("input[name='method_GET']").val() == "edit"){
            	   setTimeout("preview()",1000);
               }
            </script>
        </template:replace>

     
    </template:include>