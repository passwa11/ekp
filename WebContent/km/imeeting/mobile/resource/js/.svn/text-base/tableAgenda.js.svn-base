define( [ "dojo/_base/declare",
          "dijit/_WidgetBase",
          "dijit/_Contained",
      		"dijit/_Container",
          "dijit/_TemplatedMixin",
          "dojo/dom-style",
          "dojo/_base/lang",
          "dojo/dom-construct",
          "mui/form/Input",
          "mui/form/Address",
          "dojo/on",
          "dojo/topic",
          "mui/util",
          "mui/i18n/i18n!km-imeeting:kmImeetingAgenda"
          ],
		function(declare,WidgetBase,Contained, Container,TemplateHTML,domStyle,lang,domCtr,input,address,on,topic,util,msg) {
	
	return declare("km.mobile.resource.js.tableAgenda", [WidgetBase,Contained, Container], {
		rowIndex:0,
		operation:"",//操作
		docSubject:"",//
		docReporter:"",
		docReporterTime:"",
		attachmentName:"",
		docRespons:"",
		operaBtn:"删除",
		order:msg['kmImeetingAgenda.page.serial'],
		attachmentSubmitTime:"",
		data:'',
		buildRendering : function() {
			this.inherited(arguments);
			this.renderDom();
			this.initData();
		},
		 
		initData:function(){
			if(this.data&&this.data.length>0){
				for(var i = 0;i<this.data.length;i++){
					this.addRow(this.tableNode,this.data[i]);
				}
			}
		},
		addAddress:function(config){
			var _config = {};
			_config.idField = "kmImeetingAgendaForms["+this.rowIndex+"]."+config.idField;
			_config.nameField = "kmImeetingAgendaForms["+this.rowIndex+"]."+config.nameField;
			_config.type = config.type||'ORG_TYPE_PERSON';
			_config.curIds = config.idValue;
			_config.curNames = config.nameValue;
			var newAddress = new address(_config);
			newAddress.startup();
			return newAddress.domNode;
		},
		addInput:function(config){
			if(!config.name){
				throw "input's name is null";
				return ;
			}
			var _config = {};
			_config.name ="kmImeetingAgendaForms["+this.rowIndex+"]."+config.name;
			_config.placeholder = config.placeHolder||' ';
			_config.required = config.required||false;
			_config.showStatus = config.showStatus||'edit';
			_config.validate = config.validate||"";
			_config.opt = config.opt||false;
			_config.className = config.className||"inputEdit";
			_config.value=config.value;
			//var inputNode = domCtr.create("input",{className:"inputEdit",name:name,required:isRequred?"required":''});
			var inputObj = new input(_config);

			inputObj.startup();
			
			if(config.width){
				inputObj.inputNode.style.width = config.width;
			}
			return inputObj.domNode;
		},
		addIndexSpan:function(index){
			return domCtr.create("span",{className:"orderId",innerHTML:index});
		},
		renderDom:function(){
			this.headerTitleDom = domCtr.create("p",{innerHTML:msg['kmImeetingAgenda.info'],className:"headerTitle"},this.domNode);
			this.tableNode = domCtr.create("table",{id:"table_doc",className:"muiAgendaNormal muiNormal",style:"min-width:100%;"},this.domNode);
			//设置标题行
			var str ="<td>"+this.operation+"</td>"+"<td>"+this.order+"</td>"+"<td>"+this.docSubject+"</td>"+"<td>"+this.docReporter+"</td>"+
			"<td>"+this.docReporterTime+"</td>"+"<td>"+this.attachmentName+"</td>"+"<td>"+this.docRespons+"</td>"+
			"<td>"+this.attachmentSubmitTime+"</td>";
			this.firstTr = this.tableNode.insertRow(0);
			this.firstTr.style.display="none";
			this.firstTr.innerHTML=str;
			//新增按钮
			this.btnDiv = domCtr.create("div",{id:"addRowBtn",innerHTML:msg['kmImeetingAgenda.operation.addDetail'],style:"display:block;"},this.domNode);
			//初始化按钮宽度
			this.btnDiv.style.width = (document.body.offsetWidth/10)+"rem";
	
			var self = this;
			this.addOnClick();
			topic.publish("changeTableScrollHeight",this.tableNode.offsetHeight+this.btnDiv.offsetHeight+this.headerTitleDom.offsetHeight+20);
		},
		addRow:function(tableDoc,defv){
			var self = this;
			if(this.firstTr.style.display==="none"){
				this.firstTr.style.display="table-row";
			}
			this.headerTitleDom.style.display = "block";
			var addBtn = domCtr.create("a",{className:"mui mui-plus btnplus"});
			var trNode = tableDoc.insertRow(this.rowIndex+1);
			var opBtn = trNode.insertCell(0);
			domCtr.place(this.removeRow(),opBtn,"first");
//			on(addBtn,"touchstart",function(){
//				self.addRow(self.tableNode);
//			});
			//domCtr.place(addBtn,opBtn,"last");
			trNode.insertCell(1).appendChild(this.addIndexSpan(this.rowIndex+1));
			//会议议题
			var subject = defv&&defv.docSubject
			trNode.insertCell(2).appendChild(this.addInput({name:"docSubject",required:true,validate:"required",value:subject}));
			//汇报人
			trNode.insertCell(3).appendChild(this.addAddress({idField:"docReporterId",nameField:"docReporterName",idValue:defv?defv.docReporterId:'',nameValue:defv?defv.docReporterName:''}));
			//汇报时间
			var ReporterTime =this.addInput({name:"docReporterTime",validate:"digits",width:"3.5rem",value:defv&&defv.docReporterTime});
			domCtr.place(document.createTextNode(msg['kmImeetingAgenda.min']),ReporterTime.querySelector(".muiInput"),"after");
			trNode.insertCell(4).appendChild(ReporterTime);
			//上会所需材料
			trNode.insertCell(5).appendChild(this.addInput({name:"attachmentName",value:defv&&defv.attachmentName}));
			//材料责任人
			trNode.insertCell(6).appendChild(this.addAddress({idField:"docResponsId",nameField:"docResponsName",idValue:defv?defv.docResponsId:'',nameValue:defv?defv.docResponsName:''}));
			//材料提交时间
			var attachTime = this.addInput({name:"attachmentSubmitTime",validate:"digits",width:"3.5rem",value:defv&&defv.attachmentSubmitTime});
			domCtr.place(document.createTextNode(msg['kmImeetingAgenda.attachmentSubmitTime.tip1']),attachTime.querySelector(".muiInput"),"before");
			domCtr.place(document.createTextNode(msg['kmImeetingAgenda.attachmentSubmitTime.tip2']),attachTime.querySelector(".muiInput"),"after");
		    trNode.insertCell(7).appendChild(attachTime);
		    this.rowIndex++;
		    topic.publish("changeTableScrollHeight",this.tableNode.offsetHeight+this.btnDiv.offsetHeight+self.headerTitleDom.offsetHeight+20);
		  
		},
		removeRow:function(){
			var removeNode = domCtr.create("a",{className:"del mui mui-close delRow",href:"javascript:void(0)"});
			var self = this;
			on(removeNode,"touchstart",function(e){
				var c = 0;
				var parent = e.target;
				while(c==0){
					
					if(parent.tagName.toLowerCase()==="tr"){
						c = 1;
					}else{
						parent = parent.parentNode;
					}
				}
				var tbody =self.tableNode.getElementsByTagName("tbody"); 
				try{
					if(tbody[0]){
						tbody[0].removeChild(parent);
					}else{
						self.tableNode.removeChild(parent);
					}
				}catch(e){
					console.log(e);
				}
				self.rowIndex--;
				self.reOrderRow();
				if(self.rowIndex===0){
					self.btnDiv.style.display="block";
					self.firstTr.style.display="none";
				}
				topic.publish("changeTableScrollHeight",self.tableNode.offsetHeight+self.btnDiv.offsetHeight+self.headerTitleDom.offsetHeight+20);
				
			});
			return removeNode;
		},
		addOnClick:function(){
			var self = this;
			on(self.btnDiv,"touchstart",function(){
				self.addRow(self.tableNode);
			});
		},
		reOrderRow:function(){
			var orderDomArr = this.tableNode.querySelectorAll(".orderId");
			orderDomArr.forEach(function(v,i){
				v.innerHTML = i+1;
			});
			var trArr = this.tableNode.querySelectorAll("tr");
			try{
				for(var i = 2;i<trArr.length;i++){
					var inputArr = trArr[i].querySelectorAll("input");
					for(var j = 0;j<inputArr.length;j++){
						if(inputArr[j].getAttribute("name")!=null){
							var temp = inputArr[j].getAttribute("name");
							 inputArr[j].setAttribute("name",temp.replace(/\[(.+?)\]/,"["+(i-2)+"]"));
						}
					}
					
				}
			}catch(e){
				console.log(e);
			}
		},
		isNotNull:function(obj){
			
		}
	});
});