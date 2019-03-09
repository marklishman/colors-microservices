package io.lishman.green.model;

import java.util.List;

// TODO immutable value object

public class InstanceDetails {
    private String name;
    private String instance;
    private int port;
    private String config;
    private GreenDetails details;
    private List<InstanceDetails> calls;

    public InstanceDetails() {
    }

    public InstanceDetails(
            final String name,
            final String instance,
            final int port,
            final String config,
            final GreenDetails details,
            final List<InstanceDetails> calls
    ) {
        this.name = name;
        this.instance = instance;
        this.port = port;
        this.config = config;
        this.details = details;
        this.calls = calls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public GreenDetails getDetails() {
        return details;
    }

    public void setDetails(GreenDetails details) {
        this.details = details;
    }

    public List<InstanceDetails> getCalls() {
        return calls;
    }

    public void setCalls(List<InstanceDetails> calls) {
        this.calls = calls;
    }

    @Override
    public String toString() {
        return "InstanceDetails{" +
                "name='" + name + '\'' +
                ", instance='" + instance + '\'' +
                ", port=" + port +
                ", config='" + config + '\'' +
                ", calls=" + calls +
                '}';
    }
}
