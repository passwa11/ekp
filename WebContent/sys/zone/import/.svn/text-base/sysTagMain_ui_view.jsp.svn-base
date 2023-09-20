<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<dl class="sys_zone_info_board_tag" id="sys_zone_info_board_tag">

</dl>

<c:import url="/sys/tag/import/sysTagMain_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="sysZonePersonInfoForm" />
	<c:param name="useTab" value="false"></c:param>
	<c:param name="render" value="drawZonePerson"></c:param>
	<c:param name="fdQueryCondition" value="sysZonePersonInfo" />
</c:import>

<script>
	function drawZonePerson(rtns) { 

		seajs.use("lui/util/env", function(env) {
			var html = "";
			var classname = "class='tag_tagSignSpecial'"; 
			for (var i = 0; i < rtns.length; i++) {
				var rtn = rtns[i];
				
				html += "<li class='tag_li'><a href='" + rtn.href + "' "
						+ (rtn.isSpecial == 1 ? classname : "")
						+ " target='_blank' title='"+env.fn.formatText(rtn.text)+"'><dd><span>" + env.fn.formatText(rtn.text)
						+ "</span></dd></a></li>";
			}
			
			if(window.___modifyTag___){
				var  msg = "${lfn:message('sys-zone:sysZonePerson.addTags')}";
				html += "<li class='tag_addli' name='btn'><span class='lui_zone_append_tag com_bgcolor_d com_fontcolor_d' id='tag_selectItem' onclick='___modifyTag___();'"
					+ "title='"+msg+"'>+</span></li>";
			}
	
	
			$(".sys_zone_info_board_tag").html(html);
			
			var middle_div = $(".sys_zone_person_card_frame").height()+$(".fdSignature_table").height();
			var right_div = $(".infoWall").height();
			//var objul=document.getElementById("sys_zone_info_board_tag");
		//	var lis=objul.getElementsByTagName("li");
			//var li_num = lis.length;
			//right_div = right_div+($(".sys_tag").height())*Math.floor((li_num)/3);
			var max_div =  middle_div>right_div?middle_div:right_div;
			
			$(".sys_zone_page_info_brief").height(max_div);
		});
	}
</script>
<style>
.tag_li>a span {
    display: block;
    white-space: nowrap;
    /* width: 50px; */
    max-width: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>


