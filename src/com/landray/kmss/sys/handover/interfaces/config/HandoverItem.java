package com.landray.kmss.sys.handover.interfaces.config;

public class HandoverItem {

	private String item;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	private String itemMessageKey;

	public String getItemMessageKey() {
		return itemMessageKey;
	}

	public void setItemMessageKey(String itemMessageKey) {
		this.itemMessageKey = itemMessageKey;
	}

	private IHandoverHandler handler;

	public IHandoverHandler getHandler() {
		return handler;
	}

	public void setHandler(IHandoverHandler handler) {
		this.handler = handler;
	}

	public HandoverItem() {

	}

	public HandoverItem(String item, String itemMessageKey,
			IHandoverHandler handler) {
		super();
		this.item = item;
		this.itemMessageKey = itemMessageKey;
		this.handler = handler;
	}

}
