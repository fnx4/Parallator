package com.kursx.parallator;


import java.util.ArrayList;
import java.util.List;

public class Book {

    public static class Lang {
        public String name;
        public String lang;
        public String author;
        public String translation;
        public Integer translationSize;

        public Lang(String lang, String name,  String author,
                    String translation, String translationSize) {
            this.name = name;
            this.lang = lang;
            this.author = author;
            this.translation = translation;
            this.translationSize = translationSize != null
                    && !translationSize.isEmpty()
                    && !translationSize.equals("null")
                    ? Integer.parseInt(translationSize) : null;
        }
    }

    private String direction;
    private String name;
    private String author;
    public  String hash;
    private String thumbnail = "";
    private String filename = "";
    private List<Lang> langs = new ArrayList<>();
    private List<Chapter> chapters;

    public Book(String hash, String name, String author,  String from, String to, List<Chapter> chapters) {
        this.name = name;
        this.direction = from + "-" + to;
        this.hash = hash;
        this.chapters = chapters;
        this.author = author;
        langs.add(new Lang(from, name, author, null, null));
        initFileName();
    }

    public void initFileName() {
        filename = "";
        for (String part : name.split(" ")) {
            filename += part.toLowerCase() + "_";
        }
        filename = filename.substring(0, filename.length() - 1)
                .replace("'", "")
                .replace(":", "")
                .replace(")", "")
                .replace("(", "")
                + ".sb";
    }


    public Book() {
    }

    public void update(String hash, String name, String author,  String from, String to, String thumbnail) {
        this.thumbnail = thumbnail;
        this.name = name;
        this.direction = from + "-" + to;
        this.hash = hash;
        this.chapters = new ArrayList<>();
        this.author = author;
        langs.add(new Lang(from, name, author, null, null));
        initFileName();
        this.thumbnail = thumbnail;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public String getLang() {
        return direction;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getFilename() {
        return filename;
    }

    public List<Lang> getLangs() {
        return langs;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }


    public List<Chapter> getOnlyChaptersWithParagraphs(List<Chapter> chapters, String path) {
        if (chapters == null) chapters = this.chapters;
        List<Chapter> allChapters = new ArrayList<>();
        int counter = 0;
        for (Chapter chapter : new ArrayList<>(chapters)) {
            String currPath = path + counter;
            if (chapter.getChapters() != null && !chapter.getChapters().isEmpty()) {
                allChapters.addAll(getOnlyChaptersWithParagraphs(chapter.getChapters(), currPath + "-"));
            } else {
                chapter.path = currPath;
                allChapters.add(chapter);
            }
            counter++;
        }
        return allChapters;
    }
}
