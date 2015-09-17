package cn.fyg.service.user.domain.user;

import cn.fyg.service.user.orm.jooq.tables.pojos.User;

public interface UserService {
	
	boolean isUserExist(User user);

}
