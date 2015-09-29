package cn.fyg.service.user.interfaces;

import static cn.fyg.service.user.orm.jooq.Tables.APP;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.fyg.service.user.domain.app.AppDto;
import cn.fyg.service.user.domain.app.AppService;
import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.orm.jooq.tables.records.AppRecord;

@RestController
@RequestMapping("app")
public class AppController {
	
	@Autowired
	AppService appService;
	@Autowired
	DSLContext dsl;

    
	@RequestMapping(value="",method=RequestMethod.GET)
	public Retv<List<AppDto>>  list(){
		  List<AppDto> apps =this.appService.all();
		  return Retv.ret(apps);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Retv<AppDto> getUser(@PathVariable("id") long id) {
		System.out.println("Fetching User with id " + id);
		AppRecord appRecord = dsl.fetchOne(APP,APP.ID.equal(id));
		if (appRecord == null) {
			System.out.println("User with id " + id + " not found");
			return Retv.error("无法找到该用户");
		}
		return Retv.ret(appRecord.into(AppDto.class));
	}

	@RequestMapping(value="",method=RequestMethod.POST)
	public Retv<Long> create(@RequestBody AppDto app){
		Retv<Long> retv = this.appService.create(app);
		return retv;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public 	Retv<AppDto> update(@PathVariable("id") long id, @RequestBody AppDto app) {
		System.out.println("Updating app " + id);
		Retv<AppDto> retv = this.appService.update(id, app);
		return retv;
	}
	
	 @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	    public Retv<Void> deleteUser(@PathVariable("id") long id) {
	        System.out.println("Fetching & Deleting App with id " + id);
	 
	        AppRecord record = dsl.fetchOne(APP,APP.ID.equal(id));
	        if (record == null) {
	            System.out.println("Unable to delete. User with id " + id + " not found");
	            return Retv.error("无法找到该应用");
	        }
	 
	        record.delete();
	        return Retv.ret();
	    }
	 
}
