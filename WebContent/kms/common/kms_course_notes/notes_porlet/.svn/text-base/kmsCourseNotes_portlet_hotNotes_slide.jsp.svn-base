<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes_portlet.css" />



		<div class="hot_notes">
	    	
	        <div class="hot_notes_con">
		        <div class="shortCut_a km-note-list-panel hot slide hot_slide">
		        			<input type="hidden" id="pageno" />
							<input type="hidden" id="total" />
						 	<list:listview channel="notes_hot_slide_chl" cfg-spa="false" >
							<%-- 视图列表 --%>
							<list:gridTable name="gridtable" columnNum="3"  >
								<ui:source type="AjaxJson">
									{url:'/kms/common/kms_notes/kmsCourseNotes.do?method=getNotes&mode=${param.mode}&orderby=docPraiseCount&ordertype=down&rowsize=3&display=portlet'}
								</ui:source>
								<list:row-template ref="kms.courseNotes.hotSlide.listview.gridtable" >
								</list:row-template>
							</list:gridTable>
							<ui:event topic="list.loaded" args="vt">
								
								var pageno = $(".hot_slide #pageno").val();
								var total = $(".hot_slide #total").val();
								$(".hot_slide .pageno").text(pageno);
								$(".hot_slide .total").text(total);
							</ui:event>
							</list:listview>
					
				</div>	
			</div>
		
		</div>

<script>
	function turnLeft(){
			//var pagenum = document.getElementById("pageno").value;
			var pagenum = $(".hot_slide #pageno").val();
			//var total = document.getElementById("total").value;
			var total = $(".hot_slide #total").val();
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
					topic.channel("notes_hot_slide_chl").publish("paging.changed", evt);
				});

			}
			
	}

	function turnRight(){
		var pagenum = $(".hot_slide #pageno").val();
		var total = $(".hot_slide #total").val();
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
				topic.channel("notes_hot_slide_chl").publish("paging.changed", evt);
			});
		}
		
}
	function showInfo(id){
		Com_OpenWindow('<c:url value="/kms/common/kms_notes/kmsCourseNotes.do" />?method=view&fdId='+id);
	}
</script>
	
