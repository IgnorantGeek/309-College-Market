package com.example.campusmarket;

import android.content.Context;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MockitoExampleTest {

    private static final String FAKE_STRING = "HELLO WORLD";

    @Mock
    Context mockContext;

    @Before
    public void setup() {
        System.out.println("Testing");
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void mockito_test() {
        assertEquals(4, 2 + 2);

    /*
        // Given a mocked Context injected into the object under test...
        when(mockContext.getString(R.string.hello_world))
                .thenReturn(FAKE_STRING);
        ClassUnderTest myObjectUnderTest = new ClassUnderTest(mockContext);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.getHelloWorldString();

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
     */

    }

    @Test
    public void mockito_test_1() {

        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void mockito_verify_test_1() {
        // checks that the correct method was called
        MainActivity mockActivity = mock(MainActivity.class);
        mockActivity.testMockitoFunction("hello");
        // this will pass
        verify(mockActivity).testMockitoFunction("hello");
    }

    @Test
    public void mockito_verify_test_2() {
        MainActivity mockActivity = mock(MainActivity.class);
        mockActivity.testMockitoFunction("hello");
        // this will fail
//        verify(mockActivity).testMockitoFunction("goodbye");
    }

    @Test
    public void mockito_verify_test_3() {
        // checks how many times the method is called
        MainActivity mockActivity = mock(MainActivity.class);
        mockActivity.testMockitoFunction("hello");
        mockActivity.testMockitoFunction("hello");
        // this will pass
        verify(mockActivity, times(2)).testMockitoFunction("hello");
        // this will fail
//        verify(mockActivity, times(4)).testMockitoFunction("hello");
    }

    @Test
    public void mockito_test_2()
    {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed.
        verify(mockedList).get(0);
    }
}