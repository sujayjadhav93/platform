package org.hobbit.controller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tim Ermilov on 15.05.17.
 */
public class HobbitConfig {
    public class TimeoutConfig {
        public long benchmarkTimeout;
        public long challengeTimeout;

        public TimeoutConfig(long bt, long ct) {
            benchmarkTimeout = bt;
            challengeTimeout = ct;
        }
    }

    public Map<String, LinkedHashMap<String, Object>> timeouts;

    public static HobbitConfig loadConfig() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        HobbitConfig cfg = mapper.readValue(new File("config.yaml"), HobbitConfig.class);
        return cfg;
    }

    public TimeoutConfig getTimeout(String benchmarkUrl) {
        LinkedHashMap item = timeouts.get(benchmarkUrl);
        if (item == null) {
            return null;
        }
        long bt = -1;
        Object b = item.get("benchmark");
        if (b != null) {
            bt = Long.parseLong(b.toString());
        }

        long ct = -1;
        Object c = item.get("challenge");
        if (c != null) {
            ct = Long.parseLong(c.toString());
        }
        return new TimeoutConfig(bt, ct);
    }


}