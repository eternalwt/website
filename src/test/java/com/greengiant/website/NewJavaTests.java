package com.greengiant.website;

import com.greengiant.website.model.Student;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class NewJavaTests {

    // -------------------------------- java 10 ---------------------------------

    @Test
    public void testVar() {
        var list = new ArrayList<String>();
        list.add("hello，world！");
        System.out.println(list);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenModifyCopyOfList_thenThrowsException() {
        List<Integer> someIntList = new ArrayList<>();
        someIntList.add(1);
        someIntList.add(2);
        someIntList.add(3);
        List<Integer> copyList = List.copyOf(someIntList);
        copyList.add(4);
    }

    // --------------------------------- java 12 --------------------------------

    @Test
    public void TestTeeing(){
        List<Student> studentList= Arrays.asList(
                new Student("alice", 90, true),
                new Student("boy", 20, true),
                new Student("bruce", 40, false),
                new Student("batman", 100, false)
        );
        String teeingResult=studentList.stream().collect(
                Collectors.teeing(
                        Collectors.averagingInt(Student::getAge),
                        Collectors.summingInt(Student::getAge),
                        (s1,s2)-> s1+ ":"+ s2
                )
        );
        System.out.println(teeingResult);
    }

    @Test
    public void testSwitchExpressions() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        String typeOfDay = switch (dayOfWeek) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Working Day";
            case SATURDAY, SUNDAY -> "Day Off";
        };
        System.out.println(typeOfDay);
    }

//    @Test
//    public void testInstanceOf() {
//        Object obj = "Hello World!";
//        if (obj instanceof String s) {
//            int length = s.length();
//            System.out.println(length);
//        }
//    }

    // --------------------------------- java 13 --------------------------------

    @Test
    @SuppressWarnings("preview")
    public void whenSwitchingOnOperationSquareMe_thenWillReturnSquare() {
        var me = 4;
        var operation = "squareMe";
        var result = switch (operation) {
            case "doubleMe" -> {
                yield me * 2;
            }
            case "squareMe" -> {
                yield me * me;
            }
            default -> me;
        };

        assertEquals(16, result);
    }

//    @Test
//    public void testTextBlocks() {
//        String TEXT_BLOCK_JSON = """
//        {
//            "name" : "Baeldung",
//            "website" : "https://www.%s.com/"
//        }
//        """;
//    }

}
