package or.sabang.utl;

public class DmsFormalization {
	private String dmsLon;
	private String dmsLat;
	
	private String dmsLonDeg;
	private String dmsLonMin;
	private String dmsLonSec;
	
	private String dmsLatDeg;
	private String dmsLatMin;
	private String dmsLatSec;
	
	
	public void setDmsLon(String dmsLon) {
		this.dmsLon = dmsLon;
	}
	public void setDmsLat(String dmsLat) {
		this.dmsLat = dmsLat;
	}
	public void setDmsLonDeg(String string) {
		this.dmsLonDeg = string;
	}
	public void setDmsLonMin(String dmsLonMin) {
		this.dmsLonMin = dmsLonMin;
	}
	public void setDmsLonSec(String dmsLonSec) {
		this.dmsLonSec = dmsLonSec;
	}
	public void setDmsLatDeg(String dmsLatDeg) {
		this.dmsLatDeg = dmsLatDeg;
	}
	public void setDmsLatMin(String dmsLatMin) {
		this.dmsLatMin = dmsLatMin;
	}
	public void setDmsLatSec(String dmsLatSec) {
		this.dmsLatSec = dmsLatSec;
	}
	
	public String getDmsLon() {
		String[] lonParts = dmsLon.split("°|'|\\.");
		
		if(lonParts.length < 4) {
			return dmsLon;
		}else {
			String convertLon = "";
			String degree = lonParts[0];
			
			int min = Integer.parseInt(lonParts[1]);
			int sec  = Integer.parseInt(lonParts[2]);
			int miliSec  = Integer.parseInt(lonParts[3].replaceAll("[^0-9]",""));;
			
			convertLon += degree+"°";
			if(min < 10) {
				convertLon += "0"+Integer.toString(min)+"'";	
			}else {
				convertLon += Integer.toString(min)+"'";
			}
			
			if(sec < 10) {
				convertLon += "0"+Integer.toString(sec)+".";	
			}else {
				convertLon += Integer.toString(sec)+".";
			}
			
			if(miliSec < 10) {
				convertLon += "0"+Integer.toString(miliSec)+"\"E";	
			}else {
				convertLon += Integer.toString(miliSec)+"\"E";
			}
			this.dmsLon = convertLon;
			return dmsLon;
		}
	}
	
	public String getDmsLat() {
		String[] latParts = dmsLat.split("°|'|\\.");
		
		if(latParts.length < 4) {
			return dmsLat;
		}else {
			String convertLat = "";
			String degree = latParts[0];
			
			int min = Integer.parseInt(latParts[1]);
			int sec  = Integer.parseInt(latParts[2]);
			int miliSec  = Integer.parseInt(latParts[3].replaceAll("[^0-9]",""));;
			
			convertLat += degree+"°";
			if(min < 10) {
				convertLat += "0"+Integer.toString(min)+"'";	
			}else {
				convertLat += Integer.toString(min)+"'";
			}
			
			if(sec < 10) {
				convertLat += "0"+Integer.toString(sec)+".";	
			}else {
				convertLat += Integer.toString(sec)+".";
			}
			
			if(miliSec < 10) {
				convertLat += "0"+Integer.toString(miliSec)+"\"N";	
			}else {
				convertLat += Integer.toString(miliSec)+"\"N";
			}
			this.dmsLat = convertLat;
			return dmsLat;
		}
	}
	
	public String getDmsLonDeg() {
		return dmsLonDeg;
	}
	public String getDmsLonMin() {
		String convertMin = "";
		int lonMin = Integer.parseInt(dmsLonMin);
		
		if(lonMin < 10) {
			convertMin += "0"+dmsLonMin;
		}else {
			convertMin += dmsLonMin;
		}
		this.dmsLonMin = convertMin;
		return convertMin;
	}
	public String getDmsLonSec() {
		
		String[] secParts = dmsLonSec.split("\\.");
		
		int sec = Integer.parseInt(secParts[0]);
		int miliSec = Integer.parseInt(secParts[1]);
		
		String convertSec = "";
		
		if(sec < 10) {
			convertSec += "0"+Integer.toString(sec)+".";
		}else {
			convertSec += Integer.toString(sec)+".";
		}
		if(miliSec < 10) {
			convertSec += "0"+Integer.toString(miliSec);
		}else {
			convertSec += Integer.toString(miliSec);
		}
		this.dmsLonSec = convertSec;
		return convertSec;
	}
	public String getDmsLatDeg() {
		return dmsLatDeg;
	}
	public String getDmsLatMin() {
		String convertMin = "";
		int latMin = Integer.parseInt(dmsLatMin);
		
		if(latMin < 10) {
			convertMin += "0"+dmsLatMin;
		}else {
			convertMin += dmsLatMin;
		}
		this.dmsLatMin = convertMin;
		return convertMin;
	}
	public String getDmsLatSec() {
		String[] secParts = dmsLatSec.split("\\.");
		
		int sec = Integer.parseInt(secParts[0]);
		int miliSec = Integer.parseInt(secParts[1]);
		
		String convertSec = "";
		
		if(sec < 10) {
			convertSec += "0"+Integer.toString(sec)+".";
		}else {
			convertSec += Integer.toString(sec)+".";
		}
		if(miliSec < 10) {
			convertSec += "0"+Integer.toString(miliSec);
		}else {
			convertSec += Integer.toString(miliSec);
		}
		this.dmsLatSec = convertSec;
		return convertSec;
	}
	
}
