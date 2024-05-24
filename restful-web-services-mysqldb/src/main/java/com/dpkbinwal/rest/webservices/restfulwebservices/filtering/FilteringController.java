package com.dpkbinwal.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController

public class FilteringController {

//	@GetMapping("/filtering")
//	public SomeBean filtering() {
//		return new SomeBean("value1","value 2","value3");
//	}
//
//	@GetMapping("/filtering-list")
//	public List<SomeBean> filteringList() {
//		return Arrays.asList(new SomeBean("value1","value 2","value3"),new SomeBean("value-1","value-2","value-3"));
//	}
	
	//dynamic filtering
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
	 SomeBean someBean = new SomeBean("value1","value 2","value 3");
	 
	 MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
	 
	 SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field1");
	FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
	mappingJacksonValue.setFilters(filters);
	
	 return mappingJacksonValue;
	}

	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		 List<SomeBean> list = Arrays.asList(new SomeBean("value1","value 2","value3"),new SomeBean("value-1","value-2","value-3"));
	
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		 
		 SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field2","field1");
			FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
			mappingJacksonValue.setFilters(filters);
		
		
		 return mappingJacksonValue;
	}
	
	
}
