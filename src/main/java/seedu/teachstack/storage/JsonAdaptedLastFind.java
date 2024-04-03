package seedu.teachstack.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.teachstack.commons.exceptions.IllegalValueException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.ModelManager;
import seedu.teachstack.model.group.Group;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.PersonInGroupPredicate;

public class JsonAdaptedLastFind extends JsonAdaptedField {
    private final List<JsonAdaptedGroup> groups = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedLastFind(@JsonProperty("groups") List<JsonAdaptedGroup> groups) {
        if (groups != null) {
            this.groups.addAll(groups);
        }
    }

    public JsonAdaptedLastFind(Predicate<Person> predicate) {
        if (predicate instanceof PersonInGroupPredicate) {
            PersonInGroupPredicate lastFind = (PersonInGroupPredicate) predicate;
            this.groups.addAll(lastFind.getGroups().stream()
                    .map(JsonAdaptedGroup::new)
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void performAction() {
        if (!groups.isEmpty()) {
            try {
                Set<Group> set = new HashSet<>();
                for (JsonAdaptedGroup group : groups) {
                    Group toModelType = group.toModelType();
                    set.add(toModelType);
                }
                ModelManager.setStartingFilter(new PersonInGroupPredicate(set));
            } catch (IllegalValueException ive) {
                ModelManager.setStartingFilter(Model.PREDICATE_SHOW_ALL_PERSONS);
            }
        }
    }
}
