package com.dpkbinwal.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
	//url based
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Bob","Charlie"));
	}
	
	
	//parameter based
	@GetMapping(path="/person", params="version=1")
	public PersonV1 getFirstVersionOfPersonRequestParameter() {
		return new PersonV1("Bob Charlie");
	}
	@GetMapping(path="/person", params="version=2")
	public PersonV2 getSecondVersionOfPersonRequestParameter() {
		return new PersonV2(new Name("Bob","Charlie"));
	}
	
	//header based
		@GetMapping(path="/person/header", headers="X-API-VERSION=1")
		public PersonV1 getFirstVersionOfPersonRequestHeader() {
			return new PersonV1("Bob Charlie");
		}
		@GetMapping(path="/person/header", headers="X-API-VERSION=2")
		public PersonV2 getSecondVersionOfPersonRequestHeader() {
			return new PersonV2(new Name("Bob","Charlie"));
		}
		//media based
		@GetMapping(path="/person/accept", produces="application/vnd.company.app-v1+json")
		public PersonV1 getFirstVersionOfPersonRequestProduces() {
			return new PersonV1("Bob Charlie");
		}
		@GetMapping(path="/person/accept", produces="application/vnd.company.app-v2+json")
		public PersonV2 getSecondVersionOfPersonRequestProduces() {
			return new PersonV2(new Name("Bob","Charlie"));
		}
	
	
	
}
