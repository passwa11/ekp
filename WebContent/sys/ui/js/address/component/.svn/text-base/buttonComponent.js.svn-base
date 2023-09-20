/**
 * 地址本底部按钮栏组件
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	var topic = require('lui/topic');
	var toolbar = require('lui/toolbar');
	var lang = require('lang!sys-ui');
	var orglang = require('lang!sys-organization');
	var constant = require('lui/address/addressConstant');
	var dialog = require('lui/dialog');
	
	var ButtonComponent =  base.Container.extend({
		
		selectList : [],
		
		initProps : function($super, cfg){
			this.parent = cfg.parent,
			this.callback = this.parent.config.callback;
			this.params = this.parent.params;
			this.settingPersonal = cfg.settingPersonal;
			this.buttonCfg = {
				btnOk:{
					text : lang['ui.dialog.button.ok']
				},
				btnClear:{
					text : lang['ui.dialog.button.cancelSelect'],
					style : 'lui_toolbar_btn_gray'
				},
				btnCance:{
					text : lang['ui.dialog.button.cancel'],
					style : 'lui_toolbar_btn_gray'
				}
			};
			this.startup();
		},
		startup : function(){
			var self = this;
			this.buttonsContainer = $('<div class="lui_address_buttons_container"/>');
			//是否显示设置个人地址本
			if(this.settingPersonal){
				var settingContainer = $('<div class="lui_address_buttons_settingPersonal"/>').appendTo(this.buttonsContainer);
				settingContainer.append($('<a />')
										.text( orglang['sysOrgPersonAddressType.modifyMyAddress'] )
											.attr('href', Com_Parameter.ContextPath + 'sys/person/setting.do?setting=sys_organization_address_info')
											 .attr('target','_blank'));
			}
			this.buttonOk = this.buildButton(this.buttonCfg.btnOk);
			//bind event
			this.buttonOk.onClick = function(){
				self.handleElementSave.apply(self);
			};
	
			this.buttonCacel = this.buildButton(this.buttonCfg.btnCance);
			this.buttonCacel.onClick = function(){
				if(window.$dialog!=null){
					window.$dialog.hide();
				}else{
					window.close();
				}
			};
			
			this.element.append(this.buttonsContainer);
			topic.subscribe( constant.event['ADDRESS_ELEMENT_UPDATE'],this.handleElementUpdate,this);
			topic.subscribe( constant.event['ADDRESS_ELEMENT_SAVE'],this.handleElementSave,this);
		},
		handleElementUpdate : function(evt){
			this.selectList = evt.selectList || [];
			var length = this.selectList.length;
			//刷新确定按钮
			if( this.buttonOk && this.buttonOk.textContent){
				var textContent = this.buttonOk.textContent;
				if( !textContent.attr('baseText')){
					textContent.attr('baseText',textContent.text());
				}
				var baseText = textContent.attr('baseText');
				if(length){
					textContent.text(baseText + '(' +  length + ')');
				}else{
					textContent.text(baseText);
				}
				
			}
		},
		handleElementSave : function(){
			var isHandleFinished = false;
			var loadingDialog = null;
			// 执行时间超过500毫秒需要显示loading加载遮罩层
			setTimeout(function(){
				if(!isHandleFinished){
					loadingDialog = dialog.loading();					
				}
			},500);
			
			var contactIds = '',self = this;
			for(var i =0;i<this.selectList.length;i++){
				contactIds += this.selectList[i].id+';';
			}
			if(contactIds.length > 0){
				contactIds = contactIds.substring(0,contactIds.length - 1);
				this.setDeptName(contactIds);
			}
			var kmssData = new KMSSData();
			kmssData.AddHashMap({
				contactIds : contactIds
			});
			kmssData.SendToUrl(Com_Parameter.ContextPath+'sys/organization/sys_organization_recent_contact/sysOrganizationRecentContact.do?method=addContacts',function(){
				if(self.callback){
					self.callback(self.selectList);
				}
				// 关闭loading遮罩层
				if(loadingDialog){
					loadingDialog.hide();
				}
				isHandleFinished = true;
			});
		},
		setDeptName : function(deptIds){
			var self = this;
			var kmssData = new KMSSData();
			kmssData.UseCache = false;
			kmssData.AddHashMap({
				deptIds : deptIds
			});
			kmssData.SendToUrl(Com_Parameter.ContextPath + 'sys/organization/sys_org_element/sysOrgElement.do?method=getDeptName',function(http_request){
				var responseText = http_request.responseText;
				var data = eval("(" + responseText + ")");
				for(var i=0; i<self.selectList.length; i++) {
					for(var j=0; j<data.length; j++) {
						if(self.selectList[i].id == data[j].id) {
							self.selectList[i].name = data[j].name;
							break;
						}
					}
				}
			},false);
		},
		buildButton : function(cfg){
			var button = toolbar.buildButton({
				text : cfg.text,
				styleClass : cfg.style
			});
			this.buttonsContainer.append(button.element.css({
				margin : '0 10px',
				display : 'inline-block'
			}));
			button.draw();
			return button;
		}
	});
	
	module.exports = ButtonComponent;
	
});