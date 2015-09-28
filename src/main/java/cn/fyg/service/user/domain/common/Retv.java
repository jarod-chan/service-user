package cn.fyg.service.user.domain.common;

public class Retv<T> {
	private final T data;
	private final boolean success;
	private final String info;
	
	
	
	private Retv(T data, boolean success,String info) {
		super();
		this.data = data;
		this.success =success;
		this.info = info;
	}

	public static  <T> Retv<T> error(String info){
		return new Retv<T>(null,false,info);
	}

	public static <T> Retv<T> ret(){
		return new Retv<T>(null,true,null);
	}

	public static <T> Retv<T> ret(T data){
		return new Retv<T>(data,true,null);
	}
	
	public static <T> Retv<T> ret(String info){
		return new Retv<T>(null,true,info);
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
	
	public String info(){
		return this.info;
	}

	public String getInfo() {
		return this.info;
	}
	
	public T getData() {
		return this.data;
	}

	public boolean isSuccess() {
		return success;
	}
	
	

}
