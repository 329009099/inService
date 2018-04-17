package com.suyin.nouser.model;


import java.io.Serializable;
import java.util.Date;

import com.suyin.system.model.Page;


/**
 * 用户评论 实体类
 * @author wx
 * @version 2015-10-9
 * @see NouserDiscuz
 * @since
 */
public class NouserDiscuz implements Serializable
{

	private static final long serialVersionUID = 4813300112623888803L;

	private Integer discussId;

	/**
	 * 产品id
	 */
	private Integer proId;

	/**
	 * 产品详情id
	 */
	private Integer detailId;

	/**
	 * 产品所属商家ID
	 */
	private Integer memberId;

	/**
	 * 一级评论parent_id是0
	 */
	private Integer parentId;

	/**
	 * 用户主键信息_非微信openid
	 */
	private String userId;

	/**
	 * 产品所属活动类型：活动类型：0 抽奖式,1人气式,2兑换式，3试用式
	 */
	private Integer expType;

	/**
	 * 用户评论内容
	 */
	private String content;

	/**
	 * 回复内容
	 */
	private String reply;

	/**
	 * 商家是否：回复 0 否 1 是
	 */
	private Integer isReply;

	/**
	 * 评价上传的图片,可外链至附件表，取内容
	 */
	private String picUrl;

	/**
	 * 0:非优选评论；1:优选评论
	 */
	private Integer isPerf;

	private Double enviScore;

	private Double serveScore;

	private String qcdScore;

	private String createTime;

	private Date updateTime;

	private String userPhone;

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Integer getDiscussId()
	{
		return discussId;
	}

	public void setDiscussId(Integer discussId)
	{
		this.discussId = discussId;
	}

	public Integer getProId()
	{
		return proId;
	}

	public void setProId(Integer proId)
	{
		this.proId = proId;
	}

	public Integer getDetailId()
	{
		return detailId;
	}

	public void setDetailId(Integer detailId)
	{
		this.detailId = detailId;
	}

	public Integer getMemberId()
	{
		return memberId;
	}

	public void setMemberId(Integer memberId)
	{
		this.memberId = memberId;
	}

	public Integer getParentId()
	{
		return parentId;
	}

	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Integer getExpType()
	{
		return expType;
	}

	public void setExpType(Integer expType)
	{
		this.expType = expType;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getReply()
	{
		return reply;
	}

	public void setReply(String reply)
	{
		this.reply = reply;
	}

	public Integer getIsReply()
	{
		return isReply;
	}

	public void setIsReply(Integer isReply)
	{
		this.isReply = isReply;
	}

	public String getPicUrl()
	{
		return picUrl;
	}

	public void setPicUrl(String picUrl)
	{
		this.picUrl = picUrl;
	}

	public Integer getIsPerf()
	{
		return isPerf;
	}

	public void setIsPerf(Integer isPerf)
	{
		this.isPerf = isPerf;
	}

	public Double getEnviScore()
	{
		return enviScore;
	}

	public void setEnviScore(Double enviScore)
	{
		this.enviScore = enviScore;
	}

	public Double getServeScore()
	{
		return serveScore;
	}

	public void setServeScore(Double serveScore)
	{
		this.serveScore = serveScore;
	}

	public String getQcdScore()
	{
		return qcdScore;
	}

	public void setQcdScore(String qcdScore)
	{
		this.qcdScore = qcdScore;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}


}
