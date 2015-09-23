package cn.fyg.service.user.interfaces;

import static cn.fyg.service.user.orm.jooq.Tables.APP;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import cn.fyg.service.user.domain.app.AppDto;
import cn.fyg.service.user.domain.app.AppService;
import cn.fyg.service.user.domain.common.Retv;
import cn.fyg.service.user.orm.jooq.tables.pojos.App;
import cn.fyg.service.user.orm.jooq.tables.records.AppRecord;

@RestController
@RequestMapping("app")
public class AppController {
	
	@Autowired
	AppService appService;
	@Autowired
	DSLContext dsl;

    
	@RequestMapping(value="",method=RequestMethod.GET)
	public ResponseEntity<List<AppDto>>  list(){
		  List<AppDto> apps =this.appService.all();
          if(apps.isEmpty()){
              return new ResponseEntity<List<AppDto>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
          }
          return new ResponseEntity<List<AppDto>>(apps, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppDto> getUser(@PathVariable("id") long id) {
		System.out.println("Fetching User with id " + id);
		AppRecord appRecord = dsl.fetchOne(APP,APP.ID.equal(id));
		if (appRecord == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<AppDto>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AppDto>(appRecord.into(AppDto.class), HttpStatus.OK);
	}

	@RequestMapping(value="",method=RequestMethod.POST)
	public ResponseEntity<Retv<Long>> create(@RequestBody AppDto app,UriComponentsBuilder ucBuilder){
		Retv<Long> retv = this.appService.create(app);
		
		if (retv.fail()) {
			System.out.println(retv.info());
			return new ResponseEntity<Retv<Long>>(retv,HttpStatus.CONFLICT);
		}
	
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/app/{id}").buildAndExpand(retv.data()).toUri());
		return new ResponseEntity<Retv<Long>>(retv,headers, HttpStatus.CREATED);
		 
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public ResponseEntity<	Retv<AppDto>> updateUser(@PathVariable("id") long id, @RequestBody AppDto app) {
		System.out.println("Updating app " + id);
		Retv<AppDto> retv = this.appService.update(id, app);
		return new ResponseEntity<	Retv<AppDto>>(retv, HttpStatus.OK);
	}
	
	 @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<App> deleteUser(@PathVariable("id") long id) {
	        System.out.println("Fetching & Deleting App with id " + id);
	 
	        AppRecord record = dsl.fetchOne(APP,APP.ID.equal(id));
	        if (record == null) {
	            System.out.println("Unable to delete. User with id " + id + " not found");
	            return new ResponseEntity<App>(HttpStatus.NOT_FOUND);
	        }
	 
	        record.delete();
	        return new ResponseEntity<App>(HttpStatus.NO_CONTENT);
	    }
	 
}
