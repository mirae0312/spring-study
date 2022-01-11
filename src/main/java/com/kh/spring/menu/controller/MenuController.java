package com.kh.spring.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/menu")
public class MenuController {

	
	@GetMapping("/menu.do")
	public void menu() {}
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@GetMapping("/selectAllMenu.do")
	@ResponseBody
	public Resource selectAllMenu() {
		
		String location = "http://localhost:10000/springboot/menus";
		Resource resource = resourceLoader.getResource(location); // json
		log.debug("resource = {}", resource);
		
		return resource;
	}
}
