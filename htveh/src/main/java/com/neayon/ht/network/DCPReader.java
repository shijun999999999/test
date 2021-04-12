package com.neayon.ht.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.neayon.ht.entity.Device;
import com.neayon.ht.exception.MyException;
import com.neayon.ht.util.CharUtil;
import com.neayon.ht.util.ThreadPoolManager;

public abstract class DCPReader implements Runnable {
	private String dcpid;
	private Device device;
	private Socket client;
	protected InputStream in;
	protected OutputStream out;
	private boolean isOpen;

	public String getDcpid() {
		return dcpid;
	}

	public void setDcpid(String dcpid) {
		this.dcpid = dcpid;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, MyException {
		this.device = device;
		init();
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public OutputStream getOut() {
		return out;
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}

	public abstract void init()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, MyException;

	public void open() throws UnknownHostException, IOException {
		this.setDcpid("DCP" + this.device.getId());
		client = new Socket();
		SocketAddress endpoint = new InetSocketAddress(device.getHost(), device.getDataport());
		client.connect(endpoint, 4000);
		in = client.getInputStream();
		out = client.getOutputStream();
		ThreadPoolManager.newInstance().addExecuteTask(this);
		isOpen = true;
	}

	public void close() throws IOException, InterruptedException {
		if (isOpen) {
			in.close();
			out.close();
			client.close();
			isOpen = false;
		}
	}

	public void sendMessage(String message) {
		try {
			System.out.println("发送" + message);
			byte[] to = CharUtil.hexStringToByte(message);
			out.write(to);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
