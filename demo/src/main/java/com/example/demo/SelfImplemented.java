package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelfImplemented {
    public static List<String> getArticleTitles(String author) throws IOException {
        List<String> titles = new ArrayList<>();
        int currentPage = 1;
        boolean hasMorePages = true;
        ObjectMapper mapper = new ObjectMapper();

        while (hasMorePages) {
            String apiUrl = "https://jsonmock.hackerrank.com/api/articles?author=" + author + "&page=" + currentPage;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            bufferedReader.close();

            JsonNode node = mapper.readTree(buffer.toString());
            JsonNode data = node.path("data");

            // Process articles
            for (JsonNode article : data) {
                String title = article.path("title").asText(null);
                String storyTitle = article.path("story_title").asText(null);

                if (title != null && !title.isEmpty()) {
                    titles.add(title);
                    System.out.println("title"+title+"\n");
                } else if (storyTitle != null && !storyTitle.isEmpty()) {
                    titles.add(storyTitle);
                    System.out.println("title"+title+"\n");
                }
                
            }

            // Check if there are more pages
            long totalPages = node.path("total_pages").asLong();
            currentPage++;
            if (currentPage > totalPages) {
                hasMorePages = false;
            }
        }

        return titles;
    }
}
