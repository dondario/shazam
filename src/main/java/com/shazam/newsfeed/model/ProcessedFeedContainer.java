package com.shazam.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcessedFeedContainer implements Serializable {
    List<ProcessedEvent> items;

    public ProcessedFeedContainer(final List<ProcessedEvent> items) {
        this.items = items;
    }

    public ProcessedFeedContainer() {
        this.items = new ArrayList<>();
    }

    public void addItem(ProcessedEvent item) {
        items.add(item);
    }

    @JsonProperty("feed")
    public List<ProcessedEvent> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ProcessedFeedContainer rhs = (ProcessedFeedContainer) obj;
        return new EqualsBuilder()
                .append(this.items, rhs.items)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(items)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "EventFeedContainer{" +
                "items=" + items +
                '}';
    }
}
