package cn.fyg.service.user.interfaces;

import static cn.fyg.service.user.orm.jooq.Tables.APP;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.fyg.service.user.orm.jooq.tables.pojos.App;
import cn.fyg.service.user.orm.jooq.tables.records.AppRecord;

@RestController
@RequestMapping("app")
public class AppController {
	
	@Autowired
	DSLContext dsl;

    
	@RequestMapping(value="",method=RequestMethod.GET)
	public ResponseEntity<List<App>>  list(){
		  List<App> queryResults = dsl.selectFrom(APP).fetchInto(App.class);
          if(queryResults.isEmpty()){
              return new ResponseEntity<List<App>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
          }
          return new ResponseEntity<List<App>>(queryResults, HttpStatus.OK);
	}

	@RequestMapping(value="create",method=RequestMethod.POST)
	public App create(@RequestBody App app){
		AppRecord record = dsl.newRecord(APP);
		record.from(app);
		record.store();
		return record.into(App.class);
		 
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<App> updateUser(@PathVariable("id") long id, @RequestBody App app) {
		System.out.println("Updating app " + id);
		
		AppRecord appRecord = dsl.fetchOne(APP,APP.ID.equal(id));
		
		if (appRecord==null) {
			System.out.println("app with id " + id + " not found");
			return new ResponseEntity<App>(HttpStatus.NOT_FOUND);
		}
		appRecord.setName(app.getName());
		appRecord.setRemark(app.getRemark());
		appRecord.setUrl(app.getUrl());
		appRecord.store();
	
		return new ResponseEntity<App>(appRecord.into(App.class), HttpStatus.OK);
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
