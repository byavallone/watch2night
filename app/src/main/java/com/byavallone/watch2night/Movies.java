package com.byavallone.watch2night;

/**
 * Classe used to hold the object movie and the information
 */
public class Movies {

    // Declaring all the variable tha will hold the movies information
    private String mTitle;
    private String mReleaseDate;
    private String mPosterUrl;
    //TODO Fix this value
    private String mVoteAverage;
    private String mSynopsis;

    Movies(String title, String releaseDate, String posterUrl, String voteAverage, String synopsis){
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterUrl = posterUrl;
        mVoteAverage = voteAverage;
        mSynopsis = synopsis;
    }

    /**
     * Method used to get the title
     * @return
     */
    public String getTitle(){
        return mTitle;
    }

    /**
     * Method used tp get the release date of the movie
     * @return
     */
    public String getReleaseDate(){
        return mReleaseDate;
    }
    /**
     * Method used to get the String that hold the URL for the poster
     * @return
     */
    public String getPosterUrl(){
        return mPosterUrl;
    }

    /**
     * Method used to get the VoteAverage
     * @return
     */
    public String getVoteAverage(){
        return mVoteAverage;
    }

    /**
     * Method used to get the Synopsis of the movie
     * @return
     */
    public String getSynopsis(){
        return mSynopsis;
    }
}
