package cn.fyg.service.user.interfaces;

import static cn.fyg.service.user.orm.jooq.Tables.APP;
import static cn.fyg.service.user.orm.jooq.Tables.APPUSER;
import static cn.fyg.service.user.orm.jooq.Tables.USER;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import cn.fyg.service.user.orm.jooq.tables.pojos.App;
import cn.fyg.service.user.orm.jooq.tables.pojos.Appuser;
import cn.fyg.service.user.orm.jooq.tables.pojos.User;
import cn.fyg.service.user.orm.jooq.tables.records.AppuserRecord;

@RestController
@RequestMapping("appuser2")
public class AppuserController2 {
	
	@Autowired
	DSLContext dsl;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Appuser>> list() {
		List<Appuser> appusers =  dsl.selectFrom(APPUSER).fetchInto(Appuser.class);
		if(appusers.isEmpty()){
			return new ResponseEntity<List<Appuser>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Appuser>>(appusers, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Appuser appuser, 	UriComponentsBuilder ucBuilder) {
	
		AppuserRecord appuserRecord = dsl.newRecord(APPUSER);
		appuserRecord.from(appuser);
		appuserRecord.store();
	
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/appuser/{id}").buildAndExpand(appuserRecord.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value="/app/{appId}",method=RequestMethod.GET)
	public ResponseEntity<List<User>> appUser(@PathVariable("appId") long appId){
		Result<Record> result = dsl.select(USER.fields())
			.from(USER.leftOuterJoin(APPUSER).on(USER.FYID.equal(APPUSER.FYID)))
			.where(APPUSER.APP_ID.equal(appId))
			.orderBy(USER.FYID.desc())
			.fetch();

		List<User> users = result.into(User.class);
		if(users.isEmpty()){
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	

	@RequestMapping(value="/user/{fyid}",method=RequestMethod.GET)
	public ResponseEntity<List<App>> userApp(@PathVariable("fyid") long fyid){
		Result<Record> result = dsl.select(APP.fields())
			.from(APP.leftOuterJoin(APPUSER).on(APP.ID.equal(APPUSER.APP_ID)))
			.where(APPUSER.FYID.equal(fyid))
			.orderBy(APP.ID.desc())
			.fetch();

		List<App> apps = result.into(App.class);
		if(apps.isEmpty()){
			return new ResponseEntity<List<App>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<App>>(apps, HttpStatus.OK);
	}

}
