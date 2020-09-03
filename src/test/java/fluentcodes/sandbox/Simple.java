package fluentcodes.sandbox;

public class Simple {
    Integer i;
    String s;
    public Simple() {

    }
    public Simple(String s, Integer i) {
        this.s = s;
        this.i = i;
    }
    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
