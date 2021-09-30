package com.hansen.mobileplan.ctrlr;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hansen.mobileplan.dao.MobilePlanDao;
import com.hansen.mobileplan.model.Auditlog;
import com.hansen.mobileplan.model.MobilePlan;
import com.hansen.mobileplan.srvc.MobilePlanSrvc;

@RestController
@RequestMapping("/mp")
public class MobilePlanController {
	private Log logger = LogFactory.getLog(MobilePlanController.class);
	@Autowired
	MobilePlanSrvc mpSrvc;

	@Autowired
	MobilePlanDao mobilePlanDao;
	
	//Prady added this line for audit log Rest Client
	RestTemplate restTemplate = new RestTemplate();

	Date date = new Date();

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody MobilePlan inputentity) {
		logger.info("Inside add method");
		ResponseEntity<Object> mpResponse;
		System.out.print(date);
		Object mobilePlan = mpSrvc.create(inputentity);
		String m = "Already ID : " + inputentity.getId() + " Exist";
		if (mobilePlan != null) {
			mpResponse = new ResponseEntity<Object>(mobilePlan, null, HttpStatus.CREATED);
			//Prady added below two lines for audit log Rest Client
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("CREATED",mpResponse.getBody().toString(),date));
			restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
			
			return mpResponse;
		} else {
			mpResponse = new ResponseEntity<Object>(m, null, HttpStatus.NOT_ACCEPTABLE);
			return mpResponse;
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> read(@PathVariable(value = "id") Long id) {
		logger.info("Inside search method");
		ResponseEntity<Object> mpResponse;
		Object mobilePlan = mpSrvc.read(id);
		String m1 = "ID : " + id + " Not Found";
		if (mobilePlan != null) {
			mpResponse = new ResponseEntity<Object>(mobilePlan, null, HttpStatus.OK);
			//Prady added below two lines for audit log Rest Client
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("READ-ONE",mpResponse.getBody().toString(),date));
			restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
			
			return mpResponse;
		} else {
			mpResponse = new ResponseEntity<Object>(m1, null, HttpStatus.NOT_FOUND);
			return mpResponse;

		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<MobilePlan>> readAll() {
		logger.info("Inside readAll method");
		ResponseEntity<Iterable<MobilePlan>> mpResponse;

		Iterable<MobilePlan> mobilePlanList = mpSrvc.readAll();

		mpResponse = new ResponseEntity<Iterable<MobilePlan>>(mobilePlanList, null, HttpStatus.OK);
		//Prady added below two lines for audit log Rest Client
		HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("READ-ALL",mpResponse.getBody().toString(),date));
		restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
		
		return mpResponse;
	}

	// Homework: Write methods for update and delete below

	@RequestMapping(method = RequestMethod.PATCH) // OR PUT
	public ResponseEntity<Object> update(@RequestBody MobilePlan tobemerged) {
		logger.info("Inside update method");
		ResponseEntity<Object> planResponse;
		Object mobilePlanList = mpSrvc.update(tobemerged);
		String m = "ID : " + tobemerged.getId() + " Not Found";
		if (mobilePlanList != null) {
			planResponse = new ResponseEntity<Object>(mobilePlanList, null, HttpStatus.OK);
			
			//Prady added below two lines for audit log Rest Client
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("UPDATED",planResponse.getBody().toString(),date));
			restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
			
			return planResponse;
		} else {
			planResponse = new ResponseEntity<Object>(m, null, HttpStatus.NOT_FOUND);
			return planResponse;
		}
	}

	@RequestMapping(value = "{planid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "planid") Long planid) {
		logger.info("Inside delete method");
		ResponseEntity<Object> bookResponse;
		
		//Prady added below line for audit log
		MobilePlan mp = (MobilePlan) mpSrvc.read(planid);
		
		Boolean isDeleted = mpSrvc.delete(planid);
		String m = "ID: " + planid + " deleted successfully";
		String m1 = "ID: " + planid + " Not Found";
		if (isDeleted) {
			bookResponse = new ResponseEntity<Object>(m, null, HttpStatus.OK);
			
			//Prady added below two lines for audit log Rest Client
			HttpEntity<Auditlog> request = new HttpEntity<Auditlog>(new Auditlog("DELETED",mp.toString(),date));
			restTemplate.postForObject("http://localhost:8081/audit", request, Auditlog.class);
			
		} else {
			bookResponse = new ResponseEntity<Object>(m1, null, HttpStatus.NOT_FOUND);
		}
		return bookResponse;
	}

}
