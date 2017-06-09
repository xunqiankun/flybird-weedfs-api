package wang.flybird.api.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;
import wang.flybird.api.demo.entity.Person;
import wang.flybird.entity.FbUser;
import wang.flybird.entity.repository.FbUserRepository;
import wang.flybird.utils.idwoker.IdWorker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@ApiIgnore
public class PersonRestService {
    private static final List<Person> persons;
    @Autowired
	private FbUserRepository userRepository;
    
    @Autowired
    private IdWorker idWorker;

    static {
        persons = new ArrayList<>();
        persons.add(new Person("Hello", "World"));
        persons.add(new Person("Foo", "Bar"));
    }

    @RequestMapping(path = "/persons", method = RequestMethod.GET)
    public static List<Person> getPersons() {
        return persons;
    }

    @RequestMapping(path = "/persons/{name}", method = RequestMethod.GET)
    public static Person getPerson(@PathVariable("name") String name) {
        return persons.stream()
                .filter(person -> name.equalsIgnoreCase(person.getName()))
                .findAny().orElse(null);
    }
    @RequestMapping(path = "/persons/adduser" , method = RequestMethod.GET)
    public String adduser(){
    	FbUser user = new FbUser();
    	
        
        user.setPassword("dddd");
        user.setFirstname("dddd");
        user.setLastname("dddd");
        user.setLastPasswordResetDate(new Date());
        user.setEmail("dddd");
        user.setEnabled(true);
        
        for(int i=0;i<1000;i++){
        	 try {
     			user.setId(idWorker.getStrId());
     			user.setUsername("wangpf"+i);
     		} catch (Exception e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        	 System.out.println(i+":"+user.getId());
             userRepository.save(user);
        }
       
    	return "ok";
    }
}
