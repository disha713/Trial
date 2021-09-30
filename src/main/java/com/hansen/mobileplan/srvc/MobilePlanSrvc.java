package com.hansen.mobileplan.srvc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hansen.mobileplan.dao.MobilePlanDao;
import com.hansen.mobileplan.model.MobilePlan;

@Service
public class MobilePlanSrvc {

	@Autowired
	MobilePlanDao mobilePlanDao;

	public Object create(MobilePlan entity) {
//		Optional<MobilePlan> person = mobilePlanDao.findById(entity.getId());
//		if (person.isPresent()) {
//			return null;
//		} else {
			MobilePlan mobilePlan = mobilePlanDao.save(entity);
			return mobilePlan;
//		}
	}

	public Object read(Long id) {
		Optional<MobilePlan> person = mobilePlanDao.findById(id);
		if (person.isPresent()) {
			return person.get();
		} else {
			return null;
		}

	}

	public Iterable<MobilePlan> readAll() {
		Iterable<MobilePlan> mobilePlanList = mobilePlanDao.findAll();
		return mobilePlanList;
	}

	public Object update(MobilePlan tobemerged) {

		// Homework... write the code to upgrade
		Optional<MobilePlan> person = mobilePlanDao.findById(tobemerged.getId());

		if (person.isPresent() && tobemerged.getDescription() != null && tobemerged.getName() != null
				&& tobemerged.getValidity() != 0) {
			MobilePlan mobilePlan = mobilePlanDao.save(tobemerged);
			return mobilePlan;
		} else {
			return null;
		}
	}

	public boolean delete(Long planid) {
		// Homework... write the code to delete
		Optional<MobilePlan> person = mobilePlanDao.findById(planid);
		System.out.println("hiiiiiiiiiiiii, I am the stupidest person to add this sysout : " + person);
		if (person.isPresent()) {
			mobilePlanDao.deleteById(planid);
			return true;
		}
		return false;
	}
}
