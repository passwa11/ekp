<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<kmss:auth
	requestURL="/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=cancelIntro&fdModelName=${param.fdModelName}">
	<script>
	function introduce_cancelIntroduce(){
		datas1 = window.datas;
		var values1 = [];
		var values2 = [];
		var fdIframeInfo="${lfn:message('sys-introduce:sysIntroduceMain.cancel.confirm')}";
		LUI.$("input[name='List_Selected']:checked").each(function(){
			for(var i=0;i<datas1.length;i++){
				if(LUI.$(this).val()==datas1[i].fdId && datas1[i].fdKnowledgeType== "1"){
					values1.push(LUI.$(this).val());
				}else if(LUI.$(this).val()==datas1[i].fdId && datas1[i].fdKnowledgeType== "2"){
					values2.push(LUI.$(this).val());
				}
			}
		});
		var fdModelId="${JsParam.fdModelId}";
		if(typeof(fdModelId)!="undefined"&&fdModelId!=""){
			values.push(fdModelId);
			fdIframeInfo="${lfn:message('sys-introduce:sysIntroduceMain.cancel.thisOne')}";
		}
		if(values1.length>0||values2.length>0) {
				seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
					dialog.confirm(fdIframeInfo,function(val,dia){
						if(val){
							window.del_load = dialog.loading();
							var xurl1 = "<c:url value="/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=cancelIntro&fdModelName=com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />";
							var xurl2 = "<c:url value="/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=cancelIntro&fdModelName=com.landray.kmss.kms.wiki.model.KmsWikiMain" />";
							var xdata = {};
							if(values1.length>0){
								LUI.$.post(xurl1,LUI.$.param({"List_Selected":values1},true),function(json){
									if(window.del_load!=null)
										window.del_load.hide();
									topic.publish("list.refresh");
									if(json.status){
										dialog.success("${lfn:message('return.optSuccess')}");
									}else{
										dialog.failure("${lfn:message('return.optFailure')}");									
									}
								},'json');
							}
							if(values2.length>0){
								LUI.$.post(xurl2,LUI.$.param({"List_Selected":values2},true),function(json){
									if(window.del_load!=null)
										window.del_load.hide();
									topic.publish("list.refresh");
									if(json.status){
										dialog.success("${lfn:message('return.optSuccess')}");
									}else{
										dialog.failure("${lfn:message('return.optFailure')}");									
									}
								},'json');
							}
							
						}
					});
				});
		}else{
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert("${lfn:message('page.noSelect')}");
			});
		}
	}
	</script>
	<ui:button id="cancelIntroduce"
		cfg-if="param['docIsIntroduced']=='1'"
		text="${ lfn:message('sys-introduce:sysIntroduceMain.cancel.button') }"
		onclick="introduce_cancelIntroduce();" order="4">
	</ui:button>
	<style>
		#cancelIntroduce{
			display: block !important;
		}
	</style>
</kmss:auth>