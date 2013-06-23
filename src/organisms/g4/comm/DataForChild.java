package organisms.g4.comm;

public class DataForChild {
	private int parentLocation = -1;
	private final int DOP_WIDTH = 2;
	
	
	private boolean parentWantsToComunicate = false;
	private final int PWTC_WIDTH = 1;
	
	private int originX;
	private int originY;
	private final int LOCATION_WIDTH = 16;
	
	private final int ADD_TO_LOCATION = 2^(LOCATION_WIDTH -1) / 2 - 1;
	
	private DataForChild(int encodedData) {
		setParentLocation(encodedData & getMask(DOP_WIDTH));
		encodedData  >>>= DOP_WIDTH;
	
		setParentWantsToComunicate((encodedData & getMask(PWTC_WIDTH)) != 0);
		encodedData >>>= PWTC_WIDTH;
		
		originX = encodedData & getMask(LOCATION_WIDTH);
		encodedData >>>= LOCATION_WIDTH;
		
		originY = encodedData & getMask(LOCATION_WIDTH);
		encodedData >>>= LOCATION_WIDTH;
	}
	
	public DataForChild(int dop, boolean pwc, int ox, int oy) {
		setParentLocation(dop);
		setParentWantsToComunicate(pwc);
		setOriginX(ox);
		setOriginY(oy);
	}
	
	public int getMask(int width) {
		switch(width) {
		case 1:
			return 0x1;
		case 2:
			return 0x3;
		case 3:
			return 0x7;
		case 4:
			return 0xF;
		case 16:
			return 0xFFFF;
		default:
			throw new RuntimeException();
		}
	}
	
	public int add(int x) {
		return x + ADD_TO_LOCATION;
	}
	
	public int subtract(int x) {
		return x - ADD_TO_LOCATION;
	}
	
	public int encode() {
		int output = 0;
		
		output <<= LOCATION_WIDTH;	
		output += originY;
		
		output <<= LOCATION_WIDTH;		
		output += originX;
		
		output <<= PWTC_WIDTH;
		if (parentWantsToComunicate())
			output += 1;
		
		output <<= DOP_WIDTH;
		output += getParentLocation();

		return output;
	}
	
	public static DataForChild decode(int data) {
		return new DataForChild(data);
	}

	public int getParentLocation() {
		return parentLocation;
	}

	public void setParentLocation(int parentLocation) {
		this.parentLocation = parentLocation;
	}

	public boolean parentWantsToComunicate() {
		return parentWantsToComunicate;
	}

	public void setParentWantsToComunicate(boolean parentWantsToComunicate) {
		this.parentWantsToComunicate = parentWantsToComunicate;
	}

	public int getOriginX() {
		return subtract(originX);
	}

	public void setOriginX(int originX) {
		this.originX = add(originX);
	}

	private int getOriginY() {
		return subtract(originY);
	}

	private void setOriginY(int originY) {
		this.originY = add(originY);
	}

	
	public boolean equals(Object o) {
		if (o instanceof DataForChild) {
			DataForChild other = (DataForChild) o;
			return getOriginX() == other.getOriginX() && getOriginY() == other.getOriginY() 
					&& parentWantsToComunicate() == other.parentWantsToComunicate()
					&& getParentLocation() == other.getParentLocation();
		}
		return false;
	}
	
	public String toString() {
		return "Parent Direction: " + getParentLocation() + 
				" Parent Coms: " +  parentWantsToComunicate() +
				" OX: " + getOriginX() + 
				" OY: " + getOriginY();
				
	}
}
