package com.neayon.ht.network.abs;

import com.neayon.ht.network.device.CardDevice;

public abstract class AbstractCardDevice {
	protected CardDevice cardDevice;

	public CardDevice getCardDevice() {
		return cardDevice;
	}

	public void setCardDevice(CardDevice cardDevice) {
		this.cardDevice = cardDevice;
	}

	public void init(CardDevice cardDevice) {
		this.setCardDevice(cardDevice);
	}

	public abstract void device2pc(byte[] datas) throws InterruptedException;
}
