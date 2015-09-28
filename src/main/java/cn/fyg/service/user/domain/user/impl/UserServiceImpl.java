package cn.fyg.service.user.domain.user.impl;

import static cn.fyg.service.user.orm.jooq.Tables.USER;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.service.user.domain.common.EasUtil;
import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.domain.user.UserDto;
import cn.fyg.service.user.domain.user.UserService;
import cn.fyg.service.user.domain.user.UserState;
import cn.fyg.service.user.orm.jooq.tables.records.UserRecord;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	DSLContext dsl;

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
		if(isDuplicationUserKey(null,uname,uemail,uphone)){
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
	
	

	private boolean isDuplicationUserKey(Long fyid,String uname, String uemail,
			String uphone) {
		
		SelectQuery<Record> selectQuery = dsl.selectQuery();
		
		selectQuery.addSelect(DSL.count());
		selectQuery.addFrom(USER);
		if(fyid!=null){
			selectQuery.addConditions(USER.FYID.notEqual(fyid));
		}
		List<Condition> equals=new ArrayList<Condition>();
		if(uname!=null){
			equals.add(USER.UNAME.equal(uname));
		}
		if(uemail!=null){
			equals.add(USER.UEMAIL.equal(uemail));
		}
		if(uphone!=null){
			equals.add(USER.UPHONE.equal(uphone));
		}
		

		if(equals.size()>=1){
			Condition condition = equals.get(0);
			for (int i = 1; i < equals.size(); i++) {
				condition.or(equals.get(i));
			}
			selectQuery.addConditions(condition);
		}
		
		return selectQuery.fetchOne().into(Integer.class)>0;
		
	}

	@Override
	@Transactional
	public Retv<Void> changeState(long fyid,UserState state) {
		UserRecord userRecord = dsl.fetchOne(USER,USER.FYID.equal(fyid));
		if (userRecord == null) {
			return Retv.error("无法找到用户");
		}
		if(state==null){
			return Retv.error("状态不存在");
		}
		if(userRecord.getState().equals(state.val())){
			return Retv.error("状态重复更新");
		}
		userRecord.setState(state.val());
		userRecord.store();
		return Retv.ret();
	}

	@Override
	@Transactional
	public Retv<UserDto> update(long fyid, UserDto user) {
		UserRecord userRecord = dsl.fetchOne(USER,USER.FYID.equal(fyid));
		if (userRecord == null) {
			return Retv.error("无法找到用户");
		}
		if(StringUtils.isBlank(user.getUname())){
			return Retv.error("用户名不能为空");
		}
		if(StringUtils.isBlank(user.getRealname())){
			return Retv.error("姓名不能为空");
		}
		if(StringUtils.isBlank(user.getUemail())){
			user.setUemail(null);
		}
		if(StringUtils.isBlank(user.getUphone())){
			user.setUphone(null);
		}
		if(isDuplicationUserKey(fyid,user.getUname(),user.getUemail(),user.getUphone())){
			return Retv.error("用户名、邮件或者电话重复");
		}
		
		userRecord.from(user,USER.UNAME,USER.UEMAIL,USER.UPHONE,USER.REALNAME);
		userRecord.store();
		return Retv.ret(userRecord.into(UserDto.class));
	}

	@Override
	@Transactional
	public Retv<Void> changePassword(long fyid, String password) {
		UserRecord userRecord = dsl.fetchOne(USER,USER.FYID.equal(fyid));
		if (userRecord == null) {
			return Retv.error("无法找到用户");
		}
		if(StringUtils.isBlank(password)){
			return Retv.error("密码不能为空");
		}
		password=EasUtil.encrypt(password);
		userRecord.setPassword(password);
		userRecord.store(USER.PASSWORD);
		return  Retv.ret();
	}

}
