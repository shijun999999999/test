package com.neayon.ht.common;

public class Code {
	/** 状态 成功 */
	public static final int SUCCESS = 0;
	/** 参数为空错误 */
	public static final int PARAM_NULL_ERROR = 1001;
	/** 参数长度错误 */
	public static final int PARAM_LENGTH_ERROR = 1002;
	/** 参数校验错误 */
	public static final int PARAM_REGEX_ERROR = 1003;
	/** 无该用户错误 */
	public static final int NO_USER_ERROR = 1101;
	/** 密码校验错误 */
	public static final int PASSWORD_VERIFY_ERROR = 1102;
	/** 登录失败 */
	public static final int LOGIN_FAILED = 1103;
	/** 登录过期 */
	public static final int LOGIN_EXPIRED = 1104;
	/** 账户过期 */
	public static final int ACCOUNT_EXPIRED = 1105;
	/** 登出失败 */
	public static final int LOGOUT_FAILED = 1201;
	/** 操作失败 */
	public static final int OPTION_FAILED = 2001;
	/** 重复的操作 */
	public static final int OPTION_DUBLICATE = 2002;
	/** 数据库错误 */
	public static final int SQL_ERROR = 3001;
	/** 运行时错误 */
	public static final int RUNTIME_EXCEPTION = 3002;
	
	/** 检验员名下已绑定有在检车辆 */
	public static final int GUIDE_HAS_VEH = 4001;
	/** 检验员名下无绑定的在检车辆 */
	public static final int GUIDE_HAS_NO_VEH = 4002;
	/** 无可绑定车辆 */
	public static final int NO_VEH_BIND = 4003;
	/** 刷卡机配置问题 */
	public static final int INSTYPE_ERROR = 4004;
	/** 检验员无准驾资格 */
	public static final int GUIDE_DRIVING_BANED = 4005;
}
