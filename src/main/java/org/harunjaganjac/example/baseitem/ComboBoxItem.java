package org.harunjaganjac.example.baseitem;

import java.util.Objects;

public final class ComboBoxItem {
    private String id;
    private String name;

    public ComboBoxItem(String id, String name) {
        this.id = id;
        this.name = name;
    }
    // Getters and Setters -- START
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", name='" + name + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComboBoxItem that = (ComboBoxItem) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    // Getters and Setters -- END
}
