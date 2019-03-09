package io.lishman.green.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class InstanceDetails {
    private final String name;
    private final String instance;
    private final int port;
    private final String config;
    private final GreenDetails details;
    private final List<InstanceDetails> calls;

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

    public final String getName() {
        return name;
    }

    public final String getInstance() {
        return instance;
    }

    public final int getPort() {
        return port;
    }

    public final String getConfig() {
        return config;
    }

    public final GreenDetails getDetails() {
        return details;
    }

    public final List<InstanceDetails> getCalls() {
        return Collections.unmodifiableList(calls);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceDetails that = (InstanceDetails) o;
        return port == that.port &&
                Objects.equals(name, that.name) &&
                Objects.equals(instance, that.instance) &&
                Objects.equals(config, that.config) &&
                Objects.equals(details, that.details) &&
                Objects.equals(calls, that.calls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, instance, port, config, details, calls);
    }

    @Override
    public String toString() {
        return "InstanceDetails{" +
                "name='" + name + '\'' +
                ", instance='" + instance + '\'' +
                ", port=" + port +
                ", config='" + config + '\'' +
                ", details=" + details +
                ", calls=" + calls +
                '}';
    }
}
