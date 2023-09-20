<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
Com_AddEventListener(window,'load',function(){
	$("table.tb_normal span[policy]").each(function(idx,domElem){
		var policy = $(domElem);
		policy.mouseover(function(evt){
				var spanObj = $(evt.target); 
				var policyId =  spanObj.attr("policy");
				var iframeDiv = $("#_pop_iframe_div_" + policyId);
				if(iframeDiv.length==0){
					iframeDiv = $("<div id='_pop_iframe_div_" + policyId + "' style='width:300px;position: absolute;z-index: 10;'>"
							+"<iframe style='width:100%;border:none;' scrolling='no' frameborder='0' "
							+"src='" + Com_Parameter.ContextPath + "sys/restservice/server/sys_restservice_server_policy/sysRestserviceServerPolicy.do?method=view&forward=simple&fdId="
							+ policyId + "'></iframe></div>");
					iframeDiv.appendTo(document.body);
				}else{
					iframeDiv.show();
				}
				iframeDiv.css({"top":evt.pageY + "px","left":evt.pageX + "px"});
			});
		policy.mouseout(function(evt){
			var spanObj = $(evt.target); 
			$("#_pop_iframe_div_" + spanObj.attr("policy")).hide();
		});
	});
});
</script>
