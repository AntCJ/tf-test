package com.tecforte.blog.web.rest;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecforte.blog.service.EntryService;
import com.tecforte.blog.service.dto.EntryDTO;

import io.github.jhipster.web.util.HeaderUtil;

/**
 * Remove blog entries that contain certain keywords from specific blog.
 */
@RestController
@RequestMapping("/api")
public class SpecificBlogCleaner {

	private final Logger log = LoggerFactory.getLogger(UniversalBlogCleaner.class);

    private static final String ENTITY_NAME = "entry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntryService entryService;

    public SpecificBlogCleaner(EntryService entryService) {
        this.entryService = entryService;
    }
    
    /**
     * {@code DELETE  /blogs/:id/clean} : delete blog entries that contain certain keywords from specific blog
     *
     * @param id the id of the entryDTO to delete if any of the keywords are present.
     * @param keywords the keywords.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blogs/{id}/clean")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id, String keywords) {
    	ArrayList<Long> ids = new ArrayList<>();
    	Pageable pageable = Pageable.unpaged();
    	for (EntryDTO entry : entryService.findAll(pageable).getContent()) {
    		if (entry.getBlogId().equals(id)) {
    			for (String keyword : keywords.split(",")) {
        			if (entry.getContent().toUpperCase().contains(keyword.toUpperCase())) {
        				ids.add(entry.getId());
        			}
        		}
    		}
    	}
    	
    	String idsString = null;
    	if (ids.size() > 0) {
    		for (int i = 0; i < ids.size(); i++) {
    			if (i != 0) {
    				idsString += ", ";
    			}
    			idsString += ids.get(i);
		        log.debug("REST request to delete Entry : {}", ids.get(i));
		        entryService.delete(ids.get(i));
    		}
    	}
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, idsString)).build();
    }
}
