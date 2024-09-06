package run;
import org.compsys704.*;

public class GUI implements java.lang.Runnable {
	public void run() {
		//org.compsys704.CapLoader.main(null);
		//org.compsys704.TestClass.main_1(null);
        TestClass t = new TestClass();
        t.run();
	}
}
