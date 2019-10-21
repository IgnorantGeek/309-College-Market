package com.example.campusmarket;


import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)

public class MockitoTestL {

        @Mock
        private List<String> mockList;

//     @Test
//     void test() {
//        List<String> mockList = mock(List.class);
//        mockList.add("Pankaj");
//        mockList.size();
//
//        verify(mockList).add("Pankaj");
//    }
//
//        @Test
//        public void testMockListAdd() {
//            String addString = "some string";
//            mockList.add(addString);
//
//            //verify that the add method was called with argument 'some string'
//            verify(mockList).add(addString);
//        }
//
//        @Test
//        public void testMockListAddMultiple() {
//            String addString = "some string multiple";
//            mockList.add(addString);
//            mockList.add(addString);
//            mockList.add(addString);
//
//            //verify that the add method was called with argument 'some string'
//            verify(mockList, times(3)).add(addString);
//        }


    }
