package fluentcodes.sandbox;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JavaToJsonTest {
    Gson mapper = new Gson();

    @Test
    public void toJson () {
        Map map = new HashMap();
        map.put("key", "value");
        map.put("simple",new Simple("a",1));
        map.put("array", Arrays.asList(new Object[]{"b",2}));
        String serialized  = mapper.toJson(map);
        Assert.assertEquals("{\"array\":[\"b\",2],\"simple\":{\"i\":1,\"s\":\"a\"},\"key\":\"value\"}", serialized);
    }


    @Test
    public void callJsonWriter () throws IOException {
        Map map = new HashMap();
        map.put("key", "value");

        StringWriter writer = new StringWriter();
        JsonWriter jsonWriter = mapper.newJsonWriter(writer);
        jsonWriter.beginObject();
        jsonWriter.name("key1").value("value1");
        jsonWriter.endObject();
        String serialized = writer.toString();
        Assert.assertEquals("{\"key1\":\"value1\"}", serialized);
        //https://www.mkyong.com/java/gson-streaming-to-read-and-write-json/
    }

}
