package cn.fyg.service.user.domain.user;

import org.springframework.stereotype.Component;

import cn.fyg.service.user.orm.jooq.tables.pojos.User;

@Component
public class UserServiceImpl implements UserService {

	@Override
	public boolean isUserExist(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
