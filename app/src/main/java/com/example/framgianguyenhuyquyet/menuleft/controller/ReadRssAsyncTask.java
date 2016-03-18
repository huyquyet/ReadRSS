package com.example.framgianguyenhuyquyet.menuleft.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.Toast;

import com.example.framgianguyenhuyquyet.menuleft.models.Data;
import com.example.framgianguyenhuyquyet.menuleft.models.Informations;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by FRAMGIA\nguyen.huy.quyet on 15/03/2016.
 */
public class ReadRssAsyncTask extends AsyncTask<Void, ArrayList<Data>, ArrayList<Data>> {
    private Activity context;
    private String source = "http://www.voanews.com/api/epiqq";
    private URL url = null;
    private HttpURLConnection conn = null;
    private InputStream stream = null;
    private XmlPullParserFactory xmlFactoryObject = null;
    private XmlPullParser myparser = null;
    private Data item = null;
    private ArrayList<Data> items = new ArrayList<Data>();
    private Informations informations = new Informations();
//    ArrayList<Category> categoryArrayList = new ArrayList<>();

    public ReadRssAsyncTask(Activity ctx) {
        context = ctx;
    }

    public Informations getInformations() {
        return informations;
    }

    public ArrayList<Data> getItems() {
        return items;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Data> doInBackground(Void... params) {
        try {
            url = new URL(source);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            stream = conn.getInputStream();
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream, null);

            parseXMLAndStoreIt(myparser);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NetworkOnMainThreadException e) {
            e.printStackTrace();
            Toast.makeText(context, " Errors ", Toast.LENGTH_LONG).show();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        publishProgress(items);
        return null;
    }

    @Override
    protected void onProgressUpdate(ArrayList<Data>... values) {
        super.onProgressUpdate(values);


    }

    @Override
    protected void onPostExecute(ArrayList<Data> data) {
        super.onPostExecute(data);
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            event = myParser.getEventType();
            boolean insideItem = false;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG: // event = 2
                        if (name.equals("item")) {
                            insideItem = true;
                            item = new Data();
                        }
                        break;
                    case XmlPullParser.TEXT: // event = 4
                        text = myParser.getText().trim();
                        break;
                    case XmlPullParser.END_TAG: // event = 3
                        if (insideItem) {
                            if (name.equalsIgnoreCase("title")) {
                                item.setTitle(text); //extract the headline
                            } else if (name.equals("description")) {
                                item.setDescription(text);
                            } else if (name.equalsIgnoreCase("link")) {
                                item.setLink(text); //extract the link of article
                            } else if (name.equals("guid")) {
                                item.setGuid(text);
                            } else if (name.equals("pubDate")) {
                                Date date = formatDate(text);
                                item.setPubDate(dateFormat.format(date));
                            } else if (name.equals("category")) {
                                item.setCategory(text);
                            } else if (name.equals("author")) {
                                item.setAuthor(text);
                            } else if (name.equals("enclosure")) {
                                item.setEnclosure(myParser.getAttributeValue(0));
                            } else if (name.equals("item")) {
                                insideItem = false;
                                items.add(item);
                            }
                        } else {
                            if (name.equals("title"))
                                informations.setTitle(text);
                            else if (name.equals("link"))
                                informations.setLink(text);
                            else if (name.equals("description"))
                                informations.setDescription(text);
                            else if (name.equals("image"))
                                informations.setImage(text);
                            else if (name.equals("language"))
                                informations.setLanguage(text);
                            else if (name.equals("copyright"))
                                informations.setCopyright(text);
                            else if (name.equals("lastBuildDate")) {
                                Date date = formatDate(text);
                                informations.setLastBuildDate(dateFormat.format(date));
                            } else if (name.equals("generator"))
                                informations.setGenerator(text);
                            else if (name.equals("atom:link"))
                                informations.setAtom(myParser.getAttributeValue(0));
                            else if (name.equals("ttl"))
                                informations.setTtl(text);
                        }
                        break;
                }
                event = myParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            Log.d("Loi", e.toString());
            e.printStackTrace();
        }
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public Date formatDate(String data) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        Date date = dateFormat.parse(data);
        System.out.println(date);
        return date;
    }
}
