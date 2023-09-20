<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<%@ include file="/fssc/mobile/common/organization/organization_include.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%
	Boolean useAuth = com.landray.kmss.util.UserUtil.checkRole("ROLE_FSSCBASE_ACCOUNT");
	request.setAttribute("useAuth", useAuth);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/editMobileLink.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/newApplicationForm.css?s_cache=${LUI_Cache }">
	
    <script >
	    var formInitData={
	   		 'LUI_ContextPath':'${LUI_ContextPath}',
	    }
    	Com_IncludeFile("rem.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);  	
    	Com_Parameter.event.submit.push(function(){
    		var fdName = $("[name=fdName]").val();
    		if(!fdName){
    			jqtoast('请填写账户名')
    			return false;
    		}
    		var fdBankName = $("[name=fdBankName]").val();
    		if(!fdBankName){
    			jqtoast('请填写开户行')
    		}
    		var fdBankAccount = $("[name=fdBankAccount]").val();
    		if(!fdBankAccount){
    			jqtoast('请填写开户行账号')
    			return false;
    		}
    		var fdPersonName = $("[name=fdPersonName]").val();
    		if(!fdPersonName){
    			jqtoast('请选择归属人')
    			return false;
    		}
    		var fdAccountArea = $("[name=fdAccountArea]");
    		if(fdAccountArea.length>0&&!fdAccountArea.val()){
    			jqtoast('请选择账户归属地')
    			return false;
    		}
    		return true;
    	})
    	/*************************************************************************
		 * 选择对象
		*************************************************************************/
		function selectObject(id,name,dataSource,baseOn){
			var docTemplateId=$("[name='fdTemplateId']").val();
			var fdFeildId=id.substring(id.indexOf("(")+1,id.indexOf(")"));
			var event = event ? event : window.event;
			var obj = event.srcElement ? event.srcElement : event.target;
			$.ajax({
				url: formInitData.LUI_ContextPath+'/fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getAccountArea',
				type: 'post',
				async:false,
			}).error(function(data){
					console.log("获取信息失败"+data);
			}).success(function(data){
		      	 var rtn = JSON.parse(data);  //json数组
		      	 var objData=rtn["data"];
		      	 var nameObj=$("[name='"+name+"']");
		      	 var idObj=$("[name='"+id+"']");
		      	 //由于picker.js每次只是隐藏，下次重新创建导致无法通过id绑定控件，所以每次新建前清除上一次的对象
		      	 $(".picker").remove();
		      	 var curValue = idObj.val(),selectedIndex=0;
		      	 if(curValue!=null){
		      		 for(var i=0;i<objData.length;i++){
		      			 if(objData[i].value==curValue){
		      				 selectedIndex = i;
		      				 break;
		      			 }
		      		 }
		      	 }
		         var picker = new Picker({
		             data: [objData],
		             selectedIndex:[selectedIndex]
		           });
		          picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
		        	  for(var i=0;i<objData.length;i++){
		      			 if(objData[i].value==selectedVal[0]){
		      				$("[name="+name+"]").val(objData[i].name);
		      				 break;
		      			 }
		      		  }
		          });
		          picker.show();
		          //回车搜索
		          $("#search_input").keypress(function (e) {
		              if (e.which == 13) {
		              	var keyword=$("[name='pick_keyword']").val();
		              	if(keyword){
		              		$.ajax({
		              	           type: 'post',
		              	           url:formInitData.LUI_ContextPath+'/fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getAccountArea',
		              	           data: {"keyword":keyword},
		              	       }).success(function (data) {
		              	    	   console.log('获取信息成功');
		              	    	   var rtn = JSON.parse(data);
		              	    	   picker.refillColumn(0, rtn.data);
		              	    	   objData=rtn.data;
		              	       }).error(function (data) {
		              	    	   console.log('获取信息失败');
		              	       })
		              	}
		              }
		      	});
		          //获取到焦点
		          $("#search_input").focus(function(){
		      		$(".weui-icon-clear").attr("style","display:block;");
		      	}) 
			      //取消
		          $(".weui-icon-clear").click(function (e) {
		          	$.ajax({
		   	           type: 'post',
		   	           url:formInitData.LUI_ContextPath+'/fssc/mobile/fs_mobile_data/fsscMobileRestful?method=getAccountArea',
		   	           data: {"keyword":''},
		   	       }).success(function (data) {
		   	    	   console.log('获取分类信息成功');
		   	    	   var rtn = JSON.parse(data);
		   	    	  picker.refillColumn(0, rtn.data);
		   	    	  objData=rtn.data;
		   	    	  $("[name='pick_keyword']").val('');
		   	       }).error(function (data) {
		   	    	   console.log('获取分类信息失败');
		   	       })
		      	});
			});
		}
    </script>
    <style>
		.ld-info-item  .content>input{
			width:70%;
			max-width:4rem;
			overflow:scroll;
			text-overflow:clip;
		}
    </style>
    <title>编辑账户</title>
</head>
<body>
<html:form action="/eop/basedata/eop_basedata_account/eopBasedataAccount.do">
<div class="fssc-mobile-link-add">
		<div class="ld-info-item">
            <div class="title">账户名</div>
            <div class="content">
                 <input type="text" value="${eopBasedataAccountForm.fdName }" name="fdName" placeholder="请输入账户名"/>
                 <span style="color:red;">*</span>
            </div>
        </div>
        <div class="ld-info-item">
            <div class="title">开户行</div>
            <div class="content" >
                <input type="text" name="fdBankName" value="${eopBasedataAccountForm.fdBankName }" placeholder="请输入开户行"/>
                <span style="color:red;">*</span>
            </div>
        </div>
        <div class="ld-info-item">
            <div class="title">开户账号</div>
            <div class="content" >
                <input type="text" name="fdBankAccount" value="${eopBasedataAccountForm.fdBankAccount }" placeholder="请输入开户行账号"/>
                <span style="color:red;">*</span>
            </div>
        </div>
        <div class="ld-info-item">
            <div class="title">联行号</div>
            <div class="content" >
                <input type="text" name="fdBankNo" value="${eopBasedataAccountForm.fdBankNo }" placeholder="请输入联行号"/>
                <span>&nbsp;</span>
            </div>
        </div>
        <c:if test="${useAuth=='true' }">
        <div class="ld-info-item">
            <div class="title">归属人</div>
            <div class="content" onclick="selectOrgElement('fdPersonId','fdPersonName','','false','person');">
                 <input type="text" name="fdPersonName" value="${eopBasedataAccountForm.fdPersonName }" readonly placeholder="请选择归属人"/>
                 <input name="fdPersonId" value="${eopBasedataAccountForm.fdPersonId}" type="hidden"/>
                 <span style="color:red;">*</span>
                 <i></i>
            </div>
        </div>
        </c:if>
        <c:if test="${useAuth!='true' }">
        <div class="ld-info-item">
            <div class="title">归属人</div>
            <div class="content">
                 <input type="text" name="fdPersonName" value="${eopBasedataAccountForm.fdPersonName }" readonly/>
                 <input name="fdPersonId" value="${eopBasedataAccountForm.fdPersonId}" type="hidden"/>
                 <span>&nbsp;</span>
            </div>
        </div>
        </c:if>
        <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
        <div class="ld-info-item">
            <div class="title">归属地</div>
            <div class="content" onclick="selectObject('fdAccountAreaId','fdAccountArea');">
                 <input type="text" name="fdAccountArea" value="${eopBasedataAccountForm.fdAccountArea }" readonly placeholder="请选择账户归属地"/>
                 <input name="fdAccountAreaId" value="${eopBasedataAccountForm.fdPersonId}" type="hidden"/>
                 <span style="color:red;">*</span>
                 <i></i>
            </div>
        </div>
        </fssc:checkUseBank>
        <div class="ld-info-item">
            <div class="title">默认账户</div>
            <div class="content" >
             		<div class="checkbox_item ${eopBasedataAccountForm.fdIsDefault==true?'checked':''}" style="width: 1rem;float: right;">
             			<div class="checkbox_item_out">
             				<div class="checkbox_item_in"></div>
             			</div>
             			<div class="checkbox_item_text">是</div>
             			<input type="radio" name="_fdIsDefault" value="true">
             		</div>
             		<div class="checkbox_item ${eopBasedataAccountForm.fdIsDefault==false?'checked':''}" style="width: 1rem;float: right;">
             			<div class="checkbox_item_out">
             				<div class="checkbox_item_in"></div>
             			</div>
             			<div class="checkbox_item_text">否</div>
             			<input type="radio" name="_fdIsDefault" value="false">
             		</div>
             		<input type="hidden" name="fdIsDefault" value="${eopBasedataAccountForm.fdIsDefault }">
             </div>
        </div>
        <div class="fssc-mobile-footer" style="position:relative;bottom:0;">
			<div class="fssc-mobile-footer-cancel" onclick="window.open('${LUI_ContextPath}/eop/basedata/eop_basedata_account/eopBasedataAccount.do?method=listMobile','_self');">返回</div>
			<div class="fssc-mobile-footer-add" onclick="Com_Submit(document.eopBasedataAccountForm,'updateByMobile')">保存</div>
		</div>
	</div>
<input name="submitType" value="${param.method}" type="hidden"/>
<input name="fdId" value="${eopBasedataAccountForm.fdId}" type="hidden"/>
</html:form>
</body>
</html>
