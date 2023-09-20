package com.landray.kmss.third.ekp.java.oms.in;

import org.json.simple.JSONObject;

import com.landray.kmss.sys.oms.in.interfaces.IOMSResultEcoSet;

public class EKPResultEcoSet extends EkpResultSet implements IOMSResultEcoSet {

	public EKPResultEcoSet(String stringJsonArray) {
		super(stringJsonArray);
	}
	
	@Override
    public JSONObject getObject() throws Exception {
		if (elmentJsonArray != null) {
			JSONObject jsonObject = (JSONObject) elmentJsonArray.get(index);
			index++;
			return jsonObject;
		}
		return null;
	}

}
