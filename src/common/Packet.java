package common;

import java.util.regex.Pattern;

public abstract class Packet {
    public abstract String getPacketFormat();

    public abstract Pattern getPacketPattern();

    public abstract Object[] getParameters();

    public abstract Packet fromPayload(String payload);

    public String render() {
        return this.toString();
    }

    public String toString() {
        return String.format(getPacketFormat(), getParameters());
    }
}
