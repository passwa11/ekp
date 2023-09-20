<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.third.intell.model.IntellConfig"%>
<%
	IntellConfig intellConfig = new IntellConfig();
	String aipUrl = intellConfig.getItDomain();
	if (aipUrl!=null && aipUrl.endsWith("/")){
		aipUrl = aipUrl.substring(0, aipUrl.length()-1);
	}
	String systemId = intellConfig.getSystemName();
%>
<div class="iframe">

	<iframe id="ifr" src="<%=aipUrl %>/web/aip-smart-portal/manage/index.html#/ekpTag/tags" frameborder="0" width="100%" height="100%"></iframe>

</div>
<script type="text/javascript">  
window.onload = function(){
    var ifr = document.querySelector('#ifr');
    var content  = parent.document.getElementsByClassName("intell_tag_content");
    //var ids  = parent.document.getElementsByName("fdId");
    var ids = parent.document.getElementsByName("intellTagModelId");
    var names = parent.document.getElementsByName("intellTagModelName");
    var title = parent.document.getElementsByName("docSubject");
    
    var id = "";
    var subject="";
    var modelName="";
    
    if(ids !=null){
    	for(var n =0;n<ids.length;n++){
    		id = ids[0].value;
    	}
    }
    if(names !=null){
    	for(var n =0;n<names.length;n++){
    		modelName = names[0].value;
    	}
    }
    if(title!=null){
    	for(var n =0;n<title.length;n++){
    		subject = subject + " "+ title[n].value;
    	}
    }
    var text = subject;
    if(content !=null ){
    	for(var n =0;n<content.length;n++){
    		text = text + " "+ content[n].innerHTML;
    	}
    }

    var data ={
	            "kmId":id,
	            "kmTitle":subject,
	            "kmPlatFrom":"<%=systemId%>",
	            "kmModule":modelName,
	            "kmUrl":"url",
	            "description": "desc",
	          	"content": text
        	}
    setTimeout(function() {
    	ifr.contentWindow.postMessage(data, '*');
    } ,"500"); 
}

window.addEventListener('message', function(e){
	if(e !=null && e.data !=null){
		if(e.data.type !=null && 'error' != e.data.type){
		var container = parent.document.getElementById("intellTagView");
		if(container!=null){
			container.innerText="";
			if(e.data.tags!=null){
				var rtn = e.data.tags;
				var tagText = "";
				for (var i = 0; i < rtn.length; i++) {
					var tagDom = document.createElement("div");
					tagDom.setAttribute("class","tag_tagSign");
					if (rtn[i] !=null && rtn[i] != "") {
						tagText =rtn[i].name;
					}
					tagDom.innerText = tagText.trim();
					container.appendChild(tagDom);
				}
				
			}
		}
		var divs = parent.document.getElementsByClassName("lui_dialog_head_right");
		 if(divs !=null){
		    	for(var n =0;n<divs.length;n++){
		    		 divs[0].click();
		    	}
		    }
		}
	}
}, false);
</script>


