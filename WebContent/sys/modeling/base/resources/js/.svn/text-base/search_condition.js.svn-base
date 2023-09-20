function emitResize() {
	if (window.dialog) {
		dialog.content.emit('resize', {
			height: document.body.scrollHeight
		});
	} else if (window.frameElement != null && window.frameElement.tagName == "IFRAME") {
		window.frameElement.style.height = document.getElementsByTagName('center')[0].offsetHeight + 70 + "px";
	} else {
		return;
	}
	setTimeout(emitResize, 200);
}

Com_AddEventListener(window, 'load', doSearch);
function doSearch(){
	CommitSearch();
	emitResize();
}