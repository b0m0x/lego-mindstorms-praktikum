package basis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;

import lejos.nxt.Sound;
import lejos.nxt.comm.Bluetooth;
import lejos.util.TextMenu;

public class BTRaceStartClient extends Connection implements DiscoveryListener {

	/** Lock used to block this thread from entering the next level*/
	private final Object startLock = new Object();

	private List<RemoteDevice> devices;

	public BTRaceStartClient() {
		devices = new ArrayList<RemoteDevice>();
	}

	public void searchForDevices() {
		LocalDevice localDevice;
		DiscoveryAgent discoveryAgent;
		try {
			localDevice = LocalDevice.getLocalDevice();
			discoveryAgent = localDevice.getDiscoveryAgent();
			discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
		} catch (BluetoothStateException ex) {
		}
	}

	public void deviceDiscovered(RemoteDevice remoteDevice,
			DeviceClass deviceClass) {
		Logger.debug("Found new device: addr: "
				+ remoteDevice.getBluetoothAddress());
		devices.add(remoteDevice);
	}

	public void connect(RemoteDevice remoteDevice) {
		connection = Bluetooth.connect(remoteDevice);
		if (connection != null) {
			openConnection(connection);
		} else {
			Logger.error("Unable to connect to: "
					+ remoteDevice.getBluetoothAddress());
		}
	}

	public void inquiryCompleted(int arg0) {
		Logger.debug("Scan completed");
		String[] items = new String[devices.size()];
		for (int i = 0; i < devices.size(); i++) {
			RemoteDevice remoteDevice = devices.get(i);
			items[i] = "[" + remoteDevice.getFriendlyName(false) + "]"
					+ remoteDevice.getBluetoothAddress();
		}
		TextMenu menu = new TextMenu(items);

		int selection = menu.select();
		if (selection < 0) {
			abort();
			return;
		}

		RemoteDevice remoteDevice = devices.get(selection);
		if (remoteDevice != null) {
			connect(remoteDevice);
			start();
		} else {
			Logger.error("Unable to connect to a remote device");
		}
	}

	@Override
	protected void parseFlag(Flag flag) {
		Logger.error("Received flag: " + flag);
		switch (flag) {
		case GET_READY:
			File file = new File("motor_start.wav");
			if (file.exists()) {
				Sound.playSample(file, Sound.VOL_MAX);
			}
			break;
		case START:
			synchronized (startLock) {
				startLock.notify();
			}
			break;
		}
	}

	public void abort() {
		doFinally();
	}

	public void enterLevel() {
		searchForDevices();
		synchronized (startLock) {
			try {
				startLock.wait();
			} catch (InterruptedException ex) {
			}
		}
	}

	@Override
	protected void doAction() {
		try {
			while (!isStopped() && inputStream.available() < 1) {
			}
		} catch (IOException ex) {
		}
		super.doAction();
	}

}
