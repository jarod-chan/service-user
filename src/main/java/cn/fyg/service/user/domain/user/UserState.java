package cn.fyg.service.user.domain.user;

import cn.fyg.service.user.domain.common.StringEnum;

public enum UserState implements StringEnum {
	on("on"),off("off");
	
	private final String value;

	private UserState(String value) {
		this.value = value;
	}

	@Override
	public String val() {
		return this.value;
	}
	
	
}
