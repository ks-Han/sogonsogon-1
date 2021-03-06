package com.kh.sogon.roomboard.model.vo;

import java.sql.Timestamp;

public class RoomBoardReply {
	private int replyNo; // 댓글 번호
	private String replyContent; // 댓글 내용
	private int parentBoardNo; // 댓글이 작성된 게시글 번호
	private int parentReplyNo; // 부모 댓글 번호
	private int replyDepth; // 댓글 깊이
	private String memberId; // 댓글 작성 회원
	private Timestamp replyCreateDate; // 댓글 작성일
	private Timestamp replyModifyDate; // 댓글 수정일
	private String replyStatus; // 댓글 상태
	private int replyWriter; // 멤버No
	private int roomNo;

	public RoomBoardReply() {
		// TODO Auto-generated constructor stub
	}

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public int getParentBoardNo() {
		return parentBoardNo;
	}

	public void setParentBoardNo(int parentBoardNo) {
		this.parentBoardNo = parentBoardNo;
	}

	public int getParentReplyNo() {
		return parentReplyNo;
	}

	public void setParentReplyNo(int parentReplyNo) {
		this.parentReplyNo = parentReplyNo;
	}

	public int getReplyDepth() {
		return replyDepth;
	}

	public void setReplyDepth(int replyDepth) {
		this.replyDepth = replyDepth;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Timestamp getReplyCreateDate() {
		return replyCreateDate;
	}

	public void setReplyCreateDate(Timestamp replyCreateDate) {
		this.replyCreateDate = replyCreateDate;
	}

	public Timestamp getReplyModifyDate() {
		return replyModifyDate;
	}

	public void setReplyModifyDate(Timestamp replyModifyDate) {
		this.replyModifyDate = replyModifyDate;
	}

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public int getReplyWriter() {
		return replyWriter;
	}

	public void setReplyWriter(int replyWriter) {
		this.replyWriter = replyWriter;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	@Override
	public String toString() {
		return "RoomBoardReply [replyNo=" + replyNo + ", replyContent=" + replyContent + ", parentBoardNo="
				+ parentBoardNo + ", parentReplyNo=" + parentReplyNo + ", replyDepth=" + replyDepth + ", memberId="
				+ memberId + ", replyCreateDate=" + replyCreateDate + ", replyModifyDate=" + replyModifyDate
				+ ", replyStatus=" + replyStatus + ", replyWriter=" + replyWriter + ", roomNo=" + roomNo + "]";
	}

}
