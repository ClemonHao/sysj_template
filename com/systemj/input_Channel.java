package com.systemj;

public class input_Channel extends GenericChannel{

	public boolean isPartnerPreempted(){
		return isSenderPreempted();
	}

	public void setPreempted(boolean p){
		setReceiverPreempted(p);
		if(p)
			setACK(false);
		setUpdated(true);
	}

//	@Override
	public void setACK(boolean aCK) {
		super.setACK(aCK);
		setUpdated(true);
	}

//	@Override
	public Object[] getData() {
		return new Object[] { PartnerName, new Boolean(isACK()), new Boolean(isReceiverPreempted()) };
	}

//	@Override
	public synchronized void update(Object[] obj) {
		setREQ(((Boolean)obj[1]).booleanValue());
		setSenderPreempted(((Boolean)obj[2]).booleanValue());
		setVal((obj[3]));
	}
}
