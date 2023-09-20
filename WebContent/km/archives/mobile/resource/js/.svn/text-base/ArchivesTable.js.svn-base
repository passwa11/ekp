define([ "dojo/_base/declare", "dojo/_base/lang", "dojo/parser", "dijit/_WidgetBase", "dijit/_Contained",
	"dijit/_Container", "dojo/window", "dojo/dom", "dojo/_base/array",
	"dojo/dom-style", "dojo/dom-class", "dojo/dom-construct", "dojo/query", 
	"dojo/topic", "dojo/on", "dojo/request", "dojo/touch", 'mui/dialog/Tip', 'dojo/mouse', 'dojo/dom-attr', './util',"mui/i18n/i18n!km-archives"], 
	
	function(declare, lang, parser, WidgetBase, Contained, Container, win, 
			dom, array, domStyle, domClass, domCtr, query, topic, on, request, 
			touch, Tip, mouse, domAttr, util,Msg) {
	
	return declare("km.archives.mobile.js.ArchivesTable", [ WidgetBase, Contained, Container ], {

		selectedArchs: [],
	    
		postCreate: function(){
			this.inherited(arguments);
			
			this.tbody = this.domNode.getElementsByTagName('tbody')[0];
			
			this.bindEvents();
		},

		bindEvents: function(){
			
			var ctx = this;
			
			topic.subscribe('km/archives/selectedarch/get', function(cb) {
				cb && cb(ctx.selectedArchs);
			});
			
			topic.subscribe('km/archives/selectedarch/init', function(archs){
				
				ctx.selectedArchs = archs;
				ctx.renderselectedArchs();
			});
			
			topic.subscribe('km/archives/selectedarch/res', function(archs){
				
				var i = 0, j = 0, l = (ctx.selectedArchs || []).length, _l = (archs || []).length, res = [];
				
				for(i; i < l; i++) {
					for(j; j < _l; j++) {
						if(ctx.selectedArchs[i].fdId == archs[j].fdId) {
							res.push(ctx.selectedArchs[i]);
							break;
						}
					}
				}
				
				for(i = 0; i < _l; i++) {

					var flag = true;
					var arch = archs[i];
					
					for(j = 0; j < l; j++) {

						if(arch.fdId == ctx.selectedArchs[j].fdId) {
							flag = false;
							break;
						}
						
					}
					
					if(flag) {
						res.push({
							fdId: arch.fdId,
							fdBorrowerId: query("[name='fdBorrowerId']")[0].value,
							fdStatus: '0',
							title: arch.title,
							categoryName: arch.categoryName,
							fdValidityDate: arch.fdValidityDate!=''?arch.fdValidityDate:Msg['mui.kmArchivesMain.fdValidityDate.forever'],
							fdAuthorityRange: arch.fdDefaultRange,
							fdReturnDate:''
						});
					}
					
				}
				
				ctx.selectedArchs = res;
				ctx.renderselectedArchs();
				
			});
			
			topic.subscribe('km/archives/selectedarch/add', function(newArch){
				
				var i, l = ctx.selectedArchs.length;
				
				for(i = 0; i < l; i++){
					
					if(ctx.selectedArchs[i].fdId == newArch.fdId){
						Tip.fail({
							text: '已选取档案'
						});
						return;
					}
					
				}
				
				ctx.selectedArchs.push({
					fdId: newArch.fdId,
					fdBorrowerId: query("[name='fdBorrowerId']")[0].value,
					fdStatus: '0',
					title: newArch.title,
					categoryName: newArch.categoryName,
					fdValidityDate: newArch.fdValidityDate!=''?newArch.fdValidityDate:Msg['mui.kmArchivesMain.fdValidityDate.forever'],
					fdAuthorityRange: newArch.fdDefaultRange,
					fdReturnDate:''
				});
				
				ctx.renderselectedArchs();
				
			});
			
			topic.subscribe('km/archives/selectedarch/remove', function(idx){
				ctx.selectedArchs.splice(idx, 1);
				ctx.renderselectedArchs();
			});
			
		},
		renderselectedArchs: function(){
			
			var ctx = this;

			array.forEach(query('.selectedArchRow'), function(row, idx){
				row.remove();
			});
			
			array.forEach(ctx.selectedArchs, function(arch, idx){
				
				var archRow = domCtr.create('tr', {
					className: 'selectedArchRow'
				}, ctx.tbody);
				
				var index = domCtr.create('td', {
					innerHTML: idx + 1
				}, archRow);
				
				domCtr.create('input',{
					'type': 'hidden',
					'value': arch.fdId,
					'name': 'fdBorrowDetail_Form[' + idx + '].fdArchId'
				},index);
				
				var fdBorrowerIdHidden = domCtr.create('input',{
					'type': 'hidden',
					'value': arch.fdBorrowerId,
					'name': 'fdBorrowDetail_Form[' + idx + '].fdBorrowerId'
				},index);
				
				domClass.add(fdBorrowerIdHidden, 'detailBorrows');
				
				domCtr.create('input',{
					'type': 'hidden',
					'value': arch.fdStatus,
					'name': 'fdBorrowDetail_Form[' + idx + '].fdStatus'
				},index);
				
				var archATd = domCtr.create('td', {
					innerHTML: '<a style="color:#007aff; font-size: 1.4rem;">'+util.htmlDecode(arch.title)+'</a>'
				}, archRow);
				
				on(archATd,'click',function(){
					window.location.href = dojoConfig.baseUrl+'km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId='+arch.fdId+'&_mobile=1';
				});

				domCtr.create('td', {
					innerHTML: util.htmlDecode(arch.categoryName)
				}, archRow);				
				
				domCtr.create('td', {
					innerHTML: arch.fdValidityDate
				}, archRow);
				
				var typeCheckboxTD = domCtr.create('td', {
					style: 'padding: 0.6rem 1rem; text-align: left;'
				},archRow);
				
				
				ctx.generateCheckBoxGroup(typeCheckboxTD, 'fdBorrowDetail_Form[' + idx + '].fdAuthorityRange', arch.fdAuthorityRange);
				
				var typeDateTD = domCtr.create('td', {
				},archRow);
				
				ctx.generateDateTime(typeDateTD, 'fdBorrowDetail_Form[' + idx + '].fdReturnDate',arch.fdReturnDate,arch.fdValidityDate);
				
				var removeTD = domCtr.create('td', {
				}, archRow);
				
				domCtr.create('span', {
					className: 'selectedArchRowAction_remove',
					innerHTML: '&times;'
				}, removeTD);
				
				on(removeTD, 'click', function(){
					topic.publish('km/archives/selectedarch/remove', idx);
				});
				
			});
			
			parser.parse(ctx.tbody).then(function(e){
				topic.publish("/mui/list/resize");
			});

		},
		
		generateCheckBoxGroup : function(parentNode, name, value){
			var store = [];
			var _copy = {text:Msg['mui.kmArchivesConfig.fdDefaultRange.copy'],value:'copy'};
			var _download = {text:Msg['mui.kmArchivesConfig.fdDefaultRange.download'],value:'download'};
			var _print = {text:Msg['mui.kmArchivesConfig.fdDefaultRange.print'],value:'print'};
			if(typeof(value) != 'undefined' && value != ''){
				if(value.indexOf("copy") > -1){
					_copy.checked = true;
				}
				if(value.indexOf("download") > -1){
					_download.checked = true;
				}
				if(value.indexOf("print") > -1){
					_print.checked = true;
				}
			}
			store.push(_copy);store.push(_download);store.push(_print);
			store = JSON.stringify(store);
			domCtr.create("div", {
				"data-dojo-type": "mui/form/CheckBoxGroup",
				"class":"authorityRange",
				"data-dojo-props": lang.replace('edit:"{edit}",name:"{name}",value:"{value}",store:{store}',{
					edit: "true",
					name: name,
					value: value,
					store:store
				})
			}, parentNode);
		},
		
		generateDateTime : function(parentNode, name, value, fdValidityDate){
			if(fdValidityDate == '永久')
				fdValidityDate = '';
			domCtr.create("div", {
				"data-dojo-type": "mui/form/DateTime",
				"data-dojo-mixins": "mui/datetime/_DateTimeMixin",
				"data-dojo-props": lang.replace('valueField:"{valueField}",name:"{name}",value:"{value}",required:{required},validate:"{validate}",subject:"{subject}"',{
					valueField: name,
					name: name,
					value: value,
					required : true,
					validate : 'required datetime after returnDateValidator(' + fdValidityDate + ')',
					subject :  Msg['mui.kmArchivesDetails.fdReturnDate']
				})
			}, parentNode);
		}
		
	});
		
})