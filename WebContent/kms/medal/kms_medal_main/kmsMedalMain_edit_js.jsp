<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
var jcrop_api = null;
var jcropMedal_filePath = null;
var attId = null;
attachmentObject_medalPic.setSmallMaxSizeLimit(5);
attachmentObject_medalPic.on("uploadSuccess", function() {
	seajs.use( [ 'lui/dialog' ], function(dialog) {
		jcropMedal_filePath = null; // 清空
		var fileList =  attachmentObject_medalPic.fileList;
		attId = fileList[fileList.length - 1].fdId;
		fileList.length = 0;
		if($(".jcrop-holder").size() > 0) {
			$(".jcrop-holder").remove();
			jcrop_api.destroy();
		}
		//去除附件机制生成的img
		$("#att_xdiv_medalPic").empty();
		var load = null;
		load = dialog.loading('图片上传中...');
			//压缩图片
		$.ajax({
			 type:"post",
			 url:"${LUI_ContextPath}/kms/medal/kms_medal_main/kmsMedalMain.do?method=zoomMedal",
			 data:"attId=" + attId + "&ruleWidth=200&ruleHeight=200",
			 dataType:"json",
			 success:function(data) {
				 jcropMedal_filePath = data['zoomPath'];
				 load.hide();
				 $(".lui_medal_cut_btn").show();
				//回显图片
				 $("#crop_medal_img, #big_medal_img, #small_medal_img")
				 .attr("src","${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?${LUI_Cache}&method=download&fdId=" + data['attId']);
				 $("#crop_medal_img").css({
					 width:data['width'],
					 height:data['height']
				 });
				//显示裁剪框
				$("#crop_medal_img").Jcrop({
					allowSelect: false,
					bgFade: true,
					bgOpacity: 0.5,
					aspectRatio:1,
					onChange:   showCoords
				},function(){
					 var bounds = this.getBounds();
				     boundx = bounds[0];
				     boundy = bounds[1];
					 jcrop_api = this;
					 jcrop_api.animateTo([0, 0, 100, 100]);
			    });
				//设定为有勋章图片
				$("#hasMedalPic").val("true");
			},
            error:function (errMsg){
                load.hide();
                alert("上传出错")
                console.log(errMsg)
            }
		});
	});
 });

 /**
  * 显示裁剪框
  */
 function showCoords(c){
     $('#startX').val(c.x);
     $('#startY').val(c.y);
     $('#finishX').val(c.x2);
     $('#finishY').val(c.y2);
     $('#cropWidth').val(c.w);
     $('#cropHeight').val(c.h);
     //更新预览
     updatePreview(c);
 };
 /**
  * 更新预览
  */
 function updatePreview(c){
   if (parseInt(c.w) > 0){
     $("#big_medal_img").css({
       width: Math.round( (100 / c.w) * boundx) + 'px',
       height: Math.round((100 / c.w) * boundy) + 'px',
       marginLeft: '-' + Math.round((100 / c.w) * c.x) + 'px',
       marginTop: '-' + Math.round((100 / c.w) * c.y) + 'px'
     });
     
     $("#small_medal_img").css({
	       width: Math.round((30 / c.w) * boundx) + 'px',
	       height: Math.round((30 / c.w) * boundy) + 'px',
	       marginLeft: '-' + Math.round((30 / c.w) * c.x) + 'px',
	       marginTop: '-' + Math.round((30 / c.w) * c.y) + 'px'
	     });
   }
 };
/**
 * 裁剪图片
 */
function sureCropImg() {
	seajs.use( [ 'lui/dialog' ], function(dialog) {
		var load = null;
		load = dialog.loading('图片裁剪中...');
			$.ajax({
				async:false,
				type:"post",
				url:"${LUI_ContextPath}/kms/medal/kms_medal_main/kmsMedalMain.do?method=cropImg",
				data: "startX=" + $('#startX').val() + "&startY=" 
					+ $('#startY').val() +  "&width="
					+  $('#cropWidth').val() + "&height=" + $('#cropHeight').val() + "&attId=" + attId + "&zoomPath=" + jcropMedal_filePath,
				dataType:"json",	
				success: function(data) {
					 $("#crop_medal_img").attr("src", "${LUI_ContextPath}/kms/medal/resource/images/upload_medal_200_200.jpg").css("visibility", "visible").show();
					 $("#crop_medal_img").css({
						 width:"200",
						 height:"200"
					 });
					 var src = "${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?${LUI_Cache}&method=download&fdId=";
					 $("#big_medal_img").attr("src", src + data['bigPicAttId']).attr("style", "");
					 $("#small_medal_img").attr("src", src + data['smallPicAttId']).attr("style","");
					 $(".jcrop-holder").remove();
					 jcrop_api.destroy();
					 $("#smallPicFdId").val(data['smallPicAttId']);
					 $("#bigPicFdId").val(data['bigPicAttId']);
					 $("#medal_pic_error").hide();
					 load.hide();
					 $(".lui_medal_cut_btn").hide();
					  dialog.success("图片裁剪成功");
					  picValidate();
				},
				error: function() {
					 load.hide();
				     dialog.failure("图片裁剪失败");
				}
			});
	});
}



function cancelCropImg() {
	$.ajax({
		type:"post",
		url:"${LUI_ContextPath}/kms/medal/kms_medal_main/kmsMedalMain.do?method=cancelImg",
		data: "attId=" + attId + "&zoomPath=" + jcropMedal_filePath,
		dataType:"json",	
		success: function(data) {
			 $("#crop_medal_img").attr("src", "${LUI_ContextPath}/kms/medal/resource/images/upload_medal_200_200.jpg").css("visibility", "visible").show();
			 $("#crop_medal_img").css({
				 width:"200",
				 height:"200"
			 });
			 var method = "${param.method}";
			 var big_old = "${LUI_ContextPath}/kms/medal/resource/images/upload_medal_100_100.jpg";
			 var small_old = "${LUI_ContextPath}/kms/medal/resource/images/upload_medal_30_30.jpg";
			 if("edit" == method) {
				 var oldsrc = "${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?${LUI_Cache}&method=download&fdId=";
				 big_old = oldsrc + "${bigPicMedal.fdId}";
  				 small_old = oldsrc + "${smallPicMedal.fdId}";
			 }
			 $("#big_medal_img").attr("src", big_old).attr("style", "");
			 $("#small_medal_img").attr("src", small_old).attr("style","");
			 $(".jcrop-holder").remove();
			 jcrop_api.destroy();
			 $("#person_pic_error").hide();
			 $(".lui_medal_cut_btn ").hide();
		},
		error: function() {
		}
	});
}
/**
 * 清除裁剪框
 */
function clearCoords(){
    $('#coords input').val('');
  };
</script>    
