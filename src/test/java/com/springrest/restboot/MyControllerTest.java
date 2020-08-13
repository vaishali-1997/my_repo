package com.springrest.restboot;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springrest.restboot.controller.MyController;
import com.springrest.restboot.entities.Course;
import com.springrest.restboot.services.CourseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyControllerTest {

	private MockMvc mockMvc;
	@InjectMocks
	private MyController myController;
	@Mock
	private CourseService courseService;
	@Before
	public void init() {
		//fail("Not yet implemented");
		MockitoAnnotations.initMocks(this);
		mockMvc= MockMvcBuilders.standaloneSetup(myController).addFilters().build();
	}

	@Test
	public void test_get_courses() throws Exception
	{
		ArrayList<Course> course = new ArrayList<Course>();
		course.add(new Course(101,"ABC","abcd"));
		course.add(new Course(102,"CDE","cdef"));
		
		when(courseService.getCourse()).thenReturn(course);
		mockMvc.perform(get("/courses"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id",is(101)))
		.andExpect(jsonPath("$[0].title",is("ABC")))
		.andExpect(jsonPath("$[0].description",is("abcd")))
		.andExpect(content().contentType("application/json;UTF-8"));
		verify(courseService,times(1)).getCourse();
		//.andExpect( contentType(MediaType.APPLICATION_JSON));
		//.andExpect( (ResultMatcher) content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
	
	@Test
	public void test_get_coursebyid() throws Exception
	{
		Course courses=new Course(1,"XY","xyz");
		
		when(courseService.getCourse(1)).thenReturn(courses);
		mockMvc.perform(get("/courses/{id}",1))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.title",is("XY")))
		.andExpect(jsonPath("$.description",is("xyz")))
		.andExpect(content().contentType("application/json;UTF-8"));
		verify(courseService,times(1)).getCourse(1);
	}
	
	@Test
	public void test_add_course() throws Exception
	{
		Course course=new Course(3,"XY","xyz");
		
		Mockito.when(courseService.addCourse(Mockito.any(Course.class))).thenReturn(course);
		
		/*
		 * doNothing().doThrow(new RuntimeException())
		 * .when(courseService).addCourse(course);
		 */
		
		mockMvc.perform(post("/courses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(course)))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;UTF-8"));
	}
	
	@Test
	public void test_update_course() throws Exception
	{
		Course course=new Course(0,"Python","Pyhton basics");
		
		/*
		 * when(courseService.getCourse(course.getId())).thenReturn(course);
		 * 
		 * doNothing().when(courseService).updateCourse(course);
		 */
		
		Mockito.when(courseService.updateCourse(Mockito.any(Course.class))).thenReturn(course);
		
		mockMvc.perform(put("/courses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(course)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void test_delete_course() throws Exception
	{
		ArrayList<Course> courseList=new ArrayList<Course>();
		Course course=new Course(0,"Python","Pyhton basics");
		courseList.add(course);
		Mockito.when(courseService.deleteCourse(Mockito.anyLong())).thenReturn(courseList);
		
		mockMvc.perform(delete("/courses/{id}",course.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(course)))
				.andExpect(status().isOk());
	}
	
	
}
