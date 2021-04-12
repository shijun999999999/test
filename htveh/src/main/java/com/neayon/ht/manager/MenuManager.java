package com.neayon.ht.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neayon.ht.bean.Message;
import com.neayon.ht.common.Code;
import com.neayon.ht.dao.MenuDao;
import com.neayon.ht.dao.PayModelDao;
import com.neayon.ht.entity.Menu;
import com.neayon.ht.entity.PayModel;
import com.neayon.ht.entity.User;

@Service("MenuManager")
public class MenuManager extends BaseManager {
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private PayModelDao payModelDao;

	/**
	 * 获取菜单
	 * 
	 * @return
	 */
	public Message getMenus() {
		Message mes = new Message();
		User user = getUserStored();
		String functionPoints = user.getFunctionPoint();
		mes.setCode(Code.SUCCESS);
		mes.setMessage("menu fetch success");
		List<Menu> truMenus = new ArrayList<Menu>();
		if (functionPoints != null && !functionPoints.equals("")) {
			List<Menu> menus = menuDao.fetchMenus();
			if (menus != null && menus.size() > 0) {
				for (int i = 0; i < menus.size(); i++) {
					if (functionPoints.contains(menus.get(i).getCode())) {
						truMenus.add(menus.get(i));
					}
				}
			}
		}
		mes.setResult(resortMenus(truMenus));
		return mes;
	}

	/**
	 * 重装菜单
	 * 
	 * @param menus
	 */
	private List<Menu> resortMenus(List<Menu> menus) {
		List<Menu> newMenus = new ArrayList<Menu>();
		for (Menu pm : menus) {
			if (pm.getPid() == 0) {
				newMenus.add(pm);
				for (Menu cm : menus) {
					if (cm.getPid().intValue() == pm.getId().intValue()) {
						if (pm.getChilds() == null) {
							List<Menu> list = new ArrayList<Menu>();
							list.add(cm);
							pm.setChilds(list);
						} else {
							pm.getChilds().add(cm);
						}
					}
				}
			}
		}
		return newMenus;
	}
	
	/**
	 * 获取付费模板
	 * @param vehType
	 * @return
	 */
	public Message getPayModels(String vehType) {
		Message mes = new Message();
		if(vehType == null) {
			vehType = "";
		}
		List<PayModel> models = payModelDao.fetchModels();
		for(PayModel model : models) {
			if(!model.getVehType().contains(vehType)) {
				model.setCostOriginal(PayModel.DEFAULT_PAY);
				model.setCostActual(PayModel.DEFAULT_PAY);
			}
		}
		mes.setCode(Code.SUCCESS);
		mes.setMessage("payModels");
		mes.setResult(models);
		return mes;
	}
}
