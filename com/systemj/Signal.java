package com.systemj;

import java.util.*;

import com.systemj.ipc.GenericSignalReceiver;
import com.systemj.ipc.GenericSignalSender;

public class Signal extends Container {
	private boolean status = false;
	public Object value = null;
	public Object pre_val = null;
	public boolean pre_status = false;
	private Signal partner = null;
	private GenericSignalReceiver server;
	private GenericSignalSender client;
	private Object[] toSend = new Object[2];
	private Object[] toReceive = new Object[2];
	private boolean init = false;
	private String name;
	private int dir = INTERNAL;
	public static final int INPUT = 1;
	public static final int OUTPUT = 2;
	public static final int INTERNAL = 0;

	public Signal() {
	}

	public Signal(String n, int d) {
		name = n;
		dir = d;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit() {
		init = true;
	}

	public Signal(GenericSignalReceiver s, GenericSignalSender c) {
		this.server = s;
		this.client = c;
	}

	public void setServer(GenericSignalReceiver gsr) {
		server = gsr;
	}

	public void setClient(GenericSignalSender gss) {
		client = gss;
	}

	public void setuphook() {
		try {
			new Thread(server).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gethook() {
		if (server != null) {
			server.getBuffer(toReceive);
			if (((Boolean) toReceive[0]).booleanValue()) {
				this.status = true;
				this.value = toReceive[1];
			} else
				this.status = false;
		}
	}

	public void sethook() {
		if (client != null) {
			if (status) {
				toSend[0] = Boolean.TRUE;
				toSend[1] = value;
				if (client.setup(toSend))
					client.run();
			} else {
				client.arun();
			}
		}
	}

	public void setPresent() {
		this.status = true;
	}

	public void setClear() {
		this.status = false;
	}

	public boolean getStatus() {
		return status;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public Object getpreval() {
		return pre_val;
	}

	public void setpreval(Object ob) {
		pre_val = ob;
		if (partner != null)
			partner.pre_val = ob;
	}

	public int setprepresent() {
		pre_status = true;
		if (partner != null)
			partner.pre_status = true;
		return 0;
	}

	public int setpreclear() {
		pre_status = false;
		if (partner != null)
			partner.pre_status = false;
		return 0;
	}

	public boolean getprestatus() {
		return pre_status;
	}
}