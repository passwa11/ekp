<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/account.css?s_cache=${LUI_Cache }">
	<link rel="stylesheet"  href="${LUI_ContextPath}/fssc/mobile/resource/css/costDetail.css?s_cache=${LUI_Cache }">
    <script >
	    var formInitData={
	   		 'LUI_ContextPath':'${LUI_ContextPath}',
	    }
    	Com_IncludeFile("rem.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);  	
    	function deleteAccount(e,id){
    		e = e||window.event;
    		e.stopPropagation?e.stopPropagation():(e.cancelBubble = true);
    		jqalert({
                title:'提示',
                content:'确定要删除该账户吗？',
                yestext:'确认',
                notext:'取消',
                yesfn:function () {
                		$.ajax({
                			url:'${LUI_ContextPath}/eop/basedata/eop_basedata_account/eopBasedataAccount.do?method=deleteall',
                			type:'POST',
                			dataType:'json',
                			data:$.param({'List_Selected':id},true),
                			success:function(data){
                				jqtoast('操作成功')
                				window.location.reload();
                			},
                			error:function(e){
                				jqtoast('操作失败')
                			}
                		})
                },
                nofn:function () {
                }
            })
    	}
    </script>
    <title>我的账户</title>
</head>
<body>
<ul class="ld-account-my-ul">
<c:forEach var="eopBasedataAccount" items="${queryPage.list}" varStatus="status">
<li onclick="window.open('${LUI_ContextPath}/eop/basedata/eop_basedata_account/eopBasedataAccount.do?method=editMobile&fdId=${eopBasedataAccount.fdId}','_self');" class="ld-account-my-list-item">
      <div class="ld-account-my-top">
      		<div class="ld-account-my-icon">
      			<img src="${LUI_ContextPath }/fssc/mobile/resource/images/ifsdmobile093.png">
          	</div>
      		<div class="ld-account-my-account">
          		${eopBasedataAccount.fdName}	
          	</div>
          	<div class="ld-account-my-del">
          		<img src="${LUI_ContextPath }/fssc/mobile/resource/images/del.png" onclick="deleteAccount(event,'${eopBasedataAccount.fdId}')">
          	</div>
      </div>
      <div class="ld-account-my-info">
      		<div class="ld-account-my-info-left">
      			${eopBasedataAccount.fdBankName}
      		</div>
          <div class="ld-account-my-info-right">
            	${eopBasedataAccount.fdBankAccount}
          </div>
      </div>
  </li>
</c:forEach>
</ul>
<div class="addOne" onclick="window.open('${LUI_ContextPath}/eop/basedata/eop_basedata_account/eopBasedataAccount.do?method=addMobile','_self')"></div>
<div class="backHome" id="expenseBtn" onclick="window.open('${LUI_ContextPath}/fssc/mobile/index.jsp','_self')"></div>
<input name="pageno" value="${queryPage.pageno}" type="hidden"/>
<input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
<input name="totalrows" value="${queryPage.totalrows}" type="hidden"/>
</body>
</html>
