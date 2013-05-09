package server.service;


import java.util.List;

import server.domain.Activity;
import server.domain.Activity.ActivityId;
import server.service.impl.ActivityServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityServiceImpl.class)
public interface ActivityService extends AbstractDomainService<ActivityId, Activity> {
	public List<Activity> list(String key, boolean hourly);

	public void record(String key);
}
