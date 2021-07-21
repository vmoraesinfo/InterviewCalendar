package com.tamanna.InterviewCalendar.api;


import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.result.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class PeopleAPITest {

  @Autowired
  private MockMvc mockMvc;


  @Before
  public void beforeEach() throws Exception {
    this.mockMvc.perform(
      delete( "/people/clean" )
        .contentType( MediaType.APPLICATION_JSON )
        .accept( MediaType.APPLICATION_JSON ) )
      .andExpect( MockMvcResultMatchers.status().isOk() );
  }

  @Test
  public void testPostAddingOneInterviewerWithOneDay() throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put( "name", "David" );
    JSONArray array = new JSONArray();
    JSONObject jo = new JSONObject();
    jo.put( "name", "Monday" );
    jo.put( "ranges", "9-16" );
    array.put( jo );
    jsonObject.put( "availabilityEntityList", array );
    this.mockMvc.perform(
      post( "/people/availability?peopleType=Interviewer" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andExpect( MockMvcResultMatchers.status().isOk() );

    MvcResult mvcResult = this.mockMvc.perform(
      get( "/people/slots" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andReturn();

    String responseData = mvcResult.getResponse().getContentAsString();

    JSONObject jsonResponse = new JSONObject( responseData );
    Assert.assertSame( jsonResponse.getJSONObject( "days" ).length(), 1 );
    Assert
      .assertSame( jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).length(), 7 );
    for ( int i = 0; i < 7; i++ ) {
      Assert.assertSame(
        jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( i )
          .getJSONArray( "people" ).length(), 1 );
      Assert.assertSame(
        jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( i )
          .get( "time" ), i + 9 );
      Assert.assertEquals(
        jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( i )
          .getJSONArray( "people" ).getJSONObject( 0 ).get( "name" ), "David" );
      Assert.assertSame(
        jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( i )
          .getJSONArray( "people" ).getJSONObject( 0 ).get( "peopleType" ), 1 );
    }


  }

  @Test
  public void testPostAddingOneInterviewerWithTwoDays() throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put( "name", "David" );
    JSONArray array = new JSONArray();
    JSONObject jo = new JSONObject();
    jo.put( "name", "Monday" );
    jo.put( "ranges", "10-11" );
    array.put( jo );
    JSONObject jo1 = new JSONObject();
    jo1.put( "name", "Tuesday" );
    jo1.put( "ranges", "15-16" );
    array.put( jo1 );
    jsonObject.put( "availabilityEntityList", array );
    this.mockMvc.perform(
      post( "/people/availability?peopleType=Interviewer" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andExpect( MockMvcResultMatchers.status().isOk() );

    MvcResult mvcResult = this.mockMvc.perform(
      get( "/people/slots" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andReturn();

    String responseData = mvcResult.getResponse().getContentAsString();

    JSONObject jsonResponse = new JSONObject( responseData );
    Assert.assertSame( jsonResponse.getJSONObject( "days" ).length(), 2 );
    Assert
      .assertSame( jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .get( "time" ), 10 );
    Assert.assertEquals(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "name" ), "David" );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "peopleType" ), 1 );


    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Tuesday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Tuesday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .get( "time" ), 15 );
    Assert.assertEquals(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Tuesday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "name" ), "David" );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Tuesday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "peopleType" ), 1 );


  }


  @Test
  public void testPostAddingOneCandidateWithOneDay() throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put( "name", "Carl" );
    JSONArray array = new JSONArray();
    JSONObject jo = new JSONObject();
    jo.put( "name", "Monday" );
    jo.put( "ranges", "9-10" );
    array.put( jo );
    jsonObject.put( "availabilityEntityList", array );
    this.mockMvc.perform(
      post( "/people/availability?peopleType=Candidate" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andExpect( MockMvcResultMatchers.status().isOk() );


    MvcResult mvcResult = this.mockMvc.perform(
      get( "/people/slots" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andReturn();

    String responseData = mvcResult.getResponse().getContentAsString();

    JSONObject jsonResponse = new JSONObject( responseData );
    Assert.assertSame( jsonResponse.getJSONObject( "days" ).length(), 1 );
    Assert
      .assertSame( jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .get( "time" ), 9 );
    Assert.assertEquals(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "name" ), "Carl" );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "peopleType" ), 2 );
  }

  @Test
  public void testPostAddingOneCandidateWithTwoDays() throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put( "name", "Carl" );
    JSONArray array = new JSONArray();
    JSONObject jo = new JSONObject();
    jo.put( "name", "Monday" );
    jo.put( "ranges", "9-10" );
    array.put( jo );
    jo = new JSONObject();
    jo.put( "name", "Wednesday" );
    jo.put( "ranges", "11-12" );
    array.put( jo );
    jsonObject.put( "availabilityEntityList", array );
    this.mockMvc.perform(
      post( "/people/availability?peopleType=Candidate" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andExpect( MockMvcResultMatchers.status().isOk() );

    MvcResult mvcResult = this.mockMvc.perform(
      get( "/people/slots" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andReturn();

    String responseData = mvcResult.getResponse().getContentAsString();

    JSONObject jsonResponse = new JSONObject( responseData );
    Assert.assertSame( jsonResponse.getJSONObject( "days" ).length(), 2 );
    Assert
      .assertSame( jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .get( "time" ), 9 );
    Assert.assertEquals(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "name" ), "Carl" );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "peopleType" ), 2 );

    Assert
      .assertSame( jsonResponse.getJSONObject( "days" ).getJSONObject( "Wednesday" ).getJSONArray( "hr" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Wednesday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).length(), 1 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Wednesday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .get( "time" ), 11 );
    Assert.assertEquals(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Wednesday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "name" ), "Carl" );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Wednesday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "peopleType" ), 2 );

  }

  @Test
  public void testGetPossibleScheduleAddingOneInterviewerAndOneCandidateWithOneDayEach() throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put( "name", "Carl" );
    JSONArray array = new JSONArray();
    JSONObject jo = new JSONObject();
    jo.put( "name", "Monday" );
    jo.put( "ranges", "9-10" );
    array.put( jo );
    jsonObject.put( "availabilityEntityList", array );
    this.mockMvc.perform(
      post( "/people/availability?peopleType=Candidate" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andExpect( MockMvcResultMatchers.status().isOk() );

    jsonObject = new JSONObject();
    jsonObject.put( "name", "David" );
    array = new JSONArray();
    jo = new JSONObject();
    jo.put( "name", "Monday" );
    jo.put( "ranges", "9-16" );
    array.put( jo );
    jsonObject.put( "availabilityEntityList", array );
    this.mockMvc.perform(
      post( "/people/availability?peopleType=Interviewer" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andExpect( MockMvcResultMatchers.status().isOk() );

    MvcResult mvcResult = this.mockMvc.perform(
      get( "/people/slots" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andReturn();

    String responseData = mvcResult.getResponse().getContentAsString();

    JSONObject jsonResponse = new JSONObject( responseData );
    Assert.assertSame( jsonResponse.getJSONObject( "days" ).length(), 1 );
    Assert
      .assertSame( jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).length(), 7 );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).length(), 2 );

    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .get( "time" ), 9 );

    Assert.assertEquals(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "name" ), "Carl" );

    Assert.assertEquals(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 1 ).get( "name" ), "David" );
    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 0 ).get( "peopleType" ), 2 );


    Assert.assertSame(
      jsonResponse.getJSONObject( "days" ).getJSONObject( "Monday" ).getJSONArray( "hr" ).getJSONObject( 0 )
        .getJSONArray( "people" ).getJSONObject( 1 ).get( "peopleType" ), 1 );

    mvcResult = this.mockMvc.perform(
      get( "/people/slots/Carl" )
        .contentType( MediaType.APPLICATION_JSON )
        .accept( MediaType.APPLICATION_JSON ) )
      .andReturn();

    responseData = mvcResult.getResponse().getContentAsString();
    jsonResponse = new JSONObject( responseData );
    Assert.assertSame(
      jsonResponse.getJSONArray( "Monday" ).getJSONObject( 0 ).get( "hr" ),9);

    Assert.assertSame(  jsonResponse.getJSONArray( "Monday" ).getJSONObject( 0 ).getJSONArray( "interviewers" ).length(),1);

    Assert.assertEquals(jsonResponse.getJSONArray( "Monday" ).getJSONObject( 0 ).getJSONArray( "interviewers" ).get( 0 ),"David");

    Assert.assertEquals( jsonResponse.getJSONArray( "Monday" ).getJSONObject( 0 ).get( "candidate" ),"Carl");



  }


  @Test
  public void testGetPossibleScheduleForAnInexistentCandidate() throws Exception {
    this.mockMvc.perform(
      get( "/people/slots/Carl" )
        .contentType( MediaType.APPLICATION_JSON )
        .accept( MediaType.APPLICATION_JSON ) )
      .andExpect( MockMvcResultMatchers.status().isNotFound() );
  }

  @Test
  public void testPostWithOneValueMissing() throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put( "name", null );
    JSONArray array = new JSONArray();
    JSONObject jo = new JSONObject();
    jo.put( "name", "Monday" );
    jo.put( "ranges", "9-16" );
    array.put( jo );
    jsonObject.put( "availabilityEntityList", array );
    MvcResult mvcResult = this.mockMvc.perform(
      post( "/people/availability?peopleType=Interviewer" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andReturn();
    String responseData = mvcResult.getResponse().getContentAsString();
    JSONObject jsonResponse = new JSONObject( responseData );
    Assert.assertEquals(
      jsonResponse.get( "status" ),400);
    Assert.assertSame(
      jsonResponse.getJSONArray( "errors" ).length(),1);
    Assert.assertEquals(
      jsonResponse.getJSONArray( "errors" ).get( 0 ),"Person name must not be blank!");


  }


  @Test
  public void testPostWithTwoValuesMissing() throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put( "name", null );
    JSONArray array = new JSONArray();
    JSONObject jo = new JSONObject();
    jo.put( "name", "Monday" );
    jo.put( "ranges", null );
    array.put( jo );
    jsonObject.put( "availabilityEntityList", array );
    MvcResult mvcResult = this.mockMvc.perform(
      post( "/people/availability?peopleType=Interviewer" )
        .contentType( MediaType.APPLICATION_JSON )
        .content( jsonObject.toString() )
        .accept( MediaType.APPLICATION_JSON ) )
      .andReturn();
    String responseData = mvcResult.getResponse().getContentAsString();
    JSONObject jsonResponse = new JSONObject( responseData );
    Assert.assertEquals(
      jsonResponse.get( "status" ),400);
    Assert.assertSame(
      jsonResponse.getJSONArray( "errors" ).length(),2);
    Assert.assertEquals(
      jsonResponse.getJSONArray( "errors" ).get( 0 ),"Person name must not be blank!");
    Assert.assertEquals(
      jsonResponse.getJSONArray( "errors" ).get( 1 ),"Dates must not be blank!");


  }


}
