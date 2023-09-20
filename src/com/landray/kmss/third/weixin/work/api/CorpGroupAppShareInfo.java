package com.landray.kmss.third.weixin.work.api;

public class CorpGroupAppShareInfo {

    public CorpGroupAppShareInfo(String corpId, String corpName, String agentId, String upstreamAgentId) {
        this.corpId = corpId;
        this.corpName = corpName;
        this.agentId = agentId;
        this.upstreamAgentId = upstreamAgentId;
    }

    private String corpId;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getUpstreamAgentId() {
        return upstreamAgentId;
    }

    public void setUpstreamAgentId(String upstreamAgentId) {
        this.upstreamAgentId = upstreamAgentId;
    }

    private String corpName;
    private String agentId;
    private String upstreamAgentId;
}
