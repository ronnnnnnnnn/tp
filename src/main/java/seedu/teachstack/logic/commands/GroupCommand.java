package seedu.teachstack.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.teachstack.logic.parser.CliSyntax.PREFIX_STUDENTID;

import java.util.HashSet;
import java.util.Set;

import seedu.teachstack.commons.util.ToStringBuilder;
import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.group.Group;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;


/**
 * Creates a group with the given group name and IDs.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a group with people corresponding to the selected IDs. "
            + "Parameters: "
            + "[" + PREFIX_GROUP + "GROUP_NAME] "
            + "[" + PREFIX_STUDENTID + "STUDENTID] "
            + "... (multiple IDs allowed) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "3 \n"
            + PREFIX_STUDENTID + "A0123456X "
            + PREFIX_STUDENTID + "A0123456H ";

    public static final String MESSAGE_GROUP_SUCCESS = "All students were added!";
    public static final String MESSAGE_CLEAR_SUCCESS = "All specified students were removed from any existing groups!";
    public static final String STUDENTS_NOT_FOUND = "The following IDs were not found (and not added to the group): ";
    private final Set<Group> group;
    private final Set<Group> noGroup = new HashSet<>();
    private final Set<StudentId> studentIds;

    /**
     * @param group A Set of type Group with only one Group.
     * @param studentIds A Set of studentIds with possibly multiple Ids.
     */
    public GroupCommand(Set<Group> group, Set<StudentId> studentIds) {
        requireNonNull(group);
        requireNonNull(studentIds);
        this.group = group;
        this.studentIds = studentIds;
    }

    /**
     * @param model {@code Model} which the command should operate on.
     * @return a new CommandResult.
     * @throws CommandException if there are ANY students not found at all.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        String missingIds = "";
        requireNonNull(model);

        if (studentIds.size() == 0) {
            for (StudentId studentId : studentIds) {
                clearGroups(model, studentId);
            }
            return new CommandResult(MESSAGE_CLEAR_SUCCESS);
        }

        for (StudentId studentId : studentIds) {
            //get the existing groups
            Person currentPerson = model.getPerson(studentId);
            Set<Group> newGroup = new HashSet<>();
            Set<Group> existingGroup = currentPerson.getGroups();

            newGroup.addAll(group);
            newGroup.addAll(existingGroup);

            try {
                addGroups(newGroup, model, studentId);
            } catch (CommandException e) {
                missingIds += studentId + " ";
            }
            //there's a dependency on EditCommand, but this for loop shouldn't cause EditCommand to throw any exception.
        }
        if (missingIds.equals("")) {
            return new CommandResult(MESSAGE_GROUP_SUCCESS);
        } else {
            throw new CommandException(STUDENTS_NOT_FOUND + missingIds);
        }
    }

    /**
     * Clears all groups of a student with a particular studentId.
     *
     * @param model Provided model
     * @param studentId StudentId of student
     * @throws CommandException
     */
    private void clearGroups(Model model, StudentId studentId) throws CommandException {
        addGroups(noGroup, model, studentId);
    }

    /**
     * @param groups Set of Groups to student to (on top of existing groups)
     * @param model Provided model
     * @param studentId StudentId of student
     * @throws CommandException
     */
    private void addGroups(Set<Group> groups, Model model, StudentId studentId) throws CommandException {
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setGroups(groups);
        EditCommand editCommand = new EditCommand(studentId, editPersonDescriptor);
        editCommand.execute(model);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCommand)) {
            return false;
        }

        GroupCommand otherGroupCommand = (GroupCommand) other;
        return group.equals(otherGroupCommand.group) && studentIds.equals(otherGroupCommand.studentIds);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("group", group)
                .add("studentIds", studentIds)
                .toString();
    }
}
