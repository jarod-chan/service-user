package cn.fyg.service.user.domain.app;

import java.util.List;

import cn.fyg.service.user.domain.common.Retv;

public interface AppService {
	
	List<AppDto> all();
	
	Retv<Long> create(AppDto app);
	
	Retv<AppDto> update(long id,AppDto app);

}
