<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${lfn:message('fssc-loan:module.fssc.loan')}</title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/applicationFormList.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/dropload.css">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/search.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/jquery.min.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/dyselect.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/dropload.min.js"></script>
    <script>
    function backToHome(){
    		window.location.href='${LUI_ContextPath}/fssc/mobile/index.jsp'
    }
    $(document).ready(function(){
	       var template='${loanTemplateList}'; //分类模板
	        if(template){
	        	template=JSON.parse(template);
	        }
	        var loanTemplatePicker = new Picker({
	            data: [template]
	         });
	        loanTemplatePicker.on('picker.select', function (selectedVal, selectedIndex) {
                if(selectedVal==""){
                    jqtoast("没有借款分类!")
                    return
                }
	        	window.open("${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=add&i.docTemplate="+selectedVal,'_self');
	          });
	
	        loanTemplatePicker.on('picker.change', function (index, selectedIndex) {
	            console.log(index);
	          });
	
	        loanTemplatePicker.on('picker.valuechange', function (selectedVal, selectedIndex) {
	            console.log(selectedVal);
	          });
	        var createExpenseElement = document.getElementById('createExpense');
	        createExpenseElement.addEventListener('click', function () {
	        	loanTemplatePicker.show();
	          });
	        //点击加号
	        $("#createExpense").click(function (e) {
	        	$("[name='pick_keyword']").val('');
	        	$.ajax({
     	           type: 'post',
     	           url:'${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=findLoanTemplate',
     	           data: {"keyword":''},
     	       }).success(function (data) {
     	    	   console.log('获取信息成功');
     	    	   var rtn = JSON.parse(data);
     	    	   loanTemplatePicker.refillColumn(0, rtn);
     	       }).error(function (data) {
     	    	   console.log('获取信息失败');
     	       })
	    	});
	        //回车搜索
	        $("#search_input").keypress(function (e) {
	            if (e.which == 13) {
	            	var keyword=$("[name='pick_keyword']").val();
	            	if(keyword){
	            		$.ajax({
	            	           type: 'post',
	            	           url:'${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=findLoanTemplate',
	            	           data: {"keyword":keyword},
	            	       }).success(function (data) {
	            	    	   console.log('获取信息成功');
	            	    	   var rtn = JSON.parse(data);
	            	    	   loanTemplatePicker.refillColumn(0, rtn);
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
	        $(".weui-icon-clear").click(function (e) {
	        	$.ajax({
     	           type: 'post',
     	           url:'${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=findLoanTemplate',
     	           data: {"keyword":''},
     	       }).success(function (data) {
     	    	   console.log('获取分类信息成功');
     	    	   var rtn = JSON.parse(data);
     	    	   loanTemplatePicker.refillColumn(0, rtn);
     	    	  $("[name='pick_keyword']").val('');
     	       }).error(function (data) {
     	    	   console.log('获取分类信息失败');
     	       })
	    	});
	    });
    </script>
</head>
<body>
    <div class="ld-application-form-list">
    	<c:if test="${queryPage.list!= null and fn:length(queryPage.list) > 0}">
        <ul id="list_data">
        	<c:forEach var="fsscLoanMain" items="${queryPage.list}" varStatus="status">
        		<c:set var="loanId" value="${fsscLoanMain.fdId}"></c:set>
	        	<li onclick="viewLoan('${fsscLoanMain.fdId}');">
                <div class="ld-application-form-list-item-top">
                	<div style="width:60%;word-break: break-all;">
	                    <c:if test="${fn:length(fsscLoanMain.docSubject)>40 }">${fn:substring(fsscLoanMain.docSubject,0,37)}...</c:if>
	                    <c:if test="${fn:length(fsscLoanMain.docSubject)<=40 }">${fsscLoanMain.docSubject}</c:if>
                    </div>
                     <div style="width:40%;text-align:right;">
                     	<c:if test="${not empty fsscLoanMain.fdLoanMoney}">
                     		<kmss:showNumber value="${fsscLoanMain.fdLoanMoney}" pattern="##0.00"/>
                     	</c:if>	
                        <c:if test="${ empty fsscLoanMain.fdLoanMoney}">
                        		0.00
                        </c:if>
                    </div>
                </div>
                <div class="ld-application-form-list-item-time">
                		<div style="width:40%;float:left;">
                			<kmss:showDate value="${fsscLoanMain.docCreateTime}" type="date"></kmss:showDate>
                		</div>
                    <div style="width:40%;text-align:right;float:right;">
                      	<span class="ld-list-status ld-list-status-status${fsscLoanMain.docStatus}"><sunbor:enumsShow value="${fsscLoanMain.docStatus}" enumsType="common_status" /></span>
                    </div>
                </div>
            </li>
        	</c:forEach>
        </ul>
        </c:if>
        <c:if test="${queryPage.list== null or fn:length(queryPage.list) == 0}">
				<div style="text-align:center; line-height:50;">
					暂无内容
				</div>
        </c:if>
    </div>
    <!-- 加载中 -->   
     <div class="ld-main" id="ld-main-loading" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.loading')}</span>
            </div>
        </div>
    </div>
    <input name="pageno" value="${queryPage.pageno}" type="hidden"/>
	<input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
	<input name="totalrows" value="${queryPage.totalrows}" type="hidden"/>
    <div class="create—expense-btn" id="createExpense"></div>
    <div class="backHome" id="createExpense" onclick="backToHome()"></div>
    <script>
  //#content为某个div的id	
  var isContinue=false;  //是否继续加载，默认为false，不继续加载
  var dropload = $('.ld-application-form-list').dropload({ 		
	  //scrollArea很关键，要不然加载更多不起作用		
	  scrollArea : window,	
	  distance:100,
	  domUp : {		    
		  domClass   : 'dropload-up',		    
		  domRefresh : '<div class="dropload-refresh">↓${lfn:message("fssc-mobile:fssc.mobile.list.down.reflesh")}</div>',		    
		  domUpdate  : '<div class="dropload-update">↑${lfn:message("fssc-mobile:fssc.mobile.list.up.reflesh")}</div>',		    
		  domLoad    : '<div class="dropload-load"><span class="loading"></span>${lfn:message("fssc-mobile:fssc.mobile.list.loading")}</div>'		
		 },		
	  domDown : {		    
		  domClass   : 'dropload-down',		    
		  domRefresh : '<div class="dropload-refresh">↑${lfn:message("fssc-mobile:fssc.mobile.list.load.more")}</div>',		    
		  domLoad    : '<div class="dropload-load"><span class="loading"></span>${lfn:message("fssc-mobile:fssc.mobile.list.loading")}</div>',		    
		  domNoData  : '<div class="dropload-noData">${lfn:message("fssc-mobile:fssc.mobile.list.no.data")}</div>'		   
		 },		
	  loadUpFn : function(me){				
		  //下拉刷新需要调用的函数	
		  window.location.reload(true);			//重置下拉刷新				
		  me.resetload();   
		  },		
	  loadDownFn : function(me){
			  var totalrows=$("[name='totalrows']").val();
			  var rowsize=$("[name='rowsize']").val();
              var pageno=$("[name='pageno']").val();
              if((totalrows-rowsize*pageno)>0){
                  isContinue=true;
                  $(".dropload-down").show();
              }else{
                  isContinue=false;  //不需要再加载
                  $(".dropload-down").hide();
              }
              if(isContinue){
				  if(!pageno){
					  pageno=1;
				  }
				  pageno=pageno*1+1;
				  $("#ld-main-loading").attr("style","display: block;");
				  //上拉加载更多需要调用的函数				
				  $.ajax({
						url: '${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=moreData&pageno='+pageno+'&rowsize=${queryPage.rowsize}',
						type: 'post',
						error: function(data){
							console.log("error"+data);
						},
						success: function(data){
							//移除前一次查询的参数
							$("[name='pageno']").remove();
							$("[name='rowsize']").remove();
							console.log("success");
							$("#list_data").append($(data));
							$("#ld-main-loading").attr("style","display: none;");
						}
					});
				  $(".dropload-down").remove();
				  setTimeout(function(){			            // 每次数据加载完，必须重置			                
					  me.resetload();	
				  },1000);
		  	  }
			 }		
		  });
		function viewLoan(id){
			window.open("${LUI_ContextPath}/fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=view&fdId="+id,'_self');
		}
    </script>
</body>
</html>
