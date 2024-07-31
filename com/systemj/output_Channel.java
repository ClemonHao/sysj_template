package com.systemj;

public class output_Channel extends GenericChannel{

	public boolean isPartnerPreempted(){
		return isReceiverPreempted();
	}

	public void setPreempted(boolean p){
		setSenderPreempted(p);
		if(p)
			setREQ(false);
		setUpdated(true);
	}

//	@Override
	public void setREQ(boolean rEQ) {
		super.setREQ(rEQ);
		setUpdated(true);
	}

//	@Override
	public synchronized void setVal(Object val) {
		super.setVal(val);
		setUpdated(true);
	}

//	@Override
	public Object[] getData() {
		return new Object[] { PartnerName, new Boolean(isREQ()), new Boolean(isSenderPreempted()), getVal() };
	}

//	@Override
	public synchronized void update(Object[] obj) {
		setACK(((Boolean)obj[1]).booleanValue());
		setReceiverPreempted(((Boolean)obj[2]).booleanValue());
	}
}
