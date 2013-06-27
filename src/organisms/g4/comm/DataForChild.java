package organisms.g4.comm;

public class DataForChild {
	private int parentLocation = -1;
	private final int DOP_WIDTH = 2;
	
	private int originX;
	private int originY;
	private final int LOCATION_WIDTH = 8;
	
	private final int ADD_TO_LOCATION = (int)Math.pow(2,LOCATION_WIDTH) / 2 - 1;
	
	private int turnNumber;	
	
	private DataForChild(int encodedData) {
		parentLocation = encodedData & getMask(DOP_WIDTH);
		encodedData  >>>= DOP_WIDTH;
		
		originX = encodedData & getMask(LOCATION_WIDTH);
		encodedData >>>= LOCATION_WIDTH;
		
		originY = encodedData & getMask(LOCATION_WIDTH);
		encodedData >>>= LOCATION_WIDTH;
		
		setTurnNumber(encodedData);
	}
	
	public DataForChild(int dop, int ox, int oy, int turnNumber) {
		setParentLocation(dop);
		setOriginX(ox);
		setOriginY(oy);
		setTurnNumber(turnNumber);
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
		case 7:
			return 0x7F;
		case 8:
			return 0xFF;
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
		output = getTurnNumber();
		
		output <<= LOCATION_WIDTH;	
		output += originY;
		
		output <<= LOCATION_WIDTH;		
		output += originX;
		
		output <<= DOP_WIDTH;
		output += parentLocation;

		return output;
	}
	
	public static DataForChild decode(int data) {
		return new DataForChild(data);
	}

	public int getParentLocation() {
		return parentLocation + 1;
	}

	public void setParentLocation(int parentLocation) {
		if (parentLocation > 4 || parentLocation < 1) {
			throw new IndexOutOfBoundsException();
		}
		this.parentLocation = parentLocation - 1;
	}

	public int getOriginX() {
		//return originX;
		return subtract(originX);
	}

	public void setOriginX(int originX) {
		this.originX = add(originX);
	}

	public int getOriginY() {
		//return originY;
		return subtract(originY);
	}

	public void setOriginY(int originY) {
		this.originY = add(originY);
	}

	
	public boolean equals(Object o) {
		if (o instanceof DataForChild) {
			DataForChild other = (DataForChild) o;
			return getOriginX() == other.getOriginX() && getOriginY() == other.getOriginY() 
					&& getParentLocation() == other.getParentLocation()
					&& getTurnNumber() == other.getTurnNumber();
		}
		return false;
	}
	
	public String toString() {
		return "Parent Direction: " + getParentLocation() + 
				" OX: " + getOriginX() + 
				" OY: " + getOriginY() +
				" Turn Number: " + getTurnNumber();
				
	}
	
	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}
}
