package com.example.demo.model;

public class QuizDTO {
    private Integer id;
    private String title;
    private String category;
    private int numQ;

    public QuizDTO(Integer id,String title, String category, int numQ) {
        this.id=id;
        this.title = title;
        this.category = category;
        this.numQ = numQ;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getNumQ() {
        return numQ;
    }

    public int getId(){
        return id;
    }

}
