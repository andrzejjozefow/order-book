package pl.andrzejjozefow.softja;

public class User {

    private String name;
    private Integer quantity;

    public User(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "User{" +
            "name='" + name + '\'' +
            ", quantity=" + quantity +
            '}';
    }
}
