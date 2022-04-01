package org.eu.nl.dndmapp.dmaserver.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public class Description extends DmaEntity {
    @Column(
        name = "`title`",
        columnDefinition = "VARCHAR(24) DEFAULT NULL"
    )
    private String title;

    @Column(
        name = "`order`",
        columnDefinition = "TINYINT NOT NULL DEFAULT 0"
    )
    private Integer order;

    @Column(
        name = "`text`",
        columnDefinition = "MEDIUMTEXT NOT NULL"
    )
    private String text;

    /* CONSTRUCTORS */

    public Description() {}

    public Description(String id, String title, Integer order, String text) {
        super(id);

        this.title = title;
        this.order = order;
        this.text = text;
    }

    /* GETTERS & SETTERS */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object) || !(object instanceof Description)) return false;
        if (this == object) return true;

        Description other = (Description) object;

        return this.order.equals(other.order);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Description{ id: '%s', order: '%s' }", this.getId(), this.order);
    }
}
