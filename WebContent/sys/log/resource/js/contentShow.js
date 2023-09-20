define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var option = window.viewOption;
	
	var format = function() {
		var jsonStr = $("#contentShow").html();
		var json = JSON.parse(jsonStr);
		$("#contentShow").html(showJSON(json));
		$("#contentShow").show();
		//处理高度较高，需要隐藏的div
		seajs.use(['sys/log/resource/js/autoHide'], function(autoHide) {
			autoHide.init(100);
		});
	};

	/*
	* 主体内容展示
	*/
	function showJSON(json){
		if(json != ''){
			var divStyle = "";
			var contentShow = "<table class=\"tb_noborder\" width=100%>";
			var param = "";		//展示的param内容
			var message = "";	//展示的message内容
			var audit = "";	//展示的audit内容
			var deletes = "";	//展示的delete内容
			var find = "";	//展示的find内容
			var add = "";	//展示的add内容
			var update = "";	//展示的update内容
			$.each(json, function(i){
				var name = i;		//展示的名称
				if(i == "param"){
					name = option.lang.param;
					param += "<tr><td>" + showParam(this) + "</td></tr>";
					
				}else if(i == "find"){
					name = option.lang.find;
					find += "<tr><td>" + showList(this, i) + "</td></tr>";
					
				}else if(i == "delete"){
					name = option.lang.deletes;
					deletes += "<tr><td>" + showList(this, i) + "</td></tr>";
					
				}else if(i == "add"){
					name = option.lang.add;
					add += "<tr><td>" + showStructrue(this, i) + "</td></tr>";
					
				}else if(i == "update"){
					name = option.lang.update;
					update += "<tr><td>" + showStructrue(this, i) + "</td></tr>";
					
				}else if(i == "audit"){
					name = option.lang.audit;
					audit += "<tr><td>" + showAudit(this, i) + "</td></tr>";
					
				}else if(i == "message"){
					name = option.lang.message;
					message += "<tr><td>" + showMessage(this) + "</td></tr>";
				}
			});
			if(param)
				contentShow += param;
			if(message)
				contentShow += message;
			if(audit)
				contentShow += audit;
			if(deletes)
				contentShow += deletes;
			if(find)
				contentShow += find;
			if(add)
				contentShow += add;
			if(update)
				contentShow += update;
			contentShow += "</table>";
			return contentShow;
		}
		return "";
	}

	/*
	* 参数内容展示
	*/
	function showParam(obj, type){
		if(!obj){
			return "";
		}
		var content = "<table class=\"tb_noborder\" width=100%>";
		$.each(obj, function(i){
			content += "<tr>";
			content += "<td width=80px class=\"bold nowrap\">"+i+"</td>";
			content += "<td>";
			content += getV(this);
			content += "</td>";
			content += "</tr>";
		});
		
		content += "</table>";
		return content;
	}
	
	/*
	* 机制内容展示
	*/
	function showMechs(obj, type){
		if(!obj || $.isEmptyObject(obj)){
			return "";
		}
		var contentShow = "<table class=\"tb_normal\" width=100%>"; 
		$.each(obj, function(i){
			//主内容-一行展示
			var mechTr = "<tr>";
			//字段
			if(i == "attachment"){
				//附件机制
				mechTr += "<td width=15% class=\"td_normal_title\">"+option.lang.attachment+"</td>";
				mechTr += "<td width=85% class=\"break_all\">" + formatAttachments(this) + "</td>";
				
			}else if(this.fields){
				//其它机制（debug模式或非三员）
				mechTr += "<td width=15% class=\"td_normal_title\">"+i+"</td>";
				mechTr += "<td width=85% class=\"break_all\">"+showTable(this.fields, type)+"</td>";
			}
			mechTr += "</tr>";
			contentShow += mechTr;
		});
		contentShow += "</table>";
		return contentShow;
	}

	/*
	 * 附件格式化展示
	 */
	function formatAttachments(obj){
		if(obj['update'] || obj['delete']){
			var attachment = "<table class=\"tb_noborder\" width=100%>";
			$.each(obj, function(i){
				if(i == "update"){
					//新增/修改
					color = "green";
					type = option.lang.add + "/" + option.lang.update;
					
				}else if(i == "delete"){
					//删除
					color = "red";
					type = option.lang.deletes;
					
				}
				$.each(this, function(i){
					//文件类型
					attachment += "<tr style=\"color:" + color +"\">";
					attachment += "<td width=60px class=\"bold nowrap\">"+ type + "</td>";
					//文件数量
					attachment += "<td width=30px class=\"break_all\">" + this + option.lang.attUnit + "</td>";
					attachment += "<td class=\"break_all\">\"" + i + "\"</td>";
					attachment += "</tr>";
				});
			});
			attachment += "</table>";
			return attachment;
		}else{
			//无附件
			return option.lang.noAtt;
		}
	}
	
	/*
	* add与update的内容结构化展示
	*/
	function showStructrue(obj, showType){
		if(!obj){
			return "";
		}
		var contentShow = "<table class=\"tb_noborder\" width=100%>"; 
		$.each(obj, function(i){
			//主内容-一行展示
			var color = "";
			var type = "";
			if(showType == "add"){
				//新增
				color = "green";
				type = "\t(" + option.lang.add + ")";
				
			}else if(showType == "update"){
				//修改
				color = "blue";
				type = "\t(" + option.lang.update + ")";
			}
			var mainTable = "<table class=\"tb_noborder\" width=100%>";
			mainTable += "<tr style=\"color:" + color +"\">";
			mainTable += "<td width=60px class=\"bold nowrap\">"+(i+1)+ type + "</td>";
			mainTable += "<td width=250px>" + getV(this.fdId) + "</td>";
			mainTable += "<td class=\"break_all\">" + getV(this.displayName) + "</td>";
			mainTable += "</tr>";
			mainTable += "</table>";
			contentShow += mainTable;
			
			//字段
			if(this.fields){
				var filedTable = "<table class=\"tb_noborder\" width=100%>";
				filedTable += "<tr>";
				filedTable += "<td colspan=6 class=\"bold\">"+option.lang.fields+":</td>";
				filedTable += "</tr>";
				filedTable += "<tr>";
				filedTable += "<td colspan=6>"+showTable(this.fields, showType)+"</td>";
				filedTable += "</tr>";
				filedTable += "</table>";
				contentShow += filedTable;
			}
			
			//机制
			if(this.mechs && !$.isEmptyObject(this.mechs)){
				var mechsTable = "<table class=\"tb_noborder\" width=100%>";
				mechsTable += "<tr>";
				mechsTable += "<td colspan=6 class=\"bold\">"+option.lang.mechs+":</td>";
				mechsTable += "</tr>";
				mechsTable += "<tr>";
				mechsTable += "<td colspan=6>"+showMechs(this.mechs, showType)+"</td>";
				mechsTable += "</tr>";
				mechsTable += "</table>";
				contentShow += mechsTable;
			} 
		});
		return contentShow;
	}
	
	/*
	* 展示记录的message或error信息
	*/
	function showMessage(obj){
		var contentShow = "<table class=\"tb_normal\" width=100%>"; 
		$.each(obj, function(i){
			var messageKey = "";
			var color = "";
			if(i == "error"){
				messageKey = option.lang.errorMessage;
				color = "red";
			}else if(i == "info"){
				messageKey = option.lang.message;
			}
			contentShow += "<tr>";
			contentShow += "<td width=15% class=\"td_normal_title\" style=\"color: " + color + "\">"+messageKey+"</td>";
			contentShow += "<td width=85% class=\"break_all\"><pre style=\"white-space: pre-wrap;word-wrap: break-word;\">"+escape2Html(this)+"</pre></td>";
			contentShow += "</tr>";
		});
		contentShow += "</table>";
		return contentShow;
	}
	
	/*
	 * 转意符换成普通字符
	 */
	function escape2Html(str) {
		var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
		return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
	}
	
	/*
	* 审计的内容结构化展示
	*/
	function showAudit(obj){
		if(!obj){
			return "";
		}
		var contentShow = "<table class=\"tb_normal\" width=100%>"; 
		$.each(obj, function(i){
			//主内容-一行展示
			contentShow += "<tr>";
			contentShow += "<td width=15% class=\"td_normal_title valign_top\">"+option.lang.auditDisplay+"</td>";
			contentShow += "<td width=85%>"+getV(this.displayName)+"</td>";
			contentShow += "</tr>";
			
			//字段
			if(this.fields && this.fields['auditRecords'] && this.fields['auditRecords']['update']){
				contentShow += "<tr>";
				contentShow += "<td colspan=6 class=\"td_normal_title bold\" >"+option.lang.auditLog+"</td>";
				contentShow += "</tr>";
				contentShow += "<tr>";
				contentShow += "<td colspan=6>"+showList(this.fields['auditRecords']['update'])+"</td>";
				contentShow += "</tr>";
			}
		});
		contentShow += "</table>";
		return contentShow;
	}
	
	/*
	*以list列表形式展示（序号、ID、名称）
	*/
	function showList(obj, showType){
		if(!obj){
			return "";
		}
		var contentShow = "<table class=\"tb_noborder\" width=100%>"; 
		//内容
		$.each(obj, function(i){
			//主内容-一行展示
			var color = "";
			var type = "";
			if(showType == "delete"){
				//删除
				color = "red";
				type = "\t(" + option.lang.deletes + ")";
			}else if(showType == "find"){
				//查询
				type = "\t(" + option.lang.find + ")";
			}
			contentShow += "<tr style=\"color:" + color +"\">";
			contentShow += "<td width=60px class=\"bold nowrap\">"+(i+1) + type + "</td>";
			contentShow += "<td width=250px class=\"nowrap\">"+getV(this.fdId)+"</td>";
			contentShow += "<td class=\"break_all\">"+getV(this.displayName)+"</td>";
			contentShow += "</tr>";
		});
		contentShow += "</table>";
		return contentShow;
	}

	/*
	*以view表格形式展示（字段名/字段值）
	*/
	function showTable(obj, type){
		if(!obj){
			return "";
		}
		var tableBegin = "<table class=\"tb_normal\" width=100%>";
		var title = "";
		var content = "";
		var detailContent = "";
		//无标题
		$.each(obj, function(i){
			if(this['delete'] || this['add'] || this['update']){
				//明细列表的解析
				detailContent += "<tr>";
				//字段名
				detailContent += "<td width=15% class=\"td_normal_title valign_top\">"+i+"</td>";
				//字段值
				detailContent += "<td class=\"break_all\">"+showJSON(this)+"</td>";
				detailContent += "</tr>";
			}else{
				content += "<tr>";
				//字段名
				if(option.debug){
					//logdebug页面
					if(i.indexOf("@") != -1){
						content += "<td width=15% class=\"td_normal_title valign_top\">";
						var arr = i.split("@");
						if( arr.length > 1)
							content += arr[1];
						if( arr.length > 0)
							content += "<br><span style=\"color:gray\">(" + arr[0] + ")</span>";
						content += "</td>";
					}else{
						content += "<td width=15% class=\"td_normal_title valign_top\" style=\"color: red\">"+i+"</td>";
					}
				}else{
					content += "<td width=15% class=\"td_normal_title valign_top\">"+i+"</td>";
				}
				//字段值
				content += "<td class=\"break_all valign_top\">";
				//添加待隐藏样式
				content += "<div class=\"break_all pre_hide\">";
				//日志类型为“修改”
				if(type == "update"){
					if(this.showType == "oneToMany"){
						//一对多的角色分配以特殊格式展示
						content += getV(this, "update");
						
					}else{
						/*格式：
						 * (旧值) [隐藏]
						 * (新值) [隐藏]
						 */
						var filedTable = "<table class=\"tb_noborder\" width=100%>";
						filedTable += "<tr>";
						filedTable += "<td width=60px class=\"bold nowrap valign_top\">("+option.lang.fieldsOld+")</td>";
						filedTable += "<td class=\"break_all\">" + getV(this['old']) + "</td>";
						filedTable += "</tr><tr style=\"color:green\">";
						filedTable += "<td width=60px class=\"bold nowrap valign_top\">("+option.lang.fieldsNew+")</td>";
						filedTable += "<td class=\"break_all\">" + getV(this['new']) + "</td>";
						filedTable += "</tr>";
						filedTable += "</table>";
						content += filedTable;
					}
				}else{
					content += getV(this);
				}
				content += "</td>";
				content += "</tr>";
			}
		});
		var tableEnd = "</table>";
		var contentShow = (tableBegin + content + detailContent + tableEnd);
		return contentShow;
	}

	/*
	 * 多对多关联关系列表的展示
	 */
	function showOneToMany(value, showType){
		if(value.fdId instanceof Array){
			//内容列表展示
			var contentShow = "<table class=\"tb_noborder\" width=100%>"; 
			//内容
			for(var i=0; i< value.fdId.length; i++){
				var color = "";
				var type = "";
				if(showType == "update"){
					if(value.argument && value.argument[i] == "add"){
						//新增的
						color = "green";
						type = "\t(" + option.lang.add + ")";
					}else if(value.argument && value.argument[i] == "delete"){
						//移除的
						color = "red";
						type = "\t(" + option.lang.remove + ")";
					}else{
						//不变的
						type = "\t(" + option.lang.noChanged + ")";
					}
				}
				contentShow += "<tr style=\"color:" + color +"\">";
				contentShow += "<td width=60px class=\"bold nowrap\">"+(i+1)+ type + "</td>";
				contentShow += "<td width=250px>"+getV(value.fdId[i])+"</td>";
				contentShow += "<td class=\"break_all\">"+getV(value.displayName[i])+"</td>";
				contentShow += "</tr>";
			}
			contentShow += "</table>";
			return contentShow;
			
		}else{
			//内容一行展示
			var contentShow = "<table class=\"tb_noborder\" width=100%>"; 
			contentShow += "<tr>";
			contentShow += "<td width=\"250px\">"+getV(value.fdId)+"</td>";
			contentShow += "<td class=\"break_all\">"+getV(value.displayName)+"</td>";
			contentShow += "</tr>";
			contentShow += "</table>";
			return contentShow;
		}
	}
	
	/*
	* 格式化展示值
	*/
	function getV(value, showType){
		if(!value){
			return "";
			
		}else if(value.fdId){
			return showOneToMany(value, showType);
			
		}else if(typeof(value) == "object"){
			return JSON.stringify(value);
			
		}else{
			return value;
		}
	}
	
	module.exports.format = format;
});