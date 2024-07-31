package com.systemj;

public abstract class GenericChannel extends Container {

	public static final int UNPAIRED = 0;
	public static final int PAIRED   = 1;
	
	protected SharedPorts ports = new SharedPorts();
	protected boolean updated = false;
	protected boolean init = false;
	protected boolean isLocal = false;
	

	public String PartnerName;
	public String Name;

	public GenericChannel() { }

	public boolean isLocal() {
		return isLocal;
	}

	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit() {
		init = true;
	}

	public boolean isACK() {
		return ports.ack;
	}

	public void setACK(boolean aCK) {
		ports.ack = aCK;
	}

	public synchronized boolean isREQ() {
		return ports.req;
	}

	public void setREQ(boolean rEQ) {
		ports.req = rEQ;
	}

	public Object getVal() {
		return ports.val;
	}

	public synchronized void setVal(Object val) {
		ports.val = val;
		if(this instanceof output_Channel){
			ports.req = true;
		}
	}

	public static void setPartner(input_Channel in, output_Channel out){
		in.setLocal(true);
		out.setLocal(true);
		SharedPorts p = new SharedPorts();
		p.partnerPresent = true;
		in.ports = p;
		out.ports = p;
	}

	public boolean isPartnerPresent(){
		return this.ports.partnerPresent;
	}

	public void setPartnerPresent(boolean p){
		this.ports.partnerPresent = p;
	}

	public boolean isSenderPreempted(){
		return ports.senderPreempted;
	}

	public void setSenderPreempted(boolean p){
		ports.senderPreempted = p;
	}

	public boolean isReceiverPreempted(){
		return ports.receiverPreempted;
	}

	public void setReceiverPreempted(boolean r){
		ports.receiverPreempted = r;
	}

	public void setUpdated(boolean p){
		updated = p;
	}

	public boolean isUpdated(){
		return updated;
	}


	/* ------------------------------------------------------- */

	public abstract void update(Object[] obj);

	public void gethook(){ /* Just here for compatibility issue */}

	public void sethook(){
		if(!isLocal()){
			if(isUpdated() || !isPartnerPresent()){
				InterfaceManager im = getInterfaceManager();
				if(im.pushToQueue(this))
					setUpdated(false);
			}
		}
	}

	protected InterfaceManager getInterfaceManager() {
		Container im = this;
		while(!(im instanceof SystemJProgram)){
			im = im.getParent();
		}
		return ((SystemJProgram)im).getInterfaceManager();
	}

	public abstract Object[] getData();

}



