define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var dialog = require("lui/dialog");
	var base = require("lui/base");
	var env = require("lui/util/env");
	var strutil = require('lui/util/str');
	var topic = require("lui/topic");
	
	// 矩阵页签对象
	var MatrixPanel = base.Container.extend({
		initProps: function($super, _config) {
			this.config = _config || {};
			if(!this.config.version) {
				this.config.version = "V1";
			}
			this.version = this.config.version;
			if(!this.config.id) {
				this.config.id = "lui_matrix_panel_content_" + this.version;
			}
			
			// 初始化内置变量
			this.seqTab = $("#matrix_seq_table_" + this.version + " tbody");
			this.dataTab = $("#matrix_data_table_" + this.version + " tbody");
			this.optTab = $("#matrix_opt_table_" + this.version + " tbody");
			this.page = 1;
			this.pageSize = 20;
			this.seq = 1;
			
			// 数据
			this.result = this.config.result || {};
			
			$super(_config);
		},
		render: function($super, obj) {
			var self = this;
			// 全选
			$("#matrix_seq_checkbox_" + this.version).click(function() {
				self.seqTab.find("[name=List_Selected]:checkbox").prop("checked", this.checked);
			});
			$("#matrix_seq_table_" + this.version).on("change", "[name=List_Selected]:checkbox", function() {
				var isAll = true;
				self.seqTab.find("[name=List_Selected]:checkbox").each(function(i, n) {
					if(!n.checked) {
						isAll = false;
						return false;
					}
				});
				$("#matrix_seq_checkbox_" + this.version).prop("checked", isAll);
			});
			// 监听分页事件
			topic.subscribe('paging.changed', function(evt) {
				if(evt) {
					// 保存常量数据
					self.dataTab.find("td").each(function(i, n) {
						var td = $(n), input = td.find("input");
						if(input.length == 1) {
							var val = input.val();
							self.updateData(td, val, val);
						}
					});
					for(var i=0; i<evt.page.length; i++) {
						if(evt.page[i].key == "pageno") {
							page = evt.page[i].value[0];
							break;
						}
					}
					self.showData(page);
				}
			});
		},
		// 显示内容标题
		showTitle: function() {
			var self = this;
			if(window.conditionalTitle) {
				var tr = self.dataTab.find("tr:eq(0)");
				// 条件
				for(var i=0; i<window.conditionalTitle.length; i++) {
					var con = window.conditionalTitle[i];
					tr.append("<th class=\"lui_matrix_td_normal_title lui_maxtrix_condition_th\">" + con + "</th>");
				}
				// 结果
				for(var i=0; i<window.resultTitle.length; i++) {
					var res = window.resultTitle[i];
					tr.append("<th class=\"lui_matrix_td_normal_title lui_maxtrix_result_th\">" + res + "</th>");
				}
				// 分组
				if(window.cateTitle && window.cateTitle.length > 0) {
					tr.append("<th class=\"lui_matrix_td_normal_title\">" + window.cateTitle + "</th>");
				}
			}
		},
		// 填充数据
		showData: function(page) {
			var self = this;
			if(self.result && self.result.datas) {
				for(var key in self.result.datas) {
					if(self.result.datas.length < 1) {
						continue;
					}
					self.seqTab.find("tr:gt(0)").remove();
					self.dataTab.find("tr:gt(0)").remove();
					self.optTab.find("tr:gt(0)").remove();
					var start = 0;
					var end = self.pageSize;
					if(page > 1 && self.result.datas.length > ((page -1) * self.pageSize)) {
						start = (page -1) * self.pageSize;
						end = self.pageSize + start;
					}
					var idx = 0;
					for(var i=start; i<end; i++) {
						if(i >= self.result.datas.length) {
							break;
						}
						self.addLine(self.result.datas[i], idx, page);
						self.setData(self.result.datas[i], idx, page);
						self.showError(self.result.datas[i], idx, page);
						idx++;
					}
				}
			}
		},
		// 删除一行
		delData: function(elem) {
			var self = this;
			var idx = $(elem.parentNode).parent().parent().prevAll().length;  
			self.seqTab.find("tr:eq("+idx+")").remove();
			self.dataTab.find("tr:eq("+idx+")").remove();
			self.optTab.find("tr:eq("+idx+")").remove();
			self.result.datas.splice(idx - 1, 1);
			// 重新分页
			self.showData(self.page);
		},
		// 新增一行
		addLine: function(data, idx, page) {
			var self = this;
			var hasBr = false;
			// 内容行
			var dataTr = [];
			// 内容
			dataTr.push("<tr style=\"height: 75px;\">");
			for(var i=0; i<data.dataRow.length; i++) {
				var orgType = data.dataRow[i].fdType == 'org' ? 'ORG_TYPE_ORG' : data.dataRow[i].fdType == 'dept' ? 'ORG_TYPE_DEPT' : data.dataRow[i].fdType == 'post' ? 'ORG_TYPE_POST' : data.dataRow[i].fdType == 'person' ? 'ORG_TYPE_PERSON' : data.dataRow[i].fdType == 'group' ? 'ORG_TYPE_GROUP' : '';
				var cls = data.dataRow[i].isResult ? "lui_maxtrix_result_td" : "lui_maxtrix_condition_td";
				var _width = "100px";
				if(data.dataRow[i].isResult) {
					_width = "70px";
				}
				dataTr.push("<td class=\"" + cls + "\">");
				if(data.dataRow[i].fdMainDataType == 'sys') {
					dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
					dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + data.row + "]\">");
					dataTr.push("	<input type=\"text\" name=\"" + data.dataRow[i].fdFieldName + "[" + data.row + "]\" style=\"width:90%; min-width:" + _width + ";\" readonly=\"true\" class=\"inputsgl\">");
					dataTr.push("	<a href=\"#\" onclick=\"Dialog_MainData('" + data.dataRow[i].fdId + "[" + data.row + "]', '" + data.dataRow[i].fdFieldName + "[" + data.row + "]', '" + data.dataRow[i].fdName + "');\" class=\"selectitem\"></a>");
					dataTr.push("</div>");
				} else if(data.dataRow[i].fdMainDataType == 'cust') {
					dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
					dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + data.row + "]\">");
					dataTr.push("	<input type=\"text\" name=\"" + data.dataRow[i].fdFieldName + "[" + data.row + "]\" style=\"width:90%; min-width:" + _width + ";\" readonly=\"true\" class=\"inputsgl\">");
					dataTr.push("	<a href=\"#\" onclick=\"Dialog_CustData(false, '" + data.dataRow[i].fdId + "[" + data.row + "]', '" + data.dataRow[i].fdFieldName + "[" + data.row + "]', null, 'sysOrgMatrixMainDataService&id=" + data.dataRow[i].fdType + "', '" + data.dataRow[i].fdName + "');\" class=\"selectitem\"></a>");
					dataTr.push("</div>");
				} else if(data.dataRow[i].fdType == 'constant') {
					dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + data.row + "]\" subject=\"" + data.dataRow[i].fdName + "\" style=\"width:90%; min-width:100px;\" class=\"inputsgl\" onchange=\"constantChange(this);\" validate=\"maxLength(200)\">");
				} else if(data.dataRow[i].fdType == 'numRange') {
					// 数值区间
					dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + idx + "]\" style=\"width:30%; min-width:100px;\" class=\"inputsgl\" validate=\"number numRange\">");
					dataTr.push("<span> ~ </span>");
					dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + idx + "]_2\" style=\"width:30%; min-width:100px;\" class=\"inputsgl\" validate=\"number numRange\">");
				} else if(data.dataRow[i].fdType == 'dateRange') {
					// 日期区间
					dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + idx + "]\" style=\"width:30%; min-width:100px;\" class=\"inputsgl\" validate=\"number numRange\">");
					dataTr.push("<span> ~ </span>");
					dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + idx + "]_2\" style=\"width:30%; min-width:100px;\" class=\"inputsgl\" validate=\"number dateRange\">");
				} else if(data.dataRow[i].fdType == 'person_post') {
					hasBr = true;
					dataTr.push("<input type=\"hidden\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + data.row + "]\">");
					dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
					dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + data.row + "]_person\">");
					dataTr.push("	<input type=\"text\" name=\"" + data.dataRow[i].fdFieldName + "[" + data.row + "]_person\" style=\"width:90%;min-width:" + _width + ";\" readonly=\"true\" class=\"inputsgl\">");
					dataTr.push("	<a href=\"#\" onclick=\"Dialog_Address_Cust(false, '" + data.dataRow[i].fdId + "[" + data.row + "]_person', '" + data.dataRow[i].fdFieldName + "[" + data.row + "]_person', null, ORG_TYPE_PERSON, resultCheck2);\" class=\"personelement\"></a>");
					dataTr.push("</div>");
					dataTr.push("<br>");
					dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
					dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + data.row + "]_post\">");
					dataTr.push("	<input type=\"text\" name=\"" + data.dataRow[i].fdFieldName + "[" + data.row + "]_post\" style=\"width:90%;min-width:" + _width + ";\" readonly=\"true\" class=\"inputsgl\">");
					dataTr.push("	<a href=\"#\" onclick=\"Dialog_Address_Cust(false, '" + data.dataRow[i].fdId + "[" + data.row + "]_post', '" + data.dataRow[i].fdFieldName + "[" + data.row + "]_post', null, ORG_TYPE_POST, resultCheck2);\" class=\"postelement\"></a>");
					dataTr.push("</div>");
				} else if(data.dataRow[i].fdFieldName == "fd_cate_id") {
					dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
					dataTr.push("	<select name=\"fd_cate_id[" + data.row + "]\" onchange=\"updateCate(this);\" style=\"width: 100%;\">");
					if(self.result.cateMap) {
						for(var cate in self.result.cateMap) {
							dataTr.push("		<option value=\"" + cate + "\">" + self.result.cateMap[cate] + "</option>");
						}
					}
					dataTr.push("	</select>");
					dataTr.push("</div>");
				} else {
					var icon = data.dataRow[i].fdType;
					if(icon == 'org') {
						// 防止图标重复
						icon += '1';
					}
					dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
					dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + data.dataRow[i].fdId + "[" + data.row + "]\">");
					dataTr.push("	<input type=\"text\" name=\"" + data.dataRow[i].fdFieldName + "[" + data.row + "]\" style=\"width:90%; min-width:" + _width + ";\" readonly=\"true\" class=\"inputsgl\">");
					dataTr.push("	<a href=\"#\" onclick=\"Dialog_Address_Cust(" + data.dataRow[i].isResult + ", '" + data.dataRow[i].fdId + "[" + data.row + "]', '" + data.dataRow[i].fdFieldName + "[" + data.row + "]', null, " + orgType + ", dialogCallback);\" class=\"" + icon + "element\"></a>");
					dataTr.push("</div>");
				}
				dataTr.push("</td>");
			}
			dataTr.push("</tr>");
			self.dataTab.append(dataTr.join(""));

			// 序号行
			var seqTr = [];
			seqTr.push("<tr style=\"height: 75px;\">");
	        seqTr.push("<td><input type=\"checkbox\" name=\"List_Selected\" value=\"" + ((page -1) * self.pageSize + idx) + "\"></td>");
	        seqTr.push("<td name=\"matrix_data_seq\">" + data.row + "</td>");
			seqTr.push("</tr>");
			self.seqTab.append(seqTr.join(""));
			
			// 操作行
			var optTr = [];
			optTr.push("<tr style=\"height: 75px;\">");
			optTr.push("<td><span class=\"lui_text_primary\"><a href=\"javascript:;\" onclick=\"delData(this);\">"+Msg_Info.button_delete+"</a></span></td>");
			optTr.push("</tr>");
			self.optTab.append(optTr.join(""));
		},
		// 显示错误的数据
		showError: function(data, idx, page) {
			var self = this;
			var tr = self.dataTab.find("tr:eq(" + (idx + 1) + ")");
			for(var i=0; i<data.dataErr.length; i++) {
				var err = data.dataErr[i];
				var td = tr.find("td:eq(" + err.index + ")");
				td.append("<span class=\"lui_maxtrix_import_error\" title=\"" + err.msg + "\"></span>");
			}
		},
		// 填充正确的数据
		setData: function(data, idx, page) {
			var self = this;
			var tr = self.dataTab.find("tr:eq(" + (idx + 1) + ")");
			for(var i=0; i<data.dataSuc.length; i++) {
				var suc = data.dataSuc[i];
				var td = tr.find("td:eq(" + suc.index + ")");
				var fdType = data.dataRow[suc.index].fdType;
				if(fdType == "constant") {
					td.find("input").val(suc.name);
				} else if(fdType.indexOf('Range') > -1) {
					// 区间类型
					td.find("input[type=text]")[0].value = suc.name || "";
					td.find("input[type=text]")[1].value = suc.id || "";
				} else if(fdType == "person_post") {
					if(!suc.type) {
						continue;
					}
					var _types = suc.type.split(";");
					var _ids = suc.id.split(";");
					var _names = suc.name.split(";");
					var _json = {};
					for(var j=0; j<_types.length; j++) {
						if(_types[j] == "4") {
							td.find("input[type=hidden][name$=_post]").val(_ids[j]);
							td.find("input[type=text][name$=_post]").val(_names[j]);
							_json["post"] = {'id': _ids[j], 'name': _names[j]};
						} else if(_types[j] == "8") {
							td.find("input[type=hidden][name$=_person]").val(_ids[j]);
							td.find("input[type=text][name$=_person]").val(_names[j]);
							_json["person"] = {'id': _ids[j], 'name': _names[j]};
						}
					}
					td.find("input[type=hidden]").each(function(i, n) {
						if($(n).attr("name").indexOf("_") == -1) {
							$(n).val(JSON.stringify(_json));
							return false;
						}
					});
				} else if(fdType == "fd_cate_id") {
					td.find("select[name^=fd_cate_id]").val(suc.id);
				} else {
					td.find("input[type=hidden]").val(suc.id);
					td.find("input[type=text]").val(suc.name);
				}
				self.showSuccess(td);
			}
		},
		// 更新数据
		updateData: function(td, id, name) {
			var self = this;
			self.showSuccess(td);
			// 处理数据
			var row = td.parent().prevAll().length;
			var val = self.seqTab.find("tr:eq(" + row + ")").find("[type=checkbox]").val();
			var dataSuc = self.result.datas[val].dataSuc;
			var col = td.prevAll().length;
			var oldData;
			for(var i=0; i<dataSuc.length; i++) {
				if(dataSuc[i].index == col) {
					oldData = dataSuc[i];
					break;
				}
			}
			if(oldData) {
				oldData.name = name;
				oldData.id = id;
			} else {
				dataSuc.push({"name": name, "id": id, "index": col});
			}
		},
		// 处理提示标识
		showSuccess: function(td) {
			var self = this;
			var span = td.parent().find(".lui_maxtrix_import_error");
			span.removeClass("lui_maxtrix_import_error");
			span.addClass("lui_maxtrix_import_success");
		}
	});
	
	exports.MatrixPanel = MatrixPanel;
});
