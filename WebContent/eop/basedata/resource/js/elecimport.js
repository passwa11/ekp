define(function(require, exports, module) {
	var lang = require('lang!eop-basedata');
	var $ = require("lui/jquery");
	var dialog = require("lui/dialog");
	var topic = require("lui/topic");

	var commonExport = {};

	var init = function(params) {
		commonExport.importerVue = new Vue({
			el : "#importer",
			data : {
				dataType : "",
				dialogVisible : false,
				firstTxt : "第一步:",
				secondTxt : "第二步:",
				thirdTxt : "用户类型:",
				updateTxt : "覆盖",
				ignoreTxt : "忽略",
				downloadTxt : "下载Excel模板",
				chooseFileTxt : "选择文件",
				chooseUserType : "选择用户类型",
				choosedFile : {
					fileName : "",
					dataFile : null
				},
				delTxt : "删除",
				importHinterTxt_1 : "说明：",
				importHinterTxt_2 : "1、Excel模板或有更新，请下载最新模板导入，以免导入出错",
				importHinterTxt_3 : "2、ERP物料编码选填，如需填写，请保持该值的唯一性",
				importHinterTxt_5 : "3、物料附件选填，如需填写，请填写对应文件所在服务器的绝对路径",
				confirmTxt : "确定",
				cancelTxt : "取消",
				resultTitle : "导入结果",
				resultVisible : false,
				resultData : [],
				resultLabel : "结果",
				actionLabel : "动作",
				nameLabel : "名称",
				infoLabel : "信息",
				typeLabel : "导入类型",
				addLabel : "新增",
				updateLabel : "更新",
				errorLabel : "失败",
				errorInfo : "错误信息",
				ignoreLabel : "忽略",
				repeatOpt : "add",
				options:[],
				value:''
			},
			computed : {
				dialogTitle : function() {
					var rtn = "批量导入物料";
					return rtn;
				}
			},
			methods : {
				show : function() {
					this.dialogVisible = true;
					this.delChoosedFile();
				},
				hide : function() {
					this.dialogVisible = false;
				},
				downloadTemplate : function() {
					document.templateForm.submit();
				},
				chooseDataFile : function() {
					$("#fileUploader").click();
				},
				delChoosedFile : function() {
					if (document.getElementById("fileUploader")) {
						document.getElementById("fileUploader").value = null;
					}
					this.choosedFile.fileName = "";
					this.choosedFile.dataFile = null;
				},
				showFile : function() {
					var dataFile = $("#fileUploader")[0].files[0];
					if (dataFile) {
						this.choosedFile.dataFile = dataFile;
						this.choosedFile.fileName = dataFile.name;
					} else {
						// this.choosedFile = null;
					}
				},
				handleChange(val){
					this.value = val;
				},
				submit : function() {
					var self = this;
					if (this.choosedFile.dataFile == null) {
						this.$alert("没有选择文件");
					} else {
						var formData = new FormData();
						formData.append("repeatOpt", this.repeatOpt);
						formData.append("userType",this.value);
						formData.append("dataFile", this.choosedFile.dataFile);
						var loading = this.$loading({
							lock : true,
							text : '正在导入',
							spinner : 'el-icon-loading',
							background : 'rgba(0, 0, 0, 0.65)'
						});
						submitFile({
							formData : formData,
							callback : function() {
								loading.close();
								self.hide();
							}
						})
					}
				},
				expandDetail : function(row) {
					row.expanded = !row.expanded;
					this.$refs["resultTable"].toggleRowExpansion(row,
							row.expanded);
				},
				showResult : function(rtn) {
					this.initResult(rtn);
					this.resultVisible = true;
				},
				initResult : function(rtn) {
					this.resultData = rtn.result;
				},
				optLabel : function(opt) {
					return "新增";
				},
				showError : function(error) {
					var h = this.$createElement;
					this.$msgbox({
						title : "错误",
						message : h("div", {
							attrs : {
								"class" : "error-info"
							}
						}, [ h("p", null, error) ]),
						showConfirmButton : false
					})
				}
			}
		})
	}

	var importUrl = Com_Parameter.ContextPath
			+ "eop/basedata/eop_basedata_material/eopBasedataMaterial.do?method=importMaterial";

	var submitFile = function(params) {
		$.ajax({
			type : "POST",
			url : importUrl,
			data : params.formData,
			contentType : false,
			processData : false,
			success : function(rtn) {
				if (params.callback) {
					params.callback();
				}
				document.getElementById("fileUploader").value = null;
				topic.publish("list.refresh");
				if (rtn.success) {
					/*
					 * commonExport.importerVue.$notify({ title : "成功", message :
					 * "导入成功", type : 'success', duration : 1500 })
					 */
				} else {
					var errorStr = rtn.error;
					if (errorStr.length > 160) {
						errorStr = errorStr.substring(0, 160);
					}
					/*
					 * commonExport.importerVue.$notify({ title : "导入失败",
					 * message : "", type : 'error', duration : 1000 })
					 */
				}
				commonExport.importerVue.showResult(rtn);
			}
		})
	}

	var openImporter = function() {
		if (commonExport.importerVue) {
			$("#importer").show();
			commonExport.importerVue.show();
		}
	}

	module.exports.init = init;
	module.exports.commonExport = commonExport;
	module.exports.openImporter = openImporter;
})