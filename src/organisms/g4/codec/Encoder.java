package organisms.g4.codec;

public class Encoder {
	byte[] data;
	int index;
	
	public int setStatus() {
		return 0;
	}
	
	
	private Encoder(byte[] d) {
		data = d;
		index = 0;
	}
	
	public boolean hasDataToSend() {
		return false;
	}

}
