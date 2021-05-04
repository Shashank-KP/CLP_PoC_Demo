package org.clp.xslt.sendvehiclecoordinates.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RestRequestVehiclesCoordinates")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RestRequestVehiclesCoordinates {
	 
	public RestRequestVehiclesCoordinates() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String vID;
	private double xPosition;
    private double yPosition;
	     
    	@XmlAttribute
	    public String getvID() {
			return vID;
		}
		public void setvID(String vID) {
			this.vID = vID;
		}
		
		@XmlAttribute
		public double getxPosition() {
			return xPosition;
		}
		public void setxPosition(double xPosition) {
			this.xPosition = xPosition;
		}
		
		@XmlAttribute
		public double getyPosition() {
			return yPosition;
		}
		public void setyPosition(double yPosition) {
			this.yPosition = yPosition;
		}
		
	    public RestRequestVehiclesCoordinates(String vID, double xPosition, double yPosition) {
			super();
			this.vID = vID;
			this.xPosition = xPosition;
			this.yPosition = yPosition;
		}
}
