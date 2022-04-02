package org.eu.nl.dndmapp.dmaserver.models;

import java.util.ArrayList;
import java.util.List;

public class EntityPageResponse<E> {
    /**
     * A list of entries that has an equal part of the dataset,
     * the size of which is determined by the totalEntries.
     */
    private final List<E> content = new ArrayList<>();

    /** The total number of entries in the dataset. */
    private final int totalEntries;

    /** The number of entries per page. */
    private final int entriesPerPage;

    /** The total number of pages that encompasses the whole dataset. */
    private final int totalPages;

    /** The current page of the dataset. */
    private final int pageNumber;

    /**
     * The direction in which way the content is sorted.
     * Should be either 'ASC' or 'DESC'.
     */
    private final String sortingDirection;

    public EntityPageResponse(int totalEntries, int entriesPerPage, int totalPages, int pageNumber, String sortingDirection, List<E> content) {
        this.totalEntries = totalEntries;
        this.entriesPerPage = entriesPerPage;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber;
        this.sortingDirection = sortingDirection;

        this.content.addAll(content);
    }

    public List<E> getContent() {
        return content;
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    public int getEntriesPerPage() {
        return entriesPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getSortingDirection() {
        return sortingDirection;
    }
}
