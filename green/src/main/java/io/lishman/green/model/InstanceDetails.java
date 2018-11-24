package io.lishman.green.model;

public class InstanceDetails {
    private String name;
    private int instance;
    private int port;

    public InstanceDetails(
            String name,
            int instance,
            int port
    ) {
        this.name = name;
        this.instance = instance;
        this.port = port;
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
}
