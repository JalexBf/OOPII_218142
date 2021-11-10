package alex;

public enum Criteria {
	cafe(0), bar(1), restaurant(2), meuseum(3), sea(4), theater(5), cinema(6);
	private final int idx;
	
	Criteria(int idx){
		this.idx= idx;
	}
	public int idx() {
		return idx;
	}
}
