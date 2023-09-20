$(document).ready(function(){
	$(".ld-remember-attact-info img").each(function(){
		this.src = getSrcByName($(this).data("file"));
	})
});
//获取附件的显示图片
function getSrcByName(file_name){
	var iconMap=new Map();
	var iconArr=["aiv","aud","bmp","documents","excel","gif","html","image","jpg","js","movie","mp4","outlook","pdf","png","ppt","text","tif","video","visio","vsd","word","zip"];
	for(var i=0;i<iconArr.length;i++){
		iconMap.set(iconArr[i],iconArr[i]);
	}
		var suffix_name=file_name.substring(file_name.lastIndexOf('.')+1);
		var icon_name="";
		if("doc"==suffix_name){
			icon_name="word";
		}else if("xlsx"==suffix_name||"xls"==suffix_name){
			icon_name="excel";
		}else if("pptx"==suffix_name||"ppt"==suffix_name){
			icon_name="ppt";
		}else{
			icon_name= iconMap.get(suffix_name);
		}
		if(!icon_name){
			icon_name="documents";
		}
		return Com_Parameter.ContextPath+"sys/attachment/view/img/file/2x/icon_"+icon_name+".png";
}

/*************************************************************************
 * 删除附件
 *************************************************************************/
function deleteAtt(file_id,file_name){
	jqalert({
        title:'',
        content:fsscLang['fssc-mobile:tips.delete.att.confirm'],
        yestext:fsscLang['button.ok'],
        notext:fsscLang['button.cancel'],
        yesfn:function () {
        	var fdModelId=$("[name='fdId']").val();
        	$(".ld-remember-attact-info").parent().find("[data-attid='"+file_id+"']").parent().remove();  //移除js样式
       	  	$.ajax({
               type: 'post',
               async:false,
               url:Com_Parameter.ContextPath + 'fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=delAtt',
               data: {"fdModelId":fdModelId,"filename":file_name},
       	  	}).success(function (data) {
         	  console.log("删除附件成功");
       	  	}).error(function () {
               console.log("删除附件失败");
       	  	})
        }
    })
}

function showAtt(file_id,file_name){
	if(file_name&&(file_name.indexOf("bmp")>-1||file_name.indexOf("gif")>-1||file_name.indexOf("image")>-1||file_name.indexOf("jpg")>-1
		||file_name.indexOf("png")>-1||file_name.indexOf("jpeg")>-1||file_name.indexOf("BMP")>-1||file_name.indexOf("GIF")>-1||file_name.indexOf("IMAGE")>-1||file_name.indexOf("JPG")>-1
		||file_name.indexOf("PNG")>-1||file_name.indexOf("JPEG")>-1)){ //图片，直接查看
		$("#att_view").show();
		$("#img_view").attr('src',Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_public/fsscMobilePublic.do?method=readDownload&fdId='+file_id);
	}else if(file_name&&(file_name.indexOf("xls")>-1||file_name.indexOf("xlsx")>-1||file_name.indexOf("doc")>-1||file_name.indexOf("docx")>-1
		||file_name.indexOf("ppt")>-1||file_name.indexOf("pptx")>-1||file_name.indexOf("outlook")>-1||file_name.indexOf("pdf")>-1)){//word、excel、ppt、outlook
		window.open(Com_Parameter.ContextPath +"sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId="+file_id+"&viewer=mobilehtmlviewer&title="+file_name,"_self");
	}else if(file_name&&(file_name.indexOf("aiv")>-1||file_name.indexOf("movie")>-1||file_name.indexOf("mp4")>-1||file_name.indexOf("video")>-1
		||file_name.indexOf("aud")>-1)){//视频
		window.open(Com_Parameter.ContextPath +"sys/attachment/mobile/viewer/video/videoViewer.jsp?fdId="+file_id+"&title="+file_name,"_self");
	}else{//下载
		window.open(Com_Parameter.ContextPath +"sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="+file_id,"_self");
	}
}

function hideAtt(){
	$("#att_view").hide();
	$("#img_view").attr('src','');
}
