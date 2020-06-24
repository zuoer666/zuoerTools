package ssh;

public class CommonCommandsObject {
	String command;
	String remark;
	public CommonCommandsObject(String command, String remark) {
		super();
		this.command = command;
		this.remark = remark;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "CommonCommandsObject [command=" + command + ", remark=" + remark + "]";
	}
	
	
}
