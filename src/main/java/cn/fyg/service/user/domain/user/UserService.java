package cn.fyg.service.user.domain.user;

import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.orm.jooq.tables.pojos.User;

public interface UserService {
	
	boolean isUserExist(User user);
	
	Retv<Long> register(String uname,String uemail,String uphone,String password,String realname);
	
	Retv<Void> changeState(long fyid, UserState state);
	
	Retv<UserDto> update(long fyid,UserDto user);

	Retv<Void> changePassword(long fyid, String password);

}
