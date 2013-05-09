package server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import server.domain.Activity;
import server.domain.Activity.ActivityId;
import server.domain.Activity.ActivityId_;
import server.domain.Activity_;
import server.service.ActivityService;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class ActivityServiceImpl extends AbstractDomainServiceImpl<ActivityId, Activity> implements ActivityService {
	public ActivityServiceImpl() {
		super(Activity.class);
	}

	@Override
	public List<Activity> list(String key, boolean hourly) {
		Calendar calendar = calendar();
		long endTime = calendar.getTimeInMillis();
		if (hourly)
			calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 3);
		else {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3);
		}
		long startTime = calendar.getTimeInMillis();

		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<Activity> query = builder.createQuery(Activity.class);
		Root<Activity> root = query.from(Activity.class);
		query.select(root);

		query.where(builder.equal(root.get(Activity_.id).get(ActivityId_.key), key), //
				builder.equal(root.get(Activity_.id).get(ActivityId_.hourly), hourly), //
				builder.greaterThanOrEqualTo(root.get(Activity_.id).get(ActivityId_.time), startTime));

		TypedQuery<Activity> typedQuery = entityManager.get().createQuery(query);
		typedQuery.setHint("org.hibernate.cacheable", true);
		TreeSet<Activity> activities = new TreeSet<Activity>(typedQuery.getResultList());

		while (calendar.getTimeInMillis() < endTime) {
			Activity activity = new Activity();
			activity.setKey(key);
			activity.setHourly(hourly);
			activity.setTime(calendar.getTimeInMillis());
			activity.setTotal(0L);
			if (!activities.contains(activity))
				activities.add(activity);
			if (hourly)
				calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
			else
				calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
		}
		activities.remove(activities.last());

		return new ArrayList<Activity>(activities);
	}

	protected long hour() {
		return calendar().getTimeInMillis();
	}

	protected long date() {
		Calendar calendar = calendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTimeInMillis();
	}

	private Calendar calendar() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar;
	}

	@Override
	@Transactional
	public synchronized void record(String key) {
		record(key, false);
		record(key, true);
	}

	@Transactional
	public void record(String key, boolean hourly) {
		ActivityId id = new ActivityId(key, hourly ? hour() : date(), hourly);
		Activity activity = new Activity();
		activity.setId(id);

		if (increment(activity) == 0)
			create(activity);
	}

	@Transactional
	public int increment(Activity activity) {
		Query query = entityManager.get().createQuery(
				"update Activity set total = total + 1 where id.key = :key and id.time = :time and id.hourly = :hourly");
		query.setParameter("key", activity.getKey());
		query.setParameter("time", activity.getTime());
		query.setParameter("hourly", activity.isHourly());
		return query.executeUpdate();
	}
}
