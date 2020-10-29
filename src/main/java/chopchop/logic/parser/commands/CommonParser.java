// CommonParser.java

package chopchop.logic.parser.commands;

import java.util.List;
import java.util.Optional;

import chopchop.commons.util.Pair;
import chopchop.commons.util.Result;
import chopchop.commons.util.StringView;
import chopchop.logic.parser.ArgName;
import chopchop.logic.parser.CommandArguments;

public class CommonParser {

    /**
     * Checks that the named arguments only contain those in the {@code knownArgs} list. If there was an
     * unexpected argument, it returns an error message. If not, it returns an empty optional.
     *
     * If acceptsEditArguments is false, then it also checks that none of the arguments contain augmented
     * components (and it returns an appropriate message too).
     */
    public static Optional<String> checkArguments(CommandArguments args, String cmdname,
        List<ArgName> knownArgs, boolean acceptsEditArguments) {

        // it's easier to do this imperatively.
        for (var arg : args.getAllArguments()) {
            if (!arg.fst().getComponents().isEmpty() && !acceptsEditArguments) {
                return Optional.of(String.format("'%s' command doesn't support edit-arguments (found '%s')",
                    cmdname, arg.fst()
                ));
            } else if (!knownArgs.contains(arg.fst())) {
                return Optional.of(String.format("'%s' command doesn't support '%s'",
                    cmdname, arg.fst()
                ));
            }
        }

        return Optional.empty();
    }

    /**
     * Convenience overload of {@code checkArguments} for commands that don't support edit args.
     */
    public static Optional<String> checkArguments(CommandArguments args, String cmdname,
        List<ArgName> knownArgs) {

        return checkArguments(args, cmdname, knownArgs, false);
    }

    /**
     * Convenience overload of {@code checkArguments} for commands that take no arguments.
     */
    public static Optional<String> checkArguments(CommandArguments args, String cmdname) {
        return checkArguments(args, cmdname, List.of(), false);
    }



    /**
     * Gets the 'target' of a command, which is either 'ingredient' or 'recipe'. Returns either an error
     * if the target was invalid or empty, or a pair consisting of the {@code CommandTarget}, and the
     * rest of the unnamed arguments.
     *
     * @param acceptsPlural determines if 'recipeS' and 'ingredientS' are accepted in addition
     *                      to their singular counterparts.
     */
    public static Result<Pair<CommandTarget, String>> getCommandTarget(CommandArguments args,
        boolean acceptsPlural) {

        var str = args.getRemaining();

        if (str.isEmpty()) {
            return Result.error("no target specified (either 'recipe', 'ingredient' or 'recommendation')");
        }

        var x = new StringView(str).bisect(' ').fst().trim();
        var xs = new StringView(str).bisect(' ').snd().trim();

        return Result.ofOptional(
            CommandTarget.of(x.toString(), acceptsPlural)
                .map(target -> Pair.of(target, xs.toString())),

            String.format("Unknown target '%s'", x)
        );
    }

    /**
     * See {@code getCommandTarget(CommandArguments)}; this one is just a convenience overload.
     */
    public static Result<Pair<CommandTarget, String>> getCommandTarget(CommandArguments args) {
        return getCommandTarget(args, /* acceptsPlural: */ false);
    }

    /**
     * Ensures that the command name matches the given string.
     */
    public static void ensureCommandName(CommandArguments args, String name) {
        if (!args.getCommand().equals(name)) {
            throw new IllegalArgumentException("invalid command name passed to parser");
        }
    }
}
