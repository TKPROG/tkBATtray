package de.tkprog.tkBATtray.main;

public class Logger {
	
	private String path;

	public Logger(String path){
		setPath(path);
		if(path != null){
			openStream(path);
		}
	}
	
	
	
	private void openStream(String path) {
		
	}

	public String getPath() {
		return path;
	}

	private void setPath(String path) {
		this.path = path;
	}
}
