define("kms/lservice/mobile/js/Role", [ "dojo/dom-construct",
		'dojo/_base/declare', "dojo/dom-class", "dijit/_WidgetBase",
		"mui/util", "mui/i18n/i18n!kms-lservice:lservice.role" ], function(
		domConstruct, declare, domClass, WidgetBase, util, msg) {

	return declare('kms.lservice.mobile.js.Role', [ WidgetBase ], {

		role : 'student',

		buildRendering : function() {

			this.inherited(arguments);

			domClass.add(this.domNode, 'muiLserviceRoles');

			this.buildRoles();

		},

		buildRole : function(role) {

			var roleNode = domConstruct.create('div', {
				class : 'muiLserviceRole'
			}, this.domNode);

			domConstruct.create('img', {
				src : util.formatUrl('/kms/lservice/mobile/style/img/' + role
						+ '.png')
			}, roleNode);

			domConstruct.create('p', {
				innerHTML : msg['lservice.role.' + role]
			}, roleNode);

			if (this.role == role) {

				domClass.add(roleNode, 'on');

				return;
			}

			this.connect(roleNode, 'click', function() {

				window.open(util.formatUrl('/kms/lservice/mobile/' + role
						+ '/index.jsp'), '_self');

			})

		},

		buildRoles : function() {

			this.buildRole('admin');
			this.buildRole('teacher');
			this.buildRole('student');

		}

	})

})