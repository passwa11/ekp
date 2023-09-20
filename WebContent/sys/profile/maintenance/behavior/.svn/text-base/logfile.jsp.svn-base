<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.logfile" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="head">
		<style>
			#mainTable {width:900px;}
			#mainTable td {vertical-align: middle; line-height:30px;}
		</style>
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			var source = new sui.DataSource('/sys/profile/maintenance/behavior/behaviorSetting.do?method=listLogFile');
			source.bindRender(new sui.Paging('#paging_bar'));
			source.bindRender(new sui.TableRender('#logTable',{
				columns:['file','date'],
				align:[0,0]
			}));
			function submitForm(){
				var value = $.trim($('input[name="file"]').val());
				if(value==''){
					alert('<bean:message key="sys.profile.behavior.logfile.nofile" bundle="sys-profile-behavior" />');
					return;
				}
				if(value.toLowerCase().substring(value.length-4)!='.zip'){
					alert('<bean:message key="sys.profile.behavior.logfile.fileerr" bundle="sys-profile-behavior" />');
					return;
				}
				document.forms[0].submit();
			}
	
			$(function(){
				source.load();
				menu_focus("1__logfile");
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.logfile" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <form action="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=uploadLogfile" method="post" enctype="multipart/form-data">
					<center>
						<span style="color: red; font-weight: bold;"><c:out value="${message}" /></span>
						<table class="tb_normal" style="width:900px;" id="mainTable">
							<tr class="tr_normal_title">
								<td><bean:message key="sys.profile.behavior.logfile.info" bundle="sys-profile-behavior" /></td>
							</tr>
							<tr>
								<td style="text-align: center;">
									<input type="file" name="file" style="padding:5px 0px;">
									<input type="button" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.submit') }" style="padding:5px 20px; cursor: pointer;" onclick="submitForm();">
								</td>
							</tr>
						</table>
						<div style="margin:10px auto 5px auto;width:900px;" class="clearfloat">
							<div style="float:left; font-size: 14px;"><bean:message key="sys.profile.behavior.logfile.uploaded" bundle="sys-profile-behavior" /></div>
							<div style="float:right; text-align: right; " id="paging_bar"></div>
						</div>
						<table class="tb_normal" style="width:900px;" id="logTable">
							<tr class="tr_normal_title">
								<td style="width:50%;">
									<bean:message key="sys.profile.behavior.logfile.name" bundle="sys-profile-behavior" />
								</td>
								<td style="width:50%;">
									<bean:message key="sys.profile.behavior.logfile.time" bundle="sys-profile-behavior" />
								</td>
							</tr>
						</table>
					</center>
				</form>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>