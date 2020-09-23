package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {
    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }

    public Entry getSingleEntry(Long id){
        return entryRepository.getOne(id);
    }

    public Entry createEntry(Entry entry) {
        return entryRepository.saveAndFlush(entry);
    }

    public Entry editEntry(Entry entry, Long id){
        entry.setId(id);
        return entryRepository.save(entry);
    }

    public void deleteEntry(Long id){
        entryRepository.deleteById(id);
    }
}
