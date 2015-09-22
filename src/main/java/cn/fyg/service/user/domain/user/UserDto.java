package cn.fyg.service.user.domain.user;

import lombok.Data;

@Data
public class UserDto {
	
	private Long   fyid;
	private String uname;
	private String uemail;
	private String uphone;
	private String realname;
	private String state;

	
}
