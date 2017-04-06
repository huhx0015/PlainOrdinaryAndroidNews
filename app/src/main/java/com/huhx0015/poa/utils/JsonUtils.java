package com.huhx0015.poa.utils;

import android.util.Log;
import com.huhx0015.poa.models.Article;
import com.huhx0015.poa.models.Articles;
import com.huhx0015.poa.models.Source;
import com.huhx0015.poa.models.Sources;
import com.huhx0015.poa.models.UrlsToLogos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class JsonUtils {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /** JSON PARSER METHODS ____________________________________________________________________ **/

    public static Articles articlesFromJson(JSONObject jsonObject) {
        Articles articles = new Articles();

        try {

            String status = jsonObject.getString("status");
            String source = jsonObject.getString("source");
            String sortBy = jsonObject.getString("sortBy");

            articles.setStatus(status);
            articles.setSource(source);
            articles.setSortBy(sortBy);

            JSONArray articleArray = jsonObject.getJSONArray("articles");
            List<Article> articleList = new ArrayList<>();
            for (int index = 0; index < articleArray.length(); index++) {
                articleList.add(articleFromJson(articleArray.getJSONObject(index)));
            }
            articles.setArticles(articleList);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }

        return articles;
    }

    public static Article articleFromJson(JSONObject jsonObject) {
        Article article = new Article();

        try {
            String author = jsonObject.getString("author");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");
            String url = jsonObject.getString("url");
            String urlToImage = jsonObject.getString("urlToImage");
            String publishedAt = jsonObject.getString("publishedAt");

            article.setAuthor(author);
            article.setTitle(title);
            article.setDescription(description);
            article.setUrl(url);
            article.setUrlToImage(urlToImage);
            article.setPublishedAt(publishedAt);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }

        return article;
    }

    public static Sources sourcesFromJson(JSONObject jsonObject) {
        Sources sources = new Sources();

        try {
            String status = jsonObject.getString("status");
            sources.setStatus(status);

            JSONArray sourceArray = jsonObject.getJSONArray("sources");
            List<Source> sourceList = new ArrayList<>();
            for (int index = 0; index < sourceArray.length(); index++) {
                sourceList.add(sourceFromJson(sourceArray.getJSONObject(index)));
            }
            sources.setSources(sourceList);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }

        return sources;
    }

    public static Source sourceFromJson(JSONObject jsonObject) {
        Source source = new Source();

        try {
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            String url = jsonObject.getString("url");
            String category = jsonObject.getString("category");
            String language = jsonObject.getString("language");
            String country = jsonObject.getString("country");

            source.setId(id);
            source.setName(name);
            source.setDescription(description);
            source.setUrl(url);
            source.setCategory(category);
            source.setLanguage(language);
            source.setCountry(country);

            JSONObject urlsToLogoObject = jsonObject.getJSONObject("urlsToLogos");
            UrlsToLogos urlsToLogos = urlsToLogosFromJson(urlsToLogoObject);
            source.setUrlsToLogos(urlsToLogos);

            JSONArray sortBysAvailableArray = jsonObject.getJSONArray("sortBysAvailable");
            List<String> sortBysAvailableList = new ArrayList<>();
            for (int index = 0; index < sortBysAvailableArray.length(); index++) {
                sortBysAvailableList.add(sortBysAvailableArray.getString(index));
            }
            source.setSortBysAvailable(sortBysAvailableList);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }

        return source;
    }

    private static UrlsToLogos urlsToLogosFromJson(JSONObject jsonObject) {
        UrlsToLogos urlsToLogos = new UrlsToLogos();

        try {
            String small = jsonObject.getString("small");
            String medium = jsonObject.getString("medium");
            String large = jsonObject.getString("large");

            urlsToLogos.setSmall(small);
            urlsToLogos.setMedium(medium);
            urlsToLogos.setLarge(large);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "ERROR: An error occurred while deserializing JSON object.");
            e.printStackTrace();
        }

        return urlsToLogos;
    }
}