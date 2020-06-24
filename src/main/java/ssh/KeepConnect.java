package ssh;

public class KeepConnect implements Runnable {

	MultiUserBase multiUserBase;

	public KeepConnect(MultiUserBase multiUserBase) {
		super();
		this.multiUserBase = multiUserBase;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(5900000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Config.shellOutObject=new ShellOutObject(multiUserBase.getHostName(),"no");
			multiUserBase.getSsh().Command("");
			System.out.println("线程--保持链接");
		}
		
	}

}
