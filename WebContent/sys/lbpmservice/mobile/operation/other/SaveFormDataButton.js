define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dojo/_base/lang",
  "dojo/_base/window",
  "dojo/dom-construct",
  "dojo/query",
  "dojox/mobile/TransitionEvent",
  "dojox/mobile/ViewController",
  "dojox/mobile/viewRegistry",
  "mui/util",
  "mui/dialog/Dialog",
  "mui/tabbar/TabBarButton",
  "dojo/Deferred",
  "mui/history/listener",
  "mui/i18n/i18n!sys-lbpmservice",
  "mui/i18n/i18n!sys-lbpmservice:lbpmSetting.approve",
  "mui/dialog/Tip",
  "dojo/html",
  "dojo/dom",
  "dojo/dom-style",
  "dijit/registry",
  "dojo/topic"
], function(
  declare,
  array,
  lang,
  win,
  domConstruct,
  query,
  TransitionEvent,
  ViewController,
  viewRegistry,
  util,
  Dialog,
  TabBarButton,
  Deferred,
  listener,
  Msg,
  Msg2,
  Tip,
  html,
  dom,
  domStyle,
  registry,
  topic
) {
  var button = declare(
    "sys.lbpmservice.mobile.operation.other.SaveFormDataButton",
    [TabBarButton],
    {
      label:Msg['mui.button.savedraft'],
      processId: "",

      modelName: "",

      modelId: "",

      buildRendering: function() {

        this.inherited(arguments)
      },

      _onClick: function(evt) {
          var canStartProcessId = query("[id='sysWfBusinessForm.canStartProcess']");
          if(typeof canStartProcessId !='undefined' && canStartProcessId.length>0) {
              canStartProcessId[0].value = "false";
          }
          if(lbpm){
              lbpm.saveFormData = true;
          }
          this.beforeSubmit();
          if(this.docStatus === '11'){
              Com_Submit(document[this.formName]||document.forms[0], 'update',null,{saveDraft:true});
          }
          if(this.docStatus === '20'){
              var registryId = registry.byId("process_review_button");
              
              registryId._onClick(evt);
          }
      },

      postCreate: function() {
        this.inherited(arguments)
      },

      startup: function() {
        this.inherited(arguments)
      },
      beforeSubmit:function(){
            if(this.backTo == null){
                  view = viewRegistry.getEnclosingView(this.domNode);
                  view = viewRegistry.getParentView(view) || view;
                  this.backTo = view.id;
              }
          var element = registry.byId(this.backTo);
          if(element){
              element.notValRequired = true;
          }
          var operationMethodsGroup = query("[id='operationMethodsGroup']");
          if(typeof operationMethodsGroup !='undefined' && operationMethodsGroup.length>0) {
              operationMethodsGroup[0].value = "handler_pass:通过";
          }
      }

    });
  return button;
})
