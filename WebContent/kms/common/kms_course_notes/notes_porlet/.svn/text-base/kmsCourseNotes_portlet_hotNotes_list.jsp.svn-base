<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/hotNotes_portlet.css" />


		<input type="hidden" id="pageno" />
		<input type="hidden" id="total" />
		<div class="hot_notes">
	    	<div class="hot_notes_title">
	        	<p class="label1">${lfn:message('kms-common:kmsCommon.hotNotes') }</p>
	        	<p class="label2">HOT NOTES</p>
	        </div>
	        <div class="hot_notes_con">
		        <div class="shortCut_a">
		        			<div class="shortCut_TL" mark="but_left" onclick="turnLeft();"></div>
	  						<div class="shortCut_TR" mark="but_right" onclick="turnRight();"></div>
	  
	                  
						 	<list:listview channel="notes_chl" cfg-spa="false" >
							<%-- 视图列表 --%>
							<list:gridTable name="gridtable" columnNum="3"  >
								<ui:source type="AjaxJson">
									{url:'/kms/common/kms_notes/kmsCourseNotes.do?method=getNotes&mode=${param.mode}&orderby=docPraiseCount&ordertype=down&rowsize=3'}
								</ui:source>
								<list:row-template ref="kms.courseNotes.listview.gridtable" >
								</list:row-template>
							</list:gridTable>
							</list:listview>
							<center>
							<div  class="lui_course_notes_page_top" >
								<list:paging channel="notes_chl"  layout="sys.ui.paging.top" > 		
								</list:paging>
							</div>
							</center>
					
				</div>	
			</div>
		
		</div>

<script>
	function turnLeft(){
			var pagenum = document.getElementById("pageno").value;
			var total = document.getElementById("total").value;
			var pageSize = 3;
			var pageno =  Number(pagenum)-Number(1);
			var pageId = $(".lui-component").attr("id");
			if(pageno>=1){
				var evt = {
						paging : pageId,
						page : [{
									key : 'pageno',
									value : [pageno]
								}, {
									key : 'rowsize',
									value : [pageSize]
								}],
						
					};
				seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
					topic.channel("notes_chl").publish("paging.changed", evt);
				});
			}
			
	}

	function turnRight(){
		var pagenum = document.getElementById("pageno").value;
		var total = document.getElementById("total").value;
		var pageSize = 3;
		var pageno = Number(pagenum)+Number(1);
		var pageId = $(".lui-component").attr("id");
		if(pageno<=total){
			var evt = {
					paging : pageId,
					page : [{
								key : 'pageno',
								value : [pageno]
							}, {
								key : 'rowsize',
								value : [pageSize]
							}],
					
				};
			seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
				topic.channel("notes_chl").publish("paging.changed", evt);
			});
		}
		
}
	function showInfo(id){
		Com_OpenWindow('<c:url value="/kms/common/kms_notes/kmsCourseNotes.do" />?method=viewPorlet&fdId='+id);
	}
</script>
	
