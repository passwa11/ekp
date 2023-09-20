define( [ "dojo/_base/declare","dojo/_base/lang", "dojo/query", "dojo/dom-construct", "mui/form/_CategoryBase", "dojo/request", "mui/util"],
		function(declare, lang, query, domConstruct, _CategoryBase, request,util) {
			window.ORG_TYPE_ORG = 0x1; // 机构
			window.ORG_TYPE_DEPT = 0x2; // 部门
			window.ORG_TYPE_POST = 0x4; // 岗位
			window.ORG_TYPE_PERSON = 0x8; // 个人
			window.ORG_TYPE_GROUP_CATE = 0x1000; // 群组分类
			window.ORG_TYPE_GROUP = 0x10; // 群组
			window.ORG_TYPE_ROLE = 0x20;
			window.ORG_TYPE_ORGORDEPT = window.ORG_TYPE_ORG | window.ORG_TYPE_DEPT; // 机构或部门
			window.ORG_TYPE_POSTORPERSON = window.ORG_TYPE_POST | window.ORG_TYPE_PERSON; // 岗位或个人
			window.ORG_TYPE_ALLORG = window.ORG_TYPE_ORGORDEPT | window.ORG_TYPE_POSTORPERSON; // 所有组织架构类型
			window.ORG_TYPE_ALL = window.ORG_TYPE_ALLORG | window.ORG_TYPE_GROUP; // 所有组织架构类型+群组
			window.ORG_FLAG_AVAILABLEYES = 0x100; // 有效标记
			window.ORG_FLAG_AVAILABLENO = 0x200; // 无效标记
			window.ORG_FLAG_AVAILABLEALL = window.ORG_FLAG_AVAILABLEYES | window.ORG_FLAG_AVAILABLENO; // 包含有效和无效标记
			window.ORG_FLAG_BUSINESSYES = 0x400; // 业务标记
			window.ORG_FLAG_BUSINESSNO = 0x800; // 非业务标记
			window.ORG_FLAG_BUSINESSALL = window.ORG_FLAG_BUSINESSYES | window.ORG_FLAG_BUSINESSNO; // 包含业务和非业务标记
			
			var addressMixin = declare("mui.address.AddressMixin", null, {
				type : window.ORG_TYPE_ALL,

				isMul : false,
				
				jsURL: '/hr/organization/mobile/resource/js/address/address_sgl.js!',
				// templURL : "mui/address/address_sgl.jsp" ,

				buildRendering : function() {
					this.inherited(arguments);
				},

				_setIsMulAttr:function(mul){
					this._set('isMul' , mul);
					if(this.isMul){
						this.jsURL = '/hr/organization/mobile/resource/js/address/address_mul.js!';
						// this.templURL =  "mui/address/address_mul.jsp";
					}else{
						this.jsURL = '/hr/organization/mobile/resource/js/address/address_sgl.js!';
						// this.templURL =  "mui/address/address_sgl.jsp";
					}
				},
				
				afterSelect:function(evt){
					var url="/sys/organization/sys_organization_recent_contact/sysOrganizationRecentContact.do?method=addContacts&contactIds="+evt.curIds;
					request.get(util.formatUrl(url));
				},
			});
			var exports = {
					address : function(mulSelect, idField, nameField, selectType,
							action) {
						var addressObj = new _CategoryBase();
						addressObj.isMul = mulSelect == true ? true : false;
						addressObj.jsURL = (mulSelect == true ? "/hr/organization/mobile/resource/js/address/address_mul.js!" : "/hr/organization/mobile/resource/js/address/address_sgl.js!");
						// addressObj.templURL = (mulSelect == true ? "mui/address/address_mul.jsp" : "mui/address/address_sgl.jsp");
						addressObj.key = idField?idField:"_address_default";
						var idObj = null;
						var nameObj = null;
						if(idField)
							idObj = query("[name='" + idField + "']")[0];
						if(nameField)
							nameObj = query("[name='" + nameField + "']")[0];
						if(idObj)
							addressObj.curIds = idObj.value;
						if(nameObj)
							addressObj.curNames = nameObj.value;
						if (selectType != null) {
							addressObj.type = selectType;
						} else {
							addressObj.type = window.ORG_TYPE_ALL;
						}
						 
						addressObj.afterSelect = function(obj) {
							if(idObj)
								idObj.value = obj.curIds;
							if(nameObj)
								nameObj.value = obj.curNames;
							if (action) {
								action(obj);
							}
						};
						addressObj.eventBind();
						addressObj._selectCate();
					}
				};
		return lang.mixin(addressMixin, exports);
	});