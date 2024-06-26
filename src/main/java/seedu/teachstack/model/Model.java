package seedu.teachstack.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.teachstack.commons.core.GuiSettings;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the user prefs' archived book file path.
     */
    Path getArchivedBookFilePath();

    /**
     * Sets the user prefs' archived book file path.
     */
    void setArchivedBookFilePath(Path archivedBookFilePath);

    /**
     * Replaces archived book data with the data in {@code archivedBook}.
     */
    void setArchivedBook(ReadOnlyArchivedBook archivedBook);

    /** Returns the ArchivedBook */
    ReadOnlyArchivedBook getArchivedBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the archived book.
     */
    boolean hasArchivedPerson(Person person);

    /**
    * Deletes the given person.
    * The person must exist in the address book.
    */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Returns true if a person with the same {@code StudentId} as {@code person} exists in the address book.
     */
    boolean hasId(Person person);

    /**
     * Returns true if a person with the same {@code Email} as {@code person} exists in the address book.
     */
    boolean hasEmail(Person person);

    /**
     * Archives the given person.
     * The person must exist in the address book.
     */
    void archivePerson(Person person);

    /**
     * Unarchives the given person.
     * The person must exist in the archived book.
     */
    void unarchivePerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns a person with the given {@code id}. */
    Person getPerson(StudentId id);

    /** Returns a list of persons that are marked weak. */
    List<Person> getWeak();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns archived person with the given {@code id}. */
    Person getArchivedPerson(StudentId id);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /** Method to get filtered list for archived persons. */
    ObservableList<Person> getFilteredArchivedList();

    /**
     * Deletes the given archived person.
     * The person must exist in the archived book.
     */
    void deleteArchivedPerson(Person target);

    /**
     * Replaces the given archived person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the archived book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the archived book.
     */
    void setArchivedPerson(Person target, Person editedPerson);

    /**
     * Updates the filter of the filtered archived list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredArchivedList(Predicate<Person> predicate);

    /**
     * Returns true if a person with the same {@code StudentId} as {@code person} exists in the archived book.
     */
    boolean hasArchivedId(Person person);

    /**
     * Returns true if a person with the same {@code Email} as {@code person} exists in the archived book.
     */
    boolean hasArchivedEmail(Person person);


}
