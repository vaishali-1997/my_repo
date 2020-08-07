package com.springrest.restboot.services;

import java.util.List;

import com.springrest.restboot.entities.Course;

public interface CourseService {
		public List<Course> getCourse();

		public Course getCourse(long parseLong);

		public Course addCourse(Course course);

		public Course updateCourse(Course course);

		public List<Course> deleteCourse(long parseLong);
}
