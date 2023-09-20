<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes_portlet.css" />
<template:include ref="default.simple" sidebar="no" >
	<template:replace name="body">
		<ul class="notes" id="notes">
			
		</ul>
	<script>
		var flag =0;
		var data = ${data};
		var note;
		var notes;
		var obj;
		var view_url =Com_Parameter.ContextPath + "kms/common/kms_notes/kmsCourseNotes.do?method=view&fdId=";
		for (var one in data){
			for(var key in data[one]){
				if(flag==0){
					
					 //note ='<a href="#" onclick="edit_notes(';
					 // note = note+"'"+data[one][key]+"'"+')">';
					 note ='<a href="'+view_url+data[one][key]+'"'+" target='_blank'>";
					 flag++;
				}else if(flag==1){
					note = note+"<p>"+data[one][key]+"</p>";
						flag++;
				}else if(flag==2){
						note = note+"<h3 style='margin-left:15em;'>"+data[one][key]+"</h3> </a>";
						obj =document.createElement("li");
						obj.innerHTML= note;
						 //$(".notes").append(note);
						  notes = document.getElementById("notes");
						  notes.appendChild(obj);
						  //notes.innerHTML (note);
						flag=0;
				}
			
				
				}
			}

		
	</script>

	</template:replace>
</template:include>