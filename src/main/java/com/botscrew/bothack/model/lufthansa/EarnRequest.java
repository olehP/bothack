package com.botscrew.bothack.model.lufthansa;

public class EarnRequest {
	private int pid = 0;
	private String cardNo;
	private String action = "credit";
	private String property = "MT002";
	private int miles;
	private String activityDate = "19.11.2016";
	private String transactionText = "Bot hack";
	private String additionalPartnerData = "string";

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public int getMiles() {
		return miles;
	}

	public void setMiles(int miles) {
		this.miles = miles;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public String getTransactionText() {
		return transactionText;
	}

	public void setTransactionText(String transactionText) {
		this.transactionText = transactionText;
	}

	public String getAdditionalPartnerData() {
		return additionalPartnerData;
	}

	public void setAdditionalPartnerData(String additionalPartnerData) {
		this.additionalPartnerData = additionalPartnerData;
	}

}
