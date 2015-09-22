package cn.fyg.service.user.interfaces;

import static cn.fyg.service.user.orm.jooq.Tables.USER;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.domain.user.UserDto;
import cn.fyg.service.user.domain.user.UserService;
import cn.fyg.service.user.orm.jooq.tables.pojos.User;
import cn.fyg.service.user.orm.jooq.tables.records.UserRecord;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	DSLContext dsl;
	@Autowired
	UserService userService;
    
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<UserDto>> list() {
		List<UserDto> users =  dsl.selectFrom(USER).fetchInto(UserDto.class);
		if(users.isEmpty()){
			return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Retv<Long>> create(@RequestBody User user, 	UriComponentsBuilder ucBuilder) {
		Retv<Long> retv = this.userService.register(user.getUname(), user.getUemail(), user.getUphone(), user.getPassword(),user.getRealname());
	
		if (retv.fail()) {
			System.out.println(retv.resaon());
			return new ResponseEntity<Retv<Long>>(retv,HttpStatus.CONFLICT);
		}
	
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(retv.data()).toUri());
		return new ResponseEntity<Retv<Long>>(retv,headers, HttpStatus.CREATED);
	}
	
	//@RequestMapping(value = "/changestate", method = RequestMethod.POST)
	
	

	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long fyid) {
		System.out.println("Fetching User with id " + fyid);
		UserRecord userRecord = dsl.fetchOne(USER,USER.FYID.equal(fyid));
		if (userRecord == null) {
			System.out.println("User with id " + fyid + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(userRecord.into(User.class), HttpStatus.OK);
	}

	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		System.out.println("Updating User " + id);
		
		UserRecord userRecord = dsl.fetchOne(USER,USER.FYID.equal(id));
		
		if (userRecord==null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		user.setFyid(userRecord.getFyid());
		userRecord.from(user);
		userRecord.store();

		return new ResponseEntity<User>(userRecord.into(User.class), HttpStatus.OK);
	}


	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting User with id " + id);

		UserRecord userRecord = dsl.fetchOne(USER,USER.FYID.equal(id));
		if (userRecord == null) {
			System.out.println("Unable to delete. User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		userRecord.delete();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	

}
