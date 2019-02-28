package io.lishman.orange.model;

import java.util.List;

public class InstanceDetails {
    private String name;
    private String instance;
    private int port;
    private String config;
    private List<InstanceDetails> calls;

    public InstanceDetails() {
    }

    public InstanceDetails(
            String name,
            String instance,
            int port,
            String config,
            List<InstanceDetails> calls
    ) {
        this.name = name;
        this.instance = instance;
        this.port = port;
        this.config = config;
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
