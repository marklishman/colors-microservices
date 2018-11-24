package io.lishman.blue.model;

import java.util.List;

public class InstanceDetails {
    private String name;
    private int instance;
    private int port;
    private List<InstanceDetails> calls;

    public InstanceDetails() {
    }

    public InstanceDetails(
            String name,
            int instance,
            int port,
            List<InstanceDetails> calls
    ) {
        this.name = name;
        this.instance = instance;
        this.port = port;
        this.calls = calls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInstance() {
        return instance;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<InstanceDetails> getCalls() {
        return calls;
    }

    public void setCalls(List<InstanceDetails> calls) {
        this.calls = calls;
    }
}
