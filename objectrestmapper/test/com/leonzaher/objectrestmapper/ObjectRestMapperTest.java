package com.leonzaher.objectrestmapper;

import org.junit.Test;
import static org.junit.Assert.*;

public class ObjectRestMapperTest {

    @Test
    public void convertToParams() throws Exception {
        class TestObj {
            public String first = "Hello world";
            public String occupation = "programmer";
            public Integer number = 4;

            protected String howareyou = "fine";
            protected boolean linux = true;

            Byte byyte = 11;

            private Short number2 = 155;
            private String john = "john";
        }

        TestObj obj = new TestObj();


        String result1 = "?first=Hello world&occupation=programmer&number=4&howareyou=fine&linux=true&byyte=11&number2=155&john=john";
        ObjectRestMapper mapper1 = new ObjectRestMapper(AccessLevel.ALL);
        assertEquals("Parameters 1 - Access level ALL, excluded 0", result1, mapper1.convertToParams(obj));

        String result2 = "?first=Hello world&occupation=programmer&number=4";
        ObjectRestMapper mapper2 = new ObjectRestMapper(AccessLevel.PUBLIC);
        assertEquals("Parameters 2 - Access level PUBLIC, excluded 0", result2, mapper2.convertToParams(obj));

        String result3 = "?howareyou=fine&linux=true&byyte=11&number2=155&john=john";
        ObjectRestMapper mapper3 = new ObjectRestMapper(AccessLevel.NOT_PUBLIC);
        assertEquals("Parameters 3 - Access level NOT_PUBLIC, excluded 0", result3, mapper3.convertToParams(obj));

        String result4 = "?first=Hello world&number=4";
        ObjectRestMapper mapper4 = new ObjectRestMapper(AccessLevel.PUBLIC, "occupation", "byyte", "john");
        assertEquals("Parameters 4 - Access level PUBLIC, excluded 3", result4, mapper4.convertToParams(obj));

        String result5 = "?howareyou=fine&linux=true&number2=155";
        ObjectRestMapper mapper5 = new ObjectRestMapper(AccessLevel.NOT_PUBLIC, "occupation", "byyte", "john");
        assertEquals("Parameters 5 - Access level NOT_PUBLIC, excluded 3", result5, mapper5.convertToParams(obj));

        String result6 = "";
        ObjectRestMapper mapper6 = new ObjectRestMapper(AccessLevel.PUBLIC, "first", "occupation", "number");
        assertEquals("Parameters 6 - Access level PUBLIC, excluded 3", result6, mapper6.convertToParams(obj));
    }

    @Test
    public void convertEmpty() throws Exception {
        class TestObj {}

        TestObj obj = new TestObj();


        String result1 = "";

        ObjectRestMapper mapper1Par = new ObjectRestMapper(AccessLevel.ALL);
        assertEquals("Parameters 1 - empty", result1, mapper1Par.convertToParams(obj));

        //ObjectRestMapper mapper1Hea = new ObjectRestMapper(AccessLevel.ALL);
        //assertEquals("Headers 1 - empty", result1, mapper1Hea.convertToParams(obj));
    }

    @Test
    public void convertNull() throws Exception {
        class TestObj {}

        TestObj obj = null;


        String result1 = "";

        ObjectRestMapper mapper1Par = new ObjectRestMapper(AccessLevel.ALL);
        assertEquals("Parameters 1 - null", result1, mapper1Par.convertToParams(obj));

        //ObjectRestMapper mapper1Hea = new ObjectRestMapper(AccessLevel.ALL);
        //assertEquals("Headers 1 - null", result1, mapper1Hea.convertToParams(obj));
    }
}