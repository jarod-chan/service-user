package cn.fyg.service.user.interfaces;

import static cn.fyg.service.user.orm.jooq.Tables.USER;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.domain.user.UserDto;
import cn.fyg.service.user.domain.user.UserService;
import cn.fyg.service.user.domain.user.UserState;
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
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> getUser(@PathVariable("id") long fyid) {
		System.out.println("Fetching User with id " + fyid);
		UserRecord userRecord = dsl.fetchOne(USER,USER.FYID.equal(fyid));
		if (userRecord == null) {
			System.out.println("User with id " + fyid + " not found");
			return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDto>(userRecord.into(UserDto.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Retv<Long>> register(@RequestBody User user, 	UriComponentsBuilder ucBuilder) {
		Retv<Long> retv = this.userService.register(user.getUname(), user.getUemail(), user.getUphone(), user.getPassword(),user.getRealname());
	
		if (retv.fail()) {
			System.out.println(retv.info());
			return new ResponseEntity<Retv<Long>>(retv,HttpStatus.CONFLICT);
		}
	
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(retv.data()).toUri());
		return new ResponseEntity<Retv<Long>>(retv,headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{id}/changestate", method = RequestMethod.POST)
	public ResponseEntity<Retv<Void>> changestate(@PathVariable("id") long fyid,@RequestBody Map<String,Object> map) {
		UserState state=UserState.valueOf(map.get("state").toString());
		Retv<Void> retv = this.userService.changeState(fyid, state);
		return new ResponseEntity<Retv<Void>>(retv, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public ResponseEntity<Retv<UserDto>> updateUser(@PathVariable("id") long fyid, @RequestBody UserDto user) {
		System.out.println("Updating User " + fyid);
		Retv<UserDto> retv = this.userService.update(fyid, user);
		return new ResponseEntity<Retv<UserDto>>(retv, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}/password", method = RequestMethod.POST)
	public ResponseEntity<Retv<Void>> password(@PathVariable("id") long fyid, @RequestParam("password") String password) {
		System.out.println("change password User " + fyid);
		Retv<Void> retv = this.userService.changePassword(fyid, password);
		return new ResponseEntity<Retv<Void>>(retv, HttpStatus.OK);
	}
	
}
