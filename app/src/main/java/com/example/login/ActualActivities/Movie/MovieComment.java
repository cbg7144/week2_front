package com.example.login.ActualActivities.Movie;

public class MovieComment {

    // 수정된 코드
    private Movie movie;
    public Movie getMovie() {return movie;}
    public void setMovie(Movie movie) {this.movie = movie;}
    //


    private String commentid;
    private int score;
    private String linecomment;
    private String longcomment;

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLinecomment() {
        return linecomment;
    }

    public void setLinecomment(String linecomment) {
        this.linecomment = linecomment;
    }

    public String getLongcomment() {
        return longcomment;
    }

    public void setLongcomment(String longcomment) {
        this.longcomment = longcomment;
    }
}
