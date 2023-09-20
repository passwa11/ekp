/**
 * 移动列表视图的排序行组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		topic = require('lui/topic'),
		selectUnion = require('sys/modeling/base/mobile/resources/js/selectUnion');
	var modelingLang = require("lang!sys-modeling-base");
	var OrderGenerator = base.Component.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
			this.container = cfg.container || null;
			this.storeData = cfg.data || {};
			this.fieldWgt = null;
		},
		
		draw : function($super, cfg){
			$super(cfg);
			var rowIndex = this.container.find("tr.orderbyTr").length+1;
			var $tr = $("<tr />").appendTo(this.container);
			$tr.addClass("orderbyTr");
			this.element = $tr;
			var self = this;
			var html = "";
			html += "<td><div class='model-edit-view-oper' data-lui-position='fdOrderBy-"+(rowIndex-1)+"' onclick=switchSelectPosition(this,'right')>";
			//头部
			html += "<div class='model-edit-view-oper-head'>";
			html += "<div class='model-edit-view-oper-head-title'><div onclick='changeToOpenOrClose(this)'><i class='open'></i></div><span>"+modelingLang['listview.sort.item']+"<span class='title-index'>"+(rowIndex-1)+"</span></span></div>";
			html += "<div class='model-edit-view-oper-head-item'><div class='del' onclick='updateRowAttr(0,null,this);delTr(this,\"orderby\");updateRowAttr()'><i></i></div><div class='down' onclick='moveTr(1,this,\"orderby\");updateRowAttr(1,null,this)'><i></i></div>" +
                "<div class='up' onclick='moveTr(-1,this,\"orderby\");updateRowAttr(-1,null,this)'><i></i></div>" +
                "<div className='up sortableIcon' ><i></i></div>" +
                "</div>";
			html += "</div>";
			//内容
			html += "<div class='model-edit-view-oper-content'>";
			html += "<ul class='list-content'>";
			html += "</ul>";
			html += "</div></div></td>";
			$tr.append(html);
			//字段
			html = "<li class='model-edit-view-oper-content-item field first-item'><div class='item-title'>"+modelingLang['relation.field']+"</div></li>";
			$tr.find("ul.list-content").eq(0).append(html);
			$field = $tr.find("ul.list-content").find("li.field").eq(0);
			var $fieldTd = $("<div class='item-content' />").appendTo($field);
			this.fieldWgt = new selectUnion({container: $fieldTd,parent:this,options:listviewOption.baseInfo.modelDict,type:"order",value:self.storeData.field});
			this.fieldWgt.draw();
			//排序
			html = "<li class='model-edit-view-oper-content-item last-item'><div class='item-title'>"+modelingLang['modelingAppVersion.fdOrder']+"</div>";
			html += "<div class='item-content'><select  name='"+ self.randomName +"_fdOrderType' type='checkbox' class='inputsgl' style='width:100%'>";
			html += "<option value='asc'>"+modelingLang['modelingAppListview.fdOrderType.asc']+"</option>";
			html += "<option value='desc'>"+modelingLang['modelingAppListview.fdOrderType.desc']+"</option>";
			html += "</select></div></li>";
			$tr.find("ul.list-content").eq(0).append(html);
			// 初始化已有值
			if(self.storeData.orderType){
				$tr.find("select[name='"+ self.randomName +"_fdOrderType']").val(self.storeData.orderType);
			}
			//更新角标
			var index = this.container.find("> tbody > tr").last().find(".title-index").text();
			this.container.find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
			//更新向下的图标
			this.container.find("> tbody > tr").last().prev("tr").find("div.down").show();
			$tr.find("div.down").hide();
			//修改默认标题
			var fieldId =$tr.find("div.select_union").find("select").eq(0).val();
			var fieldText = $tr.find("div.select_union").find("select").eq(0).find("option[value='"+fieldId+"']").text();
			text = fieldText;
			fieldId =$tr.find("div.select_union").find("select").eq(1).val();
			fieldText = $tr.find("div.select_union").find("select").eq(1).find("option[value='"+fieldId+"']").text();
			if(fieldText){
				text += "|"+fieldText;
			}
			$tr.find(".model-edit-view-oper-head-title span").html(text);
		},

		getKeyData : function(){
			var keyData = {};
			keyData.field = this.fieldWgt.getFieldValue();
			keyData.fieldType = this.fieldWgt.getFieldType();
			keyData.text = this.fieldWgt.getFieldText();
			keyData.orderType = this.element.find("select[name='"+ this.randomName +"_fdOrderType'] option:selected").val();
			return keyData;
		}
	})
	
	module.exports = OrderGenerator;
	
})