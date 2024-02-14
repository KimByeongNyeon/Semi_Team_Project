package semiTeamProject.Model;

public class BrowserRatioVO {
	
	private String browser;
	private int count;
	private double ratio;
	
	public BrowserRatioVO() {
		
	}
	
	public BrowserRatioVO(String browser) {
		this.browser = browser;
	}
	
	public BrowserRatioVO(String browser, int count) {
		this.browser = browser;
		this.count = count;
	}
	
	public BrowserRatioVO(String browser, double ratio) {
		this.browser = browser;
		this.ratio = ratio;
	}
	
	public BrowserRatioVO(String browser, int count, double ratio) {
		this.browser = browser;
		this.count = count;
		this.ratio = ratio;
	}

	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

} // class
