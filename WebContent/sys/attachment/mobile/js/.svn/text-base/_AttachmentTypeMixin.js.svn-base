define([ 'dojo/_base/declare' ], function(declare) {
	return declare('sys.attachment.mobile.js._AttachmentTypeMixin', null, {
		getType : function() {
			var typeStr = '';
			if (this.name.lastIndexOf('.') > -1) {
				var fileExt = this.name
					.substring(this.name.lastIndexOf('.') + 1);
				if (fileExt != '')
					fileExt = fileExt.toLowerCase();
				switch (fileExt) {
					case 'doc':
					case 'docx':
						typeStr = 'word';
						break;
					case 'xls':
					case 'xlsx':
						typeStr = 'excel';
						break;
					case 'pps':
					case 'ppt':
					case 'pptx':
						typeStr = 'ppt';
						break;
					case 'ofd':
						typeStr = 'ofd';
						break;
					case 'pdf':
						typeStr = 'pdf';
						break;
					case 'txt':
					case 'text':
						typeStr = 'text';
						break;
					case 'jpg':
					case 'jpeg':
					case 'ico':
					case 'bmp':
					case 'gif':
					case 'png':
					case 'svg':
						typeStr = 'img';
						break;
					case 'tif':
						typeStr = 'tif';
						break;
					case 'mht':
					case 'html':
					case 'htm':
						typeStr = 'html';
						break;
					case 'mpp':
						typeStr = 'project';
						break;
					case 'vsd':
						typeStr = 'visio';
						break;
					case 'zip':
					case 'rar':
					case 'rar':
					case '7z':
					case 'gz':
						typeStr = 'zip';
						break;
					case 'mp4':
					case 'm4v':
					case 'flv':
					case 'f4v':
					case 'ogg':
					case '3gp':
					case 'avi':
					case 'wmv':
					case 'asx':
					case 'asf':
					case 'mpg':
					case 'mov':
					case 'rm':
					case 'wmv9':
					case 'wrf':
						typeStr = 'video';
						break;
					case 'mp3':
						typeStr = 'audio';
						break;
					case 'wps':
						typeStr = 'wps';
						break;
					case 'et':
						typeStr = 'et';
						break;
					case 'dps':
						typeStr = 'dps';
						break;
					case 'out':
						typeStr = 'out';
						break;
					case 'psd':
						typeStr = 'psd';
						break;
					case 'raq':
						typeStr = 'raq';
						break;
					case 'wav':
						typeStr = 'wav';
						break;
					case 'ttc':
					case 'ttf':
					case 'otf':
						typeStr = 'ttf';
						break;
					case 'key':
						typeStr = 'keynote';
						break;
					case 'rmvb':
						typeStr = 'rmvb';
						break;
					case 'ai':
						typeStr = 'ai';
						break;
					case 'sketch':
						typeStr = 'sketch';
						break;
				}
			}
			return typeStr;
		}
	});
});