package com.landray.kmss.sys.handover.interfaces.config;

import java.util.List;

public interface IHandoverProvider {

	public List<HandoverItem> items() throws Exception;
}
