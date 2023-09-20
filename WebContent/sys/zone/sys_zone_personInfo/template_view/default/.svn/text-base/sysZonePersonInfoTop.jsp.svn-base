<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/sea.js?s_cache=${ LUI_Cache }"></script>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${ LUI_Cache }"></script>

	<ui:button id="report" parentId="top"  onclick="gotoReportView();"
		styleClass="lui_zone_report_off" title="${lfn:message('sys-zone:sysZonePerson.leaders') }" text="${lfn:message('sys-zone:sysZonePerson.leaders') }" style="pading:5px;">
	</ui:button>
	<ui:button id="similartag" parentId="top"  onclick="gotoTagView();"
		styleClass="lui_zone_similartag_off" title="${lfn:message('sys-zone:sys.zone.similartag') }" text="${lfn:message('sys-zone:sys.zone.similartag') }" style="pading:5px;">
	</ui:button>
	
	
	
	<script >
	
	//完成点击空白页隐藏
    $(document).bind('click', function(e) {  
        var e = e || window.event; //浏览器兼容性   
        var elem = e.target || e.srcElement;  
        while (elem) { //循环判断至跟节点，防止点击的是div子元素   
            if (elem.id && (elem.id == 'lui_zone_relation_box' ||elem.id == 'report'||elem.id == 'similartag'||elem.id == 'lui_zone_staffYpage_similartag'||elem.id == '_experience'||elem.id == 'lui_zone_person_experience')) {  
                return;  
            }
            elem = elem.parentNode;  
        }  
        $('#lui_zone_relation_box').css('display', 'none'); //点击的不是div或其子元素   
        $('.lui_zone_staffYpage_similartag').css('display', 'none'); //点击的不是div或其子元素   
        $('.lui_zone_person_experience').css('display', 'none'); //点击的不是div或其子元素   
        
    });  
		
		function gotoReportView(){
			$(".lui_zone_relation_box").toggle();
			$(".lui_zone_staffYpage_similartag").hide();
			$(".lui_zone_person_experience").hide();
			
	
		};
		function gotoTagView(){
			$(".lui_zone_staffYpage_similartag").toggle();
			var ele=$(".lui_zone_similar_data");
			 
			for(var i=0;i<ele.length;i++){
				
				var height=$(ele[i]).find("p").height();
				
				
				var str="";
				if(height>32){
			       $($(ele[i]).find("p")).css({"height":"32px","overflow":"hidden"});
			       $($(ele[i]).find("p")).after( $("<div>....</div>"))
				}
				else{
				
			}
			}
			$(".lui_zone_relation_box").hide();
			$(".lui_zone_person_experience").hide();
			
		};

		function gotoExpView(){
			$(".lui_zone_person_experience").toggle();
			$(".lui_zone_relation_box").hide();
			$(".lui_zone_staffYpage_similartag").hide();
			
		};

 
		
	</script>
	<style>
	.lui_widget_btn .lui_widget_btn_txt {
	    display: inline-block !important;
	    white-space:normal !important;
	    vertical-align: top;
	    font-weight: normal;
	    
	}
	</style>