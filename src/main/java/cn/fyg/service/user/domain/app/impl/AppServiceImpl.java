package cn.fyg.service.user.domain.app.impl;

import static cn.fyg.service.user.orm.jooq.Tables.APP;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.service.user.domain.app.AppDto;
import cn.fyg.service.user.domain.app.AppService;
import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.orm.jooq.tables.records.AppRecord;

@Component
public class AppServiceImpl implements AppService {
	
	@Autowired
	DSLContext dsl;


	@Override
	public List<AppDto> all() {
		 return dsl.selectFrom(APP).orderBy(APP.ID.desc()).fetchInto(AppDto.class);
	}


	@Override
	@Transactional
	public Retv<Long> create(AppDto app) {
		if(StringUtils.isBlank(app.getName())){
			return Retv.error("应用名不能为空");
		}
		if(isDuplicationName(null,app.getName())){
			return Retv.error("应用名重复");
		}
		AppRecord record = dsl.newRecord(APP);
		record.from(app,APP.NAME,APP.REMARK,APP.URL);
		record.store();
		return Retv.ret(record.getId());
	}
	
	private boolean isDuplicationName(Long id,String name) {
		
		SelectQuery<Record> selectQuery = dsl.selectQuery();
		
		selectQuery.addSelect(DSL.count());
		selectQuery.addFrom(APP);
		if(id!=null){
			selectQuery.addConditions(APP.ID.notEqual(id));
		}
		selectQuery.addConditions(APP.NAME.equal(name));
		
		
		return selectQuery.fetchOne().into(Integer.class)>0;	
	}


	@Override
	public Retv<AppDto> update(long id, AppDto app) {
		AppRecord appRecord = dsl.fetchOne(APP,APP.ID.equal(id));
		if(appRecord==null){
			return Retv.error("应用不存在");
		}
		if(StringUtils.isBlank(app.getName())){
			return Retv.error("应用名不能为空");
		}
		if(isDuplicationName(id,app.getName())){
			return Retv.error("应用名重复");
		}
		appRecord.from(app,APP.NAME,APP.REMARK,APP.URL);
		appRecord.store();
		return Retv.ret(appRecord.into(AppDto.class));
	}

}
