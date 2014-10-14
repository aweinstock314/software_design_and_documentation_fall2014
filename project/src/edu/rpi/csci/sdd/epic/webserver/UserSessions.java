package edu.rpi.csci.sdd.epic.webserver;

import java.util.*;
import java.net.InetSocketAddress;

// imports from http://shiro.apache.org/tutorial.html
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.sun.net.httpserver.HttpExchange;

import edu.rpi.csci.sdd.epic.util.Util;

public class UserSessions
{
    protected Map<InetSocketAddress, Subject> addressSubjectMapping = new HashMap<InetSocketAddress, Subject>();
    public void doLogin(HttpExchange e)
    {
        InetSocketAddress address = e.getRemoteAddress();
        String username = "username";
        String password = "password";
        System.out.printf("Handling a login for %s: (\"%s\", \"%s\")\n", address, username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
    }
}
