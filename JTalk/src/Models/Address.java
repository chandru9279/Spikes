package Models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("address")
public class Address {
    public Address() {
    }

    public Address(int Priority, String AddressText) {
        this.Priority = Priority;
        this.AddressText = AddressText;
    }

    @XStreamAlias("priority")
    public Integer Priority;

    @XStreamAlias("addressText")
    public String AddressText;
}
