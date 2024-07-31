package com.systemj;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.systemj.ipc.GenericInterface;

/**
 * Must be compatible with CLDC 1.1
 * 
 * May be put cdmap here?
 * 
 * @author hpar081
 */
public class Interconnection extends Container{
	private Vector LocalLinks = new Vector();
	private Vector DestLinks = new Vector();
	
	public static class Link{
		//public final String type;
		public Hashtable InterfaceMap = new Hashtable();
		public Vector Keys = new Vector();

		public void addInterface(String SS, GenericInterface gct){
			Keys.addElement(SS);
			if(InterfaceMap.containsKey(SS))
				System.err.println("WARNING : SubSystem "+SS+" overwritten in the Interconnection");
			
			InterfaceMap.put(SS, gct);
		}
	}
	
	public void printInterconnection(){
		System.out.println("Local links : ");
		for(int i=0;i<LocalLinks.size(); i++){
			System.out.println(i+":");
			Link l = (Link)LocalLinks.elementAt(i);
			Enumeration enumm = l.InterfaceMap.keys();
			while(enumm.hasMoreElements()){
				Object key = enumm.nextElement();
				System.out.println(key+" "+l.InterfaceMap.get(key));
			}
		}
		
		System.out.println("Destination links : ");
		for(int i=0;i<DestLinks.size(); i++){
			System.out.println(i+":");
			Link l = (Link)DestLinks.elementAt(i);
			Enumeration enumm = l.InterfaceMap.keys();
			while(enumm.hasMoreElements()){
				Object key = enumm.nextElement();
				System.out.println(key+" "+l.InterfaceMap.get(key));
			}
		}
	}

	public void addLink(Link link, boolean isLocal){
		if(isLocal)
			LocalLinks.addElement(link);
		else
			DestLinks.addElement(link);
	}
	
	/**
	 * @param ssname Subsystem name
	 * @return Returns an array of interfaces with the Subsystem ssname
	 */
	public Vector getInterfaces(String ssname){
		Vector l = new Vector();
		for(int i=0; i<LocalLinks.size() ; i++){
			GenericInterface gct = (GenericInterface)((Link)LocalLinks.elementAt(i)).InterfaceMap.get(ssname);
			if(gct != null)
				l.addElement(gct);
			
		}
		for(int i=0; i<DestLinks.size() ; i++){
			GenericInterface gct = (GenericInterface)((Link)DestLinks.elementAt(i)).InterfaceMap.get(ssname);
			if(gct != null)
				l.addElement(gct);
		}
		
		return l;
	}
	
	/**
	 * 
	 * @param me Local SubSystem name
	 * @param target Remote SubSystem name
	 * @return Vector array of GenericInterfaces that can be used for transmitting data.
	 */
	public Vector getConnectedInterfaces(String me, String target){
		Vector l = new Vector();
		for(int i=0;i<LocalLinks.size(); i++){
			GenericInterface local = (GenericInterface)((Link)LocalLinks.elementAt(i)).InterfaceMap.get(me);
			GenericInterface remote = (GenericInterface)((Link)LocalLinks.elementAt(i)).InterfaceMap.get(target);
			if(remote !=null && local !=null)
				l.addElement(local); // Using local (e.g. USB)
		}
		
		for(int i=0; i<DestLinks.size() ; i++){
			GenericInterface local = (GenericInterface)((Link)DestLinks.elementAt(i)).InterfaceMap.get(me);
			GenericInterface remote = (GenericInterface)((Link)DestLinks.elementAt(i)).InterfaceMap.get(target);
			if(remote !=null && local !=null)
				l.addElement(remote); // Using remote's (e.g. TCP/IP)
		}
		return l;
	}
	
	public Vector getAllConnectedInterfaces(String ssname){
		Vector l = new Vector();
		for(int i=0;i<LocalLinks.size(); i++){
			if(((Link)LocalLinks.elementAt(i)).InterfaceMap.containsKey(ssname)){
				 Enumeration e = ((Link)LocalLinks.elementAt(i)).InterfaceMap.elements();
				 while(e.hasMoreElements())
					 l.add(e.nextElement());
			}

		}
		for(int i=0; i<DestLinks.size() ; i++){
			if(((Link)DestLinks.elementAt(i)).InterfaceMap.containsKey(ssname)){
				 Enumeration e = ((Link)DestLinks.elementAt(i)).InterfaceMap.elements();
				 while(e.hasMoreElements())
					 l.add(e.nextElement());
			}
		}
		return l;
	}
	
	
}
