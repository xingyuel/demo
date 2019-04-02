package com.xingyueli.demo;

import com.netflix.config.*;
import javax.annotation.PostConstruct;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {
    private final static Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    private DynamicStringProperty strProp;
    private String val;

    @PostConstruct
    private void init() {
        strProp = DynamicPropertyFactory.getInstance().getStringProperty("demo.name", "got ya!");
        val = strProp.get();

        // callback when value is changed
        strProp.addCallback(() -> {
            val = strProp.get();
            LOGGER.info("new value: " + val);
        });
    }

    @GetMapping("/test/{number}")
    public String echo(@PathVariable double number) {
        LOGGER.info("number = " + number);

        return Double.toString(number);
    }

    @GetMapping("/string/{str}")
    public String echoString(@PathVariable String str) {
        LOGGER.info("string = " + str);

        return str;
    }

    @GetMapping("/demo")
    public String getProperty() {
        return val;
    }
}
