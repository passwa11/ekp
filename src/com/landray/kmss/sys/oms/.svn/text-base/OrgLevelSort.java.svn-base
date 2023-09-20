package com.landray.kmss.sys.oms;

public class OrgLevelSort implements Comparable {

	private int level;

	public int getLevel() {
		return level;
	}

	private Object target;

	public Object getTarget() {
		return target;
	}

	public OrgLevelSort(Object target, int level) {
		this.target = target;
		this.level = level;
	}

	@Override
    public int compareTo(Object o) {
		OrgLevelSort sort = (OrgLevelSort) o;
		return getLevel() - sort.getLevel();
	}

}
