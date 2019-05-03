package com.model2.mvc.service.purchase.impl;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;



//==> ȸ������ DAO CRUD ����
@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}
	

	public void addPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}

	public Purchase getPurchase(int tranNo) throws Exception{
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}
	
	public Purchase getPurchase2(int prodNo) throws Exception{
		return sqlSession.selectOne("PurchaseMapper.getPurchase", prodNo);
	}

	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception{
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}
	

	public List<Purchase> getPurchaseList(Search search,String userId) throws Exception{
	return sqlSession.selectList("PurchaseMapper.getPurchaseList",search);
	}

	public List<Purchase> getSaleList(Search search) throws Exception{
	return sqlSession.selectList("PurchaseMapper.getSaleList",search);
	}
	
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
		public int getTotalCount(Search search) throws Exception {
			return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
		}
	}
	
