package com.landray.kmss.hr.staff.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomerAccount;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public class Ekp_H14_M_performance implements Serializable{
	private String fdId;
	private SysOrgPerson fdBeiKaoHeRenXingMing;
	private SysOrgElement	fdSuoShuFenBu;
	private SysOrgElement	fdYiJiBuMen;
	private SysOrgElement	fdErJiBuMen;
	private String	fdSanJiBuMen;
	private String 	fdYuanGongBianHao;
	private SysOrgElement fdGangWeiMingChen;
	private String 	fdZhiLei;
	private String 	fdZhiJiXiShu;
	private String 	fdZhiJi;
	private Date fdRuZhiRiQi;
	private String fdJiDuJiXiaoJieGuo;
	private String fdNianDuJiXiaoJieGuo;
	private String 	fdRenYuanLeiBie;
	private String 	fdGangWeiXingZhi;
	private String 	fdZhiJieShangJi;
	private Date 	fdShenQingRiQi;
	private String 	fdKaoHeZhouQi;
	private Date 	fdKaoHeKaiShiShiJian;
	private Date 	fdKaoHeJieShuShiJian;
	private Double 	fdZiPingDeFen;
	private String 	fdZiPingDengJi;
	private String fdGongZuoXiaoJie;
	private String fdBeiKaoHeRenGangWei;
	private String fdJiXiaoJieGuo;
	private String fdZhuYaoChengJiHuoChengGuo;
	private String fdYouDaiGaiJinDeFangMian;
	private String 	fdQiWangJiGaiShan;
	private String 	fdZiWoGuanLiBiaoXianHao;
	private String 	fdZiWoGuanLiXuFanSi;
	private String 	fdTuanDuiGuanLiBiaoXianHao;
	private String fdTuanDuiGuanLiXuFanSi;
	private Double 	fdMianTanJieGuo;
	private String fdLDLXingDongJiHua;
	private String fdLDLKunNanHuoSuoXuZhiChi;
	private String fdZiWoGuanLiBiaoXianHaoDe;
	private String 	fdZiWoGuanLiXuFanSiDe;
	private Date 	fdLDLJiHuaWanChengShiJi;
	private String fdJXGaiJinJiHua;
	private String 	fdJXKunNanHuoSuoXuZhiChi;
	private Date 	fdJXJiHuaWanChengShiJian;
	private String fdId1;
	private String fdId2;
	private String fdTdglXuFanSiDe;
	private String fdTdglBiaoXianHaoDe;
	private String fdXyjdQiWangJiGaiShan;
	
	public String getFdXyjdQiWangJiGaiShan() {
		return fdXyjdQiWangJiGaiShan;
	}

	public void setFdXyjdQiWangJiGaiShan(String fdXyjdQiWangJiGaiShan) {
		this.fdXyjdQiWangJiGaiShan = fdXyjdQiWangJiGaiShan;
	}

	public String getFdTdglXuFanSiDe() {
		return fdTdglXuFanSiDe;
	}

	public void setFdTdglXuFanSiDe(String fdTdglXuFanSiDe) {
		this.fdTdglXuFanSiDe = fdTdglXuFanSiDe;
	}

	public String getFdTdglBiaoXianHaoDe() {
		return fdTdglBiaoXianHaoDe;
	}

	public void setFdTdglBiaoXianHaoDe(String fdTdglBiaoXianHaoDe) {
		this.fdTdglBiaoXianHaoDe = fdTdglBiaoXianHaoDe;
	}

	public String getFdJiDuJiXiaoJieGuo() {
		return fdJiDuJiXiaoJieGuo;
	}

	public void setFdJiDuJiXiaoJieGuo(String fdJiDuJiXiaoJieGuo) {
		this.fdJiDuJiXiaoJieGuo = fdJiDuJiXiaoJieGuo;
	}

	public String getFdZiWoGuanLiBiaoXianHaoDe() {
		return fdZiWoGuanLiBiaoXianHaoDe;
	}

	public void setFdZiWoGuanLiBiaoXianHaoDe(String fdZiWoGuanLiBiaoXianHaoDe) {
		this.fdZiWoGuanLiBiaoXianHaoDe = fdZiWoGuanLiBiaoXianHaoDe;
	}

	public String getFdZiWoGuanLiXuFanSiDe() {
		return fdZiWoGuanLiXuFanSiDe;
	}

	public void setFdZiWoGuanLiXuFanSiDe(String fdZiWoGuanLiXuFanSiDe) {
		this.fdZiWoGuanLiXuFanSiDe = fdZiWoGuanLiXuFanSiDe;
	}

	public String getFdNianDuJiXiaoJieGuo() {
		return fdNianDuJiXiaoJieGuo;
	}

	public void setFdNianDuJiXiaoJieGuo(String fdNianDuJiXiaoJieGuo) {
		this.fdNianDuJiXiaoJieGuo = fdNianDuJiXiaoJieGuo;
	}

	public String getFdId1() {
		return fdId1;
	}

	public void setFdId1(String fdId1) {
		this.fdId1 = fdId1;
	}

	public String getFdId2() {
		return fdId2;
	}

	public void setFdId2(String fdId2) {
		this.fdId2 = fdId2;
	}

	public String getFdJXGaiJinJiHua() {
		return fdJXGaiJinJiHua;
	}

	public void setFdJXGaiJinJiHua(String fdJXGaiJinJiHua) {
		this.fdJXGaiJinJiHua = fdJXGaiJinJiHua;
	}

	public String getFdJXKunNanHuoSuoXuZhiChi() {
		return fdJXKunNanHuoSuoXuZhiChi;
	}

	public void setFdJXKunNanHuoSuoXuZhiChi(String fdJXKunNanHuoSuoXuZhiChi) {
		this.fdJXKunNanHuoSuoXuZhiChi = fdJXKunNanHuoSuoXuZhiChi;
	}

	public Date getFdJXJiHuaWanChengShiJian() {
		return fdJXJiHuaWanChengShiJian;
	}

	public void setFdJXJiHuaWanChengShiJian(Date fdJXJiHuaWanChengShiJian) {
		this.fdJXJiHuaWanChengShiJian = fdJXJiHuaWanChengShiJian;
	}

	public String getFdLDLXingDongJiHua() {
		return fdLDLXingDongJiHua;
	}

	public void setFdLDLXingDongJiHua(String fdLDLXingDongJiHua) {
		this.fdLDLXingDongJiHua = fdLDLXingDongJiHua;
	}

	public String getFdLDLKunNanHuoSuoXuZhiChi() {
		return fdLDLKunNanHuoSuoXuZhiChi;
	}

	public void setFdLDLKunNanHuoSuoXuZhiChi(String fdLDLKunNanHuoSuoXuZhiChi) {
		this.fdLDLKunNanHuoSuoXuZhiChi = fdLDLKunNanHuoSuoXuZhiChi;
	}

	public Date getFdLDLJiHuaWanChengShiJi() {
		return fdLDLJiHuaWanChengShiJi;
	}

	public void setFdLDLJiHuaWanChengShiJi(Date fdLDLJiHuaWanChengShiJi) {
		this.fdLDLJiHuaWanChengShiJi = fdLDLJiHuaWanChengShiJi;
	}

	public String getFdYouDaiGaiJinDeFangMian() {
		return fdYouDaiGaiJinDeFangMian;
	}

	public void setFdYouDaiGaiJinDeFangMian(String fdYouDaiGaiJinDeFangMian) {
		this.fdYouDaiGaiJinDeFangMian = fdYouDaiGaiJinDeFangMian;
	}

	public String getFdQiWangJiGaiShan() {
		return fdQiWangJiGaiShan;
	}

	public void setFdQiWangJiGaiShan(String fdQiWangJiGaiShan) {
		this.fdQiWangJiGaiShan = fdQiWangJiGaiShan;
	}

	public String getFdZiWoGuanLiBiaoXianHao() {
		return fdZiWoGuanLiBiaoXianHao;
	}

	public void setFdZiWoGuanLiBiaoXianHao(String fdZiWoGuanLiBiaoXianHao) {
		this.fdZiWoGuanLiBiaoXianHao = fdZiWoGuanLiBiaoXianHao;
	}

	public String getFdZiWoGuanLiXuFanSi() {
		return fdZiWoGuanLiXuFanSi;
	}

	public void setFdZiWoGuanLiXuFanSi(String fdZiWoGuanLiXuFanSi) {
		this.fdZiWoGuanLiXuFanSi = fdZiWoGuanLiXuFanSi;
	}

	public String getFdTuanDuiGuanLiBiaoXianHao() {
		return fdTuanDuiGuanLiBiaoXianHao;
	}

	public void setFdTuanDuiGuanLiBiaoXianHao(String fdTuanDuiGuanLiBiaoXianHao) {
		this.fdTuanDuiGuanLiBiaoXianHao = fdTuanDuiGuanLiBiaoXianHao;
	}

	public String getFdTuanDuiGuanLiXuFanSi() {
		return fdTuanDuiGuanLiXuFanSi;
	}

	public void setFdTuanDuiGuanLiXuFanSi(String fdTuanDuiGuanLiXuFanSi) {
		this.fdTuanDuiGuanLiXuFanSi = fdTuanDuiGuanLiXuFanSi;
	}

	public Double getFdMianTanJieGuo() {
		return fdMianTanJieGuo;
	}

	public void setFdMianTanJieGuo(Double fdMianTanJieGuo) {
		this.fdMianTanJieGuo = fdMianTanJieGuo;
	}

	public String getFdFenQiDian() {
		return fdFenQiDian;
	}

	public void setFdFenQiDian(String fdFenQiDian) {
		this.fdFenQiDian = fdFenQiDian;
	}
	private String 	fdFenQiDian;
	public String getFdZhuYaoChengJiHuoChengGuo() {
		return fdZhuYaoChengJiHuoChengGuo;
	}

	public void setFdZhuYaoChengJiHuoChengGuo(String fdZhuYaoChengJiHuoChengGuo) {
		this.fdZhuYaoChengJiHuoChengGuo = fdZhuYaoChengJiHuoChengGuo;
	}

	public String getFdJiXiaoJieGuo() {
		return fdJiXiaoJieGuo;
	}

	public void setFdJiXiaoJieGuo(String fdJiXiaoJieGuo) {
		this.fdJiXiaoJieGuo = fdJiXiaoJieGuo;
	}

	public String getFdBeiKaoHeRenGangWei() {
		return fdBeiKaoHeRenGangWei;
	}

	public void setFdBeiKaoHeRenGangWei(String fdBeiKaoHeRenGangWei) {
		this.fdBeiKaoHeRenGangWei = fdBeiKaoHeRenGangWei;
	}

	public SysOrgPerson getFdBeiKaoHeRenXingMing() {
		return fdBeiKaoHeRenXingMing;
	}

	public String getFdGongZuoXiaoJie() {
		return fdGongZuoXiaoJie;
	}

	public void setFdGongZuoXiaoJie(String fdGongZuoXiaoJie) {
		this.fdGongZuoXiaoJie = fdGongZuoXiaoJie;
	}

	public void setFdBeiKaoHeRenXingMing(SysOrgPerson fdBeiKaoHeRenXingMing) {
		this.fdBeiKaoHeRenXingMing = fdBeiKaoHeRenXingMing;
	}
	
	
	public String getFdYuanGongBianHao() {
		return fdYuanGongBianHao;
	}
	public void setFdYuanGongBianHao(String fdYuanGongBianHao) {
		this.fdYuanGongBianHao = fdYuanGongBianHao;
	}
	

	
	public String getFdId() {
		return fdId;
	}
	public void setFdId(String fdId) {
		this.fdId = fdId;
	}
	public String getFdZhiLei() {
		return fdZhiLei;
	}
	public void setFdZhiLei(String fdZhiLei) {
		this.fdZhiLei = fdZhiLei;
	}
	public SysOrgElement getFdSuoShuFenBu() {
		return fdSuoShuFenBu;
	}
	public void setFdSuoShuFenBu(SysOrgElement fdSuoShuFenBu) {
		this.fdSuoShuFenBu = fdSuoShuFenBu;
	}
	
	public SysOrgElement getFdGangWeiMingChen() {
		return fdGangWeiMingChen;
	}
	public void setFdGangWeiMingChen(SysOrgElement fdGangWeiMingChen) {
		this.fdGangWeiMingChen = fdGangWeiMingChen;
	}
	public String getFdZhiJiXiShu() {
		return fdZhiJiXiShu;
	}
	public SysOrgElement getFdYiJiBuMen() {
		return fdYiJiBuMen;
	}

	public void setFdYiJiBuMen(SysOrgElement fdYiJiBuMen) {
		this.fdYiJiBuMen = fdYiJiBuMen;
	}

	public SysOrgElement getFdErJiBuMen() {
		return fdErJiBuMen;
	}

	public void setFdErJiBuMen(SysOrgElement fdErJiBuMen) {
		this.fdErJiBuMen = fdErJiBuMen;
	}

	public String getFdSanJiBuMen() {
		return fdSanJiBuMen;
	}

	public void setFdSanJiBuMen(String fdSanJiBuMen) {
		this.fdSanJiBuMen = fdSanJiBuMen;
	}

	public void setFdZhiJiXiShu(String fdZhiJiXiShu) {
		this.fdZhiJiXiShu = fdZhiJiXiShu;
	}
	public String getFdZhiJi() {
		return fdZhiJi;
	}
	public void setFdZhiJi(String fdZhiJi) {
		this.fdZhiJi = fdZhiJi;
	}
	public Date getFdRuZhiRiQi() {
		return fdRuZhiRiQi;
	}
	public void setFdRuZhiRiQi(Date fdRuZhiRiQi) {
		this.fdRuZhiRiQi = fdRuZhiRiQi;
	}
	public String getFdRenYuanLeiBie() {
		return fdRenYuanLeiBie;
	}
	public void setFdRenYuanLeiBie(String fdRenYuanLeiBie) {
		this.fdRenYuanLeiBie = fdRenYuanLeiBie;
	}
	public String getFdGangWeiXingZhi() {
		return fdGangWeiXingZhi;
	}
	public void setFdGangWeiXingZhi(String fdGangWeiXingZhi) {
		this.fdGangWeiXingZhi = fdGangWeiXingZhi;
	}
	public String getFdZhiJieShangJi() {
		return fdZhiJieShangJi;
	}
	public void setFdZhiJieShangJi(String fdZhiJieShangJi) {
		this.fdZhiJieShangJi = fdZhiJieShangJi;
	}
	public Date getFdShenQingRiQi() {
		return fdShenQingRiQi;
	}
	public void setFdShenQingRiQi(Date fdShenQingRiQi) {
		this.fdShenQingRiQi = fdShenQingRiQi;
	}
	public String getFdKaoHeZhouQi() {
		return fdKaoHeZhouQi;
	}
	public void setFdKaoHeZhouQi(String fdKaoHeZhouQi) {
		this.fdKaoHeZhouQi = fdKaoHeZhouQi;
	}
	public Date getFdKaoHeKaiShiShiJian() {
		return fdKaoHeKaiShiShiJian;
	}
	public void setFdKaoHeKaiShiShiJian(Date fdKaoHeKaiShiShiJian) {
		this.fdKaoHeKaiShiShiJian = fdKaoHeKaiShiShiJian;
	}
	public Date getFdKaoHeJieShuShiJian() {
		return fdKaoHeJieShuShiJian;
	}
	public void setFdKaoHeJieShuShiJian(Date fdKaoHeJieShuShiJian) {
		this.fdKaoHeJieShuShiJian = fdKaoHeJieShuShiJian;
	}
	public Double getFdZiPingDeFen() {
		return fdZiPingDeFen;
	}
	public void setFdZiPingDeFen(Double fdZiPingDeFen) {
		this.fdZiPingDeFen = fdZiPingDeFen;
	}
	public String getFdZiPingDengJi() {
		return fdZiPingDengJi;
	}
	public void setFdZiPingDengJi(String fdZiPingDengJi) {
		this.fdZiPingDengJi = fdZiPingDengJi;
	}
	 @Override
	 public boolean equals(Object obj) {
	  return super.equals(obj);
	 }
	 @Override
	 public int hashCode() {
	  return 1;
	 }
}	
