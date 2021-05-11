package com.krish_the_dev.rssfeedviewer;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    List headlines, links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NewsFetchTask().execute();
    }

    class NewsFetchTask extends AsyncTask<Object, Void, ArrayAdapter> {

        @Override
        protected ArrayAdapter doInBackground(Object... objects) {
            headlines = new ArrayList();
            links = new ArrayList();
            try {
                URL feedUrl = new URL("https://feeds.bbci.co.uk/news/world/rss.xml");
                XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                parserFactory.setNamespaceAware(false);
                XmlPullParser parser = parserFactory.newPullParser();

                parser.setInput(feedUrl.openConnection().getInputStream(), "UTF_8");

                boolean insideItem;
                for (int eventType = parser.getEventType();
                     eventType != XmlPullParser.END_DOCUMENT;
                     eventType = parser.next()) {
                    if (eventType == XmlPullParser.START_TAG) {
                        insideItem = parser.getName().equalsIgnoreCase("item");
                        Log.d("RSS", parser.getName());
                        if (parser.getName().equalsIgnoreCase("title")) {
                                headlines.add(parser.nextText()); //extract the headline
                        }
                        else if (parser.getName().equalsIgnoreCase("link")) {
                                links.add(parser.nextText()); //extract the link of article
                        }
                    }
                    insideItem = !(eventType == XmlPullParser.END_DOCUMENT && parser.getName().equalsIgnoreCase("item"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(ArrayAdapter arrayAdapter) {
            super.onPostExecute(arrayAdapter);
            arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, headlines);
            setListAdapter(arrayAdapter);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Uri uri = Uri.parse(links.get(position).toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
