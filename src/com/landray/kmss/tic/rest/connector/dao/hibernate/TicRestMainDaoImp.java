package com.landray.kmss.tic.rest.connector.dao.hibernate;

import com.landray.kmss.tic.core.common.dao.hibernate.TicCoreFuncBaseDaoImp;
import com.landray.kmss.tic.rest.connector.dao.ITicRestMainDao;

/**
 * Rest服务请尔方法配置数据访问接口
 */
public class TicRestMainDaoImp extends TicCoreFuncBaseDaoImp implements ITicRestMainDao {

	/*
	 * @Override public String add(IBaseModel modelObj) throws Exception {
	 * TicRestMain ticRestMain = (TicRestMain) modelObj;
	 * parseParams(ticRestMain); return super.add(ticRestMain); }
	 * 
	 * @Override public void update(IBaseModel modelObj) throws Exception {
	 * TicRestMain ticRestMain = (TicRestMain) modelObj;
	 * parseParams(ticRestMain); super.update(ticRestMain); }
	 * 
	 * private void parseParams(TicRestMain ticRestMain) {
	 * if(StringUtil.isNotNull(ticRestMain.getFdOriParaIn())) { JSONArray params
	 * = new JSONArray(); parseParams(params,ticRestMain.getFdOriParaIn());
	 * ticRestMain.setFdParaIn(params.toString()); }else {
	 * ticRestMain.setFdParaIn(null); }
	 * if(StringUtil.isNotNull(ticRestMain.getFdOriParaOut())) { JSONArray
	 * params = new JSONArray();
	 * parseParams(params,ticRestMain.getFdOriParaOut());
	 * ticRestMain.setFdParaOut(params.toString()); }else {
	 * ticRestMain.setFdParaOut(null); } }
	 * 
	 * private void parseParams(JSONArray params,String fdOriParam) {
	 * if(StringUtil.isNotNull(fdOriParam)) { JSONArray oriParams =
	 * JSONArray.fromObject(fdOriParam); for(int i=0;i<oriParams.size();i++) {
	 * JSONObject oriParam = oriParams.getJSONObject(i); JSONObject param = new
	 * JSONObject(); param.accumulate("name", oriParam.getString("name"));
	 * param.accumulate("title", oriParam.getString("title"));
	 * param.accumulate("type", oriParam.getString("type"));
	 * recursionParseData(param, oriParam.getString("children"));
	 * params.add(param); } }
	 * 
	 * }
	 * 
	 * private void recursionParseData(JSONObject param, String childern) {
	 * if(StringUtil.isNotNull(childern)) { JSONArray cp = new JSONArray();
	 * JSONArray array = JSONArray.fromObject(childern); for(int
	 * i=0;i<array.size();i++) { JSONObject op = array.getJSONObject(i);
	 * JSONObject p = new JSONObject(); p.accumulate("name",
	 * op.getString("name")); p.accumulate("title", op.getString("title"));
	 * p.accumulate("type", op.getString("type"));
	 * recursionParseData(p,op.getString("children")); cp.add(p); }
	 * param.put("children", cp); }else { param.put("childern", new
	 * JSONArray()); }
	 * 
	 * }
	 */
	
}
