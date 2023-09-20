package com.landray.kmss.third.weixin.mutil.model.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class WxMenu {
	@JSONField(name = "button")
	private List<WxMenuButton> buttons = new ArrayList();

	public List<WxMenuButton> getButtons() {
		return buttons;
	}

	public void setButtons(List<WxMenuButton> buttons) {
		this.buttons = buttons;
	}

	public static class WxMenuButton {
		private String type;
		private String name;
		private String key;
		private String url;
		@JSONField(name = "sub_button")
		private List<WxMenuButton> subButtons = new ArrayList();

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public List<WxMenuButton> getSubButtons() {
			return subButtons;
		}

		public void setSubButtons(List<WxMenuButton> subButtons) {
			this.subButtons = subButtons;
		}

	}
}
