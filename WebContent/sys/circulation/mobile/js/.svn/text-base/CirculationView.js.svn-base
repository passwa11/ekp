define([
  "dojo/_base/declare",
  "dojo/topic",
  "mui/view/DocView",
  "dijit/registry"
], function(
  declare,
  topic,
  DocView,
  registry
) {
  return declare(
    "sys.circulation.CirculationView",
    [
     DocView
    ],
    {
      startup: function() {
        this.inherited(arguments);
      },
      buildRendering: function() {
        this.inherited(arguments);
      },
      postCreate: function() {
		this.inherited(arguments);
		topic.subscribe('/mui/form/valueChanged',function(widget,args){
			if(widget && widget.name=="fdRegular"){
				 var main_s = document.getElementById("main_s");
				 var main_m = document.getElementById("main_m");
				 var dialog_main_s = registry.byId("receivedCirCulator_s");
				 var dialog_main_m = registry.byId("receivedCirCulator_m");
				if(args.value == '1'){
					main_m.style.display = "none";
					main_s.style.display = "block";
					dialog_main_m._set('validate', '');
					dialog_main_s._set('validate', 'required');
					document.getElementById("spreadScopeTr").style.display="none";
					document.getElementById("spreadScopeTr2").style.display="none";
				}else{
					main_m.style.display = "block";
					main_s.style.display = "none";
					dialog_main_s._set('validate', '');
					dialog_main_m._set('validate', 'required');
					document.getElementById("spreadScopeTr").style.display="table-row";
					document.getElementById("spreadScopeTr2").style.display="none";
				}
			}
			
			if(widget && widget.name=="fdSpreadScope"){
				var spreadScope = registry.byId("spreadScope");
				if(args.value == '2'){
					spreadScope._set('validate', 'required');
					document.getElementById("OtherSpreadScope").style.display="block";
				}else{
					spreadScope._set('validate', '');
					document.getElementById("OtherSpreadScope").style.display="none";
				}
			}
		});
		this.subscribe("mui/switch/statChanged",function(widget,val){
			if(widget && widget.property=="fdAllowSpread"){
				var spreadScope = registry.byId("spreadScope");
				if(val){
					document.getElementById("spreadScopeTr2").style.display="table-row";
					var fdSpreadScope = registry.byId("fdSpreadScope");
					if(fdSpreadScope.value == '2'){
						spreadScope._set('validate', 'required');
					}
				}else{
					document.getElementById("spreadScopeTr2").style.display="none";
					spreadScope._set('validate', '');
				}
			}
		});
		this.subscribe("/mui/Category/valueChange",function(widget,evt){
			if(widget && (widget.idField=="receivedCirCulatorIds_m" || widget.idField=="receivedCirCulatorIds_s") && evt){
				document.getElementsByName("receivedCirCulatorIds")[0].value = evt.curIds;
			}
		})
      }
    }
  )
})
