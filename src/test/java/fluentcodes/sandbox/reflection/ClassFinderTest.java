package fluentcodes.sandboxjava.reflection;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class ClassFinderTest {

    private static final String SEARCH = ".*/HashMap.class";
    private static final Map<String, String> map = new TreeMap<>();
    private static final Map<String, String> origin = new TreeMap<>();

    // https://jaxenter.de/classpath-scan-im-eigenbau-aus-der-java-trickkiste-12963
    @Test
    public void findAll() throws IOException {
        ClassFinder finder = new ClassFinder();
        finder.find();
        System.out.println(finder.scanLog());
    }

    @Test
    public void findHashMap() throws IOException {
        ClassFinder finder = new ClassFinder();
        finder.addPathFilter(".*Map.*");
        finder.find();
        System.out.println(finder.scanLog());
    }

    @Test
    public void findHashMapInJarRT() throws IOException {
        ClassFinder finder = new ClassFinder();
        finder
                .addPathFilter(".*/HashMap.class")
                .addJarFilter(".*/rt.jar")
                .addPathExcludeFilter(".*Exception.class");
        finder.find();
        System.out.println(finder.scanLog());
    }

    @Test
    public void findJavaInJarRT() throws IOException {
        ClassFinder finder = new ClassFinder();
        finder
                .addPathFilter("java/util/[^/]*")
                .addJarFilter(".*/rt.jar")
                .addPathExcludeFilter(".*Exception.class");
        finder.find();
        System.out.println(finder.scanLog());
    }

    @Test
    public void findLangInJarRT() throws IOException {
        System.out.println(Integer.class.getName());
        ClassFinder finder = new ClassFinder();
        finder
                .addPathFilter(".*/lang/Integer.*");
        finder.find();
        System.out.println(finder.scanLog());
    }

    @Test
    public void findInteger() throws IOException {
        System.out.println(Integer.class.getName());
        ClassFinder finder = new ClassFinder();
        finder
                .addPathFilter(".*/Integer.class");
        finder.find();
        System.out.println(finder.scanLog());
    }

    @Test
    public void findMat() throws IOException {
        System.out.println(Integer.class.getName());
        ClassFinder finder = new ClassFinder();
        finder
                .addPathFilter(".*mat.*");
        finder.find();
        System.out.println(finder.scanLog());
    }

    //https://stackoverflow.com/questions/2548384/java-get-a-list-of-all-classes-loaded-in-the-jvm
    @Test
    public void withFieldClasses() {
    Field f;
        try{
        f = ClassLoader.class.getDeclaredField("classes");
        f.setAccessible(true);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Vector<Class> classes = (Vector<Class>) f.get(classLoader);
        for (Class cls : classes) {
            java.net.URL location = cls.getResource('/' + cls.getName().replace('.',
                    '/') + ".class");
            System.out.println("<p>" + location + "<p/>");
        }
    } catch(Exception e){

        e.printStackTrace();
    }
}
@Test
    public void withReflection () {
        //Reflections reflections = new Reflections("my.pkg", new SubTypesScanner(false));
    }

    @Test
    public void fromBootsTrap() {
            // Even IBM JDKs seem to use this property...
            String classpath = System.getProperty("sun.boot.class.path");
            Collection<String> list = getPackageFromClassPath(classpath);

    }

    public Collection<String> getPackages() {
        String classpath = System.getProperty("java.class.path");
        return getPackageFromClassPath(classpath);
    }

    //https://stackoverflow.com/questions/3845823/getting-list-of-fully-qualified-names-from-a-simple-name
    public static Set<String> getPackageFromClassPath(String classpath) {
        Set<String> packages = new HashSet<String>();
        String[] paths = classpath.split(File.pathSeparator);
        for (String path : paths) {
            if (path.trim().length() == 0) {
                continue;
            } else {
                File file = new File(path);
                if (file.exists()) {
                    /**String childPath = file.getAbsolutePath();
                    if (childPath.endsWith(".jar")) {
                        packages.addAll(ClasspathPackageProvider
                                .readZipFile(childPath));
                    } else {
                        packages.addAll(ClasspathPackageProvider
                                .readDirectory(childPath));
                    }*/
                }
            }

        }
        return packages;
    }


}
