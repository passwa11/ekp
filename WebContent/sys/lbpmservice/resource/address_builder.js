/**
 * 流程用地址簿生成器
 */

(function(lbpm){
	
	if (!lbpm.address)
		lbpm.address = {};
	
	var dialog_address_args = ['mulSelect', 'idField', 'nameField', 'splitStr', 'selectType', 'action', 'startWith', 'isMulField', 'notNull', 'winTitle', 'treeTitle', 'exceptValue', 'deptLimit'];
	var pda_address_args = ['idField', 'nameField', 'mulSelect', 'splitStr', 'selectType', 'addAction', 'deleteAction'];
	
	function idNameHtml(field) {
		return "id=" + field + " name=" + field + "";
	}
	function idNameKeyHtml(field) {
		return "id=" + field + " name=" + field +" key="+field+"";
	}
	function wrapArg(arg, name) {
		if (arg == null) {
			return ('null');
		}
		else if (name && (name.indexOf('Action') > -1 || name.indexOf('action') > -1 || name.indexOf('selectType') > -1)) {
			return arg.toString();
		}
		else if (Object.prototype.toString.apply(arg) === '[object Array]') {
			var arrayArg = [];
			for (var i = 0; i < arg.length; i ++) {
				arrayArg.push(wrapArg(arg[i]));
			}
			return "["+arrayArg.join(',')+"]";
		}
		else if (typeof arg == 'string') {
			return "'"+arg+"'";
		}
		return arg.toString();
	}
	function argHtml(args, options) {
		var html = [];
		for (var i = 0; i < args.length; i ++) {
			var name  = args[i];
			var opt = options[name];
			html.push(wrapArg(opt, name));
		}
		return html.join(',');
	}
	function alertText(options) {
		return options.alertText ? options.alertText : "";
	}
	
	function dialogAddress(options) {// orgelement
		var idValue = options['idValue'] || '';
		var nameValue = options['nameValue'] || '';
		var width = options['width'] || '70%';
		var html = "<input type='hidden' alertText='"+alertText(options)+"' value='"+Com_HtmlEscape(idValue)+"' "+idNameKeyHtml(options.idField)+">"
			+ "<input type='text' "+idNameKeyHtml(options.nameField)+" alertText='' readonly class='inputSgl' style='width:"+width+";' value='"+Com_HtmlEscape(nameValue)+"'>";
		var hrefObj = "<a href='#' ";
		if (options.onclick) {
			hrefObj +="onclick=\""+options.onclick+" \"";
		} else {
			hrefObj +="onclick=\"Dialog_Address("+argHtml(dialog_address_args, options)+"); \"";
		}
		hrefObj += ">" + options.text + "</a>";
		html += hrefObj;
		if (options.notNull) {
			html += "&nbsp;<span class='txtstrong'>*</span>";
		}
		return html;
	}
	
	function dialogAddressNew(options) {
		var idValue = options['idValue'] || '';
		var nameValue = options['nameValue'] || '';
		var width = options['width'] || '70%';
		var html = "<div class='inputselectsgl' style='width:"+width+";' ";
		if (options.onclick) {
			html +="onclick=\""+options.onclick+" \"";
		} else {
			html +="onclick=\"Dialog_Address("+argHtml(dialog_address_args, options)+"); \"";
		}
		html += ">";
		html += "<input type='hidden' alertText='"+alertText(options)+"' value='"+Com_HtmlEscape(idValue)+"' "+idNameKeyHtml(options.idField)+">"
			+ "<div class='input'><input type='text' "+idNameKeyHtml(options.nameField)+" alertText='' readonly class='inputSgl' style='width:100%;' value='"+Com_HtmlEscape(nameValue)+"'></div>";
		html += "<div class='orgelement' ></div>";
		html += "</div>";
		if (options.notNull) {
			html += "&nbsp;<span class='txtstrong'>*</span>&nbsp;";
		}
		return html;
	}
	
	function pdaAddress(options) {
		var idValue = options['idValue'] || '';
		var nameValue = options['nameValue'] || '';
		var html = "<input type='hidden' alertText='"+alertText(options)+"' value='"+Com_HtmlEscape(idValue)+"' "+idNameKeyHtml(options.idField)+">"
		+ "<input type='hidden' "+idNameKeyHtml(options.nameField)+" alertText='' value='"+Com_HtmlEscape(nameValue)+"'>";
		html += "<input type='button' class='selectStyle mui mui-org' onclick=\"Pda_Address("+argHtml(pda_address_args, options)+"); \">";
		return html;
	}
	function is_pda() {
		return (window.Pda_Address || window.dojo) ? true : false;
	}
	
	lbpm.address.is_pda = is_pda;
	
	lbpm.address.html_build = function(options) {
		if (is_pda()) {
			return pdaAddress(options);
		}
		if (window.LUI)
			return dialogAddressNew(options);
		else 
			return dialogAddress(options);
	};
	
	
})(lbpm);
