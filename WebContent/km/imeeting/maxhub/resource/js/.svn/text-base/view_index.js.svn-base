require(['dojo/topic', 'dojo/_base/array', 'dojo/dom', 'dojo/dom-class', 'dojo/dom-construct', 'dojo/dom', 'dojo/on', 'dijit/registry',
	'mhui/device/jssdk', 'mhui/dialog/Dialog', 'dojo/ready'], 

	function(topic, array, dom, domClass, domCtr, dom, on, registry, jssdk, Dialog, ready){
		window.refreshAgenda = function(e) {
			array.forEach(agendaListEventPrefixs || [], function(event) {
				topic.publish(event || '');
			});
		}
		
		window.refreshRelevant = function(e) {
			topic.publish('attachmentObject_attachment_refresh');
		}
		
		topic.subscribe('attachmentObject_attachment_count', function(count) {
			count = count || 0;
			var attachmentPanel = dom.byId('attachmentPanel');
			if(count < 1 && attachmentPanel) {
				domClass.add(attachmentPanel, 'mhui-hidden');
			}
		});
		
		
		window.refreshRecord = function(e) {
			topic.publish('attachmentObject_tmpAttachment_refresh');
		}
		
		topic.subscribe('attachmentObject_tmpAttachment_count', function(count) {
			count = count || 0;
			var openBoardPanel = dom.byId('openBoardPanel');
			if(count < 1 && openBoardPanel) {
				domClass.remove(openBoardPanel, 'mhui-hidden');
			} else {
				domClass.add(openBoardPanel, 'mhui-hidden');
			}
			
		});
		
		
		
		
		ready(function() {
			// TODO
		});
	}
);