package com.byavallone.watch2night.data;

/**
 * Class used to hold the object movie and the information
 */
public class Movies {

    // Declaring all the variable tha will hold the movies information
    private String mTitle;
    private String mReleaseDate;
    private String mPosterUrl;
    private String mBackgroundUrl;
    private String mVoteAverage;
    private String mSynopsis;

    /**
     * Constructor
     * @param title
     * @param releaseDate
     * @param posterUrl
     * @param voteAverage
     * @param synopsis
     */
    Movies(String title, String releaseDate, String posterUrl, String backgroundUrl, String voteAverage, String synopsis){
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterUrl = posterUrl;
        mBackgroundUrl = backgroundUrl;
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
     * Method used to get the URL for the poster
     * @return
     */
    public String getPosterUrl(){
        return mPosterUrl;
    }

    /**
     * Method used to get the background URL
     * @return
     */
    public String getBackgroundUrl(){return mBackgroundUrl;}

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
