package fluentcodes.sandbox;

import org.springframework.web.bind.annotation.*;

/**
 * Created by werner.diwischek on 4.9.2020.
 */

@RestController
public class WebController {



    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public String eoPostForm(
            @RequestParam(value = "name", required = true) final String name
    ) {
       return "<h1>Welcome " + name + " from post</h1>";
    }

    @RequestMapping(value = "/welcome/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@PathVariable String name) {
        return "<h1>Welcome " + name + " + from get</h1>";
    }
}
