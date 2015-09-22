package cn.fyg.service.user.domain.user;

import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.orm.jooq.tables.pojos.User;

public interface UserService {
	
	boolean isUserExist(User user);
	
	
	Retv<Long> register(String uname,String uemail,String uphone,String password,String realname);

}
