package com.order;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.product.Product;
import com.product.ProductNotEnoughException;
import com.product.ProductRepository;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public void buyOneProduct(NewOrderDto newOrder) throws ProductNotFoundException {
		Optional<Product> optProduct = productRepository.findById(newOrder.getProductId());
		if (optProduct.isEmpty())
			throw new ProductNotFoundException("cannot not find this product");
		Product product = optProduct.get();
		MemberOrder order = newOrder.toOrder();
		synchronized (product) {
			if (order.getPurchased_count() > product.getCount())
				throw new ProductNotEnoughException("no more this product");
			Integer restCount = product.getCount() - order.getPurchased_count();
			product.setCount(restCount);
			productRepository.save(product);
		}
		order.setPurchased_date(new Date());
		orderRepository.save(order);
	}

	@Override
	public Page<OrderDisplayDto> queryOrders(OrderQueryDto queryDto) {
		PageRequest pageRequest = PageRequest.of(queryDto.getPage(), queryDto.getSize());
		StringBuffer countSql = new StringBuffer(
				"SELECT count(*) FROM member_order mo left join product p on p.id = mo.product_id where 1 = 1");
		StringBuffer sql = new StringBuffer(
				"SELECT mo.id, mo.product_id, mo.account_id, mo.purchased_count, mo.purchased_date FROM member_order mo left join product p on p.id = mo.product_id where 1 = 1");
		if (queryDto.getId() != null) {
			sql.append(" AND mo.id = " + queryDto.getId());
			countSql.append(" AND mo.id = " + queryDto.getId());
		}
		if (StringUtils.hasText(queryDto.getProductName())) {
			sql.append(" AND p.product_name like '%" + queryDto.getProductName() + "%' ");
			countSql.append(" AND p.product_name like '%" + queryDto.getProductName() + "%' ");
		}
		if (queryDto.getPurchasedDate() != null) {
			sql.append(" AND mo.id = " + queryDto.getPurchasedDate());
			countSql.append(" AND mo.id = " + queryDto.getPurchasedDate());
		}
		Query query = entityManager.createNativeQuery(sql.toString(), MemberOrder.class);
		query.setFirstResult(queryDto.getPage() * queryDto.getSize());
		query.setMaxResults(queryDto.getSize());
		List<MemberOrder> orders = query.getResultList();
		BigInteger count = (BigInteger) entityManager.createNativeQuery(countSql.toString()).getSingleResult();
		Page<MemberOrder> pageOrders = new PageImpl<>(orders, pageRequest, count.longValue());
		return pageOrders.map(order -> order.toOrderDisplayDto());
	}

	@Override
	public List<MemberStatisticVo> getMemberOfnumOfOrderGreaterThanN(Long n) {
		StringBuffer sql = new StringBuffer("select mem.id, mem.account, mo.count from member mem"
				+ " inner join (select account_id, count(*) as count  "
				+ "from member_order group by  account_id having count > " + n + " ) mo on mem.id = mo.account_id;");
		List<MemberStatisticVo> memberVos = jdbcTemplate.query(sql.toString(), new RowMapper<MemberStatisticVo>() {
			@Override
			public MemberStatisticVo mapRow(ResultSet rs, int rowNum) throws SQLException {
				MemberStatisticVo member = new MemberStatisticVo();
				member.setId(rs.getLong("id"));
				member.setAccount(rs.getString("account"));
				member.setCount(rs.getLong("count"));
				return member;
			}
		});
		return memberVos;
	}

}
