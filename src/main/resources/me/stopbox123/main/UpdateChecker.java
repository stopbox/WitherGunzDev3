// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpdateChecker.java

package me.stopbox123.main;

import java.io.IOException;
import java.net.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.plugin.PluginDescriptionFile;
import org.w3c.dom.*;

// Referenced classes of package me.stopbox123.main:
//            WitherGunz

public class UpdateChecker
{

    public UpdateChecker(WitherGunz plugin, String url)
    {
        this.plugin = plugin;
        try
        {
            filesFeed = new URL(url);
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean updateNeeded()
    {
        java.io.InputStream input;
		try {
			input = filesFeed.openConnection().getInputStream();
			 Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
		        Node update = document.getElementsByTagName("item").item(0);
		        NodeList children = update.getChildNodes();
		        version = children.item(1).getTextContent().replaceAll("[a-zA-Z ]", " ");
		        link = children.item(3).getTextContent();
		        if(!plugin.getDescription().getVersion().equals(version));
		} catch (Exception e) {
			
			e.printStackTrace();
            return true;

		}
		return false;
       
    }

    public String getVersion()
    {
        return version;
    }

    public String getLink()
    {
        return link;
    }

    private WitherGunz plugin;
    private URL filesFeed;
    private String version;
    private String link;
}
