define(function(require, exports, module) {
	var base = require("lui/base");
	var topic = require('lui/topic');
	
	
	var PREVIEW_SELECTED_CHANGE = "file/change";
	
	var FILE_READ = window.File_EXT_READ ?  (window.File_EXT_READ + ";.pdf") : ".doc;.xls;.ppt;.xlc;.docx;.xlsx;.mppx;.pptx;.xlcx;.wps;.et;.pdf;.dps";
	var CAD_READ = ".dwg;.dwf;.dxf";
	
	var IMAGE_TYPE = ["image/jpeg" , "image/gif", "image/bmp", "image/png"];
	
	
	var fileValue = base.Base.extend({

		index : -1,

		
		initProps : function($super, cfg) {
			$super(cfg);
			this.completeIds = cfg.completeIds || "";
			this.fileList = cfg.fileList || [];
			this.data = [];
		},
		
		startup : function() {
			if(this.isStartup) {
				return;
			}
			
			for(var i = 0; i < this.fileList.length; i ++) {
				
				if(this.fileList[i].fileStatus <= -1 ) {
					continue;
				}
			
				var type = this.getFileType(this.fileList[i]);
			
				if( type == "office" || type == "pdf" || type == "video" || type == "cad") {
					if(this.completeIds.indexOf(this.fileList[i].fdId) > -1) {
						this.data.push(this.fileList[i])
					}
				} else if(type == "mp3" || type == "video" || type == "image" || type == "html" || type =="htm") {
						this.data.push(this.fileList[i])
				}
				
			}
			
			this.isStartup = true;
			
		},
		
		getFileType : function(file) {
			
			var type = file.fileType, fileExt = file.fileName
					.substring(file.fileName.lastIndexOf("."));
			if (!type)
				return ""

			for (var i = 0; i < IMAGE_TYPE.length; i++) {
				if (type.indexOf(IMAGE_TYPE[i]) > -1) {
					return "image";
				}
			}
			
			// cad相关文件在线阅读
			if (CAD_READ.indexOf(fileExt.toLowerCase()) > -1) {
				return "cad";
			}

			if (type.indexOf("audio/mpeg") > -1) {
				return "mp3";
			}

			if (type.indexOf("video") > -1 || type.indexOf("audio") > -1) {
				return "video";
			}

			if (fileExt.toLowerCase() == ".pdf") {
				return "pdf";
			}
			if (FILE_READ.indexOf(fileExt.toLowerCase()) > -1) {
				return "office";
			}
			if (type.indexOf("text/plain") > -1) {
				return "txt";
			}
			if (type.indexOf("text/html") > -1) {
				return "html";
			}

		},
		

		set : function(key, value) {
			this['set' + capitalize(key)].call(this, value);
		},

		get : function(key) {
			return this['get' + capitalize(key)].call(this);
		},


		getIndex : function() {
			return this.index;
		},

		getLength : function() {
			return this.data.length;
		},

		
		
		setIndexByStep : function(step) {
			var to = this.index + step;
			
			if(to > (this.data.length - 1) ) {
				to = 0;
			} else if(to < 0){
				to = this.data.length - 1;
			}
			
			this.setIndex(to);
		},

		setIndex : function(index, changeObj) {
			index = parseInt(index);
			if(index == this.index) {
				return;
			}
			this._preIndex = this.index;
			this.index = index;
			this.selectedChanged(this.index, changeObj);
		},


		getData : function() {
			return this.data;
		},

		selectedChanged : function(index, changeObj) {
			topic.publish(PREVIEW_SELECTED_CHANGE, {
				preIndex : this._preIndex,
				index : this.index,
				fileName : this.data[this.index].fileName,
				data : this.data,
				changeObj : changeObj
			});
		}

	});

	// 首字母大写
	var capitalize = function(str) {
		if (str == null || str.length == 0)
			return "";
		return str.substr(0, 1).toUpperCase() + str.substr(1);
	};

	module.exports = fileValue;
});
