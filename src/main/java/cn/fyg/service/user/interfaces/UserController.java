package cn.fyg.service.user.interfaces;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.fyg.service.user.orm.jooq.tables.pojos.User;
import static cn.fyg.service.user.orm.jooq.Tables.USER;

@RestController
public class UserController {
	
	@Autowired
	DSLContext dsl;

    
	@RequestMapping(value="user",method=RequestMethod.GET)
	public List<User> allRecord(){
    	  List<User> queryResults = dsl.selectFrom(USER).fetchInto(User.class);
    	  return queryResults;
	}

}
