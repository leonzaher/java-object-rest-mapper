package com.leonzaher.objecthttpmapper;

import org.junit.Test;
import static org.junit.Assert.*;

public class ObjectHttpMapperTest {

    @Test
    public void convertToParams() throws Exception {
        class TestObj {
            public String first = "Hello world";
            public String occupation = "programmer";
            public Integer number = 4;

            protected String howareyou = "fine";
            protected boolean linux = true;

            Byte byyte = 11;
            String id = null;

            private Short number2 = 155;
            private String john = "john";
        }

        TestObj obj = new TestObj();


        String result1 = "?first=Hello world&occupation=programmer&number=4&howareyou=fine&linux=true&byyte=11&number2=155&john=john";
        ObjectHttpMapper mapper1 = new ObjectHttpMapper(AccessLevel.ALL);
        assertEquals("Parameters 1 - Access level ALL, excluded 0", result1, mapper1.convertToParams(obj));

        String result2 = "?first=Hello world&occupation=programmer&number=4";
        ObjectHttpMapper mapper2 = new ObjectHttpMapper(AccessLevel.PUBLIC);
        assertEquals("Parameters 2 - Access level PUBLIC, excluded 0", result2, mapper2.convertToParams(obj));

        String result3 = "?howareyou=fine&linux=true&byyte=11&number2=155&john=john";
        ObjectHttpMapper mapper3 = new ObjectHttpMapper(AccessLevel.NOT_PUBLIC);
        assertEquals("Parameters 3 - Access level NOT_PUBLIC, excluded 0", result3, mapper3.convertToParams(obj));

        String result4 = "?first=Hello world&number=4";
        ObjectHttpMapper mapper4 = new ObjectHttpMapper(AccessLevel.PUBLIC, "occupation", "byyte", "john");
        assertEquals("Parameters 4 - Access level PUBLIC, excluded 3", result4, mapper4.convertToParams(obj));

        String result5 = "?howareyou=fine&linux=true&number2=155";
        ObjectHttpMapper mapper5 = new ObjectHttpMapper(AccessLevel.NOT_PUBLIC, "occupation", "byyte", "john");
        assertEquals("Parameters 5 - Access level NOT_PUBLIC, excluded 3", result5, mapper5.convertToParams(obj));

        String result6 = "";
        ObjectHttpMapper mapper6 = new ObjectHttpMapper(AccessLevel.PUBLIC, "first", "occupation", "number");
        assertEquals("Parameters 6 - Access level PUBLIC, excluded 3", result6, mapper6.convertToParams(obj));
    }

    @Test
    public void convertEmpty() throws Exception {
        class TestObj {}

        TestObj obj = new TestObj();


        String result1 = "";

        ObjectHttpMapper mapper1Par = new ObjectHttpMapper(AccessLevel.ALL);
        assertEquals("Parameters 1 - empty", result1, mapper1Par.convertToParams(obj));

        //ObjectHttpMapper mapper1Hea = new ObjectHttpMapper(AccessLevel.ALL);
        //assertEquals("Headers 1 - empty", result1, mapper1Hea.convertToParams(obj));
    }

    @Test
    public void convertNull() throws Exception {
        class TestObj {}

        TestObj obj = null;


        String result1 = "";

        ObjectHttpMapper mapper1Par = new ObjectHttpMapper(AccessLevel.ALL);
        assertEquals("Parameters 1 - null", result1, mapper1Par.convertToParams(obj));

        //ObjectHttpMapper mapper1Hea = new ObjectHttpMapper(AccessLevel.ALL);
        //assertEquals("Headers 1 - null", result1, mapper1Hea.convertToParams(obj));
    }

    @Test
    public void convertNested() throws Exception {
        class Nested {
            public String occupation = "programmer";
            Byte byyte = 11;

            @Override
            public String toString() {
                return occupation + byyte;
            }
        }

        class TestObj {
            public String first = "Hello world";

            protected String howareyou = "fine";

            private Short number2 = 155;

            Nested nested = new Nested();
        }

        TestObj obj = new TestObj();


        String result1 = "?first=Hello world&howareyou=fine&number2=155&nested=programmer11";

        ObjectHttpMapper mapper1Par = new ObjectHttpMapper(AccessLevel.ALL);
        assertEquals("Parameters 1 - nested", result1, mapper1Par.convertToParams(obj));

        //ObjectHttpMapper mapper1Hea = new ObjectHttpMapper(AccessLevel.ALL);
        //assertEquals("Headers 1 - null", result1, mapper1Hea.convertToParams(obj));
    }

    @Test
    public void convertContainingNulls() throws Exception {
        class TestObj {
            public String first = null;

            protected String id = null;

            private Short number2 = null;
        }

        TestObj obj = new TestObj();


        String result1 = "";

        ObjectHttpMapper mapper1Par = new ObjectHttpMapper(AccessLevel.ALL);
        assertEquals("Parameters 1 - containing nulls", result1, mapper1Par.convertToParams(obj));

        //ObjectHttpMapper mapper1Hea = new ObjectHttpMapper(AccessLevel.ALL);
        //assertEquals("Headers 1 - containing nulls", result1, mapper1Hea.convertToParams(obj));


        obj.id = "1234";

        String result2 = "?id=1234";

        ObjectHttpMapper mapper2Par = new ObjectHttpMapper(AccessLevel.ALL);
        assertEquals("Parameters 2 - containing nulls", result2, mapper2Par.convertToParams(obj));

        //ObjectHttpMapper mapper2Hea = new ObjectHttpMapper(AccessLevel.ALL);
        //assertEquals("Headers 2 - containing nulls", result2, mapper2Hea.convertToParams(obj));
    }
}