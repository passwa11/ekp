define([
  "dojo/_base/declare",
  "mui/i18n/i18n!:home.hello",
  dojoConfig.baseUrl + "sys/mportal/mobile/config.jsp"
], function(declare, msg, config) {
  return declare("sys.mportal.SliderHeaderMixin", null, {
	buildRendering : function() {
		this.headerType = config.headerType;
	    this.inherited(arguments);
	},
		
	buildTmpl: function() {
      this.headerType = config.headerType
      if (config.headerType == "1") {
        this.tmpl =
          '<div class="muiHeaderType1_box">' +
          '	<div class="mui_ekp_portal_title_info">' +
          '		<div class="mui_ekp_portal_title_info_headicon left" style="background: url(\'' +
          this.imgUrl +"&times="+ new Date().getTime() +
          "')\">" +
          "		</div>" +
          '		<ul class="mui_ekp_portal_title_info_txt left">' +
          '			<li class="mui_ekp_portal_minor_info muiFontSizeS muiFontColorMuted line-cut">' +
          "				" +
          this.userName +
          "			</li>" +
          '			<li class="mui_ekp_portal_main_info muiFontSizeM muiFontColorInfo line-cut">' +
          "				" +
          msg["home.hello"] +
          "			</li>" +
          "		</ul>" +
          '		<div data-dojo-type="sys/mportal/mobile/header/SliderHeaderSearchBtn" data-dojo-props="searchHost:\'' +
          config.searchHost +
          '\'" class="mui_ekp_portal_title_info_search right">' +
          '			<i class=""></i>' +
          "		</div>" +
          "	</div>" +
          "</div>"
      } else {
        this.tmpl =
          "<div>" +
          '	<div class="mui_ekp_portal_title_info_sec_container" data-dojo-type="sys/mportal/mobile/HeaderRobot" data-dojo-props="itEnabled:\'' +
          config.itEnabled +
          "',logo:'" +
          this.logo +
          "',itUrl:'" +
          config.itUrl +
          "',searchHost:'" +
          config.searchHost +
          "'\">" +
          "	</div>" +
          "</div>"
      }

      this.inherited(arguments)
    }
  })
})
