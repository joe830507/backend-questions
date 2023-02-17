package com.order;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<MemberOrder, Long> {
	@Query(nativeQuery = true, value = "SELECT mo.* FROM member_order mo left join product p on p.id = mo.product_id "
			+ "where mo.id = ?1 or p.product_name like '%?2%' or mo.purchased_date = ?3", countQuery = "SELECT count(mo.*) FROM member_order mo left join product p on p.id = mo.product_id "
					+ "where mo.id = ?1 or p.product_name like '%?2%' or mo.purchased_date = ?3")
	Page<MemberOrder> findByIdOrLikeProductNameOrByPurchasedDate(Long id, String productName, Date purchasedDate,
			Pageable pageable);
}
