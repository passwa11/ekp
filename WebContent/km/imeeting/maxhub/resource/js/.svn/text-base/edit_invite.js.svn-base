require(['dojo/dom', 'dojo/dom-construct', 'dojo/html', 'dojo/on', 'dojo/topic', 'dijit/registry',
         'mhui/device/jssdk', 'dojo/dom-construct','dojo/query','mui/qrcode/QRcode','dojo/ready','mui/dialog/Tip',"mui/util",'dojo/_base/xhr'], 
		function(dom, domCtr, html, on, topic, registry, jssdk,domConstruct,query,qrcode,ready,Tip,util,xhr){

	ready(function() {
		
		setInterval(function(){
			registry.byId("attenPersons").reload()}, 3000);
		
	});
	
	window.submit=function(cb){
		var domList = document.getElementsByClassName("mhuiAddressItemName");
		var res = [].slice.call(domList);
		var ids="";
		for(var item in res) {
			if(ids==""){
				ids = res[item].getAttribute('data-dojo-id');
			}else{
				ids += ";"+res[item].getAttribute('data-dojo-id');
			}
		}
		document.getElementById("attendIds").value=ids;
		
		xhr.post({
			url: document.saveForm.action,
			form: document.saveForm,
			load: function(res) {
				cb(res);
			},
			error: function(err){
				cb(err);
			}
		});
		
	}
	
	window.genQRCode = function(){
		var scanUrl = location.origin + dojoConfig.baseUrl + "km/imeeting/km_imeeting_main/kmImeetingMain.do?method=mhuAdd&key=mhuKmImeetingInvite&fdId="+fdId+"&t=" + new Date().getTime();
		domConstruct.empty("qrcode");
		
		var obj = qrcode.make({
			url : scanUrl,
			width: 300,
			height:300
			
		});
		var qrcodeMain = query('#qrcode');
		domConstruct.place(obj.domNode,qrcodeMain[0],'first'); 
	}
	genQRCode();
	
});