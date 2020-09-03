package fluentcodes.sandbox;

import com.lexicalscope.jewel.cli.Option;

/**
 * Created by werner.diwischek on 16.02.18.
 * http://jewelcli.lexicalscope.com/usage.html nice documentation
 */
public interface Params {
  @Option(shortName = "c", defaultValue = "defaultCall")
  String getCall();


  @Option(shortName = "f", defaultValue = "defaultFilter")
  String getFilter();

  @Option(shortName = "n", defaultValue = "1")
  Integer getNumber();

}
