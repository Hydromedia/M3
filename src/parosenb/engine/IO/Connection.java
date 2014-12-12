package parosenb.engine.IO;

import java.util.HashMap;

public class Connection {
	 private Input target;
	 public Connection(Input target) {
		 this.target = target;
	 }
	 private HashMap<String, String> args;
	 public void run() { 
		 target.run(args); 
	 }
}
