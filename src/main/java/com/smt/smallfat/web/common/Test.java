package com.smt.smallfat.web.common;

public class Test {
    public static void main(String[] args) {
        A a = new A();
        a.a = 1;
        a.b = "2";
        Test  t = new Test();
        t.a(a);
        System.out.println(a);

    }

    public void a(A a){
        a.a = 100;
        a.b = "200";
        a.c = new B(111,222);
    }
}
class A{
   public int a;
   public  String b;
   public  B c = new B(3,4);

    @Override
    public String toString() {
        return a+"|"+b+"|"+c;
    }
}
class B{
    int c;
    int d;

    public B(int c, int d) {
        this.c = c;
        this.d = d;
    }

    @Override
    public String toString() {
        return c+"|"+d;
    }
}