/**
 * Copyright 2015-现在 广州市领课网络科技有限公司
 */
package com.roncoo.recharge.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.roncoo.recharge.common.dao.OrderTradeDao;
import com.roncoo.recharge.common.dao.impl.mapper.OrderTradeMapper;
import com.roncoo.recharge.common.entity.OrderTrade;
import com.roncoo.recharge.common.entity.OrderTradeExample;
import com.roncoo.recharge.util.base.AbstractBaseJdbc;
import com.roncoo.recharge.util.bjui.Page;
import com.roncoo.recharge.util.bjui.PageUtil;

@Repository
public class OrderTradeDaoImpl extends AbstractBaseJdbc implements OrderTradeDao {
	@Autowired
	private OrderTradeMapper orderTradeMapper;

	@Override
	@Transactional
	public Long save(OrderTrade record) {
		record.setGmtCreate(new Date());
		record.setGmtModified(new Date());
		this.orderTradeMapper.insertSelective(record);
		return getLastId();
	}

	@Override
	public int deleteById(Long id) {
		return this.orderTradeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateById(OrderTrade record) {
		record.setGmtModified(new Date());
		return this.orderTradeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrderTrade getById(Long id) {
		return this.orderTradeMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<OrderTrade> listForPage(int pageCurrent, int pageSize, OrderTradeExample example) {
		int count = this.orderTradeMapper.countByExample(example);
		pageSize = PageUtil.checkPageSize(pageSize);
		pageCurrent = PageUtil.checkPageCurrent(count, pageSize, pageCurrent);
		int totalPage = PageUtil.countTotalPage(count, pageSize);
		example.setLimitStart(PageUtil.countOffset(pageCurrent, pageSize));
		example.setPageSize(pageSize);
		return new Page<OrderTrade>(count, totalPage, pageCurrent, pageSize, this.orderTradeMapper.selectByExample(example));
	}

	@Override
	public OrderTrade getByOrderNoAndUserInfoId(String orderNo, Long userInfoId) {
		OrderTradeExample example = new OrderTradeExample();
		example.createCriteria().andOrderNoEqualTo(orderNo).andUserInfoIdEqualTo(userInfoId);
		List<OrderTrade> list = this.orderTradeMapper.selectByExample(example);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public OrderTrade getByTradeNo(Long tradeNo) {
		OrderTradeExample example = new OrderTradeExample();
		example.createCriteria().andTradeNoEqualTo(tradeNo);
		List<OrderTrade> list = this.orderTradeMapper.selectByExample(example);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
