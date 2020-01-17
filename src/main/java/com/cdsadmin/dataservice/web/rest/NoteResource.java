package com.cdsadmin.dataservice.web.rest;

import com.cdsadmin.dataservice.domain.Merger;
import com.cdsadmin.dataservice.domain.Note;
import com.cdsadmin.dataservice.domain.Transfer;
import com.cdsadmin.dataservice.repository.MergerRepository;
import com.cdsadmin.dataservice.repository.NoteRepository;
import com.cdsadmin.dataservice.repository.TransferRepository;
import com.cdsadmin.dataservice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.h2.command.dml.Merge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cdsadmin.dataservice.domain.Note}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NoteResource {

    private final Logger log = LoggerFactory.getLogger(NoteResource.class);

    private static final String ENTITY_NAME = "cdsdataserviceNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoteRepository noteRepository;

    @Autowired
    MergerRepository mergerRepo;

    @Autowired
    TransferRepository transferRepo;

    public NoteResource(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * {@code POST  /notes} : Create a new note.
     *
     * @param note the note to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new note, or with status {@code 400 (Bad Request)} if the note has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notes")
    public ResponseEntity<Note> createNote(@RequestBody Note note) throws URISyntaxException {
        log.debug("REST request to save Note : {}", note);
        if (note.getId() != null) {
            throw new BadRequestAlertException("A new note cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Note result = noteRepository.save(note);
        return ResponseEntity.created(new URI("/api/notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notes} : Updates an existing note.
     *
     * @param note the note to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated note,
     * or with status {@code 400 (Bad Request)} if the note is not valid,
     * or with status {@code 500 (Internal Server Error)} if the note couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notes")
    public ResponseEntity<Note> updateNote(@RequestBody Note note) throws URISyntaxException {
        log.debug("REST request to update Note : {}", note);
        if (note.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Note result = noteRepository.save(note);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, note.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notes} : get all the notes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notes in body.
     */
    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        log.debug("REST request to get all Notes");
        return noteRepository.findAll();
    }

    /**
     * {@code GET  /notes/:id} : get the "id" note.
     *
     * @param id the id of the note to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the note, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notes/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        log.debug("REST request to get Note : {}", id);
        Optional<Note> note = noteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(note);
    }

    @GetMapping("/getNotesByCustomerTo/{customerId}/{systemId}")
    public  List<Note> getNotesByCustomerTo(@PathVariable String customerId, @PathVariable String systemId) {
        log.debug("REST request to get Note : {}", customerId);
        //List<Merger> mergers = mergerRepo.findByCustomerFromOrCustomerTo(customerId, customerId, systemId);
        List<Merger> mergers = mergerRepo.findByCustomerTo(customerId, systemId);
        List<Note> notes = new ArrayList<Note>();
        for(Merger merger : mergers) {
            //List<Note> noteByMerger = noteRepository.findByMerger(merger);
            notes.addAll(noteRepository.findByMerger(merger));
        }
        return notes;
    }

    @GetMapping("/getNotesByCustomerFrom/{customerId}/{systemId}")
    public  List<Note> getNotesByCustomerFrom(@PathVariable String customerId, @PathVariable String systemId) {
        log.debug("REST request to get Note : {}", customerId);
        //List<Merger> mergers = mergerRepo.findByCustomerFromOrCustomerTo(customerId, customerId, systemId);
        List<Merger> mergers = mergerRepo.findByCustomerFrom(customerId, systemId);
        List<Note> notes = new ArrayList<Note>();
        for(Merger merger : mergers) {
            //List<Note> noteByMerger = noteRepository.findByMerger(merger);
            notes.addAll(noteRepository.findByMerger(merger));
        }
        notes.addAll(noteRepository.findAllNullMerger());
        return notes;
    }

    @GetMapping("/transfer/getNotesByCustomerTo/{customerId}/{systemId}")
    public  List<Note> getTransferNotesByCustomerTo(@PathVariable String customerId, @PathVariable String systemId) {
        log.debug("REST request to get Note : {}", customerId);
        List<Transfer> transfers = transferRepo.findByCustomerTo(customerId, systemId);
        List<Note> notes = new ArrayList<Note>();
        for(Transfer transfer : transfers) {
            notes.addAll(noteRepository.findByTransfer(transfer));
        }
        return notes;
    }

    @GetMapping("/transfer/getNotesByCustomerFrom/{customerId}/{systemId}")
    public  List<Note> getTransferNotesByCustomerFrom(@PathVariable String customerId, @PathVariable String systemId) {
        log.debug("REST request to get Note : {}", customerId);
        //Fetch Transfer's where supplied customer is in CUSTOMET_TO column
        List<Transfer> transfers = transferRepo.findByCustomerTo(customerId, systemId);
        List<Note> notes = new ArrayList<Note>();
        for(Transfer transfer : transfers) {
            //List<Note> noteByMerger = noteRepository.findByMerger(merger);
            notes.addAll(noteRepository.findByTransfer(transfer));
        }
        notes.addAll(noteRepository.findAllNullTrarnsfer());
        return notes;
    }



    @GetMapping("/getNotesByMerger/{id}")
    public  List<Note> getNotesByMerger(@PathVariable Long id) {
        log.debug("REST request to get Note : {}", id);
        Optional<Merger> m = mergerRepo.findById(id);
        List<Note> note = noteRepository.findByMerger(m.get());
        return note;
    }
    /**
     * {@code DELETE  /notes/:id} : delete the "id" note.
     *
     * @param id the id of the note to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        log.debug("REST request to delete Note : {}", id);
        noteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/getNotesByTransfer/{id}")
    public  List<Note> getNotesByTransfer(@PathVariable Long id) {
        log.debug("REST request to get Note : {}", id);
        Optional<Transfer> m = transferRepo.findById(id);
        List<Note> note = noteRepository.findByTransfer(m.get());
        return note;
    }
}
