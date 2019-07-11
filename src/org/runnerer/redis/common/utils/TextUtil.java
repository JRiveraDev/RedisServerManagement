package org.runnerer.redis.common.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TextUtil
{
    public static void write(String path, String f) throws IOException
    {

        try(FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(f);

        } catch (IOException e)
        {

        }

    }
}
