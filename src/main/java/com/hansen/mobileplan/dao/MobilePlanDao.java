package com.hansen.mobileplan.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.hansen.mobileplan.model.MobilePlan;

public interface MobilePlanDao extends PagingAndSortingRepository<MobilePlan, Long> {

	// @Query(value="select * from mobile_plan where id=:id",nativeQuery=true)
	// MobilePlan getall(@Param("id")Integer id);
}