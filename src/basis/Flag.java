package basis;


public enum Flag {
	HELO, GET_READY, START;
	
	@Override
	public String toString() {
		return this.name();
	}

	public static void main(String[] args) {
		for (Flag f : values()) {
			System.out.println("public static final int " + f + " = "
					+ f.ordinal() + ";");
		}
	}

}
