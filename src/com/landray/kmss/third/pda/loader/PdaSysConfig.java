package com.landray.kmss.third.pda.loader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.util.StringUtil;

public class PdaSysConfig implements Serializable {

	private String urlPrefix;

	private String countUrl;
	
	private List<PdaConfig> configs = new ArrayList<PdaConfig>();

	public List<PdaConfig> getConfigs() {
		return configs;
	}

	public void addConfigs(PdaConfig pdaConfig) {
		this.configs.add(pdaConfig);
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	public String getCountUrl() {
		return countUrl;
	}

	public void setCountUrl(String countUrl) {
		this.countUrl = countUrl;
	}

	public void combine(PdaSysConfig pdaCfg) throws Exception {
		if (StringUtil.isNotNull(pdaCfg.getUrlPrefix())) {
			configs.addAll(pdaCfg.getConfigs());
		}
	}
}
