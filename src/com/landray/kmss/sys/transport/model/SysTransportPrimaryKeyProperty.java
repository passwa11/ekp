package com.landray.kmss.sys.transport.model;

import java.util.List;

/**
 * @author 苏轶
 * 主数据关键字属性
 */
public class SysTransportPrimaryKeyProperty extends Property
{
	@Override
    public Integer getFdOrder() {
		throw new UnsupportedOperationException("主数据关键字没有顺序。");
	}
	@Override
    public void setFdOrder(Integer fdOrder) {
		throw new UnsupportedOperationException("主数据关键字没有顺序。");
	}
	@Override
    public List getKeyList() {
		throw new UnsupportedOperationException("主数据关键字没有外键属性列表。");
	}
	@Override
    public void setKeyList(List keyList) {
		throw new UnsupportedOperationException("主数据关键字没有外键属性列表。");
	}
}
