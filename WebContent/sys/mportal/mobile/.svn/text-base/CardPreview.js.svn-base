define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "./Card",
  "./TabCard",
  "dojo/request",
  "mui/util"
], function(
  declare,
  lang,
  WidgetBase,
  Contained,
  Container,
  Card,
  TabCard,
  request,
  util
) {
  var content = declare(
    "sys.mportal.CardPreview",
    [WidgetBase, Container, Contained],
    {      
      cardId: "",

      loadCardById:"/sys/mportal/sys_mportal_card/sysMportalCard.do?method=loadCardById&cardId=!{cardId}",

      buildRendering: function() {
        this.inherited(arguments); 
        this.getCartDate();
      },
      
      //获取卡片数据
      getCartDate: function(){ 
    	  var self = this;
          var url = util.urlResolver(this.loadCardById, {
        	  cardId: this.cardId
          })
          var promise = request.get(util.formatUrl(url), {
            headers: {
              Accept: "application/json"
            },
            handleAs: "json"
          })
          promise.response.then(function(data) {
            if (data.data){
            	self.render(data.data)
            } 
          })
      },
      
      render: function(data) {   	  
    	  _item = this.createListItem(this.parseConfig(data));
          this.addChild(_item)
      },
      
      parseConfig: function(item) {
        return lang.mixin(
          {
            close: false
          },
          item
        )
      },
       
      createListItem: function(item) {
        if (item.configs.length <= 1) return new Card(item)
        else return new TabCard(item)
      }

    }
  )

  return content
})
