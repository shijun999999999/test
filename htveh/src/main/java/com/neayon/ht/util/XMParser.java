package com.neayon.ht.util;

import java.util.ArrayList;
import java.util.List;

import com.neayon.ht.common.GBCons;
import com.neayon.ht.common.LKSafeCons;

public class XMParser {
	/**
	 * 国标检验项目转为link检验工位
	 * 
	 * @param jyxm
	 * @return
	 */
	public static String parseGBJyxmToLink(String jyxm) {
		String xm = "";
		switch (jyxm) {
		case "B0":
			xm = LKSafeCons.JYXM.JB0;
			break;
		case "B1":
			xm = LKSafeCons.JYXM.JB1;
			break;
		case "B2":
			xm = LKSafeCons.JYXM.JB2;
			break;
		case "B3":
			xm = LKSafeCons.JYXM.JB3;
			break;
		case "B4":
			xm = LKSafeCons.JYXM.JB4;
			break;
		case "B5":
			xm = LKSafeCons.JYXM.JB5;
			break;
		case "B6":
			xm = LKSafeCons.JYXM.JB6;
			break;
		case "L1":
			xm = LKSafeCons.JYXM.JL1;
			break;
		case "L2":
			xm = LKSafeCons.JYXM.JL2;
			break;
		case "L3":
			xm = LKSafeCons.JYXM.JL3;
			break;
		case "L4":
			xm = LKSafeCons.JYXM.JL4;
			break;
		case "A1":
			xm = LKSafeCons.JYXM.JA1;
			break;
		case "S1":
			xm = LKSafeCons.JYXM.JS1;
			break;
		case "Z1":
			xm = LKSafeCons.JYXM.JZ1;
			break;
		case "F1":
			xm = LKSafeCons.JYXM.JF1;
			break;
		case "M1":
			xm = LKSafeCons.JYXM.JM1;
			break;
		case "R1":
			xm = LKSafeCons.JYXM.JR1;
			break;
		case "R2":
			xm = LKSafeCons.JYXM.JR2;
			break;
		case "H1":
			xm = LKSafeCons.JYXM.JH1;
			break;
		case "H2":
			xm = LKSafeCons.JYXM.JH2;
			break;
		case "H3":
			xm = LKSafeCons.JYXM.JH3;
			break;
		case "H4":
			xm = LKSafeCons.JYXM.JH4;
			break;
		case "C1":
			xm = LKSafeCons.JYXM.JC1;
			break;
		case "DC":
			xm = LKSafeCons.JYXM.JDC;
			break;
		default:
			break;
		}
		return xm;
	}

	public static String filterEvent(String jyxm) {
		if (jyxm != null && jyxm != "") {
			if (jyxm.indexOf("B") == 0 && jyxm.indexOf("0") < 0) {
				return LKSafeCons.VU05;
			} else if (jyxm.equals("B0")) {
				return LKSafeCons.VU06;
			} else if (jyxm.indexOf("L") == 0) {
				return LKSafeCons.VU17;
			} else if (jyxm.indexOf("A") == 0) {
				return LKSafeCons.VU11;
			} else if (jyxm.indexOf("S") == 0) {
				return LKSafeCons.VU10;
			} else if (jyxm.indexOf("H") == 0) {
				return LKSafeCons.VU09;
			} else if (jyxm.indexOf("R") >= 0) {
				return LKSafeCons.VU12;
			}
		}
		return "";
	}

	/**
	 * 将国标检验项目转为link检验工位
	 * 
	 * @param gbjyxm
	 * @param split
	 * @return
	 */
	public static String parseJyxmGBToLinkStr(String gbjyxm, String split) {
		List<String> xms = new ArrayList<String>();
		String[] gbs = gbjyxm.split(split);
		for (String gb : gbs) {
			switch (gb) {
			case GBCons.JYXM.A1:
				xms.add(LKSafeCons.JYXM.JA1);
				break;
			case GBCons.JYXM.B0:
				xms.add(LKSafeCons.JYXM.JB0);
				break;
			case GBCons.JYXM.B1:
				xms.add(LKSafeCons.JYXM.JB1);
				break;
			case GBCons.JYXM.B2:
				xms.add(LKSafeCons.JYXM.JB2);
				break;
			case GBCons.JYXM.B3:
				xms.add(LKSafeCons.JYXM.JB3);
				break;
			case GBCons.JYXM.B4:
				xms.add(LKSafeCons.JYXM.JB4);
				break;
			case GBCons.JYXM.B5:
				xms.add(LKSafeCons.JYXM.JB5);
				break;
			case GBCons.JYXM.B6:
				xms.add(LKSafeCons.JYXM.JB6);
				break;
			case GBCons.JYXM.C1:
				xms.add(LKSafeCons.JYXM.JC1);
				break;
			case GBCons.JYXM.DC:
				xms.add(LKSafeCons.JYXM.JDC);
				break;
			case GBCons.JYXM.F1:
				xms.add(LKSafeCons.JYXM.JF1);
				break;
			case GBCons.JYXM.H1:
				xms.add(LKSafeCons.JYXM.JH1);
				break;
			case GBCons.JYXM.H2:
				xms.add(LKSafeCons.JYXM.JH2);
				break;
			case GBCons.JYXM.H3:
				xms.add(LKSafeCons.JYXM.JH3);
				break;
			case GBCons.JYXM.H4:
				xms.add(LKSafeCons.JYXM.JH4);
				break;
			case GBCons.JYXM.L1:
				xms.add(LKSafeCons.JYXM.JL1);
				break;
			case GBCons.JYXM.L2:
				xms.add(LKSafeCons.JYXM.JL2);
				break;
			case GBCons.JYXM.L3:
				xms.add(LKSafeCons.JYXM.JL3);
				break;
			case GBCons.JYXM.L4:
				xms.add(LKSafeCons.JYXM.JL4);
				break;
			case GBCons.JYXM.M1:
				xms.add(LKSafeCons.JYXM.JM1);
				break;
			case GBCons.JYXM.NQ:
				break;
			case GBCons.JYXM.R1:
				xms.add(LKSafeCons.JYXM.JR1);
				break;
			case GBCons.JYXM.R2:
				xms.add(LKSafeCons.JYXM.JR2);
				break;
			case GBCons.JYXM.S1:
				xms.add(LKSafeCons.JYXM.JS1);
				break;
			case GBCons.JYXM.UC:
				break;
			case GBCons.JYXM.Z1:
				xms.add(LKSafeCons.JYXM.JZ1);
				break;
			default:
				break;
			}
		}
		String linkjyxm = String.join(",", xms);
		return linkjyxm;
	}

	/**
	 * 将link检验工位转为国标检验项目
	 * 
	 * @param shjyxm
	 * @param split
	 * @return
	 */
	public static String parseLinkJyxmToGBStr(String shjyxm, String split) {
		String[] jyxms = shjyxm.split(split);
		List<String> rjyxm = new ArrayList<String>();
		for (String jyxm : jyxms) {
			switch (jyxm) {
			case LKSafeCons.JYXM.JB1:
			case LKSafeCons.JYXM.JLB1:
				if (!rjyxm.contains(GBCons.JYXM.B1)) {
					rjyxm.add(GBCons.JYXM.B1);
				}
				break;
			case LKSafeCons.JYXM.JB2:
			case LKSafeCons.JYXM.JLB2:
				if (!rjyxm.contains(GBCons.JYXM.B2)) {
					rjyxm.add(GBCons.JYXM.B2);
				}
				break;
			case LKSafeCons.JYXM.JB3:
			case LKSafeCons.JYXM.JLB3:
				if (!rjyxm.contains(GBCons.JYXM.B3)) {
					rjyxm.add(GBCons.JYXM.B3);
				}
				break;
			case LKSafeCons.JYXM.JB4:
			case LKSafeCons.JYXM.JLB4:
				if (!rjyxm.contains(GBCons.JYXM.B4)) {
					rjyxm.add(GBCons.JYXM.B4);
				}
				break;
			case LKSafeCons.JYXM.JB5:
			case LKSafeCons.JYXM.JLB5:
				if (!rjyxm.contains(GBCons.JYXM.B5)) {
					rjyxm.add(GBCons.JYXM.B5);
				}
				break;
			case LKSafeCons.JYXM.JB6:
				rjyxm.add(GBCons.JYXM.B6);
				break;
			case LKSafeCons.JYXM.JB0:
			case LKSafeCons.JYXM.JLB0:
				if (!rjyxm.contains(GBCons.JYXM.B0)) {
					rjyxm.add(GBCons.JYXM.B0);
				}
				break;
			case LKSafeCons.JYXM.JS1:
				rjyxm.add(GBCons.JYXM.S1);
				break;
			case LKSafeCons.JYXM.JA1:
				rjyxm.add(GBCons.JYXM.A1);
				break;
			case LKSafeCons.JYXM.JF1:
				rjyxm.add(GBCons.JYXM.F1);
				rjyxm.add(GBCons.JYXM.NQ);
				rjyxm.add(GBCons.JYXM.UC);
				break;
			case LKSafeCons.JYXM.JM1:
				rjyxm.add(GBCons.JYXM.M1);
				break;
			case LKSafeCons.JYXM.JZ1:
				rjyxm.add(GBCons.JYXM.Z1);
				break;
			case LKSafeCons.JYXM.JC1:
				rjyxm.add(GBCons.JYXM.C1);
				break;
			case LKSafeCons.JYXM.JDC:
				rjyxm.add(GBCons.JYXM.DC);
				break;
			case LKSafeCons.JYXM.JH1:
				rjyxm.add(GBCons.JYXM.H1);
				break;
			case LKSafeCons.JYXM.JH2:
				rjyxm.add(GBCons.JYXM.H2);
				break;
			case LKSafeCons.JYXM.JH3:
				rjyxm.add(GBCons.JYXM.H3);
				break;
			case LKSafeCons.JYXM.JH4:
				rjyxm.add(GBCons.JYXM.H4);
				break;
			case LKSafeCons.JYXM.JR1:
				rjyxm.add(GBCons.JYXM.R1);
				break;
			case LKSafeCons.JYXM.JR2:
				rjyxm.add(GBCons.JYXM.R2);
				break;
			case LKSafeCons.JYXM.JL1:
				rjyxm.add(GBCons.JYXM.L1);
				break;
			case LKSafeCons.JYXM.JL2:
				rjyxm.add(GBCons.JYXM.L2);
				break;
			case LKSafeCons.JYXM.JL3:
				rjyxm.add(GBCons.JYXM.L3);
				break;
			case LKSafeCons.JYXM.JL4:
				rjyxm.add(GBCons.JYXM.L4);
				break;
			default:
				break;
			}
		}
		String res = String.join(",", rjyxm);
		return res;
	}

	/**
	 * 转换林克检验类别到国标
	 * 
	 * @param jylb
	 * @return
	 */
	public static String parseLinkJylbToGB(String jylb) {
		String jylbGB = "02";
		switch (jylb) {
		case "1":
			jylbGB = "01";
			break;
		case "2":
			jylbGB = "00";
			break;
		case "3":
			jylbGB = "02";
			break;
		case "4":
			jylbGB = "02";
			break;
		default:
			break;
		}
		return jylbGB;
	}
	
	/**
	 * 驱动形式翻译
	 * @param qdxs
	 * @return
	 */
	public static String parseQdxs(String qdxs) {
		String ret = "";
		switch (qdxs) {
		case "4×2前驱":
			ret = "前驱";
			break;
		case "4×2后驱":
			ret = "后驱";
			break;
		default:
			ret = qdxs;
			break;
		}
		return ret;
	}
	
	/**
	 * 常数值转换
	 * @param val
	 * @return
	 */
	public static Integer parseDesc(String val) {
		Integer ret = 0;
		switch (val) {
		case "独立":
			ret = 0;
			break;
		case "非独立":
			ret = 1;
			break;
		case "是":
			
			break;
		default:
			break;
		}
		return ret;
	}
}
