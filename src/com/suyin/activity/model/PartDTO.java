package com.suyin.activity.model;

public class PartDTO {

	//参与人
	private java.lang.Integer participatorNumber;
	
	//总票数
	private java.lang.Integer  totalVotes;
	
	//总访问次数
	private java.lang.Integer totalVisits;

	public java.lang.Integer getParticipatorNumber() {
		return participatorNumber;
	}

	public void setParticipatorNumber(java.lang.Integer participatorNumber) {
		this.participatorNumber = participatorNumber;
	}

	public java.lang.Integer getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(java.lang.Integer totalVotes) {
		this.totalVotes = totalVotes;
	}

	public java.lang.Integer getTotalVisits() {
		return totalVisits;
	}

	public void setTotalVisits(java.lang.Integer totalVisits) {
		this.totalVisits = totalVisits;
	}
}
