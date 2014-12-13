package parosenb.engine.IO;

import java.util.ArrayList;

public class Output {
	 private ArrayList<Connection> connections = new ArrayList<Connection>();
	 
	 public void connect(Connection c) { 
		 connections.add(c); 
	 }
	 
	 public void run() {
		 for (Connection c : connections){
			 c.run();
		 }
	 }
}

