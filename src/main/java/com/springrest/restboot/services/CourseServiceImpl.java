package com.springrest.restboot.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springrest.restboot.entities.Course;

@Service
public class CourseServiceImpl implements CourseService {

	public List<Course> courseList=null;
	
	CourseServiceImpl()
	{
		courseList=new ArrayList<Course>();
		courseList.add(new Course(101,"Java","This includes Java Basics"));
		courseList.add(new Course(102,"Boot","This includes Spring Boot Basics"));
	}
	
	
	@Override
	public List<Course> getCourse() {
		return courseList;
	}


	@Override
	public Course getCourse(long parseLong) {
		 Course course=null;
		for(Course courses : courseList)
		{
			if(courses.getId()==parseLong)
			{
				course=courses;
				break;
			}
		}
		return course;
	}


	public List<Course> getCourseList() {
		return courseList;
	}


	@Override
	public Course addCourse(Course course) {
		courseList.add(course);
		return course;
	}


	@Override
	public Course updateCourse(Course course) {
		Course cc=null;
		for(Course c:courseList)
		{
			if(c.getId()==course.getId())
			{
				c.setTitle(course.getTitle());
				c.setDescription(course.getDescription());
				cc=c;
				break;
			}
		}
		return cc;
	}


	@Override
	public List<Course> deleteCourse(long parseLong) {
		for(Course c: courseList)
		{
			if(c.getId()==parseLong)
			{
				courseList.remove(c);
				break;
			}
		}
		return courseList;
	}


}
