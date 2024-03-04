package org.example;

public class Pokemon {
    private int id;
    private String name;
    private String href;
    private String[] types = new String[2];

    public Pokemon(int id, String name, String href, String type1, String type2) {
        this.id = id;
        this.name = name;
        this.href = href;
        this.types[0] = type1;
        this.types[1] = type2;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHref() {
        return href;
    }

    public String[] getTypes() {
        return types;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", href='" + href + '\'' +
                ", types=[" + types[0] + ", " + types[1] + "]" +
                '}';
    }
}
