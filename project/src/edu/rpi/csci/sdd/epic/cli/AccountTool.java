package edu.rpi.csci.sdd.epic.cli;

import java.util.TreeMap;

import edu.rpi.csci.sdd.epic.db.AccountModel;

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
            public String usage() { return "Usage: create id event_provider email username password"; }
            public boolean checkArgs(String[] args) { return args.length == 6; }
            public void action(String[] args) {
                String id = args[1];
                boolean event_provider = Boolean.valueOf(args[2]);
                String email_address = args[3];
                String username = args[4];
                String password = args[5];
                try { AccountModel.createAccount(id, event_provider, email_address, username, password); }
                catch(Exception e) { e.printStackTrace(); }
            }
        });
    }
    public static void main(String[] args)
    {
        if(args.length > 0) { commands.get(args[0]).run(args); }
        else { System.err.printf("Usage: %s subcommand [subcommand_arguments]\n", AccountTool.class.getName()); }
    }
}
