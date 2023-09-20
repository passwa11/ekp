/*****************************************
功能：根据附件文件名后缀返回信息
参数：fileType 附件类型
******************************************/
function GetIconName(fileType) {
	switch(fileType){
		case "application/msword":return "word.png";
		case "application/vnd.ms-excel":return "excel.png";
		case "application/vnd.ms-powerpoint":return "ppt.png";
		case "application/pdf":return "pdf.png";
		case "text/plain":return "text.png";
		case "application/x-jpg":return "image.png";
		case "application/x-ico":return "image.png";
		case "application/x-bmp":return "image.png";
		case "image/gif":return "image.png";
		case "image/png":return "image.png";
		default:return "documents.png";
	}
}

function GetIconNameByFileName(fileName) {
	if(fileName==null || fileName=="")
		return "documents.png";
	var fileExt = fileName.substring(fileName.lastIndexOf("."));
	if(fileExt!="")
		fileExt=fileExt.toLowerCase();
	switch(fileExt){
		case ".doc":
		case ".docx":
			  return "word.png";
		case ".xls":
		case ".xlsx":
			return "excel.png";
		case ".ppt":
		case ".pptx":
			return "ppt.png";
		case ".pdf":return "pdf.png";
		case ".vsd":return "vsd.png";
		case ".txt":return "text.png";
		case ".text":return "text.png";
		case ".jpg":return "image.png";
		case ".jpeg":return "image.png";
		case ".ico":return "image.png";
		case ".bmp":return "image.png";
		case ".gif":return "image.png";
		case ".png":return "image.png";
		case ".mp3":return "audio.png";
		case ".psd":return "psd.png";
		case ".svg":return "svg.png";
		case ".wps":return "wps.png";
		case ".tif":return "tif.png";
		case ".dps":return "dps.png";
		case ".ofd":return "ofd.png";
		case ".key":return "keynote.png";
		case ".rmvb":return "rmvb.png";
		case ".et":return "et.png";
		case ".ai":return "ai.png";
		case ".raq":return "raq.png";
		case ".sketch":return "sketch.png";
		case ".pro":return "pro.png";
		case ".js":return "js.png";
		case ".zip":return "zip.png";
		case ".out":return "outlook.png";
		case ".rtf":return "wps.png";
		case ".3gp":
		case ".f4v":
		case ".mp4":
		case ".mpg":
		case ".mov":
		case ".asx":
		case ".asf":
		case ".avi":
		case ".flv":
		case ".rm":
		case ".wav":
		case ".m4v":
		case ".ogg":
		case ".wmv":
		    	   return "video.png";
		case ".ttf":
		case ".ttc":
		case ".otf":
					return "font.png";
		case ".html":
		case ".htm":
					return "html.png";
		case ".rar":
		case ".gz":
		case ".7z":
			return "7z.png";
		default:return "aiv.png";
	}
}

// 返回CSS类名
function GetIconClassByFileName(fileName) {
	if(fileName==null || fileName=="")
		return "icon_documents";
	var fileExt = fileName.substring(fileName.lastIndexOf("."));
	if(fileExt!="")
		fileExt=fileExt.toLowerCase();
	switch(fileExt){
		case ".doc":
		case ".docx":
			  return "icon_word";
		case ".rar":
		case ".zip":
		case ".tar":
		case ".gz":
			return "icon_zip";
		case ".xls":
		case ".xlsx":
			return "icon_excel";
		case ".ppt":
		case ".pptx":
			return "icon_ppt";

		case ".rmvb":
			return "icon_rmvb";
		case ".txt":return "icon_text";
		case ".jpg":
		case ".jpeg":return "icon_jpg";
		case ".audio":return "icon_aud";
		case ".pst":return "icon_outlook";
		default:return "icon_" + fileExt.substr(1);
	}
}
