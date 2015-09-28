package cn.fyg.service.user.domain.user;

import cn.fyg.service.user.domain.common.Retv;

public interface UserService {
	
	Retv<Long> register(String uname,String uemail,String uphone,String password,String realname);
	
	Retv<Void> changeState(long fyid, UserState state);
	
	Retv<UserDto> update(long fyid,UserDto user);

	Retv<Void> changePassword(long fyid, String password);

}
