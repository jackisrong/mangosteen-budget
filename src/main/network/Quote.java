package network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class Quote {
    private String quoteContent;
    private String quoteAuthor;

    // EFFECTS: create new quote and parse quote
    public Quote() {
        parseQuote();
    }

    // Code from deliverable 10 EdX page
    private String getRandomQuote() throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL("http://quote-garden.herokuapp.com/quotes/random");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return sb.toString();
    }

    // MODIFIES: this
    // EFFECTS: get a random quote then parse the JSON and sets fields
    public void parseQuote() {
        String json = "";
        try {
            json = getRandomQuote();
        } catch (IOException e) {
            System.out.println("Encountered IOException while getting random quote from the interwebs.");
        }

        Gson gson = new Gson();
        Map parsedMap = gson.fromJson(json, Map.class);

        quoteContent = parsedMap.get("quoteText").toString();
        quoteAuthor = parsedMap.get("quoteAuthor").toString();

        if (quoteAuthor.equals("")) {
            quoteAuthor = "Anonymous";
        }
    }

    // EFFECTS: returns quote contents
    public String getQuoteContent() {
        return quoteContent;
    }

    // EFFECTS: returns quote author
    public String getQuoteAuthor() {
        return quoteAuthor;
    }
}
