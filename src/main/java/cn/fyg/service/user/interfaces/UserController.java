package cn.fyg.service.user.interfaces;

import static cn.fyg.service.user.orm.jooq.Tables.USER;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public Retv<List<UserDto>> list() {
		List<UserDto> users =  dsl.selectFrom(USER).orderBy(USER.FYID.desc()).fetchInto(UserDto.class);
		return Retv.ret(users);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Retv<UserDto> getUser(@PathVariable("id") long fyid) {
		UserRecord userRecord = dsl.fetchOne(USER,USER.FYID.equal(fyid));
		if(userRecord==null){
			return Retv.error("无法找到该用户");
		}
		return Retv.ret(userRecord.into(UserDto.class));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Retv<Long> register(@RequestBody User user) {
		Retv<Long> retv = this.userService.register(user.getUname(), user.getUemail(), user.getUphone(), user.getPassword(),user.getRealname());
		return retv;
	}
	
	@RequestMapping(value = "{id}/changestate", method = RequestMethod.POST)
	public Retv<Void> changestate(@PathVariable("id") long fyid,@RequestBody Map<String,Object> map) {
		UserState state=UserState.valueOf(map.get("state").toString());
		Retv<Void> retv = this.userService.changeState(fyid, state);
		return retv;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public Retv<UserDto> updateUser(@PathVariable("id") long fyid, @RequestBody UserDto user) {
		Retv<UserDto> retv = this.userService.update(fyid, user);
		return retv;
	}
	
	@RequestMapping(value = "{id}/password", method = RequestMethod.POST)
	public Retv<Void> password(@PathVariable("id") long fyid, @RequestParam("password") String password) {
		Retv<Void> retv = this.userService.changePassword(fyid, password);
		return retv;
	}
	
}
