package cn.fyg.service.user.domain.user.impl;

import static cn.fyg.service.user.orm.jooq.Tables.USER;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.service.user.domain.common.EasUtil;
import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.domain.user.UserService;
import cn.fyg.service.user.domain.user.UserState;
import cn.fyg.service.user.orm.jooq.tables.pojos.User;
import cn.fyg.service.user.orm.jooq.tables.records.UserRecord;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	DSLContext dsl;

	@Override
	public boolean isUserExist(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public Retv<Long> register(String uname, String uemail, String uphone,
			String password, String realname) {
		if(StringUtils.isBlank(uname)){
			return Retv.error("用户名不能为空");
		}
		if(StringUtils.isBlank(password)){
			return Retv.error("密码不能为空");
		}
		if(StringUtils.isBlank(realname)){
			return Retv.error("姓名不能为空");
		}
		
		if(StringUtils.isBlank(uemail)){
			uemail=null;
		}
		if(StringUtils.isBlank(uphone)){
			uphone=null;
		}
		if(isDuplicationUserKey(uname,uemail,uphone)){
			return Retv.error("用户名、邮件或者电话重复");
		}
		password=EasUtil.encrypt(password);
		UserRecord userRecord = dsl.newRecord(USER);
		userRecord.setUname(uname);
		userRecord.setUemail(uemail);
		userRecord.setUphone(uphone);
		userRecord.setPassword(password);
		userRecord.setRealname(realname);
		userRecord.setState(UserState.on.val());
		userRecord.setCreatetime(System.currentTimeMillis());
		userRecord.store();
		
		return Retv.ret(userRecord.getFyid());
	}
	
	

	private boolean isDuplicationUserKey(String uname, String uemail,
			String uphone) {
		SelectConditionStep<Record1<Integer>> where = dsl.selectCount().from(USER).where(USER.UNAME.equal(uname));
		if(uemail!=null){
			where.or(USER.UEMAIL.equal(uemail));
		}
		if(uphone!=null){
			where.or(USER.UPHONE.equal(uphone));
		}
		return where.fetchOne().into(Integer.class).intValue() > 0;
	}

}
