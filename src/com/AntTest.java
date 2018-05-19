package com;

import org.apache.commons.lang3.StringUtils;

import java.util.ResourceBundle;

public class AntTest {

    public static void main(String[] args) {
        if (args != null && args.length > 0 && StringUtils.isNoneBlank(args[0])) {
            System.out.println("not blank!");
        } else {
            System.out.println("is blank!");
        }

        ResourceBundle resource = ResourceBundle.getBundle("config");
        String serverName = resource.getString("server.name");
        System.out.printf("---------this is my serverName: %s", serverName);
    }

}
