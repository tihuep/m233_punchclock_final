package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.controller.EntryController;
import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.error_handling.ForbiddenException;
import ch.zli.m223.punchclock.error_handling.NothingFoundException;
import ch.zli.m223.punchclock.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class EntryService {
    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<Entry> getAllEntries(ApplicationUser user) {
        List<Entry> entries = entryRepository.findAll();

        entries.removeIf(new Predicate<Entry>() {
            @Override
            public boolean test(Entry entry) {
                return !entry.getUser().equals(user) && !user.getRole().getAdmin();
            }
        });

        if (entries.size() == 0){
            throw new NothingFoundException("No Entries found");
        }

        return entries;
    }

    public Entry getSingleEntry(Long id, ApplicationUser user){
        Entry entry = entryRepository.findById(id).get();
        if (!entry.getUser().equals(user) && !user.getRole().getAdmin()) {
            throw new ForbiddenException("Entry not of this user");
        }
        return entryRepository.findById(id).get();

    }

    public Entry createEntry(Entry entry) {
        return entryRepository.saveAndFlush(entry);
    }

    public Entry editEntry(Entry entry, Long id, ApplicationUser user){
        Entry entry1 = this.getSingleEntry(id, user);
        if (!entry1.getUser().equals(user) && !user.getRole().getAdmin())
            throw new ForbiddenException("Entry not of this user");
        entry.setUser(user);
        entry.setId(id);
        return entryRepository.save(entry);
    }

    public void deleteEntry(Long id, ApplicationUser user){
        Entry entry = this.getSingleEntry(id, user);
        entryRepository.delete(entry);
    }
}
