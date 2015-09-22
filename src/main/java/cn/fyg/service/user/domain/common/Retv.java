package cn.fyg.service.user.domain.common;

public class Retv<T> {
	private final T data;
	private final boolean success;
	private final String reason;
	
	
	
	private Retv(T data, boolean success,String reason) {
		super();
		this.data = data;
		this.success =success;
		this.reason = reason;
	}
	
	private Retv(boolean success,String reason) {
		super();
		this.data=null;
		this.success =success;
		this.reason = reason;
	}

	public static  <T> Retv<T> error(String reason){
		return new Retv<T>(false,reason);
	}


	public static <T> Retv<T> ret(T data){
		return new Retv<T>(data,true,null);
	}
	
	public boolean success(){
		return this.success;
	}
	
	public boolean fail(){
		return !this.success;
	}
	
	public T data(){
		return this.data;
	}
	
	public String resaon(){
		return this.reason;
	}

	public String getInfo() {
		return this.reason;
	}
	
	public T getData() {
		return this.data;
	}

}
