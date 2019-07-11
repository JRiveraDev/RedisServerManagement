package org.runnerer.redis;

import org.runnerer.redis.database.MySQL;
import org.runnerer.redis.redis.RedisServerManagement;

import java.sql.ResultSet;
import java.util.Scanner;

public class ServerManagement
{

    public static void main(String[] args)
    {
        System.out.println("This program should only be used for QubeHost Hosting services, written by John Rivera! Copyright 2019 All rights reserved");
        System.out.println("This is in text only mode due to your operating system.");
        Scanner scanner = new Scanner(System.in);

        new MySQL();
        try
        {
            MySQL.Instance.openConnection();
        }
        catch (Exception e)
        {
            System.exit(0);
        }

        try
        {
            ResultSet resultSet = MySQL.Instance.querySQL("SELECT * FROM qubeSMData");
            String pinKey = resultSet.getString("pinKey");

            System.out.println("Please enter the pinkey sent by your manager. ");
            String pinPut = scanner.next();

            if (pinKey != pinPut)
            {
                System.exit(0);
            }
        }
        catch (Exception e)
        {
            System.exit(0);
        }
        System.out.println("What would you like to do today? (create=c/retrieve=r) ");
        String option1 = scanner.next();

        if (option1.equalsIgnoreCase("c"))
        {
            System.out.println("What is the name of this server? ");
            String name = scanner.next();

            System.out.println("Yasss! What is the type of this server? ");
            String type = scanner.next();
            ServerType serverType = null;

            if (type.equalsIgnoreCase("Regular")) serverType = ServerType.REGULAR;
                    else if (type.equalsIgnoreCase("Premium")) serverType = ServerType.PREMIUM;
                        else if (type.equalsIgnoreCase("Business")) serverType = ServerType.BUSINESS;
             else
            {
                System.out.println("Incorrect type!");
                System.exit(0);
            }

            System.out.println("Perfect! Is this an US server? (y/n) ");
            String us = scanner.next();
            boolean usBoolean = true;

            if (us.equalsIgnoreCase("n"))
            {
                usBoolean = false;
            }

            System.out.println("Cool! IP? ");
            String ip = scanner.next();

            System.out.println("Awesome sauce! What is the ram amount? ");
            String ram = scanner.next();

            System.out.println("Sweet! What is the CPU? ");
            String cpu = scanner.next();

            System.out.println("Nice, last one! What is the account owner's account name? ");
            String accountName = scanner.next();

            RedisServerManagement.addServer(name, serverType, usBoolean, ip, accountName, ram, cpu);

            try
            {
                MySQL.Instance.updateSQL("INSERT INTO serverData (`serverName`, `ip`, `ram`, `cpu`) VALUES ('" + name + "', '" + ip + "', '" + ram + "', '" + cpu + "');");
            } catch (Exception e)
            {
                System.exit(0);
            }
        } else
        {
            System.out.println("Alrighty, let's retrieve a server! What is the name of this server? ");
            String name = scanner.next();

            RedisServerManagement.retrieveServer(name);
        }
    }
}
