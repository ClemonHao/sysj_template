package com.systemj;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.systemj.ipc.GenericInterface;

/**
 * Manages routing
 * 
 * Must be compatible with CLDC 1.1
 * @author hpar081
 *
 */
public class InterfaceManager extends Container {
	private String ssname;
	private final LinkQueue OutQueue = new LinkQueue();
	private Vector LocalInterface = new Vector();
	private Vector allInterfaces = new Vector();
	private Hashtable cdlocation = new Hashtable();
	private Hashtable chanins = new Hashtable();
	private Hashtable cachedintf = new Hashtable();
	private Vector unsentdata = new Vector();
	private final int MAX_UNSENT_DATA = 50;
	
	
	/**
	 * Internal use
	 * @param ci
	 */
	public void addCDLocation(String ss, String cd){
		if(cdlocation.containsKey(cd))
			throw new RuntimeException("Tried to add duplicated CD to the map : "+cd);
		
		cdlocation.put(cd, ss);
	}
	
	/**
	 * Internal use
	 * @param ci
	 */
	public String getCDLocation(String cd){
		return (String)cdlocation.get(cd);
	}
	
	/**
	 * Internal use
	 * @param ci
	 */
	public void setChannelInstances(Hashtable ci){
		chanins = ci;
	}
	
	/**
	 * Internal use
	 * @param ci
	 */
	private Object getChannelInstance(String n){
		return chanins.get(n);
	}
	
	/**
	 * Internal use
	 * @param ci
	 */
	public Interconnection getInterconnection(){
		return (Interconnection)this.getChild(0);
	}
	
	/**
	 * Internal use
	 * @param ci
	 */
	public void setLocalInterface(String ssname){
		LocalInterface = this.getInterconnection().getInterfaces(ssname);
		this.ssname = ssname;
	}

	/**
	 * Internal use
	 * @param ci
	 */
	public void init(){
		ssname = this.getSubSystem().getSubSystemName();
		if(getChildSize() != 0){
			LocalInterface = this.getInterconnection().getInterfaces(ssname);
			allInterfaces = this.getInterconnection().getAllConnectedInterfaces(ssname);
		}
		
		for(int i=0;i<LocalInterface.size(); i++){
			((GenericInterface)LocalInterface.elementAt(i)).invokeReceivingThread();
		}
	}
	
	
	/**
	 * This is called from channel instances
	 * @param o - An object to be interted into the queue
	 * @return True when the channel data has been successfully pushed to the queue, false otherwise
	 */
	public boolean pushToQueue(GenericChannel o){
		if(OutQueue.isFull())
			return false;
		else
			return OutQueue.push(o);
	}

	/**
	 * This method adds a channel instance into a queue, whose data will
	 * later be sent to the remote subsystem via a link. The
	 * corresponding channel data will be retrieved using the
	 * {@link com.systemj.GenericChannel#getData()
	 * GenericChannel.getData()} method. <i>Note</i>: This method does
	 * not allow duplicate elements in the queue: the previously added
	 * channel instance is overwritten by the newly added one.
	 * 
	 * @param o
	 *            - Channel instance from which data will be retrieved
	 */
	public synchronized void addToUnsent(GenericChannel o){
		boolean there =false;
		for(int i=0;i<unsentdata.size(); i++){
			if(((GenericChannel)unsentdata.elementAt(i)).PartnerName.equals(o.PartnerName)){
				unsentdata.setElementAt(o, i);
				return;
			}
		}
	
		if(unsentdata.size() < MAX_UNSENT_DATA)
			unsentdata.addElement(o);
		else
			throw new RuntimeException("Unbounded unsent channel data detected : check XML routing table settings");

	}
	
	/**
	 * Internal use
	 * @param ci
	 */
	private boolean tryToSend(GenericChannel o){
		String destcd = (o.PartnerName).substring(0, (o.PartnerName).indexOf(".")); // CD name
		String dest = this.getCDLocation(destcd);			          // Corresponding SubSystem name

		GenericInterface gi = null;
		if(cachedintf.containsKey(dest)){
			gi = (GenericInterface)cachedintf.get(dest);
		}
		else{
			Vector l = this.getInterconnection().getConnectedInterfaces(ssname, dest);
			if(l.size() > 0){
				cachedintf.put(dest, l.elementAt(0));
				gi = (GenericInterface)l.elementAt(0);
			}
			else{
				System.err.println("SubSystem "+dest+" is not reacheable 1 : "+destcd);
				o.setPartnerPresent(false);
				return false;
			}
		}
		
		if(!gi.transmitData(o.getData())){
			Vector l = this.getInterconnection().getConnectedInterfaces(ssname, dest);
			for(int i=0;i<l.size(); i++){
				gi = (GenericInterface)l.elementAt(i);
				
				if(gi.transmitData(o.getData())){
					cachedintf.put(dest, gi);
					o.setPartnerPresent(true);
					return true;
				}
			}
			System.err.println("SubSystem "+dest+" is not reacheable 2 : "+destcd);
			o.setPartnerPresent(false);
			return false;
		}
		
		o.setPartnerPresent(true);
		return true;
	}
	
	/**
	 * this is called from GenericInterface (both threaded non-threaded)
	 * @param o
	 */
	public void forwardChannelData(Object[] o){
		GenericChannel chan = (GenericChannel)getChannelInstance(((String)o[0]));
		String destcd = ((String)o[0]).substring(0, ((String)o[0]).indexOf("."));
		if(chan == null){
//			if(this.getCDLocation(destcd).equals(ssname))
				System.out.println("The channel "+o[0]+" is not present in the current sub-system");
//			else{
//				System.out.println("Trying to re-route channel data "+o[0]);
//				this.addToUnsent(o);
//			}
		}
		else
			chan.update(o);
	}
	
	/**
	 * Internal use
	 * @param ci
	 */
	private synchronized void sendUnsent(){
		for(int i=0;i<unsentdata.size(); i++){
			boolean done = tryToSend((GenericChannel)unsentdata.elementAt(i));
			if(done){
				unsentdata.removeElementAt(i);
				i--;
			}
		}
	}

	private void transmit() {
		while (!OutQueue.isEmpty()) {
			GenericChannel o = (GenericChannel) OutQueue.pop();
			addToUnsent(o);
		}

		if (unsentdata.size() > 0) // is this safe?
			sendUnsent();
	}

	private void manageConnections(){
		for(int i=0;i<allInterfaces.size(); i++)
			((GenericInterface)allInterfaces.elementAt(i)).connect();
	}

	public void run(){
		manageConnections();
		transmit();
	}
	
	/**
	 * This method must to be called whenever the link is closed.
	 * Otherwise any channels connected to this this link may be blocked
	 * indefinitely (deadlock).
	 * 
	 * @param conSS
	 *            - Disconnected remote subsystem's name
	 */
	public synchronized void disconnect(String conSS){
		Enumeration e = cdlocation.keys();
		Vector cdnames = new Vector();
		
		// First collect all the CDnames that belong to the disconnected subsystem
		while(e.hasMoreElements()){
			String cdname = (String)e.nextElement();
			String ssname = (String)cdlocation.get(cdname);
			if(ssname.equals(conSS)){
				cdnames.addElement(cdname);
			}
		}
		
		// Now get all the channels in the current subsystem
		e = chanins.elements();
		while(e.hasMoreElements()){
			GenericChannel ch = (GenericChannel)e.nextElement();
			// CD name in the remote SS
			String cdname = (ch.PartnerName).substring(0, ((ch.PartnerName).indexOf("."))); 
			
			// Now set partnerpresent false to all the channels that were connected to the disconnected SS
			if(cdnames.contains(cdname)){
				ch.setPartnerPresent(false);
			}
		}
	}
	
	
	// For debugging purpose
	public Hashtable getcdmap() {
		return cdlocation;
	}

	public void printLocalInterface() {
		System.out.println("\nLocalInterface : ");
		System.out.println(this.LocalInterface);
	}
}


