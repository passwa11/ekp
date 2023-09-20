define([
	"dojo/_base/declare",
	 "mui/dialog/Tip",
	 "mui/form/validate/Validation",
	 "mui/i18n/i18n!:errors.sensitive.word.warn",
	 "mui/i18n/i18n!kms-multidoc:kmsMultidoc",
	 "dojo/topic", 
	 "dijit/registry",
	 "dojo/query",
	 "dojo/request",
	 "dojo/promise/all",
	 "mui/util",
	], function(declare,Tip, Validation, msg, lang,topic,registry,query,request,all,util) {

	
	
	
	return declare("kms.multidoc.edit", null,{
		validateUrl:'/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=checkAddSubject&fdId=!{fdId}&docSubject=!{docSubject}&cateId=!{cateId}',

		senstiveValidateUrl:'/sys/profile/sysCommonSensitiveConfig.do?method=getIsHasSensitiveword',

		init: function(data){
			this.addValidation();
			var self = this
			window.knowledge_submit = function() {
				Com_Submit.ajaxBeforeSubmit = function(formObj) {
					self.authorChange(formObj);
				}
				var fdId =  document.getElementsByName("fdId")[0].value;
				var docSubject = document.getElementsByName("docSubject")[0].value;
				var docCategoryId = document.getElementsByName("cateId")[0].value;
				var url = util.formatUrl(util.urlResolver(self.validateUrl, {"fdId" : fdId,"docSubject" : docSubject,"cateId": docCategoryId}));
				request.get(url, {
					 headers: {'Accept': 'application/json'},
		             handleAs: 'json'
				}).response.then(function(data) {
					if(!data || !data['text']){
						Tip.fail({"text" : lang['mui.return.failure']});
						return ;
					}
					var json = JSON.parse(data['text']);
					var fdIsExist = json['fdIsExist'];
					if(fdIsExist == true){
						Tip.fail({"text" : lang['kmsMultidoc.isExist']});
						return ;
					}

					// 敏感词过滤
					var promiseAll = checkSenstiveWords();
					promiseAll.then(function (results) {
						// console.log("results=>", results)
						// console.log("v1=>", results[0].flag);
						// console.log("v2=>", results[1].flag);
						// console.log("v3=>", results[2].flag);

						if(results[0].flag == true){
							Tip.fail({
								"text": msg["errors.sensitive.word.warn"]
									.replace("{0}", lang["kmsMultidoc.kmsMultidocKnowledge.docSubject"])
									.replace("{1}", '<span style="color:#cc0000">' + results[0].senWords + '</span>')
							});
							return ;
						}
						if (results[1].flag == true) {
							Tip.fail({
								"text": msg["errors.sensitive.word.warn"]
									.replace("{0}", lang["kmsMultidocKnowledge.fdDescription"])
									.replace("{1}", '<span style="color:#cc0000">' + results[1].senWords + '</span>')
							});
							return;
						}
						if (results[2].flag == true) {
							Tip.fail({
								"text": msg["errors.sensitive.word.warn"]
									.replace("{0}", lang["kmsMultidoc.kmsMultidocKnowledge.docContent"])
									.replace("{1}", '<span style="color:#cc0000">' + results[2].senWords + '</span>')
							});
							return;
						}

						Com_Submit(document.forms[0], 'save');
					}, function () {
						Tip.fail({"text" : lang['mui.return.failure']});
					})

				}, function(data) {
					Tip.fail({"text" : lang['mui.return.failure']});
				});
			}
			window.checkSenstiveWords = function () {
				var docSubject = document.getElementsByName("docSubject")[0].value;
				var fdDescription = document.getElementsByName("fdDescription")[0].value;
				var docContent = document.getElementsByName("docContent")[0].value;
				console.log("docSubject=>", docSubject);
				console.log("fdDescription=>", fdDescription);
				console.log("docContent=>", docContent);

				var suburl = util.formatUrl(self.senstiveValidateUrl);
				var subdata = {"content": encodeURIComponent(docSubject), formName: "kmsMultidocKnowledgeForm"};
				var docSubjectPromise = request.post(suburl, {data: subdata, method: 'POST', handleAs: 'json'});

				var desurl = util.formatUrl(self.senstiveValidateUrl);
				var desdata = {"content": encodeURIComponent(fdDescription), formName: "kmsMultidocKnowledgeForm"};
				var fdDescriptionPromise = request.post(desurl, {data: desdata, method: 'POST', handleAs: 'json'});

				var conurl = util.formatUrl(self.senstiveValidateUrl);
				var condata = {"content": encodeURIComponent(docContent), formName: "kmsMultidocKnowledgeForm"};
				var docContentPromise = request.post(conurl, {data: condata, method: 'POST', handleAs: 'json'});

				return all([docSubjectPromise, fdDescriptionPromise, docContentPromise])
			}
			window.changeAuthorType = function(value, e) {
					self.changeAuthorType(value, e);
			}
		},
		authorChange: function (formObj) {
			var authorType = document.getElementsByName("authorType")[0].value;
			if(authorType == "2") {
				document.getElementsByName("docAuthorId")[0].value = null;
				document.getElementsByName("docAuthorName")[0].value = null;
				$("#authorsArrary").remove();
			} else {
				document.getElementsByName("outerAuthor")[0].value = null;
			}
		},
		addValidation: function () {
			var vali = new Validation();
			var valifunc = function(v,e,o) {
				v = v.trim();
				if (v.indexOf("；")==-1 
						&& v.indexOf("，")==-1 
						&& v.indexOf(",")==-1
						&& v.indexOf(" ")==-1 
						&& v.indexOf("-")==-1 
						&& v.indexOf("_")==-1 ) {
					return true;
				}else {
					return false;
				}
			}
			vali.addValidator("checkName", lang['kmsMultidoc.validateOutAuthorFormat'], valifunc);
		},
		changeAuthorType: function(value, e) {
			document.getElementById("innerAuthor").style.display = "none";
			document.getElementById("outerAuthor").style.display = "none";
			if (value == 1) {
				query("#innerAuthor div[data-dojo-type='mui/form/Address']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: true,
					});
				});
				query("#outerAuthor div[data-dojo-type='mui/form/Input']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: false,
					});
				});
				document.getElementById("innerAuthor").style.display = "block";
				var fdOrgId = document.getElementsByName("fdDocAuthorList[0].fdOrgId");
				var fdAuthorFag = document.getElementsByName("fdDocAuthorList[0].fdAuthorFag");
				fdOrgId[0].setAttribute('validate', 'required');			
				fdAuthorFag[0].setAttribute('validate', 'required');
				
			}
			if (value == 2) {
				query("#outerAuthor div[data-dojo-type='mui/form/Input']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: true,
					});
				});
				query("#innerAuthor div[data-dojo-type='mui/form/Address']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: false,
					});
				});
				document.getElementById("outerAuthor").style.display = "block";
				var fdOrgId = document.getElementsByName("fdDocAuthorList[0].fdOrgId");
				var fdAuthorFag = document.getElementsByName("fdDocAuthorList[0].fdAuthorFag");
				fdOrgId[0].setAttribute('validate', '');			
				fdAuthorFag[0].setAttribute('validate', '');				
			}
		},
		/**
		*将部门和岗位修改为作者的部门和岗位
		*/
		changeAuthodInfo :function(value, e) {
			if(value) {
				var authorId = value;
				var authors=authorId.split(";");
				var limit = 15;
				if(authors.length>limit){
					Tip.fail({"text" : lang['kmsMultidoc.selectAuthor.DataVolume']});
				    var values = e.curIds.split(";");
				    var names = e.curNames.split(";");
				    var val = "";
				    var nam = "";
				    for(var i=0;i<limit;i++){
				    	val += values[i]+";";
				    	nam += names[i]+";";
				    }
				    var vl = val.length - 1 <=0?0:val.length - 1;
				    var nl = nam.length - 1 <=0?0:nam.length - 1;
				    val = val.substring(0,vl);
				    nam = nam.substring(0,nl);
					setTimeout(function(){
					 	e.set("value", val);
					 	e.set("curIds", val);
					 	//curNames时开始渲染;
					 	e.set("curNames", nam);
					},250) 
					document.getElementById("authorsArrary").innerHTML = document.getElementById("authorsArrary").innerHTML;
				}else{
					var htmlVL="";
					for(var i=0;i<authors.length;i++){
						htmlVL+="<tr><td><input class='fdOrgIds' type='hidden' name='fdDocAuthorList["+i+"].fdOrgId' value='"+authors[i]+"'><input type='hidden' class='fdAuthorFags' name='fdDocAuthorList["+i+"].fdAuthorFag' value='"+i+"'></td></tr>";
					}
					document.getElementById("authorsArrary").innerHTML = htmlVL;
				}
			}
		}
	});
});