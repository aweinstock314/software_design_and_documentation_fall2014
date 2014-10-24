package edu.rpi.csci.sdd.epic.cli;

import java.util.TreeMap;

abstract class SubCommand
{
    public abstract String usage();
    public abstract boolean checkArgs(String[] args);
    public abstract void action(String[] args);
    public void run(String[] args)
    {
        if(checkArgs(args)) { action(args); }
        else { System.err.println(usage()); }
    }
}

public class AccountTool
{
    static final TreeMap<String, SubCommand> commands = new TreeMap<String, SubCommand>();
    static
    {
        commands.put("create", new SubCommand() {
            public String usage() { return "Usage: create username password"; }
            public boolean checkArgs(String[] args) { return args.length == 3; }
            public void action(String[] args) {
                String username = args[1];
                String password = args[2];
                System.out.printf("Creating account with username %s and password %s\n", username, password);
            }
        });
    }
    public static void main(String[] args)
    {
        if(args.length > 0) { commands.get(args[0]).run(args); }
        else { System.err.printf("Usage: %s subcommand [subcommand_arguments]\n", AccountTool.class.getName()); }
    }
}
