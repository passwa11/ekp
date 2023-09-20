<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
      seajs.use(['lui/dialog'], function(dialog) {
    	  window.feedback = function (id){
    		  var path ="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do?method=add&fdDocId=${param.fdId}&fdCreatorId="+id;
    		  dialog.iframe(path,' ',null,{width:750,height:500});
    	  }
	      window.appointFeedback = function(){
	      	  var path = "/hr/ratify/hr_ratify_main/hrRatifyChangeFeedback.jsp?fdId=${param.fdId}"
	      	  dialog.iframe(path,' ',null,{width:750,height:500});
	      }
      });
</script>