package cn.fyg.service.user.interfaces;

import static cn.fyg.service.user.orm.jooq.Tables.APPUSER;
import static cn.fyg.service.user.orm.jooq.Tables.USER;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.domain.user.UserDto;

@RestController
@RequestMapping("appuser")
public class AppuserController {
	
	@Autowired
	DSLContext dsl;
	

	@RequestMapping(value="/app/{appId}",method=RequestMethod.GET)
	public Retv<List<UserDto>> appUser(@PathVariable("appId") long appId){
		Result<Record> result = dsl.select(USER.fields())
			.from(USER.leftOuterJoin(APPUSER).on(USER.FYID.equal(APPUSER.FYID)))
			.where(APPUSER.APP_ID.equal(appId))
			.orderBy(USER.FYID.desc())
			.fetch();

		List<UserDto> users = result.into(UserDto.class);
		return Retv.ret(users);
	}
	
	
}
