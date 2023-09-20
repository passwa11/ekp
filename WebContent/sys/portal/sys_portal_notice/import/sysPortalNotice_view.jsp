<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="lui_portal_head_notice">
	<div class="lui_portal_head_notic_box"><div style="width:1100px;overflow:hidden;height:20px;position:absolute;"><span class="lui_portal_head_notic_content" style="left:0;"></span></div><i class="close">x</i></div>
       <script>
       	Com_IncludeFile("jquery.js", null, "js");
        </script>
       <script>
           $(function(){
        	   var $luiPortalHeadNotice = $(".lui_portal_head_notice");
        	   var $luiPortalHeaderZoneFrame = $('.lui_portal_header_zone_frame');
        	   var $luiPortalHeaderZoneFrameH = $(".lui_portal_header_zone_frame_h");
        	   $(".lui_portal_head_notice .close").click(function(){
        		   $luiPortalHeadNotice.hide();
        		   $luiPortalHeaderZoneFrameH.css({height:''});
        		   $luiPortalHeaderZoneFrame.removeClass('has-noticebar');
        	   });
        	   var url = Com_Parameter.ContextPath+'sys/portal/sys_portal_notice/sysPortalNotice.do?method=getPortalNotice';
               $.post(url,function(data){
                   if(data.isShow=="1"){
                	   $(".lui_portal_head_notice span").html(data.docContent);
                	   $luiPortalHeadNotice.show();
                       $luiPortalHeaderZoneFrame.removeClass('has-noticebar').addClass('has-noticebar');
                       if($luiPortalHeaderZoneFrame.height() > 0)
                    	  $luiPortalHeaderZoneFrameH.css({'height':$luiPortalHeaderZoneFrame.height()+'px'}); 
                       
                       var noticContWidth = $(".lui_portal_head_notic_content").width();
                       if(noticContWidth>=1000){
                     	  $(".lui_portal_head_notic_content").css("left","550px");
     	                  var timer = setInterval(function(){
     	                	   if($(".lui_portal_head_notice").css("display")==="none"){
     	                		   clearInterval(timer);
     	                	   }
     	                	   var compTime = 20;
     	                	   var fixedSpeed = 2;
     	                	   var runNum =Math.floor(compTime/(0.042));
     	                	   var speedPx = Math.floor(noticContWidth/runNum);
     		            	   var noticLeft = $(".lui_portal_head_notic_content").position().left;
     		            	   if(noticLeft<=(0-noticContWidth)){
     		            		   $(".lui_portal_head_notic_content").css("left","550px");
     		            	   }else{
     		            		   $(".lui_portal_head_notic_content").css("left",(noticLeft-fixedSpeed)+"px");
     		            	   }
     	               	  },42);  
                       }else{
                     	  $(".lui_portal_head_notic_box").width(noticContWidth+100);
                       }
                       
                   }else{
                	   $luiPortalHeadNotice.hide();
                   }
               },"json").error(function(){
            	   $luiPortalHeadNotice.hide();
               });       
           });
       </script>
</div>
