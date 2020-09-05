package com.ihfazh.dicodingmyquote;

public class QuoteData  {
    private String quote, author;

    public QuoteData(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }
}
