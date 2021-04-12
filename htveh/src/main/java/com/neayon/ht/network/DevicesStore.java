package com.neayon.ht.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.neayon.ht.dao.DeviceDao;
import com.neayon.ht.entity.Device;
import com.neayon.ht.exception.MyException;
import com.neayon.ht.network.device.CardDevice;

@Service("DevicesStore")
public class DevicesStore implements InitializingBean {
	private Logger log = LoggerFactory.getLogger(DevicesStore.class);
	private Map<String, DCPReader> devices = new HashMap<String, DCPReader>();
	private ExecutorService exs = Executors.newSingleThreadExecutor();
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private DeviceDao deviceDao;

	@Override
	public void afterPropertiesSet() {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		List<Device> devices = deviceDao.getAllDevices();
		for (Device device : devices) {
			if (device.getDeviceType() == Device.DEVICE_CARD) {
				CardDevice cardDevice = (CardDevice) wac.getBean("CardDevice");
				try {
					cardDevice.setDevice(device);
					if (!open(cardDevice)) {
						continue;
					}
					this.setDevice(cardDevice);
					log.info("刷卡设备开启成功");
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | MyException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean open(final DCPReader device) {
		FutureTask<Boolean> ft = new FutureTask<Boolean>(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				device.open();
				return true;
			}
		});
		exs.execute(ft);
		boolean res = false;
		try {
			res = ft.get(2, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			ft.cancel(true);
			log.info(device.getDevice().getName() + " 打开超时");
		} finally {
			ft.cancel(true);
		}
		return res;
	}

	public void setDevice(DCPReader dcp) {
		this.devices.put(dcp.getDcpid(), dcp);
	}

	public DCPReader getDevice(String key) {
		return this.devices.get(key);
	}
}
