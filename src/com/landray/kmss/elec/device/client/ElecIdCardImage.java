package com.landray.kmss.elec.device.client;

import java.io.Serializable;

public class ElecIdCardImage implements Serializable {

	private static final long serialVersionUID = 1L;

	// 证件照名称
	private String imageName;

	// 证件照标识码
	private String imageCode;

	// 证件照数据
	private ImageData imageData;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public ImageData getImageData() {
		return imageData;
	}

	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
	}

	public class ImageData implements Serializable {

		private static final long serialVersionUID = 1L;

		String url;

		String base64;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getBase64() {
			return base64;
		}

		public void setBase64(String base64) {
			this.base64 = base64;
		}

	}
}
