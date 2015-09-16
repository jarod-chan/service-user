package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import db.jooq.tables.pojos.Author;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @Autowired
	DSLContext dsl;

    
    @RequestMapping("/jooq")
	public Author allRecord(){
    	try{
    		Author author = dsl.fetchOne(db.jooq.Tables.AUTHOR,db.jooq.Tables.AUTHOR.ID.equal(1)).into(Author.class);
    		return author;
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return null;
	}
}