package com.landray.kmss.sys.oms.temp;

/**
 * 同步配置：调用begin方法的时候传，针对一次同步事务有效
 * 注意：该参数一单确定，就不要修改，否则可能会带来数据不一致的问题。
 * @author yuliang
 * 创建时间：2020年5月29日
 */
public class SysOmsSynConfig {
    /**
     * 部门是否正序
     * 说明：默认为true
     * 如果为true，序号小的优先
     * 如果为false，序号大的优先
     */
    private Boolean fdDeptIsAsc = true;
    
    /**
     * 人员是否正序
     * 说明：默认为true
     * 如果为true，序号小的优先
     * 如果为false，序号大的优先
     */
    private Boolean fdPersonIsAsc = true;
    
    /**
     * 人员主部门是否生效
     * 说明：默认为true,在模式200中该参数起作用；
     * 如果为true，则人员信息中的人员所属部门字段生效，该字段为空表示这个人没有所属主部门；
     * 如果为false，则人员信息中人员所属部门字段不生效，系统会无视该字段，并且会从人员部门关系中自动选择一个部门作为该人员主部门
     */
    private Boolean fdPersonIsMainDept = true;
    
    /**
     * 人员部门关系是否是全量
     * 说明：默认为true，在模式200和400中该参数起作用；
     * 如果为true，则表示本次事务中人员部门关系信息是这个人所有有效的部门关系，系统会用这次传过来的关系覆盖原有的关系
     * 如果为false，则表示本次事务中人员部门关系信息是这个人变更的部门关系，如果人员部门关系信息中的是否有效字段为false，则系统会删除该条关系，如果为true，则新增该条关系，其它的关系不变。
     */
    private Boolean fdPersonDeptIsFull = true;
    
    /**
     * 人员岗位关系是否全量
     * 说明：默认为true，在模式300和400中该参数起作用；
     * 如果为true，则表示本次事务中人员岗位关系信息是这个人所有有效的岗位关系，系统会用这次传过来的关系覆盖原有的关系
     * 如果为false，则表示本次事务中人员岗位关系信息是这个人变更的岗位关系，如果人员岗位关系信息中的是否有效字段为false，则系统会删除该条关系，如果为true，则新增该条关系，系统原有的关系不变。
     */
    private Boolean fdPersonPostIsFull = true;
    
    /**
     * 是否是全量同步，默认为0
     * 如果为0
     * 蓝桥认为调用方发送的数据是增量的
     * 此配置的更新策略：根据调用方发过来的增量数据更新
     * 此配置的删除策略：根据部门/人员/岗位的fdIsAvailable=false删除
     * 如果为1
     * 蓝桥认为调用方发送的数据是全量有效的
     * 此配置下的更新策略：通过和EKP数据的属性对比，如果有一个属性变更了，则更新EKP数据
     * 此配置下的删除策略：通过和EKP全量对比删除EKP多余的数据
     */
    private int fdFullSynFlag = 0;
    
    /**
     * 外部系统的顶级部门直接挂在手动在EKP上新建的部门
     */
    private String fdEkpRootId;


	public Boolean getFdDeptIsAsc() {
		return fdDeptIsAsc;
	}

	public void setFdDeptIsAsc(Boolean fdDeptIsAsc) {
		this.fdDeptIsAsc = fdDeptIsAsc;
	}

	public Boolean getFdPersonIsAsc() {
		return fdPersonIsAsc;
	}

	public void setFdPersonIsAsc(Boolean fdPersonIsAsc) {
		this.fdPersonIsAsc = fdPersonIsAsc;
	}

	public Boolean getFdPersonIsMainDept() {
		return fdPersonIsMainDept;
	}

	public void setFdPersonIsMainDept(Boolean fdPersonIsMainDept) {
		this.fdPersonIsMainDept = fdPersonIsMainDept;
	}

	public Boolean getFdPersonDeptIsFull() {
		return fdPersonDeptIsFull;
	}

	public void setFdPersonDeptIsFull(Boolean fdPersonDeptIsFull) {
		this.fdPersonDeptIsFull = fdPersonDeptIsFull;
	}

	public Boolean getFdPersonPostIsFull() {
		return fdPersonPostIsFull;
	}

	public void setFdPersonPostIsFull(Boolean fdPersonPostIsFull) {
		this.fdPersonPostIsFull = fdPersonPostIsFull;
	}

	public int getFdFullSynFlag() {
		return fdFullSynFlag;
	}

	public void setFdFullSynFlag(int fdFullSynFlag) {
		this.fdFullSynFlag = fdFullSynFlag;
	}

	public String getFdEkpRootId() {
		return fdEkpRootId;
	}

	public void setFdEkpRootId(String fdEkpRootId) {
		this.fdEkpRootId = fdEkpRootId;
	}
	
	
    
}
