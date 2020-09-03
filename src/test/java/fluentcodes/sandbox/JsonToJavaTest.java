package fluentcodes.sandbox;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonToJavaTest {

    @Test
    public void mapMap () {
        String mapString ="{\"key\":\"value\"}";
        Gson mapper = new Gson();
        Map map = mapper.fromJson(mapString, Map.class);
        Assert.assertEquals("value", map.get("key"));
    }

    @Test
    public void mapObject () throws IOException {
        String mapString =  "{\"s\":\"value\",\"i\":1}";
        Gson mapper = new Gson();
        Simple mapped = mapper.fromJson(mapString, Simple.class);
        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = mapper.newJsonWriter(writer);
        Assert.assertEquals("value", mapped.getS());
    }

    @Test
    public void mapListOfObject () {
        String mapString =  "[" +
                "{\"s\":\"value1\",\"i\":1}," +
                "{\"s\":\"value2\",\"i\":2}" +
                "]";
        Gson mapper = new Gson();
        Type collectionType = new TypeToken<List<Simple>>(){}.getType();
        List<Simple> mapped = mapper.fromJson(mapString, collectionType);
        Assert.assertEquals("value1", mapped.get(0).getS());
    }

    @Test
    public void mapListOfMap () {
        String mapString =  "[" +
                "{\"s\":\"value1\",\"i\":1}," +
                "{\"s\":\"value2\",\"i\":2}" +
                "]";
        Gson mapper = new Gson();
        List mapped = mapper.fromJson(mapString, List.class);
        Assert.assertEquals("value1", ((Map)mapped.get(0)).get("s"));
    }

    @Test
    public void mapMapOfObject () {
        String mapString =  "{" +
                "\"a\":{\"s\":\"value1\",\"i\":1}," +
                "\"b\":{\"s\":\"value2\",\"i\":2}" +
                "}";
        Gson mapper = new Gson();
        Type collectionType = new TypeToken<Map<String, Simple>>(){}.getType();
        Map<String, Simple> mapped = mapper.fromJson(mapString, collectionType);
        Assert.assertEquals("value1", mapped.get("a").getS());
    }

    @Test
    public void mapMapOfMap () {
        String mapString =  "{" +
                "\"a\":{\"s\":\"value1\",\"i\":1}," +
                "\"b\":{\"s\":\"value2\",\"i\":2}" +
                "}";
        Gson mapper = new Gson();
        Map mapped = mapper.fromJson(mapString, Map.class);
        Assert.assertEquals("value1", ((Map)mapped.get("a")).get("s"));
    }

    @Test
    public void mapMapWithSubStructures ()  {
        String mapString =  "{" +
                "\"map\":{\"a1\":{\"a2\":\"value1\"}}," +
                "\"list\":[[\"value2\"]]" +
                "}";
        Gson mapper = new Gson();
        Map mapped = mapper.fromJson(mapString, Map.class);
        Assert.assertEquals("value1", ((Map)((Map)mapped.get("map")).get("a1")).get("a2"));
    }

}
