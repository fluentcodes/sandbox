package fluentcodes.sandboxjava.reflection;

public class Parent {

    public static class Child {
        private String var;

        public String getVar() {
            return var;
        }

        public void setVar(String var) {
            this.var = var;
        }

        public Integer add(Integer i1, Integer i2) {
            return i1 + i2;
        }
    }
}
