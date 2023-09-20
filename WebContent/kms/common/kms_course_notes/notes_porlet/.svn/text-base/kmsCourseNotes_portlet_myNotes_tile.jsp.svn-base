<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes_portlet.css" />



		<div class="hot_notes tile">
	    	
	        <div class="hot_notes_con">
		        <div class="shortCut_a km-note-list-panel hot tile my_tile">
		        			<input type="hidden" id="pageno" />
							<input type="hidden" id="total" />
						 	<list:listview channel="notes_my_tile_chl" cfg-spa="false" >
							<%-- 视图列表 --%>
							<list:gridTable name="gridtable" columnNum="4"  >
								<ui:source type="AjaxJson">
									{url:'/kms/common/kms_notes/kmsCourseNotes.do?method=getNotes&mode=${param.mode}&orderby=docPraiseCount&ordertype=down&rowsize=12&display=portlet'}
								</ui:source>
								<list:row-template ref="kms.courseNotes.myTile.listview.gridtable" >
								</list:row-template>
							</list:gridTable>
							<ui:event topic="list.loaded" args="vt">
								
								var pageno = $(".my_tile #pageno").val();
								var total = $(".my_tile #total").val();
								$(".my_tile .pageno").text(pageno);
								$(".my_tile .total").text(total);
							</ui:event>
							</list:listview>
							<list:paging channel="notes_my_tile_chl"></list:paging>	
					
				</div>	
			</div>
		
		</div>

<script>

	function showInfo(id){
		Com_OpenWindow('<c:url value="/kms/common/kms_notes/kmsCourseNotes.do" />?method=view&fdId='+id);
	}
</script>
	
