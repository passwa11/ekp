<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<html>
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  	<meta name="description" content="">
  	<meta name="author" content="Z.M. Mark">
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/profile/listShow/css/common.css" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/profile/listShow/css/maincss.css" rel="stylesheet">
	<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
	<script type="text/javascript" src='${LUI_ContextPath}/resource/js/domain.js?s_cache=${ LUI_Cache }'></script>
	<script type="text/javascript" src='${LUI_ContextPath}/sys/ui/js/LUI.js?s_cache=${ LUI_Cache }'></script>
	<script type="text/javascript" src="${LUI_ContextPath}/resource/js/common.js?s_cache=${ LUI_Cache }"></script>
	<script type="text/javascript" src="${LUI_ContextPath}/resource/js/sea.js?s_cache=${ LUI_Cache }"></script>	
</head>	
<body>
	<form action="${LUI_ContextPath}/sys/profile/listShow/sys_listShow/sysListShow.do?method=selectFields" method="post" id="selectForm">
  <div class="lui_custom_list_container">
    <!-- 主要内容 Starts -->
    <div class="lui_custom_list_box">
      <div class="lui_custom_list_box_content">
        <div class="lui_custom_list_box_content_container">
          <div class="lui_custom_list_box_content_row">
            <div class="lui_custom_list_box_content_col">
              <div class="item">
                <div class="lui_custom_list_box_content_col_header">
                  <span class="lui_custom_list_box_content_header_word"><bean:message bundle='sys-profile' key='sys.profile.select'/></span>
                  <label class="lui_custom_list_checkbox right">
                      <input type="checkbox" value="<bean:message bundle='sys-profile' key='sys.profile.select.all'/>" id="unselectedCheck" onclick="checkAllUnselected(this)"/>
                    <bean:message bundle='sys-profile' key='sys.profile.select.all'/>
                  </label>
                </div>
                <div class="lui_custom_list_box_content_col_content" id="unselectedlist">
                <c:forEach var="unselected" items="${unselectedlist}">
                  <div class="lui_custom_list_checkbox">
                      <label>
                          <input type="checkbox" name="unselected" id="${unselected.fdId}" value="${ lfn:message(unselected.fdMessagekey) }" onclick="toRight(this)"/>
                        ${ lfn:message(unselected.fdMessagekey) }
                      </label>
                  </div>
                  </c:forEach>
                </div>
              </div>
            </div>
            <div class="lui_custom_list_box_content_col">
              <div class="item">
                <div class="lui_custom_list_box_content_col_header">
                  <span class="lui_custom_list_box_content_header_word"><bean:message bundle='sys-profile' key='sys.profile.selected'/></span>
                  <span class="lui_custom_list_box_content_link_right">
                    <a href="javascript:void(0)" onclick="allToLeft()">
                       <bean:message bundle='sys-profile' key='sys.profile.cancel.all'/>
                    </a>
                  </span>
                </div>
                <div class="lui_custom_list_box_content_col_content" id="selectedlist">
                <c:forEach var="selectedlist" items="${selectedlist}">
                  <div id="${selectedlist.fdId}" class="lui_custom_list_box_content_col_content_line">
                    <span>${ lfn:message(selectedlist.fdMessagekey) }</span>
                    <div class="lui_custom_list_box_content_col_content_left">
                      <span class="lui_custom_list_box_content_col_content_widthchange">
                        <input type="text" placeholder="  <bean:message bundle='sys-profile' key='sys.profile.width'/>" class="selectedWidth" value="${selectedlist.fdWidth}"/>
                      </span>
                      <span class="lui_custom_list_box_btn_up" onclick="toTop(this)"></span>
                      <span class="lui_custom_list_box_btn_down" onclick="toDown(this)"></span>
                      <span class="lui_custom_list_box_btn_quit" onclick="toLeft(this)"></span>
                    </div>
                  </div>
                  </c:forEach>
                </div>
              </div>
            </div>
          </div>
          <div class="lui_custom_list_box_content_col_btn">
          	<input type="hidden" name="selectIdsLast" id="selectIdsLast" value="${selectIdsLast}"/>
			<input type="hidden" name="selectIds" id="selectIds" />
			<input type="hidden" name="selectWidth" id="selectWidth" />
			<input type="hidden" name="fdId" id="fdId" value="${fdId}"/>
			<input type="hidden" name="modelName" id="modelName" value="${modelName}"/>
            <a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="defaultShow()"><bean:message bundle='sys-profile' key='sys.profile.button.reset'/></a>
            <a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="submitForm()"><bean:message bundle='sys-profile' key='sys.profile.button.save'/></a>
          </div>
        </div>
      </div>
    </div>
    <!-- 主要内容 Ends -->


  </div>
</form>
<script type="text/javascript">
	function saveForm(){
		document.getElementById('selectForm').submit();
	}
	
	function toRight(elem){
		    	var id=$(elem).attr("id");
		    	var message=$(elem).val();
		    	 var html='<div id="'+id+'" class="lui_custom_list_box_content_col_content_line">'+
	                    '<span>'+message+'</span>'+
	                    '<div class="lui_custom_list_box_content_col_content_left">'+
	                      '<span class="lui_custom_list_box_content_col_content_widthchange">'+
	                        '<input type="text" placeholder="<bean:message bundle='sys-profile' key='sys.profile.width'/>" class="selectedWidth" value="">'+
	                      '</span>'+
	                      '<span class="lui_custom_list_box_btn_up" onclick="toTop(this)"></span>'+
	                      '<span class="lui_custom_list_box_btn_down" onclick="toDown(this)"></span>'+
	                      '<span class="lui_custom_list_box_btn_quit" onclick="toLeft(this)"></span>'+
	                    '</div>'+
	                  '</div>';
				$("#selectedlist").append($(html));
		    	$(elem).parent().parent().remove();
	}
	
	function toLeft(elem){
		    	var id=$(elem).parent().parent().attr("id");
		    	var message=$(elem).parent().parent().find("span").text();
		    	 var html='<div class="lui_custom_list_checkbox"><label>'+
                 '<input type="checkbox" name="unselected" id="'+id+'" value="'+message+'" onclick="toRight(this)"/>'+
                 message+
             		'</label></div>';
				$("#unselectedlist").append($(html));
		    	$(elem).parent().parent().remove();
		    	$("#unselectedCheck").prop("checked",false);
	}
	
	function allToLeft(){
		$(".lui_custom_list_box_btn_quit").each(function(){
			toLeft(this);
		  });
	}
	
	
	function toTop(elem){
				 if($(elem).parent().parent().index() != 0){
					 $(elem).parent().parent().prev().before($(elem).parent().parent());
				    }
	}
	function toDown(elem){
					  $(elem).parent().parent().next().after($(elem).parent().parent());
	}
	
	function checkAllUnselected(elem){
		var allChecked=$(elem).is(":checked");
		if(allChecked){
			$("input[name='unselected']").each(
					 function() {
							$(this).prop("checked",true);
							toRight(this);
					 }
					);
		}
	
	}
	
	function defaultShow(){
		var url = "${LUI_ContextPath}/sys/profile/listShow/sys_listShow/sysListShow.do?method=getDefaultFields&modelName="+$('#modelName').val()+"&fdId="+$('#fdId').val();
	   	 $.ajax({
			 url:url,
			 type:"post",
			 async:false,
			 datatype:"text",
			 success:function(data){
				 var a = LUI.toJSON(data);
				 var defaultFields=a["value"].split(";");
				 allToLeft();
				     for (var i = 0; i < defaultFields.length; i++) {
				    	 toRight("#"+defaultFields[i]);
					}
				   /*   var sortfields = document.getElementById("sortfields");//mySelect是select 的Id
				     sortfields.options[0].selected = true;
				     var sort = document.getElementById("sort");//mySelect是select 的Id
				     sort.options[0].selected = true; */
				 
				   }
		 } );
		}
	
	function submitForm(){
		$(".selectId").val("");
		var ids = "";
		var num=0;
		  $('.lui_custom_list_box_content_col_content_line').each(
	                function(i) {
	  					   if(ids==""){
	  						   ids = $(this).attr("id");
	  					   }else{
	  						   ids += ","+$(this).attr("id");
	  				   	}
	  					   num=i;
	                }
	            );

			$('#selectIds').val(ids);
			$(".selectWidth").val("");
			var Width = "";
			var flag=true;
			var flag2=true;
			  $('.selectedWidth').each(
		                function(){
		                	var value=$(this).val();
 		  					   if(Width==""){
 		  						   var text="";
		  						   if(value==""||value==null||typeof(value)=="undefined"){
		  							 Width = "null";  
		  						   }else if(!endWith(value,"%")&&!endWith(value,"px")){
		  							   flag=false;
		  						   }else{
		  							   if(endWith(value,"%")){
		  								   text=value.replace("%","");
		  							   }else if(endWith(value,"px")){
		  								 	text=value.replace("px","");
		  							   }
		  							   if(checkRate(text)){
		  								 Width = value;
		  							   }else{
		  								 flag2=false; 
		  							   }
		  						   }
		  					   }else{
		  						 var text="";
		  						  if(value==""||value==null||typeof(value)=="undefined"){
			  						 Width += ","+"null"; 
			  					   }else if(!endWith(value,"%")&&!endWith(value,"px")){
		  							   flag=false;
		  						   }else{
		  							 if(endWith(value,"%")){
		  								   text=value.replace("%","");
		  							   }else if(endWith(value,"px")){
		  								 	text=value.replace("px","");
		  							   }
		  							   if(checkRate(text)){
		  								 Width += ","+$(this).val();
		  							   }else{
		  								 flag2=false; 
		  							   }
		  						    
			  					   }
		  				   	}
		                }
		            );
			  $('#selectWidth').val(Width);
			  if(!flag){
				  alert("<bean:message bundle='sys-profile' key='sys.profile.width.alert1'/>");
				  return flag;
			  }
			  if(!flag2){
				  alert("<bean:message bundle='sys-profile' key='sys.profile.width.alert2'/>");
				  return flag2;
			  }
			  if(num>7){
				  	var DEFAULT_VERSION = 8.0;  
		            var ua = navigator.userAgent.toLowerCase();  
		            var isIE = ua.indexOf("msie")>-1;  
		            var safariVersion;  
		            if(isIE){  
		            	safariVersion =  ua.match(/msie ([\d.]+)/)[1];  
		            }
		            if(safariVersion <= DEFAULT_VERSION ){  
		            	var value=confirm("<bean:message bundle='sys-profile' key='sys.listShow.confirm'/>")
		            	if(value==true){
							document.getElementById('selectForm').submit();
						}
		            }else{
		            	seajs.use("lui/dialog" , function(dialog) {
					  		var dia=dialog.confirm("<bean:message bundle='sys-profile' key='sys.listShow.confirm'/>",function(value){
								if(value==true){
									document.getElementById('selectForm').submit();
								}
					  		});
					  		//console.log(dia);
					  	});
		            }
			  }else{
				  document.getElementById('selectForm').submit();
			  }
			  
		}
	function checkRate(nubmer) {
		　　var re = /^[0-9]+.?[0-9]*$/;
		　　return re.test(nubmer);
		}
	
	function endWith(str,endStr){
		 var d=str.length-endStr.length;
		 return (d>=0&&str.lastIndexOf(endStr)==d);
	}
	
</script>
</body>
</html>
