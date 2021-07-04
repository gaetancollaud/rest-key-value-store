package ch.koalasense.kv;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Key Value store API",
            version = "1.0.0",
            license =
                @License(
                    name = "Apache 2.0",
                    url = "https://www.apache.org/licenses/LICENSE-2.0.html")))
public class KeyValueStoreApplication extends Application {}
