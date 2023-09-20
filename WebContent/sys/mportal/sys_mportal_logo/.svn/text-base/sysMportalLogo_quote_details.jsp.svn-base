<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
    <title>${lfn:message('sys-mportal:sysMportal.logo.quote.click')}</title>
    <link href="${KMSS_Parameter_ContextPath}resource/style/default/doc/document.css" rel="stylesheet" type="text/css" />
	<style>
	.tb_normal td {
	    padding: 8px;
	    border: 1px #d2d2d2 solid;
	    word-break: break-all;
	}
	.background{
	    min-height: 100%;
	    width: 70%;
	    margin: 0 auto;
	    min-width: 980px;
	    padding: 30px;
	    background-color: #FFFFFF;
	    margin-top: 20px;
	}
	body {
	    background-color: #f7f7f7;
	}
	.page_subject{
	    text-align: center;
	    margin: 30px 0px;
	}
	.mportal_logo{
	    position: relative;
	    margin: 6px 0 0 6px;
	    width: 200px;
	    height: 73px;
	    display: table-cell;
	    line-height: 73px;
	    background-color: #1d9d74;
	    vertical-align: middle;
	}
	.mportal_logo img{
	    width: 153px;
	    height: 20px;
	}
	.btn_txt{
	    color: #2574ad;
	}
	</style>
    <script type="text/javascript">
	window.edit = function(id,type) { 
		if(id){
			var url="";
			if(type=='simple'){
				url="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=edit&fdId=";
			}else{
				url="/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=edit&fdId=";
			}
			window.open("${LUI_ContextPath }"+url+id); 
		}
	};
</script>
</head>
<div class="background">
    <c:if test="${fn:length(simple)>0}">
	<h1 class="page_subject">${lfn:message('sys-mportal:sysMportal.logo.quote.details.simple')}</h1>
	<table width="100%" class="tb_normal">
		<tr align="center" class="td_normal_title">
		    <td width="25%">logo</td>
		    <td width="50%">${lfn:message('sys-mportal:sysMportal.msg.common')}</td>
		    <td width="25%">${lfn:message('title.option')}</td>
		</tr>
		<c:forEach items="${simple}" var="item" varStatus="vstatus">
			<tr align="center">
			    <td width="25%">
			        <div class="mportal_logo">
			            <img src="${LUI_ContextPath }${item.fdLogo}" >
			        </div>
				</td>
			    <td width="50%">
			    <p> ${item.fdName}</p>
			    </td>
			    <td width="25%">
				    <kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=edit&fdId=${item.fdId}" requestMethod="POST">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${item.fdId}','simple')">${lfn:message('button.edit')}</a>
					</kmss:auth>
			    </td>
			</tr>
	    </c:forEach>
	</table>
	</c:if>
	<c:if test="${fn:length(composite)>0}">
	<h1 class="page_subject" style="margin-top: 60px;">${lfn:message('sys-mportal:sysMportal.logo.quote.details.composite')}</h1>
	<table width="100%" class="tb_normal">
		<tr align="center" class="td_normal_title">
		    <td width="25%">logo</td>
		    <td width="50%">${lfn:message('sys-mportal:sysMportal.msg.composite')}</td>
		    <td width="25%">${lfn:message('title.option')}</td>
		</tr>
		<c:forEach items="${composite}" var="item" varStatus="vstatus">
			<tr align="center">
			    <td width="25%">
			        <div class="mportal_logo">
			            <img src="${LUI_ContextPath }${item.fdLogo}" >
			        </div>
				</td>
			    <td width="50%">${item.fdName}</td>
			    <td width="25%">
				    <kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalComposite.do?method=edit&fdId=${item.fdId}" requestMethod="POST">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${item.fdId}','composite')">${lfn:message('button.edit')}</a>
					</kmss:auth>
			    </td>
			</tr>
	    </c:forEach>
	</table>
	</c:if>
</div>