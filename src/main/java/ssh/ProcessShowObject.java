package ssh;

public class ProcessShowObject {
	public ProcessShowObject(String uSER, String pID, String cPU, String mEM, String vSZ, String rSS, String tTY,
			String sTAT, String sTART, String tIME, String cOMMAND) {
		super();
		USER = uSER;
		PID = pID;
		CPU = cPU;
		MEM = mEM;
		VSZ = vSZ;
		RSS = rSS;
		TTY = tTY;
		STAT = sTAT;
		START = sTART;
		TIME = tIME;
		COMMAND = cOMMAND;
	}
	//USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
	private String USER;
	private String PID;
	private String CPU;
	private String MEM;
	private String VSZ;
	private String RSS;
	private String TTY;
	private String STAT;
	private String START;
	private String TIME;
	private String COMMAND;
	/**
	 * @return the uSER
	 */
	public String getUSER() {
		return USER;
	}
	/**
	 * @param uSER the uSER to set
	 */
	public void setUSER(String uSER) {
		USER = uSER;
	}
	/**
	 * @return the pID
	 */
	public String getPID() {
		return PID;
	}
	/**
	 * @param pID the pID to set
	 */
	public void setPID(String pID) {
		PID = pID;
	}
	/**
	 * @return the cPU
	 */
	public String getCPU() {
		return CPU;
	}
	/**
	 * @param cPU the cPU to set
	 */
	public void setCPU(String cPU) {
		CPU = cPU;
	}
	/**
	 * @return the mEM
	 */
	public String getMEM() {
		return MEM;
	}
	/**
	 * @param mEM the mEM to set
	 */
	public void setMEM(String mEM) {
		MEM = mEM;
	}
	/**
	 * @return the vSZ
	 */
	public String getVSZ() {
		return VSZ;
	}
	/**
	 * @param vSZ the vSZ to set
	 */
	public void setVSZ(String vSZ) {
		VSZ = vSZ;
	}
	/**
	 * @return the rSS
	 */
	public String getRSS() {
		return RSS;
	}
	/**
	 * @param rSS the rSS to set
	 */
	public void setRSS(String rSS) {
		RSS = rSS;
	}
	/**
	 * @return the tTY
	 */
	public String getTTY() {
		return TTY;
	}
	/**
	 * @param tTY the tTY to set
	 */
	public void setTTY(String tTY) {
		TTY = tTY;
	}
	/**
	 * @return the sTAT
	 */
	public String getSTAT() {
		return STAT;
	}
	/**
	 * @param sTAT the sTAT to set
	 */
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	/**
	 * @return the sTART
	 */
	public String getSTART() {
		return START;
	}
	/**
	 * @param sTART the sTART to set
	 */
	public void setSTART(String sTART) {
		START = sTART;
	}
	/**
	 * @return the tIME
	 */
	public String getTIME() {
		return TIME;
	}
	/**
	 * @param tIME the tIME to set
	 */
	public void setTIME(String tIME) {
		TIME = tIME;
	}
	/**
	 * @return the cOMMAND
	 */
	public String getCOMMAND() {
		return COMMAND;
	}
	/**
	 * @param cOMMAND the cOMMAND to set
	 */
	public void setCOMMAND(String cOMMAND) {
		COMMAND = cOMMAND;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MiningShowObject [USER=" + USER + ", PID=" + PID + ", CPU=" + CPU + ", MEM=" + MEM + ", VSZ=" + VSZ
				+ ", RSS=" + RSS + ", TTY=" + TTY + ", STAT=" + STAT + ", START=" + START + ", TIME=" + TIME
				+ ", COMMAND=" + COMMAND + "]";
	}
	
}
