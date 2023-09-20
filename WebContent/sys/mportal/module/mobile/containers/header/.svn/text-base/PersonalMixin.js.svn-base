/**
 * 个人头部
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/request",
  "mui/util",
  "mui/device/adapter",
  "mui/i18n/i18n!sys-mportal:sysMportalPage"
], function(declare, domConstruct, request, util, adapter,msg) {
  return declare("sys.mportal.module.header.PersonalMixin", null, {
    // 人员主键
    userId: dojoConfig.CurrentUserId,
    // 人员名称
    userName: dojoConfig.CurrentUserName,
    // 称呼
    address: "!{userName}",
    // 是否显示问候语
    showGreet: true,
    // 是否显示头像
    showImg: false,
    // 点击跳转链接
    href: "",
    // 头像链接
    imgUrl: "/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=!{userId}",
    // 是否显示个性签名
    showSignature: false,
    // 默认个性签名（用户未设置个性签名的情况下显示的默认签名内容）
    signature: "",
    // 用户自定义个性签名请求URL
    signatureUrl: "/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getSysZoneSignature",
    careMsg:{
    	"morning":  ""+msg['sysMportalPage.morning']+"",
    	"forenoon":  ""+msg['sysMportalPage.forenoon']+"",
    	"afternoon":  ""+msg['sysMportalPage.afternoon']+"",
    	"evening":  ""+msg['sysMportalPage.evening']+""
    },
    greeting: {
      "morning": [0, 8],
      "forenoon": [8, 12],
      "afternoon": [12, 18],
      "evening": [18, 24]
    },

    // 问候
    renderGreeting: function() {
      var hours = new Date().getHours()
      for (var key in this.greeting) {
        var ranges = this.greeting[key]
        if (hours >= ranges[0] && hours < ranges[1]) {
          return this.careMsg[key]
        }
      }
    },

    buildInfo: function() {
      var headerInfoContainerNode = domConstruct.create("div",{className: "muiModuleHeaderInfoContainer"}, this.headerNode)
      this.infoNode = domConstruct.create("div", {className: "muiModuleHeaderInfo"}, headerInfoContainerNode)

      // 显示头像
      if (this.showImg) {
    	var headerPersonIconNode = domConstruct.create("div", {className: "muiModuleHeaderPersonIcon"}, this.infoNode)
        domConstruct.create("img", {src: util.formatUrl(this.imgUrl.replace(/!{userId}/, this.userId))}, headerPersonIconNode )
      }
      
      // 显示跳转链接图标
      if (this.href) {
    	var headerPersonLinkNode = domConstruct.create("div", {className: "muiModuleHeaderLinkIcon"}, this.infoNode)
        var moreNode = domConstruct.create("i", {className: "fontmuis muis-to-right"},headerPersonLinkNode)
        this.connect(this.infoNode, "click", "onMoreClick")
      }

      if (this.userName) {
    	// 用户个性签名  
    	var personSignature = "";
    	    
        // 获取用户个性签名
        if(this.showSignature){
          // 发送同步请求获取用户自定义个性签名（如果用户未设置个性签名，则使用模块自定义传递的默认个性签名）
      	  request.get(util.formatUrl(this.signatureUrl),{handleAs:'json',sync:'false'}).then(function(data) {
      		 personSignature = (data&&data.signature) ? data.signature : this.signature;
      	  });  
        }
        
        // 创建 用户名（欢迎语）、个性签名 的载体DOM（注：有个性签名和无个性签名的场景下，显示样式有差异）
        var headerNameClass = "muiModuleHeaderName " + ( personSignature ? "hasPersonSignature" : "noPersonSignature" )
        var headerNameNode = domConstruct.create("div", {className: headerNameClass}, this.infoNode)
        
        // 显示用户名（欢迎语）
        var welcomeMessageHtml = "";
        var address = this.address.replace(/!{userName}/, this.userName)
        if (this.showGreet) {
        	welcomeMessageHtml += "<span>" + address +"</span><span>，" +this.renderGreeting() +"</span>"
        } else {
        	welcomeMessageHtml += "<span>" + address + "</span>"
        }
        domConstruct.create("div", {className: "muiModuleWelcomeMessage", innerHTML: welcomeMessageHtml}, headerNameNode)
        
        // 显示用户个性签名
        if(personSignature){
        	domConstruct.create("div", {className: "muiModulePersonSignature", innerHTML: personSignature}, headerNameNode)
        }
      }
      

      
    },

    onMoreClick: function() {
      var url = util.formatUrl(
        this.href.replace(/!{userId}/, this.userId),
        true
      )
      adapter.open(url, "_blank")
    }
  })
})