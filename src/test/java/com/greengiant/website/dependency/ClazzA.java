package com.greengiant.website.dependency;

public class ClazzA {

    //    private ClazzB b = new ClazzB();
    private ClazzB b;

    public ClazzB getB() {
        return b;
    }

    public void setB(ClazzB b) {
        this.b = b;
    }

    // todo 为什么放开toString就stackOverFlow了？ https://www.zhihu.com/question/438247718/answer/1730527725
//    @Override
//    public String toString() {
//        return "ClazzA{" +
//                "b=" + b +
//                '}';
//    }
}
