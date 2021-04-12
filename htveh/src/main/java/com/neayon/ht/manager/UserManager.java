package com.neayon.ht.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.neayon.ht.bean.Message;
import com.neayon.ht.bean.PageBean;
import com.neayon.ht.common.Code;
import com.neayon.ht.common.Constant;
import com.neayon.ht.dao.CustomerDao;
import com.neayon.ht.dao.RoleDao;
import com.neayon.ht.dao.UserDao;
import com.neayon.ht.entity.Customer;
import com.neayon.ht.entity.Role;
import com.neayon.ht.entity.User;
import com.neayon.ht.exception.MyException;
import com.neayon.ht.util.AES;
import com.neayon.ht.util.CharUtil;

@Service("UserManager")
public class UserManager extends BaseManager {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@Resource(name = "RoleManager")
	private RoleManager rm;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private CustomerDao customerDao;

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Message login(String username, String password) {
		Message mes = new Message();
		if (CharUtil.isEmpty(username)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("username");
			return mes;
		}
		if (CharUtil.isEmpty(password)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("password");
			return mes;
		}
		if (password.length() <= 2) {
			mes.setCode(Code.PARAM_LENGTH_ERROR);
			mes.setError("password");
			return mes;
		}
		User tmp = new User(username, CharUtil.toMyStr(DigestUtils.md5DigestAsHex(AES.Encrypt(password).getBytes())));
		User user = userDao.login(tmp);
		if (CharUtil.isEmpty(user)) {
			mes.setCode(Code.LOGIN_FAILED);
			mes.setError("username,password");
			return mes;
		}
		if (!validateUser(user)) {
			mes.setCode(Code.ACCOUNT_EXPIRED);
			mes.setError("account expired");
			return mes;
		}
		Role role = roleDao.getRoleById(user.getRole());
		if (CharUtil.isEmpty(role)) {
			user.setFunctionPoint("");
		} else {
			user.setFunctionPoint(role.getFunctionPoint());
		}
		redis.set(Constant.Key.USER_SESSION_KEY + user.getId(), user, Constant.EXPIRETIME);
		mes.setCode(Code.SUCCESS);
		mes.setMessage("login success");
		mes.setResult(user.getId());
		return mes;
	}

	/**
	 * 校验账户有效期
	 * 
	 * @param user
	 * @return
	 */
	private boolean validateUser(User user) {
		String validate = user.getValidate();
		try {
			validate = AES.Decrypt(CharUtil.fromMyStr(validate));
			Date validay = sdf.parse(validate);
			Date now = new Date();
			if (now.after(validay)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 用户登出
	 * 
	 * @return
	 */
	public Message logout() {
		Message mes = new Message();
		String tmp = request.getHeader(Constant.Key.REQ_HEADER);
		if (CharUtil.isEmpty(tmp)) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError(Constant.Key.REQ_HEADER);
			return mes;
		}
		try {
			Integer userid = Integer.parseInt(tmp);
			if (userid <= 0) {
				mes.setCode(Code.PARAM_NULL_ERROR);
				mes.setError(Constant.Key.REQ_HEADER);
				return mes;
			}
			redis.del(Constant.Key.USER_SESSION_KEY + userid);
			mes.setCode(Code.SUCCESS);
			mes.setError("logout success");
			mes.setResult("logout success");
		} catch (NumberFormatException e) {
			mes.setCode(Code.PARAM_REGEX_ERROR);
			mes.setError(Constant.Key.REQ_HEADER);
		}
		return mes;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public Message getUser() {
		Message mes = new Message();
		User user = getUserStored();
		user.setPassword("");
		user.setValidate("");
		mes.setCode(Code.SUCCESS);
		mes.setMessage("user");
		mes.setResult(user);
		return mes;
	}
	
	/**
	 * 获取客户列表
	 * @param isPage
	 * @param page
	 * @param size
	 * @param customer
	 * @return
	 */
	public Message fetchCustomers(boolean isPage, int page, int size, Customer customer) {
		Message mes = new Message();
		PageBean pageBean = new PageBean(isPage, page, size, customer);
		List<Customer> custs = customerDao.fetchCustomers(pageBean);
		mes.setCode(Code.SUCCESS);
		mes.setMessage("customers");
		mes.setResult(custs);
		return mes;
	}

	/**
	 * 查询员工信息
	 * 
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @param user
	 * @return
	 */
	public Message fetchUsers(int page, int size, String startTime, String endTime, User user) {
		Message mes = new Message();
		if (CharUtil.isEmpty(user)) {
			user = new User();
		}
		Date start = null;
		try {
			start = sdf.parse(startTime);
		} catch (Exception e) {
		}
		Date end = null;
		try {
			end = sdf.parse(endTime);
		} catch (Exception e) {
		}
		PageBean pageBean = new PageBean(true, page, size, user, start, end);
		List<User> users = userDao.fetchUsersByPage(pageBean);
		Integer counts = userDao.fetchUsersCountsByParam(pageBean);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", users);
		map.put("total", counts);
		mes.setCode(Code.SUCCESS);
		mes.setMessage("users");
		mes.setResult(map);
		return mes;
	}
	
	/**
	 * 新增职工
	 * @param user
	 * @return
	 */
	public Message addUser(User user) {
		Message mes = new Message();
		try {
			checkUserParams(user);
		} catch (MyException e) {
			mes.setCode(e.getCode());
			mes.setError(e.getE());
			return mes;
		}
		user.setPassword(CharUtil.toMyStr(DigestUtils.md5DigestAsHex(AES.Encrypt(User.INTIPWD).getBytes())));
		Integer res = userDao.insert(user);
		if(res > 0) {
			mes.setCode(Code.SUCCESS);
			mes.setMessage("user");
			mes.setResult("add success");
		} else {
			mes.setCode(Code.OPTION_FAILED);
			mes.setError("user");
		}
		
		return mes;
	}
	
	/**
	 * 更新职工信息
	 * @param user
	 * @return
	 */
	public Message updateUser(User user) {
		Message mes = new Message();
		try {
			checkUserParams(user);
			if(user.getId() == null || user.getId() <= 0) {
				throw new MyException(Code.PARAM_NULL_ERROR, "userId");
			}
		} catch (MyException e) {
			mes.setCode(e.getCode());
			mes.setError(e.getE());
			return mes;
		}
		Integer res = userDao.update(user);
		if(res > 0) {
			mes.setCode(Code.SUCCESS);
			mes.setMessage("user");
			mes.setResult("update success");
		} else {
			mes.setCode(Code.OPTION_FAILED);
			mes.setError("user");
		}
		return mes;
	}
	
	/**
	 * 删除职工
	 * @param userId
	 * @return
	 */
	public Message deleteUser(Integer userId) {
		Message mes = new Message();
		if(userId == null || userId <= 0) {
			mes.setCode(Code.PARAM_NULL_ERROR);
			mes.setError("userId");
			return mes;
		}
		Integer res = userDao.delete(userId);
		if(res > 0) {
			mes.setCode(Code.SUCCESS);
			mes.setMessage("user");
			mes.setResult("delete success");
		} else {
			mes.setCode(Code.OPTION_FAILED);
			mes.setError("user");
		}
		return mes;
	}
	
	private void checkUserParams(User user) throws MyException {
		if(user == null) {
			throw new MyException(Code.PARAM_NULL_ERROR, "user");
		}
		if(CharUtil.isEmpty(user.getUsername())) {
			throw new MyException(Code.PARAM_NULL_ERROR, "username");
		}
		if(CharUtil.isEmpty(user.getRole())) {
			throw new MyException(Code.PARAM_NULL_ERROR, "role");
		}
		if(!rm.checkRole(user.getRole())) {
			throw new MyException(Code.PARAM_REGEX_ERROR, "role");
		}
		if(!CharUtil.isEmpty(user.getValidate())) {
			if(!validateUser(user)) {
				throw new MyException(Code.PARAM_REGEX_ERROR, "validate");
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(CharUtil.toMyStr(DigestUtils.md5DigestAsHex(AES.Encrypt("888888").getBytes())));
		System.out.println(CharUtil.toMyStr(AES.Encrypt("20990101")));
	}
}
