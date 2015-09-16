package test;

import static db.jooq.Tables.AUTHOR;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import db.jooq.tables.records.AuthorRecord;


public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Connection conn = null;

        String userName = "root";
        String password = "0";
        String url = "jdbc:mysql://localhost:3306/library";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);

            DSLContext dsl = DSL.using(conn, SQLDialect.MYSQL);
            
            int id=3;
            createRecord(dsl,id);
            
            readRecord("after create",dsl,id);
            
            updateRecord(dsl,id);
            
            readRecord("after update",dsl,id);
            
            deleteRecord(dsl,id);
            
            readRecord("after delete",dsl,id);
            
            allRecord(dsl);
        } catch (Exception e) {
            // For the sake of this tutorial, let's keep exception handling simple
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }

	private static void allRecord(DSLContext dsl) {
		Result<Record> result = dsl.select().from(AUTHOR).fetch();
		System.out.println("get all records");
		for (Record r : result) {
		  System.out.println(r);
		}
	}

	private static void createRecord(DSLContext dslContext,int id) {
		AuthorRecord record = dslContext.newRecord(AUTHOR);
		record.setId(id);
		record.setFirstName("1");
		record.setLastName("2");
		record.store();
	}

	private static void updateRecord(DSLContext dsl,int id) {
		AuthorRecord authorRecord = dsl.fetchOne(AUTHOR,AUTHOR.ID.equal(id));
		authorRecord.setFirstName("f1");
		authorRecord.store();
	
	}

	private static void deleteRecord(DSLContext dsl,int id) {
		AuthorRecord authorRecord = dsl.fetchOne(AUTHOR,AUTHOR.ID.equal(id));
		authorRecord.delete();
	}

	private static void readRecord(String tag,DSLContext dsl,int id) {
		AuthorRecord authorRecord = dsl.fetchOne(AUTHOR,AUTHOR.ID.equal(id));
		System.out.println(tag);
		System.out.println(authorRecord);
	}
}