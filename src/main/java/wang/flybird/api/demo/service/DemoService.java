package wang.flybird.api.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wang.flybird.api.demo.repository.DemoRepository;

@Service
public class DemoService {

	@Autowired
	DemoRepository demoRepository;
	
}
