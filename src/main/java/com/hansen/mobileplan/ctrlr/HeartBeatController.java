package com.hansen.mobileplan.ctrlr;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getServerTime() {
		ResponseEntity<String> serverTime;
		Date currDate = new Date();
		serverTime = new ResponseEntity<String>(currDate.toString(), null, HttpStatus.OK);
		return serverTime;
	}
}
