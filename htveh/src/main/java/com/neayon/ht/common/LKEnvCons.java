package com.neayon.ht.common;

public class LKEnvCons {
	/** 待检车辆清单接口 */
	public static final String VD01 = "D01";
	/** 车辆详细信息下载接口 */
	public static final String VD02 = "D02";
	/** 获取环保外检结果（含OBD） */
	public static final String VD07 = "D07";
	/** 上传检测线时间点信息 */
	public static final String VU02 = "U02";
	/** 上传检测终止信息 */
	public static final String VU03 = "U03";
	/** 上传简易瞬态工况法过程信息 */
	public static final String VU21 = "U21";
	/** 上传简易瞬态工况法结果信息 */
	public static final String VU22 = "U22";
	/** 上传双怠速法过程信息 */
	public static final String VU23 = "U23";
	/** 上传双怠速法结果信息 */
	public static final String VU24 = "U24";
	/** 上传加载减速法过程信息 */
	public static final String VU25 = "U25";
	/** 上传加载减速法结果信息 */
	public static final String VU26 = "U26";
	/** 上传不透光烟度法过程信息 */
	public static final String VU27 = "U27";
	/** 上传不透光烟度法结果信息 */
	public static final String VU28 = "U28";
	/** OBD外检结果数据上传接口 */
	public static final String VU35 = "U35";
	/** OBD过程数据上传接口 */
	public static final String VU36 = "U36";

	/**
	 * 检验方法
	 * 
	 * @author kamiyj
	 *
	 */
	public static class JYFS {
		/** 简易瞬态工况法 */
		public static final String SIMPLE_TRANSIENT_CONDITION = "A";
		/** 加载减速法 */
		public static final String LOADING_DECELERATION = "B";
		/** 自由加速不透光烟度法 */
		public static final String FREE_ACCELERATION_OPACITY = "C";
		/** 双怠速法 */
		public static final String DOUBLE_IDLE = "Q";
	}
}
